/*    */ package org.junit.internal.matchers;
/*    */ 
/*    */ import org.hamcrest.BaseMatcher;
/*    */ import org.hamcrest.CoreMatchers;
/*    */ import org.hamcrest.Description;
/*    */ import org.hamcrest.Matcher;
/*    */ 
/*    */ public class Each
/*    */ {
/*    */   public static <T> Matcher<Iterable<T>> each(final Matcher<T> individual) {
/* 11 */     final Matcher<Iterable<T>> allItemsAre = CoreMatchers.not(IsCollectionContaining.hasItem(CoreMatchers.not(individual)));
/*    */     
/* 13 */     return (Matcher<Iterable<T>>)new BaseMatcher<Iterable<T>>() {
/*    */         public boolean matches(Object item) {
/* 15 */           return allItemsAre.matches(item);
/*    */         }
/*    */         
/*    */         public void describeTo(Description description) {
/* 19 */           description.appendText("each ");
/* 20 */           individual.describeTo(description);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\matchers\Each.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */