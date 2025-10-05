/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ public class WeaknessHelper
/*    */ {
/*    */   private final Setting<AutoCrystal.AntiWeakness> antiWeakness;
/*    */   private final Setting<Integer> cooldown;
/*    */   private boolean weaknessed;
/*    */   
/*    */   public WeaknessHelper(Setting<AutoCrystal.AntiWeakness> antiWeakness, Setting<Integer> cooldown) {
/* 13 */     this.antiWeakness = antiWeakness;
/* 14 */     this.cooldown = cooldown;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateWeakness() {
/* 23 */     this.weaknessed = !DamageUtil.canBreakWeakness(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isWeaknessed() {
/* 30 */     return this.weaknessed;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSwitch() {
/* 35 */     return (this.antiWeakness.getValue() == AutoCrystal.AntiWeakness.Switch && ((Integer)this.cooldown
/* 36 */       .getValue()).intValue() == 0 && this.weaknessed);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\WeaknessHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */