import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class representing a line.
 */
public class Line extends MyShape {
	
    /**
     * Calls the default parent constructor
     * Default constructor. Initializes the line with default values.
     */
	public Line() {
		super();
	}
	
    /**
     * Constructor with start and end points.
     * Calls its parent constructor
     *
     * @param p1 The start point of the line.
     * @param p2 The end point of the line.
     */
	public Line(Point2D p1, Point2D p2) {
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
	public Line(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}
	
    /**
     * Draws the line.
     *
     * @param gc The graphics context to use for drawing.
     */
	@Override
	public void draw(GraphicsContext gc) {
		
		//drawBounds(gc);
		gc.setStroke(color);
		gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	
	/**
	 * Returns a string representation of this Line object.
	 * The string representation includes the type of shape ("Line") followed by the superclass's string representation.
	 *
	 * @return A string representation of this Line object.
	 */
	@Override
	public String toString() {
		return "Line " + super.toString();
	}

}
