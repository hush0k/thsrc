/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ISPacketPlayerPosLook;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import io.netty.util.internal.ConcurrentSet;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketConfirmTeleport;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class PacketFly2 extends Module {
/*  27 */   public Setting<Boolean> autoClip = register(new Setting("AutoClip", Boolean.valueOf(false)));
/*  28 */   public Setting<Boolean> limit = register(new Setting("Limit", Boolean.valueOf(true)));
/*  29 */   public Setting<Boolean> antiKick = register(new Setting("AntiKick", Boolean.valueOf(true)));
/*  30 */   public Setting<Float> speed = register(new Setting("Speed", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(3.0F)));
/*  31 */   public Setting<Float> timer = register(new Setting("Timer", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(2.0F)));
/*  32 */   public Setting<Integer> increaseTicks = register(new Setting("IncreaseTicks", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(20)));
/*  33 */   public Setting<Integer> factor = register(new Setting("Factor", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(10)));
/*  34 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.Fast));
/*  35 */   private final Setting<Phase> phase = register(new Setting("Phase", Phase.Full));
/*  36 */   private final Setting<Type> type = register(new Setting("Type", Type.Preserve));
/*  37 */   private int Field3526 = -1;
/*  38 */   private final ConcurrentSet Field3527 = new ConcurrentSet();
/*  39 */   private final Random Field3528 = new Random();
/*  40 */   private final ConcurrentHashMap Field3529 = new ConcurrentHashMap<>();
/*  41 */   private int Field3530 = 0;
/*  42 */   private int Field3531 = 0;
/*  43 */   private int Field3532 = 0;
/*     */   private boolean Field3533 = false;
/*     */   
/*     */   public PacketFly2() {
/*  47 */     super("PacketFly2", "PacketFly2", Module.Category.MOVEMENT);
/*     */   }
/*     */   private boolean Field3534 = false;
/*     */   private static boolean Method4295(Object o) {
/*  51 */     return (System.currentTimeMillis() - ((Class443)((Map.Entry)o).getValue()).Method1915() > TimeUnit.SECONDS.toMillis(30L));
/*     */   }
/*     */   
/*     */   public static boolean Method103() {
/*  55 */     return (Util.mc.field_71439_g != null && (Util.mc.field_71439_g.field_71158_b.field_192832_b != 0.0F || Util.mc.field_71439_g.field_71158_b.field_78902_a != 0.0F));
/*     */   }
/*     */   
/*     */   public static double[] Method3180(double d) {
/*  59 */     double d2 = Util.mc.field_71439_g.field_71158_b.field_192832_b;
/*  60 */     double d3 = Util.mc.field_71439_g.field_71158_b.field_78902_a;
/*  61 */     float f = Util.mc.field_71439_g.field_70177_z;
/*  62 */     double[] dArray = new double[2];
/*  63 */     if (d2 == 0.0D && d3 == 0.0D) {
/*  64 */       dArray[0] = 0.0D;
/*  65 */       dArray[1] = 0.0D;
/*     */     } else {
/*  67 */       if (d2 != 0.0D) {
/*  68 */         if (d3 > 0.0D) {
/*  69 */           f += ((d2 > 0.0D) ? -45 : 45);
/*  70 */         } else if (d3 < 0.0D) {
/*  71 */           f += ((d2 > 0.0D) ? 45 : -45);
/*     */         } 
/*  73 */         d3 = 0.0D;
/*  74 */         if (d2 > 0.0D) {
/*  75 */           d2 = 1.0D;
/*  76 */         } else if (d2 < 0.0D) {
/*  77 */           d2 = -1.0D;
/*     */         } 
/*     */       } 
/*  80 */       dArray[0] = d2 * d * Math.cos(Math.toRadians((f + 90.0F))) + d3 * d * Math.sin(Math.toRadians((f + 90.0F)));
/*  81 */       dArray[1] = d2 * d * Math.sin(Math.toRadians((f + 90.0F))) - d3 * d * Math.cos(Math.toRadians((f + 90.0F)));
/*     */     } 
/*  83 */     return dArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  88 */     this.Field3526 = -1;
/*  89 */     this.Field3531 = 0;
/*  90 */     this.Field3532 = 0;
/*  91 */     if (fullNullCheck() && Util.mc.field_71439_g != null) {
/*  92 */       Method4286();
/*     */     }
/*  94 */     if (((Boolean)this.autoClip.getValue()).booleanValue() && this.Field3534) {
/*  95 */       Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Util.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 101 */     Thunderhack.TICK_TIMER = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void Method4286() {
/* 106 */     this.Field3530 = 0;
/* 107 */     this.Field3526 = 0;
/* 108 */     this.Field3527.clear();
/* 109 */     this.Field3529.clear();
/*     */   }
/*     */   
/*     */   public boolean Method4289(int n) {
/* 113 */     this.Field3530++;
/* 114 */     if (this.Field3530 >= n) {
/* 115 */       this.Field3530 = 0;
/* 116 */       return true;
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */   
/*     */   public void Method4290(CPacketPlayer cPacketPlayer) {
/* 122 */     this.Field3527.add(cPacketPlayer);
/* 123 */     Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)cPacketPlayer);
/*     */   }
/*     */   
/*     */   private int Method4291() {
/* 127 */     if (Util.mc.func_71356_B()) {
/* 128 */       return 2000;
/*     */     }
/* 130 */     int n = this.Field3528.nextInt(29000000);
/* 131 */     if (this.Field3528.nextBoolean()) {
/* 132 */       return n;
/*     */     }
/* 134 */     return -n;
/*     */   }
/*     */   
/*     */   public Vec3d Method4292(Vec3d vec3d, Vec3d vec3d2) {
/* 138 */     Vec3d vec3d3 = vec3d.func_178787_e(vec3d2);
/* 139 */     switch ((Type)this.type.getValue()) {
/*     */       case Preserve:
/* 141 */         vec3d3 = vec3d3.func_72441_c(Method4291(), 0.0D, Method4291());
/*     */         break;
/*     */       
/*     */       case Up:
/* 145 */         vec3d3 = vec3d3.func_72441_c(0.0D, 1337.0D, 0.0D);
/*     */         break;
/*     */       
/*     */       case Down:
/* 149 */         vec3d3 = vec3d3.func_72441_c(0.0D, -1337.0D, 0.0D);
/*     */         break;
/*     */       
/*     */       case Bounds:
/* 153 */         vec3d3 = new Vec3d(vec3d3.field_72450_a, (Util.mc.field_71439_g.field_70163_u <= 10.0D) ? 255.0D : 1.0D, vec3d3.field_72449_c);
/*     */         break;
/*     */     } 
/* 156 */     return vec3d3;
/*     */   }
/*     */   
/*     */   public void Method4293(Double d, Double d2, Double d3, Boolean bl) {
/* 160 */     Vec3d vec3d = new Vec3d(d.doubleValue(), d2.doubleValue(), d3.doubleValue());
/* 161 */     Vec3d vec3d2 = Util.mc.field_71439_g.func_174791_d().func_178787_e(vec3d);
/* 162 */     Vec3d vec3d3 = Method4292(vec3d, vec3d2);
/* 163 */     Method4290((CPacketPlayer)new CPacketPlayer.Position(vec3d2.field_72450_a, vec3d2.field_72448_b, vec3d2.field_72449_c, Util.mc.field_71439_g.field_70122_E));
/* 164 */     Method4290((CPacketPlayer)new CPacketPlayer.Position(vec3d3.field_72450_a, vec3d3.field_72448_b, vec3d3.field_72449_c, Util.mc.field_71439_g.field_70122_E));
/* 165 */     if (bl.booleanValue()) {
/* 166 */       Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketConfirmTeleport(++this.Field3526));
/* 167 */       this.Field3529.put(Integer.valueOf(this.Field3526), new Class443(vec3d2.field_72450_a, vec3d2.field_72448_b, vec3d2.field_72449_c, System.currentTimeMillis()));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean Method4294() {
/* 172 */     return !Util.mc.field_71441_e.func_184144_a((Entity)Util.mc.field_71439_g, Util.mc.field_71439_g.func_174813_aQ().func_72321_a(-0.0625D, -0.0625D, -0.0625D)).isEmpty();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void Method4282(PacketEvent.Receive eventNetworkPrePacketEvent) {
/* 177 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/* 180 */     if (Util.mc.field_71439_g != null && eventNetworkPrePacketEvent.getPacket() instanceof SPacketPlayerPosLook) {
/* 181 */       SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)eventNetworkPrePacketEvent.getPacket();
/* 182 */       Class443 class443 = (Class443)this.Field3529.remove(Integer.valueOf(((ISPacketPlayerPosLook)sPacketPlayerPosLook).getTeleportId()));
/* 183 */       if (Util.mc.field_71439_g.func_70089_S() && Util.mc.field_71441_e.func_175668_a(new BlockPos(Util.mc.field_71439_g.field_70165_t, Util.mc.field_71439_g.field_70163_u, Util.mc.field_71439_g.field_70161_v), false) && !(Util.mc.field_71462_r instanceof net.minecraft.client.gui.GuiDownloadTerrain) && this.mode.getValue() != Mode.Rubber && class443 != null && Class443.Method1920(class443) == sPacketPlayerPosLook.func_148932_c() && Class443.Method1921(class443) == sPacketPlayerPosLook.func_148928_d() && Class443.Method1922(class443) == sPacketPlayerPosLook.func_148933_e()) {
/* 184 */         eventNetworkPrePacketEvent.setCanceled(true);
/*     */         return;
/*     */       } 
/* 187 */       ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setYaw(Util.mc.field_71439_g.field_70177_z);
/* 188 */       ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setPitch(Util.mc.field_71439_g.field_70125_A);
/* 189 */       this.Field3526 = sPacketPlayerPosLook.func_186965_f();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void Method4281(PacketEvent.Send eventNetworkPostPacketEvent) {
/* 196 */     if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketPlayer) {
/* 197 */       if (this.Field3527.contains(eventNetworkPostPacketEvent.getPacket())) {
/* 198 */         this.Field3527.remove(eventNetworkPostPacketEvent.getPacket());
/*     */         return;
/*     */       } 
/* 201 */       eventNetworkPostPacketEvent.setCanceled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 207 */     this.Field3529.entrySet().removeIf(PacketFly2::Method4295);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void Method4279(EventMove eventPlayerMove) {
/* 212 */     if (!eventPlayerMove.isCanceled()) {
/* 213 */       if (this.mode.getValue() != Mode.Rubber && this.Field3526 == 0) {
/*     */         return;
/*     */       }
/* 216 */       eventPlayerMove.setCanceled(true);
/* 217 */       eventPlayerMove.set_x(Util.mc.field_71439_g.field_70159_w);
/* 218 */       eventPlayerMove.set_y(Util.mc.field_71439_g.field_70181_x);
/* 219 */       eventPlayerMove.set_z(Util.mc.field_71439_g.field_70179_y);
/* 220 */       if (this.phase.getValue() != Phase.Off && (this.phase.getValue() == Phase.Semi || Method4294())) {
/* 221 */         Util.mc.field_71439_g.field_70145_X = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void Method4278(EventSync eventPlayerUpdateWalking) {
/* 228 */     if (((Float)this.timer.getValue()).floatValue() != 1.0D) {
/* 229 */       Thunderhack.TICK_TIMER = ((Float)this.timer.getValue()).floatValue();
/*     */     }
/* 231 */     Util.mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
/* 232 */     if (this.mode.getValue() != Mode.Rubber && this.Field3526 == 0) {
/* 233 */       if (Method4289(4)) {
/* 234 */         Method4293(Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.0D), Boolean.valueOf(false));
/*     */       }
/*     */       return;
/*     */     } 
/* 238 */     boolean bl = Method4294();
/* 239 */     double d = 0.0D;
/* 240 */     d = (Util.mc.field_71439_g.field_71158_b.field_78901_c && (bl || !Method103())) ? ((((Boolean)this.antiKick.getValue()).booleanValue() && !bl) ? (Method4289((this.mode.getValue() == Mode.Rubber) ? 10 : 20) ? -0.032D : 0.062D) : 0.062D) : (Util.mc.field_71439_g.field_71158_b.field_78899_d ? -0.062D : (!bl ? (Method4289(4) ? (((Boolean)this.antiKick.getValue()).booleanValue() ? -0.04D : 0.0D) : 0.0D) : 0.0D));
/* 241 */     if (this.phase.getValue() == Phase.Full && bl && Method103() && d != 0.0D) {
/* 242 */       d = Util.mc.field_71439_g.field_71158_b.field_78901_c ? (d /= 2.5D) : (d /= 1.5D);
/*     */     }
/* 244 */     if (Util.mc.field_71439_g.field_71158_b.field_78901_c && ((Boolean)this.autoClip.getValue()).booleanValue()) {
/* 245 */       Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Util.mc.field_71439_g, this.Field3534 ? CPacketEntityAction.Action.START_SNEAKING : CPacketEntityAction.Action.STOP_SNEAKING));
/* 246 */       this.Field3534 = !this.Field3534;
/*     */     } 
/* 248 */     double[] dArray = Method3180((this.phase.getValue() == Phase.Full && bl) ? 0.034444444444444444D : (((Float)this.speed.getValue()).floatValue() * 0.26D));
/* 249 */     int n = 1;
/* 250 */     if (this.mode.getValue() == Mode.Factor && Util.mc.field_71439_g.field_70173_aa % ((Integer)this.increaseTicks.getValue()).intValue() == 0) {
/* 251 */       n = ((Integer)this.factor.getValue()).intValue();
/*     */     }
/* 253 */     for (int i = 1; i <= n; i++) {
/* 254 */       if (this.mode.getValue() == Mode.Limit) {
/* 255 */         if (Util.mc.field_71439_g.field_70173_aa % 2 == 0) {
/* 256 */           if (this.Field3533 && d >= 0.0D) {
/* 257 */             this.Field3533 = false;
/* 258 */             d = -0.032D;
/*     */           } 
/* 260 */           Util.mc.field_71439_g.field_70159_w = dArray[0] * i;
/* 261 */           Util.mc.field_71439_g.field_70179_y = dArray[1] * i;
/* 262 */           Util.mc.field_71439_g.field_70181_x = d * i;
/* 263 */           Method4293(Double.valueOf(Util.mc.field_71439_g.field_70159_w), Double.valueOf(Util.mc.field_71439_g.field_70181_x), Double.valueOf(Util.mc.field_71439_g.field_70179_y), Boolean.valueOf(!((Boolean)this.limit.getValue()).booleanValue()));
/*     */         
/*     */         }
/* 266 */         else if (d < 0.0D) {
/* 267 */           this.Field3533 = true;
/*     */         } 
/*     */       } else {
/* 270 */         Util.mc.field_71439_g.field_70159_w = dArray[0] * i;
/* 271 */         Util.mc.field_71439_g.field_70179_y = dArray[1] * i;
/* 272 */         Util.mc.field_71439_g.field_70181_x = d * i;
/* 273 */         Method4293(Double.valueOf(Util.mc.field_71439_g.field_70159_w), Double.valueOf(Util.mc.field_71439_g.field_70181_x), Double.valueOf(Util.mc.field_71439_g.field_70179_y), Boolean.valueOf((this.mode.getValue() != Mode.Rubber)));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/* 278 */   public enum Mode { Fast, Factor, Rubber, Limit; }
/*     */ 
/*     */   
/*     */   public enum Phase
/*     */   {
/* 283 */     Full, Off, Semi;
/*     */   }
/*     */   
/*     */   public enum Type {
/* 287 */     Preserve, Up, Down, Bounds;
/*     */   }
/*     */   
/*     */   public static class Class443 {
/*     */     private final double Field1501;
/*     */     private final double Field1502;
/*     */     private final double Field1503;
/*     */     private final long Field1504;
/*     */     
/*     */     public Class443(double d, double d2, double d3, long l) {
/* 297 */       this.Field1501 = d;
/* 298 */       this.Field1502 = d2;
/* 299 */       this.Field1503 = d3;
/* 300 */       this.Field1504 = l;
/*     */     }
/*     */     
/*     */     static double Method1920(Class443 class443) {
/* 304 */       return class443.Field1501;
/*     */     }
/*     */     
/*     */     static double Method1921(Class443 class443) {
/* 308 */       return class443.Field1502;
/*     */     }
/*     */     
/*     */     static double Method1922(Class443 class443) {
/* 312 */       return class443.Field1503;
/*     */     }
/*     */     
/*     */     public long Method1915() {
/* 316 */       return this.Field1504;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\PacketFly2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */