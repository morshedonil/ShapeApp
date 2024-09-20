import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Handles mouse events to copy and move shapes within a ShapeCanvas.
 */
public class CopyHandler implements EventHandler<MouseEvent>{

	private ShapeCanvas canvas;
	private MyShape shapeCopy;
	private MyShape closestShape;
	private double x0, y0;
	private double x1, y1;

	/**
	 * Constructs a CopyHandler for managing shape copying and moving on the specified canvas.
	 * @param sc The ShapeCanvas on which shapes will be copied and moved.
	 */
	public CopyHandler(ShapeCanvas sc) {
		canvas = sc;
	}

	/**
	 * Handles mouse pressed and dragged events to copy and move shapes.
	 * On mouse press, it identifies the closest shape to the cursor, creates a copy, and adds it to the canvas.
	 * On mouse drag, it moves the copied shape relative to the last known mouse coordinates.
	 * @param e The MouseEvent to process, either mouse pressed or dragged.
	 */
	@Override
	public void handle(MouseEvent e) {

		if(e.getEventType() == MouseEvent.MOUSE_PRESSED) {

			closestShape = canvas.closestShape(e.getX(), e.getY());

			if(closestShape != null) {
				shapeCopy = (MyShape) closestShape.clone();

				x0 = e.getX();
				y0 = e.getY();

				canvas.addShape(shapeCopy);
			}
		}

		if(e.getEventType() == MouseEvent.MOUSE_DRAGGED) {

			x1 = e.getX();
			y1 = e.getY();

			if(shapeCopy != null) {

				shapeCopy.move(x1 - x0, y1 - y0);

				x0 = x1;
				y0 = y1;

				canvas.paint();
			}
		}

		if(e.getEventType() == MouseEvent.MOUSE_RELEASED) {

			canvas.addEdit(new CopyEdit(canvas, shapeCopy));
		}
	}

}
