/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ public class ShiftInterp
/*    */   extends Module {
/*  8 */   private static ShiftInterp INSTANCE = new ShiftInterp();
/*  9 */   public Setting<Boolean> sleep = register(new Setting("Sleep", Boolean.valueOf(false)));
/* 10 */   public Setting<Boolean> aboba = register(new Setting("aboba", Boolean.valueOf(false)));
/*    */   
/*    */   public ShiftInterp() {
/* 13 */     super("ShiftInterp", "все игроки будут-на шифте", Module.Category.RENDER);
/* 14 */     setInstance();
/*    */   }
/*    */   
/*    */   public static ShiftInterp getInstance() {
/* 18 */     if (INSTANCE == null) {
/* 19 */       INSTANCE = new ShiftInterp();
/*    */     }
/* 21 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 25 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\ShiftInterp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */