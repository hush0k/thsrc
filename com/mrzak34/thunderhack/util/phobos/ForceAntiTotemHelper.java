/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ForceAntiTotemHelper
/*    */ {
/*    */   private final DamageSyncHelper damageSyncHelper;
/*    */   private final Setting<Integer> placeConfirm;
/*    */   private final Setting<Integer> breakConfirm;
/*    */   private BlockPos pos;
/*    */   
/*    */   public ForceAntiTotemHelper(Setting<Boolean> discrete, Setting<Integer> syncDelay, Setting<Integer> placeConfirm, Setting<Integer> breakConfirm, Setting<Boolean> dangerForce) {
/* 18 */     this.damageSyncHelper = new DamageSyncHelper(discrete, syncDelay, dangerForce);
/*    */ 
/*    */ 
/*    */     
/* 22 */     this.placeConfirm = placeConfirm;
/* 23 */     this.breakConfirm = breakConfirm;
/*    */   }
/*    */   
/*    */   public void setSync(BlockPos pos, boolean newVer) {
/* 27 */     this.damageSyncHelper.setSync(pos, Float.MAX_VALUE, newVer);
/* 28 */     this.pos = pos;
/*    */   }
/*    */   
/*    */   public boolean isForcing(boolean damageSync) {
/* 32 */     Confirmer c = this.damageSyncHelper.getConfirmer();
/* 33 */     if (c.isValid() && (
/* 34 */       !c.isPlaceConfirmed(((Integer)this.placeConfirm.getValue()).intValue()) || 
/* 35 */       !c.isBreakConfirmed(((Integer)this.breakConfirm.getValue()).intValue())))
/*    */     {
/* 37 */       return c.isValid();
/*    */     }
/*    */     
/* 40 */     return this.damageSyncHelper.isSyncing(0.0F, damageSync);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 44 */     return this.pos;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ForceAntiTotemHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */