import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class representing a rectangle.
 */
public class Rect extends MyShape{
	
    /**
     * Calls the default parent constructor
     * Default constructor. Initializes the rectangle with default values.
     */
	public Rect() {
		super();
	}
	
    /**
     * Constructor with start and end points.
     * Calls its parent constructor
     *
     * @param p1 The start point of the rectangle.
     * @param p2 The end point of the rectangle.
     */
	public Rect(Point2D p1, Point2D p2) {
		super(p1, p2);
	}
	
    /**
     * Constructor with coordinates of start and end points.
     * Calls its parent constructor
     *
     * @param x1 The x-coordinate of the start point.
     * @param y1 The y-coordinate of the start point.
     * @param x2 The x-coordinate of the end point.
     * @param y2 The y-coordinate of the end point.
     */
	public Rect(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}
	
    /**
     * Draws the rectangle.
     *
     * @param gc The graphics context to use for drawing.
     */
	@Override
	public void draw(GraphicsContext gc) {
		//drawBounds(gc);
		
		if(filled == true) {
			gc.setFill(color);
			gc.fillRect(ulx, uly, width, height);
		}
		else {
			gc.setStroke(color);
			gc.strokeRect(ulx, uly, width, height);
		}
	}
	
	/**
	 * Returns a string representation of this Rect object.
	 * The string representation includes the type of shape ("Rect") followed by the superclass's string representation.
	 *
	 * @return A string representation of this Rect object.
	 */
	@Override
	public String toString() {
		return "Rect " + super.toString();
	}
}
