/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*    */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*    */ 
/*    */ public final class ListenerSound extends SoundObserver {
/*    */   private final AutoCrystal module;
/*    */   
/*    */   public ListenerSound(AutoCrystal module) {
/* 10 */     super(module.soundRemove::getValue);
/* 11 */     this.module = module;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onChange(SPacketSoundEffect value) {
/* 17 */     if (((Boolean)this.module.soundThread.getValue()).booleanValue()) {
/* 18 */       this.module.threadHelper.startThread(new net.minecraft.util.math.BlockPos[0]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldBeNotified() {
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ListenerSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */