
/**
 * Represents an edit operation for drawing a shape on a ShapeCanvas.
 */
public class DrawEdit extends Edit {
	
    /**
     * Constructs a new DrawEdit object.
     * 
     * @param sc the ShapeCanvas on which the shape is drawn
     * @param s the shape to be managed by this edit
     */
	public DrawEdit(ShapeCanvas sc, MyShape s) {
		super(sc, s);
	}
	
    /**
     * Redoes the drawing operation by adding the shape back to the canvas and re-painting it.
     */
	@Override
	public void redo() {
		canvas.addShape(shape);
		canvas.paint();
	}
	
    /**
     * Undoes the drawing operation by removing the shape from the canvas and re-painting it.
     */
	@Override
	public void undo() {
		canvas.deleteShape(shape);
		canvas.paint();
	}
}
