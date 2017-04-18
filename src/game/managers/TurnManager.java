package game.managers;

import java.util.ArrayList;
import java.util.List;

import game.groups.Fleet;
import game.players.Player;
import game.tiles.Planet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 * Responsible for managing turns including updating planet production, fleets sent to other planets Also contains the graphical element that
 * 
 * @author MR SMITH
 *
 */
public class TurnManager
{
	public static final int SPACING = 10, LBL_WIDTH = 80, FIELD_WIDTH = 60;

	public HBox turnBar = new HBox();
	private Button btnNextTurn = new Button();
	private Button btnSendShips = new Button();
	private TextField tfShipNum = new TextField();
	private Label lblPlayer = new Label();
	private HBox rightAlignBox = new HBox();
	private Text gameTimeText = new Text();
	private TextFlow gameTime = new TextFlow();
	private int totalSeconds = 0;

	private List<Fleet> fleets = new ArrayList<Fleet>();

	public TurnManager()
	{
		turnBar.getChildren().addAll(tfShipNum, btnSendShips, btnNextTurn, lblPlayer, rightAlignBox);
		rightAlignBox.getChildren().addAll(gameTime);

		enableSend(false);
		makeHBoxUI();

		System.out.println("finished toolbar");
	}

	/**
	 * Create the UI for the turn bar
	 */
	public void makeHBoxUI()
	{
		// set text and disable sending		
		btnNextTurn.setText("Next Turn");
		btnNextTurn.getStyleClass().add("turn-bar-button");

		btnSendShips.setText("Send");
		btnSendShips.getStyleClass().add("turn-bar-button");

		turnBar.getStyleClass().add("turn-bar");
		turnBar.setAlignment(Pos.CENTER_LEFT);
		turnBar.setSpacing((float) SPACING);

		gameTime.setTextAlignment(TextAlignment.CENTER);
		gameTime.getChildren().add(gameTimeText);
		gameTime.getStyleClass().add("player-label");

		lblPlayer.getStyleClass().add("player-label");
		lblPlayer.setMinWidth(LBL_WIDTH);

		tfShipNum.setMaxWidth(FIELD_WIDTH);

		rightAlignBox.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(rightAlignBox, Priority.ALWAYS);
	}

	public void setEvents(PlayerManager pm, PlanetManager pg)
	{
		turnBar.setMaxWidth(pg.getSizeX());
		lblPlayer.setText(pm.getCurrentPlayer().getName());
		lblPlayer.getStyleClass().add(pm.getCurrentPlayer().getColor());

		Timeline oneSecond = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				totalSeconds++;
				int hours = totalSeconds / 3600;
				int minutes = (totalSeconds % 3600) / 60;
				int seconds = totalSeconds % 60;

				String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
				gameTimeText.setText(time);
			}
		}));
		oneSecond.setCycleCount(Timeline.INDEFINITE);
		oneSecond.play();

		btnNextTurn.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				try
				{
					nextTurn(pm, pg);
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}

		});

		btnSendShips.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (tfShipNum.getText().isEmpty()) return;

				int num = Integer.valueOf(tfShipNum.getText());
				Player p = pm.getCurrentPlayer();

				if (pm.canSend(p, num))
				{
					try
					{
						sendShips(p, num);
						enableSend(false);
						pg.clearSelection(pm.getCurrentPlayer().getOrigin(), pm.getCurrentPlayer().getDestination());
						pm.getCurrentPlayer().clearSelection();
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
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void nextTurn(PlayerManager pm, PlanetManager pg) throws InstantiationException, IllegalAccessException
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
				f.update(pg);
				if (f.getCount() <= 0) fleets.remove(f);
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
		Fleet f = new Fleet(o.getShipInventory().take(ConfigurationManager.defaultShip, num), p);

		o.updateToolTip();
		f.send(o, d);
		fleets.add(f);
	}
}
