/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.render.PearlESP;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({EntityThrowable.class})
/*     */ public abstract class MixinEntityThrowable
/*     */   extends Entity
/*     */ {
/*     */   @Shadow
/*     */   public Entity field_184539_c;
/*     */   public boolean predictTick;
/*     */   public boolean breaked;
/*     */   @Shadow
/*     */   protected EntityLivingBase field_70192_c;
/*     */   @Shadow
/*     */   protected boolean field_174854_a;
/*     */   double lastTickPosX2;
/*     */   double lastTickPosY2;
/*     */   double lastTickPosZ2;
/*     */   double posX2;
/*     */   double posY2;
/*     */   double posZ2;
/*     */   double prevPosX2;
/*     */   double prevPosY2;
/*     */   double prevPosZ2;
/*     */   double motionX2;
/*     */   double motionY2;
/*     */   double motionZ2;
/*     */   int ticksInGround2;
/*     */   int ticksInAir2;
/*     */   int ignoreTime2;
/*     */   int ticksExisted2;
/*     */   float prevRotationYaw2;
/*     */   float prevRotationPitch2;
/*     */   Entity ignoreEntity2;
/*     */   boolean onGround2;
/*     */   boolean inGround2;
/*     */   float rotationYaw2;
/*     */   float rotationPitch2;
/*     */   @Shadow
/*     */   private int field_70194_h;
/*     */   @Shadow
/*     */   private int field_70195_i;
/*     */   @Shadow
/*     */   private int field_184540_av;
/*     */   
/*     */   public MixinEntityThrowable(World worldIn) {
/*  69 */     super(worldIn);
/*     */   }
/*     */   
/*     */   @Inject(method = {"setVelocity"}, at = {@At("RETURN")})
/*     */   private void setVelocityHook(double x, double y, double z, CallbackInfo ci) {
/*  74 */     if (!this.breaked && ((PearlESP)Thunderhack.moduleManager.getModuleByClass(PearlESP.class)).isOn()) {
/*  75 */       this.lastTickPosX2 = this.field_70142_S;
/*  76 */       this.lastTickPosY2 = this.field_70137_T;
/*  77 */       this.lastTickPosZ2 = this.field_70136_U;
/*  78 */       this.posX2 = this.field_70165_t;
/*  79 */       this.posY2 = this.field_70163_u;
/*  80 */       this.posZ2 = this.field_70161_v;
/*  81 */       this.prevPosX2 = this.field_70169_q;
/*  82 */       this.prevPosY2 = this.field_70167_r;
/*  83 */       this.prevPosZ2 = this.field_70166_s;
/*  84 */       this.motionX2 = this.field_70159_w;
/*  85 */       this.motionY2 = this.field_70181_x;
/*  86 */       this.motionZ2 = this.field_70179_y;
/*  87 */       this.ticksInGround2 = this.field_70194_h;
/*  88 */       this.ticksInAir2 = this.field_70195_i;
/*  89 */       this.ignoreTime2 = this.field_184540_av;
/*  90 */       this.ticksExisted2 = this.field_70173_aa;
/*  91 */       this.prevRotationYaw2 = this.field_70126_B;
/*  92 */       this.prevRotationPitch2 = this.field_70127_C;
/*  93 */       this.ignoreEntity2 = this.field_184539_c;
/*     */       
/*  95 */       this.onGround2 = this.field_70122_E;
/*  96 */       this.inGround2 = this.field_174854_a;
/*     */       
/*  98 */       this.rotationYaw2 = this.field_70177_z;
/*  99 */       this.rotationPitch2 = this.field_70125_A;
/* 100 */       buildPositions(200);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildPositions(int ticks) {
/* 107 */     PearlESP tm = (PearlESP)Thunderhack.moduleManager.getModuleByClass(PearlESP.class);
/* 108 */     int i = 0;
/* 109 */     double prevLastPosX = this.lastTickPosX2;
/* 110 */     double prevLastPosY = this.lastTickPosY2;
/* 111 */     double prevLastPosZ = this.lastTickPosZ2;
/* 112 */     double prevprevPosX = this.prevPosX2;
/* 113 */     double prevprevPosY = this.prevPosY2;
/* 114 */     double prevprevPosZ = this.prevPosZ2;
/* 115 */     double prevPosX = this.posX2;
/* 116 */     double prevPosY = this.posY2;
/* 117 */     double prevPosZ = this.posZ2;
/* 118 */     double prevMotionX = this.motionX2;
/* 119 */     double prevMotionY = this.motionY2;
/* 120 */     double prevMotionZ = this.motionZ2;
/* 121 */     boolean prevOnGround = this.onGround2;
/* 122 */     boolean prevInGround = this.inGround2;
/* 123 */     int prevTicksInGround = this.ticksInGround2;
/* 124 */     int prevTicksInAir = this.ticksInAir2;
/* 125 */     int prevIgnoreTime = this.ignoreTime2;
/* 126 */     int prevTicksExisted = this.ticksExisted2;
/* 127 */     float prevPrevYaw = this.prevRotationYaw2;
/* 128 */     float prevPrevPitch = this.prevRotationPitch2;
/* 129 */     Entity prevIgnoreEntity = this.ignoreEntity2;
/* 130 */     this.predictTick = true;
/* 131 */     if (tm.entAndTrail.get(Util.mc.field_71441_e.func_73045_a(func_145782_y())) != null) {
/* 132 */       ((List)tm.entAndTrail.get(Util.mc.field_71441_e.func_73045_a(func_145782_y()))).clear();
/*     */     }
/*     */     
/* 135 */     List<PearlESP.PredictedPosition> trails22 = new ArrayList<>();
/* 136 */     tm.entAndTrail.putIfAbsent(Util.mc.field_71441_e.func_73045_a(func_145782_y()), trails22);
/*     */ 
/*     */     
/* 139 */     while (i < ticks) {
/* 140 */       onUpdateFake();
/* 141 */       PearlESP.PredictedPosition pos = new PearlESP.PredictedPosition();
/* 142 */       pos.pos = getFakePosition();
/* 143 */       pos.tick = i;
/* 144 */       pos.color = new Color(-1);
/*     */       
/* 146 */       ((List<PearlESP.PredictedPosition>)tm.entAndTrail.get(Util.mc.field_71441_e.func_73045_a(func_145782_y()))).add(pos);
/* 147 */       if (i == 0) {
/* 148 */         this.breaked = false;
/*     */       }
/* 150 */       if (this.breaked) {
/*     */         break;
/*     */       }
/* 153 */       i++;
/*     */     } 
/* 155 */     this.prevRotationYaw2 = prevPrevYaw;
/* 156 */     this.prevRotationPitch2 = prevPrevPitch;
/* 157 */     this.rotationYaw2 = this.prevRotationYaw2;
/* 158 */     this.rotationPitch2 = this.prevRotationPitch2;
/* 159 */     this.predictTick = false;
/* 160 */     this.lastTickPosX2 = prevLastPosX;
/* 161 */     this.lastTickPosY2 = prevLastPosY;
/* 162 */     this.lastTickPosZ2 = prevLastPosZ;
/* 163 */     this.prevPosX2 = prevprevPosX;
/* 164 */     this.prevPosY2 = prevprevPosY;
/* 165 */     this.prevPosZ2 = prevprevPosZ;
/* 166 */     this.posX2 = prevPosX;
/* 167 */     this.posY2 = prevPosY;
/* 168 */     this.posZ2 = prevPosZ;
/* 169 */     this.motionX2 = prevMotionX;
/* 170 */     this.motionY2 = prevMotionY;
/* 171 */     this.motionZ2 = prevMotionZ;
/* 172 */     this.onGround2 = prevOnGround;
/* 173 */     this.inGround2 = prevInGround;
/* 174 */     this.ticksInGround2 = prevTicksInGround;
/* 175 */     this.ticksInAir2 = prevTicksInAir;
/* 176 */     this.ignoreTime2 = prevIgnoreTime;
/* 177 */     this.ticksExisted2 = prevTicksExisted;
/* 178 */     this.ignoreEntity2 = prevIgnoreEntity;
/*     */   }
/*     */   
/*     */   private Vec3d getFakePosition() {
/* 182 */     return new Vec3d(this.posX2, this.posY2, this.posZ2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateFake() {
/* 187 */     this.lastTickPosX2 = this.posX2;
/* 188 */     this.lastTickPosY2 = this.posY2;
/* 189 */     this.lastTickPosZ2 = this.posZ2;
/* 190 */     if (this.inGround2) {
/* 191 */       this.inGround2 = false;
/* 192 */       this.motionX2 *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 193 */       this.motionY2 *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 194 */       this.motionZ2 *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 195 */       this.ticksInGround2 = 0;
/* 196 */       this.ticksInAir2 = 0;
/*     */     } else {
/* 198 */       this.ticksInAir2++;
/*     */     } 
/*     */ 
/*     */     
/* 202 */     Vec3d vec3d = new Vec3d(this.posX2, this.posY2, this.posZ2);
/* 203 */     Vec3d vec3d1 = new Vec3d(this.posX2 + this.motionX2, this.posY2 + this.motionY2, this.posZ2 + this.motionZ2);
/* 204 */     RayTraceResult raytraceresult = this.field_70170_p.func_72933_a(vec3d, vec3d1);
/* 205 */     vec3d = new Vec3d(this.posX2, this.posY2, this.posZ2);
/* 206 */     vec3d1 = new Vec3d(this.posX2 + this.motionX2, this.posY2 + this.motionY2, this.posZ2 + this.motionZ2);
/* 207 */     if (raytraceresult != null) {
/* 208 */       vec3d1 = new Vec3d(raytraceresult.field_72307_f.field_72450_a, raytraceresult.field_72307_f.field_72448_b, raytraceresult.field_72307_f.field_72449_c);
/*     */     }
/*     */     
/* 211 */     Entity entity = null;
/* 212 */     List<Entity> list = this.field_70170_p.func_72839_b(this, func_174813_aQ().func_72321_a(this.motionX2, this.motionY2, this.motionZ2).func_186662_g(1.0D));
/* 213 */     double d0 = 0.0D;
/* 214 */     boolean flag = false;
/*     */     
/* 216 */     for (Entity entity1 : list) {
/* 217 */       if (entity1.func_70067_L()) {
/* 218 */         if (entity1 == this.ignoreEntity2) {
/* 219 */           flag = true; continue;
/* 220 */         }  if (this.field_70192_c != null && this.field_70173_aa < 2 && this.ignoreEntity2 == null) {
/* 221 */           this.ignoreEntity2 = entity1;
/* 222 */           flag = true; continue;
/*     */         } 
/* 224 */         flag = false;
/* 225 */         AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_186662_g(0.30000001192092896D);
/* 226 */         RayTraceResult raytraceresult1 = axisalignedbb.func_72327_a(vec3d, vec3d1);
/* 227 */         if (raytraceresult1 != null) {
/* 228 */           double d1 = vec3d.func_72436_e(raytraceresult1.field_72307_f);
/* 229 */           if (d1 < d0 || d0 == 0.0D) {
/* 230 */             entity = entity1;
/* 231 */             d0 = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 238 */     if (this.ignoreEntity2 != null) {
/* 239 */       if (flag) {
/* 240 */         this.ignoreTime2 = 2;
/* 241 */       } else if (this.ignoreTime2-- <= 0) {
/* 242 */         this.ignoreEntity2 = null;
/*     */       } 
/*     */     }
/*     */     
/* 246 */     if (entity != null) {
/* 247 */       raytraceresult = new RayTraceResult(entity);
/*     */     }
/*     */     
/* 250 */     if (raytraceresult != null && (
/* 251 */       raytraceresult.field_72313_a != RayTraceResult.Type.BLOCK || this.field_70170_p.func_180495_p(raytraceresult.func_178782_a()).func_177230_c() != Blocks.field_150427_aO))
/*     */     {
/*     */       
/* 254 */       if (this.predictTick)
/*     */       {
/*     */         
/* 257 */         this.breaked = true;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 262 */     this.posX2 += this.motionX2;
/* 263 */     this.posY2 += this.motionY2;
/* 264 */     this.posZ2 += this.motionZ2;
/* 265 */     float f = MathHelper.func_76133_a(this.motionX2 * this.motionX2 + this.motionZ2 * this.motionZ2);
/* 266 */     this.rotationYaw2 = (float)(MathHelper.func_181159_b(this.motionX2, this.motionZ2) * 57.29577951308232D);
/*     */     
/* 268 */     for (this.rotationPitch2 = (float)(MathHelper.func_181159_b(this.motionY2, f) * 57.29577951308232D); this.rotationPitch2 - this.prevRotationPitch2 < -180.0F; this.prevRotationPitch2 -= 360.0F);
/*     */ 
/*     */     
/* 271 */     while (this.rotationPitch2 - this.prevRotationPitch2 >= 180.0F) {
/* 272 */       this.prevRotationPitch2 += 360.0F;
/*     */     }
/*     */     
/* 275 */     while (this.rotationYaw2 - this.prevRotationYaw2 < -180.0F) {
/* 276 */       this.prevRotationYaw2 -= 360.0F;
/*     */     }
/*     */     
/* 279 */     while (this.rotationYaw2 - this.prevRotationYaw2 >= 180.0F) {
/* 280 */       this.prevRotationYaw2 += 360.0F;
/*     */     }
/*     */     
/* 283 */     this.rotationPitch2 = this.prevRotationPitch2 + (this.rotationPitch2 - this.prevRotationPitch2) * 0.2F;
/* 284 */     this.rotationYaw2 = this.prevRotationYaw2 + (this.rotationYaw2 - this.prevRotationYaw2) * 0.2F;
/* 285 */     float f1 = 0.99F;
/* 286 */     float f2 = 0.03F;
/* 287 */     this.motionX2 *= f1;
/* 288 */     this.motionY2 *= f1;
/* 289 */     this.motionZ2 *= f1;
/* 290 */     if (!func_189652_ae())
/* 291 */       this.motionY2 -= f2; 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinEntityThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */