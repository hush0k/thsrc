/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.RenderItemEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.combat.Aura;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class ViewModel
/*     */   extends Module {
/*  12 */   private static ViewModel INSTANCE = new ViewModel();
/*  13 */   public Setting<Settings> settings = register(new Setting("Settings", Settings.TRANSLATE));
/*  14 */   public Setting<Boolean> noEatAnimation = register(new Setting("NoEatAnimation", Boolean.valueOf(false), v -> (this.settings.getValue() == Settings.TWEAKS)));
/*  15 */   public Setting<Float> eatX = register(new Setting("EatX", Float.valueOf(1.0F), Float.valueOf(-2.0F), Float.valueOf(5.0F), v -> (this.settings.getValue() == Settings.TWEAKS && !((Boolean)this.noEatAnimation.getValue()).booleanValue())));
/*  16 */   public Setting<Float> eatY = register(new Setting("EatY", Float.valueOf(1.0F), Float.valueOf(-2.0F), Float.valueOf(5.0F), v -> (this.settings.getValue() == Settings.TWEAKS && !((Boolean)this.noEatAnimation.getValue()).booleanValue())));
/*  17 */   public Setting<Boolean> doBob = register(new Setting("ItemBob", Boolean.valueOf(true), v -> (this.settings.getValue() == Settings.TWEAKS)));
/*  18 */   public Setting<Boolean> XBob = register(new Setting("ZBob", Boolean.valueOf(true), v -> (this.settings.getValue() == Settings.TWEAKS)));
/*  19 */   public Setting<Float> zbobcorr = register(new Setting("ZBobCorr", Float.valueOf(0.6F), Float.valueOf(0.1F), Float.valueOf(2.0F), v -> (this.settings.getValue() == Settings.TWEAKS)));
/*     */   
/*  21 */   public Setting<Float> mainX = register(new Setting("MainX", Float.valueOf(1.2F), Float.valueOf(-2.0F), Float.valueOf(4.0F), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  22 */   public Setting<Float> mainY = register(new Setting("MainY", Float.valueOf(-0.95F), Float.valueOf(-3.0F), Float.valueOf(3.0F), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  23 */   public Setting<Float> mainZ = register(new Setting("MainZ", Float.valueOf(-1.45F), Float.valueOf(-5.0F), Float.valueOf(5.0F), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  24 */   public Setting<Float> offX = register(new Setting("OffX", Float.valueOf(1.2F), Float.valueOf(-2.0F), Float.valueOf(4.0F), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  25 */   public Setting<Float> offY = register(new Setting("OffY", Float.valueOf(-0.95F), Float.valueOf(-3.0F), Float.valueOf(3.0F), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  26 */   public Setting<Float> offZ = register(new Setting("OffZ", Float.valueOf(-1.45F), Float.valueOf(-5.0F), Float.valueOf(5.0F), v -> (this.settings.getValue() == Settings.TRANSLATE)));
/*  27 */   public Setting<Float> mainRotX = register(new Setting("MainRotationX", Float.valueOf(0.0F), Float.valueOf(-36.0F), Float.valueOf(36.0F), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  28 */   public Setting<Float> mainRotY = register(new Setting("MainRotationY", Float.valueOf(0.0F), Float.valueOf(-36.0F), Float.valueOf(36.0F), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  29 */   public Setting<Float> mainRotZ = register(new Setting("MainRotationZ", Float.valueOf(0.0F), Float.valueOf(-36.0F), Float.valueOf(36.0F), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  30 */   public Setting<Float> offRotX = register(new Setting("OffRotationX", Float.valueOf(0.0F), Float.valueOf(-36.0F), Float.valueOf(36.0F), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  31 */   public Setting<Float> offRotY = register(new Setting("OffRotationY", Float.valueOf(0.0F), Float.valueOf(-36.0F), Float.valueOf(36.0F), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  32 */   public Setting<Float> offRotZ = register(new Setting("OffRotationZ", Float.valueOf(0.0F), Float.valueOf(-36.0F), Float.valueOf(36.0F), v -> (this.settings.getValue() == Settings.ROTATE)));
/*  33 */   public Setting<Float> mainScaleX = register(new Setting("MainScaleX", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> (this.settings.getValue() == Settings.SCALE)));
/*  34 */   public Setting<Float> mainScaleY = register(new Setting("MainScaleY", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> (this.settings.getValue() == Settings.SCALE)));
/*  35 */   public Setting<Float> mainScaleZ = register(new Setting("MainScaleZ", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> (this.settings.getValue() == Settings.SCALE)));
/*  36 */   public Setting<Float> offScaleX = register(new Setting("OffScaleX", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> (this.settings.getValue() == Settings.SCALE)));
/*  37 */   public Setting<Float> offScaleY = register(new Setting("OffScaleY", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> (this.settings.getValue() == Settings.SCALE)));
/*  38 */   public Setting<Float> offScaleZ = register(new Setting("OffScaleZ", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> (this.settings.getValue() == Settings.SCALE)));
/*     */ 
/*     */   
/*  41 */   public Timer timer2 = new Timer();
/*     */ 
/*     */   
/*  44 */   public Setting<Boolean> killauraattack = register(new Setting("KillAura", Boolean.valueOf(false)));
/*  45 */   public Setting<Float> kmainScaleX = register(new Setting("KMainScaleX", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*  46 */   public Setting<Float> kmainScaleY = register(new Setting("KMainScaleY", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*  47 */   public Setting<Float> kmainScaleZ = register(new Setting("KMainScaleZ", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*  48 */   public Setting<Float> kmainRotX = register(new Setting("KMainRotationX", Float.valueOf(0.0F), Float.valueOf(-36.0F), Float.valueOf(36.0F), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*  49 */   public Setting<Float> kmainRotY = register(new Setting("KMainRotationY", Float.valueOf(0.0F), Float.valueOf(-36.0F), Float.valueOf(36.0F), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*  50 */   public Setting<Float> kmainRotZ = register(new Setting("kMainRotationZ", Float.valueOf(0.0F), Float.valueOf(-36.0F), Float.valueOf(36.0F), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*  51 */   public Setting<Float> kmainX = register(new Setting("KMainX", Float.valueOf(1.2F), Float.valueOf(-2.0F), Float.valueOf(4.0F), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*  52 */   public Setting<Float> kmainY = register(new Setting("KMainY", Float.valueOf(-0.95F), Float.valueOf(-3.0F), Float.valueOf(3.0F), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*  53 */   public Setting<Float> kmainZ = register(new Setting("KMainZ", Float.valueOf(-1.45F), Float.valueOf(-5.0F), Float.valueOf(5.0F), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*     */ 
/*     */   
/*  56 */   public Setting<Boolean> rotatexo = register(new Setting("RotateX", Boolean.valueOf(false)));
/*  57 */   public Setting<Boolean> rotateyo = register(new Setting("RotateY", Boolean.valueOf(false)));
/*  58 */   public Setting<Boolean> rotatezo = register(new Setting("RotateZ", Boolean.valueOf(false)));
/*     */ 
/*     */   
/*  61 */   public Setting<Boolean> krotatex = register(new Setting("KRotateX", Boolean.valueOf(false), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*  62 */   public Setting<Boolean> krotatey = register(new Setting("KRotateY", Boolean.valueOf(false), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*  63 */   public Setting<Boolean> krotatez = register(new Setting("KRotateZ", Boolean.valueOf(false), v -> ((Boolean)this.killauraattack.getValue()).booleanValue()));
/*     */   
/*  65 */   public Setting<Boolean> rotatex = register(new Setting("RotateXOff", Boolean.valueOf(false)));
/*  66 */   public Setting<Boolean> rotatey = register(new Setting("RotateYOff", Boolean.valueOf(false)));
/*  67 */   public Setting<Boolean> rotatez = register(new Setting("RotateZOff", Boolean.valueOf(false)));
/*     */   
/*  69 */   public Setting<Integer> animdelay = register(new Setting("RotateSpeed", Integer.valueOf(36), Integer.valueOf(1), Integer.valueOf(1200), v -> (((Boolean)this.killauraattack.getValue()).booleanValue() || ((Boolean)this.rotatex.getValue()).booleanValue() || ((Boolean)this.rotatey.getValue()).booleanValue() || ((Boolean)this.rotatez.getValue()).booleanValue())));
/*  70 */   int negripidari = -180;
/*     */   
/*     */   public ViewModel() {
/*  73 */     super("ViewModel", "Cool", Module.Category.RENDER);
/*  74 */     setInstance();
/*     */   }
/*     */   
/*     */   public static ViewModel getInstance() {
/*  78 */     if (INSTANCE == null) {
/*  79 */       INSTANCE = new ViewModel();
/*     */     }
/*  81 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  85 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onItemRender(RenderItemEvent event) {
/*  91 */     event.setOffX(-((Float)this.offX.getValue()).floatValue());
/*  92 */     event.setOffY(((Float)this.offY.getValue()).floatValue());
/*  93 */     event.setOffZ(((Float)this.offZ.getValue()).floatValue());
/*     */ 
/*     */     
/*  96 */     if (this.timer2.passedMs((1000 / ((Integer)this.animdelay.getValue()).intValue()))) {
/*  97 */       this.negripidari++;
/*     */       
/*  99 */       if (this.negripidari > 180) {
/* 100 */         this.negripidari = -180;
/*     */       }
/* 102 */       this.timer2.reset();
/*     */     } 
/*     */ 
/*     */     
/* 106 */     if (!((Boolean)this.rotatex.getValue()).booleanValue()) {
/* 107 */       event.setOffRotX(((Float)this.offRotX.getValue()).floatValue() * 5.0F);
/*     */     } else {
/* 109 */       event.setOffRotX(this.negripidari);
/*     */     } 
/* 111 */     if (!((Boolean)this.rotatey.getValue()).booleanValue()) {
/* 112 */       event.setOffRotY(((Float)this.offRotY.getValue()).floatValue() * 5.0F);
/*     */     } else {
/* 114 */       event.setOffRotY(this.negripidari);
/*     */     } 
/* 116 */     if (!((Boolean)this.rotatez.getValue()).booleanValue()) {
/* 117 */       event.setOffRotZ(((Float)this.offRotZ.getValue()).floatValue() * 5.0F);
/*     */     } else {
/* 119 */       event.setOffRotZ(this.negripidari);
/*     */     } 
/*     */     
/* 122 */     event.setOffHandScaleX(((Float)this.offScaleX.getValue()).floatValue());
/* 123 */     event.setOffHandScaleY(((Float)this.offScaleY.getValue()).floatValue());
/* 124 */     event.setOffHandScaleZ(((Float)this.offScaleZ.getValue()).floatValue());
/*     */ 
/*     */     
/* 127 */     if (((Boolean)this.killauraattack.getValue()).booleanValue() && Aura.target != null) {
/* 128 */       event.setMainHandScaleX(((Float)this.kmainScaleX.getValue()).floatValue());
/* 129 */       event.setMainHandScaleY(((Float)this.kmainScaleY.getValue()).floatValue());
/* 130 */       event.setMainHandScaleZ(((Float)this.kmainScaleZ.getValue()).floatValue());
/*     */ 
/*     */       
/* 133 */       if (!((Boolean)this.krotatex.getValue()).booleanValue()) {
/* 134 */         event.setMainRotX(((Float)this.kmainRotX.getValue()).floatValue() * 5.0F);
/*     */       } else {
/* 136 */         event.setMainRotX(this.negripidari);
/*     */       } 
/* 138 */       if (!((Boolean)this.krotatey.getValue()).booleanValue()) {
/* 139 */         event.setMainRotY(((Float)this.kmainRotY.getValue()).floatValue() * 5.0F);
/*     */       } else {
/* 141 */         event.setMainRotY(this.negripidari);
/*     */       } 
/* 143 */       if (!((Boolean)this.krotatez.getValue()).booleanValue()) {
/* 144 */         event.setMainRotZ(((Float)this.kmainRotZ.getValue()).floatValue() * 5.0F);
/*     */       } else {
/* 146 */         event.setMainRotZ(this.negripidari);
/*     */       } 
/*     */ 
/*     */       
/* 150 */       event.setMainX(((Float)this.kmainX.getValue()).floatValue());
/* 151 */       event.setMainY(((Float)this.kmainY.getValue()).floatValue());
/* 152 */       event.setMainZ(((Float)this.kmainZ.getValue()).floatValue());
/*     */     } else {
/* 154 */       event.setMainHandScaleX(((Float)this.mainScaleX.getValue()).floatValue());
/* 155 */       event.setMainHandScaleY(((Float)this.mainScaleY.getValue()).floatValue());
/* 156 */       event.setMainHandScaleZ(((Float)this.mainScaleZ.getValue()).floatValue());
/*     */ 
/*     */       
/* 159 */       if (!((Boolean)this.rotatexo.getValue()).booleanValue()) {
/* 160 */         event.setMainRotX(((Float)this.mainRotX.getValue()).floatValue() * 5.0F);
/*     */       } else {
/* 162 */         event.setMainRotX(this.negripidari);
/*     */       } 
/* 164 */       if (!((Boolean)this.rotateyo.getValue()).booleanValue()) {
/* 165 */         event.setMainRotY(((Float)this.mainRotY.getValue()).floatValue() * 5.0F);
/*     */       } else {
/* 167 */         event.setMainRotY(this.negripidari);
/*     */       } 
/* 169 */       if (!((Boolean)this.rotatezo.getValue()).booleanValue()) {
/* 170 */         event.setMainRotZ(((Float)this.mainRotZ.getValue()).floatValue() * 5.0F);
/*     */       } else {
/* 172 */         event.setMainRotZ(this.negripidari);
/*     */       } 
/*     */ 
/*     */       
/* 176 */       event.setMainX(((Float)this.mainX.getValue()).floatValue());
/* 177 */       event.setMainY(((Float)this.mainY.getValue()).floatValue());
/* 178 */       event.setMainZ(((Float)this.mainZ.getValue()).floatValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum Settings
/*     */   {
/* 186 */     TRANSLATE,
/* 187 */     ROTATE,
/* 188 */     SCALE,
/* 189 */     TWEAKS;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\ViewModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */