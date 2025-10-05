/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ public class CrystalDataMotion extends CrystalData {
/*   6 */   private Timing timing = Timing.BOTH;
/*     */   private float postSelf;
/*     */   
/*     */   public CrystalDataMotion(Entity crystal) {
/*  10 */     super(crystal);
/*     */   }
/*     */   
/*     */   public float getPostSelf() {
/*  14 */     return this.postSelf;
/*     */   }
/*     */   
/*     */   public void setPostSelf(float postSelf) {
/*  18 */     this.postSelf = postSelf;
/*     */   }
/*     */   
/*     */   public Timing getTiming() {
/*  22 */     return this.timing;
/*     */   }
/*     */   
/*     */   public void invalidateTiming(Timing timing) {
/*  26 */     if (timing == Timing.PRE) {
/*  27 */       if (this.timing == Timing.PRE) {
/*  28 */         this.timing = Timing.NONE;
/*  29 */       } else if (this.timing == Timing.BOTH) {
/*  30 */         this.timing = Timing.POST;
/*     */       }
/*     */     
/*     */     }
/*  34 */     else if (this.timing == Timing.POST) {
/*  35 */       this.timing = Timing.NONE;
/*  36 */     } else if (this.timing == Timing.BOTH) {
/*  37 */       this.timing = Timing.PRE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(CrystalData o) {
/*  44 */     if (o instanceof CrystalDataMotion && 
/*  45 */       Math.abs(o.getDamage() - getDamage()) < 1.0F) {
/*     */       
/*  47 */       CrystalDataMotion motion = (CrystalDataMotion)o;
/*     */       
/*  49 */       boolean breakCase = true;
/*  50 */       float lowestSelf = Float.MAX_VALUE;
/*  51 */       boolean thisBetter = (getDamage() > o.getDamage());
/*     */       
/*  53 */       switch (motion.getTiming()) {
/*     */         case BOTH:
/*  55 */           breakCase = false;
/*     */         case PRE:
/*  57 */           if (motion.getSelfDmg() < lowestSelf) {
/*  58 */             lowestSelf = motion.getSelfDmg();
/*  59 */             thisBetter = false;
/*     */           } 
/*     */           
/*  62 */           if (breakCase) {
/*     */             break;
/*     */           }
/*     */         case POST:
/*  66 */           if (motion.getPostSelf() < lowestSelf) {
/*  67 */             lowestSelf = motion.getSelfDmg();
/*  68 */             thisBetter = false;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case NONE:
/*  73 */           return -1;
/*     */       } 
/*     */       
/*  76 */       breakCase = true;
/*  77 */       switch (getTiming()) {
/*     */         case BOTH:
/*  79 */           breakCase = false;
/*     */         case PRE:
/*  81 */           if (getSelfDmg() < lowestSelf || (
/*  82 */             getSelfDmg() == lowestSelf && 
/*  83 */             getDamage() > motion.getDamage())) {
/*  84 */             lowestSelf = getSelfDmg();
/*  85 */             thisBetter = true;
/*     */           } 
/*     */           
/*  88 */           if (breakCase) {
/*     */             break;
/*     */           }
/*     */         case POST:
/*  92 */           if (getSelfDmg() < lowestSelf || (
/*  93 */             getSelfDmg() == lowestSelf && 
/*  94 */             getDamage() > motion.getDamage())) {
/*  95 */             thisBetter = true;
/*     */           }
/*     */           break;
/*     */         
/*     */         case NONE:
/* 100 */           return 1;
/*     */       } 
/*     */       
/* 103 */       return thisBetter ? -1 : 1;
/*     */     } 
/*     */     
/* 106 */     return super.compareTo(o);
/*     */   }
/*     */   
/*     */   public enum Timing {
/* 110 */     NONE,
/* 111 */     PRE,
/* 112 */     POST,
/* 113 */     BOTH;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\CrystalDataMotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */