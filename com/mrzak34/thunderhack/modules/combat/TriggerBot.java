/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.misc.Timer;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import com.mrzak34.thunderhack.util.phobos.IEntityLivingBase;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class TriggerBot
/*     */   extends Module
/*     */ {
/*  24 */   public final Setting<Boolean> criticals = register(new Setting("Criticals", Boolean.valueOf(true)));
/*  25 */   public final Setting<Boolean> smartCrit = register(new Setting("OnlySpace", Boolean.valueOf(true), v -> ((Boolean)this.criticals.getValue()).booleanValue()));
/*  26 */   public final Setting<TimingMode> timingMode = register(new Setting("Timing", TimingMode.Default));
/*  27 */   public final Setting<Integer> minCPS = register(new Setting("MinCPS", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(20), v -> (this.timingMode.getValue() == TimingMode.Old)));
/*  28 */   public final Setting<Integer> maxCPS = register(new Setting("MaxCPS", Integer.valueOf(12), Integer.valueOf(1), Integer.valueOf(20), v -> (this.timingMode.getValue() == TimingMode.Old)));
/*  29 */   public final Setting<Boolean> randomDelay = register(new Setting("RandomDelay", Boolean.valueOf(true), v -> (this.timingMode.getValue() == TimingMode.Default)));
/*  30 */   public final Setting<Float> critdist = register(new Setting("FallDistance", Float.valueOf(0.15F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> ((Boolean)this.criticals.getValue()).booleanValue()));
/*  31 */   private final Timer oldTimer = new Timer();
/*     */   
/*     */   public TriggerBot() {
/*  34 */     super("TriggerBot", "аттакует сущностей-под прицелом", Module.Category.COMBAT);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPreMotion(EventSync e) {
/*  39 */     Entity entity = mc.field_71476_x.field_72308_g;
/*  40 */     if (canAttack(entity)) {
/*  41 */       mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, entity);
/*  42 */       mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canAttack(Entity entity) {
/*  48 */     if (entity == null) {
/*  49 */       return false;
/*     */     }
/*  51 */     if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/*  52 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     boolean reasonForCancelCritical = (mc.field_71439_g.func_70644_a(MobEffects.field_76421_d) || mc.field_71439_g.func_70617_f_() || Aura.isInLiquid() || ((IEntity)mc.field_71439_g).isInWeb() || (((Boolean)this.smartCrit.getValue()).booleanValue() && !mc.field_71474_y.field_74314_A.func_151470_d()));
/*     */ 
/*     */     
/*  62 */     if (this.timingMode.getValue() == TimingMode.Default) {
/*  63 */       if (!((Boolean)this.randomDelay.getValue()).booleanValue()) {
/*  64 */         if (getCooledAttackStrength() <= 0.93D) {
/*  65 */           return false;
/*     */         }
/*     */       } else {
/*  68 */         float delay = MathUtil.random(0.85F, 0.1F);
/*  69 */         if (getCooledAttackStrength() <= delay) {
/*  70 */           return false;
/*     */         }
/*     */       } 
/*     */     } else {
/*  74 */       int CPS = (int)MathUtil.random(((Integer)this.minCPS.getValue()).intValue(), ((Integer)this.maxCPS.getValue()).intValue());
/*  75 */       if (!this.oldTimer.passedMs((long)((1000.0F + MathUtil.random(1.0F, 50.0F) - MathUtil.random(1.0F, 60.0F) + MathUtil.random(1.0F, 70.0F)) / CPS))) {
/*  76 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  80 */     if (((Boolean)this.criticals.getValue()).booleanValue() && mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof net.minecraft.block.BlockLiquid && mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.0D, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof net.minecraft.block.BlockAir && mc.field_71439_g.field_70143_R >= 0.08F) {
/*  81 */       return true;
/*     */     }
/*     */     
/*  84 */     if (((Boolean)this.criticals.getValue()).booleanValue() && !reasonForCancelCritical) {
/*  85 */       boolean onFall = Aura.isBlockAboveHead() ? ((mc.field_71439_g.field_70143_R > 0.0F)) : ((mc.field_71439_g.field_70143_R >= ((Float)this.critdist.getValue()).floatValue()));
/*  86 */       return (onFall && !mc.field_71439_g.field_70122_E);
/*     */     } 
/*  88 */     this.oldTimer.reset();
/*  89 */     return true;
/*     */   }
/*     */   
/*     */   private float getCooledAttackStrength() {
/*  93 */     return MathHelper.func_76131_a((((IEntityLivingBase)mc.field_71439_g).getTicksSinceLastSwing() + 1.5F) / getCooldownPeriod(), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public float getCooldownPeriod() {
/*  97 */     return (float)(1.0D / mc.field_71439_g.func_110148_a(SharedMonsterAttributes.field_188790_f).func_111126_e() * (((Timer)Thunderhack.moduleManager.getModuleByClass(Timer.class)).isOn() ? (20.0F * ((Float)((Timer)Thunderhack.moduleManager.getModuleByClass(Timer.class)).speed.getValue()).floatValue()) : 20.0D));
/*     */   }
/*     */   
/*     */   public enum TimingMode {
/* 101 */     Default, Old;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\TriggerBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */