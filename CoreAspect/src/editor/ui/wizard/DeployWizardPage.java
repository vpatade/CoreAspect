package editor.ui.wizard;

import java.io.File;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

class DeployWizardPage extends WizardPage implements Listener {
	Text enginePathText;
	Button browseButton;
	Label enginePathLabel;
	boolean flag = true;

	DeployWizardPage() {
		super("Deployment Page");
		setTitle("Deploy The Aspects");
		setDescription("Give Details required for Deployment");
	}

	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout(2, false);
		gl.verticalSpacing = 20;
		gl.marginLeft = 20;
		gl.horizontalSpacing = 20;
		comp.setLayout(gl);
		enginePathLabel = new Label(comp, SWT.PUSH);
		enginePathLabel.setText("Engine Home :");
		enginePathText = new Text(comp, SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = 300;
		enginePathText.setText("C:/");
		enginePathText.setLayoutData(gd);

		GridData gd1 = new GridData(GridData.CENTER, GridData.BEGINNING, false, false, 2, 1);
		gd1.widthHint = 70;
		browseButton = new Button(comp, SWT.PUSH);
		browseButton.setText("Browse");
		browseButton.setLayoutData(gd1);

		// put the line
		Label line = new Label(comp, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
		GridData gridData1 = new GridData(GridData.FILL, GridData.BEGINNING, true, false, 2, 1);
		line.setLayoutData(gridData1);

		enginePathText.addListener(SWT.Modify, this);
		browseButton.addListener(SWT.Selection, this);
		setControl(comp);
	}

	@Override
	public void handleEvent(Event event) {
		if (event.widget instanceof Button) {
			DirectoryDialog dd = new DirectoryDialog(getShell(), SWT.OPEN);
			dd.setText("Browse");
			if (event.widget.equals(browseButton))
				dd.setFilterPath(enginePathText.getText());
			try {
				if (event.widget.equals(browseButton))
					enginePathText.setText(dd.open());
				final String str1 = "\\";
				final String str2 = "/";
				if (event.widget.equals(browseButton))
					enginePathText.setText(enginePathText.getText().replace(str1, str2));
			} catch (Exception ex) {
				System.out.println(ex);
			}
			return;
		}
		if (event.widget instanceof Text) {
			File f = new File(enginePathText.getText());
			if (!f.exists()) {
				setErrorMessage("Path not Exist");
				flag = false;
			} else {
				setErrorMessage(null);
				setDescription("Give the Details required for Deployment");
				flag = true;
			}
			getWizard().getContainer().updateButtons();
		}
	}
}