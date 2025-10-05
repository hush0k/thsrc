/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.MovementUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class GuiMove
/*    */   extends Module {
/* 16 */   public Setting<Boolean> clickBypass = register(new Setting("strict", Boolean.valueOf(false)));
/*    */   
/*    */   public GuiMove() {
/* 19 */     super("GuiMove", "GuiMove", Module.Category.MOVEMENT);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean pause = false;
/*    */   
/*    */   public void onUpdate() {
/* 26 */     if (mc.field_71462_r != null && 
/* 27 */       !(mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat)) {
/* 28 */       mc.field_71439_g.func_70031_b(true);
/* 29 */       if (Keyboard.isKeyDown(200)) {
/* 30 */         mc.field_71439_g.field_70125_A -= 5.0F;
/*    */       }
/* 32 */       if (Keyboard.isKeyDown(208)) {
/* 33 */         mc.field_71439_g.field_70125_A += 5.0F;
/*    */       }
/* 35 */       if (Keyboard.isKeyDown(205)) {
/* 36 */         mc.field_71439_g.field_70177_z += 5.0F;
/*    */       }
/* 38 */       if (Keyboard.isKeyDown(203)) {
/* 39 */         mc.field_71439_g.field_70177_z -= 5.0F;
/*    */       }
/* 41 */       if (mc.field_71439_g.field_70125_A > 90.0F) {
/* 42 */         mc.field_71439_g.field_70125_A = 90.0F;
/*    */       }
/* 44 */       if (mc.field_71439_g.field_70125_A < -90.0F) {
/* 45 */         mc.field_71439_g.field_70125_A = -90.0F;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send e) {
/* 53 */     if (pause) {
/* 54 */       pause = false;
/*    */       return;
/*    */     } 
/* 57 */     if (e.getPacket() instanceof net.minecraft.network.play.client.CPacketClickWindow && (
/* 58 */       (Boolean)this.clickBypass.getValue()).booleanValue() && mc.field_71439_g.field_70122_E && MovementUtil.isMoving() && mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, 0.0656D, 0.0D)).isEmpty()) {
/* 59 */       if (mc.field_71439_g.func_70051_ag()) {
/* 60 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*    */       }
/* 62 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.0656D, mc.field_71439_g.field_70161_v, false));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSendPost(PacketEvent.SendPost e) {
/* 69 */     if (e.getPacket() instanceof net.minecraft.network.play.client.CPacketClickWindow && 
/* 70 */       mc.field_71439_g.func_70051_ag())
/* 71 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\GuiMove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */