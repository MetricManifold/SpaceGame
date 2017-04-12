package layout;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class UIsetup$3
		extends JPanel
{
	private static final long serialVersionUID = 1L;

	UIsetup$3(UIsetup paramUIsetup)
	{
	}

	public void paintComponent(Graphics g)
	{
		Image img = new ImageIcon(getClass().getResource("background2.jpg")).getImage();
		Dimension size = new Dimension(this.this$0.grid.getWidth(), (int) ((this.this$0.comp.getHeight() + 4.0D) * 3.5D));
		this.this$0.setSizes(this, size);
		g.drawImage(img, 0, 0, null);
	}
}

/*
 * Location: C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java
 * Game\bin\!\layout\UIsetup$3.class Java compiler version: 6 (50.0) JD-Core
 * Version: 0.7.1
 */