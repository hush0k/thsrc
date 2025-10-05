/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.movement.GuiMove;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.minecraft.util.MovementInput;
/*    */ import net.minecraft.util.MovementInputFromOptions;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ 
/*    */ 
/*    */ @Mixin(value = {MovementInputFromOptions.class}, priority = 10000)
/*    */ public abstract class MixinMovementInputFromOptions
/*    */   extends MovementInput
/*    */ {
/*    */   @Redirect(method = {"updatePlayerMoveState"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
/*    */   public boolean isKeyPressed(KeyBinding keyBinding) {
/* 21 */     int keyCode = keyBinding.func_151463_i();
/* 22 */     if (keyCode > 0 && keyCode < 256 && (
/* 23 */       (GuiMove)Thunderhack.moduleManager.getModuleByClass(GuiMove.class)).isEnabled() && 
/* 24 */       (Minecraft.func_71410_x()).field_71462_r != null && 
/* 25 */       !((Minecraft.func_71410_x()).field_71462_r instanceof net.minecraft.client.gui.GuiChat) && 
/* 26 */       keyCode != (Minecraft.func_71410_x()).field_71474_y.field_74311_E.func_151463_i()) {
/* 27 */       return Keyboard.isKeyDown(keyCode);
/*    */     }
/*    */ 
/*    */     
/* 31 */     return keyBinding.func_151470_d();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinMovementInputFromOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */