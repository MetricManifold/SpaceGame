package game.managers;

import java.util.ArrayList;
import java.util.List;

import game.objects.Fleet;
import game.objects.Planet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Responsible for managing turns including updating planet production, fleets sent to other planets
 * Also contains the graphical element that 
 * 
 * @author MR SMITH
 *
 */
public class TurnManager
{
	public static final int SPACING = 10;
	
	public HBox turnBar = new HBox();
	private Button btnNextTurn = new Button();
	private Button btnSendShips = new Button();
	private TextField tfShipNum = new TextField();

	private PlayerManager pm;
	private PlanetManager pg;
	private List<Fleet> fleets = new ArrayList<Fleet>();

	public TurnManager(PlayerManager pm, PlanetManager pg)
	{
		this.pm = pm;

		
		btnNextTurn.setText("Next Turn");
		btnSendShips.setText("Send");

		btnNextTurn.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				nextTurn();
			}

		});

		btnSendShips.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				int num = Integer.valueOf(tfShipNum.getText());
				if (pm.canSend(num))
				{
					try
					{
						sendShips(num);
					}
					catch (NumberFormatException e)
					{
						e.printStackTrace();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

			}

		});

		tfShipNum.textProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				if (!newValue.matches("\\d*"))
				{
					tfShipNum.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		// set the turnbar UI
		turnBar.setMaxWidth(pg.maxh);
		turnBar.setAlignment(Pos.CENTER);
		turnBar.setSpacing((float) SPACING);
		turnBar.getChildren().addAll(tfShipNum, btnSendShips, btnNextTurn);

		System.out.println("finished toolbar");
	}

	/**
	 * for each planet in the planet grid, update the ship inventory
	 */
	private void updateInventories()
	{
		for (Planet p : pg.planets.values())
		{
			p.produceShips();
		}
	}

	/**
	 * update each of the fleets on the map
	 */
	private void moveShips()
	{
		for (Fleet f : fleets)
		{
			f.update();
		}
	}

	/**
	 * activate the next turn
	 */
	public void nextTurn()
	{
		updateInventories();
		moveShips();
		pm.nextPlayer();
	}

	/**
	 * return the list containing the fleets
	 * 
	 * @return
	 */
	public List<Fleet> getFleets()
	{
		return fleets;
	}

	/**
	 * send the given number of ships
	 * 
	 * @param num
	 * @throws Exception
	 */
	public void sendShips(int num) throws Exception
	{
		Planet o = pm.getCurrentPlayer().getOrigin();
		Planet d = pm.getCurrentPlayer().getDestination();
		Fleet f = (Fleet) o.getShipInventory().take(pm.defaultShip, num);

		f.send(o, d);
		fleets.add(f);
	}
}
