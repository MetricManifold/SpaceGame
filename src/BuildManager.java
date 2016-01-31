/*     */ package layout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuildManager
/*     */ {
/*  10 */   int[] destroyer = new int[20];
/*  11 */   int[] frigate = new int[20];
/*  12 */   int[] fighter = new int[20];
/*     */   
/*  14 */   boolean[] ProdStatus = new boolean[20];
/*  15 */   boolean[] StreStatus = new boolean[20];
/*     */   
/*  17 */   UIsetup u = GridManager.u;
/*  18 */   TurnManager t = GridManager.t;
/*  19 */   GridManager g = UIsetup.g;
/*     */   int credits;
/*     */   String news;
/*  22 */   int[] playercredits = new int[2];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addDestroyer(int planet)
/*     */   {
/*  29 */     this.destroyer[planet] += 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addFrigate(int planet)
/*     */   {
/*  37 */     this.frigate[planet] += 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addFighter(int planet)
/*     */   {
/*  45 */     this.fighter[planet] += 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void MakeProd(int planet)
/*     */   {
/*     */     try
/*     */     {
/*  54 */       this.playercredits = this.t.GetPlayerCredits();
/*  55 */       int[] production = this.t.GetPlanetProd();
/*     */       
/*  57 */       if (this.ProdStatus[planet] == 0) {
/*  58 */         if (this.credits >= 12500)
/*     */         {
/*  60 */           production[planet] += 3;
/*  61 */           this.t.SetPlanetProd(production);
/*  62 */           this.ProdStatus[planet] = (this.ProdStatus[planet] != 0 ? 0 : true);
/*     */           
/*     */ 
/*  65 */           if (!this.t.PlayerTurn) {
/*  66 */             this.playercredits[0] -= 12500;
/*     */           } else {
/*  68 */             this.playercredits[1] -= 12500;
/*     */           }
/*     */         } else {
/*  71 */           this.news = "You don't have enough money!";
/*     */         }
/*     */       } else {
/*  74 */         this.news = "This planet already has an advanced production facility!";
/*     */       }
/*  76 */       this.t.SetPlayerCredits(this.playercredits);
/*     */     }
/*     */     catch (Exception e1) {
/*  79 */       this.news = "Input invalid!";
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void MakeDefense(int planet)
/*     */   {
/*     */     try
/*     */     {
/*  89 */       this.playercredits = this.t.GetPlayerCredits();
/*  90 */       double[] strength = this.t.GetPlanetStrength();
/*     */       
/*  92 */       if (this.StreStatus[planet] == 0) {
/*  93 */         if (this.credits >= 9500)
/*     */         {
/*  95 */           strength[planet] += 3.0D;
/*  96 */           this.t.SetPlanetStrength(strength);
/*  97 */           this.StreStatus[planet] = (this.StreStatus[planet] != 0 ? 0 : true);
/*     */           
/*     */ 
/* 100 */           if (!this.t.PlayerTurn) {
/* 101 */             this.playercredits[0] -= 9500;
/*     */           } else {
/* 103 */             this.playercredits[1] -= 9500;
/*     */           }
/*     */         } else {
/* 106 */           this.news = "You don't have enough money!";
/*     */         }
/*     */       } else {
/* 109 */         this.news = "This planet already has an array of space defenses!";
/*     */       }
/* 111 */       this.t.SetPlayerCredits(this.playercredits);
/*     */     }
/*     */     catch (Exception e) {
/* 114 */       this.news = "Input invalid!";
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void MakeDestroyer(int planet)
/*     */   {
/*     */     try
/*     */     {
/* 124 */       this.playercredits = this.t.GetPlayerCredits();
/*     */       
/* 126 */       if (this.credits >= 1000)
/*     */       {
/* 128 */         addDestroyer(planet);
/*     */         
/*     */ 
/* 131 */         if (!this.t.PlayerTurn) {
/* 132 */           this.playercredits[0] -= 1000;
/*     */         } else {
/* 134 */           this.playercredits[1] -= 1000;
/*     */         }
/*     */       } else {
/* 137 */         this.news = "You don't have enough money!";
/*     */       }
/* 139 */       this.t.SetPlayerCredits(this.playercredits);
/*     */     }
/*     */     catch (Exception e) {
/* 142 */       this.news = "Input invalid!";
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void MakeFrigate(int planet)
/*     */   {
/*     */     try
/*     */     {
/* 152 */       this.playercredits = this.t.GetPlayerCredits();
/*     */       
/* 154 */       if (this.credits >= 500)
/*     */       {
/* 156 */         addFrigate(planet);
/*     */         
/*     */ 
/* 159 */         if (!this.t.PlayerTurn) {
/* 160 */           this.playercredits[0] -= 500;
/*     */         } else {
/* 162 */           this.playercredits[1] -= 500;
/*     */         }
/*     */       } else {
/* 165 */         this.news = "You don't have enough money!";
/*     */       }
/* 167 */       this.t.SetPlayerCredits(this.playercredits);
/*     */     }
/*     */     catch (Exception e) {
/* 170 */       this.news = "Input invalid!";
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void MakeFighter(int planet)
/*     */   {
/*     */     try
/*     */     {
/* 180 */       this.playercredits = this.t.GetPlayerCredits();
/*     */       
/* 182 */       if (this.credits >= 100)
/*     */       {
/* 184 */         addFighter(planet);
/*     */         
/*     */ 
/* 187 */         if (!this.t.PlayerTurn) {
/* 188 */           this.playercredits[0] -= 100;
/*     */         } else {
/* 190 */           this.playercredits[1] -= 100;
/*     */         }
/*     */       } else {
/* 193 */         this.news = "You don't have enough money!";
/*     */       }
/* 195 */       this.t.SetPlayerCredits(this.playercredits);
/*     */     }
/*     */     catch (Exception e) {
/* 198 */       this.news = "Input invalid!";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java Game\bin\!\layout\BuildManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */