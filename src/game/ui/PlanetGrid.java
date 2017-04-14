package game.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import game.objects.Planet;
import game.objects.Space;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

public class PlanetGrid
{
	// static values for ui
	private static final int PADH = 8, PADV = 8, TILEH = 30, TILEV = 30, NUM_P_BG = 4, NUM_B_BG = 4, MARGIN = 5;

	// planets and corresponding buttons
	private Space[] tiles;
	private Button[] buttons;
	private int x, y, len;
	private double density;

	public TilePane tilePane = new TilePane(Orientation.HORIZONTAL);
	public List<Planet> planets = new ArrayList<Planet>();

	PlanetGrid(int x, int y, double density)
	{
		// initialize local variables
		this.x = x;
		this.y = y;
		this.density = density;

		len = x * y;
		tiles = new Space[len];
		buttons = new Button[len];

		makeTilePaneUI();
		makePlanets();

	}

	/**
	 * puts planets in the grid and associates them with buttons
	 */
	void makePlanets()
	{
		for (int i = 0; i < len; i++)
		{
			// create the necessary elements
			Button b = new Button();
			Space s = new Space(i % x, i / y);
			tilePane.getChildren().add(b);

			// modify button to correct size and action
			b.setMinSize(TILEH, TILEV);
			b.setMaxSize(TILEH, TILEV);

			// choose whether to create a planet or empty space
			double prob = ThreadLocalRandom.current().nextDouble();
			if (prob < density)
			{
				// set the button background
				String bgSelect = "planet-button" + String.valueOf(ThreadLocalRandom.current().nextInt(NUM_P_BG) + 1);
				b.getStyleClass().add(bgSelect);
				b.getStyleClass().add("space-button");
								
				// set the planet click event
				b.setOnMouseClicked(new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent event)
					{
						System.out.println("Hello World!");
					}
				});
				
				Planet p = new Planet(s);
				tiles[i] = p;
				planets.add(p);
				buttons[i] = b;
			}
			else
			{
				b.getStyleClass().add("space-button");
				tiles[i] = s;
			}
		}
	}

	/**
	 * Sets the properties related to tilepane UI
	 */
	void makeTilePaneUI()
	{
		tilePane.setHgap(PADH);
		tilePane.setVgap(PADV);
		
		tilePane.setPrefTileWidth(TILEH);
		tilePane.setPrefTileHeight(TILEV);
		
		tilePane.setPrefColumns(x);
		tilePane.setPrefRows(y);
		
		tilePane.setMaxSize((TILEH + PADH) * x + MARGIN * 2, (TILEV + PADV) * y + MARGIN * 2);
		tilePane.setPadding(new Insets(MARGIN, MARGIN, MARGIN, MARGIN));
		tilePane.setAlignment(Pos.CENTER);

		String bgSelect = "planet-grid" + String.valueOf(ThreadLocalRandom.current().nextInt(NUM_B_BG) + 1);
		tilePane.getStyleClass().add(bgSelect);
		
	}

}
