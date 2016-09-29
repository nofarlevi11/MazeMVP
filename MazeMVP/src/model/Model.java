package model;


import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Seracher;
import algorithms.search.Solution;
import properties.Properties;


// TODO: Auto-generated Javadoc
/**
 * <h1> The Interface Model. </h1>
 * <p>
 * This interface will be implements by specific models - <p>
 * classes that are the MODEL in a MVC (model, view, controller) pattern
 * <p>
 * 
 * @author NofarLevi
 * @since September 2016
 */
public interface Model {
	
	/**
	 * Generate maze.
	 *
	 * @param name the name of the maze
	 * @param floors the floors (z)
	 * @param rows the rows (y)
	 * @param cols the cols (x)
	 */
	void generateMaze(String name, int floors, int rows, int cols, Maze3dGenerator generateAlgo);
	
	/**
	 * Gets the maze.
	 *
	 * @param name the name of the maze
	 * @return the maze
	 */
	Maze3d getMaze (String name);
	
	/**
	 * Exit.
	 */
	void exit();
	
	
	/**
	 * Sets the maze.
	 *
	 * @param name the name of the maze
	 * @param maze the maze itself
	 */
	void setMaze (String name, Maze3d maze);
	
	/**
	 * Save maze.
	 *
	 * @param name the name of the maze
	 * @param file the name and path of the new file
	 */
	void saveMaze (String name, String file);
	
	/**
	 * Load maze.
	 *
	 * @param path the path
	 * @param name the name of the new maze
	 */
	void loadMaze (String path, String name);
	
	/**
	 * Solve maze.
	 *
	 * @param name the name of the maze
	 * @param algorithm the algorithm
	 */
	void solveMaze (String name, Seracher<Position> algorithm);
	
	/**
	 * Gets the solution.
	 *
	 * @param name the name
	 * @return the solution
	 */
	 Solution<Position> getSolution (String name);

	 Properties getProperties();
	
	/**
	 * Checks if is good input.
	 *
	 * @param numOfArgs the num of args
	 * @param inputArgs the input args
	 * @return true, if equal
	 */
	boolean isGoodInput(int numOfArgs, int inputArgs);
	 
	void loadSolutions ();

	void setProperties(Properties prop);


}

