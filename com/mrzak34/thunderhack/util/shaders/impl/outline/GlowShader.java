/*    */ package com.mrzak34.thunderhack.util.shaders.impl.outline;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.shaders.FramebufferShader;
/*    */ import java.awt.Color;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class GlowShader
/*    */   extends FramebufferShader
/*    */ {
/* 17 */   public static final GlowShader INSTANCE = new GlowShader();
/*    */ 
/*    */   
/* 20 */   public float time = 0.0F;
/*    */   
/*    */   public GlowShader() {
/* 23 */     super("glow.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 28 */     setupUniform("texture");
/* 29 */     setupUniform("texelSize");
/* 30 */     setupUniform("color");
/* 31 */     setupUniform("divider");
/* 32 */     setupUniform("radius");
/* 33 */     setupUniform("maxSample");
/* 34 */     setupUniform("alpha0");
/*    */   }
/*    */   
/*    */   public void updateUniforms(Color color, float radius, float quality, boolean gradientAlpha, int alpha) {
/* 38 */     GL20.glUniform1i(getUniform("texture"), 0);
/* 39 */     GL20.glUniform2f(getUniform("texelSize"), 1.0F / this.mc.field_71443_c * radius * quality, 1.0F / this.mc.field_71440_d * radius * quality);
/* 40 */     GL20.glUniform3f(getUniform("color"), color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
/* 41 */     GL20.glUniform1f(getUniform("divider"), 140.0F);
/* 42 */     GL20.glUniform1f(getUniform("radius"), radius);
/* 43 */     GL20.glUniform1f(getUniform("maxSample"), 10.0F);
/* 44 */     GL20.glUniform1f(getUniform("alpha0"), gradientAlpha ? -1.0F : (alpha / 255.0F));
/*    */   }
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, boolean gradientAlpha, int alpha) {
/* 48 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 49 */     this.framebuffer.func_147609_e();
/* 50 */     GL11.glEnable(3042);
/* 51 */     GL11.glBlendFunc(770, 771);
/* 52 */     this.mc.func_147110_a().func_147610_a(true);
/* 53 */     this.mc.field_71460_t.func_175072_h();
/* 54 */     RenderHelper.func_74518_a();
/* 55 */     startShader(color, radius, quality, gradientAlpha, alpha);
/* 56 */     this.mc.field_71460_t.func_78478_c();
/* 57 */     drawFramebuffer(this.framebuffer);
/* 58 */     stopShader();
/* 59 */     this.mc.field_71460_t.func_175072_h();
/* 60 */     GlStateManager.func_179121_F();
/* 61 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(Color color, float radius, float quality, boolean gradientAlpha, int alpha) {
/* 65 */     GL11.glPushMatrix();
/* 66 */     GL20.glUseProgram(this.program);
/* 67 */     if (this.uniformsMap == null) {
/* 68 */       this.uniformsMap = new HashMap<>();
/* 69 */       setupUniforms();
/*    */     } 
/* 71 */     updateUniforms(color, radius, quality, gradientAlpha, alpha);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\outline\GlowShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */