/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityRenderer;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.player.ElytraSwap;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.PNGtoResourceLocation;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.PaletteHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.vecmath.Vector3d;
/*     */ import javax.vecmath.Vector4d;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ 
/*     */ public class ImageESP
/*     */   extends Module
/*     */ {
/*  36 */   private final int black = Color.BLACK.getRGB();
/*  37 */   private final Setting<ColorSetting> cc = register(new Setting("CustomColor", new ColorSetting(-2013200640)));
/*     */ 
/*     */   
/*  40 */   public Setting<Float> scalefactor = register(new Setting("Raytrace", Float.valueOf(2.0F), Float.valueOf(0.1F), Float.valueOf(4.0F)));
/*  41 */   public Setting<Boolean> wtf = register(new Setting("Not done", Boolean.valueOf(false)));
/*  42 */   public Setting<Float> scalefactor1 = register(new Setting("X", Float.valueOf(0.0F), Float.valueOf(-6.0F), Float.valueOf(6.0F)));
/*  43 */   public Setting<Float> scalefactor2 = register(new Setting("Y", Float.valueOf(0.0F), Float.valueOf(-6.0F), Float.valueOf(6.0F)));
/*     */   ResourceLocation customImg;
/*     */   ResourceLocation customImg2;
/*     */   ResourceLocation customImg3;
/*  47 */   private final Setting<mode2> Mode2 = register(new Setting("Color Mode", mode2.Rainbow));
/*  48 */   private final Setting<mode3> Mode3 = register(new Setting("Color Mode", mode3.CAT));
/*     */ 
/*     */   
/*     */   public ImageESP() {
/*  52 */     super("ImageESP", "ImageESP ImageESP.", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent event) {
/*  57 */     Float scaleFactor = (Float)this.scalefactor.getValue();
/*  58 */     double scaling = scaleFactor.floatValue() / Math.pow(scaleFactor.floatValue(), 2.0D);
/*  59 */     GlStateManager.func_179139_a(scaling, scaling, scaling);
/*  60 */     Color c = ((ColorSetting)this.cc.getValue()).getColorObject();
/*  61 */     int color = 0;
/*     */     
/*  63 */     if (this.Mode2.getValue() == mode2.Custom) {
/*  64 */       color = c.getRGB();
/*     */     }
/*  66 */     if (this.Mode2.getValue() == mode2.Astolfo) {
/*  67 */       color = PaletteHelper.astolfo(false, 1).getRGB();
/*     */     }
/*  69 */     if (this.Mode2.getValue() == mode2.Rainbow) {
/*  70 */       color = PaletteHelper.rainbow(300, 1.0F, 1.0F).getRGB();
/*     */     }
/*     */     
/*  73 */     float scale = 1.0F;
/*     */     
/*  75 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/*  76 */       if (isValid(entity) && RenderHelper.isInViewFrustum(entity)) {
/*  77 */         EntityPlayer entityPlayer = (EntityPlayer)entity;
/*  78 */         if (entityPlayer != mc.field_71439_g) {
/*  79 */           double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * mc.func_184121_ak();
/*  80 */           double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * mc.func_184121_ak();
/*  81 */           double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * mc.func_184121_ak();
/*  82 */           AxisAlignedBB axisAlignedBB2 = entity.func_174813_aQ();
/*  83 */           AxisAlignedBB axisAlignedBB = new AxisAlignedBB(axisAlignedBB2.field_72340_a - entity.field_70165_t + x - 0.05D, axisAlignedBB2.field_72338_b - entity.field_70163_u + y, axisAlignedBB2.field_72339_c - entity.field_70161_v + z - 0.05D, axisAlignedBB2.field_72336_d - entity.field_70165_t + x + 0.05D, axisAlignedBB2.field_72337_e - entity.field_70163_u + y + 0.15D, axisAlignedBB2.field_72334_f - entity.field_70161_v + z + 0.05D);
/*  84 */           Vector3d[] vectors = { new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f), new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f) };
/*  85 */           ((IEntityRenderer)mc.field_71460_t).invokeSetupCameraTransform(event.getPartialTicks(), 0);
/*     */           
/*  87 */           Vector4d position = null;
/*  88 */           for (Vector3d vector : vectors) {
/*  89 */             vector = project2D(scaleFactor, vector.x - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX(), vector.y - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY(), vector.z - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ());
/*  90 */             if (vector != null && vector.z > 0.0D && vector.z < 1.0D) {
/*  91 */               if (position == null)
/*  92 */                 position = new Vector4d(vector.x, vector.y, vector.z, 0.0D); 
/*  93 */               position.x = Math.min(vector.x, position.x);
/*  94 */               position.y = Math.min(vector.y, position.y);
/*  95 */               position.z = Math.max(vector.x, position.z);
/*  96 */               position.w = Math.max(vector.y, position.w);
/*     */             } 
/*     */           } 
/*     */           
/* 100 */           if (position != null) {
/*     */ 
/*     */             
/* 103 */             mc.field_71460_t.func_78478_c();
/* 104 */             double posX = position.x;
/* 105 */             double posY = position.y;
/* 106 */             double endPosX = position.z;
/* 107 */             double endPosY = position.w;
/*     */             
/* 109 */             if (((Boolean)this.wtf.getValue()).booleanValue()) {
/* 110 */               RenderUtil.drawRect(posX - 1.0D, posY, posX + 0.5D, endPosY + 0.5D, this.black);
/* 111 */               RenderUtil.drawRect(posX - 1.0D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 0.5D, this.black);
/* 112 */               RenderUtil.drawRect(endPosX - 0.5D - 0.5D, posY, endPosX + 0.5D, endPosY + 0.5D, this.black);
/* 113 */               RenderUtil.drawRect(posX - 1.0D, endPosY - 0.5D - 0.5D, endPosX + 0.5D, endPosY + 0.5D, this.black);
/* 114 */               RenderUtil.drawRect(posX - 0.5D, posY, posX + 0.5D - 0.5D, endPosY, color);
/* 115 */               RenderUtil.drawRect(posX, endPosY - 0.5D, endPosX, endPosY, color);
/* 116 */               RenderUtil.drawRect(posX - 0.5D, posY, endPosX, posY + 0.5D, color);
/* 117 */               RenderUtil.drawRect(endPosX - 0.5D, posY, endPosX, endPosY, color);
/*     */             } 
/* 119 */             RenderUtil.drawRect(posX, posY, posX, posY, color);
/*     */             
/* 121 */             if (this.Mode3.getValue() == mode3.CAT) {
/* 122 */               Minecraft.func_71410_x().func_110434_K().func_110577_a(new ResourceLocation("textures/image9.png"));
/*     */             }
/*     */             
/* 125 */             if (this.Mode3.getValue() == mode3.Custom1) {
/* 126 */               if (this.customImg == null) {
/* 127 */                 if (PNGtoResourceLocation.getCustomImg("esp1", "png") != null) {
/* 128 */                   this.customImg = PNGtoResourceLocation.getCustomImg("esp1", "png");
/*     */                 }
/*     */                 return;
/*     */               } 
/* 132 */               Util.mc.func_110434_K().func_110577_a(this.customImg);
/*     */             } 
/* 134 */             if (this.Mode3.getValue() == mode3.Custom2) {
/* 135 */               if (this.customImg2 == null) {
/* 136 */                 if (PNGtoResourceLocation.getCustomImg("esp2", "png") != null) {
/* 137 */                   this.customImg2 = PNGtoResourceLocation.getCustomImg("esp2", "png");
/*     */                 }
/*     */                 return;
/*     */               } 
/* 141 */               Util.mc.func_110434_K().func_110577_a(this.customImg2);
/*     */             } 
/* 143 */             if (this.Mode3.getValue() == mode3.Custom3) {
/* 144 */               if (this.customImg3 == null) {
/* 145 */                 if (PNGtoResourceLocation.getCustomImg("esp3", "png") != null) {
/* 146 */                   this.customImg3 = PNGtoResourceLocation.getCustomImg("esp3", "png");
/*     */                 }
/*     */                 return;
/*     */               } 
/* 150 */               Util.mc.func_110434_K().func_110577_a(this.customImg3);
/*     */             } 
/* 152 */             ElytraSwap.drawCompleteImage((float)posX + ((Float)this.scalefactor1.getValue()).floatValue(), (float)posY + ((Float)this.scalefactor2.getValue()).floatValue(), (int)((int)endPosX - posX), (int)((int)endPosY - posY));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 157 */     GL11.glEnable(2929);
/* 158 */     mc.field_71460_t.func_78478_c();
/*     */   }
/*     */   
/*     */   private Vector3d project2D(Float scaleFactor, double x, double y, double z) {
/* 162 */     float xPos = (float)x;
/* 163 */     float yPos = (float)y;
/* 164 */     float zPos = (float)z;
/* 165 */     IntBuffer viewport = GLAllocation.func_74527_f(16);
/* 166 */     FloatBuffer modelview = GLAllocation.func_74529_h(16);
/* 167 */     FloatBuffer projection = GLAllocation.func_74529_h(16);
/* 168 */     FloatBuffer vector = GLAllocation.func_74529_h(4);
/* 169 */     GL11.glGetFloat(2982, modelview);
/* 170 */     GL11.glGetFloat(2983, projection);
/* 171 */     GL11.glGetInteger(2978, viewport);
/* 172 */     if (GLU.gluProject(xPos, yPos, zPos, modelview, projection, viewport, vector))
/* 173 */       return new Vector3d((vector.get(0) / scaleFactor.floatValue()), ((Display.getHeight() - vector.get(1)) / scaleFactor.floatValue()), vector.get(2)); 
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isValid(Entity entity) {
/* 178 */     return entity instanceof EntityPlayer;
/*     */   }
/*     */   
/*     */   public enum mode2
/*     */   {
/* 183 */     Custom, Rainbow, Astolfo;
/*     */   }
/*     */   
/*     */   public enum mode3 {
/* 187 */     CAT, Custom1, Custom2, Custom3;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\ImageESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */