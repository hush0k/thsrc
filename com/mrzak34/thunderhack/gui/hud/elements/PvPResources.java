/*    */ package com.mrzak34.thunderhack.gui.hud.elements;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.RoundedShader;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
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
/*    */ public class PvPResources
/*    */   extends HudElement
/*    */ {
/* 28 */   public final Setting<ColorSetting> shadowColor = register(new Setting("ShadowColor", new ColorSetting(-15724528)));
/* 29 */   public final Setting<ColorSetting> color2 = register(new Setting("Color", new ColorSetting(-15724528)));
/* 30 */   public final Setting<ColorSetting> color3 = register(new Setting("Color2", new ColorSetting(-979657829)));
/*    */   public PvPResources() {
/* 32 */     super("PvPResources", "PvPResources", 42, 42);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 37 */     super.onRender2D(e);
/*    */     
/* 39 */     RenderUtil.drawBlurredShadow(getPosX(), getPosY(), 42.0F, 42.0F, 20, ((ColorSetting)this.shadowColor.getValue()).getColorObject());
/* 40 */     RoundedShader.drawRound(getPosX(), getPosY(), 42.0F, 42.0F, 7.0F, ((ColorSetting)this.color2.getValue()).getColorObject());
/*    */ 
/*    */     
/* 43 */     int n2 = Method492(Items.field_190929_cY);
/* 44 */     int n3 = Method492(Items.field_151062_by);
/* 45 */     int n4 = Method492(Items.field_185158_cP);
/* 46 */     int n5 = Method492(Items.field_151153_ao);
/*    */     
/* 48 */     List<ItemStack> list = new ArrayList<>();
/*    */     
/* 50 */     if (n2 > 0) {
/* 51 */       list.add(new ItemStack(Items.field_190929_cY, n2));
/*    */     }
/* 53 */     if (n3 > 0) {
/* 54 */       list.add(new ItemStack(Items.field_151062_by, n3));
/*    */     }
/* 56 */     if (n4 > 0) {
/* 57 */       list.add(new ItemStack(Items.field_185158_cP, n4));
/*    */     }
/* 59 */     if (n5 > 0) {
/* 60 */       list.add(new ItemStack(Items.field_151153_ao, n5, 1));
/*    */     }
/*    */     
/* 63 */     int n6 = list.size();
/* 64 */     for (int i = 0; i < n6; i++) {
/* 65 */       GlStateManager.func_179094_E();
/* 66 */       GlStateManager.func_179132_a(true);
/* 67 */       GlStateManager.func_179086_m(256);
/* 68 */       RenderHelper.func_74519_b();
/* 69 */       (mc.func_175599_af()).field_77023_b = -150.0F;
/* 70 */       GlStateManager.func_179118_c();
/* 71 */       GlStateManager.func_179126_j();
/* 72 */       GlStateManager.func_179129_p();
/* 73 */       int n7 = (int)getPosX();
/* 74 */       int n8 = (int)getPosY();
/* 75 */       ItemStack itemStack = list.get(i);
/* 76 */       n7 = i % 2 * 20;
/* 77 */       n8 = i / 2 * 20;
/* 78 */       mc.func_175599_af().func_180450_b(itemStack, (int)(getPosX() + n7 + 2.0F), (int)(getPosY() + n8 + 2.0F));
/* 79 */       mc.func_175599_af().func_175030_a(mc.field_71466_p, itemStack, (int)(getPosX() + n7 + 2.0F), (int)(getPosY() + n8 + 2.0F));
/* 80 */       (mc.func_175599_af()).field_77023_b = 0.0F;
/* 81 */       RenderHelper.func_74518_a();
/* 82 */       GlStateManager.func_179089_o();
/* 83 */       GlStateManager.func_179141_d();
/* 84 */       GlStateManager.func_179121_F();
/*    */     } 
/*    */   }
/*    */   
/*    */   public int Method492(Item item) {
/* 89 */     if (mc.field_71439_g == null) {
/* 90 */       return 0;
/*    */     }
/* 92 */     int n = 0;
/* 93 */     int n2 = 44;
/* 94 */     for (int i = 0; i <= n2; i++) {
/* 95 */       ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 96 */       if (itemStack.func_77973_b() == item)
/* 97 */         n += itemStack.func_190916_E(); 
/*    */     } 
/* 99 */     return n;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\PvPResources.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */