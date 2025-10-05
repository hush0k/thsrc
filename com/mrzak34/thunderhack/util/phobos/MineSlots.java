/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ public class MineSlots {
/*    */   private final int blockSlot;
/*    */   private final int toolSlot;
/*    */   private final float damage;
/*    */   
/*    */   public MineSlots(int blockSlot, int toolSlot, float damage) {
/*  9 */     this.blockSlot = blockSlot;
/* 10 */     this.toolSlot = toolSlot;
/* 11 */     this.damage = damage;
/*    */   }
/*    */   
/*    */   public int getBlockSlot() {
/* 15 */     return this.blockSlot;
/*    */   }
/*    */   
/*    */   public int getToolSlot() {
/* 19 */     return this.toolSlot;
/*    */   }
/*    */   
/*    */   public float getDamage() {
/* 23 */     return this.damage;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\MineSlots.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */