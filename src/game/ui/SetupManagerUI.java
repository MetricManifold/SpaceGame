package game.ui;

import game.managers.ConfigManager;
import game.managers.PlayerManager;
import game.managers.SetupManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
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
	private BorderPane mainPane = new BorderPane();
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
	private Spinner<Double> tfNpm = new Spinner<>(),
		tfPdb = new Spinner<>();
	private Spinner<Integer> tfIpr = new Spinner<>(),
		tfSsc = new Spinner<>();
	private RadioButton rbNsv = new RadioButton();
	private ObservableList<String> cbDshOptions = FXCollections.observableArrayList("Destroyer", "Fighter", "Bomber");
	private ComboBox<String> cbDsh = new ComboBox<>(cbDshOptions);

	public SetupManagerUI()
	{
		CM = new ConfigManager();
		PM = new PlayerManager(CM);
		PG = new PlanetManagerUI(CM);
		TM = new TurnManagerUI(CM);

		makeUI();
	}

	public void makeUI()
	{
		mainPane.setCenter(tabPane);
		mainPane.setRight(gridSettings);

		gridSettings.setTop(gridFields);
		gridSettings.setCenter(gridVisual);
		gridFields.getChildren().addAll(labelGx, sGx, labelGy, sGy, labelPd, sPd);
		gridVisual = getPlanetManager().getMiniPane();

		playerTab.setContent(playerSettings);
		playerTab.setClosable(false);
		playerTab.setText("players");
		playerSettings.getChildren().add(playerView);
		playerView.setItems(playerList);

		advancedTab.setContent(advancedSettings);
		playerTab.setClosable(false);
		playerTab.setText("advanced");

		advancedSettings.getChildren().addAll(advancedColLeft, advancedColRight);
		advancedColLeft.getChildren().addAll(labelNpm, labelPdb, labelNsv, labelDsh, labelIpr, labelSsc);
		advancedColRight.getChildren().addAll(tfNpm, tfPdb, rbNsv, cbDsh, tfIpr, tfSsc);

		labelGx.setText("x");
		labelGy.setText("y");
		labelPd.setText("density");
		labelNpm.setText("neutral production modifier");
		labelPdb.setText("planet defender bonus");
		labelNsv.setText("neutral ships visible");
		labelDsh.setText("default ship");
		labelIpr.setText("initial production");
		labelSsc.setText("ship start count");

		SpinnerValueFactory<Integer> factoryGx = new SpinnerValueFactory.IntegerSpinnerValueFactory(
			CM.minGridX, CM.maxGridX, CM.defaultGridX);
		SpinnerValueFactory<Integer> factoryGy = new SpinnerValueFactory.IntegerSpinnerValueFactory(
			CM.minGridY, CM.maxGridY, CM.defaultGridY);
		SpinnerValueFactory<Double> factoryPd = new SpinnerValueFactory.DoubleSpinnerValueFactory(
			0.0, 1.0, CM.planetDensity, 0.05);

		//Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL

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

		tfNpm.setValueFactory(factoryNpm);
		tfPdb.setValueFactory(factoryPdb);
		tfIpr.setValueFactory(factoryIpr);
		tfSsc.setValueFactory(factorySsc);
		rbNsv.setSelected(CM.neutralShipsVisible);
		cbDsh.setValue(cbDshOptions.get(0));
	}

	@Override
	public void setup()
	{
		super.setup();
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

}
