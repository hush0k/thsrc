/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketPlayer;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.modules.combat.BackTrack;
/*    */ import com.mrzak34.thunderhack.notification.NotificationManager;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class TeleportBack extends Module {
/*    */   private final Setting<Integer> reset;
/*    */   
/*    */   public TeleportBack() {
/* 22 */     super("TeleportBack", "включил отошел прыгнул-тепнуло туда где включал", "Matrix only", Module.Category.PLAYER);
/*    */     
/* 24 */     this.reset = register(new Setting("ResetDistance", Integer.valueOf(30), Integer.valueOf(1), Integer.valueOf(256)));
/* 25 */     this.color = register(new Setting("Color", new ColorSetting(-2009289807)));
/*    */   }
/*    */   private final Setting<ColorSetting> color; BackTrack.Box prev_pos;
/*    */   @SubscribeEvent
/*    */   public void onSync(EventSync event) {
/* 30 */     if (fullNullCheck())
/* 31 */       return;  mc.field_71439_g.func_70031_b(false);
/* 32 */     if (mc.field_71474_y.field_74314_A.func_151470_d()) {
/* 33 */       mc.field_71439_g.field_70181_x = 0.41999998688697815D;
/*    */     }
/* 35 */     if (this.prev_pos != null && mc.field_71439_g.func_70092_e((this.prev_pos.getPosition()).field_72450_a, (this.prev_pos.getPosition()).field_72448_b, (this.prev_pos.getPosition()).field_72449_c) > (((Integer)this.reset.getValue()).intValue() * ((Integer)this.reset.getValue()).intValue())) {
/* 36 */       NotificationManager.publicity("TeleportBack Ты отошел слишком далеко! сбрасываю позицию...", 5, Notification.Type.ERROR);
/* 37 */       Command.sendMessage(ChatFormatting.RED + "TeleportBack Ты отошел слишком далеко! сбрасываю позицию...");
/* 38 */       this.prev_pos = new BackTrack.Box(mc.field_71439_g.func_174791_d(), 20, mc.field_71439_g.field_184619_aG, mc.field_71439_g.field_70721_aZ, mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, (EntityPlayer)mc.field_71439_g);
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 44 */     if (event.getPacket() instanceof CPacketPlayer) {
/* 45 */       CPacketPlayer player = (CPacketPlayer)event.getPacket();
/* 46 */       ((ICPacketPlayer)player).setOnGround(false);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 52 */     this.prev_pos = new BackTrack.Box(mc.field_71439_g.func_174791_d(), 20, mc.field_71439_g.field_184619_aG, mc.field_71439_g.field_70721_aZ, mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, (EntityPlayer)mc.field_71439_g);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPreRenderEvent(PreRenderEvent event) {
/* 57 */     if (this.prev_pos == null)
/* 58 */       return;  GlStateManager.func_179094_E();
/* 59 */     RenderUtil.renderEntity(this.prev_pos, (ModelBase)this.prev_pos.getModelPlayer(), this.prev_pos.getLimbSwing(), this.prev_pos.getLimbSwingAmount(), this.prev_pos.getYaw(), this.prev_pos.getPitch(), (EntityLivingBase)this.prev_pos.getEnt(), ((ColorSetting)this.color.getValue()).getColorObject());
/* 60 */     GlStateManager.func_179121_F();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\TeleportBack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */