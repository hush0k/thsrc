/*    */ package com.mrzak34.thunderhack.util.shaders;
/*    */ 
/*    */ import com.mrzak34.thunderhack.mixin.mixins.IEntityRenderer;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class FramebufferShader
/*    */   extends Shader {
/*    */   protected Framebuffer framebuffer;
/*    */   protected float red;
/*    */   protected float green;
/*    */   protected float blue;
/*    */   protected float alpha;
/*    */   protected float radius;
/*    */   protected float quality;
/*    */   protected boolean entityShadows;
/* 23 */   protected Minecraft mc = Minecraft.func_71410_x();
/*    */   
/*    */   public FramebufferShader(String fragmentShader) {
/* 26 */     super(fragmentShader);
/* 27 */     this.alpha = 1.0F;
/* 28 */     this.radius = 2.0F;
/* 29 */     this.quality = 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void startDraw(float partialTicks) {
/* 34 */     GlStateManager.func_179141_d();
/* 35 */     GlStateManager.func_179094_E();
/* 36 */     GlStateManager.func_179123_a();
/* 37 */     (this.framebuffer = setupFrameBuffer(this.framebuffer)).func_147614_f();
/* 38 */     this.framebuffer.func_147610_a(true);
/* 39 */     this.entityShadows = this.mc.field_71474_y.field_181151_V;
/* 40 */     this.mc.field_71474_y.field_181151_V = false;
/* 41 */     ((IEntityRenderer)this.mc.field_71460_t).invokeSetupCameraTransform(partialTicks, 0);
/*    */   }
/*    */   
/*    */   public void stopDraw(Color color, float radius, float quality, float duplicate) {
/* 45 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/* 46 */     this.framebuffer.func_147609_e();
/* 47 */     GL11.glEnable(3042);
/* 48 */     GL11.glBlendFunc(770, 771);
/* 49 */     this.mc.func_147110_a().func_147610_a(true);
/* 50 */     this.red = color.getRed() / 255.0F;
/* 51 */     this.green = color.getGreen() / 255.0F;
/* 52 */     this.blue = color.getBlue() / 255.0F;
/* 53 */     this.alpha = color.getAlpha() / 255.0F;
/* 54 */     this.radius = radius;
/* 55 */     this.quality = quality;
/* 56 */     this.mc.field_71460_t.func_175072_h();
/* 57 */     RenderHelper.func_74518_a();
/* 58 */     startShader(duplicate);
/* 59 */     this.mc.field_71460_t.func_78478_c();
/* 60 */     drawFramebuffer(this.framebuffer);
/* 61 */     stopShader();
/* 62 */     this.mc.field_71460_t.func_175072_h();
/* 63 */     GlStateManager.func_179121_F();
/* 64 */     GlStateManager.func_179099_b();
/*    */   }
/*    */   
/*    */   public Framebuffer setupFrameBuffer(Framebuffer frameBuffer) {
/* 68 */     if (frameBuffer == null) {
/* 69 */       return new Framebuffer(this.mc.field_71443_c, this.mc.field_71440_d, true);
/*    */     }
/* 71 */     if (frameBuffer.field_147621_c != this.mc.field_71443_c || frameBuffer.field_147618_d != this.mc.field_71440_d) {
/* 72 */       frameBuffer.func_147608_a();
/* 73 */       frameBuffer = new Framebuffer(this.mc.field_71443_c, this.mc.field_71440_d, true);
/*    */     } 
/* 75 */     return frameBuffer;
/*    */   }
/*    */   
/*    */   public void drawFramebuffer(Framebuffer framebuffer) {
/* 79 */     ScaledResolution scaledResolution = new ScaledResolution(this.mc);
/* 80 */     GL11.glBindTexture(3553, framebuffer.field_147617_g);
/* 81 */     GL11.glBegin(7);
/* 82 */     GL11.glTexCoord2d(0.0D, 1.0D);
/* 83 */     GL11.glVertex2d(0.0D, 0.0D);
/* 84 */     GL11.glTexCoord2d(0.0D, 0.0D);
/* 85 */     GL11.glVertex2d(0.0D, scaledResolution.func_78328_b());
/* 86 */     GL11.glTexCoord2d(1.0D, 0.0D);
/* 87 */     GL11.glVertex2d(scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
/* 88 */     GL11.glTexCoord2d(1.0D, 1.0D);
/* 89 */     GL11.glVertex2d(scaledResolution.func_78326_a(), 0.0D);
/* 90 */     GL11.glEnd();
/* 91 */     GL20.glUseProgram(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\FramebufferShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */