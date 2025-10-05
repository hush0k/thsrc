/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketPlayer;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.modules.combat.Burrow;
/*     */ import com.mrzak34.thunderhack.modules.movement.PacketFly;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ 
/*     */ 
/*     */ public class RotationCanceller
/*     */ {
/*  18 */   private final Timer timer = new Timer();
/*     */   
/*     */   private final Setting<Integer> maxCancel;
/*     */   private final AutoCrystal module;
/*     */   private volatile CPacketPlayer last;
/*     */   
/*     */   public RotationCanceller(AutoCrystal module, Setting<Integer> maxCancel) {
/*  25 */     this.module = module;
/*  26 */     this.maxCancel = maxCancel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CPacketPlayer positionRotation(double x, double y, double z, float yaw, float pitch, boolean onGround) {
/*  35 */     return (CPacketPlayer)new CPacketPlayer.PositionRotation(x, y, z, yaw, pitch, onGround);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGameLoop() {
/*  43 */     if (this.last != null && this.timer.passedMs(((Integer)this.maxCancel.getValue()).intValue())) {
/*  44 */       sendLast();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void onPacketNigger(PacketEvent.Send event) {
/*  49 */     if (event.getPacket() instanceof CPacketPlayer) {
/*  50 */       if (event.isCanceled() || ((PacketFly)Thunderhack.moduleManager.getModuleByClass(PacketFly.class)).isEnabled()) {
/*     */         return;
/*     */       }
/*     */       
/*  54 */       reset();
/*     */       
/*  56 */       if (Thunderhack.rotationManager.isBlocking()) {
/*     */         return;
/*     */       }
/*     */       
/*  60 */       event.setCanceled(true);
/*  61 */       this.last = (CPacketPlayer)event.getPacket();
/*  62 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean setRotations(RotationFunction function) {
/*  74 */     if (this.last == null) {
/*  75 */       return false;
/*     */     }
/*     */     
/*  78 */     double x = this.last.func_186997_a(Thunderhack.positionManager.getX());
/*  79 */     double y = this.last.func_186997_a(Thunderhack.positionManager.getY());
/*  80 */     double z = this.last.func_186997_a(Thunderhack.positionManager.getZ());
/*  81 */     float yaw = Thunderhack.rotationManager.getServerYaw();
/*  82 */     float pitch = Thunderhack.rotationManager.getServerPitch();
/*  83 */     boolean onGround = this.last.func_149465_i();
/*     */     
/*  85 */     ICPacketPlayer accessor = (ICPacketPlayer)this.last;
/*  86 */     float[] r = function.apply(x, y, z, yaw, pitch);
/*  87 */     if ((r[0] - yaw) == 0.0D || (r[1] - pitch) == 0.0D) {
/*  88 */       if (!accessor.isRotating() && 
/*  89 */         !accessor.isMoving() && onGround == Thunderhack.positionManager
/*  90 */         .isOnGround()) {
/*  91 */         this.last = null;
/*  92 */         return true;
/*     */       } 
/*     */       
/*  95 */       sendLast();
/*  96 */       return true;
/*     */     } 
/*     */     
/*  99 */     if (accessor.isRotating()) {
/* 100 */       accessor.setYaw(r[0]);
/* 101 */       accessor.setPitch(r[1]);
/* 102 */       sendLast();
/* 103 */     } else if (accessor.isMoving()) {
/* 104 */       this.last = positionRotation(x, y, z, r[0], r[1], onGround);
/* 105 */       sendLast();
/*     */     } else {
/* 107 */       this.last = Burrow.rotation(r[0], r[1], onGround);
/* 108 */       sendLast();
/*     */     } 
/*     */     
/* 111 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 118 */     if (this.last != null && Util.mc.field_71439_g != null) {
/* 119 */       sendLast();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void drop() {
/* 127 */     this.last = null;
/*     */   }
/*     */   
/*     */   private synchronized void sendLast() {
/* 131 */     CPacketPlayer packet = this.last;
/* 132 */     if (packet != null && Util.mc.field_71439_g != null) {
/* 133 */       Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)packet);
/* 134 */       this.module.runPost();
/*     */     } 
/*     */     
/* 137 */     this.last = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPacketNigger9(CPacketPlayer.Rotation rotation) {
/* 143 */     if (((PacketFly)Thunderhack.moduleManager.getModuleByClass(PacketFly.class)).isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 147 */     reset();
/* 148 */     if (Thunderhack.rotationManager.isBlocking()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 153 */     this.last = (CPacketPlayer)rotation;
/* 154 */     this.timer.reset();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\RotationCanceller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */