/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.render.PearlESP;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
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
/*     */ 
/*     */ @Mixin({EntityArrow.class})
/*     */ public abstract class MixinEntityArrow
/*     */   extends Entity
/*     */ {
/*     */   @Shadow
/*     */   public int field_70249_b;
/*     */   public boolean predictTick;
/*     */   public boolean breaked;
/*     */   @Shadow
/*     */   protected boolean field_70254_i;
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
/*     */   int ticksExisted2;
/*     */   float prevRotationYaw2;
/*     */   float prevRotationPitch2;
/*     */   int arrowShake2;
/*     */   boolean onGround2;
/*     */   boolean inGround2;
/*     */   float rotationYaw2;
/*     */   float rotationPitch2;
/*     */   @Shadow
/*     */   private int field_70252_j;
/*     */   @Shadow
/*     */   private int field_70257_an;
/*     */   
/*     */   public MixinEntityArrow(World worldIn) {
/*  64 */     super(worldIn);
/*     */   }
/*     */   
/*     */   @Inject(method = {"setVelocity"}, at = {@At("RETURN")})
/*     */   private void setVelocityHook(double x, double y, double z, CallbackInfo ci) {
/*  69 */     if (!this.breaked && ((PearlESP)Thunderhack.moduleManager.getModuleByClass(PearlESP.class)).isOn()) {
/*  70 */       this.lastTickPosX2 = this.field_70142_S;
/*  71 */       this.lastTickPosY2 = this.field_70137_T;
/*  72 */       this.lastTickPosZ2 = this.field_70136_U;
/*  73 */       this.posX2 = this.field_70165_t;
/*  74 */       this.posY2 = this.field_70163_u;
/*  75 */       this.posZ2 = this.field_70161_v;
/*  76 */       this.prevPosX2 = this.field_70169_q;
/*  77 */       this.prevPosY2 = this.field_70167_r;
/*  78 */       this.prevPosZ2 = this.field_70166_s;
/*  79 */       this.motionX2 = this.field_70159_w;
/*  80 */       this.motionY2 = this.field_70181_x;
/*  81 */       this.motionZ2 = this.field_70179_y;
/*  82 */       this.ticksInGround2 = this.field_70252_j;
/*  83 */       this.ticksInAir2 = this.field_70257_an;
/*  84 */       this.ticksExisted2 = this.field_70173_aa;
/*  85 */       this.prevRotationYaw2 = this.field_70126_B;
/*  86 */       this.prevRotationPitch2 = this.field_70127_C;
/*  87 */       this.onGround2 = this.field_70122_E;
/*  88 */       this.inGround2 = this.field_70254_i;
/*  89 */       this.arrowShake2 = this.field_70249_b;
/*  90 */       this.rotationYaw2 = this.field_70177_z;
/*  91 */       this.rotationPitch2 = this.field_70125_A;
/*  92 */       buildPositions(200);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"onUpdate"}, at = {@At("HEAD")})
/*     */   private void onUpdate(CallbackInfo ci) {
/*  98 */     if (this.field_70159_w == 0.0D && this.field_70181_x == 0.0D && this.field_70179_y == 0.0D && ((PearlESP)Thunderhack.moduleManager.getModuleByClass(PearlESP.class)).isOn() && 
/*  99 */       ((PearlESP)Thunderhack.moduleManager.getModuleByClass(PearlESP.class)).entAndTrail.get(Util.mc.field_71441_e.func_73045_a(func_145782_y())) != null) {
/* 100 */       ((List)((PearlESP)Thunderhack.moduleManager.getModuleByClass(PearlESP.class)).entAndTrail.get(Util.mc.field_71441_e.func_73045_a(func_145782_y()))).clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildPositions(int ticks) {
/* 107 */     PearlESP tm = (PearlESP)Thunderhack.moduleManager.getModuleByClass(PearlESP.class);
/*     */     
/* 109 */     int i = 0;
/* 110 */     double prevLastPosX = this.lastTickPosX2;
/* 111 */     double prevLastPosY = this.lastTickPosY2;
/* 112 */     double prevLastPosZ = this.lastTickPosZ2;
/* 113 */     double prevPrevPosX = this.prevPosX2;
/* 114 */     double prevPrevPosY = this.prevPosY2;
/* 115 */     double prevPrevPosZ = this.prevPosZ2;
/* 116 */     double prevPosX = this.posX2;
/* 117 */     double prevPosY = this.posY2;
/* 118 */     double prevPosZ = this.posZ2;
/* 119 */     double prevMotionX = this.motionX2;
/* 120 */     double prevMotionY = this.motionY2;
/* 121 */     double prevMotionZ = this.motionZ2;
/* 122 */     boolean prevOnGround = this.onGround2;
/* 123 */     boolean prevInGround = this.inGround2;
/* 124 */     int prevTicksInGround = this.ticksInGround2;
/* 125 */     int prevTicksInAir = this.ticksInAir2;
/* 126 */     int prevTicksExisted = this.ticksExisted2;
/* 127 */     float prevYaw = this.rotationYaw2;
/* 128 */     float prevPitch = this.rotationPitch2;
/* 129 */     float prevPrevYaw = this.prevRotationYaw2;
/* 130 */     float prevPrevPitch = this.prevRotationPitch2;
/* 131 */     int prevArrowShake = this.arrowShake2;
/* 132 */     this.predictTick = true;
/*     */     
/* 134 */     if (tm.entAndTrail.get(Util.mc.field_71441_e.func_73045_a(func_145782_y())) != null) {
/* 135 */       ((List)tm.entAndTrail.get(Util.mc.field_71441_e.func_73045_a(func_145782_y()))).clear();
/*     */     }
/*     */     
/* 138 */     List<PearlESP.PredictedPosition> trails22 = new ArrayList<>();
/* 139 */     tm.entAndTrail.putIfAbsent(Util.mc.field_71441_e.func_73045_a(func_145782_y()), trails22);
/*     */ 
/*     */     
/* 142 */     while (i < ticks) {
/* 143 */       onUpdateFake();
/* 144 */       PearlESP.PredictedPosition pos = new PearlESP.PredictedPosition();
/* 145 */       pos.pos = getFakePosition();
/* 146 */       pos.tick = i;
/* 147 */       pos.color = new Color(-1);
/* 148 */       ((List<PearlESP.PredictedPosition>)tm.entAndTrail.get(Util.mc.field_71441_e.func_73045_a(func_145782_y()))).add(pos);
/* 149 */       if (i == 0) {
/* 150 */         this.breaked = false;
/*     */       }
/* 152 */       if (this.breaked) {
/*     */         break;
/*     */       }
/* 155 */       i++;
/*     */     } 
/* 157 */     this.arrowShake2 = prevArrowShake;
/* 158 */     this.prevRotationYaw2 = prevPrevYaw;
/* 159 */     this.prevRotationPitch2 = prevPrevPitch;
/* 160 */     this.rotationYaw2 = this.field_70126_B;
/* 161 */     this.rotationPitch2 = this.field_70127_C;
/* 162 */     this.predictTick = false;
/* 163 */     this.lastTickPosX2 = prevLastPosX;
/* 164 */     this.lastTickPosY2 = prevLastPosY;
/* 165 */     this.lastTickPosZ2 = prevLastPosZ;
/* 166 */     this.prevPosX2 = prevPrevPosX;
/* 167 */     this.prevPosY2 = prevPrevPosY;
/* 168 */     this.prevPosZ2 = prevPrevPosZ;
/* 169 */     this.posX2 = prevPosX;
/* 170 */     this.posY2 = prevPosY;
/* 171 */     this.posZ2 = prevPosZ;
/* 172 */     this.motionX2 = prevMotionX;
/* 173 */     this.motionY2 = prevMotionY;
/* 174 */     this.motionZ2 = prevMotionZ;
/* 175 */     this.onGround2 = prevOnGround;
/* 176 */     this.inGround2 = prevInGround;
/* 177 */     this.ticksInGround2 = prevTicksInGround;
/* 178 */     this.ticksInAir2 = prevTicksInAir;
/* 179 */     this.ticksExisted2 = prevTicksExisted;
/*     */   }
/*     */   
/*     */   public void onUpdateFake() {
/* 183 */     if (this.prevRotationPitch2 == 0.0F && this.prevRotationYaw2 == 0.0F) {
/* 184 */       float f = MathHelper.func_76133_a(this.motionX2 * this.motionX2 + this.motionZ2 * this.motionZ2);
/* 185 */       this.rotationYaw2 = (float)(MathHelper.func_181159_b(this.motionX2, this.motionZ2) * 57.29577951308232D);
/* 186 */       this.rotationPitch2 = (float)(MathHelper.func_181159_b(this.motionY2, f) * 57.29577951308232D);
/* 187 */       this.prevRotationYaw2 = this.rotationYaw2;
/* 188 */       this.prevRotationPitch2 = this.rotationPitch2;
/*     */     } 
/*     */     
/* 191 */     if (this.arrowShake2 > 0) {
/* 192 */       this.arrowShake2--;
/*     */     }
/*     */     
/* 195 */     if (this.inGround2) {
/* 196 */       if (!this.field_70170_p.func_184143_b(func_174813_aQ().func_186662_g(0.05D))) {
/* 197 */         this.field_70254_i = false;
/* 198 */         this.motionX2 *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 199 */         this.motionY2 *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 200 */         this.motionZ2 *= (this.field_70146_Z.nextFloat() * 0.2F);
/* 201 */         this.ticksInGround2 = 0;
/* 202 */         this.ticksInAir2 = 0;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 207 */       Vec3d vec3d = new Vec3d(this.posX2, this.posY2, this.posZ2);
/* 208 */       Vec3d vec3d1 = new Vec3d(this.posX2 + this.motionX2, this.posY2 + this.motionY2, this.posZ2 + this.motionZ2);
/* 209 */       RayTraceResult raytraceresult = this.field_70170_p.func_72933_a(vec3d, vec3d1);
/* 210 */       vec3d = new Vec3d(this.posX2, this.posY2, this.posZ2);
/* 211 */       vec3d1 = new Vec3d(this.posX2 + this.motionX2, this.posY2 + this.motionY2, this.posZ2 + this.motionZ2);
/* 212 */       if (raytraceresult != null) {
/* 213 */         vec3d1 = new Vec3d(raytraceresult.field_72307_f.field_72450_a, raytraceresult.field_72307_f.field_72448_b, raytraceresult.field_72307_f.field_72449_c);
/*     */       }
/*     */       
/* 216 */       Entity entity = null;
/* 217 */       List<Entity> list = this.field_70170_p.func_72839_b(this, func_174813_aQ().func_72321_a(this.motionX2, this.motionY2, this.motionZ2).func_186662_g(1.0D));
/* 218 */       double d0 = 0.0D;
/* 219 */       boolean flag = false;
/*     */       
/* 221 */       for (Entity entity1 : list) {
/* 222 */         if (entity1.func_70067_L()) {
/* 223 */           flag = false;
/* 224 */           AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_186662_g(0.30000001192092896D);
/* 225 */           RayTraceResult raytraceresult1 = axisalignedbb.func_72327_a(vec3d, vec3d1);
/* 226 */           if (raytraceresult1 != null) {
/* 227 */             double d1 = vec3d.func_72436_e(raytraceresult1.field_72307_f);
/* 228 */             if (d1 < d0 || d0 == 0.0D) {
/* 229 */               entity = entity1;
/* 230 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 238 */       if (entity != null) {
/* 239 */         raytraceresult = new RayTraceResult(entity);
/*     */       }
/*     */       
/* 242 */       if (raytraceresult != null && (
/* 243 */         raytraceresult.field_72313_a != RayTraceResult.Type.BLOCK || this.field_70170_p.func_180495_p(raytraceresult.func_178782_a()).func_177230_c() != Blocks.field_150427_aO))
/*     */       {
/*     */         
/* 246 */         if (this.predictTick)
/*     */         {
/*     */           
/* 249 */           this.breaked = true;
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 255 */       this.ticksInAir2++;
/* 256 */       this.posX2 += this.motionX2;
/* 257 */       this.posY2 += this.motionY2;
/* 258 */       this.posZ2 += this.motionZ2;
/* 259 */       float f4 = MathHelper.func_76133_a(this.motionX2 * this.motionX2 + this.motionZ2 * this.motionZ2);
/* 260 */       this.rotationYaw2 = (float)(MathHelper.func_181159_b(this.motionX2, this.motionZ2) * 57.29577951308232D);
/* 261 */       for (this.rotationPitch2 = (float)(MathHelper.func_181159_b(this.motionY2, f4) * 57.29577951308232D); this.rotationPitch2 - this.prevRotationPitch2 < -180.0F; this.prevRotationPitch2 -= 360.0F);
/*     */       
/* 263 */       while (this.rotationPitch2 - this.prevRotationPitch2 >= 180.0F) {
/* 264 */         this.prevRotationPitch2 += 360.0F;
/*     */       }
/*     */       
/* 267 */       while (this.rotationYaw2 - this.prevRotationYaw2 < -180.0F) {
/* 268 */         this.prevRotationYaw2 -= 360.0F;
/*     */       }
/*     */       
/* 271 */       while (this.rotationYaw2 - this.prevRotationYaw2 >= 180.0F) {
/* 272 */         this.prevRotationYaw2 += 360.0F;
/*     */       }
/* 274 */       this.rotationPitch2 = this.prevRotationPitch2 + (this.rotationPitch2 - this.prevRotationPitch2) * 0.2F;
/* 275 */       this.rotationYaw2 = this.prevRotationYaw2 + (this.rotationYaw2 - this.prevRotationYaw2) * 0.2F;
/* 276 */       float f1 = 0.99F;
/* 277 */       if (func_70090_H()) {
/* 278 */         f1 = 0.6F;
/*     */       }
/* 280 */       this.motionX2 *= f1;
/* 281 */       this.motionY2 *= f1;
/* 282 */       this.motionZ2 *= f1;
/* 283 */       if (!func_189652_ae()) {
/* 284 */         this.motionY2 -= 0.05000000074505806D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vec3d getFakePosition() {
/* 292 */     return new Vec3d(this.posX2, this.posY2, this.posZ2);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinEntityArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */