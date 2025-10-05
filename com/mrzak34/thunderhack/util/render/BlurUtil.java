/*     */ package com.mrzak34.thunderhack.util.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.nio.FloatBuffer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlurUtil
/*     */ {
/*  18 */   public static ShaderUtil blurShader = new ShaderUtil("blurShader");
/*  19 */   public static Framebuffer framebuffer = new Framebuffer(1, 1, false);
/*     */ 
/*     */   
/*     */   public static void uninitStencilBuffer() {
/*  23 */     GL11.glDisable(2960);
/*     */   }
/*     */   
/*     */   public static void drawBlur(float radius, Runnable data) {
/*  27 */     initStencilToWrite();
/*  28 */     data.run();
/*  29 */     readStencilBuffer(1);
/*  30 */     renderBlur(radius);
/*  31 */     uninitStencilBuffer();
/*     */   }
/*     */   
/*     */   public static void renderBlur(float radius) {
/*  35 */     GlStateManager.func_179147_l();
/*  36 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  37 */     OpenGlHelper.func_148821_a(770, 771, 1, 0);
/*  38 */     framebuffer = createFrameBuffer(framebuffer);
/*  39 */     framebuffer.func_147614_f();
/*  40 */     framebuffer.func_147610_a(true);
/*  41 */     blurShader.init();
/*  42 */     setupUniforms(1.0F, 0.0F, radius);
/*  43 */     bindTexture((Util.mc.func_147110_a()).field_147617_g);
/*  44 */     ShaderUtil.drawQuads();
/*  45 */     framebuffer.func_147609_e();
/*  46 */     blurShader.unload();
/*  47 */     Util.mc.func_147110_a().func_147610_a(true);
/*  48 */     blurShader.init();
/*  49 */     setupUniforms(0.0F, 1.0F, radius);
/*  50 */     bindTexture(framebuffer.field_147617_g);
/*  51 */     ShaderUtil.drawQuads();
/*  52 */     blurShader.unload();
/*  53 */     GlStateManager.func_179117_G();
/*  54 */     GlStateManager.func_179144_i(0);
/*     */   }
/*     */   
/*     */   public static void bindTexture(int texture) {
/*  58 */     GL11.glBindTexture(3553, texture);
/*     */   }
/*     */   
/*     */   public static void setupUniforms(float dir1, float dir2, float radius) {
/*  62 */     blurShader.setUniformi("textureIn", new int[] { 0 });
/*  63 */     blurShader.setUniformf("texelSize", new float[] { 1.0F / Util.mc.field_71443_c, 1.0F / Util.mc.field_71440_d });
/*  64 */     blurShader.setUniformf("direction", new float[] { dir1, dir2 });
/*  65 */     blurShader.setUniformf("radius", new float[] { radius });
/*  66 */     FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
/*     */     
/*  68 */     for (int i = 0; i <= radius; i++) {
/*  69 */       weightBuffer.put(calculateGaussianValue(i, radius / 2.0F));
/*     */     }
/*     */     
/*  72 */     weightBuffer.rewind();
/*  73 */     GL20.glUniform1(blurShader.getUniform("weights"), weightBuffer);
/*     */   }
/*     */   
/*     */   public static float calculateGaussianValue(float x, float sigma) {
/*  77 */     double PI = 3.141592653D;
/*  78 */     double output = 1.0D / Math.sqrt(2.0D * PI * (sigma * sigma));
/*  79 */     return (float)(output * Math.exp(-(x * x) / 2.0D * (sigma * sigma)));
/*     */   }
/*     */   
/*     */   public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
/*  83 */     if (framebuffer != null && framebuffer.field_147621_c == Util.mc.field_71443_c && framebuffer.field_147618_d == Util.mc.field_71440_d) {
/*  84 */       return framebuffer;
/*     */     }
/*  86 */     if (framebuffer != null) {
/*  87 */       framebuffer.func_147608_a();
/*     */     }
/*     */     
/*  90 */     return new Framebuffer(Util.mc.field_71443_c, Util.mc.field_71440_d, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initStencilToWrite() {
/*  95 */     Util.mc.func_147110_a().func_147610_a(false);
/*  96 */     checkSetupFBO(Util.mc.func_147110_a());
/*  97 */     GL11.glClear(1024);
/*  98 */     GL11.glEnable(2960);
/*  99 */     GL11.glStencilFunc(519, 1, 1);
/* 100 */     GL11.glStencilOp(7681, 7681, 7681);
/* 101 */     GL11.glColorMask(false, false, false, false);
/*     */   }
/*     */   
/*     */   public static void checkSetupFBO(Framebuffer framebuffer) {
/* 105 */     if (framebuffer != null && framebuffer.field_147624_h > -1) {
/* 106 */       setupFBO(framebuffer);
/* 107 */       framebuffer.field_147624_h = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setupFBO(Framebuffer framebuffer) {
/* 112 */     EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.field_147624_h);
/* 113 */     int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
/* 114 */     EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilDepthBufferID);
/* 115 */     EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Util.mc.field_71443_c, Util.mc.field_71440_d);
/* 116 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilDepthBufferID);
/* 117 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilDepthBufferID);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void readStencilBuffer(int ref) {
/* 122 */     GL11.glColorMask(true, true, true, true);
/* 123 */     GL11.glStencilFunc(514, ref, 1);
/* 124 */     GL11.glStencilOp(7680, 7680, 7680);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\render\BlurUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */