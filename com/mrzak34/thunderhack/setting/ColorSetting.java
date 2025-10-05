/*     */ package com.mrzak34.thunderhack.setting;
/*     */ 
/*     */ import java.awt.Color;
/*     */ 
/*     */ public final class ColorSetting
/*     */ {
/*     */   private int color;
/*     */   private boolean cycle = false;
/*   9 */   private int globalOffset = 0;
/*     */   
/*     */   public ColorSetting(int color) {
/*  12 */     this.color = color;
/*     */   }
/*     */   
/*     */   public ColorSetting(int color, boolean cycle) {
/*  16 */     this.color = color;
/*  17 */     this.cycle = cycle;
/*     */   }
/*     */   
/*     */   public ColorSetting(int color, boolean cycle, int globalOffset) {
/*  21 */     this.color = color;
/*  22 */     this.cycle = cycle;
/*  23 */     this.globalOffset = globalOffset;
/*     */   }
/*     */   
/*     */   public ColorSetting withAlpha(int alpha) {
/*  27 */     int red = getColor() >> 16 & 0xFF;
/*  28 */     int green = getColor() >> 8 & 0xFF;
/*  29 */     int blue = getColor() & 0xFF;
/*  30 */     return new ColorSetting((alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseColor(String nm) throws NumberFormatException {
/*  37 */     Integer intval = Integer.decode(nm);
/*  38 */     return intval.intValue();
/*     */   }
/*     */   
/*     */   public int getColor() {
/*  42 */     if (this.cycle) {
/*  43 */       float[] hsb = Color.RGBtoHSB(this.color >> 16 & 0xFF, this.color >> 8 & 0xFF, this.color & 0xFF, null);
/*  44 */       double rainbowState = Math.ceil((System.currentTimeMillis() + 300L + this.globalOffset) / 20.0D);
/*  45 */       rainbowState %= 360.0D;
/*  46 */       int rgb = Color.getHSBColor((float)(rainbowState / 360.0D), hsb[1], hsb[2]).getRGB();
/*  47 */       int alpha = this.color >> 24 & 0xFF;
/*  48 */       int red = rgb >> 16 & 0xFF;
/*  49 */       int green = rgb >> 8 & 0xFF;
/*  50 */       int blue = rgb & 0xFF;
/*  51 */       return (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  56 */     return this.color;
/*     */   }
/*     */   
/*     */   public void setColor(int color) {
/*  60 */     this.color = color;
/*     */   }
/*     */   
/*     */   public int getGlobalOffset() {
/*  64 */     return this.globalOffset;
/*     */   }
/*     */   
/*     */   public void setGlobalOffset(int globalOffset) {
/*  68 */     this.globalOffset = globalOffset;
/*     */   }
/*     */   
/*     */   public int getOffsetColor(int offset) {
/*  72 */     if (this.cycle) {
/*  73 */       float[] hsb = Color.RGBtoHSB(this.color >> 16 & 0xFF, this.color >> 8 & 0xFF, this.color & 0xFF, null);
/*  74 */       double rainbowState = Math.ceil((System.currentTimeMillis() + 300L + offset + this.globalOffset) / 20.0D);
/*  75 */       rainbowState %= 360.0D;
/*  76 */       int rgb = Color.getHSBColor((float)(rainbowState / 360.0D), hsb[1], hsb[2]).getRGB();
/*  77 */       int alpha = this.color >> 24 & 0xFF;
/*  78 */       int red = rgb >> 16 & 0xFF;
/*  79 */       int green = rgb >> 8 & 0xFF;
/*  80 */       int blue = rgb & 0xFF;
/*  81 */       return (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  86 */     return this.color;
/*     */   }
/*     */   
/*     */   public int getRed() {
/*  90 */     if (this.cycle) {
/*  91 */       float[] hsb = Color.RGBtoHSB(this.color >> 16 & 0xFF, this.color >> 8 & 0xFF, this.color & 0xFF, null);
/*  92 */       double rainbowState = Math.ceil((System.currentTimeMillis() + 300L + this.globalOffset) / 20.0D);
/*  93 */       rainbowState %= 360.0D;
/*  94 */       int rgb = Color.getHSBColor((float)(rainbowState / 360.0D), hsb[1], hsb[2]).getRGB();
/*  95 */       return rgb >> 16 & 0xFF;
/*     */     } 
/*  97 */     return this.color >> 16 & 0xFF;
/*     */   }
/*     */   
/*     */   public int getGreen() {
/* 101 */     if (this.cycle) {
/* 102 */       float[] hsb = Color.RGBtoHSB(this.color >> 16 & 0xFF, this.color >> 8 & 0xFF, this.color & 0xFF, null);
/* 103 */       double rainbowState = Math.ceil((System.currentTimeMillis() + 300L + this.globalOffset) / 20.0D);
/* 104 */       rainbowState %= 360.0D;
/* 105 */       int rgb = Color.getHSBColor((float)(rainbowState / 360.0D), hsb[1], hsb[2]).getRGB();
/* 106 */       return rgb >> 8 & 0xFF;
/*     */     } 
/* 108 */     return this.color >> 8 & 0xFF;
/*     */   }
/*     */   
/*     */   public int getBlue() {
/* 112 */     if (this.cycle) {
/* 113 */       float[] hsb = Color.RGBtoHSB(this.color >> 16 & 0xFF, this.color >> 8 & 0xFF, this.color & 0xFF, null);
/* 114 */       double rainbowState = Math.ceil((System.currentTimeMillis() + 300L + this.globalOffset) / 20.0D);
/* 115 */       rainbowState %= 360.0D;
/* 116 */       int rgb = Color.getHSBColor((float)(rainbowState / 360.0D), hsb[1], hsb[2]).getRGB();
/* 117 */       return rgb & 0xFF;
/*     */     } 
/* 119 */     return this.color & 0xFF;
/*     */   }
/*     */   
/*     */   public int getAlpha() {
/* 123 */     return this.color >> 24 & 0xFF;
/*     */   }
/*     */   
/*     */   public Color getColorObject() {
/* 127 */     int color = getColor();
/* 128 */     int alpha = color >> 24 & 0xFF;
/* 129 */     int red = color >> 16 & 0xFF;
/* 130 */     int green = color >> 8 & 0xFF;
/* 131 */     int blue = color & 0xFF;
/*     */     
/* 133 */     return new Color(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRawColor() {
/* 138 */     return this.color;
/*     */   }
/*     */   
/*     */   public boolean isCycle() {
/* 142 */     return this.cycle;
/*     */   }
/*     */   
/*     */   public void setCycle(boolean cycle) {
/* 146 */     this.cycle = cycle;
/*     */   }
/*     */   
/*     */   public void toggleCycle() {
/* 150 */     this.cycle = !this.cycle;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\setting\ColorSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */