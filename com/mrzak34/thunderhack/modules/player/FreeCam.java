/*     */ package com.mrzak34.thunderhack.modules.player;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.FreecamEvent;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.events.RenderItemOverlayEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.setting.SubBind;
/*     */ import com.mrzak34.thunderhack.util.FreecamCamera;
/*     */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.MovementInputFromOptions;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class FreeCam
/*     */   extends Module
/*     */ {
/*  23 */   private static FreeCam INSTANCE = new FreeCam();
/*  24 */   public Setting<SubBind> movePlayer = register(new Setting("Control", new SubBind(56)));
/*  25 */   private final Setting<Float> hSpeed = register(new Setting("HSpeed", Float.valueOf(1.0F), Float.valueOf(0.2F), Float.valueOf(2.0F)));
/*  26 */   private final Setting<Float> vSpeed = register(new Setting("VSpeed", Float.valueOf(1.0F), Float.valueOf(0.2F), Float.valueOf(2.0F)));
/*  27 */   private final Setting<Boolean> follow = register(new Setting("Follow", Boolean.valueOf(false)));
/*  28 */   private final Setting<Boolean> copyInventory = register(new Setting("CopyInv", Boolean.valueOf(false)));
/*  29 */   private Entity cachedActiveEntity = null;
/*  30 */   private int lastActiveTick = -1;
/*  31 */   private Entity oldRenderEntity = null;
/*  32 */   private FreecamCamera camera = null;
/*  33 */   private final MovementInput cameraMovement = (MovementInput)new MovementInputFromOptions(mc.field_71474_y)
/*     */     {
/*     */       public void func_78898_a() {
/*  36 */         if (!PlayerUtils.isKeyDown(((SubBind)FreeCam.this.movePlayer.getValue()).getKey())) {
/*  37 */           super.func_78898_a();
/*     */         } else {
/*  39 */           this.field_78902_a = 0.0F;
/*  40 */           this.field_192832_b = 0.0F;
/*  41 */           this.field_187255_c = false;
/*  42 */           this.field_187256_d = false;
/*  43 */           this.field_187257_e = false;
/*  44 */           this.field_187258_f = false;
/*  45 */           this.field_78901_c = false;
/*  46 */           this.field_78899_d = false;
/*     */         } 
/*     */       }
/*     */     };
/*  50 */   private final MovementInput playerMovement = (MovementInput)new MovementInputFromOptions(mc.field_71474_y)
/*     */     {
/*     */       public void func_78898_a() {
/*  53 */         if (PlayerUtils.isKeyDown(((SubBind)FreeCam.this.movePlayer.getValue()).getKey())) {
/*  54 */           super.func_78898_a();
/*     */         } else {
/*  56 */           this.field_78902_a = 0.0F;
/*  57 */           this.field_192832_b = 0.0F;
/*  58 */           this.field_187255_c = false;
/*  59 */           this.field_187256_d = false;
/*  60 */           this.field_187257_e = false;
/*  61 */           this.field_187258_f = false;
/*  62 */           this.field_78901_c = false;
/*  63 */           this.field_78899_d = false;
/*     */         } 
/*     */       }
/*     */     };
/*     */   public FreeCam() {
/*  68 */     super("FreeCam", "свобоная камера", Module.Category.PLAYER);
/*  69 */     setInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public static FreeCam getInstance() {
/*  74 */     if (INSTANCE == null) {
/*  75 */       INSTANCE = new FreeCam();
/*     */     }
/*  77 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  81 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public Entity getActiveEntity() {
/*  85 */     if (this.cachedActiveEntity == null) {
/*  86 */       this.cachedActiveEntity = (Entity)mc.field_71439_g;
/*     */     }
/*     */     
/*  89 */     int currentTick = mc.field_71439_g.field_70173_aa;
/*  90 */     if (this.lastActiveTick != currentTick) {
/*  91 */       this.lastActiveTick = currentTick;
/*     */       
/*  93 */       if (isEnabled()) {
/*  94 */         if (PlayerUtils.isKeyDown(((SubBind)this.movePlayer.getValue()).getKey())) {
/*  95 */           this.cachedActiveEntity = (Entity)mc.field_71439_g;
/*     */         } else {
/*  97 */           this.cachedActiveEntity = (mc.func_175606_aa() == null) ? (Entity)mc.field_71439_g : mc.func_175606_aa();
/*     */         } 
/*     */       } else {
/* 100 */         this.cachedActiveEntity = (Entity)mc.field_71439_g;
/*     */       } 
/*     */     } 
/* 103 */     return this.cachedActiveEntity;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onWorldLoad(WorldEvent.Unload event) {
/* 108 */     mc.func_175607_a((Entity)mc.field_71439_g);
/* 109 */     toggle();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onFreecam(FreecamEvent event) {
/* 114 */     event.setCanceled(true);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/* 119 */     ScaledResolution sr = new ScaledResolution(mc);
/* 120 */     String yCoord = "" + -Math.round(mc.field_71439_g.field_70163_u - (getActiveEntity()).field_70163_u);
/*     */     
/* 122 */     String str = ".vclip " + yCoord;
/* 123 */     FontRender.drawString6(str, (float)((sr.func_78326_a() - FontRender.getStringWidth6(str)) / 1.98D), (float)(sr.func_78328_b() / 1.8D - 20.0D), -1, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 129 */     if (mc.field_71439_g == null || mc.field_71441_e == null)
/* 130 */       return;  this.camera.setCopyInventory(((Boolean)this.copyInventory.getValue()).booleanValue());
/* 131 */     this.camera.setFollow(((Boolean)this.follow.getValue()).booleanValue());
/* 132 */     this.camera.sethSpeed(((Float)this.hSpeed.getValue()).floatValue());
/* 133 */     this.camera.setvSpeed(((Float)this.vSpeed.getValue()).floatValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 138 */     if (mc.field_71439_g == null)
/*     */       return; 
/* 140 */     this.camera = new FreecamCamera(((Boolean)this.copyInventory.getValue()).booleanValue(), ((Boolean)this.follow.getValue()).booleanValue(), ((Float)this.hSpeed.getValue()).floatValue(), ((Float)this.vSpeed.getValue()).floatValue());
/* 141 */     this.camera.field_71158_b = this.cameraMovement;
/* 142 */     mc.field_71439_g.field_71158_b = this.playerMovement;
/* 143 */     mc.field_71441_e.func_73027_a(-921, (Entity)this.camera);
/* 144 */     this.oldRenderEntity = mc.func_175606_aa();
/* 145 */     mc.func_175607_a((Entity)this.camera);
/* 146 */     mc.field_175612_E = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 151 */     if (mc.field_71439_g == null)
/*     */       return; 
/* 153 */     if (this.camera != null) mc.field_71441_e.func_72900_e((Entity)this.camera); 
/* 154 */     this.camera = null;
/* 155 */     mc.field_71439_g.field_71158_b = (MovementInput)new MovementInputFromOptions(mc.field_71474_y);
/* 156 */     mc.func_175607_a(this.oldRenderEntity);
/* 157 */     mc.field_175612_E = true;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderOverlay(RenderItemOverlayEvent event) {
/* 162 */     event.setCanceled(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\FreeCam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */