/*    */ package layout;
/*    */ 
/*    */ public class Initialization
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/*  7 */     new Menu().setVisible(true);
/*  8 */     new Music_Track1();
/*  9 */     Music_Track1.play();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void startgame()
/*    */   {
/* 16 */     new GridManager().ClearGrid();
/* 17 */     new UIsetup().setVisible(true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void openmenu()
/*    */   {
/* 25 */     new Menu().setVisible(true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void openhelp()
/*    */   {
/* 32 */     new Help().setVisible(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\Initialization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */