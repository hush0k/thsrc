/*     */ package org.junit.runner.manipulation;
/*     */ 
/*     */ import org.junit.runner.Description;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Filter
/*     */ {
/*  19 */   public static Filter ALL = new Filter()
/*     */     {
/*     */       public boolean shouldRun(Description description) {
/*  22 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public String describe() {
/*  27 */         return "all tests";
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void apply(Object child) throws NoTestsRemainException {}
/*     */ 
/*     */ 
/*     */       
/*     */       public Filter intersect(Filter second) {
/*  37 */         return second;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Filter matchMethodDescription(final Description desiredDescription) {
/*  46 */     return new Filter()
/*     */       {
/*     */         public boolean shouldRun(Description description) {
/*  49 */           if (description.isTest()) {
/*  50 */             return desiredDescription.equals(description);
/*     */           }
/*     */           
/*  53 */           for (Description each : description.getChildren()) {
/*  54 */             if (shouldRun(each))
/*  55 */               return true; 
/*  56 */           }  return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public String describe() {
/*  61 */           return String.format("Method %s", new Object[] { this.val$desiredDescription.getDisplayName() });
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean shouldRun(Description paramDescription);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String describe();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(Object child) throws NoTestsRemainException {
/*  86 */     if (!(child instanceof Filterable))
/*     */       return; 
/*  88 */     Filterable filterable = (Filterable)child;
/*  89 */     filterable.filter(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter intersect(final Filter second) {
/*  97 */     if (second == this || second == ALL) {
/*  98 */       return this;
/*     */     }
/* 100 */     final Filter first = this;
/* 101 */     return new Filter()
/*     */       {
/*     */         public boolean shouldRun(Description description) {
/* 104 */           return (first.shouldRun(description) && second.shouldRun(description));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String describe() {
/* 110 */           return first.describe() + " and " + second.describe();
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runner\manipulation\Filter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */