/*    */ package org.junit.experimental.runners;
/*    */ 
/*    */ import org.junit.runners.Suite;
/*    */ import org.junit.runners.model.RunnerBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Enclosed
/*    */   extends Suite
/*    */ {
/*    */   public Enclosed(Class<?> klass, RunnerBuilder builder) throws Throwable {
/* 29 */     super(builder, klass, klass.getClasses());
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\runners\Enclosed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */