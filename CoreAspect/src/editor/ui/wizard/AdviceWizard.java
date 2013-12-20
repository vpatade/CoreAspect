package editor.ui.wizard;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

import editor.data.DataModel;
import editor.ui.plugin.PointCutDetailView;
import editor.util.FileWriterUtil;

public class AdviceWizard extends Wizard {
	AdviceWizardSecondPage advicePage;

	public AdviceWizard() {
		setWindowTitle("Advice Creation Wizard");
		setNeedsProgressMonitor(true);
	}

	public void addPages() {
		advicePage = new AdviceWizardSecondPage();
		addPage(advicePage);
		/**/
	}

	@Override
	public boolean canFinish() {
		if (advicePage.adviceNameText.getText().equalsIgnoreCase("")
				|| advicePage.pointcutNamesCombo.getText().equalsIgnoreCase("")
				|| advicePage.classNameText.getText().equalsIgnoreCase("")
				|| advicePage.whereCombo.getText().equalsIgnoreCase("")
				|| advicePage.flag == false) {
			return false;
		}
		return true;
	}

	public boolean performFinish() {
		if (this.getContainer().getCurrentPage().equals(advicePage)) {
			DataModel.adviceNames.add(advicePage.adviceNameText.getText());
			DataModel.advicePointCutNames.add(advicePage.pointcutNamesCombo
					.getText());
			DataModel.adviceWhen.add(advicePage.whereCombo.getText());
			DataModel.adviceImplementations.add(advicePage.classNameText
					.getText());
			DataModel.adviceDocumentations.add(advicePage.docuText.getText());
			FileWriterUtil fileWriterUtil = new FileWriterUtil();
			fileWriterUtil.writeAspectFile("default");
		}
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView("pcdetails") != null)
			PointCutDetailView.updateView();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#performCancel()
	 */
	public boolean performCancel() {
		return (MessageDialog.openConfirm(getShell(), "Confirmation",
				"Are you sure to cancel the Advice Definition Wizard?"));
	}
}
