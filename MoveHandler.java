import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
/**
 * Handles mouse events for moving shapes on a ShapeCanvas.
 * Manages mouse presses, drags, and releases to move shapes around the canvas.
 */
public class MoveHandler implements EventHandler<MouseEvent> {

	private ShapeCanvas canvas;
	private MyShape closestShape;
	private double x0, x1, initX;
	private double y0, y1, initY;

	public MoveHandler(ShapeCanvas sc) {

		canvas = sc;
	}
	
	/**
	 * Responds to mouse events on the canvas, handling the movement of shapes.
	 * Processes mouse press to initialize, mouse drag to move the shape, and mouse release to finalize the move.
	 *
	 * @param e the MouseEvent that controls the action
	 */
	@Override
	public void handle(MouseEvent e) {

		if(e.getEventType() == MouseEvent.MOUSE_PRESSED) {

			closestShape = canvas.closestShape(e.getX(), e.getY()); 

			x0 = e.getX();
			y0 = e.getY();
			
			initX = x0;
			initY = y0;

			x1 = x0;
			y1 = y0;
		}

		if(e.getEventType() == MouseEvent.MOUSE_DRAGGED) {

			x1 = e.getX();
			y1 = e.getY();

			if(closestShape != null) {

				closestShape.move(x1 - x0, y1 - y0);

				x0 = x1;
				y0 = y1;

				canvas.paint();
			}
		}

		if(e.getEventType() == MouseEvent.MOUSE_RELEASED) {

			double cx = e.getX();
			double cy = e.getY();

			double dx = cx - initX;
			double dy = cy - initY;

			canvas.addEdit(new MoveEdit(canvas, closestShape, dx, dy));
		}
	}
}
