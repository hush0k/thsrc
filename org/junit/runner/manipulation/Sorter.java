/*    */ package org.junit.runner.manipulation;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ public class Sorter
/*    */   implements Comparator<Description>
/*    */ {
/* 17 */   public static Sorter NULL = new Sorter(new Comparator<Description>() {
/*    */         public int compare(Description o1, Description o2) {
/* 19 */           return 0;
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */   
/*    */   private final Comparator<Description> fComparator;
/*    */ 
/*    */   
/*    */   public Sorter(Comparator<Description> comparator) {
/* 29 */     this.fComparator = comparator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void apply(Object object) {
/* 37 */     if (object instanceof Sortable) {
/* 38 */       Sortable sortable = (Sortable)object;
/* 39 */       sortable.sort(this);
/*    */     } 
/*    */   }
/*    */   
/*    */   public int compare(Description o1, Description o2) {
/* 44 */     return this.fComparator.compare(o1, o2);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runner\manipulation\Sorter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */