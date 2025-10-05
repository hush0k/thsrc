/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityRenderer;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.AstolfoAnimation;
/*     */ import com.mrzak34.thunderhack.util.render.PaletteHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.vecmath.Vector3d;
/*     */ import javax.vecmath.Vector4d;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ public class ItemESP
/*     */   extends Module {
/*  32 */   public static AstolfoAnimation astolfo2 = new AstolfoAnimation();
/*  33 */   private final Setting<Boolean> entityName = register(new Setting("Name", Boolean.valueOf(true)));
/*  34 */   private final Setting<Boolean> fullBox = register(new Setting("Full Box", Boolean.valueOf(true)));
/*  35 */   private final Setting<ColorSetting> cc = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  36 */   private final Setting<ColorSetting> cc2 = register(new Setting("Color2", new ColorSetting(-2013200640)));
/*  37 */   private final int black = Color.BLACK.getRGB();
/*  38 */   public Setting<Float> scalefactor = register(new Setting("Raytrace", Float.valueOf(2.0F), Float.valueOf(0.1F), Float.valueOf(4.0F)));
/*  39 */   public Setting<Float> rads = register(new Setting("radius", Float.valueOf(2.0F), Float.valueOf(0.1F), Float.valueOf(1.0F)));
/*  40 */   private final Setting<mode> Mode = register(new Setting("Render Mode", mode.render2D));
/*  41 */   private final Setting<mode2> Mode2 = register(new Setting("Color Mode", mode2.Astolfo));
/*     */   
/*     */   public ItemESP() {
/*  44 */     super("ItemESP", "ItemESP", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  49 */     for (Entity item : mc.field_71441_e.field_72996_f) {
/*  50 */       if (item instanceof EntityItem) {
/*  51 */         int color = 0;
/*  52 */         if (this.Mode2.getValue() == mode2.Custom) {
/*  53 */           color = ((ColorSetting)this.cc.getValue()).getColor();
/*     */         }
/*  55 */         if (this.Mode2.getValue() == mode2.Astolfo) {
/*  56 */           color = PaletteHelper.astolfo(false, (int)item.field_70131_O).getRGB();
/*     */         }
/*  58 */         if (this.Mode.getValue() == mode.render3D) {
/*  59 */           GlStateManager.func_179094_E();
/*  60 */           RenderHelper.drawEntityBox(item, new Color(color), ((ColorSetting)this.cc2.getValue()).getColorObject(), ((Boolean)this.fullBox.getValue()).booleanValue(), ((Boolean)this.fullBox.getValue()).booleanValue() ? 0.15F : 0.9F);
/*  61 */           GlStateManager.func_179121_F();
/*     */         } 
/*  63 */         if (this.Mode.getValue() == mode.Circle) {
/*  64 */           RenderHelper.drawCircle3D(item, ((Float)this.rads.getValue()).floatValue(), event.getPartialTicks(), 32, 2.0F, color, (this.Mode2.getValue() == mode2.Astolfo));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent event) {
/*  72 */     Float scaleFactor = (Float)this.scalefactor.getValue();
/*  73 */     double scaling = scaleFactor.floatValue() / Math.pow(scaleFactor.floatValue(), 2.0D);
/*  74 */     GlStateManager.func_179139_a(scaling, scaling, scaling);
/*  75 */     Color c = ((ColorSetting)this.cc.getValue()).getColorObject();
/*  76 */     int color = 0;
/*     */     
/*  78 */     if (this.Mode2.getValue() == mode2.Custom) {
/*  79 */       color = c.getRGB();
/*     */     }
/*  81 */     if (this.Mode2.getValue() == mode2.Astolfo) {
/*  82 */       color = PaletteHelper.astolfo(false, 1).getRGB();
/*     */     }
/*  84 */     float scale = 1.0F;
/*  85 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/*  86 */       if (isValid(entity) && RenderHelper.isInViewFrustum(entity)) {
/*  87 */         EntityItem entityItem = (EntityItem)entity;
/*  88 */         double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * mc.func_184121_ak();
/*  89 */         double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * mc.func_184121_ak();
/*  90 */         double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * mc.func_184121_ak();
/*  91 */         AxisAlignedBB axisAlignedBB2 = entity.func_174813_aQ();
/*  92 */         AxisAlignedBB axisAlignedBB = new AxisAlignedBB(axisAlignedBB2.field_72340_a - entity.field_70165_t + x - 0.05D, axisAlignedBB2.field_72338_b - entity.field_70163_u + y, axisAlignedBB2.field_72339_c - entity.field_70161_v + z - 0.05D, axisAlignedBB2.field_72336_d - entity.field_70165_t + x + 0.05D, axisAlignedBB2.field_72337_e - entity.field_70163_u + y + 0.15D, axisAlignedBB2.field_72334_f - entity.field_70161_v + z + 0.05D);
/*  93 */         Vector3d[] vectors = { new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f), new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f) };
/*  94 */         ((IEntityRenderer)mc.field_71460_t).invokeSetupCameraTransform(event.getPartialTicks(), 0);
/*     */         
/*  96 */         Vector4d position = null;
/*  97 */         for (Vector3d vector : vectors) {
/*  98 */           vector = project2D(scaleFactor, vector.x - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX(), vector.y - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY(), vector.z - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ());
/*  99 */           if (vector != null && vector.z > 0.0D && vector.z < 1.0D) {
/* 100 */             if (position == null)
/* 101 */               position = new Vector4d(vector.x, vector.y, vector.z, 0.0D); 
/* 102 */             position.x = Math.min(vector.x, position.x);
/* 103 */             position.y = Math.min(vector.y, position.y);
/* 104 */             position.z = Math.max(vector.x, position.z);
/* 105 */             position.w = Math.max(vector.y, position.w);
/*     */           } 
/*     */         } 
/*     */         
/* 109 */         if (position != null) {
/* 110 */           mc.field_71460_t.func_78478_c();
/* 111 */           double posX = position.x;
/* 112 */           double posY = position.y;
/* 113 */           double endPosX = position.z;
/* 114 */           double endPosY = position.w;
/*     */           
/* 116 */           if (this.Mode.getValue() == mode.render2D) {
/* 117 */             RenderUtil.drawRect(posX - 1.0D, posY, posX + 0.5D, endPosY + 0.5D, this.black);
/* 118 */             RenderUtil.drawRect(posX - 1.0D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 0.5D, this.black);
/* 119 */             RenderUtil.drawRect(endPosX - 0.5D - 0.5D, posY, endPosX + 0.5D, endPosY + 0.5D, this.black);
/* 120 */             RenderUtil.drawRect(posX - 1.0D, endPosY - 0.5D - 0.5D, endPosX + 0.5D, endPosY + 0.5D, this.black);
/* 121 */             RenderUtil.drawRect(posX - 0.5D, posY, posX + 0.5D - 0.5D, endPosY, color);
/* 122 */             RenderUtil.drawRect(posX, endPosY - 0.5D, endPosX, endPosY, color);
/* 123 */             RenderUtil.drawRect(posX - 0.5D, posY, endPosX, posY + 0.5D, color);
/* 124 */             RenderUtil.drawRect(endPosX - 0.5D, posY, endPosX, endPosY, color);
/*     */           } 
/*     */           
/* 127 */           float diff = (float)(endPosX - posX) / 2.0F;
/* 128 */           float textWidth = Util.fr.func_78256_a(entityItem.func_92059_d().func_82833_r()) * scale;
/* 129 */           float tagX = (float)((posX + diff - (textWidth / 2.0F)) * scale);
/* 130 */           if (((Boolean)this.entityName.getValue()).booleanValue()) {
/* 131 */             Util.fr.func_175063_a(entityItem.func_92059_d().func_82833_r(), tagX, (float)posY - 10.0F, -1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 136 */     GL11.glEnable(2929);
/* 137 */     mc.field_71460_t.func_78478_c();
/*     */   }
/*     */   
/*     */   private Vector3d project2D(Float scaleFactor, double x, double y, double z) {
/* 141 */     float xPos = (float)x;
/* 142 */     float yPos = (float)y;
/* 143 */     float zPos = (float)z;
/* 144 */     IntBuffer viewport = GLAllocation.func_74527_f(16);
/* 145 */     FloatBuffer modelview = GLAllocation.func_74529_h(16);
/* 146 */     FloatBuffer projection = GLAllocation.func_74529_h(16);
/* 147 */     FloatBuffer vector = GLAllocation.func_74529_h(4);
/* 148 */     GL11.glGetFloat(2982, modelview);
/* 149 */     GL11.glGetFloat(2983, projection);
/* 150 */     GL11.glGetInteger(2978, viewport);
/* 151 */     if (GLU.gluProject(xPos, yPos, zPos, modelview, projection, viewport, vector))
/* 152 */       return new Vector3d((vector.get(0) / scaleFactor.floatValue()), ((Display.getHeight() - vector.get(1)) / scaleFactor.floatValue()), vector.get(2)); 
/* 153 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isValid(Entity entity) {
/* 157 */     return entity instanceof EntityItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 162 */     astolfo2.update();
/*     */   }
/*     */   
/*     */   public enum mode
/*     */   {
/* 167 */     render2D, render3D, Circle;
/*     */   }
/*     */   
/*     */   public enum mode2 {
/* 171 */     Custom, Astolfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\ItemESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */