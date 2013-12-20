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

class PointcutWizardActivityPage extends WizardPage implements Listener {
	Label pointcutNameLabel;
	Text pointcutNameText;
	Label activityNameLabel;
	Combo activityNameCombo;
	Label activityTypeLabel;
	Combo activityTypeCombo;
	Label activityKindLabel;
	Combo activityKindCombo;
	Label activityDescLabel;
	Text activityDescText;
	Button okButton;
	Text query;
	Button andButton, orButton;
	String processName;

	Button validateButton;
	Boolean isQueryValid;
	KeyListener keyListener;

	PointcutWizardActivityPage() {

		super("Activity Page");
		setTitle("Define Activity Specific Query");
		setDescription("Default Concatenation is logical OR");
		int i = EditorSwitch.activityname.size();
		DataModel.activityNames.clear();
		DataModel.activityTypes.clear();

		for (int j = 0; j < i; j++) {
			DataModel.activityNames.add(EditorSwitch.activityname.get(j));
			String str = EditorSwitch.activitytype.get(j);
			int k = str.lastIndexOf('.');
			str = str.substring(k + 1, str.length());
			str = "bp." + str;
			DataModel.activityTypes.add(str);
		}
		int l;
		if (DataModel.selectedtext.equalsIgnoreCase("") || DataModel.selectedtext == (null)
				|| DataModel.selectedtext.equalsIgnoreCase(" ")) {
			processName = null;
		} else {
			l = DataModel.selectedtext.indexOf(' ');
			String abc = DataModel.selectedtext.substring(0, l);
			if (abc.equalsIgnoreCase("process"))
				processName = null;
			else
				processName = DataModel.selectedtext.substring(l + 1);
		}
		isQueryValid = true;
		keyListener = new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
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
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginRight = 0;
		composite.setLayout(gridLayout);

		Composite comname = new Composite(composite, SWT.NULL);
		GridLayout gl11 = new GridLayout(2, false);
		gl11.horizontalSpacing = 20;
		comname.setLayout(gl11);

		GridData gdata1 = new GridData();
		gdata1.widthHint = 100;

		pointcutNameLabel.setLayoutData(gdata1);
		pointcutNameLabel = new Label(comname, SWT.PUSH);
		pointcutNameLabel.setText("PointCut Name");
		pointcutNameText = new Text(comname, SWT.BORDER);

		pointcutNameText.setLayoutData(gdata1);

		Label line1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
		GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		line1.setLayoutData(gridData1);

		Composite com = new Composite(composite, SWT.NULL);
		GridLayout gridL = new GridLayout(3, false);
		gridL.marginRight = 0;
		com.setLayout(gridL);

		Composite c1 = new Composite(com, SWT.NULL);
		GridLayout gl1 = new GridLayout(2, false);
		c1.setLayout(gl1);

		GridData gdd = new GridData();
		gdd.widthHint = 100;

		GridData gdd1 = new GridData();
		gdd1.widthHint = 100;
		activityNameLabel = new Label(c1, SWT.PUSH);
		activityNameLabel.setText("Activity Name");
		activityNameLabel.setLayoutData(gdd1);
		activityNameCombo = new Combo(c1, SWT.NONE | SWT.READ_ONLY);
		/*
		 * for(int j=0;j<DataModel.activityname.size();j++) {
		 * acname.add(DataModel.activityname.get(j)); }
		 */
		int i = DataModel.activityNames.size();
		String str;
		for (int j = 0; j < i; j++) {
			int k;
			str = DataModel.activityNames.get(j);
			for (k = 0; k < j; k++) {
				if (str.equals(DataModel.activityNames.get(k)))
					break;
			}
			if (k == j)
				activityNameCombo.add(DataModel.activityNames.get(j));
		}

		activityNameCombo.setLayoutData(gdd);

		activityTypeLabel = new Label(c1, SWT.PUSH);
		activityTypeLabel.setText("Activity Type");
		activityTypeLabel.setLayoutData(gdd1);
		activityTypeCombo = new Combo(c1, SWT.NONE | SWT.READ_ONLY);

		i = DataModel.activityTypes.size();
		for (int j = 0; j < i; j++) {
			int k;
			str = DataModel.activityTypes.get(j);
			for (k = 0; k < j; k++) {
				if (str.equals(DataModel.activityTypes.get(k)))
					break;
			}
			if (k == j)
				activityTypeCombo.add(DataModel.activityTypes.get(j));
		}

		activityTypeCombo.setLayoutData(gdd);

		activityKindLabel = new Label(c1, SWT.PUSH);
		activityKindLabel.setText("Activity Kind");
		activityKindLabel.setLayoutData(gdd1);
		activityKindCombo = new Combo(c1, SWT.NONE | SWT.READ_ONLY);
		activityKindCombo.add("event-source");
		activityKindCombo.add("signal-in");
		activityKindCombo.add("activity");
		activityKindCombo.setLayoutData(gdd);

		activityDescLabel = new Label(c1, SWT.PUSH);
		activityDescLabel.setText("Activity Description");
		activityDescLabel.setLayoutData(gdd1);
		Composite c2 = new Composite(c1, SWT.NULL);
		GridLayout ggg = new GridLayout(2, false);
		c2.setLayout(ggg);
		GridData ggd = new GridData();
		ggd.widthHint = 100;
		ggd.heightHint = 15;
		activityDescText = new Text(c2, SWT.BORDER);
		activityDescText.setLayoutData(ggd);

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

		Label line = new Label(com, SWT.SEPARATOR | SWT.VERTICAL | SWT.BOLD);
		GridData gridData = new GridData(GridData.FILL_VERTICAL);
		// gridData.horizontalSpan = 5;
		line.setLayoutData(gridData);

		/**
		 * Column 3
		 * 
		 */

		Composite cc1 = new Composite(com, SWT.NULL);
		GridLayout gl2 = new GridLayout(1, true);
		cc1.setLayout(gl2);
		Label querylabel = new Label(cc1, SWT.PUSH);
		querylabel.setText("Query: ");
		query = new Text(cc1, SWT.RESIZE | SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);

		// query.setText("\r\n\r\n\r\n\r\n");
		GridData gd11 = new GridData(GridData.FILL_HORIZONTAL);
		// , GridData.BEGINNING, true,true, 1, 5
		gd11.heightHint = 90;
		gd11.widthHint = 220;
		query.setLayoutData(gd11);
		if (processName == null) {
		} else
			query.setText("name=\"" + processName + "\"");
		// query.addListener(SWT.Modify, this);
		Composite cc2 = new Composite(cc1, SWT.NULL);
		GridLayout gl3 = new GridLayout(2, false);
		gl3.marginLeft = 50;
		gl3.horizontalSpacing = 20;
		cc2.setLayout(gl3);

		GridData gd = new GridData();
		gd.widthHint = 50;
		andButton = new Button(cc2, SWT.PUSH);
		andButton.setLayoutData(gd);
		andButton.setText(Constants.AND);

		orButton = new Button(cc2, SWT.PUSH);
		orButton.setLayoutData(gd);
		orButton.setText(Constants.OR);

		validateButton = new Button(cc2, SWT.PUSH);
		GridData gd1 = new GridData(GridData.CENTER);
		validateButton.setLayoutData(gd1);
		validateButton.setText("Validate");
		validateButton.addListener(SWT.Selection, this);
		query.addKeyListener(keyListener);

		activityNameCombo.addListener(SWT.Modify, this);
		activityKindCombo.addListener(SWT.Modify, this);
		activityTypeCombo.addListener(SWT.Modify, this);
		pointcutNameText.addListener(SWT.Modify, this);

		activityDescText.addListener(SWT.Modify, this);
		okButton.addListener(SWT.Selection, this);
		andButton.addListener(SWT.Selection, this);
		orButton.addListener(SWT.Selection, this);

		setControl(composite);

	}

