/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.util.SpecialTagCompound;
/*     */ import com.mrzak34.thunderhack.util.ffp.PacketListener;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.EnumPacketDirection;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.server.SPacketEntityEquipment;
/*     */ import net.minecraft.network.play.server.SPacketSetSlot;
/*     */ import net.minecraft.network.play.server.SPacketWindowItems;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class TrueDurability
/*     */   extends Module implements PacketListener {
/*     */   public TrueDurability() {
/*  25 */     super("TrueDurability", "реальная прочность-нелегальных предметов", Module.Category.PLAYER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  30 */     if (mc.field_71441_e != null && mc.field_71439_g != null) {
/*  31 */       Thunderhack.networkHandler.registerListener(EnumPacketDirection.CLIENTBOUND, this, new int[] { 20, 22, 63 });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  37 */     Thunderhack.networkHandler.registerListener(EnumPacketDirection.CLIENTBOUND, this, new int[] { 20, 22, 63 });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  42 */     Thunderhack.networkHandler.unregisterListener(EnumPacketDirection.CLIENTBOUND, this, new int[] { 20, 22, 63 });
/*     */   }
/*     */   @SubscribeEvent
/*     */   public void itemToolTip(ItemTooltipEvent event) { int damage;
/*     */     TextFormatting color;
/*  47 */     ItemStack stack = event.getItemStack();
/*  48 */     int max = stack.func_77958_k();
/*     */     
/*  50 */     if (stack.func_190926_b() || max <= 0)
/*  51 */       return;  if (stack.func_77942_o()) {
/*  52 */       assert stack.func_77978_p() != null;
/*  53 */       if (stack.func_77978_p().func_74767_n("Unbreakable"))
/*     */         return; 
/*     */     } 
/*  56 */     List<String> toolTip = event.getToolTip();
/*     */ 
/*     */     
/*  59 */     NBTTagCompound tag = stack.func_77978_p();
/*  60 */     if (tag instanceof SpecialTagCompound) {
/*  61 */       damage = ((SpecialTagCompound)tag).getTrueDamage();
/*     */     } else {
/*  63 */       damage = stack.func_77952_i();
/*     */     } 
/*  65 */     long count = max - damage;
/*     */ 
/*     */     
/*  68 */     if (damage < 0) { color = TextFormatting.DARK_PURPLE; }
/*  69 */     else if (damage > max) { color = TextFormatting.DARK_RED; }
/*  70 */     else { color = TextFormatting.BLUE; }
/*     */     
/*  72 */     toolTip.add("");
/*  73 */     toolTip.add(color + "Durability: " + count + " [Max: " + Long.toString(max) + "]" + TextFormatting.RESET); } public Packet<?> packetReceived(EnumPacketDirection direction, int id, Packet<?> packet, ByteBuf in) {
/*     */     SPacketWindowItems packet_window;
/*     */     SPacketSetSlot packet_slot;
/*     */     SPacketEntityEquipment equipment;
/*     */     PacketBuffer buf;
/*  78 */     switch (id) {
/*     */       
/*     */       case 20:
/*  81 */         packet_window = (SPacketWindowItems)packet;
/*  82 */         buf = new PacketBuffer(in);
/*  83 */         buf.readerIndex(buf.readerIndex() + 4);
/*  84 */         for (ItemStack i : packet_window.func_148910_d()) {
/*  85 */           if (buf.readShort() >= 0) {
/*  86 */             buf.readerIndex(buf.readerIndex() + 1);
/*  87 */             short true_damage = buf.readShort();
/*     */             try {
/*  89 */               if (true_damage < 0) {
/*  90 */                 i.func_77982_d((NBTTagCompound)new SpecialTagCompound(buf.func_150793_b(), true_damage)); continue;
/*  91 */               }  buf.func_150793_b();
/*  92 */             } catch (IOException e) {
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 22:
/* 101 */         packet_slot = (SPacketSetSlot)packet;
/* 102 */         buf = new PacketBuffer(in);
/* 103 */         buf.readerIndex(buf.readerIndex() + 4);
/* 104 */         if (buf.readShort() >= 0) {
/* 105 */           buf.readerIndex(buf.readerIndex() + 1);
/* 106 */           short real_damage = buf.readShort();
/* 107 */           if (real_damage < 0) {
/* 108 */             ItemStack stack = packet_slot.func_149174_e();
/* 109 */             stack.func_77982_d((NBTTagCompound)new SpecialTagCompound(stack.func_77978_p(), real_damage));
/*     */           } 
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 63:
/* 116 */         equipment = (SPacketEntityEquipment)packet;
/* 117 */         buf = new PacketBuffer(in);
/* 118 */         buf.readerIndex(buf.readerIndex() + 3 + (int)Math.floor(Math.log(equipment.func_149389_d()) / Math.log(128.0D)));
/* 119 */         if (buf.readShort() >= 0) {
/* 120 */           buf.readerIndex(buf.readerIndex() + 1);
/* 121 */           short real_damage = buf.readShort();
/* 122 */           if (real_damage < 0) {
/* 123 */             ItemStack stack = equipment.func_149390_c();
/* 124 */             stack.func_77982_d((NBTTagCompound)new SpecialTagCompound(stack.func_77978_p(), real_damage));
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/*     */     
/* 130 */     return packet;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\TrueDurability.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */