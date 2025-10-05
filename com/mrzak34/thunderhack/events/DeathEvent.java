/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class DeathEvent extends Event {
/*    */   public EntityPlayer player;
/*    */   
/*    */   public DeathEvent(EntityPlayer player) {
/* 10 */     this.player = player;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\DeathEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */