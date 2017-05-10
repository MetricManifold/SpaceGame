package game.ui;

import game.managers.*;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class WindowManager extends Application
{
	public static final String TITLE = "Starfare";
	public static Scene scene, setup;

	private PlanetManagerUI pm;
	private TurnManagerUI tm;
	private PlayerManager lm;

	private static final int SPLASH_WIDTH = 660, SPLASH_HEIGHT = 360, LOGO_WIDTH = SPLASH_WIDTH - 80;
	private static Stage primaryStage;
	private static BorderPane splashPane;

	@Override
	public void init()
	{
		ImageView splashImg = new ImageView(new Image("starfarelogo.png", LOGO_WIDTH, 0, true, true));
		splashImg.maxWidth(LOGO_WIDTH);
		splashImg.setEffect(new DropShadow());

		splashPane = new BorderPane();
		splashPane.setPrefHeight(SPLASH_HEIGHT);
		splashPane.setPrefWidth(SPLASH_WIDTH);
		splashPane.setCenter(splashImg);
		splashPane.setStyle("-fx-background-image: url(\"backgroundmain.jpg\"); -fx-background-size: cover;");
		splashPane.setEffect(new DropShadow());
	}

	@Override
	public void start(final Stage initStage) throws Exception
	{
		showSplash(initStage);

		PauseTransition pause = new PauseTransition(Duration.seconds(2));
		pause.setOnFinished(event -> {
			startGame();
			initStage.hide();
		});
		pause.play();
	}

	/**
	 * show the splash screen on startup
	 * 
	 * @param initStage
	 * @throws Exception
	 */
	private void showSplash(Stage initStage) throws Exception
	{
		Scene splash = new Scene(splashPane);
		initStage.initStyle(StageStyle.UNDECORATED);
		final Rectangle2D bounds = Screen.getPrimary().getBounds();

		initStage.setScene(splash);
		initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
		initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
		initStage.show();
	}

	/**
	 * begin the game
	 */
	private void startGame()
	{
		primaryStage = new Stage(StageStyle.DECORATED);

		BorderPane border = new BorderPane(); //				layout the game in a border pane
		BorderPane border2 = new BorderPane();
		VBox vb = new VBox(); //								create the box for the grid and turnbar

		scene = new Scene(border); //							create the scene
		setup = new Scene(border2);

		vb.setSpacing(3);
		vb.setAlignment(Pos.CENTER);
		vb.getStyleClass().add("vbox-main");
		scene.getStylesheets().add("elements.css"); //			set the style sheet for the scene

		lm = new PlayerManager(); //							creates the object managing players
		pm = new PlanetManagerUI(); //							create the planet grid with the selected x, y and density
		tm = new TurnManagerUI(); // 								create the turn bar to take input

		pm.setup(lm, tm);
		tm.setup(lm, pm);

		border.setCenter(vb);
		border.getStyleClass().add("scene");
		vb.getChildren().addAll(tm.getPane(), pm.getPane());

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

	/**
	 * swaps the current scene
	 * 
	 * @param scene
	 */
	public void swapScene(Scene scene)
	{
		primaryStage.setScene(scene);
	}

}
