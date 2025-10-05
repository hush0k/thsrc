/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import java.util.Comparator;
/*    */ import java.util.Queue;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityEnderPearl;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class PearlBait
/*    */   extends Module {
/* 19 */   private final Queue<CPacketPlayer> packets = new ConcurrentLinkedQueue<>();
/* 20 */   public Setting<Boolean> guarantee = register(new Setting("Forced Strafe", Boolean.valueOf(true)));
/* 21 */   private int thrownPearlId = -1;
/*    */   
/*    */   public PearlBait() {
/* 24 */     super("PearlBait", "кидаешь перл и-не тепаешься", Module.Category.PLAYER);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 29 */     if (event.getPacket() instanceof SPacketSpawnObject) {
/* 30 */       SPacketSpawnObject packet = (SPacketSpawnObject)event.getPacket();
/* 31 */       if (packet.func_148993_l() == 65) {
/* 32 */         mc.field_71441_e.field_73010_i.stream()
/* 33 */           .min(Comparator.comparingDouble(p -> p.func_70011_f(packet.func_186880_c(), packet.func_186882_d(), packet.func_186881_e())))
/* 34 */           .ifPresent(player -> {
/*    */               if (player.equals(mc.field_71439_g) && mc.field_71439_g.field_70122_E) {
/*    */                 mc.field_71439_g.field_70159_w = 0.0D;
/*    */ 
/*    */                 
/*    */                 mc.field_71439_g.field_70181_x = 0.0D;
/*    */                 
/*    */                 mc.field_71439_g.field_70179_y = 0.0D;
/*    */                 
/*    */                 mc.field_71439_g.field_71158_b.field_192832_b = 0.0F;
/*    */                 
/*    */                 mc.field_71439_g.field_71158_b.field_78902_a = 0.0F;
/*    */                 
/*    */                 mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.0D, mc.field_71439_g.field_70161_v, false));
/*    */                 
/*    */                 this.thrownPearlId = packet.func_149001_c();
/*    */               } 
/*    */             });
/*    */       }
/* 53 */     } else if (event.getPacket() instanceof CPacketPlayer && ((Boolean)this.guarantee.getValue()).booleanValue() && this.thrownPearlId != -1) {
/* 54 */       this.packets.add(event.getPacket());
/* 55 */       event.setCanceled(true);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 61 */     if (this.thrownPearlId != -1) {
/* 62 */       for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 63 */         if (entity.func_145782_y() == this.thrownPearlId && entity instanceof EntityEnderPearl) {
/* 64 */           EntityEnderPearl pearl = (EntityEnderPearl)entity;
/* 65 */           if (pearl.field_70128_L) {
/* 66 */             this.thrownPearlId = -1;
/*    */           }
/*    */         }
/*    */       
/*    */       } 
/* 71 */     } else if (!this.packets.isEmpty()) {
/*    */       do {
/* 73 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)this.packets.poll());
/* 74 */       } while (!this.packets.isEmpty());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\PearlBait.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */