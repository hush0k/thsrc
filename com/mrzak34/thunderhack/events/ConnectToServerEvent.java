/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class ConnectToServerEvent
/*    */   extends Event {
/*    */   String ip;
/*    */   
/*    */   public ConnectToServerEvent(String ip) {
/* 10 */     this.ip = ip;
/*    */   }
/*    */   
/*    */   public String getIp() {
/* 14 */     return this.ip;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\ConnectToServerEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */