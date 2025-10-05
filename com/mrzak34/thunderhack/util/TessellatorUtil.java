/*     */ package com.mrzak34.thunderhack.util;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TessellatorUtil
/*     */ {
/*  18 */   private static final Minecraft mc = Minecraft.func_71410_x();
/*     */ 
/*     */   
/*     */   public static void drawBoundingBox(AxisAlignedBB bb, double width, Color color) {
/*  22 */     drawBoundingBox(bb, width, color, color.getAlpha());
/*     */   }
/*     */   
/*     */   public static void drawBoundingBox(AxisAlignedBB bb, double width, Color color, int alpha) {
/*  26 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  27 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  28 */     GlStateManager.func_187441_d((float)width);
/*  29 */     bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
/*  30 */     colorVertex(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, color, color.getAlpha(), bufferbuilder);
/*  31 */     colorVertex(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f, color, color.getAlpha(), bufferbuilder);
/*  32 */     colorVertex(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f, color, color.getAlpha(), bufferbuilder);
/*  33 */     colorVertex(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c, color, color.getAlpha(), bufferbuilder);
/*  34 */     colorVertex(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, color, color.getAlpha(), bufferbuilder);
/*  35 */     colorVertex(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c, color, alpha, bufferbuilder);
/*  36 */     colorVertex(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f, color, alpha, bufferbuilder);
/*  37 */     colorVertex(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f, color, color.getAlpha(), bufferbuilder);
/*  38 */     colorVertex(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f, color, color.getAlpha(), bufferbuilder);
/*  39 */     colorVertex(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f, color, alpha, bufferbuilder);
/*  40 */     colorVertex(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f, color, alpha, bufferbuilder);
/*  41 */     colorVertex(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f, color, alpha, bufferbuilder);
/*  42 */     colorVertex(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c, color, alpha, bufferbuilder);
/*  43 */     colorVertex(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c, color, color.getAlpha(), bufferbuilder);
/*  44 */     colorVertex(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c, color, alpha, bufferbuilder);
/*  45 */     colorVertex(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c, color, alpha, bufferbuilder);
/*  46 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawBox(AxisAlignedBB bb, Color color) {
/*  51 */     drawBox(bb, true, 1.0D, color, color.getAlpha(), 63);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawBox(AxisAlignedBB bb, boolean check, double height, Color color, int alpha, int sides) {
/*  56 */     if (check) {
/*  57 */       drawBox(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d - bb.field_72340_a, bb.field_72337_e - bb.field_72338_b, bb.field_72334_f - bb.field_72339_c, color, alpha, sides);
/*     */     } else {
/*  59 */       drawBox(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d - bb.field_72340_a, height, bb.field_72334_f - bb.field_72339_c, color, alpha, sides);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawBox(double x, double y, double z, double w, double h, double d, Color color, int alpha, int sides) {
/*  65 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  66 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  67 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/*  68 */     doVerticies(new AxisAlignedBB(x, y, z, x + w, y + h, z + d), color, alpha, bufferbuilder, sides, false);
/*  69 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawLine(double posx, double posy, double posz, double posx2, double posy2, double posz2, Color color) {
/*  74 */     drawLine(posx, posy, posz, posx2, posy2, posz2, color, 1.0F);
/*     */   }
/*     */   
/*     */   public static void drawLine(double posx, double posy, double posz, double posx2, double posy2, double posz2, Color color, float width) {
/*  78 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  79 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  80 */     GlStateManager.func_187441_d(width);
/*  81 */     bufferbuilder.func_181668_a(1, DefaultVertexFormats.field_181705_e);
/*  82 */     vertex(posx, posy, posz, bufferbuilder);
/*  83 */     vertex(posx2, posy2, posz2, bufferbuilder);
/*  84 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void vertex(double x, double y, double z, BufferBuilder bufferbuilder) {
/*  89 */     bufferbuilder.func_181662_b(x - (mc.func_175598_ae()).field_78730_l, y - (mc.func_175598_ae()).field_78731_m, z - (mc.func_175598_ae()).field_78728_n).func_181675_d();
/*     */   }
/*     */   
/*     */   private static void colorVertex(double x, double y, double z, Color color, int alpha, BufferBuilder bufferbuilder) {
/*  93 */     bufferbuilder.func_181662_b(x - (mc.func_175598_ae()).field_78730_l, y - (mc.func_175598_ae()).field_78731_m, z - (mc.func_175598_ae()).field_78728_n).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), alpha).func_181675_d();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void doVerticies(AxisAlignedBB axisAlignedBB, Color color, int alpha, BufferBuilder bufferbuilder, int sides, boolean five) {
/*  98 */     if ((sides & 0x20) != 0) {
/*  99 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f, color, color.getAlpha(), bufferbuilder);
/* 100 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, color, color.getAlpha(), bufferbuilder);
/* 101 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c, color, alpha, bufferbuilder);
/* 102 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f, color, alpha, bufferbuilder);
/* 103 */       if (five)
/* 104 */         colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f, color, color.getAlpha(), bufferbuilder); 
/*     */     } 
/* 106 */     if ((sides & 0x10) != 0) {
/* 107 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, color, color.getAlpha(), bufferbuilder);
/* 108 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f, color, color.getAlpha(), bufferbuilder);
/* 109 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f, color, alpha, bufferbuilder);
/* 110 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c, color, alpha, bufferbuilder);
/* 111 */       if (five)
/* 112 */         colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, color, color.getAlpha(), bufferbuilder); 
/*     */     } 
/* 114 */     if ((sides & 0x4) != 0) {
/* 115 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, color, color.getAlpha(), bufferbuilder);
/* 116 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, color, color.getAlpha(), bufferbuilder);
/* 117 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c, color, alpha, bufferbuilder);
/* 118 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c, color, alpha, bufferbuilder);
/* 119 */       if (five)
/* 120 */         colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, color, color.getAlpha(), bufferbuilder); 
/*     */     } 
/* 122 */     if ((sides & 0x8) != 0) {
/* 123 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f, color, color.getAlpha(), bufferbuilder);
/* 124 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f, color, color.getAlpha(), bufferbuilder);
/* 125 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f, color, alpha, bufferbuilder);
/* 126 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f, color, alpha, bufferbuilder);
/* 127 */       if (five)
/* 128 */         colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f, color, color.getAlpha(), bufferbuilder); 
/*     */     } 
/* 130 */     if ((sides & 0x2) != 0) {
/* 131 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c, color, alpha, bufferbuilder);
/* 132 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c, color, alpha, bufferbuilder);
/* 133 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f, color, alpha, bufferbuilder);
/* 134 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f, color, alpha, bufferbuilder);
/* 135 */       if (five)
/* 136 */         colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c, color, alpha, bufferbuilder); 
/*     */     } 
/* 138 */     if ((sides & 0x1) != 0) {
/* 139 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, color, color.getAlpha(), bufferbuilder);
/* 140 */       colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f, color, color.getAlpha(), bufferbuilder);
/* 141 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f, color, color.getAlpha(), bufferbuilder);
/* 142 */       colorVertex(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, color, color.getAlpha(), bufferbuilder);
/* 143 */       if (five)
/* 144 */         colorVertex(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, color, color.getAlpha(), bufferbuilder); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void prepare() {
/* 149 */     GL11.glPushAttrib(1048575);
/* 150 */     GL11.glHint(3154, 4354);
/* 151 */     GlStateManager.func_179120_a(770, 771, 0, 1);
/* 152 */     GlStateManager.func_179103_j(7425);
/* 153 */     GlStateManager.func_179132_a(false);
/* 154 */     GlStateManager.func_179147_l();
/* 155 */     GlStateManager.func_179097_i();
/* 156 */     GlStateManager.func_179090_x();
/* 157 */     GlStateManager.func_179140_f();
/* 158 */     GlStateManager.func_179129_p();
/* 159 */     GlStateManager.func_179141_d();
/* 160 */     GL11.glEnable(2848);
/* 161 */     GL11.glEnable(34383);
/*     */   }
/*     */   
/*     */   public static void release() {
/* 165 */     GL11.glDisable(34383);
/* 166 */     GL11.glDisable(2848);
/* 167 */     GlStateManager.func_179141_d();
/* 168 */     GlStateManager.func_179089_o();
/* 169 */     GlStateManager.func_179145_e();
/* 170 */     GlStateManager.func_179098_w();
/* 171 */     GlStateManager.func_179126_j();
/* 172 */     GlStateManager.func_179084_k();
/* 173 */     GlStateManager.func_179132_a(true);
/* 174 */     GlStateManager.func_187441_d(1.0F);
/* 175 */     GlStateManager.func_179103_j(7424);
/* 176 */     GL11.glHint(3154, 4352);
/* 177 */     GL11.glPopAttrib();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\TessellatorUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */