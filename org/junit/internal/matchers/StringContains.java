/*    */ package org.junit.internal.matchers;
/*    */ 
/*    */ import org.hamcrest.Factory;
/*    */ import org.hamcrest.Matcher;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringContains
/*    */   extends SubstringMatcher
/*    */ {
/*    */   public StringContains(String substring) {
/* 13 */     super(substring);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean evalSubstringOf(String s) {
/* 18 */     return (s.indexOf(this.substring) >= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String relationship() {
/* 23 */     return "containing";
/*    */   }
/*    */   
/*    */   @Factory
/*    */   public static Matcher<String> containsString(String substring) {
/* 28 */     return (Matcher<String>)new StringContains(substring);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\matchers\StringContains.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */