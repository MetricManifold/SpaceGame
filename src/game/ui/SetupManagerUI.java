package game.ui;

import java.util.ArrayList;
import game.managers.ConfigManager;
import game.managers.PlayerManager;
import game.managers.PlayerManager.Controller;
import game.managers.SetupManager;
import game.managers.ConfigManager.ShipType;
import game.players.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SetupManagerUI extends SetupManager
{
	private BorderPane rootPane;
	private BorderPane setupPane = new BorderPane();
	private VBox gamePane = new VBox();
	private TabPane tabPane = new TabPane();

	/*
	 * pane for holding the grid and grid settings
	 */
	private BorderPane gridSettings = new BorderPane();
	private HBox gridFields = new HBox();
	private Label labelGx = new Label(),
		labelGy = new Label(),
		labelPd = new Label();
	private Spinner<Integer> sGx = new Spinner<>(),
		sGy = new Spinner<>();
	private Spinner<Double> sPd = new Spinner<>();
	private Pane gridVisual;

	/*
	 * tab for modifying player settings
	 */
	private Tab playerTab = new Tab();
	private VBox playerSettings = new VBox();
	private ListView<Node> playerView = new ListView<>();
	private ObservableList<Node> playerList;

	/*
	 * tab for modifying advanced settings
	 */
	private Tab advancedTab = new Tab();
	private HBox advancedSettings = new HBox();
	private VBox advancedColLeft = new VBox(),
		advancedColRight = new VBox();
	private Label labelNpm = new Label(),
		labelPdb = new Label(),
		labelNsv = new Label(),
		labelDsh = new Label(),
		labelIpr = new Label(),
		labelSsc = new Label();
	private Spinner<Double> sNpm = new Spinner<>(),
		sPdb = new Spinner<>();
	private Spinner<Integer> sIpr = new Spinner<>(),
		sSsc = new Spinner<>();
	private RadioButton rbNsv = new RadioButton();
	private ObservableList<ShipType> cbDshOptions = FXCollections.observableArrayList(ShipType.DESTROYER, ShipType.FIGHTER, ShipType.BOMBER);
	private ComboBox<ShipType> cbDsh = new ComboBox<>(cbDshOptions);

	private static final int HEIGHT = 30, SPACING = 10, PADDING = 10;

	/*
	 * start button
	 */
	private VBox bottomBox = new VBox();
	private Button btnStart = new Button("Start");

	public SetupManagerUI(BorderPane rootPane)
	{
		CM = new ConfigManager();

		this.rootPane = rootPane;
		makeUI();
	}

	public void makeUI()
	{
		Insets pad = new Insets(PADDING, PADDING, PADDING, PADDING);
		
		setupPane.setLeft(tabPane);
		setupPane.setRight(gridSettings);
		setupPane.setBottom(bottomBox);
		setupPane.setPadding(pad);

		tabPane.setPadding(pad);
		gridSettings.setPadding(pad);
		bottomBox.setPadding(pad);
		
		btnStart.setOnMouseClicked(e -> startGame());
		btnStart.setMinHeight(HEIGHT);
		bottomBox.getChildren().add(btnStart);
		
		tabPane.getTabs().addAll(playerTab, advancedTab);
		tabPane.getStyleClass().add("tab-pane");

		gridSettings.setTop(gridFields);
		gridSettings.setCenter(gridVisual);
		gridSettings.getStyleClass().add("grid-settings");
		gridFields.getChildren().addAll(labelGx, sGx, labelGy, sGy, labelPd, sPd);
		gridFields.setSpacing(SPACING);

		playerTab.setContent(playerSettings);
		playerTab.setClosable(false);
		playerTab.setText("players");
		playerSettings.getChildren().add(playerView);
		playerView.setItems(playerList);

		advancedTab.setContent(advancedSettings);
		advancedTab.setClosable(false);
		advancedTab.setText("advanced");

		advancedSettings.getChildren().addAll(advancedColLeft, advancedColRight);
		advancedSettings.setSpacing(SPACING);
		advancedSettings.getStyleClass().add("advanced-settings");
		
		advancedColLeft.getChildren().addAll(labelNpm, labelPdb, labelNsv, labelDsh, labelIpr, labelSsc);
		advancedColRight.getChildren().addAll(sNpm, sPdb, rbNsv, cbDsh, sIpr, sSsc);
		advancedColLeft.setSpacing(SPACING);
		advancedColRight.setSpacing(SPACING);

		labelGx.setText("x");
		labelGy.setText("y");
		labelPd.setText("density");
		labelNpm.setText("neutral production modifier");
		labelPdb.setText("planet defender bonus");
		labelNsv.setText("neutral ships visible");
		labelDsh.setText("default ship");
		labelIpr.setText("initial production");
		labelSsc.setText("ship start count");

		Control[] sizingElements = new Control[] {
			labelNpm, labelPdb, labelNsv, labelDsh, labelIpr,
			labelSsc, sNpm, sPdb, sIpr, sSsc, rbNsv, cbDsh,
			labelGx, labelGy, labelPd, sGx, sGy, sPd };

		for (Control n : sizingElements)
		{
			n.setMinHeight(HEIGHT);
			n.setMaxHeight(HEIGHT);
		}

		Control[] spinnerElements = new Control[] {
			sGx, sGy, sPd, sNpm, sPdb, sIpr, sSsc };

		for (Control n : spinnerElements)
		{
			n.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		}

		SpinnerValueFactory<Integer> factoryGx = new SpinnerValueFactory.IntegerSpinnerValueFactory(
			CM.minGridX, CM.maxGridX, CM.gridX);
		SpinnerValueFactory<Integer> factoryGy = new SpinnerValueFactory.IntegerSpinnerValueFactory(
			CM.minGridY, CM.maxGridY, CM.gridY);
		SpinnerValueFactory<Double> factoryPd = new SpinnerValueFactory.DoubleSpinnerValueFactory(
			0.0, 1.0, CM.planetDensity, 0.05);

		sGx.setValueFactory(factoryGx);
		sGy.setValueFactory(factoryGy);
		sPd.setValueFactory(factoryPd);

		SpinnerValueFactory<Double> factoryNpm = new SpinnerValueFactory.DoubleSpinnerValueFactory(
			CM.cminNeutralProdModifier, CM.cmaxNeutralProdModifier, CM.neutralProdModifier, 0.05);
		SpinnerValueFactory<Double> factoryPdb = new SpinnerValueFactory.DoubleSpinnerValueFactory(
			CM.cminPlanetDefenderBonus, CM.cmaxPlanetDefenderBonus, CM.planetDefenderBonus, 0.05);
		SpinnerValueFactory<Integer> factoryIpr = new SpinnerValueFactory.IntegerSpinnerValueFactory(
			0, CM.cmaxProduction, CM.initialProduction);
		SpinnerValueFactory<Integer> factorySsc = new SpinnerValueFactory.IntegerSpinnerValueFactory(
			0, CM.cmaxShipStartCount, CM.shipStartCount);

		sNpm.setValueFactory(factoryNpm);
		sPdb.setValueFactory(factoryPdb);
		sIpr.setValueFactory(factoryIpr);
		sSsc.setValueFactory(factorySsc);
		rbNsv.setSelected(CM.neutralShipsVisible);
		cbDsh.setValue(CM.defaultShip);

		gamePane.setSpacing(3);
		gamePane.setAlignment(Pos.CENTER);
		gamePane.getStyleClass().add("vbox-main");

		rootPane.getStyleClass().add("scene");
	}

	@Override
	public void setup()
	{
		PM = new PlayerManager(CM);
		PG = new PlanetManagerUI(CM);
		TM = new TurnManagerUI(CM);
		
		PG.setup(PM, TM);

		playerList = FXCollections.observableList(new ArrayList<Node>());
		rootPane.setCenter(setupPane);
		gridVisual = getPlanetManager().getMiniPane();

		populatePlayers();
	}

	@Override
	public PlanetManagerUI getPlanetManager()
	{
		return (PlanetManagerUI) PG;
	}

	@Override
	public TurnManagerUI getTurnManager()
	{
		return (TurnManagerUI) TM;
	}

	public Pane getGamePane()
	{
		return gamePane;
	}

	public Pane getSetupPane()
	{
		return setupPane;
	}

	@Override
	public void startGame()
	{
		CM.gridX = sGx.getValue();
		CM.gridY = sGy.getValue();
		CM.planetDensity = sPd.getValue();
		CM.neutralProdModifier = sNpm.getValue();
		CM.planetDefenderBonus = sPdb.getValue();
		CM.initialProduction = sIpr.getValue();
		CM.shipStartCount = sSsc.getValue();
		CM.neutralShipsVisible = rbNsv.isSelected();
		CM.defaultShip = cbDsh.getValue();

		super.startGame();
		
		gamePane.getChildren().addAll(getTurnManager().getPane(), getPlanetManager().getPane());
		rootPane.setCenter(gamePane);
	}

	@Override
	public void populatePlayers()
	{
		playerList.clear();
		
		super.populatePlayers();
		for (Player p : PM.getPlayersOfType(Controller.HUMAN))
		{
			Label lbl = new Label(p.getName());
			playerList.add(lbl);
		}
	}

}
