/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class TotemPopEvent
/*    */   extends Event {
/*    */   private final EntityPlayer entity;
/*    */   
/*    */   public TotemPopEvent(EntityPlayer entity) {
/* 11 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public EntityPlayer getEntity() {
/* 15 */     return this.entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\TotemPopEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */