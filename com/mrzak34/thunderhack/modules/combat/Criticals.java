/*    */ package com.mrzak34.thunderhack.modules.combat;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.AttackEvent;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Criticals
/*    */   extends Module
/*    */ {
/* 23 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.FunnyGame));
/* 24 */   Timer timer = new Timer();
/*    */   boolean cancelSomePackets;
/*    */   
/*    */   public Criticals() {
/* 28 */     super("Criticals", "Каждый удар станет-критом", Module.Category.COMBAT);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onAttack(AttackEvent e) {
/* 33 */     if (e.getStage() == 1) {
/*    */       return;
/*    */     }
/*    */     
/* 37 */     boolean reasonToReturn = (mc.field_71439_g.field_70143_R > 0.08F || mc.field_71439_g.func_180799_ab() || ((IEntity)mc.field_71439_g).isInWeb() || mc.field_71439_g.func_184218_aH() || mc.field_71439_g.func_70617_f_() || e.getEntity() instanceof net.minecraft.entity.item.EntityEnderCrystal);
/*    */     
/* 39 */     if (reasonToReturn) {
/*    */       return;
/*    */     }
/* 42 */     if (this.mode.getValue() == Mode.Deadcode) {
/* 43 */       if (!(e.getEntity() instanceof EntityPlayer)) {
/*    */         return;
/*    */       }
/* 46 */       if (((EntityPlayer)e.getEntity()).field_70737_aN >= 7) {
/*    */         return;
/*    */       }
/* 49 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.0625D, mc.field_71439_g.field_70161_v, true));
/* 50 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
/* 51 */       mc.field_71439_g.func_71009_b(e.getEntity());
/*    */     } 
/* 53 */     if (this.mode.getValue() == Mode.Nurik && 
/* 54 */       mc.field_71439_g.field_70122_E) {
/* 55 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.0625D + Aura.interpolateRandom(-0.09F, 0.09F), mc.field_71439_g.field_70161_v, false));
/* 56 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.001D - Math.random() / 10000.0D, mc.field_71439_g.field_70161_v, false));
/*    */     } 
/*    */     
/* 59 */     if (this.mode.getValue() == Mode.FunnyGame && 
/* 60 */       mc.field_71439_g.field_70124_G && this.timer.passedMs(400L)) {
/* 61 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.0627D, mc.field_71439_g.field_70161_v, false));
/* 62 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
/* 63 */       mc.field_71439_g.func_71009_b(e.getEntity());
/* 64 */       this.timer.reset();
/* 65 */       this.cancelSomePackets = true;
/*    */     } 
/*    */     
/* 68 */     if (this.mode.getValue() == Mode.Strict) {
/* 69 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.11D, mc.field_71439_g.field_70161_v, false));
/* 70 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.1100013579D, mc.field_71439_g.field_70161_v, false));
/* 71 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.3579E-6D, mc.field_71439_g.field_70161_v, false));
/*    */     } 
/* 73 */     if (this.mode.getValue() == Mode.OldNCP) {
/* 74 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.05000000074505806D, mc.field_71439_g.field_70161_v, false));
/* 75 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
/* 76 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.012511000037193298D, mc.field_71439_g.field_70161_v, false));
/* 77 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*    */   public void onPacketSend(PacketEvent.Send e) {
/* 83 */     if (e.getPacket() instanceof CPacketPlayer && 
/* 84 */       this.cancelSomePackets) {
/* 85 */       this.cancelSomePackets = false;
/* 86 */       e.setCanceled(true);
/*    */     } 
/*    */   }
/*    */   
/*    */   private enum Mode
/*    */   {
/* 92 */     OldNCP, Strict, Nurik, FunnyGame, Deadcode;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\Criticals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */