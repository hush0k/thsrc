/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.client.MultiConnect;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.client.gui.ServerListEntryNormal;
/*    */ import org.lwjgl.input.Mouse;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({ServerListEntryNormal.class})
/*    */ public abstract class MixinServerListEntryNormal
/*    */ {
/*    */   @Inject(method = {"mousePressed"}, at = {@At("HEAD")})
/*    */   public void Z(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY, CallbackInfoReturnable<Boolean> cir) {
/* 20 */     if (MultiConnect.getInstance().isEnabled() && 
/* 21 */       Mouse.isButtonDown(1)) {
/* 22 */       (MultiConnect.getInstance()).serverData.add(Integer.valueOf(slotIndex));
/* 23 */       System.out.println("THUNDER HACK добавлен слот " + slotIndex);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"drawEntry"}, at = {@At("TAIL")})
/*    */   public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks, CallbackInfo ci) {
/* 30 */     if (MultiConnect.getInstance().isEnabled() && 
/* 31 */       (MultiConnect.getInstance()).serverData.contains(Integer.valueOf(slotIndex)))
/* 32 */       Util.fr.func_78276_b("SELECTED", x - 45, y + 14, -1); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinServerListEntryNormal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */