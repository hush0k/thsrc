/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Sprint
/*     */   extends Module {
/*     */   public static double oldSpeed;
/*     */   public static double contextFriction;
/*     */   int cooldown;
/*  19 */   private final Setting<mode> Mode = register(new Setting("Mode", mode.Default));
/*  20 */   public Setting<Float> speed1 = register(new Setting("Speed", Float.valueOf(0.1F), Float.valueOf(0.0F), Float.valueOf(0.5F), v -> (this.Mode.getValue() == mode.MatrixOmniSprint)));
/*     */ 
/*     */   
/*     */   public Sprint() {
/*  24 */     super("Sprint", "автоматически-спринтится", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   public static double calculateSpeed(double speed) {
/*  28 */     float speedAttributes = getAIMoveSpeed((EntityPlayer)mc.field_71439_g);
/*  29 */     float n6 = getFrictionFactor((EntityPlayer)mc.field_71439_g);
/*  30 */     float n7 = 0.16277136F / n6 * n6 * n6;
/*  31 */     float n8 = speedAttributes * n7;
/*  32 */     double max2 = oldSpeed + n8;
/*  33 */     max2 = Math.max(0.25D, max2);
/*  34 */     contextFriction = n6;
/*  35 */     return max2 + speed;
/*     */   }
/*     */   
/*     */   public static void postMove(double horizontal) {
/*  39 */     oldSpeed = horizontal * contextFriction;
/*     */   }
/*     */   
/*     */   public static float getAIMoveSpeed(EntityPlayer contextPlayer) {
/*  43 */     boolean prevSprinting = contextPlayer.func_70051_ag();
/*  44 */     contextPlayer.func_70031_b(false);
/*  45 */     float speed = contextPlayer.func_70689_ay() * 1.3F;
/*  46 */     contextPlayer.func_70031_b(prevSprinting);
/*  47 */     return speed;
/*     */   }
/*     */   
/*     */   private static float getFrictionFactor(EntityPlayer contextPlayer) {
/*  51 */     BlockPos.PooledMutableBlockPos blockpos = BlockPos.PooledMutableBlockPos.func_185345_c(mc.field_71439_g.field_70169_q, (mc.field_71439_g.func_174813_aQ()).field_72338_b, mc.field_71439_g.field_70166_s);
/*  52 */     return (contextPlayer.field_70170_p.func_180495_p((BlockPos)blockpos).func_177230_c()).field_149765_K * 0.91F;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onSync(EventSync e) {
/*  57 */     if (fullNullCheck())
/*  58 */       return;  if (mc.field_71439_g.func_70093_af())
/*  59 */       return;  if (mc.field_71474_y.field_74351_w.func_151470_d()) {
/*  60 */       mc.field_71439_g.func_70031_b(true);
/*     */     }
/*  62 */     if (this.cooldown > 0) {
/*  63 */       this.cooldown--;
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  69 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*     */     
/*  73 */     if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
/*  74 */       this.cooldown = 60;
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMove(EventMove event) {
/*  80 */     if (this.Mode.getValue() == mode.MatrixOmniSprint && ((Speed)Thunderhack.moduleManager
/*  81 */       .getModuleByClass(Speed.class)).isDisabled() && ((Strafe)Thunderhack.moduleManager
/*  82 */       .getModuleByClass(Strafe.class)).isDisabled() && ((RusherScaffold)Thunderhack.moduleManager
/*  83 */       .getModuleByClass(RusherScaffold.class)).isDisabled()) {
/*     */       
/*  85 */       double dX = mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q;
/*  86 */       double dZ = mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s;
/*  87 */       postMove(Math.sqrt(dX * dX + dZ * dZ));
/*     */ 
/*     */       
/*  90 */       if (strafes()) {
/*  91 */         double forward = mc.field_71439_g.field_71158_b.field_192832_b;
/*  92 */         double strafe = mc.field_71439_g.field_71158_b.field_78902_a;
/*  93 */         float yaw = mc.field_71439_g.field_70177_z;
/*  94 */         if (forward == 0.0D && strafe == 0.0D) {
/*  95 */           oldSpeed = 0.0D;
/*     */         } else {
/*     */           
/*  98 */           if (forward != 0.0D) {
/*  99 */             if (strafe > 0.0D) {
/* 100 */               yaw += ((forward > 0.0D) ? -45 : 45);
/* 101 */             } else if (strafe < 0.0D) {
/* 102 */               yaw += ((forward > 0.0D) ? 45 : -45);
/*     */             } 
/* 104 */             strafe = 0.0D;
/* 105 */             if (forward > 0.0D) {
/* 106 */               forward = 1.0D;
/* 107 */             } else if (forward < 0.0D) {
/* 108 */               forward = -1.0D;
/*     */             } 
/*     */           } 
/* 111 */           double speed = calculateSpeed((((Float)this.speed1.getValue()).floatValue() / 10.0F));
/* 112 */           event.set_x(forward * speed * Math.cos(Math.toRadians((yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((yaw + 90.0F))));
/* 113 */           event.set_z(forward * speed * Math.sin(Math.toRadians((yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((yaw + 90.0F))));
/*     */         } 
/*     */       } else {
/* 116 */         oldSpeed = 0.0D;
/*     */       } 
/* 118 */       event.setCanceled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean strafes() {
/* 124 */     if (mc.field_71439_g.func_70093_af()) {
/* 125 */       return false;
/*     */     }
/* 127 */     if (mc.field_71439_g.func_180799_ab()) {
/* 128 */       return false;
/*     */     }
/* 130 */     if (mc.field_71439_g.func_70090_H()) {
/* 131 */       return false;
/*     */     }
/* 133 */     if (((IEntity)mc.field_71439_g).isInWeb()) {
/* 134 */       return false;
/*     */     }
/* 136 */     if (!mc.field_71439_g.field_70122_E) {
/* 137 */       return false;
/*     */     }
/* 139 */     if (mc.field_71474_y.field_74314_A.func_151470_d()) {
/* 140 */       return false;
/*     */     }
/* 142 */     if (this.cooldown > 0) {
/* 143 */       return false;
/*     */     }
/* 145 */     if (((LongJump)Thunderhack.moduleManager.getModuleByClass(LongJump.class)).isOn()) {
/* 146 */       return false;
/*     */     }
/* 148 */     return !mc.field_71439_g.field_71075_bZ.field_75100_b;
/*     */   }
/*     */   
/*     */   public enum mode {
/* 152 */     Default, MatrixOmniSprint;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\Sprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */