/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.play.server.SPacketChat;
/*     */ import net.minecraft.network.play.server.SPacketWindowItems;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ public class AutoBuy
/*     */   extends Module
/*     */ {
/*  24 */   public static List<AutoBuyItem> items = new ArrayList<>();
/*     */   boolean clickGreenPannel = false;
/*     */   boolean clicked = false;
/*     */   boolean direction = false;
/*     */   int pages;
/*  29 */   private final Timer timer = new Timer();
/*  30 */   private final Timer timer2 = new Timer();
/*  31 */   private final Timer roamDelay = new Timer();
/*     */   private int windowId;
/*     */   
/*     */   public AutoBuy() {
/*  35 */     super("AutoBuy", "авто залупка", Module.Category.MISC);
/*     */   }
/*     */ 
/*     */   
/*     */   public static NBTTagList getLoreTagList(ItemStack stack) {
/*  40 */     NBTTagCompound displayTag = getDisplayTag(stack);
/*     */     
/*  42 */     if (!hasLore(stack)) {
/*  43 */       displayTag.func_74782_a("Lore", (NBTBase)new NBTTagList());
/*     */     }
/*     */     
/*  46 */     return displayTag.func_150295_c("Lore", 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasLore(ItemStack stack) {
/*  53 */     return (hasDisplayTag(stack) && getDisplayTag(stack).func_150297_b("Lore", 9));
/*     */   }
/*     */   
/*     */   public static boolean hasDisplayTag(ItemStack stack) {
/*  57 */     return (stack.func_77942_o() && stack.func_77978_p().func_150297_b("display", 10));
/*     */   }
/*     */   
/*     */   public static NBTTagCompound getDisplayTag(ItemStack stack) {
/*  61 */     return stack.func_190925_c("display");
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  66 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*  69 */     if (e.getPacket() instanceof SPacketWindowItems) {
/*  70 */       SPacketWindowItems pac = (SPacketWindowItems)e.getPacket();
/*  71 */       this.windowId = pac.func_148911_c();
/*  72 */       int slot = 0;
/*  73 */       if (this.clickGreenPannel)
/*  74 */         return;  for (ItemStack itemStack : pac.func_148910_d()) {
/*  75 */         for (AutoBuyItem abitem : items) {
/*     */           
/*  77 */           if (Objects.equals(abitem.getName(), (itemStack.func_77973_b().getRegistryName() + "").replace("minecraft:", "")) && 
/*  78 */             getPrice(getLoreTagList(itemStack).toString()) <= abitem.price1) {
/*  79 */             if (abitem.EnchantsIsEmpty()) {
/*  80 */               this.roamDelay.reset();
/*  81 */               Buy(slot); continue;
/*     */             } 
/*  83 */             String[] ench = new String[20];
/*  84 */             int i = 0;
/*  85 */             for (NBTBase tag : itemStack.func_77986_q()) {
/*  86 */               ench[i] = rewriteEnchant(tag.toString());
/*  87 */               i++;
/*     */             } 
/*  89 */             if (isContain(abitem.enchantments, ench)) {
/*  90 */               this.roamDelay.reset();
/*  91 */               Buy(slot);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/*  97 */         slot++;
/*     */       } 
/*     */     } 
/* 100 */     if (e.getPacket() instanceof SPacketChat) {
/* 101 */       SPacketChat packetChat = (SPacketChat)e.getPacket();
/* 102 */       if (packetChat.func_148915_c().func_150260_c().contains("Успешная покупка")) {
/* 103 */         this.clicked = false;
/* 104 */         this.clickGreenPannel = false;
/* 105 */         this.direction = false;
/* 106 */         this.pages = 0;
/* 107 */         this.timer.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 114 */     if (this.timer2.passedMs(100L)) {
/* 115 */       if (mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiChest && this.roamDelay.passedMs(2000L)) {
/* 116 */         if (this.pages < 15 && !this.direction) {
/* 117 */           mc.field_71442_b.func_187098_a(this.windowId, 50, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 118 */           mc.field_71442_b.func_78765_e();
/* 119 */           this.pages++;
/* 120 */         } else if (this.pages == 15 && !this.direction) {
/* 121 */           this.direction = true;
/* 122 */         } else if (this.pages == 0) {
/* 123 */           this.direction = false;
/* 124 */         } else if (this.direction) {
/* 125 */           mc.field_71442_b.func_187098_a(this.windowId, 48, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 126 */           mc.field_71442_b.func_78765_e();
/* 127 */           this.pages--;
/*     */         } 
/*     */       }
/* 130 */       this.timer2.reset();
/*     */     } 
/* 132 */     if (this.clickGreenPannel && !this.clicked) {
/* 133 */       Buy(0);
/* 134 */     } else if (this.clicked) {
/* 135 */       this.clickGreenPannel = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void Buy(int slot) {
/* 140 */     if (this.timer.passedMs(600L)) {
/* 141 */       mc.field_71442_b.func_187098_a(this.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 142 */       mc.field_71442_b.func_78765_e();
/*     */       
/* 144 */       if (slot == 0 && this.clickGreenPannel) {
/* 145 */         this.clicked = true;
/*     */       } else {
/* 147 */         this.clickGreenPannel = true;
/*     */       } 
/* 149 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isContain(String[] m1, String[] m2) {
/* 156 */     int count = 0;
/* 157 */     for (String a : m1) {
/* 158 */       for (String b : m2) {
/* 159 */         if (Objects.equals(a, b)) {
/* 160 */           count++; break;
/*     */         } 
/*     */       } 
/* 163 */     }  return (count == m1.length);
/*     */   }
/*     */   
/*     */   public String rewriteEnchant(String string) {
/* 167 */     String id = StringUtils.substringBetween(string, "id:", "s");
/* 168 */     String lvl = StringUtils.substringBetween(string, "lvl:", "s,");
/* 169 */     return id + "(" + lvl + ")";
/*     */   }
/*     */   
/*     */   public int getPrice(String string) {
/* 173 */     if (string == null) {
/* 174 */       return 9999999;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 179 */     String string2 = StringUtils.substringBetween(string, "за все: ", "$");
/*     */     
/* 181 */     if (string2 == null) {
/* 182 */       return 9999999;
/*     */     }
/* 184 */     string2 = string2.replace(",", "");
/* 185 */     String[] string3 = string2.split("l");
/*     */ 
/*     */     
/* 188 */     return Integer.parseInt(string3[1]);
/*     */   }
/*     */   
/*     */   public static class AutoBuyItem {
/*     */     String name;
/*     */     String[] enchantments;
/*     */     int price1;
/*     */     int price2;
/*     */     boolean noench = true;
/*     */     
/*     */     public AutoBuyItem(String name, String[] enchantments, int price1, int price2, boolean noEnch) {
/* 199 */       this.name = name;
/* 200 */       this.enchantments = enchantments;
/* 201 */       this.price1 = price1;
/* 202 */       this.price2 = price2;
/* 203 */       this.noench = noEnch;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 207 */       return this.name;
/*     */     }
/*     */     
/*     */     public String[] getEnchantments() {
/* 211 */       return this.enchantments;
/*     */     }
/*     */     
/*     */     public int getPrice1() {
/* 215 */       return this.price1;
/*     */     }
/*     */     
/*     */     public int getPrice2() {
/* 219 */       return this.price2;
/*     */     }
/*     */     
/*     */     public boolean EnchantsIsEmpty() {
/* 223 */       return this.noench;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\AutoBuy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */