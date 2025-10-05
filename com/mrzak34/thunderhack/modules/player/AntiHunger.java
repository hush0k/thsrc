/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketPlayer;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ 
/*    */ public class AntiHunger extends Module {
/* 12 */   public Setting<Boolean> sprint = register(new Setting("Sprint", Boolean.valueOf(true)));
/* 13 */   public Setting<Boolean> noGround = register(new Setting("Ground", Boolean.valueOf(true)));
/* 14 */   public Setting<Boolean> grPacket = register(new Setting("GroundPacket", Boolean.valueOf(true)));
/*    */   
/*    */   private boolean isOnGround = false;
/*    */   
/*    */   public AntiHunger() {
/* 19 */     super("AntiHunger", "уменьшает потребление-голода", Module.Category.PLAYER);
/*    */   }
/*    */   
/*    */   public void onEnable() {
/* 23 */     if (((Boolean)this.sprint.getValue()).booleanValue() && mc.field_71439_g != null) {
/* 24 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*    */     }
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 29 */     if (((Boolean)this.sprint.getValue()).booleanValue() && mc.field_71439_g != null && mc.field_71439_g.func_70051_ag()) {
/* 30 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 36 */     if (event.getPacket() instanceof CPacketEntityAction) {
/* 37 */       CPacketEntityAction action = (CPacketEntityAction)event.getPacket();
/* 38 */       if (((Boolean)this.sprint.getValue()).booleanValue() && (action.func_180764_b() == CPacketEntityAction.Action.START_SPRINTING || action.func_180764_b() == CPacketEntityAction.Action.STOP_SPRINTING)) {
/* 39 */         event.setCanceled(true);
/*    */       }
/*    */     } 
/*    */     
/* 43 */     if (event.getPacket() instanceof CPacketPlayer) {
/* 44 */       CPacketPlayer player = (CPacketPlayer)event.getPacket();
/* 45 */       boolean ground = mc.field_71439_g.field_70122_E;
/* 46 */       if (((Boolean)this.noGround.getValue()).booleanValue() && this.isOnGround && ground && player.func_186996_b(0.0D) == (!mc.field_71439_g.func_70051_ag() ? 0.0D : mc.field_71439_g.field_70163_u)) {
/* 47 */         if (((Boolean)this.grPacket.getValue()).booleanValue()) {
/* 48 */           ((ICPacketPlayer)player).setOnGround(false);
/*    */         } else {
/*    */           
/* 51 */           mc.field_71439_g.field_70122_E = false;
/*    */         } 
/*    */       }
/* 54 */       this.isOnGround = ground;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\AntiHunger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */