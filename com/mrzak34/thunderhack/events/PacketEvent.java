/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.phobos.SafeRunnable;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class PacketEvent
/*    */   extends Event {
/*    */   private final Packet<?> packet;
/*    */   
/*    */   public PacketEvent(Packet<?> packet) {
/* 15 */     this.packet = packet;
/*    */   }
/*    */   
/*    */   public <T extends Packet<?>> T getPacket() {
/* 19 */     return (T)this.packet;
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class Send
/*    */     extends PacketEvent {
/*    */     public Send(Packet<?> packet) {
/* 26 */       super(packet);
/*    */     }
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class Receive extends PacketEvent {
/* 32 */     private final Deque<Runnable> postEvents = new ArrayDeque<>();
/*    */     
/*    */     public Receive(Packet<?> packet) {
/* 35 */       super(packet);
/*    */     }
/*    */     
/*    */     public void addPostEvent(SafeRunnable runnable) {
/* 39 */       this.postEvents.add(runnable);
/*    */     }
/*    */ 
/*    */     
/*    */     public Deque<Runnable> getPostEvents() {
/* 44 */       return this.postEvents;
/*    */     }
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class SendPost extends PacketEvent {
/*    */     public SendPost(Packet<?> packet) {
/* 51 */       super(packet);
/*    */     }
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class ReceivePost extends PacketEvent {
/*    */     public ReceivePost(Packet<?> packet) {
/* 58 */       super(packet);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\PacketEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */