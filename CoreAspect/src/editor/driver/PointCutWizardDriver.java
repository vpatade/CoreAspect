/**
 * Driver class for Pointcut Wizard
 */
package editor.driver;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import editor.ui.wizard.PointcutWizard;

public class PointCutWizardDriver {
	public PointCutWizardDriver() {
		Shell s = new Shell();
		PointcutWizard wizard = new PointcutWizard();
		WizardDialog dialog = new WizardDialog(s, wizard);
		dialog.setBlockOnOpen(true);
		dialog.open();
	}
}
