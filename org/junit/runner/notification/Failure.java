/*    */ package org.junit.runner.notification;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.Serializable;
/*    */ import java.io.StringWriter;
/*    */ import org.junit.runner.Description;
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
/*    */ public class Failure
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final Description fDescription;
/*    */   private final Throwable fThrownException;
/*    */   
/*    */   public Failure(Description description, Throwable thrownException) {
/* 27 */     this.fThrownException = thrownException;
/* 28 */     this.fDescription = description;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTestHeader() {
/* 35 */     return this.fDescription.getDisplayName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Description getDescription() {
/* 42 */     return this.fDescription;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable getException() {
/* 50 */     return this.fThrownException;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     StringBuffer buffer = new StringBuffer();
/* 56 */     buffer.append(getTestHeader() + ": " + this.fThrownException.getMessage());
/* 57 */     return buffer.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTrace() {
/* 65 */     StringWriter stringWriter = new StringWriter();
/* 66 */     PrintWriter writer = new PrintWriter(stringWriter);
/* 67 */     getException().printStackTrace(writer);
/* 68 */     StringBuffer buffer = stringWriter.getBuffer();
/* 69 */     return buffer.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 77 */     return getException().getMessage();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runner\notification\Failure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */