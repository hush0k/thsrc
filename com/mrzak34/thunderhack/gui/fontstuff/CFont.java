/*     */ package com.mrzak34.thunderhack.gui.fontstuff;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CFont {
/*  13 */   private final float imgSize = 512.0F;
/*  14 */   protected CharData[] charData = new CharData[1105];
/*     */   protected Font font;
/*     */   protected boolean antiAlias;
/*     */   protected boolean fractionalMetrics;
/*  18 */   protected int fontHeight = -1;
/*  19 */   protected int charOffset = 0;
/*     */   protected DynamicTexture tex;
/*     */   
/*     */   public CFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
/*  23 */     this.font = font;
/*  24 */     this.antiAlias = antiAlias;
/*  25 */     this.fractionalMetrics = fractionalMetrics;
/*  26 */     this.tex = setupTexture(font, antiAlias, fractionalMetrics, this.charData);
/*     */   }
/*     */   
/*     */   protected DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  30 */     BufferedImage img = generateFontImage(font, antiAlias, fractionalMetrics, chars);
/*     */     
/*     */     try {
/*  33 */       return new DynamicTexture(img);
/*  34 */     } catch (Exception e) {
/*  35 */       e.printStackTrace();
/*     */ 
/*     */       
/*  38 */       return null;
/*     */     } 
/*     */   }
/*     */   public int getHeight() {
/*  42 */     return (this.fontHeight - 8) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  47 */     getClass(); int imgSize = 512;
/*  48 */     BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, 2);
/*  49 */     Graphics2D g = (Graphics2D)bufferedImage.getGraphics();
/*  50 */     g.setFont(font);
/*  51 */     g.setColor(new Color(255, 255, 255, 0));
/*  52 */     g.fillRect(0, 0, imgSize, imgSize);
/*  53 */     g.setColor(Color.WHITE);
/*  54 */     g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
/*     */ 
/*     */     
/*  57 */     g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*     */     
/*  59 */     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
/*     */     
/*  61 */     FontMetrics fontMetrics = g.getFontMetrics();
/*  62 */     int charHeight = 0;
/*  63 */     int positionX = 0;
/*  64 */     int positionY = 1;
/*     */     
/*  66 */     for (int i = 0; i <= chars.length; i++) {
/*  67 */       char ch = (char)i;
/*  68 */       if ((ch > 'Џ' && ch < 'ѐ') || ch < 'Ā') {
/*     */         
/*  70 */         CharData charData = new CharData();
/*  71 */         Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), g);
/*  72 */         charData.width = (dimensions.getBounds()).width + 8;
/*  73 */         charData.height = (dimensions.getBounds()).height;
/*     */         
/*  75 */         if (positionX + charData.width >= imgSize) {
/*  76 */           positionX = 0;
/*  77 */           positionY += charHeight;
/*  78 */           charHeight = 0;
/*     */         } 
/*     */         
/*  81 */         if (charData.height > charHeight) {
/*  82 */           charHeight = charData.height;
/*     */         }
/*     */         
/*  85 */         charData.storedX = positionX;
/*  86 */         charData.storedY = positionY;
/*     */         
/*  88 */         if (charData.height > this.fontHeight) {
/*  89 */           this.fontHeight = charData.height;
/*     */         }
/*     */         
/*  92 */         chars[i] = charData;
/*  93 */         g.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
/*  94 */         positionX += charData.width;
/*     */       } 
/*     */     } 
/*  97 */     return bufferedImage;
/*     */   }
/*     */   
/*     */   public void drawChar(CharData[] chars, char c, float x, float y) throws ArrayIndexOutOfBoundsException {
/*     */     try {
/* 102 */       drawQuad(x, y, (chars[c]).width, (chars[c]).height, (chars[c]).storedX, (chars[c]).storedY, (chars[c]).width, (chars[c]).height);
/*     */     }
/* 104 */     catch (Exception e) {
/* 105 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
/*     */     try {
/* 112 */       float renderSRCX = srcX / 512.0F;
/* 113 */       float renderSRCY = srcY / 512.0F;
/* 114 */       float renderSRCWidth = srcWidth / 512.0F;
/* 115 */       float renderSRCHeight = srcHeight / 512.0F;
/* 116 */       GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 117 */       GL11.glVertex2d((x + width), y);
/* 118 */       GL11.glTexCoord2f(renderSRCX, renderSRCY);
/* 119 */       GL11.glVertex2d(x, y);
/* 120 */       GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 121 */       GL11.glVertex2d(x, (y + height));
/* 122 */       GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 123 */       GL11.glVertex2d(x, (y + height));
/* 124 */       GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
/* 125 */       GL11.glVertex2d((x + width), (y + height));
/* 126 */       GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 127 */       GL11.glVertex2d((x + width), y);
/* 128 */     } catch (Exception e) {
/* 129 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getStringHeight(String text) {
/* 134 */     return getFontHeight();
/*     */   }
/*     */   
/*     */   public int getFontHeight() {
/* 138 */     return (this.fontHeight - 8) / 2;
/*     */   }
/*     */   
/*     */   public int getStringWidth(String text) {
/* 142 */     int width = 0;
/*     */     
/* 144 */     for (char c : text.toCharArray()) {
/* 145 */       if (c < this.charData.length) {
/* 146 */         width += (this.charData[c]).width - 8 + this.charOffset;
/*     */       }
/*     */     } 
/*     */     
/* 150 */     return width / 2;
/*     */   }
/*     */   
/*     */   public boolean isAntiAlias() {
/* 154 */     return this.antiAlias;
/*     */   }
/*     */   
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 158 */     if (this.antiAlias != antiAlias) {
/* 159 */       this.antiAlias = antiAlias;
/* 160 */       this.tex = setupTexture(this.font, antiAlias, this.fractionalMetrics, this.charData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFractionalMetrics() {
/* 165 */     return this.fractionalMetrics;
/*     */   }
/*     */   
/*     */   public void setFractionalMetrics(boolean fractionalMetrics) {
/* 169 */     if (this.fractionalMetrics != fractionalMetrics) {
/* 170 */       this.fractionalMetrics = fractionalMetrics;
/* 171 */       this.tex = setupTexture(this.font, this.antiAlias, fractionalMetrics, this.charData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Font getFont() {
/* 176 */     return this.font;
/*     */   }
/*     */   
/*     */   public void setFont(Font font) {
/* 180 */     this.font = font;
/* 181 */     this.tex = setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
/*     */   }
/*     */   
/*     */   protected static class CharData {
/*     */     public int width;
/*     */     public int height;
/*     */     public int storedX;
/*     */     public int storedY;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\fontstuff\CFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */