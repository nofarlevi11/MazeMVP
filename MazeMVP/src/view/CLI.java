package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import presenter.Command;

// TODO: Auto-generated Javadoc
/**
 * The Class CLI.
 */
public class CLI extends Observable {

	/** The in. */
	private BufferedReader in;

	/** The out. */
	private PrintWriter out;

	/** The commands. */
	private HashMap<String, Command> commands;

	/**
	 * Instantiates a new cli.
	 *
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 */
	public CLI(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
	}

	/**
	 * Sets the command.
	 *
	 * @param commands
	 *            the commands
	 */
	public void setCommand(HashMap<String, Command> commands) {
		this.commands = commands;
	}

	/**
	 * Prints the menu.
	 */
	private void printMenu() {
		out.println("what do you want to do? Please enter a command from the list below:");
		out.println("### For Help, press 'help' ###\n");
		// for (String command : commands.keySet()) {
		// out.println("* " + command);
		// }
		//out.println("* help\n");
		out.flush();
	}

	/**
	 * Start.
	 */
	public void start() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					printMenu();
					try {
						String commandLine = in.readLine();
						setChanged();
						notifyObservers(commandLine);

						if (commandLine.equals("exit")) {
							break;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		thread.start();
	}

	/**
	 * Prints the help.
	 */
	private void printHelp() {
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

}
