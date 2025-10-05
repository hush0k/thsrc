/*    */ package com.mrzak34.thunderhack.modules.client;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class MultiConnect
/*    */   extends Module
/*    */ {
/* 11 */   private static MultiConnect INSTANCE = new MultiConnect();
/* 12 */   public List<Integer> serverData = new ArrayList<>();
/*    */   
/*    */   public MultiConnect() {
/* 15 */     super("MultiConnect", "MultiConnect", Module.Category.CLIENT);
/* 16 */     setInstance();
/*    */   }
/*    */   
/*    */   public static MultiConnect getInstance() {
/* 20 */     if (INSTANCE == null) {
/* 21 */       INSTANCE = new MultiConnect();
/*    */     }
/* 23 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 27 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\client\MultiConnect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */