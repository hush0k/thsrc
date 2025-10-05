/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ 
/*    */ public class LiquidInteract
/*    */   extends Module {
/*  7 */   private static LiquidInteract INSTANCE = new LiquidInteract();
/*    */   
/*    */   public LiquidInteract() {
/* 10 */     super("LiquidInteract", "ставить блоки на воду", Module.Category.PLAYER);
/* 11 */     setInstance();
/*    */   }
/*    */   
/*    */   public static LiquidInteract getInstance() {
/* 15 */     if (INSTANCE == null) {
/* 16 */       INSTANCE = new LiquidInteract();
/*    */     }
/* 18 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 22 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\LiquidInteract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */