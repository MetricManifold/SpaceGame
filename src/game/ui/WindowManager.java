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
	public static final String TITLE = "Konquest II";

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		javafx.scene.text.Font.getFamilies().forEach(f -> System.out.println(f));
		
		BorderPane border = new BorderPane(); //				layout the game in a border pane
		Scene scene = new Scene(border); //						create the scene
		VBox vb = new VBox(); //								create the box for the grid and turnbar

		vb.setAlignment(Pos.CENTER);
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
