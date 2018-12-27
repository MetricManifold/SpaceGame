package com.silber.ui;

import javafx.animation.PauseTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class WindowManager
{
	private static SetupManagerUI SM;
	
	// splash screen dimensions, conforming to given picture
	private static final int SPLASH_WIDTH = 660;
	private static final int SPLASH_HEIGHT = 360;
	private static final int LOGO_WIDTH = SPLASH_WIDTH - 80;
	
	private static String splashStyle = "-fx-background-image: url(\"backgroundmain.jpg\"); -fx-background-size: cover;";
	private static String splashImgFile = "starfarelogo.png";

	// scenes for the splash and main screens
	private static Scene scene;
	private static Scene splash;

	// stage for the main screen
	private static Stage primaryStage;
	private static BorderPane mainPane;

	// screen dimensions
	final static Rectangle2D bounds = Screen.getPrimary().getBounds();

	/**
	 * begin the game
	 */
	public static void startGame(Stage initStage, String title)
	{
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		
		/*
		 * show the splash screen
		 */
		initStage.initStyle(StageStyle.UNDECORATED);
		initStage.setTitle(title);
		initStage.setScene(splash);
		initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
		initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
		initStage.show();

		/*
		 * start the game
		 */

		primaryStage = new Stage(StageStyle.DECORATED);
		mainPane = new BorderPane();
		SM = new SetupManagerUI(mainPane);
		
		scene = new Scene(mainPane);
		scene.getStylesheets().add("elements.css");
		
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);

		SM.setup();
		System.out.println("game started");

		// after the given pause time, start the game		
		pause.setOnFinished(event -> {
			initStage.hide();
			primaryStage.show();
		});
		pause.play();
	}


	/**
	 * show the splash screen on startup, static method called on application initialization
	 * 
	 * @param initStage
	 */
	public static void showSplash(String title)
	{
		BorderPane splashPane;
		ImageView splashImg;
		
		// set up the splash pane image
		splashImg = new ImageView(
			new Image(splashImgFile, LOGO_WIDTH, 0, true, true)
			);

		splashImg.maxWidth(LOGO_WIDTH);
		splashImg.setEffect(new DropShadow());

		// set up the splash pane itself and give it appropriate style
		splashPane = new BorderPane();
		splashPane.setPrefHeight(SPLASH_HEIGHT);
		splashPane.setPrefWidth(SPLASH_WIDTH);
		splashPane.setCenter(splashImg);
		splashPane.setStyle(splashStyle);
		splashPane.setEffect(new DropShadow());

		splash = new Scene(splashPane);
	}
	
	/**
	 * 
	 */
	public static Scene getScene()
	{
		return scene;
	}
	
}
