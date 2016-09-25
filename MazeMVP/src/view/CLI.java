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
		out.println("what do you want to do? Please enter a command");
		out.println("### For Help, press 'help' ###\n");

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

}
