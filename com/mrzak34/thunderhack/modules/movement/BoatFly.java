/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.EventPlayerTravel;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import io.netty.util.internal.ConcurrentSet;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketClickWindow;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.network.play.client.CPacketVehicleMove;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class BoatFly extends Module {
/*  25 */   private final ConcurrentSet Field2263 = new ConcurrentSet();
/*  26 */   public Setting<Boolean> strict = register(new Setting("Strict", Boolean.valueOf(false)));
/*  27 */   public Setting<Boolean> limit = register(new Setting("Limit", Boolean.valueOf(true)));
/*  28 */   public Setting<Boolean> phase = register(new Setting("Phase", Boolean.valueOf(true)));
/*  29 */   public Setting<Boolean> gravity = register(new Setting("Gravity", Boolean.valueOf(true)));
/*  30 */   public Setting<Boolean> ongroundpacket = register(new Setting("OnGroundPacket", Boolean.valueOf(false)));
/*  31 */   public Setting<Boolean> spoofpackets = register(new Setting("SpoofPackets", Boolean.valueOf(false)));
/*  32 */   public Setting<Boolean> cancelrotations = register(new Setting("CancelRotations", Boolean.valueOf(true)));
/*  33 */   public Setting<Boolean> cancel = register(new Setting("Cancel", Boolean.valueOf(true)));
/*  34 */   public Setting<Boolean> remount = register(new Setting("Remount", Boolean.valueOf(true)));
/*  35 */   public Setting<Boolean> stop = register(new Setting("Stop", Boolean.valueOf(false)));
/*  36 */   public Setting<Boolean> ylimit = register(new Setting("yLimit", Boolean.valueOf(false)));
/*  37 */   public Setting<Boolean> debug = register(new Setting("Debug", Boolean.valueOf(true)));
/*  38 */   public Setting<Boolean> automount = register(new Setting("AutoMount", Boolean.valueOf(true)));
/*  39 */   public Setting<Boolean> stopunloaded = register(new Setting("StopUnloaded", Boolean.valueOf(true)));
/*  40 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.Packet));
/*  41 */   private final Setting<Float> speed = register(new Setting("Speed", Float.valueOf(2.0F), Float.valueOf(0.0F), Float.valueOf(45.0F)));
/*  42 */   private final Setting<Float> yspeed = register(new Setting("YSpeed", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/*  43 */   private final Setting<Float> glidespeed = register(new Setting("GlideSpeed", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/*  44 */   private final Setting<Float> timer = register(new Setting("Timer", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(5.0F)));
/*  45 */   private final Setting<Float> height = register(new Setting("Height", Float.valueOf(127.0F), Float.valueOf(0.0F), Float.valueOf(256.0F)));
/*  46 */   private final Setting<Float> offset = register(new Setting("Offset", Float.valueOf(0.1F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/*  47 */   private final Setting<Integer> enableticks = register(new Setting("EnableTicks", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(100)));
/*  48 */   private final Setting<Integer> waitticks = register(new Setting("WaitTicks", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(100)));
/*  49 */   private int Field2264 = 0;
/*  50 */   private int Field2265 = 0; private boolean Field2266 = false;
/*     */   private boolean Field2267 = false;
/*     */   private boolean Field2268 = false;
/*     */   
/*     */   public BoatFly() {
/*  55 */     super("BoatFly", "BoatFly", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   public static double[] Method1330(double d) {
/*  59 */     float f = Util.mc.field_71439_g.field_71158_b.field_192832_b;
/*  60 */     float f2 = Util.mc.field_71439_g.field_71158_b.field_78902_a;
/*  61 */     float f3 = Util.mc.field_71439_g.field_70126_B + (Util.mc.field_71439_g.field_70177_z - Util.mc.field_71439_g.field_70126_B) * Util.mc.func_184121_ak();
/*  62 */     if (f != 0.0F) {
/*  63 */       if (f2 > 0.0F) {
/*  64 */         f3 += ((f > 0.0F) ? -45 : 45);
/*  65 */       } else if (f2 < 0.0F) {
/*  66 */         f3 += ((f > 0.0F) ? 45 : -45);
/*     */       } 
/*  68 */       f2 = 0.0F;
/*  69 */       if (f > 0.0F) {
/*  70 */         f = 1.0F;
/*  71 */       } else if (f < 0.0F) {
/*  72 */         f = -1.0F;
/*     */       } 
/*     */     } 
/*  75 */     double d2 = Math.sin(Math.toRadians((f3 + 90.0F)));
/*  76 */     double d3 = Math.cos(Math.toRadians((f3 + 90.0F)));
/*  77 */     double d4 = f * d * d3 + f2 * d * d2;
/*  78 */     double d5 = f * d * d2 - f2 * d * d3;
/*  79 */     return new double[] { d4, d5 };
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  84 */     if (Util.mc.field_71439_g == null || Util.mc.field_71439_g.field_70170_p == null) {
/*  85 */       toggle();
/*     */       return;
/*     */     } 
/*  88 */     if (((Boolean)this.automount.getValue()).booleanValue()) {
/*  89 */       Method2868();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  95 */     Thunderhack.TICK_TIMER = 1.0F;
/*  96 */     this.Field2263.clear();
/*  97 */     this.Field2266 = false;
/*  98 */     if (Util.mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/* 101 */     if (((Boolean)this.phase.getValue()).booleanValue() && this.mode.getValue() == Mode.Motion) {
/* 102 */       if (Util.mc.field_71439_g.func_184187_bx() != null) {
/* 103 */         (Util.mc.field_71439_g.func_184187_bx()).field_70145_X = false;
/*     */       }
/* 105 */       Util.mc.field_71439_g.field_70145_X = false;
/*     */     } 
/* 107 */     if (Util.mc.field_71439_g.func_184187_bx() != null) {
/* 108 */       Util.mc.field_71439_g.func_184187_bx().func_189654_d(false);
/*     */     }
/* 110 */     Util.mc.field_71439_g.func_189654_d(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private float Method2874() {
/* 115 */     this.Field2268 = !this.Field2268;
/* 116 */     return this.Field2268 ? ((Float)this.offset.getValue()).floatValue() : -((Float)this.offset.getValue()).floatValue();
/*     */   }
/*     */   
/*     */   private void Method2875(CPacketVehicleMove cPacketVehicleMove) {
/* 120 */     this.Field2263.add(cPacketVehicleMove);
/* 121 */     Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)cPacketVehicleMove);
/*     */   }
/*     */   
/*     */   private void Method2876(Entity entity) {
/* 125 */     double d = entity.field_70163_u;
/* 126 */     BlockPos blockPos = new BlockPos(entity.field_70165_t, (int)entity.field_70163_u, entity.field_70161_v);
/* 127 */     for (int i = 0; i < 255; i++) {
/* 128 */       if (!Util.mc.field_71441_e.func_180495_p(blockPos).func_185904_a().func_76222_j() || Util.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150355_j) {
/* 129 */         entity.field_70163_u = (blockPos.func_177956_o() + 1);
/* 130 */         if (((Boolean)this.debug.getValue()).booleanValue()) {
/* 131 */           Command.sendMessage("GroundY" + entity.field_70163_u);
/*     */         }
/* 133 */         Method2875(new CPacketVehicleMove(entity));
/* 134 */         entity.field_70163_u = d;
/*     */         break;
/*     */       } 
/* 137 */       blockPos = blockPos.func_177982_a(0, -1, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void Method2868() {
/* 142 */     for (Entity entity : Util.mc.field_71441_e.field_72996_f) {
/* 143 */       if (!(entity instanceof EntityBoat) || Util.mc.field_71439_g.func_70032_d(entity) >= 5.0F)
/* 144 */         continue;  Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerTravel(EventPlayerTravel eventPlayerTravel) {
/* 151 */     if (Util.mc.field_71439_g == null || Util.mc.field_71441_e == null) {
/*     */       return;
/*     */     }
/* 154 */     if (Util.mc.field_71439_g.func_184187_bx() == null) {
/* 155 */       if (((Boolean)this.automount.getValue()).booleanValue()) {
/* 156 */         Method2868();
/*     */       }
/*     */       return;
/*     */     } 
/* 160 */     if (((Boolean)this.phase.getValue()).booleanValue() && this.mode.getValue() == Mode.Motion) {
/* 161 */       (Util.mc.field_71439_g.func_184187_bx()).field_70145_X = true;
/* 162 */       Util.mc.field_71439_g.func_184187_bx().func_189654_d(true);
/* 163 */       Util.mc.field_71439_g.field_70145_X = true;
/*     */     } 
/* 165 */     if (!this.Field2267) {
/* 166 */       Util.mc.field_71439_g.func_184187_bx().func_189654_d(!((Boolean)this.gravity.getValue()).booleanValue());
/* 167 */       Util.mc.field_71439_g.func_189654_d(!((Boolean)this.gravity.getValue()).booleanValue());
/*     */     } 
/* 169 */     if (((Boolean)this.stop.getValue()).booleanValue()) {
/* 170 */       if (this.Field2264 > ((Integer)this.enableticks.getValue()).intValue() && !this.Field2266) {
/* 171 */         this.Field2264 = 0;
/* 172 */         this.Field2266 = true;
/* 173 */         this.Field2265 = ((Integer)this.waitticks.getValue()).intValue();
/*     */       } 
/* 175 */       if (this.Field2265 > 0 && this.Field2266) {
/* 176 */         this.Field2265--;
/*     */         return;
/*     */       } 
/* 179 */       if (this.Field2265 <= 0) {
/* 180 */         this.Field2266 = false;
/*     */       }
/*     */     } 
/* 183 */     Entity entity = Util.mc.field_71439_g.func_184187_bx();
/* 184 */     if (((Boolean)this.debug.getValue()).booleanValue()) {
/* 185 */       Command.sendMessage("Y" + entity.field_70163_u);
/* 186 */       Command.sendMessage("Fall" + entity.field_70143_R);
/*     */     } 
/* 188 */     if ((!Util.mc.field_71441_e.func_190526_b(entity.func_180425_c().func_177958_n() >> 4, entity.func_180425_c().func_177952_p() >> 4) || entity.func_180425_c().func_177956_o() < 0) && ((Boolean)this.stopunloaded.getValue()).booleanValue()) {
/* 189 */       if (((Boolean)this.debug.getValue()).booleanValue()) {
/* 190 */         Command.sendMessage("Detected unloaded chunk!");
/*     */       }
/* 192 */       this.Field2267 = true;
/*     */       return;
/*     */     } 
/* 195 */     if (((Float)this.timer.getValue()).floatValue() != 1.0F) {
/* 196 */       Thunderhack.TICK_TIMER = ((Float)this.timer.getValue()).floatValue();
/*     */     }
/* 198 */     entity.field_70177_z = Util.mc.field_71439_g.field_70177_z;
/* 199 */     double[] dArray = Method1330(((Float)this.speed.getValue()).floatValue());
/* 200 */     double d = entity.field_70165_t + dArray[0];
/* 201 */     double d2 = entity.field_70161_v + dArray[1];
/* 202 */     double d3 = entity.field_70163_u;
/* 203 */     if ((!Util.mc.field_71441_e.func_190526_b((int)d >> 4, (int)d2 >> 4) || entity.func_180425_c().func_177956_o() < 0) && ((Boolean)this.stopunloaded.getValue()).booleanValue()) {
/* 204 */       if (((Boolean)this.debug.getValue()).booleanValue()) {
/* 205 */         Command.sendMessage("Detected unloaded chunk!");
/*     */       }
/* 207 */       this.Field2267 = true;
/*     */       return;
/*     */     } 
/* 210 */     this.Field2267 = false;
/* 211 */     entity.field_70181_x = -(((Float)this.glidespeed.getValue()).floatValue() / 100.0F);
/* 212 */     if (this.mode.getValue() == Mode.Motion) {
/* 213 */       entity.field_70159_w = dArray[0];
/* 214 */       entity.field_70179_y = dArray[1];
/*     */     } 
/* 216 */     if (Util.mc.field_71439_g.field_71158_b.field_78901_c) {
/* 217 */       if (!((Boolean)this.ylimit.getValue()).booleanValue() || entity.field_70163_u <= ((Float)this.height.getValue()).floatValue()) {
/* 218 */         if (this.mode.getValue() == Mode.Motion) {
/* 219 */           entity.field_70181_x += ((Float)this.yspeed.getValue()).floatValue();
/*     */         } else {
/* 221 */           d3 += ((Float)this.yspeed.getValue()).floatValue();
/*     */         } 
/*     */       }
/* 224 */     } else if (Util.mc.field_71439_g.field_71158_b.field_78899_d) {
/* 225 */       if (this.mode.getValue() == Mode.Motion) {
/* 226 */         entity.field_70181_x += -((Float)this.yspeed.getValue()).floatValue();
/*     */       } else {
/* 228 */         d3 += -((Float)this.yspeed.getValue()).floatValue();
/*     */       } 
/*     */     } 
/* 231 */     if (Util.mc.field_71439_g.field_71158_b.field_78902_a == 0.0F && Util.mc.field_71439_g.field_71158_b.field_192832_b == 0.0F) {
/* 232 */       entity.field_70159_w = 0.0D;
/* 233 */       entity.field_70179_y = 0.0D;
/*     */     } 
/* 235 */     if (((Boolean)this.ongroundpacket.getValue()).booleanValue()) {
/* 236 */       Method2876(entity);
/*     */     }
/* 238 */     if (this.mode.getValue() != Mode.Motion) {
/* 239 */       entity.func_70107_b(d, d3, d2);
/*     */     }
/* 241 */     if (this.mode.getValue() == Mode.Packet) {
/* 242 */       Method2875(new CPacketVehicleMove(entity));
/*     */     }
/* 244 */     if (((Boolean)this.strict.getValue()).booleanValue()) {
/* 245 */       Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketClickWindow(0, 0, 0, ClickType.CLONE, ItemStack.field_190927_a, (short)0));
/*     */     }
/* 247 */     if (((Boolean)this.spoofpackets.getValue()).booleanValue()) {
/* 248 */       Vec3d vec3d = entity.func_174791_d().func_72441_c(0.0D, Method2874(), 0.0D);
/* 249 */       EntityBoat entityBoat = new EntityBoat((World)Util.mc.field_71441_e, vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
/* 250 */       entityBoat.field_70177_z = entity.field_70177_z;
/* 251 */       entityBoat.field_70125_A = entity.field_70125_A;
/* 252 */       Method2875(new CPacketVehicleMove((Entity)entityBoat));
/*     */     } 
/* 254 */     if (((Boolean)this.remount.getValue()).booleanValue()) {
/* 255 */       Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
/*     */     }
/* 257 */     eventPlayerTravel.setCanceled(true);
/* 258 */     this.Field2264++;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive eventNetworkPrePacketEvent) {
/* 263 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/* 266 */     if (eventNetworkPrePacketEvent.getPacket() instanceof net.minecraft.network.play.server.SPacketDisconnect) {
/* 267 */       toggle();
/*     */     }
/* 269 */     if (!Util.mc.field_71439_g.func_184218_aH() || this.Field2267 || this.Field2266) {
/*     */       return;
/*     */     }
/* 272 */     if (eventNetworkPrePacketEvent.getPacket() instanceof net.minecraft.network.play.server.SPacketMoveVehicle && Util.mc.field_71439_g.func_184218_aH() && ((Boolean)this.cancel.getValue()).booleanValue()) {
/* 273 */       eventNetworkPrePacketEvent.setCanceled(true);
/*     */     }
/* 275 */     if (eventNetworkPrePacketEvent.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook && Util.mc.field_71439_g.func_184218_aH() && ((Boolean)this.cancel.getValue()).booleanValue()) {
/* 276 */       eventNetworkPrePacketEvent.setCanceled(true);
/*     */     }
/* 278 */     if (eventNetworkPrePacketEvent.getPacket() instanceof net.minecraft.network.play.server.SPacketEntity && ((Boolean)this.cancel.getValue()).booleanValue()) {
/* 279 */       eventNetworkPrePacketEvent.setCanceled(true);
/*     */     }
/* 281 */     if (eventNetworkPrePacketEvent.getPacket() instanceof net.minecraft.network.play.server.SPacketEntityAttach && ((Boolean)this.cancel.getValue()).booleanValue()) {
/* 282 */       eventNetworkPrePacketEvent.setCanceled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send eventNetworkPostPacketEvent) {
/* 288 */     if (Util.mc.field_71439_g == null || Util.mc.field_71441_e == null) {
/*     */       return;
/*     */     }
/* 291 */     if (((eventNetworkPostPacketEvent.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Rotation && ((Boolean)this.cancelrotations.getValue()).booleanValue()) || eventNetworkPostPacketEvent.getPacket() instanceof net.minecraft.network.play.client.CPacketInput) && Util.mc.field_71439_g.func_184218_aH()) {
/* 292 */       eventNetworkPostPacketEvent.setCanceled(true);
/*     */     }
/* 294 */     if (this.Field2267 && eventNetworkPostPacketEvent.getPacket() instanceof CPacketVehicleMove) {
/* 295 */       eventNetworkPostPacketEvent.setCanceled(true);
/*     */     }
/* 297 */     if (!Util.mc.field_71439_g.func_184218_aH() || this.Field2267 || this.Field2266) {
/*     */       return;
/*     */     }
/* 300 */     Entity entity = Util.mc.field_71439_g.func_184187_bx();
/* 301 */     if ((!Util.mc.field_71441_e.func_190526_b(entity.func_180425_c().func_177958_n() >> 4, entity.func_180425_c().func_177952_p() >> 4) || entity.func_180425_c().func_177956_o() < 0) && ((Boolean)this.stopunloaded.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/* 304 */     if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketVehicleMove && ((Boolean)this.limit.getValue()).booleanValue() && this.mode.getValue() == Mode.Packet) {
/* 305 */       CPacketVehicleMove cPacketVehicleMove = (CPacketVehicleMove)eventNetworkPostPacketEvent.getPacket();
/* 306 */       if (this.Field2263.contains(cPacketVehicleMove)) {
/* 307 */         this.Field2263.remove(cPacketVehicleMove);
/*     */       } else {
/* 309 */         eventNetworkPostPacketEvent.setCanceled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 315 */     Packet, Motion;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\BoatFly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */