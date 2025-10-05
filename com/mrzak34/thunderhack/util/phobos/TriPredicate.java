/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface TriPredicate<T, U, V>
/*    */ {
/*    */   default TriPredicate<T, U, V> and(TriPredicate<? super T, ? super U, ? super V> other) {
/* 21 */     Objects.requireNonNull(other);
/* 22 */     return (t, u, v) -> (test((T)t, (U)u, (V)v) && other.test(t, u, v));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default TriPredicate<T, U, V> negate() {
/* 29 */     return (t, u, v) -> !test((T)t, (U)u, (V)v);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default TriPredicate<T, U, V> or(TriPredicate<? super T, ? super U, ? super V> other) {
/* 36 */     Objects.requireNonNull(other);
/* 37 */     return (t, u, v) -> (test((T)t, (U)u, (V)v) || other.test(t, u, v));
/*    */   }
/*    */   
/*    */   boolean test(T paramT, U paramU, V paramV);
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\TriPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */