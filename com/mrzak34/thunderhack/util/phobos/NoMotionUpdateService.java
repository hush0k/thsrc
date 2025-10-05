/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.EventPostSync;
/*    */ import com.mrzak34.thunderhack.events.EventSync;
/*    */ import com.mrzak34.thunderhack.events.NoMotionUpdateEvent;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoMotionUpdateService
/*    */ {
/*    */   private boolean awaiting;
/*    */   
/*    */   public void init() {
/* 24 */     MinecraftForge.EVENT_BUS.register(this);
/*    */   }
/*    */   
/*    */   public void unload() {
/* 28 */     MinecraftForge.EVENT_BUS.unregister(this);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 33 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.PositionRotation) {
/* 34 */       setAwaiting(false);
/*    */     }
/* 36 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Position) {
/* 37 */       setAwaiting(false);
/*    */     }
/* 39 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Rotation) {
/* 40 */       setAwaiting(false);
/*    */     }
/* 42 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer) {
/* 43 */       setAwaiting(false);
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onMotion(EventSync e) {
/* 49 */     if (e.isCanceled()) {
/*    */       return;
/*    */     }
/*    */     
/* 53 */     setAwaiting(true);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPost(EventPostSync e) {
/* 59 */     if (e.isCanceled()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 64 */     if (isAwaiting()) {
/* 65 */       NoMotionUpdateEvent noMotionUpdate = new NoMotionUpdateEvent();
/* 66 */       MinecraftForge.EVENT_BUS.post((Event)noMotionUpdate);
/*    */     } 
/*    */     
/* 69 */     setAwaiting(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAwaiting() {
/* 74 */     return this.awaiting;
/*    */   }
/*    */   
/*    */   public void setAwaiting(boolean awaiting) {
/* 78 */     this.awaiting = awaiting;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\NoMotionUpdateService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */