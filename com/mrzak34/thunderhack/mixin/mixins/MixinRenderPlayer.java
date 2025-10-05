/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.commands.ChangeSkinCommand;
/*     */ import com.mrzak34.thunderhack.events.FreecamEvent;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.RadarRewrite;
/*     */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*     */ import com.mrzak34.thunderhack.modules.render.Models;
/*     */ import com.mrzak34.thunderhack.modules.render.NameTags;
/*     */ import com.mrzak34.thunderhack.util.PNGtoResourceLocation;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ 
/*     */ @Mixin({RenderPlayer.class})
/*     */ public class MixinRenderPlayer
/*     */ {
/*  29 */   private final ResourceLocation amogus = new ResourceLocation("textures/amogus.png");
/*  30 */   private final ResourceLocation rabbit = new ResourceLocation("textures/rabbit.png");
/*  31 */   private final ResourceLocation fred = new ResourceLocation("textures/freddy.png");
/*     */   
/*     */   private float renderPitch;
/*     */   private float renderYaw;
/*     */   private float renderHeadYaw;
/*     */   private float prevRenderHeadYaw;
/*     */   private float prevRenderPitch;
/*     */   
/*     */   @Inject(method = {"renderEntityName"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void renderEntityNameHook(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info) {
/*  41 */     if (((NameTags)Thunderhack.moduleManager.getModuleByClass(NameTags.class)).isEnabled()) {
/*  42 */       info.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Redirect(method = {"doRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;isUser()Z"))
/*     */   private boolean isUserRedirect(AbstractClientPlayer abstractClientPlayer) {
/*  48 */     Minecraft mc = Minecraft.func_71410_x();
/*  49 */     FreecamEvent event = new FreecamEvent();
/*  50 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  51 */     if (event.isCanceled()) {
/*  52 */       return (abstractClientPlayer.func_175144_cb() && abstractClientPlayer == mc.func_175606_aa());
/*     */     }
/*  54 */     return abstractClientPlayer.func_175144_cb();
/*     */   }
/*     */   
/*     */   @Inject(method = {"doRender"}, at = {@At("HEAD")})
/*     */   private void rotateBegin(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
/*  59 */     if (((Boolean)((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).renderRotations.getValue()).booleanValue() && entity == (Minecraft.func_71410_x()).field_71439_g) {
/*  60 */       if ((Minecraft.func_71410_x()).field_71439_g.func_184187_bx() != null)
/*  61 */         return;  this.prevRenderHeadYaw = entity.field_70758_at;
/*  62 */       this.prevRenderPitch = entity.field_70127_C;
/*  63 */       this.renderPitch = entity.field_70125_A;
/*  64 */       this.renderYaw = entity.field_70177_z;
/*  65 */       this.renderHeadYaw = entity.field_70759_as;
/*  66 */       float interpYaw = (float)RadarRewrite.interp(Thunderhack.rotationManager.visualYaw, Thunderhack.rotationManager.prevVisualYaw);
/*  67 */       float interpPitch = (float)RadarRewrite.interp(Thunderhack.rotationManager.visualPitch, Thunderhack.rotationManager.prevVisualPitch);
/*  68 */       entity.field_70125_A = interpPitch;
/*  69 */       entity.field_70127_C = interpPitch;
/*  70 */       entity.field_70177_z = interpYaw;
/*  71 */       entity.field_70759_as = interpYaw;
/*  72 */       entity.field_70758_at = interpYaw;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"doRender"}, at = {@At("RETURN")})
/*     */   private void rotateEnd(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
/*  78 */     if (((Boolean)((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).renderRotations.getValue()).booleanValue() && entity == (Minecraft.func_71410_x()).field_71439_g) {
/*  79 */       if ((Minecraft.func_71410_x()).field_71439_g.func_184187_bx() != null)
/*  80 */         return;  entity.field_70125_A = this.renderPitch;
/*  81 */       entity.field_70177_z = this.renderYaw;
/*  82 */       entity.field_70759_as = this.renderHeadYaw;
/*  83 */       entity.field_70758_at = this.prevRenderHeadYaw;
/*  84 */       entity.field_70127_C = this.prevRenderPitch;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"getEntityTexture"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void getEntityTexture(AbstractClientPlayer entity, CallbackInfoReturnable<ResourceLocation> ci) {
/*  90 */     if (((Models)Thunderhack.moduleManager.getModuleByClass(Models.class)).isEnabled() && (!((Boolean)((Models)Thunderhack.moduleManager.getModuleByClass(Models.class)).onlySelf.getValue()).booleanValue() || entity == (Minecraft.func_71410_x()).field_71439_g || (Thunderhack.friendManager.isFriend(entity.func_70005_c_()) && ((Boolean)((Models)Thunderhack.moduleManager.getModuleByClass(Models.class)).friends.getValue()).booleanValue()))) {
/*  91 */       if (((Models)Thunderhack.moduleManager.getModuleByClass(Models.class)).Mode.getValue() == Models.mode.Amogus) {
/*  92 */         ci.setReturnValue(this.amogus);
/*     */       }
/*     */       
/*  95 */       if (((Models)Thunderhack.moduleManager.getModuleByClass(Models.class)).Mode.getValue() == Models.mode.Rabbit) {
/*  96 */         ci.setReturnValue(this.rabbit);
/*     */       }
/*  98 */       if (((Models)Thunderhack.moduleManager.getModuleByClass(Models.class)).Mode.getValue() == Models.mode.Freddy) {
/*  99 */         ci.setReturnValue(this.fred);
/*     */       }
/*     */     }
/* 102 */     else if ((ChangeSkinCommand.getInstance()).changedplayers.contains(entity.func_70005_c_())) {
/* 103 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 104 */       ci.setReturnValue(PNGtoResourceLocation.getTexture3(entity.func_70005_c_(), "png"));
/*     */     } else {
/*     */       
/* 107 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 108 */       ci.setReturnValue(entity.func_110306_p());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinRenderPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */