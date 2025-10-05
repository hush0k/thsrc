/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class EmptySet<T>
/*    */   implements Set<T>
/*    */ {
/*    */   public int size() {
/* 12 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(Object o) {
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<T> iterator() {
/* 27 */     return Collections.emptyIterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object[] toArray() {
/* 32 */     return new Object[0];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T1> T1[] toArray(T1[] a) {
/* 38 */     return (T1[])new Object[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean add(T t) {
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean remove(Object o) {
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsAll(Collection<?> c) {
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean addAll(Collection<? extends T> c) {
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean retainAll(Collection<?> c) {
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean removeAll(Collection<?> c) {
/* 68 */     return false;
/*    */   }
/*    */   
/*    */   public void clear() {}
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\EmptySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */