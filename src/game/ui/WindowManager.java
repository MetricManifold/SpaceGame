package game.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WindowManager extends Application
{
	HBox topBox = new HBox();
	PlanetGrid planets;
	TurnBar turn;

	public static final int PADX = 5, PADY = 5, 
			PX = 30, PY = 30,
			SIZEX = 300, SIZEY = 250;
	public static final double D = 0.07;
	public static final String TITLE = "Konquest II";

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		BorderPane border = new BorderPane();
		Scene scene = new Scene(border);
		
		scene.getStylesheets().add("elements.css");
		planets = new PlanetGrid(PX, PY, D);
		turn = new TurnBar();
		
		border.setCenter(planets.tilePane);
		border.setTop(turn.turnBar);
		border.setPadding(new Insets(PADY, PADX, PADY, PADX));
		
		primaryStage.setTitle(TITLE);
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
