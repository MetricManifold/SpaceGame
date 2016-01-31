/*    */ package layout;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Menu$1
/*    */   extends JPanel
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   Menu$1(Menu paramMenu) {}
/*    */   
/*    */   public void paintComponent(Graphics g)
/*    */   {
/* 31 */     Image img = new ImageIcon("C:\\Users\\Zirconix\\Pictures\\Project pictures\\outerspace.jpg").getImage();
/* 32 */     Dimension size = new Dimension(400, 400);
/* 33 */     setPreferredSize(size);
/* 34 */     setMinimumSize(size);
/* 35 */     setMaximumSize(size);
/* 36 */     setSize(size);
/* 37 */     g.drawImage(img, 0, 0, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\Menu$1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */