/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class CrystalData implements Comparable<CrystalData> {
/*    */   private final Entity crystal;
/*    */   private float selfDmg;
/*    */   private float damage;
/*    */   private float[] rotations;
/*    */   private double angle;
/*    */   
/*    */   public CrystalData(Entity crystal) {
/* 13 */     this.crystal = crystal;
/*    */   }
/*    */   
/*    */   public Entity getCrystal() {
/* 17 */     return this.crystal;
/*    */   }
/*    */   
/*    */   public float getSelfDmg() {
/* 21 */     return this.selfDmg;
/*    */   }
/*    */   
/*    */   public void setSelfDmg(float damage) {
/* 25 */     this.selfDmg = damage;
/*    */   }
/*    */   
/*    */   public float getDamage() {
/* 29 */     return this.damage;
/*    */   }
/*    */   
/*    */   public void setDamage(float damage) {
/* 33 */     this.damage = damage;
/*    */   }
/*    */   
/*    */   public float[] getRotations() {
/* 37 */     return this.rotations;
/*    */   }
/*    */   
/*    */   public double getAngle() {
/* 41 */     return this.angle;
/*    */   }
/*    */   
/*    */   public boolean hasCachedRotations() {
/* 45 */     return (this.rotations != null);
/*    */   }
/*    */   
/*    */   public void cacheRotations(float[] rotations, double angle) {
/* 49 */     this.rotations = rotations;
/* 50 */     this.angle = angle;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(CrystalData o) {
/* 55 */     if (Math.abs(o.damage - this.damage) < 1.0F) {
/* 56 */       return Float.compare(this.selfDmg, o.selfDmg);
/*    */     }
/*    */     
/* 59 */     return Float.compare(o.damage, this.damage);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 65 */     return this.crystal.func_180425_c().hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 70 */     if (o instanceof CrystalData) {
/* 71 */       return (hashCode() == o.hashCode());
/*    */     }
/*    */     
/* 74 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\CrystalData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */