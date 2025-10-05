/*     */ package org.junit;
/*     */ 
/*     */ import org.hamcrest.Matcher;
/*     */ import org.hamcrest.SelfDescribing;
/*     */ import org.hamcrest.StringDescription;
/*     */ import org.junit.internal.ArrayComparisonFailure;
/*     */ import org.junit.internal.ExactComparisonCriteria;
/*     */ import org.junit.internal.InexactComparisonCriteria;
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
/*     */ public class Assert
/*     */ {
/*     */   public static void assertTrue(String message, boolean condition) {
/*  42 */     if (!condition) {
/*  43 */       fail(message);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertTrue(boolean condition) {
/*  54 */     assertTrue(null, condition);
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
/*     */   public static void assertFalse(String message, boolean condition) {
/*  68 */     assertTrue(message, !condition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertFalse(boolean condition) {
/*  79 */     assertFalse(null, condition);
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
/*     */   public static void fail(String message) {
/*  91 */     if (message == null)
/*  92 */       throw new AssertionError(); 
/*  93 */     throw new AssertionError(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fail() {
/* 100 */     fail(null);
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
/*     */   
/*     */   public static void assertEquals(String message, Object expected, Object actual) {
/* 119 */     if (expected == null && actual == null)
/*     */       return; 
/* 121 */     if (expected != null && isEquals(expected, actual))
/*     */       return; 
/* 123 */     if (expected instanceof String && actual instanceof String) {
/* 124 */       String cleanMessage = (message == null) ? "" : message;
/* 125 */       throw new ComparisonFailure(cleanMessage, (String)expected, (String)actual);
/*     */     } 
/*     */     
/* 128 */     failNotEquals(message, expected, actual);
/*     */   }
/*     */   
/*     */   private static boolean isEquals(Object expected, Object actual) {
/* 132 */     return expected.equals(actual);
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
/*     */   public static void assertEquals(Object expected, Object actual) {
/* 147 */     assertEquals((String)null, expected, actual);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertArrayEquals(String message, Object[] expecteds, Object[] actuals) throws ArrayComparisonFailure {
/* 168 */     internalArrayEquals(message, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(Object[] expecteds, Object[] actuals) {
/* 185 */     assertArrayEquals((String)null, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(String message, byte[] expecteds, byte[] actuals) throws ArrayComparisonFailure {
/* 202 */     internalArrayEquals(message, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(byte[] expecteds, byte[] actuals) {
/* 215 */     assertArrayEquals((String)null, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(String message, char[] expecteds, char[] actuals) throws ArrayComparisonFailure {
/* 232 */     internalArrayEquals(message, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(char[] expecteds, char[] actuals) {
/* 245 */     assertArrayEquals((String)null, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(String message, short[] expecteds, short[] actuals) throws ArrayComparisonFailure {
/* 262 */     internalArrayEquals(message, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(short[] expecteds, short[] actuals) {
/* 275 */     assertArrayEquals((String)null, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(String message, int[] expecteds, int[] actuals) throws ArrayComparisonFailure {
/* 292 */     internalArrayEquals(message, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(int[] expecteds, int[] actuals) {
/* 305 */     assertArrayEquals((String)null, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(String message, long[] expecteds, long[] actuals) throws ArrayComparisonFailure {
/* 322 */     internalArrayEquals(message, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(long[] expecteds, long[] actuals) {
/* 335 */     assertArrayEquals((String)null, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(String message, double[] expecteds, double[] actuals, double delta) throws ArrayComparisonFailure {
/* 352 */     (new InexactComparisonCriteria(delta)).arrayEquals(message, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(double[] expecteds, double[] actuals, double delta) {
/* 365 */     assertArrayEquals((String)null, expecteds, actuals, delta);
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
/*     */   public static void assertArrayEquals(String message, float[] expecteds, float[] actuals, float delta) throws ArrayComparisonFailure {
/* 382 */     (new InexactComparisonCriteria(delta)).arrayEquals(message, expecteds, actuals);
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
/*     */   public static void assertArrayEquals(float[] expecteds, float[] actuals, float delta) {
/* 395 */     assertArrayEquals((String)null, expecteds, actuals, delta);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static void internalArrayEquals(String message, Object expecteds, Object actuals) throws ArrayComparisonFailure {
/* 416 */     (new ExactComparisonCriteria()).arrayEquals(message, expecteds, actuals);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String message, double expected, double actual, double delta) {
/* 440 */     if (Double.compare(expected, actual) == 0)
/*     */       return; 
/* 442 */     if (Math.abs(expected - actual) > delta) {
/* 443 */       failNotEquals(message, new Double(expected), new Double(actual));
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
/*     */   public static void assertEquals(long expected, long actual) {
/* 456 */     assertEquals((String)null, expected, actual);
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
/*     */   public static void assertEquals(String message, long expected, long actual) {
/* 472 */     assertEquals(message, Long.valueOf(expected), Long.valueOf(actual));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void assertEquals(double expected, double actual) {
/* 482 */     assertEquals((String)null, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void assertEquals(String message, double expected, double actual) {
/* 493 */     fail("Use assertEquals(expected, actual, delta) to compare floating-point numbers");
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
/*     */   
/*     */   public static void assertEquals(double expected, double actual, double delta) {
/* 512 */     assertEquals(null, expected, actual, delta);
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
/*     */   public static void assertNotNull(String message, Object object) {
/* 526 */     assertTrue(message, (object != null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertNotNull(Object object) {
/* 537 */     assertNotNull(null, object);
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
/*     */   public static void assertNull(String message, Object object) {
/* 551 */     assertTrue(message, (object == null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertNull(Object object) {
/* 562 */     assertNull(null, object);
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
/*     */   public static void assertSame(String message, Object expected, Object actual) {
/* 578 */     if (expected == actual)
/*     */       return; 
/* 580 */     failNotSame(message, expected, actual);
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
/*     */   public static void assertSame(Object expected, Object actual) {
/* 593 */     assertSame(null, expected, actual);
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
/*     */   public static void assertNotSame(String message, Object unexpected, Object actual) {
/* 611 */     if (unexpected == actual) {
/* 612 */       failSame(message);
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
/*     */   public static void assertNotSame(Object unexpected, Object actual) {
/* 626 */     assertNotSame(null, unexpected, actual);
/*     */   }
/*     */   
/*     */   private static void failSame(String message) {
/* 630 */     String formatted = "";
/* 631 */     if (message != null)
/* 632 */       formatted = message + " "; 
/* 633 */     fail(formatted + "expected not same");
/*     */   }
/*     */ 
/*     */   
/*     */   private static void failNotSame(String message, Object expected, Object actual) {
/* 638 */     String formatted = "";
/* 639 */     if (message != null)
/* 640 */       formatted = message + " "; 
/* 641 */     fail(formatted + "expected same:<" + expected + "> was not:<" + actual + ">");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void failNotEquals(String message, Object expected, Object actual) {
/* 647 */     fail(format(message, expected, actual));
/*     */   }
/*     */   
/*     */   static String format(String message, Object expected, Object actual) {
/* 651 */     String formatted = "";
/* 652 */     if (message != null && !message.equals(""))
/* 653 */       formatted = message + " "; 
/* 654 */     String expectedString = String.valueOf(expected);
/* 655 */     String actualString = String.valueOf(actual);
/* 656 */     if (expectedString.equals(actualString)) {
/* 657 */       return formatted + "expected: " + formatClassAndValue(expected, expectedString) + " but was: " + formatClassAndValue(actual, actualString);
/*     */     }
/*     */ 
/*     */     
/* 661 */     return formatted + "expected:<" + expectedString + "> but was:<" + actualString + ">";
/*     */   }
/*     */ 
/*     */   
/*     */   private static String formatClassAndValue(Object value, String valueString) {
/* 666 */     String className = (value == null) ? "null" : value.getClass().getName();
/* 667 */     return className + "<" + valueString + ">";
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void assertEquals(String message, Object[] expecteds, Object[] actuals) {
/* 690 */     assertArrayEquals(message, expecteds, actuals);
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
/*     */   @Deprecated
/*     */   public static void assertEquals(Object[] expecteds, Object[] actuals) {
/* 709 */     assertArrayEquals(expecteds, actuals);
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
/*     */   public static <T> void assertThat(T actual, Matcher<T> matcher) {
/* 738 */     assertThat("", actual, matcher);
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
/*     */   public static <T> void assertThat(String reason, T actual, Matcher<T> matcher) {
/* 772 */     if (!matcher.matches(actual)) {
/* 773 */       StringDescription stringDescription = new StringDescription();
/* 774 */       stringDescription.appendText(reason);
/* 775 */       stringDescription.appendText("\nExpected: ");
/* 776 */       stringDescription.appendDescriptionOf((SelfDescribing)matcher);
/* 777 */       stringDescription.appendText("\n     got: ");
/* 778 */       stringDescription.appendValue(actual);
/* 779 */       stringDescription.appendText("\n");
/* 780 */       throw new AssertionError(stringDescription.toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\Assert.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */