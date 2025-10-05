/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.ConnectToServerEvent;
/*    */ import net.minecraft.client.multiplayer.GuiConnecting;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin(value = {GuiConnecting.class}, priority = 999)
/*    */ public class MixinGuiConnecting extends MixinGuiScreen {
/*    */   @Inject(method = {"connect"}, at = {@At("HEAD")})
/*    */   private void connectHook(String ip, int port, CallbackInfo ci) {
/* 16 */     ConnectToServerEvent event = new ConnectToServerEvent(ip);
/* 17 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinGuiConnecting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */