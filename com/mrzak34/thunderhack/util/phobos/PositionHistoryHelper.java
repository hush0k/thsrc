/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.ConnectToServerEvent;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.util.RotationUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.Deque;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentLinkedDeque;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PositionHistoryHelper
/*     */ {
/*     */   private static final int REMOVE_TIME = 1000;
/*  25 */   private final Deque<RotationHistory> packets = new ConcurrentLinkedDeque<>();
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConnect(ConnectToServerEvent e) {
/*  30 */     this.packets.clear();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send e) {
/*  35 */     if (e.getPacket() instanceof CPacketPlayer) {
/*  36 */       onPlayerPacket((CPacketPlayer)e.getPacket());
/*     */     }
/*  38 */     if (e.getPacket() instanceof CPacketPlayer.Position) {
/*  39 */       onPlayerPacket((CPacketPlayer)e.getPacket());
/*     */     }
/*  41 */     if (e.getPacket() instanceof CPacketPlayer.Rotation) {
/*  42 */       onPlayerPacket((CPacketPlayer)e.getPacket());
/*     */     }
/*  44 */     if (e.getPacket() instanceof CPacketPlayer.PositionRotation) {
/*  45 */       onPlayerPacket((CPacketPlayer)e.getPacket());
/*     */     }
/*     */   }
/*     */   
/*     */   private void onPlayerPacket(CPacketPlayer packet) {
/*  50 */     this.packets.removeIf(h -> 
/*  51 */         (h == null || System.currentTimeMillis() - h.time > 1000L));
/*  52 */     this.packets.addFirst(new RotationHistory(packet));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean arePreviousRotationsLegit(Entity entity, int time, boolean skipFirst) {
/*  58 */     if (time == 0) {
/*  59 */       return true;
/*     */     }
/*     */     
/*  62 */     Iterator<RotationHistory> itr = this.packets.iterator();
/*  63 */     while (itr.hasNext()) {
/*  64 */       RotationHistory next = itr.next();
/*  65 */       if (skipFirst) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  71 */       if (next != null) {
/*  72 */         if (System.currentTimeMillis() - next.time > 1000L) {
/*  73 */           itr.remove(); continue;
/*  74 */         }  if (System.currentTimeMillis() - next.time > time)
/*     */           break; 
/*  76 */         if (!isLegit(next, entity)) {
/*  77 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isLegit(RotationHistory history, Entity entity) {
/*  87 */     RayTraceResult result = RayTracer.rayTraceEntities((World)Util.mc.field_71441_e, 
/*  88 */         (Entity)RotationUtil.getRotationPlayer(), 7.0D, history.x, history.y, history.z, history.yaw, history.pitch, history.bb, e -> 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  97 */         (e != null && e.equals(entity)), new Entity[] { entity, entity });
/*     */ 
/*     */     
/* 100 */     return (result != null && entity
/* 101 */       .equals(result.field_72308_g));
/*     */   }
/*     */   
/*     */   public Deque<RotationHistory> getPackets() {
/* 105 */     return this.packets;
/*     */   }
/*     */   
/*     */   public static final class RotationHistory {
/*     */     public final double x;
/*     */     public final double y;
/*     */     public final double z;
/*     */     public final float yaw;
/*     */     public final float pitch;
/*     */     public final long time;
/*     */     public final AxisAlignedBB bb;
/*     */     public final boolean hasLook;
/*     */     public final boolean hasPos;
/*     */     public final boolean hasChanged;
/*     */     
/*     */     public RotationHistory(CPacketPlayer packet) {
/* 121 */       this(packet.func_186997_a(Thunderhack.positionManager.getX()), packet
/* 122 */           .func_186996_b(Thunderhack.positionManager.getY()), packet
/* 123 */           .func_187000_c(Thunderhack.positionManager.getZ()), packet
/* 124 */           .func_186999_a(Thunderhack.rotationManager.getServerYaw()), packet
/* 125 */           .func_186998_b(Thunderhack.rotationManager.getServerPitch()), (packet instanceof CPacketPlayer.Rotation || packet instanceof CPacketPlayer.PositionRotation), (packet instanceof CPacketPlayer.Position || packet instanceof CPacketPlayer.PositionRotation));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RotationHistory(double x, double y, double z, float yaw, float pitch, boolean hasLook, boolean hasPos) {
/* 137 */       this.x = x;
/* 138 */       this.y = y;
/* 139 */       this.z = z;
/* 140 */       this.yaw = yaw;
/* 141 */       this.pitch = pitch;
/* 142 */       this.hasLook = hasLook;
/* 143 */       this.hasPos = hasPos;
/* 144 */       this.time = System.currentTimeMillis();
/* 145 */       float w = Util.mc.field_71439_g.field_70130_N / 2.0F;
/* 146 */       float h = Util.mc.field_71439_g.field_70131_O;
/* 147 */       this.bb = new AxisAlignedBB(x - w, y, z - w, x + w, y + h, z + w);
/* 148 */       this.hasChanged = (hasLook || hasPos);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\PositionHistoryHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */