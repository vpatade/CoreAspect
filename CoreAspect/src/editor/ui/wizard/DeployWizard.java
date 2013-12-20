package editor.ui.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;

import editor.data.DataModel;
import editor.logic.UpdateAspect;

public class DeployWizard extends Wizard {
	DeployWizardPage deployPage;

	public DeployWizard() {
		setWindowTitle("Deployment Wizard");
		setNeedsProgressMonitor(true);
	}

	public void addPages() {
		deployPage = new DeployWizardPage();
		addPage(deployPage);
	}

	@Override
	public boolean canFinish() {
		if (!deployPage.enginePathText.getText().replaceAll(" ", "").equalsIgnoreCase("") && deployPage.flag)
			return true;
		return false;
	}

	public boolean performFinish() {
		// Copy Aspect
		new UpdateAspect();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(DataModel.projectName);
		try {
			FileChannel in = new FileInputStream("C:/Aspects.bpaspect").getChannel();
			FileChannel out = new FileOutputStream("C:/aspects/Aspects.bpaspect").getChannel();
			FileChannel out1 = new FileOutputStream("D:/aspects/Aspects.bpaspect").getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (in.read(buffer) != -1) {
				buffer.flip(); // Prepare for writing
				out.write(buffer);

				buffer.clear(); // Prepare for reading
			}
			in = new FileInputStream("C:/Aspects.bpaspect").getChannel();
			while (in.read(buffer) != -1) {
				buffer.flip(); // Prepare for writing
				out1.write(buffer);

				buffer.clear(); // Prepare for reading
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// jar creation
		try {
			Runtime.getRuntime().exec(
					"jar -cf " + deployPage.enginePathText.getText()
							+ "/coreAspects.jar /meta-inf/aspect.amf /aspects");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Packaging advice implementation
		File fx = new File("/samples");
		if (fx.isDirectory()) {
			File[] fi = fx.listFiles();
			for (int i = 0; i < fi.length; i++) {
				if (fi[i].getPath().compareToIgnoreCase("/GxmlUtils.class") != 0)
					fi[i].delete();
			}
		}
		int j = 0;
		for (int i = 0; i < DataModel.adviceImplementations.size(); i++) {
			for (j = 0; j < i; j++) {
				if (DataModel.adviceImplementations.get(i).equals(DataModel.adviceImplementations.get(j))) {
					break;
				}
			}
			if (j < i)
				continue;
			String str = DataModel.adviceImplementations.get(i);
			str = str.substring(str.lastIndexOf('\\') + 1, str.length());
			File f = new File("/samples/" + str);
			if (!f.exists())
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			CopyFile(DataModel.adviceImplementations.get(i), "/samples/" + str);

		}
		try {
			Runtime.getRuntime().exec(
					"jar -cf " + deployPage.enginePathText.getText()
							+ "/coreAspect.jar /com");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	void CopyFile(String src, String dest) {
		FileChannel in;
		try {
			in = new FileInputStream(src).getChannel();
			FileChannel out = new FileOutputStream(dest).getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (in.read(buffer) != -1) {
				buffer.flip(); // Prepare for writing
				out.write(buffer);
				buffer.clear(); // Prepare for reading
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#performCancel()
	 */
	public boolean performCancel() {
		return (MessageDialog.openConfirm(getShell(), "Confirmation", "Are you sure to cancel the Deployment Wizard?"));
	}
}
