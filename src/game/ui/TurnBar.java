package game.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TurnBar
{
	public HBox turnBar = new HBox();
	private Button btnNextTurn = new Button();
	private Button btnSendShips = new Button();
	private TextField entShipNum = new TextField();
	
	public TurnBar()
	{
		turnBar.setAlignment(Pos.CENTER_LEFT);
		turnBar.setSpacing(10.0);
		
		btnNextTurn.setText("Next Turn");
		btnSendShips.setText("Send");
		
		turnBar.getChildren().addAll(entShipNum, btnSendShips, btnNextTurn);
		
		System.out.println("finished toolbar");
	}
	
	public void sendShips()
	{
		
	}
}
