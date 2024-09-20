
public class DeleteEdit extends Edit {

	public DeleteEdit(ShapeCanvas sc, MyShape s) {
		super(sc, s);
	}
	
	@Override
	public void redo() {
		canvas.deleteShape(shape);
		canvas.paint();
	}
	
	@Override
	public void undo() {
		canvas.addShape(shape);
	}

}
