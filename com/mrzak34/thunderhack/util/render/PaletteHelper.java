/*    */ package com.mrzak34.thunderhack.util.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ public class PaletteHelper
/*    */ {
/*    */   public static int getColor(Color color) {
/*  8 */     return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/*    */   }
/*    */   
/*    */   public static int getColor(int bright) {
/* 12 */     return getColor(bright, bright, bright, 255);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Color astolfo(float yDist, float yTotal, float saturation, float speedt) {
/* 17 */     float speed = 1800.0F; float hue;
/* 18 */     for (hue = (float)(System.currentTimeMillis() % (int)speed) + (yTotal - yDist) * speedt; hue > speed; hue -= speed);
/*    */     
/* 20 */     if ((hue /= speed) > 0.5D) {
/* 21 */       hue = 0.5F - hue - 0.5F;
/*    */     }
/* 23 */     return Color.getHSBColor(hue += 0.5F, saturation, 1.0F);
/*    */   }
/*    */   
/*    */   public static int getColor(int red, int green, int blue, int alpha) {
/* 27 */     int color = 0;
/* 28 */     color |= alpha << 24;
/* 29 */     color |= red << 16;
/* 30 */     color |= green << 8;
/* 31 */     color |= blue;
/* 32 */     return color;
/*    */   }
/*    */   
/*    */   public static Color astolfo(boolean clickgui, int yOffset) {
/* 36 */     float speed = clickgui ? 3500.0F : 3000.0F;
/* 37 */     float hue = (float)(System.currentTimeMillis() % (int)speed + yOffset);
/* 38 */     if (hue > speed) {
/* 39 */       hue -= speed;
/*    */     }
/* 41 */     hue /= speed;
/* 42 */     if (hue > 0.5F) {
/* 43 */       hue = 0.5F - hue - 0.5F;
/*    */     }
/* 45 */     hue += 0.5F;
/* 46 */     return Color.getHSBColor(hue, 0.4F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getColor(int brightness, int alpha) {
/* 51 */     return getColor(brightness, brightness, brightness, alpha);
/*    */   }
/*    */   
/*    */   public static Color rainbow(int delay, float saturation, float brightness) {
/* 55 */     double rainbow = Math.ceil(((float)(System.currentTimeMillis() + delay) / 16.0F));
/* 56 */     rainbow %= 360.0D;
/* 57 */     return Color.getHSBColor((float)(rainbow / 360.0D), saturation, brightness);
/*    */   }
/*    */   
/*    */   public static int fadeColor(int startColor, int endColor, float progress) {
/* 61 */     if (progress > 1.0F) {
/* 62 */       progress = 1.0F - progress % 1.0F;
/*    */     }
/* 64 */     return fade(startColor, endColor, progress);
/*    */   }
/*    */   
/*    */   public static int fade(int startColor, int endColor, float progress) {
/* 68 */     float invert = 1.0F - progress;
/* 69 */     int r = (int)((startColor >> 16 & 0xFF) * invert + (endColor >> 16 & 0xFF) * progress);
/* 70 */     int g = (int)((startColor >> 8 & 0xFF) * invert + (endColor >> 8 & 0xFF) * progress);
/* 71 */     int b = (int)((startColor & 0xFF) * invert + (endColor & 0xFF) * progress);
/* 72 */     int a = (int)((startColor >> 24 & 0xFF) * invert + (endColor >> 24 & 0xFF) * progress);
/* 73 */     return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\render\PaletteHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */