/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import com.mrzak34.thunderhack.util.render.Drawable;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class DurabilityAlert
/*    */   extends Module {
/*    */   public Setting<Boolean> friends;
/*    */   public Setting<Integer> percent;
/*    */   
/*    */   public DurabilityAlert() {
/* 23 */     super("DurabilityAlert", "предупреждает о-прочности брони", "durability alert", Module.Category.PLAYER);
/*    */ 
/*    */     
/* 26 */     this.friends = register(new Setting("Friend message", Boolean.valueOf(true)));
/* 27 */     this.percent = register(new Setting("Percent", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(100)));
/* 28 */     this.ICON = new ResourceLocation("textures/broken_shield.png");
/* 29 */     this.need_alert = false;
/* 30 */     this.timer = new Timer();
/*    */   }
/*    */   private final ResourceLocation ICON; private boolean need_alert; private Timer timer;
/*    */   public void onUpdate() {
/* 34 */     if (((Boolean)this.friends.getValue()).booleanValue()) {
/* 35 */       for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/* 36 */         if (!Thunderhack.friendManager.isFriend(player) || 
/* 37 */           player == mc.field_71439_g)
/* 38 */           continue;  for (ItemStack stack : player.field_71071_by.field_70460_b) {
/* 39 */           if (!stack.func_190926_b() && 
/* 40 */             getDurability(stack) < ((Integer)this.percent.getValue()).intValue() && this.timer.passedMs(30000L)) {
/* 41 */             if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 42 */               mc.field_71439_g.func_71165_d("/msg " + player.func_70005_c_() + " Срочно чини броню!");
/*    */             } else {
/* 44 */               mc.field_71439_g.func_71165_d("/msg " + player.func_70005_c_() + " Repair your armor immediately!");
/*    */             } 
/* 46 */             this.timer.reset();
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     }
/*    */     
/* 52 */     boolean flag = false;
/* 53 */     for (ItemStack stack : mc.field_71439_g.field_71071_by.field_70460_b) {
/* 54 */       if (!stack.func_190926_b() && 
/* 55 */         getDurability(stack) < ((Integer)this.percent.getValue()).intValue()) {
/* 56 */         this.need_alert = true;
/* 57 */         flag = true;
/*    */       } 
/*    */     } 
/* 60 */     if (!flag && this.need_alert) {
/* 61 */       this.need_alert = false;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 68 */     if (this.need_alert) {
/* 69 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 70 */         FontRender.drawCentString6("Срочно чини броню!", (float)e.getScreenWidth() / 2.0F, (float)e.getScreenHeight() / 3.0F, (new Color(16768768)).getRGB());
/* 71 */         Drawable.drawTexture(this.ICON, ((float)e.getScreenWidth() / 2.0F - 40.0F), ((float)e.getScreenHeight() / 3.0F - 120.0F), 80.0D, 80.0D, new Color(16768768));
/*    */       } else {
/* 73 */         FontRender.drawCentString6("Repair your armor immediately!", (float)e.getScreenWidth() / 2.0F, (float)e.getScreenHeight() / 3.0F, (new Color(16768768)).getRGB());
/* 74 */         Drawable.drawTexture(this.ICON, ((float)e.getScreenWidth() / 2.0F - 40.0F), ((float)e.getScreenHeight() / 3.0F - 120.0F), 80.0D, 80.0D, new Color(16768768));
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getDurability(ItemStack stack) {
/* 81 */     return (int)((stack.func_77958_k() - stack.func_77952_i()) / Math.max(0.1D, stack.func_77958_k()) * 100.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\DurabilityAlert.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */