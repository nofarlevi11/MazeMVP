package view;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;


public class MazeDisplay extends Canvas {

	private int[][] mazeDataDisplay;
	private Maze3d maze;
	private Character character;
	private SpecificPoint startPoint = new SpecificPoint("start.jpg");
	private SpecificPoint goalPoint = new SpecificPoint("goal.jpg");
	private SpecificPoint floorAbove = new SpecificPoint("up.jpg");
	private SpecificPoint floorBelow = new SpecificPoint("down.jpg");
	private SpecificPoint wallPoint = new SpecificPoint("wall.jpg");

	public void setMazeData(int[][] mazeData) {
		this.mazeDataDisplay = mazeData;
		this.redraw();
	}

	public MazeDisplay(Shell parent, int style) {
		super(parent, style);
		character = new Character();
		character.setPosition(new Position (0,0,0));

		

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				Position pos = character.getPosition();
				ArrayList<Position> moves = maze.getPossibleMoves(pos);
				switch (e.keyCode) {
				case SWT.ARROW_RIGHT:
					if (moves.contains(new Position(pos.z, pos.y, pos.x+1))) {
						character.moveRight();
						redraw();
					}
					break;

				case SWT.ARROW_LEFT:
					if (moves.contains(new Position(pos.z, pos.y, pos.x-1))) {
						character.moveLeft();
						redraw();
					}
					break;
				case SWT.ARROW_UP:
					if (moves.contains(new Position(pos.z, pos.y-1, pos.x))) {
						character.moveForeword();
						redraw();
					}
					break;
				case SWT.ARROW_DOWN:
					if (moves.contains(new Position(pos.z, pos.y+1, pos.x))) {
						character.moveBackward();
						redraw();
					}
					break;
				case SWT.PAGE_UP:
					if (moves.contains(new Position(pos.z + 1, pos.y, pos.x))) {
						mazeDataDisplay = maze.getCrossSectionByZ(pos.z + 1);
						character.moveUp();
						redraw();
					}
					break;
				case SWT.PAGE_DOWN:
					if (moves.contains(new Position(pos.z - 1, pos.y, pos.x))) {
						mazeDataDisplay = maze.getCrossSectionByZ(pos.z - 1);
						character.moveDown();
						redraw();
					}
					break;
				}

			}

		});
		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (mazeDataDisplay == null)
					return;

				e.gc.setForeground(new Color(null, 0, 0, 0));
				e.gc.setBackground(new Color(null, 0, 0, 0));

				int width = getSize().x;
				int height = getSize().y;

				int w = width / mazeDataDisplay[0].length;
				int h = height / mazeDataDisplay.length;

				for (int i = 0; i < mazeDataDisplay.length; i++)
					for (int j = 0; j < mazeDataDisplay[i].length; j++) {
						int x = j * w;
						int y = i * h;
						if (mazeDataDisplay[i][j] != 0) {
							wallPoint.draw(w, h, e.gc, (new Position(character.getPosition().z + 1, i, j)));
						} else {
							// check for options to go up or down (other floor)
							ArrayList<Position> moves = maze
									.getPossibleMoves(new Position(character.getPosition().z, i, j));
							if (moves.contains(new Position(character.getPosition().z + 1, i, j))) {// up
								floorAbove.draw(w, h, e.gc, (new Position(character.getPosition().z +1, i, j)));
							} else if (moves.contains(new Position(character.getPosition().z - 1, i, j))) {// down
								floorBelow.draw(w, h, e.gc, (new Position(character.getPosition().z-1, i, j)));
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

			}
		});

	}

	public void setCharacterPosition(Position pos) {
		character.setPosition(pos);
	}

	public void setMaze(Maze3d maze) {
		this.maze = maze;
		this.redraw();
	}
}
//	public void printSolution(Solution<Position> sol) {

	// {
			
//			System.out.println(sol);
//			TimerTask task = new TimerTask() {
//				int i=0;
//				@Override
//				public void run() {	
//					getDisplay().syncExec(new Runnable() {					
//
//						@Override
//						public void run() {
//							
//							if (i==sol.getSolution().size()){
//								return;
//								//TODO: fix this line
//							}
//								
//							character.setPos(sol.getSolution().get(i++).getState());
//							setMazeData(maze.getCrossSectionByZ(character.getPosition().z));
//							redraw();
//						}
//					});
//					
//				}
////			};
//			Timer timer = new Timer();
//			timer.scheduleAtFixedRate(task, 0, 500);
//			
//		}
//		
//	}

