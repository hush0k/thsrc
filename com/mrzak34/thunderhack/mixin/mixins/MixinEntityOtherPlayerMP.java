/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mrzak34.thunderhack.modules.combat.Aura;
/*     */ import com.mrzak34.thunderhack.modules.render.NoInterp;
/*     */ import com.mrzak34.thunderhack.modules.render.ShiftInterp;
/*     */ import com.mrzak34.thunderhack.util.rotations.ResolverUtil;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({EntityOtherPlayerMP.class})
/*     */ public class MixinEntityOtherPlayerMP
/*     */   extends AbstractClientPlayer
/*     */ {
/*     */   @Shadow
/*     */   private int field_71184_b;
/*     */   @Shadow
/*     */   private double field_71185_c;
/*     */   @Shadow
/*     */   private double field_71182_d;
/*     */   @Shadow
/*     */   private double field_71183_e;
/*     */   @Shadow
/*     */   private double field_71180_f;
/*     */   @Shadow
/*     */   private double field_71181_g;
/*     */   private double serverX;
/*     */   private double serverY;
/*     */   private double serverZ;
/*     */   private double prevServerX;
/*     */   private double prevServerY;
/*     */   private double prevServerZ;
/*     */   
/*     */   public MixinEntityOtherPlayerMP(World worldIn, GameProfile gameProfileIn) {
/*  45 */     super(worldIn, gameProfileIn);
/*     */   }
/*     */   
/*     */   @Inject(method = {"onLivingUpdate"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void onLivingUpdate(CallbackInfo ci) {
/*  50 */     if (NoInterp.getInstance().isEnabled()) {
/*  51 */       ci.cancel();
/*  52 */       onLivingUpdateCustom();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onLivingUpdateCustom() {
/*  57 */     if (this.field_71184_b > 0) {
/*     */       double d0, d1, d2;
/*     */ 
/*     */       
/*  61 */       if (NoInterp.getInstance().isEnabled()) {
/*  62 */         d0 = this.field_70118_ct / 4096.0D;
/*  63 */         d1 = this.field_70117_cu / 4096.0D;
/*  64 */         d2 = this.field_70116_cv / 4096.0D;
/*     */       } else {
/*  66 */         d0 = this.field_70165_t + (this.field_71185_c - this.field_70165_t) / this.field_71184_b;
/*  67 */         d1 = this.field_70163_u + (this.field_71182_d - this.field_70163_u) / this.field_71184_b;
/*  68 */         d2 = this.field_70161_v + (this.field_71183_e - this.field_70161_v) / this.field_71184_b;
/*     */       } 
/*     */       double d3;
/*  71 */       for (d3 = this.field_71180_f - this.field_70177_z; d3 < -180.0D; d3 += 360.0D);
/*     */ 
/*     */       
/*  74 */       while (d3 >= 180.0D) {
/*  75 */         d3 -= 360.0D;
/*     */       }
/*     */       
/*  78 */       this.field_70177_z = (float)(this.field_70177_z + d3 / this.field_71184_b);
/*  79 */       this.field_70125_A = (float)(this.field_70125_A + (this.field_71181_g - this.field_70125_A) / this.field_71184_b);
/*  80 */       this.field_71184_b--;
/*  81 */       func_70107_b(d0, d1, d2);
/*  82 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */     } 
/*     */     
/*  85 */     this.field_71107_bF = this.field_71109_bG;
/*  86 */     func_82168_bl();
/*  87 */     float f1 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  88 */     float f = (float)Math.atan(-this.field_70181_x * 0.20000000298023224D) * 15.0F;
/*  89 */     if (f1 > 0.1F) {
/*  90 */       f1 = 0.1F;
/*     */     }
/*     */     
/*  93 */     if (!this.field_70122_E || func_110143_aJ() <= 0.0F) {
/*  94 */       f1 = 0.0F;
/*     */     }
/*     */     
/*  97 */     if (this.field_70122_E || func_110143_aJ() <= 0.0F) {
/*  98 */       f = 0.0F;
/*     */     }
/* 100 */     this.field_71109_bG += (f1 - this.field_71109_bG) * 0.4F;
/* 101 */     this.field_70726_aT += (f - this.field_70726_aT) * 0.8F;
/* 102 */     this.field_70170_p.field_72984_F.func_76320_a("push");
/* 103 */     func_85033_bc();
/* 104 */     this.field_70170_p.field_72984_F.func_76319_b();
/*     */   }
/*     */   
/*     */   @Inject(method = {"onUpdate"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void prikol(CallbackInfo ci) {
/* 109 */     if (((Boolean)(NoInterp.getInstance()).lowIQ.getValue()).booleanValue() && NoInterp.getInstance().isEnabled()) {
/* 110 */       this.field_71082_cx = 0.0F;
/* 111 */       func_70071_h_();
/* 112 */       this.field_184619_aG = 0.0F;
/* 113 */       this.field_70721_aZ = 0.0F;
/* 114 */       this.field_184618_aE = 0.0F;
/* 115 */       ci.cancel();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"setPositionAndRotationDirect"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport, CallbackInfo ci) {
/* 121 */     this.prevServerX = this.serverX;
/* 122 */     this.prevServerY = this.serverY;
/* 123 */     this.prevServerZ = this.serverZ;
/* 124 */     this.serverX = x;
/* 125 */     this.serverY = y;
/* 126 */     this.serverZ = z;
/* 127 */     if (Aura.target != null && Aura.target == this) {
/* 128 */       ResolverUtil.prevServerX = this.prevServerX;
/* 129 */       ResolverUtil.prevServerY = this.prevServerY;
/* 130 */       ResolverUtil.prevServerZ = this.prevServerZ;
/* 131 */       ResolverUtil.serverX = this.serverX;
/* 132 */       ResolverUtil.serverY = this.serverY;
/* 133 */       ResolverUtil.serverZ = this.serverZ;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"onUpdate"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void prikol2(CallbackInfo ci) {
/* 139 */     if (ShiftInterp.getInstance().isOn()) {
/* 140 */       this.field_71082_cx = 0.0F;
/* 141 */       func_70071_h_();
/*     */       
/* 143 */       if (((Boolean)(ShiftInterp.getInstance()).sleep.getValue()).booleanValue()) {
/* 144 */         this.field_71083_bS = true;
/*     */       } else {
/* 146 */         this.field_71083_bS = false;
/* 147 */         func_70095_a(true);
/*     */       } 
/* 149 */       ci.cancel();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinEntityOtherPlayerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */