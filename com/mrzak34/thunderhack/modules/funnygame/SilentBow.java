/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ 
/*    */ public class SilentBow extends Module {
/* 14 */   public Setting<Boolean> bomb = register(new Setting("Bomb", Boolean.valueOf(false)));
/* 15 */   private int prev_slot = -2;
/* 16 */   private int ticks = 4;
/*    */   public SilentBow() {
/* 18 */     super("SilentBow", "Стреляет из лука-без свапа", "SilentBow", Module.Category.FUNNYGAME);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 23 */     this.ticks = 4;
/* 24 */     int bowslot = InventoryUtil.getBowAtHotbar();
/* 25 */     this.prev_slot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 26 */     if (bowslot != -1) {
/* 27 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(bowslot));
/* 28 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/* 29 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 30 */       if (((Boolean)this.bomb.getValue()).booleanValue()) {
/* 31 */         for (int i = 0; i < 106; i++) {
/* 32 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.0E-10D, mc.field_71439_g.field_70161_v, true));
/* 33 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.0E-10D, mc.field_71439_g.field_70161_v, false));
/*    */         } 
/*    */       }
/*    */     } else {
/* 37 */       Command.sendMessage("У тебя лука в хотбаре нема, дуранчеус");
/* 38 */       toggle();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 45 */     if (this.ticks > 0) {
/* 46 */       this.ticks--;
/* 47 */     } else if (this.prev_slot != -2) {
/* 48 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.prev_slot));
/* 49 */       this.prev_slot = -2;
/* 50 */       this.ticks = 4;
/*    */       
/* 52 */       toggle();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\SilentBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */