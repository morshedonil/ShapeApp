import javafx.scene.input.MouseEvent;

/**
 * Class representing a handler for drawing ovals.
 */
public class OvalHandler extends DrawHandler{
	
    /**
     * Constructor for OvalHandler.
     *
     * @param sc The canvas on which ovals will be drawn.
     */
	public OvalHandler(ShapeCanvas sc) {		
		super(sc);
	}
	
    /**
     * Handles the mouse pressed event for drawing an oval.
     *
     * @param e The mouse event.
     */
	@Override
	public void mousePressed(MouseEvent e) {
		
		shape = new Oval();
		super.mousePressed(e);
	}

}
