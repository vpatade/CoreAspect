package editor.ui.wizard;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

import editor.data.DataModel;
import editor.ui.plugin.PointCutDetailView;
import editor.util.FileWriterUtil;

public class EditAdviceWizard extends Wizard {
	EditAdviceWizardPage advicePage;
  public EditAdviceWizard() {
    setWindowTitle("Edit Advice Wizard");
    setNeedsProgressMonitor(true);
  }
  public void addPages() {
	 advicePage=new EditAdviceWizardPage();
	 addPage(advicePage);
	/**/
  }

  @Override
public boolean canFinish() {
	if(advicePage.adviceNameText.getText().compareToIgnoreCase("")==0||advicePage.pointcutNameCombo.getText().compareTo("")==0
			||advicePage.classNameText.getText().compareTo("")==0||advicePage.whereCombo.getText().compareTo("")==0||!advicePage.flag){
		return false;
	}
	return true;
}
public boolean performFinish(){ 
	if(this.getContainer().getCurrentPage().equals(advicePage))
	{
		DataModel.adviceNames.set(advicePage.ind,advicePage.adviceNameText.getText());
		DataModel.advicePointCutNames.set(advicePage.ind,advicePage.pointcutNameCombo.getText());
		DataModel.adviceWhen.set(advicePage.ind,advicePage.whereCombo.getText());
		DataModel.adviceImplementations.set(advicePage.ind,advicePage.classNameText.getText());
		DataModel.adviceDocumentations.set(advicePage.ind,advicePage.docuText.getText());
	}
	if(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("pcdetails")!=null)
		PointCutDetailView.updateView();
	FileWriterUtil fileWriterUtil = new FileWriterUtil();
	fileWriterUtil.writeAspectFile("default");
 	return true;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#performCancel()
   */
  public boolean performCancel() {
    return(MessageDialog.openConfirm(getShell(), "Confirmation", "Are you sure to cancel the Advice Definition Wizard?"));
  }  
}

