import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class representing an oval.
 */
public class Oval extends MyShape{
	
    /**
     * Calls its default parent constructor
     * Default constructor. Initializes the oval with default values.
     */
	public Oval() {
		super();
	}
	
    /**
     * Constructor with start and end points.
     * Calls its parent constructor
     *
     * @param p1 The start point of the oval.
     * @param p2 The end point of the oval.
     */
	public Oval(Point2D p1, Point2D p2) {
		super(p1, p2);
	}
	
    /**
     * Constructor with coordinates of start and end points.
     * Calls it parent constructor
     *
     * @param x1 The x-coordinate of the start point.
     * @param y1 The y-coordinate of the start point.
     * @param x2 The x-coordinate of the end point.
     * @param y2 The y-coordinate of the end point.
     */
	public Oval (double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}
	
    /**
     * Draws the oval.
     *
     * @param gc The graphics context to use for drawing.
     */
	@Override
	public void draw(GraphicsContext gc) {
		//drawBounds(gc);
		
		if(filled == true) {
			gc.setFill(color);
			gc.fillOval(ulx, uly, width, height);
		}
		else {
			gc.setStroke(color);
			gc.strokeOval(ulx, uly, width, height);
		}
	}
	
	/**
	 * Returns a string representation of this Oval object.
	 * The string representation includes the type of shape ("Oval") followed by the superclass's string representation.
	 *
	 * @return A string representation of this Oval object.
	 */
	@Override
	public String toString() {
		return "Oval " + super.toString();
	}
}
