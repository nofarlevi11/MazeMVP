package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuBar.
 */
public class MenuBar extends Observable {

	/** The my menu. */
	Menu myMenu;
	
	/** The shell. */
	Shell shell;

	/**
	 * Instantiates a new menu bar.
	 *
	 * @param shell the shell
	 * @param style the style
	 */
	MenuBar(Shell shell, int style) {
		this.shell = shell;

		Label label = new Label(shell, SWT.CENTER);
		label.setBounds(shell.getClientArea());

		myMenu = new Menu(shell, SWT.BAR);
	}

	/**
	 * Creates the menu bar.
	 */
	public void createMenuBar() {
		addMenuItems();
		shell.setMenuBar(myMenu);
	}

	/**
	 * Adds the menu items.
	 */
	private void addMenuItems() {
		MenuItem fileMenuHeader = new MenuItem(myMenu, SWT.CASCADE);
		fileMenuHeader.setText("&file");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		MenuItem PropItem = new MenuItem(fileMenu, SWT.PUSH);
		PropItem.setText("Open Properties");
		PropItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog propLoadFileDialog = new FileDialog(shell, SWT.OPEN);
				propLoadFileDialog.setText("Load XML File");
				propLoadFileDialog.setFilterPath("lib/properties");
				propLoadFileDialog.setFilterNames(new String[] { "XML Files", "All Files (*.*)" });
				propLoadFileDialog.setFilterExtensions(new String[] { "*.xml", "*.*" });

				propLoadFileDialog.open();
				if (propLoadFileDialog.getFileName() != ""){
				setChanged();
				notifyObservers(
						"load_XML " + propLoadFileDialog.getFilterPath() + "\\" + propLoadFileDialog.getFileName());
				notifyLoadingSuccess();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH); //item for exit the program
		exitItem.setText("Exit");
		exitItem.addSelectionListener(new SelectionListener() { 

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (isSure()) {
					setChanged();
					notifyObservers("exit");
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		
		MenuItem loadMemItem = new MenuItem(fileMenu, SWT.PUSH); //item for load  from memory
		loadMemItem.setText("load from memory");
		loadMemItem.addSelectionListener(new SelectionListener() { 

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (isSure()) {
					setChanged();
					notifyObservers("exit");
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * Notify loading success.
	 */
	private void notifyLoadingSuccess() {

		MessageBox msg = new MessageBox(shell);
		msg.setMessage("The Properties file was loaded successfully");
		msg.open();
	}
	
	/**
	 * Checks if is sure.
	 *
	 * @return true, if is sure
	 */
	private boolean isSure() {
		MessageBox msg = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		msg.setMessage("Are you sure? you're gonna leave the party :(");
		int ans = msg.open();
		if (ans == 64)
			return true;
		return false;
	}
}
