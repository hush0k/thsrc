/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ 
/*    */ public class AutoLeave extends Module {
/*  9 */   public Setting<Float> health = register(new Setting("health", Float.valueOf(4.0F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/* 10 */   public Setting<Boolean> leaveOnEnable = register(new Setting("LeaveOnEnable", Boolean.valueOf(true)));
/*    */   public AutoLeave() {
/* 12 */     super("AutoLeave", "ливает если твое хвх-подходит к концу", Module.Category.FUNNYGAME);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 17 */     if (mc.field_71439_g != null && mc.field_71441_e != null && ((Boolean)this.leaveOnEnable.getValue()).booleanValue()) {
/* 18 */       for (int i = 0; i < 1000; i++) {
/* 19 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t + 100.0D, mc.field_71439_g.field_70163_u + 100.0D, mc.field_71439_g.field_70161_v + 100.0D, false));
/*    */       }
/* 21 */       toggle();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 27 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/* 30 */     if (mc.field_71439_g.func_110143_aJ() <= ((Float)this.health.getValue()).floatValue() && mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_190929_cY && mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_190929_cY) {
/* 31 */       for (int i = 0; i < 1000; i++) {
/* 32 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t + 100.0D, mc.field_71439_g.field_70163_u + 100.0D, mc.field_71439_g.field_70161_v + 100.0D, false));
/*    */       }
/* 34 */       toggle();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\AutoLeave.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */