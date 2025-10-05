/*    */ package com.mrzak34.thunderhack.util;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class PositionforFP {
/*    */   private final double x;
/*    */   private final double y;
/*    */   private final double z;
/*    */   private final float yaw;
/*    */   private final float pitch;
/*    */   private final float head;
/*    */   
/*    */   public PositionforFP(EntityPlayer player) {
/* 14 */     this.x = player.field_70165_t;
/* 15 */     this.y = player.field_70163_u;
/* 16 */     this.z = player.field_70161_v;
/* 17 */     this.yaw = player.field_70177_z;
/* 18 */     this.pitch = player.field_70125_A;
/* 19 */     this.head = player.field_70759_as;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 23 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 27 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 31 */     return this.z;
/*    */   }
/*    */   
/*    */   public float getYaw() {
/* 35 */     return this.yaw;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 39 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public float getHead() {
/* 43 */     return this.head;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\PositionforFP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */