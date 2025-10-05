/*    */ package com.mrzak34.thunderhack.setting;
/*    */ 
/*    */ import com.google.common.base.Converter;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class EnumConverter
/*    */   extends Converter<Enum, JsonElement>
/*    */ {
/*    */   private final Class<? extends Enum> clazz;
/*    */   
/*    */   public EnumConverter(Class<? extends Enum> clazz) {
/* 14 */     this.clazz = clazz;
/*    */   }
/*    */   
/*    */   public static int currentEnum(Enum clazz) {
/* 18 */     for (int i = 0; i < ((Enum[])clazz.getClass().getEnumConstants()).length; ) {
/* 19 */       Enum e = ((Enum[])clazz.getClass().getEnumConstants())[i];
/* 20 */       if (!e.name().equalsIgnoreCase(clazz.name())) { i++; continue; }
/* 21 */        return i;
/*    */     } 
/* 23 */     return -1;
/*    */   }
/*    */   
/*    */   public static int getEnumInt(Enum clazz) {
/* 27 */     return ((Enum[])clazz.getClass().getEnumConstants()).length;
/*    */   }
/*    */   
/*    */   public static Enum increaseEnum(Enum clazz) {
/* 31 */     int index = currentEnum(clazz);
/* 32 */     for (int i = 0; i < ((Enum[])clazz.getClass().getEnumConstants()).length; ) {
/* 33 */       Enum e = ((Enum[])clazz.getClass().getEnumConstants())[i];
/* 34 */       if (i != index + 1) { i++; continue; }
/* 35 */        return e;
/*    */     } 
/* 37 */     return ((Enum[])clazz.getClass().getEnumConstants())[0];
/*    */   }
/*    */   
/*    */   public static Enum setEnumInt(Enum clazz, int id) {
/* 41 */     for (int i = 0; i < ((Enum[])clazz.getClass().getEnumConstants()).length; ) {
/* 42 */       Enum e = ((Enum[])clazz.getClass().getEnumConstants())[i];
/* 43 */       if (i != id) { i++; continue; }
/* 44 */        return e;
/*    */     } 
/* 46 */     return ((Enum[])clazz.getClass().getEnumConstants())[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] getNames(Enum clazz) {
/* 51 */     return (String[])Arrays.stream(clazz.getClass().getEnumConstants()).map(Enum::name).toArray(x$0 -> new String[x$0]);
/*    */   }
/*    */   
/*    */   public static Enum naoborot(Enum clazz) {
/* 55 */     int index = currentEnum(clazz);
/* 56 */     for (int i = 0; i < ((Enum[])clazz.getClass().getEnumConstants()).length; ) {
/* 57 */       Enum e = ((Enum[])clazz.getClass().getEnumConstants())[i];
/* 58 */       if (i != index - 1) { i++; continue; }
/* 59 */        return e;
/*    */     } 
/* 61 */     return ((Enum[])clazz.getClass().getEnumConstants())[0];
/*    */   }
/*    */   
/*    */   public static String getProperName(Enum clazz) {
/* 65 */     return Character.toUpperCase(clazz.name().charAt(0)) + clazz.name().toLowerCase().substring(1);
/*    */   }
/*    */   
/*    */   public JsonElement doForward(Enum anEnum) {
/* 69 */     return (JsonElement)new JsonPrimitive(anEnum.toString());
/*    */   }
/*    */   
/*    */   public Enum doBackward(JsonElement jsonElement) {
/*    */     try {
/* 74 */       return Enum.valueOf((Class)this.clazz, jsonElement.getAsString());
/* 75 */     } catch (IllegalArgumentException e) {
/* 76 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\setting\EnumConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */