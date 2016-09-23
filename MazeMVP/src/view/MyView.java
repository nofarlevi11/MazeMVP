package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

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

public class MyView extends CommonView {

	/**
	 * Instantiates a new my view.
	 *
	 * @param in
	 * @param out
	 */
	public MyView(BufferedReader in, PrintWriter out) {
		super(in, out);
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
	 * <h1>Start
	 * <h1/>The Start Method - starting the program by the CLI class
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
	 * @see view.View#notifyProgramIsAboutToEnd()
	 */
	@Override
	public void notifyProgramIsAboutToEnd() {
		out.println("Bye Bye :) ");
		out.flush();
	}

	/*
	 * @see view.View#notifyBadInput()
	 */
	@Override
	public void notifyBadInput() {
		out.println("OOPS, Your input has not the right num of parameters. please try again. for help, press help\n\n");
	}

	@Override
	public void displayMessage(String[] msg) {
		for (String st : msg) {
			out.println(st);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == cli) {
			setChanged();
			notifyObservers(arg);
		}
	}
}
