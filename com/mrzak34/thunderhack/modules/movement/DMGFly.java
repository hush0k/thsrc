/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.EventMove;
/*    */ import com.mrzak34.thunderhack.events.EventSync;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class DMGFly
/*    */   extends Module {
/*    */   public static long lastVelocityTime;
/*    */   
/*    */   public DMGFly() {
/* 15 */     super("DMGFly", "DMGFly", Module.Category.MOVEMENT);
/*    */   }
/*    */   public static double velocityXZ; public static double velocityY;
/*    */   public static double[] getSpeed(double speed) {
/* 19 */     float yaw = mc.field_71439_g.field_70177_z;
/* 20 */     float forward = mc.field_71439_g.field_71158_b.field_192832_b;
/* 21 */     float strafe = mc.field_71439_g.field_71158_b.field_78902_a;
/* 22 */     if (forward != 0.0F) {
/* 23 */       if (strafe > 0.0F) {
/* 24 */         yaw += ((forward > 0.0F) ? -45 : 45);
/* 25 */       } else if (strafe < 0.0F) {
/* 26 */         yaw += ((forward > 0.0F) ? 45 : -45);
/*    */       } 
/* 28 */       strafe = 0.0F;
/* 29 */       if (forward > 0.0F) {
/* 30 */         forward = 1.0F;
/* 31 */       } else if (forward < 0.0F) {
/* 32 */         forward = -1.0F;
/*    */       } 
/*    */     } 
/* 35 */     return new double[] { forward * speed * 
/* 36 */         Math.cos(Math.toRadians((yaw + 90.0F))) + strafe * speed * 
/* 37 */         Math.sin(Math.toRadians((yaw + 90.0F))), forward * speed * 
/* 38 */         Math.sin(Math.toRadians((yaw + 90.0F))) - strafe * speed * 
/* 39 */         Math.cos(Math.toRadians((yaw + 90.0F))), yaw };
/*    */   }
/*    */ 
/*    */   
/*    */   public static double getProgress() {
/* 44 */     return (System.currentTimeMillis() - lastVelocityTime > 1350L) ? 0.0D : (1.0D - (
/* 45 */       System.currentTimeMillis() - lastVelocityTime) / 1350.0D);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPyroMove(EventMove e) {
/* 50 */     if (System.currentTimeMillis() - lastVelocityTime < 1350L) {
/* 51 */       double speed = Math.hypot(e.get_x(), e.get_z()) + velocityXZ - 0.25D;
/* 52 */       double[] brain = getSpeed(speed);
/* 53 */       e.set_x(brain[0]);
/* 54 */       e.set_z(brain[1]);
/* 55 */       if (velocityY > 0.0D)
/* 56 */         e.set_y(velocityY); 
/* 57 */       e.setCanceled(true);
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onUpdateWalkingPlayer(EventSync e) {
/* 63 */     if (System.currentTimeMillis() - lastVelocityTime < 1350L) {
/* 64 */       mc.field_71439_g.func_70031_b(!mc.field_71439_g.func_70051_ag());
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 70 */     if (event.getPacket() instanceof SPacketEntityVelocity) {
/* 71 */       SPacketEntityVelocity packet = (SPacketEntityVelocity)event.getPacket();
/* 72 */       if (packet.func_149412_c() == mc.field_71439_g.func_145782_y() && 
/* 73 */         System.currentTimeMillis() - lastVelocityTime > 1350L) {
/* 74 */         double vX = Math.abs(packet.func_149411_d() / 8000.0D);
/* 75 */         double vY = packet.func_149410_e() / 8000.0D;
/* 76 */         double vZ = Math.abs(packet.func_149409_f() / 8000.0D);
/* 77 */         if (vX + vZ > 0.3D) {
/* 78 */           velocityXZ = vX + vZ;
/* 79 */           lastVelocityTime = System.currentTimeMillis();
/* 80 */           velocityY = vY;
/*    */         } else {
/* 82 */           velocityXZ = 0.0D;
/* 83 */           velocityY = 0.0D;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\DMGFly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */