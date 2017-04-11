 package layout;
 
 import java.awt.Color;
 import javax.swing.BorderFactory;
 import javax.swing.UIDefaults;
 import javax.swing.border.Border;
 import javax.swing.plaf.ColorUIResource;
 import javax.swing.plaf.metal.MetalLookAndFeel;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 class GridManager$ToolTipLookAndFeel
   extends MetalLookAndFeel
 {
   private static final long serialVersionUID = 1L;
   
   GridManager$ToolTipLookAndFeel(GridManager paramGridManager) {}
   
   protected void initSystemColorDefaults(UIDefaults table)
   {
     super.initSystemColorDefaults(table);
     table.put("info", new ColorUIResource(200, 200, 200));
   }
   
   protected void initComponentDefaults(UIDefaults table) {
     super.initComponentDefaults(table);
     
     Border border = BorderFactory.createLineBorder(new Color(76, 79, 83));
     table.put("ToolTip.border", border);
   }
 }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\GridManager$ToolTipLookAndFeel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */