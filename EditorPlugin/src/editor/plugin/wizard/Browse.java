package editor.plugin.wizard;

import java.io.File;
import java.util.Vector;
import org.eclipse.core.resources .*;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.swt.widgets.*;
import org.eclipse.jface.wizard.IWizardPage;

public class Browse extends WizardPage implements Listener
{
	IWorkbenchPage page;
	IFile file; 
	IWorkbench workbench;
	IStructuredSelection selection;
	Button open;
	public Text browsetext;
	String selected;
	Text projname;
	public boolean flag;
	public boolean flag1=false;
	public static Vector<String> ifiles=new Vector<String>();
	public Browse(IWorkbench workbench, IStructuredSelection selection) {
		super("Page 1");
		setTitle("Browse");
		setDescription("Select BP Project");
		this.workbench = workbench;
		this.selection = selection;
	}
	public void createControl(Composite parent) {
		Composite composite =  new Composite(parent, SWT.NULL);
		GridLayout gd=new GridLayout(1,false);
		composite.setLayout(gd);
		gd.verticalSpacing=10;
		Composite c=new Composite(composite, SWT.NULL);
		GridLayout gd1=new GridLayout(2,false);
		c.setLayout(gd1);
		
		GridData lab=new GridData();
		lab.widthHint=100;
		
		GridData txt=new GridData();
		txt.widthHint=200;
		
		Label projNamelab=new Label(c, SWT.PUSH);
		projNamelab.setText("Enter project Name");
		projNamelab.setLayoutData(lab);
		projname=new Text(c,SWT.BORDER);
		projname.setLayoutData(txt);
		
		Label line1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL
				| SWT.BOLD);
		GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		line1.setLayoutData(gridData1);
		
		Composite c1=new Composite(composite, SWT.NULL);
		GridLayout gd2=new GridLayout(3,false);
		c1.setLayout(gd2);
		Label browselabel=new Label(c1, SWT.NULL);
		browselabel.setText("Select BP Project");
		browselabel.setLayoutData(lab);
		browsetext=new Text(c1,SWT.BORDER);
		browsetext.setLayoutData(txt);
	
		open=new Button(c1,SWT.NONE);
        open.setText("Browse");
        open.addListener(SWT.Selection, this);
        
		Label line2 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL
				| SWT.BOLD);
		GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
		line2.setLayoutData(gridData2);
		
		projname.addListener(SWT.KeyUp, this);
		browsetext.addListener(SWT.Modify, this);
		setControl(composite);		
	}
	static void iterateFilesInDirectory(String xmldir){
        File dir = new File(xmldir);
        File [] files = dir.listFiles();
        if (files!=null){
           for (int idx=0;idx<files.length;idx++){
            if(files[idx].isDirectory() == false) {
            	String fileName=files[idx].getName();
            	if(fileName.endsWith(".process") ) {
            		ifiles.add(files[idx].getAbsolutePath());
            	}
             }
            else{
            	iterateFilesInDirectory(files[idx].getAbsolutePath());
            }
          }
        }
	}
	public IWizardPage getNextPage(){
		if(!flag)
			return null;
		AspectEditorWizard.aaproject=projname.getText();
		
		AspectEditorWizard.selected=browsetext.getText();
		 ifiles.removeAllElements();
		iterateFilesInDirectory(selected);
		SelectProcesses page = ((AspectEditorWizard)getWizard()).selectProcess;
		page.updateTable();
		return page;
	}
	public void handleEvent(Event event) {
		if(event.widget.equals(open)){
			DirectoryDialog fd=new DirectoryDialog(this.getShell(),SWT.OPEN);
			fd.setText("Browse");
			if(browsetext.getText().replaceAll(" ", "").compareToIgnoreCase("")==0)
				fd.setFilterPath("C:/");
			else
				fd.setFilterPath(browsetext.getText());
			selected = fd.open();
			final String s1="\\\\";
			final String s2="/";
			selected=selected.replaceAll(s1, s2);
			browsetext.setText(selected);       
			browsetext.setSize(200,20);
			getWizard().getContainer().updateButtons();
			}
		if(event.widget.equals(browsetext)){
			File f=new File(browsetext.getText());
			if(!f.isDirectory()){
				this.setErrorMessage("BP Project Name Not Right");
				flag=false;
			}			
			else{
				this.setErrorMessage(null);
				this.setDescription("Select BP Project");
				flag=true;
			}
			this.getContainer().updateButtons();
		}
		if(event.widget.equals(projname)){
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject project=null;
			String str=projname.getText();
			if(str.compareToIgnoreCase("")!=0)
			project = root.getProject(projname.getText());
			
			if(str.compareToIgnoreCase("")==0||project.exists()){
				this.setErrorMessage("Give Project Name/Project already exists");
				flag1=false;
			}
			else{
				this.setErrorMessage(null);
				this.setDescription("Select BP Project");
				flag1=true;
			}
			this.getContainer().updateButtons();
		}
	}
	public boolean canFlipToNextPage()
	{
		if(flag && flag1)			
			return true;
		return false;
	}	
}	