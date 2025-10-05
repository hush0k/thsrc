/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.PushEvent;
/*     */ import com.mrzak34.thunderhack.events.StepEvent;
/*     */ import com.mrzak34.thunderhack.events.TurnEvent;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.modules.combat.BackTrack;
/*     */ import com.mrzak34.thunderhack.modules.combat.HitBoxes;
/*     */ import com.mrzak34.thunderhack.modules.render.PlayerTrails;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.phobos.IEntityNoInterp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.gen.Accessor;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ 
/*     */ @Mixin({Entity.class})
/*     */ public abstract class MixinEntity
/*     */   implements IEntity
/*     */ {
/*  36 */   private final Timer pseudoTimer = new Timer();
/*     */   
/*     */   @Shadow
/*     */   public double field_70165_t;
/*     */   @Shadow
/*     */   public double field_70163_u;
/*     */   @Shadow
/*     */   public double field_70161_v;
/*     */   @Shadow
/*     */   public double field_70159_w;
/*     */   @Shadow
/*     */   public double field_70181_x;
/*     */   @Shadow
/*     */   public double field_70179_y;
/*     */   @Shadow
/*     */   public float field_70177_z;
/*     */   @Shadow
/*     */   public float field_70125_A;
/*     */   @Shadow
/*     */   public boolean field_70122_E;
/*     */   @Shadow
/*     */   public World field_70170_p;
/*     */   @Shadow
/*     */   public float field_70138_W;
/*     */   @Shadow
/*     */   public double field_70169_q;
/*     */   @Shadow
/*     */   public double field_70166_s;
/*     */   @Shadow
/*     */   public double field_70142_S;
/*     */   @Shadow
/*     */   public double field_70137_T;
/*     */   @Shadow
/*     */   public double field_70136_U;
/*     */   @Shadow
/*     */   public boolean field_70128_L;
/*     */   @Shadow
/*     */   public float field_70130_N;
/*     */   @Shadow
/*     */   public float field_70126_B;
/*     */   @Shadow
/*     */   public float field_70131_O;
/*     */   private boolean pseudoDead;
/*     */   private long stamp;
/*  80 */   public List<PlayerTrails.Trail> trails = new ArrayList<>();
/*  81 */   public List<BackTrack.Box> position_history = new ArrayList<>();
/*     */   
/*     */   @Shadow
/*     */   protected boolean field_71087_bX;
/*     */   
/*     */   @Accessor("isInWeb")
/*     */   public abstract boolean isInWeb();
/*     */   
/*     */   @Shadow
/*     */   public abstract boolean func_70093_af();
/*     */   
/*     */   @Shadow
/*     */   public abstract AxisAlignedBB func_174813_aQ();
/*     */   
/*     */   @Inject(method = {"move"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endSection()V", shift = At.Shift.BEFORE, ordinal = 0)})
/*     */   public void onMove(MoverType type, double x, double y, double z, CallbackInfo info) {
/*  97 */     if (((Entity)this).equals(Util.mc.field_71439_g)) {
/*  98 */       StepEvent event = new StepEvent(func_174813_aQ(), this.field_70138_W);
/*  99 */       MinecraftForge.EVENT_BUS.post((Event)event);
/* 100 */       if (event.isCanceled()) {
/* 101 */         this.field_70138_W = event.getHeight();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"turn"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void onTurn(float yaw, float pitch, CallbackInfo ci) {
/* 108 */     TurnEvent event = new TurnEvent(yaw, pitch);
/* 109 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 110 */     if (event.isCanceled()) {
/* 111 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Redirect(method = {"applyEntityCollision"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
/*     */   public void addVelocityHook(Entity entity, double x, double y, double z) {
/* 117 */     PushEvent event = new PushEvent();
/* 118 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */   }
/*     */   
/*     */   @Inject(method = {"getCollisionBorderSize"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void getCollisionBorderSize(CallbackInfoReturnable<Float> callbackInfoReturnable) {
/* 123 */     if (((HitBoxes)Thunderhack.moduleManager.getModuleByClass(HitBoxes.class)).isEnabled()) {
/* 124 */       callbackInfoReturnable.setReturnValue(Float.valueOf(0.1F + ((Float)((HitBoxes)Thunderhack.moduleManager.getModuleByClass(HitBoxes.class)).expand.getValue()).floatValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   public abstract boolean equals(Object paramObject);
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   public abstract String func_70005_c_();
/*     */ 
/*     */   
/*     */   public boolean isPseudoDeadT() {
/* 138 */     if (this.pseudoDead && !this.field_70128_L && this.pseudoTimer.passedMs(500L)) {
/* 139 */       this.pseudoDead = false;
/*     */     }
/*     */     
/* 142 */     return this.pseudoDead;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPseudoDeadT(boolean pseudoDead) {
/* 147 */     this.pseudoDead = pseudoDead;
/* 148 */     if (pseudoDead) {
/* 149 */       this.pseudoTimer.reset();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInPortal(boolean bool) {
/* 155 */     this.field_71087_bX = bool;
/*     */   }
/*     */ 
/*     */   
/*     */   public Timer getPseudoTimeT() {
/* 160 */     return this.pseudoTimer;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BackTrack.Box> getPosition_history() {
/* 165 */     return this.position_history;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PlayerTrails.Trail> getTrails() {
/* 170 */     return this.trails;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTimeStampT() {
/* 175 */     return this.stamp;
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"<init>"}, at = {@At("RETURN")})
/*     */   public void ctrHook(CallbackInfo info) {
/* 181 */     this.stamp = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"setPositionAndRotation"}, at = {@At("RETURN")})
/*     */   public void setPositionAndRotationHook(double x, double y, double z, float yaw, float pitch, CallbackInfo ci) {
/* 191 */     if (this instanceof IEntityNoInterp) {
/* 192 */       ((IEntityNoInterp)Util.mc.field_71439_g).setNoInterpX(x);
/* 193 */       ((IEntityNoInterp)Util.mc.field_71439_g).setNoInterpY(y);
/* 194 */       ((IEntityNoInterp)Util.mc.field_71439_g).setNoInterpZ(z);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */