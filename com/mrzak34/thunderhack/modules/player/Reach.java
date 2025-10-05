/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ 
/*    */ public class Reach
/*    */   extends Module
/*    */ {
/* 10 */   private static Reach INSTANCE = new Reach();
/*    */ 
/*    */   
/* 13 */   public Setting<Float> add = register(new Setting("Add", Float.valueOf(3.0F), Float.valueOf(0.0F), Float.valueOf(7.0F)));
/*    */   
/*    */   public Reach() {
/* 16 */     super("Reach", "Увеличивает дальность-взаимодействий (и член)", Module.Category.PLAYER);
/* 17 */     setInstance();
/*    */   }
/*    */   
/*    */   public static Reach getInstance() {
/* 21 */     if (INSTANCE == null) {
/* 22 */       INSTANCE = new Reach();
/*    */     }
/* 24 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 28 */     INSTANCE = this;
/*    */   }
/*    */   
/*    */   public String getDisplayInfo() {
/* 32 */     return ((Float)this.add.getValue()).toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\Reach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */