/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.ClickBlockEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ public class NoInteract
/*    */   extends Module
/*    */ {
/*    */   public NoInteract() {
/* 14 */     super("NoInteract", "не посылать пакеты-использования блоков", Module.Category.PLAYER);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onInteract(ClickBlockEvent.Right event) {
/* 19 */     Block block = mc.field_71441_e.func_180495_p(event.getPos()).func_177230_c();
/* 20 */     if (block == Blocks.field_150467_bQ || block == Blocks.field_150462_ai || block == Blocks.field_180387_bt || block == Blocks.field_180392_bq || block == Blocks.field_180385_bs || block == Blocks.field_180386_br || block == Blocks.field_180391_bp || block == Blocks.field_180390_bo || block == Blocks.field_150486_ae || block == Blocks.field_150477_bB || block == Blocks.field_150381_bn || block == 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 31 */       Block.func_149729_e(63) || block == Blocks.field_150460_al || block == Blocks.field_150470_am)
/*    */     {
/*    */ 
/*    */       
/* 35 */       event.setCanceled(true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\NoInteract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */