/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MotionTracker
/*     */   extends EntityPlayerNoInterp
/*     */ {
/*     */   public double extraPosX;
/*     */   public double extraPosY;
/*     */   public double extraPosZ;
/*     */   public double lastExtraPosX;
/*     */   public double lastExtraPosY;
/*     */   public double lastExtraPosZ;
/*     */   public EntityPlayer tracked;
/*     */   public volatile boolean active;
/*     */   public boolean shrinkPush;
/*     */   public boolean gravity;
/*  24 */   public double gravityFactor = 1.0D;
/*  25 */   public double yPlusFactor = 1.0D;
/*  26 */   public double yMinusFactor = 1.0D;
/*     */   public int ticks;
/*     */   
/*     */   public MotionTracker(World worldIn, EntityPlayer from) {
/*  30 */     super(worldIn, new GameProfile(from.func_146103_bH().getId(), "Motion-Tracker-" + from.func_70005_c_()));
/*  31 */     this.tracked = from;
/*  32 */     func_145769_d(from.func_145782_y() * -1);
/*  33 */     func_82149_j((Entity)from);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private MotionTracker(World worldIn) {
/*  39 */     super(worldIn, new GameProfile(UUID.randomUUID(), "Motion-Tracker"));
/*     */   }
/*     */   
/*     */   public void resetMotion() {
/*  43 */     this.field_70159_w = 0.0D;
/*  44 */     this.field_70181_x = 0.0D;
/*  45 */     this.field_70179_y = 0.0D;
/*     */   }
/*     */   
/*     */   public void updateFromTrackedEntity() {
/*  49 */     this.field_70159_w = this.tracked.field_70159_w;
/*  50 */     this.field_70181_x = (this.tracked.field_70181_x > 0.0D) ? (this.tracked.field_70181_x * this.yPlusFactor) : (this.tracked.field_70181_x * this.yMinusFactor);
/*  51 */     this.field_70179_y = this.tracked.field_70179_y;
/*     */     
/*  53 */     if (this.gravity) {
/*  54 */       this.field_70181_x -= 0.03999999910593033D * this.gravityFactor * this.ticks;
/*     */     }
/*     */     
/*  57 */     List<AxisAlignedBB> list1 = this.field_70170_p.func_184144_a((Entity)this, func_174813_aQ().func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y));
/*  58 */     if (this.field_70181_x != 0.0D) {
/*  59 */       int k = 0;
/*     */       
/*  61 */       for (int l = list1.size(); k < l; k++) {
/*  62 */         this.field_70181_x = ((AxisAlignedBB)list1.get(k)).func_72323_b(func_174813_aQ(), this.field_70181_x);
/*     */       }
/*     */       
/*  65 */       func_174826_a(func_174813_aQ().func_72317_d(0.0D, this.field_70181_x, 0.0D));
/*     */     } 
/*     */     
/*  68 */     if (this.field_70159_w != 0.0D) {
/*  69 */       int j5 = 0;
/*     */       
/*  71 */       for (int l5 = list1.size(); j5 < l5; j5++) {
/*  72 */         this.field_70159_w = ((AxisAlignedBB)list1.get(j5)).func_72316_a(func_174813_aQ(), this.field_70159_w);
/*     */       }
/*     */       
/*  75 */       if (this.field_70159_w != 0.0D) {
/*  76 */         func_174826_a(func_174813_aQ().func_72317_d(this.field_70159_w, 0.0D, 0.0D));
/*     */       }
/*     */     } 
/*     */     
/*  80 */     if (this.field_70181_x != 0.0D) {
/*  81 */       int k5 = 0;
/*     */       
/*  83 */       for (int i6 = list1.size(); k5 < i6; k5++) {
/*  84 */         this.field_70179_y = ((AxisAlignedBB)list1.get(k5)).func_72322_c(func_174813_aQ(), this.field_70179_y);
/*     */       }
/*     */       
/*  87 */       if (this.field_70179_y != 0.0D) {
/*  88 */         func_174826_a(func_174813_aQ().func_72317_d(0.0D, 0.0D, this.field_70179_y));
/*     */       }
/*     */     } 
/*     */     
/*  92 */     func_174829_m();
/*  93 */     this.field_70122_E = this.tracked.field_70122_E;
/*  94 */     this.field_70169_q = this.tracked.field_70169_q;
/*  95 */     this.field_70167_r = this.tracked.field_70167_r;
/*  96 */     this.field_70166_s = this.tracked.field_70166_s;
/*  97 */     this.field_70132_H = this.tracked.field_70132_H;
/*  98 */     this.field_70123_F = this.tracked.field_70123_F;
/*  99 */     this.field_70124_G = this.tracked.field_70124_G;
/* 100 */     this.field_191988_bg = this.tracked.field_191988_bg;
/* 101 */     this.field_70702_br = this.tracked.field_70702_br;
/* 102 */     this.field_70701_bs = this.tracked.field_70701_bs;
/* 103 */     this.field_70142_S = this.field_70165_t;
/* 104 */     this.field_70137_T = this.field_70163_u;
/* 105 */     this.field_70136_U = this.field_70161_v;
/* 106 */     this.lastExtraPosX = this.extraPosX;
/* 107 */     this.lastExtraPosY = this.extraPosY;
/* 108 */     this.lastExtraPosZ = this.extraPosZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70636_d() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 123 */     this.field_70128_L = true;
/* 124 */     this.field_70729_aU = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175149_v() {
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_184812_l_() {
/* 134 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\MotionTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */