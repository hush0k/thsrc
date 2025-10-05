/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ public class CrystalTimeStamp extends TimeStamp {
/*    */   private final float damage;
/*    */   private final boolean shield;
/*    */   
/*    */   public CrystalTimeStamp(float damage, boolean shield) {
/*  8 */     this.damage = damage;
/*  9 */     this.shield = shield;
/*    */   }
/*    */   
/*    */   public float getDamage() {
/* 13 */     return this.damage;
/*    */   }
/*    */   
/*    */   public boolean isShield() {
/* 17 */     return this.shield;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\CrystalTimeStamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */