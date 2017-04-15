package game.managers;

import java.util.ArrayList;
import java.util.List;

import game.objects.Fleet;
import game.objects.Planet;
import game.players.Player;
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

	private List<Fleet> fleets = new ArrayList<Fleet>();

	public TurnManager()
	{
		btnNextTurn.setText("Next Turn");
		btnSendShips.setText("Send");
		enableSend(false);

		// set the turnbar UI
		turnBar.getStyleClass().add("turn-bar");
		turnBar.setAlignment(Pos.CENTER_LEFT);
		turnBar.setSpacing((float) SPACING);
		turnBar.getChildren().addAll(tfShipNum, btnSendShips, btnNextTurn);

		System.out.println("finished toolbar");
	}
	
	public void setEvents(PlayerManager pm, PlanetManager pg)
	{
		turnBar.setMaxWidth(pg.maxh);
		
		btnNextTurn.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				nextTurn(pm, pg);
			}

		});

		btnSendShips.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				int num = Integer.valueOf(tfShipNum.getText());
				Player p = pm.getCurrentPlayer();
				
				if (pm.canSend(p, num))
				{
					try
					{
						sendShips(p, num);
						enableSend(false);
						tfShipNum.setText("");
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
				// check if the value does not exceed ships to send
				if (!newValue.isEmpty())
				{
					int num = Integer.valueOf(newValue);
					if (!pm.canSend(pm.getCurrentPlayer(), num)) btnSendShips.setDisable(true);
					else btnSendShips.setDisable(false);
				}
				else
				{
					btnSendShips.setDisable(false);
				}
				
				// ensure only numbers are added to the text field
				if (!newValue.matches("\\d*"))
				{
					tfShipNum.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	/**
	 * activate the next turn
	 */
	public void nextTurn(PlayerManager pm, PlanetManager pg)
	{
		for (Planet p : pg.planets.values())
		{
			p.produceShips();
		}
		

		for (Fleet f : fleets)
		{
			f.update();
		}
		
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
	
	public void enableSend(boolean b)
	{
		tfShipNum.setDisable(!b);
		btnSendShips.setDisable(!b);
	}

	/**
	 * send the given number of ships
	 * 
	 * @param num
	 * @throws Exception
	 */
	public void sendShips(Player p, int num) throws Exception
	{
		Planet o = p.getOrigin();
		Planet d = p.getDestination();
		Fleet f = (Fleet) o.getShipInventory().take(PlayerManager.defaultShip, num);

		f.send(o, d);
		fleets.add(f);
	}
}
