/*     */ package org.junit;
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
/*     */ 
/*     */ 
/*     */ public class ComparisonFailure
/*     */   extends AssertionError
/*     */ {
/*     */   private static final int MAX_CONTEXT_LENGTH = 20;
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String fExpected;
/*     */   private String fActual;
/*     */   
/*     */   public ComparisonFailure(String message, String expected, String actual) {
/*  28 */     super(message);
/*  29 */     this.fExpected = expected;
/*  30 */     this.fActual = actual;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  41 */     return (new ComparisonCompactor(20, this.fExpected, this.fActual)).compact(super.getMessage());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getActual() {
/*  49 */     return this.fActual;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpected() {
/*  56 */     return this.fExpected;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ComparisonCompactor
/*     */   {
/*     */     private static final String ELLIPSIS = "...";
/*     */     
/*     */     private static final String DELTA_END = "]";
/*     */     
/*     */     private static final String DELTA_START = "[";
/*     */     
/*     */     private int fContextLength;
/*     */     
/*     */     private String fExpected;
/*     */     
/*     */     private String fActual;
/*     */     
/*     */     private int fPrefix;
/*     */     
/*     */     private int fSuffix;
/*     */ 
/*     */     
/*     */     public ComparisonCompactor(int contextLength, String expected, String actual) {
/*  81 */       this.fContextLength = contextLength;
/*  82 */       this.fExpected = expected;
/*  83 */       this.fActual = actual;
/*     */     }
/*     */     
/*     */     private String compact(String message) {
/*  87 */       if (this.fExpected == null || this.fActual == null || areStringsEqual()) {
/*  88 */         return Assert.format(message, this.fExpected, this.fActual);
/*     */       }
/*  90 */       findCommonPrefix();
/*  91 */       findCommonSuffix();
/*  92 */       String expected = compactString(this.fExpected);
/*  93 */       String actual = compactString(this.fActual);
/*  94 */       return Assert.format(message, expected, actual);
/*     */     }
/*     */     
/*     */     private String compactString(String source) {
/*  98 */       String result = "[" + source.substring(this.fPrefix, source.length() - this.fSuffix + 1) + "]";
/*  99 */       if (this.fPrefix > 0)
/* 100 */         result = computeCommonPrefix() + result; 
/* 101 */       if (this.fSuffix > 0)
/* 102 */         result = result + computeCommonSuffix(); 
/* 103 */       return result;
/*     */     }
/*     */     
/*     */     private void findCommonPrefix() {
/* 107 */       this.fPrefix = 0;
/* 108 */       int end = Math.min(this.fExpected.length(), this.fActual.length());
/* 109 */       for (; this.fPrefix < end && 
/* 110 */         this.fExpected.charAt(this.fPrefix) == this.fActual.charAt(this.fPrefix); this.fPrefix++);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void findCommonSuffix() {
/* 116 */       int expectedSuffix = this.fExpected.length() - 1;
/* 117 */       int actualSuffix = this.fActual.length() - 1;
/* 118 */       while (actualSuffix >= this.fPrefix && expectedSuffix >= this.fPrefix && 
/* 119 */         this.fExpected.charAt(expectedSuffix) == this.fActual.charAt(actualSuffix)) {
/*     */         actualSuffix--; expectedSuffix--;
/*     */       } 
/* 122 */       this.fSuffix = this.fExpected.length() - expectedSuffix;
/*     */     }
/*     */     
/*     */     private String computeCommonPrefix() {
/* 126 */       return ((this.fPrefix > this.fContextLength) ? "..." : "") + this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix);
/*     */     }
/*     */     
/*     */     private String computeCommonSuffix() {
/* 130 */       int end = Math.min(this.fExpected.length() - this.fSuffix + 1 + this.fContextLength, this.fExpected.length());
/* 131 */       return this.fExpected.substring(this.fExpected.length() - this.fSuffix + 1, end) + ((this.fExpected.length() - this.fSuffix + 1 < this.fExpected.length() - this.fContextLength) ? "..." : "");
/*     */     }
/*     */     
/*     */     private boolean areStringsEqual() {
/* 135 */       return this.fExpected.equals(this.fActual);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\ComparisonFailure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */