/*    */ package org.junit.experimental.theories;
/*    */ 
/*    */ public abstract class PotentialAssignment {
/*    */   public static class CouldNotGenerateValueException extends Exception {
/*    */     private static final long serialVersionUID = 1L;
/*    */   }
/*    */   
/*    */   public static PotentialAssignment forValue(final String name, final Object value) {
/*  9 */     return new PotentialAssignment()
/*    */       {
/*    */         public Object getValue() throws PotentialAssignment.CouldNotGenerateValueException {
/* 12 */           return value;
/*    */         }
/*    */ 
/*    */         
/*    */         public String toString() {
/* 17 */           return String.format("[%s]", new Object[] { this.val$value });
/*    */         }
/*    */ 
/*    */ 
/*    */         
/*    */         public String getDescription() throws PotentialAssignment.CouldNotGenerateValueException {
/* 23 */           return name;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public abstract Object getValue() throws CouldNotGenerateValueException;
/*    */   
/*    */   public abstract String getDescription() throws CouldNotGenerateValueException;
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\theories\PotentialAssignment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */