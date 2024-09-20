import javafx.scene.input.MouseEvent;

/**
 * Class representing a handler for drawing rectangles.
 */
public class RectHandler extends DrawHandler{
	
    /**
     * Constructor for RectHandler.
     *
     * @param sc The canvas on which rectangles will be drawn.
     */
	public RectHandler(ShapeCanvas sc) {
		super(sc);
	}
	
    /**
     * Handles the mouse pressed event for drawing a rectangle.
     *
     * @param e The mouse event.
     */
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		shape = new Rect();
		super.mousePressed(e);
	}
}
