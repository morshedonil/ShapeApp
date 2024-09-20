
/**
 * Represents an edit operation for moving a shape on a ShapeCanvas.
 */
public class MoveEdit extends Edit {

	private double dx;
	private double dy;
	
    /**
     * Constructs a new MoveEdit object to manage the movement of a shape.
     * 
     * @param sc the ShapeCanvas on which the shape is located
     * @param s the shape to be moved
     * @param x the horizontal displacement of the shape
     * @param y the vertical displacement of the shape
     */
	public MoveEdit(ShapeCanvas sc, MyShape s, double x, double y) {

		super(sc, s);
		dx = x;
		dy = y;
	}
	
    /**
     * Redoes the movement of the shape by applying the specified horizontal and vertical displacements.
     */
	@Override
	public void redo() {
		shape.move(dx, dy);
		canvas.paint();
	}
	
    /**
     * Undoes the movement of the shape by reversing the applied displacements.
     */
	@Override
	public void undo() {
		shape.move(-dx, -dy);
		canvas.paint();
	}
}
