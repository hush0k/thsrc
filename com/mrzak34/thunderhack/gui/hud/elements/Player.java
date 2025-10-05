/*    */ package com.mrzak34.thunderhack.gui.hud.elements;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Player
/*    */   extends HudElement
/*    */ {
/* 22 */   public Setting<Integer> scale = register(new Setting("Scale", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(200)));
/* 23 */   public Setting<Boolean> yw = register(new Setting("Yaw", Boolean.valueOf(true)));
/* 24 */   public Setting<Boolean> pch = register(new Setting("Pitch", Boolean.valueOf(true)));
/*    */ 
/*    */ 
/*    */   
/*    */   public Player() {
/* 29 */     super("PlayerView", "Player", 100, 100);
/*    */   }
/*    */   
/*    */   public static void drawPlayerOnScreen(int x, int y, int scale, float mouseX, float mouseY, EntityPlayer player, boolean yaw, boolean pitch) {
/* 33 */     GlStateManager.func_179094_E();
/* 34 */     GlStateManager.func_179126_j();
/* 35 */     GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
/* 36 */     GlStateManager.func_179142_g();
/* 37 */     GlStateManager.func_179094_E();
/* 38 */     GlStateManager.func_179109_b(x, y, 50.0F);
/* 39 */     GlStateManager.func_179152_a(-scale, scale, scale);
/* 40 */     GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
/* 41 */     float f = player.field_70761_aq;
/* 42 */     float f1 = player.field_70177_z;
/* 43 */     float f2 = player.field_70125_A;
/* 44 */     float f3 = player.field_70758_at;
/* 45 */     float f4 = player.field_70759_as;
/* 46 */     GlStateManager.func_179114_b(135.0F, 0.0F, 1.0F, 0.0F);
/* 47 */     RenderHelper.func_74519_b();
/* 48 */     GlStateManager.func_179114_b(-135.0F, 0.0F, 1.0F, 0.0F);
/* 49 */     mouseX = yaw ? (player.field_70177_z * -1.0F) : mouseX;
/* 50 */     mouseY = pitch ? (player.field_70125_A * -1.0F) : mouseY;
/* 51 */     GlStateManager.func_179114_b(-((float)Math.atan((mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
/* 52 */     if (!yaw) {
/* 53 */       player.field_70761_aq = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
/* 54 */       player.field_70177_z = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
/* 55 */       player.field_70759_as = player.field_70177_z;
/* 56 */       player.field_70758_at = player.field_70177_z;
/*    */     } 
/* 58 */     if (!pitch) {
/* 59 */       player.field_70125_A = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
/*    */     }
/* 61 */     GlStateManager.func_179109_b(0.0F, 0.0F, 0.0F);
/* 62 */     RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
/* 63 */     rendermanager.func_178631_a(180.0F);
/* 64 */     rendermanager.func_178633_a(false);
/* 65 */     rendermanager.func_188391_a((Entity)player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
/* 66 */     rendermanager.func_178633_a(true);
/* 67 */     if (!yaw) {
/* 68 */       player.field_70761_aq = f;
/* 69 */       player.field_70177_z = f1;
/* 70 */       player.field_70758_at = f3;
/* 71 */       player.field_70759_as = f4;
/*    */     } 
/* 73 */     if (!pitch) {
/* 74 */       player.field_70125_A = f2;
/*    */     }
/* 76 */     GlStateManager.func_179121_F();
/* 77 */     RenderHelper.func_74518_a();
/* 78 */     GlStateManager.func_179101_C();
/* 79 */     GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
/* 80 */     GlStateManager.func_179090_x();
/* 81 */     GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
/* 82 */     GlStateManager.func_179097_i();
/* 83 */     GlStateManager.func_179121_F();
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 88 */     super.onRender2D(e);
/* 89 */     drawPlayerOnScreen((int)getPosX(), (int)getPosY(), ((Integer)this.scale.getValue()).intValue(), -30.0F, 0.0F, (EntityPlayer)(Minecraft.func_71410_x()).field_71439_g, ((Boolean)this.yw.getValue()).booleanValue(), ((Boolean)this.pch.getValue()).booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\Player.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */