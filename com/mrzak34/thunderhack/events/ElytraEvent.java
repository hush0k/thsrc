/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class ElytraEvent
/*    */   extends Event {
/*    */   private final Entity entity;
/*    */   
/*    */   public ElytraEvent(Entity entity) {
/* 13 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 17 */     return this.entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\ElytraEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */