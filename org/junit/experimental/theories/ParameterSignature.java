/*    */ package org.junit.experimental.theories;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ParameterSignature {
/*    */   private final Class<?> type;
/*    */   private final Annotation[] annotations;
/*    */   
/*    */   public static ArrayList<ParameterSignature> signatures(Method method) {
/* 15 */     return signatures(method.getParameterTypes(), method.getParameterAnnotations());
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<ParameterSignature> signatures(Constructor<?> constructor) {
/* 20 */     return signatures(constructor.getParameterTypes(), constructor.getParameterAnnotations());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static ArrayList<ParameterSignature> signatures(Class<?>[] parameterTypes, Annotation[][] parameterAnnotations) {
/* 26 */     ArrayList<ParameterSignature> sigs = new ArrayList<ParameterSignature>();
/* 27 */     for (int i = 0; i < parameterTypes.length; i++) {
/* 28 */       sigs.add(new ParameterSignature(parameterTypes[i], parameterAnnotations[i]));
/*    */     }
/*    */     
/* 31 */     return sigs;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private ParameterSignature(Class<?> type, Annotation[] annotations) {
/* 39 */     this.type = type;
/* 40 */     this.annotations = annotations;
/*    */   }
/*    */   
/*    */   public boolean canAcceptType(Class<?> candidate) {
/* 44 */     return this.type.isAssignableFrom(candidate);
/*    */   }
/*    */   
/*    */   public Class<?> getType() {
/* 48 */     return this.type;
/*    */   }
/*    */   
/*    */   public List<Annotation> getAnnotations() {
/* 52 */     return Arrays.asList(this.annotations);
/*    */   }
/*    */   
/*    */   public boolean canAcceptArrayType(Class<?> type) {
/* 56 */     return (type.isArray() && canAcceptType(type.getComponentType()));
/*    */   }
/*    */   
/*    */   public boolean hasAnnotation(Class<? extends Annotation> type) {
/* 60 */     return (getAnnotation(type) != null);
/*    */   }
/*    */   
/*    */   public <T extends Annotation> T findDeepAnnotation(Class<T> annotationType) {
/* 64 */     Annotation[] annotations2 = this.annotations;
/* 65 */     return findDeepAnnotation(annotations2, annotationType, 3);
/*    */   }
/*    */ 
/*    */   
/*    */   private <T extends Annotation> T findDeepAnnotation(Annotation[] annotations, Class<T> annotationType, int depth) {
/* 70 */     if (depth == 0)
/* 71 */       return null; 
/* 72 */     for (Annotation each : annotations) {
/* 73 */       if (annotationType.isInstance(each))
/* 74 */         return annotationType.cast(each); 
/* 75 */       Annotation candidate = findDeepAnnotation(each.annotationType().getAnnotations(), annotationType, depth - 1);
/*    */       
/* 77 */       if (candidate != null) {
/* 78 */         return annotationType.cast(candidate);
/*    */       }
/*    */     } 
/* 81 */     return null;
/*    */   }
/*    */   
/*    */   public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
/* 85 */     for (Annotation each : getAnnotations()) {
/* 86 */       if (annotationType.isInstance(each))
/* 87 */         return annotationType.cast(each); 
/* 88 */     }  return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\theories\ParameterSignature.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */