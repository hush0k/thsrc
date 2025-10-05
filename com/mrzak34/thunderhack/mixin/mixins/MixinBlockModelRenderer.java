/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.render.XRay;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.BlockModelRenderer;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({BlockModelRenderer.class})
/*    */ public class MixinBlockModelRenderer {
/*    */   @Inject(method = {"renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;Z)Z"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void renderModelHook(IBlockAccess blockAccess, IBakedModel bakedModel, IBlockState blockState, BlockPos blockPos, BufferBuilder bufferBuilder, boolean b, CallbackInfoReturnable<Boolean> info) {
/*    */     try {
/* 22 */       if (((XRay)Thunderhack.moduleManager.getModuleByClass(XRay.class)).isOn() && !((XRay)Thunderhack.moduleManager.getModuleByClass(XRay.class)).shouldRender(blockState.func_177230_c()).booleanValue()) {
/* 23 */         info.setReturnValue(Boolean.valueOf(false));
/* 24 */         info.cancel();
/*    */       } 
/* 26 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   @ModifyArg(method = {"renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModelFlat(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z"))
/*    */   private boolean renderModelFlatHook(boolean input) {
/*    */     try {
/* 33 */       if (((XRay)Thunderhack.moduleManager.getModuleByClass(XRay.class)).isOn()) {
/* 34 */         return false;
/*    */       }
/*    */     }
/* 37 */     catch (Exception exception) {}
/*    */     
/* 39 */     return input;
/*    */   }
/*    */   
/*    */   @ModifyArg(method = {"renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModelSmooth(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z"))
/*    */   private boolean renderModelSmoothHook(boolean input) {
/*    */     try {
/* 45 */       if (((XRay)Thunderhack.moduleManager.getModuleByClass(XRay.class)).isOn()) {
/* 46 */         return false;
/*    */       }
/*    */     }
/* 49 */     catch (Exception exception) {}
/*    */     
/* 51 */     return input;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinBlockModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */