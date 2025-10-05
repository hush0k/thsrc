/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.StopUsingItemEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityPlayerSP;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Quiver extends Module {
/*  21 */   public final Setting<Boolean> speed = register(new Setting("Swiftness", Boolean.valueOf(false)));
/*  22 */   public final Setting<Boolean> strength = register(new Setting("Strength", Boolean.valueOf(false)));
/*  23 */   public final Setting<Boolean> toggelable = register(new Setting("Toggelable", Boolean.valueOf(false)));
/*  24 */   public final Setting<Boolean> autoSwitch = register(new Setting("AutoSwitch", Boolean.valueOf(false)));
/*  25 */   public final Setting<Boolean> rearrange = register(new Setting("Rearrange", Boolean.valueOf(false)));
/*  26 */   public final Setting<Boolean> noGapSwitch = register(new Setting("NoGapSwitch", Boolean.valueOf(false)));
/*  27 */   public final Setting<Integer> health = register(new Setting("MinHealth", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(36)));
/*  28 */   private final Timer timer = new Timer();
/*     */   private boolean cancelStopUsingItem = false;
/*     */   
/*     */   public Quiver() {
/*  32 */     super("Quiver", "Накладывать эффекты-на себя с лука ", Module.Category.COMBAT);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayer(EventSync event) {
/*  37 */     if (mc.field_71439_g == null || mc.field_71441_e == null)
/*     */       return; 
/*  39 */     if (!this.timer.passedMs(2500L))
/*     */       return; 
/*  41 */     if (mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() < ((Integer)this.health.getValue()).intValue())
/*     */       return; 
/*  43 */     if (((Boolean)this.noGapSwitch.getValue()).booleanValue() && mc.field_71439_g.func_184607_cu().func_77973_b() instanceof net.minecraft.item.ItemFood)
/*     */       return; 
/*  45 */     if (((Boolean)this.strength.getValue()).booleanValue() && !mc.field_71439_g.func_70644_a(MobEffects.field_76420_g)) {
/*  46 */       if (isFirstAmmoValid("Стрела силы")) {
/*  47 */         shootBow();
/*     */       }
/*  49 */       if (isFirstAmmoValid("Arrow of Strength")) {
/*  50 */         shootBow();
/*  51 */       } else if (((Boolean)this.toggelable.getValue()).booleanValue()) {
/*  52 */         toggle();
/*     */       } 
/*     */     } 
/*     */     
/*  56 */     if (((Boolean)this.speed.getValue()).booleanValue() && !mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
/*  57 */       if (isFirstAmmoValid("Стрела стремительности")) {
/*  58 */         shootBow();
/*  59 */       } else if (isFirstAmmoValid("Arrow of Swiftness")) {
/*  60 */         shootBow();
/*  61 */       } else if (((Boolean)this.toggelable.getValue()).booleanValue()) {
/*  62 */         toggle();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onStopUsingItem(StopUsingItemEvent event) {
/*  69 */     if (this.cancelStopUsingItem) {
/*  70 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  76 */     this.cancelStopUsingItem = false;
/*     */   }
/*     */   
/*     */   private void shootBow() {
/*  80 */     if (mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() == Items.field_151031_f) {
/*  81 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(0.0F, -90.0F, mc.field_71439_g.field_70122_E));
/*  82 */       ((IEntityPlayerSP)mc.field_71439_g).setLastReportedYaw(0.0F);
/*  83 */       ((IEntityPlayerSP)mc.field_71439_g).setLastReportedPitch(-90.0F);
/*  84 */       if (mc.field_71439_g.func_184612_cw() >= 3) {
/*  85 */         this.cancelStopUsingItem = false;
/*  86 */         mc.field_71442_b.func_78766_c((EntityPlayer)mc.field_71439_g);
/*  87 */         if (((Boolean)this.toggelable.getValue()).booleanValue()) {
/*  88 */           toggle();
/*     */         }
/*  90 */         this.timer.reset();
/*  91 */       } else if (mc.field_71439_g.func_184612_cw() == 0) {
/*  92 */         mc.field_71442_b.func_187101_a((EntityPlayer)mc.field_71439_g, (World)mc.field_71441_e, EnumHand.MAIN_HAND);
/*  93 */         this.cancelStopUsingItem = true;
/*     */       } 
/*  95 */     } else if (((Boolean)this.autoSwitch.getValue()).booleanValue()) {
/*  96 */       int bowSlot = getBowSlot();
/*  97 */       if (bowSlot != -1 && bowSlot != mc.field_71439_g.field_71071_by.field_70461_c) {
/*  98 */         mc.field_71439_g.field_71071_by.field_70461_c = bowSlot;
/*  99 */         mc.field_71442_b.func_78765_e();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getBowSlot() {
/* 105 */     int bowSlot = -1;
/*     */     
/* 107 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151031_f) {
/* 108 */       bowSlot = Module.mc.field_71439_g.field_71071_by.field_70461_c;
/*     */     }
/*     */ 
/*     */     
/* 112 */     if (bowSlot == -1) {
/* 113 */       for (int l = 0; l < 9; l++) {
/* 114 */         if (mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_151031_f) {
/* 115 */           bowSlot = l;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 121 */     return bowSlot;
/*     */   }
/*     */   
/*     */   private boolean isFirstAmmoValid(String type) {
/* 125 */     for (int i = 0; i < 36; i++) {
/* 126 */       ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 127 */       if (itemStack.func_77973_b() == Items.field_185167_i) {
/* 128 */         boolean matches = itemStack.func_82833_r().equalsIgnoreCase(type);
/* 129 */         if (matches)
/* 130 */           return true; 
/* 131 */         if (((Boolean)this.rearrange.getValue()).booleanValue()) {
/* 132 */           return rearrangeArrow(i, type);
/*     */         }
/* 134 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     return false;
/*     */   }
/*     */   
/*     */   private boolean rearrangeArrow(int fakeSlot, String type) {
/* 142 */     for (int i = 0; i < 36; i++) {
/* 143 */       ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 144 */       if (itemStack.func_77973_b() == Items.field_185167_i && 
/* 145 */         itemStack.func_82833_r().equalsIgnoreCase(type)) {
/* 146 */         mc.field_71442_b.func_187098_a(0, fakeSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 147 */         mc.field_71442_b.func_187098_a(0, i, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 148 */         mc.field_71442_b.func_187098_a(0, fakeSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 149 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\Quiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */