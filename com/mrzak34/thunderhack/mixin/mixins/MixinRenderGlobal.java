/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.PostRenderEntitiesEvent;
/*     */ import com.mrzak34.thunderhack.modules.render.StorageEsp;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ @Mixin({RenderGlobal.class})
/*     */ public abstract class MixinRenderGlobal {
/*     */   private static void VZWQ(float width) {
/*  25 */     MqiP();
/*  26 */     GL11.glPushAttrib(1048575);
/*  27 */     GL11.glDisable(3008);
/*  28 */     GL11.glDisable(3553);
/*  29 */     GL11.glDisable(2896);
/*  30 */     GL11.glEnable(3042);
/*  31 */     GL11.glBlendFunc(770, 771);
/*  32 */     GL11.glLineWidth(width);
/*  33 */     GL11.glEnable(2848);
/*  34 */     GL11.glEnable(2960);
/*  35 */     GL11.glClear(1024);
/*  36 */     GL11.glClearStencil(15);
/*  37 */     GL11.glStencilFunc(512, 1, 15);
/*  38 */     GL11.glStencilOp(7681, 7681, 7681);
/*  39 */     GL11.glPolygonMode(1032, 6913);
/*     */   }
/*     */   
/*     */   private static void JLYv() {
/*  43 */     GL11.glStencilFunc(512, 0, 15);
/*  44 */     GL11.glStencilOp(7681, 7681, 7681);
/*  45 */     GL11.glPolygonMode(1032, 6914);
/*     */   }
/*     */   
/*     */   private static void feKn() {
/*  49 */     GL11.glStencilFunc(514, 1, 15);
/*  50 */     GL11.glStencilOp(7680, 7680, 7680);
/*  51 */     GL11.glPolygonMode(1032, 6913);
/*     */   }
/*     */   
/*     */   private static void mptE() {
/*  55 */     GL11.glDepthMask(false);
/*  56 */     GL11.glDisable(2929);
/*  57 */     GL11.glEnable(10754);
/*  58 */     GL11.glPolygonOffset(1.0F, -2000000.0F);
/*  59 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/*     */   }
/*     */   
/*     */   private static void VdOT() {
/*  63 */     GL11.glPolygonOffset(1.0F, 2000000.0F);
/*  64 */     GL11.glDisable(10754);
/*  65 */     GL11.glEnable(2929);
/*  66 */     GL11.glDepthMask(true);
/*  67 */     GL11.glDisable(2960);
/*  68 */     GL11.glDisable(2848);
/*  69 */     GL11.glHint(3154, 4352);
/*  70 */     GL11.glEnable(3042);
/*  71 */     GL11.glEnable(2896);
/*  72 */     GL11.glEnable(3553);
/*  73 */     GL11.glEnable(3008);
/*  74 */     GL11.glPopAttrib();
/*     */   }
/*     */   
/*     */   private static void MqiP() {
/*  78 */     Framebuffer framebuffer = Util.mc.func_147110_a();
/*  79 */     if (framebuffer != null && framebuffer.field_147624_h > -1) {
/*  80 */       Cvvp(framebuffer);
/*  81 */       framebuffer.field_147624_h = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void Cvvp(Framebuffer framebuffer) {
/*  86 */     EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.field_147624_h);
/*  87 */     int glGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
/*  88 */     EXTFramebufferObject.glBindRenderbufferEXT(36161, glGenRenderbuffersEXT);
/*  89 */     EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, (Minecraft.func_71410_x()).field_71443_c, (Minecraft.func_71410_x()).field_71440_d);
/*  90 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, glGenRenderbuffersEXT);
/*  91 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, glGenRenderbuffersEXT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"renderEntities"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos$PooledMutableBlockPos;release()V", shift = At.Shift.BEFORE)})
/*     */   private void renderEntitiesHook(Entity renderViewEntity, ICamera camera, float partialTicks, CallbackInfo ci) {
/* 104 */     MinecraftForge.EVENT_BUS.post((Event)new PostRenderEntitiesEvent(partialTicks, 0));
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderEntities"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;preRenderDamagedBlocks()V", shift = At.Shift.BEFORE)})
/*     */   public void renderEntities(Entity entity, ICamera camera, float n, CallbackInfo callbackInfo) {
/* 109 */     StorageEsp esp = (StorageEsp)Thunderhack.moduleManager.getModuleByClass(StorageEsp.class);
/* 110 */     if ((esp.isEnabled() && esp.mode.getValue() == StorageEsp.Mode.ShaderBox) || esp.mode.getValue() == StorageEsp.Mode.ShaderOutline) {
/* 111 */       esp.renderNormal(n);
/* 112 */       VZWQ(((Float)esp.lineWidth.getValue()).floatValue());
/* 113 */       esp.renderNormal(n);
/* 114 */       JLYv();
/* 115 */       esp.renderColor(n);
/* 116 */       feKn();
/* 117 */       mptE();
/* 118 */       esp.renderColor(n);
/* 119 */       VdOT();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinRenderGlobal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */