/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ 
/*     */ public class Sphere
/*     */ {
/*  12 */   private static final Vec3i[] SPHERE = new Vec3i[4187707];
/*  13 */   private static final int[] INDICES = new int[101];
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  18 */     SPHERE[SPHERE.length - 1] = new Vec3i(2147483647, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Sphere() {
/*  25 */     throw new AssertionError();
/*     */   }
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
/*     */   public static int getRadius(double radius) {
/*  39 */     return INDICES[MathUtil.clamp((int)Math.ceil(radius), 0, INDICES.length)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3i get(int index) {
/*  51 */     return SPHERE[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLength() {
/*  58 */     return SPHERE.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void cacheSphere() {
/*  66 */     long time = System.currentTimeMillis();
/*     */     
/*  68 */     BlockPos pos = BlockPos.field_177992_a;
/*  69 */     Set<BlockPos> positions = new TreeSet<>((o, p) -> {
/*     */           if (o.equals(p)) {
/*     */             return 0;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           int compare = Double.compare(pos.func_177951_i((Vec3i)o), pos.func_177951_i((Vec3i)p));
/*     */ 
/*     */ 
/*     */           
/*     */           if (compare == 0) {
/*     */             compare = Integer.compare(Math.abs(o.func_177958_n()) + Math.abs(o.func_177956_o()) + Math.abs(o.func_177952_p()), Math.abs(p.func_177958_n()) + Math.abs(p.func_177956_o()) + Math.abs(p.func_177952_p()));
/*     */           }
/*     */ 
/*     */           
/*     */           return (compare == 0) ? 1 : compare;
/*     */         });
/*     */ 
/*     */     
/*  89 */     double r = 100.0D;
/*  90 */     double rSquare = r * r;
/*  91 */     for (int x = pos.func_177958_n() - (int)r; x <= pos.func_177958_n() + r; x++) {
/*  92 */       for (int z = pos.func_177952_p() - (int)r; z <= pos.func_177952_p() + r; z++) {
/*  93 */         for (int y = pos.func_177956_o() - (int)r; y < pos.func_177956_o() + r; y++) {
/*     */ 
/*     */           
/*  96 */           double dist = ((pos.func_177958_n() - x) * (pos.func_177958_n() - x) + (pos.func_177952_p() - z) * (pos.func_177952_p() - z) + (pos.func_177956_o() - y) * (pos.func_177956_o() - y));
/*  97 */           if (dist < rSquare) {
/*  98 */             positions.add(new BlockPos(x, y, z));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     if (positions.size() != SPHERE.length) {
/* 105 */       throw new IllegalStateException("Unexpected Size for Sphere: " + positions
/* 106 */           .size() + ", expected " + SPHERE.length + "!");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     int i = 0;
/* 113 */     int currentDistance = 0;
/* 114 */     for (BlockPos off : positions) {
/* 115 */       if (Math.sqrt(pos.func_177951_i((Vec3i)off)) > currentDistance) {
/* 116 */         INDICES[currentDistance++] = i;
/*     */       }
/*     */       
/* 119 */       SPHERE[i++] = (Vec3i)off;
/*     */     } 
/*     */     
/* 122 */     if (currentDistance != INDICES.length - 1) {
/* 123 */       throw new IllegalStateException("Sphere Indices not initialized!");
/*     */     }
/*     */     
/* 126 */     INDICES[INDICES.length - 1] = SPHERE.length;
/* 127 */     if (SPHERE[SPHERE.length - 1].func_177958_n() == Integer.MAX_VALUE) {
/* 128 */       throw new IllegalStateException("Sphere wasn't filled!");
/*     */     }
/*     */     
/* 131 */     time = System.currentTimeMillis() - time;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\Sphere.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */