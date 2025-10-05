/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class DamageSyncHelper {
/*  9 */   private final DiscreteTimer discreteTimer = new GuardTimer(1000L, 5L);
/* 10 */   private final Timer timer = new Timer();
/*    */   
/*    */   private final Setting<Integer> syncDelay;
/*    */   
/*    */   private final Setting<Boolean> discrete;
/*    */   
/*    */   private final Setting<Boolean> danger;
/*    */   
/*    */   private final Confirmer confirmer;
/*    */   private float lastDamage;
/*    */   
/*    */   public DamageSyncHelper(Setting<Boolean> discrete, Setting<Integer> syncDelay, Setting<Boolean> danger) {
/* 22 */     this.danger = danger;
/* 23 */     this.confirmer = Confirmer.createAndSubscribe();
/* 24 */     this.syncDelay = syncDelay;
/* 25 */     this.discrete = discrete;
/* 26 */     this.discreteTimer.reset(((Integer)syncDelay.getValue()).intValue());
/*    */   }
/*    */   
/*    */   public void setSync(BlockPos pos, float damage, boolean newVer) {
/* 30 */     int placeTime = (int)(Thunderhack.serverManager.getPing() / 2.0D + 1.0D);
/* 31 */     this.confirmer.setPos(pos.func_185334_h(), newVer, placeTime);
/* 32 */     this.lastDamage = damage;
/*    */     
/* 34 */     if (((Boolean)this.discrete.getValue()).booleanValue() && this.discreteTimer.passed(((Integer)this.syncDelay.getValue()).intValue())) {
/* 35 */       this.discreteTimer.reset(((Integer)this.syncDelay.getValue()).intValue());
/* 36 */     } else if (!((Boolean)this.discrete.getValue()).booleanValue() && this.timer.passed(((Integer)this.syncDelay.getValue()).intValue())) {
/* 37 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSyncing(float damage, boolean damageSync) {
/* 43 */     return (damageSync && 
/* 44 */       !((Boolean)this.danger.getValue()).booleanValue() && this.confirmer
/* 45 */       .isValid() && damage <= this.lastDamage && ((((Boolean)this.discrete
/*    */       
/* 47 */       .getValue()).booleanValue() && 
/* 48 */       !this.discreteTimer.passed(((Integer)this.syncDelay.getValue()).intValue())) || (
/* 49 */       !((Boolean)this.discrete.getValue()).booleanValue() && 
/* 50 */       !this.timer.passed(((Integer)this.syncDelay.getValue()).intValue()))));
/*    */   }
/*    */   
/*    */   public Confirmer getConfirmer() {
/* 54 */     return this.confirmer;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\DamageSyncHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */