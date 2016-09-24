package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithm.demo.MazeAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Maze3dGeneratorBase;
import algorithms.mazeGenerators.Position;
import algorithms.search.Seracher;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import properties.Properties;
import properties.PropertiesLoader;

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

public class MyModel extends Observable implements Model {

	/** The mazes. */
	protected Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>(); // hash map for the mazes

	/** The thread pool. */
	protected ExecutorService threadPool;

	/** The solutions. */
	@SuppressWarnings("rawtypes")
	protected Map<String, Solution> solutions = new ConcurrentHashMap<String, Solution>();

	/** The open files. */
	protected List<String> openFiles = new ArrayList<String>();
	protected Properties properties;
	
	/**
	 * Instantiates a new my model.
	 */
	public MyModel() {
		properties = PropertiesLoader.getInstance().getProperties();
		threadPool = Executors.newFixedThreadPool(properties.getNumOfThreads());
		this.loadSolutions();
	}
	
	/**
	 * Sets the mazes.
	 *
	 * @param mazes
	 *            the mazes
	 */
	public void setMazes(Map<String, Maze3d> mazes) {
		this.mazes = mazes;
	}

	/**
	 * Gets the thread pool.
	 *
	 * @return the thread pool
	 */
	public ExecutorService getThreadPool() {
		return threadPool;
	}

	/**
	 * Sets the thread pool.
	 *
	 * @param threadPool
	 *            the new thread pool
	 */
	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	/*
	 * @see model.Model#getMaze(java.lang.String)
	 */
	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}

	public Properties getProperties() {
		return this.properties;

	}
	
	/**
	 * Gets the mazes.
	 *
	 * @return the mazes
	 */
	public Map<String, Maze3d> getMazes() {
		return mazes;
	}

	/*
	 * @see model.Model#generateMaze(java.lang.String, int, int, int)
	 */
	@Override
	public void generateMaze(String name, int floors, int rows, int cols, Maze3dGenerator generateAlgo) {

		threadPool.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3d maze = generateAlgo.generate(floors, rows, cols);
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
		
		threadPool.submit(new Callable<Solution<Position>>() {
			@Override
			public Solution call() throws Exception {
				MazeAdapter myMazeAdap = new MazeAdapter(getMaze(name));
				Solution<Position> sol = algorithm.search(myMazeAdap);
				setSolution(name, sol);
				
				setChanged();
				notifyObservers(new String [] {"solution_ready" ,name});
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
		notifyObservers(new String [] {"maze_ready" , name});
	}

	/*
	 * @see model.Model#exit()
	 */
	public void exit() {
		//saving the solution for the next run befor close
		saveSolutions();
		// terminate all of the Callable threads
		threadPool.shutdownNow();

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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadSolutions() {
		File file = new File("solutions.dat");
		if (!file.exists())
			return;
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solutions.dat")));
			mazes = (Map<String, Maze3d>)ois.readObject();
			solutions = (Map<String, Solution>)ois.readObject();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	protected void saveSolutions() {
		ObjectOutputStream oos = null;
		try {
		    oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solutions.dat")));
			oos.writeObject(mazes);
			oos.writeObject(solutions);			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
