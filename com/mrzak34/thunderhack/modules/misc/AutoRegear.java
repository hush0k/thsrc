/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.command.commands.KitCommand;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class AutoRegear extends Module {
/*  16 */   private final HashMap<Integer, String> containerInv = new HashMap<>();
/*  17 */   public Setting<Integer> tickDelay = register(new Setting("Tick Delay", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(20)));
/*  18 */   public Setting<Integer> switchForTick = register(new Setting("Switch Per Tick", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(100)));
/*  19 */   public Setting<Boolean> debugMode = register(new Setting("Debug Mode", Boolean.valueOf(false)));
/*  20 */   public Setting<Boolean> infoMsgs = register(new Setting("Info Msgs", Boolean.valueOf(false)));
/*  21 */   public Setting<Boolean> closeAfter = register(new Setting("Close After", Boolean.valueOf(false)));
/*  22 */   public Setting<Boolean> invasive = register(new Setting("saInvasive", Boolean.valueOf(false)));
/*  23 */   public Setting<Boolean> confirmSort = register(new Setting("Confirm Sort", Boolean.valueOf(false)));
/*  24 */   public Setting<Boolean> enderChest = register(new Setting("enderChest", Boolean.valueOf(false)));
/*  25 */   private HashMap<Integer, String> planInventory = new HashMap<>();
/*  26 */   private ArrayList<Integer> sortItems = new ArrayList<>(); private int delayTimeTicks; private int stepNow; private boolean openedBefore;
/*     */   private boolean finishSort;
/*     */   private boolean doneBefore;
/*     */   
/*     */   public AutoRegear() {
/*  31 */     super("AutoRegear", "регирит тебя из-шалкера", Module.Category.MISC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  39 */     String curConfigName = KitCommand.getCurrentSet();
/*     */ 
/*     */ 
/*     */     
/*  43 */     if (curConfigName.equals("")) {
/*  44 */       disable();
/*     */       
/*     */       return;
/*     */     } 
/*  48 */     if (((Boolean)this.infoMsgs.getValue()).booleanValue()) {
/*  49 */       Command.sendMessage("Config " + curConfigName + " actived");
/*     */     }
/*     */     
/*  52 */     String inventoryConfig = KitCommand.getInventoryKit(curConfigName);
/*     */ 
/*     */ 
/*     */     
/*  56 */     if (inventoryConfig.equals("")) {
/*  57 */       disable();
/*     */       
/*     */       return;
/*     */     } 
/*  61 */     String[] inventoryDivided = inventoryConfig.split(" ");
/*     */     
/*  63 */     this.planInventory = new HashMap<>();
/*  64 */     HashMap<String, Integer> nItems = new HashMap<>();
/*     */     
/*  66 */     for (int i = 0; i < inventoryDivided.length; i++) {
/*     */       
/*  68 */       if (!inventoryDivided[i].contains("air")) {
/*     */         
/*  70 */         this.planInventory.put(Integer.valueOf(i), inventoryDivided[i]);
/*     */         
/*  72 */         if (nItems.containsKey(inventoryDivided[i])) {
/*     */           
/*  74 */           nItems.put(inventoryDivided[i], Integer.valueOf(((Integer)nItems.get(inventoryDivided[i])).intValue() + 1));
/*     */         } else {
/*     */           
/*  77 */           nItems.put(inventoryDivided[i], Integer.valueOf(1));
/*     */         } 
/*     */       } 
/*     */     } 
/*  81 */     this.delayTimeTicks = 0;
/*     */     
/*  83 */     this.openedBefore = this.doneBefore = false;
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*  87 */     if (((Boolean)this.infoMsgs.getValue()).booleanValue() && this.planInventory.size() > 0) {
/*  88 */       Command.sendMessage("AutoSort Turned Off!");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  94 */     if (this.delayTimeTicks < ((Integer)this.tickDelay.getValue()).intValue()) {
/*  95 */       this.delayTimeTicks++;
/*     */       return;
/*     */     } 
/*  98 */     this.delayTimeTicks = 0;
/*     */ 
/*     */ 
/*     */     
/* 102 */     if (this.planInventory.size() == 0) {
/* 103 */       disable();
/*     */     }
/*     */     
/* 106 */     if ((mc.field_71439_g.field_71070_bA instanceof ContainerChest && (((Boolean)this.enderChest.getValue()).booleanValue() || !((ContainerChest)mc.field_71439_g.field_71070_bA).func_85151_d().func_145748_c_().func_150260_c().equals("Ender Chest"))) || mc.field_71439_g.field_71070_bA instanceof net.minecraft.inventory.ContainerShulkerBox)
/*     */     
/*     */     { 
/*     */       
/* 110 */       sortInventoryAlgo(); }
/* 111 */     else { this.openedBefore = false; }
/*     */   
/*     */   }
/*     */   
/*     */   private void sortInventoryAlgo() {
/* 116 */     if (!this.openedBefore) {
/*     */       
/* 118 */       if (((Boolean)this.infoMsgs.getValue()).booleanValue() && !this.doneBefore) {
/* 119 */         Command.sendMessage("Start sorting inventory...");
/*     */       }
/* 121 */       int maxValue = (mc.field_71439_g.field_71070_bA instanceof ContainerChest) ? ((ContainerChest)mc.field_71439_g.field_71070_bA).func_85151_d().func_70302_i_() : 27;
/*     */ 
/*     */       
/* 124 */       for (int i = 0; i < maxValue; i++) {
/* 125 */         ItemStack item = (ItemStack)mc.field_71439_g.field_71070_bA.func_75138_a().get(i);
/* 126 */         this.containerInv.put(Integer.valueOf(i), ((ResourceLocation)Objects.<ResourceLocation>requireNonNull(item.func_77973_b().getRegistryName())).toString() + item.func_77960_j());
/*     */       } 
/* 128 */       this.openedBefore = true;
/*     */       
/* 130 */       HashMap<Integer, String> inventoryCopy = getInventoryCopy(maxValue);
/*     */       
/* 132 */       HashMap<Integer, String> aimInventory = getInventoryCopy(maxValue, this.planInventory);
/*     */       
/* 134 */       this.sortItems = getInventorySort(inventoryCopy, aimInventory, maxValue);
/*     */       
/* 136 */       if (this.sortItems.size() == 0 && !this.doneBefore) {
/*     */         
/* 138 */         this.finishSort = false;
/*     */         
/* 140 */         if (((Boolean)this.infoMsgs.getValue()).booleanValue()) {
/* 141 */           Command.sendMessage("Inventory already sorted...");
/*     */         }
/* 143 */         if (((Boolean)this.closeAfter.getValue()).booleanValue()) {
/* 144 */           mc.field_71439_g.func_71053_j();
/*     */         }
/*     */       } else {
/* 147 */         this.finishSort = true;
/* 148 */         this.stepNow = 0;
/*     */       } 
/* 150 */       this.openedBefore = true;
/* 151 */     } else if (this.finishSort) {
/* 152 */       for (int i = 0; i < ((Integer)this.switchForTick.getValue()).intValue(); i++) {
/*     */ 
/*     */         
/* 155 */         if (this.sortItems.size() != 0) {
/*     */           
/* 157 */           int slotChange = ((Integer)this.sortItems.get(this.stepNow++)).intValue();
/*     */           
/* 159 */           mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71070_bA.field_75152_c, slotChange, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */         } 
/*     */         
/* 162 */         if (this.stepNow == this.sortItems.size()) {
/*     */           
/* 164 */           if (((Boolean)this.confirmSort.getValue()).booleanValue() && 
/* 165 */             !this.doneBefore) {
/*     */             
/* 167 */             this.openedBefore = false;
/* 168 */             this.finishSort = false;
/* 169 */             this.doneBefore = true;
/*     */             
/* 171 */             checkLastItem();
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 176 */           this.finishSort = false;
/*     */           
/* 178 */           if (((Boolean)this.infoMsgs.getValue()).booleanValue()) {
/* 179 */             Command.sendMessage("Inventory sorted");
/*     */           }
/*     */           
/* 182 */           checkLastItem();
/* 183 */           this.doneBefore = false;
/*     */           
/* 185 */           if (((Boolean)this.closeAfter.getValue()).booleanValue()) {
/* 186 */             mc.field_71439_g.func_71053_j();
/*     */           }
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkLastItem() {
/* 195 */     if (this.sortItems.size() != 0) {
/*     */       
/* 197 */       int slotChange = ((Integer)this.sortItems.get(this.sortItems.size() - 1)).intValue();
/*     */       
/* 199 */       if (((ItemStack)mc.field_71439_g.field_71070_bA.func_75138_a().get(slotChange)).func_190926_b())
/*     */       {
/* 201 */         mc.field_71442_b.func_187098_a(0, slotChange, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Integer> getInventorySort(HashMap<Integer, String> copyInventory, HashMap<Integer, String> planInventoryCopy, int startValues) {
/* 211 */     ArrayList<Integer> planMove = new ArrayList<>();
/*     */ 
/*     */     
/* 214 */     HashMap<String, Integer> nItemsCopy = new HashMap<>();
/*     */     
/* 216 */     for (String value : planInventoryCopy.values()) {
/* 217 */       if (nItemsCopy.containsKey(value)) {
/* 218 */         nItemsCopy.put(value, Integer.valueOf(((Integer)nItemsCopy.get(value)).intValue() + 1)); continue;
/*     */       } 
/* 220 */       nItemsCopy.put(value, Integer.valueOf(1));
/*     */     } 
/*     */ 
/*     */     
/* 224 */     ArrayList<Integer> ignoreValues = new ArrayList<>();
/*     */ 
/*     */     
/* 227 */     int[] listValue = new int[planInventoryCopy.size()];
/*     */     
/* 229 */     int id = 0;
/* 230 */     for (Iterator<Integer> iterator = planInventoryCopy.keySet().iterator(); iterator.hasNext(); ) { int idx = ((Integer)iterator.next()).intValue();
/* 231 */       listValue[id++] = idx; }
/*     */ 
/*     */ 
/*     */     
/* 235 */     for (int item : listValue) {
/* 236 */       if (((String)copyInventory.get(Integer.valueOf(item))).equals(planInventoryCopy.get(Integer.valueOf(item)))) {
/*     */         
/* 238 */         ignoreValues.add(Integer.valueOf(item));
/*     */         
/* 240 */         nItemsCopy.put(planInventoryCopy.get(Integer.valueOf(item)), Integer.valueOf(((Integer)nItemsCopy.get(planInventoryCopy.get(Integer.valueOf(item)))).intValue() - 1));
/*     */         
/* 242 */         if (((Integer)nItemsCopy.get(planInventoryCopy.get(Integer.valueOf(item)))).intValue() == 0) {
/* 243 */           nItemsCopy.remove(planInventoryCopy.get(Integer.valueOf(item)));
/*     */         }
/* 245 */         planInventoryCopy.remove(Integer.valueOf(item));
/*     */       } 
/*     */     } 
/*     */     
/* 249 */     String pickedItem = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     for (int i = startValues; i < startValues + copyInventory.size(); i++) {
/*     */       
/* 256 */       if (!ignoreValues.contains(Integer.valueOf(i))) {
/*     */         
/* 258 */         String itemCheck = copyInventory.get(Integer.valueOf(i));
/*     */         
/* 260 */         Optional<Map.Entry<Integer, String>> momentAim = planInventoryCopy.entrySet().stream().filter(x -> ((String)x.getValue()).equals(itemCheck)).findFirst();
/*     */         
/* 262 */         if (momentAim.isPresent()) {
/*     */ 
/*     */           
/* 265 */           if (pickedItem == null) {
/* 266 */             planMove.add(Integer.valueOf(i));
/*     */           }
/* 268 */           int aimKey = ((Integer)((Map.Entry)momentAim.get()).getKey()).intValue();
/* 269 */           planMove.add(Integer.valueOf(aimKey));
/*     */           
/* 271 */           if (pickedItem == null || !pickedItem.equals(itemCheck)) {
/* 272 */             ignoreValues.add(Integer.valueOf(aimKey));
/*     */           }
/*     */           
/* 275 */           nItemsCopy.put(itemCheck, Integer.valueOf(((Integer)nItemsCopy.get(itemCheck)).intValue() - 1));
/*     */           
/* 277 */           if (((Integer)nItemsCopy.get(itemCheck)).intValue() == 0) {
/* 278 */             nItemsCopy.remove(itemCheck);
/*     */           }
/* 280 */           copyInventory.put(Integer.valueOf(i), copyInventory.get(Integer.valueOf(aimKey)));
/* 281 */           copyInventory.put(Integer.valueOf(aimKey), itemCheck);
/*     */ 
/*     */           
/* 284 */           if (!((String)copyInventory.get(Integer.valueOf(aimKey))).equals("minecraft:air0")) {
/*     */ 
/*     */ 
/*     */             
/* 288 */             if (i >= startValues + copyInventory.size()) {
/*     */               continue;
/*     */             }
/*     */             
/* 292 */             pickedItem = copyInventory.get(Integer.valueOf(i));
/* 293 */             i--;
/*     */           } else {
/*     */             
/* 296 */             pickedItem = null;
/*     */           } 
/*     */           
/* 299 */           planInventoryCopy.remove(Integer.valueOf(aimKey));
/*     */           continue;
/*     */         } 
/* 302 */         if (pickedItem != null) {
/*     */           
/* 304 */           planMove.add(Integer.valueOf(i));
/* 305 */           copyInventory.put(Integer.valueOf(i), pickedItem);
/*     */           
/* 307 */           pickedItem = null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*     */       continue;
/*     */     } 
/*     */     
/* 315 */     if (planMove.size() != 0 && ((Integer)planMove.get(planMove.size() - 1)).equals(planMove.get(planMove.size() - 2))) {
/* 316 */       planMove.remove(planMove.size() - 1);
/*     */     }
/*     */     
/* 319 */     Object[] keyList = this.containerInv.keySet().toArray();
/*     */ 
/*     */     
/* 322 */     for (int values = 0; values < keyList.length; values++) {
/*     */       
/* 324 */       int itemC = ((Integer)keyList[values]).intValue();
/*     */       
/* 326 */       if (nItemsCopy.containsKey(this.containerInv.get(Integer.valueOf(itemC)))) {
/*     */         
/* 328 */         int start = ((Integer)((Map.Entry)planInventoryCopy.entrySet().stream().filter(x -> ((String)x.getValue()).equals(this.containerInv.get(Integer.valueOf(itemC)))).findFirst().get()).getKey()).intValue();
/*     */         
/* 330 */         if (((Boolean)this.invasive.getValue()).booleanValue() || ((ItemStack)mc.field_71439_g.field_71070_bA.func_75138_a().get(start)).func_190926_b()) {
/*     */           
/* 332 */           planMove.add(Integer.valueOf(start));
/* 333 */           planMove.add(Integer.valueOf(itemC));
/* 334 */           planMove.add(Integer.valueOf(start));
/*     */           
/* 336 */           nItemsCopy.put(planInventoryCopy.get(Integer.valueOf(start)), Integer.valueOf(((Integer)nItemsCopy.get(planInventoryCopy.get(Integer.valueOf(start)))).intValue() - 1));
/* 337 */           if (((Integer)nItemsCopy.get(planInventoryCopy.get(Integer.valueOf(start)))).intValue() == 0) {
/* 338 */             nItemsCopy.remove(planInventoryCopy.get(Integer.valueOf(start)));
/*     */           }
/* 340 */           planInventoryCopy.remove(Integer.valueOf(start));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 347 */     if (((Boolean)this.debugMode.getValue()).booleanValue())
/*     */     {
/* 349 */       for (Iterator<Integer> iterator1 = planMove.iterator(); iterator1.hasNext(); ) { int valuePath = ((Integer)iterator1.next()).intValue();
/* 350 */         Command.sendMessage(Integer.toString(valuePath)); }
/*     */     
/*     */     }
/*     */     
/* 354 */     return planMove;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private HashMap<Integer, String> getInventoryCopy(int startPoint) {
/* 360 */     HashMap<Integer, String> output = new HashMap<>();
/*     */     
/* 362 */     int sizeInventory = mc.field_71439_g.field_71071_by.field_70462_a.size();
/*     */ 
/*     */     
/* 365 */     for (int i = 0; i < sizeInventory; i++) {
/*     */       
/* 367 */       int value = i + startPoint + ((i < 9) ? (sizeInventory - 9) : -9);
/*     */       
/* 369 */       ItemStack item = (ItemStack)mc.field_71439_g.field_71070_bA.func_75138_a().get(value);
/*     */       
/* 371 */       output.put(Integer.valueOf(value), ((ResourceLocation)Objects.<ResourceLocation>requireNonNull(item.func_77973_b().getRegistryName())).toString() + item.func_77960_j());
/*     */     } 
/*     */     
/* 374 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private HashMap<Integer, String> getInventoryCopy(int startPoint, HashMap<Integer, String> inventory) {
/* 380 */     HashMap<Integer, String> output = new HashMap<>();
/*     */     
/* 382 */     int sizeInventory = mc.field_71439_g.field_71071_by.field_70462_a.size();
/* 383 */     for (Iterator<Integer> iterator = inventory.keySet().iterator(); iterator.hasNext(); ) { int val = ((Integer)iterator.next()).intValue();
/*     */       
/* 385 */       output.put(Integer.valueOf(val + startPoint + ((val < 9) ? (sizeInventory - 9) : -9)), inventory.get(Integer.valueOf(val))); }
/*     */ 
/*     */     
/* 388 */     return output;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\AutoRegear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */