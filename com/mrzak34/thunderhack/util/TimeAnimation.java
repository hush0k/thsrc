/*     */ package com.mrzak34.thunderhack.util;
/*     */ 
/*     */ import com.mrzak34.thunderhack.util.math.AnimationMode;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class TimeAnimation {
/*     */   private final long length;
/*     */   private final double start;
/*     */   private final double end;
/*     */   private double current;
/*     */   private double progress;
/*     */   private boolean playing;
/*     */   private boolean backwards;
/*     */   private boolean reverseOnEnd;
/*     */   private final long startTime;
/*     */   private long lastTime;
/*     */   private double per;
/*     */   private final long dif;
/*     */   private boolean flag;
/*     */   private AnimationMode mode;
/*     */   
/*     */   public TimeAnimation(long length, double start, double end, boolean backwards, AnimationMode mode) {
/*     */     double dif;
/*     */     int i;
/*  25 */     this.length = length;
/*  26 */     this.start = start;
/*  27 */     this.current = start;
/*  28 */     this.end = end;
/*  29 */     this.mode = mode;
/*  30 */     this.backwards = backwards;
/*  31 */     this.startTime = System.currentTimeMillis();
/*  32 */     this.playing = true;
/*  33 */     this.dif = System.currentTimeMillis() - this.startTime;
/*  34 */     switch (mode) {
/*     */       case LINEAR:
/*  36 */         this.per = (end - start) / length;
/*     */         break;
/*     */       case EXPONENTIAL:
/*  39 */         dif = end - start;
/*  40 */         this.flag = (dif < 0.0D);
/*  41 */         if (this.flag) dif *= -1.0D; 
/*  42 */         for (i = 0; i < length; i++) {
/*  43 */           dif = Math.sqrt(dif);
/*     */         }
/*  45 */         this.per = dif;
/*     */         break;
/*     */     } 
/*  48 */     this.lastTime = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(float partialTicks) {
/*  53 */     if (this.playing) {
/*  54 */       if (this.mode == AnimationMode.LINEAR) {
/*  55 */         this.current = this.start + this.progress;
/*  56 */         this.progress += this.per * (System.currentTimeMillis() - this.lastTime);
/*  57 */       } else if (this.mode == AnimationMode.EXPONENTIAL) {
/*     */       
/*     */       } 
/*  60 */       this.current = MathHelper.func_151237_a(this.current, this.start, this.end);
/*  61 */       if (this.current >= this.end || (this.backwards && this.current <= this.start)) {
/*  62 */         if (this.reverseOnEnd) {
/*  63 */           reverse();
/*  64 */           this.reverseOnEnd = false;
/*     */         } else {
/*  66 */           this.playing = false;
/*     */         } 
/*     */       }
/*     */     } 
/*  70 */     this.lastTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public long getLength() {
/*  74 */     return this.length;
/*     */   }
/*     */   
/*     */   public double getStart() {
/*  78 */     return this.start;
/*     */   }
/*     */   
/*     */   public double getEnd() {
/*  82 */     return this.end;
/*     */   }
/*     */   
/*     */   public double getCurrent() {
/*  86 */     return this.current;
/*     */   }
/*     */   
/*     */   public void setCurrent(double current) {
/*  90 */     this.current = current;
/*     */   }
/*     */   
/*     */   public AnimationMode getMode() {
/*  94 */     return this.mode;
/*     */   }
/*     */   
/*     */   public void setMode(AnimationMode mode) {
/*  98 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public void play() {
/* 102 */     this.playing = true;
/*     */   }
/*     */   
/*     */   public void stop() {
/* 106 */     this.playing = false;
/*     */   }
/*     */   
/*     */   public void reverse() {
/* 110 */     this.backwards = !this.backwards;
/* 111 */     this.per *= -1.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\TimeAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */