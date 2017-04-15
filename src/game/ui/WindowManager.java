package game.ui;

import game.managers.PlanetManager;
import game.managers.PlayerManager;
import game.managers.TurnManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WindowManager extends Application
{
	private PlanetManager planets;
	private TurnManager turnBar;
	private PlayerManager playerManager;

	public static final int PADX = 5, PADY = 5,
			PX = 30, PY = 30,
			SIZEX = 300, SIZEY = 250;
	public static final double D = 0.07;
	public static final String TITLE = "Konquest II";

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		BorderPane border = new BorderPane(); //				layout the game in a border pane
		Scene scene = new Scene(border); //						create the scene
		VBox vb = new VBox(); //								create the box for the grid and turnbar

		vb.setAlignment(Pos.CENTER);
		scene.getStylesheets().add("elements.css"); //			set the style sheet for the scene

		playerManager = new PlayerManager(); //					creates the object managing players
		planets = new PlanetManager(PX, PY, D); //				create the planet grid with the selected x, y and density
		turnBar = new TurnManager(playerManager, planets); // 	create the turn bar to take input

		border.setCenter(vb); // 								add the elements to the scene
		vb.getChildren().addAll(turnBar.turnBar, planets.tilePane);
		border.setPadding(new Insets(PADY, PADX, PADY, PADX));

		primaryStage.setTitle(TITLE); //						set the title and scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Program entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		// new Music_Track1();
		// Music_Track1.play();
		launch(args);
	}

}
