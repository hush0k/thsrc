/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.network.play.server.SPacketPlayerListItem;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class EventPlayer
/*    */   extends Event {
/*    */   private final SPacketPlayerListItem.AddPlayerData addPlayerData;
/*    */   private final SPacketPlayerListItem.Action action;
/*    */   
/*    */   public EventPlayer(SPacketPlayerListItem.AddPlayerData addPlayerData, SPacketPlayerListItem.Action action) {
/* 12 */     this.addPlayerData = addPlayerData;
/* 13 */     this.action = action;
/*    */   }
/*    */   
/*    */   public SPacketPlayerListItem.AddPlayerData getPlayerData() {
/* 17 */     return this.addPlayerData;
/*    */   }
/*    */   
/*    */   public SPacketPlayerListItem.Action getAction() {
/* 21 */     return this.action;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */