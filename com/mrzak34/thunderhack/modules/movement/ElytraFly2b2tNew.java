/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.EventPlayerTravel;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.TurnEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ISPacketPlayerPosLook;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.PyroSpeed;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.client.event.EntityViewRenderEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class ElytraFly2b2tNew extends Module {
/*  29 */   private final Setting<Float> speedSetting = register(new Setting("FSpeed", Float.valueOf(16.0F), Float.valueOf(0.1F), Float.valueOf(20.0F)));
/*  30 */   public Setting<Boolean> timerControl = register(new Setting("Timer", Boolean.valueOf(true)));
/*  31 */   public Setting<Boolean> durabilityWarning = register(new Setting("ToggleIfLow", Boolean.valueOf(true)));
/*  32 */   public Setting<Boolean> glide = register(new Setting("Glide", Boolean.valueOf(false)));
/*  33 */   private final Setting<Float> glideSpeed = register(new Setting("GlideSpeed", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(10.0F), v -> ((Boolean)this.glide.getValue()).booleanValue()));
/*  34 */   public Setting<Float> speed = register(new Setting("Speed", Float.valueOf(0.8F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*  35 */   public Setting<Float> speedM = register(new Setting("MaxSpeed", Float.valueOf(0.8F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*  36 */   public Setting<Integer> acceleration = register(new Setting("Boost", Integer.valueOf(60), Integer.valueOf(0), Integer.valueOf(100)));
/*  37 */   public Setting<Float> boost_delay = register(new Setting("BoostDelay", Float.valueOf(1.5F), Float.valueOf(0.1F), Float.valueOf(3.0F)));
/*  38 */   int acceleration_ticks = 0;
/*     */   double current_speed;
/*     */   private boolean elytraIsEquipped = false;
/*  41 */   private int elytraDurability = 0;
/*     */   private boolean isFlying = false;
/*     */   private boolean isStandingStillH = false;
/*  44 */   private double hoverTarget = -1.0D;
/*     */   private boolean hoverState = false;
/*  46 */   private final Timer accelerationDelay = new Timer();
/*  47 */   private float dYaw = 0.0F;
/*  48 */   private float dPitch = 0.0F;
/*     */ 
/*     */   
/*     */   public ElytraFly2b2tNew() {
/*  52 */     super("ElytraFly2b2tNew", "ElytraFly2b2tNew", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  57 */     if (fullNullCheck())
/*  58 */       return;  if (mc.field_71439_g.func_175149_v() || !this.elytraIsEquipped || this.elytraDurability <= 1 || !this.isFlying)
/*  59 */       return;  if (e.getPacket() instanceof SPacketPlayerPosLook) {
/*  60 */       SPacketPlayerPosLook packet = (SPacketPlayerPosLook)e.getPacket();
/*  61 */       ((ISPacketPlayerPosLook)packet).setPitch(mc.field_71439_g.field_70125_A);
/*  62 */       this.acceleration_ticks = 0;
/*  63 */       this.accelerationDelay.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flyyyyy() {
/*  70 */     double[] dir = MathUtil.directionSpeed((float)RenderUtil.interpolate(((Float)this.speedM.getValue()).floatValue(), ((Float)this.speed.getValue()).floatValue(), (Math.min(this.acceleration_ticks, ((Integer)this.acceleration.getValue()).intValue()) / ((Integer)this.acceleration.getValue()).intValue())));
/*  71 */     if (Flight.mc.field_71439_g.field_71158_b.field_78902_a != 0.0F || Flight.mc.field_71439_g.field_71158_b.field_192832_b != 0.0F) {
/*  72 */       Flight.mc.field_71439_g.field_70159_w = dir[0];
/*  73 */       Flight.mc.field_71439_g.field_70179_y = dir[1];
/*     */     } 
/*  75 */     if (((Float)this.glideSpeed.getValue()).floatValue() != 0.0D && !mc.field_71474_y.field_74314_A.func_151470_d() && !mc.field_71474_y.field_74311_E.func_151470_d())
/*  76 */       mc.field_71439_g.field_70181_x = -((Float)this.glideSpeed.getValue()).floatValue(); 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onElytra(EventPlayerTravel event) {
/*  81 */     if (mc.field_71439_g.func_175149_v())
/*  82 */       return;  stateUpdate();
/*  83 */     flyyyyy();
/*  84 */     if (this.elytraIsEquipped && this.elytraDurability > 1) {
/*  85 */       if (!this.isFlying) {
/*  86 */         takeoff();
/*     */       } else {
/*  88 */         Thunderhack.TICK_TIMER = 1.0F;
/*  89 */         mc.field_71439_g.func_70031_b(false);
/*  90 */         controlMode(event);
/*     */       } 
/*     */     } else {
/*  93 */       reset2(true);
/*     */     } 
/*  95 */     if (this.accelerationDelay.passedMs((long)(((Float)this.boost_delay.getValue()).floatValue() * 1000.0F)))
/*  96 */       this.acceleration_ticks++; 
/*     */   }
/*     */   
/*     */   public void stateUpdate() {
/* 100 */     ItemStack armorSlot = (ItemStack)mc.field_71439_g.field_71071_by.field_70460_b.get(2);
/* 101 */     this.elytraIsEquipped = (armorSlot.func_77973_b() == Items.field_185160_cR);
/* 102 */     if (this.elytraIsEquipped) {
/* 103 */       int oldDurability = this.elytraDurability;
/* 104 */       this.elytraDurability = armorSlot.func_77958_k() - armorSlot.func_77952_i();
/* 105 */       if (!mc.field_71439_g.field_70122_E && oldDurability != this.elytraDurability && 
/* 106 */         this.elytraDurability <= 1 && (
/* 107 */         (Boolean)this.durabilityWarning.getValue()).booleanValue()) {
/* 108 */         mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_194007_a(SoundEvents.field_187604_bf, 1.0F, 1.0F));
/* 109 */         Command.sendMessage("Elytra is low, disabling!");
/* 110 */         toggle();
/*     */       } 
/*     */     } else {
/*     */       
/* 114 */       this.elytraDurability = 0;
/*     */     } 
/* 116 */     this.isFlying = mc.field_71439_g.func_184613_cA();
/*     */     
/* 118 */     this.isStandingStillH = (mc.field_71439_g.field_71158_b.field_192832_b == 0.0F && mc.field_71439_g.field_71158_b.field_78902_a == 0.0F);
/*     */     
/* 120 */     if (shouldSwing()) {
/* 121 */       mc.field_71439_g.field_184618_aE = mc.field_71439_g.field_70721_aZ;
/* 122 */       mc.field_71439_g.field_184619_aG = (float)(mc.field_71439_g.field_184619_aG + 1.3D);
/* 123 */       float speedRatio = (float)(this.current_speed / (float)RenderUtil.interpolate(((Float)this.speedM.getValue()).floatValue(), ((Float)this.speed.getValue()).floatValue(), (Math.min(this.acceleration_ticks, ((Integer)this.acceleration.getValue()).intValue()) / ((Integer)this.acceleration.getValue()).intValue())));
/* 124 */       mc.field_71439_g.field_70721_aZ = (float)(mc.field_71439_g.field_70721_aZ + (speedRatio * 1.2D - mc.field_71439_g.field_70721_aZ) * 0.4000000059604645D);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void updateValues(EventSync e) {
/* 130 */     double distTraveledLastTickX = mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q;
/* 131 */     double distTraveledLastTickZ = mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s;
/* 132 */     this.current_speed = Math.sqrt(distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ);
/*     */   }
/*     */   
/*     */   private void reset2(boolean cancelflu) {
/* 136 */     Thunderhack.TICK_TIMER = 1.0F;
/* 137 */     this.acceleration_ticks = 0;
/* 138 */     this.accelerationDelay.reset();
/*     */   }
/*     */   
/*     */   private void takeoff() {
/* 142 */     if (mc.field_71439_g.field_70122_E) {
/* 143 */       reset2(mc.field_71439_g.field_70122_E);
/*     */       return;
/*     */     } 
/* 146 */     if (mc.field_71439_g.field_70181_x < 0.0D) {
/* 147 */       if (((Boolean)this.timerControl.getValue()).booleanValue() && !mc.func_71356_B())
/* 148 */         Thunderhack.TICK_TIMER = 0.1F; 
/* 149 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/* 150 */       this.hoverTarget = (float)(mc.field_71439_g.field_70163_u + 0.2D);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setSpeed(double yaw) {
/* 155 */     mc.field_71439_g.func_70016_h(Math.sin(-yaw) * (float)RenderUtil.interpolate(((Float)this.speedM.getValue()).floatValue(), ((Float)this.speed.getValue()).floatValue(), (Math.min(this.acceleration_ticks, ((Integer)this.acceleration.getValue()).intValue()) / ((Integer)this.acceleration.getValue()).intValue())), mc.field_71439_g.field_70181_x, Math.cos(yaw) * (float)RenderUtil.interpolate(((Float)this.speedM.getValue()).floatValue(), ((Float)this.speed.getValue()).floatValue(), (Math.min(this.acceleration_ticks, ((Integer)this.acceleration.getValue()).intValue()) / ((Integer)this.acceleration.getValue()).intValue())));
/*     */   }
/*     */   
/*     */   private void controlMode(EventPlayerTravel event) {
/* 159 */     double currentSpeed = Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y);
/* 160 */     if (this.hoverTarget < 0.0D) {
/* 161 */       this.hoverTarget = mc.field_71439_g.field_70163_u;
/*     */     }
/* 163 */     this.hoverState = getHoverState();
/* 164 */     if (!this.isStandingStillH)
/* 165 */     { if (!this.hoverState || (currentSpeed < 0.8D && mc.field_71439_g.field_70181_x <= 1.0D)) {
/*     */ 
/*     */         
/* 168 */         mc.field_71439_g.field_70181_x = -3.0E-14D;
/* 169 */         setSpeed(calcMoveYaw());
/*     */       }  }
/* 171 */     else { mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D); }
/* 172 */      event.setCanceled(true);
/*     */   }
/*     */   
/*     */   private boolean shouldSwing() {
/* 176 */     return this.isFlying;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void Skid(EventSync e) {
/* 181 */     mc.field_71439_g.field_70125_A = -2.3F;
/*     */   }
/*     */   
/*     */   public boolean getHoverState() {
/* 185 */     if (this.hoverState) {
/* 186 */       return (mc.field_71439_g.field_70163_u < this.hoverTarget);
/*     */     }
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 194 */     reset2(true);
/* 195 */     mc.field_71439_g.field_71075_bZ.field_75100_b = false;
/* 196 */     mc.field_71439_g.field_71075_bZ.func_75092_a(0.05F);
/*     */   }
/*     */   
/*     */   public double calcMoveYaw() {
/* 200 */     double strafe = (90.0F * mc.field_71439_g.field_70702_br);
/* 201 */     strafe *= (mc.field_71439_g.field_191988_bg != 0.0F) ? (mc.field_71439_g.field_191988_bg * 0.5F) : 1.0D;
/*     */     
/* 203 */     double yaw = mc.field_71439_g.field_70177_z - strafe;
/* 204 */     yaw -= (mc.field_71439_g.field_191988_bg < 0.0F) ? 180.0D : 0.0D;
/*     */     
/* 206 */     return Math.toRadians(yaw);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
/* 211 */     if (PyroSpeed.isMovingClient()) {
/* 212 */       event.setYaw(event.getYaw() + this.dYaw);
/* 213 */       event.setPitch(event.getPitch() + this.dPitch);
/*     */     } else {
/* 215 */       this.dYaw = 0.0F;
/* 216 */       this.dPitch = 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTurnEvent(TurnEvent event) {
/* 222 */     if (PyroSpeed.isMovingClient()) {
/* 223 */       this.dYaw = (float)(this.dYaw + event.getYaw() * 0.15D);
/* 224 */       this.dPitch = (float)(this.dPitch - event.getPitch() * 0.15D);
/* 225 */       this.dPitch = MathHelper.func_76131_a(this.dPitch, -90.0F, 90.0F);
/* 226 */       event.setCanceled(true);
/*     */     } else {
/* 228 */       this.dYaw = 0.0F;
/* 229 */       this.dPitch = 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 236 */     this.dYaw = 0.0F;
/* 237 */     this.dPitch = 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\ElytraFly2b2tNew.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */