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
/*    */ public class FlowShader
/*    */   extends FramebufferShader
/*    */ {
/* 19 */   public static final FlowShader INSTANCE = new FlowShader();
/*    */   
/*    */   public float time;
/*    */ 
/*    */   
/*    */   public FlowShader() {
/* 25 */     super("flow.frag");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 30 */     setupUniform("resolution");
/* 31 */     setupUniform("time");
/* 32 */     setupUniform("color");
/* 33 */     setupUniform("iterations");
/* 34 */     setupUniform("formuparam2");
/* 35 */     setupUniform("stepsize");
/* 36 */     setupUniform("volsteps");
/* 37 */     setupUniform("zoom");
/* 38 */     setupUniform("tile");
/* 39 */     setupUniform("distfading");
/* 40 */     setupUniform("saturation");
/* 41 */     setupUniform("fadeBol");
/*    */   }
/*    */   
/*    */   public void updateUniforms(float duplicate, float red, float green, float blue, float alpha, int iteractions, float formuparam2, float zoom, float volumSteps, float stepSize, float title, float distfading, float saturation, float cloud, int fade) {
/* 45 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/* 46 */     GL20.glUniform1f(getUniform("time"), this.time);
/* 47 */     GL20.glUniform4f(getUniform("color"), red, green, blue, alpha);
/* 48 */     GL20.glUniform1i(getUniform("iterations"), iteractions);
/* 49 */     GL20.glUniform1f(getUniform("formuparam2"), formuparam2);
/* 50 */     GL20.glUniform1i(getUniform("volsteps"), (int)volumSteps);
/* 51 */     GL20.glUniform1f(getUniform("stepsize"), stepSize);
/* 52 */     GL20.glUniform1f(getUniform("zoom"), zoom);
/* 53 */     GL20.glUniform1f(getUniform("tile"), title);
/* 54 */     GL20.glUniform1f(getUniform("distfading"), distfading);
/* 55 */     GL20.glUniform1f(getUniform("saturation"), saturation);
/*    */     
/* 57 */     GL20.glUniform1i(getUniform("fadeBol"), fade);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, float duplicate, float red, float green, float blue, float alpha, int iteractions, float formuparam2, float zoom, float volumSteps, float stepSize, float title, float distfading, float saturation, float cloud, int fade) {
/* 63 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 64 */     this.framebuffer.func_147609_e();
/* 65 */     GL11.glEnable(3042);
/* 66 */     GL11.glBlendFunc(770, 771);
/* 67 */     this.mc.func_147110_a().func_147610_a(true);
/* 68 */     this.radius = radius;
/* 69 */     this.quality = quality;
/* 70 */     this.mc.field_71460_t.func_175072_h();
/* 71 */     RenderHelper.func_74518_a();
/* 72 */     startShader(duplicate, red, green, blue, alpha, iteractions, formuparam2, zoom, volumSteps, stepSize, title, distfading, saturation, cloud, fade);
/* 73 */     this.mc.field_71460_t.func_78478_c();
/* 74 */     drawFramebuffer(this.framebuffer);
/* 75 */     stopShader();
/* 76 */     this.mc.field_71460_t.func_175072_h();
/* 77 */     GlStateManager.func_179121_F();
/* 78 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public void startShader(float duplicate, float red, float green, float blue, float alpha, int iteractions, float formuparam2, float zoom, float volumSteps, float stepSize, float title, float distfading, float saturation, float cloud, int fade) {
/* 82 */     GL11.glPushMatrix();
/* 83 */     GL20.glUseProgram(this.program);
/* 84 */     if (this.uniformsMap == null) {
/* 85 */       this.uniformsMap = new HashMap<>();
/* 86 */       setupUniforms();
/*    */     } 
/* 88 */     updateUniforms(duplicate, red, green, blue, alpha, iteractions, formuparam2, zoom, volumSteps, stepSize, title, distfading, saturation, cloud, fade);
/*    */   }
/*    */   
/*    */   public void update(double speed) {
/* 92 */     this.time = (float)(this.time + speed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\fill\FlowShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */