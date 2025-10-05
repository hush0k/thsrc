/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.misc.SolidWeb;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockWeb;
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
/*    */ @Mixin({BlockWeb.class})
/*    */ public class MixinBlockWeb extends Block {
/*    */   protected MixinBlockWeb() {
/* 20 */     super(Material.field_151569_G);
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"getCollisionBoundingBox"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable<AxisAlignedBB> cir) {
/* 26 */     if (((SolidWeb)Thunderhack.moduleManager.getModuleByClass(SolidWeb.class)).isEnabled())
/* 27 */       cir.setReturnValue(field_185505_j); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinBlockWeb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */