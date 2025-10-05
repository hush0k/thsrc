/*     */ package org.junit.experimental.max;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import junit.framework.Test;
/*     */ import junit.framework.TestSuite;
/*     */ import org.junit.internal.runners.ErrorReportingRunner;
/*     */ import org.junit.internal.runners.JUnit38ClassRunner;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runner.JUnitCore;
/*     */ import org.junit.runner.Request;
/*     */ import org.junit.runner.Result;
/*     */ import org.junit.runner.Runner;
/*     */ import org.junit.runners.Suite;
/*     */ import org.junit.runners.model.InitializationError;
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
/*     */ public class MaxCore
/*     */ {
/*     */   private static final String MALFORMED_JUNIT_3_TEST_CLASS_PREFIX = "malformed JUnit 3 test class: ";
/*     */   private final MaxHistory fHistory;
/*     */   
/*     */   @Deprecated
/*     */   public static MaxCore forFolder(String folderName) {
/*  42 */     return storedLocally(new File(folderName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MaxCore storedLocally(File storedResults) {
/*  49 */     return new MaxCore(storedResults);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private MaxCore(File storedResults) {
/*  55 */     this.fHistory = MaxHistory.forFolder(storedResults);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result run(Class<?> testClass) {
/*  63 */     return run(Request.aClass(testClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result run(Request request) {
/*  72 */     return run(request, new JUnitCore());
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
/*     */   public Result run(Request request, JUnitCore core) {
/*  86 */     core.addListener(this.fHistory.listener());
/*  87 */     return core.run(sortRequest(request).getRunner());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request sortRequest(Request request) {
/*  95 */     if (request instanceof org.junit.internal.requests.SortingRequest)
/*  96 */       return request; 
/*  97 */     List<Description> leaves = findLeaves(request);
/*  98 */     Collections.sort(leaves, this.fHistory.testComparator());
/*  99 */     return constructLeafRequest(leaves);
/*     */   }
/*     */   
/*     */   private Request constructLeafRequest(List<Description> leaves) {
/* 103 */     final List<Runner> runners = new ArrayList<Runner>();
/* 104 */     for (Description each : leaves)
/* 105 */       runners.add(buildRunner(each)); 
/* 106 */     return new Request()
/*     */       {
/*     */         public Runner getRunner() {
/*     */           try {
/* 110 */             return (Runner)new Suite((Class)null, runners) {  };
/* 111 */           } catch (InitializationError e) {
/* 112 */             return (Runner)new ErrorReportingRunner(null, (Throwable)e);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private Runner buildRunner(Description each) {
/* 119 */     if (each.toString().equals("TestSuite with 0 tests"))
/* 120 */       return Suite.emptySuite(); 
/* 121 */     if (each.toString().startsWith("malformed JUnit 3 test class: "))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 126 */       return (Runner)new JUnit38ClassRunner((Test)new TestSuite(getMalformedTestClass(each))); } 
/* 127 */     Class<?> type = each.getTestClass();
/* 128 */     if (type == null)
/* 129 */       throw new RuntimeException("Can't build a runner from description [" + each + "]"); 
/* 130 */     String methodName = each.getMethodName();
/* 131 */     if (methodName == null)
/* 132 */       return Request.aClass(type).getRunner(); 
/* 133 */     return Request.method(type, methodName).getRunner();
/*     */   }
/*     */   
/*     */   private Class<?> getMalformedTestClass(Description each) {
/*     */     try {
/* 138 */       return Class.forName(each.toString().replace("malformed JUnit 3 test class: ", ""));
/* 139 */     } catch (ClassNotFoundException e) {
/* 140 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Description> sortedLeavesForTest(Request request) {
/* 150 */     return findLeaves(sortRequest(request));
/*     */   }
/*     */   
/*     */   private List<Description> findLeaves(Request request) {
/* 154 */     List<Description> results = new ArrayList<Description>();
/* 155 */     findLeaves(null, request.getRunner().getDescription(), results);
/* 156 */     return results;
/*     */   }
/*     */   
/*     */   private void findLeaves(Description parent, Description description, List<Description> results) {
/* 160 */     if (description.getChildren().isEmpty())
/* 161 */     { if (description.toString().equals("warning(junit.framework.TestSuite$1)")) {
/* 162 */         results.add(Description.createSuiteDescription("malformed JUnit 3 test class: " + parent, new java.lang.annotation.Annotation[0]));
/*     */       } else {
/* 164 */         results.add(description);
/*     */       }  }
/* 166 */     else { for (Description each : description.getChildren())
/* 167 */         findLeaves(description, each, results);  }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\max\MaxCore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */