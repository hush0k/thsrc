/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.EnumHand;
/*    */ 
/*    */ public class AutoOzera
/*    */   extends Module {
/* 15 */   private final Setting<Mode> mainMode = register(new Setting("Version", Mode.New));
/* 16 */   public enum Mode { Old, New; }
/*    */   
/* 18 */   public Setting<Integer> delay = register(new Setting("DelayOnUse", Integer.valueOf(200), Integer.valueOf(100), Integer.valueOf(2000)));
/* 19 */   public Setting<Boolean> negativeLakeEff = register(new Setting("RemoveEffects", Boolean.valueOf(false)));
/* 20 */   public Timer timer = new Timer();
/*    */   
/*    */   public AutoOzera() {
/* 23 */     super("AutoOzera", "Пьёт Родные Озёра", Module.Category.FUNNYGAME);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 28 */     if (this.timer.passedMs(((Integer)this.delay.getValue()).intValue()) && InventoryUtil.getOzeraAtHotbar((this.mainMode.getValue() == Mode.Old)) != -1 && !mc.field_71439_g.func_70644_a(MobEffects.field_76420_g)) {
/* 29 */       int hotbarslot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 30 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(InventoryUtil.getOzeraAtHotbar((this.mainMode.getValue() == Mode.Old))));
/* 31 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 32 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(hotbarslot));
/* 33 */       this.timer.reset();
/*    */     } 
/* 35 */     if (((Boolean)this.negativeLakeEff.getValue()).booleanValue()) {
/* 36 */       if (mc.field_71439_g.func_70644_a(MobEffects.field_188424_y)) {
/* 37 */         mc.field_71439_g.func_184596_c(MobEffects.field_188424_y);
/*    */       }
/* 39 */       if (mc.field_71439_g.func_70644_a(MobEffects.field_76431_k)) {
/* 40 */         mc.field_71439_g.func_184596_c(MobEffects.field_76431_k);
/*    */       }
/* 42 */       if (mc.field_71439_g.func_70644_a(MobEffects.field_76440_q))
/* 43 */         mc.field_71439_g.func_184596_c(MobEffects.field_76440_q); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\AutoOzera.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */