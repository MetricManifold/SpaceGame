 package layout;
 
 
 
 public class ShipBlip
 {
   public int locx;
   
 
   public int locy;
   
 
   int Movx;
   
   int Movy;
   
 
   public ShipBlip(int Posx, int Posy, int x, int y)
   {
     this.locx = Posx;
     this.locy = Posy;
     
     this.Movx = x;
     this.Movy = y;
   }
   
 
 
 
   public int getX()
   {
     return this.locx;
   }
   
 
 
 
   public int getY()
   {
     return this.locy;
   }
   
 
 
 
 
 
   public void Move()
   {
     this.locx += this.Movx;
     this.locy += this.Movy;
   }
 }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\ShipBlip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */