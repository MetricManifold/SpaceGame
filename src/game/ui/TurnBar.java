package game.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TurnBar
{
	public HBox turnBar = new HBox();
	public Button btnNextTurn = new Button();
	public TextField entShipNum = new TextField();
	
	public TurnBar()
	{
		turnBar.setAlignment(Pos.CENTER_LEFT);
		btnNextTurn.setText("Next Turn");
		
		
	}
}
