import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Handles mouse click events to delete shapes from a ShapeCanvas.
 */
public class DeleteHandler implements EventHandler<MouseEvent>{
	
    /**
     * Constructs a DeleteHandler for managing shape deletions on the specified canvas.
     *
     * @param sc The ShapeCanvas from which shapes will be deleted.
     */
	private ShapeCanvas canvas;

	public DeleteHandler(ShapeCanvas sc) {
		canvas = sc;
	}

	/**
	 * Handles mouse clicked events to delete the closest shape to the click location.
	 * When a mouse click event is detected, it finds the shape nearest to the click position and deletes.
	 * @param e The MouseEvent to process, specifically looking for mouse clicked events.
	 */
	@Override
	public void handle(MouseEvent e) {

		if(e.getEventType() == MouseEvent.MOUSE_CLICKED) {
			MyShape shape = canvas.closestShape(e.getX(), e.getY());
			canvas.deleteShape(shape);
			canvas.paint();
			canvas.addEdit(new DeleteEdit(canvas, shape));
		}
	}
}


