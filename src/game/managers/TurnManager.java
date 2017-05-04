package game.managers;

import java.util.ArrayList;
import java.util.Collections;
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
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 * Responsible for managing turns including updating planet production, fleets sent to other planets Also contains the graphical element
 * 
 * @author MR SMITH
 *
 */
public class TurnManager
{
	private static final int SPACING = 10, LBL_WIDTH = 80, FIELD_WIDTH = 60, PLAYER_LIST_WIDTH = 150;

	private HBox turnBar = new HBox();

	/*
	 * elements associated with ship sending and next turn
	 */
	private Label lblNextTurn = new Label();
	private Label lblSendShips = new Label();
	private TextField tfShipNum = new TextField();
	private Label lblPlayer = new Label();

	/*
	 * elements associated with the player label and planet list
	 */
	private Tooltip ttPlayer = new Tooltip();
	private BorderPane tblPlayer = new BorderPane();
	private boolean lblClick = false;
	private VBox v1 = new VBox();
	private VBox v2 = new VBox();
	private VBox v3 = new VBox();
	private int sortColumn = 1;

	/*
	 * elements associated with the game time
	 */
	private HBox rightAlignBox = new HBox();
	private Text gameTimeText = new Text();
	private TextFlow gameTime = new TextFlow();
	private int totalSeconds = 0;
	private String timeFormat = "%02d : %02d";

	/*
	 * managers used by this manager
	 */
	private PlanetManager PG;
	private PlayerManager PM;

	private List<Fleet> fleets = new ArrayList<Fleet>();

	public TurnManager()
	{
		turnBar.getChildren().addAll(tfShipNum, lblSendShips, lblNextTurn, lblPlayer, rightAlignBox);
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
		lblNextTurn.setText("NEXT TURN");
		lblNextTurn.getStyleClass().add("turn-bar-button");

		lblSendShips.setText("SEND");
		lblSendShips.getStyleClass().add("turn-bar-button");

		turnBar.getStyleClass().add("turn-bar");
		turnBar.setSpacing((float) SPACING);

		gameTime.getChildren().add(gameTimeText);
		gameTime.getStyleClass().add("timer");

		lblPlayer.getStyleClass().add("player-label");
		lblPlayer.setMinWidth(LBL_WIDTH);

		tfShipNum.setMaxWidth(FIELD_WIDTH);
		tfShipNum.getStyleClass().add("turn-bar-entry");

		rightAlignBox.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(rightAlignBox, Priority.ALWAYS);

		ttPlayer.setGraphic(tblPlayer);
		tblPlayer.setPrefWidth(PLAYER_LIST_WIDTH);

		v1.setAlignment(Pos.CENTER_LEFT);
		v2.setAlignment(Pos.CENTER_RIGHT);
		v3.setAlignment(Pos.CENTER_RIGHT);
		v2.setPadding(new Insets(0, 10, 0, 10));

		Text[] titles = new Text[3];
		int i = 0;
		for (String t : "Planet Industry Ships".split(" "))
		{
			titles[i] = new Text(t);
			titles[i].getStyleClass().add("tooltip-list-title");
			tooltipSortHandle(titles[i], i);
			i++;
		}

		v1.getChildren().add(titles[0]);
		v2.getChildren().add(titles[1]);
		v3.getChildren().add(titles[2]);

		tblPlayer.setLeft(v1);
		tblPlayer.setCenter(v2);
		tblPlayer.setRight(v3);

	}

	/**
	 * set the events when interacting with the turnbar
	 * 
	 * @param pm
	 * @param pg
	 */
	public void setEvents(PlayerManager pm, PlanetManager pg)
	{
		PM = pm;
		PG = pg;

		turnBar.setMinWidth(pg.getDefSizeX());
		turnBar.setPrefWidth(pg.getSizeX());
		turnBar.setMaxWidth(pg.getSizeX());
		updatePlayerLabel();

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

		lblNextTurn.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				try
				{
					nextTurn();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

		});

		lblSendShips.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				try
				{
					sendShips();
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
				if (!newValue.matches("\\d*")) tfShipNum.setText(newValue.replaceAll("[^\\d]", ""));
				shipNumFieldHandle();
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
						sendShips();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});

		lblPlayer.setOnMouseEntered(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (!lblClick)
				{
					Point2D pnt = lblPlayer.localToScreen(lblPlayer.getLayoutBounds().getMaxX(), lblPlayer.getLayoutBounds().getMaxY());
					ttPlayer.show(lblPlayer, pnt.getX(), pnt.getY());
				}
			}
		});

		lblPlayer.setOnMouseExited(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (!lblClick) ttPlayer.hide();
			}
		});

		lblPlayer.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				lblClick = !lblClick;
				if (!lblClick) ttPlayer.hide();
			}
		});
		
	}

	/**
	 * activate the next turn
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public void nextTurn()
	{
		PG.clearSelection(PM.getCurrentPlayer().getOrigin(), PM.getCurrentPlayer().getDestination());
		PM.nextPlayer();

		// produce ships if all players have made their turn
		if (PM.getPlayer(1) == PM.getCurrentPlayer())
		{
			for (Planet p : PG.getPlanetArray())
			{
				// if owner is neutral, produce 75% total ships
				if (p.getOwner() == PM.neutral)
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
				try
				{
					f.update(PG);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			fleets.removeIf(f -> f.getCount() == 0);
		}

		updatePlayerLabel();
		enableSend(false);

		lblClick = false;
		ttPlayer.hide();
		PM.getCurrentPlayer().update(PG, this, PM);
	}

	/**
	 * handle behaviour of textfield
	 * 
	 * @param PM
	 */
	public void shipNumFieldHandle()
	{
		// check if the value does not exceed ships to send
		if (!tfShipNum.getText().isEmpty())
		{
			// create a new fleet to send
			int num = Integer.valueOf(tfShipNum.getText());
			ShipGroup f = new ShipInventory(ConfigurationManager.defaultShip, num);

			if (!PM.canSend(PM.getCurrentPlayer(), f))
			{
				tfShipNum.setText(String.valueOf(PM.getCurrentPlayer().getOrigin().getShipInventory().getCount()));
			}
		}
	}

	/**
	 * update the label that shows the current player and the tooltip
	 * 
	 * @param PM
	 */
	private void updatePlayerLabel()
	{
		if (lblPlayer.getStyleClass().size() > 2)
		{
			lblPlayer.getStyleClass().remove(2);
		}

		lblPlayer.getStyleClass().add(PM.getCurrentPlayer().getColor());
		lblPlayer.setText(PM.getCurrentPlayer().getName());
		
		updatePlayerPlanetList();
	}
	
	private void updatePlayerPlanetList()
	{
		v1.getChildren().remove(1, v1.getChildren().size());
		v2.getChildren().remove(1, v2.getChildren().size());
		v3.getChildren().remove(1, v3.getChildren().size());

		List<Planet> planets = PM.getCurrentPlayer().getPlanets();

		int sortColumnAbs = Math.abs(sortColumn);
		int sortColumnSign = sortColumn / sortColumnAbs;

		switch (sortColumnAbs)
		{
		case 1:
			Collections.sort(planets, (a, b) -> sortColumnSign * a.getName().compareToIgnoreCase(b.getName()));
		case 2:
			Collections.sort(planets, (a, b) -> sortColumnSign * (a.getProduction() - b.getProduction()));
		case 3:
			Collections.sort(planets, (a, b) -> sortColumnSign * (a.getShipInventory().getCount() - b.getShipInventory().getCount()));
		}

		for (Planet p : planets)
		{
			Text n = new Text(p.getName());
			Text c = new Text(String.valueOf(p.getProduction()));
			Text t = new Text(String.valueOf(p.getShipInventory().getCount()));

			n.getStyleClass().add("tooltip-list");
			c.getStyleClass().add("tooltip-list-production");
			t.getStyleClass().add("tooltip-list-production");

			v1.getChildren().add(n);
			v2.getChildren().add(c);
			v3.getChildren().add(t);

			setListTextHover(n, p);
		}
	}

	/**
	 * highlights the planet when name is hovered in the list
	 * 
	 * @param t
	 * @param p
	 * @param PG
	 */
	private void setListTextHover(Text t, Planet p)
	{
		t.setOnMouseEntered(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				PG.getTiles().get(p).getStyleClass().add("space-button-illuminate");
			}
		});

		t.setOnMouseExited(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				PG.getTiles().get(p).getStyleClass().remove("space-button-illuminate");
			}
		});
	}

	/**
	 * clicking the title will reset the list and sort it according to title clicked
	 * 
	 * @param text
	 * @param i
	 */
	private void tooltipSortHandle(Text text, int i)
	{
		text.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (Math.abs(sortColumn) == i + 1)
				{
					sortColumn = -sortColumn;
				}
				else
				{
					sortColumn = i + 1;
				}
				
				updatePlayerPlanetList();
			}
		});

	}

	/**
	 * updates the time shown on the screen every second
	 */
	private void updateTime()
	{
		totalSeconds++;
		int minutes = (totalSeconds % 3600) / 60;
		int seconds = totalSeconds % 60;

		String time = String.format(timeFormat, minutes, seconds);
		gameTimeText.setText("");
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
		lblSendShips.setDisable(!b);
	}

	/**
	 * send the given number of ships
	 * 
	 * @param num
	 * @throws Exception
	 */
	public void sendShips() throws Exception
	{
		if (!tfShipNum.getText().isEmpty())
		{
			int num = Integer.valueOf(tfShipNum.getText());
			Player p = PM.getCurrentPlayer();
			Fleet f = new Fleet(ConfigurationManager.defaultShip, num, p);

			if (PM.canSend(p, f))
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
					PG.clearSelection(PM.getCurrentPlayer().getOrigin(), PM.getCurrentPlayer().getDestination());
					PM.getCurrentPlayer().clearSelection();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		updatePlayerLabel();
	}

	public Pane getPane()
	{
		return turnBar;
	}

}
