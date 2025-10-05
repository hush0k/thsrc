/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.client.MultiConnect;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiListExtended;
/*    */ import net.minecraft.client.gui.GuiMultiplayer;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.ServerListEntryNormal;
/*    */ import net.minecraft.client.gui.ServerSelectionList;
/*    */ import net.minecraftforge.fml.client.FMLClientHandler;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiMultiplayer.class})
/*    */ public abstract class MixinGuiMultiplayer
/*    */   extends GuiScreen
/*    */ {
/*    */   @Inject(method = {"createButtons"}, at = {@At("HEAD")})
/*    */   public void dobovlyaemhuiny(CallbackInfo ci) {
/* 25 */     if (MultiConnect.getInstance().isEnabled()) {
/* 26 */       IGuiScreen screen = (IGuiScreen)this;
/*    */       
/* 28 */       List<GuiButton> buttonList = screen.getButtonList();
/* 29 */       buttonList.add(new GuiButton(22810007, this.field_146294_l / 2 + 4 + 76 + 95, this.field_146295_m - 52, 98, 20, "MultiConnect"));
/* 30 */       buttonList.add(new GuiButton(1337339, this.field_146294_l / 2 + 4 + 76 + 95, this.field_146295_m - 28, 98, 20, "Clear Selected"));
/* 31 */       screen.setButtonList(buttonList);
/*    */     } 
/*    */   }
/*    */   @Shadow
/*    */   private ServerSelectionList field_146803_h;
/*    */   @Inject(method = {"actionPerformed"}, at = {@At("RETURN")})
/*    */   public void chekarmknopki(GuiButton button, CallbackInfo ci) {
/* 38 */     if (MultiConnect.getInstance().isEnabled()) {
/* 39 */       if (button.field_146127_k == 1337339) {
/* 40 */         (MultiConnect.getInstance()).serverData.clear();
/*    */       }
/*    */       
/* 43 */       if (button.field_146127_k == 22810007) {
/* 44 */         if (!(MultiConnect.getInstance()).serverData.isEmpty()) {
/* 45 */           for (Iterator<Integer> iterator = (MultiConnect.getInstance()).serverData.iterator(); iterator.hasNext(); ) { int serv = ((Integer)iterator.next()).intValue();
/* 46 */             connectToSelected(serv); }
/*    */         
/*    */         } else {
/* 49 */           System.out.println("THUNDER ERROR!!!  Бля выбери серверы");
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void connectToSelected(int pizda) {
/* 57 */     if (MultiConnect.getInstance().isEnabled()) {
/* 58 */       GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (pizda < 0) ? null : this.field_146803_h.func_148180_b(pizda);
/* 59 */       if (guilistextended$iguilistentry instanceof ServerListEntryNormal)
/* 60 */         FMLClientHandler.instance().connectToServer(this, ((ServerListEntryNormal)guilistextended$iguilistentry).func_148296_a()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinGuiMultiplayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */