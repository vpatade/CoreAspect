/**
 * Class which update the generated aspect code.
 * */
package editor.logic;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import editor.data.DataModel;

public class UpdateAspect {

	public UpdateAspect() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(DataModel.projectName);

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc1 = documentBuilder.parse(new File(project.getLocationURI().getPath() + "/AspectOld.bpaspect"));
			doc1.getDocumentElement().normalize();
			Document doc = documentBuilder.newDocument();
			Node targetSections = doc1.getElementsByTagName("aspect").item(0);
			Node add = doc.importNode(targetSections, true);
			doc.appendChild(add);
			NodeList listofad = doc.getElementsByTagName("advice");
			if (listofad.getLength() != 0) {
				for (int i = 0; i < listofad.getLength(); i++) {
					Node impl = doc.getElementsByTagName("ImplementationJava").item(0);
					String str = ((Element) impl).getAttribute("className");
					int j = str.lastIndexOf("\\");
					int k = str.lastIndexOf(".");
					str = str.substring(j + 1, k);
					((Element) impl).setAttribute("className", str);
					doc.renameNode(impl, null, "implementation.java");
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			OutputFormat format = new OutputFormat();
			format.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer();
			serializer.setOutputFormat(format);
			serializer.setOutputCharStream(new java.io.FileWriter("C:/Aspects.bpaspect"));
			serializer.serialize(doc);

		} catch (Exception e) {
			System.out.println("Exception during deploy updation: " + e);
		}
	}
}
