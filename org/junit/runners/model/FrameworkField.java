/*    */ package org.junit.runners.model;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Modifier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FrameworkField
/*    */   extends FrameworkMember<FrameworkField>
/*    */ {
/*    */   private final Field fField;
/*    */   
/*    */   FrameworkField(Field field) {
/* 17 */     this.fField = field;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 21 */     return getField().getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public Annotation[] getAnnotations() {
/* 26 */     return this.fField.getAnnotations();
/*    */   }
/*    */   
/*    */   public boolean isPublic() {
/* 30 */     int modifiers = this.fField.getModifiers();
/* 31 */     return Modifier.isPublic(modifiers);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isShadowedBy(FrameworkField otherMember) {
/* 36 */     return otherMember.getName().equals(getName());
/*    */   }
/*    */   
/*    */   public boolean isStatic() {
/* 40 */     int modifiers = this.fField.getModifiers();
/* 41 */     return Modifier.isStatic(modifiers);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 48 */     return this.fField;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?> getType() {
/* 56 */     return this.fField.getType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object get(Object target) throws IllegalArgumentException, IllegalAccessException {
/* 63 */     return this.fField.get(target);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\model\FrameworkField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */