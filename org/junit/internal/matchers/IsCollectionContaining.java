/*    */ package org.junit.internal.matchers;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.hamcrest.Description;
/*    */ import org.hamcrest.Factory;
/*    */ import org.hamcrest.Matcher;
/*    */ import org.hamcrest.SelfDescribing;
/*    */ import org.hamcrest.core.AllOf;
/*    */ import org.hamcrest.core.IsEqual;
/*    */ 
/*    */ public class IsCollectionContaining<T>
/*    */   extends TypeSafeMatcher<Iterable<T>>
/*    */ {
/*    */   private final Matcher<? extends T> elementMatcher;
/*    */   
/*    */   public IsCollectionContaining(Matcher<? extends T> elementMatcher) {
/* 18 */     this.elementMatcher = elementMatcher;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesSafely(Iterable<T> collection) {
/* 23 */     for (T item : collection) {
/* 24 */       if (this.elementMatcher.matches(item)) {
/* 25 */         return true;
/*    */       }
/*    */     } 
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public void describeTo(Description description) {
/* 32 */     description.appendText("a collection containing ").appendDescriptionOf((SelfDescribing)this.elementMatcher);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Factory
/*    */   public static <T> Matcher<Iterable<T>> hasItem(Matcher<? extends T> elementMatcher) {
/* 39 */     return (Matcher)new IsCollectionContaining<T>(elementMatcher);
/*    */   }
/*    */   
/*    */   @Factory
/*    */   public static <T> Matcher<Iterable<T>> hasItem(T element) {
/* 44 */     return hasItem(IsEqual.equalTo(element));
/*    */   }
/*    */   
/*    */   @Factory
/*    */   public static <T> Matcher<Iterable<T>> hasItems(Matcher<? extends T>... elementMatchers) {
/* 49 */     Collection<Matcher<? extends Iterable<T>>> all = new ArrayList<Matcher<? extends Iterable<T>>>(elementMatchers.length);
/*    */     
/* 51 */     for (Matcher<? extends T> elementMatcher : elementMatchers) {
/* 52 */       all.add(hasItem(elementMatcher));
/*    */     }
/* 54 */     return AllOf.allOf(all);
/*    */   }
/*    */   
/*    */   @Factory
/*    */   public static <T> Matcher<Iterable<T>> hasItems(T... elements) {
/* 59 */     Collection<Matcher<? extends Iterable<T>>> all = new ArrayList<Matcher<? extends Iterable<T>>>(elements.length);
/*    */     
/* 61 */     for (T element : elements) {
/* 62 */       all.add(hasItem(element));
/*    */     }
/* 64 */     return AllOf.allOf(all);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\matchers\IsCollectionContaining.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */