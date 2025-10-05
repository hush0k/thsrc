/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.EventPlayerTravel;
/*    */ import com.mrzak34.thunderhack.mixin.ducks.IEntityPlayer;
/*    */ import com.mrzak34.thunderhack.modules.movement.KeepSprint;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import com.mrzak34.thunderhack.util.phobos.MotionTracker;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({EntityPlayer.class})
/*    */ public abstract class MixinEntityPlayer
/*    */   extends EntityLivingBase
/*    */   implements IEntityPlayer {
/*    */   @Unique
/*    */   private MotionTracker motionTrackerT;
/*    */   
/*    */   public MixinEntityPlayer(World worldIn, GameProfile gameProfileIn) {
/* 31 */     super(worldIn);
/*    */   } @Unique
/*    */   private MotionTracker breakMotionTrackerT; @Unique
/*    */   private MotionTracker blockMotionTrackerT;
/*    */   public MotionTracker getMotionTrackerT() {
/* 36 */     return this.motionTrackerT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setMotionTrackerT(MotionTracker motionTracker) {
/* 41 */     this.motionTrackerT = motionTracker;
/*    */   }
/*    */ 
/*    */   
/*    */   public MotionTracker getBreakMotionTrackerT() {
/* 46 */     return this.breakMotionTrackerT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBreakMotionTrackerT(MotionTracker breakMotionTracker) {
/* 51 */     this.breakMotionTrackerT = breakMotionTracker;
/*    */   }
/*    */ 
/*    */   
/*    */   public MotionTracker getBlockMotionTrackerT() {
/* 56 */     return this.blockMotionTrackerT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBlockMotionTrackerT(MotionTracker blockMotionTracker) {
/* 61 */     this.blockMotionTrackerT = blockMotionTracker;
/*    */   }
/*    */   
/*    */   @Inject(method = {"travel"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void travel(float strafe, float vertical, float forward, CallbackInfo info) {
/*    */     EntityPlayerSP entityPlayerSP;
/* 67 */     EntityPlayer us = null;
/* 68 */     if (Util.mc.field_71439_g != null) {
/* 69 */       entityPlayerSP = Util.mc.field_71439_g;
/*    */     }
/* 71 */     if (entityPlayerSP == null) {
/*    */       return;
/*    */     }
/* 74 */     EventPlayerTravel event = new EventPlayerTravel(strafe, vertical, forward);
/* 75 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 76 */     if (event.isCanceled()) {
/* 77 */       func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 78 */       info.cancel();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"attackTargetEntityWithCurrentItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;setSprinting(Z)V", shift = At.Shift.AFTER)})
/*    */   public void onAttackTargetEntityWithCurrentItem(CallbackInfo callbackInfo) {
/* 85 */     KeepSprint ks = (KeepSprint)Thunderhack.moduleManager.getModuleByClass(KeepSprint.class);
/* 86 */     if (ks.isEnabled()) {
/* 87 */       float multiplier = 0.6F + 0.4F * ((Float)ks.motion.getValue()).floatValue();
/* 88 */       this.field_70159_w = this.field_70159_w / 0.6D * multiplier;
/* 89 */       this.field_70179_y = this.field_70179_y / 0.6D * multiplier;
/* 90 */       if (((Boolean)ks.sprint.getValue()).booleanValue())
/* 91 */         func_70031_b(true); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinEntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */