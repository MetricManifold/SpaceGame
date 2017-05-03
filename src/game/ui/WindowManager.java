package game.ui;

import game.managers.PlanetManager;
import game.managers.PlayerManager;
import game.managers.TurnManager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WindowManager extends Application
{
	private PlanetManager pm;
	private TurnManager tm;
	private PlayerManager lm;

	public static final int PX = 30, PY = 30, SIZEX = 300, SIZEY = 250;
	public static final double D = 0.07;
	public static final String TITLE = "Starfare";
	public static Scene scene, setup;
	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		WindowManager.primaryStage = primaryStage;
		
		BorderPane border = new BorderPane(); //				layout the game in a border pane
		BorderPane border2 = new BorderPane();
		VBox vb = new VBox(); //								create the box for the grid and turnbar

		scene = new Scene(border); //							create the scene
		setup = new Scene(border2);
		
		vb.setAlignment(Pos.CENTER);
		vb.getStyleClass().add("vbox-main");
		scene.getStylesheets().add("elements.css"); //			set the style sheet for the scene

		lm = new PlayerManager(); //							creates the object managing players
		pm = new PlanetManager(); //							create the planet grid with the selected x, y and density
		tm = new TurnManager(); // 								create the turn bar to take input

		pm.setEvents(lm, tm);
		tm.setEvents(lm, pm);

		border.setCenter(vb);
		border.getStyleClass().add("scene");
		vb.getChildren().addAll(tm.turnBar, pm.tilePane);

		primaryStage.setTitle(TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();

		System.out.println("game started");
	}
	
	public void swapScene(Scene scene)
	{
		primaryStage.setScene(scene);
	}

	/**
	 * Program entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		launch(args);
	}

}
