/**
 * Driver class for Advice Wizard
 */
package editor.driver;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import editor.ui.wizard.AdviceWizard;

public class AdviceWizardDriver {
	public AdviceWizardDriver() {
		Shell s = new Shell();
		AdviceWizard wizard = new AdviceWizard();
		WizardDialog dialog = new WizardDialog(s, wizard);
		dialog.setBlockOnOpen(true);
		dialog.open();
	}
}
