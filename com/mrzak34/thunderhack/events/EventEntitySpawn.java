/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class EventEntitySpawn extends Event {
/*    */   private final Entity entity;
/*    */   
/*    */   public EventEntitySpawn(Entity entity) {
/* 10 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 14 */     return this.entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventEntitySpawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */