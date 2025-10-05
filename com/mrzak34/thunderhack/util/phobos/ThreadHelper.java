/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadHelper
/*     */ {
/*  19 */   private final Timer threadTimer = new Timer();
/*     */   
/*     */   private final Setting<Boolean> multiThread;
/*     */   
/*     */   private final Setting<Boolean> mainThreadThreads;
/*     */   
/*     */   private final Setting<Integer> threadDelay;
/*     */   
/*     */   private final Setting<AutoCrystal.RotationThread> rotationThread;
/*     */   
/*     */   private final Setting<AutoCrystal.ACRotate> rotate;
/*     */   
/*     */   private final AutoCrystal module;
/*     */   private volatile AbstractCalculation<?> currentCalc;
/*     */   
/*     */   public ThreadHelper(AutoCrystal module, Setting<Boolean> multiThread, Setting<Boolean> mainThreadThreads, Setting<Integer> threadDelay, Setting<AutoCrystal.RotationThread> rotationThread, Setting<AutoCrystal.ACRotate> rotate) {
/*  35 */     this.module = module;
/*  36 */     this.multiThread = multiThread;
/*  37 */     this.mainThreadThreads = mainThreadThreads;
/*  38 */     this.threadDelay = threadDelay;
/*  39 */     this.rotationThread = rotationThread;
/*  40 */     this.rotate = rotate;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void start(AbstractCalculation<?> calculation, boolean multiThread) {
/*  45 */     if (this.threadTimer.passedMs(((Integer)this.threadDelay.getValue()).intValue()) && (this.currentCalc == null || this.currentCalc.isFinished())) {
/*  46 */       this.currentCalc = calculation;
/*  47 */       execute(this.currentCalc, multiThread);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void startThread(BlockPos... blackList) {
/*  52 */     if (Util.mc.field_71441_e == null || Util.mc.field_71439_g == null || 
/*     */       
/*  54 */       !this.threadTimer.passedMs(((Integer)this.threadDelay.getValue()).intValue()) || (this.currentCalc != null && 
/*  55 */       !this.currentCalc.isFinished())) {
/*     */       return;
/*     */     }
/*     */     
/*  59 */     if (Util.mc.func_152345_ab()) {
/*  60 */       startThread(new ArrayList<>(Util.mc.field_71441_e.field_72996_f), new ArrayList<>(Util.mc.field_71441_e.field_73010_i), blackList);
/*     */     }
/*     */     else {
/*     */       
/*  64 */       startThread(Thunderhack.entityProvider.getEntities(), Thunderhack.entityProvider
/*  65 */           .getPlayers(), blackList);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void startThread(boolean breakOnly, boolean noBreak, BlockPos... blackList) {
/*  71 */     if (Util.mc.field_71441_e == null || Util.mc.field_71439_g == null || 
/*     */       
/*  73 */       !this.threadTimer.passedMs(((Integer)this.threadDelay.getValue()).intValue()) || (this.currentCalc != null && 
/*  74 */       !this.currentCalc.isFinished())) {
/*     */       return;
/*     */     }
/*     */     
/*  78 */     if (Util.mc.func_152345_ab()) {
/*  79 */       startThread(new ArrayList<>(Util.mc.field_71441_e.field_72996_f), new ArrayList<>(Util.mc.field_71441_e.field_73010_i), breakOnly, noBreak, blackList);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  85 */       startThread(Thunderhack.entityProvider.getEntities(), Thunderhack.entityProvider
/*  86 */           .getPlayers(), breakOnly, noBreak, blackList);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startThread(List<Entity> entities, List<EntityPlayer> players, boolean breakOnly, boolean noBreak, BlockPos... blackList) {
/*  98 */     this.currentCalc = new Calculation(this.module, entities, players, breakOnly, noBreak, blackList);
/*  99 */     execute(this.currentCalc, ((Boolean)this.multiThread.getValue()).booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void startThread(List<Entity> entities, List<EntityPlayer> players, BlockPos... blackList) {
/* 105 */     this.currentCalc = new Calculation(this.module, entities, players, blackList);
/* 106 */     execute(this.currentCalc, ((Boolean)this.multiThread.getValue()).booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   private void execute(AbstractCalculation<?> calculation, boolean multiThread) {
/* 111 */     if (multiThread) {
/* 112 */       Thunderhack.threadManager.submitRunnable(calculation);
/* 113 */       this.threadTimer.reset();
/*     */     } else {
/* 115 */       this.threadTimer.reset();
/* 116 */       calculation.run();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void schedulePacket(PacketEvent.Receive event) {
/* 121 */     if ((((Boolean)this.multiThread.getValue()).booleanValue() || ((Boolean)this.mainThreadThreads.getValue()).booleanValue()) && (this.rotate
/* 122 */       .getValue() == AutoCrystal.ACRotate.None || this.rotationThread
/* 123 */       .getValue() != AutoCrystal.RotationThread.Predict)) {
/* 124 */       event.addPostEvent(() -> rec$.startThread(new BlockPos[0]));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractCalculation<?> getCurrentCalc() {
/* 132 */     return this.currentCalc;
/*     */   }
/*     */   
/*     */   public void resetThreadHelper() {
/* 136 */     this.currentCalc = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ThreadHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */