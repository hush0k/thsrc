/*    */ package com.mrzak34.thunderhack.modules.combat;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.EventSync;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class BowSpam extends Module {
/* 17 */   private final Timer timer = new Timer();
/* 18 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.FAST));
/* 19 */   public Setting<Boolean> allowOffhand = register(new Setting("Offhand", Boolean.TRUE, v -> (this.mode.getValue() != Mode.AUTORELEASE)));
/* 20 */   public Setting<Integer> ticks = register(new Setting("Ticks", Integer.valueOf(3), Integer.valueOf(0), Integer.valueOf(20), v -> (this.mode.getValue() == Mode.FAST)));
/* 21 */   public Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(500), v -> (this.mode.getValue() == Mode.AUTORELEASE)));
/*    */   
/*    */   private boolean offhand = false;
/*    */   
/*    */   public BowSpam() {
/* 26 */     super("BowSpam", "Спамит стрелами", Module.Category.COMBAT);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPlayerPre(EventSync event) {
/* 32 */     if (this.mode.getValue() == Mode.FAST && (this.offhand || mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof net.minecraft.item.ItemBow) && mc.field_71439_g.func_184587_cr() && 
/* 33 */       mc.field_71439_g.func_184612_cw() >= ((Integer)this.ticks.getValue()).intValue()) {
/* 34 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, mc.field_71439_g.func_174811_aO()));
/* 35 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(this.offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND));
/* 36 */       mc.field_71439_g.func_184597_cx();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 43 */     this.offhand = (mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151031_f && ((Boolean)this.allowOffhand.getValue()).booleanValue());
/* 44 */     if (this.mode.getValue() == Mode.AUTORELEASE) {
/* 45 */       if ((!this.offhand && !(mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof net.minecraft.item.ItemBow)) || !this.timer.passedMs((int)((Integer)this.delay.getValue()).intValue()))
/*    */         return; 
/* 47 */       mc.field_71442_b.func_78766_c((EntityPlayer)mc.field_71439_g);
/* 48 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */   
/*    */   public enum Mode {
/* 53 */     FAST,
/* 54 */     AUTORELEASE;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\BowSpam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */