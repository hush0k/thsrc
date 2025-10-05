/*     */ package org.junit.runners;
/*     */ 
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Inherited;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.util.List;
/*     */ import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runner.Runner;
/*     */ import org.junit.runner.notification.RunNotifier;
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
/*     */ public class Suite
/*     */   extends ParentRunner<Runner>
/*     */ {
/*     */   private final List<Runner> fRunners;
/*     */   
/*     */   public static Runner emptySuite() {
/*     */     try {
/*  30 */       return new Suite((Class)null, new Class[0]);
/*  31 */     } catch (InitializationError e) {
/*  32 */       throw new RuntimeException("This shouldn't be possible");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?>[] getAnnotatedClasses(Class<?> klass) throws InitializationError {
/*  51 */     SuiteClasses annotation = klass.<SuiteClasses>getAnnotation(SuiteClasses.class);
/*  52 */     if (annotation == null)
/*  53 */       throw new InitializationError(String.format("class '%s' must have a SuiteClasses annotation", new Object[] { klass.getName() })); 
/*  54 */     return annotation.value();
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
/*     */   public Suite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
/*  67 */     this(builder, klass, getAnnotatedClasses(klass));
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
/*     */   public Suite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
/*  79 */     this((Class<?>)null, builder.runners(null, classes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Suite(Class<?> klass, Class<?>[] suiteClasses) throws InitializationError {
/*  89 */     this((RunnerBuilder)new AllDefaultPossibilitiesBuilder(true), klass, suiteClasses);
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
/*     */   protected Suite(RunnerBuilder builder, Class<?> klass, Class<?>[] suiteClasses) throws InitializationError {
/* 101 */     this(klass, builder.runners(klass, suiteClasses));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Suite(Class<?> klass, List<Runner> runners) throws InitializationError {
/* 112 */     super(klass);
/* 113 */     this.fRunners = runners;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<Runner> getChildren() {
/* 118 */     return this.fRunners;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Description describeChild(Runner child) {
/* 123 */     return child.getDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void runChild(Runner runner, RunNotifier notifier) {
/* 128 */     runner.run(notifier);
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   @Target({ElementType.TYPE})
/*     */   @Inherited
/*     */   public static @interface SuiteClasses {
/*     */     Class<?>[] value();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\Suite.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */