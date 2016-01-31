/*     */ package layout;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TurnManager
/*     */ {
/*  12 */   static GridManager g = UIsetup.g;
/*  13 */   static UIsetup u = new UIsetup();
/*  14 */   static BuildManager b = UIsetup.b;
/*     */   
/*  16 */   public boolean FirstTurn = true;
/*  17 */   boolean PlayerTurn = false;
/*  18 */   Random random = new Random();
/*  19 */   int TravelNum = 0;
/*     */   
/*  21 */   boolean P1win = false;
/*  22 */   boolean P2win = false;
/*  23 */   boolean P1lose = false;
/*  24 */   boolean P2lose = false;
/*     */   
/*  26 */   private int[] playercredits = new int[2];
/*  27 */   boolean SendStatus = true;
/*     */   
/*  29 */   int[] Travel = new int[100000];
/*  30 */   int[] SourceShips = new int[100000];
/*  31 */   int[] Destination = new int[100000];
/*  32 */   String[] SentFrom = new String[100000];
/*     */   
/*  34 */   int[] Destroyers = new int[100000];
/*  35 */   int[] Frigates = new int[100000];
/*  36 */   int[] Fighters = new int[100000];
/*     */   
/*     */ 
/*  39 */   private int[] planetships = new int[20];
/*  40 */   private int[] planetproduction = new int[20];
/*  41 */   private int[] planetcredits = new int[20];
/*  42 */   private double[] planetstrength = new double[20];
/*  43 */   public String[] planetname = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t" };
/*  44 */   private String[] planetowner = new String[20];
/*     */   
/*     */ 
/*     */   String news;
/*     */   
/*     */ 
/*     */   TurnManager()
/*     */   {
/*  52 */     for (int x = 0; x < 10000; x++) {
/*  53 */       this.Travel[x] = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void RandomValues()
/*     */   {
/*  60 */     String[] owner = new String[20];
/*  61 */     int[] ships = new int[20];
/*  62 */     int[] production = new int[20];
/*  63 */     double[] strength = new double[20];
/*  64 */     int[] credits = new int[20];
/*     */     
/*  66 */     for (int x = 1; x < 19; x++) {
/*  67 */       owner[x] = "neutral";
/*     */       
/*  69 */       ships[x] = 10;
/*     */       
/*  71 */       production[x] = (this.random.nextInt(11) + 5);
/*     */       
/*  73 */       double StrengthNum = this.random.nextInt(601) + 300;
/*  74 */       strength[x] = Round(StrengthNum / 100.0D, 2);
/*     */       
/*  76 */       credits[x] = (this.random.nextInt(751) + 250);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  81 */     owner[0] = "Player 1";
/*  82 */     ships[0] = 10;
/*  83 */     production[0] = 10;
/*  84 */     strength[0] = 4.5D;
/*  85 */     credits[0] = 400;
/*     */     
/*  87 */     owner[19] = "Player 2";
/*  88 */     ships[19] = 10;
/*  89 */     production[19] = 10;
/*  90 */     strength[19] = 4.5D;
/*  91 */     credits[19] = 400;
/*     */     
/*     */ 
/*  94 */     SetPlanetOwner(owner);
/*  95 */     SetPlanetShips(ships);
/*  96 */     SetPlanetProd(production);
/*  97 */     SetPlanetStrength(strength);
/*  98 */     SetPlanetCredits(credits);
/*     */     
/*     */ 
/*     */ 
/* 102 */     this.FirstTurn = false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void NextTurn()
/*     */   {
/* 108 */     int[] shipnum = GetPlanetShips();
/* 109 */     int[] prodnum = GetPlanetProd();
/* 110 */     int[] adjustships = new int[20];
/* 111 */     String[] planetowner = GetPlanetOwner();
/* 112 */     int[] credits = GetPlayerCredits();
/* 113 */     int[] planetcred = GetPlanetCredits();
/*     */     
/*     */ 
/* 116 */     for (int x = 0; x < 20; x++)
/*     */     {
/* 118 */       if (!planetowner[x].equals("neutral")) {
/* 119 */         shipnum[x] += prodnum[x];
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 124 */         shipnum[x] += 1;
/*     */       }
/*     */     }
/* 127 */     SetPlanetShips(adjustships);
/*     */     
/*     */ 
/*     */ 
/* 131 */     for (int x = 0; x < 20; x++) {
/* 132 */       if (planetowner[x].equals("Player 1")) {
/* 133 */         credits[0] += planetcred[x];
/*     */       }
/* 135 */       else if (planetowner[x].equals("Player 2")) {
/* 136 */         credits[1] += planetcred[x];
/*     */       }
/*     */     }
/*     */     
/* 140 */     SetPlayerCredits(credits);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void ShipBattle(int x)
/*     */   {
/* 150 */     int[] planetships = GetPlanetShips();
/* 151 */     double[] planetstrength = GetPlanetStrength();
/* 152 */     String[] planetowner = GetPlanetOwner();
/* 153 */     String[] name = GetPlanetName();
/*     */     
/* 155 */     if (this.SentFrom[x].equals(planetowner[this.Destination[x]])) {
/* 156 */       planetships[this.Destination[x]] += this.SourceShips[x];
/* 157 */       this.news = ("Planet " + name[this.Destination[x]] + " has recieved " + this.SourceShips[x] + " reinforcements!");
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 162 */       int attack = this.SourceShips[x] + this.Fighters[x] * 2;
/* 163 */       int attackdefense = (int)((planetships[this.Destination[x]] - this.Frigates[x] * 10) * (1.0D + planetstrength[this.Destination[x]] / 20.0D * Math.pow(0.9D, this.Destroyers[x])));
/* 164 */       int shipdefense = (int)(planetstrength[this.Destination[x]] * (b.destroyer[this.Destination[x]] * 0.08D + b.frigate[this.Destination[x]] * 0.04D + b.fighter[this.Destination[x]] * 0.01D));
/*     */       
/*     */ 
/* 167 */       planetships[this.Destination[x]] = (attackdefense + shipdefense - attack);
/*     */       
/*     */ 
/* 170 */       if (planetships[this.Destination[x]] < 0) {
/* 171 */         planetships[this.Destination[x]] = Math.abs(planetships[this.Destination[x]]);
/* 172 */         planetowner[this.Destination[x]] = this.SentFrom[x];
/* 173 */         SetPlanetOwner(planetowner);
/*     */         
/* 175 */         this.news = ("Planet " + name[this.Destination[x]] + " has been taken by " + this.SentFrom[x] + "!");
/*     */       }
/*     */       else
/*     */       {
/* 179 */         this.news = ("Planet " + name[this.Destination[x]] + " has withstood an attack from " + this.SentFrom[x] + "!");
/*     */       }
/*     */       
/* 182 */       SetPlanetShips(planetships);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void Win()
/*     */   {
/* 191 */     int P1count = 0;
/* 192 */     int P2count = 0;
/*     */     
/* 194 */     for (int x = 0; x < 20; x++) {
/* 195 */       if (this.planetowner[x] == "Player 1") {
/* 196 */         P1count++;
/* 197 */       } else if (this.planetowner[x] == "Player 2") {
/* 198 */         P2count++;
/*     */       }
/*     */     }
/* 201 */     if (P1count >= 20) {
/* 202 */       this.P1win = true;
/* 203 */     } else if (P2count >= 20) {
/* 204 */       this.P2win = true;
/*     */     }
/* 206 */     if (P1count <= 0)
/* 207 */       this.P1lose = true;
/* 208 */     if (P2count <= 0) {
/* 209 */       this.P2lose = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double Round(double Rval, int Rpl)
/*     */   {
/* 219 */     double p = Math.pow(10.0D, Rpl);
/* 220 */     Rval *= p;
/* 221 */     double tmp = Math.round(Rval);
/* 222 */     return tmp / p;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] GetPlayerCredits()
/*     */   {
/* 231 */     return this.playercredits;
/*     */   }
/*     */   
/* 234 */   public String[] GetPlanetOwner() { return this.planetowner; }
/*     */   
/*     */   public String[] GetPlanetName() {
/* 237 */     return this.planetname;
/*     */   }
/*     */   
/* 240 */   public int[] GetPlanetShips() { return this.planetships; }
/*     */   
/*     */   public int[] GetPlanetProd() {
/* 243 */     return this.planetproduction;
/*     */   }
/*     */   
/* 246 */   public double[] GetPlanetStrength() { return this.planetstrength; }
/*     */   
/*     */   public int[] GetPlanetCredits() {
/* 249 */     return this.planetcredits;
/*     */   }
/*     */   
/* 252 */   public boolean GetFirstTurn() { return this.FirstTurn; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SetPlayerCredits(int[] credits)
/*     */   {
/* 259 */     this.playercredits = credits;
/*     */   }
/*     */   
/* 262 */   public void SetPlanetOwner(String[] owner) { this.planetowner = owner; }
/*     */   
/*     */   public void SetPlanetName(String[] name) {
/* 265 */     this.planetname = name;
/*     */   }
/*     */   
/* 268 */   public void SetPlanetShips(int[] ships) { this.planetships = ships; }
/*     */   
/*     */   public void SetPlanetProd(int[] production) {
/* 271 */     this.planetproduction = production;
/*     */   }
/*     */   
/* 274 */   public void SetPlanetStrength(double[] strength) { this.planetstrength = strength; }
/*     */   
/*     */   public void SetPlanetCredits(int[] credits) {
/* 277 */     this.planetcredits = credits;
/*     */   }
/*     */   
/* 280 */   public void SetFirstTurn(boolean turn) { this.FirstTurn = turn; }
/*     */ }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\TurnManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */