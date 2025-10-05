/*    */ package org.junit.runners.model;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class FrameworkMember<T extends FrameworkMember<T>>
/*    */ {
/*    */   abstract Annotation[] getAnnotations();
/*    */   
/*    */   abstract boolean isShadowedBy(T paramT);
/*    */   
/*    */   boolean isShadowedBy(List<T> members) {
/* 15 */     for (FrameworkMember frameworkMember : members) {
/* 16 */       if (isShadowedBy((T)frameworkMember))
/* 17 */         return true; 
/* 18 */     }  return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\model\FrameworkMember.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */