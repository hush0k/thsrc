/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class AntiTotemHelper
/*    */ {
/*    */   private final Setting<Float> health;
/*    */   
/*    */   public AntiTotemHelper(Setting<Float> health) {
/* 14 */     this.health = health;
/*    */   }
/*    */   private EntityPlayer target; private BlockPos targetPos;
/*    */   public boolean isDoublePoppable(EntityPlayer player) {
/* 18 */     return (Thunderhack.combatManager.lastPop((Entity)player) > 500L && player.func_110143_aJ() <= ((Float)this.health.getValue()).floatValue());
/*    */   }
/*    */   
/*    */   public BlockPos getTargetPos() {
/* 22 */     return this.targetPos;
/*    */   }
/*    */   
/*    */   public void setTargetPos(BlockPos targetPos) {
/* 26 */     this.targetPos = targetPos;
/*    */   }
/*    */   
/*    */   public EntityPlayer getTarget() {
/* 30 */     return this.target;
/*    */   }
/*    */   
/*    */   public void setTarget(EntityPlayer target) {
/* 34 */     this.target = target;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\AntiTotemHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */