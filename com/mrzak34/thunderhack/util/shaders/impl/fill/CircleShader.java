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
/*    */ public class CircleShader
/*    */   extends FramebufferShader
/*    */ {
/* 19 */   public static final CircleShader INSTANCE = new CircleShader();
/*    */   
/*    */   public float time;
/*    */ 
/*    */   
/*    */   public CircleShader() {
/* 25 */     super("circle.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 30 */     setupUniform("resolution");
/* 31 */     setupUniform("time");
/* 32 */     setupUniform("colors");
/* 33 */     setupUniform("PI");
/* 34 */     setupUniform("rad");
/*    */   }
/*    */   
/*    */   public void updateUniforms(float duplicate, Color color, Float PI, Float rad) {
/* 38 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/* 39 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 40 */     GL20.glUniform1f(getUniform("PI"), PI.floatValue());
/* 41 */     GL20.glUniform1f(getUniform("rad"), rad.floatValue());
/* 42 */     GL20.glUniform4f(getUniform("colors"), color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*    */   }
/*    */   
/*    */   public void stopDraw(float duplicate, Color color, Float PI, Float rad) {
/* 46 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 47 */     this.framebuffer.func_147609_e();
/* 48 */     GL11.glEnable(3042);
/* 49 */     GL11.glBlendFunc(770, 771);
/* 50 */     this.mc.func_147110_a().func_147610_a(true);
/* 51 */     this.mc.field_71460_t.func_175072_h();
/* 52 */     RenderHelper.func_74518_a();
/* 53 */     startShader(duplicate, color, PI, rad);
/* 54 */     this.mc.field_71460_t.func_78478_c();
/* 55 */     drawFramebuffer(this.framebuffer);
/* 56 */     stopShader();
/* 57 */     this.mc.field_71460_t.func_175072_h();
/* 58 */     GlStateManager.func_179121_F();
/* 59 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(float duplicate, Color color, Float PI, Float rad) {
/* 63 */     GL11.glPushMatrix();
/* 64 */     GL20.glUseProgram(this.program);
/* 65 */     if (this.uniformsMap == null) {
/* 66 */       this.uniformsMap = new HashMap<>();
/* 67 */       setupUniforms();
/*    */     } 
/* 69 */     updateUniforms(duplicate, color, PI, rad);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 73 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\fill\CircleShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */