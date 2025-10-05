/*     */ package com.mrzak34.thunderhack.util;
/*     */ import com.mrzak34.thunderhack.util.phobos.HelperRotation;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemAxe;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ 
/*     */ public class InventoryUtil implements Util {
/*     */   public static int getBestSword() {
/*  21 */     int b = -1;
/*  22 */     float f = 1.0F;
/*  23 */     for (int b1 = 0; b1 < 9; b1++) {
/*  24 */       ItemStack itemStack = Util.mc.field_71439_g.field_71071_by.func_70301_a(b1);
/*  25 */       if (itemStack != null && itemStack.func_77973_b() instanceof ItemSword) {
/*  26 */         ItemSword itemSword = (ItemSword)itemStack.func_77973_b();
/*  27 */         float f1 = itemSword.func_77612_l();
/*  28 */         f1 += EnchantmentHelper.func_77506_a(Enchantment.func_185262_c(20), itemStack);
/*  29 */         if (f1 > f) {
/*  30 */           f = f1;
/*  31 */           b = b1;
/*     */         } 
/*     */       } 
/*     */     } 
/*  35 */     return b;
/*     */   }
/*     */   
/*     */   public static int getItemCount(Item item) {
/*  39 */     if (mc.field_71439_g == null) {
/*  40 */       return 0;
/*     */     }
/*  42 */     int n = 0;
/*  43 */     int n2 = 44;
/*  44 */     for (int i = 0; i <= n2; i++) {
/*  45 */       ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/*  46 */       if (itemStack.func_77973_b() == item)
/*  47 */         n += itemStack.func_190916_E(); 
/*     */     } 
/*  49 */     return n;
/*     */   }
/*     */   
/*     */   public static int getBestAxe() {
/*  53 */     int b = -1;
/*  54 */     float f = 1.0F;
/*  55 */     for (int b1 = 0; b1 < 9; b1++) {
/*  56 */       ItemStack itemStack = Util.mc.field_71439_g.field_71071_by.func_70301_a(b1);
/*  57 */       if (itemStack != null && itemStack.func_77973_b() instanceof ItemAxe) {
/*  58 */         ItemAxe axe = (ItemAxe)itemStack.func_77973_b();
/*  59 */         float f1 = axe.func_77612_l();
/*  60 */         f1 += EnchantmentHelper.func_77506_a(Enchantment.func_185262_c(20), itemStack);
/*  61 */         if (f1 > f) {
/*  62 */           f = f1;
/*  63 */           b = b1;
/*     */         } 
/*     */       } 
/*     */     } 
/*  67 */     return b;
/*     */   }
/*     */   
/*     */   public static int hotbarToInventory(int slot) {
/*  71 */     if (slot == -2) {
/*  72 */       return 45;
/*     */     }
/*     */     
/*  75 */     if (slot > -1 && slot < 9) {
/*  76 */       return 36 + slot;
/*     */     }
/*     */     
/*  79 */     return slot;
/*     */   }
/*     */   
/*     */   public static void bypassSwitch(int slot) {
/*  83 */     if (slot >= 0) {
/*  84 */       mc.field_71442_b.func_187100_a(slot);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void switchTo(int slot) {
/*  89 */     if (mc.field_71439_g.field_71071_by.field_70461_c != slot && slot > -1 && slot < 9) {
/*  90 */       mc.field_71439_g.field_71071_by.field_70461_c = slot;
/*  91 */       syncItem();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void switchToBypass(int slot) {
/*  96 */     HelperRotation.acquire(() -> {
/*     */           if (mc.field_71439_g.field_71071_by.field_70461_c != slot && slot > -1 && slot < 9) {
/*     */             int lastSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */             int targetSlot = hotbarToInventory(slot);
/*     */             int currentSlot = hotbarToInventory(lastSlot);
/*     */             mc.field_71442_b.func_187098_a(0, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */             mc.field_71442_b.func_187098_a(0, currentSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */             mc.field_71442_b.func_187098_a(0, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void switchToBypassAlt(int slot) {
/* 116 */     HelperRotation.acquire(() -> {
/*     */           if (mc.field_71439_g.field_71071_by.field_70461_c != slot && slot > -1 && slot < 9) {
/*     */             HelperRotation.acquire(());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumHand getHand(int slot) {
/* 128 */     return (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
/*     */   }
/*     */   
/*     */   public static void syncItem() {
/* 132 */     ((IPlayerControllerMP)mc.field_71442_b).syncItem();
/*     */   }
/*     */   
/*     */   public static EnumHand getHand(Item item) {
/* 136 */     return (mc.field_71439_g.func_184614_ca().func_77973_b() == item) ? EnumHand.MAIN_HAND : (
/*     */       
/* 138 */       (mc.field_71439_g.func_184592_cb().func_77973_b() == item) ? EnumHand.OFF_HAND : null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void switchToHotbarSlot(int slot, boolean silent) {
/* 144 */     if (mc.field_71439_g.field_71071_by.field_70461_c == slot || slot < 0) {
/*     */       return;
/*     */     }
/* 147 */     if (silent) {
/* 148 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/* 149 */       mc.field_71442_b.func_78765_e();
/*     */     } else {
/* 151 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/* 152 */       mc.field_71439_g.field_71071_by.field_70461_c = slot;
/* 153 */       mc.field_71442_b.func_78765_e();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int containerToSlots(int containerSlot) {
/* 158 */     if (containerSlot < 5 || containerSlot > 45) {
/* 159 */       return -1;
/*     */     }
/*     */     
/* 162 */     if (containerSlot <= 9) {
/* 163 */       return 44 - containerSlot;
/*     */     }
/*     */     
/* 166 */     if (containerSlot < 36) {
/* 167 */       return containerSlot;
/*     */     }
/*     */     
/* 170 */     if (containerSlot < 45) {
/* 171 */       return containerSlot - 36;
/*     */     }
/*     */     
/* 174 */     return 40;
/*     */   }
/*     */   
/*     */   public static void put(int slot, ItemStack stack) {
/* 178 */     if (slot == -2) {
/* 179 */       mc.field_71439_g.field_71071_by.func_70437_b(stack);
/*     */     }
/*     */     
/* 182 */     mc.field_71439_g.field_71069_bz.func_75141_a(slot, stack);
/*     */     
/* 184 */     int invSlot = containerToSlots(slot);
/* 185 */     if (invSlot != -1) {
/* 186 */       mc.field_71439_g.field_71071_by.func_70299_a(invSlot, stack);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getCount(Item item) {
/* 191 */     int result = 0;
/* 192 */     for (int i = 0; i < 46; i++) {
/*     */ 
/*     */ 
/*     */       
/* 196 */       ItemStack stack = (ItemStack)mc.field_71439_g.field_71069_bz.func_75138_a().get(i);
/*     */       
/* 198 */       if (stack.func_77973_b() == item) {
/* 199 */         result += stack.func_190916_E();
/*     */       }
/*     */     } 
/*     */     
/* 203 */     if (mc.field_71439_g.field_71071_by.func_70445_o().func_77973_b() == item) {
/* 204 */       result += mc.field_71439_g.field_71071_by.func_70445_o().func_190916_E();
/*     */     }
/*     */     
/* 207 */     return result;
/*     */   }
/*     */   
/*     */   public static ItemStack get(int slot) {
/* 211 */     if (slot == -2) {
/* 212 */       return mc.field_71439_g.field_71071_by.func_70445_o();
/*     */     }
/*     */     
/* 215 */     return (ItemStack)mc.field_71439_g.field_71069_bz.func_75138_a().get(slot);
/*     */   }
/*     */   
/*     */   public static int findSoupAtHotbar() {
/* 219 */     int b = -1;
/* 220 */     for (int a = 0; a < 9; a++) {
/* 221 */       if (mc.field_71439_g.field_71071_by.func_70301_a(a).func_77973_b() == Items.field_151009_A)
/* 222 */         b = a; 
/*     */     } 
/* 224 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findFirstBlockSlot(Class<? extends Block> blockToFind, int lower, int upper) {
/* 229 */     int slot = -1;
/* 230 */     NonNullList<ItemStack> nonNullList = mc.field_71439_g.field_71071_by.field_70462_a;
/*     */     
/* 232 */     for (int i = lower; i <= upper; i++) {
/* 233 */       ItemStack stack = nonNullList.get(i);
/*     */       
/* 235 */       if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock)
/*     */       {
/*     */ 
/*     */         
/* 239 */         if (blockToFind.isInstance(((ItemBlock)stack.func_77973_b()).func_179223_d())) {
/* 240 */           slot = i;
/*     */           break;
/*     */         }  } 
/*     */     } 
/* 244 */     return slot;
/*     */   }
/*     */   
/*     */   public static int getBowAtHotbar() {
/* 248 */     for (int i = 0; i < 9; ) {
/* 249 */       ItemStack itemStack = Util.mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 250 */       if (!(itemStack.func_77973_b() instanceof net.minecraft.item.ItemBow)) { i++; continue; }
/* 251 */        return i;
/*     */     } 
/* 253 */     return -1;
/*     */   }
/*     */   
/*     */   public static int getCrysathotbar() {
/* 257 */     for (int i = 0; i < 9; ) {
/* 258 */       ItemStack itemStack = Util.mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 259 */       if (!(itemStack.func_77973_b() instanceof net.minecraft.item.ItemEndCrystal)) { i++; continue; }
/* 260 */        return i;
/*     */     } 
/* 262 */     return -1;
/*     */   }
/*     */   
/*     */   public static int getPicatHotbar() {
/* 266 */     for (int i = 0; i < 9; ) {
/* 267 */       ItemStack itemStack = Util.mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 268 */       if (!(itemStack.func_77973_b() instanceof net.minecraft.item.ItemPickaxe)) { i++; continue; }
/* 269 */        return i;
/*     */     } 
/* 271 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getPowderAtHotbar() {
/* 276 */     for (int i = 0; i < 9; ) {
/* 277 */       ItemStack itemStack = Util.mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 278 */       if (!itemStack.func_77973_b().func_77653_i(itemStack).equals("Порох")) { i++; continue; }
/* 279 */        return i;
/*     */     } 
/* 281 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findHotbarBlock(Class clazz) {
/* 286 */     for (int i = 0; i < 9; i++) {
/*     */       
/* 288 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 289 */       if (stack != ItemStack.field_190927_a) {
/* 290 */         if (clazz.isInstance(stack.func_77973_b()))
/* 291 */           return i; 
/*     */         Block block;
/* 293 */         if (stack.func_77973_b() instanceof ItemBlock && clazz.isInstance(block = ((ItemBlock)stack.func_77973_b()).func_179223_d()))
/*     */         {
/* 295 */           return i; } 
/*     */       } 
/* 297 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findItemAtHotbar(Item stacks) {
/* 302 */     for (int i = 0; i < 9; i++) {
/* 303 */       Item stack = mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
/* 304 */       if (stack != Items.field_190931_a && 
/* 305 */         stack == stacks) {
/* 306 */         return i;
/*     */       }
/*     */     } 
/* 309 */     return -1;
/*     */   }
/*     */   
/*     */   public static int findHotbarBlock(Block blockIn) {
/* 313 */     for (int i = 0; i < 9; ) {
/*     */       
/* 315 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i); Block block;
/* 316 */       if (stack == ItemStack.field_190927_a || !(stack.func_77973_b() instanceof ItemBlock) || (block = ((ItemBlock)stack.func_77973_b()).func_179223_d()) != blockIn) {
/*     */         i++; continue;
/* 318 */       }  return i;
/*     */     } 
/* 320 */     return -1;
/*     */   }
/*     */   
/*     */   public static int getCappuchinoAtHotbar(boolean old) {
/* 324 */     for (int i = 0; i < 9; i++) {
/* 325 */       ItemStack itemStack = Util.mc.field_71439_g.field_71071_by.func_70301_a(i);
/*     */       
/* 327 */       if (old ? (
/* 328 */         itemStack.func_77973_b() != Items.field_151068_bn) : (
/*     */         
/* 330 */         itemStack.func_77973_b() != Items.field_185157_bK))
/*     */       {
/*     */         
/* 333 */         if (itemStack.func_82833_r().contains("Каппучино"))
/* 334 */           return i;  } 
/*     */     } 
/* 336 */     return -1;
/*     */   }
/*     */   
/*     */   public static int getAmericanoAtHotbar(boolean old) {
/* 340 */     for (int i = 0; i < 9; i++) {
/* 341 */       ItemStack itemStack = Util.mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 342 */       if (old ? (
/* 343 */         itemStack.func_77973_b() != Items.field_151068_bn) : (
/*     */         
/* 345 */         itemStack.func_77973_b() != Items.field_185157_bK))
/*     */       {
/* 347 */         if (itemStack.func_82833_r().contains("Американо"))
/* 348 */           return i;  } 
/*     */     } 
/* 350 */     return -1;
/*     */   }
/*     */   
/*     */   public static int getOzeraAtHotbar(boolean old) {
/* 354 */     for (int i = 0; i < 9; i++) {
/* 355 */       ItemStack itemStack = Util.mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 356 */       if (old ? (
/* 357 */         itemStack.func_77973_b() != Items.field_151068_bn) : (
/*     */         
/* 359 */         itemStack.func_77973_b() != Items.field_185157_bK))
/*     */       {
/* 361 */         if (itemStack.func_82833_r().contains("Родные озёра"))
/* 362 */           return i;  } 
/*     */     } 
/* 364 */     return -1;
/*     */   }
/*     */   
/*     */   public static ItemStack getPotionItemStack(boolean old) {
/* 368 */     for (int i = 0; i < 9; i++) {
/* 369 */       ItemStack itemStack = Util.mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 370 */       if (old ? (
/* 371 */         itemStack.func_77973_b() != Items.field_151068_bn) : (
/*     */         
/* 373 */         itemStack.func_77973_b() != Items.field_185157_bK))
/*     */       {
/* 375 */         if (itemStack.func_82833_r().contains("Каппучино"))
/* 376 */           return itemStack;  } 
/*     */     } 
/* 378 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isInstanceOf(ItemStack stack, Class clazz) {
/* 384 */     if (stack == null) {
/* 385 */       return false;
/*     */     }
/* 387 */     Item item = stack.func_77973_b();
/* 388 */     if (clazz.isInstance(item)) {
/* 389 */       return true;
/*     */     }
/* 391 */     if (item instanceof ItemBlock) {
/* 392 */       Block block = Block.func_149634_a(item);
/* 393 */       return clazz.isInstance(block);
/*     */     } 
/* 395 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isHolding(EntityPlayer player, Item experienceBottle) {
/* 400 */     return (player.func_184614_ca().func_77973_b() == experienceBottle || player.func_184592_cb().func_77973_b() == experienceBottle);
/*     */   }
/*     */   
/*     */   public static boolean isHolding(Item experienceBottle) {
/* 404 */     return (mc.field_71439_g.func_184614_ca().func_77973_b() == experienceBottle || mc.field_71439_g.func_184592_cb().func_77973_b() == experienceBottle);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isHolding(Block block) {
/* 409 */     ItemStack mainHand = mc.field_71439_g.func_184614_ca();
/* 410 */     ItemStack offHand = mc.field_71439_g.func_184592_cb();
/*     */     
/* 412 */     if (!(mainHand.func_77973_b() instanceof ItemBlock) || !(offHand.func_77973_b() instanceof ItemBlock)) return false; 
/* 413 */     return (((ItemBlock)mainHand.func_77973_b()).func_179223_d() == block || ((ItemBlock)offHand.func_77973_b()).func_179223_d() == block);
/*     */   }
/*     */   
/*     */   public static int getRodSlot() {
/* 417 */     for (int i = 0; i < 9; i++) {
/* 418 */       ItemStack item = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 419 */       if (item.func_77973_b() == Items.field_151112_aM && item.func_77952_i() < 52) {
/* 420 */         return i;
/*     */       }
/*     */     } 
/* 423 */     return -1;
/*     */   }
/*     */   
/*     */   public static int swapToHotbarSlot(int slot, boolean silent) {
/* 427 */     if (mc.field_71439_g.field_71071_by.field_70461_c == slot || slot < 0 || slot > 8) return slot; 
/* 428 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/* 429 */     if (!silent) mc.field_71439_g.field_71071_by.field_70461_c = slot; 
/* 430 */     mc.field_71442_b.func_78765_e();
/* 431 */     return slot;
/*     */   }
/*     */   
/*     */   public static int findItem(Class clazz) {
/* 435 */     for (int i = 0; i < 9; i++) {
/* 436 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 437 */       if (stack != ItemStack.field_190927_a) {
/* 438 */         if (clazz.isInstance(stack.func_77973_b())) {
/* 439 */           return i;
/*     */         }
/* 441 */         if (stack.func_77973_b() instanceof ItemBlock && clazz.isInstance(((ItemBlock)stack.func_77973_b()).func_179223_d()))
/*     */         {
/* 443 */           return i; } 
/*     */       } 
/* 445 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getElytra() {
/* 450 */     for (ItemStack stack : mc.field_71439_g.func_184193_aE()) {
/* 451 */       if (stack.func_77973_b() == Items.field_185160_cR) {
/* 452 */         return -2;
/*     */       }
/*     */     } 
/* 455 */     int slot = -1;
/* 456 */     for (int i = 0; i < 36; i++) {
/* 457 */       ItemStack s = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 458 */       if (s.func_77973_b() == Items.field_185160_cR) {
/* 459 */         slot = i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 463 */     if (slot < 9 && slot != -1) {
/* 464 */       slot += 36;
/*     */     }
/* 466 */     return slot;
/*     */   }
/*     */   
/*     */   public static int getFireWorks() {
/* 470 */     for (int i = 0; i < 9; i++) {
/* 471 */       if (mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() instanceof net.minecraft.item.ItemFirework) {
/* 472 */         return i;
/*     */       }
/*     */     } 
/* 475 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\InventoryUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */