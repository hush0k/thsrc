/*    */ package com.mrzak34.thunderhack.gui.hud.elements;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArmorHud
/*    */   extends HudElement
/*    */ {
/*    */   public ArmorHud() {
/* 23 */     super("ArmorHud", "armorhud", 100, 20);
/*    */   }
/*    */   
/*    */   public static float calculatePercentage(ItemStack stack) {
/* 27 */     float durability = (stack.func_77958_k() - stack.func_77952_i());
/* 28 */     return durability / stack.func_77958_k() * 100.0F;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 33 */     super.onRender2D(e);
/* 34 */     GlStateManager.func_179098_w();
/*    */     
/* 36 */     int iteration = 0;
/*    */     
/* 38 */     for (ItemStack is : mc.field_71439_g.field_71071_by.field_70460_b) {
/* 39 */       iteration++;
/* 40 */       if (is.func_190926_b())
/*    */         continue; 
/* 42 */       int x = (int)(getPosX() - 90.0F + ((9 - iteration) * 20) + 2.0F);
/* 43 */       GlStateManager.func_179126_j();
/* 44 */       RenderUtil.itemRender.field_77023_b = 200.0F;
/* 45 */       RenderUtil.itemRender.func_180450_b(is, x, (int)getPosY());
/* 46 */       RenderUtil.itemRender.func_180453_a(mc.field_71466_p, is, x, (int)getPosY(), "");
/* 47 */       RenderUtil.itemRender.field_77023_b = 0.0F;
/* 48 */       GlStateManager.func_179098_w();
/* 49 */       GlStateManager.func_179140_f();
/* 50 */       GlStateManager.func_179097_i();
/* 51 */       String s = (is.func_190916_E() > 1) ? (is.func_190916_E() + "") : "";
/* 52 */       mc.field_71466_p.func_175063_a(s, (x + 19 - 2 - mc.field_71466_p.func_78256_a(s)), getPosY() + 9.0F, 16777215);
/* 53 */       int dmg = (int)calculatePercentage(is);
/* 54 */       mc.field_71466_p.func_175063_a(dmg + "", (x + 8) - mc.field_71466_p.func_78256_a(dmg + "") / 2.0F, getPosY() - 11.0F, (new Color(0, 255, 0)).getRGB());
/*    */     } 
/* 56 */     GlStateManager.func_179126_j();
/* 57 */     GlStateManager.func_179140_f();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\ArmorHud.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */