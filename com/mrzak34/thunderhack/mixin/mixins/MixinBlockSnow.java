/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.funnygame.GroundBoost;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockSnow;
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
/*    */ @Mixin({BlockSnow.class})
/*    */ public abstract class MixinBlockSnow extends Block {
/*    */   protected MixinBlockSnow(Material materialIn) {
/* 20 */     super(materialIn);
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"getCollisionBoundingBox"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos, CallbackInfoReturnable<AxisAlignedBB> cir) {
/* 26 */     if (((GroundBoost)Thunderhack.moduleManager.getModuleByClass(GroundBoost.class)).isEnabled())
/* 27 */       cir.setReturnValue(null); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinBlockSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */