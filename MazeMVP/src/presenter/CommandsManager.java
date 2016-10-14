package presenter;

import java.io.File;
import java.util.HashMap;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BFS;
import algorithms.search.CommonSearcher;
import algorithms.search.DFS;
import algorithms.search.Seracher;
import algorithms.search.Solution;
import model.Model;
import properties.PropertiesLoader;
import view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class CommandsManager.
 */
public class CommandsManager {

	/** The model. */
	private Model model;

	/** The view. */
	private View view;

	/**
	 * Instantiates a new command manager.
	 *
	 * @param model
	 *            the model
	 * @param view
	 *            the view
	 */
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Gets the commands map.
	 *
	 * @return the commands map
	 */
	public HashMap<String, Command> getCommandsMap() {
		HashMap<String, Command> commands = new HashMap<String, Command>();
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("dir", new DirCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("save_maze", new SaveMazeCommand());
		commands.put("load_maze", new LoadMazeCommand());
		commands.put("solve_maze", new SolveMazeCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.put("load_XML", new LoadPropXMLCommand());
		commands.put("show_properties", new ShowPropertiesCommand());
		commands.put("exit", new exit());
		
		return commands;
	}

	/**
	 * The Class GenerateMazeCommand.
	 */
	public class GenerateMazeCommand implements Command {


		@Override
		public void doCommand(String[] args) {

			// checking if the user entered the right num of parameter
			if (model.isGoodInput(4, args.length)) {
				String name = args[0];
				int floors = Integer.parseInt(args[1]);
				int rows = Integer.parseInt(args[2]);
				int cols = Integer.parseInt(args[3]);
				model.generateMaze(name, floors, rows, cols,
						getGenerator(model.getProperties().getGenerateMazeAlgorithm()));
			}
		}
	}

	/**
	 * Gets the generator.
	 *
	 * @param generator the generator
	 * @return the generator
	 */
	public Maze3dGenerator getGenerator(String generator) { //holdoing the data structure for the generators, and return new maze3d generator according the String
		HashMap<String, Maze3dGenerator> generators = new HashMap<String, Maze3dGenerator>();
		generators.put("GrowingTree", new GrowingTreeGenerator());
		generators.put("Simple", new SimpleMaze3dGenerator());
		if (!generators.containsKey(generator)) {
			view.displayMessage(
					new String[] { "Arguments Error", "Unknown generator. please choose: " + generators.keySet() });
		}
		return generators.get(generator);
	}

	/**
	 * Gets the algorithm.
	 *
	 * @param algo the algo
	 * @return the algorithm
	 */
	public Seracher<Position> getAlgorithm(String algo) { //holdoing the data structure for the Search Algorithms, and return new Searcher according the String
		HashMap<String, CommonSearcher<Position>> commands = new HashMap<String, CommonSearcher<Position>>();
		commands.put("BFS", new BFS<Position>());
		commands.put("DFS", new DFS<Position>());
		return commands.get(algo);
	}

	/**
	 * The Class DisplayMazeCommand.
	 */
	public class DisplayMazeCommand implements Command {


		@Override
		public void doCommand(String[] args) {

			// checking if the user entered the right num of parameter
			if (model.isGoodInput(1, args.length)) {
				String name = args[0];
				Maze3d maze = model.getMaze(name);
				view.displayMaze(maze);
			}
		}
	}

	/**
	 * The Class DirCommand.
	 */
	public class DirCommand implements Command {
		

		@Override
		public void doCommand(String[] args) {

			// checking if the user entered the right num of parameter
			if (model.isGoodInput(1, args.length)) {
				String paths = args[0];
				File folderPath = null;
				String[] filelist;

				try {
					// create new file
					folderPath = new File(paths);

					// array of files and directory
					filelist = folderPath.list();

					view.printAnswers(filelist);
				} catch (Exception e) {
					// if any error occurs
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * The Class DisplayCrossSectionCommand.
	 */
	public class DisplayCrossSectionCommand implements Command {


		@Override
		public void doCommand(String[] args) {

			// checking if the user entered the right num of parameter
			if (model.isGoodInput(3, args.length)) {
				String name = args[0];
				String cross = args[1].toLowerCase(); // the dimension
				String index = args[2];
				Maze3d maze = model.getMaze(name);
				switch (cross) { // checking what dimension the cross will be
				case "z":
					view.printCrossSection(maze.getCrossSectionByZ(Integer.parseInt(index)));
					break;
				case "y":
					view.printCrossSection(maze.getCrossSectionByY(Integer.parseInt(index)));
					break;
				case "x":
					view.printCrossSection(maze.getCrossSectionByX(Integer.parseInt(index)));
					break;
				default:
					break;
				}

			}
		}
	}

	/**
	 * The Class SaveMazeCommand.
	 */
	public class SaveMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {

			// checking if the user entered the right num of parameter
			if (model.isGoodInput(2, args.length)) {
				String name = args[0];
				String path = args[1];
				model.saveMaze(name, path);
			}
		}
	}

	/**
	 * The Class LoadMazeCommand.
	 */
	public class LoadMazeCommand implements Command {


		@Override
		public void doCommand(String[] args) {

			// checking if the user entered the right num of parameter
			if (model.isGoodInput(2, args.length)) {
				String path = args[0];
				String name = args[1];
				model.loadMaze(path, name);
			}
		};
	}

	/**
	 * The Class SolveMazeCommand.
	 */
	public class SolveMazeCommand implements Command {


		@Override
		public void doCommand(String[] args) {
			// checking if the user entered the right num of parameter
			if (model.isGoodInput(1, args.length)) {
				String name = args[0];
				Seracher<Position> algo = getAlgorithm(model.getProperties().getSolveMazeAlgorithm());

				model.solveMaze(name, algo);
			}
		}
	}

	/**
	 * The Class DisplaySolutionCommand.
	 */
	public class DisplaySolutionCommand implements Command {


		@Override
		public void doCommand(String[] args) {
			// checking if the user entered the right num of parameter
			if (model.isGoodInput(1, args.length)) {

				String name = args[0];
				// bring the solution of the maze (according to the name) from
				// the
				// model, to sent to the view
				Solution<Position> sol = model.getSolution(name);

				// send the solution to the view, so it could be displayed for
				// the end-user
				view.displaySolution(sol);
			}
		}

	}

	/**
	 * The Class LoadPropXMLCommand.
	 */
	public class LoadPropXMLCommand implements Command { //for loading a properties (XML) file to the program

		@Override
		public void doCommand(String[] args) {
			if (model.isGoodInput(1, args.length)) {
				String file = args[0];
				PropertiesLoader.loadXML(file);
				System.out.println(file);
				model.setProperties(PropertiesLoader.getProperties());
				
			}

		}

	}
	
	/**
	 * The Class ShowPropertiesCommand.
	 */
	public class ShowPropertiesCommand implements Command { //show to the user the current properties

		@Override
		public void doCommand(String[] args) {
			model.showProperties();
		}
	}
	
	
	/**
	 * The Class exit.
	 */
	public class exit implements Command {

		@Override
		public void doCommand(String[] s) {
			model.exit();
		}
	}
}
