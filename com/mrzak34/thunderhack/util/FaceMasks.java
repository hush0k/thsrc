/*    */ package com.mrzak34.thunderhack.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ public final class FaceMasks
/*    */ {
/*  9 */   public static final HashMap<EnumFacing, Integer> FACEMAP = new HashMap<>();
/*    */   
/*    */   static {
/* 12 */     FACEMAP.put(EnumFacing.DOWN, Integer.valueOf(1));
/* 13 */     FACEMAP.put(EnumFacing.WEST, Integer.valueOf(16));
/* 14 */     FACEMAP.put(EnumFacing.NORTH, Integer.valueOf(4));
/* 15 */     FACEMAP.put(EnumFacing.SOUTH, Integer.valueOf(8));
/* 16 */     FACEMAP.put(EnumFacing.EAST, Integer.valueOf(32));
/* 17 */     FACEMAP.put(EnumFacing.UP, Integer.valueOf(2));
/*    */   }
/*    */   
/*    */   public static final class Quad {
/*    */     public static final int DOWN = 1;
/*    */     public static final int UP = 2;
/*    */     public static final int NORTH = 4;
/*    */     public static final int SOUTH = 8;
/*    */     public static final int WEST = 16;
/*    */     public static final int EAST = 32;
/*    */     public static final int ALL = 63;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\FaceMasks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */