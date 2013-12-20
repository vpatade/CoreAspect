package editor.ui.wizard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import editor.data.DataModel;
import editor.logic.ValidateQuery;
import editor.util.Constants;

class PointcutWizardProcessPage extends WizardPage implements Listener {
	Label processNameLabel;
	Combo processNameCombo;
	Label processTNSLabel;
	Combo processTNSCombo;
	Label processKindLabel;
	Combo processKingCombo;
	Label processDescLabel;
	Text processDescText;
	Button andButton, orButton, okButton;
	Text queryText;
	String processName;
	Button validateButton;
	Boolean isQueryValid;
	KeyListener keyListener;

	PointcutWizardProcessPage() {
		super("Process Page");
		setTitle("Define Process Specific Query");
		setDescription("Default Concatenation is logical OR");

		int i = EditorSwitch.processname.size();
		DataModel.processNames.clear();

		for (int j = 0; j < i; j++) {
			DataModel.processNames.add(EditorSwitch.processname.get(j));
		}
		for (int j = 0; j < EditorSwitch.processtns.size(); j++) {
			DataModel.processTNSs.add(EditorSwitch.processtns.get(j));
		}
		if (DataModel.selectedtext.equalsIgnoreCase("")) {
			processName = null;
		} else {
			int l = DataModel.selectedtext.indexOf(' ');
			String process = DataModel.selectedtext.substring(0, l);
			if (process.equalsIgnoreCase("process")) {
				String temp = DataModel.selectedtext.substring(l + 1);
				int slashIndex = temp.indexOf(' ');
				processName = temp.substring(slashIndex + 1);
			} else
				processName = null;
		}

		isQueryValid = true;
		keyListener = new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				isQueryValid = false;
				setDescription("Press Validate to go to Next Page");
				getWizard().getContainer().updateButtons();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		};

	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginRight = 0;
		composite.setLayout(gridLayout);

		Composite c1 = new Composite(composite, SWT.NULL);
		GridLayout gl1 = new GridLayout(2, false);
		c1.setLayout(gl1);

		GridData gdd = new GridData();
		gdd.widthHint = 100;

		GridData gdd1 = new GridData();
		gdd1.widthHint = 100;
		processNameLabel = new Label(c1, SWT.PUSH);
		processNameLabel.setText("Process Name");
		processNameLabel.setLayoutData(gdd1);
		processNameCombo = new Combo(c1, SWT.NONE | SWT.READ_ONLY);

		int i = DataModel.processNames.size();
		String str;
		for (int j = 0; j < i; j++) {
			int k;
			str = DataModel.processNames.get(j);
			for (k = 0; k < j; k++) {
				if (str.equals(DataModel.processNames.get(k)))
					break;
			}
			if (k == j)
				processNameCombo.add(DataModel.processNames.get(j));
		}

		processNameCombo.setLayoutData(gdd);

		processTNSLabel = new Label(c1, SWT.PUSH);
		processTNSLabel.setText("Process tns");
		processTNSLabel.setLayoutData(gdd1);
		processTNSCombo = new Combo(c1, SWT.NONE | SWT.READ_ONLY);
		i = DataModel.processTNSs.size();
		for (int j = 0; j < i; j++) {
			int k;
			str = DataModel.processTNSs.get(j);
			for (k = 0; k < j; k++) {
				if (str.equals(DataModel.processTNSs.get(k)))
					break;
			}
			if (k == j)
				processTNSCombo.add(DataModel.processTNSs.get(j));
		}

		processTNSCombo.setLayoutData(gdd);
		// actype.setLayoutData(gd);

		processKindLabel = new Label(c1, SWT.PUSH);
		processKindLabel.setText("Process Kind");
		processKindLabel.setLayoutData(gdd1);
		processKingCombo = new Combo(c1, SWT.NONE | SWT.READ_ONLY);
		processKingCombo.add("sub-process");
		processKingCombo.add("regular");
		processKingCombo.setLayoutData(gdd);

		processDescLabel = new Label(c1, SWT.PUSH);
		processDescLabel.setText("Process Description");
		processDescLabel.setLayoutData(gdd1);
		Composite c2 = new Composite(c1, SWT.NULL);
		GridLayout ggg = new GridLayout(2, false);
		c2.setLayout(ggg);
		GridData ggd = new GridData();
		ggd.widthHint = 100;
		ggd.heightHint = 15;
		processDescText = new Text(c2, SWT.BORDER);
		processDescText.setLayoutData(ggd);

		okButton = new Button(c2, SWT.PUSH);
		okButton.setText("Ok");
		GridData ggd1 = new GridData();
		ggd1.widthHint = 25;
		okButton.setLayoutData(ggd1);
		okButton.setEnabled(false);

		/**
		 * 
		 * Column 2
		 */

		Label line = new Label(composite, SWT.SEPARATOR | SWT.VERTICAL | SWT.BOLD);
		GridData gridData = new GridData(GridData.FILL_VERTICAL);
		// gridData.horizontalSpan = 5;
		line.setLayoutData(gridData);

		/**
		 * Column 3
		 * 
		 */

		Composite cc1 = new Composite(composite, SWT.NULL);
		GridLayout gl2 = new GridLayout(1, true);
		cc1.setLayout(gl2);
		Label querylabel = new Label(cc1, SWT.PUSH);
		querylabel.setText("Query: ");
		queryText = new Text(cc1, SWT.RESIZE | SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);

		GridData gd11 = new GridData(GridData.FILL_HORIZONTAL);
		gd11.heightHint = 90;
		gd11.widthHint = 220;
		if (processName == null) {
		} else
			queryText.setText("name=\"" + processName + "\"");
		queryText.setLayoutData(gd11);

		Composite cc2 = new Composite(cc1, SWT.NULL);
		GridLayout gl3 = new GridLayout(2, false);
		gl3.marginLeft = 50;
		gl3.horizontalSpacing = 20;
		cc2.setLayout(gl3);

		GridData gd = new GridData();
		gd.widthHint = 50;
		andButton.setLayoutData(gd);
		andButton = new Button(cc2, SWT.PUSH);
		andButton.setText("and");

		orButton = new Button(cc2, SWT.PUSH);
		orButton.setLayoutData(gd);
		orButton.setText("or");

		processNameCombo.addListener(SWT.Modify, this);
		processKingCombo.addListener(SWT.Modify, this);
		processTNSCombo.addListener(SWT.Modify, this);

		processDescText.addListener(SWT.Modify, this);
		okButton.addListener(SWT.Selection, this);
		andButton.addListener(SWT.Selection, this);
		orButton.addListener(SWT.Selection, this);

		validateButton = new Button(cc2, SWT.PUSH);
		validateButton.setLayoutData(gd);
		validateButton.setText("Validate");
		validateButton.addListener(SWT.Selection, this);
		queryText.addKeyListener(keyListener);

		setControl(composite);
	}

	@Override
	public IWizardPage getNextPage() {
		PointcutWizardCombineQueryPage combineQueryPage = ((PointcutWizard) getWizard()).pointcutWizardCombineQueryPage;
		PointcutWizardActivityPage activityPage = ((PointcutWizard) getWizard()).pointcutWizardActivityPage;
		PointcutWizardProcessPage processPage = ((PointcutWizard) getWizard()).pointcutWizardProcessPage;
		if (activityPage.query.getText().replaceAll(" ", "").equalsIgnoreCase("")
				&& processPage.queryText.getText().replaceAll(" ", "").equalsIgnoreCase("")) {
			return null;
		}
		if (!activityPage.query.getText().replaceAll(" ", "").equalsIgnoreCase("")
				&& !processPage.queryText.getText().replaceAll(" ", "").equalsIgnoreCase("")) {
			combineQueryPage.activityButton.setEnabled(true);
			combineQueryPage.processButton.setEnabled(true);
			combineQueryPage.andButton.setEnabled(true);
			combineQueryPage.orButton.setEnabled(true);
			combineQueryPage.queryText.setText("");
		}
		if (activityPage.query.getText().replaceAll(" ", "").equalsIgnoreCase("")) {
			combineQueryPage.activityButton.setEnabled(false);
			combineQueryPage.processButton.setEnabled(false);
			combineQueryPage.andButton.setEnabled(false);
			combineQueryPage.orButton.setEnabled(false);
			combineQueryPage.queryText.setText("process(" + processPage.queryText.getText() + ")");
		} else if (processPage.queryText.getText().replaceAll(" ", "").equalsIgnoreCase("")) {
			combineQueryPage.activityButton.setEnabled(false);
			combineQueryPage.processButton.setEnabled(false);
			combineQueryPage.andButton.setEnabled(false);
			combineQueryPage.orButton.setEnabled(false);
			combineQueryPage.queryText.setText("activity(" + activityPage.query.getText() + ")");
		}
		combineQueryPage.canFlipToNextPage();
		return combineQueryPage;
	}

	@Override
	public boolean canFlipToNextPage() {
		PointcutWizardActivityPage activityPage = ((PointcutWizard) getWizard()).pointcutWizardActivityPage;
		PointcutWizardProcessPage processPage = ((PointcutWizard) getWizard()).pointcutWizardProcessPage;
		if (activityPage.query.getText().replaceAll(" ", "").equalsIgnoreCase("")
				&& processPage.queryText.getText().replaceAll(" ", "").equalsIgnoreCase("") || !isQueryValid) {
			return false;
		}
		return true;
	}

	@Override
	public void handleEvent(Event e) {
		String str;
		if (e.widget.equals(validateButton)) {
			queryText.setText(ValidateQuery.fixQuery(queryText.getText()));
			String err = ValidateQuery.validateQuery(queryText.getText());
			if (!(err.equals("Valid"))) {
				this.setErrorMessage(err);
				isQueryValid = false;
			} else {
				this.setErrorMessage(null);
				this.setDescription("The query is VALID!");
				isQueryValid = true;
			}
			getWizard().getContainer().updateButtons();
		}

		if (e.widget instanceof Combo) {
			str = queryText.getText();
			str = str.replaceAll(" ", "");
			if (!(str.endsWith(Constants.AND) || str.endsWith(Constants.OR))) {
				if (!str.equalsIgnoreCase("")) {
					queryText.append(Constants.LOGICAL_OR);
				}
			}
			if (e.widget.equals(processNameCombo)) {
				str = Constants.QL_NAME;
			}
			if (e.widget.equals(processTNSCombo)) {
				str = Constants.QL_TYPE;
			}
			if (e.widget.equals(processKingCombo)) {
				str = Constants.QL_KIND;
			}
			queryText.append(str + "\"" + ((Combo) e.widget).getText() + "\"");
		}
		if (e.widget.equals(andButton) || e.widget.equals(orButton)) {
			queryText.append(" " + ((Button) e.widget).getText() + " ");
		}
		if (e.widget.equals(okButton)) {
			str = queryText.getText();
			str = str.replaceAll(" ", "");
			if (!(str.endsWith(Constants.AND) || str.endsWith(Constants.OR))) {
				if (!str.equalsIgnoreCase("")) {
					queryText.append(Constants.LOGICAL_OR);
				}
			}
			queryText.append("desc=" + "\"" + processDescText.getText() + "\"");
		}
		if (e.widget.equals(processDescText)) {
			if (processDescText.getText().replaceAll(" ", "").compareTo("") != 0)
				okButton.setEnabled(true);
			else
				okButton.setEnabled(false);
		}
	}
}