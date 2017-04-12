package layout;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WindowManager extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Button btn = new Button();
		btn.setText("Say 'Hello World'");

		btn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				System.out.println("Hello World!");
			}
		});

		BorderPane root = new BorderPane();
		root.getChildren().add(btn);

		Scene gameScene = new Scene(root, 300, 250);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(gameScene);
		primaryStage.show();
	}
	
	/**
	 * Program entry point
	 * @param args
	 */
	public static void main(String[] args)
	{
		//new Music_Track1();
		//Music_Track1.play();
		launch(args);
	}

}
