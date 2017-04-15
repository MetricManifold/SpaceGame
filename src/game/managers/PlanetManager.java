package game.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import game.objects.Planet;
import game.objects.Space;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class PlanetManager
{
	// static values for ui
	public static final int PADH = 8, PADV = 8,
			TILEH = 20, TILEV = 20, 
			NUM_P_BG = 4, NUM_B_BG = 4,
			MARGIN = 5, TOPMARGIN = 10;

	// planets and corresponding buttons
	private Space[] tiles;
	private int x, y, len;
	private double density;
	//private BuildManager bm = new BuildManager();
	
	public int maxh, maxv;
	public TilePane tilePane = new TilePane(Orientation.HORIZONTAL);
	public Map<Integer, Planet> planets = new HashMap<Integer, Planet>();

	public PlanetManager(int x, int y, double density)
	{
		// initialize local variables
		this.x = x;
		this.y = y;
		this.density = density;
		this.maxh = (TILEH + PADH) * x + MARGIN * 2;
		this.maxv = (TILEV + PADV) * y + MARGIN * 2;

		len = x * y;
		tiles = new Space[len];

		makeTilePaneUI(); // 		setup tilepane UI
		makePlanets(); // 			create the planets on this grid

		System.out.println("finished grid");
	}

	/**
	 * Sets the mouse event associated with the tilepane to find all the grid locations
	 */
	public void setMouseEvent(PlayerManager pm)
	{
		tilePane.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				double sx = (event.getX() - MARGIN) / (TILEH + PADH); // 	normalized selected x
				double sy = (event.getY() - MARGIN) / (TILEV + PADV); // 	normalized selected y
				double dx = sx - (int) sx; //							fractional distance x
				double dy = sy - (int) sy; // 							fractional distance y

				// check if selection is before padding
				if (dx < PADH / (TILEH / PADH) && dy < PADV / (TILEV / PADV))
				{
					Planet p = planets.get(hashLocation((int) sx, (int) sy));
					pm.getCurrentPlayer().clickTile(p);
				}
				else
				{
					pm.getCurrentPlayer().clearSelection();
				}
			}
		});

		System.out.println("set mouse event");
	}

	/**
	 * puts planets in the grid and associates them with buttons
	 */
	void makePlanets()
	{
		for (int i = 0; i < len; i++)
		{
			// create the necessary elements
			Label l = new Label();
			Space s = new Space(i % x, i / y);

			// modify button to correct size and action
			l.setMinSize(TILEH, TILEV);
			l.setMaxSize(TILEH, TILEV);

			tilePane.getChildren().add(l);

			// choose whether to create a planet or empty space
			double prob = ThreadLocalRandom.current().nextDouble();
			if (prob < density)
			{
				String bgSelect = "planet-button" + String.valueOf(ThreadLocalRandom.current().nextInt(NUM_P_BG) + 1);
				l.getStyleClass().addAll(bgSelect, "space-button");

				Planet p = new Planet(s);
				tiles[i] = p;
				planets.put(hashLocation(i % x, i / y), p);
			}
			else
			{
				l.getStyleClass().add("space-button");
				tiles[i] = s;
			}
		}

		System.out.println("placed planets in grid");
	}

	/**
	 * Sets the properties related to tilepane UI
	 */
	void makeTilePaneUI()
	{
		VBox.setMargin(tilePane, new Insets(TOPMARGIN, 0, 0, 0));

		tilePane.setHgap(PADH);
		tilePane.setVgap(PADV);

		tilePane.setPrefTileWidth(TILEH);
		tilePane.setPrefTileHeight(TILEV);

		tilePane.setPrefColumns(x);
		tilePane.setPrefRows(y);

		tilePane.setMaxSize(maxh, maxv);
		tilePane.setPadding(new Insets(MARGIN, MARGIN, MARGIN, MARGIN));
		tilePane.setAlignment(Pos.CENTER);

		String bgSelect = "planet-grid" + String.valueOf(ThreadLocalRandom.current().nextInt(NUM_B_BG) + 1);
		tilePane.getStyleClass().addAll(bgSelect, "planet-grid");

		System.out.println("created grid ui");
	}

	/**
	 * Creates a unique integer associated with the provided 2d location.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	int hashLocation(int x, int y)
	{
		return x + this.x * y;
	}

}
