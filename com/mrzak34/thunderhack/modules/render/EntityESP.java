/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityRenderer;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.math.MathematicHelper;
/*     */ import com.mrzak34.thunderhack.util.render.DrawHelper;
/*     */ import java.awt.Color;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.vecmath.Vector3d;
/*     */ import javax.vecmath.Vector4d;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityESP
/*     */   extends Module
/*     */ {
/*  41 */   public final Setting<Boolean> border = register(new Setting("Border Rect", Boolean.valueOf(true)));
/*  42 */   public final Setting<Boolean> fullBox = register(new Setting("Full Box", Boolean.valueOf(false)));
/*  43 */   public final Setting<Boolean> heathPercentage = register(new Setting("HealthPercent", Boolean.valueOf(true)));
/*  44 */   public final Setting<Boolean> healRect = register(new Setting("HealthRect", Boolean.valueOf(true)));
/*  45 */   public final Setting<Boolean> ignoreInvisible = register(new Setting("IgnoreInvisible", Boolean.valueOf(true)));
/*  46 */   private final int black = Color.BLACK.getRGB();
/*  47 */   private final Setting<ColorSetting> colorEsp = register(new Setting("ESPColor", new ColorSetting(575714484)));
/*  48 */   private final Setting<ColorSetting> healColor = register(new Setting("HealthColor", new ColorSetting(575714484)));
/*  49 */   private final Setting<healcolorModeEn> healcolorMode = register(new Setting("HealthMode", healcolorModeEn.Custom));
/*  50 */   private final Setting<colorModeEn> colorMode = register(new Setting("ColorBoxMode", colorModeEn.Custom));
/*  51 */   private final Setting<espModeEn> espMode = register(new Setting("espMode", espModeEn.Flat));
/*  52 */   private final Setting<rectModeEn> rectMode = register(new Setting("RectMode", rectModeEn.Default));
/*  53 */   private final Setting<csgoModeEn> csgoMode = register(new Setting("csgoMode", csgoModeEn.Box));
/*     */   public EntityESP() {
/*  55 */     super("EntityESP", "Ренднрит есп-сущностей", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event3D) {
/*  60 */     int color = 0;
/*     */     
/*  62 */     switch ((colorModeEn)this.colorMode.getValue()) {
/*     */       case Custom:
/*  64 */         color = ((ColorSetting)this.colorEsp.getValue()).getColor();
/*     */         break;
/*     */       case Astolfo:
/*  67 */         color = DrawHelper.astolfo(false, 10).getRGB();
/*     */         break;
/*     */       case Rainbow:
/*  70 */         color = DrawHelper.rainbow(300, 1.0F, 1.0F).getRGB();
/*     */         break;
/*     */     } 
/*     */     
/*  74 */     if (this.espMode.getValue() == espModeEn.Box) {
/*  75 */       GlStateManager.func_179094_E();
/*  76 */       for (Entity entity : mc.field_71441_e.field_72996_f) {
/*  77 */         if (entity instanceof net.minecraft.entity.player.EntityPlayer && entity != mc.field_71439_g) {
/*  78 */           DrawHelper.drawEntityBox(entity, new Color(color), ((Boolean)this.fullBox.getValue()).booleanValue(), ((Boolean)this.fullBox.getValue()).booleanValue() ? 0.15F : 0.9F);
/*     */         }
/*     */       } 
/*  81 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent event) {
/*  87 */     ScaledResolution sr = new ScaledResolution(mc);
/*  88 */     int scaleFactor = sr.func_78325_e();
/*  89 */     double scaling = scaleFactor / Math.pow(scaleFactor, 2.0D);
/*  90 */     GL11.glPushMatrix();
/*  91 */     GlStateManager.func_179139_a(scaling, scaling, scaling);
/*     */     
/*  93 */     int color = 0;
/*  94 */     switch ((colorModeEn)this.colorMode.getValue()) {
/*     */       case Custom:
/*  96 */         color = ((ColorSetting)this.colorEsp.getValue()).getColor();
/*     */         break;
/*     */       case Astolfo:
/*  99 */         color = DrawHelper.astolfo(false, 1).getRGB();
/*     */         break;
/*     */       case Rainbow:
/* 102 */         color = DrawHelper.rainbow(300, 1.0F, 1.0F).getRGB();
/*     */         break;
/*     */     } 
/*     */     
/* 106 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 107 */       if (entity instanceof net.minecraft.entity.player.EntityPlayer && entity != mc.field_71439_g) {
/*     */ 
/*     */         
/* 110 */         if (((Boolean)this.ignoreInvisible.getValue()).booleanValue() && entity.func_82150_aj()) {
/*     */           continue;
/*     */         }
/* 113 */         if (isValid(entity) && DrawHelper.isInViewFrustum(entity)) {
/* 114 */           double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * mc.func_184121_ak();
/* 115 */           double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * mc.func_184121_ak();
/* 116 */           double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * mc.func_184121_ak();
/* 117 */           double width = entity.field_70130_N / 1.5D;
/* 118 */           double height = entity.field_70131_O + ((entity.func_70093_af() || (entity == mc.field_71439_g && mc.field_71439_g.func_70093_af())) ? -0.3D : 0.2D);
/* 119 */           AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
/* 120 */           List<Vector3d> vectors = Arrays.asList(new Vector3d[] { new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c), new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f), new Vector3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f), new Vector3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f) });
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 125 */           ((IEntityRenderer)mc.field_71460_t).invokeSetupCameraTransform(event.getPartialTicks(), 0);
/*     */           
/* 127 */           Vector4d position = null;
/* 128 */           for (Vector3d vector : vectors) {
/* 129 */             vector = vectorRender2D(scaleFactor, vector.x - ((IRenderManager)mc.func_175598_ae()).getRenderPosX(), vector.y - ((IRenderManager)mc.func_175598_ae()).getRenderPosY(), vector.z - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ());
/* 130 */             if (vector != null && vector.z > 0.0D && vector.z < 1.0D) {
/*     */               
/* 132 */               if (position == null) {
/* 133 */                 position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);
/*     */               }
/*     */               
/* 136 */               position.x = Math.min(vector.x, position.x);
/* 137 */               position.y = Math.min(vector.y, position.y);
/* 138 */               position.z = Math.max(vector.x, position.z);
/* 139 */               position.w = Math.max(vector.y, position.w);
/*     */             } 
/*     */           } 
/*     */           
/* 143 */           if (position != null) {
/* 144 */             mc.field_71460_t.func_78478_c();
/* 145 */             double posX = position.x;
/* 146 */             double posY = position.y;
/* 147 */             double endPosX = position.z;
/* 148 */             double endPosY = position.w;
/* 149 */             if (((Boolean)this.border.getValue()).booleanValue()) {
/* 150 */               if (this.espMode.getValue() == espModeEn.Flat && this.csgoMode.getValue() == csgoModeEn.Box && this.rectMode.getValue() == rectModeEn.Smooth) {
/*     */ 
/*     */                 
/* 153 */                 DrawHelper.drawSmoothRect(posX - 0.5D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 1.0D, this.black);
/*     */ 
/*     */                 
/* 156 */                 DrawHelper.drawSmoothRect(posX - 0.5D, endPosY - 0.5D - 1.0D, endPosX + 0.5D, endPosY + 0.5D, this.black);
/*     */ 
/*     */                 
/* 159 */                 DrawHelper.drawSmoothRect(posX - 1.5D, posY, posX + 0.5D, endPosY + 0.5D, this.black);
/*     */ 
/*     */                 
/* 162 */                 DrawHelper.drawSmoothRect(endPosX - 0.5D - 1.0D, posY, endPosX + 0.5D, endPosY + 0.5D, this.black);
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 167 */                 DrawHelper.drawSmoothRect(posX - 1.0D, posY, posX + 0.5D - 0.5D, endPosY, color);
/*     */ 
/*     */                 
/* 170 */                 DrawHelper.drawSmoothRect(posX, endPosY - 1.0D, endPosX, endPosY, color);
/*     */ 
/*     */                 
/* 173 */                 DrawHelper.drawSmoothRect(posX - 1.0D, posY, endPosX, posY + 1.0D, color);
/*     */ 
/*     */                 
/* 176 */                 DrawHelper.drawSmoothRect(endPosX - 1.0D, posY, endPosX, endPosY, color);
/* 177 */               } else if (this.espMode.getValue() == espModeEn.Flat && this.csgoMode.getValue() == csgoModeEn.Corner && this.rectMode.getValue() == rectModeEn.Smooth) {
/*     */ 
/*     */                 
/* 180 */                 DrawHelper.drawSmoothRect(posX + 1.0D, posY, posX - 1.0D, posY + (endPosY - posY) / 4.0D + 0.5D, this.black);
/*     */ 
/*     */                 
/* 183 */                 DrawHelper.drawSmoothRect(posX - 1.0D, endPosY, posX + 1.0D, endPosY - (endPosY - posY) / 4.0D - 0.5D, this.black);
/*     */ 
/*     */                 
/* 186 */                 DrawHelper.drawSmoothRect(posX - 1.0D, posY - 0.5D, posX + (endPosX - posX) / 3.0D, posY + 1.0D, this.black);
/*     */ 
/*     */                 
/* 189 */                 DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0D - 0.0D, posY - 0.5D, endPosX, posY + 1.5D, this.black);
/*     */ 
/*     */                 
/* 192 */                 DrawHelper.drawSmoothRect(endPosX - 1.5D, posY, endPosX + 0.5D, posY + (endPosY - posY) / 4.0D + 0.5D, this.black);
/*     */ 
/*     */                 
/* 195 */                 DrawHelper.drawSmoothRect(endPosX - 1.5D, endPosY, endPosX + 0.5D, endPosY - (endPosY - posY) / 4.0D - 0.5D, this.black);
/*     */ 
/*     */                 
/* 198 */                 DrawHelper.drawSmoothRect(posX - 1.0D, endPosY - 1.5D, posX + (endPosX - posX) / 3.0D + 0.5D, endPosY + 0.5D, this.black);
/*     */ 
/*     */                 
/* 201 */                 DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0D - 0.5D, endPosY - 1.5D, endPosX + 0.5D, endPosY + 0.5D, this.black);
/*     */                 
/* 203 */                 DrawHelper.drawSmoothRect(posX + 0.5D, posY, posX - 0.5D, posY + (endPosY - posY) / 4.0D, color);
/*     */                 
/* 205 */                 DrawHelper.drawSmoothRect(posX + 0.5D, endPosY, posX - 0.5D, endPosY - (endPosY - posY) / 4.0D, color);
/*     */                 
/* 207 */                 DrawHelper.drawSmoothRect(posX - 0.5D, posY, posX + (endPosX - posX) / 3.0D, posY + 1.0D, color);
/* 208 */                 DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0D + 0.5D, posY, endPosX, posY + 1.0D, color);
/* 209 */                 DrawHelper.drawSmoothRect(endPosX - 1.0D, posY, endPosX, posY + (endPosY - posY) / 4.0D + 0.5D, color);
/* 210 */                 DrawHelper.drawSmoothRect(endPosX - 1.0D, endPosY, endPosX, endPosY - (endPosY - posY) / 4.0D, color);
/* 211 */                 DrawHelper.drawSmoothRect(posX, endPosY - 1.0D, posX + (endPosX - posX) / 3.0D, endPosY, color);
/* 212 */                 DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0D, endPosY - 1.0D, endPosX - 0.5D, endPosY, color);
/* 213 */               } else if (this.espMode.getValue() == espModeEn.Flat && this.csgoMode.getValue() == csgoModeEn.Box && this.rectMode.getValue() == rectModeEn.Default) {
/*     */ 
/*     */                 
/* 216 */                 DrawHelper.drawNewRect(posX - 0.5D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 1.0D, this.black);
/*     */ 
/*     */                 
/* 219 */                 DrawHelper.drawNewRect(posX - 0.5D, endPosY - 0.5D - 1.0D, endPosX + 0.5D, endPosY + 0.5D, this.black);
/*     */ 
/*     */                 
/* 222 */                 DrawHelper.drawNewRect(posX - 1.5D, posY, posX + 0.5D, endPosY + 0.5D, this.black);
/*     */ 
/*     */                 
/* 225 */                 DrawHelper.drawNewRect(endPosX - 0.5D - 1.0D, posY, endPosX + 0.5D, endPosY + 0.5D, this.black);
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 230 */                 DrawHelper.drawNewRect(posX - 1.0D, posY, posX + 0.5D - 0.5D, endPosY, color);
/*     */ 
/*     */                 
/* 233 */                 DrawHelper.drawNewRect(posX, endPosY - 1.0D, endPosX, endPosY, color);
/*     */ 
/*     */                 
/* 236 */                 DrawHelper.drawNewRect(posX - 1.0D, posY, endPosX, posY + 1.0D, color);
/*     */ 
/*     */                 
/* 239 */                 DrawHelper.drawNewRect(endPosX - 1.0D, posY, endPosX, endPosY, color);
/* 240 */               } else if (this.espMode.getValue() == espModeEn.Flat && this.csgoMode.getValue() == csgoModeEn.Corner && this.rectMode.getValue() == rectModeEn.Default) {
/*     */ 
/*     */                 
/* 243 */                 DrawHelper.drawNewRect(posX + 1.0D, posY, posX - 1.0D, posY + (endPosY - posY) / 4.0D + 0.5D, this.black);
/*     */ 
/*     */                 
/* 246 */                 DrawHelper.drawNewRect(posX - 1.0D, endPosY, posX + 1.0D, endPosY - (endPosY - posY) / 4.0D - 0.5D, this.black);
/*     */ 
/*     */                 
/* 249 */                 DrawHelper.drawNewRect(posX - 1.0D, posY - 0.5D, posX + (endPosX - posX) / 3.0D, posY + 1.0D, this.black);
/*     */ 
/*     */                 
/* 252 */                 DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0D - 0.0D, posY - 0.5D, endPosX, posY + 1.5D, this.black);
/*     */ 
/*     */                 
/* 255 */                 DrawHelper.drawNewRect(endPosX - 1.5D, posY, endPosX + 0.5D, posY + (endPosY - posY) / 4.0D + 0.5D, this.black);
/*     */ 
/*     */                 
/* 258 */                 DrawHelper.drawNewRect(endPosX - 1.5D, endPosY, endPosX + 0.5D, endPosY - (endPosY - posY) / 4.0D - 0.5D, this.black);
/*     */ 
/*     */                 
/* 261 */                 DrawHelper.drawNewRect(posX - 1.0D, endPosY - 1.5D, posX + (endPosX - posX) / 3.0D + 0.5D, endPosY + 0.5D, this.black);
/*     */ 
/*     */                 
/* 264 */                 DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0D - 0.5D, endPosY - 1.5D, endPosX + 0.5D, endPosY + 0.5D, this.black);
/*     */                 
/* 266 */                 DrawHelper.drawNewRect(posX + 0.5D, posY, posX - 0.5D, posY + (endPosY - posY) / 4.0D, color);
/*     */                 
/* 268 */                 DrawHelper.drawNewRect(posX + 0.5D, endPosY, posX - 0.5D, endPosY - (endPosY - posY) / 4.0D, color);
/*     */                 
/* 270 */                 DrawHelper.drawNewRect(posX - 0.5D, posY, posX + (endPosX - posX) / 3.0D, posY + 1.0D, color);
/* 271 */                 DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0D + 0.5D, posY, endPosX, posY + 1.0D, color);
/* 272 */                 DrawHelper.drawNewRect(endPosX - 1.0D, posY, endPosX, posY + (endPosY - posY) / 4.0D + 0.5D, color);
/* 273 */                 DrawHelper.drawNewRect(endPosX - 1.0D, endPosY, endPosX, endPosY - (endPosY - posY) / 4.0D, color);
/* 274 */                 DrawHelper.drawNewRect(posX, endPosY - 1.0D, posX + (endPosX - posX) / 3.0D, endPosY, color);
/* 275 */                 DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0D, endPosY - 1.0D, endPosX - 0.5D, endPosY, color);
/*     */               } 
/*     */             }
/* 278 */             boolean living = entity instanceof EntityLivingBase;
/* 279 */             EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
/* 280 */             float targetHP = entityLivingBase.func_110143_aJ();
/* 281 */             targetHP = MathHelper.func_76131_a(targetHP, 0.0F, 24.0F);
/* 282 */             float maxHealth = entityLivingBase.func_110138_aP();
/* 283 */             double hpPercentage = (targetHP / maxHealth);
/* 284 */             double hpHeight2 = (endPosY - posY) * hpPercentage;
/*     */ 
/*     */             
/* 287 */             if (living && ((Boolean)this.healRect.getValue()).booleanValue() && this.espMode.getValue() != espModeEn.Box) {
/* 288 */               int colorHeal = 0;
/*     */               
/* 290 */               switch ((healcolorModeEn)this.healcolorMode.getValue()) {
/*     */                 case Custom:
/* 292 */                   colorHeal = ((ColorSetting)this.healColor.getValue()).getColor();
/*     */                   break;
/*     */                 case Astolfo:
/* 295 */                   colorHeal = DrawHelper.astolfo(false, (int)entity.field_70131_O).getRGB();
/*     */                   break;
/*     */                 case Rainbow:
/* 298 */                   colorHeal = DrawHelper.rainbow(300, 1.0F, 1.0F).getRGB();
/*     */                   break;
/*     */                 case Health:
/* 301 */                   colorHeal = DrawHelper.getHealthColor(((EntityLivingBase)entity).func_110143_aJ(), ((EntityLivingBase)entity).func_110138_aP());
/*     */                   break;
/*     */               } 
/* 304 */               if (targetHP > 0.0F) {
/* 305 */                 String string2 = "" + MathematicHelper.round(entityLivingBase.func_110143_aJ() / entityLivingBase.func_110138_aP() * 20.0F, 1);
/* 306 */                 if (living && ((Boolean)this.heathPercentage.getValue()).booleanValue() && this.espMode.getValue() != espModeEn.Box && (
/* 307 */                   (Boolean)this.heathPercentage.getValue()).booleanValue()) {
/* 308 */                   GlStateManager.func_179094_E();
/*     */                   
/* 310 */                   mc.field_71466_p.func_175063_a(string2, (float)posX - 30.0F, (float)((float)endPosY - hpHeight2), -1);
/* 311 */                   GlStateManager.func_179121_F();
/*     */                 } 
/*     */                 
/* 314 */                 DrawHelper.drawRect(posX - 5.0D, posY - 0.5D, posX - 2.5D, endPosY + 0.5D, (new Color(0, 0, 0, 125)).getRGB());
/* 315 */                 DrawHelper.drawRect(posX - 4.5D, endPosY, posX - 3.0D, endPosY - hpHeight2, colorHeal);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     GL11.glEnable(2929);
/* 324 */     GlStateManager.func_179147_l();
/* 325 */     GL11.glPopMatrix();
/* 326 */     mc.field_71460_t.func_78478_c();
/*     */   }
/*     */   
/*     */   private boolean isValid(Entity entity) {
/* 330 */     if (mc.field_71474_y.field_74320_O == 0 && entity == mc.field_71439_g)
/* 331 */       return false; 
/* 332 */     if (entity.field_70128_L)
/* 333 */       return false; 
/* 334 */     if (entity instanceof net.minecraft.entity.passive.EntityAnimal)
/* 335 */       return false; 
/* 336 */     if (entity instanceof net.minecraft.entity.player.EntityPlayer)
/* 337 */       return true; 
/* 338 */     if (entity instanceof net.minecraft.entity.item.EntityArmorStand)
/* 339 */       return false; 
/* 340 */     if (entity instanceof net.minecraft.entity.passive.IAnimals)
/* 341 */       return false; 
/* 342 */     if (entity instanceof net.minecraft.entity.item.EntityItemFrame)
/* 343 */       return false; 
/* 344 */     if (entity instanceof net.minecraft.entity.projectile.EntityArrow)
/* 345 */       return false; 
/* 346 */     if (entity instanceof net.minecraft.entity.item.EntityMinecart)
/* 347 */       return false; 
/* 348 */     if (entity instanceof net.minecraft.entity.item.EntityBoat)
/* 349 */       return false; 
/* 350 */     if (entity instanceof net.minecraft.entity.projectile.EntityDragonFireball)
/* 351 */       return false; 
/* 352 */     if (entity instanceof net.minecraft.entity.item.EntityXPOrb)
/* 353 */       return false; 
/* 354 */     if (entity instanceof net.minecraft.entity.item.EntityTNTPrimed)
/* 355 */       return false; 
/* 356 */     if (entity instanceof net.minecraft.entity.item.EntityExpBottle)
/* 357 */       return false; 
/* 358 */     if (entity instanceof net.minecraft.entity.effect.EntityLightningBolt)
/* 359 */       return false; 
/* 360 */     if (entity instanceof net.minecraft.entity.projectile.EntityPotion)
/* 361 */       return false; 
/* 362 */     if (entity instanceof Entity)
/* 363 */       return false; 
/* 364 */     if (entity instanceof net.minecraft.entity.monster.EntityMob || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.boss.EntityDragon || entity instanceof net.minecraft.entity.monster.EntityGolem)
/*     */     {
/* 366 */       return false; } 
/* 367 */     return (entity != mc.field_71439_g);
/*     */   }
/*     */   
/*     */   private Vector3d vectorRender2D(int scaleFactor, double x, double y, double z) {
/* 371 */     float xPos = (float)x;
/* 372 */     float yPos = (float)y;
/* 373 */     float zPos = (float)z;
/* 374 */     IntBuffer viewport = GLAllocation.func_74527_f(16);
/* 375 */     FloatBuffer modelview = GLAllocation.func_74529_h(16);
/* 376 */     FloatBuffer projection = GLAllocation.func_74529_h(16);
/* 377 */     FloatBuffer vector = GLAllocation.func_74529_h(4);
/* 378 */     GL11.glGetFloat(2982, modelview);
/* 379 */     GL11.glGetFloat(2983, projection);
/* 380 */     GL11.glGetInteger(2978, viewport);
/* 381 */     if (GLU.gluProject(xPos, yPos, zPos, modelview, projection, viewport, vector))
/* 382 */       return new Vector3d((vector.get(0) / scaleFactor), ((Display.getHeight() - vector.get(1)) / scaleFactor), vector.get(2)); 
/* 383 */     return null;
/*     */   }
/*     */   
/* 386 */   public enum healcolorModeEn { Custom, Astolfo, Health, Rainbow, Client; }
/*     */ 
/*     */   
/*     */   public enum colorModeEn
/*     */   {
/* 391 */     Custom, Astolfo, Rainbow, Client;
/*     */   }
/*     */   
/*     */   public enum espModeEn
/*     */   {
/* 396 */     Flat, Box;
/*     */   }
/*     */   
/*     */   public enum rectModeEn {
/* 400 */     Default, Smooth;
/*     */   }
/*     */   
/*     */   public enum csgoModeEn {
/* 404 */     Box, Corner;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\EntityESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */