package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecificPoint.
 */
public class SpecificPoint {

	/** The image. */
	private Image image;

	/**
	 * Instantiates a new specific point.
	 *
	 * @param type the type
	 */
	public SpecificPoint(String type) {
		ImageData ideaData = new ImageData("lib/images/" + type);
		int whitePixel = ideaData.palette.getPixel(new RGB(255, 255, 255));
		ideaData.transparentPixel = whitePixel;
		image = new Image(null, ideaData);
	}

	/**
	 * Draw.
	 *
	 * @param cellWidth the cell width
	 * @param cellHeight the cell height
	 * @param gc the gc
	 * @param pos the pos
	 */
	public void draw(int cellWidth, int cellHeight, GC gc, Position pos) {

		gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, cellWidth * pos.x,
				cellHeight * pos.y, cellWidth, cellHeight);
	}
}
