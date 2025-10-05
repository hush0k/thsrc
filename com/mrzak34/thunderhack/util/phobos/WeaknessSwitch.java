/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ public class WeaknessSwitch {
/*  4 */   public static final WeaknessSwitch NONE = new WeaknessSwitch(-1, false);
/*  5 */   public static final WeaknessSwitch INVALID = new WeaknessSwitch(-1, true);
/*    */   
/*    */   private final int slot;
/*    */   private final boolean needsSwitch;
/*    */   
/*    */   public WeaknessSwitch(int slot, boolean needsSwitch) {
/* 11 */     this.slot = slot;
/* 12 */     this.needsSwitch = needsSwitch;
/*    */   }
/*    */   
/*    */   public int getSlot() {
/* 16 */     return this.slot;
/*    */   }
/*    */   
/*    */   public boolean needsSwitch() {
/* 20 */     return this.needsSwitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\WeaknessSwitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */