/*    */ package com.mrzak34.thunderhack.notification;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ 
/*    */ public abstract class Animation
/*    */ {
/*  7 */   public Timer timerUtil = new Timer();
/*    */   protected int duration;
/*    */   protected double endPoint;
/*    */   protected Direction direction;
/*    */   
/*    */   public Animation(int ms, double endPoint) {
/* 13 */     this.duration = ms;
/* 14 */     this.endPoint = endPoint;
/* 15 */     this.direction = Direction.FORWARDS;
/*    */   }
/*    */   
/*    */   public Animation(int ms, double endPoint, Direction direction) {
/* 19 */     this.duration = ms;
/* 20 */     this.endPoint = endPoint;
/* 21 */     this.direction = direction;
/*    */   }
/*    */   
/*    */   public boolean finished(Direction direction) {
/* 25 */     return (isDone() && this.direction.equals(direction));
/*    */   }
/*    */   
/*    */   public void reset() {
/* 29 */     this.timerUtil.reset();
/*    */   }
/*    */   
/*    */   public boolean isDone() {
/* 33 */     return this.timerUtil.passedMs(this.duration);
/*    */   }
/*    */   
/*    */   public Direction getDirection() {
/* 37 */     return this.direction;
/*    */   }
/*    */   
/*    */   public void setDirection(Direction direction) {
/* 41 */     if (this.direction != direction) {
/* 42 */       this.direction = direction;
/* 43 */       this.timerUtil.setMs(System.currentTimeMillis() - this.duration - Math.min(this.duration, this.timerUtil.getPassedTimeMs()));
/*    */     } 
/*    */   }
/*    */   protected boolean correctOutput() {
/* 47 */     return false;
/*    */   }
/*    */   
/*    */   public double getOutput() {
/* 51 */     if (this.direction == Direction.FORWARDS) {
/* 52 */       if (isDone())
/* 53 */         return this.endPoint; 
/* 54 */       return getEquation(this.timerUtil.getPassedTimeMs()) * this.endPoint;
/*    */     } 
/* 56 */     if (isDone()) return 0.0D; 
/* 57 */     if (correctOutput()) {
/* 58 */       double revTime = Math.min(this.duration, Math.max(0L, this.duration - this.timerUtil.getPassedTimeMs()));
/* 59 */       return getEquation(revTime) * this.endPoint;
/* 60 */     }  return (1.0D - getEquation(this.timerUtil.getPassedTimeMs())) * this.endPoint;
/*    */   }
/*    */   
/*    */   protected abstract double getEquation(double paramDouble);
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\notification\Animation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */