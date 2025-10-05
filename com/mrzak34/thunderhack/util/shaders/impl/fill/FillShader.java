/*    */ package com.mrzak34.thunderhack.util.shaders.impl.fill;
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
/*    */ 
/*    */ public class FillShader
/*    */   extends FramebufferShader
/*    */ {
/* 18 */   public static final FillShader INSTANCE = new FillShader();
/*    */   
/*    */   public float time;
/*    */ 
/*    */   
/*    */   public FillShader() {
/* 24 */     super("fill.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 29 */     setupUniform("color");
/*    */   }
/*    */   
/*    */   public void updateUniforms(float red, float green, float blue, float alpha) {
/* 33 */     GL20.glUniform4f(getUniform("color"), red, green, blue, alpha);
/*    */   }
/*    */   
/*    */   public void stopDraw(Color color) {
/* 37 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 38 */     this.framebuffer.func_147609_e();
/* 39 */     GL11.glEnable(3042);
/* 40 */     GL11.glBlendFunc(770, 771);
/* 41 */     this.mc.func_147110_a().func_147610_a(true);
/* 42 */     this.mc.field_71460_t.func_175072_h();
/* 43 */     RenderHelper.func_74518_a();
/* 44 */     startShader(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/* 45 */     this.mc.field_71460_t.func_78478_c();
/* 46 */     drawFramebuffer(this.framebuffer);
/* 47 */     stopShader();
/* 48 */     this.mc.field_71460_t.func_175072_h();
/* 49 */     GlStateManager.func_179121_F();
/* 50 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(float red, float green, float blue, float alpha) {
/* 54 */     GL11.glPushMatrix();
/* 55 */     GL20.glUseProgram(this.program);
/* 56 */     if (this.uniformsMap == null) {
/* 57 */       this.uniformsMap = new HashMap<>();
/* 58 */       setupUniforms();
/*    */     } 
/* 60 */     updateUniforms(red, green, blue, alpha);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 64 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\fill\FillShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */