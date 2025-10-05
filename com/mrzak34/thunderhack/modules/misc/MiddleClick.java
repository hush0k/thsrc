/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.ClickMiddleEvent;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IMinecraft;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class MiddleClick extends Module {
/*  24 */   public Setting<Boolean> fm = register(new Setting("FriendMessage", Boolean.valueOf(true)));
/*  25 */   public Setting<Boolean> friend = register(new Setting("Friend", Boolean.valueOf(true)));
/*  26 */   public Setting<Boolean> rocket = register(new Setting("Rocket", Boolean.valueOf(false)));
/*  27 */   public Setting<Boolean> ep = register(new Setting("Pearl", Boolean.valueOf(true)));
/*  28 */   public Setting<Boolean> silentPearl = register(new Setting("SilentPearl", Boolean.valueOf(true), v -> ((Boolean)this.ep.getValue()).booleanValue()));
/*  29 */   public Setting<Integer> swapDelay = register(new Setting("SwapDelay", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(1000)));
/*  30 */   public Setting<Boolean> xp = register(new Setting("XP", Boolean.valueOf(false)));
/*  31 */   public Setting<Boolean> feetExp = register(new Setting("FeetXP", Boolean.valueOf(false)));
/*  32 */   public Setting<Boolean> silent = register(new Setting("SilentXP", Boolean.valueOf(true)));
/*  33 */   public Setting<Boolean> whileEating = register(new Setting("WhileEating", Boolean.valueOf(true)));
/*  34 */   public Setting<Boolean> pickBlock = register(new Setting("CancelMC", Boolean.valueOf(true)));
/*  35 */   public Timer timr = new Timer();
/*  36 */   private int lastSlot = -1;
/*     */ 
/*     */   
/*     */   public MiddleClick() {
/*  40 */     super("MiddleClick", "действия на колесико-мыши", Module.Category.MISC);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPreMotion(EventSync event) {
/*  45 */     if (mc.field_71439_g == null || mc.field_71441_e == null)
/*     */       return; 
/*  47 */     if (((Boolean)this.feetExp.getValue()).booleanValue() && Mouse.isButtonDown(2)) {
/*  48 */       if (!((Boolean)this.xp.getValue()).booleanValue())
/*  49 */         return;  mc.field_71439_g.field_70125_A = 90.0F;
/*     */     } 
/*     */     
/*  52 */     if (((Boolean)this.friend.getValue()).booleanValue() && mc.field_71476_x != null && mc.field_71476_x.field_72308_g != null) {
/*  53 */       if (!Mouse.isButtonDown(2))
/*  54 */         return;  Entity entity = mc.field_71476_x.field_72308_g;
/*  55 */       if (entity instanceof EntityPlayer && this.timr.passedMs(2500L)) {
/*  56 */         if (Thunderhack.friendManager.isFriend(entity.func_70005_c_())) {
/*  57 */           Thunderhack.friendManager.removeFriend(entity.func_70005_c_());
/*  58 */           Command.sendMessage("Removed §b" + entity.func_70005_c_() + "§r as a friend!");
/*     */         } else {
/*  60 */           Thunderhack.friendManager.addFriend(entity.func_70005_c_());
/*  61 */           if (((Boolean)this.fm.getValue()).booleanValue()) {
/*  62 */             mc.field_71439_g.func_71165_d("/w " + entity.func_70005_c_() + " i friended u at ThunderHackPlus");
/*     */           }
/*  64 */           Command.sendMessage("Added §b" + entity.func_70005_c_() + "§r as a friend!");
/*     */         } 
/*  66 */         this.timr.reset();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  71 */     if (((Boolean)this.rocket.getValue()).booleanValue() && findRocketSlot() != -1 && this.timr.passedMs(500L)) {
/*  72 */       if (!Mouse.isButtonDown(2))
/*  73 */         return;  int rocketSlot = findRocketSlot();
/*  74 */       int originalSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */       
/*  76 */       if (rocketSlot != -1) {
/*  77 */         mc.field_71439_g.field_71071_by.field_70461_c = rocketSlot;
/*  78 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(rocketSlot));
/*     */         
/*  80 */         mc.field_71442_b.func_187101_a((EntityPlayer)mc.field_71439_g, (World)mc.field_71441_e, EnumHand.MAIN_HAND);
/*     */         
/*  82 */         mc.field_71439_g.field_71071_by.field_70461_c = originalSlot;
/*  83 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(originalSlot));
/*  84 */         this.timr.reset();
/*     */         return;
/*     */       } 
/*     */     } 
/*  88 */     if (((Boolean)this.ep.getValue()).booleanValue() && this.timr.passedMs(500L) && mc.field_71462_r == null) {
/*  89 */       if (!Mouse.isButtonDown(2))
/*  90 */         return;  if (((Boolean)this.silentPearl.getValue()).booleanValue()) {
/*  91 */         int epSlot = findEPSlot();
/*  92 */         int originalSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*  93 */         if (epSlot != -1) {
/*  94 */           mc.field_71439_g.field_71071_by.field_70461_c = epSlot;
/*  95 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(epSlot));
/*  96 */           ((IMinecraft)mc).invokeRightClick();
/*  97 */           mc.field_71439_g.field_71071_by.field_70461_c = originalSlot;
/*  98 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(originalSlot));
/*     */         } 
/*     */       } else {
/* 101 */         int epSlot = findEPSlot();
/* 102 */         int originalSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 103 */         if (epSlot != -1) {
/* 104 */           (new PearlThread(mc.field_71439_g, epSlot, originalSlot, ((Integer)this.swapDelay.getValue()).intValue())).start();
/*     */         }
/*     */       } 
/* 107 */       this.timr.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPostMotion(EventPostSync event) {
/* 115 */     if (((Boolean)this.xp.getValue()).booleanValue()) {
/* 116 */       if (Mouse.isButtonDown(2) && (((Boolean)this.whileEating.getValue()).booleanValue() || !(mc.field_71439_g.func_184607_cu().func_77973_b() instanceof net.minecraft.item.ItemFood))) {
/* 117 */         int slot = InventoryUtil.findItemAtHotbar(Items.field_151062_by);
/* 118 */         if (slot != -1) {
/* 119 */           int lastSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 120 */           InventoryUtil.switchTo(slot);
/* 121 */           mc.field_71442_b.func_187101_a((EntityPlayer)mc.field_71439_g, (World)mc.field_71441_e, InventoryUtil.getHand(slot));
/*     */           
/* 123 */           if (((Boolean)this.silent.getValue()).booleanValue()) {
/* 124 */             InventoryUtil.switchTo(lastSlot);
/*     */           }
/* 126 */         } else if (this.lastSlot != -1) {
/* 127 */           InventoryUtil.switchTo(this.lastSlot);
/* 128 */           this.lastSlot = -1;
/*     */         } 
/* 130 */       } else if (this.lastSlot != -1) {
/* 131 */         InventoryUtil.switchTo(this.lastSlot);
/* 132 */         this.lastSlot = -1;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private int findRocketSlot() {
/* 138 */     int rocketSlot = -1;
/*     */     
/* 140 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151152_bP) {
/* 141 */       rocketSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */     }
/*     */ 
/*     */     
/* 145 */     if (rocketSlot == -1) {
/* 146 */       for (int l = 0; l < 9; l++) {
/* 147 */         if (mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_151152_bP) {
/* 148 */           rocketSlot = l;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 154 */     return rocketSlot;
/*     */   }
/*     */   
/*     */   private int findEPSlot() {
/* 158 */     int epSlot = -1;
/*     */     
/* 160 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151079_bi) {
/* 161 */       epSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */     }
/*     */ 
/*     */     
/* 165 */     if (epSlot == -1) {
/* 166 */       for (int l = 0; l < 9; l++) {
/* 167 */         if (mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_151079_bi) {
/* 168 */           epSlot = l;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 174 */     return epSlot;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMiddleClick(ClickMiddleEvent event) {
/* 179 */     if (!((Boolean)this.xp.getValue()).booleanValue())
/* 180 */       return;  if (((Boolean)this.pickBlock.getValue()).booleanValue()) {
/* 181 */       int slot = InventoryUtil.findItemAtHotbar(Items.field_151062_by);
/* 182 */       if (slot != -1 && slot != -2 && slot != mc.field_71439_g.field_71071_by.field_70461_c)
/* 183 */         event.setCanceled(true); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public class PearlThread extends Thread { public EntityPlayerSP player;
/*     */     int epSlot;
/*     */     int originalSlot;
/*     */     int delay;
/*     */     
/*     */     public PearlThread(EntityPlayerSP entityPlayerSP, int epSlot, int originalSlot, int delay) {
/* 193 */       this.player = entityPlayerSP;
/* 194 */       this.epSlot = epSlot;
/* 195 */       this.originalSlot = originalSlot;
/* 196 */       this.delay = delay;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 201 */       Module.mc.field_71439_g.field_71071_by.field_70461_c = this.epSlot;
/* 202 */       Module.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.epSlot)); 
/* 203 */       try { sleep(this.delay); } catch (Exception exception) {}
/* 204 */       ((IMinecraft)Module.mc).invokeRightClick(); 
/* 205 */       try { sleep(this.delay); } catch (Exception exception) {}
/* 206 */       Module.mc.field_71439_g.field_71071_by.field_70461_c = this.originalSlot;
/* 207 */       Module.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.originalSlot));
/* 208 */       super.run();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\MiddleClick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */