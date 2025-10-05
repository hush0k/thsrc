/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ 
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.PlayerUpdateEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IKeyBinding;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.player.ElytraSwap;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.MovementUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class LegitStrafe
/*     */   extends Module
/*     */ {
/*  27 */   public Setting<Float> motion2 = register(new Setting("motionY", Float.valueOf(0.42F), Float.valueOf(0.0F), Float.valueOf(0.84F)));
/*  28 */   public Setting<Float> speed = register(new Setting("Speed", Float.valueOf(0.8F), Float.valueOf(0.1F), Float.valueOf(3.0F)));
/*  29 */   public Setting<Float> speedM = register(new Setting("MaxSpeed", Float.valueOf(0.8F), Float.valueOf(0.1F), Float.valueOf(3.0F)));
/*  30 */   public Setting<Integer> acceleration = register(new Setting("Acceleration", Integer.valueOf(60), Integer.valueOf(0), Integer.valueOf(100)));
/*  31 */   public Setting<Boolean> onlyDown = register(new Setting("Silent", Boolean.valueOf(true)));
/*  32 */   public Setting<Float> jitterY = register(new Setting("JitterY", Float.valueOf(0.2F), Float.valueOf(0.0F), Double.valueOf(0.42D)));
/*  33 */   int prevElytraSlot = -1;
/*  34 */   int acceleration_ticks = 0;
/*  35 */   private final Timer timer = new Timer();
/*  36 */   private final Timer fixTimer = new Timer();
/*     */   public LegitStrafe() {
/*  38 */     super("GlideFly", "флай на саник-хуй пососаник", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   public static int getElly() {
/*  42 */     for (ItemStack stack : mc.field_71439_g.func_184193_aE()) {
/*  43 */       if (stack.func_77973_b() == Items.field_185160_cR) {
/*  44 */         return -2;
/*     */       }
/*     */     } 
/*  47 */     int slot = -1;
/*  48 */     for (int i = 0; i < 36; i++) {
/*  49 */       ItemStack s = mc.field_71439_g.field_71071_by.func_70301_a(i);
/*  50 */       if (s.func_77973_b() == Items.field_185160_cR) {
/*  51 */         slot = i;
/*     */         break;
/*     */       } 
/*     */     } 
/*  55 */     if (slot < 9 && slot != -1) {
/*  56 */       slot += 36;
/*     */     }
/*  58 */     return slot;
/*     */   }
/*     */   
/*     */   public static void setSpeed(float speed) {
/*  62 */     float yaw = mc.field_71439_g.field_70177_z;
/*  63 */     float forward = mc.field_71439_g.field_71158_b.field_192832_b;
/*  64 */     float strafe = mc.field_71439_g.field_71158_b.field_78902_a;
/*  65 */     if (forward != 0.0F) {
/*  66 */       if (strafe > 0.0F) {
/*  67 */         yaw += ((forward > 0.0F) ? -45 : 45);
/*  68 */       } else if (strafe < 0.0F) {
/*  69 */         yaw += ((forward > 0.0F) ? 45 : -45);
/*     */       } 
/*     */       
/*  72 */       strafe = 0.0F;
/*  73 */       if (forward > 0.0F) {
/*  74 */         forward = 1.0F;
/*  75 */       } else if (forward < 0.0F) {
/*  76 */         forward = -1.0F;
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/*  81 */     double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/*  82 */     mc.field_71439_g.field_70159_w = (forward * speed) * cos + (strafe * speed) * sin;
/*  83 */     mc.field_71439_g.field_70179_y = (forward * speed) * sin - (strafe * speed) * cos;
/*     */   }
/*     */   
/*     */   public static void setSpeed2(float speed) {
/*  87 */     float yaw = mc.field_71439_g.field_70177_z;
/*  88 */     float forward = 1.0F;
/*  89 */     double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/*  90 */     double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/*  91 */     mc.field_71439_g.field_70159_w = (forward * speed) * cos;
/*  92 */     mc.field_71439_g.field_70179_y = (forward * speed) * sin;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEvent222(PlayerUpdateEvent event) {
/*  97 */     if (!((Boolean)this.onlyDown.getValue()).booleanValue()) {
/*  98 */       if (mc.field_71439_g.field_70173_aa % 2 != 0)
/*  99 */         return;  ItemStack itemStack = ElytraSwap.getItemStack(38);
/* 100 */       if (itemStack == null)
/* 101 */         return;  if (mc.field_71439_g.field_70122_E)
/*     */         return; 
/* 103 */       if (itemStack.func_77973_b() == Items.field_185160_cR) {
/* 104 */         if (this.prevElytraSlot != -1) {
/* 105 */           ElytraSwap.clickSlot(this.prevElytraSlot);
/* 106 */           ElytraSwap.clickSlot(38);
/* 107 */           ElytraSwap.clickSlot(this.prevElytraSlot);
/*     */         } 
/* 109 */       } else if (ElytraSwap.hasItem(Items.field_185160_cR)) {
/* 110 */         this.prevElytraSlot = ElytraSwap.getSlot(Items.field_185160_cR);
/* 111 */         ElytraSwap.clickSlot(this.prevElytraSlot);
/* 112 */         ElytraSwap.clickSlot(38);
/* 113 */         ElytraSwap.clickSlot(this.prevElytraSlot);
/* 114 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/*     */         
/* 116 */         mc.field_71439_g.field_70181_x = ((Float)this.jitterY.getValue()).floatValue();
/*     */         
/* 118 */         if (((IKeyBinding)mc.field_71474_y.field_74314_A).isPressed()) {
/* 119 */           mc.field_71439_g.field_70181_x = ((Float)this.motion2.getValue()).floatValue();
/* 120 */         } else if (((IKeyBinding)mc.field_71474_y.field_74311_E).isPressed()) {
/* 121 */           mc.field_71439_g.field_70181_x = -((Float)this.motion2.getValue()).floatValue();
/*     */         } 
/* 123 */         if (MovementUtil.isMoving()) {
/* 124 */           setSpeed(((Float)this.speed.getValue()).floatValue());
/*     */         } else {
/* 126 */           setSpeed2(0.1F);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 130 */       int elytra = getElly();
/*     */       
/* 132 */       if (elytra == -1) {
/* 133 */         Command.sendMessage("Нет элитр!");
/* 134 */         toggle();
/*     */         
/*     */         return;
/*     */       } 
/* 138 */       if (mc.field_71439_g.field_70122_E) {
/* 139 */         mc.field_71439_g.func_70664_aZ();
/* 140 */         this.timer.reset();
/* 141 */         this.acceleration_ticks = 0;
/* 142 */       } else if (this.timer.passedMs(350L)) {
/*     */         
/* 144 */         if (mc.field_71439_g.field_70173_aa % 2 == 0) {
/* 145 */           disabler2(elytra);
/*     */         }
/*     */         
/* 148 */         mc.field_71439_g.field_70181_x = (mc.field_71439_g.field_70173_aa % 2 != 0) ? -0.25D : 0.25D;
/*     */ 
/*     */         
/* 151 */         if (!mc.field_71439_g.func_70093_af() && ((IKeyBinding)mc.field_71474_y.field_74314_A).isPressed()) {
/* 152 */           mc.field_71439_g.field_70181_x = ((Float)this.motion2.getValue()).floatValue();
/*     */         }
/* 154 */         if (mc.field_71474_y.field_74311_E.func_151470_d()) {
/* 155 */           mc.field_71439_g.field_70181_x = -((Float)this.motion2.getValue()).floatValue();
/*     */         }
/* 157 */         if (MovementUtil.isMoving()) {
/* 158 */           setSpeed((float)RenderUtil.interpolate(((Float)this.speedM.getValue()).floatValue(), ((Float)this.speed.getValue()).floatValue(), (Math.min(this.acceleration_ticks, ((Integer)this.acceleration.getValue()).intValue()) / ((Integer)this.acceleration.getValue()).intValue())));
/*     */         } else {
/* 160 */           this.acceleration_ticks = 0;
/* 161 */           setSpeed2(0.1F);
/*     */         } 
/*     */       } 
/*     */     } 
/* 165 */     this.acceleration_ticks++;
/* 166 */     fixElytra();
/*     */   }
/*     */   
/*     */   public void fixElytra() {
/* 170 */     ItemStack stack = mc.field_71439_g.field_71071_by.func_70445_o();
/* 171 */     if (stack != null && stack.func_77973_b() instanceof ItemArmor && this.fixTimer.passed(300L)) {
/* 172 */       ItemArmor ia = (ItemArmor)stack.func_77973_b();
/* 173 */       if (ia.field_77881_a == EntityEquipmentSlot.CHEST && mc.field_71439_g.field_71071_by.func_70440_f(2).func_77973_b() == Items.field_185160_cR) {
/* 174 */         mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 175 */         int nullSlot = Strafe.findNullSlot();
/* 176 */         boolean needDrop = (nullSlot == 999);
/* 177 */         if (needDrop) {
/* 178 */           nullSlot = 9;
/*     */         }
/* 180 */         mc.field_71442_b.func_187098_a(0, nullSlot, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 181 */         if (needDrop) {
/* 182 */           mc.field_71442_b.func_187098_a(0, -999, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */         }
/* 184 */         this.fixTimer.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void disabler2(int elytra) {
/* 190 */     if (elytra != -2) {
/* 191 */       mc.field_71442_b.func_187098_a(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 192 */       mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */     } 
/*     */     
/* 195 */     if (!((Boolean)this.onlyDown.getValue()).booleanValue())
/* 196 */       mc.func_147114_u().func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING)); 
/* 197 */     mc.func_147114_u().func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/*     */     
/* 199 */     if (elytra != -2) {
/* 200 */       mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 201 */       mc.field_71442_b.func_187098_a(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 207 */     this.acceleration_ticks = 0;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/* 212 */     if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook)
/* 213 */       this.acceleration_ticks = 0; 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\LegitStrafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */