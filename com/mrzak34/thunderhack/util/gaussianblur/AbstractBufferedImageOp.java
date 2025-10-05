/*    */ package com.mrzak34.thunderhack.util.gaussianblur;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.RenderingHints;
/*    */ import java.awt.geom.Point2D;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.awt.image.BufferedImageOp;
/*    */ import java.awt.image.ColorModel;
/*    */ 
/*    */ public abstract class AbstractBufferedImageOp implements BufferedImageOp, Cloneable {
/*    */   public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
/* 13 */     if (dstCM == null) {
/* 14 */       dstCM = src.getColorModel();
/*    */     }
/*    */     
/* 17 */     return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Rectangle2D getBounds2D(BufferedImage src) {
/* 23 */     return new Rectangle(0, 0, src.getWidth(), src.getHeight());
/*    */   }
/*    */ 
/*    */   
/*    */   public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
/* 28 */     if (dstPt == null) {
/* 29 */       dstPt = new Point2D.Double();
/*    */     }
/*    */     
/* 32 */     dstPt.setLocation(srcPt.getX(), srcPt.getY());
/* 33 */     return dstPt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RenderingHints getRenderingHints() {
/* 39 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
/* 44 */     int type = image.getType();
/*    */     
/* 46 */     if (type == 2 || type == 1) {
/* 47 */       return (int[])image.getRaster().getDataElements(x, y, width, height, pixels);
/*    */     }
/*    */     
/* 50 */     return image.getRGB(x, y, width, height, pixels, 0, width);
/*    */   }
/*    */   
/*    */   public void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
/* 54 */     int type = image.getType();
/*    */     
/* 56 */     if (type == 2 || type == 1) {
/*    */       
/* 58 */       image.getRaster().setDataElements(x, y, width, height, pixels);
/*    */     } else {
/*    */       
/* 61 */       image.setRGB(x, y, width, height, pixels, 0, width);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object clone() {
/*    */     try {
/* 68 */       return super.clone();
/* 69 */     } catch (CloneNotSupportedException e) {
/*    */       
/* 71 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\gaussianblur\AbstractBufferedImageOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */