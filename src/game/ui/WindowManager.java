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

	public static final int PADX = 5, PADY = 5, 
			PX = 10, PY = 10,
			SIZEX = 300, SIZEY = 250;
	public static final double D = 0.10;
	public static final String TITLE = "Konquest II";

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		planets = new PlanetGrid(PX, PY, D);

		BorderPane border = new BorderPane();
		border.setCenter(planets.tilePane);
		border.setPadding(new Insets(PADY, PADX, PADY, PADX));
		
		Scene scene = new Scene(border, SIZEX, SIZEY);

		primaryStage.setTitle(TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void makeEntryHead()
	{

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
