/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Particles
/*     */ {
/*     */   public double x;
/*     */   public double y;
/*     */   public double deltaX;
/*     */   
/*     */   public static void vertex(double x, double y) {
/*  14 */     GL11.glVertex2d(x, y);
/*     */   }
/*     */   public double deltaY; public double size; public double opacity; public Color color;
/*     */   public static void color(double red, double green, double blue, double alpha) {
/*  18 */     GL11.glColor4d(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void roundedRect(double x, double y, double width, double height, double edgeRadius, Color color) {
/*  23 */     double halfRadius = edgeRadius / 2.0D;
/*  24 */     width -= halfRadius;
/*  25 */     height -= halfRadius;
/*     */     
/*  27 */     float sideLength = (float)edgeRadius;
/*  28 */     sideLength /= 2.0F;
/*  29 */     start();
/*  30 */     color(color);
/*     */     
/*  32 */     begin(6);
/*     */     
/*     */     double i;
/*  35 */     for (i = 180.0D; i <= 270.0D; i++) {
/*  36 */       double angle = i * 6.283185307179586D / 360.0D;
/*  37 */       vertex(x + sideLength * Math.cos(angle) + sideLength, y + sideLength * Math.sin(angle) + sideLength);
/*     */     } 
/*  39 */     vertex(x + sideLength, y + sideLength);
/*     */ 
/*     */     
/*  42 */     end();
/*  43 */     stop();
/*     */     
/*  45 */     sideLength = (float)edgeRadius;
/*  46 */     sideLength /= 2.0F;
/*  47 */     start();
/*  48 */     color(color);
/*  49 */     GL11.glEnable(2848);
/*  50 */     begin(6);
/*     */ 
/*     */     
/*  53 */     for (i = 0.0D; i <= 90.0D; i++) {
/*  54 */       double angle = i * 6.283185307179586D / 360.0D;
/*  55 */       vertex(x + width + sideLength * Math.cos(angle), y + height + sideLength * Math.sin(angle));
/*     */     } 
/*  57 */     vertex(x + width, y + height);
/*     */ 
/*     */     
/*  60 */     end();
/*  61 */     GL11.glDisable(2848);
/*  62 */     stop();
/*     */     
/*  64 */     sideLength = (float)edgeRadius;
/*  65 */     sideLength /= 2.0F;
/*  66 */     start();
/*  67 */     color(color);
/*  68 */     GL11.glEnable(2848);
/*  69 */     begin(6);
/*     */ 
/*     */     
/*  72 */     for (i = 270.0D; i <= 360.0D; i++) {
/*  73 */       double angle = i * 6.283185307179586D / 360.0D;
/*  74 */       vertex(x + width + sideLength * Math.cos(angle), y + sideLength * Math.sin(angle) + sideLength);
/*     */     } 
/*  76 */     vertex(x + width, y + sideLength);
/*     */ 
/*     */     
/*  79 */     end();
/*  80 */     GL11.glDisable(2848);
/*  81 */     stop();
/*     */     
/*  83 */     sideLength = (float)edgeRadius;
/*  84 */     sideLength /= 2.0F;
/*  85 */     start();
/*  86 */     color(color);
/*  87 */     GL11.glEnable(2848);
/*  88 */     begin(6);
/*     */ 
/*     */     
/*  91 */     for (i = 90.0D; i <= 180.0D; i++) {
/*  92 */       double angle = i * 6.283185307179586D / 360.0D;
/*  93 */       vertex(x + sideLength * Math.cos(angle) + sideLength, y + height + sideLength * Math.sin(angle));
/*     */     } 
/*  95 */     vertex(x + sideLength, y + height);
/*     */ 
/*     */     
/*  98 */     end();
/*  99 */     GL11.glDisable(2848);
/* 100 */     stop();
/*     */ 
/*     */     
/* 103 */     rect(x + halfRadius, y + halfRadius, width - halfRadius, height - halfRadius, color);
/*     */ 
/*     */     
/* 106 */     rect(x, y + halfRadius, edgeRadius / 2.0D, height - halfRadius, color);
/* 107 */     rect(x + width, y + halfRadius, edgeRadius / 2.0D, height - halfRadius, color);
/*     */ 
/*     */     
/* 110 */     rect(x + halfRadius, y, width - halfRadius, halfRadius, color);
/* 111 */     rect(x + halfRadius, y + height, width - halfRadius, halfRadius, color);
/*     */   }
/*     */   
/*     */   public static Color mixColors(Color color1, Color color2, double percent) {
/* 115 */     double inverse_percent = 1.0D - percent;
/* 116 */     int redPart = (int)(color1.getRed() * percent + color2.getRed() * inverse_percent);
/* 117 */     int greenPart = (int)(color1.getGreen() * percent + color2.getGreen() * inverse_percent);
/* 118 */     int bluePart = (int)(color1.getBlue() * percent + color2.getBlue() * inverse_percent);
/* 119 */     return new Color(redPart, greenPart, bluePart);
/*     */   }
/*     */   
/*     */   public static void rect(double x, double y, double width, double height, Color color) {
/* 123 */     rect(x, y, width, height, true, color);
/*     */   }
/*     */   
/*     */   public static void rect(double x, double y, double width, double height, boolean filled, Color color) {
/* 127 */     start();
/* 128 */     if (color != null)
/* 129 */       color(color); 
/* 130 */     begin(filled ? 6 : 1);
/*     */ 
/*     */     
/* 133 */     vertex(x, y);
/* 134 */     vertex(x + width, y);
/* 135 */     vertex(x + width, y + height);
/* 136 */     vertex(x, y + height);
/* 137 */     if (!filled) {
/* 138 */       vertex(x, y);
/* 139 */       vertex(x, y + height);
/* 140 */       vertex(x + width, y);
/* 141 */       vertex(x + width, y + height);
/*     */     } 
/*     */     
/* 144 */     end();
/* 145 */     stop();
/*     */   }
/*     */   
/*     */   public static void color(Color color) {
/* 149 */     if (color == null)
/* 150 */       color = Color.white; 
/* 151 */     color((color.getRed() / 255.0F), (color.getGreen() / 255.0F), (color.getBlue() / 255.0F), (color.getAlpha() / 255.0F));
/*     */   }
/*     */   
/*     */   public static void enable(int glTarget) {
/* 155 */     GL11.glEnable(glTarget);
/*     */   }
/*     */   
/*     */   public static void begin(int glMode) {
/* 159 */     GL11.glBegin(glMode);
/*     */   }
/*     */   
/*     */   public static void end() {
/* 163 */     GL11.glEnd();
/*     */   }
/*     */   
/*     */   public static void disable(int glTarget) {
/* 167 */     GL11.glDisable(glTarget);
/*     */   }
/*     */   
/*     */   public static void start() {
/* 171 */     enable(3042);
/* 172 */     GL11.glBlendFunc(770, 771);
/* 173 */     disable(3553);
/* 174 */     disable(2884);
/* 175 */     GlStateManager.func_179118_c();
/* 176 */     GlStateManager.func_179097_i();
/*     */   }
/*     */   
/*     */   public static void stop() {
/* 180 */     GlStateManager.func_179141_d();
/* 181 */     GlStateManager.func_179126_j();
/* 182 */     enable(2884);
/* 183 */     enable(3553);
/* 184 */     disable(3042);
/* 185 */     color(Color.white);
/*     */   }
/*     */   
/*     */   public void render2D() {
/* 189 */     circle(this.x, this.y, this.size, new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), (int)this.opacity));
/*     */   }
/*     */   
/*     */   public void updatePosition() {
/* 193 */     this.x += this.deltaX * 2.0D;
/* 194 */     this.y += this.deltaY * 2.0D;
/* 195 */     this.deltaY *= 0.95D;
/* 196 */     this.deltaX *= 0.95D;
/* 197 */     this.opacity -= 2.0D;
/* 198 */     if (this.opacity < 1.0D) this.opacity = 1.0D; 
/*     */   }
/*     */   
/*     */   public void init(double x, double y, double deltaX, double deltaY, double size, Color color) {
/* 202 */     this.x = x;
/* 203 */     this.y = y;
/* 204 */     this.deltaX = deltaX;
/* 205 */     this.deltaY = deltaY;
/* 206 */     this.size = size;
/* 207 */     this.opacity = 254.0D;
/* 208 */     this.color = color;
/*     */   }
/*     */   
/*     */   public void circle(double x, double y, double radius, Color color) {
/* 212 */     polygon(x, y, radius, 360, color);
/*     */   }
/*     */   
/*     */   public void polygon(double x, double y, double sideLength, double amountOfSides, boolean filled, Color color) {
/* 216 */     sideLength /= 2.0D;
/* 217 */     start();
/* 218 */     if (color != null)
/* 219 */       color(color); 
/* 220 */     if (!filled) GL11.glLineWidth(2.0F); 
/* 221 */     GL11.glEnable(2848);
/* 222 */     begin(filled ? 6 : 3);
/*     */     double i;
/* 224 */     for (i = 0.0D; i <= amountOfSides / 4.0D; i++) {
/* 225 */       double angle = i * 4.0D * 6.283185307179586D / 360.0D;
/* 226 */       vertex(x + sideLength * Math.cos(angle) + sideLength, y + sideLength * Math.sin(angle) + sideLength);
/*     */     } 
/*     */     
/* 229 */     end();
/* 230 */     GL11.glDisable(2848);
/* 231 */     stop();
/*     */   }
/*     */   
/*     */   public void color(double red, double green, double blue) {
/* 235 */     color(red, green, blue, 1.0D);
/*     */   }
/*     */   
/*     */   public void polygon(double x, double y, double sideLength, int amountOfSides, Color color) {
/* 239 */     polygon(x, y, sideLength, amountOfSides, true, color);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\Particles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */