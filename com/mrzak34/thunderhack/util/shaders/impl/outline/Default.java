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
/*    */ public final class Default
/*    */   extends FramebufferShader
/*    */ {
/* 18 */   public static final Default INSTANCE = new Default();
/*    */ 
/*    */   
/* 21 */   public float time = 0.0F;
/*    */   
/*    */   public Default() {
/* 24 */     super("default.frag");
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
/*    */   }
/*    */   
/*    */   public void updateUniforms(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate) {
/* 41 */     GL20.glUniform1i(getUniform("texture"), 0);
/* 42 */     GL20.glUniform2f(getUniform("texelSize"), 1.0F / this.mc.field_71443_c * radius * quality, 1.0F / this.mc.field_71440_d * radius * quality);
/* 43 */     GL20.glUniform3f(getUniform("color"), color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
/* 44 */     GL20.glUniform1f(getUniform("divider"), 140.0F);
/* 45 */     GL20.glUniform1f(getUniform("radius"), radius);
/* 46 */     GL20.glUniform1f(getUniform("maxSample"), 10.0F);
/* 47 */     GL20.glUniform1f(getUniform("alpha0"), gradientAlpha ? -1.0F : (alphaOutline / 255.0F));
/* 48 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/* 49 */     GL20.glUniform1f(getUniform("time"), this.time);
/*    */   }
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate) {
/* 53 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 54 */     this.framebuffer.func_147609_e();
/* 55 */     GL11.glEnable(3042);
/* 56 */     GL11.glBlendFunc(770, 771);
/* 57 */     this.mc.func_147110_a().func_147610_a(true);
/* 58 */     this.mc.field_71460_t.func_175072_h();
/* 59 */     RenderHelper.func_74518_a();
/* 60 */     startShader(color, radius, quality, gradientAlpha, alphaOutline, duplicate);
/* 61 */     this.mc.field_71460_t.func_78478_c();
/* 62 */     drawFramebuffer(this.framebuffer);
/* 63 */     stopShader();
/* 64 */     this.mc.field_71460_t.func_175072_h();
/* 65 */     GlStateManager.func_179121_F();
/* 66 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate) {
/* 70 */     GL11.glPushMatrix();
/* 71 */     GL20.glUseProgram(this.program);
/* 72 */     if (this.uniformsMap == null) {
/* 73 */       this.uniformsMap = new HashMap<>();
/* 74 */       setupUniforms();
/*    */     } 
/* 76 */     updateUniforms(color, radius, quality, gradientAlpha, alphaOutline, duplicate);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 80 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\outline\Default.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */