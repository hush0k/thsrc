/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*    */ 
/*    */ public enum CooldownBypass
/*    */ {
/*  7 */   None
/*    */   {
/*    */     public void switchTo(int slot) {
/* 10 */       InventoryUtil.switchTo(slot);
/*    */     }
/*    */ 
/*    */     
/*    */     public void switchBack(int lastSlot, int from) {
/* 15 */       switchTo(lastSlot);
/*    */     }
/*    */   },
/* 18 */   Slot
/*    */   {
/*    */     public void switchTo(int slot) {
/* 21 */       InventoryUtil.switchToBypass(
/* 22 */           InventoryUtil.hotbarToInventory(slot));
/*    */     }
/*    */   },
/* 25 */   Swap
/*    */   {
/*    */     public void switchTo(int slot) {
/* 28 */       InventoryUtil.switchToBypassAlt(
/* 29 */           InventoryUtil.hotbarToInventory(slot));
/*    */     }
/*    */   },
/* 32 */   Pick
/*    */   {
/*    */     public void switchTo(int slot) {
/* 35 */       InventoryUtil.bypassSwitch(slot);
/*    */     }
/*    */   };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void switchBack(int lastSlot, int from) {
/* 47 */     switchTo(from);
/*    */   }
/*    */   
/*    */   public abstract void switchTo(int paramInt);
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\CooldownBypass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */