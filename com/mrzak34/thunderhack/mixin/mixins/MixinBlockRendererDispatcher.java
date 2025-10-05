/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.BlockRenderEvent;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({BlockRendererDispatcher.class})
/*    */ public class MixinBlockRendererDispatcher {
/*    */   @Inject(method = {"renderBlock"}, at = {@At("HEAD")})
/*    */   public void blockRenderInject(IBlockState iBlockState, BlockPos blockPos, IBlockAccess iBlockAccess, BufferBuilder bufferBuilder, CallbackInfoReturnable<Boolean> cir) {
/* 20 */     BlockRenderEvent event = new BlockRenderEvent(iBlockState.func_177230_c(), blockPos);
/* 21 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinBlockRendererDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */