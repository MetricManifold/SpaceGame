package game.ui;

import java.util.Random;

import game.objects.Planet;
import javafx.scene.layout.TilePane;

public class PlanetGrid
{
	private static final int PADH = 5, PADV = 5;
	
	private TilePane tiles = new TilePane();
	private Random gen = new Random();
	
	private Planet[] planets;
	private int len;
	

	PlanetGrid(int x, int y, double density) {
		
		len = x * y;
		planets = new Planet[len];

		// set up planet grid ui
		tiles.setHgap(PADH);
		tiles.setVgap(PADV);
		tiles.setPrefColumns(x);
		
		// Create planets
		for (int i = 0; i < len; i++)
		{
			if (gen.nextDouble() <= density)
			{
				Planet p = new Planet(i % x, i / y);
				planets[i] = p;
				tiles.getChildren().add(p.button);
			}
			
		}
	}

	
	
}
