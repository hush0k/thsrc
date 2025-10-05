/*     */ package org.junit.runners.model;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import org.junit.internal.runners.model.ReflectiveCallable;
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
/*     */ public class FrameworkMethod
/*     */   extends FrameworkMember<FrameworkMethod>
/*     */ {
/*     */   final Method fMethod;
/*     */   
/*     */   public FrameworkMethod(Method method) {
/*  25 */     this.fMethod = method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Method getMethod() {
/*  32 */     return this.fMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invokeExplosively(final Object target, Object... params) throws Throwable {
/*  42 */     return (new ReflectiveCallable()
/*     */       {
/*     */         protected Object runReflectiveCall() throws Throwable {
/*  45 */           return FrameworkMethod.this.fMethod.invoke(target, params);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  54 */     return this.fMethod.getName();
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
/*     */   public void validatePublicVoidNoArg(boolean isStatic, List<Throwable> errors) {
/*  67 */     validatePublicVoid(isStatic, errors);
/*  68 */     if ((this.fMethod.getParameterTypes()).length != 0) {
/*  69 */       errors.add(new Exception("Method " + this.fMethod.getName() + " should have no parameters"));
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
/*     */   public void validatePublicVoid(boolean isStatic, List<Throwable> errors) {
/*  82 */     if (Modifier.isStatic(this.fMethod.getModifiers()) != isStatic) {
/*  83 */       String state = isStatic ? "should" : "should not";
/*  84 */       errors.add(new Exception("Method " + this.fMethod.getName() + "() " + state + " be static"));
/*     */     } 
/*  86 */     if (!Modifier.isPublic(this.fMethod.getDeclaringClass().getModifiers()))
/*  87 */       errors.add(new Exception("Class " + this.fMethod.getDeclaringClass().getName() + " should be public")); 
/*  88 */     if (!Modifier.isPublic(this.fMethod.getModifiers()))
/*  89 */       errors.add(new Exception("Method " + this.fMethod.getName() + "() should be public")); 
/*  90 */     if (this.fMethod.getReturnType() != void.class)
/*  91 */       errors.add(new Exception("Method " + this.fMethod.getName() + "() should be void")); 
/*     */   }
/*     */   
/*     */   public void validateNoTypeParametersOnArgs(List<Throwable> errors) {
/*  95 */     (new NoGenericTypeParametersValidator(this.fMethod)).validate(errors);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShadowedBy(FrameworkMethod other) {
/* 100 */     if (!other.getName().equals(getName()))
/* 101 */       return false; 
/* 102 */     if ((other.getParameterTypes()).length != (getParameterTypes()).length)
/* 103 */       return false; 
/* 104 */     for (int i = 0; i < (other.getParameterTypes()).length; i++) {
/* 105 */       if (!other.getParameterTypes()[i].equals(getParameterTypes()[i]))
/* 106 */         return false; 
/* 107 */     }  return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 112 */     if (!FrameworkMethod.class.isInstance(obj))
/* 113 */       return false; 
/* 114 */     return ((FrameworkMethod)obj).fMethod.equals(this.fMethod);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 119 */     return this.fMethod.hashCode();
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
/*     */   @Deprecated
/*     */   public boolean producesType(Type type) {
/* 133 */     return ((getParameterTypes()).length == 0 && type instanceof Class && ((Class)type).isAssignableFrom(this.fMethod.getReturnType()));
/*     */   }
/*     */ 
/*     */   
/*     */   private Class<?>[] getParameterTypes() {
/* 138 */     return this.fMethod.getParameterTypes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[] getAnnotations() {
/* 146 */     return this.fMethod.getAnnotations();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
/* 154 */     return this.fMethod.getAnnotation(annotationType);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\model\FrameworkMethod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */