/*    */ package com.mrzak34.thunderhack.util.dism;
/*    */ 
/*    */ import net.minecraft.client.particle.Particle;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleBlood extends Particle {
/*    */   public ParticleBlood(World world, double d, double d1, double d2, double d3, double d4, double d5, boolean isPlayer) {
/*  8 */     super(world, d, d1, d2, d3, d4, d5);
/*  9 */     this.field_70545_g = 0.06F;
/* 10 */     this.field_70552_h = 1.0F;
/* 11 */     this.field_70553_i = 0.0F;
/* 12 */     this.field_70551_j = 0.0F;
/* 13 */     this.field_70544_f *= 1.2F;
/* 14 */     func_70543_e(1.2F);
/* 15 */     this.field_187130_j += (this.field_187136_p.nextFloat() * 0.15F);
/* 16 */     this.field_187131_k *= (0.4F / (this.field_187136_p.nextFloat() * 0.9F + 0.1F));
/* 17 */     this.field_187129_i *= (0.4F / (this.field_187136_p.nextFloat() * 0.9F + 0.1F));
/* 18 */     this.field_70547_e = (int)(200.0F + 20.0F / (this.field_187136_p.nextFloat() * 0.9F + 0.1F));
/* 19 */     func_187115_a(0.01F, 0.01F);
/* 20 */     func_70536_a(19 + this.field_187136_p.nextInt(4));
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_189213_a() {
/* 25 */     if (this.field_70546_d++ >= this.field_70547_e) {
/* 26 */       func_187112_i();
/*    */       return;
/*    */     } 
/* 29 */     this.field_187123_c = this.field_187126_f;
/* 30 */     this.field_187124_d = this.field_187127_g;
/* 31 */     this.field_187125_e = this.field_187128_h;
/* 32 */     if (this.field_187129_i != 0.0D && this.field_187131_k != 0.0D && !this.field_187132_l) {
/* 33 */       this.field_187130_j -= this.field_70545_g;
/* 34 */       func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
/* 35 */       this.field_187129_i *= 0.9800000190734863D;
/* 36 */       this.field_187130_j *= 0.9800000190734863D;
/* 37 */       this.field_187131_k *= 0.9800000190734863D;
/* 38 */       if (this.field_187132_l) {
/* 39 */         this.field_187129_i *= 0.699999988079071D;
/* 40 */         this.field_187131_k *= 0.699999988079071D;
/* 41 */         this.field_187127_g += 0.2D;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_70537_b() {
/* 48 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\dism\ParticleBlood.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */