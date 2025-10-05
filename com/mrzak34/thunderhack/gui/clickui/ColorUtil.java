/*     */ package com.mrzak34.thunderhack.gui.clickui;
/*     */ 
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.awt.Color;
/*     */ import java.awt.image.BufferedImage;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorUtil
/*     */ {
/*     */   public static int fade(Color color, int delay) {
/*  13 */     float[] hsb = new float[3];
/*  14 */     Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
/*  15 */     float brightness = Math.abs((float)(System.currentTimeMillis() % 2000L + delay) / 1000.0F % 2.0F - 1.0F);
/*  16 */     brightness = 0.5F + 0.5F * brightness;
/*  17 */     hsb[2] = brightness % 2.0F;
/*  18 */     return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
/*     */   }
/*     */   
/*     */   public static int astolfoRainbow2(int counter, int alpha) {
/*  22 */     int width = 110;
/*     */     
/*  24 */     double rainbowState = Math.ceil((System.currentTimeMillis() - counter * 110L)) / 11.0D;
/*  25 */     rainbowState %= 360.0D;
/*  26 */     float hue = ((float)(rainbowState / 360.0D) < 0.5D) ? -((float)(rainbowState / 360.0D)) : (float)(rainbowState / 360.0D);
/*     */ 
/*     */     
/*  29 */     Color color = Color.getHSBColor(hue, 0.7F, 1.0F);
/*  30 */     return (new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha)).getRGB();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color astolfoRainbow2(int offset, float distance, float speedl) {
/*  35 */     float speed = 3000.0F;
/*  36 */     float hue = (float)(System.currentTimeMillis() % (int)speed) + (distance - offset) * speedl;
/*  37 */     while (hue > speed) {
/*  38 */       hue -= speed;
/*     */     }
/*  40 */     hue /= speed;
/*  41 */     if (hue > 0.5D) {
/*  42 */       hue = 0.5F - hue - 0.5F;
/*     */     }
/*  44 */     hue += 0.5F;
/*  45 */     return Color.getHSBColor(hue, 0.4F, 1.0F);
/*     */   }
/*     */   
/*     */   public static Color astolfoRainbow(int offset) {
/*  49 */     float speed = 3000.0F;
/*  50 */     float hue = (float)(System.currentTimeMillis() % (int)speed + offset);
/*  51 */     while (hue > speed) {
/*  52 */       hue -= speed;
/*     */     }
/*  54 */     hue /= speed;
/*  55 */     if (hue > 0.5D) {
/*  56 */       hue = 0.5F - hue - 0.5F;
/*     */     }
/*  58 */     hue += 0.5F;
/*  59 */     return Color.getHSBColor(hue, 0.4F, 1.0F);
/*     */   }
/*     */   
/*     */   public static Color skyRainbow(int speed, int index) {
/*  63 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/*  64 */     float hue = angle / 360.0F;
/*  65 */     return Color.getHSBColor(((float)((angle = (int)(angle % 360.0D)) / 360.0D) < 0.5D) ? -((float)(angle / 360.0D)) : (float)(angle / 360.0D), 0.5F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int rainbow(int delay, double speed) {
/*  70 */     double rainbow = Math.ceil((System.currentTimeMillis() + delay) / speed);
/*  71 */     rainbow %= 360.0D;
/*  72 */     return Color.getHSBColor((float)-(rainbow / 360.0D), 0.9F, 1.0F).getRGB();
/*     */   }
/*     */   
/*     */   public static int getBrightness(Color color, float brightness) {
/*  76 */     float[] hsb = new float[3];
/*  77 */     Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
/*  78 */     brightness = 0.5F + 0.5F * brightness;
/*  79 */     hsb[2] = brightness % 2.0F;
/*  80 */     return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
/*     */   }
/*     */   
/*     */   public static void glColor(Color color) {
/*  84 */     float red = color.getRed() / 255.0F;
/*  85 */     float green = color.getGreen() / 255.0F;
/*  86 */     float blue = color.getBlue() / 255.0F;
/*  87 */     float alpha = color.getAlpha() / 255.0F;
/*     */     
/*  89 */     GlStateManager.func_179131_c(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public static void glColor(int hex, float alpha) {
/*  93 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/*  94 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/*  95 */     float blue = (hex & 0xFF) / 255.0F;
/*     */     
/*  97 */     GlStateManager.func_179131_c(red, green, blue, alpha / 255.0F);
/*     */   }
/*     */   
/*     */   public static void glColor(int hex) {
/* 101 */     float alpha = (hex >> 24 & 0xFF) / 255.0F;
/* 102 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/* 103 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/* 104 */     float blue = (hex & 0xFF) / 255.0F;
/*     */     
/* 106 */     GlStateManager.func_179131_c(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public static Color getColor(int hex, int alpha) {
/* 110 */     float f1 = (hex >> 16 & 0xFF) / 255.0F;
/* 111 */     float f2 = (hex >> 8 & 0xFF) / 255.0F;
/* 112 */     float f3 = (hex & 0xFF) / 255.0F;
/* 113 */     return new Color((int)(f1 * 255.0F), (int)(f2 * 255.0F), (int)(f3 * 255.0F), alpha);
/*     */   }
/*     */   
/*     */   public static Color getColor(Color color, int alpha) {
/* 117 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
/*     */   }
/*     */   
/*     */   public static Color blendColors(float[] fractions, Color[] colors, float progress) {
/* 121 */     if (fractions.length == colors.length) {
/* 122 */       int[] indices = getFractionIndices(fractions, progress);
/* 123 */       float[] range = { fractions[indices[0]], fractions[indices[1]] };
/* 124 */       Color[] colorRange = { colors[indices[0]], colors[indices[1]] };
/* 125 */       float max = range[1] - range[0];
/* 126 */       float value = progress - range[0];
/* 127 */       float weight = value / max;
/* 128 */       return blend(colorRange[0], colorRange[1], (1.0F - weight));
/*     */     } 
/* 130 */     throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
/*     */   }
/*     */   
/*     */   public static int[] getFractionIndices(float[] fractions, float progress) {
/* 134 */     int[] range = new int[2];
/*     */     int startPoint;
/* 136 */     for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; startPoint++);
/*     */     
/* 138 */     if (startPoint >= fractions.length)
/* 139 */       startPoint = fractions.length - 1; 
/* 140 */     range[0] = startPoint - 1;
/* 141 */     range[1] = startPoint;
/* 142 */     return range;
/*     */   }
/*     */   
/*     */   public static Color blend(Color acolor, Color bcolor, double ratio) {
/* 146 */     float r = (float)ratio;
/* 147 */     float ir = 1.0F - r;
/*     */     
/* 149 */     float[] rgb1 = new float[3];
/* 150 */     float[] rgb2 = new float[3];
/*     */     
/* 152 */     acolor.getColorComponents(rgb1);
/* 153 */     bcolor.getColorComponents(rgb2);
/*     */     
/* 155 */     float red = rgb1[0] * r + rgb2[0] * ir;
/* 156 */     float green = rgb1[1] * r + rgb2[1] * ir;
/* 157 */     float blue = rgb1[2] * r + rgb2[2] * ir;
/*     */     
/* 159 */     if (red < 0.0F) {
/* 160 */       red = 0.0F;
/* 161 */     } else if (red > 255.0F) {
/* 162 */       red = 255.0F;
/*     */     } 
/* 164 */     if (green < 0.0F) {
/* 165 */       green = 0.0F;
/* 166 */     } else if (green > 255.0F) {
/* 167 */       green = 255.0F;
/*     */     } 
/* 169 */     if (blue < 0.0F) {
/* 170 */       blue = 0.0F;
/* 171 */     } else if (blue > 255.0F) {
/* 172 */       blue = 255.0F;
/*     */     } 
/*     */     
/* 175 */     Color color = null;
/*     */     try {
/* 177 */       color = new Color(red, green, blue);
/* 178 */     } catch (IllegalArgumentException illegalArgumentException) {}
/*     */     
/* 180 */     return color;
/*     */   }
/*     */   
/*     */   public static int evaluate(float fraction, int startValue, int endValue) {
/* 184 */     float startA = (startValue >> 24 & 0xFF) / 255.0F;
/* 185 */     float startR = (startValue >> 16 & 0xFF) / 255.0F;
/* 186 */     float startG = (startValue >> 8 & 0xFF) / 255.0F;
/* 187 */     float startB = (startValue & 0xFF) / 255.0F;
/*     */     
/* 189 */     float endA = (endValue >> 24 & 0xFF) / 255.0F;
/* 190 */     float endR = (endValue >> 16 & 0xFF) / 255.0F;
/* 191 */     float endG = (endValue >> 8 & 0xFF) / 255.0F;
/* 192 */     float endB = (endValue & 0xFF) / 255.0F;
/*     */     
/* 194 */     startR = (float)Math.pow(startR, 2.2D);
/* 195 */     startG = (float)Math.pow(startG, 2.2D);
/* 196 */     startB = (float)Math.pow(startB, 2.2D);
/*     */     
/* 198 */     endR = (float)Math.pow(endR, 2.2D);
/* 199 */     endG = (float)Math.pow(endG, 2.2D);
/* 200 */     endB = (float)Math.pow(endB, 2.2D);
/*     */     
/* 202 */     float a = MathUtil.lerp(fraction, startA, endA);
/* 203 */     float r = MathUtil.lerp(fraction, startR, endR);
/* 204 */     float g = MathUtil.lerp(fraction, startG, endG);
/* 205 */     float b = MathUtil.lerp(fraction, startB, endB);
/*     */     
/* 207 */     a *= 255.0F;
/* 208 */     r = (float)Math.pow(r, 0.45454545454545453D) * 255.0F;
/* 209 */     g = (float)Math.pow(g, 0.45454545454545453D) * 255.0F;
/* 210 */     b = (float)Math.pow(b, 0.45454545454545453D) * 255.0F;
/*     */     
/* 212 */     return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
/*     */   }
/*     */   
/*     */   public static Color[] getAnalogousColor(Color color) {
/* 216 */     Color[] colors = new Color[2];
/* 217 */     float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
/*     */     
/* 219 */     float degree = 0.083333336F;
/*     */     
/* 221 */     float newHueAdded = hsb[0] + degree;
/* 222 */     colors[0] = new Color(Color.HSBtoRGB(newHueAdded, hsb[1], hsb[2]));
/*     */     
/* 224 */     float newHueSubtracted = hsb[0] - degree;
/*     */     
/* 226 */     colors[1] = new Color(Color.HSBtoRGB(newHueSubtracted, hsb[1], hsb[2]));
/*     */     
/* 228 */     return colors;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] rgbToHsv(int rgb) {
/* 233 */     float h, r = ((rgb & 0xFF0000) >> 16) / 255.0F;
/* 234 */     float g = ((rgb & 0xFF00) >> 8) / 255.0F;
/* 235 */     float b = (rgb & 0xFF) / 255.0F;
/* 236 */     float M = (r > g) ? ((r > b) ? r : b) : ((g > b) ? g : b);
/* 237 */     float m = (r < g) ? ((r < b) ? r : b) : ((g < b) ? g : b);
/* 238 */     float c = M - m;
/*     */     
/* 240 */     if (M == r) {
/* 241 */       h = (g - b) / c;
/* 242 */       while (h < 0.0F)
/* 243 */         h += 6.0F; 
/* 244 */       h %= 6.0F;
/* 245 */     } else if (M == g) {
/* 246 */       h = (b - r) / c + 2.0F;
/*     */     } else {
/* 248 */       h = (r - g) / c + 4.0F;
/*     */     } 
/* 250 */     h *= 60.0F;
/* 251 */     float s = c / M;
/* 252 */     return new int[] { (c == 0.0F) ? -1 : (int)h, (int)(s * 100.0F), (int)(M * 100.0F) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color hslToRGB(float[] hsl) {
/*     */     float red, green, blue;
/* 260 */     if (hsl[1] == 0.0F) {
/* 261 */       red = green = blue = 1.0F;
/*     */     } else {
/* 263 */       float q = (hsl[2] < 0.5D) ? (hsl[2] * (1.0F + hsl[1])) : (hsl[2] + hsl[1] - hsl[2] * hsl[1]);
/* 264 */       float p = 2.0F * hsl[2] - q;
/*     */       
/* 266 */       red = hueToRGB(p, q, hsl[0] + 0.33333334F);
/* 267 */       green = hueToRGB(p, q, hsl[0]);
/* 268 */       blue = hueToRGB(p, q, hsl[0] - 0.33333334F);
/*     */     } 
/*     */     
/* 271 */     red *= 255.0F;
/* 272 */     green *= 255.0F;
/* 273 */     blue *= 255.0F;
/*     */     
/* 275 */     return new Color((int)red, (int)green, (int)blue);
/*     */   }
/*     */   
/*     */   public static float hueToRGB(float p, float q, float t) {
/* 279 */     float newT = t;
/* 280 */     if (newT < 0.0F)
/* 281 */       newT++; 
/* 282 */     if (newT > 1.0F)
/* 283 */       newT--; 
/* 284 */     if (newT < 0.16666667F)
/* 285 */       return p + (q - p) * 6.0F * newT; 
/* 286 */     if (newT < 0.5F)
/* 287 */       return q; 
/* 288 */     if (newT < 0.6666667F)
/* 289 */       return p + (q - p) * (0.6666667F - newT) * 6.0F; 
/* 290 */     return p;
/*     */   }
/*     */   
/*     */   public static int hsvToRgb(int hue, int saturation, int value) {
/*     */     float r, g, b, m;
/* 295 */     hue %= 360;
/* 296 */     float s = saturation / 100.0F;
/* 297 */     float v = value / 100.0F;
/* 298 */     float c = v * s;
/* 299 */     float h = hue / 60.0F;
/* 300 */     float x = c * (1.0F - Math.abs(h % 2.0F - 1.0F));
/*     */     
/* 302 */     switch (hue / 60) {
/*     */       case 0:
/* 304 */         r = c;
/* 305 */         g = x;
/* 306 */         b = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 336 */         m = v - c;
/* 337 */         return (int)((r + m) * 255.0F) << 16 | (int)((g + m) * 255.0F) << 8 | (int)((b + m) * 255.0F);case 1: r = x; g = c; b = 0.0F; m = v - c; return (int)((r + m) * 255.0F) << 16 | (int)((g + m) * 255.0F) << 8 | (int)((b + m) * 255.0F);case 2: r = 0.0F; g = c; b = x; m = v - c; return (int)((r + m) * 255.0F) << 16 | (int)((g + m) * 255.0F) << 8 | (int)((b + m) * 255.0F);case 3: r = 0.0F; g = x; b = c; m = v - c; return (int)((r + m) * 255.0F) << 16 | (int)((g + m) * 255.0F) << 8 | (int)((b + m) * 255.0F);case 4: r = x; g = 0.0F; b = c; m = v - c; return (int)((r + m) * 255.0F) << 16 | (int)((g + m) * 255.0F) << 8 | (int)((b + m) * 255.0F);case 5: r = c; g = 0.0F; b = x; m = v - c; return (int)((r + m) * 255.0F) << 16 | (int)((g + m) * 255.0F) << 8 | (int)((b + m) * 255.0F);
/*     */     } 
/*     */     return 0;
/*     */   } public static float[] rgbToHSL(Color rgb) {
/* 341 */     float red = rgb.getRed() / 255.0F;
/* 342 */     float green = rgb.getGreen() / 255.0F;
/* 343 */     float blue = rgb.getBlue() / 255.0F;
/*     */     
/* 345 */     float max = Math.max(Math.max(red, green), blue);
/* 346 */     float min = Math.min(Math.min(red, green), blue);
/* 347 */     float c = (max + min) / 2.0F;
/* 348 */     float[] hsl = { c, c, c };
/*     */     
/* 350 */     if (max == min) {
/* 351 */       hsl[1] = 0.0F; hsl[0] = 0.0F;
/*     */     } else {
/* 353 */       float d = max - min;
/* 354 */       hsl[1] = (hsl[2] > 0.5D) ? (d / (2.0F - max - min)) : (d / (max + min));
/*     */       
/* 356 */       if (max == red) {
/* 357 */         hsl[0] = (green - blue) / d + ((green < blue) ? 6 : false);
/* 358 */       } else if (max == blue) {
/* 359 */         hsl[0] = (blue - red) / d + 2.0F;
/* 360 */       } else if (max == green) {
/* 361 */         hsl[0] = (red - green) / d + 4.0F;
/*     */       } 
/* 363 */       hsl[0] = hsl[0] / 6.0F;
/*     */     } 
/* 365 */     return hsl;
/*     */   }
/*     */   
/*     */   public static Color imitateTransparency(Color backgroundColor, Color accentColor, float percentage) {
/* 369 */     return new Color(interpolateColor(backgroundColor, accentColor, 255.0F * percentage / 255.0F));
/*     */   }
/*     */   
/*     */   public static int applyOpacity(int color, float opacity) {
/* 373 */     Color old = new Color(color);
/* 374 */     return applyOpacity(old, opacity).getRGB();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color applyOpacity(Color color, float opacity) {
/* 379 */     opacity = Math.min(1.0F, Math.max(0.0F, opacity));
/*     */ 
/*     */     
/* 382 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * opacity));
/*     */   }
/*     */   
/*     */   public static Color darker(Color color, float FACTOR) {
/* 386 */     return new Color(Math.max((int)(color.getRed() * FACTOR), 0), Math.max((int)(color.getGreen() * FACTOR), 0), Math.max((int)(color.getBlue() * FACTOR), 0), color.getAlpha());
/*     */   }
/*     */   
/*     */   public static Color brighter(Color color, float FACTOR) {
/* 390 */     int r = color.getRed();
/* 391 */     int g = color.getGreen();
/* 392 */     int b = color.getBlue();
/* 393 */     int alpha = color.getAlpha();
/*     */     
/* 395 */     int i = (int)(1.0D / (1.0D - FACTOR));
/* 396 */     if (r == 0 && g == 0 && b == 0) {
/* 397 */       return new Color(i, i, i, alpha);
/*     */     }
/* 399 */     if (r > 0 && r < i)
/* 400 */       r = i; 
/* 401 */     if (g > 0 && g < i)
/* 402 */       g = i; 
/* 403 */     if (b > 0 && b < i) {
/* 404 */       b = i;
/*     */     }
/* 406 */     return new Color(Math.min((int)(r / FACTOR), 255), Math.min((int)(g / FACTOR), 255), 
/* 407 */         Math.min((int)(b / FACTOR), 255), alpha);
/*     */   }
/*     */   
/*     */   public static Color averageColor(BufferedImage bi, int width, int height, int pixelStep) {
/* 411 */     int[] color = new int[3]; int x;
/* 412 */     for (x = 0; x < width; x += pixelStep) {
/* 413 */       int y; for (y = 0; y < height; y += pixelStep) {
/* 414 */         Color pixel = new Color(bi.getRGB(x, y));
/* 415 */         color[0] = color[0] + pixel.getRed();
/* 416 */         color[1] = color[1] + pixel.getGreen();
/* 417 */         color[2] = color[2] + pixel.getBlue();
/*     */       } 
/*     */     } 
/* 420 */     int num = width * height / pixelStep * pixelStep;
/* 421 */     return new Color(color[0] / num, color[1] / num, color[2] / num);
/*     */   }
/*     */   
/*     */   public static Color rainbow(int speed, int index, float saturation, float brightness, float opacity) {
/* 425 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 426 */     float hue = angle / 360.0F;
/* 427 */     Color color = new Color(Color.HSBtoRGB(hue, saturation, brightness));
/* 428 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), 
/* 429 */         Math.max(0, Math.min(255, (int)(opacity * 255.0F))));
/*     */   }
/*     */   
/*     */   public static Color interpolateColorsBackAndForth(int speed, int index, Color start, Color end, boolean trueColor) {
/* 433 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 434 */     angle = ((angle >= 180) ? (360 - angle) : angle) * 2;
/* 435 */     return trueColor ? interpolateColorHue(start, end, angle / 360.0F) : 
/* 436 */       interpolateColorC(start, end, angle / 360.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int interpolateColor(Color color1, Color color2, float amount) {
/* 441 */     amount = Math.min(1.0F, Math.max(0.0F, amount));
/* 442 */     return interpolateColorC(color1, color2, amount).getRGB();
/*     */   }
/*     */   
/*     */   public static int interpolateColor(int color1, int color2, float amount) {
/* 446 */     amount = Math.min(1.0F, Math.max(0.0F, amount));
/* 447 */     Color cColor1 = new Color(color1);
/* 448 */     Color cColor2 = new Color(color2);
/* 449 */     return interpolateColorC(cColor1, cColor2, amount).getRGB();
/*     */   }
/*     */   
/*     */   public static Color interpolateColorC(Color color1, Color color2, float amount) {
/* 453 */     amount = Math.min(1.0F, Math.max(0.0F, amount));
/* 454 */     return new Color(interpolateInt(color1.getRed(), color2.getRed(), amount), 
/* 455 */         interpolateInt(color1.getGreen(), color2.getGreen(), amount), 
/* 456 */         interpolateInt(color1.getBlue(), color2.getBlue(), amount), 
/* 457 */         interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
/*     */   }
/*     */   
/*     */   public static Color interpolateColorHue(Color color1, Color color2, float amount) {
/* 461 */     amount = Math.min(1.0F, Math.max(0.0F, amount));
/*     */     
/* 463 */     float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
/* 464 */     float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);
/*     */     
/* 466 */     Color resultColor = Color.getHSBColor(interpolateFloat(color1HSB[0], color2HSB[0], amount), 
/* 467 */         interpolateFloat(color1HSB[1], color2HSB[1], amount), 
/* 468 */         interpolateFloat(color1HSB[2], color2HSB[2], amount));
/*     */     
/* 470 */     return new Color(resultColor.getRed(), resultColor.getGreen(), resultColor.getBlue(), 
/* 471 */         interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
/*     */   }
/*     */   
/*     */   public static Color fade(int speed, int index, Color color, float alpha) {
/* 475 */     float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
/* 476 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 477 */     angle = ((angle > 180) ? (360 - angle) : angle) + 180;
/*     */     
/* 479 */     Color colorHSB = new Color(Color.HSBtoRGB(hsb[0], hsb[1], angle / 360.0F));
/*     */     
/* 481 */     return new Color(colorHSB.getRed(), colorHSB.getGreen(), colorHSB.getBlue(), Math.max(0, Math.min(255, (int)(alpha * 255.0F))));
/*     */   }
/*     */   
/*     */   private static float getAnimationEquation(int index, int speed) {
/* 485 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 486 */     return (((angle > 180) ? (360 - angle) : angle) + 180) / 360.0F;
/*     */   }
/*     */   
/*     */   public static int[] createColorArray(int color) {
/* 490 */     return new int[] { bitChangeColor(color, 16), bitChangeColor(color, 8), bitChangeColor(color, 0), 
/* 491 */         bitChangeColor(color, 24) };
/*     */   }
/*     */   
/*     */   public static int getOppositeColor(int color) {
/* 495 */     int R = bitChangeColor(color, 0);
/* 496 */     int G = bitChangeColor(color, 8);
/* 497 */     int B = bitChangeColor(color, 16);
/* 498 */     int A = bitChangeColor(color, 24);
/* 499 */     R = 255 - R;
/* 500 */     G = 255 - G;
/* 501 */     B = 255 - B;
/* 502 */     return R + (G << 8) + (B << 16) + (A << 24);
/*     */   }
/*     */   
/*     */   private static int bitChangeColor(int color, int bitChange) {
/* 506 */     return color >> bitChange & 0xFF;
/*     */   }
/*     */   
/*     */   public static Double interpolate(double oldValue, double newValue, double interpolationValue) {
/* 510 */     return Double.valueOf(oldValue + (newValue - oldValue) * interpolationValue);
/*     */   }
/*     */   
/*     */   public static float interpolateFloat(float oldValue, float newValue, double interpolationValue) {
/* 514 */     return interpolate(oldValue, newValue, (float)interpolationValue).floatValue();
/*     */   }
/*     */   
/*     */   public static int interpolateInt(int oldValue, int newValue, double interpolationValue) {
/* 518 */     return interpolate(oldValue, newValue, (float)interpolationValue).intValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\ColorUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */