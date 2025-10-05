/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Observable<T> {
/*  7 */   private final List<Observer<? super T>> observers = new LinkedList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean deactivated;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T onChange(T value) {
/* 17 */     if (!this.deactivated) {
/* 18 */       for (Observer<? super T> observer : this.observers) {
/* 19 */         observer.onChange(value);
/*    */       }
/*    */     }
/*    */     
/* 23 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addObserver(Observer<? super T> observer) {
/* 32 */     if (observer != null && !this.observers.contains(observer)) {
/* 33 */       this.observers.add(observer);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeObserver(Observer<? super T> observer) {
/* 43 */     this.observers.remove(observer);
/*    */   }
/*    */   
/*    */   public void setObserversDeactivated(boolean deactivated) {
/* 47 */     this.deactivated = deactivated;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\Observable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */