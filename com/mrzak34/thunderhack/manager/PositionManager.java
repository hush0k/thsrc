/*     */ package com.mrzak34.thunderhack.manager;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityTracker;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ public class PositionManager
/*     */ {
/*     */   private boolean blocking;
/*     */   private volatile int teleportID;
/*     */   private volatile double last_x;
/*     */   private volatile double last_y;
/*     */   private volatile double last_z;
/*     */   private volatile boolean onGround;
/*     */   private volatile boolean sprinting;
/*     */   
/*     */   public void init() {
/*  36 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public void unload() {
/*  40 */     MinecraftForge.EVENT_BUS.unregister(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.SendPost e) {
/*  46 */     if (e.getPacket() instanceof CPacketPlayer.Position) {
/*  47 */       readCPacket((CPacketPlayer)e.getPacket());
/*     */     }
/*  49 */     if (e.getPacket() instanceof CPacketPlayer.PositionRotation) {
/*  50 */       readCPacket((CPacketPlayer)e.getPacket());
/*     */     }
/*  52 */     if (e.getPacket() instanceof CPacketEntityAction) {
/*  53 */       CPacketEntityAction action = (CPacketEntityAction)e.getPacket();
/*  54 */       if (action.func_180764_b() == CPacketEntityAction.Action.START_SPRINTING) {
/*  55 */         this.sprinting = true;
/*     */       }
/*  57 */       if (action.func_180764_b() == CPacketEntityAction.Action.STOP_SPRINTING) {
/*  58 */         this.sprinting = false;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  65 */     if (EventManager.fullNullCheck())
/*     */       return; 
/*  67 */     if (e.getPacket() instanceof SPacketPlayerPosLook) {
/*     */       
/*  69 */       EntityPlayerSP player = Util.mc.field_71439_g;
/*  70 */       if (player == null) {
/*  71 */         if (!Util.mc.func_152345_ab()) {
/*  72 */           Util.mc.func_152344_a(() -> onPacketReceive(e));
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  78 */       SPacketPlayerPosLook packet = (SPacketPlayerPosLook)e.getPacket();
/*  79 */       double x = packet.func_148932_c();
/*  80 */       double y = packet.func_148928_d();
/*  81 */       double z = packet.func_148933_e();
/*     */       
/*  83 */       if (packet.func_179834_f()
/*  84 */         .contains(SPacketPlayerPosLook.EnumFlags.X)) {
/*  85 */         x += player.field_70165_t;
/*     */       }
/*     */       
/*  88 */       if (packet.func_179834_f()
/*  89 */         .contains(SPacketPlayerPosLook.EnumFlags.Y)) {
/*  90 */         y += player.field_70163_u;
/*     */       }
/*     */       
/*  93 */       if (packet.func_179834_f()
/*  94 */         .contains(SPacketPlayerPosLook.EnumFlags.Z)) {
/*  95 */         z += player.field_70161_v;
/*     */       }
/*     */       
/*  98 */       this.last_x = MathHelper.func_151237_a(x, -3.0E7D, 3.0E7D);
/*  99 */       this.last_y = y;
/* 100 */       this.last_z = MathHelper.func_151237_a(z, -3.0E7D, 3.0E7D);
/*     */       
/* 102 */       player.field_70118_ct = EntityTracker.func_187253_a(this.last_x);
/* 103 */       player.field_70117_cu = EntityTracker.func_187253_a(this.last_y);
/* 104 */       player.field_70116_cv = EntityTracker.func_187253_a(this.last_z);
/*     */ 
/*     */       
/* 107 */       this.onGround = false;
/* 108 */       this.teleportID = packet.func_186965_f();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTeleportID() {
/* 117 */     return this.teleportID;
/*     */   }
/*     */   
/*     */   public double getX() {
/* 121 */     return this.last_x;
/*     */   }
/*     */   
/*     */   public double getY() {
/* 125 */     return this.last_y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 129 */     return this.last_z;
/*     */   }
/*     */   
/*     */   public boolean isOnGround() {
/* 133 */     return this.onGround;
/*     */   }
/*     */   
/*     */   public void setOnGround(boolean onGround) {
/* 137 */     this.onGround = onGround;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getBB() {
/* 141 */     double x = this.last_x;
/* 142 */     double y = this.last_y;
/* 143 */     double z = this.last_z;
/* 144 */     float w = Util.mc.field_71439_g.field_70130_N / 2.0F;
/* 145 */     float h = Util.mc.field_71439_g.field_70131_O;
/* 146 */     return new AxisAlignedBB(x - w, y, z - w, x + w, y + h, z + w);
/*     */   }
/*     */   
/*     */   public Vec3d getVec() {
/* 150 */     return new Vec3d(this.last_x, this.last_y, this.last_z);
/*     */   }
/*     */   
/*     */   public void readCPacket(CPacketPlayer packetIn) {
/* 154 */     this.last_x = packetIn.func_186997_a(Util.mc.field_71439_g.field_70165_t);
/* 155 */     this.last_y = packetIn.func_186996_b(Util.mc.field_71439_g.field_70163_u);
/* 156 */     this.last_z = packetIn.func_187000_c(Util.mc.field_71439_g.field_70161_v);
/*     */     EntityPlayerSP entityPlayerSP;
/* 158 */     if ((entityPlayerSP = Util.mc.field_71439_g) != null) {
/* 159 */       ((EntityPlayer)entityPlayerSP).field_70118_ct = EntityTracker.func_187253_a(this.last_x);
/* 160 */       ((EntityPlayer)entityPlayerSP).field_70117_cu = EntityTracker.func_187253_a(this.last_y);
/* 161 */       ((EntityPlayer)entityPlayerSP).field_70116_cv = EntityTracker.func_187253_a(this.last_z);
/*     */     } 
/*     */     
/* 164 */     setOnGround(packetIn.func_149465_i());
/*     */   }
/*     */   
/*     */   public double getDistanceSq(Entity entity) {
/* 168 */     return getDistanceSq(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*     */   }
/*     */   
/*     */   public double getDistanceSq(double x, double y, double z) {
/* 172 */     double xDiff = this.last_x - x;
/* 173 */     double yDiff = this.last_y - y;
/* 174 */     double zDiff = this.last_z - z;
/* 175 */     return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canEntityBeSeen(Entity entity) {
/* 180 */     return (Util.mc.field_71441_e.func_147447_a(new Vec3d(this.last_x, this.last_y + Util.mc.field_71439_g
/* 181 */           .func_70047_e(), this.last_z), new Vec3d(entity.field_70165_t, entity.field_70163_u + entity
/* 182 */           .func_70047_e(), entity.field_70161_v), false, true, false) == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(double x, double y, double z) {
/* 189 */     this.last_x = x;
/* 190 */     this.last_y = y;
/* 191 */     this.last_z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlocking() {
/* 202 */     return this.blocking;
/*     */   }
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
/*     */   public void setBlocking(boolean blocking) {
/* 217 */     this.blocking = blocking;
/*     */   }
/*     */   
/*     */   public boolean isSprintingSS() {
/* 221 */     return this.sprinting;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\manager\PositionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */