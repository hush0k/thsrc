/*     */ package com.mrzak34.thunderhack.modules.player;
/*     */ 
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ElytraSwap extends Module {
/*  22 */   private final ResourceLocation toelytra = new ResourceLocation("textures/swapel.png");
/*  23 */   private final ResourceLocation tochest = new ResourceLocation("textures/swapch.png");
/*  24 */   public Setting<Boolean> image = register(new Setting("indicator", Boolean.valueOf(true)));
/*  25 */   public Setting<Integer> imagex = register(new Setting("indicatorX", Integer.valueOf(512), Integer.valueOf(0), Integer.valueOf(1023), v -> ((Boolean)this.image.getValue()).booleanValue()));
/*  26 */   public Setting<Integer> imagey = register(new Setting("indicatorY", Integer.valueOf(512), Integer.valueOf(0), Integer.valueOf(1023), v -> ((Boolean)this.image.getValue()).booleanValue()));
/*  27 */   public Timer timer = new Timer();
/*  28 */   public int swap = 0;
/*     */   public ElytraSwap() {
/*  30 */     super("ElytraSwap", "свап между нагрудником-и элитрой", Module.Category.PLAYER);
/*     */   }
/*     */   
/*     */   public static int getChestPlateSlot() {
/*  34 */     Item[] items = { (Item)Items.field_151163_ad, (Item)Items.field_151023_V, (Item)Items.field_151030_Z, (Item)Items.field_151171_ah, (Item)Items.field_151027_R };
/*     */     
/*  36 */     for (Item item : items) {
/*  37 */       if (hasItem(item)) {
/*  38 */         return getSlot(item);
/*     */       }
/*     */     } 
/*     */     
/*  42 */     return -1;
/*     */   }
/*     */   
/*     */   public static boolean hasItem(Item item) {
/*  46 */     return (getAmountOfItem(item) != 0);
/*     */   }
/*     */   
/*     */   public static int getAmountOfItem(Item item) {
/*  50 */     int count = 0;
/*     */     
/*  52 */     for (ItemStackUtil itemStack : getAllItems()) {
/*  53 */       if (itemStack.itemStack != null && itemStack.itemStack.func_77973_b().equals(item)) {
/*  54 */         count += itemStack.itemStack.func_190916_E();
/*     */       }
/*     */     } 
/*     */     
/*  58 */     return count;
/*     */   }
/*     */   
/*     */   public static void drawCompleteImage(float posX, float posY, int width, int height) {
/*  62 */     GL11.glPushMatrix();
/*  63 */     GL11.glTranslatef(posX, posY, 0.0F);
/*  64 */     GL11.glBegin(7);
/*  65 */     GL11.glTexCoord2f(0.0F, 0.0F);
/*  66 */     GL11.glVertex3f(0.0F, 0.0F, 0.0F);
/*  67 */     GL11.glTexCoord2f(0.0F, 1.0F);
/*  68 */     GL11.glVertex3f(0.0F, height, 0.0F);
/*  69 */     GL11.glTexCoord2f(1.0F, 1.0F);
/*  70 */     GL11.glVertex3f(width, height, 0.0F);
/*  71 */     GL11.glTexCoord2f(1.0F, 0.0F);
/*  72 */     GL11.glVertex3f(width, 0.0F, 0.0F);
/*  73 */     GL11.glEnd();
/*  74 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static int getClickSlot(int id) {
/*  78 */     if (id == -1) {
/*  79 */       return id;
/*     */     }
/*     */     
/*  82 */     if (id < 9) {
/*  83 */       id += 36;
/*  84 */       return id;
/*     */     } 
/*     */     
/*  87 */     if (id == 39) {
/*  88 */       id = 5;
/*  89 */     } else if (id == 38) {
/*  90 */       id = 6;
/*  91 */     } else if (id == 37) {
/*  92 */       id = 7;
/*  93 */     } else if (id == 36) {
/*  94 */       id = 8;
/*  95 */     } else if (id == 40) {
/*  96 */       id = 45;
/*     */     } 
/*     */     
/*  99 */     return id;
/*     */   }
/*     */   
/*     */   public static void clickSlot(int id) {
/* 103 */     if (id != -1) {
/*     */       try {
/* 105 */         mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71070_bA.field_75152_c, getClickSlot(id), 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 106 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSlot(Item item) {
/*     */     try {
/* 114 */       for (ItemStackUtil itemStack : getAllItems()) {
/* 115 */         if (itemStack.itemStack.func_77973_b().equals(item)) {
/* 116 */           return itemStack.slotId;
/*     */         }
/*     */       } 
/* 119 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 123 */     return -1;
/*     */   }
/*     */   
/*     */   public static ArrayList<ItemStackUtil> getAllItems() {
/* 127 */     ArrayList<ItemStackUtil> items = new ArrayList<>();
/*     */     
/* 129 */     for (int i = 0; i < 36; i++) {
/* 130 */       items.add(new ItemStackUtil(getItemStack(i), i));
/*     */     }
/*     */     
/* 133 */     return items;
/*     */   }
/*     */   
/*     */   public static ItemStack getItemStack(int id) {
/*     */     try {
/* 138 */       return mc.field_71439_g.field_71071_by.func_70301_a(id);
/* 139 */     } catch (NullPointerException e) {
/* 140 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 146 */     mc.field_71439_g.func_70095_a(true);
/* 147 */     this.timer.reset();
/* 148 */     ItemStack itemStack = getItemStack(38);
/*     */     
/* 150 */     if (itemStack.func_77973_b() == Items.field_185160_cR) {
/* 151 */       int slot = getChestPlateSlot();
/* 152 */       if (slot != -1) {
/* 153 */         clickSlot(slot);
/* 154 */         clickSlot(38);
/* 155 */         clickSlot(slot);
/* 156 */         this.swap = 1;
/*     */       } else {
/* 158 */         Command.sendMessage("У тебя нет честплейта!");
/*     */       } 
/* 160 */     } else if (hasItem(Items.field_185160_cR)) {
/* 161 */       int slot = getSlot(Items.field_185160_cR);
/* 162 */       clickSlot(slot);
/* 163 */       clickSlot(38);
/* 164 */       clickSlot(slot);
/* 165 */       this.swap = 2;
/*     */     } else {
/* 167 */       Command.sendMessage("У тебя нет элитры!");
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent event) {
/* 173 */     System.out.println(this.swap);
/* 174 */     double psx = ((Integer)this.imagex.getValue()).intValue();
/* 175 */     double psy = ((Integer)this.imagey.getValue()).intValue();
/* 176 */     float xOffset = (float)psx + 10.0F;
/* 177 */     float yOffset = (float)psy;
/* 178 */     if (this.swap == 1) {
/* 179 */       RenderUtil.drawRect(400.0F, 400.0F, 400.0F, 400.0F, (new Color(252, 252, 252, 255)).getRGB());
/* 180 */       Util.mc.func_110434_K().func_110577_a(this.toelytra);
/* 181 */       drawCompleteImage(xOffset - 1.0F, yOffset - 160.0F, 49, 49);
/*     */     } 
/* 183 */     if (this.swap == 2) {
/* 184 */       RenderUtil.drawRect(400.0F, 400.0F, 400.0F, 400.0F, (new Color(252, 252, 252, 255)).getRGB());
/* 185 */       Util.mc.func_110434_K().func_110577_a(this.tochest);
/* 186 */       drawCompleteImage(xOffset - 1.0F, yOffset - 160.0F, 49, 49);
/*     */     } 
/* 188 */     if (this.timer.passedMs(1000L)) {
/* 189 */       mc.field_71439_g.func_70095_a(false);
/* 190 */       disable();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class ItemStackUtil
/*     */   {
/*     */     public ItemStack itemStack;
/*     */     public int slotId;
/*     */     
/*     */     public ItemStackUtil(ItemStack itemStack, int slotId) {
/* 200 */       this.itemStack = itemStack;
/* 201 */       this.slotId = slotId;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\ElytraSwap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */