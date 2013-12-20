package editor.plugin.wizard;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import javax.xml.transform.*; 
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.StreamResult; 
import java.io.*;
import java.util.Vector;

public class Concatination {
	
	Concatination(Vector<String> processFile)
	{
		try{
			
			 DocumentBuilderFactory documentBuilderFactory = 
                 DocumentBuilderFactory.newInstance();
			 DocumentBuilder documentBuilder = 
				 documentBuilderFactory.newDocumentBuilder();
			 Document document = documentBuilder.newDocument();
			 Element rootElement = document.createElement("pd:Project");
			 document.appendChild(rootElement);	
			rootElement.setAttribute("xmlns:pd","/process/Editor");		 
			Element ProjectName=document.createElement("pd:name");
			 rootElement.appendChild(ProjectName);	
			 String str=AspectEditorWizard.selected;
			 str=str.substring(str.lastIndexOf('/')+1, str.length());
			 ProjectName.appendChild(document.createTextNode(str));	
			for(int i=0;i<processFile.size();i++)
			{
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new File(processFile.elementAt(i)));
				doc.getDocumentElement().normalize();
		   
			
				
				Node targetSections = doc.getElementsByTagName("pd:ProcessDefinition").item(0);
				
				rootElement.appendChild(document.adoptNode(targetSections));
				Node target11 = document.getElementsByTagName("pd:ProcessDefinition").item(0);
			
				document.renameNode(target11, null, "processDefinition");

			}
			 TransformerFactory transfac = TransformerFactory.newInstance();
			 Transformer trans = transfac.newTransformer();
			  DOMSource source = new DOMSource(document);
			  
			  IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			  IProject project = root.getProject(AspectEditorWizard.aaproject);
			   StreamResult result =  new StreamResult(new FileOutputStream(new File(project.getLocationURI().getPath()+"/Project.process")));
			    trans.transform(source, result);

			  
		}
		catch(Exception e)
		{
			System.out.println("XML Parsing error: "+e);
			
		}
		
	}
	

}
