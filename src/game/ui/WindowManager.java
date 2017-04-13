package game.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WindowManager extends Application
{
	HBox topBox = new HBox();
	PlanetGrid planets;

	public static final int PX = 10, PY = 10;
	public static final double D = 0.10;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Button btn = new Button();
		btn.setText("Say 'Hello World'");

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event)
			{
				System.out.println("Hello World!");
			}
		});

		BorderPane root = new BorderPane();
		root.setCenter(btn);

		Scene gameScene = new Scene(root, 300, 250);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(gameScene);
		primaryStage.show();
		
		planets = new PlanetGrid(PX, PY, D);
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
