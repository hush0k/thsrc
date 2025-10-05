/*    */ package com.mrzak34.thunderhack.util.shaders.impl.outline;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.shaders.FramebufferShader;
/*    */ import java.awt.Color;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class GradientOutlineShader
/*    */   extends FramebufferShader
/*    */ {
/* 18 */   public static final GradientOutlineShader INSTANCE = new GradientOutlineShader();
/*    */ 
/*    */   
/* 21 */   public float time = 0.0F;
/*    */   
/*    */   public GradientOutlineShader() {
/* 24 */     super("outlineGradient.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 29 */     setupUniform("texture");
/* 30 */     setupUniform("texelSize");
/* 31 */     setupUniform("color");
/* 32 */     setupUniform("divider");
/* 33 */     setupUniform("radius");
/* 34 */     setupUniform("maxSample");
/* 35 */     setupUniform("alpha0");
/* 36 */     setupUniform("resolution");
/* 37 */     setupUniform("time");
/* 38 */     setupUniform("moreGradient");
/* 39 */     setupUniform("Creepy");
/* 40 */     setupUniform("alpha");
/* 41 */     setupUniform("NUM_OCTAVES");
/*    */   }
/*    */   
/*    */   public void updateUniforms(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, float moreGradient, float creepy, float alpha, int numOctaves) {
/* 45 */     GL20.glUniform1i(getUniform("texture"), 0);
/* 46 */     GL20.glUniform2f(getUniform("texelSize"), 1.0F / this.mc.field_71443_c * radius * quality, 1.0F / this.mc.field_71440_d * radius * quality);
/* 47 */     GL20.glUniform3f(getUniform("color"), color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
/* 48 */     GL20.glUniform1f(getUniform("divider"), 140.0F);
/* 49 */     GL20.glUniform1f(getUniform("radius"), radius);
/* 50 */     GL20.glUniform1f(getUniform("maxSample"), 10.0F);
/* 51 */     GL20.glUniform1f(getUniform("alpha0"), gradientAlpha ? -1.0F : (alphaOutline / 255.0F));
/* 52 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/* 53 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 54 */     GL20.glUniform1f(getUniform("moreGradient"), moreGradient);
/* 55 */     GL20.glUniform1f(getUniform("Creepy"), creepy);
/* 56 */     GL20.glUniform1f(getUniform("alpha"), alpha);
/* 57 */     GL20.glUniform1i(getUniform("NUM_OCTAVES"), numOctaves);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, float moreGradient, float creepy, float alpha, int numOctaves) {
/* 62 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 63 */     this.framebuffer.func_147609_e();
/* 64 */     GL11.glEnable(3042);
/* 65 */     GL11.glBlendFunc(770, 771);
/* 66 */     this.mc.func_147110_a().func_147610_a(true);
/* 67 */     this.mc.field_71460_t.func_175072_h();
/* 68 */     RenderHelper.func_74518_a();
/* 69 */     startShader(color, radius, quality, gradientAlpha, alphaOutline, duplicate, moreGradient, creepy, alpha, numOctaves);
/* 70 */     this.mc.field_71460_t.func_78478_c();
/* 71 */     drawFramebuffer(this.framebuffer);
/* 72 */     stopShader();
/* 73 */     this.mc.field_71460_t.func_175072_h();
/* 74 */     GlStateManager.func_179121_F();
/* 75 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, float moreGradient, float creepy, float alpha, int numOctaves) {
/* 79 */     GL11.glPushMatrix();
/* 80 */     GL20.glUseProgram(this.program);
/* 81 */     if (this.uniformsMap == null) {
/* 82 */       this.uniformsMap = new HashMap<>();
/* 83 */       setupUniforms();
/*    */     } 
/* 85 */     updateUniforms(color, radius, quality, gradientAlpha, alphaOutline, duplicate, moreGradient, creepy, alpha, numOctaves);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 89 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\outline\GradientOutlineShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */