/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class CombatManager
/*     */ {
/*  17 */   private final Map<EntityPlayer, PopCounter> pops = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  25 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public void unload() {
/*  29 */     MinecraftForge.EVENT_BUS.unregister(this);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  34 */     if (Util.mc.field_71439_g == null || Util.mc.field_71441_e == null)
/*  35 */       return;  if (e.getPacket() instanceof SPacketEntityStatus) {
/*  36 */       switch (((SPacketEntityStatus)e.getPacket()).func_149160_c()) {
/*     */         case 3:
/*  38 */           Util.mc.func_152344_a(() -> onDeath((Util.mc.field_71441_e == null) ? null : ((SPacketEntityStatus)e.getPacket()).func_149161_a((World)Util.mc.field_71441_e)));
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 35:
/*  44 */           Util.mc.func_152344_a(() -> onTotemPop((SPacketEntityStatus)e.getPacket()));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetCombatManager() {
/*  52 */     this.pops.clear();
/*     */   }
/*     */   
/*     */   public int getPops(Entity player) {
/*  56 */     if (player instanceof EntityPlayer) {
/*  57 */       PopCounter popCounter = this.pops.get(player);
/*  58 */       if (popCounter != null) {
/*  59 */         return popCounter.getPops();
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return 0;
/*     */   }
/*     */   
/*     */   public long lastPop(Entity player) {
/*  67 */     if (player instanceof EntityPlayer) {
/*  68 */       PopCounter popCounter = this.pops.get(player);
/*  69 */       if (popCounter != null) {
/*  70 */         return popCounter.lastPop();
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return 2147483647L;
/*     */   }
/*     */   
/*     */   private void onTotemPop(SPacketEntityStatus packet) {
/*  78 */     Entity player = packet.func_149161_a((World)Util.mc.field_71441_e);
/*  79 */     if (player instanceof EntityPlayer) {
/*  80 */       ((PopCounter)this.pops.computeIfAbsent((EntityPlayer)player, v -> new PopCounter()))
/*  81 */         .pop();
/*     */     }
/*     */   }
/*     */   
/*     */   private void onDeath(Entity entity) {
/*  86 */     if (entity instanceof EntityPlayer)
/*  87 */       this.pops.remove(entity); 
/*     */   }
/*     */   
/*     */   private static class PopCounter
/*     */   {
/*  92 */     private final Timer timer = new Timer();
/*     */     private int pops;
/*     */     
/*     */     public int getPops() {
/*  96 */       return this.pops;
/*     */     }
/*     */     
/*     */     public void pop() {
/* 100 */       this.timer.reset();
/* 101 */       this.pops++;
/*     */     }
/*     */     
/*     */     public void reset() {
/* 105 */       this.pops = 0;
/*     */     }
/*     */     
/*     */     public long lastPop() {
/* 109 */       return this.timer.getTimeMs();
/*     */     }
/*     */     
/*     */     private PopCounter() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\CombatManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */