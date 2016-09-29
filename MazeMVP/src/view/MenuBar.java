package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MenuBar extends MazeWindow {

	Menu myMenu;
	Shell shell;

	MenuBar(Shell shell, int style) {
		this.shell = shell;

		Label label = new Label(shell, SWT.CENTER);
		label.setBounds(shell.getClientArea());

		myMenu = new Menu(shell, SWT.BAR);
	}

	public void createMenuBar() {
		addMenuItems();
		shell.setMenuBar(myMenu);
	}

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
				setChanged();
				notifyObservers(
						"load_XML " + propLoadFileDialog.getFilterPath() + "\\" + propLoadFileDialog.getFileName());
				notifyLodingSuccess();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("Exit");
		exitItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("exit");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}

		});

	}

	private void notifyLodingSuccess() {

		MessageBox msg = new MessageBox(shell);
		msg.setMessage("The Properties file was loaded successfully");
		msg.open();

	}

}
