/*     */ package com.mrzak34.thunderhack.gui.fontstuff;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontRender
/*     */ {
/*     */   public static float drawStringWithShadow(String text, float x, float y, int color) {
/*  15 */     return drawStringWithShadow(text, (int)x, (int)y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float drawString(String text, float x, float y, int color) {
/*  20 */     return drawString(text, (int)x, (int)y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float drawStringWithShadow(String text, int x, int y, int color) {
/*  25 */     return Thunderhack.fontRenderer.drawStringWithShadow(text, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawString(String text, int x, int y, int color) {
/*  29 */     return Thunderhack.fontRenderer.drawString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawString2(String text, int x, int y, int color) {
/*  33 */     return Thunderhack.fontRenderer2.drawString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawIcon(String id, int x, int y, int color) {
/*  37 */     return Thunderhack.icons.drawString(id, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawIconF(String id, float x, float y, int color) {
/*  41 */     return Thunderhack.icons.drawString(id, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawMidIcon(String id, float x, float y, int color) {
/*  45 */     return Thunderhack.middleicons.drawString(id, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawMidIconF(String id, int x, int y, int color) {
/*  49 */     return Thunderhack.middleicons.drawString(id, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawBigIcon(String id, int x, int y, int color) {
/*  53 */     return Thunderhack.BIGicons.drawString(id, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawString3(String text, float x, float y, int color) {
/*  57 */     return Thunderhack.fontRenderer3.drawString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawCentString3(String text, float x, float y, int color) {
/*  61 */     return Thunderhack.fontRenderer3.drawString(text, x - getStringWidth3(text) / 2.0F, y - getFontHeight3() / 2.0F, color);
/*     */   }
/*     */   public static float drawCentString4(String text, float x, float y, int color) {
/*  64 */     return Thunderhack.fontRenderer4.drawString(text, x - getStringWidth4(text) / 2.0F, y - getFontHeight4() / 2.0F, color);
/*     */   }
/*     */   
/*     */   public static float drawString8(String text, int x, int y, int color) {
/*  68 */     return Thunderhack.fontRenderer8.drawString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawCentString8(String text, float x, float y, int color) {
/*  72 */     return Thunderhack.fontRenderer8.drawString(text, x - getStringWidth6(text) / 2.0F, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float drawString4(String text, int x, int y, int color) {
/*  77 */     return Thunderhack.fontRenderer4.drawString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawString5(String text, float x, float y, int color) {
/*  81 */     return Thunderhack.fontRenderer5.drawString(text, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float drawString6(String text, float x, float y, int color, boolean shadow) {
/*  86 */     return Thunderhack.fontRenderer6.drawString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawString7(String text, float x, float y, int color, boolean shadow) {
/*  90 */     return Thunderhack.fontRenderer7.drawString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public static float drawCentString6(String text, float x, float y, int color) {
/*  94 */     return Thunderhack.fontRenderer6.drawString(text, x - getStringWidth6(text) / 2.0F, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float drawCentString5(String text, float x, float y, int color) {
/*  99 */     return Thunderhack.fontRenderer5.drawString(text, x - getStringWidth5(text) / 2.0F, y, color);
/*     */   }
/*     */   
/*     */   public static float drawCentString2(String text, float x, float y, int color) {
/* 103 */     return Thunderhack.fontRenderer2.drawString(text, x - getStringWidth2(text) / 2.0F, y, color);
/*     */   }
/*     */   
/*     */   public static int getStringWidth2(String str) {
/* 107 */     return Thunderhack.fontRenderer2.getStringWidth(str);
/*     */   }
/*     */   
/*     */   public static int getStringWidth(String str) {
/* 111 */     return Thunderhack.fontRenderer.getStringWidth(str);
/*     */   }
/*     */   
/*     */   public static int getStringWidth6(String str) {
/* 115 */     if (str == null) {
/* 116 */       return 1;
/*     */     }
/* 118 */     if (str.equals("")) {
/* 119 */       return 1;
/*     */     }
/* 121 */     return Thunderhack.fontRenderer6.getStringWidth(str);
/*     */   }
/*     */   
/*     */   public static int getStringWidth5(String str) {
/* 125 */     return Thunderhack.fontRenderer5.getStringWidth(str);
/*     */   }
/*     */   
/*     */   public static int getStringWidth3(String str) {
/* 129 */     return Thunderhack.fontRenderer3.getStringWidth(str);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getStringWidth4(String str) {
/* 134 */     return Thunderhack.fontRenderer4.getStringWidth(str);
/*     */   }
/*     */   
/*     */   public static int getFontHeight() {
/* 138 */     return Thunderhack.fontRenderer.getHeight() + 2;
/*     */   }
/*     */   
/*     */   public static int getFontHeight2() {
/* 142 */     return Thunderhack.fontRenderer2.getHeight() + 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getFontHeight3() {
/* 147 */     return Thunderhack.fontRenderer3.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getFontHeight4() {
/* 152 */     return Thunderhack.fontRenderer3.getHeight();
/*     */   }
/*     */   
/*     */   public static int getFontHeight6() {
/* 156 */     return Thunderhack.fontRenderer6.getHeight();
/*     */   }
/*     */   
/*     */   public static int getFontHeight8() {
/* 160 */     return Thunderhack.fontRenderer8.getHeight();
/*     */   }
/*     */   
/*     */   public static int getFontHeight5() {
/* 164 */     return Thunderhack.fontRenderer5.getHeight() + 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\fontstuff\FontRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */