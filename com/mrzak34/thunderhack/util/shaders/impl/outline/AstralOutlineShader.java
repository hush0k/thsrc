/*     */ package com.mrzak34.thunderhack.util.shaders.impl.outline;
/*     */ 
/*     */ import com.mrzak34.thunderhack.util.shaders.FramebufferShader;
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AstralOutlineShader
/*     */   extends FramebufferShader
/*     */ {
/*  18 */   public static final AstralOutlineShader INSTANCE = new AstralOutlineShader();
/*     */ 
/*     */   
/*  21 */   public float time = 0.0F;
/*     */   
/*     */   public AstralOutlineShader() {
/*  24 */     super("astralOutline.frag");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupUniforms() {
/*  29 */     setupUniform("texture");
/*  30 */     setupUniform("texelSize");
/*  31 */     setupUniform("color");
/*  32 */     setupUniform("divider");
/*  33 */     setupUniform("radius");
/*  34 */     setupUniform("maxSample");
/*  35 */     setupUniform("alpha0");
/*  36 */     setupUniform("time");
/*  37 */     setupUniform("iterations");
/*  38 */     setupUniform("formuparam2");
/*  39 */     setupUniform("stepsize");
/*  40 */     setupUniform("volsteps");
/*  41 */     setupUniform("zoom");
/*  42 */     setupUniform("tile");
/*  43 */     setupUniform("distfading");
/*  44 */     setupUniform("saturation");
/*  45 */     setupUniform("fadeBol");
/*  46 */     setupUniform("resolution");
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateUniforms(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, float red, float green, float blue, float alpha, int iteractions, float formuparam2, float zoom, float volumSteps, float stepSize, float title, float distfading, float saturation, float cloud, int fade) {
/*  51 */     GL20.glUniform1i(getUniform("texture"), 0);
/*  52 */     GL20.glUniform2f(getUniform("texelSize"), 1.0F / this.mc.field_71443_c * radius * quality, 1.0F / this.mc.field_71440_d * radius * quality);
/*  53 */     GL20.glUniform1f(getUniform("divider"), 140.0F);
/*  54 */     GL20.glUniform1f(getUniform("radius"), radius);
/*  55 */     GL20.glUniform1f(getUniform("maxSample"), 10.0F);
/*  56 */     GL20.glUniform1f(getUniform("alpha0"), gradientAlpha ? -1.0F : (alphaOutline / 255.0F));
/*  57 */     GL20.glUniform2f(getUniform("resolution"), (new ScaledResolution(this.mc)).func_78326_a() / duplicate, (new ScaledResolution(this.mc)).func_78328_b() / duplicate);
/*  58 */     GL20.glUniform1f(getUniform("time"), this.time);
/*  59 */     GL20.glUniform4f(getUniform("color"), red, green, blue, alpha);
/*  60 */     GL20.glUniform1i(getUniform("iterations"), iteractions);
/*  61 */     GL20.glUniform1f(getUniform("formuparam2"), formuparam2);
/*  62 */     GL20.glUniform1i(getUniform("volsteps"), (int)volumSteps);
/*  63 */     GL20.glUniform1f(getUniform("stepsize"), stepSize);
/*  64 */     GL20.glUniform1f(getUniform("zoom"), zoom);
/*  65 */     GL20.glUniform1f(getUniform("tile"), title);
/*  66 */     GL20.glUniform1f(getUniform("distfading"), distfading);
/*  67 */     GL20.glUniform1f(getUniform("saturation"), saturation);
/*     */     
/*  69 */     GL20.glUniform1i(getUniform("fadeBol"), fade);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopDraw(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, float red, float green, float blue, float alpha, int iteractions, float formuparam2, float zoom, float volumSteps, float stepSize, float title, float distfading, float saturation, float cloud, int fade) {
/*  74 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/*  75 */     this.framebuffer.func_147609_e();
/*  76 */     GL11.glEnable(3042);
/*  77 */     GL11.glBlendFunc(770, 771);
/*  78 */     this.mc.func_147110_a().func_147610_a(true);
/*  79 */     this.mc.field_71460_t.func_175072_h();
/*  80 */     RenderHelper.func_74518_a();
/*  81 */     startShader(color, radius, quality, gradientAlpha, alphaOutline, duplicate, red, green, blue, alpha, iteractions, formuparam2, zoom, volumSteps, stepSize, title, distfading, saturation, cloud, fade);
/*  82 */     this.mc.field_71460_t.func_78478_c();
/*  83 */     drawFramebuffer(this.framebuffer);
/*  84 */     stopShader();
/*  85 */     this.mc.field_71460_t.func_175072_h();
/*  86 */     GlStateManager.func_179121_F();
/*  87 */     GlStateManager.func_179099_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void startShader(Color color, float radius, float quality, boolean gradientAlpha, int alphaOutline, float duplicate, float red, float green, float blue, float alpha, int iteractions, float formuparam2, float zoom, float volumSteps, float stepSize, float title, float distfading, float saturation, float cloud, int fade) {
/*  92 */     GL11.glPushMatrix();
/*  93 */     GL20.glUseProgram(this.program);
/*  94 */     if (this.uniformsMap == null) {
/*  95 */       this.uniformsMap = new HashMap<>();
/*  96 */       setupUniforms();
/*     */     } 
/*  98 */     updateUniforms(color, radius, quality, gradientAlpha, alphaOutline, duplicate, red, green, blue, alpha, iteractions, formuparam2, zoom, volumSteps, stepSize, title, distfading, saturation, cloud, fade);
/*     */   }
/*     */   
/*     */   public void update(double speed) {
/* 102 */     this.time = (float)(this.time + speed);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\impl\outline\AstralOutlineShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */