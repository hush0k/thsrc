/*     */ package com.mrzak34.thunderhack.setting;
/*     */ 
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ public class Setting<T>
/*     */ {
/*     */   private final String name;
/*     */   private final T defaultValue;
/*     */   private T value;
/*     */   private T plannedValue;
/*     */   private T min;
/*     */   private T max;
/*  15 */   private Setting<Parent> parent = null;
/*     */   
/*     */   private boolean hasRestriction;
/*     */   
/*     */   private Predicate<T> visibility;
/*     */   private String description;
/*     */   private Module module;
/*     */   
/*     */   public Setting(String name, T defaultValue) {
/*  24 */     this.name = name;
/*  25 */     this.defaultValue = defaultValue;
/*  26 */     this.value = defaultValue;
/*  27 */     this.plannedValue = defaultValue;
/*  28 */     this.description = "";
/*     */   }
/*     */ 
/*     */   
/*     */   public Setting(String name, T defaultValue, T min, T max) {
/*  33 */     this.name = name;
/*  34 */     this.defaultValue = defaultValue;
/*  35 */     this.value = defaultValue;
/*  36 */     this.min = min;
/*  37 */     this.max = max;
/*  38 */     this.plannedValue = defaultValue;
/*  39 */     this.description = "";
/*  40 */     this.hasRestriction = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Setting(String name, T defaultValue, T min, T max, Predicate<T> visibility) {
/*  45 */     this.name = name;
/*  46 */     this.defaultValue = defaultValue;
/*  47 */     this.value = defaultValue;
/*  48 */     this.min = min;
/*  49 */     this.max = max;
/*  50 */     this.plannedValue = defaultValue;
/*  51 */     this.visibility = visibility;
/*  52 */     this.description = "";
/*  53 */     this.hasRestriction = true;
/*     */   }
/*     */   
/*     */   public Setting(String name, T defaultValue, Predicate<T> visibility) {
/*  57 */     this.name = name;
/*  58 */     this.defaultValue = defaultValue;
/*  59 */     this.value = defaultValue;
/*  60 */     this.visibility = visibility;
/*  61 */     this.plannedValue = defaultValue;
/*     */   }
/*     */   
/*     */   public static Enum get(Enum clazz) {
/*  65 */     int index = EnumConverter.currentEnum(clazz);
/*  66 */     for (int i = 0; i < ((Enum[])clazz.getClass().getEnumConstants()).length; ) {
/*  67 */       Enum e = ((Enum[])clazz.getClass().getEnumConstants())[i];
/*  68 */       if (i != index + 1) { i++; continue; }
/*  69 */        return e;
/*     */     } 
/*  71 */     return ((Enum[])clazz.getClass().getEnumConstants())[0];
/*     */   }
/*     */   
/*     */   public String getName() {
/*  75 */     return this.name;
/*     */   }
/*     */   
/*     */   public T getValue() {
/*  79 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setValue(T value) {
/*  83 */     setPlannedValue(value);
/*  84 */     if (this.hasRestriction) {
/*  85 */       if (((Number)this.min).floatValue() > ((Number)value).floatValue()) {
/*  86 */         setPlannedValue(this.min);
/*     */       }
/*  88 */       if (((Number)this.max).floatValue() < ((Number)value).floatValue()) {
/*  89 */         setPlannedValue(this.max);
/*     */       }
/*     */     } 
/*  92 */     this.value = this.plannedValue;
/*     */   }
/*     */   
/*     */   public float getPow2Value() {
/*  96 */     if (this.value instanceof Float) {
/*  97 */       return ((Float)this.value).floatValue() * ((Float)this.value).floatValue();
/*     */     }
/*  99 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public void setPlannedValue(T value) {
/* 103 */     this.plannedValue = value;
/*     */   }
/*     */   
/*     */   public T getMin() {
/* 107 */     return this.min;
/*     */   }
/*     */   
/*     */   public void setMin(T min) {
/* 111 */     this.min = min;
/*     */   }
/*     */   
/*     */   public T getMax() {
/* 115 */     return this.max;
/*     */   }
/*     */   
/*     */   public void setMax(T max) {
/* 119 */     this.max = max;
/*     */   }
/*     */   
/*     */   public Module getModule() {
/* 123 */     return this.module;
/*     */   }
/*     */   
/*     */   public void setModule(Module module) {
/* 127 */     this.module = module;
/*     */   }
/*     */   
/*     */   public String currentEnumName() {
/* 131 */     return EnumConverter.getProperName((Enum)this.value);
/*     */   }
/*     */   
/*     */   public String[] getModes() {
/* 135 */     return EnumConverter.getNames((Enum)this.value);
/*     */   }
/*     */   
/*     */   public void setEnum(Enum mod) {
/* 139 */     this.plannedValue = (T)mod;
/*     */   }
/*     */   
/*     */   public void increaseEnum() {
/* 143 */     this.plannedValue = (T)EnumConverter.increaseEnum((Enum)this.value);
/* 144 */     this.value = this.plannedValue;
/*     */   }
/*     */   
/*     */   public void setEnumByNumber(int id) {
/* 148 */     this.plannedValue = (T)EnumConverter.setEnumInt((Enum)this.value, id);
/* 149 */     this.value = this.plannedValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/* 154 */     if (isEnumSetting()) {
/* 155 */       return "Enum";
/*     */     }
/* 157 */     if (isColorSetting()) {
/* 158 */       return "ColorSetting";
/*     */     }
/* 160 */     if (isPositionSetting()) {
/* 161 */       return "PositionSetting";
/*     */     }
/*     */     
/* 164 */     return getClassName(this.defaultValue);
/*     */   }
/*     */   
/*     */   public <T> String getClassName(T value) {
/* 168 */     return value.getClass().getSimpleName();
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 172 */     if (this.description == null) {
/* 173 */       return "";
/*     */     }
/* 175 */     return this.description;
/*     */   }
/*     */   
/*     */   public boolean isNumberSetting() {
/* 179 */     return (this.value instanceof Double || this.value instanceof Integer || this.value instanceof Short || this.value instanceof Long || this.value instanceof Float);
/*     */   }
/*     */   
/*     */   public boolean isColorHeader() {
/* 183 */     return this.value instanceof ColorSettingHeader;
/*     */   }
/*     */   
/*     */   public boolean isInteger() {
/* 187 */     return this.value instanceof Integer;
/*     */   }
/*     */   
/*     */   public boolean isFloat() {
/* 191 */     return this.value instanceof Float;
/*     */   }
/*     */   
/*     */   public boolean isEnumSetting() {
/* 195 */     return (!isPositionSetting() && !isColorHeader() && !isNumberSetting() && !(this.value instanceof PositionSetting) && !(this.value instanceof String) && !(this.value instanceof ColorSetting) && !(this.value instanceof Parent) && !(this.value instanceof Bind) && !(this.value instanceof SubBind) && !(this.value instanceof Character) && !(this.value instanceof Boolean));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBindSetting() {
/* 200 */     return this.value instanceof Bind;
/*     */   }
/*     */   
/*     */   public boolean isStringSetting() {
/* 204 */     return this.value instanceof String;
/*     */   }
/*     */   
/*     */   public boolean isColorSetting() {
/* 208 */     return this.value instanceof ColorSetting;
/*     */   }
/*     */   
/*     */   public boolean isPositionSetting() {
/* 212 */     return this.value instanceof PositionSetting;
/*     */   }
/*     */   
/*     */   public T getDefaultValue() {
/* 216 */     return this.defaultValue;
/*     */   }
/*     */   
/*     */   public String getValueAsString() {
/* 220 */     return this.value.toString();
/*     */   }
/*     */   
/*     */   public boolean hasRestriction() {
/* 224 */     return this.hasRestriction;
/*     */   }
/*     */   
/*     */   public Setting<T> withParent(Setting<Parent> parent) {
/* 228 */     this.parent = parent;
/* 229 */     return this;
/*     */   }
/*     */   
/*     */   public Setting<Parent> getParent() {
/* 233 */     return this.parent;
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 237 */     if (this.parent != null && 
/* 238 */       !((Parent)this.parent.getValue()).isExtended()) {
/* 239 */       return false;
/*     */     }
/*     */     
/* 242 */     if (this.visibility == null) {
/* 243 */       return true;
/*     */     }
/* 245 */     return this.visibility.test(getValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\setting\Setting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */