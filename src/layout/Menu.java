package layout;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends javax.swing.JFrame implements java.awt.event.ActionListener
{
	private static final long serialVersionUID = 1L;
	UIsetup u = TurnManager.u;
	static Help h = new Help();
	static Menu m = ToolBar.m;

	JButton Start = new JButton("Start");
	JButton Help = new JButton("Help");
	JPanel MenuSpace;
	JLabel Title = new JLabel("CONQUEST");

	public Menu()
	{
		this.MenuSpace = new JPanel()
		{
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g)
			{
				java.awt.Image img = new javax.swing.ImageIcon("C:\\Users\\Zirconix\\Pictures\\Project pictures\\outerspace.jpg").getImage();
				Dimension size = new Dimension(400, 400);
				setPreferredSize(size);
				setMinimumSize(size);
				setMaximumSize(size);
				setSize(size);
				g.drawImage(img, 0, 0, null);
			}

		};
		this.MenuSpace.setLayout(new java.awt.GridBagLayout());

		setSize(400, 400);

		java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();

		this.Start.addActionListener(this);
		this.Help.addActionListener(this);

		this.Title.setFont(new java.awt.Font("Ariel", 1, 58));
		this.Title.setForeground(java.awt.Color.cyan);

		c.anchor = 10;

		int p = 0;

		c.gridy = (p++);
		c.insets = new Insets(0, 10, 50, 10);
		this.MenuSpace.add(this.Title, c);

		c.gridy = (p++);
		c.insets = new Insets(0, 10, 0, 10);
		this.MenuSpace.add(this.Start, c);

		c.gridy = (p++);
		c.insets = new Insets(10, 30, 0, 30);
		this.MenuSpace.add(this.Help, c);

		add(this.MenuSpace);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		setLocation(x, y);

		setDefaultCloseOperation(3);
		setResizable(false);
		setTitle("Conquest");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Menu.class.getResource("planet1.png")));
	}

	public void actionPerformed(java.awt.event.ActionEvent e)
	{
		if (e.getSource() == this.Start)
		{
			dispose();
			new Initialization().startgame();

		}
		else
		{
			new Initialization().openhelp();
			new Help().menu = true;
		}
	}
}

/*
 * Location: C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java
 * Game\bin\!\layout\Menu.class Java compiler version: 6 (50.0) JD-Core Version:
 * 0.7.1
 */