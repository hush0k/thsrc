/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.PostPlayerUpdateEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.MovementUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Timer
/*     */   extends Module
/*     */ {
/*     */   public static long lastUpdateTime;
/*     */   public static double value;
/*  19 */   public final Setting<ColorSetting> color = register(new Setting("Color1", new ColorSetting(-2013233153)));
/*  20 */   public final Setting<ColorSetting> color2 = register(new Setting("Color2", new ColorSetting(-2001657727)));
/*  21 */   public final Setting<Integer> slices = register(new Setting("colorOffset1", Integer.valueOf(125), Integer.valueOf(10), Integer.valueOf(500)));
/*  22 */   public final Setting<Integer> slices1 = register(new Setting("colorOffset2", Integer.valueOf(211), Integer.valueOf(10), Integer.valueOf(500)));
/*  23 */   public final Setting<Integer> slices2 = register(new Setting("colorOffset3", Integer.valueOf(162), Integer.valueOf(10), Integer.valueOf(500)));
/*  24 */   public final Setting<Integer> slices3 = register(new Setting("colorOffset4", Integer.valueOf(60), Integer.valueOf(10), Integer.valueOf(500)));
/*  25 */   public final Setting<Integer> yyy = register(new Setting("Y", Integer.valueOf(180), Integer.valueOf(10), Integer.valueOf(500)));
/*  26 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.NORMAL));
/*  27 */   public Setting<Float> speed = register(new Setting("Speed", Float.valueOf(2.0F), Float.valueOf(0.1F), Float.valueOf(10.0F), v -> (this.mode.getValue() == Mode.NORMAL)));
/*  28 */   public Setting<Boolean> smart = register(new Setting("Smart", Boolean.valueOf(true), v -> (this.mode.getValue() == Mode.NORMAL)));
/*  29 */   public Setting<Boolean> noMove = register(new Setting("NoMove", Boolean.valueOf(true), v -> (((Boolean)this.smart.getValue()).booleanValue() && this.mode.getValue() == Mode.NORMAL)));
/*  30 */   public Setting<Boolean> autoDisable = register(new Setting("AutoDisable", Boolean.valueOf(true), v -> (((Boolean)this.smart.getValue()).booleanValue() && this.mode.getValue() == Mode.NORMAL)));
/*  31 */   public Setting<Boolean> indicator = register(new Setting("Indicator", Boolean.valueOf(false), v -> (((Boolean)this.smart.getValue()).booleanValue() && this.mode.getValue() == Mode.NORMAL)));
/*  32 */   public Setting<Integer> maxTicks = register(new Setting("Bound", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(15), v -> (this.mode.getValue() == Mode.NORMAL)));
/*  33 */   public Setting<Float> shiftTicks = register(new Setting("ShiftTicks", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(40.0F), v -> (this.mode.getValue() == Mode.ReallyWorld)));
/*     */   public Timer() {
/*  35 */     super("Timer", "Timer", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   public static Color TwoColoreffect(Color cl1, Color cl2, double speed) {
/*  39 */     double thing = speed / 4.0D % 1.0D;
/*  40 */     float val = MathHelper.func_76131_a((float)Math.sin(18.84955592153876D * thing) / 2.0F + 0.5F, 0.0F, 1.0F);
/*  41 */     return new Color(lerp(cl1.getRed() / 255.0F, cl2.getRed() / 255.0F, val), lerp(cl1.getGreen() / 255.0F, cl2.getGreen() / 255.0F, val), lerp(cl1.getBlue() / 255.0F, cl2.getBlue() / 255.0F, val));
/*     */   }
/*     */   
/*     */   public static float lerp(float a, float b, float f) {
/*  45 */     return a + f * (b - a);
/*     */   }
/*     */   
/*     */   public int getMin() {
/*  49 */     return -(15 - ((Integer)this.maxTicks.getValue()).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  54 */     if (this.mode.getValue() == Mode.ReallyWorld)
/*     */       return; 
/*  56 */     if (!MovementUtil.isMoving() && mc.field_71439_g.field_70122_E && (
/*  57 */       (Boolean)this.noMove.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  61 */     if (!((Boolean)this.smart.getValue()).booleanValue() || canEnableTimer(((Float)this.speed.getValue()).floatValue() + 0.2F)) {
/*  62 */       Thunderhack.TICK_TIMER = Math.max(((Float)this.speed.getValue()).floatValue() + ((mc.field_71439_g.field_70173_aa % 2 == 0) ? -0.2F : 0.2F), 0.1F);
/*     */     } else {
/*  64 */       if (((Boolean)this.autoDisable.getValue()).booleanValue()) {
/*  65 */         toggle();
/*     */       }
/*  67 */       Thunderhack.TICK_TIMER = 1.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPostPlayerUpdate(PostPlayerUpdateEvent event) {
/*  73 */     if (this.mode.getValue() == Mode.ReallyWorld) {
/*  74 */       int status = (int)((10.0D - value) / (Math.abs(getMin()) + 10) * 100.0D);
/*  75 */       if (status < 90) {
/*  76 */         Command.sendMessage("Перед повторным использованием необходимо постоять на месте!");
/*  77 */         toggle();
/*     */         return;
/*     */       } 
/*  80 */       event.setCanceled(true);
/*  81 */       event.setIterations(((Float)this.shiftTicks.getValue()).intValue());
/*  82 */       toggle();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  88 */     Thunderhack.TICK_TIMER = 1.0F;
/*     */   }
/*     */   
/*     */   public boolean canEnableTimer(float speed) {
/*  92 */     double predictVl = (50.0D - 50.0D / speed) / 50.0D;
/*  93 */     return (predictVl + value < (10.0F - ((Float)this.speed.getValue()).floatValue()));
/*     */   }
/*     */   
/*     */   public void m() {
/*  97 */     long now = System.currentTimeMillis();
/*  98 */     long timeElapsed = now - lastUpdateTime;
/*  99 */     lastUpdateTime = now;
/* 100 */     value += (50.0D - timeElapsed) / 50.0D;
/* 101 */     value -= 0.001D;
/* 102 */     value = MathHelper.func_151237_a(value, getMin(), 25.0D);
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 106 */     NORMAL,
/* 107 */     ReallyWorld;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */