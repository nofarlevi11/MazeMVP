package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import algorithms.mazeGenerators.Position;

public class Character {

	private Position position;
	private Image image;

	public Character() {
		ImageData ideaData = new ImageData("lib/images/character.png");
		int whitePixel = ideaData.palette.getPixel(new RGB(255, 255, 255));
		ideaData.transparentPixel = whitePixel;
		image = new Image(null, ideaData);
	}
	
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = new Position(position.z, position.y, position.x);
	}

	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, cellWidth * position.x,
				cellHeight * position.y, cellWidth, cellHeight);
	}
	

	public void moveRight() {
		position.x++;
	}

	public void moveLeft() {
		position.x--;
	}

	public void moveUp() {
		position.z++;
	}

	public void moveDown() {
		position.z--;
	}

	public void moveForeword() {
		position.y--;
	}

	public void moveBackward() {
		position.y++;
	}
}
