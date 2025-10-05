/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.StepEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.player.FreeCam;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Step
/*     */   extends Module
/*     */ {
/*  20 */   private final Timer stepTimer = new Timer();
/*  21 */   public Setting<Float> height = register(new Setting("Height", Float.valueOf(2.0F), Float.valueOf(1.0F), Float.valueOf(2.5F)));
/*  22 */   public Setting<Boolean> entityStep = register(new Setting("EntityStep", Boolean.valueOf(false)));
/*  23 */   public Setting<Boolean> useTimer = register(new Setting("Timer", Boolean.valueOf(true)));
/*  24 */   public Setting<Boolean> strict = register(new Setting("Strict", Boolean.valueOf(false)));
/*  25 */   public Setting<Integer> stepDelay = register(new Setting("StepDelay", Integer.valueOf(200), Integer.valueOf(0), Integer.valueOf(1000)));
/*  26 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.NORMAL));
/*     */ 
/*     */   
/*     */   private boolean timer;
/*     */   
/*     */   private Entity entityRiding;
/*     */ 
/*     */   
/*     */   public Step() {
/*  35 */     super("Step", "Step", Module.Category.MOVEMENT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  40 */     super.onDisable();
/*  41 */     mc.field_71439_g.field_70138_W = 0.6F;
/*  42 */     if (this.entityRiding != null) {
/*  43 */       if (this.entityRiding instanceof net.minecraft.entity.passive.EntityHorse || this.entityRiding instanceof net.minecraft.entity.passive.EntityLlama || this.entityRiding instanceof net.minecraft.entity.passive.EntityMule || (this.entityRiding instanceof EntityPig && this.entityRiding.func_184207_aI() && ((EntityPig)this.entityRiding).func_82171_bF())) {
/*  44 */         this.entityRiding.field_70138_W = 1.0F;
/*     */       } else {
/*  46 */         this.entityRiding.field_70138_W = 0.5F;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  53 */     if (mc.field_71439_g.field_71075_bZ.field_75100_b || ((FreeCam)Thunderhack.moduleManager.getModuleByClass(FreeCam.class)).isOn()) {
/*  54 */       mc.field_71439_g.field_70138_W = 0.6F;
/*     */       return;
/*     */     } 
/*  57 */     if (Jesus.isInLiquid()) {
/*  58 */       mc.field_71439_g.field_70138_W = 0.6F;
/*     */       return;
/*     */     } 
/*  61 */     if (this.timer && mc.field_71439_g.field_70122_E) {
/*  62 */       Thunderhack.TICK_TIMER = 1.0F;
/*  63 */       this.timer = false;
/*     */     } 
/*     */     
/*  66 */     if (mc.field_71439_g.field_70122_E && this.stepTimer.passedMs(((Integer)this.stepDelay.getValue()).intValue())) {
/*  67 */       if (mc.field_71439_g.func_184218_aH() && mc.field_71439_g.func_184187_bx() != null) {
/*  68 */         this.entityRiding = mc.field_71439_g.func_184187_bx();
/*  69 */         if (((Boolean)this.entityStep.getValue()).booleanValue()) {
/*  70 */           (mc.field_71439_g.func_184187_bx()).field_70138_W = ((Float)this.height.getValue()).floatValue();
/*     */         }
/*     */       } else {
/*  73 */         mc.field_71439_g.field_70138_W = ((Float)this.height.getValue()).floatValue();
/*     */       }
/*     */     
/*  76 */     } else if (mc.field_71439_g.func_184218_aH() && mc.field_71439_g.func_184187_bx() != null) {
/*  77 */       this.entityRiding = mc.field_71439_g.func_184187_bx();
/*  78 */       if (this.entityRiding != null) {
/*  79 */         if (this.entityRiding instanceof net.minecraft.entity.passive.EntityHorse || this.entityRiding instanceof net.minecraft.entity.passive.EntityLlama || this.entityRiding instanceof net.minecraft.entity.passive.EntityMule || (this.entityRiding instanceof EntityPig && this.entityRiding.func_184207_aI() && ((EntityPig)this.entityRiding).func_82171_bF())) {
/*  80 */           this.entityRiding.field_70138_W = 1.0F;
/*     */         } else {
/*  82 */           this.entityRiding.field_70138_W = 0.5F;
/*     */         } 
/*     */       }
/*     */     } else {
/*  86 */       mc.field_71439_g.field_70138_W = 0.6F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onStep(StepEvent event) {
/*  93 */     if (((Mode)this.mode.getValue()).equals(Mode.NORMAL)) {
/*  94 */       double stepHeight = (event.getAxisAlignedBB()).field_72338_b - mc.field_71439_g.field_70163_u;
/*  95 */       if (stepHeight <= 0.0D || stepHeight > ((Float)this.height.getValue()).floatValue()) {
/*     */         return;
/*     */       }
/*  98 */       double[] offsets = getOffset(stepHeight);
/*  99 */       if (offsets != null && offsets.length > 1) {
/* 100 */         if (((Boolean)this.useTimer.getValue()).booleanValue()) {
/* 101 */           Thunderhack.TICK_TIMER = 1.0F / offsets.length;
/* 102 */           this.timer = true;
/*     */         } 
/* 104 */         for (double offset : offsets) {
/* 105 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + offset, mc.field_71439_g.field_70161_v, false));
/*     */         }
/*     */       } 
/* 108 */       this.stepTimer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   public double[] getOffset(double height) {
/* 113 */     if (height == 0.75D) {
/* 114 */       if (((Boolean)this.strict.getValue()).booleanValue()) {
/* 115 */         return new double[] { 0.42D, 0.753D, 0.75D };
/*     */       }
/* 117 */       return new double[] { 0.42D, 0.753D };
/*     */     } 
/* 119 */     if (height == 0.8125D) {
/* 120 */       if (((Boolean)this.strict.getValue()).booleanValue()) {
/* 121 */         return new double[] { 0.39D, 0.7D, 0.8125D };
/*     */       }
/* 123 */       return new double[] { 0.39D, 0.7D };
/*     */     } 
/* 125 */     if (height == 0.875D) {
/* 126 */       if (((Boolean)this.strict.getValue()).booleanValue()) {
/* 127 */         return new double[] { 0.39D, 0.7D, 0.875D };
/*     */       }
/* 129 */       return new double[] { 0.39D, 0.7D };
/*     */     } 
/* 131 */     if (height == 1.0D) {
/* 132 */       if (((Boolean)this.strict.getValue()).booleanValue()) {
/* 133 */         return new double[] { 0.42D, 0.753D, 1.0D };
/*     */       }
/* 135 */       return new double[] { 0.42D, 0.753D };
/*     */     } 
/* 137 */     if (height == 1.5D)
/* 138 */       return new double[] { 0.42D, 0.75D, 1.0D, 1.16D, 1.23D, 1.2D }; 
/* 139 */     if (height == 2.0D)
/* 140 */       return new double[] { 0.42D, 0.78D, 0.63D, 0.51D, 0.9D, 1.21D, 1.45D, 1.43D }; 
/* 141 */     if (height == 2.5D) {
/* 142 */       return new double[] { 0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D, 2.019D, 1.907D };
/*     */     }
/*     */     
/* 145 */     return null;
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 149 */     NORMAL,
/* 150 */     VANILLA;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\Step.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */