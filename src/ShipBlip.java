/*    */ package layout;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShipBlip
/*    */ {
/*    */   public int locx;
/*    */   
/*    */ 
/*    */   public int locy;
/*    */   
/*    */ 
/*    */   int Movx;
/*    */   
/*    */   int Movy;
/*    */   
/*    */ 
/*    */   public ShipBlip(int Posx, int Posy, int x, int y)
/*    */   {
/* 20 */     this.locx = Posx;
/* 21 */     this.locy = Posy;
/*    */     
/* 23 */     this.Movx = x;
/* 24 */     this.Movy = y;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getX()
/*    */   {
/* 32 */     return this.locx;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getY()
/*    */   {
/* 40 */     return this.locy;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void Move()
/*    */   {
/* 50 */     this.locx += this.Movx;
/* 51 */     this.locy += this.Movy;
/*    */   }
/*    */ }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\ShipBlip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */