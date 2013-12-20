/**
 * Creating our own view PointCutDetails View
 */
package editor.ui.plugin;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import editor.data.DataModel;
import editor.driver.EditAdviceWizardDriver;
import editor.logic.Highlight;
import editor.util.FileWriterUtil;

public class PointCutDetailView extends ViewPart implements Listener {

	public PointCutDetailView() {
		// TODO Auto-generated constructor stub
	}

	static TableCursor cursor;
	static Composite composite;
	Shell shell;
	static Table table;
	static Combo pointCutCombo;
	static Button joinButton;
	static Combo adviceCombo;
	Text detailsText;
	Button upButton, downButton;
	Menu menu;

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		shell = parent.getShell();
		composite = parent;
		Composite comp = new Composite(parent, SWT.NULL);
		GridLayout fl = new GridLayout(1, false);
		comp.setLayout(fl);

		Composite c = new Composite(comp, SWT.NULL);
		GridLayout gl = new GridLayout(1, false);
		c.setLayout(gl);

		Composite cf = new Composite(c, SWT.NULL);
		cf.setLayout(new GridLayout(2, false));

		Composite c1 = new Composite(cf, SWT.NULL);
		GridLayout gl1 = new GridLayout(2, false);
		c1.setLayout(gl1);
		GridData gd1 = new GridData();
		gd1.widthHint = 70;
		GridData gd2 = new GridData();
		gd2.widthHint = 50;

		Label lpc = new Label(c1, SWT.PUSH);
		lpc.setLayoutData(gd2);
		lpc.setText("Pointcuts :");
		pointCutCombo = new Combo(c1, SWT.BORDER | SWT.READ_ONLY);
		pointCutCombo.setLayoutData(gd1);
		joinButton = new Button(c1, SWT.PUSH);
		GridData data = new GridData(GridData.FILL_HORIZONTAL, GridData.BEGINNING, false, false, 2, 1);
		data.widthHint = 50;
		data.horizontalIndent = 60;
		joinButton.setLayoutData(data);
		joinButton.setText("join");
		joinButton.setEnabled(false);
		Label ladvice = new Label(c1, SWT.PUSH);
		ladvice.setLayoutData(gd2);
		ladvice.setText("Advices :");
		adviceCombo = new Combo(c1, SWT.BORDER | SWT.READ_ONLY);
		adviceCombo.setLayoutData(gd1);

		Composite c2 = new Composite(cf, SWT.NULL);
		GridLayout ll = new GridLayout(1, false);

		c2.setLayout(ll);
		GridData gd = new GridData();

		gd.widthHint = 300;
		gd.heightHint = 100;
		detailsText = new Text(c2, SWT.MULTI | SWT.BORDER | SWT.WRAP);
		detailsText.setEditable(false);
		detailsText.setLayoutData(gd);

		Composite c3 = new Composite(comp, SWT.NULL);
		GridLayout lay = new GridLayout(1, false);
		lay.horizontalSpacing = 20;
		c3.setLayout(lay);

		Composite c4 = new Composite(c3, SWT.NULL);
		GridLayout gdl = new GridLayout(2, false);
		gdl.horizontalSpacing = 20;
		c4.setLayout(gdl);
		upButton = new Button(c4, SWT.PUSH);
		downButton = new Button(c4, SWT.PUSH);
		upButton.setText("up");
		downButton.setText("down");
		upButton.setEnabled(false);
		downButton.setEnabled(false);

		Composite c5 = new Composite(c3, SWT.NULL);
		c5.setLayout(new GridLayout(1, true));
		table = new Table(c5, SWT.BORDER);
		GridData gdata = new GridData();
		gdata.heightHint = 200;
		gdata.widthHint = 500;
		table.setLayoutData(gdata);

		TableColumn tc1 = new TableColumn(table, SWT.CENTER);
		TableColumn tc2 = new TableColumn(table, SWT.CENTER);
		TableColumn tc3 = new TableColumn(table, SWT.CENTER);
		tc1.setText("PointCut");
		tc2.setText("Advice");
		tc3.setText("Implementation");
		tc1.setWidth(120);
		tc2.setWidth(120);
		tc3.setWidth(120);
		TableItem ti = new TableItem(table, SWT.FULL_SELECTION);
		ti.setText(new String[] { "", "" });
		cursor = new TableCursor(table, SWT.NONE);
		cursor.addListener(SWT.MouseUp, this);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				// height cannot be per row so simply set
				event.height = 25;
			}
		});
		table.addListener(SWT.MouseUp, this);
		// Context Menu
		menu = new Menu(comp);
		// first item
		final MenuItem item1 = new MenuItem(menu, SWT.PUSH);
		item1.setText("Delete Pointcut");
		item1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (cursor.getRow() != null) {
					if (MessageDialog.openConfirm(shell, "Confirmation", "Are you sure u wish to delete this \""
							+ cursor.getRow().getText(0) + "\"PointCut..??")) {
						DataModel.deletePC(cursor.getRow().getText(0));
						updateView();
						FileWriterUtil fileWriterUtil = new FileWriterUtil();
						fileWriterUtil.writeAspectFile("default");
					}
				} else {
					MessageDialog.openWarning(shell, "Message", "Properly Select a Pointcut..??");
				}
			}
		});

		// Second item
		final MenuItem item2 = new MenuItem(menu, SWT.PUSH);
		item2.setText("Edit Advice");
		item2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (cursor.getRow() != null && cursor.getRow().getText(1).compareTo("") != 0) {
					EditAdviceWizardDriver.ad = cursor.getRow().getText(1);
					new EditAdviceWizardDriver();
				} else {
					MessageDialog.openWarning(shell, "Message", "Properly Select an Advice..??");
				}
			}
		});

		// Third item
		final MenuItem item3 = new MenuItem(menu, SWT.PUSH);
		item3.setText("Delete Advice");
		item3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (cursor.getRow() != null && cursor.getRow().getText(1).compareTo("") != 0) {
					if (MessageDialog.openConfirm(shell, "Confirmation", "Are you sure u wish to delete this \""
							+ cursor.getRow().getText(1) + "\" Advice..??")) {
						DataModel.deleteAdvice(cursor.getRow().getText(1));
						updateView();
						FileWriterUtil fileWriterUtil = new FileWriterUtil();
						fileWriterUtil.writeAspectFile("default");
					}
				} else {
					MessageDialog.openWarning(shell, "Message", "Properly Select an Advice..??");
				}
			}
		});
		table.setMenu(menu);
		updateView();
		pointCutCombo.addListener(SWT.Modify, this);
		adviceCombo.addListener(SWT.Modify, this);
		joinButton.addListener(SWT.Selection, this);
		upButton.addListener(SWT.Selection, this);
		downButton.addListener(SWT.Selection, this);
	}

	public static void updateView() {
		boolean flag1 = true;
		int i, j;
		pointCutCombo.removeAll();
		adviceCombo.removeAll();
		table.removeAll();
		for (i = 0; i < DataModel.pointCutNames.size(); i++)
			pointCutCombo.add(DataModel.pointCutNames.get(i));
		for (i = 0; i < DataModel.adviceNames.size(); i++)
			adviceCombo.add(DataModel.adviceNames.get(i));

		// update table

		for (j = 0; j < DataModel.pointCutNames.size(); j++) {
			flag1 = true;
			for (i = 0; i < DataModel.advicePointCutNames.size(); i++) {
				if (DataModel.pointCutNames.get(j).compareToIgnoreCase(DataModel.advicePointCutNames.get(i)) == 0) {
					flag1 = false;
					TableItem ti = new TableItem(table, SWT.NONE);
					ti.setText(0, DataModel.advicePointCutNames.get(i));
					ti.setText(1, DataModel.adviceNames.get(i));
					ti.setText(2, DataModel.adviceImplementations.get(i));
				}
			}
			if (flag1) {
				TableItem ti = new TableItem(table, SWT.NONE);
				ti.setText(0, DataModel.pointCutNames.get(j));
				ti.setText(1, "");
				ti.setText(2, "");
			}
		}
		if (table.getItemCount() == 0) {
			table.getMenu().getItem(0).setEnabled(false);
			table.getMenu().getItem(1).setEnabled(false);
		} else {
			table.getMenu().getItem(0).setEnabled(true);
			table.getMenu().getItem(1).setEnabled(true);
		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleEvent(Event event) {
		int i, min = 0, max = 0, row = -1;
		String str;
		boolean flag;
		if (event.widget.equals(joinButton)) {
			while (true) {
				InputDialog dialog = new InputDialog(shell, "Cloning the Advice", "Enter New Advice Name", "", null);
				int res = dialog.open();
				if (res == 1)
					continue;
				str = dialog.getValue();
				if (str.length() == 0)
					continue;
				flag = true;
				for (i = 0; i < DataModel.adviceNames.size(); i++) {
					if (DataModel.adviceNames.get(i).compareToIgnoreCase(str) == 0) {
						flag = false;
						MessageDialog.openError(shell, "Error", "Advice name already Exist..!!");
						break;
					}
				}
				if (flag)
					break;
			}
			DataModel.advicePointCutNames.add(pointCutCombo.getText());
			for (i = 0; i < DataModel.adviceNames.size(); i++)
				if (DataModel.adviceNames.get(i).compareToIgnoreCase(adviceCombo.getText()) == 0)
					break;
			DataModel.adviceNames.add(str);
			DataModel.adviceImplementations.add(DataModel.adviceImplementations.get(i));
			DataModel.adviceWhen.add(DataModel.adviceWhen.get(i));
			DataModel.adviceDocumentations.add(DataModel.adviceDocumentations.get(i));

			updateView();
		}
		if (event.widget.equals(pointCutCombo)) {
			detailsText.setText(DataModel.getQuery(pointCutCombo.getText()));
			// Highlight.parseQuery(DataModel.getQuery(details.getText()));
			if (pointCutCombo.getText().compareTo("") != 0 && adviceCombo.getText().compareTo("") != 0)
				joinButton.setEnabled(true);
			else
				joinButton.setEnabled(false);
		}
		if (event.widget.equals(adviceCombo)) {
			for (i = 0; i < DataModel.adviceNames.size(); i++) {
				if (DataModel.adviceNames.get(i).compareToIgnoreCase(adviceCombo.getText()) == 0) {
					detailsText.setText("Implementation=" + DataModel.adviceImplementations.get(i) + "\n");
					detailsText.append("Where=" + DataModel.adviceWhen.get(i) + "\n");
					if (DataModel.adviceDocumentations.get(i).compareToIgnoreCase("") != 0)
						detailsText.append("Documentation=" + DataModel.adviceDocumentations.get(i) + "\n");
				}
			}
			if (pointCutCombo.getText().compareTo("") != 0 && adviceCombo.getText().compareTo("") != 0)
				joinButton.setEnabled(true);
			else
				joinButton.setEnabled(false);
		}
		if (event.widget.equals(upButton)) {
			if (cursor.getRow().getText(1).compareToIgnoreCase("") == 0)
				return;
			String pc = cursor.getRow().getText(0);
			for (i = 0; i < table.getItemCount(); i++) {
				if (table.getItem(i).getText(0).compareToIgnoreCase(pc) == 0) {
					min = i;
					while (i < table.getItemCount() && table.getItem(i).getText(0).compareToIgnoreCase(pc) == 0) {
						if (table.getItem(i).equals(cursor.getRow()))
							row = i;
						i++;
					}
					max = --i;
					break;
				}
			}
			int ind = DataModel.getADIndex(table.getItem(row).getText(1));
			int ind1;
			if (row == min)
				ind1 = DataModel.getADIndex(table.getItem(max).getText(1));
			else
				ind1 = DataModel.getADIndex(table.getItem(row - 1).getText(1));
			DataModel.swapad(ind, ind1);
			updateView();
			FileWriterUtil fileWriterUtil = new FileWriterUtil();
			fileWriterUtil.writeAspectFile("default");
		}
		if (event.widget.equals(downButton)) {
			if (cursor.getRow().getText(1).compareToIgnoreCase("") == 0)
				return;
			String pc = cursor.getRow().getText(0);
			for (i = 0; i < table.getItemCount(); i++) {
				if (table.getItem(i).getText(0).compareToIgnoreCase(pc) == 0) {
					min = i;
					while (i < table.getItemCount() && table.getItem(i).getText(0).compareToIgnoreCase(pc) == 0) {
						if (table.getItem(i).equals(cursor.getRow()))
							row = i;
						i++;
					}
					max = --i;
					break;
				}
			}
			int ind = DataModel.getADIndex(table.getItem(row).getText(1));
			int ind1;
			if (row == max)
				ind1 = DataModel.getADIndex(table.getItem(min).getText(1));
			else
				ind1 = DataModel.getADIndex(table.getItem(row + 1).getText(1));
			DataModel.swapad(ind, ind1);
			updateView();
			FileWriterUtil fileWriterUtil = new FileWriterUtil();
			fileWriterUtil.writeAspectFile("default");
		}
		if (event.widget.equals(cursor)) {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			String viewId = IPageLayout.ID_PROP_SHEET;
			// get the reference for your viewId
			// release the view
			IViewPart part = page.findView(viewId);
			page.hideView(part);
			if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("pcproperties") == null) {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("pcproperties");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			int col = cursor.getColumn();
			TableItem ti = cursor.getRow();
			str = ti.getText(col);
			if (cursor.getColumn() == 0) {
				PointCutPropertiesView.click = "pc";
			}
			if (cursor.getColumn() == 1)
				PointCutPropertiesView.click = "ac";
			if (cursor.getColumn() == 2)
				PointCutPropertiesView.click = "im";
			PointCutPropertiesView.value = cursor.getRow().getText(cursor.getColumn());
			if (PointCutPropertiesView.value.compareToIgnoreCase(" ") == 0) {
				for (i = 0; i < table.getItemCount(); i++) {
					if (table.getItem(i).equals(cursor.getRow()))
						break;
				}
				while (table.getItem(i).getText(0).compareToIgnoreCase(" ") == 0)
					i--;
				PointCutPropertiesView.value = table.getItem(i).getText(0);
			}
			PointCutPropertiesView.propTableUpdate();

			if (cursor.getColumn() == 0) {
				Highlight.parseQuery(DataModel.getQuery(PointCutPropertiesView.value));
				upButton.setEnabled(true);
				downButton.setEnabled(true);
			} else {
				upButton.setEnabled(false);
				downButton.setEnabled(false);
			}
			if (cursor.getRow().getText(1).compareToIgnoreCase("") == 0) {
				upButton.setEnabled(false);
				downButton.setEnabled(false);
			}
		}
	}
}
