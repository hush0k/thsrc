/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.misc.DiscordEmbeds;
/*    */ import com.mrzak34.thunderhack.modules.misc.ToolTips;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiScreen.class})
/*    */ public class MixinGuiScreen
/*    */   extends Gui
/*    */ {
/*    */   @Inject(method = {"renderToolTip"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderToolTipHook(ItemStack stack, int x, int y, CallbackInfo info) {
/* 20 */     if (ToolTips.getInstance().isOn() && stack.func_77973_b() instanceof net.minecraft.item.ItemShulkerBox) {
/* 21 */       ToolTips.getInstance().renderShulkerToolTip(stack, x, y, null);
/* 22 */       info.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"handleComponentHover"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void handleComponentHoverHook(ITextComponent component, int x, int y, CallbackInfo info) {
/* 28 */     if (component != null) {
/* 29 */       DiscordEmbeds.saveDickPick(component.func_150256_b().func_150210_i().func_150702_b().func_150260_c(), "png");
/* 30 */       DiscordEmbeds.nado = true;
/* 31 */       DiscordEmbeds.timer.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinGuiScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */