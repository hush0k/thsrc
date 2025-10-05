/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.ISPacketSpawnObject;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityTracker;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*     */ import net.minecraft.util.EnumHand;
/*     */ 
/*     */ public class HelperInstantAttack
/*     */ {
/*     */   public static void attack(AutoCrystal module, SPacketSpawnObject packet, PacketEvent.Receive event, EntityEnderCrystal entityIn, boolean slow) {
/*  20 */     attack(module, packet, event, entityIn, slow, true);
/*     */   }
/*     */   public static void attack(AutoCrystal module, SPacketSpawnObject packet, PacketEvent.Receive event, EntityEnderCrystal entityIn, boolean slow, boolean allowAntiWeakness) {
/*     */     WeaknessSwitch w;
/*  24 */     ((ISPacketSpawnObject)event.getPacket()).setAttacked(true);
/*  25 */     CPacketUseEntity p = new CPacketUseEntity((Entity)entityIn);
/*     */     
/*  27 */     if (allowAntiWeakness) {
/*  28 */       w = HelperRotation.antiWeakness(module);
/*  29 */       if (w.needsSwitch() && (
/*  30 */         w.getSlot() == -1 || !((Boolean)module.instantAntiWeak.getValue()).booleanValue())) {
/*     */         return;
/*     */       }
/*     */     } else {
/*     */       
/*  35 */       w = WeaknessSwitch.NONE;
/*     */     } 
/*     */     
/*  38 */     int lastSlot = Util.mc.field_71439_g.field_71071_by.field_70461_c;
/*  39 */     Runnable runnable = () -> {
/*     */         if (w.getSlot() != -1) {
/*     */           switch ((AutoCrystal.CooldownBypass2)module.antiWeaknessBypass.getValue()) {
/*     */             case None:
/*     */               CooldownBypass.None.switchTo(w.getSlot());
/*     */               break;
/*     */             
/*     */             case Pick:
/*     */               CooldownBypass.Pick.switchTo(w.getSlot());
/*     */               break;
/*     */             
/*     */             case Slot:
/*     */               CooldownBypass.Slot.switchTo(w.getSlot());
/*     */               break;
/*     */             
/*     */             case Swap:
/*     */               CooldownBypass.Swap.switchTo(w.getSlot());
/*     */               break;
/*     */           } 
/*     */         
/*     */         }
/*     */         if (module.breakSwing.getValue() == AutoCrystal.SwingTime.Pre) {
/*     */           Swing.Packet.swing(EnumHand.MAIN_HAND);
/*     */         }
/*     */         Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)p);
/*     */         if (module.breakSwing.getValue() == AutoCrystal.SwingTime.Post) {
/*     */           Swing.Packet.swing(EnumHand.MAIN_HAND);
/*     */         }
/*     */         if (w.getSlot() != -1) {
/*     */           switch ((AutoCrystal.CooldownBypass2)module.antiWeaknessBypass.getValue()) {
/*     */             case None:
/*     */               CooldownBypass.None.switchBack(lastSlot, w.getSlot());
/*     */               break;
/*     */             
/*     */             case Pick:
/*     */               CooldownBypass.Pick.switchBack(lastSlot, w.getSlot());
/*     */               break;
/*     */             
/*     */             case Slot:
/*     */               CooldownBypass.Slot.switchBack(lastSlot, w.getSlot());
/*     */               break;
/*     */             case Swap:
/*     */               CooldownBypass.Swap.switchBack(lastSlot, w.getSlot());
/*     */               break;
/*     */           } 
/*     */         }
/*     */       };
/*  86 */     if (w.getSlot() != -1) {
/*  87 */       HelperRotation.acquire(runnable);
/*     */     } else {
/*  89 */       runnable.run();
/*     */     } 
/*     */     
/*  92 */     module.breakTimer.reset(slow ? ((Integer)module.slowBreakDelay.getValue()).intValue() : ((Integer)module.breakDelay.getValue()).intValue());
/*     */     
/*  94 */     event.addPostEvent(() -> {
/*     */           Entity entity = Util.mc.field_71441_e.func_73045_a(packet.func_149001_c());
/*     */           
/*     */           if (entity instanceof EntityEnderCrystal) {
/*     */             module.setCrystal(entity);
/*     */           }
/*     */         });
/*     */     
/* 102 */     if (((Boolean)module.simulateExplosion.getValue()).booleanValue()) {
/* 103 */       HelperUtil.simulateExplosion(module, packet.func_186880_c(), packet.func_186882_d(), packet.func_186881_e());
/*     */     }
/*     */     
/* 106 */     if (((Boolean)module.pseudoSetDead.getValue()).booleanValue()) {
/* 107 */       event.addPostEvent(() -> {
/*     */             Entity entity = Util.mc.field_71441_e.func_73045_a(packet.func_149001_c());
/*     */             
/*     */             if (entity != null) {
/*     */               ((IEntity)entity).setPseudoDeadT(true);
/*     */             }
/*     */           });
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 118 */     if (((Boolean)module.instantSetDead.getValue()).booleanValue()) {
/* 119 */       event.setCanceled(true);
/* 120 */       Util.mc.func_152344_a(() -> {
/*     */             Entity entity = Util.mc.field_71441_e.func_73045_a(packet.func_149001_c());
/*     */             if (entity instanceof EntityEnderCrystal)
/*     */               module.crystalRender.onSpawn((EntityEnderCrystal)entity); 
/*     */             if (!event.isCanceled())
/*     */               return; 
/*     */             EntityTracker.func_187254_a((Entity)entityIn, packet.func_186880_c(), packet.func_186882_d(), packet.func_186881_e());
/*     */             Thunderhack.setDeadManager.setDead((Entity)entityIn);
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\HelperInstantAttack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */