/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.player.AutoTool;
/*     */ import com.mrzak34.thunderhack.modules.player.NoClip;
/*     */ import com.mrzak34.thunderhack.modules.render.XRay;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({Block.class})
/*     */ public abstract class MixinBlock
/*     */ {
/*     */   @Shadow
/*     */   @Deprecated
/*     */   public abstract float func_176195_g(IBlockState paramIBlockState, World paramWorld, BlockPos paramBlockPos);
/*     */   
/*     */   @Inject(method = {"isFullCube"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void isFullCubeHook(IBlockState blockState, CallbackInfoReturnable<Boolean> info) {
/*     */     try {
/*  41 */       if (((XRay)Thunderhack.moduleManager.getModuleByClass(XRay.class)).isOn() && ((Boolean)((XRay)Thunderhack.moduleManager.getModuleByClass(XRay.class)).wh.getValue()).booleanValue()) {
/*  42 */         info.setReturnValue(((XRay)Thunderhack.moduleManager.getModuleByClass(XRay.class)).shouldRender(Block.class.cast(this)));
/*     */       }
/*  44 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   public abstract BlockStateContainer func_176194_O();
/*     */   
/*     */   @Inject(method = {"addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void addCollisionBoxToListHook(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState, CallbackInfo info) {
/*  53 */     if (entityIn != null && Util.mc.field_71439_g != null && entityIn
/*     */       
/*  55 */       .equals(Util.mc.field_71439_g) && ((NoClip)Thunderhack.moduleManager
/*  56 */       .getModuleByClass(NoClip.class)).isOn() && ((NoClip)Thunderhack.moduleManager
/*  57 */       .getModuleByClass(NoClip.class)).canNoClip() && entityIn
/*  58 */       .equals(Util.mc.field_71439_g) && ((NoClip)Thunderhack.moduleManager.getModuleByClass(NoClip.class)).mode.getValue() != NoClip.Mode.CC && (Util.mc.field_71474_y.field_74311_E
/*  59 */       .func_151470_d() || (!Objects.equals(pos, (new BlockPos((Entity)Util.mc.field_71439_g)).func_177982_a(0, -1, 0)) && !Objects.equals(pos, (new BlockPos((Entity)Util.mc.field_71439_g)).func_177982_a(0, -2, 0))))) {
/*  60 */       info.cancel();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"getPlayerRelativeBlockHardness"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos, CallbackInfoReturnable<Float> ci) {
/*  69 */     AutoTool autoTool = (AutoTool)Thunderhack.moduleManager.getModuleByClass(AutoTool.class);
/*  70 */     if (autoTool.isEnabled() && ((Boolean)autoTool.silent.getValue()).booleanValue()) {
/*  71 */       float f = state.func_185887_b(worldIn, pos);
/*  72 */       if (f < 0.0F) {
/*  73 */         ci.setReturnValue(Float.valueOf(0.0F));
/*     */       } else {
/*  75 */         ci.setReturnValue(
/*  76 */             Float.valueOf(!canHarvestBlock(state, player.field_71071_by.func_70301_a(autoTool.itemIndex)) ? (
/*  77 */               getDigSpeed(state, player.field_71071_by.func_70301_a(autoTool.itemIndex)) / f / 100.0F) : (
/*  78 */               getDigSpeed(state, player.field_71071_by.func_70301_a(autoTool.itemIndex)) / f / 30.0F)));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  83 */     NoClip nclip = (NoClip)Thunderhack.moduleManager.getModuleByClass(NoClip.class);
/*  84 */     if (nclip.isEnabled()) {
/*  85 */       float f = state.func_185887_b(worldIn, pos);
/*  86 */       if (f < 0.0F) {
/*  87 */         ci.setReturnValue(Float.valueOf(0.0F));
/*     */       } else {
/*  89 */         ci.setReturnValue(
/*  90 */             Float.valueOf(!canHarvestBlock(state, player.field_71071_by.func_70301_a(nclip.itemIndex)) ? (
/*  91 */               getDigSpeed(state, player.field_71071_by.func_70301_a(nclip.itemIndex)) / f / 100.0F) : (
/*  92 */               getDigSpeed(state, player.field_71071_by.func_70301_a(nclip.itemIndex)) / f / 30.0F)));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDigSpeed(IBlockState state, ItemStack stack) {
/* 100 */     double str = stack.func_150997_a(state);
/* 101 */     int effect = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, stack);
/* 102 */     return (float)Math.max(str + ((str > 1.0D) ? ((effect * effect) + 1.0D) : 0.0D), 0.0D);
/*     */   }
/*     */   
/*     */   private boolean canHarvestBlock(IBlockState state, ItemStack stack) {
/* 106 */     if (state.func_185904_a().func_76229_l()) {
/* 107 */       return true;
/*     */     }
/* 109 */     String tool = state.func_177230_c().getHarvestTool(state);
/* 110 */     if (stack.func_190926_b() || tool == null) {
/* 111 */       return Util.mc.field_71439_g.func_184823_b(state);
/*     */     }
/* 113 */     int toolLevel = stack.func_77973_b().getHarvestLevel(stack, tool, (EntityPlayer)Util.mc.field_71439_g, state);
/* 114 */     if (toolLevel < 0) {
/* 115 */       return Util.mc.field_71439_g.func_184823_b(state);
/*     */     }
/* 117 */     return (toolLevel >= state.func_177230_c().getHarvestLevel(state));
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */