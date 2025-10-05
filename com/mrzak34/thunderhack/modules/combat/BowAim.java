/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.render.PaletteHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderHelper;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BowAim
/*     */   extends Module
/*     */ {
/*  25 */   private final Setting<Float> range = register(new Setting("Range", Float.valueOf(60.0F), Float.valueOf(0.0F), Float.valueOf(200.0F)));
/*  26 */   private final Setting<Float> fov = register(new Setting("fov", Float.valueOf(60.0F), Float.valueOf(0.0F), Float.valueOf(180.0F)));
/*     */   Entity target;
/*  28 */   private final Setting<Boolean> ignoreWalls = register(new Setting("IgnoreWalls", Boolean.valueOf(false)));
/*  29 */   private final Setting<Boolean> noVertical = register(new Setting("NoVertical", Boolean.valueOf(false)));
/*     */   
/*     */   private double sideMultiplier;
/*     */   
/*     */   public BowAim() {
/*  34 */     super("AimBot", "AimBot", Module.Category.COMBAT);
/*     */   }
/*     */   private double upMultiplier; private Vec3d predict;
/*     */   @SubscribeEvent
/*     */   public void onMotionUpdate(EventPostSync event) {
/*  39 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemBow && mc.field_71439_g.func_184587_cr() && mc.field_71439_g
/*  40 */       .func_184612_cw() > 0) {
/*  41 */       this.target = (Entity)findTarget();
/*     */       
/*  43 */       if (this.target == null) {
/*     */         return;
/*     */       }
/*  46 */       double xPos = this.target.field_70165_t;
/*  47 */       double yPos = this.target.field_70163_u;
/*  48 */       double zPos = this.target.field_70161_v;
/*  49 */       this.sideMultiplier = (mc.field_71439_g.func_70032_d(this.target) / mc.field_71439_g.func_70032_d(this.target) / 2.0F * 5.0F);
/*  50 */       this.upMultiplier = (mc.field_71439_g.func_70032_d(this.target) / 320.0F) * 1.1D;
/*     */ 
/*     */       
/*  53 */       this.predict = new Vec3d(xPos, yPos + this.upMultiplier, zPos);
/*     */ 
/*     */       
/*  56 */       float[] rotation = lookAtPredict(this.predict);
/*     */       
/*  58 */       mc.field_71439_g.field_70177_z = rotation[0];
/*  59 */       if (((Boolean)this.noVertical.getValue()).booleanValue())
/*  60 */         mc.field_71439_g.field_70125_A = rotation[1]; 
/*  61 */       this.target = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float[] lookAtPredict(Vec3d vec) {
/*  67 */     double diffX = vec.field_72450_a + 0.5D - mc.field_71439_g.field_70165_t;
/*  68 */     double diffY = vec.field_72448_b + 0.5D - mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e();
/*  69 */     double diffZ = vec.field_72449_c + 0.5D - mc.field_71439_g.field_70161_v;
/*  70 */     double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
/*  71 */     float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
/*  72 */     float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
/*  73 */     return new float[] { mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - mc.field_71439_g.field_70177_z), mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - mc.field_71439_g.field_70125_A) };
/*     */   }
/*     */   
/*     */   public EntityPlayer findTarget() {
/*  77 */     EntityPlayer target = null;
/*  78 */     double distance = (((Float)this.range.getValue()).floatValue() * ((Float)this.range.getValue()).floatValue());
/*  79 */     for (EntityPlayer entity : mc.field_71441_e.field_73010_i) {
/*  80 */       if (entity == mc.field_71439_g) {
/*     */         continue;
/*     */       }
/*  83 */       if (Thunderhack.friendManager.isFriend(entity)) {
/*     */         continue;
/*     */       }
/*  86 */       if (EntityUtil.canEntityBeSeen((Entity)entity) && !((Boolean)this.ignoreWalls.getValue()).booleanValue()) {
/*     */         continue;
/*     */       }
/*     */       
/*  90 */       if (!EntityUtil.canSeeEntityAtFov((Entity)entity, ((Float)this.fov.getValue()).floatValue())) {
/*     */         continue;
/*     */       }
/*  93 */       if (mc.field_71439_g.func_70068_e((Entity)entity) <= distance) {
/*  94 */         target = entity;
/*  95 */         distance = mc.field_71439_g.func_70068_e((Entity)entity);
/*     */       } 
/*     */     } 
/*  98 */     return target;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/* 104 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemBow && this.target != null)
/* 105 */       RenderHelper.drawEntityBox(this.target, new Color(PaletteHelper.astolfo(false, 12).getRGB()), new Color(PaletteHelper.astolfo(false, 12).getRGB()), false, 255.0F); 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\BowAim.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */