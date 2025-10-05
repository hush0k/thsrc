/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ public class MutableWrapper<T> {
/*    */   protected T value;
/*    */   
/*    */   public MutableWrapper() {
/*  7 */     this(null);
/*    */   }
/*    */   
/*    */   public MutableWrapper(T value) {
/* 11 */     this.value = value;
/*    */   }
/*    */   
/*    */   public T get() {
/* 15 */     return this.value;
/*    */   }
/*    */   
/*    */   public void set(T value) {
/* 19 */     this.value = value;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\MutableWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */