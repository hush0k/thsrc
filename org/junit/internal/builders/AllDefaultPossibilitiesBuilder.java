/*    */ package org.junit.internal.builders;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runners.model.RunnerBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AllDefaultPossibilitiesBuilder
/*    */   extends RunnerBuilder
/*    */ {
/*    */   private final boolean fCanUseSuiteMethod;
/*    */   
/*    */   public AllDefaultPossibilitiesBuilder(boolean canUseSuiteMethod) {
/* 16 */     this.fCanUseSuiteMethod = canUseSuiteMethod;
/*    */   }
/*    */ 
/*    */   
/*    */   public Runner runnerForClass(Class<?> testClass) throws Throwable {
/* 21 */     List<RunnerBuilder> builders = Arrays.asList(new RunnerBuilder[] { ignoredBuilder(), annotatedBuilder(), suiteMethodBuilder(), junit3Builder(), junit4Builder() });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 28 */     for (RunnerBuilder each : builders) {
/* 29 */       Runner runner = each.safeRunnerForClass(testClass);
/* 30 */       if (runner != null)
/* 31 */         return runner; 
/*    */     } 
/* 33 */     return null;
/*    */   }
/*    */   
/*    */   protected JUnit4Builder junit4Builder() {
/* 37 */     return new JUnit4Builder();
/*    */   }
/*    */   
/*    */   protected JUnit3Builder junit3Builder() {
/* 41 */     return new JUnit3Builder();
/*    */   }
/*    */   
/*    */   protected AnnotatedBuilder annotatedBuilder() {
/* 45 */     return new AnnotatedBuilder(this);
/*    */   }
/*    */   
/*    */   protected IgnoredBuilder ignoredBuilder() {
/* 49 */     return new IgnoredBuilder();
/*    */   }
/*    */   
/*    */   protected RunnerBuilder suiteMethodBuilder() {
/* 53 */     if (this.fCanUseSuiteMethod)
/* 54 */       return new SuiteMethodBuilder(); 
/* 55 */     return new NullBuilder();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\builders\AllDefaultPossibilitiesBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */