/*    */ package org.junit.internal.runners.rules;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.util.List;
/*    */ import org.junit.ClassRule;
/*    */ import org.junit.Rule;
/*    */ import org.junit.rules.MethodRule;
/*    */ import org.junit.rules.TestRule;
/*    */ import org.junit.runners.model.FrameworkField;
/*    */ import org.junit.runners.model.TestClass;
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
/*    */ public enum RuleFieldValidator
/*    */ {
/* 25 */   CLASS_RULE_VALIDATOR((Class)ClassRule.class, true),
/*    */ 
/*    */ 
/*    */   
/* 29 */   RULE_VALIDATOR((Class)Rule.class, false);
/*    */   
/*    */   private final Class<? extends Annotation> fAnnotation;
/*    */   
/*    */   private final boolean fOnlyStaticFields;
/*    */ 
/*    */   
/*    */   RuleFieldValidator(Class<? extends Annotation> annotation, boolean onlyStaticFields) {
/* 37 */     this.fAnnotation = annotation;
/* 38 */     this.fOnlyStaticFields = onlyStaticFields;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validate(TestClass target, List<Throwable> errors) {
/* 48 */     List<FrameworkField> fields = target.getAnnotatedFields(this.fAnnotation);
/* 49 */     for (FrameworkField each : fields)
/* 50 */       validateField(each, errors); 
/*    */   }
/*    */   
/*    */   private void validateField(FrameworkField field, List<Throwable> errors) {
/* 54 */     optionallyValidateStatic(field, errors);
/* 55 */     validatePublic(field, errors);
/* 56 */     validateTestRuleOrMethodRule(field, errors);
/*    */   }
/*    */ 
/*    */   
/*    */   private void optionallyValidateStatic(FrameworkField field, List<Throwable> errors) {
/* 61 */     if (this.fOnlyStaticFields && !field.isStatic())
/* 62 */       addError(errors, field, "must be static."); 
/*    */   }
/*    */   
/*    */   private void validatePublic(FrameworkField field, List<Throwable> errors) {
/* 66 */     if (!field.isPublic()) {
/* 67 */       addError(errors, field, "must be public.");
/*    */     }
/*    */   }
/*    */   
/*    */   private void validateTestRuleOrMethodRule(FrameworkField field, List<Throwable> errors) {
/* 72 */     if (!isMethodRule(field) && !isTestRule(field))
/* 73 */       addError(errors, field, "must implement MethodRule or TestRule."); 
/*    */   }
/*    */   
/*    */   private boolean isTestRule(FrameworkField target) {
/* 77 */     return TestRule.class.isAssignableFrom(target.getType());
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean isMethodRule(FrameworkField target) {
/* 82 */     return MethodRule.class.isAssignableFrom(target.getType());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void addError(List<Throwable> errors, FrameworkField field, String suffix) {
/* 88 */     String message = "The @" + this.fAnnotation.getSimpleName() + " '" + field.getName() + "' " + suffix;
/*    */     
/* 90 */     errors.add(new Exception(message));
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\rules\RuleFieldValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */