/*    */ package com.mrzak34.thunderhack.util;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.render.ShaderUtil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RoundedShader
/*    */ {
/* 13 */   private static final ShaderUtil roundedGradientShader = new ShaderUtil("roundedRectGradient");
/* 14 */   public static ShaderUtil roundedShader = new ShaderUtil("roundedRect");
/* 15 */   public static ShaderUtil roundedOutlineShader = new ShaderUtil("textures/roundrectoutline.frag");
/*    */   
/*    */   public static void drawRound(float x, float y, float width, float height, float radius, Color color) {
/* 18 */     drawRound(x, y, width, height, radius, false, color);
/*    */   }
/*    */   
/*    */   public static void drawGradientRound(float x, float y, float width, float height, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
/* 22 */     GlStateManager.func_179117_G();
/* 23 */     GlStateManager.func_179147_l();
/* 24 */     GlStateManager.func_179112_b(770, 771);
/* 25 */     roundedGradientShader.init();
/* 26 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedGradientShader);
/* 27 */     roundedGradientShader.setUniformf("color1", new float[] { bottomLeft.getRed() / 255.0F, bottomLeft.getGreen() / 255.0F, bottomLeft.getBlue() / 255.0F, bottomLeft.getAlpha() / 255.0F });
/* 28 */     roundedGradientShader.setUniformf("color2", new float[] { topLeft.getRed() / 255.0F, topLeft.getGreen() / 255.0F, topLeft.getBlue() / 255.0F, topLeft.getAlpha() / 255.0F });
/* 29 */     roundedGradientShader.setUniformf("color3", new float[] { bottomRight.getRed() / 255.0F, bottomRight.getGreen() / 255.0F, bottomRight.getBlue() / 255.0F, bottomRight.getAlpha() / 255.0F });
/* 30 */     roundedGradientShader.setUniformf("color4", new float[] { topRight.getRed() / 255.0F, topRight.getGreen() / 255.0F, topRight.getBlue() / 255.0F, topRight.getAlpha() / 255.0F });
/* 31 */     ShaderUtil.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/* 32 */     roundedGradientShader.unload();
/* 33 */     GlStateManager.func_179117_G();
/* 34 */     GlStateManager.func_179084_k();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void drawRound(float x, float y, float width, float height, float radius, boolean blur, Color color) {
/* 39 */     GlStateManager.func_179117_G();
/* 40 */     GlStateManager.func_179147_l();
/* 41 */     GlStateManager.func_179112_b(770, 771);
/* 42 */     roundedShader.init();
/* 43 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
/* 44 */     roundedShader.setUniformi("blur", new int[] { blur ? 1 : 0 });
/* 45 */     roundedShader.setUniformf("color", new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F });
/* 46 */     ShaderUtil.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
/* 47 */     roundedShader.unload();
/* 48 */     GlStateManager.func_179084_k();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void drawRoundOutline(float x, float y, float width, float height, float radius, float outlineThickness, Color color, Color outlineColor) {
/* 53 */     GlStateManager.func_179117_G();
/* 54 */     GlStateManager.func_179147_l();
/* 55 */     GlStateManager.func_179112_b(770, 771);
/* 56 */     roundedOutlineShader.init();
/* 57 */     ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
/* 58 */     setupRoundedRectUniforms(x, y, width, height, radius, roundedOutlineShader);
/* 59 */     roundedOutlineShader.setUniformf("outlineThickness", new float[] { outlineThickness * sr.func_78325_e() });
/* 60 */     roundedOutlineShader.setUniformf("color", new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F });
/* 61 */     roundedOutlineShader.setUniformf("outlineColor", new float[] { outlineColor.getRed() / 255.0F, outlineColor.getGreen() / 255.0F, outlineColor.getBlue() / 255.0F, outlineColor.getAlpha() / 255.0F });
/* 62 */     ShaderUtil.drawQuads(x - 2.0F + outlineThickness, y - 2.0F + outlineThickness, width + 4.0F + outlineThickness * 2.0F, height + 4.0F + outlineThickness * 2.0F);
/* 63 */     roundedOutlineShader.unload();
/* 64 */     GlStateManager.func_179084_k();
/*    */   }
/*    */ 
/*    */   
/*    */   private static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius, ShaderUtil roundedTexturedShader) {
/* 69 */     ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
/* 70 */     roundedTexturedShader.setUniformf("location", new float[] { x * sr.func_78325_e(), Util.mc.field_71440_d - height * sr.func_78325_e() - y * sr.func_78325_e() });
/* 71 */     roundedTexturedShader.setUniformf("rectSize", new float[] { width * sr.func_78325_e(), height * sr.func_78325_e() });
/* 72 */     roundedTexturedShader.setUniformf("radius", new float[] { radius * sr.func_78325_e() });
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\RoundedShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */