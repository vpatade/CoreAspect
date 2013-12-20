package editor.ui.wizard;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

import editor.data.DataModel;
import editor.ui.plugin.PointCutDetailView;
import editor.util.FileWriterUtil;

public class PointcutWizard extends Wizard {
	PointcutWizardActivityPage pointcutWizardActivityPage;
	PointcutWizardProcessPage pointcutWizardProcessPage;
	PointcutWizardCombineQueryPage pointcutWizardCombineQueryPage;
	AdviceWizardFirstPage adviceWizardFirstPage;

	public PointcutWizard() {
		setWindowTitle("PointCut Creation Wizard");
		setNeedsProgressMonitor(true);
	}

	public void addPages() {
		pointcutWizardActivityPage = new PointcutWizardActivityPage();
		addPage(pointcutWizardActivityPage);
		pointcutWizardProcessPage = new PointcutWizardProcessPage();
		addPage(pointcutWizardProcessPage);
		pointcutWizardCombineQueryPage = new PointcutWizardCombineQueryPage();
		addPage(pointcutWizardCombineQueryPage);
		adviceWizardFirstPage = new AdviceWizardFirstPage();
		addPage(adviceWizardFirstPage);
	}

	@Override
	public boolean canFinish() {
		if (this.getContainer().getCurrentPage().equals(pointcutWizardCombineQueryPage)
				&& pointcutWizardCombineQueryPage.queryText.getText().compareTo("") != 0) {
			return true;
		}
		if (this.getContainer().getCurrentPage().equals(adviceWizardFirstPage)) {
			if (adviceWizardFirstPage.isDone() && adviceWizardFirstPage.flag)
				return true;
		}
		return false;
	}

	public boolean performFinish() {
		if (this.getContainer().getCurrentPage().equals(adviceWizardFirstPage)) {
			if (DataModel.hasAdvice(adviceWizardFirstPage.adviceNameText.getText())) {
				MessageDialog.openError(getShell(), "Error", "Duplicate Advice Name Name");
				return false;
			}
			DataModel.adviceNames.add(adviceWizardFirstPage.adviceNameText.getText());
			DataModel.advicePointCutNames.add(adviceWizardFirstPage.pointcutNameCombo.getText());
			DataModel.adviceWhen.add(adviceWizardFirstPage.whereCombo.getText());
			DataModel.adviceImplementations.add(adviceWizardFirstPage.classNameText.getText());
			DataModel.adviceDocumentations.add(adviceWizardFirstPage.docuText.getText());
		}
		if (!(pointcutWizardCombineQueryPage.queryText.getText().replaceAll(" ", "").compareTo("") == 0)) {
			DataModel.pointCutNames.add(pointcutWizardActivityPage.pointcutNameText.getText());
			DataModel.pointCutQueries.add(pointcutWizardCombineQueryPage.queryText.getText());
		}
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("pcdetails") != null)
			PointCutDetailView.updateView();
		FileWriterUtil fileWriterUtil = new FileWriterUtil();
		fileWriterUtil.writeAspectFile("default");

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#performCancel()
	 */
	public boolean performCancel() {
		return (MessageDialog.openConfirm(getShell(), "Confirmation",
				"Are you sure to cancel the PointCut Definition Wizard?"));
	}
}
