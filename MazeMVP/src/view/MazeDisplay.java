package view;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


// TODO: Auto-generated Javadoc
/**
 * The Class MazeDisplay.
 */
public class MazeDisplay extends Canvas {

	/** The maze data display. */
	private int[][] mazeDataDisplay;
	
	/** The maze. */
	private Maze3d maze;
	
	/** The character. */
	private Character character;
	
	/** The start point. */
	private SpecificPoint startPoint = new SpecificPoint("start.png");
	
	/** The goal point. */
	private SpecificPoint goalPoint = new SpecificPoint("goal.png");
	
	/** The floor above. */
	private SpecificPoint floorAbove = new SpecificPoint("up.png");
	
	/** The floor below. */
	private SpecificPoint floorBelow = new SpecificPoint("down.png");
	
	/** The up down. */
	private SpecificPoint up_down = new SpecificPoint("up_down.png");
	
	/** The wall point. */
	private SpecificPoint wallPoint = new SpecificPoint("wall.png");
	
	/** The j. */
	int j;

	/** The win. */
	public boolean win = false;

	/**
	 * Sets the maze data.
	 *
	 * @param mazeData the new maze data
	 */
	public void setMazeData(int[][] mazeData) {
		this.mazeDataDisplay = mazeData;
		this.redraw();
	}

	/** The shell. */
	Shell shell;

	/**
	 * Instantiates a new maze display.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	public MazeDisplay(Shell parent, int style) {
		super(parent, style);
		shell = parent;
		character = new Character();
		character.setPosition(new Position(0, 0, 0));

		addKeyListener();
		addPaintListener();
	}

	/**
	 * Adds the key listener.
	 */
	protected void addKeyListener() {
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (mazeDataDisplay ==null)
					return ;
				Position pos = character.getPosition();
				ArrayList<Position> moves = maze.getPossibleMoves(pos);
				switch (e.keyCode) {
				case SWT.ARROW_RIGHT:
					if (moves.contains(new Position(pos.z, pos.y, pos.x + 1))) { //checking if the right is available
						character.moveRight();
						redraw();
					}
					break;

				case SWT.ARROW_LEFT:
					if (moves.contains(new Position(pos.z, pos.y, pos.x - 1))) {  //checking if the left is available
						character.moveLeft();
						redraw();
					}
					break;
				case SWT.ARROW_UP:
					if (moves.contains(new Position(pos.z, pos.y - 1, pos.x))) { //checking if the up is available
						character.moveForeword();
						redraw();
					}
					break;
				case SWT.ARROW_DOWN:
					if (moves.contains(new Position(pos.z, pos.y + 1, pos.x))) {//checking if the down is available
						character.moveBackward();
						redraw();
					}
					break;
				case SWT.PAGE_UP:
					if (moves.contains(new Position(pos.z + 1, pos.y, pos.x))) { //checking if the up-floor is available
						mazeDataDisplay = maze.getCrossSectionByZ(pos.z + 2);
						character.moveUp();
						redraw();
					}
					break;
				case SWT.PAGE_DOWN:
					if (moves.contains(new Position(pos.z - 1, pos.y, pos.x))) { //checking if the down-floor is available
						mazeDataDisplay = maze.getCrossSectionByZ(pos.z - 2);
						character.moveDown();
						redraw();
					}
					break;
				default:
					break;
				}

			}

		});

	}

	/**
	 * Adds the paint listener.
	 */
	protected void addPaintListener() {
		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (mazeDataDisplay == null) {
					Image imgBack = new Image(null, "lib/images/minions_bg.png");
					e.gc.drawImage(imgBack, 0, 0, imgBack.getBounds().width, imgBack.getBounds().height, 0, 0,
							getSize().x, getSize().y);

					return;
				}

				e.gc.setForeground(new Color(null, 0, 0, 0));
				e.gc.setBackground(new Color(null, 0, 0, 0));

				int width = getSize().x;
				int height = getSize().y;

				int w = width / mazeDataDisplay[0].length;
				int h = height / mazeDataDisplay.length;

				for (int i = 0; i < mazeDataDisplay.length; i++)
					for (int j = 0; j < mazeDataDisplay[i].length; j++) {
						if (mazeDataDisplay[i][j] != 0) {
							wallPoint.draw(w, h, e.gc, (new Position(character.getPosition().z + 1, i, j)));
						} else {
							// check for options to go up or down (other floor)
							ArrayList<Position> moves = maze
									.getPossibleMoves(new Position(character.getPosition().z, i, j));
							if (moves.contains(new Position(character.getPosition().z + 1, i, j))
									&& moves.contains(new Position(character.getPosition().z - 1, i, j))) {
								up_down.draw(w, h, e.gc, (new Position(character.getPosition().z + 1, i, j)));
							} else if (moves.contains(new Position(character.getPosition().z + 1, i, j))) {// up
								floorAbove.draw(w, h, e.gc, (new Position(character.getPosition().z + 1, i, j)));
							} else if (moves.contains(new Position(character.getPosition().z - 1, i, j))) {// down
								floorBelow.draw(w, h, e.gc, (new Position(character.getPosition().z + 1, i, j)));
							}
						}
					}
				// drawing the start and goal positions
				if (character.getPosition().z == maze.getStartPosition().z) {
					startPoint.draw(w, h, e.gc, maze.getStartPosition());
				}

				if (character.getPosition().z == maze.getGoalPosition().z) {
					goalPoint.draw(w, h, e.gc, maze.getGoalPosition());
				}
				character.draw(w, h, e.gc);

				if (character.getPosition().equals(maze.getGoalPosition()) && !win) { //if win
					openWinWindow();
				}
			}
		});

	}

	/**
	 * Open win window.
	 */
	protected void openWinWindow() { //open when the character gets to the goal point
		Shell shell = new Shell();
		shell.setSize(400, 400);
		shell.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				Image hagiga = new Image(null, "lib/images/endParty.png");
				e.gc.drawImage(hagiga, 0, 0, hagiga.getBounds().width, hagiga.getBounds().height, 0, 0,
						shell.getSize().x, shell.getSize().y);
				win = true;
			}
		});
		shell.setSize(600, 400);
		shell.open();

	}

	/**
	 * Sets the character position.
	 *
	 * @param pos the new character position
	 */
	public void setCharacterPosition(Position pos) {
		character.setPosition(pos);
	}

	/**
	 * Sets the maze.
	 *
	 * @param maze the new maze
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
		this.redraw();
	}

	/**
	 * Prints the solution.
	 *
	 * @param sol the sol
	 */
	public void printSolution(Solution<Position> sol) { //show the solution

		{
			TimerTask task = new TimerTask() {
				int i = 0;

				@Override
				public void run() {
					getDisplay().syncExec(new Runnable() {

						@Override
						public void run() {

							if (i == sol.getStates().size()) {
								cancel();
								return;
								// TODO: fix this line
							}
							if (character.getPosition().z == sol.getStates().get(i).getValue().z) {
								character.setPosition(sol.getStates().get(i++).getValue());
								setMazeData(maze.getCrossSectionByZ(character.getPosition().z));
								redraw();
							} else {
								character.setPosition(sol.getStates().get(i + 1).getValue()); //skipping the floor/ceiling floor
								i++;
								setMazeData(maze.getCrossSectionByZ(character.getPosition().z));
								redraw();
							}
						}
					});

				}
			};
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(task, 0, 500); //move every 500 mili-seconds (half second)
		}
	}

	/**
	 * Prints the hint.
	 *
	 * @param sol the sol
	 */
	public void printHint(Solution<Position> sol) {

		Position currPos = character.getPosition();

		for (j = 0; j < sol.getStates().size(); j++)
			if (sol.getStates().get(j).getValue().equals(currPos))
				break;

		TimerTask task = new TimerTask() {

			int i = 0;

			@Override
			public void run() {
				getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {

						if ((j == sol.getStates().size()) || i == 3 || i == sol.getStates().size()) {
							cancel();
							return;
						}

						if ((j != 0) || (j != sol.getStates().size())) {
							character.setPosition(sol.getStates().get(j++).getValue());
							setMazeData(maze.getCrossSectionByZ(character.getPosition().z));
							redraw();
							i++;
						}
					}
				});
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 500);

	}

}
