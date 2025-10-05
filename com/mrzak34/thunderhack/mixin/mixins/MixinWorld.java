/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EntityAddedEvent;
/*     */ import com.mrzak34.thunderhack.events.EntityRemovedEvent;
/*     */ import com.mrzak34.thunderhack.events.EventEntityMove;
/*     */ import com.mrzak34.thunderhack.events.EventEntitySpawn;
/*     */ import com.mrzak34.thunderhack.events.PushEvent;
/*     */ import com.mrzak34.thunderhack.events.UpdateEntitiesEvent;
/*     */ import com.mrzak34.thunderhack.modules.misc.NoGlitchBlock;
/*     */ import com.mrzak34.thunderhack.modules.render.NoRender;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({World.class})
/*     */ public class MixinWorld
/*     */ {
/*     */   @Shadow
/*     */   @Final
/*     */   public boolean field_72995_K;
/*     */   double nigga1;
/*     */   double nigga2;
/*     */   double nigga3;
/*     */   
/*     */   @Inject(method = {"updateEntities"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 2)})
/*     */   public void updateEntitiesHook(CallbackInfo ci) {
/*  45 */     if (this.field_72995_K) {
/*  46 */       UpdateEntitiesEvent event = new UpdateEntitiesEvent();
/*  47 */       MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"checkLightFor"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void updateLightmapHook(EnumSkyBlock lightType, BlockPos pos, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
/*  53 */     NoRender noRender = (NoRender)Thunderhack.moduleManager.getModuleByClass(NoRender.class);
/*  54 */     if (lightType == EnumSkyBlock.SKY && noRender.isEnabled() && ((Boolean)noRender.SkyLight.getValue()).booleanValue() && !Util.mc.func_71356_B()) {
/*  55 */       callbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"onEntityAdded"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void onEntityAdded(Entity entity, CallbackInfo callbackInfo) {
/*  61 */     EntityAddedEvent event = new EntityAddedEvent(entity);
/*     */     
/*  63 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  64 */     if (event.isCanceled()) {
/*  65 */       callbackInfo.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"onEntityRemoved"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void onEntityRemoved(Entity entity, CallbackInfo callbackInfo) {
/*  71 */     EntityRemovedEvent event = new EntityRemovedEvent(entity);
/*     */     
/*  73 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  74 */     if (event.isCanceled()) {
/*  75 */       callbackInfo.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Redirect(method = {"handleMaterialAcceleration"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isPushedByWater()Z"))
/*     */   public boolean isPushedbyWaterHook(Entity entity) {
/*  81 */     PushEvent event = new PushEvent();
/*  82 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  83 */     return (entity.func_96092_aw() && !event.isCanceled());
/*     */   }
/*     */   
/*     */   @Inject(method = {"spawnEntity"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void spawnEntityFireWork(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
/*  88 */     EventEntitySpawn ees = new EventEntitySpawn(entityIn);
/*  89 */     if (ees.isCanceled()) {
/*  90 */       cir.setReturnValue(Boolean.valueOf(false));
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"updateEntityWithOptionalForce"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onUpdate()V", shift = At.Shift.AFTER)})
/*     */   public void updateEntityWithOptionalForceHookPost(Entity entityIn, boolean forceUpdate, CallbackInfo ci) {
/*  96 */     if (this.nigga1 != entityIn.field_70165_t || this.nigga2 != entityIn.field_70163_u || this.nigga3 != entityIn.field_70161_v) {
/*  97 */       EventEntityMove event = new EventEntityMove(entityIn, new Vec3d(this.nigga1, this.nigga2, this.nigga3));
/*  98 */       MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"updateEntityWithOptionalForce"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onUpdate()V")})
/*     */   public void updateEntityWithOptionalForceHookPre(Entity entityIn, boolean forceUpdate, CallbackInfo ci) {
/* 105 */     this.nigga1 = entityIn.field_70165_t;
/* 106 */     this.nigga2 = entityIn.field_70163_u;
/* 107 */     this.nigga3 = entityIn.field_70161_v;
/*     */   }
/*     */   
/*     */   @Inject(method = {"setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void setBlockState(BlockPos pos, IBlockState newState, int flags, CallbackInfoReturnable<Boolean> cir) {
/* 112 */     NoGlitchBlock noGlitchBlock = (NoGlitchBlock)Thunderhack.moduleManager.getModuleByClass(NoGlitchBlock.class);
/* 113 */     if (noGlitchBlock.isEnabled() && flags != 3) {
/* 114 */       cir.cancel();
/* 115 */       cir.setReturnValue(Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */