/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class NcpInteractTrace extends NcpTrace {
/*    */   protected final boolean strict = false;
/*    */   protected int lastBx;
/*    */   protected int lastBy;
/*    */   
/*    */   public NcpInteractTrace() {
/* 14 */     this.forceStepEndPos = false;
/*    */   }
/*    */   protected int lastBz; protected int targetX; protected int targetY; protected int targetZ;
/*    */   public void set(double x0, double y0, double z0, double x1, double y1, double z1) {
/* 18 */     set(x0, y0, z0, x1, y1, z1, Visible.floor(x1), Visible.floor(y1), Visible.floor(z1));
/*    */   }
/*    */   
/*    */   public void set(double x0, double y0, double z0, double x1, double y1, double z1, int targetX, int targetY, int targetZ) {
/* 22 */     super.set(x0, y0, z0, x1, y1, z1);
/* 23 */     this.collides = false;
/* 24 */     this.lastBx = this.blockX;
/* 25 */     this.lastBy = this.blockY;
/* 26 */     this.lastBz = this.blockZ;
/* 27 */     this.targetX = targetX;
/* 28 */     this.targetY = targetY;
/* 29 */     this.targetZ = targetZ;
/*    */   }
/*    */   
/*    */   private boolean doesCollide(int blockX, int blockY, int blockZ) {
/* 33 */     BlockPos pos = new BlockPos(blockX, blockY, blockZ);
/* 34 */     IBlockState state = Util.mc.field_71441_e.func_180495_p(pos);
/*    */     
/* 36 */     if (!state.func_185904_a().func_76220_a() || state
/* 37 */       .func_185904_a().func_76224_d() || 
/* 38 */       !state.func_177230_c().func_176209_a(state, false))
/*    */     {
/*    */       
/* 41 */       return false;
/*    */     }
/*    */     
/* 44 */     return (state.func_185900_c((IBlockAccess)Util.mc.field_71441_e, pos).func_72320_b() == 1.0D);
/*    */   }
/*    */   
/*    */   public boolean isTargetBlock() {
/* 48 */     return (this.targetX != Integer.MAX_VALUE && this.blockX == this.targetX && this.blockY == this.targetY && this.blockZ == this.targetZ);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean step(int blockX, int blockY, int blockZ, double oX, double oY, double oZ, double dT, boolean isPrimary) {
/* 53 */     if (isTargetBlock() || !doesCollide(blockX, blockY, blockZ)) {
/* 54 */       if (isPrimary) {
/* 55 */         this.lastBx = blockX;
/* 56 */         this.lastBy = blockY;
/* 57 */         this.lastBz = blockZ;
/*    */       } 
/*    */       
/* 60 */       return true;
/*    */     } 
/*    */     
/* 63 */     this.collides = true;
/* 64 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\NcpInteractTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */