/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.misc.Shulkerception;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.inventory.SlotShulkerBox;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ @Mixin({SlotShulkerBox.class})
/*    */ public class MixinSlotShulkerBox
/*    */ {
/*    */   @Inject(method = {"isItemValid"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void isItemValid(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
/* 19 */     if (((Shulkerception)Thunderhack.moduleManager.getModuleByClass(Shulkerception.class)).isEnabled() && Block.func_149634_a(stack.func_77973_b()) instanceof net.minecraft.block.BlockShulkerBox)
/* 20 */       ci.setReturnValue(Boolean.valueOf(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinSlotShulkerBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */