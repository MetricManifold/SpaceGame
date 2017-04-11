 package layout;
 
 import java.awt.BorderLayout;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.JButton;
 import javax.swing.JPanel;
 import javax.swing.JTextArea;
 import javax.swing.JToolBar;
 
 
 
 public class ToolBar
   extends JPanel
   implements ActionListener
 {
   private static final long serialVersionUID = 1L;
   static Menu m = new Menu();
   
   protected JTextArea textArea;
   
   protected String newline = "\n";
   static final String MENU = "main menu";
   static final String HELP = "help";
   JToolBar toolBar = new JToolBar("toolbar");
   
   JButton Help = new JButton("Help");
   
   public ToolBar() {
     super(new BorderLayout());
     
 
     addButtons(this.toolBar);
     this.toolBar.setFloatable(false);
     
 
 
     this.textArea = new JTextArea(4, 30);
     this.textArea.setEditable(false);
   }
   
   protected void addButtons(JToolBar toolBar)
   {
     JButton button = null;
     
 
     button = makeNavigationButton("Back24", "help", 
       "Go to Help", 
       "Help");
     
 
     toolBar.add(button);
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
   protected JButton makeNavigationButton(String imageName, String actionCommand, String toolTipText, String altText)
   {
     JButton button = new JButton();
     button.setActionCommand(actionCommand);
     button.setToolTipText(toolTipText);
     button.addActionListener(this);
     
     button.setText(altText);
     
     return button;
   }
   
   public void actionPerformed(ActionEvent e)
   {
     String cmd = e.getActionCommand();
     
 
     if ("help".equals(cmd)) {
       new Help().game = true;
       new Initialization().openhelp();
     }
   }
 }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\ToolBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */