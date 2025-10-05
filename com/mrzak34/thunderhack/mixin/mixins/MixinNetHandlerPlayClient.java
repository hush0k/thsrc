/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.DeathEvent;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.play.server.SPacketEntityMetadata;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({NetHandlerPlayClient.class})
/*    */ public class MixinNetHandlerPlayClient {
/*    */   @Inject(method = {"handleEntityMetadata"}, at = {@At("RETURN")}, cancellable = true)
/*    */   private void handleEntityMetadataHook(SPacketEntityMetadata packetIn, CallbackInfo info) {
/*    */     Entity entity;
/*    */     EntityPlayer player;
/* 22 */     if (Util.mc.field_71441_e != null && entity = Util.mc.field_71441_e.func_73045_a(packetIn.func_149375_d()) instanceof EntityPlayer && (player = (EntityPlayer)entity).func_110143_aJ() <= 0.0F)
/* 23 */       MinecraftForge.EVENT_BUS.post((Event)new DeathEvent(player)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinNetHandlerPlayClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */