/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoEntityTrace
/*    */   extends Module
/*    */ {
/* 15 */   private static NoEntityTrace INSTANCE = new NoEntityTrace();
/*    */ 
/*    */   
/* 18 */   public Setting<Boolean> pick = register(new Setting("Pick", Boolean.valueOf(true)));
/* 19 */   public Setting<Boolean> gap = register(new Setting("Gap", Boolean.valueOf(false)));
/* 20 */   public Setting<Boolean> obby = register(new Setting("Obby", Boolean.valueOf(false)));
/*    */   public boolean noTrace;
/*    */   
/*    */   public NoEntityTrace() {
/* 24 */     super("NoEntityTrace", "копать сквозь игроков", Module.Category.PLAYER);
/* 25 */     setInstance();
/*    */   }
/*    */   
/*    */   public static NoEntityTrace getINSTANCE() {
/* 29 */     if (INSTANCE == null) {
/* 30 */       INSTANCE = new NoEntityTrace();
/*    */     }
/* 32 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 36 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 41 */     Item item = mc.field_71439_g.func_184614_ca().func_77973_b();
/* 42 */     if (item instanceof net.minecraft.item.ItemPickaxe && ((Boolean)this.pick.getValue()).booleanValue()) {
/* 43 */       this.noTrace = true;
/*    */       return;
/*    */     } 
/* 46 */     if (item == Items.field_151153_ao && ((Boolean)this.gap.getValue()).booleanValue()) {
/* 47 */       this.noTrace = true;
/*    */       return;
/*    */     } 
/* 50 */     if (item instanceof ItemBlock) {
/* 51 */       this.noTrace = (((ItemBlock)item).func_179223_d() == Blocks.field_150343_Z && ((Boolean)this.obby.getValue()).booleanValue());
/*    */       return;
/*    */     } 
/* 54 */     this.noTrace = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\NoEntityTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */