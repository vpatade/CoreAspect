package editor.ui.wizard;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardDescriptor;

import editor.data.DataModel;

public class AdviceWizardSecondPage extends WizardPage implements Listener{
  Combo comboRoomTypes;
  
  Label adviceNameLabel;
  Text adviceNameText;
  Label pointcutNameLabel;
  Combo pointcutNamesCombo;
  Text queryText;
  Label whereLabel;
  Combo whereCombo;
  Label docuLabel;
  Text docuText;
  Label implLabel;
  Label classNameLabel;
  Text classNameText;
  Button browseButton;
  Button createButton;
  Label propertiesLabel;
  String filePath;
  boolean flag;
  
  Composite c41;
  GridData gd411; 
  AdviceWizardSecondPage() {
	  super("Activity Page2");
	  setTitle("Define Advice");
	  setDescription("Specify the advice related to a Poin-cut");
  }
  
 
  public void createControl(Composite parent) {

    Composite composite = new Composite(parent, SWT.NULL);
    GridLayout gridLayout = new GridLayout(1,false);
    gridLayout.verticalSpacing=0;
    composite.setLayout(gridLayout);
    
    
    Composite c1=new Composite(composite, SWT.NULL);
    GridLayout gl1 = new GridLayout(3, false);
    c1.setLayout(gl1);
    
    Composite cc1=new Composite(c1,SWT.NULL);
    GridLayout gl2 = new GridLayout(2, false);
    cc1.setLayout(gl2);
    
    GridData ptgrid=new GridData();
    ptgrid.widthHint=100;
    
    GridData gd111=new GridData();
    gd111.widthHint=80;
    
    pointcutNameLabel=new Label(cc1,SWT.PUSH);
    pointcutNameLabel.setText("Pointcut:");
    pointcutNameLabel.setLayoutData(ptgrid);
    
    pointcutNamesCombo=new Combo(cc1,SWT.NONE|SWT.READ_ONLY);
    pointcutNamesCombo.setLayoutData(gd111);
    for(int i=0;i<DataModel.pointCutNames.size();i++){
		 String str=DataModel.pointCutNames.get(i);
		 pointcutNamesCombo.add(str);
	 }
    
    adviceNameLabel=new Label(cc1,SWT.PUSH);
    adviceNameLabel.setText("Advice Name:");
    adviceNameLabel.setLayoutData(ptgrid);
    
    adviceNameText=new Text(cc1,SWT.BORDER);
    adviceNameText.setLayoutData(gd111);   
  
    
    Label line = new Label(c1, SWT.SEPARATOR|SWT.VERTICAL|SWT.BOLD);
	GridData gridData = new GridData(GridData.FILL_VERTICAL);
	line.setLayoutData(gridData);
    
	 queryText=new Text(c1,SWT.RESIZE|SWT.BORDER|SWT.MULTI|SWT.WRAP|SWT.V_SCROLL|SWT.READ_ONLY);
     
	 GridData gd11=new GridData(GridData.FILL_HORIZONTAL);
	 gd11.heightHint=50;
	 gd11.widthHint=150;
	 queryText.setLayoutData(gd11);  
	 
	 Label line1 = new Label(composite, SWT.SEPARATOR|SWT.HORIZONTAL|SWT.BOLD);
	GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
	line1.setLayoutData(gridData1);
	
	Composite c2=new Composite(composite,SWT.NULL);
	GridLayout gl3=new GridLayout(2,false);
	c2.setLayout(gl3);
	
	 GridData gdd1=new GridData();
	 gdd1.widthHint=100;
	
	 GridData ggd1=new GridData();
	 ggd1.widthHint=240;
	
	 
	whereLabel=new Label(c2,SWT.PUSH);
	whereLabel.setText("Where:");
	whereLabel.setLayoutData(gdd1);
	
	whereCombo=new Combo(c2,SWT.NONE|SWT.READ_ONLY);
	whereCombo.add("Before");
	whereCombo.add("After");
	whereCombo.add("After returning");
	whereCombo.add("After throwing");
	whereCombo.setLayoutData(ggd1);
	
	docuLabel=new Label(c2,SWT.PUSH);
	docuLabel.setText("Documentation:");
	docuLabel.setLayoutData(gdd1);
	
	docuText=new Text(c2,SWT.RESIZE|SWT.BORDER|SWT.MULTI|SWT.WRAP|SWT.V_SCROLL);
	GridData gd2=new GridData(GridData.FILL_HORIZONTAL);
	gd2.heightHint=30;
	gd2.widthHint=200;
	docuText.setLayoutData(gd2);  
	
	Label line2 = new Label(composite, SWT.SEPARATOR|SWT.HORIZONTAL|SWT.BOLD);
	GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
	line2.setLayoutData(gridData2);
	
	implLabel=new Label(composite,SWT.PUSH);
	implLabel.setText("Implementation:");
	
	Composite c3=new Composite(composite,SWT.NULL);
	GridLayout gd4=new GridLayout(2,false);
	
	c3.setLayout(gd4);
	
	classNameLabel=new Label(c3,SWT.PUSH);
	GridData gd3=new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,GridData.VERTICAL_ALIGN_FILL,false,false,1,1);
	gd3.widthHint=100;
	gd3.grabExcessVerticalSpace=true;
	classNameLabel.setLayoutData(gd3);
	classNameLabel.setText("Class Name:");
	
	
	Composite c31=new Composite(c3,SWT.NULL);
	GridLayout gd5=new GridLayout(1,false);
	c31.setLayout(gd5);
	
	classNameText=new Text(c31, SWT.BORDER);
	GridData gd51=new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,GridData.VERTICAL_ALIGN_FILL,false,false,1,1);
	gd51.widthHint=250;
	classNameText.setLayoutData(gd51);

	Composite c311=new Composite(c31,SWT.NULL);
	GridLayout gd6=new GridLayout(2,false);
	gd6.marginLeft=135;
	c311.setLayout(gd6);
	
	
	GridData gd77=new GridData();
	gd77.widthHint=60;
	 browseButton=new Button(c311,SWT.PUSH);
	 browseButton.setLayoutData(gd77);
	 browseButton.setText("Browse");
	
	 
	 createButton=new Button(c311,SWT.PUSH);
	 createButton.setLayoutData(gd77);
	 createButton.setText("Create");
	
	 
	 
	 propertiesLabel=new Label(c3,SWT.PUSH);
	 propertiesLabel.setLayoutData(gd3);
	 propertiesLabel.setText("Property:");
	 propertiesLabel.setLayoutData(gd3);
	 
	 Composite c4444=new Composite(c3,SWT.NULL);
	 GridLayout gl10=new GridLayout(1,false);
	 c4444.setLayout(gl10);

	 
	c41=new Composite(c4444,SWT.NULL);
	 FillLayout fl=new FillLayout();
	 //gl8.horizontalSpacing=93;
	 c41.setLayout(fl);
	 Table table = new Table(c41, SWT.BORDER | SWT.MULTI);
	    table.addListener(SWT.MeasureItem, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				event.height=25;
			}
		});
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    for (int i = 0; i < 2; i++) {
	      TableColumn column = new TableColumn(table, SWT.NONE);
	      if(i==0)
	      column.setText("Name");
	      else
	      column.setText("Value");	  
	      column.setWidth(150);
	    }
	    for (int i = 0; i < 5; i++) {
	      new TableItem(table, SWT.NONE);
	    }
	    TableItem[] items = table.getItems();
	    for (int i = 0; i < items.length; i++) {
	      TableEditor editor = new TableEditor(table);
	      Text name = new Text(table, SWT.NONE);
	      editor.grabHorizontal = true;
	      editor.setEditor(name, items[i], 0);
	      
	      editor = new TableEditor(table);
	      Text value = new Text(table, SWT.NONE);
	      editor.grabHorizontal = true;
	      editor.setEditor(value, items[i], 1);
	    }
	 browseButton.addListener(SWT.Selection, this);
	 createButton.addListener(SWT.Selection,this);
	 pointcutNamesCombo.addListener(SWT.Modify, this);
	 adviceNameText.addListener(SWT.Modify, this);
	 classNameText.addListener(SWT.Modify, this);
	 whereCombo.addListener(SWT.Modify, this);
	 
	 getShell().pack();
     setControl(composite); 
	
  }
  
  

  @Override
public IWizardPage getNextPage() {
	
	return super.getNextPage();
}


@Override
public boolean canFlipToNextPage() {
	
	return super.canFlipToNextPage();
}


@Override
  public void handleEvent(Event e) {
	  if(e.widget.equals(pointcutNamesCombo)){
		  	queryText.setText(DataModel.getQuery(pointcutNamesCombo.getText()));
		  	queryText.clearSelection();	
	  }else if(e.widget.equals(browseButton)){
		  FileDialog fd=new FileDialog(getShell(),SWT.OPEN);
			fd.setText("Browse");
			fd.setFilterPath("C:/");
	        String[] filterExt = { "*.class" };
	        fd.setFilterExtensions(filterExt);
	     try{
	        filePath = fd.open();
	        classNameText.setText(filePath);
	        final String str1="\\";
	        final String  str2="/";
	        filePath=filePath.replace(str1,str2);
	        System.out.println(filePath);	       
	        }
	        catch(Exception ex){
	        	System.out.println(ex);
	        }
	        return;
	  }else if(e.widget.equals(createButton)){
		  IWizardDescriptor descriptor = PlatformUI.getWorkbench().getNewWizardRegistry().findWizard("org.eclipse.jdt.ui.wizards.NewClassCreationWizard");		  
		  try  {
			     if  (descriptor != null){
			       IWizard wizard = descriptor.createWizard();	
			       Shell s=new Shell();
			       WizardDialog wd = new  WizardDialog(s, wizard);			 
			       wd.setTitle(wizard.getWindowTitle());			  
			       wd.open();			  
			     }		
			   } catch  (Exception ex){			  
			     ex.printStackTrace();			  
			  }
	  }
	if(e.widget.equals(pointcutNamesCombo)||e.widget.equals(whereCombo)
		||e.widget.equals(classNameText)||e.widget.equals(adviceNameText)){
		if(e.widget.equals(adviceNameText)){
			if(DataModel.hasAdvice(adviceNameText.getText())){
				setErrorMessage("Advice already Exist");
				flag=false;
			}
			else{
				setErrorMessage(null);
				setDescription("Specify the advice related to a PoinCut");
				flag=true;
			}
		}
		if(e.widget.equals(classNameText)){
			if(!classNameText.getText().endsWith(".class")){
				setErrorMessage("Browse a .class file");
				flag=false;
			}
			else{
				setErrorMessage(null);
				setDescription("Specify the advice related to a PoinCut");
				flag=true;
			}
		}
		getWizard().getContainer().updateButtons();
	  }
	}
}