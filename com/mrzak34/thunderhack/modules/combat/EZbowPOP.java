/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Parent;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EZbowPOP
/*     */   extends Module
/*     */ {
/*  29 */   public static Timer delayTimer = new Timer();
/*  30 */   public final Setting<Parent> selection = register(new Setting("Selection", new Parent(false)));
/*  31 */   public final Setting<Boolean> bow = register(new Setting("Bows", Boolean.valueOf(true))).withParent(this.selection);
/*  32 */   public final Setting<Boolean> pearls = register(new Setting("EPearls", Boolean.valueOf(true))).withParent(this.selection);
/*  33 */   public final Setting<Boolean> xp = register(new Setting("XP", Boolean.valueOf(true))).withParent(this.selection);
/*  34 */   public final Setting<Boolean> eggs = register(new Setting("Eggs", Boolean.valueOf(true))).withParent(this.selection);
/*  35 */   public final Setting<Boolean> potions = register(new Setting("SplashPotions", Boolean.valueOf(true))).withParent(this.selection);
/*  36 */   public final Setting<Boolean> snowballs = register(new Setting("Snowballs", Boolean.valueOf(true))).withParent(this.selection);
/*  37 */   public Setting<Boolean> rotation = register(new Setting("Rotation", Boolean.valueOf(false)));
/*  38 */   public Setting<ModeEn> Mode = register(new Setting("Mode", ModeEn.Maximum));
/*  39 */   public Setting<Float> factor = register(new Setting("Factor", Float.valueOf(1.0F), Float.valueOf(1.0F), Float.valueOf(20.0F)));
/*  40 */   public Setting<exploitEn> exploit = register(new Setting("Exploit", exploitEn.Strong));
/*  41 */   public Setting<Float> scale = register(new Setting("Scale", Float.valueOf(0.01F), Float.valueOf(0.01F), Float.valueOf(0.4F)));
/*  42 */   public Setting<Boolean> minimize = register(new Setting("Minimize", Boolean.valueOf(false)));
/*  43 */   public Setting<Float> delay = register(new Setting("Delay", Float.valueOf(5.0F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/*  44 */   private final Random rnd = new Random();
/*     */   public EZbowPOP() {
/*  46 */     super("EZbowPOP", "Шотает с лука", Module.Category.COMBAT);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   protected void onPacketSend(PacketEvent.Send event) {
/*  51 */     if (fullNullCheck() || !delayTimer.passedMs((long)(((Float)this.delay.getValue()).floatValue() * 1000.0F)))
/*  52 */       return;  if ((event.getPacket() instanceof CPacketPlayerDigging && ((CPacketPlayerDigging)event.getPacket()).func_180762_c() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM && mc.field_71439_g.func_184607_cu().func_77973_b() == Items.field_151031_f && ((Boolean)this.bow.getValue()).booleanValue()) || (event
/*  53 */       .getPacket() instanceof CPacketPlayerTryUseItem && ((CPacketPlayerTryUseItem)event.getPacket()).func_187028_a() == EnumHand.MAIN_HAND && ((mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151079_bi && ((Boolean)this.pearls.getValue()).booleanValue()) || (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151062_by && ((Boolean)this.xp.getValue()).booleanValue()) || (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151110_aK && ((Boolean)this.eggs.getValue()).booleanValue()) || (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185155_bH && ((Boolean)this.potions.getValue()).booleanValue()) || (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151126_ay && ((Boolean)this.snowballs.getValue()).booleanValue())))) {
/*     */       
/*  55 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*     */       
/*  57 */       double[] strict_direction = { 100.0D * -Math.sin(Math.toRadians(mc.field_71439_g.field_70177_z)), 100.0D * Math.cos(Math.toRadians(mc.field_71439_g.field_70177_z)) };
/*     */       
/*  59 */       if (this.exploit.getValue() == exploitEn.Fast) {
/*  60 */         for (int i = 0; i < getRuns(); i++) {
/*  61 */           spoof(mc.field_71439_g.field_70165_t, ((Boolean)this.minimize.getValue()).booleanValue() ? mc.field_71439_g.field_70163_u : (mc.field_71439_g.field_70163_u - 1.0E-10D), mc.field_71439_g.field_70161_v, true);
/*  62 */           spoof(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.0E-10D, mc.field_71439_g.field_70161_v, false);
/*     */         } 
/*     */       }
/*  65 */       if (this.exploit.getValue() == exploitEn.Strong) {
/*  66 */         for (int i = 0; i < getRuns(); i++) {
/*  67 */           spoof(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.0E-10D, mc.field_71439_g.field_70161_v, false);
/*  68 */           spoof(mc.field_71439_g.field_70165_t, ((Boolean)this.minimize.getValue()).booleanValue() ? mc.field_71439_g.field_70163_u : (mc.field_71439_g.field_70163_u - 1.0E-10D), mc.field_71439_g.field_70161_v, true);
/*     */         } 
/*     */       }
/*  71 */       if (this.exploit.getValue() == exploitEn.Phobos) {
/*  72 */         for (int i = 0; i < getRuns(); i++) {
/*  73 */           spoof(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.3E-13D, mc.field_71439_g.field_70161_v, true);
/*  74 */           spoof(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 2.7E-13D, mc.field_71439_g.field_70161_v, false);
/*     */         } 
/*     */       }
/*  77 */       if (this.exploit.getValue() == exploitEn.Strict) {
/*  78 */         for (int i = 0; i < getRuns(); i++) {
/*  79 */           if (this.rnd.nextBoolean()) {
/*  80 */             spoof(mc.field_71439_g.field_70165_t - strict_direction[0], mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v - strict_direction[1], false);
/*     */           } else {
/*  82 */             spoof(mc.field_71439_g.field_70165_t + strict_direction[0], mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v + strict_direction[1], true);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/*  87 */       delayTimer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spoof(double x, double y, double z, boolean ground) {
/*  92 */     if (((Boolean)this.rotation.getValue()).booleanValue()) {
/*  93 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(x, y, z, mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, ground));
/*     */     } else {
/*  95 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(x, y, z, ground));
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getRuns() {
/* 100 */     if (this.Mode.getValue() == ModeEn.Factorised) {
/* 101 */       return 10 + (int)(((Float)this.factor.getValue()).floatValue() - 1.0F);
/*     */     }
/* 103 */     if (this.Mode.getValue() == ModeEn.Normal) {
/* 104 */       return (int)Math.floor(((Float)this.factor.getValue()).floatValue());
/*     */     }
/* 106 */     if (this.Mode.getValue() == ModeEn.Maximum) {
/* 107 */       return (int)(30.0F * ((Float)this.factor.getValue()).floatValue());
/*     */     }
/* 109 */     return 1;
/*     */   }
/*     */   
/*     */   private enum exploitEn {
/* 113 */     Strong, Fast, Strict, Phobos;
/*     */   }
/*     */   
/*     */   private enum ModeEn {
/* 117 */     Normal, Maximum, Factorised;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\EZbowPOP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */