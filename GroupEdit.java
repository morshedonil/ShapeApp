/**
 * Represents an edit operation for grouping shapes on a ShapeCanvas.
 */
public class GroupEdit extends Edit{
	
	private ShapeGroup group;
	
    /**
     * Constructs a new GroupEdit object to manage the grouping of shapes into a single ShapeGroup.
     *
     * @param canvas the ShapeCanvas on which the grouping occurs
     * @param shape the ShapeGroup containing the grouped shapes
     */
	public GroupEdit(ShapeCanvas canvas, MyShape shape) {

		super(canvas, shape);
		group = (ShapeGroup) shape;
	}
	
    /**
     * Un-groups the grouping of shapes. 
     */
	@Override
	public void undo() {
		
		for(MyShape s : group.getMembers()) {
			canvas.addShape(s);
		}
		canvas.deleteShape(group);
		canvas.paint();
	}
	
	/**
	 * Redoes the grouping of shapes
	 */
	@Override
	public void redo() {
		
		for(MyShape s : group.getMembers()) {
			canvas.deleteShape(s);
		}
		
		canvas.addShape(group);
		canvas.paint();
	}

}
