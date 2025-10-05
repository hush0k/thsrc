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
/*    */ public final class SmokeOutlineShader
/*    */   extends FramebufferShader
/*    */ {
/* 18 */   public static final SmokeOutlineShader INSTANCE = new SmokeOutlineShader();
/*    */ 
/*    */   
/* 21 */   public float time = 0.0F;
/*    */   
/*    */   public SmokeOutlineShader() {
/* 24 */     super("smokeOutline.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 29 */     setupUniform("texture");
/* 30 */     setupUniform("texelSize");
/* 31 */     setupUniform("divider");
/* 32 */     setupUniform("radius");
/* 33 */     setupUniform("maxSample");
/* 34 */     setupUniform("alpha0");
/* 35 */     setupUniform("resolution");
/* 36 */     setupUniform("time");
/* 37 */     setupUniform("first");
/* 38 */     setupUniform("second");
/* 39 */     setupUniform("third");
/* 40 */     setupUniform("oct");
/*    */   }
/*    */   
/*    */   public void updateUniforms(Color first, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, Color second, Color third, int oct) {
/* 44 */     GL20.glUniform1i(getUniform("texture"), 0);
/* 45 */     GL20.glUniform2f(getUniform("texelSize"), 1.0F / this.mc.field_71443_c * radius * quality, 1.0F / this.mc.field_71440_d * radius * quality);
/* 46 */     GL20.glUniform1f(getUniform("divider"), 140.0F);
/* 47 */     GL20.glUniform1f(getUniform("radius"), radius);
/* 48 */     GL20.glUniform1f(getUniform("maxSample"), 10.0F);
/* 49 */     GL20.glUniform1f(getUniform("alpha0"), gradientAlpha ? -1.0F : (alphaOutline / 255.0F));
/* 50 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/* 51 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 52 */     GL20.glUniform4f(getUniform("first"), first.getRed() / 255.0F * 5.0F, first.getGreen() / 255.0F * 5.0F, first.getBlue() / 255.0F * 5.0F, first.getAlpha() / 255.0F);
/* 53 */     GL20.glUniform3f(getUniform("second"), second.getRed() / 255.0F * 5.0F, second.getGreen() / 255.0F * 5.0F, second.getBlue() / 255.0F * 5.0F);
/* 54 */     GL20.glUniform3f(getUniform("third"), third.getRed() / 255.0F * 5.0F, third.getGreen() / 255.0F * 5.0F, third.getBlue() / 255.0F * 5.0F);
/* 55 */     GL20.glUniform1i(getUniform("oct"), oct);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, Color second, Color third, int oct) {
/* 60 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 61 */     this.framebuffer.func_147609_e();
/* 62 */     GL11.glEnable(3042);
/* 63 */     GL11.glBlendFunc(770, 771);
/* 64 */     this.mc.func_147110_a().func_147610_a(true);
/* 65 */     this.mc.field_71460_t.func_175072_h();
/* 66 */     RenderHelper.func_74518_a();
/* 67 */     startShader(color, radius, quality, gradientAlpha, alphaOutline, duplicate, second, third, oct);
/* 68 */     this.mc.field_71460_t.func_78478_c();
/* 69 */     drawFramebuffer(this.framebuffer);
/* 70 */     stopShader();
/* 71 */     this.mc.field_71460_t.func_175072_h();
/* 72 */     GlStateManager.func_179121_F();
/* 73 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, Color second, Color third, int oct) {
/* 77 */     GL11.glPushMatrix();
/* 78 */     GL20.glUseProgram(this.program);
/* 79 */     if (this.uniformsMap == null) {
/* 80 */       this.uniformsMap = new HashMap<>();
/* 81 */       setupUniforms();
/*    */     } 
/* 83 */     updateUniforms(color, radius, quality, gradientAlpha, alphaOutline, duplicate, second, third, oct);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 87 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\outline\SmokeOutlineShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */