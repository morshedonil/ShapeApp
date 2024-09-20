import javafx.scene.input.MouseEvent;

/**
 * Class representing a handler for drawing lines.
 */
public class LineHandler extends DrawHandler {
	
    /**
     * Constructor for LineHandler.
     *
     * @param sc The canvas on which lines will be drawn.
     */
	public LineHandler(ShapeCanvas sc) {
		super(sc);
	}
	
    /**
     * Handles the mouse pressed event for drawing a line.
     *
     * @param e The mouse event.
     */
	@Override
	public void mousePressed(MouseEvent e) {
		shape = new Line();	
		super.mousePressed(e);
	}
}