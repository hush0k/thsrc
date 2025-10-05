/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DMGParticles
/*     */   extends Module
/*     */ {
/*  32 */   public final Setting<ColorSetting> color1 = register(new Setting("HealColor", new ColorSetting(3142544)));
/*  33 */   public final Setting<ColorSetting> color2 = register(new Setting("DamageColor", new ColorSetting(15811379)));
/*  34 */   private final Setting<Float> size = register(new Setting("size", Float.valueOf(0.5F), Float.valueOf(0.1F), Float.valueOf(3.0F)));
/*  35 */   private final Setting<Integer> ticks = register(new Setting("ticks", Integer.valueOf(35), Float.valueOf(5.0F), Integer.valueOf(60))); private final HashMap<Integer, Float> healthMap;
/*     */   
/*     */   public DMGParticles() {
/*  38 */     super("DMGParticles", "партиклы урона", Module.Category.RENDER);
/*     */ 
/*     */     
/*  41 */     this.healthMap = new HashMap<>();
/*  42 */     this.particles = new ArrayList<>();
/*     */   }
/*     */   private final ArrayList<Marker> particles;
/*     */   public void onDisable() {
/*  46 */     this.particles.clear();
/*  47 */     this.healthMap.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  52 */     synchronized (this.particles) {
/*  53 */       for (Entity entity : mc.field_71441_e.field_72996_f) {
/*  54 */         if (entity == null || mc.field_71439_g.func_70032_d(entity) > 10.0F || entity.field_70128_L || !(entity instanceof EntityLivingBase))
/*  55 */           continue;  float lastHealth = ((Float)this.healthMap.getOrDefault(Integer.valueOf(entity.func_145782_y()), Float.valueOf(((EntityLivingBase)entity).func_110138_aP()))).floatValue();
/*     */         
/*  57 */         this.healthMap.put(Integer.valueOf(entity.func_145782_y()), Float.valueOf(EntityUtil.getHealth(entity)));
/*  58 */         if (lastHealth == EntityUtil.getHealth(entity))
/*  59 */           continue;  this.particles.add(new Marker(entity, lastHealth - EntityUtil.getHealth(entity), entity.field_70165_t - 0.5D + (new Random(System.currentTimeMillis())).nextInt(5) * 0.1D, (entity.func_174813_aQ()).field_72338_b + ((entity.func_174813_aQ()).field_72337_e - (entity.func_174813_aQ()).field_72338_b) / 2.0D, entity.field_70161_v - 0.5D + (new Random(System.currentTimeMillis() + 1L)).nextInt(5) * 0.1D));
/*     */       } 
/*  61 */       ArrayList<Marker> needRemove = new ArrayList<>();
/*  62 */       for (Marker marker : this.particles) {
/*  63 */         marker.ticks++;
/*  64 */         if (marker.ticks < ((Integer)this.ticks.getValue()).intValue() && !(marker.getEntity()).field_70128_L)
/*  65 */           continue;  needRemove.add(marker);
/*     */       } 
/*  67 */       for (Marker marker : needRemove) {
/*  68 */         this.particles.remove(marker);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  76 */     synchronized (this.particles) {
/*  77 */       for (Marker marker : this.particles) {
/*  78 */         RenderManager renderManager = mc.func_175598_ae();
/*  79 */         double size = (((Float)this.size.getValue()).floatValue() / marker.getScale() * 2.0F) * 0.1D;
/*  80 */         size = MathHelper.func_151237_a(size, 0.03D, ((Float)this.size.getValue()).floatValue());
/*  81 */         double x = marker.posX - ((IRenderManager)renderManager).getRenderPosX();
/*  82 */         double y = marker.posY - ((IRenderManager)renderManager).getRenderPosY();
/*  83 */         double z = marker.posZ - ((IRenderManager)renderManager).getRenderPosZ();
/*  84 */         GlStateManager.func_179094_E();
/*  85 */         GlStateManager.func_179088_q();
/*  86 */         GlStateManager.func_179136_a(1.0F, -1500000.0F);
/*  87 */         GlStateManager.func_179137_b(x, y, z);
/*  88 */         GL11.glEnable(2884);
/*  89 */         GL11.glEnable(3553);
/*  90 */         GL11.glDisable(3042);
/*  91 */         GL11.glEnable(2929);
/*  92 */         GlStateManager.func_179114_b(-renderManager.field_78735_i, 0.0F, 1.0F, 0.0F);
/*  93 */         double textY = (mc.field_71474_y.field_74320_O == 2) ? -1.0D : 1.0D;
/*  94 */         GlStateManager.func_179114_b(renderManager.field_78732_j, (float)textY, 0.0F, 0.0F);
/*  95 */         GlStateManager.func_179139_a(-size, -size, size);
/*  96 */         GL11.glDepthMask(false);
/*  97 */         int color = (marker.getHp() > 0.0F) ? ((ColorSetting)this.color1.getValue()).getColor() : ((ColorSetting)this.color2.getValue()).getColor();
/*  98 */         DecimalFormat decimalFormat = new DecimalFormat("#.#");
/*  99 */         Util.fr.func_175063_a(decimalFormat.format(marker.getHp()), -(mc.field_71466_p.func_78256_a(marker.getHp() + "") / 2.0F), -(mc.field_71466_p.field_78288_b - 1), color);
/* 100 */         GlStateManager.func_179084_k();
/* 101 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 102 */         GL11.glDepthMask(true);
/* 103 */         GlStateManager.func_179136_a(1.0F, 1500000.0F);
/* 104 */         GlStateManager.func_179113_r();
/* 105 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class Marker {
/*     */     private final Entity entity;
/*     */     private final float hp;
/*     */     private final double posX;
/*     */     private final double posY;
/*     */     private final double posZ;
/* 116 */     private int ticks = 0;
/*     */     
/*     */     public Marker(Entity entity, float hp, double posX, double posY, double posZ) {
/* 119 */       this.entity = entity;
/* 120 */       this.hp = hp;
/* 121 */       this.posX = posX;
/* 122 */       this.posY = posY;
/* 123 */       this.posZ = posZ;
/*     */     }
/*     */     
/*     */     public float getScale() {
/* 127 */       return (float)RenderUtil.interpolate(this.ticks, (this.ticks - 1), Module.mc.func_184121_ak());
/*     */     }
/*     */     
/*     */     public Entity getEntity() {
/* 131 */       return this.entity;
/*     */     }
/*     */     
/*     */     public float getHp() {
/* 135 */       return -this.hp;
/*     */     }
/*     */     
/*     */     public double getPosX() {
/* 139 */       return this.posX;
/*     */     }
/*     */     
/*     */     public double getPosY() {
/* 143 */       return this.posY;
/*     */     }
/*     */     
/*     */     public double getPosZ() {
/* 147 */       return this.posZ;
/*     */     }
/*     */     
/*     */     public int getTicks() {
/* 151 */       return this.ticks;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\DMGParticles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */