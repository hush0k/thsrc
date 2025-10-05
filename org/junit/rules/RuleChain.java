/*    */ package org.junit.rules;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runners.model.Statement;
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
/*    */ public class RuleChain
/*    */   implements TestRule
/*    */ {
/* 45 */   private static final RuleChain EMPTY_CHAIN = new RuleChain(Collections.emptyList());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private List<TestRule> rulesStartingWithInnerMost;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static RuleChain emptyRuleChain() {
/* 57 */     return EMPTY_CHAIN;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static RuleChain outerRule(TestRule outerRule) {
/* 69 */     return emptyRuleChain().around(outerRule);
/*    */   }
/*    */   
/*    */   private RuleChain(List<TestRule> rules) {
/* 73 */     this.rulesStartingWithInnerMost = rules;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RuleChain around(TestRule enclosedRule) {
/* 85 */     List<TestRule> rulesOfNewChain = new ArrayList<TestRule>();
/* 86 */     rulesOfNewChain.add(enclosedRule);
/* 87 */     rulesOfNewChain.addAll(this.rulesStartingWithInnerMost);
/* 88 */     return new RuleChain(rulesOfNewChain);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Statement apply(Statement base, Description description) {
/* 95 */     for (TestRule each : this.rulesStartingWithInnerMost)
/* 96 */       base = each.apply(base, description); 
/* 97 */     return base;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\RuleChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */