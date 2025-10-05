/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import org.apache.commons.lang3.math.NumberUtils;
/*    */ 
/*    */ public class EclipCommand extends Command {
/*    */   public EclipCommand() {
/* 21 */     super("eclip");
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getSlotIDFromItem(Item item) {
/* 26 */     int slot = -1;
/* 27 */     for (int i = 0; i < 36; ) {
/* 28 */       ItemStack s = (Minecraft.func_71410_x()).field_71439_g.field_71071_by.func_70301_a(i);
/* 29 */       if (s.func_77973_b() != item) { i++; continue; }
/* 30 */        slot = i;
/*    */     } 
/*    */     
/* 33 */     if (slot < 9 && slot != -1) {
/* 34 */       slot += 36;
/*    */     }
/* 36 */     return slot;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 41 */     if (commands.length == 1) {
/* 42 */       Command.sendMessage(".eclip <value> , /up/down/bedrock");
/*    */ 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 48 */     float y = 0.0F;
/* 49 */     if (commands[0].equals("bedrock")) {
/* 50 */       y = -((float)this.mc.field_71439_g.field_70163_u) - 3.0F;
/*    */     }
/* 52 */     if (commands[0].equals("down")) {
/* 53 */       for (int i = 1; i < 255; ) {
/* 54 */         if (this.mc.field_71441_e.func_180495_p((new BlockPos((Entity)this.mc.field_71439_g)).func_177982_a(0, -i, 0)) == Blocks.field_150350_a.func_176223_P()) {
/* 55 */           y = (-i - 1);
/*    */           break;
/*    */         } 
/* 58 */         if (this.mc.field_71441_e.func_180495_p((new BlockPos((Entity)this.mc.field_71439_g)).func_177982_a(0, -i, 0)) != Blocks.field_150357_h.func_176223_P()) {
/*    */           i++; continue;
/* 60 */         }  Command.sendMessage(ChatFormatting.RED + " можно телепортироваться только под бедрок");
/* 61 */         Command.sendMessage(ChatFormatting.RED + "eclip bedrock");
/*    */         return;
/*    */       } 
/*    */     }
/* 65 */     if (commands[0].equals("up")) {
/* 66 */       for (int i = 4; i < 255; ) {
/* 67 */         if (this.mc.field_71441_e.func_180495_p((new BlockPos((Entity)this.mc.field_71439_g)).func_177982_a(0, i, 0)) != Blocks.field_150350_a.func_176223_P()) {
/*    */           i++; continue;
/* 69 */         }  y = (i + 1);
/*    */       } 
/*    */     }
/*    */     
/* 73 */     if (y == 0.0F)
/* 74 */       if (NumberUtils.isNumber(commands[0])) {
/* 75 */         y = Float.parseFloat(commands[0]);
/*    */       } else {
/* 77 */         Command.sendMessage(ChatFormatting.RED + commands[0] + ChatFormatting.GRAY + "не являестя числом");
/*    */         return;
/*    */       }  
/*    */     int elytra;
/* 81 */     if ((elytra = getSlotIDFromItem(Items.field_185160_cR)) == -1) {
/* 82 */       Command.sendMessage(ChatFormatting.RED + "вам нужны элитры в инвентаре");
/*    */       return;
/*    */     } 
/* 85 */     if (elytra != -2) {
/* 86 */       this.mc.field_71442_b.func_187098_a(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)this.mc.field_71439_g);
/* 87 */       this.mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)this.mc.field_71439_g);
/*    */     } 
/* 89 */     this.mc.func_147114_u().func_147297_a((Packet)new CPacketPlayer.Position(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v, false));
/* 90 */     this.mc.func_147114_u().func_147297_a((Packet)new CPacketPlayer.Position(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v, false));
/* 91 */     this.mc.func_147114_u().func_147297_a((Packet)new CPacketEntityAction((Entity)this.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/* 92 */     this.mc.func_147114_u().func_147297_a((Packet)new CPacketPlayer.Position(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u + y, this.mc.field_71439_g.field_70161_v, false));
/* 93 */     this.mc.func_147114_u().func_147297_a((Packet)new CPacketEntityAction((Entity)this.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/* 94 */     if (elytra != -2) {
/* 95 */       this.mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)this.mc.field_71439_g);
/* 96 */       this.mc.field_71442_b.func_187098_a(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)this.mc.field_71439_g);
/*    */     } 
/* 98 */     this.mc.field_71439_g.func_70107_b(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u + y, this.mc.field_71439_g.field_70161_v);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\EclipCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */