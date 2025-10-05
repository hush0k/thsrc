/*    */ package com.mrzak34.thunderhack.gui.hud.elements;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.EventSync;
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import java.math.BigDecimal;
/*    */ import java.math.RoundingMode;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class Speedometer
/*    */   extends HudElement {
/* 18 */   public final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(-2013200640)));
/* 19 */   public double speedometerCurrentSpeed = 0.0D;
/* 20 */   private final Setting<Boolean> bps = register(new Setting("BPS", Boolean.valueOf(false)));
/*    */   public Speedometer() {
/* 22 */     super("Speedometer", "Speedometer", 50, 10);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 27 */     super.onRender2D(e);
/* 28 */     String str = "";
/* 29 */     if (!((Boolean)this.bps.getValue()).booleanValue()) {
/* 30 */       str = "Speed " + ChatFormatting.WHITE + round(getSpeedKpH() * Thunderhack.TICK_TIMER) + " km/h";
/*    */     } else {
/* 32 */       str = String.format("Speed " + ChatFormatting.WHITE + round(getSpeedMpS() * Thunderhack.TICK_TIMER) + " b/s", new Object[0]);
/*    */     } 
/* 34 */     FontRender.drawString6(str, getPosX(), getPosY(), ((ColorSetting)this.color.getValue()).getRawColor(), true);
/*    */   }
/*    */   
/*    */   private float round(double value) {
/* 38 */     BigDecimal bd = new BigDecimal(value);
/* 39 */     bd = bd.setScale(1, RoundingMode.HALF_UP);
/* 40 */     return bd.floatValue();
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void updateValues(EventSync e) {
/* 45 */     double distTraveledLastTickX = mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q;
/* 46 */     double distTraveledLastTickZ = mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s;
/* 47 */     this.speedometerCurrentSpeed = distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public double turnIntoKpH(double input) {
/* 52 */     return MathHelper.func_76133_a(input) * 71.2729367892D;
/*    */   }
/*    */   
/*    */   public double getSpeedKpH() {
/* 56 */     double speedometerkphdouble = turnIntoKpH(this.speedometerCurrentSpeed);
/* 57 */     speedometerkphdouble = Math.round(10.0D * speedometerkphdouble) / 10.0D;
/* 58 */     return speedometerkphdouble;
/*    */   }
/*    */   
/*    */   public double getSpeedMpS() {
/* 62 */     double speedometerMpsdouble = turnIntoKpH(this.speedometerCurrentSpeed) / 3.6D;
/* 63 */     speedometerMpsdouble = Math.round(10.0D * speedometerMpsdouble) / 10.0D;
/* 64 */     return speedometerMpsdouble;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\Speedometer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */