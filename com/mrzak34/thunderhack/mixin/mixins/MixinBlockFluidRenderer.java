/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.render.XRay;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.BlockFluidRenderer;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({BlockFluidRenderer.class})
/*    */ public class MixinBlockFluidRenderer {
/*    */   @Inject(method = {"renderFluid"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderFluidHook(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, BufferBuilder bufferBuilder, CallbackInfoReturnable<Boolean> info) {
/* 19 */     if (((XRay)Thunderhack.moduleManager.getModuleByClass(XRay.class)).isOn() && !((XRay)Thunderhack.moduleManager.getModuleByClass(XRay.class)).shouldRender(blockState.func_177230_c()).booleanValue()) {
/* 20 */       info.setReturnValue(Boolean.valueOf(false));
/* 21 */       info.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinBlockFluidRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */