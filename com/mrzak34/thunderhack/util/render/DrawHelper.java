/*     */ package com.mrzak34.thunderhack.util.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import java.awt.Color;
/*     */ import java.text.NumberFormat;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class DrawHelper
/*     */ {
/*  20 */   public static Frustum frustum = new Frustum();
/*  21 */   private static final Minecraft mc = Minecraft.func_71410_x();
/*     */   
/*     */   public static void drawEntityBox(Entity entity, Color color, boolean fullBox, float alpha) {
/*  24 */     GlStateManager.func_179094_E();
/*  25 */     GlStateManager.func_179112_b(770, 771);
/*  26 */     GL11.glEnable(3042);
/*  27 */     GlStateManager.func_187441_d(2.0F);
/*  28 */     GlStateManager.func_179090_x();
/*  29 */     GL11.glDisable(2929);
/*  30 */     GlStateManager.func_179132_a(false);
/*  31 */     double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * mc.func_184121_ak() - ((IRenderManager)mc.func_175598_ae()).getRenderPosX();
/*  32 */     double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * mc.func_184121_ak() - ((IRenderManager)mc.func_175598_ae()).getRenderPosY();
/*  33 */     double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * mc.func_184121_ak() - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ();
/*  34 */     AxisAlignedBB axisAlignedBB = entity.func_174813_aQ();
/*  35 */     AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB.field_72340_a - entity.field_70165_t + x - 0.05D, axisAlignedBB.field_72338_b - entity.field_70163_u + y, axisAlignedBB.field_72339_c - entity.field_70161_v + z - 0.05D, axisAlignedBB.field_72336_d - entity.field_70165_t + x + 0.05D, axisAlignedBB.field_72337_e - entity.field_70163_u + y + 0.15D, axisAlignedBB.field_72334_f - entity.field_70161_v + z + 0.05D);
/*  36 */     GlStateManager.func_187441_d(2.0F);
/*  37 */     GL11.glEnable(2848);
/*  38 */     GlStateManager.func_179131_c(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha);
/*  39 */     if (fullBox) {
/*  40 */       drawColorBox(axisAlignedBB2, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha);
/*  41 */       GlStateManager.func_179131_c(0.0F, 0.0F, 0.0F, 0.5F);
/*     */     } 
/*  43 */     drawSelectionBoundingBox(axisAlignedBB2);
/*  44 */     GlStateManager.func_187441_d(2.0F);
/*  45 */     GlStateManager.func_179098_w();
/*  46 */     GL11.glEnable(2929);
/*  47 */     GlStateManager.func_179132_a(true);
/*  48 */     GlStateManager.func_179084_k();
/*  49 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static Color injectAlpha(Color color, int alpha) {
/*  53 */     int alph = MathHelper.func_76125_a(alpha, 0, 255);
/*  54 */     return new Color(color.getRed(), color.getGreen(), color.getBlue(), alph);
/*     */   }
/*     */   
/*     */   public static boolean isInViewFrustum(Entity entity) {
/*  58 */     return (isInViewFrustum(entity.func_174813_aQ()) || entity.field_70158_ak);
/*     */   }
/*     */   
/*     */   private static boolean isInViewFrustum(AxisAlignedBB bb) {
/*  62 */     Entity current = mc.func_175606_aa();
/*  63 */     if (current != null) {
/*  64 */       frustum.func_78547_a(current.field_70165_t, current.field_70163_u, current.field_70161_v);
/*     */     }
/*  66 */     return frustum.func_78546_a(bb);
/*     */   }
/*     */   
/*     */   public static Color astolfo(boolean clickgui, int yOffset) {
/*  70 */     float speed = clickgui ? 100.0F : 1000.0F;
/*  71 */     float hue = (float)(System.currentTimeMillis() % (int)speed + yOffset);
/*  72 */     if (hue > speed) {
/*  73 */       hue -= speed;
/*     */     }
/*  75 */     hue /= speed;
/*  76 */     if (hue > 0.5F) {
/*  77 */       hue = 0.5F - hue - 0.5F;
/*     */     }
/*  79 */     hue += 0.5F;
/*  80 */     return Color.getHSBColor(hue, 0.4F, 1.0F);
/*     */   }
/*     */   
/*     */   public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
/*  84 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  85 */     BufferBuilder vertexbuffer = tessellator.func_178180_c();
/*  86 */     vertexbuffer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
/*  87 */     vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
/*  88 */     vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
/*  89 */     vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
/*  90 */     vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
/*  91 */     vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
/*  92 */     tessellator.func_78381_a();
/*  93 */     vertexbuffer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
/*  94 */     vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
/*  95 */     vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
/*  96 */     vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
/*  97 */     vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
/*  98 */     vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
/*  99 */     tessellator.func_78381_a();
/* 100 */     vertexbuffer.func_181668_a(1, DefaultVertexFormats.field_181705_e);
/* 101 */     vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
/* 102 */     vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
/* 103 */     vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
/* 104 */     vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
/* 105 */     vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
/* 106 */     vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
/* 107 */     vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
/* 108 */     vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
/* 109 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
/* 113 */     Tessellator ts = Tessellator.func_178181_a();
/* 114 */     BufferBuilder vb = ts.func_178180_c();
/* 115 */     vb.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 116 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 117 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 118 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 119 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 120 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 121 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 122 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 123 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 124 */     ts.func_78381_a();
/* 125 */     vb.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 126 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 127 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 128 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 129 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 130 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 131 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 132 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 133 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 134 */     ts.func_78381_a();
/* 135 */     vb.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 136 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 137 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 138 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 139 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 140 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 141 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 142 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 143 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 144 */     ts.func_78381_a();
/* 145 */     vb.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 146 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 147 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 148 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 149 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 150 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 151 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 152 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 153 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 154 */     ts.func_78381_a();
/* 155 */     vb.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 156 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 157 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 158 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 159 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 160 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 161 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 162 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 163 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 164 */     ts.func_78381_a();
/* 165 */     vb.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 166 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 167 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 168 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 169 */     vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 170 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 171 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 172 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 173 */     vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 174 */     ts.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableStandardItemLighting() {
/* 179 */     GlStateManager.func_179140_f();
/* 180 */     GlStateManager.func_179122_b(0);
/* 181 */     GlStateManager.func_179122_b(1);
/* 182 */     GlStateManager.func_179119_h();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setColor(int color) {
/* 187 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */   }
/*     */   public static void drawRectWithGlow(double X, double Y, double Width, double Height, double GlowRange, double GlowMultiplier, Color color) {
/*     */     float i;
/* 191 */     for (i = 1.0F; i < GlowRange; i += 0.5F) {
/* 192 */       drawRoundedRect99(X - GlowRange - i, Y - GlowRange - i, Width + GlowRange - i, Height + GlowRange - i, injectAlpha(color, (int)Math.round(i * GlowMultiplier)).getRGB());
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawRoundedRect99(double x, double y, double x1, double y1, int insideC) {
/* 197 */     RenderUtil.drawRect((float)(x + 0.5D), (float)y, (float)x1 - 0.5F, (float)y + 0.5F, insideC);
/* 198 */     RenderUtil.drawRect((float)(x + 0.5D), (float)y1 - 0.5F, (float)x1 - 0.5F, (float)y1, insideC);
/* 199 */     RenderUtil.drawRect((float)x, (float)y + 0.5F, (float)x1, (float)y1 - 0.5F, insideC);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void color(double red, double green, double blue, double alpha) {
/* 204 */     GL11.glColor4d(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawSmoothRect(double left, double top, double right, double bottom, int color) {
/* 209 */     GL11.glEnable(3042);
/* 210 */     GL11.glEnable(2848);
/* 211 */     drawRect(left, top, right, bottom, color);
/* 212 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 213 */     drawRect(left * 2.0D - 1.0D, top * 2.0D, left * 2.0D, bottom * 2.0D - 1.0D, color);
/* 214 */     drawRect(left * 2.0D, top * 2.0D - 1.0D, right * 2.0D, top * 2.0D, color);
/* 215 */     drawRect(right * 2.0D, top * 2.0D, right * 2.0D + 1.0D, bottom * 2.0D - 1.0D, color);
/* 216 */     drawRect(left * 2.0D, bottom * 2.0D - 1.0D, right * 2.0D, bottom * 2.0D, color);
/* 217 */     GL11.glDisable(3042);
/* 218 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawNewRect(double left, double top, double right, double bottom, int color) {
/* 223 */     if (left < right) {
/* 224 */       double i = left;
/* 225 */       left = right;
/* 226 */       right = i;
/*     */     } 
/* 228 */     if (top < bottom) {
/* 229 */       double j = top;
/* 230 */       top = bottom;
/* 231 */       bottom = j;
/*     */     } 
/* 233 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/* 234 */     float f = (color >> 16 & 0xFF) / 255.0F;
/* 235 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/* 236 */     float f2 = (color & 0xFF) / 255.0F;
/* 237 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 238 */     BufferBuilder vertexbuffer = tessellator.func_178180_c();
/* 239 */     GlStateManager.func_179147_l();
/* 240 */     GlStateManager.func_179090_x();
/* 241 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 242 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/* 243 */     vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 244 */     vertexbuffer.func_181662_b(left, bottom, 0.0D).func_181675_d();
/* 245 */     vertexbuffer.func_181662_b(right, bottom, 0.0D).func_181675_d();
/* 246 */     vertexbuffer.func_181662_b(right, top, 0.0D).func_181675_d();
/* 247 */     vertexbuffer.func_181662_b(left, top, 0.0D).func_181675_d();
/* 248 */     tessellator.func_78381_a();
/* 249 */     GlStateManager.func_179098_w();
/* 250 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   public static void drawRect(double left, double top, double right, double bottom, int color) {
/* 254 */     if (left < right) {
/* 255 */       double i = left;
/* 256 */       left = right;
/* 257 */       right = i;
/*     */     } 
/*     */     
/* 260 */     if (top < bottom) {
/* 261 */       double j = top;
/* 262 */       top = bottom;
/* 263 */       bottom = j;
/*     */     } 
/*     */     
/* 266 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/* 267 */     float f = (color >> 16 & 0xFF) / 255.0F;
/* 268 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/* 269 */     float f2 = (color & 0xFF) / 255.0F;
/* 270 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 271 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 272 */     GlStateManager.func_179147_l();
/* 273 */     GlStateManager.func_179090_x();
/* 274 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 275 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/* 276 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 277 */     bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181675_d();
/* 278 */     bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181675_d();
/* 279 */     bufferbuilder.func_181662_b(right, top, 0.0D).func_181675_d();
/* 280 */     bufferbuilder.func_181662_b(left, top, 0.0D).func_181675_d();
/* 281 */     tessellator.func_78381_a();
/* 282 */     GlStateManager.func_179098_w();
/* 283 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   public static int color(int n, int n2, int n3, int n4) {
/* 287 */     n4 = 255;
/* 288 */     return (new Color(n, n2, n3, n4)).getRGB();
/*     */   }
/*     */   
/*     */   public static Color rainbow(int delay, float saturation, float brightness) {
/* 292 */     double rainbow = Math.ceil(((System.currentTimeMillis() + delay) / 16L));
/* 293 */     rainbow %= 360.0D;
/* 294 */     return Color.getHSBColor((float)(rainbow / 360.0D), saturation, brightness);
/*     */   }
/*     */   
/*     */   public static int getHealthColor(float health, float maxHealth) {
/* 298 */     return Color.HSBtoRGB(Math.max(0.0F, Math.min(health, maxHealth) / maxHealth) / 3.0F, 1.0F, 0.8F) | 0xFF000000;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getColor(int red, int green, int blue) {
/* 303 */     return getColor(red, green, blue, 255);
/*     */   }
/*     */   
/*     */   public static int getColor(int red, int green, int blue, int alpha) {
/* 307 */     int color = 0;
/* 308 */     color |= alpha << 24;
/* 309 */     color |= red << 16;
/* 310 */     color |= green << 8;
/* 311 */     return color |= blue;
/*     */   }
/*     */   
/*     */   public static int getColor(Color color) {
/* 315 */     return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/*     */   }
/*     */   
/*     */   public static int getColor(int bright) {
/* 319 */     return getColor(bright, bright, bright, 255);
/*     */   }
/*     */   
/*     */   public static int getColor(int brightness, int alpha) {
/* 323 */     return getColor(brightness, brightness, brightness, alpha);
/*     */   }
/*     */   
/*     */   public static Color fade(Color color, int index, int count) {
/* 327 */     float[] hsb = new float[3];
/* 328 */     Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
/* 329 */     float brightness = Math.abs((
/* 330 */         (float)(System.currentTimeMillis() % 2000L) / 1000.0F + index / count * 2.0F) % 2.0F - 1.0F);
/*     */     
/* 332 */     brightness = 0.5F + 0.5F * brightness;
/* 333 */     hsb[2] = brightness % 2.0F;
/* 334 */     return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
/*     */   }
/*     */   
/*     */   public static int fade(int startColor, int endColor, float progress) {
/* 338 */     float invert = 1.0F - progress;
/* 339 */     int r = (int)((startColor >> 16 & 0xFF) * invert + (endColor >> 16 & 0xFF) * progress);
/* 340 */     int g = (int)((startColor >> 8 & 0xFF) * invert + (endColor >> 8 & 0xFF) * progress);
/* 341 */     int b = (int)((startColor & 0xFF) * invert + (endColor & 0xFF) * progress);
/* 342 */     int a = (int)((startColor >> 24 & 0xFF) * invert + (endColor >> 24 & 0xFF) * progress);
/* 343 */     return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*     */   }
/*     */   
/*     */   public static Color blend(Color color1, Color color2, double ratio) {
/* 347 */     float r = (float)ratio;
/* 348 */     float ir = 1.0F - r;
/* 349 */     float[] rgb1 = new float[3];
/* 350 */     float[] rgb2 = new float[3];
/* 351 */     color1.getColorComponents(rgb1);
/* 352 */     color2.getColorComponents(rgb2);
/* 353 */     float red = rgb1[0] * r + rgb2[0] * ir;
/* 354 */     float green = rgb1[1] * r + rgb2[1] * ir;
/* 355 */     float blue = rgb1[2] * r + rgb2[2] * ir;
/* 356 */     if (red < 0.0F) {
/* 357 */       red = 0.0F;
/* 358 */     } else if (red > 255.0F) {
/* 359 */       red = 255.0F;
/*     */     } 
/* 361 */     if (green < 0.0F) {
/* 362 */       green = 0.0F;
/* 363 */     } else if (green > 255.0F) {
/* 364 */       green = 255.0F;
/*     */     } 
/* 366 */     if (blue < 0.0F) {
/* 367 */       blue = 0.0F;
/* 368 */     } else if (blue > 255.0F) {
/* 369 */       blue = 255.0F;
/*     */     } 
/* 371 */     Color color = null;
/*     */     try {
/* 373 */       color = new Color(red, green, blue);
/* 374 */     } catch (IllegalArgumentException exp) {
/* 375 */       NumberFormat numberFormat = NumberFormat.getNumberInstance();
/*     */     } 
/* 377 */     return color;
/*     */   }
/*     */   
/*     */   public static int astolfo(int yOffset, int yTotal) {
/* 381 */     float speed = 1000.0F;
/* 382 */     float hue = (float)(System.currentTimeMillis() % (int)speed + (yOffset - yTotal) * 9L);
/* 383 */     while (hue > speed) {
/* 384 */       hue -= speed;
/*     */     }
/* 386 */     hue /= speed;
/* 387 */     if (hue > 0.5D) {
/* 388 */       hue = 0.5F - hue - 0.5F;
/*     */     }
/* 390 */     hue += 0.5F;
/* 391 */     return Color.HSBtoRGB(hue, 0.6F, 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\render\DrawHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */