/*     */ package com.mrzak34.thunderhack.util.gaussianblur;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.Kernel;
/*     */ 
/*     */ 
/*     */ public class GaussianFilter
/*     */   extends ConvolveFilter
/*     */ {
/*     */   protected float radius;
/*     */   protected Kernel kernel;
/*     */   
/*     */   public GaussianFilter(float radius) {
/*  14 */     setRadius(radius);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void convolveAndTranspose(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, boolean premultiply, boolean unpremultiply, int edgeAction) {
/*  19 */     float[] matrix = kernel.getKernelData(null);
/*     */     
/*  21 */     int cols = kernel.getWidth();
/*     */     
/*  23 */     int cols2 = cols / 2;
/*     */ 
/*     */     
/*  26 */     for (int y = 0; y < height; y++) {
/*     */ 
/*     */       
/*  29 */       int index = y;
/*     */       
/*  31 */       int ioffset = y * width;
/*     */ 
/*     */       
/*  34 */       for (int x = 0; x < width; x++) {
/*     */ 
/*     */         
/*  37 */         float r = 0.0F, g = 0.0F, b = 0.0F, a = 0.0F;
/*     */         
/*  39 */         int moffset = cols2;
/*     */ 
/*     */         
/*  42 */         for (int col = -cols2; col <= cols2; col++) {
/*     */ 
/*     */           
/*  45 */           float f = matrix[moffset + col];
/*     */ 
/*     */           
/*  48 */           if (f != 0.0F) {
/*     */ 
/*     */             
/*  51 */             int ix = x + col;
/*     */ 
/*     */             
/*  54 */             if (ix < 0) {
/*     */ 
/*     */               
/*  57 */               if (edgeAction == CLAMP_EDGES)
/*     */               {
/*  59 */                 ix = 0;
/*     */               
/*     */               }
/*  62 */               else if (edgeAction == WRAP_EDGES)
/*     */               {
/*  64 */                 ix = (x + width) % width;
/*     */               }
/*     */             
/*     */             }
/*  68 */             else if (ix >= width) {
/*     */ 
/*     */               
/*  71 */               if (edgeAction == CLAMP_EDGES) {
/*     */ 
/*     */                 
/*  74 */                 ix = width - 1;
/*     */               
/*     */               }
/*  77 */               else if (edgeAction == WRAP_EDGES) {
/*     */ 
/*     */                 
/*  80 */                 ix = (x + width) % width;
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/*  85 */             int rgb = inPixels[ioffset + ix];
/*     */             
/*  87 */             int pa = rgb >> 24 & 0xFF;
/*     */             
/*  89 */             int pr = rgb >> 16 & 0xFF;
/*     */             
/*  91 */             int pg = rgb >> 8 & 0xFF;
/*     */             
/*  93 */             int pb = rgb & 0xFF;
/*     */ 
/*     */             
/*  96 */             if (premultiply) {
/*     */ 
/*     */               
/*  99 */               float a255 = pa * 0.003921569F;
/*     */               
/* 101 */               pr = (int)(pr * a255);
/*     */               
/* 103 */               pg = (int)(pg * a255);
/*     */               
/* 105 */               pb = (int)(pb * a255);
/*     */             } 
/*     */ 
/*     */             
/* 109 */             a += f * pa;
/*     */             
/* 111 */             r += f * pr;
/*     */             
/* 113 */             g += f * pg;
/*     */             
/* 115 */             b += f * pb;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 120 */         if (unpremultiply && a != 0.0F && a != 255.0F) {
/*     */ 
/*     */           
/* 123 */           float f = 255.0F / a;
/*     */           
/* 125 */           r *= f;
/*     */           
/* 127 */           g *= f;
/*     */           
/* 129 */           b *= f;
/*     */         } 
/*     */ 
/*     */         
/* 133 */         int ia = alpha ? clamp((int)(a + 0.5D)) : 255;
/*     */         
/* 135 */         int ir = clamp((int)(r + 0.5D));
/*     */         
/* 137 */         int ig = clamp((int)(g + 0.5D));
/*     */         
/* 139 */         int ib = clamp((int)(b + 0.5D));
/*     */         
/* 141 */         outPixels[index] = ia << 24 | ir << 16 | ig << 8 | ib;
/*     */         
/* 143 */         index += height;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int clamp(int c) {
/* 149 */     if (c < 0) {
/* 150 */       return 0;
/*     */     }
/*     */     
/* 153 */     return Math.min(c, 255);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Kernel makeKernel(float radius) {
/* 158 */     int r = (int)Math.ceil(radius);
/*     */     
/* 160 */     int rows = r * 2 + 1;
/*     */     
/* 162 */     float[] matrix = new float[rows];
/*     */     
/* 164 */     float sigma = radius / 3.0F;
/*     */     
/* 166 */     float sigma22 = 2.0F * sigma * sigma;
/*     */     
/* 168 */     float sigmaPi2 = 6.2831855F * sigma;
/*     */     
/* 170 */     float sqrtSigmaPi2 = (float)Math.sqrt(sigmaPi2);
/*     */     
/* 172 */     float radius2 = radius * radius;
/*     */     
/* 174 */     float total = 0.0F;
/*     */     
/* 176 */     int index = 0;
/*     */ 
/*     */     
/* 179 */     for (int row = -r; row <= r; row++) {
/*     */ 
/*     */       
/* 182 */       float distance = (row * row);
/*     */ 
/*     */       
/* 185 */       if (distance > radius2) {
/*     */ 
/*     */         
/* 188 */         matrix[index] = 0.0F;
/*     */       }
/*     */       else {
/*     */         
/* 192 */         matrix[index] = (float)Math.exp((-distance / sigma22)) / sqrtSigmaPi2;
/*     */       } 
/*     */ 
/*     */       
/* 196 */       total += matrix[index];
/*     */       
/* 198 */       index++;
/*     */     } 
/*     */ 
/*     */     
/* 202 */     for (int i = 0; i < rows; i++)
/*     */     {
/* 204 */       matrix[i] = matrix[i] / total;
/*     */     }
/*     */ 
/*     */     
/* 208 */     return new Kernel(rows, 1, matrix);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRadius() {
/* 213 */     return this.radius;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRadius(float radius) {
/* 218 */     this.radius = radius;
/*     */     
/* 220 */     this.kernel = makeKernel(radius);
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
/* 225 */     int width = src.getWidth();
/*     */     
/* 227 */     int height = src.getHeight();
/*     */ 
/*     */     
/* 230 */     if (dst == null)
/*     */     {
/* 232 */       dst = createCompatibleDestImage(src, null);
/*     */     }
/*     */ 
/*     */     
/* 236 */     int[] inPixels = new int[width * height];
/*     */     
/* 238 */     int[] outPixels = new int[width * height];
/*     */     
/* 240 */     src.getRGB(0, 0, width, height, inPixels, 0, width);
/*     */ 
/*     */     
/* 243 */     if (this.radius > 0.0F) {
/*     */ 
/*     */       
/* 246 */       convolveAndTranspose(this.kernel, inPixels, outPixels, width, height, this.alpha, (this.alpha && this.premultiplyAlpha), false, CLAMP_EDGES);
/*     */       
/* 248 */       convolveAndTranspose(this.kernel, outPixels, inPixels, height, width, this.alpha, false, (this.alpha && this.premultiplyAlpha), CLAMP_EDGES);
/*     */     } 
/*     */ 
/*     */     
/* 252 */     dst.setRGB(0, 0, width, height, inPixels, 0, width);
/*     */     
/* 254 */     return dst;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 259 */     return "Blur/Gaussian Blur...";
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\gaussianblur\GaussianFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */