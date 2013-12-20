package editor.plugin.wizard;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;

import java.io.File;
import org.eclipse.core.filesystem.*;

public class EditorLaunch {
	public EditorLaunch(String file){
		
			File f=new File(file);
			if(f.exists() && f.isFile()){
				IFileStore filestore=EFS.getLocalFileSystem().getStore(f.toURI());
				IWorkbenchPage page=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try{
					IDE.openEditorOnFileStore(page, filestore);					
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
	}
}
