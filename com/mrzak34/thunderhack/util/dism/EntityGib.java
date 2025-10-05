/*     */ package com.mrzak34.thunderhack.util.dism;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.misc.Dismemberment;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityGib
/*     */   extends Entity
/*     */ {
/*     */   public EntityLivingBase parent;
/*     */   public int type;
/*     */   public float pitchSpin;
/*     */   public float yawSpin;
/*     */   public int groundTime;
/*     */   public int liveTime;
/*     */   public boolean explosion;
/*     */   
/*     */   public EntityGib(World world) {
/*  30 */     super(world);
/*  31 */     this.parent = null;
/*  32 */     this.type = 0;
/*     */     
/*  34 */     this.groundTime = 0;
/*  35 */     this.liveTime = Dismemberment.ticks;
/*  36 */     this.field_70158_ak = true;
/*     */   }
/*     */   
/*     */   public EntityGib(World world, EntityLivingBase gibParent, int gibType, Entity explo) {
/*  40 */     this(world);
/*  41 */     this.parent = gibParent;
/*  42 */     this.type = gibType;
/*     */     
/*  44 */     this.liveTime = Dismemberment.ticks;
/*     */     
/*  46 */     func_70012_b(this.parent.field_70165_t, (this.parent.func_174813_aQ()).field_72338_b, this.parent.field_70161_v, this.parent.field_70177_z, this.parent.field_70125_A);
/*  47 */     this.field_70177_z = this.parent.field_70760_ar;
/*  48 */     this.field_70126_B = this.parent.field_70177_z;
/*  49 */     this.field_70127_C = this.parent.field_70125_A;
/*     */     
/*  51 */     this.field_70159_w = this.parent.field_70159_w + (this.field_70146_Z.nextDouble() - this.field_70146_Z.nextDouble()) * 0.25D;
/*  52 */     this.field_70181_x = this.parent.field_70181_x;
/*  53 */     this.field_70179_y = this.parent.field_70179_y + (this.field_70146_Z.nextDouble() - this.field_70146_Z.nextDouble()) * 0.25D;
/*     */     
/*  55 */     if (this.type == -1) {
/*  56 */       this.field_70177_z = this.parent.field_70759_as;
/*  57 */       func_70105_a(1.0F, 1.0F);
/*  58 */       this.field_70163_u += 1.5D;
/*     */     } 
/*  60 */     if (this.type == 0) {
/*  61 */       this.field_70177_z = this.parent.field_70759_as;
/*  62 */       func_70105_a(0.5F, 0.5F);
/*  63 */       if (this.parent instanceof EntityCreeper) {
/*  64 */         this.field_70163_u += 1.25D;
/*     */       } else {
/*  66 */         this.field_70163_u += 1.5D;
/*     */       } 
/*  68 */     } else if (this.type == 1 || this.type == 2) {
/*  69 */       func_70105_a(0.3F, 0.4F);
/*     */       
/*  71 */       double offset = 0.35D;
/*  72 */       double offset1 = -0.25D;
/*     */       
/*  74 */       if (this.parent instanceof net.minecraft.entity.monster.EntitySkeleton) {
/*  75 */         offset -= 0.05D;
/*  76 */         this.field_70163_u += 0.15D;
/*     */       } 
/*  78 */       if (this.type == 2) {
/*  79 */         offset *= -1.0D;
/*     */       }
/*     */       
/*  82 */       this.field_70165_t += offset * Math.cos(Math.toRadians(this.parent.field_70761_aq));
/*  83 */       this.field_70161_v += offset * Math.sin(Math.toRadians(this.parent.field_70761_aq));
/*     */       
/*  85 */       this.field_70165_t += offset1 * Math.sin(Math.toRadians(this.parent.field_70761_aq));
/*  86 */       this.field_70161_v -= offset1 * Math.cos(Math.toRadians(this.parent.field_70761_aq));
/*     */       
/*  88 */       this.field_70163_u += 1.25D;
/*     */       
/*  90 */       this.field_70127_C = this.field_70125_A = -90.0F;
/*  91 */     } else if (this.type == 3) {
/*  92 */       func_70105_a(0.5F, 0.5F);
/*  93 */       if (this.parent instanceof EntityCreeper) {
/*  94 */         this.field_70163_u += 0.75D;
/*     */       } else {
/*  96 */         this.field_70163_u++;
/*     */       } 
/*  98 */     } else if (this.type == 4 || this.type == 5) {
/*  99 */       func_70105_a(0.3F, 0.4F);
/*     */       
/* 101 */       double offset = 0.125D;
/*     */       
/* 103 */       if (this.type == 5) {
/* 104 */         offset *= -1.0D;
/*     */       }
/*     */       
/* 107 */       this.field_70165_t += offset * Math.cos(Math.toRadians(this.parent.field_70761_aq));
/* 108 */       this.field_70161_v += offset * Math.sin(Math.toRadians(this.parent.field_70761_aq));
/*     */       
/* 110 */       this.field_70163_u += 0.375D;
/* 111 */     } else if (this.type >= 6) {
/* 112 */       func_70105_a(0.3F, 0.4F);
/*     */       
/* 114 */       double offset = 0.125D;
/* 115 */       double offset1 = -0.25D;
/*     */       
/* 117 */       if (this.parent instanceof net.minecraft.entity.monster.EntitySkeleton) {
/* 118 */         offset -= 0.05D;
/* 119 */         this.field_70163_u += 0.15D;
/*     */       } 
/* 121 */       if (this.type % 2 == 1) {
/* 122 */         offset *= -1.0D;
/*     */       }
/* 124 */       if (this.type >= 8) {
/* 125 */         offset1 *= -1.0D;
/*     */       }
/*     */       
/* 128 */       this.field_70165_t += offset * Math.cos(Math.toRadians(this.parent.field_70761_aq));
/* 129 */       this.field_70161_v += offset * Math.sin(Math.toRadians(this.parent.field_70761_aq));
/*     */       
/* 131 */       this.field_70165_t += offset1 * Math.sin(Math.toRadians(this.parent.field_70761_aq));
/* 132 */       this.field_70161_v -= offset1 * Math.cos(Math.toRadians(this.parent.field_70761_aq));
/*     */       
/* 134 */       this.field_70163_u += 0.3125D;
/*     */     } 
/*     */     
/* 137 */     float i = this.field_70146_Z.nextInt(45) + 5.0F + this.field_70146_Z.nextFloat();
/* 138 */     float j = this.field_70146_Z.nextInt(45) + 5.0F + this.field_70146_Z.nextFloat();
/* 139 */     if (this.field_70146_Z.nextInt(2) == 0) {
/* 140 */       i *= -1.0F;
/*     */     }
/* 142 */     if (this.field_70146_Z.nextInt(2) == 0) {
/* 143 */       j *= -1.0F;
/*     */     }
/* 145 */     this.pitchSpin = i * (float)(this.field_70181_x + 0.3D);
/* 146 */     this.yawSpin = j * (float)(Math.sqrt(this.field_70159_w * this.field_70179_y) + 0.3D);
/*     */     
/* 148 */     func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
/*     */     
/* 150 */     if (explo != null) {
/* 151 */       double mag = 1.0D;
/* 152 */       double mag2 = 1.0D;
/* 153 */       double dist = explo.func_70032_d((Entity)this.parent);
/* 154 */       dist = Math.pow(dist / 2.0D, 2.0D);
/* 155 */       if (dist < 0.1D) {
/* 156 */         dist = 0.1D;
/*     */       }
/* 158 */       if (explo instanceof net.minecraft.entity.item.EntityTNTPrimed || explo instanceof net.minecraft.entity.item.EntityMinecartTNT) {
/* 159 */         mag = 4.0D / dist;
/* 160 */       } else if (explo instanceof EntityCreeper) {
/* 161 */         EntityCreeper creep = (EntityCreeper)explo;
/* 162 */         if (creep.func_70830_n()) {
/* 163 */           mag = 6.0D / dist;
/*     */         } else {
/* 165 */           mag = 3.0D / dist;
/*     */         } 
/*     */       } 
/* 168 */       mag = Math.pow(mag, 2.0D) * 0.2D;
/* 169 */       mag2 = this.field_70163_u - explo.field_70163_u;
/* 170 */       this.field_70159_w *= mag;
/* 171 */       this.field_70181_x = mag2 * 0.4D + 0.22D;
/* 172 */       this.field_70179_y *= mag;
/*     */       
/* 174 */       this.explosion = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 181 */     if (this.parent == null) {
/* 182 */       func_70106_y();
/*     */       return;
/*     */     } 
/* 185 */     if (this.explosion) {
/* 186 */       this.field_70159_w *= 1.0869565217391304D;
/* 187 */       this.field_70181_x *= 1.0526315789473684D;
/* 188 */       this.field_70179_y *= 1.0869565217391304D;
/*     */     } 
/* 190 */     super.func_70071_h_();
/* 191 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     
/* 193 */     this.field_70181_x -= 0.08D;
/*     */     
/* 195 */     this.field_70181_x *= 0.98D;
/* 196 */     this.field_70159_w *= 0.91D;
/* 197 */     this.field_70179_y *= 0.91D;
/*     */     
/* 199 */     if (this.field_70171_ac) {
/* 200 */       this.field_70181_x = 0.3D;
/* 201 */       this.pitchSpin = 0.0F;
/* 202 */       this.yawSpin = 0.0F;
/*     */     } 
/* 204 */     if (this.field_70122_E || func_70072_I()) {
/* 205 */       this.field_70125_A += (-90.0F - this.field_70125_A % 360.0F) / 2.0F;
/*     */       
/* 207 */       this.field_70181_x *= 0.8D;
/* 208 */       this.field_70159_w *= 0.8D;
/* 209 */       this.field_70179_y *= 0.8D;
/*     */     } else {
/* 211 */       this.field_70125_A += this.pitchSpin;
/* 212 */       this.field_70177_z += this.yawSpin;
/* 213 */       this.pitchSpin *= 0.98F;
/* 214 */       this.yawSpin *= 0.98F;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 219 */     List var2 = this.field_70170_p.func_72839_b(this, func_174813_aQ().func_72314_b(0.15D, 0.0D, 0.15D));
/* 220 */     if (var2 != null && !var2.isEmpty()) {
/* 221 */       Iterator<Entity> var10 = var2.iterator();
/*     */       
/* 223 */       while (var10.hasNext()) {
/* 224 */         Entity var4 = var10.next();
/*     */         
/* 226 */         if (var4 instanceof EntityGib && !var4.field_70122_E) {
/*     */           continue;
/*     */         }
/*     */         
/* 230 */         if (var4.func_70104_M()) {
/* 231 */           var4.func_70108_f(this);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 237 */     if (this.field_70122_E || func_70072_I()) {
/* 238 */       this.groundTime++;
/* 239 */       if (this.groundTime > ((Integer)((Dismemberment)Thunderhack.moduleManager.getModuleByClass(Dismemberment.class)).gibGroundTime.getValue()).intValue() + 20) {
/* 240 */         func_70106_y();
/*     */       }
/* 242 */     } else if (this.groundTime > ((Integer)((Dismemberment)Thunderhack.moduleManager.getModuleByClass(Dismemberment.class)).gibGroundTime.getValue()).intValue()) {
/* 243 */       this.groundTime--;
/*     */     } else {
/* 245 */       this.groundTime = 0;
/*     */     } 
/* 247 */     if (this.liveTime + ((Integer)((Dismemberment)Thunderhack.moduleManager.getModuleByClass(Dismemberment.class)).gibTime.getValue()).intValue() < Dismemberment.ticks) {
/* 248 */       func_70106_y();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180430_e(float distance, float damageMultiplier) {}
/*     */ 
/*     */   
/*     */   public boolean func_70089_S() {
/* 258 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {}
/*     */ 
/*     */   
/*     */   public boolean func_70039_c(NBTTagCompound par1NBTTagCompound) {
/* 267 */     return false;
/*     */   }
/*     */   
/*     */   public void func_70037_a(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   public void func_70014_b(NBTTagCompound nbttagcompound) {}
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\dism\EntityGib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */