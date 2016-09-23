package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import algorithm.demo.MazeAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Seracher;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * <h1>The Class MyModel.</h1>
 * <p>
 * This is a specific class that define the data and methods of our specific
 * model- for a Maze Problem
 * <p>
 * 
 * @author NofarLevi
 * @since September 2016
 */

public class MyModel extends CommonModel {

	/**
	 * Instantiates a new my model.
	 */
	public MyModel() {
		super();
	}

	/**
	 * The Class GenerateMazeRunnable.
	 */
	// class GenerateMazeRunnable implements Runnable {
	//
	// /** The dimension of the maze. */
	// private int floors, rows, cols;
	//
	// /** The name. */
	// private String name;
	//
	// /** The generator. */
	// private GrowingTreeGenerator generator;
	//
	//
	// /**
	// * Instantiates a new generate maze runnable.
	// *
	// * @param name the name
	// * @param floors the floors
	// * @param rows the rows
	// * @param cols the columns
	// */
	// public GenerateMazeRunnable(String name, int floors, int rows, int cols)
	// {
	// this.name = name;
	// this.floors = floors;
	// this.rows = rows;
	// this.cols = cols;
	// }
	//
	//
	// @Override
	// public void run() {
	// GrowingTreeGenerator generator = new GrowingTreeGenerator();
	// Maze3d maze = generator.generate(floors, rows, cols);
	// mazes.put(name, maze);
	//
	// presenter.notifyMazeIsReady(name);
	// }
	//
	// /**
	// * Terminate.
	// */
	// public void terminate() {
	// generator.setIsDone(true);
	// }
	// }

//	/**
//	 * The Class MazeSolverRunnable.
//	 */
//	class MazeSolverRunnable implements Runnable {
//
//		/** The name. */
//		private String name;
//
//		/** The algorithm. */
//		private Seracher<Position> algorithm;
//
//		/**
//		 * Instantiates a new maze solver runnable.
//		 *
//		 * @param name
//		 *            the name
//		 * @param algo
//		 *            the algorithm
//		 */
//		public MazeSolverRunnable(String name, Seracher<Position> algo) {
//			this.name = name;
//			this.algorithm = algo;
//		}
//
//		/*
//		 * @see java.lang.Runnable#run()
//		 */
//		@Override
//		public void run() {
//			MazeAdapter myMazeAdap = new MazeAdapter(getMaze(name));
//			Solution<Position> sol = algorithm.search(myMazeAdap);
//			solutions.put(name, sol);
//
//			controller.notifyMazeWasSolved(name);
//		}
//
//		/**
//		 * Terminate.
//		 */
//		public void terminate() {
//			algorithm.setIsDone(true);
//		}
//	}

	/*
	 * @see model.Model#generateMaze(java.lang.String, int, int, int)
	 */
	@Override
	public void generateMaze(String name, int floors, int rows, int cols) {
		// GenerateMazeRunnable generateMazeRunnable = new
		// GenerateMazeRunnable(name, floors, rows, cols);
		// generateMazeTasks.add(generateMazeRunnable);
		// Thread thread = new Thread(generateMazeRunnable);
		// threadPool.submit(thread);

		threadPool.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				GrowingTreeGenerator generator = new GrowingTreeGenerator();
				Maze3d maze = generator.generate(floors, rows, cols);
				mazes.put(name, maze);

				setChanged();
				notifyObservers(new String [] {"maze_ready" , name});
				return maze;
			}
		});
	}

	/*
	 * @see model.Model#setMaze(java.lang.String,
	 * algorithms.mazeGenerators.Maze3d)
	 */
	@Override
	public void setMaze(String name, Maze3d maze) {
		if (!mazes.containsValue(maze))
			mazes.put(name, maze);
	}

	/*
	 * @see model.Model#solveMaze(java.lang.String, algorithms.search.Seracher)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void solveMaze(String name, Seracher<Position> algorithm) {
//		MazeSolverRunnable solverRunnable = new MazeSolverRunnable(name, algorithm);
//		solverTasks.add(solverRunnable);
//		Thread thread = new Thread(solverRunnable);
//		threadPool.submit(thread);
		
		threadPool.submit(new Callable<Solution>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Solution call() throws Exception {
				MazeAdapter myMazeAdap = new MazeAdapter(getMaze(name));
				Solution<Position> sol = algorithm.search(myMazeAdap);
				setSolution(name, sol);
				
				setChanged();
				notifyObservers("Solution_Ready " + name);
				return sol;
			}
		});	
	}

	/*
	 * @see model.Model#getSolution(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Solution<Position> getSolution(String name) {
		return solutions.get(name);
	}
	
	public void setSolution(String name, Solution<Position> sol) {
		solutions.put(name, sol);
	}

	/*
	 * @see model.Model#saveMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveMaze(String name, String path) {
		Maze3d maze = getMaze(name);
		OutputStream out = null;
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(path));
			openFiles.add("compressor"); // insert some String to a list, to
											// monitor which Files are open..
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			out.write(maze.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.close();
			openFiles.remove("compressor");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @see model.Model#loadMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void loadMaze(String path, String name) {
		InputStream in = null;
		File fileIns = null;
		byte b[] = null;
		try {
			// file instance needed so we could know the size of the maze we
			// are going to load
			fileIns = new File(path);
			openFiles.add("fileIns"); // insert some String to a list, to
										// monitor which Files are open..
			in = new MyDecompressorInputStream(new FileInputStream(fileIns));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			double sizeByteArray = fileIns.length();
			b = new byte[(int) sizeByteArray];
			in.read(b);
		} catch (IOException e) {
			// if any error occurs
			e.printStackTrace();
		}
		try {
			in.close();
			openFiles.remove("fileIns");
		} catch (IOException e) {
			// if any error occurs
			e.printStackTrace();
		}

		// create new 3d maze with the array of the bytes
		Maze3d loadedMaze = new Maze3d(b);
		// enter the new maze to the HashMap
		setMaze(name, loadedMaze);
		// notify the maze is ready
		setChanged();
		notifyObservers("Solution_Ready " + name);
	}

	/*
	 * @see model.Model#exit()
	 */
	public void exit() {

		// terminate all of the Callable threads
		threadPool.shutdown();

		// if there are open files,
		if (!openFiles.isEmpty()) {
			openFiles.clear();
		}
		System.exit(0);
	}

	/*
	 * @see model.Model#isGoodInput(int, int)
	 */
//	@Override
//	public boolean isGoodInput(int numOfArgs, int inputArgs) {
//		if (numOfArgs == inputArgs)
//			return true;
//		else {
//			controller.notifyBadInput(); // if the user hasn't entered the right
//											// num of parameters
//		}
//		return false;
//	}
}
