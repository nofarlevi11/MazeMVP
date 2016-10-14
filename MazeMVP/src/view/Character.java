package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class Character.
 */
public class Character {

	/** The position. */
	private Position position;
	
	/** The image. */
	private Image image;

	/**
	 * Instantiates a new character.
	 */
	public Character() {
		ImageData ideaData = new ImageData("lib/images/character.png");
		//remove the white around
		int whitePixel = ideaData.palette.getPixel(new RGB(255, 255, 255));
		ideaData.transparentPixel = whitePixel;
		image = new Image(null, ideaData);
	}
	
	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public Position getPosition() { //return the current position of the character
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(Position position) { //put the character in another position
		this.position = new Position(position.z, position.y, position.x);
	}

	/**
	 * Draw.
	 *
	 * @param cellWidth the cell width
	 * @param cellHeight the cell height
	 * @param gc the gc
	 */
	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, cellWidth * position.x,
				cellHeight * position.y, cellWidth, cellHeight);
	}
	

	/**
	 * Move right.
	 */
	public void moveRight() {
		position.x++;
	}

	/**
	 * Move left.
	 */
	public void moveLeft() {
		position.x--;
	}

	/**
	 * Move up.
	 */
	public void moveUp() {
		position.z++;
		position.z++;

	}

	/**
	 * Move down.
	 */
	public void moveDown() {
		position.z--;
		position.z--;

	}

	/**
	 * Move foreword.
	 */
	public void moveForeword() {
		position.y--;
	}

	/**
	 * Move backward.
	 */
	public void moveBackward() {
		position.y++;
	}
}
