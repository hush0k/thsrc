/*     */ package org.junit.runners.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.junit.internal.runners.ErrorReportingRunner;
/*     */ import org.junit.runner.Runner;
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
/*     */ public abstract class RunnerBuilder
/*     */ {
/*  39 */   private final Set<Class<?>> parents = new HashSet<Class<?>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Runner runnerForClass(Class<?> paramClass) throws Throwable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Runner safeRunnerForClass(Class<?> testClass) {
/*     */     try {
/*  57 */       return runnerForClass(testClass);
/*  58 */     } catch (Throwable e) {
/*  59 */       return (Runner)new ErrorReportingRunner(testClass, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   Class<?> addParent(Class<?> parent) throws InitializationError {
/*  64 */     if (!this.parents.add(parent))
/*  65 */       throw new InitializationError(String.format("class '%s' (possibly indirectly) contains itself as a SuiteClass", new Object[] { parent.getName() })); 
/*  66 */     return parent;
/*     */   }
/*     */   
/*     */   void removeParent(Class<?> klass) {
/*  70 */     this.parents.remove(klass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Runner> runners(Class<?> parent, Class<?>[] children) throws InitializationError {
/*  81 */     addParent(parent);
/*     */     
/*     */     try {
/*  84 */       return runners(children);
/*     */     } finally {
/*  86 */       removeParent(parent);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Runner> runners(Class<?> parent, List<Class<?>> children) throws InitializationError {
/*  92 */     return runners(parent, (Class[])children.<Class<?>[]>toArray((Class<?>[][])new Class[0]));
/*     */   }
/*     */   
/*     */   private List<Runner> runners(Class<?>[] children) {
/*  96 */     ArrayList<Runner> runners = new ArrayList<Runner>();
/*  97 */     for (Class<?> each : children) {
/*  98 */       Runner childRunner = safeRunnerForClass(each);
/*  99 */       if (childRunner != null)
/* 100 */         runners.add(childRunner); 
/*     */     } 
/* 102 */     return runners;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\model\RunnerBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */