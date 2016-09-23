package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import model.Model;
import view.View;

public class Presenter implements Observer {

	private Model model;
	private View view;
	private CommandsManager commandsManager;
	private HashMap<String, Command> commands;

	public Presenter(Model model, View view) {
		this.model = model;
		this.view = view;
		commandsManager = new CommandsManager(model, view);
		commands = commandsManager.getCommandsMap();
	}

	@Override
	public void update(Observable o, Object args) {
		if (o == view) {

			String commandLine = (String) args;

			String commandsArr[] = commandLine.split(" ");
			String command = commandsArr[0];

			if (!commands.containsKey(command)) {
				view.displayMessage(new String[] { "Critical Error", "Command doesn't exist!" });
			} else {
				String[] arguments = null;
				if (commandsArr.length > 1) {
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
			switch (command) {
			case "error":
				view.displayMessage(arguments);
				break;
			case "maze_ready":
				view.notifyMazeIsReady(arguments[0]);
				break;
			case "solution_ready":
				view.notifyMazeWasSolved(arguments[0]);
				break;
			default:
				view.displayMessage(arguments);
				break;
			}
		}
	}
}
