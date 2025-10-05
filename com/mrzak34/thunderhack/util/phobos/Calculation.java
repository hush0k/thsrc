/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Calculation
/*    */   extends AbstractCalculation<CrystalData>
/*    */ {
/*    */   public Calculation(AutoCrystal module, List<Entity> entities, List<EntityPlayer> players, BlockPos... blackList) {
/* 18 */     super(module, entities, players, blackList);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Calculation(AutoCrystal module, List<Entity> entities, List<EntityPlayer> players, boolean breakOnly, boolean noBreak, BlockPos... blackList) {
/* 27 */     super(module, entities, players, breakOnly, noBreak, blackList);
/*    */   }
/*    */ 
/*    */   
/*    */   protected IBreakHelper<CrystalData> getBreakHelper() {
/* 32 */     return this.module.breakHelper;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\Calculation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */