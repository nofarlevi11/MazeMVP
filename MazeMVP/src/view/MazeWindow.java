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
	
	@Override
	protected void initWidgets() {
		GridLayout gridLayout = new GridLayout(2, false);
		shell.setLayout(gridLayout);				
		
		Composite btnGroup = new Composite(shell, SWT.BORDER);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		btnGroup.setLayout(rowLayout);
		
		Button btnGenerateMaze = new Button(btnGroup, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");
		btnGenerateMaze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				showGenerateMazeOptions();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		Button btnSolveMaze = new Button(btnGroup, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		
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
				notifyObservers("generate_maze " + txtName.getText() + " " + txtRows.getText() + " " + txtCols.getText() + " " + txtFloors.getText());
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
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
				notifyObservers(new String [] {"display_maze", name});
			}
		});			
	}

	@Override
	public void displayMaze(Maze3d maze) {
		
//		int[][] mazeData={
//				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//				{1,0,0,0,0,0,0,0,1,1,0,1,0,0,1},
//				{0,0,1,1,1,1,1,0,0,1,0,1,0,1,1},
//				{1,1,1,0,0,0,1,0,1,1,0,1,0,0,1},
//				{1,0,1,0,1,1,1,0,0,0,0,1,1,0,1},
//				{1,1,0,0,0,1,0,0,1,1,1,1,0,0,1},
//				{1,0,0,1,0,0,1,0,0,0,0,1,0,1,1},
//				{1,0,1,1,0,1,1,0,1,1,0,0,0,1,1},
//				{1,0,0,0,0,0,0,0,0,1,0,1,0,0,1},
//				{1,1,1,1,1,1,1,1,1,1,1,1,0,1,1},
//			};
		
		mazeDisplay.setMaze(maze);
		int[][] mazeData=maze.getCrossSectionByZ(maze.getStartPosition().z);
		
		
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
	public void printAnswers(String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printCrossSection(int[][] maze2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyMazeWasSolved(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolution(Solution<Position> sol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyProgramIsAboutToEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyBadInput() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void printHelp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMessage(String[] msg) {
		// TODO Auto-generated method stub
		
	}

}
