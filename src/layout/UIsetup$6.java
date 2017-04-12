package layout;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class UIsetup$6
		extends JPanel
{
	private static final long serialVersionUID = 1L;

	UIsetup$6(UIsetup paramUIsetup)
	{
	}

	public void paintComponent(Graphics g)
	{
		Image img = new ImageIcon(getClass().getResource("outerspace.jpg")).getImage();
		g.drawImage(img, 0, 0, null);
	}
}

/*
 * Location: C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java
 * Game\bin\!\layout\UIsetup$6.class Java compiler version: 6 (50.0) JD-Core
 * Version: 0.7.1
 */