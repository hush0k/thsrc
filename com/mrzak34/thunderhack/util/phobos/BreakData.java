/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.EntityUtil;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ public class BreakData<T extends CrystalData>
/*    */ {
/*    */   private final Collection<T> data;
/* 11 */   private float fallBackDmg = Float.MAX_VALUE;
/*    */   private Entity antiTotem;
/*    */   private Entity fallBack;
/*    */   private int shieldCount;
/*    */   
/*    */   public BreakData(Collection<T> data) {
/* 17 */     this.data = data;
/*    */   }
/*    */   
/*    */   public void register(T dataIn) {
/* 21 */     if (dataIn.getSelfDmg() < this.fallBackDmg && !EntityUtil.isDead(dataIn.getCrystal())) {
/* 22 */       this.fallBack = dataIn.getCrystal();
/* 23 */       this.fallBackDmg = dataIn.getSelfDmg();
/*    */     } 
/*    */     
/* 26 */     this.data.add(dataIn);
/*    */   }
/*    */   
/*    */   public float getFallBackDmg() {
/* 30 */     return this.fallBackDmg;
/*    */   }
/*    */   
/*    */   public Entity getAntiTotem() {
/* 34 */     return this.antiTotem;
/*    */   }
/*    */   
/*    */   public void setAntiTotem(Entity antiTotem) {
/* 38 */     this.antiTotem = antiTotem;
/*    */   }
/*    */   
/*    */   public Entity getFallBack() {
/* 42 */     return this.fallBack;
/*    */   }
/*    */   
/*    */   public Collection<T> getData() {
/* 46 */     return this.data;
/*    */   }
/*    */   
/*    */   public int getShieldCount() {
/* 50 */     return this.shieldCount;
/*    */   }
/*    */   
/*    */   public void setShieldCount(int shieldCount) {
/* 54 */     this.shieldCount = shieldCount;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\BreakData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */