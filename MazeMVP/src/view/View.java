package view;

import java.util.HashMap;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


// TODO: Auto-generated Javadoc
/**
 * <h1> The Interface View. </h1>
 * <p>
 * This interface will be implements by specific View - <p>
 * classes that are the View in a MVC (model, view, controller) pattern
 * <p>
 * 
 * @author NofarLevi
 * @since September 2016
 */
 
public interface View {
	
	/**
	 * Notify maze is ready.
	 *
	 * @param name the name of the maze
	 */
	void notifyMazeIsReady(String name);
	
	/**
	 * Display maze.
	 *
	 * @param maze the maze that going to be displayed
	 */
	void displayMaze (Maze3d maze);
	
	/**
	 * Sets the commands.
	 *
	 * @param commands the commands
	 */
	void setCommands(HashMap<String, presenter.Command> commands);
	
	/**
	 * Start.
	 */
	void start();
	
	/**
	 * This function is a Auxiliary function, for the command "dir"
	 * <p> it prints the answers - values that come in an array of strings.
	 *<p>
	 * @param args the arguments - array of the answers
	 */
	void printAnswers(String[] args);
	
	/**
	 * Prints the cross section.
	 *
	 * @param maze2d the 2d maze
	 */
	void printCrossSection(int[][] maze2d);
	
	/**
	 * Notify maze was solved.
	 *
	 * @param name the name of the maze
	 */
	void notifyMazeWasSolved(String name);
	
	/**
	 * Display solution.
	 *
	 * @param sol the solution of the maze
	 */
	void displaySolution(Solution<Position> sol);
	

	/**
	 * Notify bad input.
	 *
	 * @param msg the msg
	 */
	void displayMessage(String[] msg);
	
	/**
	 * Prints the help.
	 */
	void printHelp();

	/**
	 * Adds the observer.
	 *
	 * @param observer the observer
	 */
	void addObserver(Observer observer);

	/**
	 * Notify loading successfully.
	 */
	void notifyLoadingSuccessfully();

	/**
	 * Show properties.
	 *
	 * @param arguments the arguments
	 */
	void showProperties(String[] arguments);
	
}
