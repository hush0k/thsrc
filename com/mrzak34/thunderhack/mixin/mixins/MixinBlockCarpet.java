/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.funnygame.GroundBoost;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockCarpet;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({BlockCarpet.class})
/*    */ public abstract class MixinBlockCarpet
/*    */   extends Block {
/*    */   public MixinBlockCarpet(Material blockMaterialIn, MapColor blockMapColorIn) {
/* 22 */     super(blockMaterialIn, blockMapColorIn);
/*    */   }
/*    */   
/*    */   @Inject(method = {"getBoundingBox"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos, CallbackInfoReturnable<AxisAlignedBB> cir) {
/* 27 */     if (((GroundBoost)Thunderhack.moduleManager.getModuleByClass(GroundBoost.class)).isEnabled()) {
/* 28 */       AxisAlignedBB CARPET_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D);
/* 29 */       cir.setReturnValue(CARPET_AABB);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinBlockCarpet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */