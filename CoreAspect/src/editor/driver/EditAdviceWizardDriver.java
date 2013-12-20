/**
 * Driver class for Edit Advice Wizard
 */
package editor.driver;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.*;

import editor.ui.wizard.EditAdviceWizard;


public class EditAdviceWizardDriver {
	public static String ad="";
	 public EditAdviceWizardDriver(){
			 Shell s=new Shell();			
			 EditAdviceWizard wizard = new EditAdviceWizard();		       
		     WizardDialog dialog = new WizardDialog(s, wizard);
		     dialog.setBlockOnOpen(true);
	         dialog.open();
	 }
}
