/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.ElytraEvent;
/*     */ import com.mrzak34.thunderhack.events.EventJump;
/*     */ import com.mrzak34.thunderhack.events.EventMoveDirection;
/*     */ import com.mrzak34.thunderhack.events.FinishUseItemEvent;
/*     */ import com.mrzak34.thunderhack.events.HandleLiquidJumpEvent;
/*     */ import com.mrzak34.thunderhack.modules.movement.NoJumpDelay;
/*     */ import com.mrzak34.thunderhack.modules.render.Animations;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.phobos.IEntityLivingBase;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.gen.Accessor;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ @Mixin({EntityLivingBase.class})
/*     */ public abstract class MixinEntityLivingBase
/*     */   extends Entity
/*     */   implements IEntityLivingBase
/*     */ {
/*     */   @Shadow
/*     */   public float field_70702_br;
/*  33 */   protected float lowestDura = Float.MAX_VALUE; @Shadow
/*     */   public float field_191988_bg; @Shadow
/*  35 */   public int field_70773_bE; public MixinEntityLivingBase(World worldIn) { super(worldIn); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLowestDura(float lowest) {
/*  40 */     this.lowestDura = lowest;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getLowestDurability() {
/*  45 */     return this.lowestDura;
/*     */   }
/*     */ 
/*     */   
/*     */   @Accessor("ticksSinceLastSwing")
/*     */   public abstract int getTicksSinceLastSwing();
/*     */ 
/*     */   
/*     */   @Accessor("ticksSinceLastSwing")
/*     */   public abstract void setTicksSinceLastSwing(int paramInt);
/*     */ 
/*     */   
/*     */   @Accessor("activeItemStackUseCount")
/*     */   public abstract int getActiveItemStackUseCount();
/*     */ 
/*     */   
/*     */   @Accessor("activeItemStackUseCount")
/*     */   public abstract void setActiveItemStackUseCount(int paramInt);
/*     */   
/*     */   @Inject(method = {"travel"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void onTravelPre(float strafe, float vertical, float forward, CallbackInfo ci) {
/*  66 */     ElytraEvent event = new ElytraEvent(this);
/*  67 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  68 */     if (Util.mc.field_71439_g != null && (EntityLivingBase)this == Util.mc.field_71439_g) {
/*  69 */       EventMoveDirection event2 = new EventMoveDirection(false);
/*  70 */       MinecraftForge.EVENT_BUS.post((Event)event2);
/*     */     } 
/*  72 */     if (event.isCanceled()) {
/*  73 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"travel"}, at = {@At("RETURN")}, cancellable = true)
/*     */   public void onTravelPost(float strafe, float vertical, float forward, CallbackInfo ci) {
/*  79 */     if (Util.mc.field_71439_g != null && (EntityLivingBase)this == Util.mc.field_71439_g) {
/*  80 */       EventMoveDirection event = new EventMoveDirection(true);
/*  81 */       MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"handleJumpWater"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void handleJumpWater(CallbackInfo ci) {
/*  87 */     HandleLiquidJumpEvent event = new HandleLiquidJumpEvent();
/*  88 */     if (event.isCanceled()) {
/*  89 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"handleJumpLava"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void handleJumpLava(CallbackInfo ci) {
/*  95 */     HandleLiquidJumpEvent event = new HandleLiquidJumpEvent();
/*  96 */     if (event.isCanceled()) {
/*  97 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"jump"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void jumphook(CallbackInfo ci) {
/* 103 */     EventJump event = new EventJump(this.field_70177_z);
/* 104 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 105 */     if (event.isCanceled()) {
/* 106 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"onItemUseFinish"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void finishHook(CallbackInfo ci) {
/* 112 */     FinishUseItemEvent event = new FinishUseItemEvent();
/* 113 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 114 */     if (event.isCanceled()) {
/* 115 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"getArmSwingAnimationEnd"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void getArmSwingAnimationEnd(CallbackInfoReturnable<Integer> info) {
/* 121 */     if (((Animations)Thunderhack.moduleManager.getModuleByClass(Animations.class)).isEnabled() && ((Animations)Thunderhack.moduleManager.getModuleByClass(Animations.class)).rMode.getValue() == Animations.rmode.Slow) {
/* 122 */       info.setReturnValue((Animations.getInstance()).slowValue.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"onLivingUpdate"}, at = {@At("HEAD")})
/*     */   public void onLivingUpdate(CallbackInfo ci) {
/* 128 */     if (((NoJumpDelay)Thunderhack.moduleManager.getModuleByClass(NoJumpDelay.class)).isEnabled())
/* 129 */       this.field_70773_bE = 0; 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinEntityLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */