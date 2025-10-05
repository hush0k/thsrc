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
/*    */ public class SmokeShader
/*    */   extends FramebufferShader
/*    */ {
/* 19 */   public static final SmokeShader INSTANCE = new SmokeShader();
/*    */   
/*    */   public float time;
/*    */ 
/*    */   
/*    */   public SmokeShader() {
/* 25 */     super("smoke.frag");
/*    */   }
/*    */   
/*    */   public void setupUniforms() {
/* 29 */     setupUniform("resolution");
/* 30 */     setupUniform("time");
/* 31 */     setupUniform("first");
/* 32 */     setupUniform("second");
/* 33 */     setupUniform("third");
/* 34 */     setupUniform("oct");
/*    */   }
/*    */   
/*    */   public void updateUniforms(float duplicate, Color first, Color second, Color third, int oct) {
/* 38 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/* 39 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 40 */     GL20.glUniform4f(getUniform("first"), first.getRed() / 255.0F * 5.0F, first.getGreen() / 255.0F * 5.0F, first.getBlue() / 255.0F * 5.0F, first.getAlpha() / 255.0F);
/* 41 */     GL20.glUniform3f(getUniform("second"), second.getRed() / 255.0F * 5.0F, second.getGreen() / 255.0F * 5.0F, second.getBlue() / 255.0F * 5.0F);
/* 42 */     GL20.glUniform3f(getUniform("third"), third.getRed() / 255.0F * 5.0F, third.getGreen() / 255.0F * 5.0F, third.getBlue() / 255.0F * 5.0F);
/* 43 */     GL20.glUniform1i(getUniform("oct"), oct);
/*    */   }
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, float duplicate, Color first, Color second, Color third, int oct) {
/* 47 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 48 */     this.framebuffer.func_147609_e();
/* 49 */     GL11.glEnable(3042);
/* 50 */     GL11.glBlendFunc(770, 771);
/* 51 */     this.mc.func_147110_a().func_147610_a(true);
/* 52 */     this.red = color.getRed() / 255.0F;
/* 53 */     this.green = color.getGreen() / 255.0F;
/* 54 */     this.blue = color.getBlue() / 255.0F;
/* 55 */     this.alpha = color.getAlpha() / 255.0F;
/* 56 */     this.radius = radius;
/* 57 */     this.quality = quality;
/* 58 */     this.mc.field_71460_t.func_175072_h();
/* 59 */     RenderHelper.func_74518_a();
/* 60 */     startShader(duplicate, first, second, third, oct);
/* 61 */     this.mc.field_71460_t.func_78478_c();
/* 62 */     drawFramebuffer(this.framebuffer);
/* 63 */     stopShader();
/* 64 */     this.mc.field_71460_t.func_175072_h();
/* 65 */     GlStateManager.func_179121_F();
/* 66 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(float duplicate, Color first, Color second, Color third, int oct) {
/* 70 */     GL11.glPushMatrix();
/* 71 */     GL20.glUseProgram(this.program);
/* 72 */     if (this.uniformsMap == null) {
/* 73 */       this.uniformsMap = new HashMap<>();
/* 74 */       setupUniforms();
/*    */     } 
/* 76 */     updateUniforms(duplicate, first, second, third, oct);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 80 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\fill\SmokeShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */