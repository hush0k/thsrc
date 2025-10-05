/*     */ package org.junit.runner;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
/*     */ import org.junit.internal.requests.ClassRequest;
/*     */ import org.junit.internal.requests.FilterRequest;
/*     */ import org.junit.internal.requests.SortingRequest;
/*     */ import org.junit.internal.runners.ErrorReportingRunner;
/*     */ import org.junit.runner.manipulation.Filter;
/*     */ import org.junit.runners.model.InitializationError;
/*     */ import org.junit.runners.model.RunnerBuilder;
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
/*     */ public abstract class Request
/*     */ {
/*     */   public static Request method(Class<?> clazz, String methodName) {
/*  35 */     Description method = Description.createTestDescription(clazz, methodName);
/*  36 */     return aClass(clazz).filterWith(method);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request aClass(Class<?> clazz) {
/*  46 */     return (Request)new ClassRequest(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request classWithoutSuiteMethod(Class<?> clazz) {
/*  56 */     return (Request)new ClassRequest(clazz, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request classes(Computer computer, Class<?>... classes) {
/*     */     try {
/*  68 */       AllDefaultPossibilitiesBuilder builder = new AllDefaultPossibilitiesBuilder(true);
/*  69 */       Runner suite = computer.getSuite((RunnerBuilder)builder, classes);
/*  70 */       return runner(suite);
/*  71 */     } catch (InitializationError e) {
/*  72 */       throw new RuntimeException("Bug in saff's brain: Suite constructor, called as above, should always complete");
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
/*     */   public static Request classes(Class<?>... classes) {
/*  84 */     return classes(JUnitCore.defaultComputer(), classes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Request errorReport(Class<?> klass, Throwable cause) {
/*  93 */     return runner((Runner)new ErrorReportingRunner(klass, cause));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request runner(final Runner runner) {
/* 101 */     return new Request()
/*     */       {
/*     */         public Runner getRunner() {
/* 104 */           return runner;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Runner getRunner();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request filterWith(Filter filter) {
/* 122 */     return (Request)new FilterRequest(this, filter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request filterWith(Description desiredDescription) {
/* 132 */     return filterWith(Filter.matchMethodDescription(desiredDescription));
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
/*     */   public Request sortWith(Comparator<Description> comparator) {
/* 159 */     return (Request)new SortingRequest(this, comparator);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runner\Request.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */