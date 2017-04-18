package game.managers;

import java.util.ArrayList;
import java.util.List;

import game.groups.Fleet;
import game.players.Player;
import game.tiles.Planet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Responsible for managing turns including updating planet production, fleets sent to other planets Also contains the graphical element that
 * 
 * @author MR SMITH
 *
 */
public class TurnManager
{
	public static final int SPACING = 10, LBL_WIDTH = 80;

	public HBox turnBar = new HBox();
	private Button btnNextTurn = new Button();
	private Button btnSendShips = new Button();
	private TextField tfShipNum = new TextField();
	private Label lblPlayer = new Label();

	private List<Fleet> fleets = new ArrayList<Fleet>();

	public TurnManager()
	{
		enableSend(false);
		makeHBoxUI();

		turnBar.getChildren().addAll(tfShipNum, btnSendShips, btnNextTurn, lblPlayer);

		System.out.println("finished toolbar");
	}

	/**
	 * Create the UI for the turn bar
	 */
	public void makeHBoxUI()
	{
		// set text and disable sending
		btnNextTurn.setText("Next Turn");
		btnSendShips.setText("Send");

		turnBar.getStyleClass().add("turn-bar");
		turnBar.setAlignment(Pos.CENTER_LEFT);
		turnBar.setSpacing((float) SPACING);

		lblPlayer.getStyleClass().add("player-label");
		lblPlayer.setMinWidth(LBL_WIDTH);
	}

	public void setEvents(PlayerManager pm, PlanetManager pg)
	{
		turnBar.setMaxWidth(pg.getSizeX());
		lblPlayer.setText(pm.getCurrentPlayer().getName());
		lblPlayer.getStyleClass().add(pm.getCurrentPlayer().getColor());

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

				// ensure only numbers are added to the text field
				if (!newValue.matches("\\d*"))
				{
					tfShipNum.setText(newValue.replaceAll("[^\\d]", ""));
				}

				// check if the value does not exceed ships to send
				if (!tfShipNum.getText().isEmpty())
				{
					int num = Integer.valueOf(newValue);
					if (!pm.canSend(pm.getCurrentPlayer(), num))
						btnSendShips.setDisable(true);
					else btnSendShips.setDisable(false);
				}
				else
				{
					btnSendShips.setDisable(false);
				}
			}
		});
	}

	/**
	 * activate the next turn
	 */
	public void nextTurn(PlayerManager pm, PlanetManager pg)
	{
		// switch player label
		lblPlayer.getStyleClass().remove(pm.getCurrentPlayer().getColor());
		pm.nextPlayer();
		lblPlayer.setText(pm.getCurrentPlayer().getName());
		lblPlayer.getStyleClass().add(pm.getCurrentPlayer().getColor());

		// produce ships if all players have made their turn
		if (pm.getPlayer(1) == pm.getCurrentPlayer())
		{

			for (Planet p : pg.getPlanetArray())
			{
				// if owner is neutral, produce 75% total ships
				if (p.getOwner() == pm.neutral)
				{
					p.addShips(ConfigurationManager.defaultShip, (int) (p.getProduction() * ConfigurationManager.neutralProdModifier));
				}
				else
				{
					p.produceShips();
				}
			}

			for (Fleet f : fleets)
			{
				f.update();
			}
		}

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
	 * turn the send button on or off
	 * 
	 * @param b
	 */
	public void enableSend(boolean b)
	{
		tfShipNum.setDisable(!b);
		tfShipNum.clear();
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
		Fleet f = new Fleet(o.getShipInventory().take(ConfigurationManager.defaultShip, num));

		o.updateToolTip();
		f.send(o, d);
		fleets.add(f);
	}
}
