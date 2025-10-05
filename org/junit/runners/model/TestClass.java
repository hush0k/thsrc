/*     */ package org.junit.runners.model;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.junit.Assert;
/*     */ import org.junit.Before;
/*     */ import org.junit.BeforeClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestClass
/*     */ {
/*     */   private final Class<?> fClass;
/*  24 */   private Map<Class<?>, List<FrameworkMethod>> fMethodsForAnnotations = new HashMap<Class<?>, List<FrameworkMethod>>();
/*     */   
/*  26 */   private Map<Class<?>, List<FrameworkField>> fFieldsForAnnotations = new HashMap<Class<?>, List<FrameworkField>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TestClass(Class<?> klass) {
/*  35 */     this.fClass = klass;
/*  36 */     if (klass != null && (klass.getConstructors()).length > 1) {
/*  37 */       throw new IllegalArgumentException("Test class can only have one constructor");
/*     */     }
/*     */     
/*  40 */     for (Class<?> eachClass : getSuperClasses(this.fClass)) {
/*  41 */       for (Method eachMethod : eachClass.getDeclaredMethods()) {
/*  42 */         addToAnnotationLists(new FrameworkMethod(eachMethod), this.fMethodsForAnnotations);
/*     */       }
/*  44 */       for (Field eachField : eachClass.getDeclaredFields()) {
/*  45 */         addToAnnotationLists(new FrameworkField(eachField), this.fFieldsForAnnotations);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private <T extends FrameworkMember<T>> void addToAnnotationLists(T member, Map<Class<?>, List<T>> map) {
/*  52 */     for (Annotation each : member.getAnnotations()) {
/*  53 */       Class<? extends Annotation> type = each.annotationType();
/*  54 */       List<T> members = getAnnotatedMembers(map, type);
/*  55 */       if (member.isShadowedBy(members))
/*     */         return; 
/*  57 */       if (runsTopToBottom(type)) {
/*  58 */         members.add(0, member);
/*     */       } else {
/*  60 */         members.add(member);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<FrameworkMethod> getAnnotatedMethods(Class<? extends Annotation> annotationClass) {
/*  70 */     return getAnnotatedMembers(this.fMethodsForAnnotations, annotationClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<FrameworkField> getAnnotatedFields(Class<? extends Annotation> annotationClass) {
/*  79 */     return getAnnotatedMembers(this.fFieldsForAnnotations, annotationClass);
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> List<T> getAnnotatedMembers(Map<Class<?>, List<T>> map, Class<? extends Annotation> type) {
/*  84 */     if (!map.containsKey(type))
/*  85 */       map.put(type, new ArrayList<T>()); 
/*  86 */     return map.get(type);
/*     */   }
/*     */   
/*     */   private boolean runsTopToBottom(Class<? extends Annotation> annotation) {
/*  90 */     return (annotation.equals(Before.class) || annotation.equals(BeforeClass.class));
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Class<?>> getSuperClasses(Class<?> testClass) {
/*  95 */     ArrayList<Class<?>> results = new ArrayList<Class<?>>();
/*  96 */     Class<?> current = testClass;
/*  97 */     while (current != null) {
/*  98 */       results.add(current);
/*  99 */       current = current.getSuperclass();
/*     */     } 
/* 101 */     return results;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getJavaClass() {
/* 108 */     return this.fClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 115 */     if (this.fClass == null)
/* 116 */       return "null"; 
/* 117 */     return this.fClass.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Constructor<?> getOnlyConstructor() {
/* 126 */     Constructor[] arrayOfConstructor = (Constructor[])this.fClass.getConstructors();
/* 127 */     Assert.assertEquals(1L, arrayOfConstructor.length);
/* 128 */     return arrayOfConstructor[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[] getAnnotations() {
/* 135 */     if (this.fClass == null)
/* 136 */       return new Annotation[0]; 
/* 137 */     return this.fClass.getAnnotations();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> List<T> getAnnotatedFieldValues(Object test, Class<? extends Annotation> annotationClass, Class<T> valueClass) {
/* 142 */     List<T> results = new ArrayList<T>();
/* 143 */     for (FrameworkField each : getAnnotatedFields(annotationClass)) {
/*     */       try {
/* 145 */         Object fieldValue = each.get(test);
/* 146 */         if (valueClass.isInstance(fieldValue))
/* 147 */           results.add(valueClass.cast(fieldValue)); 
/* 148 */       } catch (IllegalAccessException e) {
/* 149 */         throw new RuntimeException("How did getFields return a field we couldn't access?", e);
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return results;
/*     */   }
/*     */   
/*     */   public boolean isANonStaticInnerClass() {
/* 157 */     return (this.fClass.isMemberClass() && !Modifier.isStatic(this.fClass.getModifiers()));
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\model\TestClass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */