/*     */ package com.mrzak34.thunderhack.util.surround;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.combat.Surround;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ModeUtil
/*     */ {
/*  18 */   private final Vec3d[] NormalVecArray = new Vec3d[] { new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, -1.0D) };
/*  19 */   public final SurroundMode MormalMode = new SurroundMode(Surround.Mode.Normal, this.NormalVecArray);
/*     */   
/*  21 */   private final Vec3d[] StrictModeArray = new Vec3d[] { new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, -1.0D) };
/*  22 */   public final SurroundMode StrictMode = new SurroundMode(Surround.Mode.Strict, this.StrictModeArray);
/*     */   
/*  24 */   private final Vec3d[] SemiSafeModeArray = new Vec3d[] { new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 2.0D), new Vec3d(0.0D, 0.0D, -2.0D) };
/*  25 */   public final SurroundMode SemiSafeMode = new SurroundMode(Surround.Mode.SemiSafe, this.SemiSafeModeArray);
/*     */   
/*  27 */   private final Vec3d[] SafeArray = new Vec3d[] { new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(1.0D, -1.0D, 1.0D), new Vec3d(1.0D, -1.0D, -1.0D), new Vec3d(-1.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 0.0D, -1.0D), new Vec3d(-1.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, -1.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 2.0D), new Vec3d(0.0D, 0.0D, -2.0D) };
/*  28 */   public final SurroundMode SafeMode = new SurroundMode(Surround.Mode.Safe, this.SafeArray);
/*     */   
/*  30 */   private final Vec3d[] CubicModeArray = new Vec3d[] { new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(1.0D, -1.0D, 1.0D), new Vec3d(1.0D, -1.0D, -1.0D), new Vec3d(-1.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 0.0D, -1.0D), new Vec3d(-1.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, -1.0D) };
/*  31 */   public final SurroundMode CubicMode = new SurroundMode(Surround.Mode.Cubic, this.CubicModeArray);
/*     */   
/*  33 */   private final Vec3d[] HighModeArray = new Vec3d[] { new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, -1.0D) };
/*  34 */   public final SurroundMode HighMode = new SurroundMode(Surround.Mode.High, this.HighModeArray);
/*     */   
/*  36 */   public final SurroundMode AntiFacePlaceMode = new SurroundMode(Surround.Mode.AntiFacePlace, null);
/*  37 */   public final SurroundMode DynamicMode = new SurroundMode(Surround.Mode.Dynamic, null);
/*     */ 
/*     */   
/*     */   public List<BlockPos> getBlockPositions(Surround.Mode mode) {
/*  41 */     Surround surround = (Surround)Thunderhack.moduleManager.getModuleByClass(Surround.class);
/*  42 */     ArrayList<BlockPos> arrayList = new ArrayList<>(64);
/*  43 */     if (mode == this.DynamicMode.name) {
/*  44 */       return surround.getDynamicPositions();
/*     */     }
/*  46 */     if (mode == this.AntiFacePlaceMode.name) {
/*  47 */       return surround.getAntiFacePlacePositions();
/*     */     }
/*  49 */     if (((Boolean)surround.feetBlocks.getValue()).booleanValue()) {
/*  50 */       arrayList.addAll(surround.checkHitBoxes((Entity)Util.mc.field_71439_g, Util.mc.field_71439_g.field_70163_u, -1));
/*     */     }
/*  52 */     if (((Boolean)surround.down.getValue()).booleanValue()) {
/*  53 */       arrayList.addAll(surround.checkHitBoxes((Entity)Util.mc.field_71439_g, Util.mc.field_71439_g.field_70163_u, -2));
/*     */     }
/*  55 */     Vec3d vec3d = Util.mc.field_71439_g.func_174791_d();
/*  56 */     Vec3d[] vec3dArray = (getMode(mode)).vecArray;
/*  57 */     int n = vec3dArray.length;
/*  58 */     int n2 = 0;
/*  59 */     while (n2 < n) {
/*  60 */       Vec3d vec3d2 = vec3dArray[n2];
/*  61 */       BlockPos blockPos = new BlockPos(vec3d2.func_178787_e(vec3d));
/*  62 */       if (!((Boolean)surround.smartHelping.getValue()).booleanValue() || vec3d2.field_72448_b >= 0.0D || getFacing(blockPos).isEmpty()) {
/*  63 */         arrayList.add(blockPos);
/*     */       }
/*  65 */       n2++;
/*     */     } 
/*  67 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<EnumFacing> getFacing(BlockPos blockPos) {
/*  72 */     ArrayList<EnumFacing> arrayList = new ArrayList<>();
/*  73 */     if (Util.mc.field_71441_e == null) return arrayList; 
/*  74 */     if (blockPos == null) {
/*  75 */       return arrayList;
/*     */     }
/*  77 */     EnumFacing[] enumFacingArray = EnumFacing.values();
/*  78 */     int n = enumFacingArray.length;
/*  79 */     int n2 = 0;
/*  80 */     while (n2 < n) {
/*  81 */       EnumFacing enumFacing = enumFacingArray[n2];
/*  82 */       BlockPos blockPos2 = blockPos.func_177972_a(enumFacing);
/*  83 */       IBlockState iBlockState = Util.mc.field_71441_e.func_180495_p(blockPos2);
/*  84 */       if (iBlockState != null && iBlockState.func_177230_c().func_176209_a(iBlockState, false) && !iBlockState.func_185904_a().func_76222_j()) {
/*  85 */         arrayList.add(enumFacing);
/*     */       }
/*  87 */       n2++;
/*     */     } 
/*  89 */     return arrayList;
/*     */   }
/*     */   
/*     */   private SurroundMode getMode(Surround.Mode mode) {
/*  93 */     List<SurroundMode> list = new ArrayList<>();
/*  94 */     list.add(this.MormalMode);
/*  95 */     list.add(this.StrictMode);
/*  96 */     list.add(this.SemiSafeMode);
/*  97 */     list.add(this.SafeMode);
/*  98 */     list.add(this.CubicMode);
/*  99 */     list.add(this.HighMode);
/* 100 */     list.add(this.AntiFacePlaceMode);
/* 101 */     list.add(this.DynamicMode);
/*     */     
/* 103 */     for (SurroundMode mode2 : list) {
/* 104 */       if (mode2.name == mode) {
/* 105 */         return mode2;
/*     */       }
/*     */     } 
/* 108 */     return this.MormalMode;
/*     */   }
/*     */   
/*     */   public static class SurroundMode {
/*     */     Surround.Mode name;
/*     */     Vec3d[] vecArray;
/*     */     
/*     */     public SurroundMode(Surround.Mode name, Vec3d[] vec3dArray) {
/* 116 */       this.name = name;
/* 117 */       this.vecArray = vec3dArray;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\surround\ModeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */