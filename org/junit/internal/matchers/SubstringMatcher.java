/*    */ package org.junit.internal.matchers;
/*    */ 
/*    */ import org.hamcrest.Description;
/*    */ 
/*    */ public abstract class SubstringMatcher
/*    */   extends TypeSafeMatcher<String> {
/*    */   protected final String substring;
/*    */   
/*    */   protected SubstringMatcher(String substring) {
/* 10 */     this.substring = substring;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesSafely(String item) {
/* 15 */     return evalSubstringOf(item);
/*    */   }
/*    */   
/*    */   public void describeTo(Description description) {
/* 19 */     description.appendText("a string ").appendText(relationship()).appendText(" ").appendValue(this.substring);
/*    */   }
/*    */   
/*    */   protected abstract boolean evalSubstringOf(String paramString);
/*    */   
/*    */   protected abstract String relationship();
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\matchers\SubstringMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */