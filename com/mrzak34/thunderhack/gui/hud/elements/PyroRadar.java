/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityRenderer;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PyroRadar
/*     */   extends Module
/*     */ {
/*  27 */   public Setting<Float> scale = register(new Setting("Scale", Float.valueOf(0.2F), Float.valueOf(0.0F), Integer.valueOf(1)));
/*  28 */   public Setting<Float> distance = register(new Setting("distance", Float.valueOf(0.2F), Float.valueOf(0.0F), Float.valueOf(2.0F)));
/*  29 */   public Setting<Boolean> unlockTilt = register(new Setting("Unlock Tilt", Boolean.valueOf(false)));
/*  30 */   public Setting<Integer> tilt = register(new Setting("Tilt", Integer.valueOf(50), Integer.valueOf(-90), Integer.valueOf(90)));
/*  31 */   public Setting<Boolean> items = register(new Setting("items", Boolean.valueOf(false)));
/*  32 */   public Setting<Boolean> players = register(new Setting("players", Boolean.valueOf(false)));
/*  33 */   public Setting<Boolean> hidefrustum = register(new Setting("HideInFrustrum", Boolean.valueOf(false)));
/*  34 */   public Setting<Boolean> other = register(new Setting("other", Boolean.valueOf(false)));
/*  35 */   public Setting<Boolean> bosses = register(new Setting("bosses", Boolean.valueOf(false)));
/*  36 */   public Setting<Boolean> hostiles = register(new Setting("hostiles", Boolean.valueOf(false)));
/*  37 */   public Setting<Boolean> friends = register(new Setting("friends", Boolean.valueOf(false)));
/*     */   
/*  39 */   public final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  40 */   public final Setting<ColorSetting> fcolor = register(new Setting("FriendColor", new ColorSetting(-2013200640)));
/*     */   
/*     */   public PyroRadar() {
/*  43 */     super("PyroRadar", "радар из пайро", Module.Category.HUD);
/*     */ 
/*     */     
/*  46 */     this.entityList = new CopyOnWriteArrayList<>();
/*     */   }
/*     */   private CopyOnWriteArrayList<Entity> entityList;
/*     */   public void onUpdate() {
/*  50 */     this.entityList.clear();
/*  51 */     this.entityList.addAll(mc.field_71441_e.field_72996_f);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  56 */     for (Entity ent : this.entityList) {
/*  57 */       if (ent == mc.field_71439_g || ((
/*  58 */         (Boolean)this.hidefrustum.getValue()).booleanValue() && 
/*  59 */         RenderUtil.camera.func_78546_a(ent.func_174813_aQ())))
/*     */         continue; 
/*  61 */       if (ent instanceof EntityPlayer) {
/*  62 */         if (((Boolean)this.friends.getValue()).booleanValue() && Thunderhack.friendManager.isFriend((EntityPlayer)ent)) {
/*  63 */           drawArrow(ent, ((ColorSetting)this.fcolor.getValue()).getColorObject());
/*     */           continue;
/*     */         } 
/*  66 */         if (!((Boolean)this.players.getValue()).booleanValue() && Thunderhack.friendManager.isFriend((EntityPlayer)ent))
/*  67 */           continue;  drawArrow(ent, ((ColorSetting)this.color.getValue()).getColorObject());
/*     */         continue;
/*     */       } 
/*  70 */       if (ent instanceof net.minecraft.entity.boss.EntityWither) {
/*  71 */         if (!((Boolean)this.bosses.getValue()).booleanValue())
/*  72 */           continue;  drawArrow(ent, ((ColorSetting)this.color.getValue()).getColorObject());
/*     */         continue;
/*     */       } 
/*  75 */       if (ent instanceof net.minecraft.entity.boss.EntityDragon) {
/*  76 */         if (!((Boolean)this.bosses.getValue()).booleanValue())
/*  77 */           continue;  drawArrow(ent, ((ColorSetting)this.color.getValue()).getColorObject());
/*     */         continue;
/*     */       } 
/*  80 */       if (ent.isCreatureType(EnumCreatureType.MONSTER, false)) {
/*  81 */         if (!((Boolean)this.hostiles.getValue()).booleanValue())
/*  82 */           continue;  drawArrow(ent, ((ColorSetting)this.color.getValue()).getColorObject());
/*     */         continue;
/*     */       } 
/*  85 */       if (ent instanceof net.minecraft.entity.item.EntityItem) {
/*  86 */         if (!((Boolean)this.items.getValue()).booleanValue())
/*  87 */           continue;  drawArrow(ent, ((ColorSetting)this.color.getValue()).getColorObject());
/*     */         continue;
/*     */       } 
/*  90 */       if (!((Boolean)this.other.getValue()).booleanValue())
/*  91 */         continue;  drawArrow(ent, ((ColorSetting)this.color.getValue()).getColorObject());
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getYaw(Entity entity) {
/*  96 */     double delta_x = RenderUtil.interpolate(entity.field_70165_t, entity.field_70142_S) - RenderUtil.interpolate(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70142_S);
/*  97 */     double delta_z = RenderUtil.interpolate(entity.field_70161_v, entity.field_70136_U) - RenderUtil.interpolate(mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70136_U);
/*  98 */     return MathHelper.func_76142_g((float)Math.toDegrees(MathHelper.func_181159_b(delta_x, delta_z)) + 180.0F);
/*     */   }
/*     */   
/*     */   public void arrow(float f, float f2, float f3, float f4) {
/* 102 */     GlStateManager.func_187447_r(6);
/* 103 */     GlStateManager.func_187435_e(f, f2, f3);
/* 104 */     GlStateManager.func_187435_e(f + 0.1F * f4, f2, f3 - 0.2F * f4);
/* 105 */     GlStateManager.func_187435_e(f, f2, f3 - 0.12F * f4);
/* 106 */     GlStateManager.func_187435_e(f - 0.1F * f4, f2, f3 - 0.2F * f4);
/* 107 */     GlStateManager.func_187437_J();
/*     */   }
/*     */   
/*     */   public void drawArrow(Entity ent, Color color) {
/* 111 */     if (mc.field_71460_t != null) {
/*     */       
/* 113 */       Vec3d var14 = (new Vec3d(0.0D, 0.0D, 1.0D)).func_178785_b((float)Math.toRadians((getYaw(ent) + mc.field_71439_g.field_70177_z))).func_178789_a(3.1415927F);
/*     */       
/* 115 */       GlStateManager.func_179112_b(770, 771);
/* 116 */       GlStateManager.func_179090_x();
/* 117 */       GlStateManager.func_179132_a(false);
/* 118 */       GlStateManager.func_179097_i();
/* 119 */       GlStateManager.func_179131_c(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/* 120 */       GlStateManager.func_179140_f();
/* 121 */       GlStateManager.func_179096_D();
/*     */       
/* 123 */       ((IEntityRenderer)mc.field_71460_t).orientCam(mc.func_184121_ak());
/* 124 */       GlStateManager.func_179109_b(0.0F, mc.field_71439_g.func_70047_e(), 0.0F);
/* 125 */       GlStateManager.func_179114_b(-mc.field_71439_g.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 126 */       GlStateManager.func_179114_b(mc.field_71439_g.field_70125_A, 1.0F, 0.0F, 0.0F);
/* 127 */       GlStateManager.func_179109_b(0.0F, 0.0F, 1.0F);
/* 128 */       float tilt_value = ((Integer)this.tilt.getValue()).intValue();
/* 129 */       if (((Boolean)this.unlockTilt.getValue()).booleanValue() && 
/* 130 */         90.0F - mc.field_71439_g.field_70125_A < tilt_value) {
/* 131 */         tilt_value = 90.0F - mc.field_71439_g.field_70125_A;
/*     */       }
/* 133 */       GlStateManager.func_179114_b(tilt_value, 1.0F, 0.0F, 0.0F);
/* 134 */       GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
/* 135 */       GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
/* 136 */       GlStateManager.func_179109_b(0.0F, 0.0F, 1.0F);
/* 137 */       GlStateManager.func_179114_b(getYaw(ent) + mc.field_71439_g.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 138 */       GlStateManager.func_179109_b(0.0F, 0.0F, ((Float)this.distance.getValue()).floatValue() * 0.2F);
/* 139 */       arrow((float)var14.field_72450_a, (float)var14.field_72448_b, (float)var14.field_72449_c, ((Float)this.scale.getValue()).floatValue());
/*     */       
/* 141 */       GlStateManager.func_179098_w();
/* 142 */       GlStateManager.func_179132_a(true);
/* 143 */       GlStateManager.func_179126_j();
/* 144 */       GlStateManager.func_179145_e();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\PyroRadar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */