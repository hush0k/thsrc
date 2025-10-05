/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.EntityAddedEvent;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemFishingRod;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*     */ import net.minecraft.network.play.server.SPacketDisconnect;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class AutoFish extends Module {
/*  23 */   public Setting<Boolean> rodSave = register(new Setting("RodSave", Boolean.valueOf(true)));
/*  24 */   public Setting<Boolean> changeRod = register(new Setting("ChangeRod", Boolean.valueOf(false)));
/*  25 */   public Setting<Boolean> autoSell = register(new Setting("AutoSell", Boolean.valueOf(false)));
/*  26 */   public Setting<Boolean> autoLeave = register(new Setting("AutoLeave", Boolean.valueOf(false)));
/*  27 */   private int rodSlot = -1;
/*  28 */   private final Timer timeout = new Timer();
/*     */   public AutoFish() {
/*  30 */     super("AutoFish", "признайся захотел", Module.Category.MISC);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  35 */     if (fullNullCheck()) {
/*  36 */       toggle();
/*     */       return;
/*     */     } 
/*  39 */     this.rodSlot = InventoryUtil.findItem(ItemFishingRod.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  44 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemFishingRod && 
/*  45 */       mc.field_71439_g.func_184614_ca().func_77952_i() > 52) {
/*  46 */       if (((Boolean)this.rodSave.getValue()).booleanValue() && !((Boolean)this.changeRod.getValue()).booleanValue()) {
/*  47 */         Command.sendMessage("Saving rod...");
/*  48 */         toggle();
/*  49 */       } else if (((Boolean)this.changeRod.getValue()).booleanValue() && InventoryUtil.getRodSlot() != -1) {
/*  50 */         Command.sendMessage("Swapped to a new rod");
/*  51 */         mc.field_71439_g.field_71071_by.field_70461_c = InventoryUtil.getRodSlot();
/*     */       } else {
/*  53 */         Command.sendMessage("Saving rod...");
/*  54 */         toggle();
/*     */       } 
/*     */     }
/*     */     
/*  58 */     if (this.timeout.passedMs(60000L)) {
/*  59 */       if (this.rodSlot == -1)
/*  60 */         this.rodSlot = InventoryUtil.findItem(ItemFishingRod.class); 
/*  61 */       if (this.rodSlot != -1) {
/*  62 */         int startSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*  63 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.rodSlot));
/*  64 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/*  65 */         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*  66 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/*  67 */         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*  68 */         if (startSlot != -1)
/*  69 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(startSlot)); 
/*  70 */         this.timeout.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityAdded(EntityAddedEvent event) {
/*  78 */     if (((Boolean)this.autoLeave.getValue()).booleanValue() && !Thunderhack.friendManager.isFriend(event.entity.func_70005_c_())) {
/*  79 */       toggle();
/*  80 */       mc.field_71439_g.field_71174_a.func_147253_a(new SPacketDisconnect((ITextComponent)new TextComponentString("AutoFish (log)")));
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/*  86 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*  89 */     if (event.getPacket() instanceof SPacketSoundEffect) {
/*  90 */       SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
/*  91 */       if (packet.func_186977_b() == SoundCategory.NEUTRAL && packet.func_186978_a() == SoundEvents.field_187609_F) {
/*  92 */         if (this.rodSlot == -1)
/*  93 */           this.rodSlot = InventoryUtil.findItem(ItemFishingRod.class); 
/*  94 */         if (this.rodSlot != -1) {
/*  95 */           int startSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*  96 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.rodSlot));
/*  97 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/*  98 */           mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*  99 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 100 */           mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 101 */           if (startSlot != -1)
/* 102 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(startSlot)); 
/* 103 */           if (((Boolean)this.autoSell.getValue()).booleanValue() && 
/* 104 */             this.timeout.passedMs(1000L)) {
/* 105 */             mc.field_71439_g.func_71165_d("/sellfish");
/*     */           }
/*     */           
/* 108 */           this.timeout.reset();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\AutoFish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */