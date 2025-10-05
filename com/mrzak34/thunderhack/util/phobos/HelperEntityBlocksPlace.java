/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.mixin.ducks.IEntityPlayer;
/*    */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ 
/*    */ public class HelperEntityBlocksPlace {
/*    */   private final AutoCrystal module;
/*    */   
/*    */   public HelperEntityBlocksPlace(AutoCrystal module) {
/* 12 */     this.module = module;
/*    */   }
/*    */   
/*    */   public boolean blocksBlock(AxisAlignedBB bb, Entity entity) {
/* 16 */     if (entity instanceof IEntityPlayer && ((Integer)this.module.blockExtrapol
/* 17 */       .getValue()).intValue() != 0) {
/*    */       
/* 19 */       MotionTracker tracker = ((IEntityPlayer)entity).getBlockMotionTrackerT();
/* 20 */       if (tracker != null && tracker.active) {
/* 21 */         switch ((AutoCrystal.BlockExtrapolationMode)this.module.blockExtraMode.getValue()) {
/*    */           case Extrapolated:
/* 23 */             return tracker.func_174813_aQ().func_72326_a(bb);
/*    */           case Pessimistic:
/* 25 */             return (tracker.func_174813_aQ().func_72326_a(bb) || entity
/* 26 */               .func_174813_aQ().func_72326_a(bb));
/*    */         } 
/*    */         
/* 29 */         return (tracker.func_174813_aQ().func_72326_a(bb) && entity
/* 30 */           .func_174813_aQ().func_72326_a(bb));
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 35 */     return entity.func_174813_aQ().func_72326_a(bb);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\HelperEntityBlocksPlace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */