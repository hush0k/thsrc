/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ 
/*     */ public class TickShift
/*     */   extends Module
/*     */ {
/*  18 */   private final AtomicLong lagTimer = new AtomicLong();
/*  19 */   public Setting<Float> timer = register(new Setting("Timer", Float.valueOf(2.0F), Float.valueOf(0.1F), Float.valueOf(100.0F)));
/*  20 */   public Setting<Integer> packets = register(new Setting("Packets", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(1000)));
/*  21 */   public Setting<Integer> lagTime = register(new Setting("LagTime", Integer.valueOf(1000), Integer.valueOf(0), Integer.valueOf(10000)));
/*  22 */   public Setting<Boolean> sneaking = register(new Setting("Sneaking", Boolean.valueOf(false)));
/*  23 */   public Setting<Boolean> cancelGround = register(new Setting("CancelGround", Boolean.valueOf(false)));
/*  24 */   public Setting<Boolean> cancelRotations = register(new Setting("CancelRotation", Boolean.valueOf(false)));
/*     */   
/*     */   public TickShift() {
/*  27 */     super("TickShift", "тикшифт эксплоит", Module.Category.MISC);
/*     */   }
/*     */   private int ticks;
/*     */   public static boolean noMovementKeys() {
/*  31 */     return (!mc.field_71439_g.field_71158_b.field_187255_c && !mc.field_71439_g.field_71158_b.field_187256_d && !mc.field_71439_g.field_71158_b.field_187258_f && !mc.field_71439_g.field_71158_b.field_187257_e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean passed(int ms) {
/*  38 */     return (System.currentTimeMillis() - this.lagTimer.get() >= ms);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent e) {
/*  43 */     if (mc.field_71439_g == null || mc.field_71441_e == null || !passed(((Integer)this.lagTime.getValue()).intValue())) {
/*  44 */       rozetked();
/*  45 */     } else if (this.ticks <= 0 || noMovementKeys() || (!((Boolean)this.sneaking.getValue()).booleanValue() && mc.field_71439_g.func_70093_af())) {
/*  46 */       Thunderhack.TICK_TIMER = 1.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEventMove(EventMove e) {
/*  58 */     Thunderhack.TICK_TIMER = 1.0F;
/*  59 */     int maxPackets = ((Integer)this.packets.getValue()).intValue();
/*  60 */     this.ticks = (this.ticks >= maxPackets) ? maxPackets : (this.ticks + 1);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  65 */     if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
/*  66 */       this.lagTimer.set(System.currentTimeMillis());
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send e) {
/*  72 */     if (e.getPacket() instanceof CPacketPlayer.PositionRotation) {
/*  73 */       hth(e, true);
/*     */     }
/*  75 */     if (e.getPacket() instanceof CPacketPlayer.Rotation) {
/*  76 */       if (((Boolean)this.cancelRotations.getValue()).booleanValue() && (((Boolean)this.cancelGround
/*  77 */         .getValue()).booleanValue() || ((CPacketPlayer.Rotation)e
/*  78 */         .getPacket()).func_149465_i() == mc.field_71439_g.field_70122_E)) {
/*  79 */         e.setCanceled(true);
/*     */       } else {
/*  81 */         hth(e, false);
/*     */       } 
/*     */     }
/*  84 */     if (e.getPacket() instanceof CPacketPlayer.Position) {
/*  85 */       hth(e, true);
/*     */     }
/*  87 */     if (e.getPacket() instanceof CPacketPlayer) {
/*  88 */       if (((Boolean)this.cancelGround.getValue()).booleanValue()) {
/*  89 */         e.setCanceled(true);
/*     */       } else {
/*  91 */         hth(e, false);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/*  98 */     return this.ticks + "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 103 */     rozetked();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 108 */     rozetked();
/*     */   }
/*     */ 
/*     */   
/*     */   private void hth(PacketEvent.Send event, boolean moving) {
/* 113 */     if (event.isCanceled()) {
/*     */       return;
/*     */     }
/*     */     
/* 117 */     if (moving && !noMovementKeys() && (((Boolean)this.sneaking.getValue()).booleanValue() || !mc.field_71439_g.func_70093_af())) {
/* 118 */       Thunderhack.TICK_TIMER = ((Float)this.timer.getValue()).floatValue();
/*     */     }
/*     */ 
/*     */     
/* 122 */     this.ticks = (this.ticks <= 0) ? 0 : (this.ticks - 1);
/*     */   }
/*     */   
/*     */   public void rozetked() {
/* 126 */     Thunderhack.TICK_TIMER = 1.0F;
/* 127 */     this.ticks = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\TickShift.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */