/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ import net.minecraft.network.play.server.SPacketHeldItemChange;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SwitchManager
/*    */ {
/* 20 */   private final Timer timer = new Timer();
/*    */ 
/*    */   
/*    */   private volatile int last_slot;
/*    */ 
/*    */ 
/*    */   
/*    */   public void unload() {
/* 28 */     MinecraftForge.EVENT_BUS.unregister(this);
/*    */   }
/*    */   
/*    */   public void init() {
/* 32 */     MinecraftForge.EVENT_BUS.register(this);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send e) {
/* 37 */     if (e.getPacket() instanceof CPacketHeldItemChange) {
/* 38 */       this.timer.reset();
/* 39 */       this.last_slot = ((CPacketHeldItemChange)e.getPacket()).func_149614_c();
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive e) {
/* 45 */     if (Util.mc.field_71439_g == null || Util.mc.field_71441_e == null)
/* 46 */       return;  if (e.getPacket() instanceof SPacketHeldItemChange) {
/* 47 */       this.last_slot = ((SPacketHeldItemChange)e.getPacket()).func_149385_c();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getLastSwitch() {
/* 57 */     return this.timer.getTimeMs();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSlot() {
/* 64 */     return this.last_slot;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\SwitchManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */