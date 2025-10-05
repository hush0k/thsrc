/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.UpdateEntitiesEvent;
/*    */ import com.mrzak34.thunderhack.mixin.ducks.IEntityPlayer;
/*    */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ExtrapolationHelper
/*    */ {
/*    */   public ExtrapolationHelper(AutoCrystal module) {}
/*    */   
/*    */   public static void onUpdateEntity(UpdateEntitiesEvent e) {
/* 18 */     for (EntityPlayer player : Util.mc.field_71441_e.field_73010_i) {
/* 19 */       MotionTracker tracker = ((IEntityPlayer)player).getMotionTrackerT();
/* 20 */       MotionTracker breakTracker = ((IEntityPlayer)player).getBreakMotionTrackerT();
/* 21 */       MotionTracker blockTracker = ((IEntityPlayer)player).getBlockMotionTrackerT();
/* 22 */       if (player.func_110143_aJ() <= 0.0F || Util.mc.field_71439_g.func_70068_e((Entity)player) > 400.0D || (
/* 23 */         !((Boolean)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).selfExtrapolation.getValue()).booleanValue() && player
/* 24 */         .equals(Util.mc.field_71439_g))) {
/* 25 */         if (tracker != null) {
/* 26 */           tracker.active = false;
/*    */         }
/*    */         
/* 29 */         if (breakTracker != null) {
/* 30 */           breakTracker.active = false;
/*    */         }
/*    */         
/* 33 */         if (blockTracker != null) {
/* 34 */           blockTracker.active = false;
/*    */         }
/*    */         
/*    */         continue;
/*    */       } 
/*    */       
/* 40 */       if (tracker == null && ((Integer)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).extrapol.getValue()).intValue() != 0) {
/* 41 */         tracker = new MotionTracker((World)Util.mc.field_71441_e, player);
/* 42 */         ((IEntityPlayer)player).setMotionTrackerT(tracker);
/*    */       } 
/*    */       
/* 45 */       if (breakTracker == null && ((Integer)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).bExtrapol.getValue()).intValue() != 0) {
/* 46 */         breakTracker = new MotionTracker((World)Util.mc.field_71441_e, player);
/* 47 */         ((IEntityPlayer)player).setBreakMotionTrackerT(breakTracker);
/*    */       } 
/*    */       
/* 50 */       if (blockTracker == null && ((Integer)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).blockExtrapol.getValue()).intValue() != 0) {
/* 51 */         blockTracker = new MotionTracker((World)Util.mc.field_71441_e, player);
/* 52 */         ((IEntityPlayer)player).setBlockMotionTrackerT(blockTracker);
/*    */       } 
/*    */       
/* 55 */       updateTracker(tracker, ((Integer)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).extrapol.getValue()).intValue());
/* 56 */       updateTracker(breakTracker, ((Integer)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).bExtrapol.getValue()).intValue());
/* 57 */       updateTracker(blockTracker, ((Integer)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).blockExtrapol.getValue()).intValue());
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void updateTracker(MotionTracker tracker, int ticks) {
/* 62 */     if (tracker == null) {
/*    */       return;
/*    */     }
/*    */     
/* 66 */     tracker.active = false;
/* 67 */     tracker.func_82149_j((Entity)tracker.tracked);
/* 68 */     tracker.gravity = ((Boolean)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).gravityExtrapolation.getValue()).booleanValue();
/* 69 */     tracker.gravityFactor = ((Double)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).gravityFactor.getValue()).doubleValue();
/* 70 */     tracker.yPlusFactor = ((Double)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).yPlusFactor.getValue()).doubleValue();
/* 71 */     tracker.yMinusFactor = ((Double)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).yMinusFactor.getValue()).doubleValue();
/* 72 */     for (tracker.ticks = 0; tracker.ticks < ticks; tracker.ticks++) {
/* 73 */       tracker.updateFromTrackedEntity();
/*    */     }
/*    */     
/* 76 */     tracker.active = true;
/*    */   }
/*    */   
/*    */   public MotionTracker getTrackerFromEntity(Entity player) {
/* 80 */     return ((IEntityPlayer)player).getMotionTrackerT();
/*    */   }
/*    */   
/*    */   public MotionTracker getBreakTrackerFromEntity(Entity player) {
/* 84 */     return ((IEntityPlayer)player).getBreakMotionTrackerT();
/*    */   }
/*    */   
/*    */   public MotionTracker getBlockTracker(Entity player) {
/* 88 */     return ((IEntityPlayer)player).getBlockMotionTrackerT();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ExtrapolationHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */