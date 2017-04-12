package layout;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class UIsetup$4 extends JPanel
{
	private static final long serialVersionUID = 1L;

	UIsetup$4(UIsetup paramUIsetup)
	{
	}

	public void paintComponent(Graphics g)
	{
		Image img = new ImageIcon(getClass().getResource("background1.jpg")).getImage();
		Dimension size = new Dimension(this.this$0.status.getWidth(), (int) ((this.this$0.comp.getHeight() + 4.0D) * 3.5D));
		this.this$0.setSizes(this, size);
		g.drawImage(img, 0, 0, null);
	}
}
