package view;

import java.io.File;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;

public class MazeWindow extends BasicWindow implements View {

	private MazeDisplay mazeDisplay;
	String mazeName;
	boolean solveCmdWasSelected = false;
	MenuBar myMenuBar;

	@Override
	protected void initWidgets() {

		GridLayout gridLayout = new GridLayout(2, false);
		shell.setLayout(gridLayout);
		shell.setText("Nofar's Maze3d Game");
		shell.setBackground(new Color(null, 47, 79, 79));

		shell.setImage(new Image(null, (new ImageData("lib/images/icon.ico"))));

		ImageData imgd = new ImageData("lib/images/Capture.PNG");
		Image eye = new Image(null, imgd);
		ImageData imgd2 = new ImageData("lib/images/Capture2.PNG");
		Image eye2 = new Image(null, imgd2);
		ImageData imgd3 = new ImageData("lib/images/save.PNG");
		Image save = new Image(null, imgd3);
		ImageData imgd4 = new ImageData("lib/images/load.PNG");
		Image load = new Image(null, imgd4);

		Composite btnGroup = new Composite(shell, SWT.FILL);
		RowLayout rowLayout = new RowLayout(SWT.FILL);
		rowLayout.pack = false;
		btnGroup.setLayout(rowLayout);

		Button btnGenerateMaze = new Button(btnGroup, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");

		btnGenerateMaze.setBackground(new Color(null, 255, 165, 0));
		btnGenerateMaze.setImage(eye);

		btnGenerateMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				mazeDisplay.win = false;
				showGenerateMazeOptions();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button btnSolveMaze = new Button(btnGroup, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		btnSolveMaze.setBackground(new Color(null, 255, 215, 0));
		btnSolveMaze.setImage(eye2);
		btnSolveMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (mazeName != null) {
					solveCmdWasSelected = true;
					setChanged();
					notifyObservers("solve_maze " + mazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});

		Button btnDisplayHint = new Button(btnGroup, SWT.PUSH);
		btnDisplayHint.setText("       Get Hint!");
		btnDisplayHint.setBackground(new Color(null, 255, 140, 0));
		btnDisplayHint.setImage(eye);
		btnDisplayHint.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setChanged();
				notifyObservers("solve_maze " + mazeName);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		mazeDisplay = new MazeDisplay(this.shell, SWT.DOUBLE_BUFFERED);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		mazeDisplay.setFocus();

		Composite btnSaveLoadGroup = new Composite(shell, SWT.FILL);
		RowLayout rowLayout2 = new RowLayout(SWT.CENTER);
		rowLayout2.pack = false;
		btnSaveLoadGroup.setLayout(rowLayout2);

		Button btnSaveMaze = new Button(btnSaveLoadGroup, SWT.PUSH);
		btnSaveMaze.setText("Save Maze");
		btnSaveMaze.setBackground(new Color(null, 218, 165, 32));
		btnSaveMaze.setImage(save);
		btnSaveMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				openSaveWindow();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		Button btnLoadMaze = new Button(btnSaveLoadGroup, SWT.PUSH);
		btnLoadMaze.setText("Load Maze");
		btnLoadMaze.setBackground(new Color(null, 128, 128, 0));
		btnLoadMaze.setImage(load);
		btnLoadMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				openLoadWindow();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		Button btnExit = new Button(btnSaveLoadGroup, SWT.PUSH);
		btnExit.setText("Exit");
		btnExit.addSelectionListener(new SelectionListener() {

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

		addMenuBar();
	}

	private void addMenuBar() {
		myMenuBar = new MenuBar(shell, SWT.NONE);
		myMenuBar.createMenuBar();
	}

	protected void showGenerateMazeOptions() {
		Shell shell = new Shell();
		shell.setText("Generate Maze");
		shell.setSize(300, 200);

		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Maze Name: ");
		Text txtName = new Text(shell, SWT.BORDER);

		Label lblFloors = new Label(shell, SWT.NONE);
		lblFloors.setText("Floors: ");
		Text txtFloors = new Text(shell, SWT.BORDER);

		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		Text txtRows = new Text(shell, SWT.BORDER);

		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Colomns: ");
		Text txtCols = new Text(shell, SWT.BORDER);

		Button btnGenerate = new Button(shell, SWT.PUSH);
		btnGenerate.setText("Generate");
		btnGenerate.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setChanged();
				notifyObservers("generate_maze " + txtName.getText() + " " + txtFloors.getText() + " "
						+ txtRows.getText() + " " + txtCols.getText());
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		shell.open();
	}

	private void openSaveWindow() {
		FileDialog fd = new FileDialog(shell, SWT.SAVE);
		fd.setText("Save Maze");
		fd.setFilterPath("lib/mazes");
		String[] filterText = { "*.maz" };
		fd.setFilterExtensions(filterText);
		boolean done = false;
		String fileName = fd.open();
		if (mazeName != null) {
			while (!done) {
				if (fileName == null) // user has cancelled
					done = true;
				else {
					File file = new File(fileName);
					if (file.exists()) {
						MessageBox mb = new MessageBox(fd.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
						mb.setMessage(fileName + " already exist. do you want to replace it?");
						done = mb.open() == SWT.YES;
					} else
						done = true;
				}
			}
		}
		if (done) {
			// System.out.println(fileName);
			// System.out.println(fd.getFileName());
			setChanged();
			notifyObservers("save_maze " + mazeName + " " + fd.getFileName());
			displayMessage(new String[] { "Your maze was saved successfully" });
		}
	}

	private void openLoadWindow() {
		FileDialog fd = new FileDialog(shell, SWT.OPEN);
		fd.setText("Load Maze");
		fd.setFilterPath("lib/mazes");
		String[] filterText = { "*.maz" };
		fd.setFilterExtensions(filterText);
		String fileName = fd.open();
		System.out.println(fd.getFileName());
		if (fileName != null) {
			// give name to the new maze
			Shell shell = new Shell();
			shell.setText("Give it a Name");
			shell.setSize(200, 100);

			GridLayout layout = new GridLayout(2, false);
			shell.setLayout(layout);

			Label lblName = new Label(shell, SWT.NONE);
			lblName.setText("Maze Name: ");
			Text txtName = new Text(shell, SWT.BORDER);

			Button btnLoad = new Button(shell, SWT.PUSH);
			btnLoad.setText("Load");
			btnLoad.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if (txtName != null) {
						setChanged();
						notifyObservers("load_maze " + fd.getFileName() + " " + txtName.getText());
						shell.close();
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
			shell.open();
		}
	}

	@Override
	public void notifyMazeIsReady(String name) {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				MessageBox msg = new MessageBox(shell);
				msg.setMessage("Maze " + name + " is ready");
				msg.open();

				setChanged();
				notifyObservers("display " + name);
				mazeName = name;
			}
		});
	}

	@Override
	public void displayMaze(Maze3d maze) {

		mazeDisplay.setMaze(maze);
		int[][] mazeData = maze.getCrossSectionByZ(maze.getStartPosition().z);
		mazeDisplay.setCharacterPosition(maze.getStartPosition());
		mazeDisplay.setMazeData(mazeData);
	}

	@Override
	public void start() {
		run();
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printAnswers(String[] msg) {
		MessageBox massageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
		for (String st : msg)
			if (!st.isEmpty())
				massageBox.setText(st);

		massageBox.open();

	}

	@Override
	public void printCrossSection(int[][] maze2d) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyMazeWasSolved(String name) {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (solveCmdWasSelected) {
					MessageBox msg = new MessageBox(shell);
					msg.setMessage("The solution for " + name + " is ready!");
					msg.open();
				} else {
					MessageBox msg = new MessageBox(shell);
					msg.setMessage("Your hint is ready!");
					msg.open();
				}

				setChanged();
				notifyObservers("display_solution " + name);
				mazeName = name;
				solveCmdWasSelected = false;
			}
		});
	}

	@Override
	public void displaySolution(Solution<Position> sol) {
		if (solveCmdWasSelected) {
			mazeDisplay.printSolution(sol);
		} else
			mazeDisplay.printHint(sol);
	}

	@Override
	public void printHelp() {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayMessage(String[] msg) {
		MessageBox massageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
		for (String st : msg)
			if (!st.isEmpty())
				massageBox.setText(st);

		massageBox.open();
	}

	protected boolean isSure() {
		MessageBox msg = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		msg.setMessage("Are you sure? you're gonna leave the party :(");
		int ans = msg.open();
		if (ans == 64)
			return true;
		return false;
	}

}
