/*     */ package layout;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Toolkit;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class Menu extends javax.swing.JFrame implements java.awt.event.ActionListener
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  14 */   UIsetup u = TurnManager.u;
/*  15 */   static Help h = new Help();
/*  16 */   static Menu m = ToolBar.m;
/*     */   
/*     */ 
/*  19 */   JButton Start = new JButton("Start");
/*  20 */   JButton Help = new JButton("Help");
/*     */   JPanel MenuSpace;
/*  22 */   JLabel Title = new JLabel("CONQUEST");
/*     */   
/*     */ 
/*     */   public Menu()
/*     */   {
/*  27 */     this.MenuSpace = new JPanel() {
/*     */       private static final long serialVersionUID = 1L;
/*     */       
/*     */       public void paintComponent(Graphics g) {
/*  31 */         java.awt.Image img = new javax.swing.ImageIcon("C:\\Users\\Zirconix\\Pictures\\Project pictures\\outerspace.jpg").getImage();
/*  32 */         Dimension size = new Dimension(400, 400);
/*  33 */         setPreferredSize(size);
/*  34 */         setMinimumSize(size);
/*  35 */         setMaximumSize(size);
/*  36 */         setSize(size);
/*  37 */         g.drawImage(img, 0, 0, null);
/*     */       }
/*     */       
/*     */ 
/*  41 */     };
/*  42 */     this.MenuSpace.setLayout(new java.awt.GridBagLayout());
/*     */     
/*     */ 
/*  45 */     setSize(400, 400);
/*     */     
/*  47 */     java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  52 */     this.Start.addActionListener(this);
/*  53 */     this.Help.addActionListener(this);
/*     */     
/*  55 */     this.Title.setFont(new java.awt.Font("Ariel", 1, 58));
/*  56 */     this.Title.setForeground(java.awt.Color.cyan);
/*     */     
/*  58 */     c.anchor = 10;
/*     */     
/*  60 */     int p = 0;
/*     */     
/*  62 */     c.gridy = (p++);
/*  63 */     c.insets = new Insets(0, 10, 50, 10);
/*  64 */     this.MenuSpace.add(this.Title, c);
/*     */     
/*  66 */     c.gridy = (p++);
/*  67 */     c.insets = new Insets(0, 10, 0, 10);
/*  68 */     this.MenuSpace.add(this.Start, c);
/*     */     
/*  70 */     c.gridy = (p++);
/*  71 */     c.insets = new Insets(10, 30, 0, 30);
/*  72 */     this.MenuSpace.add(this.Help, c);
/*     */     
/*     */ 
/*  75 */     add(this.MenuSpace);
/*     */     
/*     */ 
/*  78 */     Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
/*  79 */     int w = getSize().width;
/*  80 */     int h = getSize().height;
/*  81 */     int x = (dim.width - w) / 2;
/*  82 */     int y = (dim.height - h) / 2;
/*  83 */     setLocation(x, y);
/*     */     
/*     */ 
/*  86 */     setDefaultCloseOperation(3);
/*  87 */     setResizable(false);
/*  88 */     setTitle("Conquest");
/*  89 */     setIconImage(Toolkit.getDefaultToolkit().getImage(
/*  90 */       Menu.class.getResource("planet1.png")));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void actionPerformed(java.awt.event.ActionEvent e)
/*     */   {
/*  99 */     if (e.getSource() == this.Start) {
/* 100 */       dispose();
/* 101 */       new Initialization().startgame();
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 106 */       new Initialization().openhelp();
/* 107 */       new Help().menu = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\Menu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */