/*     */ package com.mrzak34.thunderhack.modules.funnygame;
/*     */ 
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class AutoPot
/*     */   extends Module
/*     */ {
/*  27 */   public static int neededCap = 0;
/*  28 */   private final Setting<Mode> mainMode = register(new Setting("Version", Mode.New));
/*  29 */   public enum Mode { Old, New; }
/*  30 */   public Setting<Integer> triggerhealth = register(new Setting("TriggerHealth", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(36)));
/*  31 */   public Setting<Integer> delay = register(new Setting("delay", Integer.valueOf(200), Integer.valueOf(1), Integer.valueOf(2000)));
/*  32 */   public Setting<Boolean> animation = register(new Setting("Animation", Boolean.valueOf(true)));
/*  33 */   public Timer timer = new Timer();
/*  34 */   public Timer alerttimer = new Timer();
/*     */   private int itemActivationTicks;
/*     */   private float itemActivationOffX;
/*     */   private float itemActivationOffY;
/*     */   private ItemStack itemActivationItem;
/*  39 */   private final Random random = new Random();
/*     */ 
/*     */   
/*     */   public AutoPot() {
/*  43 */     super("AutoCappuccino", "автокаппучино для-фангейма", Module.Category.FUNNYGAME);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  48 */     if (mc.field_71439_g.func_110143_aJ() < ((Integer)this.triggerhealth.getValue()).intValue() && this.timer.passedMs(((Integer)this.delay.getValue()).intValue()) && InventoryUtil.getCappuchinoAtHotbar((this.mainMode.getValue() == Mode.Old)) != -1) {
/*  49 */       this.itemActivationItem = InventoryUtil.getPotionItemStack((this.mainMode.getValue() == Mode.Old));
/*  50 */       int hotbarslot = mc.field_71439_g.field_71071_by.field_70461_c;
/*  51 */       mc.field_71441_e.func_184156_a(PlayerUtils.getPlayerPos(), SoundEvents.field_187621_J, SoundCategory.AMBIENT, 150.0F, 1.0F, true);
/*  52 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(InventoryUtil.getCappuchinoAtHotbar((this.mainMode.getValue() == Mode.Old))));
/*  53 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/*  54 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(hotbarslot));
/*  55 */       neededCap++;
/*  56 */       aboba();
/*  57 */       this.timer.reset();
/*     */     } 
/*  59 */     if (InventoryUtil.getCappuchinoAtHotbar((this.mainMode.getValue() == Mode.Old)) == -1 && this.alerttimer.passedMs(1000L)) {
/*  60 */       Command.sendMessage("Нема зелек!!!!");
/*  61 */       mc.field_71441_e.func_184156_a(PlayerUtils.getPlayerPos(), SoundEvents.field_187603_D, SoundCategory.AMBIENT, 150.0F, 10.0F, true);
/*  62 */       this.alerttimer.reset();
/*     */     } 
/*  64 */     if (this.itemActivationTicks > 0) {
/*  65 */       this.itemActivationTicks--;
/*  66 */       if (this.itemActivationTicks == 0) {
/*  67 */         this.itemActivationItem = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/*  74 */     if (((Boolean)this.animation.getValue()).booleanValue())
/*  75 */       renderItemActivation(e.scaledResolution.func_78326_a(), e.scaledResolution.func_78328_b(), mc.func_184121_ak()); 
/*     */   }
/*     */   
/*     */   public void aboba() {
/*  79 */     this.itemActivationTicks = 40;
/*  80 */     this.itemActivationOffX = this.random.nextFloat() * 2.0F - 1.0F;
/*  81 */     this.itemActivationOffY = this.random.nextFloat() * 2.0F - 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderItemActivation(int p_190563_1_, int p_190563_2_, float p_190563_3_) {
/*  87 */     if (this.itemActivationItem != null && this.itemActivationTicks > 0) {
/*  88 */       int i = 40 - this.itemActivationTicks;
/*  89 */       float f = (i + p_190563_3_) / 40.0F;
/*  90 */       float f1 = f * f;
/*  91 */       float f2 = f * f1;
/*  92 */       float f3 = 10.25F * f2 * f1 + -24.95F * f1 * f1 + 25.5F * f2 + -13.8F * f1 + 4.0F * f;
/*  93 */       float f4 = f3 * 3.1415927F;
/*  94 */       float f5 = this.itemActivationOffX * (p_190563_1_ / 4);
/*  95 */       float f6 = this.itemActivationOffY * (p_190563_2_ / 4);
/*  96 */       GlStateManager.func_179141_d();
/*  97 */       GlStateManager.func_179094_E();
/*  98 */       GlStateManager.func_179123_a();
/*  99 */       GlStateManager.func_179126_j();
/* 100 */       GlStateManager.func_179129_p();
/* 101 */       RenderHelper.func_74519_b();
/* 102 */       GlStateManager.func_179109_b((p_190563_1_ / 2) + f5 * MathHelper.func_76135_e(MathHelper.func_76126_a(f4 * 2.0F)), (p_190563_2_ / 2) + f6 * MathHelper.func_76135_e(MathHelper.func_76126_a(f4 * 2.0F)), -50.0F);
/* 103 */       float f7 = 50.0F + 175.0F * MathHelper.func_76126_a(f4);
/* 104 */       GlStateManager.func_179152_a(f7, -f7, f7);
/* 105 */       GlStateManager.func_179114_b(900.0F * MathHelper.func_76135_e(MathHelper.func_76126_a(f4)), 0.0F, 1.0F, 0.0F);
/* 106 */       GlStateManager.func_179114_b(6.0F * MathHelper.func_76134_b(f * 8.0F), 1.0F, 0.0F, 0.0F);
/* 107 */       GlStateManager.func_179114_b(6.0F * MathHelper.func_76134_b(f * 8.0F), 0.0F, 0.0F, 1.0F);
/* 108 */       this; mc.func_175599_af().func_181564_a(this.itemActivationItem, ItemCameraTransforms.TransformType.FIXED);
/* 109 */       GlStateManager.func_179099_b();
/* 110 */       GlStateManager.func_179121_F();
/* 111 */       RenderHelper.func_74518_a();
/* 112 */       GlStateManager.func_179089_o();
/* 113 */       GlStateManager.func_179097_i();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\AutoPot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */