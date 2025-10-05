/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.modules.misc.Timer;
/*    */ 
/*    */ public class TpsSync
/*    */   extends Module {
/*    */   public TpsSync() {
/* 10 */     super("TpsSync", "синхронизирует игру-с тпс", Module.Category.PLAYER);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 16 */     if (((Timer)Thunderhack.moduleManager.getModuleByClass(Timer.class)).isEnabled()) {
/*    */       return;
/*    */     }
/* 19 */     if (Thunderhack.serverManager.getTPS() > 1.0F) {
/* 20 */       Thunderhack.TICK_TIMER = Thunderhack.serverManager.getTPS() / 20.0F;
/*    */     } else {
/* 22 */       Thunderhack.TICK_TIMER = 1.0F;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 29 */     Thunderhack.TICK_TIMER = 1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\TpsSync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */