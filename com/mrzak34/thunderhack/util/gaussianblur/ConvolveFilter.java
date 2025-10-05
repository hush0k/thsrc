/*     */ package com.mrzak34.thunderhack.util.gaussianblur;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Kernel;
/*     */ 
/*     */ public class ConvolveFilter
/*     */   extends AbstractBufferedImageOp
/*     */ {
/*  14 */   public static int CLAMP_EDGES = 1;
/*     */ 
/*     */   
/*  17 */   public static int WRAP_EDGES = 2;
/*     */ 
/*     */   
/*  20 */   protected Kernel kernel = null;
/*     */ 
/*     */   
/*     */   protected boolean alpha = true;
/*     */ 
/*     */   
/*     */   protected boolean premultiplyAlpha = true;
/*     */ 
/*     */   
/*  29 */   private final int edgeAction = CLAMP_EDGES;
/*     */ 
/*     */ 
/*     */   
/*     */   public ConvolveFilter() {
/*  34 */     this(new float[9]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ConvolveFilter(float[] matrix) {
/*  40 */     this(new Kernel(3, 3, matrix));
/*     */   }
/*     */ 
/*     */   
/*     */   public ConvolveFilter(Kernel kernel) {
/*  45 */     this.kernel = kernel;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void convolve(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
/*  50 */     if (kernel.getHeight() == 1) {
/*     */ 
/*     */       
/*  53 */       convolveH(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
/*     */     
/*     */     }
/*  56 */     else if (kernel.getWidth() == 1) {
/*     */ 
/*     */       
/*  59 */       convolveV(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
/*     */     }
/*     */     else {
/*     */       
/*  63 */       convolveHV(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void convolveHV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
/*  69 */     int index = 0;
/*     */     
/*  71 */     float[] matrix = kernel.getKernelData(null);
/*     */     
/*  73 */     int rows = kernel.getHeight();
/*     */     
/*  75 */     int cols = kernel.getWidth();
/*     */     
/*  77 */     int rows2 = rows / 2;
/*     */     
/*  79 */     int cols2 = cols / 2;
/*     */ 
/*     */     
/*  82 */     for (int y = 0; y < height; y++) {
/*     */ 
/*     */       
/*  85 */       for (int x = 0; x < width; x++) {
/*     */ 
/*     */         
/*  88 */         float r = 0.0F, g = 0.0F, b = 0.0F, a = 0.0F;
/*     */ 
/*     */         
/*  91 */         for (int row = -rows2; row <= rows2; row++) {
/*     */ 
/*     */           
/*  94 */           int ioffset, iy = y + row;
/*     */ 
/*     */ 
/*     */           
/*  98 */           if (0 <= iy && iy < height) {
/*     */ 
/*     */             
/* 101 */             ioffset = iy * width;
/*     */           
/*     */           }
/* 104 */           else if (edgeAction == CLAMP_EDGES) {
/*     */ 
/*     */             
/* 107 */             ioffset = y * width;
/*     */           
/*     */           }
/* 110 */           else if (edgeAction == WRAP_EDGES) {
/*     */ 
/*     */             
/* 113 */             ioffset = (iy + height) % height * width;
/*     */           } else {
/*     */             continue;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 121 */           int moffset = cols * (row + rows2) + cols2;
/*     */ 
/*     */           
/* 124 */           for (int col = -cols2; col <= cols2; col++) {
/*     */ 
/*     */             
/* 127 */             float f = matrix[moffset + col];
/*     */ 
/*     */             
/* 130 */             if (f != 0.0F) {
/*     */ 
/*     */               
/* 133 */               int ix = x + col;
/*     */ 
/*     */               
/* 136 */               if (0 > ix || ix >= width)
/*     */               {
/* 138 */                 if (edgeAction == CLAMP_EDGES) {
/*     */ 
/*     */                   
/* 141 */                   ix = x;
/*     */                 
/*     */                 }
/* 144 */                 else if (edgeAction == WRAP_EDGES) {
/*     */ 
/*     */                   
/* 147 */                   ix = (x + width) % width;
/*     */                 } else {
/*     */                   continue;
/*     */                 } 
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 156 */               int rgb = inPixels[ioffset + ix];
/*     */               
/* 158 */               a += f * (rgb >> 24 & 0xFF);
/*     */               
/* 160 */               r += f * (rgb >> 16 & 0xFF);
/*     */               
/* 162 */               g += f * (rgb >> 8 & 0xFF);
/*     */               
/* 164 */               b += f * (rgb & 0xFF);
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           continue;
/*     */         } 
/* 171 */         int ia = alpha ? clamp((int)(a + 0.5D)) : 255;
/*     */         
/* 173 */         int ir = clamp((int)(r + 0.5D));
/*     */         
/* 175 */         int ig = clamp((int)(g + 0.5D));
/*     */         
/* 177 */         int ib = clamp((int)(b + 0.5D));
/*     */         
/* 179 */         outPixels[index++] = ia << 24 | ir << 16 | ig << 8 | ib;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void convolveH(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
/* 186 */     int index = 0;
/*     */     
/* 188 */     float[] matrix = kernel.getKernelData(null);
/*     */     
/* 190 */     int cols = kernel.getWidth();
/*     */     
/* 192 */     int cols2 = cols / 2;
/*     */ 
/*     */     
/* 195 */     for (int y = 0; y < height; y++) {
/*     */ 
/*     */       
/* 198 */       int ioffset = y * width;
/*     */ 
/*     */       
/* 201 */       for (int x = 0; x < width; x++) {
/*     */ 
/*     */         
/* 204 */         float r = 0.0F, g = 0.0F, b = 0.0F, a = 0.0F;
/*     */         
/* 206 */         int moffset = cols2;
/*     */ 
/*     */         
/* 209 */         for (int col = -cols2; col <= cols2; col++) {
/*     */ 
/*     */           
/* 212 */           float f = matrix[moffset + col];
/*     */ 
/*     */           
/* 215 */           if (f != 0.0F) {
/*     */ 
/*     */             
/* 218 */             int ix = x + col;
/*     */ 
/*     */             
/* 221 */             if (ix < 0) {
/*     */ 
/*     */               
/* 224 */               if (edgeAction == CLAMP_EDGES)
/*     */               {
/* 226 */                 ix = 0;
/*     */               
/*     */               }
/* 229 */               else if (edgeAction == WRAP_EDGES)
/*     */               {
/* 231 */                 ix = (x + width) % width;
/*     */               }
/*     */             
/*     */             }
/* 235 */             else if (ix >= width) {
/*     */ 
/*     */               
/* 238 */               if (edgeAction == CLAMP_EDGES) {
/*     */ 
/*     */                 
/* 241 */                 ix = width - 1;
/*     */               
/*     */               }
/* 244 */               else if (edgeAction == WRAP_EDGES) {
/*     */ 
/*     */                 
/* 247 */                 ix = (x + width) % width;
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 252 */             int rgb = inPixels[ioffset + ix];
/*     */             
/* 254 */             a += f * (rgb >> 24 & 0xFF);
/*     */             
/* 256 */             r += f * (rgb >> 16 & 0xFF);
/*     */             
/* 258 */             g += f * (rgb >> 8 & 0xFF);
/*     */             
/* 260 */             b += f * (rgb & 0xFF);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 265 */         int ia = alpha ? clamp((int)(a + 0.5D)) : 255;
/*     */         
/* 267 */         int ir = clamp((int)(r + 0.5D));
/*     */         
/* 269 */         int ig = clamp((int)(g + 0.5D));
/*     */         
/* 271 */         int ib = clamp((int)(b + 0.5D));
/*     */         
/* 273 */         outPixels[index++] = ia << 24 | ir << 16 | ig << 8 | ib;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void convolveV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
/* 280 */     int index = 0;
/*     */     
/* 282 */     float[] matrix = kernel.getKernelData(null);
/*     */     
/* 284 */     int rows = kernel.getHeight();
/*     */     
/* 286 */     int rows2 = rows / 2;
/*     */ 
/*     */     
/* 289 */     for (int y = 0; y < height; y++) {
/*     */ 
/*     */       
/* 292 */       for (int x = 0; x < width; x++) {
/*     */ 
/*     */         
/* 295 */         float r = 0.0F, g = 0.0F, b = 0.0F, a = 0.0F;
/*     */ 
/*     */         
/* 298 */         for (int row = -rows2; row <= rows2; row++) {
/*     */ 
/*     */           
/* 301 */           int ioffset, iy = y + row;
/*     */ 
/*     */ 
/*     */           
/* 305 */           if (iy < 0) {
/*     */ 
/*     */             
/* 308 */             if (edgeAction == CLAMP_EDGES) {
/*     */               
/* 310 */               ioffset = 0;
/*     */             
/*     */             }
/* 313 */             else if (edgeAction == WRAP_EDGES) {
/*     */               
/* 315 */               ioffset = (y + height) % height * width;
/*     */             } else {
/*     */               
/* 318 */               ioffset = iy * width;
/*     */             }
/*     */           
/*     */           }
/* 322 */           else if (iy >= height) {
/*     */ 
/*     */             
/* 325 */             if (edgeAction == CLAMP_EDGES) {
/*     */               
/* 327 */               ioffset = (height - 1) * width;
/*     */             
/*     */             }
/* 330 */             else if (edgeAction == WRAP_EDGES) {
/*     */               
/* 332 */               ioffset = (y + height) % height * width;
/*     */             } else {
/*     */               
/* 335 */               ioffset = iy * width;
/*     */             }
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 341 */             ioffset = iy * width;
/*     */           } 
/*     */ 
/*     */           
/* 345 */           float f = matrix[row + rows2];
/*     */ 
/*     */           
/* 348 */           if (f != 0.0F) {
/*     */ 
/*     */             
/* 351 */             int rgb = inPixels[ioffset + x];
/*     */             
/* 353 */             a += f * (rgb >> 24 & 0xFF);
/*     */             
/* 355 */             r += f * (rgb >> 16 & 0xFF);
/*     */             
/* 357 */             g += f * (rgb >> 8 & 0xFF);
/*     */             
/* 359 */             b += f * (rgb & 0xFF);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 364 */         int ia = alpha ? clamp((int)(a + 0.5D)) : 255;
/*     */         
/* 366 */         int ir = clamp((int)(r + 0.5D));
/*     */         
/* 368 */         int ig = clamp((int)(g + 0.5D));
/*     */         
/* 370 */         int ib = clamp((int)(b + 0.5D));
/*     */         
/* 372 */         outPixels[index++] = ia << 24 | ir << 16 | ig << 8 | ib;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int clamp(int c) {
/* 378 */     if (c < 0) {
/* 379 */       return 0;
/*     */     }
/*     */     
/* 382 */     return Math.min(c, 255);
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
/* 387 */     int width = src.getWidth();
/*     */     
/* 389 */     int height = src.getHeight();
/*     */ 
/*     */     
/* 392 */     if (dst == null)
/*     */     {
/* 394 */       dst = createCompatibleDestImage(src, (ColorModel)null);
/*     */     }
/*     */ 
/*     */     
/* 398 */     int[] inPixels = new int[width * height];
/*     */     
/* 400 */     int[] outPixels = new int[width * height];
/*     */     
/* 402 */     getRGB(src, 0, 0, width, height, inPixels);
/*     */ 
/*     */     
/* 405 */     if (this.premultiplyAlpha)
/*     */     {
/* 407 */       ImageMath.premultiply(inPixels, 0, inPixels.length);
/*     */     }
/*     */ 
/*     */     
/* 411 */     convolve(this.kernel, inPixels, outPixels, width, height, this.alpha, this.edgeAction);
/*     */ 
/*     */     
/* 414 */     if (this.premultiplyAlpha)
/*     */     {
/* 416 */       ImageMath.unpremultiply(outPixels, 0, outPixels.length);
/*     */     }
/*     */ 
/*     */     
/* 420 */     setRGB(dst, 0, 0, width, height, outPixels);
/*     */     
/* 422 */     return dst;
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
/* 427 */     if (dstCM == null)
/*     */     {
/* 429 */       dstCM = src.getColorModel();
/*     */     }
/*     */ 
/*     */     
/* 433 */     return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle2D getBounds2D(BufferedImage src) {
/* 438 */     return new Rectangle(0, 0, src.getWidth(), src.getHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
/* 443 */     if (dstPt == null)
/*     */     {
/* 445 */       dstPt = new Point2D.Double();
/*     */     }
/*     */ 
/*     */     
/* 449 */     dstPt.setLocation(srcPt.getX(), srcPt.getY());
/*     */     
/* 451 */     return dstPt;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderingHints getRenderingHints() {
/* 456 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 461 */     return "Blur/Convolve...";
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\gaussianblur\ConvolveFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */