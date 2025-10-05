/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.funnygame.KDShop;
/*    */ import net.minecraft.client.gui.inventory.GuiChest;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiChest.class})
/*    */ public abstract class MixinGuiChest {
/*    */   @Inject(method = {"drawScreen"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void drawScreenHook(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
/* 14 */     if (KDShop.cancelRender)
/* 15 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinGuiChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */