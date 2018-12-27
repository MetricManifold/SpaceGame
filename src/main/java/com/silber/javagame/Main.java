package com.silber.javagame;

import com.silber.ui.WindowManager;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 *
 */
public class Main extends Application
{
	public static final String TITLE = "Starfare";

	@Override
	public void start(final Stage initStage)
	{
		WindowManager.startGame(initStage, TITLE);
	}

	@Override
	public void init()
	{
		WindowManager.setupSplash(TITLE);
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
