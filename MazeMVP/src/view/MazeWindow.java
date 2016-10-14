package view;

import java.io.File;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;

// TODO: Auto-generated Javadoc
/**
 * The Class MazeWindow.
 */
public class MazeWindow extends BasicWindow implements View, Observer{

	/** The maze display. */
	private MazeDisplay mazeDisplay;
	
	/** The maze name. */
	String mazeName;
	
	/** The solve cmd was selected. */
	boolean solveCmdWasSelected = false;
	
	/** The my menu bar. */
	MenuBar myMenuBar;
	
	/** The user interface. */
	String userInterface;
	
	/** The generate algo. */
	String generateAlgo;
	
	/** The num of thread. */
	String numOfThread;
	
	/** The solve algo. */
	String solveAlgo;

	/* (non-Javadoc)
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	protected void initWidgets() {
		GridLayout gridLayout = new GridLayout(2, false);
		shell.setLayout(gridLayout);
		shell.setText("Nofar's Maze3d Game");
		shell.setBackground(new Color(null, 47, 79, 79));

		shell.setImage(new Image(null, (new ImageData("lib/images/icon.ico"))));

		ImageData imgd = new ImageData("lib/images/Capture.PNG");
		Image eye = new Image(null, imgd);
		ImageData imgd3 = new ImageData("lib/images/save.PNG");
		Image save = new Image(null, imgd3);
		ImageData imgd4 = new ImageData("lib/images/load.PNG");
		Image load = new Image(null, imgd4);
		ImageData imgd5 = new ImageData("lib/images/properties.PNG");
		Image properties = new Image(null, imgd5);

		Composite btnGroup = new Composite(shell, SWT.FILL);
		RowLayout rowLayout = new RowLayout(SWT.FILL);
		rowLayout.pack = false;
		btnGroup.setLayout(rowLayout);

		shell.addListener(SWT.Close, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (isSure()) {
					setChanged();
					notifyObservers("exit");
				}
			}
		});

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
		btnSolveMaze.setText("   Solve maze");
		btnSolveMaze.setBackground(new Color(null, 255, 215, 0));
		btnSolveMaze.setImage(eye);
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
		RowLayout rowLayout2 = new RowLayout(SWT.FILL);
		rowLayout2.pack = false;
		btnSaveLoadGroup.setLayout(rowLayout2);
		btnSaveLoadGroup.setLayoutData(new GridData(SWT.NONE, SWT.BOTTOM,false,false));

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

		Button btnProperties = new Button(btnSaveLoadGroup, SWT.PUSH);
		//btnProperties.setText("Properties");
		btnProperties.setImage(properties);
		btnProperties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("show_properties");
				openPropertiesWindow();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		Button btnExit = new Button(btnSaveLoadGroup, SWT.PUSH);
		btnExit.setText("Exit");
		btnExit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (isSure()) {
					exitProgram(); 
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		addMenuBar();
		
		
	}

	/**
	 * Adds the menu bar.
	 */
	private void addMenuBar() {
		myMenuBar = new MenuBar(shell, SWT.NONE);
		myMenuBar.addObserver(this);
		myMenuBar.createMenuBar();
	}

	/**
	 * Show generate maze options.
	 */
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

	/**
	 * Open save window.
	 */
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

	/**
	 * Open load window.
	 */
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

	/* (non-Javadoc)
	 * @see view.View#notifyMazeIsReady(java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see view.View#displayMaze(algorithms.mazeGenerators.Maze3d)
	 */
	@Override
	public void displayMaze(Maze3d maze) {

		mazeDisplay.setMaze(maze);
		int[][] mazeData = maze.getCrossSectionByZ(maze.getStartPosition().z);
		mazeDisplay.setCharacterPosition(maze.getStartPosition());
		mazeDisplay.setMazeData(mazeData);
	}

	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	@Override
	public void start() {
		run();
	}

	/* (non-Javadoc)
	 * @see view.View#setCommands(java.util.HashMap)
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see view.View#printAnswers(java.lang.String[])
	 */
	@Override
	public void printAnswers(String[] msg) {
		MessageBox massageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
		for (String st : msg)
			if (!st.isEmpty())
				massageBox.setText(st);

		massageBox.open();

	}

	/* (non-Javadoc)
	 * @see view.View#printCrossSection(int[][])
	 */
	@Override
	public void printCrossSection(int[][] maze2d) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see view.View#notifyMazeWasSolved(java.lang.String)
	 */
	@Override
	public void notifyMazeWasSolved(String name) {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (solveCmdWasSelected) {
					MessageBox msg = new MessageBox(shell);
					msg.setMessage("The solution for " + name + " is ready!");
					msg.open();
				} //else {
//					MessageBox msg = new MessageBox(shell);
//					msg.setMessage("Your hint is ready!");
//					msg.open();
//				}

				setChanged();
				notifyObservers("display_solution " + name);
				mazeName = name;
				solveCmdWasSelected = false;
			}
		});
	}

	/* (non-Javadoc)
	 * @see view.View#displaySolution(algorithms.search.Solution)
	 */
	@Override
	public void displaySolution(Solution<Position> sol) {
		if (solveCmdWasSelected) {
			mazeDisplay.printSolution(sol);
		} else
			mazeDisplay.printHint(sol);
	}

	/* (non-Javadoc)
	 * @see view.View#printHelp()
	 */
	@Override
	public void printHelp() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see view.View#displayMessage(java.lang.String[])
	 */
	@Override
	public void displayMessage(String[] msg) {
		MessageBox massageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
		for (String st : msg)
			if (!st.isEmpty())
				massageBox.setText(st);

		massageBox.open();
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

	/**
	 * Exit program.
	 */
	protected void exitProgram() {
		setChanged();
		notifyObservers("exit");
	}

	/* (non-Javadoc)
	 * @see view.View#notifyLoadingSuccessfully()
	 */
	@Override
	public void notifyLoadingSuccessfully() {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				MessageBox msg = new MessageBox(shell);
				msg.setMessage("Your properties was loaded successfully");
				msg.open();
			}
		});
		
	}
	
	/**
	 * Open properties window.
	 */
	public void openPropertiesWindow(){
		
		Shell shell = new Shell();
		shell.setText("Properties");
		shell.setSize(300, 150);
		
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		Label uInterface = new Label(shell, SWT.NONE);
		uInterface.setText("User Interface: ");
		uInterface.setBackground(new Color(null, 255, 255, 255));
		Label uInterface2 = new Label(shell, SWT.NONE);
		uInterface2.setText(userInterface);
		
		Label gAlgo = new Label(shell, SWT.NONE);
		gAlgo.setText("Generation Maze Algorithm: ");
		gAlgo.setBackground(new Color(null, 255, 255, 255));
		Label gAlgo2 = new Label(shell, SWT.NONE);
		gAlgo2.setText(generateAlgo);
		
		Label sAlgo = new Label(shell, SWT.NONE);
		sAlgo.setText("Solving Maze Algorithm: ");
		sAlgo.setBackground(new Color(null, 255, 255, 255));
		Label sAlgo2 = new Label(shell, SWT.NONE);
		sAlgo2.setText(solveAlgo);
		
		Label numOfThread = new Label(shell, SWT.NONE);
		numOfThread.setText("Number Of Threads: ");
		numOfThread.setBackground(new Color(null, 255, 255, 255));
		Label numOfThread2 = new Label(shell, SWT.NONE);
		numOfThread2.setText(this.numOfThread);

		
		Button closeProperties = new Button(shell, SWT.PUSH | SWT.BOTTOM);
		closeProperties.setText("Close");
		closeProperties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		shell.open();
	}

	/* (non-Javadoc)
	 * @see view.View#showProperties(java.lang.String[])
	 */
	@Override
	public void showProperties(String[] arguments) {
		int k = 0;
		userInterface = arguments[k++];
		generateAlgo = arguments[k++];
		numOfThread = arguments[k++];
		solveAlgo = arguments[k++];
	}
	

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object args) {
		if (o == myMenuBar) {
			String commandLine = (String) args;

			String commandsArr[] = commandLine.split(" ");
			String command = commandsArr[0];
			String commandArgs = null;
			if (commandsArr.length > 1) {
				commandArgs = commandLine.substring(commandLine.indexOf(" ") + 1);
			}
			setChanged();
			notifyObservers(command + " " + commandArgs);
		}
	}
}
