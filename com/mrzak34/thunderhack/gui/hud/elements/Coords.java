/*    */ package com.mrzak34.thunderhack.gui.hud.elements;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Coords
/*    */   extends HudElement
/*    */ {
/* 18 */   public final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(-2013200640)));
/*    */   
/*    */   public Coords() {
/* 21 */     super("Coords", "coords", 100, 10);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 26 */     super.onRender2D(e);
/* 27 */     boolean inHell = mc.field_71441_e.func_180494_b(mc.field_71439_g.func_180425_c()).func_185359_l().equals("Hell");
/* 28 */     int posX = (int)mc.field_71439_g.field_70165_t;
/* 29 */     int posY = (int)mc.field_71439_g.field_70163_u;
/* 30 */     int posZ = (int)mc.field_71439_g.field_70161_v;
/* 31 */     float nether = !inHell ? 0.125F : 8.0F;
/* 32 */     int hposX = (int)(mc.field_71439_g.field_70165_t * nether);
/* 33 */     int hposZ = (int)(mc.field_71439_g.field_70161_v * nether);
/* 34 */     String coordinates = ChatFormatting.WHITE + "XYZ " + ChatFormatting.RESET + (inHell ? (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]" + ChatFormatting.RESET) : (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]"));
/* 35 */     FontRender.drawString6(coordinates, getPosX(), getPosY(), ((ColorSetting)this.color.getValue()).getRawColor(), false);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\Coords.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */