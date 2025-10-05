/*    */ package com.mrzak34.thunderhack.macro;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class Macro
/*    */ {
/*    */   private final String name;
/*    */   private String text;
/*    */   private int bind;
/*    */   
/*    */   public Macro(String name, String text, int bind) {
/* 12 */     this.name = name;
/* 13 */     this.text = text;
/* 14 */     this.bind = bind;
/*    */   }
/*    */   
/*    */   public void runMacro() {
/* 18 */     (Minecraft.func_71410_x()).field_71439_g.func_71165_d(this.text);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 22 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 26 */     return this.text;
/*    */   }
/*    */   
/*    */   public void setText(String text) {
/* 30 */     this.text = text;
/*    */   }
/*    */   
/*    */   public int getBind() {
/* 34 */     return this.bind;
/*    */   }
/*    */   
/*    */   public void setBind(int bind) {
/* 38 */     this.bind = bind;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 43 */     if (obj instanceof Macro) {
/* 44 */       return getName().equalsIgnoreCase(((Macro)obj).getName());
/*    */     }
/* 46 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\macro\Macro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */