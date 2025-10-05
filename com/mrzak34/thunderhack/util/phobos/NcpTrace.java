/*     */ package com.mrzak34.thunderhack.util.phobos;public abstract class NcpTrace {
/*     */   protected double x0;
/*     */   protected double y0;
/*     */   protected double z0;
/*     */   protected double dX;
/*     */   protected double dY;
/*     */   protected double dZ;
/*     */   protected int blockX;
/*   9 */   protected double t = Double.MIN_VALUE; protected int blockY; protected int blockZ; protected int endBlockX; protected int endBlockY; protected int endBlockZ; protected double oX; protected double oY; protected double oZ;
/*  10 */   protected double tol = 0.0D;
/*     */   protected boolean forceStepEndPos = true;
/*  12 */   protected int step = 0;
/*     */   protected boolean secondaryStep = true;
/*     */   protected boolean collides = false;
/*  15 */   private int maxSteps = Integer.MAX_VALUE;
/*     */   
/*     */   public NcpTrace() {
/*  18 */     set(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   private static double tDiff(double dTotal, double offset, boolean isEndBlock) {
/*  22 */     if (dTotal > 0.0D) {
/*  23 */       if (offset >= 1.0D) {
/*  24 */         return isEndBlock ? Double.MAX_VALUE : 0.0D;
/*     */       }
/*  26 */       return (1.0D - offset) / dTotal;
/*     */     } 
/*  28 */     if (dTotal < 0.0D) {
/*  29 */       if (offset <= 0.0D) {
/*  30 */         return isEndBlock ? Double.MAX_VALUE : 0.0D;
/*     */       }
/*  32 */       return offset / -dTotal;
/*     */     } 
/*     */     
/*  35 */     return Double.MAX_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract boolean step(int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean);
/*     */   
/*     */   public void set(double x0, double y0, double z0, double x1, double y1, double z1) {
/*  42 */     this.x0 = x0;
/*  43 */     this.y0 = y0;
/*  44 */     this.z0 = z0;
/*  45 */     this.dX = x1 - x0;
/*  46 */     this.dY = y1 - y0;
/*  47 */     this.dZ = z1 - z0;
/*  48 */     this.blockX = Visible.floor(x0);
/*  49 */     this.blockY = Visible.floor(y0);
/*  50 */     this.blockZ = Visible.floor(z0);
/*  51 */     this.endBlockX = Visible.floor(x1);
/*  52 */     this.endBlockY = Visible.floor(y1);
/*  53 */     this.endBlockZ = Visible.floor(z1);
/*  54 */     this.oX = x0 - this.blockX;
/*  55 */     this.oY = y0 - this.blockY;
/*  56 */     this.oZ = z0 - this.blockZ;
/*  57 */     this.t = 0.0D;
/*  58 */     this.step = 0;
/*  59 */     this.collides = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loop() {
/*  66 */     while (this.t + this.tol < 1.0D) {
/*  67 */       double tX = tDiff(this.dX, this.oX, (this.blockX == this.endBlockX));
/*  68 */       double tY = tDiff(this.dY, this.oY, (this.blockY == this.endBlockY));
/*  69 */       double tZ = tDiff(this.dZ, this.oZ, (this.blockZ == this.endBlockZ));
/*  70 */       double tMin = Math.max(0.0D, Math.min(tX, Math.min(tY, tZ)));
/*  71 */       if (tMin == Double.MAX_VALUE) {
/*  72 */         if (this.step < 1) {
/*  73 */           tMin = 0.0D;
/*     */         } else {
/*     */           break;
/*     */         } 
/*     */       }
/*     */       
/*  79 */       if (this.t + tMin > 1.0D) {
/*  80 */         tMin = 1.0D - this.t;
/*     */       }
/*     */       
/*  83 */       this.step++;
/*  84 */       if (!step(this.blockX, this.blockY, this.blockZ, this.oX, this.oY, this.oZ, tMin, true)) {
/*     */         break;
/*     */       }
/*     */       
/*  88 */       if (this.t + tMin + this.tol >= 1.0D && isEndBlock()) {
/*     */         break;
/*     */       }
/*     */       
/*  92 */       int transitions = 0;
/*  93 */       boolean transZ = false, transY = transZ, transX = transY;
/*  94 */       if (tX == tMin && this.blockX != this.endBlockX && this.dX != 0.0D) {
/*  95 */         transX = true;
/*  96 */         transitions++;
/*     */       } 
/*  98 */       if (tY == tMin && this.blockY != this.endBlockY && this.dY != 0.0D) {
/*  99 */         transY = true;
/* 100 */         transitions++;
/*     */       } 
/* 102 */       if (tZ == tMin && this.blockZ != this.endBlockZ && this.dZ != 0.0D) {
/* 103 */         transZ = true;
/* 104 */         transitions++;
/*     */       } 
/*     */       
/* 107 */       this.oX = Math.min(1.0D, Math.max(0.0D, this.oX + tMin * this.dX));
/* 108 */       this.oY = Math.min(1.0D, Math.max(0.0D, this.oY + tMin * this.dY));
/* 109 */       this.oZ = Math.min(1.0D, Math.max(0.0D, this.oZ + tMin * this.dZ));
/* 110 */       this.t = Math.min(1.0D, this.t + tMin);
/*     */       
/* 112 */       if (transitions <= 0 || 
/* 113 */         !handleTransitions(transitions, transX, transY, transZ)) {
/*     */         break;
/*     */       }
/*     */       
/* 117 */       if (this.forceStepEndPos && this.t + this.tol >= 1.0D) {
/* 118 */         step(this.blockX, this.blockY, this.blockZ, this.oX, this.oY, this.oZ, 0.0D, true);
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */       
/* 125 */       if (this.step >= this.maxSteps) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean handleTransitions(int transitions, boolean transX, boolean transY, boolean transZ) {
/* 133 */     if (transitions > 1 && this.secondaryStep && 
/* 134 */       !handleSecondaryTransitions(transitions, transX, transY, transZ)) {
/* 135 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 139 */     double tcMin = 1.0D;
/*     */     
/* 141 */     if (transX) {
/* 142 */       if (this.dX > 0.0D) {
/* 143 */         this.blockX++;
/* 144 */         tcMin = Math.min(tcMin, (this.blockX - this.x0) / this.dX);
/*     */       } else {
/* 146 */         this.blockX--;
/* 147 */         tcMin = Math.min(tcMin, (1.0D + this.blockX - this.x0) / this.dX);
/*     */       } 
/*     */     }
/*     */     
/* 151 */     if (transY) {
/* 152 */       if (this.dY > 0.0D) {
/* 153 */         this.blockY++;
/* 154 */         tcMin = Math.min(tcMin, (this.blockY - this.y0) / this.dY);
/*     */       } else {
/* 156 */         this.blockY--;
/* 157 */         tcMin = Math.min(tcMin, (1.0D + this.blockY - this.y0) / this.dY);
/*     */       } 
/*     */     }
/*     */     
/* 161 */     if (transZ) {
/* 162 */       if (this.dZ > 0.0D) {
/* 163 */         this.blockZ++;
/* 164 */         tcMin = Math.min(tcMin, (this.blockZ - this.z0) / this.dZ);
/*     */       } else {
/* 166 */         this.blockZ--;
/* 167 */         tcMin = Math.min(tcMin, (1.0D + this.blockZ - this.z0) / this.dZ);
/*     */       } 
/*     */     }
/*     */     
/* 171 */     this.oX = this.x0 + tcMin * this.dX - this.blockX;
/* 172 */     this.oY = this.y0 + tcMin * this.dY - this.blockY;
/* 173 */     this.oZ = this.z0 + tcMin * this.dZ - this.blockZ;
/* 174 */     this.t = tcMin;
/* 175 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean handleSecondaryTransitions(int transitions, boolean transX, boolean transY, boolean transZ) {
/* 179 */     if (transX) {
/* 180 */       if (!step(this.blockX + ((this.dX > 0.0D) ? 1 : -1), this.blockY, this.blockZ, (this.dX > 0.0D) ? 0.0D : 1.0D, this.oY, this.oZ, 0.0D, false))
/*     */       {
/* 182 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 186 */     if (transY) {
/* 187 */       if (!step(this.blockX, this.blockY + ((this.dY > 0.0D) ? 1 : -1), this.blockZ, this.oX, (this.dY > 0.0D) ? 0.0D : 1.0D, this.oZ, 0.0D, false))
/*     */       {
/* 189 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 193 */     if (transZ) {
/* 194 */       if (!step(this.blockX, this.blockY, this.blockZ + ((this.dZ > 0.0D) ? 1 : -1), this.oX, this.oY, (this.dZ > 0.0D) ? 0.0D : 1.0D, 0.0D, false))
/*     */       {
/* 196 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 200 */     if (transitions == 3) {
/* 201 */       return handleSecondaryDoubleTransitions();
/*     */     }
/*     */     
/* 204 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean handleSecondaryDoubleTransitions() {
/* 208 */     if (!step(this.blockX + ((this.dX > 0.0D) ? 1 : -1), this.blockY + ((this.dY > 0.0D) ? 1 : -1), this.blockZ, (this.dX > 0.0D) ? 0.0D : 1.0D, (this.dY > 0.0D) ? 0.0D : 1.0D, this.oZ, 0.0D, false)) {
/* 209 */       return false;
/*     */     }
/*     */     
/* 212 */     if (!step(this.blockX + ((this.dX > 0.0D) ? 1 : -1), this.blockY, this.blockZ + ((this.dZ > 0.0D) ? 1 : -1), (this.dX > 0.0D) ? 0.0D : 1.0D, this.oY, (this.dZ > 0.0D) ? 0.0D : 1.0D, 0.0D, false)) {
/* 213 */       return false;
/*     */     }
/*     */     
/* 216 */     return step(this.blockX, this.blockY + ((this.dY > 0.0D) ? 1 : -1), this.blockZ + ((this.dZ > 0.0D) ? 1 : -1), this.oX, (this.dY > 0.0D) ? 0.0D : 1.0D, (this.dZ > 0.0D) ? 0.0D : 1.0D, 0.0D, false);
/*     */   }
/*     */   
/*     */   public boolean isEndBlock() {
/* 220 */     return (this.blockX == this.endBlockX && this.blockY == this.endBlockY && this.blockZ == this.endBlockZ);
/*     */   }
/*     */   
/*     */   public int getStepsDone() {
/* 224 */     return this.step;
/*     */   }
/*     */   
/*     */   public int getMaxSteps() {
/* 228 */     return this.maxSteps;
/*     */   }
/*     */   
/*     */   public void setMaxSteps(int maxSteps) {
/* 232 */     this.maxSteps = maxSteps;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\NcpTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */