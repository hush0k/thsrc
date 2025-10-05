/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import net.minecraft.util.Timer;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({Timer.class})
/*    */ public class MixinTimer
/*    */ {
/*    */   @Shadow
/*    */   public float field_194148_c;
/*    */   
/*    */   @Inject(method = {"updateTimer"}, at = {@At(value = "FIELD", target = "net/minecraft/util/Timer.elapsedPartialTicks:F", ordinal = 1)})
/*    */   public void updateTimer(CallbackInfo info) {
/* 19 */     this.field_194148_c *= Thunderhack.TICK_TIMER;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */