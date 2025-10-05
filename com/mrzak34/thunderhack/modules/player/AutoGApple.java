/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.events.FinishUseItemEvent;
/*    */ import com.mrzak34.thunderhack.events.PlayerUpdateEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.IKeyBinding;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.EntityUtil;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AutoGApple
/*    */   extends Module {
/*    */   public static boolean stopAura = false;
/* 17 */   public final Setting<Boolean> fg = register(new Setting("FunnyGame", Boolean.valueOf(false)));
/* 18 */   public final Setting<Integer> Delay = register(new Setting("UseDelay", Integer.valueOf(200), Integer.valueOf(0), Integer.valueOf(2000)));
/* 19 */   private final Setting<Float> health = register(new Setting("health", Float.valueOf(20.0F), Float.valueOf(1.0F), Float.valueOf(36.0F)));
/*    */   private boolean isActive;
/* 21 */   private int antiLag = 0;
/* 22 */   private final Timer useDelay = new Timer();
/*    */   
/*    */   public AutoGApple() {
/* 25 */     super("AutoGApple", "AutoGApple", Module.Category.PLAYER);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onUpdate(PlayerUpdateEvent e) {
/* 30 */     if (fullNullCheck())
/* 31 */       return;  if (GapInOffHand()) {
/* 32 */       stopAura = false;
/* 33 */       if (EntityUtil.getHealth((Entity)mc.field_71439_g) <= ((Float)this.health.getValue()).floatValue() && this.useDelay.passedMs(((Integer)this.Delay.getValue()).intValue())) {
/* 34 */         stopAura = true;
/* 35 */         this.isActive = true;
/* 36 */         ((IKeyBinding)mc.field_71474_y.field_74313_G).setPressed(true);
/* 37 */       } else if (this.isActive) {
/* 38 */         stopAura = false;
/* 39 */         this.isActive = false;
/* 40 */         ((IKeyBinding)mc.field_71474_y.field_74313_G).setPressed(false);
/* 41 */         this.antiLag = 0;
/*    */       } else {
/* 43 */         stopAura = false;
/*    */       } 
/* 45 */       if (((IKeyBinding)mc.field_71474_y.field_74313_G).isPressed() && ((Boolean)this.fg.getValue()).booleanValue()) {
/* 46 */         this.antiLag++;
/* 47 */         if (this.antiLag > 50) {
/* 48 */           Command.sendMessage("AntiGapLAG");
/* 49 */           ((IKeyBinding)mc.field_71474_y.field_74313_G).setPressed(false);
/* 50 */           this.antiLag = 0;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onFinishEating(FinishUseItemEvent e) {
/* 58 */     this.useDelay.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 63 */     stopAura = false;
/*    */   }
/*    */   
/*    */   private boolean GapInOffHand() {
/* 67 */     return (!mc.field_71439_g.func_184592_cb().func_190926_b() && mc.field_71439_g.func_184592_cb().func_77973_b() instanceof net.minecraft.item.ItemAppleGold);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\AutoGApple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */