package editor.ui.wizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

class PointcutWizardCombineQueryPage extends WizardPage implements Listener{
  Button activityButton,processButton,andButton,orButton;
  Text queryText;
    
  public PointcutWizardCombineQueryPage() {
    super("Combone Query");
    setTitle("Combine the Queries");
    setDescription("Generate final Query using appropriate concatenation");
    setPageComplete(false);
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  /* (non-Javadoc)
 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
 */
public void createControl(Composite parent) {
    Composite composite = new Composite(parent, SWT.NULL);
    GridLayout gl=new GridLayout(2, false);
    //gl.marginLeft=150;
    //gl.verticalSpacing=30;
    composite.setLayout(gl);
    
    Composite comp1=new Composite(composite,SWT.NULL);
    GridLayout gl1=new GridLayout(1,true);
    gl1.marginLeft=20;
    gl1.verticalSpacing=30;
    comp1.setLayout(gl1);
    activityButton=new Button(comp1,SWT.RADIO);
    activityButton.setText("Activity Specific Query"); 
    processButton=new Button(comp1,SWT.RADIO);
    processButton.setText("Process Specific Query");
    
    
    Composite comp2=new Composite(composite,SWT.NULL);
    GridLayout gl2=new GridLayout(2,false);
    gl2.marginLeft=20;
    gl2.horizontalSpacing=20;
    comp2.setLayout(gl2);
    GridData data=new GridData();
    data.widthHint=50;
    andButton=new Button(comp2,SWT.PUSH);
    andButton.setText("and");
    andButton.setLayoutData(data);
    orButton=new Button(comp2,SWT.PUSH);
    orButton.setText("or");
    orButton.setLayoutData(data);
    
    Composite comp3=new Composite(composite,SWT.NULL);
    GridData data1 = new GridData(GridData.FILL, GridData.BEGINNING, true,false, 2, 1);
    comp3.setLayoutData(data1);
    GridLayout gl3=new GridLayout(1,true);
    gl3.marginLeft=20;
    comp3.setLayout(gl3);
    queryText=new Text(comp3,SWT.MULTI|SWT.WRAP|SWT.BORDER|SWT.V_SCROLL|SWT.READ_ONLY);
    GridData data2=new GridData();
    data2.heightHint=90;
    data2.widthHint=400;
    queryText.setLayoutData(data2);
    queryText.setBackground(new Color(null,255,255,255));
    queryText.setFont(new Font(null,"Times New Roman",12,20));
    
    queryText.addListener(SWT.Modify, this);
    andButton.addListener(SWT.Selection, this);
    orButton.addListener(SWT.Selection, this);
    activityButton.addListener(SWT.Selection, this);
    processButton.addListener(SWT.Selection, this);
    setControl(composite);
  }

@Override
public IWizardPage getNextPage() {
	 if(queryText.getText().replaceAll(" ","").compareTo("")==0){
		 return null;
	  }
	 AdviceWizardFirstPage page=((PointcutWizard)getWizard()).adviceWizardFirstPage;
	 page.pointcutNameCombo.removeAll();
	 page.pointcutNameCombo.add(  ((PointcutWizard)getWizard()).pointcutWizardActivityPage.pointcutNameText.getText() );
	 page.pointcutNameCombo.setText(  ((PointcutWizard)getWizard()).pointcutWizardActivityPage.pointcutNameText.getText() );
	 page.queryText.setText(  ((PointcutWizard)getWizard()).pointcutWizardCombineQueryPage.queryText.getText() );
	 return page;
}

@Override
public boolean canFlipToNextPage() {
	if(queryText.getText().replaceAll(" ","").compareTo("")==0)
		 return false;
	return true;
}

public void handleEvent(Event e) {
	  if(e.widget.equals(activityButton) || e.widget.equals(processButton)){
		  andButton.setEnabled(true);
	  	  orButton.setEnabled(true);
	  }
	  if(e.widget.equals(andButton)||e.widget.equals(orButton)){
		if(activityButton.getSelection()){
			queryText.setText("activity("+ ((PointcutWizard)getWizard()).pointcutWizardActivityPage.query.getText()+")");
			if(e.widget.equals(andButton))
				queryText.append(" and ");
			else
				queryText.append(" or ");
			queryText.append("process("+ ((PointcutWizard)getWizard()).pointcutWizardProcessPage.queryText.getText()+")");
		}
		else if(processButton.getSelection()){
			queryText.setText("process("+ ((PointcutWizard)getWizard()).pointcutWizardProcessPage.queryText.getText()+")");
			if(e.widget.equals(andButton))
				queryText.append(" and ");
			else
				queryText.append(" or ");
			queryText.append("activity("+ ((PointcutWizard)getWizard()).pointcutWizardActivityPage.query.getText()+")");
		}
	  }
	  if(e.widget.equals(queryText)){
		  getWizard().getContainer().updateButtons();
	  }
	}
  }
