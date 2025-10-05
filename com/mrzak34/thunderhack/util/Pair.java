/*    */ package com.mrzak34.thunderhack.util;
/*    */ 
/*    */ public class Pair<T, S>
/*    */ {
/*    */   T key;
/*    */   S value;
/*    */   
/*    */   public Pair(T key, S value) {
/*  9 */     this.key = key;
/* 10 */     this.value = value;
/*    */   }
/*    */   
/*    */   public T getKey() {
/* 14 */     return this.key;
/*    */   }
/*    */   
/*    */   public void setKey(T key) {
/* 18 */     this.key = key;
/*    */   }
/*    */   
/*    */   public S getValue() {
/* 22 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(S value) {
/* 26 */     this.value = value;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\Pair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */