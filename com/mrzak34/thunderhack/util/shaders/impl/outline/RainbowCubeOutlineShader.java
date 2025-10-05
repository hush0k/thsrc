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
/*    */ public final class RainbowCubeOutlineShader
/*    */   extends FramebufferShader
/*    */ {
/* 18 */   public static final RainbowCubeOutlineShader INSTANCE = new RainbowCubeOutlineShader();
/*    */ 
/*    */   
/* 21 */   public float time = 0.0F;
/*    */   
/*    */   public RainbowCubeOutlineShader() {
/* 24 */     super("rainbowCubeOutline.frag");
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
/* 38 */     setupUniform("WAVELENGTH");
/* 39 */     setupUniform("R");
/* 40 */     setupUniform("G");
/* 41 */     setupUniform("B");
/* 42 */     setupUniform("RSTART");
/* 43 */     setupUniform("GSTART");
/* 44 */     setupUniform("BSTART");
/* 45 */     setupUniform("alpha");
/*    */   }
/*    */   
/*    */   public void updateUniforms(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, Color start, int wave, int rStart, int gStart, int bStart) {
/* 49 */     GL20.glUniform1i(getUniform("texture"), 0);
/* 50 */     GL20.glUniform2f(getUniform("texelSize"), 1.0F / this.mc.field_71443_c * radius * quality, 1.0F / this.mc.field_71440_d * radius * quality);
/* 51 */     GL20.glUniform1f(getUniform("divider"), 140.0F);
/* 52 */     GL20.glUniform1f(getUniform("radius"), radius);
/* 53 */     GL20.glUniform1f(getUniform("maxSample"), 10.0F);
/* 54 */     GL20.glUniform1f(getUniform("alpha0"), gradientAlpha ? -1.0F : (alphaOutline / 255.0F));
/* 55 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/* 56 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 57 */     GL20.glUniform1f(getUniform("alpha"), start.getAlpha() / 255.0F);
/* 58 */     GL20.glUniform1f(getUniform("WAVELENGTH"), wave);
/* 59 */     GL20.glUniform1i(getUniform("R"), start.getRed());
/* 60 */     GL20.glUniform1i(getUniform("G"), start.getGreen());
/* 61 */     GL20.glUniform1i(getUniform("B"), start.getBlue());
/* 62 */     GL20.glUniform1i(getUniform("RSTART"), rStart);
/* 63 */     GL20.glUniform1i(getUniform("GSTART"), gStart);
/* 64 */     GL20.glUniform1i(getUniform("BSTART"), bStart);
/*    */   }
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, Color start, int wave, int rStart, int gStart, int bStart) {
/* 68 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 69 */     this.framebuffer.func_147609_e();
/* 70 */     GL11.glEnable(3042);
/* 71 */     GL11.glBlendFunc(770, 771);
/* 72 */     this.mc.func_147110_a().func_147610_a(true);
/* 73 */     this.mc.field_71460_t.func_175072_h();
/* 74 */     RenderHelper.func_74518_a();
/* 75 */     startShader(color, radius, quality, gradientAlpha, alphaOutline, duplicate, start, wave, rStart, gStart, bStart);
/* 76 */     this.mc.field_71460_t.func_78478_c();
/* 77 */     drawFramebuffer(this.framebuffer);
/* 78 */     stopShader();
/* 79 */     this.mc.field_71460_t.func_175072_h();
/* 80 */     GlStateManager.func_179121_F();
/* 81 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, Color start, int wave, int rStart, int gStart, int bStart) {
/* 85 */     GL11.glPushMatrix();
/* 86 */     GL20.glUseProgram(this.program);
/* 87 */     if (this.uniformsMap == null) {
/* 88 */       this.uniformsMap = new HashMap<>();
/* 89 */       setupUniforms();
/*    */     } 
/* 91 */     updateUniforms(color, radius, quality, gradientAlpha, alphaOutline, duplicate, start, wave, rStart, gStart, bStart);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 95 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\outline\RainbowCubeOutlineShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */