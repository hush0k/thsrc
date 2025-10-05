/*    */ package org.junit.experimental.theories.suppliers;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.junit.experimental.theories.ParameterSignature;
/*    */ import org.junit.experimental.theories.ParameterSupplier;
/*    */ import org.junit.experimental.theories.PotentialAssignment;
/*    */ 
/*    */ 
/*    */ public class TestedOnSupplier
/*    */   extends ParameterSupplier
/*    */ {
/*    */   public List<PotentialAssignment> getValueSources(ParameterSignature sig) {
/* 15 */     List<PotentialAssignment> list = new ArrayList<PotentialAssignment>();
/* 16 */     TestedOn testedOn = (TestedOn)sig.getAnnotation(TestedOn.class);
/* 17 */     int[] ints = testedOn.ints();
/* 18 */     for (int i : ints) {
/* 19 */       list.add(PotentialAssignment.forValue(Arrays.<int[]>asList(new int[][] { ints }, ).toString(), Integer.valueOf(i)));
/*    */     } 
/* 21 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\theories\suppliers\TestedOnSupplier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */