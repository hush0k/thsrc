/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ 
/*    */ public class CustomEnchants extends Module {
/*  6 */   private static CustomEnchants INSTANCE = new CustomEnchants();
/*    */   
/*    */   public CustomEnchants() {
/*  9 */     super("RainbowEnchants", "радужные зачары", Module.Category.RENDER);
/* 10 */     setInstance();
/*    */   }
/*    */   
/*    */   public static CustomEnchants getInstance() {
/* 14 */     if (INSTANCE == null) {
/* 15 */       INSTANCE = new CustomEnchants();
/*    */     }
/* 17 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 21 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\CustomEnchants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */