/*     */ package com.mrzak34.thunderhack.util.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.gaussianblur.GaussianFilter;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Drawable
/*     */ {
/*  23 */   private static final HashMap<Integer, Integer> shadowCache = new HashMap<>();
/*     */   
/*     */   public static void drawTexture(ResourceLocation texture, double x, double y, double width, double height) {
/*  26 */     drawTexture(texture, x, y, width, height, Color.WHITE);
/*     */   }
/*     */   
/*     */   public static void drawTexture(ResourceLocation texture, double x, double y, double width, double height, Color color) {
/*  30 */     Util.mc.func_110434_K().func_110577_a(texture);
/*  31 */     RenderUtil.glColor(color);
/*  32 */     BufferBuilder bufferBuilder = Tessellator.func_178181_a().func_178180_c();
/*  33 */     bufferBuilder.func_181668_a(4, DefaultVertexFormats.field_181709_i);
/*  34 */     bufferBuilder.func_181662_b(x + width, y, 0.0D).func_187315_a(1.0D, 0.0D).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
/*  35 */     bufferBuilder.func_181662_b(x, y, 0.0D).func_187315_a(0.0D, 0.0D).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
/*  36 */     bufferBuilder.func_181662_b(x, y + height, 0.0D).func_187315_a(0.0D, 1.0D).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
/*  37 */     bufferBuilder.func_181662_b(x, y + height, 0.0D).func_187315_a(0.0D, 1.0D).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
/*  38 */     bufferBuilder.func_181662_b(x + width, y + height, 0.0D).func_187315_a(1.0D, 1.0D).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
/*  39 */     bufferBuilder.func_181662_b(x + width, y, 0.0D).func_187315_a(1.0D, 0.0D).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
/*  40 */     draw(true);
/*  41 */     GlStateManager.func_179117_G();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void draw(boolean texture) {
/*  46 */     GlStateManager.func_179097_i();
/*  47 */     GlStateManager.func_179147_l();
/*  48 */     GlStateManager.func_179112_b(770, 771);
/*  49 */     GlStateManager.func_179118_c();
/*  50 */     GlStateManager.func_179140_f();
/*     */     
/*  52 */     GlStateManager.func_179129_p();
/*  53 */     GL11.glEnable(2848);
/*  54 */     GlStateManager.func_179103_j(7425);
/*     */     
/*  56 */     if (texture) {
/*  57 */       GlStateManager.func_179098_w();
/*     */     } else {
/*  59 */       GlStateManager.func_179090_x();
/*     */     } 
/*     */     
/*  62 */     Tessellator.func_178181_a().func_78381_a();
/*     */     
/*  64 */     GlStateManager.func_179141_d();
/*  65 */     GlStateManager.func_179126_j();
/*  66 */     GlStateManager.func_179098_w();
/*  67 */     GL11.glDisable(2848);
/*     */   }
/*     */   
/*     */   public static void drawRect(Rectangle r, Color color) {
/*  71 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  72 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  73 */     bufferbuilder.func_181668_a(2, DefaultVertexFormats.field_181706_f);
/*  74 */     bufferbuilder.func_181662_b(r.x, (r.y + r.height), 0.0D).func_181666_a(color.getRed() / 255.0F, color.getGreen() / 255.0F, color
/*  75 */         .getBlue() / 255.0F, color.getAlpha() / 255.0F).func_181675_d();
/*  76 */     bufferbuilder.func_181662_b((r.x + r.width), (r.y + r.height), 0.0D).func_181666_a(color.getRed() / 255.0F, color
/*  77 */         .getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F).func_181675_d();
/*  78 */     bufferbuilder.func_181662_b((r.x + r.width), r.y, 0.0D).func_181666_a(color.getRed() / 255.0F, color.getGreen() / 255.0F, color
/*  79 */         .getBlue() / 255.0F, color.getAlpha() / 255.0F).func_181675_d();
/*  80 */     bufferbuilder.func_181662_b(r.x, r.y, 0.0D).func_181666_a(color.getRed() / 255.0F, color.getGreen() / 255.0F, color
/*  81 */         .getBlue() / 255.0F, color.getAlpha() / 255.0F).func_181675_d();
/*  82 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public static void drawRect(int mode, double left, double top, double right, double bottom, int color) {
/*  86 */     if (left < right) {
/*  87 */       double i = left;
/*  88 */       left = right;
/*  89 */       right = i;
/*     */     } 
/*     */     
/*  92 */     if (top < bottom) {
/*  93 */       double j = top;
/*  94 */       top = bottom;
/*  95 */       bottom = j;
/*     */     } 
/*     */     
/*  98 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*  99 */     float f = (color >> 16 & 0xFF) / 255.0F;
/* 100 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/* 101 */     float f2 = (color & 0xFF) / 255.0F;
/* 102 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 103 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 104 */     GlStateManager.func_179147_l();
/* 105 */     GlStateManager.func_179090_x();
/* 106 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*     */ 
/*     */     
/* 109 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/* 110 */     bufferbuilder.func_181668_a(mode, DefaultVertexFormats.field_181705_e);
/* 111 */     bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181675_d();
/* 112 */     bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181675_d();
/* 113 */     bufferbuilder.func_181662_b(right, top, 0.0D).func_181675_d();
/* 114 */     bufferbuilder.func_181662_b(left, top, 0.0D).func_181675_d();
/* 115 */     tessellator.func_78381_a();
/* 116 */     GlStateManager.func_179098_w();
/* 117 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   public static void drawRect(double left, double top, double right, double bottom, int color) {
/* 121 */     drawRect(7, left, top, right, bottom, color);
/*     */   }
/*     */   
/*     */   public static void drawRectWH(double x, double y, double width, double height, int color) {
/* 125 */     drawRect(x, y, x + width, y + height, color);
/*     */   }
/*     */   
/*     */   public static void horizontalGradient(double x1, double y1, double x2, double y2, int startColor, int endColor) {
/* 129 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/* 130 */     float f1 = (startColor >> 16 & 0xFF) / 255.0F;
/* 131 */     float f2 = (startColor >> 8 & 0xFF) / 255.0F;
/* 132 */     float f3 = (startColor & 0xFF) / 255.0F;
/* 133 */     float f4 = (endColor >> 24 & 0xFF) / 255.0F;
/* 134 */     float f5 = (endColor >> 16 & 0xFF) / 255.0F;
/* 135 */     float f6 = (endColor >> 8 & 0xFF) / 255.0F;
/* 136 */     float f7 = (endColor & 0xFF) / 255.0F;
/* 137 */     GlStateManager.func_179090_x();
/* 138 */     GlStateManager.func_179147_l();
/* 139 */     GlStateManager.func_179118_c();
/* 140 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*     */ 
/*     */     
/* 143 */     GlStateManager.func_179103_j(7425);
/* 144 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 145 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 146 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/* 147 */     bufferbuilder.func_181662_b(x1, y1, 0.0D).func_181666_a(f1, f2, f3, f).func_181675_d();
/* 148 */     bufferbuilder.func_181662_b(x1, y2, 0.0D).func_181666_a(f1, f2, f3, f).func_181675_d();
/* 149 */     bufferbuilder.func_181662_b(x2, y2, 0.0D).func_181666_a(f5, f6, f7, f4).func_181675_d();
/* 150 */     bufferbuilder.func_181662_b(x2, y1, 0.0D).func_181666_a(f5, f6, f7, f4).func_181675_d();
/* 151 */     tessellator.func_78381_a();
/* 152 */     GlStateManager.func_179103_j(7424);
/* 153 */     GlStateManager.func_179084_k();
/* 154 */     GlStateManager.func_179141_d();
/* 155 */     GlStateManager.func_179098_w();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void verticalGradient(double left, double top, double right, double bottom, int startColor, int endColor) {
/* 160 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/* 161 */     float f1 = (startColor >> 16 & 0xFF) / 255.0F;
/* 162 */     float f2 = (startColor >> 8 & 0xFF) / 255.0F;
/* 163 */     float f3 = (startColor & 0xFF) / 255.0F;
/* 164 */     float f4 = (endColor >> 24 & 0xFF) / 255.0F;
/* 165 */     float f5 = (endColor >> 16 & 0xFF) / 255.0F;
/* 166 */     float f6 = (endColor >> 8 & 0xFF) / 255.0F;
/* 167 */     float f7 = (endColor & 0xFF) / 255.0F;
/* 168 */     GlStateManager.func_179090_x();
/* 169 */     GlStateManager.func_179147_l();
/* 170 */     GlStateManager.func_179118_c();
/* 171 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*     */ 
/*     */     
/* 174 */     GlStateManager.func_179103_j(7425);
/* 175 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 176 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 177 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/* 178 */     bufferbuilder.func_181662_b(right, top, 0.0D).func_181666_a(f1, f2, f3, f).func_181675_d();
/* 179 */     bufferbuilder.func_181662_b(left, top, 0.0D).func_181666_a(f1, f2, f3, f).func_181675_d();
/* 180 */     bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181666_a(f5, f6, f7, f4).func_181675_d();
/* 181 */     bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181666_a(f5, f6, f7, f4).func_181675_d();
/* 182 */     tessellator.func_78381_a();
/* 183 */     GlStateManager.func_179103_j(7424);
/* 184 */     GlStateManager.func_179084_k();
/* 185 */     GlStateManager.func_179141_d();
/* 186 */     GlStateManager.func_179098_w();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isHovered(double mouseX, double mouseY, double x, double y, double width, double height) {
/* 191 */     return (mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y);
/*     */   }
/*     */   
/*     */   public static void drawBlurredShadow(float x, float y, float width, float height, int blurRadius, Color color) {
/* 195 */     GlStateManager.func_179092_a(516, 0.01F);
/*     */     
/* 197 */     width += (blurRadius * 2);
/* 198 */     height += (blurRadius * 2);
/* 199 */     x -= blurRadius;
/* 200 */     y -= blurRadius;
/*     */     
/* 202 */     float _X = x - 0.25F;
/* 203 */     float _Y = y + 0.25F;
/*     */     
/* 205 */     int identifier = (int)(width * height + width + (color.hashCode() * blurRadius) + blurRadius);
/*     */     
/* 207 */     boolean text2d = GL11.glIsEnabled(3553);
/* 208 */     boolean cface = GL11.glIsEnabled(2884);
/* 209 */     boolean atest = GL11.glIsEnabled(3008);
/* 210 */     boolean blend = GL11.glIsEnabled(3042);
/*     */     
/* 212 */     GL11.glEnable(3553);
/* 213 */     GL11.glDisable(2884);
/* 214 */     GL11.glEnable(3008);
/* 215 */     GlStateManager.func_179147_l();
/*     */     
/* 217 */     int texId = -1;
/* 218 */     if (shadowCache.containsKey(Integer.valueOf(identifier))) {
/* 219 */       texId = ((Integer)shadowCache.get(Integer.valueOf(identifier))).intValue();
/* 220 */       GlStateManager.func_179144_i(texId);
/*     */     } else {
/* 222 */       BufferedImage original = new BufferedImage((int)width, (int)height, 2);
/*     */       
/* 224 */       Graphics g = original.getGraphics();
/* 225 */       g.setColor(color);
/* 226 */       g.fillRect(blurRadius, blurRadius, (int)(width - (blurRadius * 2)), (int)(height - (blurRadius * 2)));
/* 227 */       g.dispose();
/*     */       
/* 229 */       GaussianFilter op = new GaussianFilter(blurRadius);
/*     */       
/* 231 */       BufferedImage blurred = op.filter(original, null);
/*     */       
/* 233 */       texId = TextureUtil.func_110989_a(TextureUtil.func_110996_a(), blurred, true, false);
/* 234 */       shadowCache.put(Integer.valueOf(identifier), Integer.valueOf(texId));
/*     */     } 
/* 236 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 237 */     GL11.glBegin(7);
/* 238 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 239 */     GL11.glVertex2f(_X, _Y);
/* 240 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 241 */     GL11.glVertex2f(_X, _Y + height);
/* 242 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 243 */     GL11.glVertex2f(_X + width, _Y + height);
/* 244 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 245 */     GL11.glVertex2f(_X + width, _Y);
/* 246 */     GL11.glEnd();
/* 247 */     GlStateManager.func_179117_G();
/* 248 */     if (!blend)
/* 249 */       GlStateManager.func_179084_k(); 
/* 250 */     if (!atest)
/* 251 */       GL11.glDisable(3008); 
/* 252 */     if (!cface)
/* 253 */       GL11.glEnable(2884); 
/* 254 */     if (!text2d)
/* 255 */       GL11.glDisable(3553); 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\render\Drawable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */