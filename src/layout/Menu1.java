package layout;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class Menu1
		extends JPanel
{
	private static final long serialVersionUID = 1L;

	Menu1(Menu paramMenu)
	{
	}

	public void paintComponent(Graphics g)
	{
		Image img = new ImageIcon("C:\\Users\\Zirconix\\Pictures\\Project pictures\\outerspace.jpg").getImage();
		Dimension size = new Dimension(400, 400);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		g.drawImage(img, 0, 0, null);
	}
}

/*
 * Location: C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java
 * Game\bin\!\layout\Menu$1.class Java compiler version: 6 (50.0) JD-Core
 * Version: 0.7.1
 */