import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a group of shapes as a single shape
 */
public class ShapeGroup extends MyShape{

	protected ArrayList<MyShape> group;
	
    /**
     * Constructs an empty ShapeGroup.
     */
	public ShapeGroup() {

		group = new ArrayList<>();
	}
	
    /**
     * Checks if the shape group is empty.
     * 
     * @return true if there are no shapes in the group, false otherwise.
     */
	public boolean isEmpty() {

		return group.isEmpty();
	}
	
    /**
     * Returns the number of shapes in the group.
     * 
     * @return the size of the group.
     */
	public int size() {

		return group.size();
	}
	
    /**
     * Creates a deep copy of this ShapeGroup including all its members.
     * 
     * @return a deep copy of this ShapeGroup.
     */
	@Override
	public Object clone() {
		ShapeGroup copy = (ShapeGroup) super.clone();
		copy.group = new ArrayList<>();

		for(int i = 0; i < group.size(); i++) {

			copy.group.add((MyShape) group.get(i).clone());	
		}
		return copy;
	}
	

    /**
     * Adds a shape to the group if it is not already included.
     * 
     * @param shape The shape to be added to the group.
     */
	public void addMember(MyShape shape) {

		if(!group.contains(shape)) {
			group.add(shape);
		}
		updateCenter();
	}
	
    /**
     * Removes a shape from the group.
     * 
     * @param shape The shape to be removed from the group.
     */
	public void removeMember(MyShape shape) {
		group.remove(shape);

		updateCenter();
	}
	
    /**
     * Determines if a shape's center is within the group's bounding box.
     * 
     * @param shape The shape to check.
     * @return true if the shape is within the group's bounds, false otherwise.
     */
	public boolean within(MyShape shape) {

		double curX = shape.getCetner().getX();
		double curY = shape.getCetner().getY();

		if(curX <= ulx + width && curX >= ulx && curY <= uly + height && curY >= uly) {
			return true;
		}
		else {
			return false;
		}
	}
	
    /**
     * Updates the center of the group based on the average coordinates of its members' positions.
     */
	@Override
	public void updateCenter() {

		double totalX = 0;
		double totalY = 0;

		for(MyShape s : group) {
			totalX += s.getP1().getX();
			totalY += s.getP2().getY();
		}

		double averageX = totalX / group.size();
		double averageY = totalY / group.size();

		center = new Point2D(averageX, averageY);
	}
	
    /**
     * Moves all members of the group by specified offsets along the x and y axes.
     * 
     * @param dx The offset along the x-axis.
     * @param dy The offset along the y-axis.
     */
	@Override
	public void move(double dx, double dy) {
		super.move(dx, dy);

		for(MyShape s : group) {
			s.move(dx, dy);
		}
	}
	
    /**
     * Draws the group's bounding box and all its members on the given GraphicsContext.
     * 
     * @param gc The graphics context where the group and its members are drawn.
     */
	@Override
	public void draw(GraphicsContext gc) {
		gc.setLineDashes(10);
		gc.setStroke(Color.LIGHTGRAY);
		super.drawBounds(gc);

		for(int i = 0; i < group.size(); i++) {

			MyShape curShape = group.get(i);
			curShape.draw(gc);
		}
	}
	
	/**
	 * Returns the list of shapes that are members of the group.
	 *
	 * @return an ArrayList of MyShape objects representing the members of the group.
	 */
	public ArrayList<MyShape> getMembers() {
		return group;
	}
	
    /**
     * Returns a string representation of the ShapeGroup, including all its members.
     * @return A formatted string detailing the group and its members.
     */
	public String toString() {

		String toString = String.format("ShapeGroup %d %f %f %f %f \n", group.size(), p1.getX(), p1.getY(), p2.getX(), p2.getY());

		for(MyShape s : group) {
			toString += s.toString();
		}

		return toString;
	}
}
