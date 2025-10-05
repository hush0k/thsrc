/*     */ package com.mrzak34.thunderhack.util.gaussianblur;
/*     */ 
/*     */ public class ImageMath {
/*     */   public static final float PI = 3.1415927F;
/*     */   
/*     */   public static float step(float a, float x) {
/*   7 */     return (x < a) ? 0.0F : 1.0F;
/*     */   }
/*     */   
/*     */   public static float clamp(float x, float a, float b) {
/*  11 */     return (x < a) ? a : Math.min(x, b);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int clamp(int x, int a, int b) {
/*  16 */     return (x < a) ? a : Math.min(x, b);
/*     */   }
/*     */   
/*     */   public static double mod(double a, double b) {
/*  20 */     int n = (int)(a / b);
/*  21 */     a -= n * b;
/*     */     
/*  23 */     if (a < 0.0D) {
/*  24 */       return a + b;
/*     */     }
/*     */     
/*  27 */     return a;
/*     */   }
/*     */   
/*     */   public static float mod(float a, float b) {
/*  31 */     int n = (int)(a / b);
/*  32 */     a -= n * b;
/*     */     
/*  34 */     if (a < 0.0F) {
/*  35 */       return a + b;
/*     */     }
/*     */     
/*  38 */     return a;
/*     */   }
/*     */   
/*     */   public static int mod(int a, int b) {
/*  42 */     int n = a / b;
/*  43 */     a -= n * b;
/*     */     
/*  45 */     if (a < 0) {
/*  46 */       return a + b;
/*     */     }
/*     */     
/*  49 */     return a;
/*     */   }
/*     */   
/*     */   public static void premultiply(int[] p, int offset, int length) {
/*  53 */     length += offset;
/*     */     
/*  55 */     for (int i = offset; i < length; i++) {
/*     */       
/*  57 */       int rgb = p[i];
/*  58 */       int a = rgb >> 24 & 0xFF;
/*  59 */       int r = rgb >> 16 & 0xFF;
/*  60 */       int g = rgb >> 8 & 0xFF;
/*  61 */       int b = rgb & 0xFF;
/*  62 */       float f = a * 0.003921569F;
/*  63 */       r = (int)(r * f);
/*  64 */       g = (int)(g * f);
/*  65 */       b = (int)(b * f);
/*  66 */       p[i] = a << 24 | r << 16 | g << 8 | b;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void unpremultiply(int[] p, int offset, int length) {
/*  71 */     length += offset;
/*     */     
/*  73 */     for (int i = offset; i < length; i++) {
/*     */       
/*  75 */       int rgb = p[i];
/*  76 */       int a = rgb >> 24 & 0xFF;
/*  77 */       int r = rgb >> 16 & 0xFF;
/*  78 */       int g = rgb >> 8 & 0xFF;
/*  79 */       int b = rgb & 0xFF;
/*     */       
/*  81 */       if (a != 0 && a != 255) {
/*     */         
/*  83 */         float f = 255.0F / a;
/*  84 */         r = (int)(r * f);
/*  85 */         g = (int)(g * f);
/*  86 */         b = (int)(b * f);
/*     */         
/*  88 */         if (r > 255) {
/*  89 */           r = 255;
/*     */         }
/*     */         
/*  92 */         if (g > 255) {
/*  93 */           g = 255;
/*     */         }
/*     */         
/*  96 */         if (b > 255) {
/*  97 */           b = 255;
/*     */         }
/*     */         
/* 100 */         p[i] = a << 24 | r << 16 | g << 8 | b;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\gaussianblur\ImageMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */