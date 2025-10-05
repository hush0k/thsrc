/*      */ package com.mrzak34.thunderhack.util.render;
/*      */ 
/*      */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*      */ import com.mrzak34.thunderhack.modules.combat.BackTrack;
/*      */ import com.mrzak34.thunderhack.util.EntityUtil;
/*      */ import com.mrzak34.thunderhack.util.Util;
/*      */ import com.mrzak34.thunderhack.util.gaussianblur.GaussianFilter;
/*      */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*      */ import java.awt.Color;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.HashMap;
/*      */ import java.util.Objects;
/*      */ import javax.vecmath.Vector3d;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.model.ModelBase;
/*      */ import net.minecraft.client.model.ModelPlayer;
/*      */ import net.minecraft.client.renderer.BufferBuilder;
/*      */ import net.minecraft.client.renderer.GLAllocation;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.RenderGlobal;
/*      */ import net.minecraft.client.renderer.RenderItem;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.texture.TextureUtil;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ 
/*      */ public class RenderUtil implements Util {
/*      */   public static RenderItem itemRender;
/*   49 */   public static ICamera camera = (ICamera)new Frustum();
/*   50 */   public static Tessellator tessellator = Tessellator.func_178181_a();
/*   51 */   public static BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*   52 */   private static final HashMap<Integer, Integer> shadowCache = new HashMap<>();
/*      */   public static long delta;
/*      */   
/*   55 */   static { itemRender = mc.func_175599_af();
/*   56 */     camera = (ICamera)new Frustum();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1048 */     delta = 0L; }
/*      */   public static double interpolate(double current, double old, double scale) { return old + (current - old) * scale; }
/*      */   public static double interpolate(double current, double old) { return old + (current - old) * mc.func_184121_ak(); }
/* 1051 */   public static void bindTexture(int texture) { GL11.glBindTexture(3553, texture); } public static void renderEntity(BackTrack.Box entity, ModelBase modelBase, float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch, EntityLivingBase entityIn, Color color1) { boolean texture = GL11.glIsEnabled(3553); boolean blend = GL11.glIsEnabled(3042); boolean hz = GL11.glIsEnabled(2848); GlStateManager.func_179147_l(); GlStateManager.func_179120_a(770, 771, 0, 1); GlStateManager.func_179090_x(); GL11.glEnable(2848); GL11.glHint(3154, 4354); GlStateManager.func_179131_c(color1.getRed() / 255.0F, color1.getGreen() / 255.0F, color1.getBlue() / 255.0F, color1.getAlpha() / 255.0F); GL11.glPolygonMode(1032, 6914); if (modelBase instanceof ModelPlayer) { ModelPlayer modelPlayer = (ModelPlayer)modelBase; modelPlayer.field_178730_v.field_78806_j = false; modelPlayer.field_178733_c.field_78806_j = false; modelPlayer.field_178731_d.field_78806_j = false; modelPlayer.field_178734_a.field_78806_j = false; modelPlayer.field_178732_b.field_78806_j = false; modelPlayer.field_178720_f.field_78806_j = true; modelPlayer.field_78116_c.field_78806_j = false; }  float partialTicks = mc.func_184121_ak(); double x = (entity.getPosition()).field_72450_a - (mc.func_175598_ae()).field_78730_l; double y = (entity.getPosition()).field_72448_b - (mc.func_175598_ae()).field_78731_m; double z = (entity.getPosition()).field_72449_c - (mc.func_175598_ae()).field_78728_n; GlStateManager.func_179094_E(); GlStateManager.func_179109_b((float)x, (float)y, (float)z); GlStateManager.func_179114_b(180.0F - entity.getYaw(), 0.0F, 1.0F, 0.0F); float f4 = prepareScale(1.0F); float yaw = entity.getYaw(); boolean alpha = GL11.glIsEnabled(3008); GlStateManager.func_179141_d(); modelBase.func_78086_a(entityIn, limbSwing, limbSwingAmount, partialTicks); modelBase.func_78087_a(limbSwing, limbSwingAmount, 0.0F, yaw, entity.getPitch(), f4, (Entity)entityIn); modelBase.func_78088_a((Entity)entityIn, limbSwing, limbSwingAmount, 0.0F, yaw, entity.getPitch(), f4); if (!alpha) GlStateManager.func_179118_c();  GlStateManager.func_179121_F(); GlStateManager.func_179098_w(); if (!hz) GL11.glDisable(2848);  if (texture) GlStateManager.func_179098_w();  if (!blend) GlStateManager.func_179084_k();  } private static float prepareScale(float scale) { GlStateManager.func_179091_B(); GlStateManager.func_179152_a(-1.0F, -1.0F, 1.0F); double widthX = 0.6000000238418579D; double widthZ = 0.6000000238418579D; GlStateManager.func_179139_a(scale + widthX, (scale * 1.8F), scale + widthZ); float f = 0.0625F; GlStateManager.func_179109_b(0.0F, -1.501F, 0.0F); return f; } public static void drawBoundingBox(BackTrack.Box box, double width, Color color) { Tessellator tessellator = Tessellator.func_178181_a(); BufferBuilder bufferbuilder = tessellator.func_178180_c(); GlStateManager.func_187441_d((float)width); bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f); colorVertex((box.getPosition()).field_72450_a - 0.3D, (box.getPosition()).field_72448_b, (box.getPosition()).field_72449_c - 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a - 0.3D, (box.getPosition()).field_72448_b, (box.getPosition()).field_72449_c + 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a + 0.3D, (box.getPosition()).field_72448_b, (box.getPosition()).field_72449_c + 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a + 0.3D, (box.getPosition()).field_72448_b, (box.getPosition()).field_72449_c - 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a - 0.3D, (box.getPosition()).field_72448_b, (box.getPosition()).field_72449_c - 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a - 0.3D, (box.getPosition()).field_72448_b + 1.7999999523162842D, (box.getPosition()).field_72449_c - 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a - 0.3D, (box.getPosition()).field_72448_b + 1.7999999523162842D, (box.getPosition()).field_72449_c + 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a - 0.3D, (box.getPosition()).field_72448_b, (box.getPosition()).field_72449_c + 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a + 0.3D, (box.getPosition()).field_72448_b, (box.getPosition()).field_72449_c + 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a + 0.3D, (box.getPosition()).field_72448_b + 1.7999999523162842D, (box.getPosition()).field_72449_c + 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a - 0.3D, (box.getPosition()).field_72448_b + 1.7999999523162842D, (box.getPosition()).field_72449_c + 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a + 0.3D, (box.getPosition()).field_72448_b + 1.7999999523162842D, (box.getPosition()).field_72449_c + 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a + 0.3D, (box.getPosition()).field_72448_b + 1.7999999523162842D, (box.getPosition()).field_72449_c - 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a + 0.3D, (box.getPosition()).field_72448_b, (box.getPosition()).field_72449_c - 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a + 0.3D, (box.getPosition()).field_72448_b + 1.7999999523162842D, (box.getPosition()).field_72449_c - 0.3D, color, color.getAlpha(), bufferbuilder); colorVertex((box.getPosition()).field_72450_a - 0.3D, (box.getPosition()).field_72448_b + 1.7999999523162842D, (box.getPosition()).field_72449_c - 0.3D, color, color.getAlpha(), bufferbuilder); tessellator.func_78381_a(); } private static void colorVertex(double x, double y, double z, Color color, int alpha, BufferBuilder bufferbuilder) { bufferbuilder.func_181662_b(x - (mc.func_175598_ae()).field_78730_l, y - (mc.func_175598_ae()).field_78731_m, z - (mc.func_175598_ae()).field_78728_n).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), alpha).func_181675_d(); } public static Vector3d vectorTo2D(int scaleFactor, double x, double y, double z) { float xPos = (float)x; float yPos = (float)y; float zPos = (float)z; IntBuffer viewport = GLAllocation.func_74527_f(16); FloatBuffer modelview = GLAllocation.func_74529_h(16); FloatBuffer projection = GLAllocation.func_74529_h(16); FloatBuffer vector = GLAllocation.func_74529_h(4); GL11.glGetFloat(2982, modelview); GL11.glGetFloat(2983, projection); GL11.glGetInteger(2978, viewport); if (GLU.gluProject(xPos, yPos, zPos, modelview, projection, viewport, vector)) return new Vector3d((vector.get(0) / scaleFactor), ((Display.getHeight() - vector.get(1)) / scaleFactor), vector.get(2));  return null; } public static float scrollAnimate(float endPoint, float current, float speed) { boolean shouldContinueAnimation = (endPoint > current); if (speed < 0.0F) { speed = 0.0F; } else if (speed > 1.0F) { speed = 1.0F; }  float dif = Math.max(endPoint, current) - Math.min(endPoint, current); float factor = dif * speed; return current + (shouldContinueAnimation ? factor : -factor); } public static void glBillboard(float x, float y, float z) { float scale = 0.02666667F; GlStateManager.func_179137_b(x - ((IRenderManager)mc.func_175598_ae()).getRenderPosX(), y - ((IRenderManager)mc.func_175598_ae()).getRenderPosY(), z - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ()); GlStateManager.func_187432_a(0.0F, 1.0F, 0.0F); GlStateManager.func_179114_b(-(Minecraft.func_71410_x()).field_71439_g.field_70177_z, 0.0F, 1.0F, 0.0F); GlStateManager.func_179114_b((Minecraft.func_71410_x()).field_71439_g.field_70125_A, ((Minecraft.func_71410_x()).field_71474_y.field_74320_O == 2) ? -1.0F : 1.0F, 0.0F, 0.0F); GlStateManager.func_179152_a(-scale, -scale, scale); } public static void glBillboardDistanceScaled(float x, float y, float z, EntityPlayer player, float scale) { glBillboard(x, y, z); int distance = (int)player.func_70011_f(x, y, z); float scaleDistance = distance / 2.0F / (2.0F + 2.0F - scale); if (scaleDistance < 1.0F) scaleDistance = 1.0F;  GlStateManager.func_179152_a(scaleDistance, scaleDistance, scaleDistance); } public static void scale() { switch (mc.field_71474_y.field_74335_Z) { case 0: GlStateManager.func_179139_a(0.5D, 0.5D, 0.5D); break;case 1: GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F); break;case 3: GlStateManager.func_179139_a(0.6666666666666667D, 0.6666666666666667D, 0.6666666666666667D); break; }  } public static float lerp(float a, float b, float f) { return a + f * (b - a); } public static Color TwoColoreffect(Color cl1, Color cl2, double speed) { double thing = speed / 4.0D % 1.0D; float val = MathHelper.func_76131_a((float)Math.sin(18.84955592153876D * thing) / 2.0F + 0.5F, 0.0F, 1.0F); return new Color(lerp(cl1.getRed() / 255.0F, cl2.getRed() / 255.0F, val), lerp(cl1.getGreen() / 255.0F, cl2.getGreen() / 255.0F, val), lerp(cl1.getBlue() / 255.0F, cl2.getBlue() / 255.0F, val)); } public static void drawFilledCircleNoGL(int x, int y, double r, int c, int quality) { float f = (c >> 24 & 0xFF) / 255.0F; float f1 = (c >> 16 & 0xFF) / 255.0F; float f2 = (c >> 8 & 0xFF) / 255.0F; float f3 = (c & 0xFF) / 255.0F; GL11.glColor4f(f1, f2, f3, f); GL11.glBegin(6); for (int i = 0; i <= 360 / quality; i++) { double x2 = Math.sin((i * quality) * Math.PI / 180.0D) * r; double y2 = Math.cos((i * quality) * Math.PI / 180.0D) * r; GL11.glVertex2d(x + x2, y + y2); }  GL11.glEnd(); } public static void glColor(int hex) { float alpha = (hex >> 24 & 0xFF) / 255.0F; float red = (hex >> 16 & 0xFF) / 255.0F; float green = (hex >> 8 & 0xFF) / 255.0F; float blue = (hex & 0xFF) / 255.0F; GL11.glColor4f(red, green, blue, alpha); } public static void glColor(Color color) { float red = color.getRed() / 255.0F; float green = color.getGreen() / 255.0F; float blue = color.getBlue() / 255.0F; float alpha = color.getAlpha() / 255.0F; GlStateManager.func_179131_c(red, green, blue, alpha); } public static void drawRect(float x, float y, float w, float h, int color) { float alpha = (color >> 24 & 0xFF) / 255.0F; float red = (color >> 16 & 0xFF) / 255.0F; float green = (color >> 8 & 0xFF) / 255.0F; float blue = (color & 0xFF) / 255.0F; Tessellator tessellator = Tessellator.func_178181_a(); BufferBuilder bufferBuilder = tessellator.func_178180_c(); GlStateManager.func_179147_l(); GlStateManager.func_179090_x(); GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO); GlStateManager.func_179131_c(red, green, blue, alpha); bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e); bufferBuilder.func_181662_b(x, h, 0.0D).func_181675_d(); bufferBuilder.func_181662_b(w, h, 0.0D).func_181675_d(); bufferBuilder.func_181662_b(w, y, 0.0D).func_181675_d(); bufferBuilder.func_181662_b(x, y, 0.0D).func_181675_d(); tessellator.func_78381_a(); GlStateManager.func_179098_w(); GlStateManager.func_179084_k(); } public static void drawOutlineRect(float x, float y, float w, float h, float lineWidth, int color) { float right = x + w; float bottom = y + h; float alpha = (color >> 24 & 0xFF) / 255.0F; float red = (color >> 16 & 0xFF) / 255.0F; float green = (color >> 8 & 0xFF) / 255.0F; float blue = (color & 0xFF) / 255.0F; Tessellator tessellator = Tessellator.func_178181_a(); BufferBuilder bufferBuilder = tessellator.func_178180_c(); GlStateManager.func_179147_l(); GlStateManager.func_179090_x(); GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO); GlStateManager.func_179131_c(red, green, blue, alpha); GL11.glEnable(2848); GlStateManager.func_187441_d(lineWidth); bufferBuilder.func_181668_a(1, DefaultVertexFormats.field_181705_e); bufferBuilder.func_181662_b(x, bottom, 0.0D).func_181675_d(); bufferBuilder.func_181662_b(right, bottom, 0.0D).func_181675_d(); bufferBuilder.func_181662_b(right, bottom, 0.0D).func_181675_d(); bufferBuilder.func_181662_b(right, y, 0.0D).func_181675_d(); bufferBuilder.func_181662_b(right, y, 0.0D).func_181675_d(); bufferBuilder.func_181662_b(x, y, 0.0D).func_181675_d(); bufferBuilder.func_181662_b(x, y, 0.0D).func_181675_d(); bufferBuilder.func_181662_b(x, bottom, 0.0D).func_181675_d(); tessellator.func_78381_a(); GlStateManager.func_179098_w(); GlStateManager.func_179084_k(); } public static void draw2DGradientRect(float left, float top, float right, float bottom, int leftBottomColor, int leftTopColor, int rightBottomColor, int rightTopColor) { float lba = (leftBottomColor >> 24 & 0xFF) / 255.0F; float lbr = (leftBottomColor >> 16 & 0xFF) / 255.0F; float lbg = (leftBottomColor >> 8 & 0xFF) / 255.0F; float lbb = (leftBottomColor & 0xFF) / 255.0F; float rba = (rightBottomColor >> 24 & 0xFF) / 255.0F; float rbr = (rightBottomColor >> 16 & 0xFF) / 255.0F; float rbg = (rightBottomColor >> 8 & 0xFF) / 255.0F; float rbb = (rightBottomColor & 0xFF) / 255.0F; float lta = (leftTopColor >> 24 & 0xFF) / 255.0F; float ltr = (leftTopColor >> 16 & 0xFF) / 255.0F; float ltg = (leftTopColor >> 8 & 0xFF) / 255.0F; float ltb = (leftTopColor & 0xFF) / 255.0F; float rta = (rightTopColor >> 24 & 0xFF) / 255.0F; float rtr = (rightTopColor >> 16 & 0xFF) / 255.0F; float rtg = (rightTopColor >> 8 & 0xFF) / 255.0F; float rtb = (rightTopColor & 0xFF) / 255.0F; GlStateManager.func_179090_x(); GlStateManager.func_179147_l(); GlStateManager.func_179118_c(); GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO); GlStateManager.func_179103_j(7425); Tessellator tessellator = Tessellator.func_178181_a(); BufferBuilder bufferbuilder = tessellator.func_178180_c(); bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f); bufferbuilder.func_181662_b(right, top, 0.0D).func_181666_a(rtr, rtg, rtb, rta).func_181675_d(); bufferbuilder.func_181662_b(left, top, 0.0D).func_181666_a(ltr, ltg, ltb, lta).func_181675_d(); bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181666_a(lbr, lbg, lbb, lba).func_181675_d(); bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181666_a(rbr, rbg, rbb, rba).func_181675_d(); tessellator.func_78381_a(); GlStateManager.func_179103_j(7424); GlStateManager.func_179084_k(); GlStateManager.func_179141_d(); GlStateManager.func_179098_w(); } public static void draw1DGradientRect(float left, float top, float right, float bottom, int leftColor, int rightColor) { float la = (leftColor >> 24 & 0xFF) / 255.0F; float lr = (leftColor >> 16 & 0xFF) / 255.0F; float lg = (leftColor >> 8 & 0xFF) / 255.0F; float lb = (leftColor & 0xFF) / 255.0F; float ra = (rightColor >> 24 & 0xFF) / 255.0F; float rr = (rightColor >> 16 & 0xFF) / 255.0F; float rg = (rightColor >> 8 & 0xFF) / 255.0F; float rb = (rightColor & 0xFF) / 255.0F; GlStateManager.func_179090_x(); GlStateManager.func_179147_l(); GlStateManager.func_179118_c(); GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO); GlStateManager.func_179103_j(7425); Tessellator tessellator = Tessellator.func_178181_a(); BufferBuilder bufferbuilder = tessellator.func_178180_c(); bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f); bufferbuilder.func_181662_b(right, top, 0.0D).func_181666_a(rr, rg, rb, ra).func_181675_d(); bufferbuilder.func_181662_b(left, top, 0.0D).func_181666_a(lr, lg, lb, la).func_181675_d(); bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181666_a(lr, lg, lb, la).func_181675_d(); bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181666_a(rr, rg, rb, ra).func_181675_d(); tessellator.func_78381_a(); GlStateManager.func_179103_j(7424); GlStateManager.func_179084_k(); GlStateManager.func_179141_d(); GlStateManager.func_179098_w(); } public static void beginRender() { GL11.glBlendFunc(770, 771); GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO); GlStateManager.func_179147_l(); GlStateManager.func_179140_f(); GlStateManager.func_179129_p(); } public static void endRender() { GlStateManager.func_179089_o(); GlStateManager.func_179145_e(); GlStateManager.func_179084_k(); } public static void colorflux(int color) { float f = (color >> 24 & 0xFF) / 255.0F; float f1 = (color >> 16 & 0xFF) / 255.0F; float f2 = (color >> 8 & 0xFF) / 255.0F; float f3 = (color & 0xFF) / 255.0F; GL11.glColor4f(f1, f2, f3, f); } public static void renderCrosses(BlockPos pos, Color color, float lineWidth) { AxisAlignedBB bb = new AxisAlignedBB(pos.func_177958_n() - (Util.mc.func_175598_ae()).field_78730_l, pos.func_177956_o() - (Util.mc.func_175598_ae()).field_78731_m, pos.func_177952_p() - (Util.mc.func_175598_ae()).field_78728_n, (pos.func_177958_n() + 1) - (Util.mc.func_175598_ae()).field_78730_l, (pos.func_177956_o() + 1) - (Util.mc.func_175598_ae()).field_78731_m, (pos.func_177952_p() + 1) - (Util.mc.func_175598_ae()).field_78728_n); camera.func_78547_a(((Entity)Objects.requireNonNull((T)Util.mc.func_175606_aa())).field_70165_t, (Util.mc.func_175606_aa()).field_70163_u, (Util.mc.func_175606_aa()).field_70161_v); if (camera.func_78546_a(new AxisAlignedBB(pos))) { GlStateManager.func_179094_E(); GlStateManager.func_179147_l(); GlStateManager.func_179097_i(); GlStateManager.func_179120_a(770, 771, 0, 1); GlStateManager.func_179090_x(); GlStateManager.func_179132_a(false); GL11.glEnable(2848); GL11.glHint(3154, 4354); GL11.glLineWidth(lineWidth); renderCrosses(bb, color); GL11.glDisable(2848); GlStateManager.func_179132_a(true); GlStateManager.func_179126_j(); GlStateManager.func_179098_w(); GlStateManager.func_179084_k(); GlStateManager.func_179121_F(); }  } public static void renderCrosses(AxisAlignedBB bb, Color color) { int hex = color.getRGB(); float red = (hex >> 16 & 0xFF) / 255.0F; float green = (hex >> 8 & 0xFF) / 255.0F; float blue = (hex & 0xFF) / 255.0F; Tessellator tessellator = Tessellator.func_178181_a(); BufferBuilder bufferbuilder = tessellator.func_178180_c(); bufferbuilder.func_181668_a(1, DefaultVertexFormats.field_181706_f); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, 1.0F).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, 1.0F).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, 1.0F).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, 1.0F).func_181675_d(); tessellator.func_78381_a(); } public static void drawRect(double left, double top, double right, double bottom, int color) { if (left < right) {
/* 1052 */       double i = left;
/* 1053 */       left = right;
/* 1054 */       right = i;
/*      */     } 
/*      */     
/* 1057 */     if (top < bottom) {
/* 1058 */       double j = top;
/* 1059 */       top = bottom;
/* 1060 */       bottom = j;
/*      */     } 
/*      */     
/* 1063 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/* 1064 */     float f = (color >> 16 & 0xFF) / 255.0F;
/* 1065 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/* 1066 */     float f2 = (color & 0xFF) / 255.0F;
/* 1067 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 1068 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 1069 */     GlStateManager.func_179147_l();
/* 1070 */     GlStateManager.func_179090_x();
/* 1071 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1072 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/* 1073 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 1074 */     bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181675_d();
/* 1075 */     bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181675_d();
/* 1076 */     bufferbuilder.func_181662_b(right, top, 0.0D).func_181675_d();
/* 1077 */     bufferbuilder.func_181662_b(left, top, 0.0D).func_181675_d();
/* 1078 */     tessellator.func_78381_a();
/* 1079 */     GlStateManager.func_179098_w();
/* 1080 */     GlStateManager.func_179084_k(); }
/*      */   public static void blockEspFrame(BlockPos blockPos, double red, double green, double blue) { double d = blockPos.func_177958_n(); Minecraft.func_71410_x().func_175598_ae(); double x = d - ((IRenderManager)mc.func_175598_ae()).getRenderPosX(); double d2 = blockPos.func_177956_o(); Minecraft.func_71410_x().func_175598_ae(); double y = d2 - ((IRenderManager)mc.func_175598_ae()).getRenderPosY(); double d3 = blockPos.func_177952_p(); Minecraft.func_71410_x().func_175598_ae(); double z = d3 - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ(); GL11.glBlendFunc(770, 771); GL11.glEnable(3042); GL11.glLineWidth(1.0F); GL11.glDisable(3553); GL11.glDisable(2929); GL11.glDepthMask(false); GL11.glColor4d(red, green, blue, 0.5D); drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D)); GL11.glEnable(3553); GL11.glEnable(2929); GL11.glDepthMask(true); GL11.glDisable(3042); }
/*      */   public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) { drawRect((float)(x + width), (float)(y + width), (float)(x1 - width), (float)(y1 - width), internalColor); drawRect((float)(x + width), (float)y, (float)(x1 - width), (float)(y + width), borderColor); drawRect((float)x, (float)y, (float)(x + width), (float)y1, borderColor); drawRect((float)(x1 - width), (float)y, (float)x1, (float)y1, borderColor); drawRect((float)(x + width), (float)(y1 - width), (float)(x1 - width), (float)y1, borderColor); }
/*      */   public static void rotationHelper(float xAngle, float yAngle, float zAngle) { GlStateManager.func_179114_b(yAngle, 0.0F, 1.0F, 0.0F); GlStateManager.func_179114_b(zAngle, 0.0F, 0.0F, 1.0F); GlStateManager.func_179114_b(xAngle, 1.0F, 0.0F, 0.0F); }
/* 1084 */   public static AxisAlignedBB interpolateAxis(AxisAlignedBB bb) { return new AxisAlignedBB(bb.field_72340_a - (mc.func_175598_ae()).field_78730_l, bb.field_72338_b - (mc.func_175598_ae()).field_78731_m, bb.field_72339_c - (mc.func_175598_ae()).field_78728_n, bb.field_72336_d - (mc.func_175598_ae()).field_78730_l, bb.field_72337_e - (mc.func_175598_ae()).field_78731_m, bb.field_72334_f - (mc.func_175598_ae()).field_78728_n); } public static void drawTexturedRect(int x, int y, int textureX, int textureY, int width, int height, int zLevel) { Tessellator tessellator = Tessellator.func_178181_a(); BufferBuilder BufferBuilder2 = tessellator.func_178180_c(); BufferBuilder2.func_181668_a(7, DefaultVertexFormats.field_181707_g); BufferBuilder2.func_181662_b(x, (y + height), zLevel).func_187315_a((textureX * 0.00390625F), ((textureY + height) * 0.00390625F)).func_181675_d(); BufferBuilder2.func_181662_b((x + width), (y + height), zLevel).func_187315_a(((textureX + width) * 0.00390625F), ((textureY + height) * 0.00390625F)).func_181675_d(); BufferBuilder2.func_181662_b((x + width), y, zLevel).func_187315_a(((textureX + width) * 0.00390625F), (textureY * 0.00390625F)).func_181675_d(); BufferBuilder2.func_181662_b(x, y, zLevel).func_187315_a((textureX * 0.00390625F), (textureY * 0.00390625F)).func_181675_d(); tessellator.func_78381_a(); } public static void drawBoxESP(BlockPos pos, Color color, boolean secondC, Color secondColor, float lineWidth, boolean outline, boolean box, int boxAlpha, boolean air, int mode) { if (box) drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha), mode);  if (outline) drawBlockOutline(pos, secondC ? secondColor : color, lineWidth, air, mode);  } public static void glScissor(float x, float y, float x1, float y1, ScaledResolution sr) { GL11.glScissor((int)(x * sr.func_78325_e()), (int)(mc.field_71440_d - y1 * sr.func_78325_e()), (int)((x1 - x) * sr.func_78325_e()), (int)((y1 - y) * sr.func_78325_e())); } public static void glScissor(float x, float y, float x1, float y1, ScaledResolution sr, double animation_factor) { float h = y + y1; float h2 = (float)(h * (1.0D - MathUtil.clamp(animation_factor, 0.0D, 1.002500057220459D))); float x3 = x; float y3 = y + h2; float x4 = x1; float y4 = y1 - h2; if (x4 < x3) x4 = x3;  if (y4 < y3) y4 = y3;  glScissor(x3, y3, x4, y4, sr); } public static Color blend(Color color1, Color color2, double ratio) { float r = (float)ratio; float ir = 1.0F - r; float[] rgb1 = new float[3]; float[] rgb2 = new float[3]; color1.getColorComponents(rgb1); color2.getColorComponents(rgb2); float red = rgb1[0] * r + rgb2[0] * ir; float green = rgb1[1] * r + rgb2[1] * ir; float blue = rgb1[2] * r + rgb2[2] * ir; if (red < 0.0F) { red = 0.0F; } else if (red > 255.0F) { red = 255.0F; }  if (green < 0.0F) { green = 0.0F; } else if (green > 255.0F) { green = 255.0F; }  if (blue < 0.0F) { blue = 0.0F; } else if (blue > 255.0F) { blue = 255.0F; }  Color color = null; try { color = new Color(red, green, blue); } catch (IllegalArgumentException exp) { NumberFormat numberFormat = NumberFormat.getNumberInstance(); }  return color; } public static void drawSmoothRect(float left, float top, float right, float bottom, int color) { GL11.glEnable(3042); GL11.glEnable(2848); drawRect(left, top, right, bottom, color); GL11.glScalef(0.5F, 0.5F, 0.5F); drawRect(left * 2.0F - 1.0F, top * 2.0F, left * 2.0F, bottom * 2.0F - 1.0F, color); drawRect(left * 2.0F, top * 2.0F - 1.0F, right * 2.0F, top * 2.0F, color); drawRect(right * 2.0F, top * 2.0F, right * 2.0F + 1.0F, bottom * 2.0F - 1.0F, color); drawRect(left * 2.0F, bottom * 2.0F - 1.0F, right * 2.0F, bottom * 2.0F, color); GL11.glDisable(3042); GL11.glScalef(2.0F, 2.0F, 2.0F); } public static void drawBox(BlockPos pos, Color color, int mode) { IBlockState iblockstate = mc.field_71441_e.func_180495_p(pos); Vec3d interp = EntityUtil.interpolateEntity(mc.func_175606_aa(), mc.func_184121_ak()); AxisAlignedBB bb = null; switch (mode) { case 0: bb = iblockstate.func_185918_c((World)mc.field_71441_e, pos).func_186662_g(0.0020000000949949026D).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c); break;case 1: bb = iblockstate.func_185918_c((World)mc.field_71441_e, pos).func_186662_g(0.0020000000949949026D).func_72321_a(0.9D, 0.0D, 0.0D).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c); break;case 2: bb = iblockstate.func_185918_c((World)mc.field_71441_e, pos).func_186662_g(0.0020000000949949026D).func_72321_a(-0.9D, 0.0D, 0.0D).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c); break;case 3: bb = iblockstate.func_185918_c((World)mc.field_71441_e, pos).func_186662_g(0.0020000000949949026D).func_72321_a(0.0D, 0.0D, 0.9D).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c); break;case 4: bb = iblockstate.func_185918_c((World)mc.field_71441_e, pos).func_186662_g(0.0020000000949949026D).func_72321_a(0.0D, 0.0D, -0.9D).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c); break; }  camera.func_78547_a(((Entity)Objects.requireNonNull((T)mc.func_175606_aa())).field_70165_t, (mc.func_175606_aa()).field_70163_u, (mc.func_175606_aa()).field_70161_v); if (camera.func_78546_a(new AxisAlignedBB(bb.field_72340_a + (mc.func_175598_ae()).field_78730_l, bb.field_72338_b + (mc.func_175598_ae()).field_78731_m, bb.field_72339_c + (mc.func_175598_ae()).field_78728_n, bb.field_72336_d + (mc.func_175598_ae()).field_78730_l, bb.field_72337_e + (mc.func_175598_ae()).field_78731_m, bb.field_72334_f + (mc.func_175598_ae()).field_78728_n))) { GlStateManager.func_179094_E(); boolean texture = GL11.glIsEnabled(3553); boolean blend = GL11.glIsEnabled(3042); boolean hz = GL11.glIsEnabled(2848); GlStateManager.func_179147_l(); GlStateManager.func_179120_a(770, 771, 0, 1); GlStateManager.func_179090_x(); GL11.glEnable(2848); GL11.glHint(3154, 4354); RenderGlobal.func_189696_b(bb, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / ((mode == 0) ? 255.0F : 510.0F)); if (!hz) GL11.glDisable(2848);  if (texture) GlStateManager.func_179098_w();  if (!blend) GlStateManager.func_179084_k();  GlStateManager.func_179121_F(); }  } public static void drawBlockOutline(BlockPos pos, Color color, float linewidth, boolean air, int mode) { IBlockState iblockstate = mc.field_71441_e.func_180495_p(pos); if ((air || iblockstate.func_185904_a() != Material.field_151579_a) && mc.field_71441_e.func_175723_af().func_177746_a(pos)) { assert mc.func_175606_aa() != null; Vec3d interp = EntityUtil.interpolateEntity(mc.func_175606_aa(), mc.func_184121_ak()); drawBlockOutline(iblockstate.func_185918_c((World)mc.field_71441_e, pos).func_186662_g(0.0020000000949949026D).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c), color, linewidth); }  } public static void drawBlockOutline(AxisAlignedBB bb, Color color, float linewidth) { float red = color.getRed() / 255.0F; float green = color.getGreen() / 255.0F; float blue = color.getBlue() / 255.0F; float alpha = color.getAlpha() / 255.0F; GlStateManager.func_179094_E(); boolean texture = GL11.glIsEnabled(3553); boolean blend = GL11.glIsEnabled(3042); boolean DEPTH = GL11.glIsEnabled(2929); boolean HZ = GL11.glIsEnabled(2848); GlStateManager.func_179147_l(); GlStateManager.func_179097_i(); GlStateManager.func_179120_a(770, 771, 0, 1); GlStateManager.func_179090_x(); GlStateManager.func_179132_a(false); GL11.glEnable(2848); GL11.glHint(3154, 4354); GL11.glLineWidth(linewidth); Tessellator tessellator = Tessellator.func_178181_a(); BufferBuilder bufferbuilder = tessellator.func_178180_c(); bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); tessellator.func_78381_a(); if (!HZ) GL11.glDisable(2848);  if (DEPTH) { GlStateManager.func_179132_a(true); GlStateManager.func_179126_j(); }  if (texture) GlStateManager.func_179098_w();  if (blend) GlStateManager.func_179084_k();  GlStateManager.func_179121_F(); } public static void blockEsp(BlockPos blockPos, Color c, double length, double length2) { double x = blockPos.func_177958_n() - ((IRenderManager)mc.func_175598_ae()).getRenderPosX(); double y = blockPos.func_177956_o() - ((IRenderManager)mc.func_175598_ae()).getRenderPosY(); double z = blockPos.func_177952_p() - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ(); GL11.glPushMatrix(); GL11.glBlendFunc(770, 771); GL11.glEnable(3042); GL11.glLineWidth(2.0F); GL11.glDisable(3553); GL11.glDisable(2929); GL11.glDepthMask(false); GL11.glColor4d((c.getRed() / 255.0F), (c.getGreen() / 255.0F), (c.getBlue() / 255.0F), 0.25D); drawColorBox(new AxisAlignedBB(x, y, z, x + length2, y + 1.0D, z + length), 0.0F, 0.0F, 0.0F, 0.0F); GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.5D); drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + length2, y + 1.0D, z + length)); GL11.glLineWidth(2.0F); GL11.glEnable(3553); GL11.glEnable(2929); GL11.glDepthMask(true); GL11.glDisable(3042); GL11.glPopMatrix(); GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); } public static void drawRect2(double left, double top, double right, double bottom, int color) { GlStateManager.func_179094_E(); if (left < right) { double i = left; left = right; right = i; }  if (top < bottom) { double j = top; top = bottom; bottom = j; }  float f3 = (color >> 24 & 0xFF) / 255.0F; float f = (color >> 16 & 0xFF) / 255.0F; float f1 = (color >> 8 & 0xFF) / 255.0F; float f2 = (color & 0xFF) / 255.0F; Tessellator tessellator = Tessellator.func_178181_a(); BufferBuilder bufferbuilder = tessellator.func_178180_c(); GlStateManager.func_179147_l(); GlStateManager.func_179090_x(); GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO); GlStateManager.func_179131_c(f, f1, f2, f3); bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e); bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181675_d(); bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181675_d(); bufferbuilder.func_181662_b(right, top, 0.0D).func_181675_d(); bufferbuilder.func_181662_b(left, top, 0.0D).func_181675_d(); tessellator.func_78381_a(); GlStateManager.func_179098_w(); GlStateManager.func_179084_k(); GlStateManager.func_179121_F(); } public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) { Tessellator ts = Tessellator.func_178181_a(); BufferBuilder vb = ts.func_178180_c(); vb.func_181668_a(7, DefaultVertexFormats.field_181707_g); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); ts.func_78381_a(); vb.func_181668_a(7, DefaultVertexFormats.field_181707_g); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); ts.func_78381_a(); vb.func_181668_a(7, DefaultVertexFormats.field_181707_g); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); ts.func_78381_a(); vb.func_181668_a(7, DefaultVertexFormats.field_181707_g); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); ts.func_78381_a(); vb.func_181668_a(7, DefaultVertexFormats.field_181707_g); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); ts.func_78381_a(); vb.func_181668_a(7, DefaultVertexFormats.field_181707_g); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72337_e, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); vb.func_181662_b(axisalignedbb.field_72336_d, axisalignedbb.field_72338_b, axisalignedbb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); ts.func_78381_a(); } public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) { Tessellator tessellator = Tessellator.func_178181_a(); BufferBuilder vertexbuffer = tessellator.func_178180_c(); vertexbuffer.func_181668_a(3, DefaultVertexFormats.field_181705_e); vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d(); tessellator.func_78381_a(); vertexbuffer.func_181668_a(3, DefaultVertexFormats.field_181705_e); vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d(); tessellator.func_78381_a(); vertexbuffer.func_181668_a(1, DefaultVertexFormats.field_181705_e); vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d(); vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d(); tessellator.func_78381_a(); } public static void glEnd() { GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); GL11.glPopMatrix(); GL11.glEnable(2929); GL11.glEnable(3553); GL11.glDisable(3042); GL11.glDisable(2848); } public static AxisAlignedBB getBoundingBox(BlockPos blockPos) { return mc.field_71441_e.func_180495_p(blockPos).func_185900_c((IBlockAccess)mc.field_71441_e, blockPos).func_186670_a(blockPos); } public static void drawFilledBox(AxisAlignedBB bb, int color) { GlStateManager.func_179094_E(); GlStateManager.func_179147_l(); GlStateManager.func_179097_i(); GlStateManager.func_179120_a(770, 771, 0, 1); GlStateManager.func_179090_x(); GlStateManager.func_179132_a(false); float alpha = (color >> 24 & 0xFF) / 255.0F; float red = (color >> 16 & 0xFF) / 255.0F; float green = (color >> 8 & 0xFF) / 255.0F; float blue = (color & 0xFF) / 255.0F; Tessellator tessellator = Tessellator.func_178181_a(); BufferBuilder bufferbuilder = tessellator.func_178180_c(); bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d(); bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d(); tessellator.func_78381_a(); GlStateManager.func_179132_a(true); GlStateManager.func_179126_j(); GlStateManager.func_179098_w(); GlStateManager.func_179084_k(); GlStateManager.func_179121_F(); } public static void setColor(Color color) { GL11.glColor4d(color.getRed() / 255.0D, color.getGreen() / 255.0D, color.getBlue() / 255.0D, color.getAlpha() / 255.0D); } public static Entity getEntity() { return (mc.func_175606_aa() == null) ? (Entity)mc.field_71439_g : mc.func_175606_aa(); } public static void prepare(float x, float y, float x1, float y1, int color, int color1) { startRender(); GL11.glShadeModel(7425); GL11.glBegin(7); GL11.glVertex2f(x, y1); GL11.glVertex2f(x1, y1); GL11.glVertex2f(x1, y); GL11.glVertex2f(x, y); GL11.glEnd(); GL11.glShadeModel(7424); endRender2(); } public static void startRender() { GL11.glPushAttrib(1048575); GL11.glPushMatrix(); GL11.glDisable(3008); GL11.glEnable(3042); GL11.glBlendFunc(770, 771); GL11.glDisable(3553); GL11.glDisable(2929); GL11.glDepthMask(false); GL11.glEnable(2884); GL11.glEnable(2848); GL11.glHint(3154, 4353); GL11.glDisable(2896); } public static void endRender2() { GL11.glEnable(2896); GL11.glDisable(2848); GL11.glEnable(3553); GL11.glEnable(2929); GL11.glDisable(3042); GL11.glEnable(3008); GL11.glDepthMask(true); GL11.glCullFace(1029); GL11.glPopMatrix(); GL11.glPopAttrib(); } public static void drawBlurredShadow(float x, float y, float width, float height, int blurRadius, Color color) { GL11.glPushMatrix(); GlStateManager.func_179092_a(516, 0.01F); width += (blurRadius * 2); height += (blurRadius * 2); x -= blurRadius; y -= blurRadius; float _X = x - 0.25F; float _Y = y + 0.25F; int identifier = (int)(width * height + width + (color.hashCode() * blurRadius) + blurRadius); GL11.glEnable(3553); GL11.glDisable(2884); GL11.glEnable(3008); GlStateManager.func_179147_l(); int texId = -1; if (shadowCache.containsKey(Integer.valueOf(identifier))) { texId = ((Integer)shadowCache.get(Integer.valueOf(identifier))).intValue(); GlStateManager.func_179144_i(texId); } else { if (width <= 0.0F) width = 1.0F;  if (height <= 0.0F) height = 1.0F;  BufferedImage original = new BufferedImage((int)width, (int)height, 3); Graphics g = original.getGraphics(); g.setColor(color); g.fillRect(blurRadius, blurRadius, (int)(width - (blurRadius * 2)), (int)(height - (blurRadius * 2))); g.dispose(); GaussianFilter op = new GaussianFilter(blurRadius); BufferedImage blurred = op.filter(original, null); texId = TextureUtil.func_110989_a(TextureUtil.func_110996_a(), blurred, true, false); shadowCache.put(Integer.valueOf(identifier), Integer.valueOf(texId)); }  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); GL11.glBegin(7); GL11.glTexCoord2f(0.0F, 0.0F); GL11.glVertex2f(_X, _Y); GL11.glTexCoord2f(0.0F, 1.0F); GL11.glVertex2f(_X, _Y + height); GL11.glTexCoord2f(1.0F, 1.0F); GL11.glVertex2f(_X + width, _Y + height); GL11.glTexCoord2f(1.0F, 0.0F); GL11.glVertex2f(_X + width, _Y); GL11.glEnd(); GlStateManager.func_179098_w(); GlStateManager.func_179084_k(); GlStateManager.func_179117_G(); GL11.glEnable(2884); GL11.glPopMatrix(); } public static void drawBorderedRect(float left, float top, float right, float bottom, float borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) { drawRect(left - (!borderIncludedInBounds ? borderWidth : 0.0F), top - (!borderIncludedInBounds ? borderWidth : 0.0F), right + (!borderIncludedInBounds ? borderWidth : 0.0F), bottom + (!borderIncludedInBounds ? borderWidth : 0.0F), borderColor);
/* 1085 */     drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0F), top + (borderIncludedInBounds ? borderWidth : 0.0F), right - (borderIncludedInBounds ? borderWidth : 0.0F), bottom - (borderIncludedInBounds ? borderWidth : 0.0F), insideColor); }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void renderBox(AxisAlignedBB bb, Color color, Color outLineColor, float lineWidth) {
/* 1090 */     GL11.glPushMatrix();
/* 1091 */     drawOutline(bb, lineWidth, outLineColor);
/* 1092 */     drawBox(bb, color);
/* 1093 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1094 */     GL11.glPopMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawBox(AxisAlignedBB bb, Color color) {
/* 1099 */     GL11.glPushMatrix();
/* 1100 */     GL11.glEnable(3042);
/* 1101 */     GL11.glBlendFunc(770, 771);
/* 1102 */     GL11.glDisable(2896);
/* 1103 */     GL11.glDisable(3553);
/* 1104 */     GL11.glEnable(2848);
/* 1105 */     GL11.glDisable(2929);
/* 1106 */     GL11.glDepthMask(false);
/* 1107 */     color(color);
/* 1108 */     fillBox(bb);
/* 1109 */     GL11.glDisable(2848);
/* 1110 */     GL11.glEnable(3553);
/* 1111 */     GL11.glEnable(2896);
/* 1112 */     GL11.glEnable(2929);
/* 1113 */     GL11.glDepthMask(true);
/* 1114 */     GL11.glDisable(3042);
/* 1115 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public static void drawOutline(AxisAlignedBB bb, float lineWidth, Color color) {
/* 1119 */     GL11.glPushMatrix();
/* 1120 */     GL11.glEnable(3042);
/* 1121 */     GL11.glBlendFunc(770, 771);
/* 1122 */     GL11.glDisable(2896);
/* 1123 */     GL11.glDisable(3553);
/* 1124 */     GL11.glEnable(2848);
/* 1125 */     GL11.glDisable(2929);
/* 1126 */     GL11.glDepthMask(false);
/* 1127 */     GL11.glLineWidth(lineWidth);
/* 1128 */     color(color);
/* 1129 */     fillOutline(bb);
/* 1130 */     GL11.glLineWidth(1.0F);
/* 1131 */     GL11.glDisable(2848);
/* 1132 */     GL11.glEnable(3553);
/* 1133 */     GL11.glEnable(2896);
/* 1134 */     GL11.glEnable(2929);
/* 1135 */     GL11.glDepthMask(true);
/* 1136 */     GL11.glDisable(3042);
/* 1137 */     GL11.glPopMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void fillOutline(AxisAlignedBB bb) {
/* 1142 */     if (bb != null) {
/* 1143 */       GL11.glBegin(1);
/*      */       
/* 1145 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
/* 1146 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
/*      */       
/* 1148 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
/* 1149 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
/*      */       
/* 1151 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
/* 1152 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
/*      */       
/* 1154 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
/* 1155 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
/*      */       
/* 1157 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
/* 1158 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
/*      */       
/* 1160 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
/* 1161 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
/*      */       
/* 1163 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
/* 1164 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
/*      */       
/* 1166 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
/* 1167 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
/*      */       
/* 1169 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
/* 1170 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
/*      */       
/* 1172 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
/* 1173 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
/*      */       
/* 1175 */       GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
/* 1176 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
/*      */       
/* 1178 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
/* 1179 */       GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
/*      */       
/* 1181 */       glEnd();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void fillBox(AxisAlignedBB boundingBox) {
/* 1186 */     if (boundingBox != null) {
/* 1187 */       GL11.glBegin(7);
/* 1188 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1189 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1190 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1191 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1192 */       glEnd();
/*      */       
/* 1194 */       GL11.glBegin(7);
/* 1195 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1196 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1197 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1198 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1199 */       glEnd();
/*      */       
/* 1201 */       GL11.glBegin(7);
/* 1202 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1203 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1204 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1205 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1206 */       glEnd();
/*      */       
/* 1208 */       GL11.glBegin(7);
/* 1209 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1210 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1211 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1212 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1213 */       glEnd();
/*      */       
/* 1215 */       GL11.glBegin(7);
/* 1216 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1217 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1218 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1219 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1220 */       glEnd();
/*      */       
/* 1222 */       GL11.glBegin(7);
/* 1223 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1224 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1225 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1226 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1227 */       glEnd();
/*      */       
/* 1229 */       GL11.glBegin(7);
/* 1230 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1231 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1232 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1233 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1234 */       glEnd();
/*      */       
/* 1236 */       GL11.glBegin(7);
/* 1237 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1238 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1239 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1240 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1241 */       glEnd();
/*      */       
/* 1243 */       GL11.glBegin(7);
/* 1244 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1245 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1246 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1247 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1248 */       glEnd();
/*      */       
/* 1250 */       GL11.glBegin(7);
/* 1251 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1252 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72339_c);
/* 1253 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1254 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72337_e, (float)boundingBox.field_72334_f);
/* 1255 */       glEnd();
/*      */       
/* 1257 */       GL11.glBegin(7);
/* 1258 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1259 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1260 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1261 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1262 */       glEnd();
/*      */       
/* 1264 */       GL11.glBegin(7);
/* 1265 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1266 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72339_c);
/* 1267 */       GL11.glVertex3d((float)boundingBox.field_72340_a, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1268 */       GL11.glVertex3d((float)boundingBox.field_72336_d, (float)boundingBox.field_72338_b, (float)boundingBox.field_72334_f);
/* 1269 */       glEnd();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void color(Color color) {
/* 1274 */     GL11.glColor4f(color.getRed() / 255.0F, color
/* 1275 */         .getGreen() / 255.0F, color
/* 1276 */         .getBlue() / 255.0F, color
/* 1277 */         .getAlpha() / 255.0F);
/*      */   }
/*      */   
/*      */   public static void color(int color) {
/* 1281 */     Color colord = new Color(color);
/*      */     
/* 1283 */     GL11.glColor4f(colord.getRed() / 255.0F, colord.getGreen() / 255.0F, colord.getBlue() / 255.0F, colord.getAlpha() / 255.0F);
/*      */   }
/*      */   
/*      */   public static void color(float r, float g, float b, float a) {
/* 1287 */     GL11.glColor4f(r, g, b, a);
/*      */   }
/*      */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\render\RenderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */