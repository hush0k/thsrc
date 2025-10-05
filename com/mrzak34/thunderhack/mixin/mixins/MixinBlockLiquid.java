/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ import com.mrzak34.thunderhack.events.EventBlockCollisionBoundingBox;
/*    */ import com.mrzak34.thunderhack.events.JesusEvent;
/*    */ import com.mrzak34.thunderhack.modules.player.LiquidInteract;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockLiquid;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({BlockLiquid.class})
/*    */ public class MixinBlockLiquid extends Block {
/*    */   protected MixinBlockLiquid(Material materialIn) {
/* 23 */     super(materialIn);
/*    */   }
/*    */   
/*    */   @Inject(method = {"canCollideCheck"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void canCollideCheckHook(IBlockState blockState, boolean hitIfLiquid, CallbackInfoReturnable<Boolean> info) {
/* 28 */     info.setReturnValue(Boolean.valueOf(((hitIfLiquid && ((Integer)blockState.func_177229_b((IProperty)BlockLiquid.field_176367_b)).intValue() == 0) || LiquidInteract.getInstance().isOn())));
/*    */   }
/*    */   
/*    */   @Inject(method = {"getCollisionBoundingBox"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getCollisionBoundingBoxHook(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable<AxisAlignedBB> info) {
/* 33 */     JesusEvent event = new JesusEvent(pos);
/* 34 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 35 */     if (event.isCanceled()) {
/* 36 */       info.setReturnValue(event.getBoundingBox());
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"getCollisionBoundingBox"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable<AxisAlignedBB> callbackInfoReturnable) {
/* 42 */     EventBlockCollisionBoundingBox event = new EventBlockCollisionBoundingBox(pos);
/* 43 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 44 */     if (event.isCanceled()) {
/* 45 */       callbackInfoReturnable.setReturnValue(event.getBoundingBox());
/* 46 */       callbackInfoReturnable.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinBlockLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */