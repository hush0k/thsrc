/*     */ package com.mrzak34.thunderhack.util;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PyroSpeed
/*     */ {
/*     */   public static AxisAlignedBB Method5403(double d) {
/*  19 */     double[] arrd = Method732(0.20000000298023224D);
/*  20 */     double d2 = arrd[0];
/*  21 */     double d3 = arrd[1];
/*  22 */     double d4 = d2;
/*  23 */     double d6 = d3;
/*  24 */     List<AxisAlignedBB> list = Util.mc.field_71441_e.func_184144_a((Entity)Util.mc.field_71439_g, Util.mc.field_71439_g.func_174813_aQ().func_72321_a(d2, 0.0D, d3));
/*  25 */     AxisAlignedBB axisAlignedBB = Util.mc.field_71439_g.func_174813_aQ();
/*  26 */     if (d2 != 0.0D) {
/*  27 */       int n2 = list.size();
/*  28 */       for (int n = 0; n < n2; n++) {
/*  29 */         d2 = ((AxisAlignedBB)list.get(n)).func_72316_a(axisAlignedBB, d2);
/*     */       }
/*  31 */       if (d2 != 0.0D) {
/*  32 */         axisAlignedBB = axisAlignedBB.func_72317_d(d2, 0.0D, 0.0D);
/*     */       }
/*     */     } 
/*  35 */     if (d3 != 0.0D) {
/*  36 */       int n2 = list.size();
/*  37 */       for (int n = 0; n < n2; n++) {
/*  38 */         d3 = ((AxisAlignedBB)list.get(n)).func_72322_c(axisAlignedBB, d3);
/*     */       }
/*  40 */       if (d3 != 0.0D) {
/*  41 */         axisAlignedBB = axisAlignedBB.func_72317_d(0.0D, 0.0D, d3);
/*     */       }
/*     */     } 
/*  44 */     double d7 = d2;
/*  45 */     double d9 = d3;
/*  46 */     AxisAlignedBB axisAlignedBB2 = Util.mc.field_71439_g.func_174813_aQ();
/*  47 */     double d10 = d;
/*  48 */     List<AxisAlignedBB> list2 = Util.mc.field_71441_e.func_184144_a((Entity)Util.mc.field_71439_g, axisAlignedBB.func_72321_a(d4, d10, d6));
/*  49 */     AxisAlignedBB axisAlignedBB3 = axisAlignedBB;
/*  50 */     AxisAlignedBB axisAlignedBB4 = axisAlignedBB3.func_72321_a(d4, 0.0D, d6);
/*  51 */     double d11 = d10;
/*  52 */     for (AxisAlignedBB axisAlignedBB7 : list2) {
/*  53 */       d11 = axisAlignedBB7.func_72323_b(axisAlignedBB4, d11);
/*     */     }
/*  55 */     axisAlignedBB3 = axisAlignedBB3.func_72317_d(0.0D, d11, 0.0D);
/*  56 */     double d12 = d4;
/*  57 */     for (AxisAlignedBB axisAlignedBB1 : list2) {
/*  58 */       d12 = axisAlignedBB1.func_72316_a(axisAlignedBB3, d12);
/*     */     }
/*  60 */     axisAlignedBB3 = axisAlignedBB3.func_72317_d(d12, 0.0D, 0.0D);
/*  61 */     double d13 = d6;
/*  62 */     for (AxisAlignedBB element : list2) {
/*  63 */       d13 = element.func_72322_c(axisAlignedBB3, d13);
/*     */     }
/*  65 */     axisAlignedBB3 = axisAlignedBB3.func_72317_d(0.0D, 0.0D, d13);
/*  66 */     AxisAlignedBB axisAlignedBB5 = axisAlignedBB;
/*  67 */     double d14 = d10;
/*  68 */     for (AxisAlignedBB item : list2) {
/*  69 */       d14 = item.func_72323_b(axisAlignedBB5, d14);
/*     */     }
/*  71 */     axisAlignedBB5 = axisAlignedBB5.func_72317_d(0.0D, d14, 0.0D);
/*  72 */     double d15 = d4;
/*  73 */     for (AxisAlignedBB value : list2) {
/*  74 */       d15 = value.func_72316_a(axisAlignedBB5, d15);
/*     */     }
/*  76 */     axisAlignedBB5 = axisAlignedBB5.func_72317_d(d15, 0.0D, 0.0D);
/*  77 */     double d16 = d6;
/*  78 */     for (AxisAlignedBB bb : list2) {
/*  79 */       d16 = bb.func_72322_c(axisAlignedBB5, d16);
/*     */     }
/*  81 */     axisAlignedBB5 = axisAlignedBB5.func_72317_d(0.0D, 0.0D, d16);
/*  82 */     double d17 = d12 * d12 + d13 * d13;
/*  83 */     double d18 = d15 * d15 + d16 * d16;
/*     */     
/*  85 */     if (d17 > d18) {
/*  86 */       d2 = d12;
/*  87 */       d3 = d13;
/*  88 */       d10 = -d11;
/*  89 */       axisAlignedBB6 = axisAlignedBB3;
/*     */     } else {
/*  91 */       d2 = d15;
/*  92 */       d3 = d16;
/*  93 */       d10 = -d14;
/*  94 */       axisAlignedBB6 = axisAlignedBB5;
/*     */     } 
/*  96 */     for (AxisAlignedBB alignedBB : list2) {
/*  97 */       d10 = alignedBB.func_72323_b(axisAlignedBB6, d10);
/*     */     }
/*  99 */     AxisAlignedBB axisAlignedBB6 = axisAlignedBB6.func_72317_d(0.0D, d10, 0.0D);
/* 100 */     if (d7 * d7 + d9 * d9 >= d2 * d2 + d3 * d3) {
/* 101 */       axisAlignedBB6 = axisAlignedBB2;
/*     */     }
/* 103 */     return axisAlignedBB6;
/*     */   }
/*     */   
/*     */   public static boolean isMovingClient() {
/* 107 */     return (Util.mc.field_71439_g != null && (Util.mc.field_71439_g.field_71158_b.field_192832_b != 0.0F || Util.mc.field_71439_g.field_71158_b.field_78902_a != 0.0F));
/*     */   }
/*     */   
/*     */   public static void Method744(EventMove event, double d) {
/* 111 */     MovementInput movementInput = Util.mc.field_71439_g.field_71158_b;
/* 112 */     double d2 = movementInput.field_192832_b;
/* 113 */     double d3 = movementInput.field_78902_a;
/*     */     
/* 115 */     d3 = MathUtil.clamp(d3, -1.0D, 1.0D);
/*     */     
/* 117 */     float f = Util.mc.field_71439_g.field_70177_z;
/* 118 */     if (d2 == 0.0D && d3 == 0.0D) {
/* 119 */       event.set_x(0.0D);
/* 120 */       event.set_z(0.0D);
/*     */     } else {
/* 122 */       if (d2 != 0.0D) {
/* 123 */         if (d3 > 0.0D) {
/* 124 */           f += ((d2 > 0.0D) ? -45 : 45);
/* 125 */         } else if (d3 < 0.0D) {
/* 126 */           f += ((d2 > 0.0D) ? 45 : -45);
/*     */         } 
/* 128 */         d3 = 0.0D;
/* 129 */         if (d2 > 0.0D) {
/* 130 */           d2 = 1.0D;
/* 131 */         } else if (d2 < 0.0D) {
/* 132 */           d2 = -1.0D;
/*     */         } 
/*     */       } 
/* 135 */       event.set_x(d2 * d * Math.cos(Math.toRadians((f + 90.0F))) + d3 * d * Math.sin(Math.toRadians((f + 90.0F))));
/* 136 */       event.set_z(d2 * d * Math.sin(Math.toRadians((f + 90.0F))) - d3 * d * Math.cos(Math.toRadians((f + 90.0F))));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double Method5402(double d) {
/* 141 */     if (!Util.mc.field_71439_g.field_70122_E) return 0.0D; 
/* 142 */     if (!Util.mc.field_71439_g.field_70123_F) return 0.0D; 
/* 143 */     if (Util.mc.field_71439_g.field_70143_R != 0.0F) return 0.0D; 
/* 144 */     if (Util.mc.field_71439_g.func_70090_H()) return 0.0D; 
/* 145 */     if (Util.mc.field_71439_g.func_180799_ab()) return 0.0D; 
/* 146 */     if (Util.mc.field_71439_g.func_70617_f_()) return 0.0D; 
/* 147 */     if (Util.mc.field_71439_g.field_71158_b.field_78901_c) return 0.0D; 
/* 148 */     if (Util.mc.field_71439_g.field_71158_b.field_78899_d) return 0.0D; 
/* 149 */     return (Method5403(d)).field_72338_b - (Util.mc.field_71439_g.func_174813_aQ()).field_72338_b;
/*     */   }
/*     */   
/*     */   public static double Method718() {
/* 153 */     float f = Util.mc.field_71439_g.field_70177_z;
/* 154 */     if (Util.mc.field_71439_g.field_191988_bg < 0.0F) {
/* 155 */       f += 180.0F;
/*     */     }
/* 157 */     float f2 = 1.0F;
/* 158 */     if (Util.mc.field_71439_g.field_191988_bg < 0.0F) {
/* 159 */       f2 = -0.5F;
/* 160 */     } else if (Util.mc.field_71439_g.field_191988_bg > 0.0F) {
/* 161 */       f2 = 0.5F;
/*     */     } 
/* 163 */     if (Util.mc.field_71439_g.field_70702_br > 0.0F) {
/* 164 */       f -= 90.0F * f2;
/*     */     }
/* 166 */     if (Util.mc.field_71439_g.field_70702_br < 0.0F) {
/* 167 */       f += 90.0F * f2;
/*     */     }
/* 169 */     return Math.toRadians(f);
/*     */   }
/*     */   
/*     */   public static double[] Method732(double d) {
/* 173 */     if (!isMovingClient()) {
/* 174 */       return null;
/*     */     }
/* 176 */     double d2 = Method718();
/* 177 */     double d3 = -Math.sin(d2) * d;
/* 178 */     double d4 = Math.cos(d2) * d;
/* 179 */     return new double[] { d3, d4 };
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\PyroSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */