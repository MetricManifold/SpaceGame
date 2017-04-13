package game.ui;

import java.util.concurrent.ThreadLocalRandom;

import game.objects.Planet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.TilePane;

public class PlanetGrid
{
	// static values for ui
	private static final int PADH = 1, PADV = 1, TILEH = 30, TILEV = 30, NUM_P_BG = 4;
	private static final String[] pf = new String[] { "planet1.png", "planet2.png", "planet3.png", "planet4.png" };
	private static final String bf = "background3.jpg";

	private Background[] pbg = new Background[NUM_P_BG];
	private Background bg;

	// planets and corresponding buttons
	private Planet[] planets;
	private Button[] buttons;
	private int len;

	// ui tiles
	public TilePane tilePane = new TilePane(Orientation.HORIZONTAL);

	PlanetGrid(int x, int y, double density)
	{
		makeBackgrounds();

		len = x * y;
		planets = new Planet[len];
		buttons = new Button[len];

		// set up planet grid ui
		tilePane.setHgap(PADH);
		tilePane.setVgap(PADV);
		tilePane.setPrefColumns(x);
		tilePane.setPrefTileWidth(TILEH);
		tilePane.setPrefTileHeight(TILEV);
		tilePane.setBackground(bg);

		// Create planets
		for (int i = 0; i < len; i++)
		{
			if (ThreadLocalRandom.current().nextDouble() <= density)
			{
				// create the necessary elements
				Button b = new Button();
				Planet p = new Planet(i % x, i / y);

				// modify button to correct size and action
				b.setMinSize(TILEH, TILEV);
				b.setMaxSize(TILEH, TILEV);
				b.setBackground(pbg[ThreadLocalRandom.current().nextInt(NUM_P_BG)]);
				b.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						System.out.println("Hello World!");
					}
				});

				planets[i] = p;
				buttons[i] = b;

				// add the button to the tilepane
				tilePane.getChildren().add(b);
			}

		}
	}

	/**
	 * Create the list of backgrounds used to fill the planets
	 */
	void makeBackgrounds()
	{
		// creation of backgrounds for planets
		for (int i = 0; i < NUM_P_BG; i++)
		{
			Image im = new Image(pf[i], TILEH, TILEV, true, true);
			pbg[i] = new Background(
					new BackgroundImage(
							im, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
							BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
		}

		// main tilepane background creation
		Image im = new Image(bf, TILEH, TILEV, true, true);
		bg = new Background(
				new BackgroundImage(
						im, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
	}

}
