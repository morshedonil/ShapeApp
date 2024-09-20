import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Abstract class representing a shape.
 */
public abstract class MyShape implements Serializable, Cloneable {

	private static final Color DEFAULT_COLOR = Color.BLACK;

	protected transient Point2D p1;
	protected transient Point2D p2; 
	protected transient Point2D center; 
	protected transient Color color;
	protected boolean filled;
	protected double ulx;
	protected double uly; 
	public double width, height;

	/**
	 * Default constructor. Initializes the shape with default color.
	 */
	public MyShape() {
	}

	/**
	 * Constructor with start and end points.
	 *
	 * @param p1 The start point of the shape.
	 * @param p2 The end point of the shape.
	 */
	public MyShape (Point2D p1, Point2D p2) {
		this(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	/**
	 * Constructor with coordinates of start and end points.
	 *
	 * @param x1 The x-coordinate of the start point.
	 * @param y1 The y-coordinate of the start point.
	 * @param x2 The x-coordinate of the end point.
	 * @param y2 The y-coordinate of the end point.
	 */
	public MyShape (double x1, double y1, double x2, double y2) {
		p1 = new Point2D(x1, y1);
		p2 = new Point2D(x2, y2);

		filled = false;

		updateBounds();
		updateCenter();
	}

	/**
	 *
	 * @return The start point of the shape.
	 */
	public Point2D getP1() {
		return p1;
	}

	/**
	 *
	 * @return The end point of the shape.
	 */
	public Point2D getP2() {
		return p2;
	}

	/**
	 *
	 * @return The color of the shape.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Checks if the shape is filled.
	 *
	 * @return true if the shape is filled, false otherwise.
	 */
	public boolean isFilled() {
		return filled;
	}

	/**
	 *
	 * @return The upper-left x-coordinate of the bounding box.
	 */
	public double getULX() {
		return ulx;
	}

	/**
	 *
	 * @return The upper-left y-coordinate of the bounding box.
	 */
	public double getULY() {
		return uly;
	}

	/**
	 *
	 * @return The width of the shape.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 *
	 * @return The height of the shape.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 *
	 * @return The center point of the shape.
	 */
	public Point2D getCetner() {
		return center;
	}

	/**
	 * Sets the start point of the shape.
	 *
	 * @param newP1 The new start point of the shape.
	 */
	public void setP1(Point2D newP1) {
		p1 = newP1;
	}

	/**
	 * Sets the start point of the shape using coordinates.
	 *
	 * @param x The x-coordinate of the new start point.
	 * @param y The y-coordinate of the new start point.
	 */
	public void setP1(double x, double y) {
		p1 = new Point2D(x, y);
	}

	/**
	 * Sets the end point of the shape.
	 *
	 * @param newP2 The new end point of the shape.
	 */
	public void setP2(Point2D newP2) {
		p2 = newP2;
		updateBounds();
		updateCenter();
	}

	/**
	 * Sets the end point of the shape using coordinates.
	 *
	 * @param x The x-coordinate of the new end point.
	 * @param y The y-coordinate of the new end point.
	 */
	public void setP2(double x, double y) {
		p2 = new Point2D(x, y);
		updateBounds();
		updateCenter();
	}

	/**
	 * Sets the color of the shape.
	 *
	 * @param newColor The new color of the shape.
	 */
	public void setColor(Color newColor) {
		color = newColor;
	}

	/**
	 * Sets whether the shape is filled.
	 *
	 * @param isFilled true if the shape should be filled, false otherwise.
	 */
	public void setFilled(boolean isFilled) {
		filled = isFilled;
	}

	/**
	 * Updates the bounds of the shape based on its start and end points.
	 */
	public void updateBounds() {

		ulx = Math.min(p1.getX(), p2.getX());
		uly = Math.min(p1.getY(), p2.getY());

		width = Math.abs(p1.getX() - p2.getX());
		height = Math.abs(p1.getY() - p2.getY());
	}

	/**
	 * Updates the center point of the shape based on its start and end points.
	 */
	public void updateCenter() {
		center = p1.midpoint(p2.getX(), p2.getY());
	}

	/**
	 * Calculates the distance between the shape and a given point.
	 *
	 * @param x The x-coordinate of the point.
	 * @param y The y-coordinate of the point.
	 * @return The distance between the shape and the point.
	 */
	public double distance(double x, double y) {
		return center.distance(x, y);
	}

	/**
	 * Draws the bounding box of the shape.
	 *
	 * @param gc The graphics context to use for drawing.
	 */
	public void drawBounds(GraphicsContext gc) {
		gc.strokeRect(ulx, uly, width, height);
		gc.setLineDashes(null);
	}

	/**
	 * Abstract method for drawing the shape.
	 *
	 * @param gc The graphics context to use for drawing.
	 */
	public abstract void draw(GraphicsContext gc);

	/**
	 * Custom serialization method for writing object state to a stream.
	 * Writes the coordinates of points p1 and p2, and the color components (red, green, blue) to the ObjectOutputStream.
	 *
	 * @param oos The ObjectOutputStream to write to.
	 * @throws IOException If an I/O error occurs during writing.
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();

		oos.writeDouble(p1.getX());
		oos.writeDouble(p1.getY());
		oos.writeDouble(p2.getX());
		oos.writeDouble(p2.getY());

		oos.writeDouble(color.getRed());
		oos.writeDouble(color.getBlue());
		oos.writeDouble(color.getGreen());
	}

	/**
	 * Custom deserialization method for reading object state from a stream.
	 * Reads the coordinates of points p1 and p2, and the color components (red, green, blue) from the ObjectInputStream.
	 * Sets the points and color of the object based on the read values.
	 *
	 * @param ois The ObjectInputStream to read from.
	 * @throws IOException If an I/O error occurs during reading.
	 * @throws ClassNotFoundException If the class of a serialized object cannot be found.
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();

		double x1 = ois.readDouble();
		double y1 = ois.readDouble();
		double x2 = ois.readDouble();
		double y2 = ois.readDouble();

		double r = ois.readDouble();
		double g = ois.readDouble();
		double b = ois.readDouble();

		setP1(x1, y1);
		setP2(x2, y2);

		System.out.println(p1.getX() + " " + p1.getY());
		System.out.println(p2.getX() + " " + p2.getY());

		updateBounds();
		updateCenter();

		color = Color.color(r, g, b);
	}

	/**
	 * Returns a string representation of this object.
	 * The string includes the coordinates of points p1 and p2, the color components (red, green, blue), and the filled status.
	 * @return A string representation of this object.
	 */
	@Override
	public String toString() {
		return String.format("%.3f %.3f %.3f %.3f %.3f %.3f %.3f %b\n", p1.getX(), p1.getY(), p2.getX(), p2.getY(), 
				color.getRed(), color.getGreen(), color.getBlue(), filled);
	}

	/**
	 * Moves this shape by dx and dy amount along the x and y axes respectively.
	 * @param dx The amount to move the shape along the x-axis.
	 * @param dy The amount to move the shape along the y-axis.
	 */
	public void move(double dx, double dy) {
		setP1(dx + p1.getX(), dy + p1.getY());
		setP2(dx + p2.getX(), dy + p2.getY());
	}

	/**
	 * Creates a deep copy of this shape.
	 * @return A deep copy of this shape or null if cloning is not supported.
	 * @throws CloneNotSupportedException if the object's class does not support the Cloneable interface.
	 */
	@Override
	public Object clone() {
		try{
			MyShape copyShape = (MyShape) super.clone();

			copyShape.p1 = new Point2D(p1.getX(), p1.getY());
			copyShape.p2 = new Point2D(p2.getX(), p2.getY());
			copyShape.center = new Point2D(center.getX(), center.getY());
			copyShape.color = Color.color(color.getRed(), color.getBlue(), color.getGreen());

			return copyShape;
		}
		catch(CloneNotSupportedException e) {
			System.out.print("Clonning unsuccessful");
			return null;
		}
	}
}