	@Override
	public void handleEvent(Event e) {

		String str;
		if (e.widget.equals(validateButton)) {
			query.setText(ValidateQuery.fixQuery(query.getText()));
			String err = ValidateQuery.validateQuery(query.getText());
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
			str = query.getText();
			str = str.replaceAll(" ", "");
			if (!(str.endsWith(Constants.AND) || str.endsWith(Constants.OR))) {
				if (str.equalsIgnoreCase("")) {
					query.append(Constants.LOGICAL_OR);
				}
			}
			if (e.widget.equals(activityNameCombo))
				str = Constants.QL_NAME;
			if (e.widget.equals(activityTypeCombo))
				str = Constants.QL_TYPE;
			if (e.widget.equals(activityKindCombo))
				str = Constants.QL_KIND;
			query.append(str + "\"" + ((Combo) e.widget).getText() + "\"");
			((Combo) e.widget).clearSelection();
		}
		if (e.widget.equals(andButton) || e.widget.equals(orButton)) {
			query.append(" " + ((Button) e.widget).getText() + " ");
		}
		if (e.widget.equals(okButton)) {
			str = query.getText();
			str = str.replaceAll(" ", "");
			if (!(str.endsWith(Constants.AND) || str.endsWith(Constants.OR))) {
				if (str.equalsIgnoreCase("")) {
					query.append(Constants.LOGICAL_OR);
				}
			}
			query.append("desc=" + "\"" + activityDescText.getText() + "\"");
		}
		if (e.widget.equals(activityDescText)) {
			if (activityDescText.getText().replaceAll(" ", "").equalsIgnoreCase(""))
				okButton.setEnabled(true);
			else
				okButton.setEnabled(false);
		}
		if (e.widget.equals(pointcutNameText)) {
			getWizard().getContainer().updateButtons();
		}
	}

	@Override
	public boolean canFlipToNextPage() {

		if (DataModel.hasPC(pointcutNameText.getText().trim())) {
			setErrorMessage("Duplicate PointCut Name");
			return false;
		} else {
			if (pointcutNameText.getText().replaceAll(" ", "").compareTo("") == 0 || !isQueryValid)// sne
				return false;
		}
		setErrorMessage(null);
		return true;
	}

	@Override
	public IWizardPage getNextPage() {
		if (pointcutNameText.getText().replaceAll(" ", "").compareTo("") == 0)
			return null;
		return ((PointcutWizard) getWizard()).pointcutWizardProcessPage;
	}

}