import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Main application class for the shape editor.
 */
public class ShapeEditor extends Application{

	// constants
	private static final int APP_WIDTH = 800;
	private static final int APP_HEIGHT = 750;
	private static final int CANVAS_WIDTH = 800;
	private static final int CANVAS_HEIGHT = 660;
	private static final int CNTL_HEIGHT = 7;

	// GUI-variables
	private BorderPane mainPane;
	private ShapeCanvas canvas;
	private HBox controlPanel;
	private Button bnClear, bnUndo, bnRedo;
	private CheckBox cbFilled;
	private RadioButton rbLine, rbRect, rbOval, rbDelete, rbMove, rbCopy, rbGroup;
	private LineHandler lineHandler;
	private OvalHandler ovalHandler;
	private RectHandler rectHandler;
	private DeleteHandler deleteHandler;
	private MoveHandler moveHandler;
	private CopyHandler copyHandler;
	private GroupHandler groupHandler;
	private MenuBar menuBar;
	private Menu menuFile, menuAbout;
	private MenuItem miOpen, miSave, miOpenB, miSaveB;
	private FileChooser fcOpen, fcSave;
	private ColorPicker colorPicker;
	
    /**
     * Starts the application and sets up the stage.
     *
     * @param stage The primary stage for this application.
     */
	@Override
	public void start(Stage stage) {

		mainPane = new BorderPane();
		
		setupCanvas();
		setupControls();
		setupMenu();
		
		mainPane.setCenter(canvas);
		mainPane.setTop(controlPanel);

		Scene scene = new Scene(mainPane, APP_WIDTH, APP_HEIGHT);

		stage.setScene(scene);
		stage.setTitle("ShapeApp");

		stage.show();
	}
	
    /**
     * Sets up the canvas for drawing shapes.
     */
	public void setupCanvas() {

		canvas = new ShapeCanvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	}
	
    /**
     * Sets up the controls for the application.
     */
	public void setupControls() {

		controlPanel = new HBox(CNTL_HEIGHT);
		
		colorPicker = new ColorPicker(Color.BLACK);

		bnClear = new Button("Clear");
		bnUndo  = new Button("Undo");
		bnRedo  = new Button("Redo");

		cbFilled = new CheckBox("Filled");
		cbFilled.setSelected(false);

		rbLine = new RadioButton("Line");
		rbRect = new RadioButton("Rectangle");
		rbOval = new RadioButton("Oval");
		rbDelete = new RadioButton("Delete");
		rbMove = new RadioButton("Move");
		rbCopy = new RadioButton("Copy");
		rbGroup = new RadioButton("Group");
		rbLine.setSelected(true);

		ToggleGroup group = new ToggleGroup();
		rbLine.setToggleGroup(group);
		rbRect.setToggleGroup(group);
		rbOval.setToggleGroup(group);
		rbDelete.setToggleGroup(group);
		rbMove.setToggleGroup(group);
		rbCopy.setToggleGroup(group);
		rbGroup.setToggleGroup(group);

		lineHandler = new LineHandler(canvas);
		rectHandler = new RectHandler(canvas);
		ovalHandler = new OvalHandler(canvas);
		deleteHandler = new DeleteHandler(canvas);
		moveHandler = new MoveHandler(canvas);
		copyHandler = new CopyHandler(canvas);
		groupHandler = new GroupHandler(canvas);

		bnClear.setOnAction(e->{
			canvas.clear();
			canvas.paint();
		});
		
		bnUndo.setOnAction(e->{
			canvas.undo();
		});
		
		bnRedo.setOnAction(e->{
			canvas.redo();
		});
		
		canvas.replaceMouseHandler(lineHandler);

		rbRect.setOnAction(e->{
			canvas.replaceMouseHandler(rectHandler);
		});

		rbOval.setOnAction(e->{
			canvas.replaceMouseHandler(ovalHandler);
		});
		
		rbLine.setOnAction(e-> {
			canvas.replaceMouseHandler(lineHandler);
		});
		
		rbDelete.setOnAction(e->{
			canvas.replaceMouseHandler(deleteHandler);
		});
		
		rbMove.setOnAction(e->{
			canvas.replaceMouseHandler(moveHandler);
		});
		
		rbCopy.setOnAction(e->{
			canvas.replaceMouseHandler(copyHandler);
		});
		
		rbGroup.setOnAction(e ->{
			canvas.replaceMouseHandler(groupHandler);
		});
		
		cbFilled.setOnAction(e->{
			canvas.setCurrentFilled(cbFilled.isSelected());
		});
		
		colorPicker.setOnAction(e->{
			canvas.setCurrColor(colorPicker.getValue());
		});
		
		controlPanel.getChildren().addAll(rbLine, rbRect, rbOval, rbDelete, rbMove, rbCopy, rbGroup, cbFilled, colorPicker, bnClear, bnUndo, bnRedo);
	}
	
	/**
	 * Sets up the menu bar with its menus and menu items.
	 * The menu bar includes a "File" menu and an "About" menu.
	 * The "File" menu contains options for opening and saving files in both text and binary formats.
	 */
	public void setupMenu() {
		
		menuBar = new MenuBar();
		
		menuFile = new Menu("File");
		menuAbout = new Menu("About");
		
		miOpen = new MenuItem("Open");
		miSave = new MenuItem("Save");
		miOpenB = new MenuItem("Open Binary");
		miSaveB = new MenuItem("Save Binary");
		
		fcSave = new FileChooser();
		fcOpen = new FileChooser();
		
		miSave.setOnAction(e->{
			File newFile = fcSave.showSaveDialog(null);
			
			if(newFile != null) {
				canvas.toTextFile(newFile);
			}
		});
		
		miOpen.setOnAction(e->{
			File openFile = fcOpen.showOpenDialog(null);
			
			if(openFile != null) {
				canvas.fromTextFile(openFile);
			}
		});
		
		miSaveB.setOnAction(e->{
			fcSave.setTitle("Save drawing as: ");
			
			File newFile = fcSave.showSaveDialog(null);
			
			if(newFile != null) {
				canvas.toBinaryFile(newFile);
			}
		});
		
		miOpenB.setOnAction(e->{
			File newFile = fcOpen.showOpenDialog(null);
			
			if(newFile != null) {
				
				canvas.fromBinaryFile(newFile);
			}
		});
		
		menuBar.getMenus().addAll(menuFile, menuAbout);
		
		menuFile.getItems().addAll(miOpen, miSave, miOpenB, miSaveB);
		
		mainPane.setBottom(menuBar);
	}
	
    /**
     * Main method to launch the application.
     *
     * @param args Command line arguments.
     */
	public static void main(String[] args) {
		launch(args);
	}
}
