/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.misc.ExtraTab;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiPlayerTabOverlay;
/*    */ import net.minecraft.client.network.NetworkPlayerInfo;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({GuiPlayerTabOverlay.class})
/*    */ public class MixinGuiPlayerTabOverlay
/*    */   extends Gui {
/*    */   @Redirect(method = {"renderPlayerlist"}, at = @At(value = "INVOKE", target = "Ljava/util/List;subList(II)Ljava/util/List;"))
/*    */   public List<NetworkPlayerInfo> subListHook(List<NetworkPlayerInfo> list, int fromIndex, int toIndex) {
/* 19 */     return list.subList(fromIndex, ExtraTab.getINSTANCE().isEnabled() ? Math.min(((Integer)(ExtraTab.getINSTANCE()).size.getValue()).intValue(), list.size()) : toIndex);
/*    */   }
/*    */   
/*    */   @Inject(method = {"getPlayerName"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getPlayerNameHook(NetworkPlayerInfo networkPlayerInfoIn, CallbackInfoReturnable<String> info) {
/* 24 */     if (ExtraTab.getINSTANCE().isEnabled())
/* 25 */       info.setReturnValue(ExtraTab.getPlayerName(networkPlayerInfoIn)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinGuiPlayerTabOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */