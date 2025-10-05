/*    */ package org.junit.internal.matchers;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import org.hamcrest.BaseMatcher;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TypeSafeMatcher<T>
/*    */   extends BaseMatcher<T>
/*    */ {
/*    */   private Class<?> expectedType;
/*    */   
/*    */   public abstract boolean matchesSafely(T paramT);
/*    */   
/*    */   protected TypeSafeMatcher() {
/* 24 */     this.expectedType = findExpectedType(getClass());
/*    */   }
/*    */   
/*    */   private static Class<?> findExpectedType(Class<?> fromClass) {
/* 28 */     for (Class<?> c = fromClass; c != Object.class; c = c.getSuperclass()) {
/* 29 */       for (Method method : c.getDeclaredMethods()) {
/* 30 */         if (isMatchesSafelyMethod(method)) {
/* 31 */           return method.getParameterTypes()[0];
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 36 */     throw new Error("Cannot determine correct type for matchesSafely() method.");
/*    */   }
/*    */   
/*    */   private static boolean isMatchesSafelyMethod(Method method) {
/* 40 */     return (method.getName().equals("matchesSafely") && (method.getParameterTypes()).length == 1 && !method.isSynthetic());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected TypeSafeMatcher(Class<T> expectedType) {
/* 46 */     this.expectedType = expectedType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean matches(Object item) {
/* 56 */     return (item != null && this.expectedType.isInstance(item) && matchesSafely((T)item));
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\matchers\TypeSafeMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */