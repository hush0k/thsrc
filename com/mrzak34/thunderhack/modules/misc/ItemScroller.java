/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ public class ItemScroller
/*    */   extends Module {
/*  8 */   public Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(1000)));
/*    */   
/*    */   public ItemScroller() {
/* 11 */     super("ItemScroller", "Позволяет быстро-перекладывать-предметы", Module.Category.MISC);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\ItemScroller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */