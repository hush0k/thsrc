/*    */ package com.mrzak34.thunderhack.util.shaders.impl.fill;
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
/*    */ 
/*    */ public class PhobosShader
/*    */   extends FramebufferShader
/*    */ {
/* 19 */   public static final PhobosShader INSTANCE = new PhobosShader();
/*    */   
/*    */   public float time;
/*    */ 
/*    */   
/*    */   public PhobosShader() {
/* 25 */     super("phobos.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 30 */     setupUniform("resolution");
/* 31 */     setupUniform("time");
/* 32 */     setupUniform("color");
/* 33 */     setupUniform("texelSize");
/* 34 */     setupUniform("texture");
/*    */   }
/*    */   
/*    */   public void updateUniforms(float duplicate, Color color, int lines, double tau) {
/* 38 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/* 39 */     GL20.glUniform1i(getUniform("texture"), 0);
/* 40 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 41 */     GL20.glUniform4f(getUniform("color"), color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/* 42 */     GL20.glUniform2f(getUniform("texelSize"), 1.0F / this.mc.field_71443_c * this.radius * this.quality, 1.0F / this.mc.field_71440_d * this.radius * this.quality);
/*    */   }
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, float duplicate, int lines, double tau) {
/* 46 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 47 */     this.framebuffer.func_147609_e();
/* 48 */     GL11.glEnable(3042);
/* 49 */     GL11.glBlendFunc(770, 771);
/* 50 */     this.mc.func_147110_a().func_147610_a(true);
/* 51 */     this.red = color.getRed() / 255.0F;
/* 52 */     this.green = color.getGreen() / 255.0F;
/* 53 */     this.blue = color.getBlue() / 255.0F;
/* 54 */     this.alpha = color.getAlpha() / 255.0F;
/* 55 */     this.radius = radius;
/* 56 */     this.quality = quality;
/* 57 */     this.mc.field_71460_t.func_175072_h();
/* 58 */     RenderHelper.func_74518_a();
/* 59 */     startShader(duplicate, color, lines, tau);
/* 60 */     this.mc.field_71460_t.func_78478_c();
/* 61 */     drawFramebuffer(this.framebuffer);
/* 62 */     stopShader();
/* 63 */     this.mc.field_71460_t.func_175072_h();
/* 64 */     GlStateManager.func_179121_F();
/* 65 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(float duplicate, Color color, int lines, double tau) {
/* 69 */     GL11.glPushMatrix();
/* 70 */     GL20.glUseProgram(this.program);
/* 71 */     if (this.uniformsMap == null) {
/* 72 */       this.uniformsMap = new HashMap<>();
/* 73 */       setupUniforms();
/*    */     } 
/* 75 */     updateUniforms(duplicate, color, lines, tau);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 79 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\fill\PhobosShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */