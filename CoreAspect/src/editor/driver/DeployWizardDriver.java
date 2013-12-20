/**
 * Driver class for Deploy Wizard
 */
package editor.driver;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.*;

import editor.ui.wizard.DeployWizard;


public class DeployWizardDriver {
	 public DeployWizardDriver(){
			 Shell s=new Shell();			
			 DeployWizard wizard = new DeployWizard();		       
		     WizardDialog dialog = new WizardDialog(s, wizard);
		     dialog.setBlockOnOpen(true);
	         dialog.open();
	 }
}
