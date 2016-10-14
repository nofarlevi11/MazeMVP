package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import model.Model;
import view.View;

/**
 * <h1> The Class Presenter. </h1>
 * <p>
 * This class will be the connection between model and view in MVP / MVC pattern - <p>
 * <p>
 * 
 * @author NofarLevi
 * @since October 2016
 */

public class Presenter implements Observer {

	/** The model. */
	private Model model;
	
	/** The view. */
	private View view;
	
	/** The commands manager. */
	private CommandsManager commandsManager;
	
	/** The commands. */
	private HashMap<String, Command> commands;

	/**
	 * Instantiates a new presenter.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public Presenter(Model model, View view) {
		this.model = model;
		this.view = view;
		commandsManager = new CommandsManager(model, view);
		commands = commandsManager.getCommandsMap();
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object args) {
		if (o == view) {

			String commandLine = (String) args;

			String commandsArr[] = commandLine.split(" "); //put the command and the arguments in an Array of String
			String command = commandsArr[0]; //split the command
			if (command.equals("help"))
				view.printHelp();
			else if (!commands.containsKey(command)) {
				view.displayMessage(new String[] { "Critical Error", "Command doesn't exist!" });
			} else {
				String[] arguments = null;
				if (commandsArr.length > 1) { //if there are arguments after the command
					String commandArgs = commandLine.substring(commandLine.indexOf(" ") + 1);
					arguments = commandArgs.split(" ");
				}
				Command cmd = commands.get(command);
				try {
					cmd.doCommand(arguments);
				} catch (NullPointerException e) {
					view.displayMessage(new String[] { "Arguments Error", "No arguments!" });
				}
			}
		} else if (o == model) {
			String commandsArr[] = (String[]) args;
			String command = commandsArr[0];
			String[] arguments = null;
			if (commandsArr.length > 1) {
				arguments = new String[commandsArr.length - 1];
				for (int i = 0; i < commandsArr.length - 1; i++) {
					arguments[i] = commandsArr[i + 1];
				}
			}
			switch (command) { //switch between the commands that comes from model
			case "error":
				view.displayMessage(arguments);
				break;
			case "maze_ready":
				view.notifyMazeIsReady(arguments[0]);
				break;
			case "solution_ready":
				view.notifyMazeWasSolved(arguments[0]);
				break;
			case "loading_successfully":
				view.notifyLoadingSuccessfully();
				break;
			case "properties":
				view.showProperties(arguments);
				break;
			default:
				view.displayMessage(arguments);
				break;
			}
		}
	}
}
