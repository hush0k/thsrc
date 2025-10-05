/*    */ package com.mrzak34.thunderhack.util;
/*    */ 
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ 
/*    */ public class TimeVec3d extends Vec3d {
/*    */   private final long time;
/*    */   
/*    */   public TimeVec3d(double xIn, double yIn, double zIn, long time) {
/* 10 */     super(xIn, yIn, zIn);
/* 11 */     this.time = time;
/*    */   }
/*    */   
/*    */   public TimeVec3d(Vec3i vector, long time) {
/* 15 */     super(vector);
/* 16 */     this.time = time;
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 20 */     return this.time;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\TimeVec3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */