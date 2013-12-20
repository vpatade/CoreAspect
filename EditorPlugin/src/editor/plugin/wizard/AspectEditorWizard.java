package editor.plugin.wizard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import editor.data.DataModel;

public class AspectEditorWizard extends Wizard implements INewWizard {
	@Override
	public void createPageControls(Composite pageContainer) {
		// TODO Auto-generated method stub
		super.createPageControls(pageContainer);
	}

	Browse browsePage;
	SelectProcesses selectProcess;

	public static boolean wizflag = false;
	public static boolean flag = true;
	public static String selected = null;
	public static Vector<String> filenames = new Vector<String>();
	public static String aaproject = null;
	IWorkbenchPage page;
	IFile file;
	protected IStructuredSelection selection;
	protected IWorkbench workbench;

	public AspectEditorWizard() {
		super();
		setWindowTitle("New Aspect Editor");
	}

	public void addPages() {
		browsePage = new Browse(workbench, selection);
		addPage(browsePage);
		selectProcess = new SelectProcesses("Page 2");
		addPage(selectProcess);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;

	}

	@Override
	public boolean performCancel() {
		flag = true;
		return true;
	}

	public boolean canFinish() {
		if (this.getContainer().getCurrentPage().equals(selectProcess))
			return true;
		else if (this.getContainer().getCurrentPage().equals(browsePage)) {
			if (browsePage.flag && browsePage.flag1)
				return true;
			else
				return false;
		}
		return false;
	}

	static void iterateFilesInDirectory(String xmldir) {
		File dir = new File(xmldir);
		File[] files = dir.listFiles();
		if (files != null) {
			for (int idx = 0; idx < files.length; idx++) {
				if (files[idx].isDirectory() == false) {
					String fileName = files[idx].getName();
					if (fileName.endsWith(".process")) {
						filenames.add(files[idx].getAbsolutePath());
					}
				} else {
					iterateFilesInDirectory(files[idx].getAbsolutePath());
				}
			}
		}
	}

	public boolean performFinish() {
		// Add files to the Vector
		DataModel.removeAll();
		if (this.getContainer().getCurrentPage().equals(browsePage)) {
			selected = browsePage.browsetext.getText();
			filenames.removeAllElements();
			iterateFilesInDirectory(selected);
			if (filenames.isEmpty())
				return false;
		} else if (this.getContainer().getCurrentPage().equals(selectProcess)) {
			AspectEditorWizard.filenames.removeAllElements();

			for (int i = 0; i < selectProcess.table.getItemCount(); i++) {
				if (selectProcess.table.getItem(i).getChecked())
					AspectEditorWizard.filenames.add(selectProcess.table
							.getItem(i).getText(0));
			}
		}
		aaproject = browsePage.projname.getText();
		IProgressMonitor progressMonitor = new NullProgressMonitor();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(aaproject);
		try {
			project.create(progressMonitor);
			project.open(progressMonitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		File f = new File(project.getLocationURI().getPath()
				+ "/Properties.properties");
		try {
			FileWriter fstream = new FileWriter(f);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("bpproject=" + selected + "\n");
			out.write("count=" + filenames.size() + "\n");
			for (int i = 0; i < filenames.size(); i++) {
				String str = "process" + i;
				out.write(str + "=" + filenames.elementAt(i) + "\n");

			}
			out.write("projectName=" + aaproject + "\n");
			project.refreshLocal(IProject.DEPTH_INFINITE, progressMonitor);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder;

			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element rootElement = document.createElement("Aspect");
			rootElement.setAttribute("order", "1");
			rootElement.setAttribute("targetNamespace",
					"http://example.org/One");
			rootElement
					.setAttribute("xmlns", "http://schemas.aspecteditor.com/bp");
			rootElement.setAttribute("xmlns:xsi",
					"http://www.w3.org/2001/XMLSchema-instance");
			rootElement.setAttribute("xsi:schemaLocation",
					"http://schemas.aspecteditor.com/bp/schemas");

			document.appendChild(rootElement);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);

			OutputFormat format = new OutputFormat();
			format.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer();
			serializer.setOutputFormat(format);
			serializer.setOutputCharStream(new java.io.FileWriter(project
					.getLocationURI().getPath() + "/Aspects.bpaspect"));
			serializer.serialize(document);

			project.refreshLocal(IProject.DEPTH_INFINITE, progressMonitor);
		} catch (Exception e) {
			System.out.println("Creating aspect file error ::" + e);
		}
		DataModel.projectName = aaproject;
		new Concatination(filenames);

		new EditorLaunch(project.getLocationURI().getPath()
				+ "/Project.process");

		try {
			project.refreshLocal(IProject.DEPTH_INFINITE, progressMonitor);
		} catch (CoreException e) {

			e.printStackTrace();
		}
		return true;
	}

}
