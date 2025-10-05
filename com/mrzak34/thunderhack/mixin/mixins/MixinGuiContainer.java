/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.misc.ItemScroller;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.lwjgl.input.Mouse;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiContainer.class})
/*    */ public abstract class MixinGuiContainer
/*    */   extends GuiScreen {
/*    */   @Shadow
/*    */   public Container field_147002_h;
/* 24 */   private final Timer delayTimer = new Timer();
/*    */ 
/*    */   
/*    */   @Shadow
/*    */   protected abstract boolean func_146981_a(Slot paramSlot, int paramInt1, int paramInt2);
/*    */   
/*    */   @Shadow
/*    */   protected abstract void func_184098_a(Slot paramSlot, int paramInt1, int paramInt2, ClickType paramClickType);
/*    */   
/*    */   @Inject(method = {"drawScreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawGradientRect(IIIIII)V", shift = At.Shift.BEFORE)})
/*    */   private void drawScreenHook(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
/* 35 */     ItemScroller scroller = (ItemScroller)Thunderhack.moduleManager.getModuleByClass(ItemScroller.class);
/*    */     
/* 37 */     for (int i1 = 0; i1 < this.field_147002_h.field_75151_b.size(); i1++) {
/* 38 */       Slot slot = this.field_147002_h.field_75151_b.get(i1);
/* 39 */       if (func_146981_a(slot, mouseX, mouseY) && slot.func_111238_b() && 
/* 40 */         scroller.isEnabled() && Mouse.isButtonDown(0) && Keyboard.isKeyDown(this.field_146297_k.field_71474_y.field_74311_E.func_151463_i()) && this.delayTimer.passedMs(((Integer)scroller.delay.getValue()).intValue())) {
/* 41 */         func_184098_a(slot, slot.field_75222_d, 0, ClickType.QUICK_MOVE);
/* 42 */         this.delayTimer.reset();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinGuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */