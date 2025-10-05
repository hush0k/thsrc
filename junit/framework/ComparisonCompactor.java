/*    */ package junit.framework;
/*    */ 
/*    */ 
/*    */ public class ComparisonCompactor
/*    */ {
/*    */   private static final String ELLIPSIS = "...";
/*    */   private static final String DELTA_END = "]";
/*    */   private static final String DELTA_START = "[";
/*    */   private int fContextLength;
/*    */   private String fExpected;
/*    */   private String fActual;
/*    */   private int fPrefix;
/*    */   private int fSuffix;
/*    */   
/*    */   public ComparisonCompactor(int contextLength, String expected, String actual) {
/* 16 */     this.fContextLength = contextLength;
/* 17 */     this.fExpected = expected;
/* 18 */     this.fActual = actual;
/*    */   }
/*    */   
/*    */   public String compact(String message) {
/* 22 */     if (this.fExpected == null || this.fActual == null || areStringsEqual()) {
/* 23 */       return Assert.format(message, this.fExpected, this.fActual);
/*    */     }
/* 25 */     findCommonPrefix();
/* 26 */     findCommonSuffix();
/* 27 */     String expected = compactString(this.fExpected);
/* 28 */     String actual = compactString(this.fActual);
/* 29 */     return Assert.format(message, expected, actual);
/*    */   }
/*    */   
/*    */   private String compactString(String source) {
/* 33 */     String result = "[" + source.substring(this.fPrefix, source.length() - this.fSuffix + 1) + "]";
/* 34 */     if (this.fPrefix > 0)
/* 35 */       result = computeCommonPrefix() + result; 
/* 36 */     if (this.fSuffix > 0)
/* 37 */       result = result + computeCommonSuffix(); 
/* 38 */     return result;
/*    */   }
/*    */   
/*    */   private void findCommonPrefix() {
/* 42 */     this.fPrefix = 0;
/* 43 */     int end = Math.min(this.fExpected.length(), this.fActual.length());
/* 44 */     for (; this.fPrefix < end && 
/* 45 */       this.fExpected.charAt(this.fPrefix) == this.fActual.charAt(this.fPrefix); this.fPrefix++);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void findCommonSuffix() {
/* 51 */     int expectedSuffix = this.fExpected.length() - 1;
/* 52 */     int actualSuffix = this.fActual.length() - 1;
/* 53 */     while (actualSuffix >= this.fPrefix && expectedSuffix >= this.fPrefix && 
/* 54 */       this.fExpected.charAt(expectedSuffix) == this.fActual.charAt(actualSuffix)) {
/*    */       actualSuffix--; expectedSuffix--;
/*    */     } 
/* 57 */     this.fSuffix = this.fExpected.length() - expectedSuffix;
/*    */   }
/*    */   
/*    */   private String computeCommonPrefix() {
/* 61 */     return ((this.fPrefix > this.fContextLength) ? "..." : "") + this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix);
/*    */   }
/*    */   
/*    */   private String computeCommonSuffix() {
/* 65 */     int end = Math.min(this.fExpected.length() - this.fSuffix + 1 + this.fContextLength, this.fExpected.length());
/* 66 */     return this.fExpected.substring(this.fExpected.length() - this.fSuffix + 1, end) + ((this.fExpected.length() - this.fSuffix + 1 < this.fExpected.length() - this.fContextLength) ? "..." : "");
/*    */   }
/*    */   
/*    */   private boolean areStringsEqual() {
/* 70 */     return this.fExpected.equals(this.fActual);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\framework\ComparisonCompactor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */