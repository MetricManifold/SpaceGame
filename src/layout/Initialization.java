package layout;

public class Initialization
{
	public static void main(String[] args)
	{
		new Menu().setVisible(true);
		new Music_Track1();
		Music_Track1.play();
	}

	public void startgame()
	{
		new GridManager().ClearGrid();
		new UIsetup().setVisible(true);
	}

	public void openmenu()
	{
		new Menu().setVisible(true);
	}

	public void openhelp()
	{
		//new Help().setVisible(true);
	}
}
