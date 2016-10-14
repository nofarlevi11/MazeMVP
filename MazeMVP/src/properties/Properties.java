package properties;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * /**
 * <h1> The class Properties. </h1>
 * <p>
 * this class is holding the data that defines by the properties external file
 * <p>
 * 
 * @author NofarLevi
 * @since October 2016
 */
 
public class Properties implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The num of threads. */
	private int numOfThreads;
	
	/** The generate maze algorithm. */
	private String generateMazeAlgorithm;
	
	/** The solve maze algorithm. */
	private String solveMazeAlgorithm;
	
	/** The user interface. */
	private String userInterface;
	
	/**
	 * Gets the user interface.
	 *
	 * @return the user interface
	 */
	public String getUserInterface() {
		return userInterface;
	}

	/**
	 * Sets the user interface.
	 *
	 * @param userInterface the new user interface
	 */
	public void setUserInterface(String userInterface) {
		this.userInterface = userInterface;
	}

	/**
	 * Instantiates a new properties.
	 */
	public Properties() {
	}

	/**
	 * Gets the num of threads.
	 *
	 * @return the num of threads
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}

	/**
	 * Sets the num of threads.
	 *
	 * @param numOfThreads the new num of threads
	 */
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}

	/**
	 * Gets the generate maze algorithm.
	 *
	 * @return the generate maze algorithm
	 */
	public String getGenerateMazeAlgorithm() {
		return generateMazeAlgorithm;
	}

	/**
	 * Sets the generate maze algorithm.
	 *
	 * @param generateMazeAlgorithm the new generate maze algorithm
	 */
	public void setGenerateMazeAlgorithm(String generateMazeAlgorithm) {
		this.generateMazeAlgorithm = generateMazeAlgorithm;
	}

	/**
	 * Gets the solve maze algorithm.
	 *
	 * @return the solve maze algorithm
	 */
	public String getSolveMazeAlgorithm() {
		return solveMazeAlgorithm;
	}

	/**
	 * Sets the solve maze algorithm.
	 *
	 * @param solveMazeAlgorithm the new solve maze algorithm
	 */
	public void setSolveMazeAlgorithm(String solveMazeAlgorithm) {
		this.solveMazeAlgorithm = solveMazeAlgorithm;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Properties [numOfThreads=" + numOfThreads + ", generateMazeAlgorithm=" + generateMazeAlgorithm
				+ ", solveMazeAlgorithm=" + solveMazeAlgorithm + ", userInterface=" + userInterface + "]";
	}

}