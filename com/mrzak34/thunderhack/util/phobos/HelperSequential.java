/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketUseEntity;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.network.play.server.SPacketBlockChange;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HelperSequential
/*     */ {
/*  24 */   private final Timer timer = new Timer();
/*     */   private final AutoCrystal module;
/*     */   private volatile BlockPos expecting;
/*     */   private volatile Vec3d crystalPos;
/*     */   
/*     */   public HelperSequential(AutoCrystal module) {
/*  30 */     this.module = module;
/*  31 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.SendPost e) {
/*  36 */     if (e.getPacket() instanceof net.minecraft.network.play.client.CPacketUseEntity) {
/*  37 */       Entity entity = Util.mc.field_71441_e.func_73045_a(((ICPacketUseEntity)e.getPacket()).getEntityId());
/*  38 */       if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/*  39 */         if (((Boolean)this.module.endSequenceOnBreak.getValue()).booleanValue()) {
/*  40 */           setExpecting(null);
/*     */         } else {
/*  42 */           this.crystalPos = entity.func_174791_d();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  50 */     if (Util.mc.field_71439_g == null || Util.mc.field_71441_e == null) {
/*     */       return;
/*     */     }
/*  53 */     if (e.getPacket() instanceof SPacketSoundEffect) {
/*  54 */       Vec3d cPos = this.crystalPos;
/*  55 */       if (((Boolean)this.module.endSequenceOnExplosion.getValue()).booleanValue() && ((SPacketSoundEffect)e
/*  56 */         .getPacket()).func_186977_b() == SoundCategory.BLOCKS && ((SPacketSoundEffect)e
/*  57 */         .getPacket()).func_186978_a() == SoundEvents.field_187539_bB && cPos != null && cPos
/*     */         
/*  59 */         .func_186679_c(((SPacketSoundEffect)e.getPacket()).func_149207_d(), ((SPacketSoundEffect)e.getPacket()).func_149211_e(), ((SPacketSoundEffect)e.getPacket()).func_149210_f()) < 144.0D) {
/*  60 */         setExpecting(null);
/*     */       }
/*     */     } 
/*  63 */     if (e.getPacket() instanceof SPacketSpawnObject && (
/*  64 */       (SPacketSpawnObject)e.getPacket()).func_148993_l() == 51) {
/*     */ 
/*     */       
/*  67 */       BlockPos pos = new BlockPos(((SPacketSpawnObject)e.getPacket()).func_186880_c(), ((SPacketSpawnObject)e.getPacket()).func_186882_d(), ((SPacketSpawnObject)e.getPacket()).func_186881_e());
/*  68 */       if (pos.func_177977_b().equals(this.expecting)) {
/*  69 */         if (((Boolean)this.module.endSequenceOnSpawn.getValue()).booleanValue()) {
/*  70 */           setExpecting(null);
/*  71 */         } else if (this.crystalPos == null) {
/*  72 */           this
/*     */ 
/*     */             
/*  75 */             .crystalPos = new Vec3d(((SPacketSpawnObject)e.getPacket()).func_186880_c(), ((SPacketSpawnObject)e.getPacket()).func_186882_d(), ((SPacketSpawnObject)e.getPacket()).func_186881_e());
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  80 */     if (e.getPacket() instanceof SPacketBlockChange) {
/*  81 */       BlockPos expected = this.expecting;
/*  82 */       if (expected != null && expected.equals(((SPacketBlockChange)e.getPacket()).func_179827_b()) && (
/*  83 */         (Boolean)this.module.antiPlaceFail.getValue()).booleanValue() && this.crystalPos == null) {
/*  84 */         this.module.placeTimer.setTime(0L);
/*  85 */         setExpecting(null);
/*  86 */         if (((Boolean)this.module.debugAntiPlaceFail.getValue()).booleanValue()) {
/*  87 */           Util.mc.func_152344_a(() -> Command.sendMessage("Crystal failed to place!"));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockingPlacement() {
/*  96 */     return (((Boolean)this.module.sequential.getValue()).booleanValue() && this.expecting != null && 
/*     */       
/*  98 */       !this.timer.passedMs(((Integer)this.module.seqTime.getValue()).intValue()));
/*     */   }
/*     */   
/*     */   public void setExpecting(BlockPos expecting) {
/* 102 */     this.timer.reset();
/* 103 */     this.expecting = expecting;
/* 104 */     this.crystalPos = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\HelperSequential.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */