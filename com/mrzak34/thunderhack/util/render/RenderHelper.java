/*     */ package com.mrzak34.thunderhack.util.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.RadarRewrite;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.render.ItemESP;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.gaussianblur.GaussianFilter;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderHelper
/*     */ {
/*  31 */   public static Frustum frustum = new Frustum();
/*     */   
/*  33 */   private static final HashMap<Integer, Integer> shadowCache = new HashMap<>();
/*     */ 
/*     */   
/*     */   public static void drawBlurredShadow(float x, float y, float width, float height, int blurRadius, Color color) {
/*  37 */     BufferedImage original = null;
/*  38 */     GaussianFilter op = null;
/*  39 */     GL11.glPushMatrix();
/*  40 */     GlStateManager.func_179092_a(516, 0.01F);
/*  41 */     float _X = (x -= blurRadius) - 0.25F;
/*  42 */     float _Y = (y -= blurRadius) + 0.25F;
/*  43 */     int identifier = String.valueOf((width += (blurRadius * 2)) * (height += (blurRadius * 2)) + width + (1000000000 * blurRadius) + blurRadius).hashCode();
/*  44 */     GL11.glEnable(3553);
/*  45 */     GL11.glDisable(2884);
/*  46 */     GL11.glEnable(3008);
/*  47 */     GlStateManager.func_179147_l();
/*  48 */     int texId = -1;
/*  49 */     if (shadowCache.containsKey(Integer.valueOf(identifier))) {
/*  50 */       texId = ((Integer)shadowCache.get(Integer.valueOf(identifier))).intValue();
/*  51 */       GlStateManager.func_179144_i(texId);
/*     */     } else {
/*  53 */       if (width <= 0.0F) {
/*  54 */         width = 1.0F;
/*     */       }
/*  56 */       if (height <= 0.0F) {
/*  57 */         height = 1.0F;
/*     */       }
/*  59 */       if (original == null) {
/*  60 */         original = new BufferedImage((int)width, (int)height, 3);
/*     */       }
/*  62 */       Graphics g = original.getGraphics();
/*  63 */       g.setColor(Color.white);
/*  64 */       g.fillRect(blurRadius, blurRadius, (int)(width - (blurRadius * 2)), (int)(height - (blurRadius * 2)));
/*  65 */       g.dispose();
/*  66 */       if (op == null) {
/*  67 */         op = new GaussianFilter(blurRadius);
/*     */       }
/*  69 */       BufferedImage blurred = op.filter(original, null);
/*  70 */       texId = TextureUtil.func_110989_a(TextureUtil.func_110996_a(), blurred, true, false);
/*  71 */       shadowCache.put(Integer.valueOf(identifier), Integer.valueOf(texId));
/*     */     } 
/*  73 */     GlStateManager.func_179131_c(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*  74 */     GL11.glBegin(7);
/*  75 */     GL11.glTexCoord2f(0.0F, 0.0F);
/*  76 */     GL11.glVertex2f(_X, _Y);
/*  77 */     GL11.glTexCoord2f(0.0F, 1.0F);
/*  78 */     GL11.glVertex2f(_X, _Y + height);
/*  79 */     GL11.glTexCoord2f(1.0F, 1.0F);
/*  80 */     GL11.glVertex2f(_X + width, _Y + height);
/*  81 */     GL11.glTexCoord2f(1.0F, 0.0F);
/*  82 */     GL11.glVertex2f(_X + width, _Y);
/*  83 */     GL11.glEnd();
/*  84 */     GlStateManager.func_179098_w();
/*  85 */     GlStateManager.func_179084_k();
/*  86 */     GlStateManager.func_179117_G();
/*  87 */     GL11.glEnable(2884);
/*  88 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static boolean isInViewFrustum(Entity entity) {
/*  92 */     return (isInViewFrustum(entity.func_174813_aQ()) || entity.field_70158_ak);
/*     */   }
/*     */   
/*     */   private static boolean isInViewFrustum(AxisAlignedBB bb) {
/*  96 */     Entity current = Util.mc.func_175606_aa();
/*  97 */     if (current != null) {
/*  98 */       frustum.func_78547_a(current.field_70165_t, current.field_70163_u, current.field_70161_v);
/*     */     }
/* 100 */     return frustum.func_78546_a(bb);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setColor(int color) {
/* 105 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */   }
/*     */   
/*     */   public static void setColor(Color color, float alpha) {
/* 109 */     float red = color.getRed() / 255.0F;
/* 110 */     float green = color.getGreen() / 255.0F;
/* 111 */     float blue = color.getBlue() / 255.0F;
/* 112 */     GlStateManager.func_179131_c(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public static void drawCircle3D(Entity entity, double radius, float partialTicks, int points, float width, int color, boolean astolfo) {
/* 116 */     GL11.glPushMatrix();
/* 117 */     GL11.glDisable(3553);
/* 118 */     GL11.glEnable(2848);
/* 119 */     GL11.glHint(3154, 4354);
/* 120 */     GL11.glDisable(2929);
/* 121 */     GL11.glLineWidth(width);
/* 122 */     GL11.glEnable(3042);
/* 123 */     GL11.glBlendFunc(770, 771);
/* 124 */     GL11.glDisable(2929);
/* 125 */     GL11.glBegin(3);
/* 126 */     double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * partialTicks - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX();
/* 127 */     double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * partialTicks - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY();
/* 128 */     double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * partialTicks - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ();
/* 129 */     if (!astolfo) {
/* 130 */       setColor(color);
/* 131 */       for (int i = 0; i <= points; i++) {
/* 132 */         GL11.glVertex3d(x + radius * Math.cos(i * 6.28D / points), y, z + radius * Math.sin(i * 6.28D / points));
/*     */       }
/*     */     } else {
/* 135 */       for (int i = 0; i <= points; i++) {
/* 136 */         setColor(ItemESP.astolfo2.getColor(i));
/* 137 */         GL11.glVertex3d(x + radius * Math.cos(i * 6.28D / points), y, z + radius * Math.sin(i * 6.28D / points));
/*     */       } 
/*     */     } 
/* 140 */     GL11.glEnd();
/* 141 */     GL11.glDepthMask(true);
/* 142 */     GL11.glDisable(3042);
/* 143 */     GL11.glEnable(2929);
/* 144 */     GL11.glDisable(2848);
/* 145 */     GL11.glEnable(2929);
/* 146 */     GL11.glEnable(3553);
/* 147 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawEntityBox(Entity entity, Color color, Color color2, boolean fullBox, float alpha) {
/* 152 */     GlStateManager.func_179094_E();
/* 153 */     GlStateManager.func_179112_b(770, 771);
/* 154 */     GL11.glEnable(3042);
/* 155 */     GlStateManager.func_187441_d(2.0F);
/* 156 */     GlStateManager.func_179090_x();
/* 157 */     GL11.glDisable(2929);
/* 158 */     GlStateManager.func_179132_a(false);
/* 159 */     double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * Util.mc.func_184121_ak() - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX();
/* 160 */     double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * Util.mc.func_184121_ak() - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY();
/* 161 */     double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * Util.mc.func_184121_ak() - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ();
/* 162 */     AxisAlignedBB axisAlignedBB = entity.func_174813_aQ();
/* 163 */     AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB.field_72340_a - entity.field_70165_t + x - 0.05D, axisAlignedBB.field_72338_b - entity.field_70163_u + y, axisAlignedBB.field_72339_c - entity.field_70161_v + z - 0.05D, axisAlignedBB.field_72336_d - entity.field_70165_t + x + 0.05D, axisAlignedBB.field_72337_e - entity.field_70163_u + y + 0.15D, axisAlignedBB.field_72334_f - entity.field_70161_v + z + 0.05D);
/* 164 */     GlStateManager.func_187441_d(2.0F);
/* 165 */     GL11.glEnable(2848);
/* 166 */     GlStateManager.func_179131_c(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha);
/* 167 */     if (fullBox) {
/* 168 */       drawColorBox(axisAlignedBB2, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha);
/* 169 */       GlStateManager.func_179131_c(color2.getRed() / 255.0F, color2.getGreen() / 255.0F, color2.getBlue() / 255.0F, alpha);
/*     */     } 
/* 171 */     drawSelectionBoundingBox(axisAlignedBB2);
/* 172 */     GlStateManager.func_187441_d(2.0F);
/* 173 */     GlStateManager.func_179098_w();
/* 174 */     GL11.glEnable(2929);
/* 175 */     GlStateManager.func_179132_a(true);
/* 176 */     GlStateManager.func_179084_k();
/* 177 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
/* 182 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 183 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/* 185 */     builder.func_181668_a(3, DefaultVertexFormats.field_181705_e);
/* 186 */     builder.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
/* 187 */     builder.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
/* 188 */     builder.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
/* 189 */     builder.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
/* 190 */     builder.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
/* 191 */     tessellator.func_78381_a();
/* 192 */     builder.func_181668_a(3, DefaultVertexFormats.field_181705_e);
/* 193 */     builder.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
/* 194 */     builder.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
/* 195 */     builder.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
/* 196 */     builder.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
/* 197 */     builder.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
/* 198 */     tessellator.func_78381_a();
/* 199 */     builder.func_181668_a(1, DefaultVertexFormats.field_181705_e);
/* 200 */     builder.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
/* 201 */     builder.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
/* 202 */     builder.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
/* 203 */     builder.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
/* 204 */     builder.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
/* 205 */     builder.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
/* 206 */     builder.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
/* 207 */     builder.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
/* 208 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawCircle(float x, float y, float start, float end, float radius, boolean filled, Color color) {
/* 217 */     GlStateManager.func_179131_c(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */     
/* 220 */     if (start > end) {
/* 221 */       float endOffset = end;
/* 222 */       end = start;
/* 223 */       start = endOffset;
/*     */     } 
/*     */     
/* 226 */     GlStateManager.func_179147_l();
/* 227 */     GlStateManager.func_179090_x();
/* 228 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/*     */     
/* 230 */     if (color != null) {
/* 231 */       setColor(color.getRGB());
/*     */     }
/*     */     
/* 234 */     GL11.glEnable(2848);
/* 235 */     GL11.glLineWidth(2.0F);
/* 236 */     if (filled) {
/* 237 */       GL11.glBegin(6);
/*     */     } else {
/* 239 */       GL11.glBegin(3);
/*     */     }  float i;
/* 241 */     for (i = end; i >= start; i -= 5.0F) {
/*     */       
/* 243 */       if (color == null) {
/* 244 */         double stage = (i + 90.0F) / 360.0D;
/* 245 */         int clr = RadarRewrite.astolfo.getColor(stage);
/* 246 */         int red = clr >> 16 & 0xFF;
/* 247 */         int green = clr >> 8 & 0xFF;
/* 248 */         int blue = clr & 0xFF;
/*     */         
/* 250 */         GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, 1.0F);
/*     */       } 
/* 252 */       float cos = (float)Math.cos(i * Math.PI / 180.0D) * radius;
/* 253 */       float sin = (float)Math.sin(i * Math.PI / 180.0D) * radius;
/* 254 */       GL11.glVertex2f(x + cos, y + sin);
/*     */     } 
/*     */ 
/*     */     
/* 258 */     GL11.glEnd();
/* 259 */     GL11.glDisable(2848);
/* 260 */     GlStateManager.func_179098_w();
/* 261 */     GlStateManager.func_179084_k();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawElipse(float x, float y, float rx, float ry, float start, float end, float radius, Color color, int stage1, RadarRewrite.mode2 cmode) {
/* 270 */     GlStateManager.func_179131_c(0.0F, 0.0F, 0.0F, 0.0F);
/*     */     
/* 272 */     if (start > end) {
/* 273 */       float endOffset = end;
/* 274 */       end = start;
/* 275 */       start = endOffset;
/*     */     } 
/* 277 */     GlStateManager.func_179147_l();
/* 278 */     GlStateManager.func_179090_x();
/* 279 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/* 280 */     GL11.glEnable(2848);
/* 281 */     GL11.glLineWidth(2.0F);
/* 282 */     GL11.glBegin(3); float i;
/* 283 */     for (i = start; i <= end; i += 2.0F) {
/*     */       
/* 285 */       double stage = ((i - start) / 360.0F);
/* 286 */       Color clr = null;
/*     */       
/* 288 */       if (cmode == RadarRewrite.mode2.Astolfo) {
/* 289 */         clr = new Color(RadarRewrite.astolfo.getColor(stage));
/* 290 */       } else if (cmode == RadarRewrite.mode2.Rainbow) {
/* 291 */         clr = color;
/* 292 */       } else if (cmode == RadarRewrite.mode2.Custom) {
/* 293 */         clr = color;
/*     */       } else {
/* 295 */         clr = RenderUtil.TwoColoreffect(color, ((ColorSetting)((RadarRewrite)Thunderhack.moduleManager.getModuleByClass(RadarRewrite.class)).cColor2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0D + (i * (20.0F - ((Integer)((RadarRewrite)Thunderhack.moduleManager.getModuleByClass(RadarRewrite.class)).colorOffset1.getValue()).intValue()) / 200.0F));
/*     */       } 
/*     */       
/* 298 */       int clr2 = clr.getRGB();
/* 299 */       int red = clr2 >> 16 & 0xFF;
/* 300 */       int green = clr2 >> 8 & 0xFF;
/* 301 */       int blue = clr2 & 0xFF;
/*     */       
/* 303 */       GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, 1.0F);
/*     */ 
/*     */ 
/*     */       
/* 307 */       float cos = (float)Math.cos(i * Math.PI / 180.0D) * radius / ry;
/* 308 */       float sin = (float)Math.sin(i * Math.PI / 180.0D) * radius / rx;
/* 309 */       GL11.glVertex2f(x + cos, y + sin);
/*     */     } 
/* 311 */     GL11.glEnd();
/* 312 */     GL11.glDisable(2848);
/* 313 */     GlStateManager.func_179098_w();
/* 314 */     GlStateManager.func_179084_k();
/* 315 */     if (stage1 != -1) {
/* 316 */       float cos = (float)Math.cos((start - 15.0F) * Math.PI / 180.0D) * radius / ry;
/* 317 */       float sin = (float)Math.sin((start - 15.0F) * Math.PI / 180.0D) * radius / rx;
/*     */       
/* 319 */       switch (stage1) {
/*     */         case 0:
/* 321 */           FontRender.drawCentString3("W", x + cos, y + sin, -1);
/*     */           break;
/*     */         
/*     */         case 1:
/* 325 */           FontRender.drawCentString3("N", x + cos, y + sin, -1);
/*     */           break;
/*     */         
/*     */         case 2:
/* 329 */           FontRender.drawCentString3("E", x + cos, y + sin, -1);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/* 334 */           FontRender.drawCentString3("S", x + cos, y + sin, -1);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawCircle(float x, float y, float radius, boolean filled, Color color) {
/* 343 */     drawCircle(x, y, 0.0F, 360.0F, radius, filled, color);
/*     */   }
/*     */   
/*     */   public static void drawEllipsCompas(int yaw, float x, float y, float x2, float y2, float radius, Color color, boolean Dir, RadarRewrite.mode2 mode) {
/* 347 */     if (Dir) {
/* 348 */       drawElipse(x, y, x2, y2, (15 + yaw), (75 + yaw), radius, color, 0, mode);
/* 349 */       drawElipse(x, y, x2, y2, (105 + yaw), (165 + yaw), radius, color, 1, mode);
/* 350 */       drawElipse(x, y, x2, y2, (195 + yaw), (255 + yaw), radius, color, 2, mode);
/* 351 */       drawElipse(x, y, x2, y2, (285 + yaw), (345 + yaw), radius, color, 3, mode);
/*     */     } else {
/* 353 */       drawElipse(x, y, x2, y2, (15 + yaw), (75 + yaw), radius, color, -1, mode);
/* 354 */       drawElipse(x, y, x2, y2, (105 + yaw), (165 + yaw), radius, color, -1, mode);
/* 355 */       drawElipse(x, y, x2, y2, (195 + yaw), (255 + yaw), radius, color, -1, mode);
/* 356 */       drawElipse(x, y, x2, y2, (285 + yaw), (345 + yaw), radius, color, -1, mode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
/* 363 */     Tessellator ts = Tessellator.func_178181_a();
/* 364 */     BufferBuilder buffer = ts.func_178180_c();
/* 365 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*     */     
/* 367 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 368 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 369 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 370 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 371 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 372 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 373 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 374 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 375 */     ts.func_78381_a();
/* 376 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 377 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 378 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 379 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 380 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 381 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 382 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 383 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 384 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 385 */     ts.func_78381_a();
/* 386 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 387 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 388 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 389 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 390 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 391 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 392 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 393 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 394 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 395 */     ts.func_78381_a();
/* 396 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 397 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 398 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 399 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 400 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 401 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 402 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 403 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 404 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 405 */     ts.func_78381_a();
/* 406 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 407 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 408 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 409 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 410 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 411 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 412 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 413 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 414 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 415 */     ts.func_78381_a();
/* 416 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 417 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 418 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 419 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 420 */     buffer.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 421 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 422 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 423 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 424 */     buffer.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 425 */     ts.func_78381_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\render\RenderHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */