/*    */ package com.mrzak34.thunderhack.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.DimensionType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Trace
/*    */ {
/*    */   private String name;
/*    */   private int index;
/*    */   private Vec3d pos;
/*    */   private List<TracePos> trace;
/*    */   private DimensionType type;
/*    */   
/*    */   public Trace(int index, String name, DimensionType type, Vec3d pos, List<TracePos> trace) {
/* 20 */     this.index = index;
/* 21 */     this.name = name;
/* 22 */     this.type = type;
/* 23 */     this.pos = pos;
/* 24 */     this.trace = trace;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 28 */     return this.index;
/*    */   }
/*    */   
/*    */   public void setIndex(int index) {
/* 32 */     this.index = index;
/*    */   }
/*    */   
/*    */   public DimensionType getType() {
/* 36 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(DimensionType type) {
/* 40 */     this.type = type;
/*    */   }
/*    */   
/*    */   public List<TracePos> getTrace() {
/* 44 */     return this.trace;
/*    */   }
/*    */   
/*    */   public void setTrace(List<TracePos> trace) {
/* 48 */     this.trace = trace;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 52 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 56 */     this.name = name;
/*    */   }
/*    */   
/*    */   public Vec3d getPos() {
/* 60 */     return this.pos;
/*    */   }
/*    */   
/*    */   public void setPos(Vec3d pos) {
/* 64 */     this.pos = pos;
/*    */   }
/*    */   
/*    */   public static class TracePos {
/*    */     private final Vec3d pos;
/* 69 */     private final Timer stopWatch = new Timer();
/*    */     private long time;
/*    */     
/*    */     public TracePos(Vec3d pos) {
/* 73 */       this.pos = pos;
/* 74 */       this.stopWatch.reset();
/*    */     }
/*    */     
/*    */     public TracePos(Vec3d pos, long time) {
/* 78 */       this.pos = pos;
/* 79 */       this.stopWatch.reset();
/* 80 */       this.time = time;
/*    */     }
/*    */     
/*    */     public Vec3d getPos() {
/* 84 */       return this.pos;
/*    */     }
/*    */     
/*    */     public boolean shouldRemoveTrace() {
/* 88 */       return this.stopWatch.passedMs(2000L);
/*    */     }
/*    */     
/*    */     public long getTime() {
/* 92 */       return this.time;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\Trace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */