/*     */ package org.junit.experimental.theories.internal;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.junit.experimental.theories.DataPoint;
/*     */ import org.junit.experimental.theories.DataPoints;
/*     */ import org.junit.experimental.theories.ParameterSignature;
/*     */ import org.junit.experimental.theories.ParameterSupplier;
/*     */ import org.junit.experimental.theories.PotentialAssignment;
/*     */ import org.junit.runners.model.FrameworkMethod;
/*     */ import org.junit.runners.model.TestClass;
/*     */ 
/*     */ 
/*     */ public class AllMembersSupplier
/*     */   extends ParameterSupplier
/*     */ {
/*     */   private final TestClass fClass;
/*     */   
/*     */   static class MethodParameterValue
/*     */     extends PotentialAssignment
/*     */   {
/*     */     private final FrameworkMethod fMethod;
/*     */     
/*     */     private MethodParameterValue(FrameworkMethod dataPointMethod) {
/*  28 */       this.fMethod = dataPointMethod;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getValue() throws PotentialAssignment.CouldNotGenerateValueException {
/*     */       try {
/*  34 */         return this.fMethod.invokeExplosively(null, new Object[0]);
/*  35 */       } catch (IllegalArgumentException e) {
/*  36 */         throw new RuntimeException("unexpected: argument length is checked");
/*     */       }
/*  38 */       catch (IllegalAccessException e) {
/*  39 */         throw new RuntimeException("unexpected: getMethods returned an inaccessible method");
/*     */       }
/*  41 */       catch (Throwable e) {
/*  42 */         throw new PotentialAssignment.CouldNotGenerateValueException();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getDescription() throws PotentialAssignment.CouldNotGenerateValueException {
/*  49 */       return this.fMethod.getName();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AllMembersSupplier(TestClass type) {
/*  59 */     this.fClass = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PotentialAssignment> getValueSources(ParameterSignature sig) {
/*  64 */     List<PotentialAssignment> list = new ArrayList<PotentialAssignment>();
/*     */     
/*  66 */     addFields(sig, list);
/*  67 */     addSinglePointMethods(sig, list);
/*  68 */     addMultiPointMethods(list);
/*     */     
/*  70 */     return list;
/*     */   }
/*     */   
/*     */   private void addMultiPointMethods(List<PotentialAssignment> list) {
/*  74 */     for (FrameworkMethod dataPointsMethod : this.fClass.getAnnotatedMethods(DataPoints.class)) {
/*     */       
/*     */       try {
/*  77 */         addArrayValues(dataPointsMethod.getName(), list, dataPointsMethod.invokeExplosively(null, new Object[0]));
/*  78 */       } catch (Throwable e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addSinglePointMethods(ParameterSignature sig, List<PotentialAssignment> list) {
/*  86 */     for (FrameworkMethod dataPointMethod : this.fClass.getAnnotatedMethods(DataPoint.class)) {
/*     */       
/*  88 */       Class<?> type = sig.getType();
/*  89 */       if (dataPointMethod.producesType(type)) {
/*  90 */         list.add(new MethodParameterValue(dataPointMethod));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addFields(ParameterSignature sig, List<PotentialAssignment> list) {
/*  96 */     for (Field field : this.fClass.getJavaClass().getFields()) {
/*  97 */       if (Modifier.isStatic(field.getModifiers())) {
/*  98 */         Class<?> type = field.getType();
/*  99 */         if (sig.canAcceptArrayType(type) && field.getAnnotation(DataPoints.class) != null) {
/*     */           
/* 101 */           addArrayValues(field.getName(), list, getStaticFieldValue(field));
/* 102 */         } else if (sig.canAcceptType(type) && field.getAnnotation(DataPoint.class) != null) {
/*     */           
/* 104 */           list.add(PotentialAssignment.forValue(field.getName(), getStaticFieldValue(field)));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addArrayValues(String name, List<PotentialAssignment> list, Object array) {
/* 112 */     for (int i = 0; i < Array.getLength(array); i++)
/* 113 */       list.add(PotentialAssignment.forValue(name + "[" + i + "]", Array.get(array, i))); 
/*     */   }
/*     */   
/*     */   private Object getStaticFieldValue(Field field) {
/*     */     try {
/* 118 */       return field.get(null);
/* 119 */     } catch (IllegalArgumentException e) {
/* 120 */       throw new RuntimeException("unexpected: field from getClass doesn't exist on object");
/*     */     }
/* 122 */     catch (IllegalAccessException e) {
/* 123 */       throw new RuntimeException("unexpected: getFields returned an inaccessible field");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\theories\internal\AllMembersSupplier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */