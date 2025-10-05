/*     */ package com.mrzak34.thunderhack.util.ffp;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import net.minecraft.network.EnumPacketDirection;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
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
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class NetworkHandler
/*     */ {
/*     */   private boolean isConnected = false;
/*  34 */   private NetworkManager networkManager = null;
/*  35 */   private final List<PacketListener>[] outbound_listeners = (List<PacketListener>[])new List[33];
/*  36 */   private final ReadWriteLock[] outbound_lock = new ReadWriteLock[33]; public NetworkHandler() {
/*     */     int i;
/*  38 */     for (i = 0; i < 33; i++) {
/*  39 */       this.outbound_lock[i] = new ReentrantReadWriteLock();
/*     */     }
/*     */     
/*  42 */     this.inbound_listeners = (List<PacketListener>[])new List[80];
/*  43 */     this.inbound_lock = new ReadWriteLock[80];
/*     */     
/*  45 */     for (i = 0; i < 80; i++)
/*  46 */       this.inbound_lock[i] = new ReentrantReadWriteLock(); 
/*     */   }
/*     */   private final ReadWriteLock[] inbound_lock;
/*     */   private final List<PacketListener>[] inbound_listeners;
/*     */   
/*     */   public Packet<?> packetReceived(EnumPacketDirection direction, int id, Packet<?> packet, ByteBuf buf) {
/*     */     List<PacketListener> listeners;
/*     */     ReadWriteLock lock;
/*  54 */     if (direction == EnumPacketDirection.CLIENTBOUND) {
/*  55 */       listeners = this.inbound_listeners[id];
/*  56 */       lock = this.inbound_lock[id];
/*     */     } else {
/*  58 */       listeners = this.outbound_listeners[id];
/*  59 */       lock = this.outbound_lock[id];
/*     */     } 
/*     */     
/*  62 */     if (listeners != null) {
/*  63 */       int buff_start = 0;
/*  64 */       if (buf != null) buff_start = buf.readerIndex();
/*     */       
/*  66 */       lock.readLock().lock();
/*  67 */       int size = listeners.size();
/*  68 */       lock.readLock().unlock();
/*     */       
/*  70 */       for (int i = 0; i < size; i++) {
/*  71 */         lock.readLock().lock();
/*  72 */         PacketListener l = listeners.get(i - size - listeners.size());
/*  73 */         lock.readLock().unlock();
/*     */         
/*  75 */         if (buf != null) buf.readerIndex(buff_start); 
/*  76 */         if ((packet = l.packetReceived(direction, id, packet, buf)) == null) return null;
/*     */       
/*     */       } 
/*     */     } 
/*  80 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerListener(EnumPacketDirection direction, PacketListener listener, int... ids) {
/*     */     List<PacketListener>[] listeners;
/*     */     ReadWriteLock[] locks;
/*  87 */     if (direction == EnumPacketDirection.CLIENTBOUND) {
/*  88 */       listeners = this.inbound_listeners;
/*  89 */       locks = this.inbound_lock;
/*     */     } else {
/*  91 */       listeners = this.outbound_listeners;
/*  92 */       locks = this.outbound_lock;
/*     */     } 
/*     */     
/*  95 */     for (int id : ids) {
/*     */       
/*  97 */       try { locks[id].writeLock().lock();
/*     */         
/*  99 */         if (listeners[id] == null) listeners[id] = new ArrayList<>(); 
/* 100 */         if (!listeners[id].contains(listener)) {
/* 101 */           listeners[id].add(listener);
/*     */         }
/*     */         
/* 104 */         locks[id].writeLock().unlock(); } finally { locks[id].writeLock().unlock(); }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterListener(EnumPacketDirection direction, PacketListener listener, int... ids) {
/*     */     List<PacketListener>[] listeners;
/*     */     ReadWriteLock[] locks;
/* 114 */     if (direction == EnumPacketDirection.CLIENTBOUND) {
/* 115 */       listeners = this.inbound_listeners;
/* 116 */       locks = this.inbound_lock;
/*     */     } else {
/* 118 */       listeners = this.outbound_listeners;
/* 119 */       locks = this.outbound_lock;
/*     */     } 
/*     */     
/* 122 */     for (int id : ids) {
/*     */       
/* 124 */       try { locks[id].writeLock().lock();
/* 125 */         if (listeners[id] != null) {
/* 126 */           listeners[id].remove(listener);
/* 127 */           if (listeners[id].size() == 0) listeners[id] = null;
/*     */         
/*     */         } 
/* 130 */         locks[id].writeLock().unlock(); } finally { locks[id].writeLock().unlock(); }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendPacket(Packet<?> packet) {
/* 136 */     if (this.networkManager != null) {
/* 137 */       this.networkManager.func_179290_a(packet);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
/* 144 */     if (!this.isConnected) {
/*     */       
/* 146 */       ChannelPipeline pipeline = event.getManager().channel().pipeline();
/*     */ 
/*     */       
/*     */       try {
/* 150 */         ChannelHandler old = pipeline.get("decoder");
/* 151 */         if (old != null && old instanceof net.minecraft.network.NettyPacketDecoder) {
/* 152 */           InboundInterceptor spoof = new InboundInterceptor(this, EnumPacketDirection.CLIENTBOUND);
/* 153 */           pipeline.replace("decoder", "decoder", (ChannelHandler)spoof);
/*     */         } 
/*     */ 
/*     */         
/* 157 */         old = pipeline.get("encoder");
/* 158 */         if (old != null && old instanceof net.minecraft.network.NettyPacketEncoder) {
/* 159 */           OutboundInterceptor spoof = new OutboundInterceptor(this, EnumPacketDirection.SERVERBOUND);
/* 160 */           pipeline.replace("encoder", "encoder", (ChannelHandler)spoof);
/*     */         } 
/*     */ 
/*     */         
/* 164 */         this.networkManager = event.getManager();
/* 165 */         this.isConnected = true;
/* 166 */       } catch (NoSuchElementException noSuchElementException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
/* 173 */     this.isConnected = false;
/* 174 */     this.networkManager = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\ffp\NetworkHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */