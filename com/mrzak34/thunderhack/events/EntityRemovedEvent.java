/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class EntityRemovedEvent extends Event {
/*    */   public Entity entity;
/*    */   
/*    */   public EntityRemovedEvent(Entity entity) {
/* 10 */     this.entity = entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EntityRemovedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */