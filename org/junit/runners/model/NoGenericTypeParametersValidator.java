/*    */ package org.junit.runners.model;
/*    */ 
/*    */ import java.lang.reflect.GenericArrayType;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import java.lang.reflect.Type;
/*    */ import java.lang.reflect.WildcardType;
/*    */ import java.util.List;
/*    */ 
/*    */ class NoGenericTypeParametersValidator
/*    */ {
/*    */   private final Method fMethod;
/*    */   
/*    */   NoGenericTypeParametersValidator(Method method) {
/* 15 */     this.fMethod = method;
/*    */   }
/*    */   
/*    */   void validate(List<Throwable> errors) {
/* 19 */     for (Type each : this.fMethod.getGenericParameterTypes())
/* 20 */       validateNoTypeParameterOnType(each, errors); 
/*    */   }
/*    */   
/*    */   private void validateNoTypeParameterOnType(Type type, List<Throwable> errors) {
/* 24 */     if (type instanceof java.lang.reflect.TypeVariable) {
/* 25 */       errors.add(new Exception("Method " + this.fMethod.getName() + "() contains unresolved type variable " + type));
/*    */     }
/* 27 */     else if (type instanceof ParameterizedType) {
/* 28 */       validateNoTypeParameterOnParameterizedType((ParameterizedType)type, errors);
/* 29 */     } else if (type instanceof WildcardType) {
/* 30 */       validateNoTypeParameterOnWildcardType((WildcardType)type, errors);
/* 31 */     } else if (type instanceof GenericArrayType) {
/* 32 */       validateNoTypeParameterOnGenericArrayType((GenericArrayType)type, errors);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void validateNoTypeParameterOnParameterizedType(ParameterizedType parameterized, List<Throwable> errors) {
/* 37 */     for (Type each : parameterized.getActualTypeArguments()) {
/* 38 */       validateNoTypeParameterOnType(each, errors);
/*    */     }
/*    */   }
/*    */   
/*    */   private void validateNoTypeParameterOnWildcardType(WildcardType wildcard, List<Throwable> errors) {
/* 43 */     for (Type each : wildcard.getUpperBounds())
/* 44 */       validateNoTypeParameterOnType(each, errors); 
/* 45 */     for (Type each : wildcard.getLowerBounds()) {
/* 46 */       validateNoTypeParameterOnType(each, errors);
/*    */     }
/*    */   }
/*    */   
/*    */   private void validateNoTypeParameterOnGenericArrayType(GenericArrayType arrayType, List<Throwable> errors) {
/* 51 */     validateNoTypeParameterOnType(arrayType.getGenericComponentType(), errors);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\model\NoGenericTypeParametersValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */