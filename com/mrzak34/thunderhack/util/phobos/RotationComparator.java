/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import java.util.Comparator;
/*    */ import java.util.TreeSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RotationComparator
/*    */   implements Comparator<CrystalData>
/*    */ {
/*    */   private final double ex;
/*    */   private final double diff;
/*    */   
/*    */   public RotationComparator(double exponent, double minRotationDiff) {
/* 17 */     this.ex = exponent;
/* 18 */     this.diff = minRotationDiff;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends CrystalData> TreeSet<T> asSet(double exponent, double diff) {
/* 23 */     return new TreeSet<>(new RotationComparator(exponent, diff));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compare(CrystalData o1, CrystalData o2) {
/*    */     double angle1, angle2;
/* 31 */     float[] rotations = null;
/* 32 */     if (o1.hasCachedRotations()) {
/* 33 */       angle1 = o1.getAngle();
/*    */     } else {
/*    */       
/* 36 */       rotations = new float[] { Thunderhack.rotationManager.getServerYaw(), Thunderhack.rotationManager.getServerPitch() };
/*    */       
/* 38 */       float[] rotations1 = RotationUtil.getRotations(o1.getCrystal());
/* 39 */       angle1 = RotationUtil.angle(rotations, rotations1);
/*    */       
/* 41 */       o1.cacheRotations(rotations1, angle1);
/*    */     } 
/*    */     
/* 44 */     if (o2.hasCachedRotations()) {
/* 45 */       angle2 = o2.getAngle();
/*    */     } else {
/* 47 */       if (rotations == null)
/*    */       {
/* 49 */         rotations = new float[] { Thunderhack.rotationManager.getServerYaw(), Thunderhack.rotationManager.getServerPitch() };
/*    */       }
/*    */       
/* 52 */       float[] rotations2 = RotationUtil.getRotations(o2.getCrystal());
/* 53 */       angle2 = RotationUtil.angle(rotations, rotations2);
/* 54 */       o2.cacheRotations(rotations2, angle2);
/*    */     } 
/*    */     
/* 57 */     if (Math.abs(angle1 - angle2) < this.diff) {
/* 58 */       return o1.compareTo(o2);
/*    */     }
/*    */     
/* 61 */     angle1 /= 180.0D;
/* 62 */     angle2 /= 180.0D;
/*    */     
/* 64 */     float damage1 = o1.getDamage();
/* 65 */     float damage2 = o2.getDamage();
/* 66 */     float self1 = o1.getSelfDmg();
/* 67 */     float self2 = o2.getSelfDmg();
/*    */     
/* 69 */     o1.setDamage((float)(damage1 * Math.pow(1.0D / angle1, this.ex)));
/* 70 */     o2.setDamage((float)(damage2 * Math.pow(1.0D / angle2, this.ex)));
/* 71 */     o1.setSelfDmg((float)(self1 * Math.pow(angle1, this.ex)));
/* 72 */     o2.setSelfDmg((float)(self2 * Math.pow(angle2, this.ex)));
/*    */     
/* 74 */     int result = o1.compareTo(o2);
/*    */     
/* 76 */     o1.setSelfDmg(self1);
/* 77 */     o2.setSelfDmg(self2);
/* 78 */     o1.setDamage(damage1);
/* 79 */     o2.setDamage(damage2);
/*    */     
/* 81 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\RotationComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */