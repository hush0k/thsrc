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
/*    */ public final class CircleOutlineShader
/*    */   extends FramebufferShader
/*    */ {
/* 18 */   public static final CircleOutlineShader INSTANCE = new CircleOutlineShader();
/*    */ 
/*    */   
/* 21 */   public float time = 0.0F;
/*    */   
/*    */   public CircleOutlineShader() {
/* 24 */     super("circleOutline.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 29 */     setupUniform("texture");
/* 30 */     setupUniform("texelSize");
/* 31 */     setupUniform("colors");
/* 32 */     setupUniform("divider");
/* 33 */     setupUniform("radius");
/* 34 */     setupUniform("maxSample");
/* 35 */     setupUniform("alpha0");
/* 36 */     setupUniform("resolution");
/* 37 */     setupUniform("time");
/* 38 */     setupUniform("PI");
/* 39 */     setupUniform("rad");
/*    */   }
/*    */   
/*    */   public void updateUniforms(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, float PI, float rad) {
/* 43 */     GL20.glUniform1i(getUniform("texture"), 0);
/* 44 */     GL20.glUniform2f(getUniform("texelSize"), 1.0F / this.mc.field_71443_c * radius * quality, 1.0F / this.mc.field_71440_d * radius * quality);
/* 45 */     GL20.glUniform3f(getUniform("colors"), color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
/* 46 */     GL20.glUniform1f(getUniform("divider"), 140.0F);
/* 47 */     GL20.glUniform1f(getUniform("radius"), radius);
/* 48 */     GL20.glUniform1f(getUniform("maxSample"), 10.0F);
/* 49 */     GL20.glUniform1f(getUniform("alpha0"), gradientAlpha ? -1.0F : (alphaOutline / 255.0F));
/* 50 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/* 51 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 52 */     GL20.glUniform1f(getUniform("PI"), PI);
/* 53 */     GL20.glUniform1f(getUniform("rad"), rad);
/*    */   }
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, float PI, float rad) {
/* 57 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 58 */     this.framebuffer.func_147609_e();
/* 59 */     GL11.glEnable(3042);
/* 60 */     GL11.glBlendFunc(770, 771);
/* 61 */     this.mc.func_147110_a().func_147610_a(true);
/* 62 */     this.mc.field_71460_t.func_175072_h();
/* 63 */     RenderHelper.func_74518_a();
/* 64 */     startShader(color, radius, quality, gradientAlpha, alphaOutline, duplicate, PI, rad);
/* 65 */     this.mc.field_71460_t.func_78478_c();
/* 66 */     drawFramebuffer(this.framebuffer);
/* 67 */     stopShader();
/* 68 */     this.mc.field_71460_t.func_175072_h();
/* 69 */     GlStateManager.func_179121_F();
/* 70 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, float PI, float rad) {
/* 74 */     GL11.glPushMatrix();
/* 75 */     GL20.glUseProgram(this.program);
/* 76 */     if (this.uniformsMap == null) {
/* 77 */       this.uniformsMap = new HashMap<>();
/* 78 */       setupUniforms();
/*    */     } 
/* 80 */     updateUniforms(color, radius, quality, gradientAlpha, alphaOutline, duplicate, PI, rad);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 84 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\outline\CircleOutlineShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */