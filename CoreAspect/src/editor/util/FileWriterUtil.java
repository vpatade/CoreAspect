package editor.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import editor.data.DataModel;

public class FileWriterUtil {

	@SuppressWarnings("deprecation")
	public void writeAspectFile(String path) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(DataModel.projectName);
		if (path.equalsIgnoreCase(Constants.DEFAULT)) {
			path = project.getLocationURI().getPath() + Constants.ASPEFT_FILE;
		}
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element rootElement = document.createElement(Constants.ELE_ASPECT);
			rootElement.setAttribute(Constants.ATT_ORDER, "1");
			rootElement.setAttribute(Constants.ATT_TNS, Constants.VAL_TNS);
			rootElement.setAttribute(Constants.ATT_XNS, Constants.VAL_XNS);
			rootElement.setAttribute(Constants.ATT_XSI, Constants.VAL_XSI);
			rootElement.setAttribute(Constants.ATT_SCHEMA_LOCATION, Constants.VAL_SCHEMA_LOCATION);

			document.appendChild(rootElement);

			int i = DataModel.pointCutNames.size();

			for (int j = 0; j < i; j++) {
				Element pointCutElement = document.createElement(Constants.ELE_POINTCUT);

				pointCutElement.setAttribute(Constants.ATT_NAME, DataModel.pointCutNames.get(j));
				rootElement.appendChild(pointCutElement);
				Element queryElement = document.createElement(Constants.ELE_QUERY);
				queryElement.setAttribute(Constants.ATT_QUERY_LANG, Constants.VAL_QUERY_LANG);
				queryElement.setTextContent(DataModel.pointCutQueries.get(j));

				pointCutElement.appendChild(queryElement);
			}

			i = DataModel.adviceNames.size();

			for (int j = 0; j < i; j++) {
				Element adviceElement = document.createElement(Constants.ELE_ADVICE);
				rootElement.appendChild(adviceElement);
				adviceElement.setAttribute(Constants.ATT_NAME, DataModel.adviceNames.get(j));
				adviceElement.setAttribute(Constants.ATT_POINTCUT, DataModel.advicePointCutNames.get(j));
				Element documentationElement = document.createElement(Constants.ELE_DOCU);
				if (!DataModel.adviceDocumentations.get(j).equals(" ")) {
					documentationElement.setTextContent(DataModel.adviceDocumentations.get(j));
				}
				adviceElement.appendChild(documentationElement);
				Element activityElement = document.createElement(Constants.ELE_ACTIVITY);
				activityElement.setAttribute(Constants.ATT_WHERE, DataModel.adviceWhen.get(j));
				adviceElement.appendChild(activityElement);
				Element implementationElement = document.createElement(Constants.ELE_IMPL);
				String str;
				str = DataModel.adviceImplementations.get(j);
				implementationElement.setAttribute(Constants.ATT_CLASS_NAME, str);
				activityElement.appendChild(implementationElement);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, Constants.YES);
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);

			OutputFormat format = new OutputFormat();
			format.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer();
			serializer.setOutputFormat(format);
			serializer.setOutputCharStream(new java.io.FileWriter(path));
			serializer.serialize(document);

			serializer.setOutputCharStream(new java.io.FileWriter(project.getLocationURI().getPath()
					+ Constants.ASPEFT_FILE));
			serializer.serialize(document);
			IProgressMonitor progressMonitor = new NullProgressMonitor();
			project.getFile(Constants.ASPEFT_FILE).setHidden(true);
			project.refreshLocal(IProject.DEPTH_INFINITE, progressMonitor);
		} catch (Exception e) {
			System.err.println("Error : " + e.getMessage());
			e.printStackTrace();
		}

	}
}