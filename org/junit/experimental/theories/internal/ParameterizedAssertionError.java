/*    */ package org.junit.experimental.theories.internal;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParameterizedAssertionError
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ParameterizedAssertionError(Throwable targetException, String methodName, Object... params) {
/* 16 */     super(String.format("%s(%s)", new Object[] { methodName, join(", ", params) }), targetException);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 21 */     return toString().equals(obj.toString());
/*    */   }
/*    */   
/*    */   public static String join(String delimiter, Object... params) {
/* 25 */     return join(delimiter, Arrays.asList(params));
/*    */   }
/*    */ 
/*    */   
/*    */   public static String join(String delimiter, Collection<Object> values) {
/* 30 */     StringBuffer buffer = new StringBuffer();
/* 31 */     Iterator<Object> iter = values.iterator();
/* 32 */     while (iter.hasNext()) {
/* 33 */       Object next = iter.next();
/* 34 */       buffer.append(stringValueOf(next));
/* 35 */       if (iter.hasNext()) {
/* 36 */         buffer.append(delimiter);
/*    */       }
/*    */     } 
/* 39 */     return buffer.toString();
/*    */   }
/*    */   
/*    */   private static String stringValueOf(Object next) {
/*    */     try {
/* 44 */       return String.valueOf(next);
/* 45 */     } catch (Throwable e) {
/* 46 */       return "[toString failed]";
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\theories\internal\ParameterizedAssertionError.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */