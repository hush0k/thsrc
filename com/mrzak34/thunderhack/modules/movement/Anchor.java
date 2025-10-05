/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.EntityUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class Anchor extends Module {
/*    */   public static boolean Anchoring;
/* 13 */   private final Setting<Integer> pitch = register(new Setting("Pitch", Integer.valueOf(60), Integer.valueOf(0), Integer.valueOf(90)));
/* 14 */   private final Setting<Boolean> disable = register(new Setting("AutoDisable", Boolean.valueOf(true)));
/* 15 */   private final Setting<Boolean> pull = register(new Setting("Pull", Boolean.valueOf(true)));
/*    */   int holeblocks;
/*    */   
/*    */   public Anchor() {
/* 19 */     super("Anchor", "если над холкой-движение=0 так понятно?", Module.Category.MOVEMENT);
/*    */   }
/*    */   
/*    */   public boolean isBlockHole(BlockPos blockPos) {
/* 23 */     this.holeblocks = 0;
/* 24 */     if (mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 3, 0)).func_177230_c() == Blocks.field_150350_a) {
/* 25 */       this.holeblocks++;
/*    */     }
/* 27 */     if (mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c() == Blocks.field_150350_a) {
/* 28 */       this.holeblocks++;
/*    */     }
/* 30 */     if (mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c() == Blocks.field_150350_a) {
/* 31 */       this.holeblocks++;
/*    */     }
/* 33 */     if (mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, 0)).func_177230_c() == Blocks.field_150350_a) {
/* 34 */       this.holeblocks++;
/*    */     }
/* 36 */     if (mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150357_h) {
/* 37 */       this.holeblocks++;
/*    */     }
/* 39 */     if (mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150357_h) {
/* 40 */       this.holeblocks++;
/*    */     }
/* 42 */     if (mc.field_71441_e.func_180495_p(blockPos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(blockPos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150357_h) {
/* 43 */       this.holeblocks++;
/*    */     }
/* 45 */     if (mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150357_h) {
/* 46 */       this.holeblocks++;
/*    */     }
/* 48 */     if (mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150357_h) {
/* 49 */       this.holeblocks++;
/*    */     }
/* 51 */     return (this.holeblocks >= 9);
/*    */   }
/*    */   
/*    */   public Vec3d GetCenter(double d, double d2, double d3) {
/* 55 */     double d4 = Math.floor(d) + 0.5D;
/* 56 */     double d5 = Math.floor(d2);
/* 57 */     double d6 = Math.floor(d3) + 0.5D;
/* 58 */     return new Vec3d(d4, d5, d6);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 63 */     if (mc.field_71441_e == null) {
/*    */       return;
/*    */     }
/* 66 */     if (mc.field_71439_g.field_70125_A >= ((Integer)this.pitch.getValue()).intValue()) {
/* 67 */       if (isBlockHole(getPlayerPos().func_177979_c(1)) || isBlockHole(getPlayerPos().func_177979_c(2)) || isBlockHole(getPlayerPos().func_177979_c(3)) || isBlockHole(getPlayerPos().func_177979_c(4))) {
/* 68 */         Anchoring = true;
/* 69 */         if (!((Boolean)this.pull.getValue()).booleanValue()) {
/* 70 */           mc.field_71439_g.field_70159_w = 0.0D;
/* 71 */           mc.field_71439_g.field_70179_y = 0.0D;
/*    */         } else {
/* 73 */           Vec3d center = GetCenter(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
/* 74 */           double d = Math.abs(center.field_72450_a - mc.field_71439_g.field_70165_t);
/* 75 */           double d2 = Math.abs(center.field_72449_c - mc.field_71439_g.field_70161_v);
/* 76 */           if (d > 0.1D || d2 > 0.1D) {
/* 77 */             double d3 = center.field_72450_a - mc.field_71439_g.field_70165_t;
/* 78 */             double d4 = center.field_72449_c - mc.field_71439_g.field_70161_v;
/* 79 */             mc.field_71439_g.field_70159_w = d3 / 2.0D;
/* 80 */             mc.field_71439_g.field_70179_y = d4 / 2.0D;
/*    */           } 
/*    */         } 
/*    */       } else {
/* 84 */         Anchoring = false;
/*    */       } 
/*    */     }
/* 87 */     if (((Boolean)this.disable.getValue()).booleanValue() && EntityUtil.isSafe((Entity)mc.field_71439_g)) {
/* 88 */       disable();
/*    */     }
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 93 */     Anchoring = false;
/* 94 */     this.holeblocks = 0;
/*    */   }
/*    */   
/*    */   public BlockPos getPlayerPos() {
/* 98 */     return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\Anchor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */