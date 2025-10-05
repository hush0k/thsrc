/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.render.PaletteHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Radar extends HudElement {
/*     */   private final Setting<Integer> size;
/*     */   public final Setting<ColorSetting> shadowColor;
/*     */   public final Setting<ColorSetting> color2;
/*     */   
/*     */   public Radar() {
/*  21 */     super("Radar", "классический 2д-радар", "classic 2d-radar", 100, 100);
/*     */ 
/*     */     
/*  24 */     this.size = register(new Setting("Size", Integer.valueOf(80), Integer.valueOf(20), Integer.valueOf(300)));
/*     */     
/*  26 */     this.shadowColor = register(new Setting("ShadowColor", new ColorSetting(-15724528)));
/*  27 */     this.color2 = register(new Setting("Color", new ColorSetting(-15724528)));
/*  28 */     this.color3 = register(new Setting("PlayerColor", new ColorSetting(-979657829)));
/*  29 */     this.jew = register(new Setting("Jew", Boolean.valueOf(false)));
/*  30 */     this.ljew = register(new Setting("LaaaargeJew", Boolean.valueOf(false)));
/*     */ 
/*     */     
/*  33 */     this.players = new CopyOnWriteArrayList<>();
/*     */   }
/*     */   public final Setting<ColorSetting> color3; public Setting<Boolean> jew; public Setting<Boolean> ljew; private CopyOnWriteArrayList<EntityPlayer> players;
/*     */   public void onUpdate() {
/*  37 */     this.players.clear();
/*  38 */     this.players.addAll(mc.field_71441_e.field_73010_i);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/*  43 */     super.onRender2D(e);
/*     */     
/*  45 */     double psx = getPosX();
/*  46 */     double psy = getPosY();
/*  47 */     int sizeRect = ((Integer)this.size.getValue()).intValue();
/*  48 */     float xOffset = (float)psx;
/*  49 */     float yOffset = (float)psy;
/*  50 */     double playerPosX = mc.field_71439_g.field_70165_t;
/*  51 */     double playerPosZ = mc.field_71439_g.field_70161_v;
/*     */ 
/*     */     
/*  54 */     RenderUtil.drawBlurredShadow(xOffset, getPosY(), sizeRect, sizeRect, 20, ((ColorSetting)this.shadowColor.getValue()).getColorObject());
/*  55 */     RoundedShader.drawRound(xOffset, getPosY(), sizeRect, sizeRect, 7.0F, ((ColorSetting)this.color2.getValue()).getColorObject());
/*     */ 
/*     */ 
/*     */     
/*  59 */     RenderUtil.drawRect(xOffset + (sizeRect / 2.0F) - 0.5D, yOffset + 3.5D, xOffset + (sizeRect / 2.0F) + 0.2D, (yOffset + sizeRect) - 3.5D, 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  64 */         PaletteHelper.getColor(155, 100));
/*     */     
/*  66 */     RenderUtil.drawRect(xOffset + 3.5D, yOffset + (sizeRect / 2.0F) - 0.2D, (xOffset + sizeRect) - 3.5D, yOffset + (sizeRect / 2.0F) + 0.5D, 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  71 */         PaletteHelper.getColor(155, 100));
/*     */ 
/*     */     
/*  74 */     for (EntityPlayer entityPlayer : this.players) {
/*  75 */       if (entityPlayer == mc.field_71439_g) {
/*     */         continue;
/*     */       }
/*  78 */       float partialTicks = mc.func_184121_ak();
/*  79 */       float posX = (float)(entityPlayer.field_70165_t + (entityPlayer.field_70165_t - entityPlayer.field_70142_S) * partialTicks - playerPosX) * 2.0F;
/*  80 */       float posZ = (float)(entityPlayer.field_70161_v + (entityPlayer.field_70161_v - entityPlayer.field_70136_U) * partialTicks - playerPosZ) * 2.0F;
/*  81 */       float cos = (float)Math.cos(mc.field_71439_g.field_70177_z * 0.017453292D);
/*  82 */       float sin = (float)Math.sin(mc.field_71439_g.field_70177_z * 0.017453292D);
/*  83 */       float rotY = -(posZ * cos - posX * sin);
/*  84 */       float rotX = -(posX * cos + posZ * sin);
/*  85 */       if (rotY > sizeRect / 2.0F - 6.0F) {
/*  86 */         rotY = sizeRect / 2.0F - 6.0F;
/*  87 */       } else if (rotY < -(sizeRect / 2.0F - 8.0F)) {
/*  88 */         rotY = -(sizeRect / 2.0F - 8.0F);
/*     */       } 
/*  90 */       if (rotX > sizeRect / 2.0F - 5.0F) {
/*  91 */         rotX = sizeRect / 2.0F - 5.0F;
/*  92 */       } else if (rotX < -(sizeRect / 2.0F - 5.0F)) {
/*  93 */         rotX = -(sizeRect / 2.0F - 5.0F);
/*     */       } 
/*  95 */       if (((Boolean)this.jew.getValue()).booleanValue()) {
/*  96 */         if (!((Boolean)this.ljew.getValue()).booleanValue()) {
/*  97 */           FontRender.drawIconF("y", xOffset + sizeRect / 2.0F + rotX - 2.0F, yOffset + sizeRect / 2.0F + rotY - 2.0F, ((ColorSetting)this.color3.getValue()).getColor()); continue;
/*     */         } 
/*  99 */         FontRender.drawMidIcon("y", xOffset + sizeRect / 2.0F + rotX - 4.0F, yOffset + sizeRect / 2.0F + rotY - 4.0F, ((ColorSetting)this.color3.getValue()).getColor()); continue;
/*     */       } 
/* 101 */       RoundedShader.drawRound(xOffset + sizeRect / 2.0F + rotX - 2.0F, yOffset + sizeRect / 2.0F + rotY - 2.0F, 4.0F, 4.0F, 4.0F, ((ColorSetting)this.color3.getValue()).getColorObject());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\Radar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */