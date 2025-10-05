/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class AutoBuff extends Module {
/*  24 */   public Setting<Boolean> strenght = register(new Setting("Strenght", Boolean.valueOf(true)));
/*  25 */   public Setting<Boolean> speed = register(new Setting("Speed", Boolean.valueOf(true)));
/*  26 */   public Setting<Boolean> fire = register(new Setting("FireRes", Boolean.valueOf(true)));
/*  27 */   public Setting<Boolean> heal = register(new Setting("Heal", Boolean.valueOf(true)));
/*  28 */   public Setting<Integer> health = register(new Setting("Health", Integer.valueOf(8), Integer.valueOf(0), Integer.valueOf(20)));
/*  29 */   public Timer timer = new Timer();
/*     */   
/*     */   public AutoBuff() {
/*  32 */     super("AutoBuff", "Кидает бафы", "uses explosive potions", Module.Category.COMBAT);
/*     */   }
/*     */   
/*     */   public static int getPotionSlot(Potions potion) {
/*  36 */     for (int i = 0; i < 9; i++) {
/*  37 */       if (isStackPotion(mc.field_71439_g.field_71071_by.func_70301_a(i), potion)) {
/*  38 */         return i;
/*     */       }
/*     */     } 
/*  41 */     return -1;
/*     */   }
/*     */   
/*     */   public static boolean isPotionOnHotBar(Potions potions) {
/*  45 */     return (getPotionSlot(potions) != -1);
/*     */   }
/*     */   
/*     */   public static boolean isStackPotion(ItemStack stack, Potions potion) {
/*  49 */     if (stack == null) {
/*  50 */       return false;
/*     */     }
/*  52 */     if (stack.func_77973_b() == Items.field_185155_bH) {
/*  53 */       int id = 0;
/*     */       
/*  55 */       switch (potion) {
/*     */         case STRENGTH:
/*  57 */           id = 5;
/*     */           break;
/*     */         
/*     */         case SPEED:
/*  61 */           id = 1;
/*     */           break;
/*     */         
/*     */         case FIRERES:
/*  65 */           id = 12;
/*     */           break;
/*     */         
/*     */         case HEAL:
/*  69 */           id = 6;
/*     */           break;
/*     */       } 
/*     */       
/*  73 */       for (PotionEffect effect : PotionUtils.func_185189_a(stack)) {
/*  74 */         if (effect.func_188419_a() == Potion.func_188412_a(id)) {
/*  75 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*  79 */     return false;
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOWEST)
/*     */   public void onEvent(EventSync event) {
/*  84 */     if (Aura.target != null && mc.field_71439_g.func_184825_o(1.0F) > 0.5F) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  89 */     boolean shouldThrow = ((!mc.field_71439_g.func_70644_a(MobEffects.field_76424_c) && isPotionOnHotBar(Potions.SPEED) && ((Boolean)this.speed.getValue()).booleanValue()) || (!mc.field_71439_g.func_70644_a(MobEffects.field_76420_g) && isPotionOnHotBar(Potions.STRENGTH) && ((Boolean)this.strenght.getValue()).booleanValue()) || (!mc.field_71439_g.func_70644_a(MobEffects.field_76426_n) && isPotionOnHotBar(Potions.FIRERES) && ((Boolean)this.fire.getValue()).booleanValue()) || (EntityUtil.getHealth((Entity)mc.field_71439_g) < ((Integer)this.health.getValue()).intValue() && isPotionOnHotBar(Potions.HEAL) && ((Boolean)this.heal.getValue()).booleanValue()));
/*  90 */     if (mc.field_71439_g.field_70173_aa > 80 && shouldThrow) {
/*  91 */       mc.field_71439_g.field_70125_A = 90.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPostMotion(EventPostSync e) {
/*  97 */     if (Aura.target != null && mc.field_71439_g.func_184825_o(1.0F) > 0.5F)
/*     */       return; 
/*  99 */     e.addPostEvent(() -> {
/* 100 */           boolean shouldThrow = ((!mc.field_71439_g.func_70644_a(MobEffects.field_76424_c) && isPotionOnHotBar(Potions.SPEED) && ((Boolean)this.speed.getValue()).booleanValue()) || (!mc.field_71439_g.func_70644_a(MobEffects.field_76420_g) && isPotionOnHotBar(Potions.STRENGTH) && ((Boolean)this.strenght.getValue()).booleanValue()) || (!mc.field_71439_g.func_70644_a(MobEffects.field_76426_n) && isPotionOnHotBar(Potions.FIRERES) && ((Boolean)this.fire.getValue()).booleanValue()) || (EntityUtil.getHealth((Entity)mc.field_71439_g) < ((Integer)this.health.getValue()).intValue() && isPotionOnHotBar(Potions.HEAL) && ((Boolean)this.heal.getValue()).booleanValue()));
/*     */           if (mc.field_71439_g.field_70173_aa > 80 && shouldThrow && this.timer.passedMs(1000L)) {
/*     */             if (!mc.field_71439_g.func_70644_a(MobEffects.field_76424_c) && isPotionOnHotBar(Potions.SPEED) && ((Boolean)this.speed.getValue()).booleanValue()) {
/*     */               throwPotion(Potions.SPEED);
/*     */             }
/*     */             if (!mc.field_71439_g.func_70644_a(MobEffects.field_76420_g) && isPotionOnHotBar(Potions.STRENGTH) && ((Boolean)this.strenght.getValue()).booleanValue()) {
/*     */               throwPotion(Potions.STRENGTH);
/*     */             }
/*     */             if (!mc.field_71439_g.func_70644_a(MobEffects.field_76426_n) && isPotionOnHotBar(Potions.FIRERES) && ((Boolean)this.fire.getValue()).booleanValue()) {
/*     */               throwPotion(Potions.FIRERES);
/*     */             }
/*     */             if (EntityUtil.getHealth((Entity)mc.field_71439_g) < ((Integer)this.health.getValue()).intValue() && ((Boolean)this.heal.getValue()).booleanValue() && isPotionOnHotBar(Potions.HEAL)) {
/*     */               throwPotion(Potions.HEAL);
/*     */             }
/*     */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
/*     */             this.timer.reset();
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void throwPotion(Potions potion) {
/* 125 */     int slot = getPotionSlot(potion);
/* 126 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/* 127 */     mc.field_71442_b.func_78765_e();
/* 128 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 129 */     mc.field_71442_b.func_78765_e();
/*     */   }
/*     */   
/*     */   public enum Potions {
/* 133 */     STRENGTH, SPEED, FIRERES, HEAL;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\AutoBuff.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */