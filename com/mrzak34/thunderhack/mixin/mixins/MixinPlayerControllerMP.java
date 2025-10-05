/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ import com.mrzak34.thunderhack.events.AttackEvent;
/*     */ import com.mrzak34.thunderhack.events.ClickBlockEvent;
/*     */ import com.mrzak34.thunderhack.events.DamageBlockEvent;
/*     */ import com.mrzak34.thunderhack.events.DestroyBlockEvent;
/*     */ import com.mrzak34.thunderhack.events.ResetBlockEvent;
/*     */ import com.mrzak34.thunderhack.events.StopUsingItemEvent;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IPlayerControllerMP;
/*     */ import com.mrzak34.thunderhack.modules.misc.NoGlitchBlock;
/*     */ import com.mrzak34.thunderhack.modules.player.Reach;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ @Mixin({PlayerControllerMP.class})
/*     */ public abstract class MixinPlayerControllerMP implements IPlayerControllerMP {
/*     */   @Inject(method = {"getBlockReachDistance"}, at = {@At("RETURN")}, cancellable = true)
/*     */   private void getReachDistanceHook(CallbackInfoReturnable<Float> distance) {
/*  33 */     if (Reach.getInstance().isOn()) {
/*  34 */       float range = ((Float)distance.getReturnValue()).floatValue();
/*  35 */       distance.setReturnValue(Float.valueOf(range + ((Float)(Reach.getInstance()).add.getValue()).floatValue()));
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"clickBlock"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void clickBlockHook(BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> info) {
/*  41 */     ClickBlockEvent event2 = new ClickBlockEvent(pos, face);
/*  42 */     MinecraftForge.EVENT_BUS.post((Event)event2);
/*  43 */     if (event2.isCanceled()) {
/*  44 */       info.cancel();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Invoker("syncCurrentPlayItem")
/*     */   public abstract void syncItem();
/*     */   
/*     */   @Inject(method = {"attackEntity"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void attackEntityPre(EntityPlayer playerIn, Entity targetEntity, CallbackInfo info) {
/*  54 */     AttackEvent event = new AttackEvent(targetEntity, (short)0);
/*  55 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     
/*  57 */     if (event.isCanceled())
/*  58 */       info.cancel(); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"attackEntity"}, at = {@At("RETURN")}, cancellable = true)
/*     */   public void attackEntityPost(EntityPlayer playerIn, Entity targetEntity, CallbackInfo info) {
/*  63 */     AttackEvent event = new AttackEvent(targetEntity, (short)1);
/*  64 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  65 */     if (event.isCanceled()) {
/*  66 */       info.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"processRightClickBlock"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void clickBlockHook(EntityPlayerSP player, WorldClient worldIn, BlockPos pos, EnumFacing direction, Vec3d vec, EnumHand hand, CallbackInfoReturnable<EnumActionResult> info) {
/*  72 */     ClickBlockEvent.Right event = new ClickBlockEvent.Right(pos, direction, vec, hand);
/*     */     
/*  74 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     
/*  76 */     if (event.isCanceled()) {
/*  77 */       info.cancel();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"resetBlockRemoving"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void resetBlockRemovingHook(CallbackInfo info) {
/*  86 */     ResetBlockEvent event = new ResetBlockEvent();
/*  87 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     
/*  89 */     if (event.isCanceled()) {
/*  90 */       info.cancel();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"onStoppedUsingItem"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void stopHook(CallbackInfo info) {
/*  97 */     StopUsingItemEvent event = new StopUsingItemEvent();
/*  98 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  99 */     if (event.isCanceled()) {
/* 100 */       info.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"onPlayerDestroyBlock"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V")}, cancellable = true)
/*     */   private void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
/* 106 */     NoGlitchBlock noGlitchBlock = (NoGlitchBlock)Thunderhack.moduleManager.getModuleByClass(NoGlitchBlock.class);
/* 107 */     if (noGlitchBlock.isEnabled()) {
/* 108 */       callbackInfoReturnable.cancel();
/* 109 */       callbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
/*     */     } 
/* 111 */     MinecraftForge.EVENT_BUS.post((Event)new DestroyBlockEvent(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
/* 117 */     DamageBlockEvent event = new DamageBlockEvent(posBlock, directionFacing);
/* 118 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 119 */     if (event.isCanceled())
/* 120 */       callbackInfoReturnable.setReturnValue(Boolean.valueOf(false)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinPlayerControllerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */