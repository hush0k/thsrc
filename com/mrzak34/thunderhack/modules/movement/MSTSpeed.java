/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ public class MSTSpeed
/*    */   extends Module
/*    */ {
/* 15 */   static int ticks = 0;
/* 16 */   static int maxticks2 = 1;
/* 17 */   public Setting<Integer> maxticks = register(new Setting("Ticks", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(100)));
/* 18 */   public Setting<Float> speed = register(new Setting("Speed", Float.valueOf(0.7F), Float.valueOf(0.1F), Float.valueOf(2.0F)));
/* 19 */   public Setting<Float> airspeed = register(new Setting("AirSpeed", Float.valueOf(0.7F), Float.valueOf(0.1F), Float.valueOf(2.0F)));
/* 20 */   public Setting<Boolean> onlyGround = register(new Setting("onlyGround", Boolean.valueOf(true)));
/*    */   public MSTSpeed() {
/* 22 */     super("DMGSpeed", "Matrix moment", Module.Category.MOVEMENT);
/*    */   }
/*    */   
/*    */   public static double getProgress() {
/* 26 */     return ticks / maxticks2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 31 */     if (ticks > 0) {
/* 32 */       ticks--;
/*    */     }
/* 34 */     if (mc.field_71439_g.field_191988_bg == 0.0F && mc.field_71439_g.field_70702_br == 0.0F) {
/*    */       return;
/*    */     }
/* 37 */     maxticks2 = ((Integer)this.maxticks.getValue()).intValue();
/* 38 */     if (ticks > 0) {
/* 39 */       mc.field_71439_g.func_70031_b(true);
/* 40 */       if (mc.field_71439_g.field_70122_E) {
/* 41 */         mc.field_71439_g.field_70159_w = -MathHelper.func_76126_a(get_rotation_yaw()) * ((Float)this.speed.getValue()).floatValue();
/* 42 */         mc.field_71439_g.field_70179_y = MathHelper.func_76134_b(get_rotation_yaw()) * ((Float)this.speed.getValue()).floatValue();
/* 43 */       } else if (((Boolean)this.onlyGround.getValue()).booleanValue() && !mc.field_71439_g.field_70122_E) {
/* 44 */         mc.field_71439_g.field_70159_w = -MathHelper.func_76126_a(get_rotation_yaw()) * ((Float)this.airspeed.getValue()).floatValue();
/* 45 */         mc.field_71439_g.field_70179_y = MathHelper.func_76134_b(get_rotation_yaw()) * ((Float)this.airspeed.getValue()).floatValue();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 52 */     if (event.getPacket() instanceof SPacketEntityVelocity && (
/* 53 */       (SPacketEntityVelocity)event.getPacket()).func_149412_c() == mc.field_71439_g.func_145782_y()) {
/* 54 */       ticks = ((Integer)this.maxticks.getValue()).intValue();
/*    */     }
/*    */     
/* 57 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
/* 58 */       ticks = 0;
/*    */     }
/*    */   }
/*    */   
/*    */   private float get_rotation_yaw() {
/* 63 */     float rotation_yaw = mc.field_71439_g.field_70177_z;
/* 64 */     if (mc.field_71439_g.field_191988_bg < 0.0F) {
/* 65 */       rotation_yaw += 180.0F;
/*    */     }
/* 67 */     float n = 1.0F;
/* 68 */     if (mc.field_71439_g.field_191988_bg < 0.0F) {
/* 69 */       n = -0.5F;
/* 70 */     } else if (mc.field_71439_g.field_191988_bg > 0.0F) {
/* 71 */       n = 0.5F;
/*    */     } 
/* 73 */     if (mc.field_71439_g.field_70702_br > 0.0F) {
/* 74 */       rotation_yaw -= 90.0F * n;
/*    */     }
/* 76 */     if (mc.field_71439_g.field_70702_br < 0.0F) {
/* 77 */       rotation_yaw += 90.0F * n;
/*    */     }
/* 79 */     return rotation_yaw * 0.017453292F;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\MSTSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */