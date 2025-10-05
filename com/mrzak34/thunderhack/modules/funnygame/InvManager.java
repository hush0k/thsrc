/*     */ package com.mrzak34.thunderhack.modules.funnygame;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ 
/*     */ public class InvManager extends Module {
/*  23 */   public static int weaponSlot = 36; public static int pickaxeSlot = 37; public static int axeSlot = 38; public static int shovelSlot = 39;
/*  24 */   public static List<Block> invalidBlocks = Arrays.asList(new Block[] { Blocks.field_150381_bn, Blocks.field_150460_al, Blocks.field_150404_cg, Blocks.field_150462_ai, Blocks.field_150447_bR, (Block)Blocks.field_150486_ae, Blocks.field_150367_z, Blocks.field_150350_a, (Block)Blocks.field_150355_j, (Block)Blocks.field_150353_l, (Block)Blocks.field_150358_i, (Block)Blocks.field_150356_k, (Block)Blocks.field_150354_m, Blocks.field_150431_aC, Blocks.field_150478_aa, Blocks.field_150467_bQ, Blocks.field_150421_aI, Blocks.field_150430_aB, Blocks.field_150471_bO, Blocks.field_150442_at, Blocks.field_150323_B, Blocks.field_150456_au, Blocks.field_150445_bS, Blocks.field_150452_aw, Blocks.field_150443_bT, (Block)Blocks.field_150333_U, (Block)Blocks.field_150376_bx, (Block)Blocks.field_180389_cP, (Block)Blocks.field_150337_Q, (Block)Blocks.field_150338_P, (Block)Blocks.field_150327_N, (Block)Blocks.field_150328_O, Blocks.field_150467_bQ, Blocks.field_150410_aZ, (Block)Blocks.field_150397_co, Blocks.field_150411_aY, (Block)Blocks.field_150434_aF, Blocks.field_150468_ap, Blocks.field_150321_G });
/*  25 */   public final Setting<Float> delay1 = register(new Setting("Sort Delay", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/*  26 */   private final Timer timer = new Timer();
/*     */ 
/*     */   
/*  29 */   public Setting<Integer> cap = register(new Setting("Block Cap", Integer.valueOf(128), Integer.valueOf(8), Integer.valueOf(256)));
/*  30 */   public Setting<Boolean> archer = register(new Setting("Archer", Boolean.valueOf(false)));
/*  31 */   public Setting<Boolean> food = register(new Setting("Food", Boolean.valueOf(false)));
/*  32 */   public Setting<Boolean> sword = register(new Setting("Sword", Boolean.valueOf(true)));
/*  33 */   public Setting<Boolean> cleaner = register(new Setting("Inv Cleaner", Boolean.valueOf(true)));
/*  34 */   public Setting<Boolean> openinv = register(new Setting("Open Inv", Boolean.valueOf(true)));
/*     */   public InvManager() {
/*  36 */     super("InvManager", "очищает инвентарь-от хлама", Module.Category.FUNNYGAME);
/*     */   }
/*     */   
/*     */   public static boolean isBestArmor(ItemStack stack, int type) {
/*  40 */     String armorType = "";
/*  41 */     if (type == 1) {
/*  42 */       armorType = "helmet";
/*  43 */     } else if (type == 2) {
/*  44 */       armorType = "chestplate";
/*  45 */     } else if (type == 3) {
/*  46 */       armorType = "leggings";
/*  47 */     } else if (type == 4) {
/*  48 */       armorType = "boots";
/*     */     } 
/*  50 */     if (!stack.func_77973_b().func_77657_g(stack).contains(armorType)) {
/*  51 */       return false;
/*     */     }
/*  53 */     for (int i = 5; i < 45; i++) {
/*  54 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/*  55 */         ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/*     */       }
/*     */     } 
/*  58 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  63 */     long delay = (long)(((Float)this.delay1.getValue()).floatValue() * 50.0F);
/*  64 */     if (!(mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiInventory) && ((Boolean)this.openinv.getValue()).booleanValue())
/*     */       return; 
/*  66 */     if (mc.field_71462_r == null || mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiInventory || mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat) {
/*  67 */       if (this.timer.passedMs(delay) && weaponSlot >= 36) {
/*  68 */         if (!mc.field_71439_g.field_71069_bz.func_75139_a(weaponSlot).func_75216_d()) {
/*  69 */           getBestWeapon(weaponSlot);
/*     */         }
/*  71 */         else if (!isBestWeapon(mc.field_71439_g.field_71069_bz.func_75139_a(weaponSlot).func_75211_c())) {
/*  72 */           getBestWeapon(weaponSlot);
/*     */         } 
/*     */       }
/*     */       
/*  76 */       if (this.timer.passedMs(delay) && pickaxeSlot >= 36) {
/*  77 */         getBestPickaxe();
/*     */       }
/*  79 */       if (this.timer.passedMs(delay) && shovelSlot >= 36) {
/*  80 */         getBestShovel();
/*     */       }
/*  82 */       if (this.timer.passedMs(delay) && axeSlot >= 36) {
/*  83 */         getBestAxe();
/*     */       }
/*  85 */       if (this.timer.passedMs(delay) && ((Boolean)this.cleaner.getValue()).booleanValue())
/*  86 */         for (int i = 9; i < 45; i++) {
/*  87 */           if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/*  88 */             ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/*  89 */             if (shouldDrop(is, i)) {
/*  90 */               drop(i);
/*  91 */               if (delay == 0L) {
/*  92 */                 mc.field_71439_g.func_71053_j();
/*     */               }
/*  94 */               this.timer.reset();
/*  95 */               if (delay > 0L) {
/*     */                 break;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   public void swap(int slot, int hotbarSlot) {
/* 105 */     mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, slot, hotbarSlot, ClickType.SWAP, (EntityPlayer)mc.field_71439_g);
/*     */   }
/*     */   
/*     */   public void drop(int slot) {
/* 109 */     mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, slot, 1, ClickType.THROW, (EntityPlayer)mc.field_71439_g);
/*     */   }
/*     */   
/*     */   public boolean isBestWeapon(ItemStack stack) {
/* 113 */     float damage = getDamage(stack);
/* 114 */     for (int i = 9; i < 45; i++) {
/* 115 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/* 116 */         ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/* 117 */         if (getDamage(is) > damage && (is.func_77973_b() instanceof ItemSword || !((Boolean)this.sword.getValue()).booleanValue()))
/* 118 */           return false; 
/*     */       } 
/*     */     } 
/* 121 */     return (stack.func_77973_b() instanceof ItemSword || !((Boolean)this.sword.getValue()).booleanValue());
/*     */   }
/*     */   
/*     */   public void getBestWeapon(int slot) {
/* 125 */     for (int i = 9; i < 45; i++) {
/* 126 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/* 127 */         ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/* 128 */         if (isBestWeapon(is) && getDamage(is) > 0.0F && (is.func_77973_b() instanceof ItemSword || !((Boolean)this.sword.getValue()).booleanValue())) {
/* 129 */           swap(i, slot - 36);
/* 130 */           this.timer.reset();
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getDamage(ItemStack stack) {
/* 138 */     float damage = 0.0F;
/* 139 */     Item item = stack.func_77973_b();
/* 140 */     if (item instanceof ItemTool) {
/* 141 */       ItemTool tool = (ItemTool)item;
/* 142 */       damage += tool.func_77612_l();
/*     */     } 
/* 144 */     if (item instanceof ItemSword) {
/* 145 */       ItemSword sword = (ItemSword)item;
/* 146 */       damage += sword.func_77612_l();
/*     */     } 
/* 148 */     damage += EnchantmentHelper.func_77506_a(Objects.<Enchantment>requireNonNull(Enchantment.func_185262_c(16)), stack) * 1.25F + EnchantmentHelper.func_77506_a(Objects.<Enchantment>requireNonNull(Enchantment.func_185262_c(20)), stack) * 0.01F;
/* 149 */     return damage;
/*     */   }
/*     */   
/*     */   public boolean shouldDrop(ItemStack stack, int slot) {
/* 153 */     if (stack.func_82833_r().toLowerCase().contains("/")) {
/* 154 */       return false;
/*     */     }
/* 156 */     if (stack.func_82833_r().toLowerCase().contains("предметы")) {
/* 157 */       return false;
/*     */     }
/* 159 */     if (stack.func_82833_r().toLowerCase().contains("§k||")) {
/* 160 */       return false;
/*     */     }
/* 162 */     if (stack.func_82833_r().toLowerCase().contains("kit")) {
/* 163 */       return false;
/*     */     }
/* 165 */     if (stack.func_82833_r().toLowerCase().contains("wool")) {
/* 166 */       return false;
/*     */     }
/* 168 */     if (stack.func_82833_r().toLowerCase().contains("лобби")) {
/* 169 */       return false;
/*     */     }
/* 171 */     if ((slot == weaponSlot && isBestWeapon(mc.field_71439_g.field_71069_bz.func_75139_a(weaponSlot).func_75211_c())) || (slot == pickaxeSlot && 
/* 172 */       isBestPickaxe(mc.field_71439_g.field_71069_bz.func_75139_a(pickaxeSlot).func_75211_c()) && pickaxeSlot >= 0) || (slot == axeSlot && 
/* 173 */       isBestAxe(mc.field_71439_g.field_71069_bz.func_75139_a(axeSlot).func_75211_c()) && axeSlot >= 0) || (slot == shovelSlot && 
/* 174 */       isBestShovel(mc.field_71439_g.field_71069_bz.func_75139_a(shovelSlot).func_75211_c()) && shovelSlot >= 0)) {
/* 175 */       return false;
/*     */     }
/* 177 */     if (stack.func_77973_b() instanceof net.minecraft.item.ItemArmor) {
/* 178 */       for (int type = 1; type < 5; type++) {
/* 179 */         if (mc.field_71439_g.field_71069_bz.func_75139_a(4 + type).func_75216_d()) {
/* 180 */           ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(4 + type).func_75211_c();
/* 181 */           if (isBestArmor(is, type)) {
/*     */             continue;
/*     */           }
/*     */         } 
/* 185 */         if (isBestArmor(stack, type)) {
/* 186 */           return false;
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/*     */     }
/*     */     
/* 193 */     if (stack.func_77973_b() instanceof net.minecraft.item.ItemPotion && 
/* 194 */       isBadPotion(stack)) {
/* 195 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 199 */     if (stack.func_77973_b() instanceof net.minecraft.item.ItemFood && ((Boolean)this.food.getValue()).booleanValue() && !(stack.func_77973_b() instanceof net.minecraft.item.ItemAppleGold)) {
/* 200 */       return true;
/*     */     }
/* 202 */     if (stack.func_77973_b() instanceof net.minecraft.item.ItemHoe || stack.func_77973_b() instanceof ItemTool || stack.func_77973_b() instanceof ItemSword || stack.func_77973_b() instanceof net.minecraft.item.ItemArmor) {
/* 203 */       return true;
/*     */     }
/* 205 */     if ((stack.func_77973_b() instanceof net.minecraft.item.ItemBow || stack.func_77973_b().func_77657_g(stack).contains("arrow")) && ((Boolean)this.archer.getValue()).booleanValue()) {
/* 206 */       return true;
/*     */     }
/*     */     
/* 209 */     return (stack.func_77973_b().func_77657_g(stack).contains("tnt") || stack
/* 210 */       .func_77973_b().func_77657_g(stack).contains("stick") || stack
/* 211 */       .func_77973_b().func_77657_g(stack).contains("egg") || stack
/* 212 */       .func_77973_b().func_77657_g(stack).contains("string") || stack
/* 213 */       .func_77973_b().func_77657_g(stack).contains("cake") || stack
/* 214 */       .func_77973_b().func_77657_g(stack).contains("mushroom") || stack
/* 215 */       .func_77973_b().func_77657_g(stack).contains("flint") || stack
/* 216 */       .func_77973_b().func_77657_g(stack).contains("dyePowder") || stack
/* 217 */       .func_77973_b().func_77657_g(stack).contains("feather") || stack
/* 218 */       .func_77973_b().func_77657_g(stack).contains("bucket") || (stack
/* 219 */       .func_77973_b().func_77657_g(stack).contains("chest") && !stack.func_82833_r().toLowerCase().contains("collect")) || stack
/* 220 */       .func_77973_b().func_77657_g(stack).contains("snow") || stack
/* 221 */       .func_77973_b().func_77657_g(stack).contains("fish") || stack
/* 222 */       .func_77973_b().func_77657_g(stack).contains("enchant") || stack
/* 223 */       .func_77973_b().func_77657_g(stack).contains("exp") || stack
/* 224 */       .func_77973_b().func_77657_g(stack).contains("shears") || stack
/* 225 */       .func_77973_b().func_77657_g(stack).contains("anvil") || stack
/* 226 */       .func_77973_b().func_77657_g(stack).contains("torch") || stack
/* 227 */       .func_77973_b().func_77657_g(stack).contains("seeds") || stack
/* 228 */       .func_77973_b().func_77657_g(stack).contains("leather") || stack
/* 229 */       .func_77973_b().func_77657_g(stack).contains("reeds") || stack
/* 230 */       .func_77973_b().func_77657_g(stack).contains("skull") || stack
/* 231 */       .func_77973_b().func_77657_g(stack).contains("wool") || stack
/* 232 */       .func_77973_b().func_77657_g(stack).contains("record") || stack
/* 233 */       .func_77973_b().func_77657_g(stack).contains("snowball") || stack
/* 234 */       .func_77973_b() instanceof net.minecraft.item.ItemGlassBottle || stack
/* 235 */       .func_77973_b().func_77657_g(stack).contains("piston"));
/*     */   }
/*     */   
/*     */   private int getBlockCount() {
/* 239 */     int blockCount = 0;
/* 240 */     for (int i = 0; i < 45; i++) {
/* 241 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/* 242 */         ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/* 243 */         Item item = is.func_77973_b();
/* 244 */         if (!(is.func_77973_b() instanceof ItemBlock) || !invalidBlocks.contains(((ItemBlock)item).func_179223_d()));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 249 */     return blockCount;
/*     */   }
/*     */   
/*     */   private void getBestPickaxe() {
/* 253 */     for (int i = 9; i < 45; i++) {
/* 254 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/* 255 */         ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/*     */         
/* 257 */         if (isBestPickaxe(is) && pickaxeSlot != i && 
/* 258 */           !isBestWeapon(is))
/* 259 */           if (!mc.field_71439_g.field_71069_bz.func_75139_a(pickaxeSlot).func_75216_d()) {
/* 260 */             swap(i, pickaxeSlot - 36);
/* 261 */             this.timer.reset();
/* 262 */             if (((Float)this.delay1.getValue()).floatValue() > 0.0F)
/*     */               return; 
/* 264 */           } else if (!isBestPickaxe(mc.field_71439_g.field_71069_bz.func_75139_a(pickaxeSlot).func_75211_c())) {
/* 265 */             swap(i, pickaxeSlot - 36);
/* 266 */             this.timer.reset();
/* 267 */             if (((Float)this.delay1.getValue()).floatValue() > 0.0F) {
/*     */               return;
/*     */             }
/*     */           }  
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getBestShovel() {
/* 276 */     for (int i = 9; i < 45; i++) {
/* 277 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/* 278 */         ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/*     */         
/* 280 */         if (isBestShovel(is) && shovelSlot != i && 
/* 281 */           !isBestWeapon(is))
/* 282 */           if (!mc.field_71439_g.field_71069_bz.func_75139_a(shovelSlot).func_75216_d()) {
/* 283 */             swap(i, shovelSlot - 36);
/* 284 */             this.timer.reset();
/* 285 */             if (((Float)this.delay1.getValue()).floatValue() > 0.0F)
/*     */               return; 
/* 287 */           } else if (!isBestShovel(mc.field_71439_g.field_71069_bz.func_75139_a(shovelSlot).func_75211_c())) {
/* 288 */             swap(i, shovelSlot - 36);
/* 289 */             this.timer.reset();
/* 290 */             if (((Float)this.delay1.getValue()).floatValue() > 0.0F) {
/*     */               return;
/*     */             }
/*     */           }  
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getBestAxe() {
/* 299 */     for (int i = 9; i < 45; i++) {
/* 300 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/* 301 */         ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/*     */         
/* 303 */         if (isBestAxe(is) && axeSlot != i && 
/* 304 */           !isBestWeapon(is))
/* 305 */           if (!mc.field_71439_g.field_71069_bz.func_75139_a(axeSlot).func_75216_d()) {
/* 306 */             swap(i, axeSlot - 36);
/* 307 */             this.timer.reset();
/* 308 */             if (((Float)this.delay1.getValue()).floatValue() > 0.0F)
/*     */               return; 
/* 310 */           } else if (!isBestAxe(mc.field_71439_g.field_71069_bz.func_75139_a(axeSlot).func_75211_c())) {
/* 311 */             swap(i, axeSlot - 36);
/* 312 */             this.timer.reset();
/* 313 */             if (((Float)this.delay1.getValue()).floatValue() > 0.0F) {
/*     */               return;
/*     */             }
/*     */           }  
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isBestPickaxe(ItemStack stack) {
/* 322 */     Item item = stack.func_77973_b();
/* 323 */     if (!(item instanceof net.minecraft.item.ItemPickaxe))
/* 324 */       return false; 
/* 325 */     float value = getToolEffect(stack);
/* 326 */     for (int i = 9; i < 45; i++) {
/* 327 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/* 328 */         ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/* 329 */         if (getToolEffect(is) > value && is.func_77973_b() instanceof net.minecraft.item.ItemPickaxe) {
/* 330 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 334 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isBestShovel(ItemStack stack) {
/* 338 */     Item item = stack.func_77973_b();
/* 339 */     if (!(item instanceof net.minecraft.item.ItemSpade))
/* 340 */       return false; 
/* 341 */     float value = getToolEffect(stack);
/* 342 */     for (int i = 9; i < 45; i++) {
/* 343 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/* 344 */         ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/* 345 */         if (getToolEffect(is) > value && is.func_77973_b() instanceof net.minecraft.item.ItemSpade) {
/* 346 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 350 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isBestAxe(ItemStack stack) {
/* 354 */     Item item = stack.func_77973_b();
/* 355 */     if (!(item instanceof net.minecraft.item.ItemAxe))
/* 356 */       return false; 
/* 357 */     float value = getToolEffect(stack);
/* 358 */     for (int i = 9; i < 45; i++) {
/* 359 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/* 360 */         ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/* 361 */         if (getToolEffect(is) > value && is.func_77973_b() instanceof net.minecraft.item.ItemAxe && !isBestWeapon(stack)) {
/* 362 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 366 */     return true;
/*     */   }
/*     */   
/*     */   private float getToolEffect(ItemStack stack) {
/* 370 */     Item item = stack.func_77973_b();
/* 371 */     if (!(item instanceof ItemTool))
/* 372 */       return 0.0F; 
/* 373 */     ItemTool tool = (ItemTool)item;
/*     */     
/* 375 */     if (item instanceof net.minecraft.item.ItemPickaxe) {
/* 376 */       value = tool.func_150893_a(stack, Blocks.field_150348_b.func_176223_P());
/* 377 */     } else if (item instanceof net.minecraft.item.ItemSpade) {
/* 378 */       value = tool.func_150893_a(stack, Blocks.field_150346_d.func_176223_P());
/* 379 */     } else if (item instanceof net.minecraft.item.ItemAxe) {
/* 380 */       value = tool.func_150893_a(stack, Blocks.field_150364_r.func_176223_P());
/*     */     } else {
/* 382 */       return 1.0F;
/* 383 */     }  float value = (float)(value + EnchantmentHelper.func_77506_a(Objects.<Enchantment>requireNonNull(Enchantment.func_185262_c(32)), stack) * 0.0075D);
/* 384 */     value = (float)(value + EnchantmentHelper.func_77506_a(Objects.<Enchantment>requireNonNull(Enchantment.func_185262_c(34)), stack) / 100.0D);
/* 385 */     return value;
/*     */   }
/*     */   
/*     */   private boolean isBadPotion(ItemStack stack) {
/* 389 */     if (stack != null && stack.func_77973_b() instanceof net.minecraft.item.ItemPotion) {
/* 390 */       for (PotionEffect o : PotionUtils.func_185189_a(stack)) {
/* 391 */         if (o.func_188419_a() == Potion.func_188412_a(19) || o.func_188419_a() == Potion.func_188412_a(7) || o.func_188419_a() == Potion.func_188412_a(2) || o.func_188419_a() == Potion.func_188412_a(18)) {
/* 392 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 396 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\InvManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */