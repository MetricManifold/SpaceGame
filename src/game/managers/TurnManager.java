package game.managers;

import java.util.ArrayList;
import java.util.List;
import game.groups.Fleet;
import game.groups.ShipGroup;
import game.groups.ShipInventory;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private Tooltip ttPlayer = new Tooltip();
	private HBox rightAlignBox = new HBox();
	private Text gameTimeText = new Text();
	private TextFlow gameTime = new TextFlow();
	private int totalSeconds = 0;
	private String timeFormat = "%02d : %02d";

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
		gameTime.getStyleClass().add("timer");

		lblPlayer.getStyleClass().add("player-label");
		lblPlayer.setMinWidth(LBL_WIDTH);
		lblPlayer.setTooltip(ttPlayer);
		
		ttPlayer.getStyleClass().add("tooltip-list");
		tfShipNum.setMaxWidth(FIELD_WIDTH);

		rightAlignBox.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(rightAlignBox, Priority.ALWAYS);
	}

	/**
	 * set the events when interacting with the turnbar
	 * 
	 * @param pm
	 * @param pg
	 */
	public void setEvents(PlayerManager pm, PlanetManager pg)
	{
		turnBar.setMaxWidth(pg.getSizeX());
		updatePlayerLabel(pm);

		Timeline oneSecond = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				updateTime();
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
				catch (Exception e)
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
				try
				{
					sendShips(pm, pg);
				}
				catch (Exception e)
				{
					e.printStackTrace();
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

				shipNumFieldHandle(pm);
			}
		});

		tfShipNum.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent ke)
			{
				if (ke.getCode().equals(KeyCode.ENTER))
				{
					try
					{
						sendShips(pm, pg);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * activate the next turn
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public void nextTurn(PlayerManager pm, PlanetManager pg) throws InstantiationException, IllegalAccessException
	{
		// clear selection and set label
		pg.clearSelection(pm.getCurrentPlayer().getOrigin(), pm.getCurrentPlayer().getDestination());
		lblPlayer.getStyleClass().remove(pm.getCurrentPlayer().getColor());
		pm.nextPlayer();

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

			// update all the fleets
			for (Fleet f : fleets)
			{
				f.update(pg);
			}

			fleets.removeIf(f -> f.getCount() == 0);
		}
		updatePlayerLabel(pm);
	}
	
	private void updatePlayerLabel(PlayerManager pm)
	{
		lblPlayer.setText(pm.getCurrentPlayer().getName());
		lblPlayer.getStyleClass().add(pm.getCurrentPlayer().getColor());
		
		StringBuilder b = new StringBuilder("Owned Planets");
		for (Planet p : pm.getCurrentPlayer().getPlanets())
		{
			b.append(String.format("\n%-20s%d", p.getName(), p.getShipInventory().getCount()));
		}
		ttPlayer.setText(b.toString());
	}

	private void updateTime()
	{
		totalSeconds++;
		int minutes = (totalSeconds % 3600) / 60;
		int seconds = totalSeconds % 60;

		String time = String.format(timeFormat, minutes, seconds);
		gameTimeText.setText(time);
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
		tfShipNum.requestFocus();
		btnSendShips.setDisable(!b);
	}

	/**
	 * send the given number of ships
	 * 
	 * @param num
	 * @throws Exception
	 */
	public void sendShips(PlayerManager pm, PlanetManager pg) throws Exception
	{
		if (!tfShipNum.getText().isEmpty())
		{
			int num = Integer.valueOf(tfShipNum.getText());
			Player p = pm.getCurrentPlayer();
			Fleet f = new Fleet(ConfigurationManager.defaultShip, num, p);

			if (pm.canSend(p, f))
			{
				try
				{
					Planet o = p.getOrigin();
					Planet d = p.getDestination();

					o.getShipInventory().remove(f);
					o.updateToolTip();
					f.send(o, d);
					fleets.add(f);

					enableSend(false);
					pg.clearSelection(pm.getCurrentPlayer().getOrigin(), pm.getCurrentPlayer().getDestination());
					pm.getCurrentPlayer().clearSelection();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		updatePlayerLabel(pm);
	}

	public void shipNumFieldHandle(PlayerManager pm)
	{
		// check if the value does not exceed ships to send
		if (!tfShipNum.getText().isEmpty())
		{
			// create a new fleet to send
			int num = Integer.valueOf(tfShipNum.getText());
			ShipGroup f = new ShipInventory(ConfigurationManager.defaultShip, num);
			btnSendShips.setDisable(!pm.canSend(pm.getCurrentPlayer(), f));
		}
		else
		{
			btnSendShips.setDisable(false);
		}
	}
}
