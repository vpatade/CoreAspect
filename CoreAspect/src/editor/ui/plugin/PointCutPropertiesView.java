/**
 * PointcutDetails view
 */
package editor.ui.plugin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import editor.data.DataModel;

public class PointCutPropertiesView extends ViewPart {

	public static Table table;
	public static String click = "";
	public static String value = "";

	public PointCutPropertiesView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite comp = new Composite(parent, SWT.NULL);
		comp.setLayout(new FillLayout());
		table = new Table(comp, SWT.BORDER | SWT.NONE);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn col1 = new TableColumn(table, SWT.CENTER);
		col1.setText("Property");
		TableColumn col2 = new TableColumn(table, SWT.CENTER);
		col2.setText("Value");
		col1.setWidth(300);
		col2.setWidth(300);
	}

	public static void propTableUpdate() {
		table.removeAll();
		int i;
		if (click.compareToIgnoreCase("pc") == 0) {
			TableItem ti = new TableItem(table, SWT.NONE);
			ti.setText(new String[] { "Query", DataModel.getQuery(value) });
		} else if (click.compareToIgnoreCase("ac") == 0) {
			for (i = 0; i < DataModel.adviceNames.size(); i++) {
				if (DataModel.adviceNames.get(i).compareToIgnoreCase(value) == 0) {
					TableItem ti1 = new TableItem(table, SWT.NONE);
					ti1.setText(new String[] { "Pointut",
							DataModel.advicePointCutNames.get(i) });
					TableItem ti2 = new TableItem(table, SWT.NONE);
					ti2.setText(new String[] { "Implementation",
							DataModel.adviceImplementations.get(i) });
					TableItem ti3 = new TableItem(table, SWT.NONE);
					ti3.setText(new String[] { "where",
							DataModel.adviceWhen.get(i) });
					TableItem ti4 = new TableItem(table, SWT.NONE);
					ti4.setText(new String[] { "Documentation",
							DataModel.adviceDocumentations.get(i) });
				}
			}
		} else if (click.compareToIgnoreCase("im") == 0) {

		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

}
