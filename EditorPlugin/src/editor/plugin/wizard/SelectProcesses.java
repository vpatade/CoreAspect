package editor.plugin.wizard;


import java.util.Vector;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.widgets.*;

public class SelectProcesses extends WizardPage implements Listener
{
	@Override
	public IWizardPage getPreviousPage() {
		AspectEditorWizard.flag=false;
		Browse page = ((AspectEditorWizard)getWizard()).browsePage;
		return page;
	}
	public Vector<Button>filebuttons=new Vector<Button>();
	Composite composite;
	Button selectall,deselectall;
	Table table;
	SelectProcesses(String arg) {
		super(arg);
		setTitle("Selection");
		setDescription("Select .bpprocess Files");
	}
	public void createControl(Composite parent) {
		composite =  new Composite(parent, SWT.NULL);
		GridLayout gd1=new GridLayout(1,false);
		gd1.marginLeft=50;
		gd1.marginRight=70;
		composite.setLayout(gd1);
		filebuttons.removeAllElements();	
		
		 table = new Table(composite, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		 TableColumn tc=new TableColumn(table,SWT.BORDER);
		 tc.setWidth(700);
		 tc.setText("Process files");
		 for (int i = 0; i < Browse.ifiles.size(); i++) {
		      TableItem item = new TableItem(table, SWT.NONE);
		      item.setText(Browse.ifiles.elementAt(i));
		 }
		 table.setLinesVisible(true);
		 table.setHeaderVisible(true);
		 GridData gd=new GridData();
		 table.setLayoutData(gd);
		 gd.widthHint=500;
		 gd.heightHint=300;
		 table.addListener(SWT.MeasureItem, new Listener() {
			 
			@Override
			public void handleEvent(Event event) {
				event.height=25;
			}
		});
		 GridLayout gl=new GridLayout(2,false);
		 Composite c=new Composite(composite,SWT.NULL);
		 c.setLayout(gl);
		 selectall=new Button(c,SWT.RADIO);
		selectall.setText("Select All");
			
		deselectall=new Button(c,SWT.RADIO);
		
		deselectall.setText("Deselect All");
		
		selectall.addListener(SWT.Selection, this);
		deselectall.addListener(SWT.Selection, this);
		 
	
		setControl(composite);
	}
    public void updateTable(){
    	table.removeAll();
    	 for (int i = 0; i < Browse.ifiles.size(); i++) {
		      TableItem item = new TableItem(table, SWT.NONE);
		      item.setText(Browse.ifiles.elementAt(i));
		      item.setChecked(true);
		 }
    	 selectall.setSelection(true);
    }
	public void handleEvent(Event e) {
		int i;
		if(e.widget.equals(selectall)){
			for(i=0;i<table.getItemCount();i++){
				table.getItem(i).setChecked(true);
			}
		}
		else if(e.widget.equals(deselectall)){
			for(i=0;i<table.getItemCount();i++){
				table.getItem(i).setChecked(false);
			}
		}
	}
	public boolean canFinish(){
			return true;
	}
	public boolean performFinish(){	
		return true;
	}
	public boolean canFlipToNextPage(){
		return false;
	}	
}	