/*    */ package org.junit.matchers;
/*    */ 
/*    */ import org.hamcrest.Matcher;
/*    */ import org.junit.internal.matchers.CombinableMatcher;
/*    */ import org.junit.internal.matchers.Each;
/*    */ import org.junit.internal.matchers.IsCollectionContaining;
/*    */ import org.junit.internal.matchers.StringContains;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JUnitMatchers
/*    */ {
/*    */   public static <T> Matcher<Iterable<T>> hasItem(T element) {
/* 19 */     return IsCollectionContaining.hasItem(element);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> Matcher<Iterable<T>> hasItem(Matcher<? extends T> elementMatcher) {
/* 27 */     return IsCollectionContaining.hasItem(elementMatcher);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> Matcher<Iterable<T>> hasItems(T... elements) {
/* 35 */     return IsCollectionContaining.hasItems((Object[])elements);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> Matcher<Iterable<T>> hasItems(Matcher<? extends T>... elementMatchers) {
/* 45 */     return IsCollectionContaining.hasItems((Matcher[])elementMatchers);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> Matcher<Iterable<T>> everyItem(Matcher<T> elementMatcher) {
/* 53 */     return Each.each(elementMatcher);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Matcher<String> containsString(String substring) {
/* 61 */     return StringContains.containsString(substring);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> CombinableMatcher<T> both(Matcher<T> matcher) {
/* 71 */     return new CombinableMatcher(matcher);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> CombinableMatcher<T> either(Matcher<T> matcher) {
/* 81 */     return new CombinableMatcher(matcher);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\matchers\JUnitMatchers.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */