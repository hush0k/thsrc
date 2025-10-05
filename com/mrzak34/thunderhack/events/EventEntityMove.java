/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class EventEntityMove
/*    */   extends Event {
/*    */   private final Entity ctx;
/*    */   private final Vec3d from;
/*    */   
/*    */   public EventEntityMove(Entity ctx, Vec3d from) {
/* 13 */     this.ctx = ctx;
/* 14 */     this.from = from;
/*    */   }
/*    */   
/*    */   public Vec3d from() {
/* 18 */     return this.from;
/*    */   }
/*    */   
/*    */   public Entity ctx() {
/* 22 */     return this.ctx;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventEntityMove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */