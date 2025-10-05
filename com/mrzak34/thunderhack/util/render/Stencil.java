/*    */ package com.mrzak34.thunderhack.util.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ import org.lwjgl.opengl.EXTFramebufferObject;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class Stencil
/*    */ {
/*    */   public static void dispose() {
/* 14 */     GL11.glDisable(2960);
/* 15 */     GlStateManager.func_179118_c();
/* 16 */     GlStateManager.func_179084_k();
/*    */   }
/*    */   
/*    */   public static void erase(boolean invert) {
/* 20 */     GL11.glStencilFunc(invert ? 514 : 517, 1, 65535);
/* 21 */     GL11.glStencilOp(7680, 7680, 7681);
/* 22 */     GlStateManager.func_179135_a(true, true, true, true);
/* 23 */     GlStateManager.func_179141_d();
/* 24 */     GlStateManager.func_179147_l();
/* 25 */     GL11.glAlphaFunc(516, 0.0F);
/*    */   }
/*    */   
/*    */   public static void write(boolean renderClipLayer) {
/* 29 */     checkSetupFBO();
/* 30 */     GL11.glClearStencil(0);
/* 31 */     GL11.glClear(1024);
/* 32 */     GL11.glEnable(2960);
/* 33 */     GL11.glStencilFunc(519, 1, 65535);
/* 34 */     GL11.glStencilOp(7680, 7680, 7681);
/* 35 */     if (!renderClipLayer) {
/* 36 */       GlStateManager.func_179135_a(false, false, false, false);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void write(boolean renderClipLayer, Framebuffer fb) {
/* 41 */     checkSetupFBO(fb);
/* 42 */     GL11.glClearStencil(0);
/* 43 */     GL11.glClear(1024);
/* 44 */     GL11.glEnable(2960);
/* 45 */     GL11.glStencilFunc(519, 1, 65535);
/* 46 */     GL11.glStencilOp(7680, 7680, 7681);
/* 47 */     if (!renderClipLayer) {
/* 48 */       GlStateManager.func_179135_a(false, false, false, false);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void checkSetupFBO() {
/* 53 */     Framebuffer fbo = Util.mc.func_147110_a();
/* 54 */     if (fbo != null && fbo.field_147624_h > -1) {
/* 55 */       setupFBO(fbo);
/* 56 */       fbo.field_147624_h = -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void checkSetupFBO(Framebuffer fbo) {
/* 61 */     if (fbo != null && fbo.field_147624_h > -1) {
/* 62 */       setupFBO(fbo);
/* 63 */       fbo.field_147624_h = -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void setupFBO(Framebuffer fbo) {
/* 68 */     EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.field_147624_h);
/* 69 */     int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
/* 70 */     EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
/* 71 */     EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, (Minecraft.func_71410_x()).field_71443_c, (Minecraft.func_71410_x()).field_71440_d);
/* 72 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
/* 73 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\render\Stencil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */