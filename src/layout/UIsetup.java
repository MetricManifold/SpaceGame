 package layout;
 
 import java.awt.Dimension;
 import java.awt.Graphics;
 import java.awt.Image;
 import javax.swing.ImageIcon;
 import javax.swing.JPanel;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 class UIsetup$1
   extends JPanel
 {
   private static final long serialVersionUID = 1L;
   
   UIsetup$1(UIsetup paramUIsetup) {}
   
   public void paintComponent(Graphics g)
   {
     Image img = new ImageIcon(getClass().getResource("outerspace.jpg")).getImage();
     Dimension size = new Dimension((int)((this.this$0.comp.getWidth() + 4.0D) * 22.7D), (int)((this.this$0.comp.getHeight() + 4.0D) * 21.6D));
     this.this$0.setSizes(this, size);
     g.drawImage(img, 0, 3, null);
   }
 }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\UIsetup$1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */