/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ public class NoInterp extends Module {
/*  7 */   private static NoInterp INSTANCE = new NoInterp();
/*  8 */   public Setting<Boolean> lowIQ = register(new Setting("LowIQ", Boolean.valueOf(true)));
/*    */   
/*    */   public NoInterp() {
/* 11 */     super("NoInterp", "убирает интерполяцию-с игроков", Module.Category.RENDER);
/* 12 */     setInstance();
/*    */   }
/*    */   
/*    */   public static NoInterp getInstance() {
/* 16 */     if (INSTANCE == null) {
/* 17 */       INSTANCE = new NoInterp();
/*    */     }
/* 19 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 23 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\NoInterp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */