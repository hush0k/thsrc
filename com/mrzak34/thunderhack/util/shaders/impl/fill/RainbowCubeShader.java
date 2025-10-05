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
/*    */ public class RainbowCubeShader
/*    */   extends FramebufferShader
/*    */ {
/* 19 */   public static final RainbowCubeShader INSTANCE = new RainbowCubeShader();
/*    */   
/*    */   public float time;
/*    */ 
/*    */   
/*    */   public RainbowCubeShader() {
/* 25 */     super("rainbowCube.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 30 */     setupUniform("resolution");
/* 31 */     setupUniform("time");
/* 32 */     setupUniform("alpha");
/* 33 */     setupUniform("WAVELENGTH");
/* 34 */     setupUniform("R");
/* 35 */     setupUniform("G");
/* 36 */     setupUniform("B");
/* 37 */     setupUniform("RSTART");
/* 38 */     setupUniform("GSTART");
/* 39 */     setupUniform("BSTART");
/*    */   }
/*    */   
/*    */   public void updateUniforms(float duplicate, Color start, int wave, int rStart, int gStart, int bStart) {
/* 43 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/* 44 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 45 */     GL20.glUniform1f(getUniform("alpha"), start.getAlpha() / 255.0F);
/* 46 */     GL20.glUniform1f(getUniform("WAVELENGTH"), wave);
/* 47 */     GL20.glUniform1i(getUniform("R"), start.getRed());
/* 48 */     GL20.glUniform1i(getUniform("G"), start.getGreen());
/* 49 */     GL20.glUniform1i(getUniform("B"), start.getBlue());
/* 50 */     GL20.glUniform1i(getUniform("RSTART"), rStart);
/* 51 */     GL20.glUniform1i(getUniform("GSTART"), gStart);
/* 52 */     GL20.glUniform1i(getUniform("BSTART"), bStart);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, float duplicate, Color start, int wave, int rStart, int gStart, int bStart) {
/* 58 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 59 */     this.framebuffer.func_147609_e();
/* 60 */     GL11.glEnable(3042);
/* 61 */     GL11.glBlendFunc(770, 771);
/* 62 */     this.mc.func_147110_a().func_147610_a(true);
/* 63 */     this.red = color.getRed() / 255.0F;
/* 64 */     this.green = color.getGreen() / 255.0F;
/* 65 */     this.blue = color.getBlue() / 255.0F;
/* 66 */     this.alpha = color.getAlpha() / 255.0F;
/* 67 */     this.radius = radius;
/* 68 */     this.quality = quality;
/* 69 */     this.mc.field_71460_t.func_175072_h();
/* 70 */     RenderHelper.func_74518_a();
/* 71 */     startShader(duplicate, start, wave, rStart, gStart, bStart);
/* 72 */     this.mc.field_71460_t.func_78478_c();
/* 73 */     drawFramebuffer(this.framebuffer);
/* 74 */     stopShader();
/* 75 */     this.mc.field_71460_t.func_175072_h();
/* 76 */     GlStateManager.func_179121_F();
/* 77 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(float duplicate, Color start, int wave, int rStart, int gStart, int bStart) {
/* 81 */     GL11.glPushMatrix();
/* 82 */     GL20.glUseProgram(this.program);
/* 83 */     if (this.uniformsMap == null) {
/* 84 */       this.uniformsMap = new HashMap<>();
/* 85 */       setupUniforms();
/*    */     } 
/* 87 */     updateUniforms(duplicate, start, wave, rStart, gStart, bStart);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 91 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\fill\RainbowCubeShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */