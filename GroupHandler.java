import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Handles mouse events for grouping shapes within a ShapeCanvas.
 */
public class GroupHandler implements EventHandler<MouseEvent>{

	private ShapeCanvas canvas;
	private ShapeGroup shapeGroup;
	private ArrayList<MyShape> allShapes;

	/**
	 * Constructs a GroupHandler for managing shape grouping on the canvas.
	 * @param sc The ShapeCanvas on which shapes will be grouped.
	 */
	public GroupHandler(ShapeCanvas sc) {
		canvas = sc;
	}               

	/**
	 * Processes mouse events to create and manage a group of shapes.
	 * 
	 * On MOUSE_PRESSED, initializes a new ShapeGroup.
	 * On MOUSE_DRAGGED, updates the group's boundaries.
	 * On MOUSE_RELEASED, adds shapes to the group, removing them from the main canvas.
	 * @param e The MouseEvent to process mouse events.
	 */
	@Override
	public void handle(MouseEvent e) {

		if(e.getEventType() == MouseEvent.MOUSE_PRESSED) {

			shapeGroup = new ShapeGroup();

			if(shapeGroup != null) {
				canvas.setCurrentShape(shapeGroup);
				shapeGroup.setP1(e.getX(), e.getY());
			}
		}

		if(e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			if(shapeGroup != null) {
				shapeGroup.setP2(e.getX(), e.getY());
				canvas.paint();
			}
		}

		if(e.getEventType() == MouseEvent.MOUSE_RELEASED) {

			shapeGroup.setP2(e.getX(), e.getY());
			canvas.paint();

			allShapes = canvas.getShapes();

			int i = 0;

			while (i < allShapes.size()) {

				MyShape curShape = allShapes.get(i);

				if(shapeGroup.within(curShape)) {

					shapeGroup.addMember(curShape);
					canvas.deleteShape(curShape);
				}
				else {
					i++;
				}
			}
			if(shapeGroup.size() > 0) {

				canvas.addShape(shapeGroup);
				canvas.setCurrentShape(null);
				canvas.addEdit(new GroupEdit(canvas, shapeGroup));
				canvas.paint();
				shapeGroup = null;
			}
		}
	}
}
