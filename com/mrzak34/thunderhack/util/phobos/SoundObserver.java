/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.function.BooleanSupplier;
/*    */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*    */ 
/*    */ public abstract class SoundObserver
/*    */   implements Observer<SPacketSoundEffect> {
/*    */   private final BooleanSupplier soundRemove;
/*    */   
/*    */   public SoundObserver(BooleanSupplier soundRemove) {
/* 11 */     this.soundRemove = soundRemove;
/*    */   }
/*    */   
/*    */   public boolean shouldRemove() {
/* 15 */     return this.soundRemove.getAsBoolean();
/*    */   }
/*    */   
/*    */   public boolean shouldBeNotified() {
/* 19 */     return shouldRemove();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\SoundObserver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */