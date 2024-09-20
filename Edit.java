
/**
 * Abstract class representing an edit operation on a ShapeCanvas.
 */
public abstract class Edit {
	
	protected MyShape shape;
	protected ShapeCanvas canvas;
	
    /**
     * Constructs an Edit object with a specified shape and canvas.
     * 
     * @param c The canvas associated with this edit
     * @param s The shape involved in this edit
     */
	public Edit(ShapeCanvas c, MyShape s) {
		
		canvas = c;
		shape = s;
	}
	
    /**
     * Undoes the edit operation.
     */
	public abstract void undo();
	
    /**
     * Redoes the edit operation.
     */
	public abstract void redo();
}
