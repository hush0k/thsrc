/*     */ package com.mrzak34.thunderhack.util;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.FoodStats;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.MovementInputFromOptions;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class FreecamCamera
/*     */   extends EntityPlayerSP {
/*  27 */   private final Minecraft mc = Minecraft.func_71410_x();
/*     */   
/*     */   private boolean copyInventory;
/*     */   
/*     */   private boolean follow;
/*     */   private float hSpeed;
/*     */   private float vSpeed;
/*     */   
/*     */   public FreecamCamera(boolean copyInventory, boolean follow, float hSpeed, float vSpeed) {
/*  36 */     super(Util.mc, (World)Util.mc.field_71441_e, Util.mc.field_71439_g.field_71174_a, Util.mc.field_71439_g.func_146107_m(), Util.mc.field_71439_g.func_192035_E());
/*  37 */     this.copyInventory = copyInventory;
/*  38 */     this.follow = follow;
/*  39 */     this.hSpeed = hSpeed;
/*  40 */     this.vSpeed = vSpeed;
/*  41 */     this.field_70145_X = true;
/*  42 */     func_70606_j(this.mc.field_71439_g.func_110143_aJ());
/*  43 */     this.field_70165_t = this.mc.field_71439_g.field_70165_t;
/*  44 */     this.field_70163_u = this.mc.field_71439_g.field_70163_u;
/*  45 */     this.field_70161_v = this.mc.field_71439_g.field_70161_v;
/*  46 */     this.field_70169_q = this.mc.field_71439_g.field_70169_q;
/*  47 */     this.field_70167_r = this.mc.field_71439_g.field_70167_r;
/*  48 */     this.field_70166_s = this.mc.field_71439_g.field_70166_s;
/*  49 */     this.field_70142_S = this.mc.field_71439_g.field_70142_S;
/*  50 */     this.field_70137_T = this.mc.field_71439_g.field_70137_T;
/*  51 */     this.field_70136_U = this.mc.field_71439_g.field_70136_U;
/*  52 */     this.field_70177_z = this.mc.field_71439_g.field_70177_z;
/*  53 */     this.field_70125_A = this.mc.field_71439_g.field_70125_A;
/*  54 */     this.field_70759_as = this.mc.field_71439_g.field_70759_as;
/*  55 */     this.field_70126_B = this.mc.field_71439_g.field_70126_B;
/*  56 */     this.field_70127_C = this.mc.field_71439_g.field_70127_C;
/*  57 */     this.field_70758_at = this.mc.field_71439_g.field_70758_at;
/*  58 */     if (this.copyInventory) {
/*  59 */       this.field_71071_by = this.mc.field_71439_g.field_71071_by;
/*  60 */       this.field_71069_bz = this.mc.field_71439_g.field_71069_bz;
/*  61 */       func_184611_a(EnumHand.MAIN_HAND, this.mc.field_71439_g.func_184614_ca());
/*  62 */       func_184611_a(EnumHand.OFF_HAND, this.mc.field_71439_g.func_184592_cb());
/*     */     } 
/*  64 */     NBTTagCompound compound = new NBTTagCompound();
/*  65 */     this.mc.field_71439_g.field_71075_bZ.func_75091_a(compound);
/*  66 */     this.field_71075_bZ.func_75095_b(compound);
/*  67 */     this.field_71075_bZ.field_75100_b = true;
/*  68 */     this.field_70739_aP = this.mc.field_71439_g.field_70739_aP;
/*  69 */     this.field_71158_b = (MovementInput)new MovementInputFromOptions(this.mc.field_71474_y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70014_b(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70037_a(NBTTagCompound compound) {}
/*     */ 
/*     */   
/*     */   public boolean func_70055_a(Material material) {
/*  82 */     return this.mc.field_71439_g.func_70055_a(material);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Potion, PotionEffect> func_193076_bZ() {
/*  88 */     return this.mc.field_71439_g.func_193076_bZ();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PotionEffect> func_70651_bq() {
/*  94 */     return this.mc.field_71439_g.func_70651_bq();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70658_aO() {
/*  99 */     return this.mc.field_71439_g.func_70658_aO();
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_110139_bj() {
/* 104 */     return this.mc.field_71439_g.func_110139_bj();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70644_a(Potion potion) {
/* 109 */     return this.mc.field_71439_g.func_70644_a(potion);
/*     */   }
/*     */ 
/*     */   
/*     */   public PotionEffect func_70660_b(Potion potion) {
/* 114 */     return this.mc.field_71439_g.func_70660_b(potion);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FoodStats func_71024_bL() {
/* 120 */     return this.mc.field_71439_g.func_71024_bL();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70041_e_() {
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity entity) {
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_174813_aQ() {
/* 141 */     return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70108_f(Entity entity) {}
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource source, float amount) {
/* 155 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70075_an() {
/* 160 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_184228_n(Entity entity) {
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_90999_ad() {
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTrample(World world, Block block, BlockPos pos, float fallDistance) {
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145775_I() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_184231_a(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
/*     */ 
/*     */   
/*     */   public boolean func_190530_aW() {
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumPushReaction func_184192_z() {
/* 199 */     return EnumPushReaction.IGNORE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_189652_ae() {
/* 204 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70636_d() {
/* 209 */     this.field_70159_w = 0.0D;
/* 210 */     this.field_70181_x = 0.0D;
/* 211 */     this.field_70179_y = 0.0D;
/* 212 */     this.field_71158_b.func_78898_a();
/* 213 */     float up = this.field_71158_b.field_78901_c ? 1.0F : (this.field_71158_b.field_78899_d ? -1.0F : 0.0F);
/* 214 */     setMotion(this.field_71158_b.field_78902_a, up, this.field_71158_b.field_192832_b);
/* 215 */     if (this.mc.field_71474_y.field_151444_V.func_151470_d()) {
/* 216 */       this.field_70159_w *= 2.0D;
/* 217 */       this.field_70181_x *= 2.0D;
/* 218 */       this.field_70179_y *= 2.0D;
/* 219 */       func_70031_b(true);
/*     */     } else {
/* 221 */       func_70031_b(false);
/*     */     } 
/* 223 */     if (this.follow) {
/* 224 */       if (Math.abs(this.field_70159_w) <= 9.99999993922529E-9D) {
/* 225 */         this.field_70165_t += this.mc.field_71439_g.field_70165_t - this.mc.field_71439_g.field_70169_q;
/*     */       }
/* 227 */       if (Math.abs(this.field_70181_x) <= 9.99999993922529E-9D) {
/* 228 */         this.field_70181_x += this.mc.field_71439_g.field_70163_u - this.mc.field_71439_g.field_70167_r;
/*     */       }
/* 230 */       if (Math.abs(this.field_70179_y) <= 9.99999993922529E-9D) {
/* 231 */         this.field_70179_y += this.mc.field_71439_g.field_70161_v - this.mc.field_71439_g.field_70166_s;
/*     */       }
/*     */     } 
/* 234 */     func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */   }
/*     */   
/*     */   public void setMotion(float strafe, float up, float forward) {
/* 238 */     float f = strafe * strafe + up * up + forward * forward;
/* 239 */     if (f >= 1.0E-4F) {
/* 240 */       f = MathHelper.func_76129_c(f);
/* 241 */       if (f < 1.0F) f = 1.0F; 
/* 242 */       f /= 2.0F;
/* 243 */       strafe *= f;
/* 244 */       up *= f;
/* 245 */       forward *= f;
/*     */       
/* 247 */       float f1 = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
/* 248 */       float f2 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
/* 249 */       this.field_70159_w = ((strafe * f2 - forward * f1) * this.hSpeed);
/* 250 */       this.field_70181_x = up * this.vSpeed;
/* 251 */       this.field_70179_y = ((forward * f2 + strafe * f1) * this.hSpeed);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCopyInventory(boolean copyInventory) {
/* 257 */     this.copyInventory = copyInventory;
/*     */   }
/*     */   
/*     */   public void setFollow(boolean follow) {
/* 261 */     this.follow = follow;
/*     */   }
/*     */   
/*     */   public void sethSpeed(float hSpeed) {
/* 265 */     this.hSpeed = hSpeed;
/*     */   }
/*     */   
/*     */   public void setvSpeed(float vSpeed) {
/* 269 */     this.vSpeed = vSpeed;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\FreecamCamera.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */