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
/*    */ public class GradientShader
/*    */   extends FramebufferShader
/*    */ {
/* 19 */   public static final GradientShader INSTANCE = new GradientShader();
/*    */   
/*    */   public float time;
/*    */ 
/*    */   
/*    */   public GradientShader() {
/* 25 */     super("gradient.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 30 */     setupUniform("resolution");
/* 31 */     setupUniform("time");
/* 32 */     setupUniform("moreGradient");
/* 33 */     setupUniform("Creepy");
/* 34 */     setupUniform("alpha");
/* 35 */     setupUniform("NUM_OCTAVES");
/*    */   }
/*    */   
/*    */   public void updateUniforms(float duplicate, float moreGradient, float creepy, float alpha, int numOctaves) {
/* 39 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/* 40 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 41 */     GL20.glUniform1f(getUniform("moreGradient"), moreGradient);
/* 42 */     GL20.glUniform1f(getUniform("Creepy"), creepy);
/* 43 */     GL20.glUniform1f(getUniform("alpha"), alpha);
/* 44 */     GL20.glUniform1i(getUniform("NUM_OCTAVES"), numOctaves);
/*    */   }
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, float duplicate, float moreGradient, float creepy, float alpha, int numOctaves) {
/* 48 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 49 */     this.framebuffer.func_147609_e();
/* 50 */     GL11.glEnable(3042);
/* 51 */     GL11.glBlendFunc(770, 771);
/* 52 */     this.mc.func_147110_a().func_147610_a(true);
/* 53 */     this.red = color.getRed() / 255.0F;
/* 54 */     this.green = color.getGreen() / 255.0F;
/* 55 */     this.blue = color.getBlue() / 255.0F;
/* 56 */     this.radius = radius;
/* 57 */     this.quality = quality;
/* 58 */     this.mc.field_71460_t.func_175072_h();
/* 59 */     RenderHelper.func_74518_a();
/* 60 */     startShader(duplicate, moreGradient, creepy, alpha, numOctaves);
/* 61 */     this.mc.field_71460_t.func_78478_c();
/* 62 */     drawFramebuffer(this.framebuffer);
/* 63 */     stopShader();
/* 64 */     this.mc.field_71460_t.func_175072_h();
/* 65 */     GlStateManager.func_179121_F();
/* 66 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(float duplicate, float moreGradient, float creepy, float alpha, int numOctaves) {
/* 70 */     GL11.glPushMatrix();
/* 71 */     GL20.glUseProgram(this.program);
/* 72 */     if (this.uniformsMap == null) {
/* 73 */       this.uniformsMap = new HashMap<>();
/* 74 */       setupUniforms();
/*    */     } 
/* 76 */     updateUniforms(duplicate, moreGradient, creepy, alpha, numOctaves);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 80 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\fill\GradientShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */