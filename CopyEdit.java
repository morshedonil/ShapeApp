
/**
 * Represents an edit operation for copying a shape on a ShapeCanvas.
 */
public class CopyEdit extends Edit {
	
    /**
     * Constructs a new CopyEdit object.
     * 
     * @param sc the ShapeCanvas on which the shape is copied
     * @param s the shape that is copied
     */
	public CopyEdit(ShapeCanvas sc, MyShape s) {
		super(sc, s);
	}
	
    /**
     * Redoes the copy operation by adding the shape back to the canvas and re-painting it.
     */
	@Override
	public void redo() {
		canvas.addShape(shape);
		canvas.paint();
	}
	
    /**
     * Undoes the copy operation by removing the shape from the canvas and re-painting it.
     */
	@Override
	public void undo() {
		canvas.deleteShape(shape);	
		canvas.paint();
	}
}
