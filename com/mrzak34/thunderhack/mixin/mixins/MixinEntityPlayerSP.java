/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSprint;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.FreecamEvent;
/*     */ import com.mrzak34.thunderhack.events.MatrixMove;
/*     */ import com.mrzak34.thunderhack.events.PlayerUpdateEvent;
/*     */ import com.mrzak34.thunderhack.events.PostPlayerUpdateEvent;
/*     */ import com.mrzak34.thunderhack.events.PushEvent;
/*     */ import com.mrzak34.thunderhack.manager.EventManager;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntityPlayerSP;
/*     */ import com.mrzak34.thunderhack.modules.movement.Speed;
/*     */ import com.mrzak34.thunderhack.modules.movement.Strafe;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ @Mixin(value = {EntityPlayerSP.class}, priority = 9998)
/*     */ public abstract class MixinEntityPlayerSP extends AbstractClientPlayer implements IEntityPlayerSP {
/*     */   @Shadow
/*     */   public final NetHandlerPlayClient field_71174_a;
/*     */   public Runnable auraCallBack;
/*     */   
/*     */   public MixinEntityPlayerSP(Minecraft p_i47378_1_, World p_i47378_2_, NetHandlerPlayClient p_i47378_3_, StatisticsManager p_i47378_4_, RecipeBook p_i47378_5_, NetHandlerPlayClient connection) {
/*  45 */     super(p_i47378_2_, p_i47378_3_.func_175105_e());
/*  46 */     this.field_71174_a = p_i47378_3_;
/*     */   }
/*     */   boolean pre_sprint_state = false; private boolean updateLock = false;
/*     */   
/*     */   @Shadow
/*     */   public abstract boolean func_70093_af();
/*     */   
/*     */   @Redirect(method = {"onUpdateWalkingPlayer"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"))
/*     */   private boolean redirectIsCurrentViewEntity(EntityPlayerSP entityPlayerSP) {
/*  55 */     FreecamEvent event = new FreecamEvent();
/*  56 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  57 */     if (event.isCanceled()) {
/*  58 */       return (entityPlayerSP == Util.mc.field_71439_g);
/*     */     }
/*  60 */     return (Util.mc.func_175606_aa() == entityPlayerSP);
/*     */   }
/*     */   
/*     */   @Inject(method = {"onUpdate"}, at = {@At("HEAD")})
/*     */   private void updateHook(CallbackInfo info) {
/*  65 */     PlayerUpdateEvent playerUpdateEvent = new PlayerUpdateEvent();
/*  66 */     MinecraftForge.EVENT_BUS.post((Event)playerUpdateEvent);
/*     */   }
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_175161_p();
/*     */   
/*     */   @Inject(method = {"onUpdate"}, at = {@At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.onUpdateWalkingPlayer()V", ordinal = 0, shift = At.Shift.AFTER)}, cancellable = true)
/*     */   private void PostUpdateHook(CallbackInfo info) {
/*  74 */     if (this.updateLock) {
/*     */       return;
/*     */     }
/*  77 */     PostPlayerUpdateEvent playerUpdateEvent = new PostPlayerUpdateEvent();
/*  78 */     MinecraftForge.EVENT_BUS.post((Event)playerUpdateEvent);
/*  79 */     if (playerUpdateEvent.isCanceled()) {
/*  80 */       info.cancel();
/*  81 */       if (playerUpdateEvent.getIterations() > 0) {
/*  82 */         for (int i = 0; i < playerUpdateEvent.getIterations(); i++) {
/*  83 */           this.updateLock = true;
/*  84 */           func_70071_h_();
/*  85 */           this.updateLock = false;
/*  86 */           func_175161_p();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Redirect(method = {"updateEntityActionState"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"))
/*     */   private boolean redirectIsCurrentViewEntity2(EntityPlayerSP entityPlayerSP) {
/*  95 */     Minecraft mc = Minecraft.func_71410_x();
/*  96 */     FreecamEvent event = new FreecamEvent();
/*  97 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  98 */     if (event.isCanceled()) {
/*  99 */       return (entityPlayerSP == mc.field_71439_g);
/*     */     }
/* 101 */     return (mc.func_175606_aa() == entityPlayerSP);
/*     */   }
/*     */   
/*     */   @Inject(method = {"pushOutOfBlocks"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void pushOutOfBlocksHook(double x, double y, double z, CallbackInfoReturnable<Boolean> info) {
/* 106 */     PushEvent event = new PushEvent();
/* 107 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 108 */     if (event.isCanceled()) {
/* 109 */       info.setReturnValue(Boolean.valueOf(false));
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"onUpdateWalkingPlayer"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void preMotion(CallbackInfo info) {
/* 115 */     EventSync event = new EventSync(this.field_70177_z, this.field_70125_A, this.field_70122_E);
/* 116 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 117 */     EventSprint e = new EventSprint(func_70051_ag());
/* 118 */     MinecraftForge.EVENT_BUS.post((Event)e);
/*     */     
/* 120 */     if (e.getSprintState() != ((IEntityPlayerSP)Util.mc.field_71439_g).getServerSprintState()) {
/* 121 */       if (e.getSprintState()) {
/* 122 */         this.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_SPRINTING));
/*     */       } else {
/* 124 */         this.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.STOP_SPRINTING));
/*     */       } 
/* 126 */       ((IEntityPlayerSP)Util.mc.field_71439_g).setServerSprintState(e.getSprintState());
/*     */     } 
/* 128 */     this.pre_sprint_state = ((IEntityPlayerSP)Util.mc.field_71439_g).getServerSprintState();
/* 129 */     EventManager.lock_sprint = true;
/* 130 */     if (event.isCanceled()) {
/* 131 */       info.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"move"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void movePre(MoverType type, double x, double y, double z, CallbackInfo info) {
/* 137 */     EventMove event = new EventMove(type, x, y, z);
/* 138 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 139 */     if (event.isCanceled()) {
/* 140 */       func_70091_d(type, event.get_x(), event.get_y(), event.get_z());
/* 141 */       info.cancel();
/*     */     } 
/*     */     
/* 144 */     if (((Speed)Thunderhack.moduleManager.getModuleByClass(Speed.class)).isEnabled() || ((Strafe)Thunderhack.moduleManager.getModuleByClass(Strafe.class)).isEnabled()) {
/* 145 */       AxisAlignedBB before = func_174813_aQ();
/*     */ 
/*     */       
/* 148 */       boolean predictGround = (!Util.mc.field_71441_e.func_184144_a((Entity)Util.mc.field_71439_g, Util.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -0.228D, 0.0D)).isEmpty() && this.field_70143_R > 0.1F && !Util.mc.field_71439_g.field_70122_E);
/*     */       
/* 150 */       MatrixMove move = new MatrixMove(Util.mc.field_71439_g.field_70165_t, Util.mc.field_71439_g.field_70163_u, Util.mc.field_71439_g.field_70161_v, x, y, z, predictGround, before);
/* 151 */       MinecraftForge.EVENT_BUS.post((Event)move);
/* 152 */       if (move.isCanceled()) {
/* 153 */         func_70091_d(type, move.getMotionX(), move.getMotionY(), move.getMotionZ());
/* 154 */         info.cancel();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"onUpdateWalkingPlayer"}, at = {@At("RETURN")})
/*     */   private void postMotion(CallbackInfo info) {
/* 162 */     ((IEntityPlayerSP)Util.mc.field_71439_g).setServerSprintState(this.pre_sprint_state);
/* 163 */     EventManager.lock_sprint = false;
/*     */     
/* 165 */     EventPostSync event = new EventPostSync();
/* 166 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     
/* 168 */     if (!event.getPostEvents().isEmpty()) {
/* 169 */       for (Runnable runnable : event.getPostEvents()) {
/* 170 */         Minecraft.func_71410_x().func_152344_a(runnable);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAuraCallback(Runnable auraCallBack) {
/* 177 */     this.auraCallBack = auraCallBack;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinEntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */