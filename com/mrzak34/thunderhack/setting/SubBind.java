/*    */ package com.mrzak34.thunderhack.setting;
/*    */ 
/*    */ import com.google.common.base.Converter;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class SubBind
/*    */ {
/*    */   private int key;
/*    */   
/*    */   public SubBind(int key) {
/* 13 */     this.key = key;
/*    */   }
/*    */   
/*    */   public static SubBind none() {
/* 17 */     return new SubBind(-1);
/*    */   }
/*    */   
/*    */   public int getKey() {
/* 21 */     return this.key;
/*    */   }
/*    */   
/*    */   public void setKey(int key) {
/* 25 */     this.key = key;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 29 */     return (this.key < 0);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 33 */     return isEmpty() ? "None" : ((this.key < 0) ? "None" : capitalise(Keyboard.getKeyName(this.key)));
/*    */   }
/*    */   
/*    */   public boolean isDown() {
/* 37 */     return (!isEmpty() && Keyboard.isKeyDown(getKey()));
/*    */   }
/*    */   
/*    */   private String capitalise(String str) {
/* 41 */     if (str.isEmpty()) {
/* 42 */       return "";
/*    */     }
/* 44 */     return Character.toUpperCase(str.charAt(0)) + ((str.length() != 1) ? str.substring(1).toLowerCase() : "");
/*    */   }
/*    */   
/*    */   public static class SubBindConverter
/*    */     extends Converter<SubBind, JsonElement> {
/*    */     public JsonElement doForward(SubBind subbind) {
/* 50 */       return (JsonElement)new JsonPrimitive(subbind.toString());
/*    */     }
/*    */     
/*    */     public SubBind doBackward(JsonElement jsonElement) {
/* 54 */       String s = jsonElement.getAsString();
/* 55 */       if (s.equalsIgnoreCase("None")) {
/* 56 */         return SubBind.none();
/*    */       }
/* 58 */       int key = -1;
/*    */       try {
/* 60 */         key = Keyboard.getKeyIndex(s.toUpperCase());
/* 61 */       } catch (Exception exception) {}
/*    */ 
/*    */       
/* 64 */       if (key == 0) {
/* 65 */         return SubBind.none();
/*    */       }
/* 67 */       return new SubBind(key);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\setting\SubBind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */