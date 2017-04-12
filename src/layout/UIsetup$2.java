package layout;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class UIsetup$2
		extends JPanel
{
	private static final long serialVersionUID = 1L;

	UIsetup$2(UIsetup paramUIsetup)
	{
	}

	public void paintComponent(Graphics g)
	{
		Image img = new ImageIcon(getClass().getResource("background1.jpg")).getImage();
		Dimension size = new Dimension((int) ((this.this$0.comp.getWidth() + 4.0D) * 5.2D), this.this$0.grid.getHeight() / 3);
		this.this$0.setSizes(this, size);
		g.drawImage(img, 0, 0, null);
	}
}

/*
 * Location: C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java
 * Game\bin\!\layout\UIsetup$2.class Java compiler version: 6 (50.0) JD-Core
 * Version: 0.7.1
 */