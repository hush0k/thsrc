/*    */ package com.mrzak34.thunderhack.setting;
/*    */ 
/*    */ import com.google.common.base.Converter;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class Bind {
/*    */   private int key;
/*    */   private boolean hold = false;
/*    */   
/*    */   public Bind(int key) {
/* 13 */     this.key = key;
/*    */   }
/*    */   
/*    */   public static Bind none() {
/* 17 */     return new Bind(-1);
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
/*    */   public boolean isHold() {
/* 48 */     return this.hold;
/*    */   }
/*    */   
/*    */   public void setHold(boolean hold) {
/* 52 */     this.hold = hold;
/*    */   }
/*    */   
/*    */   public static class BindConverter extends Converter<Bind, JsonElement> {
/*    */     public JsonElement doForward(Bind bind) {
/* 57 */       return (JsonElement)new JsonPrimitive(bind.toString());
/*    */     }
/*    */     
/*    */     public Bind doBackward(JsonElement jsonElement) {
/* 61 */       String s = jsonElement.getAsString();
/* 62 */       if (s.equalsIgnoreCase("None")) {
/* 63 */         return Bind.none();
/*    */       }
/* 65 */       int key = -1;
/*    */       try {
/* 67 */         key = Keyboard.getKeyIndex(s.toUpperCase());
/* 68 */       } catch (Exception exception) {}
/*    */ 
/*    */       
/* 71 */       if (key == 0) {
/* 72 */         return Bind.none();
/*    */       }
/* 74 */       return new Bind(key);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\setting\Bind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */