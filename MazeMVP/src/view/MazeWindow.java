package view;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
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
	
	@Override
	protected void initWidgets() {
		GridLayout gridLayout = new GridLayout(2, false);
		shell.setLayout(gridLayout);
		shell.setText("Nofar's Maze3d Game");

		Composite btnGroup = new Composite(shell, SWT.BORDER);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		btnGroup.setLayout(rowLayout);

		Button btnGenerateMaze = new Button(btnGroup, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");

		btnGenerateMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// setChanged();
				// GenerateMazeWindow gmWindow = new GenerateMazeWindow(base);
				// gmWindow.start(display);
				showGenerateMazeOptions();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button btnSolveMaze = new Button(btnGroup, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		btnSolveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (mazeName != null){
					setChanged();
					notifyObservers("solve_maze " + mazeName);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		mazeDisplay = new MazeDisplay(this.shell,SWT.DOUBLE_BUFFERED);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
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
				notifyObservers("generate_maze " + txtName.getText() + " " + txtFloors.getText() + " " + txtRows.getText()
						+ " " + txtCols.getText());
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		shell.open();
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
				notifyObservers("display "+ name);
				mazeName=name;
			}
		});
	}

	@Override
	public void displayMaze(Maze3d maze) {

		mazeDisplay.setMaze(maze);
		int[][] mazeData = maze.getCrossSectionByZ(maze.getStartPosition().z);
		mazeDisplay.setCharacterPosition(maze.getStartPosition());
		mazeDisplay.setMazeData(mazeData);
		mazeDisplay.forceFocus();
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
		MessageBox msgBox = new MessageBox(shell, SWT.ICON_INFORMATION);
		msgBox.setText("Solution Is Ready");
		msgBox.setMessage("The solution for " + name + " is ready!");
		msgBox.open();
	}

	@Override
	public void displaySolution(Solution<Position> sol) {
		//mazeDisplay.printSolution(sol);
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

}
