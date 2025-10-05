/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.ConnectToServerEvent;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Iterator;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerTickManager
/*     */ {
/*  27 */   private final Timer serverTickTimer = new Timer();
/*  28 */   private final ArrayDeque<Integer> spawnObjectTimes = new ArrayDeque<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int averageSpawnObjectTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  48 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public void unload() {
/*  52 */     MinecraftForge.EVENT_BUS.unregister(this);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConnect(ConnectToServerEvent e) {
/*  57 */     resetTickManager();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  62 */     if (Util.mc.field_71439_g == null || Util.mc.field_71441_e == null)
/*  63 */       return;  if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketTimeUpdate && 
/*  64 */       Util.mc.field_71441_e != null && Util.mc.field_71441_e.field_72995_K)
/*     */     {
/*  66 */       resetTickManager();
/*     */     }
/*     */     
/*  69 */     if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketSpawnObject && 
/*  70 */       Util.mc.field_71441_e != null && Util.mc.field_71441_e.field_72995_K)
/*     */     {
/*  72 */       onSpawnObject();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTickTime() {
/*  83 */     if (this.serverTickTimer.getTimeMs() < 50L) return (int)this.serverTickTimer.getTimeMs(); 
/*  84 */     return (int)(this.serverTickTimer.getTimeMs() % getServerTickLengthMS());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTickTimeAdjusted() {
/*  93 */     int time = getTickTime() + Thunderhack.serverManager.getPing() / 2;
/*  94 */     if (time < getServerTickLengthMS()) return time; 
/*  95 */     return time % getServerTickLengthMS();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTickTimeAdjustedForServerPackets() {
/* 104 */     int time = getTickTime() - Thunderhack.serverManager.getPing() / 2;
/* 105 */     if (time < getServerTickLengthMS() && time > 0) return time; 
/* 106 */     if (time < 0) return time + getServerTickLengthMS(); 
/* 107 */     return time % getServerTickLengthMS();
/*     */   }
/*     */   
/*     */   public void resetTickManager() {
/* 111 */     this.serverTickTimer.reset();
/* 112 */     this.serverTickTimer.adjust(Thunderhack.serverManager.getPing() / 2);
/*     */   }
/*     */   
/*     */   public int getServerTickLengthMS() {
/* 116 */     if (Thunderhack.serverManager.getTPS() == 0.0F) return 50; 
/* 117 */     return (int)(50.0F * 20.0F / Thunderhack.serverManager.getTPS());
/*     */   }
/*     */   
/*     */   public void onSpawnObject() {
/* 121 */     int time = getTickTimeAdjustedForServerPackets();
/* 122 */     if (this.spawnObjectTimes.size() > 10) this.spawnObjectTimes.poll(); 
/* 123 */     this.spawnObjectTimes.add(Integer.valueOf(time));
/* 124 */     int totalTime = 0;
/* 125 */     for (Iterator<Integer> iterator = this.spawnObjectTimes.iterator(); iterator.hasNext(); ) { int spawnTime = ((Integer)iterator.next()).intValue();
/* 126 */       totalTime += spawnTime; }
/*     */     
/* 128 */     this.averageSpawnObjectTime = totalTime / this.spawnObjectTimes.size();
/*     */   }
/*     */   
/*     */   public int normalize(int toNormalize) {
/* 132 */     while (toNormalize < 0) {
/* 133 */       toNormalize += getServerTickLengthMS();
/*     */     }
/* 135 */     while (toNormalize > getServerTickLengthMS()) {
/* 136 */       toNormalize -= getServerTickLengthMS();
/*     */     }
/* 138 */     return toNormalize;
/*     */   }
/*     */   
/*     */   public boolean valid(int currentTime, int minTime, int maxTime) {
/* 142 */     if (minTime > maxTime) {
/* 143 */       return (currentTime >= minTime || currentTime <= maxTime);
/*     */     }
/* 145 */     return (currentTime >= minTime && currentTime <= maxTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpawnTime() {
/* 150 */     return this.averageSpawnObjectTime;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ServerTickManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */