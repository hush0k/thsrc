/*    */ package org.junit.internal;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class RealSystem
/*    */   implements JUnitSystem {
/*    */   public void exit(int code) {
/*  8 */     System.exit(code);
/*    */   }
/*    */   
/*    */   public PrintStream out() {
/* 12 */     return System.out;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\RealSystem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */