/*    */ package org.junit.rules;
/*    */ 
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runners.model.Statement;
/*    */ 
/*    */ 
/*    */ public class RunRules
/*    */   extends Statement
/*    */ {
/*    */   private final Statement statement;
/*    */   
/*    */   public RunRules(Statement base, Iterable<TestRule> rules, Description description) {
/* 13 */     this.statement = applyAll(base, rules, description);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evaluate() throws Throwable {
/* 18 */     this.statement.evaluate();
/*    */   }
/*    */ 
/*    */   
/*    */   private static Statement applyAll(Statement result, Iterable<TestRule> rules, Description description) {
/* 23 */     for (TestRule each : rules)
/* 24 */       result = each.apply(result, description); 
/* 25 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\RunRules.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */