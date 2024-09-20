import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Class representing a canvas for drawing shapes.
 */
public class ShapeCanvas extends Canvas {

	private GraphicsContext gc;
	private ArrayList<MyShape> shapes;
	private MyShape currShape;
	private Color currColor;
	private boolean currFilled;
	private Stack<Edit> stackUndo;
	private Stack<Edit> stackRedo;

	private double width, height;

	/**
	 * Constructor for ShapeCanvas.
	 *
	 * @param w The width of the canvas.
	 * @param h The height of the canvas.
	 */
	public ShapeCanvas(double w, double h) {
		super(w, h);

		gc = getGraphicsContext2D();
		shapes = new ArrayList<>();
		currColor = Color.BLACK;
		currFilled = false;
		width = w;
		height = h;

		stackUndo = new Stack<>();
		stackRedo = new Stack<>();
	}

	/**
	 * Paints the canvas by drawing all shapes in the shapes ArrayList.
	 */
	public void paint() {
		gc.clearRect(0, 0, width, height);

		for(MyShape ms : shapes) {
			ms.draw(gc);
		}

		if(currShape != null) {
			currShape.draw(gc);
		}		
	}

	/**
	 * Adds a shape to the shapes ArrayList
	 *
	 * @param s The shape to be added.
	 */
	public void addShape(MyShape s) {
		shapes.add(s);
		paint();
	}

	public void setCurrColor(Color col) {
		currColor = col;
	}

	/**
	 * Sets the current shape that is being drawn.
	 *
	 * @param s The current shape.
	 */
	public void setCurrentShape(MyShape s) {

		currShape = s;

		if(s != null) {
			currShape.setColor(currColor);
			currShape.setFilled(currFilled);
		}
	}

	/**
	 * Sets whether the current shape is filled.
	 *
	 * @param isFilled True if the shape is filled, false otherwise.
	 */
	public void setCurrentFilled(boolean isFilled) {
		currFilled = isFilled;
	}

	/**
	 * Clears all shapes from the canvas and repaints it.
	 */
	public void clear() {
		shapes.clear();
		paint();
	}

	/**
	 * writes the shapes to a text file. Each shape is written in a format defined by its toString method.
	 *
	 * @param fileObj The file to write to.
	 */
	public void toTextFile(File fileObj) {
		try {
			PrintWriter fileOut = new PrintWriter(fileObj);

			fileOut.println(shapes.size());

			for(MyShape ms : shapes) {
				fileOut.println(ms.toString());
			}

			fileOut.close();
		}
		catch(FileNotFoundException e) {
			System.err.println("could not be opened for writing");
			e.printStackTrace();
		}
	}

	/**
	 * Reads shapes from a text file and adds them to the canvas. The file format must match that of toTextFile.
	 *
	 * @param fileObj The file to read from.
	 */
	public void fromTextFile(File fileObj) {
		try {
			Scanner fileIn = new Scanner(fileObj);

			clear();

			int nShapes = fileIn.nextInt();

			for(int i = 0; i < nShapes; i++) {

				String type = fileIn.next();

				MyShape shape = null;

				if(type.equalsIgnoreCase("shapegroup")) {
					shape = loadGroupText(fileIn);
				}
				else {
					shape = loadSingletonText(fileIn, type);
				}
				shapes.add(shape);
			}

			fileIn.close();
			paint();
		}
		catch(FileNotFoundException e) {
			System.err.println("could not be opened for reading.");
		}
	}

	/**
	 * loads and returns one shape of the shapes ArrayList
	 *
	 * @param fIn Scanner object for reading shape data.
	 * @param shapeType Type of shape to create ("line", "rect", "oval").
	 * @return Configured shape instance of {@code MyShape}.
	 */
	private MyShape loadSingletonText(Scanner fIn, String shapeType) {		

		double x1 = fIn.nextDouble();
		double y1 = fIn.nextDouble();
		double x2 = fIn.nextDouble();
		double y2 = fIn.nextDouble();

		double r = fIn.nextDouble();
		double g = fIn.nextDouble();
		double b = fIn.nextDouble();

		boolean filled = fIn.nextBoolean();
		Color color = Color.color(r, g, b);

		currShape = null;

		if(shapeType.equalsIgnoreCase("line")) {
			currShape = new Line(x1, y1, x2, y2);
		}
		else if(shapeType.equalsIgnoreCase("rect")) {
			currShape = new Rect(x1, y1, x2, y2);
		}
		else {
			currShape = new Oval(x1, y1, x2, y2);
		}

		currShape.setFilled(filled);
		currShape.setColor(Color.color(r, g, b));
		return currShape;
	}

	/**
	 *	loads and returns a group of shapes
	 * @param fIn The scanner to read shape configuration data.
	 * @return A fully configured ShapeGroup with all members set.
	 */
	private ShapeGroup loadGroupText(Scanner fIn) {

		int nShapes = fIn.nextInt();

		double x0 = fIn.nextDouble();
		double y0 = fIn.nextDouble();

		double x1 = fIn.nextDouble();
		double y1 = fIn.nextDouble();

		ShapeGroup shapeGroup = new ShapeGroup();
		shapeGroup.setP1(x0, y0);
		shapeGroup.setP2(x1, y1);

		MyShape shape = null;

		for(int i = 0; i < nShapes; i++) {

			String shapeType = fIn.next();

			if(shapeType.equalsIgnoreCase("shapegroup")) {
				shapeGroup = loadGroupText(fIn);
			}
			else {
				shape = loadSingletonText(fIn, shapeType);
			}

			shapeGroup.addMember(shape);
		}
		return shapeGroup;
	}

	/**
	 * writes the shapes to a binary file using serialization.
	 *
	 * @param fileObj The file to write to.
	 */
	public void toBinaryFile(File fileObj) {

		try {
			FileOutputStream fOS = new FileOutputStream(fileObj);
			ObjectOutputStream fOut = new ObjectOutputStream(fOS);

			fOut.writeInt(shapes.size());

			for(MyShape shape : shapes) {
				fOut.writeObject(shape);
			}

			fOut.close();
			fOS.close();
		}
		catch(IOException e) {
			System.err.println("could not be opened for writing.");
			e.printStackTrace();
		}
	}

	/**
	 * reads shapes from a binary file using deserialization and adds them to the canvas.
	 *
	 * @param fileObj The file to read from.
	 */
	public void fromBinaryFile(File fileObj) {

		try {
			FileInputStream fIS = new FileInputStream(fileObj);
			ObjectInputStream fIn = new ObjectInputStream(fIS);
			clear();

			int n = fIn.readInt();

			System.out.println(n);

			for(int i = 0; i < n; i++) {
				MyShape shape = (MyShape) fIn.readObject();
				shapes.add(shape);
			}

			fIn.close();
			fIS.close();

			paint();
		}
		catch(IOException e) {
			System.err.println("could not be opened for reading");
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			System.err.println("Class not found");
			e.printStackTrace();
		}
	}

	/**
	 * Finds and returns the shape closest to a specified point.
	 *
	 * Compares the distance from each shape's center to (x, y). If no shapes exist, returns null.
	 *
	 * @param x X-coordinate of the point.
	 * @param y Y-coordinate of the point.
	 * @return Closest shape or null if no shapes are available.
	 */
	public MyShape closestShape(double x, double y) {

		int minIndex = 0;

		if(shapes.size() > 0) {

			MyShape curShape = shapes.get(0);

			double minDistance = curShape.distance(x, y);

			for(int i = 1; i < shapes.size(); i++) {

				MyShape s = shapes.get(i);
				Point2D curCenter = s.getCetner();

				double distance = curCenter.distance(x, y);

				if(distance < minDistance) {
					minDistance = distance;
					minIndex = i;
				}
			}
		}

		else {
			return null;
		}

		return shapes.get(minIndex);
	}

	/**
	 * Removes a specified shape from the collection.
	 * 
	 * @param s The shape to be removed from the collection.
	 */
	public void deleteShape(MyShape s) {
		shapes.remove(s);
		//System.out.println(s.toString());
	}

	/**
	 * Retrieves the list of all shapes in the collection.
	 * 
	 * @return An ArrayList of shapes currently in the collection.
	 */
	public ArrayList<MyShape> getShapes() { 
		return shapes;
	}

	public void addEdit(Edit edit) {
		
		stackUndo.push(edit);
	}

	public void undo() {
		
		if(!stackUndo.empty()) {
			Edit curEdit = stackUndo.pop();
			curEdit.undo();

			stackRedo.push(curEdit);
		}
	}

	public void redo() {

		if(!stackRedo.empty()) {
			Edit curEdit = stackRedo.pop();
			curEdit.redo();

			stackUndo.push(curEdit);
		}
	}

	/**
	 * replaces the current mouse listener (press/release) and mouse motion listener (drag)
	 * with the passed listener object
	 *
	 * @param listener an EventHandler object 
	 */
	public void replaceMouseHandler(EventHandler<MouseEvent> listener) {
		setOnMousePressed(listener);
		setOnMouseDragged(listener);
		setOnMouseReleased(listener);
		setOnMouseClicked(listener);
	}
}