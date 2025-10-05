/*     */ package com.mrzak34.thunderhack.manager;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityPlayerSP;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class RotationManager
/*     */ {
/*     */   private boolean blocking;
/*     */   private volatile float last_yaw;
/*     */   private volatile float last_pitch;
/*     */   public float visualYaw;
/*     */   public float visualPitch;
/*     */   public float prevVisualYaw;
/*     */   public float prevVisualPitch;
/*     */   private float yaw;
/*     */   private float pitch;
/*     */   
/*     */   public void init() {
/*  29 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public void unload() {
/*  33 */     MinecraftForge.EVENT_BUS.unregister(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onSync(EventSync event) {
/*  39 */     if (Module.fullNullCheck()) {
/*     */       return;
/*     */     }
/*     */     
/*  43 */     this.yaw = Util.mc.field_71439_g.field_70177_z;
/*  44 */     this.pitch = Util.mc.field_71439_g.field_70125_A;
/*     */ 
/*     */     
/*  47 */     this.prevVisualPitch = this.visualPitch;
/*  48 */     this.prevVisualYaw = this.visualYaw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void postSync(EventPostSync event) {
/*  57 */     if (Module.fullNullCheck())
/*     */       return; 
/*  59 */     this.visualPitch = Util.mc.field_71439_g.field_70125_A;
/*  60 */     this.visualYaw = Util.mc.field_71439_g.field_70177_z;
/*  61 */     Util.mc.field_71439_g.field_70177_z = this.yaw;
/*  62 */     Util.mc.field_71439_g.field_70759_as = this.yaw;
/*  63 */     Util.mc.field_71439_g.field_70125_A = this.pitch;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.SendPost event) {
/*  68 */     if (event.getPacket() instanceof CPacketPlayer) {
/*  69 */       readCPacket((CPacketPlayer)event.getPacket());
/*     */     }
/*  71 */     if (event.getPacket() instanceof CPacketPlayer.Position) {
/*  72 */       readCPacket((CPacketPlayer)event.getPacket());
/*     */     }
/*  74 */     if (event.getPacket() instanceof CPacketPlayer.Rotation) {
/*  75 */       readCPacket((CPacketPlayer)event.getPacket());
/*     */     }
/*  77 */     if (event.getPacket() instanceof CPacketPlayer.PositionRotation) {
/*  78 */       readCPacket((CPacketPlayer)event.getPacket());
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  84 */     if (Module.fullNullCheck())
/*     */       return; 
/*  86 */     if (e.getPacket() instanceof SPacketPlayerPosLook) {
/*  87 */       SPacketPlayerPosLook packet = (SPacketPlayerPosLook)e.getPacket();
/*  88 */       float yaw = packet.func_148931_f();
/*  89 */       float pitch = packet.func_148930_g();
/*     */       
/*  91 */       if (packet.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.X_ROT)) {
/*  92 */         yaw += Util.mc.field_71439_g.field_70177_z;
/*     */       }
/*     */       
/*  95 */       if (packet.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.Y_ROT)) {
/*  96 */         pitch += Util.mc.field_71439_g.field_70125_A;
/*     */       }
/*     */       
/*  99 */       if (Util.mc.field_71439_g != null) {
/* 100 */         setServerRotations(yaw, pitch);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getServerYaw() {
/* 106 */     return this.last_yaw;
/*     */   }
/*     */   
/*     */   public float getServerPitch() {
/* 110 */     return this.last_pitch;
/*     */   }
/*     */   
/*     */   public boolean isBlocking() {
/* 114 */     return this.blocking;
/*     */   }
/*     */   
/*     */   public void setBlocking(boolean blocking) {
/* 118 */     this.blocking = blocking;
/*     */   }
/*     */   
/*     */   public void setServerRotations(float yaw, float pitch) {
/* 122 */     this.last_yaw = yaw;
/* 123 */     this.last_pitch = pitch;
/*     */   }
/*     */   
/*     */   public void readCPacket(CPacketPlayer packetIn) {
/* 127 */     ((IEntityPlayerSP)Util.mc.field_71439_g).setLastReportedYaw(packetIn.func_186999_a(((IEntityPlayerSP)Util.mc.field_71439_g).getLastReportedYaw()));
/* 128 */     ((IEntityPlayerSP)Util.mc.field_71439_g).setLastReportedPitch(packetIn.func_186998_b(((IEntityPlayerSP)Util.mc.field_71439_g).getLastReportedPitch()));
/* 129 */     setServerRotations(packetIn.func_186999_a(this.last_yaw), packetIn.func_186998_b(this.last_pitch));
/* 130 */     Thunderhack.positionManager.setOnGround(packetIn.func_149465_i());
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\manager\RotationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */