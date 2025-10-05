/*     */ package org.junit.experimental.theories.internal;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.junit.experimental.theories.ParameterSignature;
/*     */ import org.junit.experimental.theories.ParameterSupplier;
/*     */ import org.junit.experimental.theories.ParametersSuppliedBy;
/*     */ import org.junit.experimental.theories.PotentialAssignment;
/*     */ import org.junit.runners.model.TestClass;
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
/*     */ public class Assignments
/*     */ {
/*     */   private List<PotentialAssignment> fAssigned;
/*     */   private final List<ParameterSignature> fUnassigned;
/*     */   private final TestClass fClass;
/*     */   
/*     */   private Assignments(List<PotentialAssignment> assigned, List<ParameterSignature> unassigned, TestClass testClass) {
/*  30 */     this.fUnassigned = unassigned;
/*  31 */     this.fAssigned = assigned;
/*  32 */     this.fClass = testClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Assignments allUnassigned(Method testMethod, TestClass testClass) throws Exception {
/*  42 */     List<ParameterSignature> signatures = ParameterSignature.signatures(testClass.getOnlyConstructor());
/*     */     
/*  44 */     signatures.addAll(ParameterSignature.signatures(testMethod));
/*  45 */     return new Assignments(new ArrayList<PotentialAssignment>(), signatures, testClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/*  50 */     return (this.fUnassigned.size() == 0);
/*     */   }
/*     */   
/*     */   public ParameterSignature nextUnassigned() {
/*  54 */     return this.fUnassigned.get(0);
/*     */   }
/*     */   
/*     */   public Assignments assignNext(PotentialAssignment source) {
/*  58 */     List<PotentialAssignment> assigned = new ArrayList<PotentialAssignment>(this.fAssigned);
/*     */     
/*  60 */     assigned.add(source);
/*     */     
/*  62 */     return new Assignments(assigned, this.fUnassigned.subList(1, this.fUnassigned.size()), this.fClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getActualValues(int start, int stop, boolean nullsOk) throws PotentialAssignment.CouldNotGenerateValueException {
/*  68 */     Object[] values = new Object[stop - start];
/*  69 */     for (int i = start; i < stop; i++) {
/*  70 */       Object value = ((PotentialAssignment)this.fAssigned.get(i)).getValue();
/*  71 */       if (value == null && !nullsOk)
/*  72 */         throw new PotentialAssignment.CouldNotGenerateValueException(); 
/*  73 */       values[i - start] = value;
/*     */     } 
/*  75 */     return values;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PotentialAssignment> potentialsForNextUnassigned() throws InstantiationException, IllegalAccessException {
/*  80 */     ParameterSignature unassigned = nextUnassigned();
/*  81 */     return getSupplier(unassigned).getValueSources(unassigned);
/*     */   }
/*     */ 
/*     */   
/*     */   public ParameterSupplier getSupplier(ParameterSignature unassigned) throws InstantiationException, IllegalAccessException {
/*  86 */     ParameterSupplier supplier = getAnnotatedSupplier(unassigned);
/*  87 */     if (supplier != null) {
/*  88 */       return supplier;
/*     */     }
/*  90 */     return new AllMembersSupplier(this.fClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public ParameterSupplier getAnnotatedSupplier(ParameterSignature unassigned) throws InstantiationException, IllegalAccessException {
/*  95 */     ParametersSuppliedBy annotation = (ParametersSuppliedBy)unassigned.findDeepAnnotation(ParametersSuppliedBy.class);
/*     */     
/*  97 */     if (annotation == null)
/*  98 */       return null; 
/*  99 */     return annotation.value().newInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] getConstructorArguments(boolean nullsOk) throws PotentialAssignment.CouldNotGenerateValueException {
/* 104 */     return getActualValues(0, getConstructorParameterCount(), nullsOk);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] getMethodArguments(boolean nullsOk) throws PotentialAssignment.CouldNotGenerateValueException {
/* 109 */     return getActualValues(getConstructorParameterCount(), this.fAssigned.size(), nullsOk);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getAllArguments(boolean nullsOk) throws PotentialAssignment.CouldNotGenerateValueException {
/* 115 */     return getActualValues(0, this.fAssigned.size(), nullsOk);
/*     */   }
/*     */   
/*     */   private int getConstructorParameterCount() {
/* 119 */     List<ParameterSignature> signatures = ParameterSignature.signatures(this.fClass.getOnlyConstructor());
/*     */     
/* 121 */     int constructorParameterCount = signatures.size();
/* 122 */     return constructorParameterCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] getArgumentStrings(boolean nullsOk) throws PotentialAssignment.CouldNotGenerateValueException {
/* 127 */     Object[] values = new Object[this.fAssigned.size()];
/* 128 */     for (int i = 0; i < values.length; i++) {
/* 129 */       values[i] = ((PotentialAssignment)this.fAssigned.get(i)).getDescription();
/*     */     }
/* 131 */     return values;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\theories\internal\Assignments.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */