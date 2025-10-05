/*     */ package junit.framework;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
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
/*     */ public abstract class TestCase
/*     */   extends Assert
/*     */   implements Test
/*     */ {
/*     */   private String fName;
/*     */   
/*     */   public TestCase() {
/*  87 */     this.fName = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TestCase(String name) {
/*  93 */     this.fName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int countTestCases() {
/*  99 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TestResult createResult() {
/* 107 */     return new TestResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TestResult run() {
/* 116 */     TestResult result = createResult();
/* 117 */     run(result);
/* 118 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run(TestResult result) {
/* 124 */     result.run(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void runBare() throws Throwable {
/* 131 */     Throwable exception = null;
/* 132 */     setUp();
/*     */     try {
/* 134 */       runTest();
/* 135 */     } catch (Throwable running) {
/* 136 */       exception = running;
/*     */     } finally {
/*     */       
/*     */       try {
/* 140 */         tearDown();
/* 141 */       } catch (Throwable tearingDown) {
/* 142 */         if (exception == null) exception = tearingDown; 
/*     */       } 
/*     */     } 
/* 145 */     if (exception != null) throw exception;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runTest() throws Throwable {
/* 152 */     assertNotNull("TestCase.fName cannot be null", this.fName);
/* 153 */     Method runMethod = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 159 */       runMethod = getClass().getMethod(this.fName, (Class[])null);
/* 160 */     } catch (NoSuchMethodException e) {
/* 161 */       fail("Method \"" + this.fName + "\" not found");
/*     */     } 
/* 163 */     if (!Modifier.isPublic(runMethod.getModifiers())) {
/* 164 */       fail("Method \"" + this.fName + "\" should be public");
/*     */     }
/*     */     
/*     */     try {
/* 168 */       runMethod.invoke(this, new Object[0]);
/*     */     }
/* 170 */     catch (InvocationTargetException e) {
/* 171 */       e.fillInStackTrace();
/* 172 */       throw e.getTargetException();
/*     */     }
/* 174 */     catch (IllegalAccessException e) {
/* 175 */       e.fillInStackTrace();
/* 176 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setUp() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tearDown() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 196 */     return getName() + "(" + getClass().getName() + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 203 */     return this.fName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 210 */     this.fName = name;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\framework\TestCase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */