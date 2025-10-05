/*     */ package com.mrzak34.thunderhack.manager;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.EnumConverter;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.util.Scanner;
/*     */ import net.minecraft.block.Block;
/*     */ 
/*     */ public class ConfigManager implements Util {
/*  22 */   public static File MainFolder = new File(mc.field_71412_D, "ThunderHack");
/*  23 */   public static File ConfigsFolder = new File(MainFolder, "configs");
/*  24 */   public static File CustomImages = new File(MainFolder, "images");
/*  25 */   public static File TempFolder = new File(MainFolder, "temp");
/*  26 */   public static File SkinsFolder = new File(TempFolder, "skins");
/*  27 */   public static File CapesFolder = new File(TempFolder, "capes");
/*  28 */   public static File HeadsFolder = new File(TempFolder, "heads");
/*  29 */   public static File DiscordEmbeds = new File(TempFolder, "embeds");
/*  30 */   public static File MiscFolder = new File(MainFolder, "misc");
/*  31 */   public static File KitsFolder = new File(MiscFolder, "kits");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   public static File currentConfig = null;
/*     */   
/*     */   public static void init() {
/*  44 */     if (!MainFolder.exists()) MainFolder.mkdirs(); 
/*  45 */     if (!ConfigsFolder.exists()) ConfigsFolder.mkdirs(); 
/*  46 */     if (!CustomImages.exists()) CustomImages.mkdirs(); 
/*  47 */     if (!TempFolder.exists()) TempFolder.mkdirs(); 
/*  48 */     if (!SkinsFolder.exists()) SkinsFolder.mkdirs(); 
/*  49 */     if (!CapesFolder.exists()) CapesFolder.mkdirs(); 
/*  50 */     if (!HeadsFolder.exists()) HeadsFolder.mkdirs(); 
/*  51 */     if (!MiscFolder.exists()) MiscFolder.mkdirs(); 
/*  52 */     if (!KitsFolder.exists()) KitsFolder.mkdirs(); 
/*  53 */     if (!DiscordEmbeds.exists()) DiscordEmbeds.mkdirs();
/*     */   
/*     */   }
/*     */   
/*     */   public static String getConfigDate(String name) {
/*  58 */     File file = new File(ConfigsFolder, name + ".th");
/*  59 */     if (!file.exists()) {
/*  60 */       return "none";
/*     */     }
/*  62 */     long x = file.lastModified();
/*  63 */     DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm");
/*  64 */     Date sol = new Date(x);
/*  65 */     return obj.format(sol);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void load(String name) {
/*  70 */     File file = new File(ConfigsFolder, name + ".th");
/*  71 */     if (!file.exists()) {
/*  72 */       Command.sendMessage("Конфига " + name + " не существует!");
/*     */       
/*     */       return;
/*     */     } 
/*  76 */     if (currentConfig != null) {
/*  77 */       save(currentConfig);
/*     */     }
/*     */     
/*  80 */     Thunderhack.moduleManager.onUnload();
/*  81 */     Thunderhack.moduleManager.onUnloadPost();
/*  82 */     load(file);
/*  83 */     Thunderhack.moduleManager.onLoad();
/*     */   }
/*     */   
/*     */   public static void load(File config) {
/*  87 */     if (!config.exists())
/*  88 */       save(config); 
/*     */     try {
/*  90 */       FileReader reader = new FileReader(config);
/*  91 */       JsonParser parser = new JsonParser();
/*     */       
/*  93 */       JsonArray array = null;
/*     */       try {
/*  95 */         array = (JsonArray)parser.parse(reader);
/*  96 */       } catch (ClassCastException e) {
/*  97 */         save(config);
/*     */       } 
/*     */       
/* 100 */       JsonArray modules = null;
/*     */       try {
/* 102 */         JsonObject modulesObject = (JsonObject)array.get(0);
/* 103 */         modules = modulesObject.getAsJsonArray("Modules");
/* 104 */       } catch (Exception e) {
/* 105 */         System.err.println("Module Array not found, skipping!");
/*     */       } 
/*     */       
/* 108 */       if (modules != null) {
/* 109 */         modules.forEach(m -> {
/*     */               try {
/*     */                 parseModule(m.getAsJsonObject());
/* 112 */               } catch (NullPointerException e) {
/*     */                 System.err.println(e.getMessage());
/*     */               } 
/*     */             });
/*     */       }
/* 117 */       Command.sendMessage("Загружен конфиг " + config.getName());
/* 118 */     } catch (IOException e) {
/* 119 */       e.printStackTrace();
/*     */     } 
/* 121 */     currentConfig = config;
/* 122 */     saveCurrentConfig();
/*     */   }
/*     */   
/*     */   public static void save(String name) {
/* 126 */     File file = new File(ConfigsFolder, name + ".th");
/*     */     
/* 128 */     if (file.exists()) {
/* 129 */       Command.sendMessage("Конфиг " + name + " уже существует");
/*     */       
/*     */       return;
/*     */     } 
/* 133 */     save(file);
/* 134 */     Command.sendMessage("Конфиг " + name + " успешно сохранен!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void save(File config) {
/*     */     try {
/* 141 */       if (!config.exists()) {
/* 142 */         config.createNewFile();
/*     */       }
/* 144 */       JsonArray array = new JsonArray();
/*     */       
/* 146 */       JsonObject modulesObj = new JsonObject();
/* 147 */       modulesObj.add("Modules", (JsonElement)getModuleArray());
/* 148 */       array.add((JsonElement)modulesObj);
/*     */ 
/*     */       
/* 151 */       FileWriter writer = new FileWriter(config);
/* 152 */       Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*     */       
/* 154 */       gson.toJson((JsonElement)array, writer);
/* 155 */       writer.close();
/* 156 */     } catch (IOException e) {
/* 157 */       Command.sendMessage("Cant write to config file!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void parseModule(JsonObject object) throws NullPointerException {
/* 167 */     Module module = Thunderhack.moduleManager.modules.stream().filter(m -> (object.getAsJsonObject(m.getName()) != null)).findFirst().orElse(null);
/*     */     
/* 169 */     if (module != null) {
/* 170 */       JsonObject mobject = object.getAsJsonObject(module.getName());
/*     */       
/* 172 */       for (Setting setting2 : module.getSettings()) {
/*     */         try {
/* 174 */           JsonArray array4; JsonArray array; JsonArray array3; switch (setting2.getType()) {
/*     */ 
/*     */             
/*     */             case "Boolean":
/* 178 */               setting2.setValue(Boolean.valueOf(mobject.getAsJsonPrimitive(setting2.getName()).getAsBoolean()));
/*     */             
/*     */             case "Double":
/* 181 */               setting2.setValue(Double.valueOf(mobject.getAsJsonPrimitive(setting2.getName()).getAsDouble()));
/*     */             
/*     */             case "Float":
/* 184 */               setting2.setValue(Float.valueOf(mobject.getAsJsonPrimitive(setting2.getName()).getAsFloat()));
/*     */             
/*     */             case "Integer":
/* 187 */               setting2.setValue(Integer.valueOf(mobject.getAsJsonPrimitive(setting2.getName()).getAsInt()));
/*     */             
/*     */             case "String":
/* 190 */               setting2.setValue(mobject.getAsJsonPrimitive(setting2.getName()).getAsString().replace("_", " "));
/*     */             
/*     */             case "Bind":
/* 193 */               array4 = mobject.getAsJsonArray("Keybind");
/* 194 */               setting2.setValue((new Bind.BindConverter()).doBackward(array4.get(0)));
/* 195 */               ((Bind)setting2.getValue()).setHold(array4.get(1).getAsBoolean());
/*     */             
/*     */             case "ColorSetting":
/* 198 */               array = mobject.getAsJsonArray(setting2.getName());
/* 199 */               ((ColorSetting)setting2.getValue()).setColor(array.get(0).getAsInt());
/* 200 */               ((ColorSetting)setting2.getValue()).setCycle(array.get(1).getAsBoolean());
/* 201 */               ((ColorSetting)setting2.getValue()).setGlobalOffset(array.get(2).getAsInt());
/*     */             
/*     */             case "PositionSetting":
/* 204 */               array3 = mobject.getAsJsonArray(setting2.getName());
/* 205 */               ((PositionSetting)setting2.getValue()).setX(array3.get(0).getAsFloat());
/* 206 */               ((PositionSetting)setting2.getValue()).setY(array3.get(1).getAsFloat());
/*     */             
/*     */             case "SubBind":
/* 209 */               setting2.setValue((new SubBind.SubBindConverter()).doBackward((JsonElement)mobject.getAsJsonPrimitive(setting2.getName())));
/*     */             
/*     */             case "Enum":
/*     */               try {
/* 213 */                 EnumConverter converter = new EnumConverter(((Enum)setting2.getValue()).getClass());
/* 214 */                 Enum value = converter.doBackward((JsonElement)mobject.getAsJsonPrimitive(setting2.getName()));
/* 215 */                 setting2.setValue((value == null) ? setting2.getDefaultValue() : value);
/* 216 */               } catch (Exception exception) {}
/*     */           } 
/*     */         
/* 219 */         } catch (Exception e) {
/* 220 */           System.out.println(module.getName());
/* 221 */           System.out.println(setting2);
/* 222 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String parseLastServer(JsonObject object) throws NullPointerException {
/* 230 */     Command.sendMessage(object.getAsString());
/* 231 */     JsonObject mobject = object.getAsJsonObject("ClientSettings");
/* 232 */     return mobject.getAsJsonPrimitive("LastConfigServer").getAsString().replace("_", " ");
/*     */   }
/*     */ 
/*     */   
/*     */   private static JsonArray getModuleArray() {
/* 237 */     JsonArray modulesArray = new JsonArray();
/* 238 */     for (Module m : Thunderhack.moduleManager.modules) {
/* 239 */       modulesArray.add((JsonElement)getModuleObject(m));
/*     */     }
/* 241 */     return modulesArray;
/*     */   }
/*     */   
/*     */   public static JsonObject getModuleObject(Module m) {
/* 245 */     JsonObject attribs = new JsonObject();
/* 246 */     JsonParser jp = new JsonParser();
/*     */     
/* 248 */     for (Setting setting : m.getSettings()) {
/* 249 */       if (setting.isEnumSetting()) {
/* 250 */         EnumConverter converter = new EnumConverter(((Enum)setting.getValue()).getClass());
/* 251 */         attribs.add(setting.getName(), converter.doForward((Enum)setting.getValue()));
/*     */         continue;
/*     */       } 
/* 254 */       if (setting.isStringSetting()) {
/* 255 */         String str = (String)setting.getValue();
/* 256 */         setting.setValue(str.replace(" ", "_"));
/*     */       } 
/* 258 */       if (setting.isColorSetting()) {
/* 259 */         JsonArray array = new JsonArray();
/* 260 */         array.add((JsonElement)new JsonPrimitive(Integer.valueOf(((ColorSetting)setting.getValue()).getRawColor())));
/* 261 */         array.add((JsonElement)new JsonPrimitive(Boolean.valueOf(((ColorSetting)setting.getValue()).isCycle())));
/* 262 */         array.add((JsonElement)new JsonPrimitive(Integer.valueOf(((ColorSetting)setting.getValue()).getGlobalOffset())));
/* 263 */         attribs.add(setting.getName(), (JsonElement)array);
/*     */         continue;
/*     */       } 
/* 266 */       if (setting.isPositionSetting()) {
/* 267 */         JsonArray array = new JsonArray();
/* 268 */         float num2 = ((PositionSetting)setting.getValue()).getX();
/* 269 */         float num1 = ((PositionSetting)setting.getValue()).getY();
/* 270 */         array.add((JsonElement)new JsonPrimitive(Float.valueOf(num2)));
/* 271 */         array.add((JsonElement)new JsonPrimitive(Float.valueOf(num1)));
/*     */         
/* 273 */         attribs.add(setting.getName(), (JsonElement)array);
/*     */         continue;
/*     */       } 
/* 276 */       if (setting.isBindSetting()) {
/* 277 */         JsonArray array = new JsonArray();
/* 278 */         String key = setting.getValueAsString();
/* 279 */         boolean hold = ((Bind)setting.getValue()).isHold();
/* 280 */         array.add((JsonElement)new JsonPrimitive(key));
/* 281 */         array.add((JsonElement)new JsonPrimitive(Boolean.valueOf(hold)));
/*     */         
/* 283 */         attribs.add(setting.getName(), (JsonElement)array);
/*     */         continue;
/*     */       } 
/*     */       try {
/* 287 */         attribs.add(setting.getName(), jp.parse(setting.getValueAsString()));
/* 288 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/* 292 */     JsonObject moduleObject = new JsonObject();
/* 293 */     moduleObject.add(m.getName(), (JsonElement)attribs);
/* 294 */     return moduleObject;
/*     */   }
/*     */   
/*     */   public static boolean delete(File file) {
/* 298 */     return file.delete();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean delete(String name) {
/* 303 */     File file = new File(ConfigsFolder, name + ".th");
/* 304 */     if (!file.exists()) {
/* 305 */       return false;
/*     */     }
/* 307 */     return delete(file);
/*     */   }
/*     */   
/*     */   public static List<String> getConfigList() {
/* 311 */     if (!MainFolder.exists() || MainFolder.listFiles() == null) return null;
/*     */     
/* 313 */     List<String> list = new ArrayList<>();
/*     */     
/* 315 */     if (ConfigsFolder.listFiles() != null) {
/* 316 */       for (File file : Arrays.<File>stream(ConfigsFolder.listFiles()).filter(f -> f.getName().endsWith(".th")).collect(Collectors.toList())) {
/* 317 */         list.add(file.getName().replace(".th", ""));
/*     */       }
/*     */     }
/* 320 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveCurrentConfig() {
/* 325 */     File file = new File("ThunderHack/misc/currentcfg.txt");
/*     */     try {
/* 327 */       if (file.exists()) {
/* 328 */         FileWriter writer = new FileWriter(file);
/* 329 */         writer.write(currentConfig.getName().replace(".th", ""));
/* 330 */         writer.close();
/*     */       } else {
/* 332 */         file.createNewFile();
/* 333 */         FileWriter writer = new FileWriter(file);
/* 334 */         writer.write(currentConfig.getName().replace(".th", ""));
/* 335 */         writer.close();
/*     */       } 
/* 337 */     } catch (Exception e) {
/* 338 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static File getCurrentConfig() {
/* 343 */     File file = new File("ThunderHack/misc/currentcfg.txt");
/* 344 */     String name = "config";
/*     */     try {
/* 346 */       if (file.exists()) {
/* 347 */         Scanner reader = new Scanner(file);
/* 348 */         while (reader.hasNextLine())
/* 349 */           name = reader.nextLine(); 
/* 350 */         reader.close();
/*     */       } 
/* 352 */     } catch (Exception e) {
/* 353 */       e.printStackTrace();
/*     */     } 
/* 355 */     currentConfig = new File(ConfigsFolder, name + ".th");
/* 356 */     return currentConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadAlts() {
/*     */     try {
/* 362 */       File file = new File("ThunderHack/misc/alts.txt");
/*     */       
/* 364 */       if (file.exists()) {
/* 365 */         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
/* 366 */           while (reader.ready()) {
/* 367 */             String name = reader.readLine();
/* 368 */             Thunderhack.alts.add(name);
/*     */           }
/*     */         
/*     */         } 
/*     */       }
/* 373 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveAlts() {
/* 378 */     File file = new File("ThunderHack/misc/alts.txt");
/*     */     try {
/* 380 */       (new File("ThunderHack")).mkdirs();
/* 381 */       file.createNewFile();
/* 382 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 385 */     try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
/* 386 */       for (String name : Thunderhack.alts) {
/* 387 */         writer.write(name + "\n");
/*     */       }
/* 389 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadSearch() {
/*     */     try {
/* 396 */       File file = new File("ThunderHack/misc/search.txt");
/*     */       
/* 398 */       if (file.exists()) {
/* 399 */         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
/* 400 */           while (reader.ready()) {
/* 401 */             String name = reader.readLine();
/* 402 */             Search.defaultBlocks.add(getRegisteredBlock(name));
/*     */           }
/*     */         
/*     */         } 
/*     */       }
/* 407 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveSearch() {
/* 412 */     File file = new File("ThunderHack/misc/search.txt");
/*     */     try {
/* 414 */       (new File("ThunderHack")).mkdirs();
/* 415 */       file.createNewFile();
/* 416 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 419 */     try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
/* 420 */       for (Block name : Search.defaultBlocks) {
/* 421 */         writer.write(name.getRegistryName() + "\n");
/*     */       }
/* 423 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static Block getRegisteredBlock(String blockName) {
/* 428 */     return (Block)Block.field_149771_c.func_82594_a(new ResourceLocation(blockName));
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\manager\ConfigManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */