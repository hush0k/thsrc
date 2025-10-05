/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.modules.combat.Aura;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ 
/*    */ public class EffectsRemover extends Module {
/* 21 */   public static int nig = 0;
/*    */   public static boolean jboost = false;
/* 23 */   public Setting<Boolean> jumpBoost = register(new Setting("JumpBoostRemove", Boolean.valueOf(false)));
/* 24 */   public Setting<Boolean> oldr = register(new Setting("OldRemove", Boolean.valueOf(false)));
/* 25 */   public Timer timer = new Timer();
/*    */   public EffectsRemover() {
/* 27 */     super("PowderTweaks", "Убирает джампбуст от-пороха и юзает его автоматом", Module.Category.FUNNYGAME);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 32 */     nig--;
/*    */     
/* 34 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/*    */     
/* 38 */     if (this.timer.passedMs(500L) && !mc.field_71439_g.func_70644_a(Objects.<Potion>requireNonNull(Potion.func_180142_b("strength"))) && mc.field_71476_x != null && mc.field_71476_x.field_72313_a != RayTraceResult.Type.BLOCK) {
/* 39 */       int hotbarslot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 40 */       ItemStack itemStack = Util.mc.field_71439_g.field_71071_by.func_70301_a(InventoryUtil.getPowderAtHotbar());
/* 41 */       if (!itemStack.func_77973_b().func_77653_i(itemStack).equals("Порох"))
/* 42 */         return;  mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(InventoryUtil.getPowderAtHotbar()));
/* 43 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 44 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(hotbarslot));
/* 45 */       this.timer.reset();
/*    */     } 
/*    */     
/* 48 */     if (((Boolean)this.jumpBoost.getValue()).booleanValue() && (
/* 49 */       (Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).isEnabled() && 
/* 50 */       mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
/* 51 */       nig = ((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.field_71439_g.func_70660_b(MobEffects.field_76430_j))).func_76459_b();
/* 52 */       mc.field_71439_g.func_184596_c(Potion.func_180142_b("jump_boost"));
/* 53 */       jboost = true;
/*    */     } 
/*    */ 
/*    */     
/* 57 */     if (((Boolean)this.oldr.getValue()).booleanValue() && 
/* 58 */       mc.field_71439_g.func_70644_a(MobEffects.field_76430_j))
/* 59 */       mc.field_71439_g.func_184596_c(Potion.func_180142_b("jump_boost")); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\EffectsRemover.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */