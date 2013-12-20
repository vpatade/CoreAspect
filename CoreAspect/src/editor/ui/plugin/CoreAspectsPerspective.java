/**
 * Creating our own perspective CoreAspects
 * */
package editor.ui.plugin;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class CoreAspectsPerspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.addView(IPageLayout.ID_PROJECT_EXPLORER, IPageLayout.LEFT,
				0.15f, layout.getEditorArea());
		layout.addView("pcdetails", IPageLayout.RIGHT, 0.60f,
				layout.getEditorArea());
		IFolderLayout bot = layout.createFolder("bottom", IPageLayout.BOTTOM,
				0.76f, layout.getEditorArea());
		layout.setFixed(true);
	}

}
