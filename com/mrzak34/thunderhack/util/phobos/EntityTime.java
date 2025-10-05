/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class EntityTime
/*    */ {
/*  8 */   private final AtomicBoolean valid = new AtomicBoolean(true);
/*    */   private final Entity entity;
/*    */   public long time;
/*    */   
/*    */   public EntityTime(Entity entity) {
/* 13 */     this.entity = entity;
/* 14 */     this.time = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public boolean passed(long ms) {
/* 18 */     return (ms <= 0L || System.currentTimeMillis() - this.time > ms);
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 22 */     return this.entity;
/*    */   }
/*    */   
/*    */   public boolean isValid() {
/* 26 */     return this.valid.get();
/*    */   }
/*    */   
/*    */   public void setValid(boolean valid) {
/* 30 */     this.valid.set(valid);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 34 */     this.time = System.currentTimeMillis();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\EntityTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */