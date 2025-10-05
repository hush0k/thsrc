/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class SmartRangeUtil
/*    */ {
/*    */   public static boolean isInSmartRange(BlockPos pos, Entity entity, double rangeSq, int smartTicks) {
/* 11 */     return isInSmartRange((pos.func_177958_n() + 0.5F), (pos.func_177956_o() + 1), (pos.func_177952_p() + 0.5F), entity, rangeSq, smartTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isInSmartRange(double crystalX, double crystalY, double crystalZ, Entity entity, double rangeSq, int smartTicks) {
/* 18 */     double x = entity.field_70165_t + entity.field_70159_w * smartTicks;
/* 19 */     double y = entity.field_70163_u + entity.field_70181_x * smartTicks;
/* 20 */     double z = entity.field_70161_v + entity.field_70179_y * smartTicks;
/*    */     
/* 22 */     return (distanceSq(crystalX, crystalY, crystalZ, x, y, z) < rangeSq);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isInStrictBreakRange(double crystalX, double crystalY, double crystalZ, double rangeSq, double entityX, double entityY, double entityZ) {
/* 29 */     double height = 2.0D;
/*    */     
/* 31 */     double pY = entityY + Util.mc.field_71439_g.func_70047_e();
/* 32 */     double dY = crystalY;
/*    */ 
/*    */     
/* 35 */     if (pY > dY)
/*    */     {
/* 37 */       if (pY >= dY + 2.0D) {
/* 38 */         crystalY = dY + 2.0D;
/*    */       } else {
/* 40 */         crystalY = pY;
/*    */       } 
/*    */     }
/* 43 */     double x = crystalX - entityX;
/* 44 */     double y = crystalY - pY;
/* 45 */     double z = crystalZ - entityZ;
/*    */     
/* 47 */     return (x * x + y * y + z * z <= rangeSq);
/*    */   }
/*    */   
/*    */   public static double distanceSq(double x, double y, double z, Entity entity) {
/* 51 */     return distanceSq(x, y, z, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*    */   }
/*    */   
/*    */   public static double distanceSq(double x, double y, double z, double x1, double y1, double z1) {
/* 55 */     double xDist = x - x1;
/* 56 */     double yDist = y - y1;
/* 57 */     double zDist = z - z1;
/* 58 */     return xDist * xDist + yDist * yDist + zDist * zDist;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\SmartRangeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */