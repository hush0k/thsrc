/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class AttackEvent extends Event {
/*    */   short stage;
/*    */   Entity entity;
/*    */   
/*    */   public AttackEvent(Entity attack, short stage) {
/* 13 */     this.entity = attack;
/* 14 */     this.stage = stage;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 18 */     return this.entity;
/*    */   }
/*    */   
/*    */   public short getStage() {
/* 22 */     return this.stage;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\AttackEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */