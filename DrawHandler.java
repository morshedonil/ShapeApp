import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * Class representing a handler for drawing shapes.
 */
public class DrawHandler implements EventHandler<MouseEvent> { 

	protected MyShape shape;
	protected ShapeCanvas canvas;
	
    /**
     * Constructor for DrawHandler.
     *
     * @param sc The canvas on which shapes will be drawn.
     */
	public DrawHandler(ShapeCanvas sc) {
		canvas = sc;
	}
	
    /**
     * Handles the mouse pressed event.
     *
     * @param e The mouse event.
     */
	protected void mousePressed(MouseEvent e) {

		if(shape != null) {
			canvas.setCurrentShape(shape);
			shape.setP1(e.getX(), e.getY());
		}
	}
	
    /**
     * Handles the mouse dragged event.
     *
     * @param e The mouse event.
     */
	protected void mouseDragged(MouseEvent e) {

		if(shape != null) {
			shape.setP2(e.getX(), e.getY());
			canvas.paint();
		}
	}
	
    /**
     * Handles the mouse released event.
     *
     * @param e The mouse event.
     */
	protected void mouseReleased(MouseEvent e) {

		if(shape != null) {
			shape.setP2(e.getX(), e.getY());
			canvas.addShape(shape);
			canvas.setCurrentShape(null);
			canvas.paint();
			canvas.addEdit(new DrawEdit(canvas, shape));
			shape = null;
		}
	}
	
    /**
     * Handles the mouse event.
     *
     * @param event The mouse event.
     */
	@Override
	public void handle(MouseEvent event) {

		EventType<?extends MouseEvent> eventType = event.getEventType();

		String eventName = eventType.getName();

		switch(eventName) {

		case "MOUSE_PRESSED":			
			mousePressed(event);
			break;

		case "MOUSE_DRAGGED":		
			mouseDragged(event);
			break;

		default:
			mouseReleased(event);
		}
	}
}
