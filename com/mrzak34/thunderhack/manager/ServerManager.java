/*    */ package com.mrzak34.thunderhack.manager;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerManager
/*    */ {
/*    */   private float tps;
/*    */   private long time;
/* 23 */   private final ArrayDeque<Float> tpsResult = new ArrayDeque<>(20);
/* 24 */   private final Timer timeDelay = new Timer();
/*    */ 
/*    */   
/*    */   public Timer getDelayTimer() {
/* 28 */     return this.timeDelay;
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 32 */     return this.time;
/*    */   }
/*    */   
/*    */   public void setTime(long time) {
/* 36 */     this.time = time;
/*    */   }
/*    */   
/*    */   public float getTPS() {
/* 40 */     return MathUtil.round2(this.tps);
/*    */   }
/*    */   
/*    */   public void setTPS(float tps) {
/* 44 */     this.tps = tps;
/*    */   }
/*    */   
/*    */   public ArrayDeque<Float> getTPSResults() {
/* 48 */     return this.tpsResult;
/*    */   }
/*    */   
/*    */   public int getPing() {
/* 52 */     if (Util.mc.field_71441_e == null || Util.mc.field_71439_g == null) {
/* 53 */       return 0;
/*    */     }
/*    */     try {
/* 56 */       return ((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(Util.mc.func_147114_u())).func_175102_a(Util.mc.func_147114_u().func_175105_e().getId()).func_178853_c();
/* 57 */     } catch (Exception e) {
/* 58 */       return 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 64 */     if (!(event.getPacket() instanceof net.minecraft.network.play.server.SPacketChat)) {
/* 65 */       getDelayTimer().reset();
/*    */     }
/* 67 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketTimeUpdate) {
/* 68 */       if (getTime() != 0L) {
/* 69 */         if (getTPSResults().size() > 20) {
/* 70 */           getTPSResults().poll();
/*    */         }
/* 72 */         getTPSResults().add(Float.valueOf(20.0F * 1000.0F / (float)(System.currentTimeMillis() - getTime())));
/* 73 */         float f = 0.0F;
/* 74 */         for (Float value : getTPSResults()) {
/* 75 */           f += Math.max(0.0F, Math.min(20.0F, value.floatValue()));
/*    */         }
/* 77 */         setTPS(f / getTPSResults().size());
/*    */       } 
/* 79 */       setTime(System.currentTimeMillis());
/*    */     } 
/*    */   }
/*    */   
/*    */   public void init() {
/* 84 */     MinecraftForge.EVENT_BUS.register(this);
/*    */   }
/*    */   
/*    */   public void unload() {
/* 88 */     MinecraftForge.EVENT_BUS.register(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\manager\ServerManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */