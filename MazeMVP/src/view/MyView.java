package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;

// TODO: Auto-generated Javadoc
/**
 * <h1>The Class MyView.</h1>
 * <p>
 * This is a specific class that define the data and methods of our specific
 * view- for a Maze Problem
 * <p>
 * 
 * @author NofarLevi
 * @since September 2016
 */

public class MyView extends Observable implements View, Observer {

	/** The in. */
	protected BufferedReader in;

	/** The out. */
	protected PrintWriter out;

	/** The cli. */
	protected CLI cli;

	/**
	 * Instantiates a new my view.
	 *
	 * @param in
	 * @param out
	 */
	public MyView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		cli = new CLI(in, out);
		cli.addObserver(this); // adding the ciew to be an observer of the cli
	}

	/*
	 * @see view.CommonView#notifyMazeIsReady(java.lang.String)
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		out.println("The Maze " + name + " is ready");
		out.flush();
	}

	/*
	 * @see view.CommonView#displayMaze(algorithms.mazeGenerators.Maze3d)
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		out.println(maze);
		out.flush();
	}

	/*
	 * @see view.CommonView#setCommands(java.util.HashMap)
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		cli.setCommand(commands);
	}

	/*
	 * @see view.CommonView#printAnswers(java.lang.String[])
	 */
	@Override
	public void printAnswers(String[] args) {
		for (String line : args) {
			// prints filename and directory name
			out.println(line);
			out.flush();
		}
	}

	/**
	 * <h1>Start</h1>
	 * The Start Method - starting the program by the CLI class
	 */
	@Override
	public void start() {
		cli.start();
	}

	/*
	 * 
	 * @see view.CommonView#printCrossSection(int[][])
	 */
	@Override
	public void printCrossSection(int[][] maze2d) {
		for (int[] i : maze2d) {
			for (int j : i) {
				out.print(j + " ");
			}
			out.println("");
		}
		out.println("");
	}

	/*
	 * @see view.View#notifyMazeWasSolved(java.lang.String)
	 */
	@Override
	public void notifyMazeWasSolved(String name) {
		out.println("Solution for " + name + " is ready");
		out.flush();
	}

	/*
	 * @see view.View#displaySolution(algorithms.search.Solution)
	 */
	public void displaySolution(Solution<Position> sol) {
		out.println("");
		out.println("************** \n The Solution for the maze is: ");
		out.println(sol);
		out.println("");
		out.flush();
	}


	/*
	 * @see view.View#notifyBadInput()
	 */
	public void notifyBadInput() {
		out.println("OOPS, Your input has not the right num of parameters. please try again. for help, press help\n\n");
	}

	@Override
	public void displayMessage(String[] msg) {
		for (String st : msg) {
			out.println(st);
			out.println();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == cli) {
			setChanged();
			notifyObservers(arg);
		}
	}

	/**
	 * Prints the help.
	 */
	public void printHelp() {
		out.println("Welcome to the Maze console!");
		out.println("this is your place to Generate Mazes, Solve them, Save to a file, and more.. \n"
				+ "Here are some explination about the commands: \n");
		out.println("********************");
		out.println("*  generate_maze : this command will generate a 3d maze for you. \n "
				+ "\t Enter the name of the command, and provide: 1) a name for the maze, 2) three numbers which are the demension of you maze \n"
				+ "*  display : this command will display the 3d maze you have generated. \n"
				+ "\t Enter the name of the command, and provide: the name of the maze. \n"
				+ "*  solve_maze : this command will solve your maze, by algorithm you'll choose \n"
				+ "\t Enter the name of the command, and provide: 1) the name og the maze, 2) a name of Serach Algorithm you choose to solve your maze with \n"
				+ "* display_solution : this command will display the solution of the maze \n"
				+ "\t Enter the name of the command, and provide: the name of the maze \n"
				+ "*  display_cross_section : this command will show you cross section of your maze \n"
				+ "\t Enter the name of the command, and provide: 1)the name od the maze. 2) the demenssion (x, y, z), 3) the index you want the cross \n"
				+ "*  save_maze : this command will save your maze, compressed, to a file \n"
				+ "\t Enter the name of the command, and provide: 1) the name of tha maze, 2) the file name of path you want it to be saved in \n"
				+ "*  load_maze : this command will load the maze, deCompressed, to your program. \n"
				+ "\t Enter the name of the command, and provide: 1) the name of the file, 2) the name you choose for the maze\n"
				+ "*  dir : this command will display a list of files and folders, which are in a path you provide \n"
				+ "\t Enter the name of the command, and provide: the path \n"
				+ "*  exit : this command will exit the program properly \n\n"
				+ "\t\t FOR EXAMPLE:   generate_maze Nofar 5 5 5\n\n");
	}

	@Override
	public void notifyLoadingSuccessfully() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showProperties(String[] arguments) {
		
	}

}
