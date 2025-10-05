/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ public class CollectionUtil
/*     */ {
/*     */   public static void emptyQueue(Queue<Runnable> runnables) {
/*  18 */     emptyQueue(runnables, Runnable::run);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> void emptyQueue(Queue<T> queue, Consumer<T> onPoll) {
/*  30 */     while (!queue.isEmpty()) {
/*  31 */       T polled = queue.poll();
/*  32 */       if (polled != null) {
/*  33 */         onPoll.accept(polled);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   public static <T> List<List<T>> split(List<T> list, Predicate<T>... predicates) {
/*  54 */     List<List<T>> result = new ArrayList<>(predicates.length + 1);
/*     */     
/*  56 */     List<T> current = new ArrayList<>(list);
/*  57 */     List<T> next = new ArrayList<>();
/*  58 */     for (Predicate<T> p : predicates) {
/*  59 */       Iterator<T> it = current.iterator();
/*  60 */       while (it.hasNext()) {
/*  61 */         T t = it.next();
/*  62 */         if (p.test(t)) {
/*  63 */           next.add(t);
/*  64 */           it.remove();
/*     */         } 
/*     */       } 
/*     */       
/*  68 */       result.add(next);
/*  69 */       next = new ArrayList<>();
/*     */     } 
/*     */     
/*  72 */     result.add(current);
/*  73 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T, C extends T> C getByClass(Class<C> clazz, Collection<T> collection) {
/*  79 */     for (T t : collection) {
/*  80 */       if (clazz.isInstance(t)) {
/*  81 */         return (C)t;
/*     */       }
/*     */     } 
/*     */     
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   public static <T, R> List<T> convert(R[] array, Function<R, T> function) {
/*  89 */     List<T> result = new ArrayList<>(array.length);
/*  90 */     for (int i = 0; i < array.length; i++) {
/*  91 */       result.add(i, function.apply(array[i]));
/*     */     }
/*     */     
/*  94 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
/*  99 */     List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
/* 100 */     list.sort((Comparator)Map.Entry.comparingByValue());
/*     */     
/* 102 */     Map<K, V> result = new LinkedHashMap<>();
/* 103 */     for (Map.Entry<K, V> entry : list) {
/* 104 */       result.put(entry.getKey(), entry.getValue());
/*     */     }
/*     */     
/* 107 */     return result;
/*     */   }
/*     */   
/*     */   public static <T> T getLast(Collection<T> iterable) {
/* 111 */     T last = null;
/* 112 */     for (T t : iterable) {
/* 113 */       last = t;
/*     */     }
/*     */     
/* 116 */     return last;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\CollectionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */