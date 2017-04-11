 package layout;
 
 public class Initialization
 {
   public static void main(String[] args)
   {
     new Menu().setVisible(true);
     new Music_Track1();
     Music_Track1.play();
   }
   
 
 
   public void startgame()
   {
     new GridManager().ClearGrid();
     new UIsetup().setVisible(true);
   }
   
 
 
 
   public void openmenu()
   {
     new Menu().setVisible(true);
   }
   
 
 
   public void openhelp()
   {
     new Help().setVisible(true);
   }
 }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\Initialization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */