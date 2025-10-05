/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.Set;
/*    */ import java.util.TreeSet;
/*    */ 
/*    */ public class ForceData {
/*  7 */   private final Set<ForcePosition> forceData = new TreeSet<>();
/*    */   private boolean possibleHighDamage;
/*    */   private boolean possibleAntiTotem;
/*    */   
/*    */   public boolean hasPossibleHighDamage() {
/* 12 */     return this.possibleHighDamage;
/*    */   }
/*    */   
/*    */   public void setPossibleHighDamage(boolean possibleHighDamage) {
/* 16 */     this.possibleHighDamage = possibleHighDamage;
/*    */   }
/*    */   
/*    */   public boolean hasPossibleAntiTotem() {
/* 20 */     return this.possibleAntiTotem;
/*    */   }
/*    */   
/*    */   public void setPossibleAntiTotem(boolean possibleAntiTotem) {
/* 24 */     this.possibleAntiTotem = possibleAntiTotem;
/*    */   }
/*    */   
/*    */   public Set<ForcePosition> getForceData() {
/* 28 */     return this.forceData;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ForceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */