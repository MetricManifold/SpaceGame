/*    */ package layout;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextArea;
/*    */ import javax.swing.JToolBar;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ToolBar
/*    */   extends JPanel
/*    */   implements ActionListener
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 18 */   static Menu m = new Menu();
/*    */   
/*    */   protected JTextArea textArea;
/*    */   
/* 22 */   protected String newline = "\n";
/*    */   static final String MENU = "main menu";
/*    */   static final String HELP = "help";
/* 25 */   JToolBar toolBar = new JToolBar("toolbar");
/*    */   
/* 27 */   JButton Help = new JButton("Help");
/*    */   
/*    */   public ToolBar() {
/* 30 */     super(new BorderLayout());
/*    */     
/*    */ 
/* 33 */     addButtons(this.toolBar);
/* 34 */     this.toolBar.setFloatable(false);
/*    */     
/*    */ 
/*    */ 
/* 38 */     this.textArea = new JTextArea(4, 30);
/* 39 */     this.textArea.setEditable(false);
/*    */   }
/*    */   
/*    */   protected void addButtons(JToolBar toolBar)
/*    */   {
/* 44 */     JButton button = null;
/*    */     
/*    */ 
/* 47 */     button = makeNavigationButton("Back24", "help", 
/* 48 */       "Go to Help", 
/* 49 */       "Help");
/*    */     
/*    */ 
/* 52 */     toolBar.add(button);
/*    */   }
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
/*    */   protected JButton makeNavigationButton(String imageName, String actionCommand, String toolTipText, String altText)
/*    */   {
/* 69 */     JButton button = new JButton();
/* 70 */     button.setActionCommand(actionCommand);
/* 71 */     button.setToolTipText(toolTipText);
/* 72 */     button.addActionListener(this);
/*    */     
/* 74 */     button.setText(altText);
/*    */     
/* 76 */     return button;
/*    */   }
/*    */   
/*    */   public void actionPerformed(ActionEvent e)
/*    */   {
/* 81 */     String cmd = e.getActionCommand();
/*    */     
/*    */ 
/* 84 */     if ("help".equals(cmd)) {
/* 85 */       new Help().game = true;
/* 86 */       new Initialization().openhelp();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\ToolBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */