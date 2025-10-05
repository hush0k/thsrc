/*     */ package com.mrzak34.thunderhack.util.math;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ExplosionBuilder
/*     */ {
/*     */   private final World worldObj;
/*     */   private final double explosionX;
/*     */   private final double explosionY;
/*     */   private final double explosionZ;
/*     */   private final Entity exploder;
/*     */   private final float explosionSize;
/*  25 */   public HashMap<EntityPlayer, Float> damageMap = new HashMap<>();
/*     */ 
/*     */   
/*     */   public ExplosionBuilder(World worldIn, Entity entityIn, double x, double y, double z, float size) {
/*  29 */     this.worldObj = worldIn;
/*  30 */     this.exploder = entityIn;
/*  31 */     this.explosionSize = size;
/*  32 */     this.explosionX = x;
/*  33 */     this.explosionY = y;
/*  34 */     this.explosionZ = z;
/*  35 */     doExplosionA();
/*     */   }
/*     */   
/*     */   public void doExplosionA() {
/*  39 */     Set<BlockPos> set = Sets.newHashSet();
/*     */     
/*  41 */     for (int j = 0; j < 16; j++) {
/*  42 */       for (int k = 0; k < 16; k++) {
/*  43 */         for (int l = 0; l < 16; l++) {
/*  44 */           if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
/*  45 */             double d0 = (j / 15.0F * 2.0F - 1.0F);
/*  46 */             double d1 = (k / 15.0F * 2.0F - 1.0F);
/*  47 */             double d2 = (l / 15.0F * 2.0F - 1.0F);
/*  48 */             double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*  49 */             d0 /= d3;
/*  50 */             d1 /= d3;
/*  51 */             d2 /= d3;
/*  52 */             float f = this.explosionSize * (0.7F + this.worldObj.field_73012_v.nextFloat() * 0.6F);
/*  53 */             double d4 = this.explosionX;
/*  54 */             double d6 = this.explosionY;
/*  55 */             double d8 = this.explosionZ;
/*     */             
/*  57 */             for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
/*  58 */               BlockPos blockpos = new BlockPos(d4, d6, d8);
/*  59 */               IBlockState iblockstate = this.worldObj.func_180495_p(blockpos);
/*     */               
/*  61 */               if (iblockstate.func_185904_a() != Material.field_151579_a) {
/*  62 */                 float f2 = iblockstate.func_177230_c().func_149638_a(null);
/*  63 */                 f -= (f2 + 0.3F) * 0.3F;
/*     */               } 
/*     */               
/*  66 */               if (f > 0.0F && this.exploder == null) {
/*  67 */                 set.add(blockpos);
/*     */               }
/*     */               
/*  70 */               d4 += d0 * 0.30000001192092896D;
/*  71 */               d6 += d1 * 0.30000001192092896D;
/*  72 */               d8 += d2 * 0.30000001192092896D;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  78 */     float f3 = this.explosionSize * 2.0F;
/*  79 */     int k1 = MathHelper.func_76128_c(this.explosionX - f3 - 1.0D);
/*  80 */     int l1 = MathHelper.func_76128_c(this.explosionX + f3 + 1.0D);
/*  81 */     int i2 = MathHelper.func_76128_c(this.explosionY - f3 - 1.0D);
/*  82 */     int i1 = MathHelper.func_76128_c(this.explosionY + f3 + 1.0D);
/*  83 */     int j2 = MathHelper.func_76128_c(this.explosionZ - f3 - 1.0D);
/*  84 */     int j1 = MathHelper.func_76128_c(this.explosionZ + f3 + 1.0D);
/*  85 */     List<Entity> list = this.worldObj.func_72839_b(this.exploder, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));
/*  86 */     Vec3d vec3d = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);
/*     */     
/*  88 */     for (Entity entity : list) {
/*  89 */       if (!entity.func_180427_aV()) {
/*  90 */         double d12 = entity.func_70011_f(this.explosionX, this.explosionY, this.explosionZ) / f3;
/*  91 */         if (d12 <= 1.0D) {
/*  92 */           double d5 = entity.field_70165_t - this.explosionX;
/*  93 */           double d7 = entity.field_70163_u + entity.func_70047_e() - this.explosionY;
/*  94 */           double d9 = entity.field_70161_v - this.explosionZ;
/*  95 */           double d13 = MathHelper.func_76133_a(d5 * d5 + d7 * d7 + d9 * d9);
/*  96 */           if (d13 != 0.0D) {
/*  97 */             double d14 = this.worldObj.func_72842_a(vec3d, entity.func_174813_aQ());
/*  98 */             double d10 = (1.0D - d12) * d14;
/*  99 */             if (entity instanceof EntityPlayer)
/* 100 */               this.damageMap.put((EntityPlayer)entity, Float.valueOf((int)((d10 * d10 + d10) / 2.0D * 7.0D * f3 + 1.0D))); 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\math\ExplosionBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */