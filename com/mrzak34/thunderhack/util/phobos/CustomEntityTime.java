/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class CustomEntityTime extends EntityTime {
/*    */   private final long customTime;
/*    */   
/*    */   public CustomEntityTime(Entity entity, long customTime) {
/*  9 */     super(entity);
/* 10 */     this.customTime = customTime;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean passed(long ms) {
/* 15 */     return (System.currentTimeMillis() - this.time > this.customTime);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\CustomEntityTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */