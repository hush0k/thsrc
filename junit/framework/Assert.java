/*     */ package junit.framework;
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
/*  19 */     if (!condition) {
/*  20 */       fail(message);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertTrue(boolean condition) {
/*  27 */     assertTrue(null, condition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertFalse(String message, boolean condition) {
/*  34 */     assertTrue(message, !condition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertFalse(boolean condition) {
/*  41 */     assertFalse(null, condition);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fail(String message) {
/*  47 */     if (message == null) {
/*  48 */       throw new AssertionFailedError();
/*     */     }
/*  50 */     throw new AssertionFailedError(message);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fail() {
/*  56 */     fail(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String message, Object expected, Object actual) {
/*  63 */     if (expected == null && actual == null)
/*     */       return; 
/*  65 */     if (expected != null && expected.equals(actual))
/*     */       return; 
/*  67 */     failNotEquals(message, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(Object expected, Object actual) {
/*  74 */     assertEquals((String)null, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String message, String expected, String actual) {
/*  80 */     if (expected == null && actual == null)
/*     */       return; 
/*  82 */     if (expected != null && expected.equals(actual))
/*     */       return; 
/*  84 */     String cleanMessage = (message == null) ? "" : message;
/*  85 */     throw new ComparisonFailure(cleanMessage, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String expected, String actual) {
/*  91 */     assertEquals((String)null, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String message, double expected, double actual, double delta) {
/*  99 */     if (Double.compare(expected, actual) == 0)
/*     */       return; 
/* 101 */     if (Math.abs(expected - actual) > delta) {
/* 102 */       failNotEquals(message, new Double(expected), new Double(actual));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(double expected, double actual, double delta) {
/* 109 */     assertEquals((String)null, expected, actual, delta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String message, float expected, float actual, float delta) {
/* 117 */     if (Float.compare(expected, actual) == 0)
/*     */       return; 
/* 119 */     if (Math.abs(expected - actual) > delta) {
/* 120 */       failNotEquals(message, new Float(expected), new Float(actual));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(float expected, float actual, float delta) {
/* 127 */     assertEquals((String)null, expected, actual, delta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String message, long expected, long actual) {
/* 134 */     assertEquals(message, new Long(expected), new Long(actual));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(long expected, long actual) {
/* 140 */     assertEquals((String)null, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String message, boolean expected, boolean actual) {
/* 147 */     assertEquals(message, Boolean.valueOf(expected), Boolean.valueOf(actual));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(boolean expected, boolean actual) {
/* 153 */     assertEquals((String)null, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String message, byte expected, byte actual) {
/* 160 */     assertEquals(message, new Byte(expected), new Byte(actual));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(byte expected, byte actual) {
/* 166 */     assertEquals((String)null, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String message, char expected, char actual) {
/* 173 */     assertEquals(message, new Character(expected), new Character(actual));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(char expected, char actual) {
/* 179 */     assertEquals((String)null, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String message, short expected, short actual) {
/* 186 */     assertEquals(message, new Short(expected), new Short(actual));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(short expected, short actual) {
/* 192 */     assertEquals((String)null, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(String message, int expected, int actual) {
/* 199 */     assertEquals(message, new Integer(expected), new Integer(actual));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertEquals(int expected, int actual) {
/* 205 */     assertEquals((String)null, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertNotNull(Object object) {
/* 211 */     assertNotNull(null, object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertNotNull(String message, Object object) {
/* 218 */     assertTrue(message, (object != null));
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
/* 229 */     String message = "Expected: <null> but was: " + String.valueOf(object);
/* 230 */     assertNull(message, object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertNull(String message, Object object) {
/* 237 */     assertTrue(message, (object == null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertSame(String message, Object expected, Object actual) {
/* 244 */     if (expected == actual)
/*     */       return; 
/* 246 */     failNotSame(message, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertSame(Object expected, Object actual) {
/* 253 */     assertSame(null, expected, actual);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertNotSame(String message, Object expected, Object actual) {
/* 261 */     if (expected == actual) {
/* 262 */       failSame(message);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void assertNotSame(Object expected, Object actual) {
/* 269 */     assertNotSame(null, expected, actual);
/*     */   }
/*     */   
/*     */   public static void failSame(String message) {
/* 273 */     String formatted = "";
/* 274 */     if (message != null)
/* 275 */       formatted = message + " "; 
/* 276 */     fail(formatted + "expected not same");
/*     */   }
/*     */   
/*     */   public static void failNotSame(String message, Object expected, Object actual) {
/* 280 */     String formatted = "";
/* 281 */     if (message != null)
/* 282 */       formatted = message + " "; 
/* 283 */     fail(formatted + "expected same:<" + expected + "> was not:<" + actual + ">");
/*     */   }
/*     */   
/*     */   public static void failNotEquals(String message, Object expected, Object actual) {
/* 287 */     fail(format(message, expected, actual));
/*     */   }
/*     */   
/*     */   public static String format(String message, Object expected, Object actual) {
/* 291 */     String formatted = "";
/* 292 */     if (message != null && message.length() > 0)
/* 293 */       formatted = message + " "; 
/* 294 */     return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\framework\Assert.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */