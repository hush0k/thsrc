/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ import com.mrzak34.thunderhack.events.AttackEvent;
/*    */ import com.mrzak34.thunderhack.events.EventSync;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AutoFlyme extends Module {
/* 16 */   public final Setting<Boolean> space = register(new Setting("OnlySpace", Boolean.valueOf(true)));
/* 17 */   public final Setting<Boolean> instantSpeed = register(new Setting("InstantSpeed", Boolean.valueOf(true)));
/* 18 */   public final Setting<Boolean> criticals = register(new Setting("criticals", Boolean.valueOf(true)));
/* 19 */   public final Setting<Boolean> hover = register(new Setting("hover", Boolean.valueOf(false)));
/* 20 */   public Setting<Float> hoverY = register(new Setting("hoverY", Float.valueOf(0.228F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> ((Boolean)this.hover.getValue()).booleanValue()));
/*    */ 
/*    */   
/* 23 */   private final Timer timer = new Timer();
/*    */   boolean cancelSomePackets;
/*    */   
/* 26 */   public AutoFlyme() { super("AutoFlyme", "Автоматически пишет /flyme", Module.Category.FUNNYGAME);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 46 */     this.cancelSomePackets = false; }
/*    */   public void onEnable() { if (fullNullCheck())
/*    */       return; 
/*    */     mc.field_71439_g.func_71165_d("/flyme"); } @SubscribeEvent
/* 50 */   public void onAttack(AttackEvent attackEvent) { if (((Boolean)this.criticals.getValue()).booleanValue() && 
/* 51 */       attackEvent.getStage() == 0) {
/* 52 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.1100013579D, mc.field_71439_g.field_70161_v, false));
/* 53 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.3579E-6D, mc.field_71439_g.field_70161_v, false));
/* 54 */       this.cancelSomePackets = true;
/*    */     }  }
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*    */   public void onPacketSend(PacketEvent.Send e) {
/* 61 */     if (e.getPacket() instanceof CPacketPlayer && 
/* 62 */       this.cancelSomePackets) {
/* 63 */       this.cancelSomePackets = false;
/* 64 */       e.setCanceled(true);
/*    */     }  } public void onUpdate() {
/*    */     if (!mc.field_71439_g.field_71075_bZ.field_75100_b && this.timer.passedMs(1000L) && !mc.field_71439_g.field_70122_E && (!((Boolean)this.space.getValue()).booleanValue() || mc.field_71474_y.field_74314_A.func_151470_d())) {
/*    */       mc.field_71439_g.func_71165_d("/flyme");
/*    */       this.timer.reset();
/*    */     } 
/*    */     if (((Boolean)this.hover.getValue()).booleanValue() && mc.field_71439_g.field_71075_bZ.field_75100_b && !mc.field_71439_g.field_70122_E && mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -((Float)this.hoverY.getValue()).floatValue(), 0.0D)).isEmpty())
/*    */       mc.field_71439_g.field_70181_x = -0.05000000074505806D; 
/*    */   } @SubscribeEvent
/*    */   public void onUpdateWalkingPlayer(EventSync event) {
/* 74 */     if (!((Boolean)this.instantSpeed.getValue()).booleanValue() || !mc.field_71439_g.field_71075_bZ.field_75100_b)
/* 75 */       return;  double[] dir = MathUtil.directionSpeed(1.0499999523162842D);
/* 76 */     if (mc.field_71439_g.field_71158_b.field_78902_a != 0.0F || mc.field_71439_g.field_71158_b.field_192832_b != 0.0F) {
/* 77 */       mc.field_71439_g.field_70159_w = dir[0];
/* 78 */       mc.field_71439_g.field_70179_y = dir[1];
/*    */     } else {
/* 80 */       mc.field_71439_g.field_70159_w = 0.0D;
/* 81 */       mc.field_71439_g.field_70179_y = 0.0D;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\AutoFlyme.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */