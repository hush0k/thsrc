/*     */ package com.mrzak34.thunderhack.modules.player;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.setting.SubBind;
/*     */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.PaletteHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class AutoMend extends Module {
/*     */   public Setting<Integer> waterMarkZ1;
/*     */   public Setting<Integer> waterMarkZ2;
/*     */   public Setting<SubBind> subBind;
/*     */   private final Setting<Integer> threshold;
/*     */   private final Setting<Integer> dlay;
/*     */   private final Setting<Integer> armdlay;
/*     */   private final Timer timer;
/*     */   
/*     */   public AutoMend() {
/*  30 */     super("AutoMend", "необходимо включить-AutoArmor!", "turn on AutoArmor!", Module.Category.PLAYER);
/*     */ 
/*     */     
/*  33 */     this.waterMarkZ1 = register(new Setting("Y", Integer.valueOf(10), Integer.valueOf(0), Integer.valueOf(524)));
/*  34 */     this.waterMarkZ2 = register(new Setting("X", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(862)));
/*  35 */     this.subBind = register(new Setting("subbind", new SubBind(56)));
/*  36 */     this.threshold = register(new Setting("Percent", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(100)));
/*  37 */     this.dlay = register(new Setting("ThrowDelay", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(100)));
/*  38 */     this.armdlay = register(new Setting("ArmorDelay", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(1000)));
/*     */     
/*  40 */     this.timer = new Timer();
/*  41 */     this.timer2 = new Timer();
/*     */   }
/*     */   private final Timer timer2; int arm1; int arm2; int arm3; int arm4; int totalarmor; int prev_item;
/*     */   public static boolean isMending = false;
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntitySync(EventSync event) {
/*  48 */     if (PlayerUtils.isKeyDown(((SubBind)this.subBind.getValue()).getKey())) {
/*  49 */       mc.field_71439_g.field_70125_A = 90.0F;
/*  50 */       isMending = true;
/*     */     } else {
/*  52 */       isMending = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void postEntitySync(EventPostSync e) {
/*  58 */     if (fullNullCheck())
/*     */       return; 
/*  60 */     if (PlayerUtils.isKeyDown(((SubBind)this.subBind.getValue()).getKey()) && (
/*  61 */       calculatePercentage(mc.field_71439_g.field_71071_by.func_70301_a(39)) < ((Integer)this.threshold.getValue()).intValue() || 
/*  62 */       calculatePercentage(mc.field_71439_g.field_71071_by.func_70301_a(38)) < ((Integer)this.threshold.getValue()).intValue() || 
/*  63 */       calculatePercentage(mc.field_71439_g.field_71071_by.func_70301_a(37)) < ((Integer)this.threshold.getValue()).intValue() || 
/*  64 */       calculatePercentage(mc.field_71439_g.field_71071_by.func_70301_a(36)) < ((Integer)this.threshold.getValue()).intValue()) && 
/*  65 */       getXpSlot() != -1) {
/*     */       
/*  67 */       this.prev_item = mc.field_71439_g.field_71071_by.field_70461_c;
/*  68 */       mc.field_71439_g.field_71071_by.field_70461_c = getXpSlot();
/*  69 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(getXpSlot()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  75 */       ItemStack[] armorStacks = { mc.field_71439_g.field_71071_by.func_70301_a(39), mc.field_71439_g.field_71071_by.func_70301_a(38), mc.field_71439_g.field_71071_by.func_70301_a(37), mc.field_71439_g.field_71071_by.func_70301_a(36) };
/*     */ 
/*     */       
/*  78 */       for (int i = 0; i < 4; i++) {
/*  79 */         ItemStack stack = armorStacks[i];
/*  80 */         if (stack.func_77973_b() instanceof net.minecraft.item.ItemArmor && 
/*  81 */           calculatePercentage(stack) >= ((Integer)this.threshold.getValue()).intValue()) {
/*  82 */           for (int s = 0; s < 36; s++) {
/*  83 */             ItemStack emptyStack = mc.field_71439_g.field_71071_by.func_70301_a(s);
/*  84 */             if (emptyStack.func_190926_b() && emptyStack.func_77973_b() == Items.field_190931_a && 
/*  85 */               this.timer2.passedMs(((Integer)this.armdlay.getValue()).intValue())) {
/*  86 */               mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, i + 5, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*  87 */               mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, (s < 9) ? (s + 36) : s, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*  88 */               mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, i + 5, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*  89 */               mc.field_71442_b.func_78765_e();
/*  90 */               this.timer2.reset();
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*  96 */       if (this.timer.passedMs(((Integer)this.dlay.getValue()).intValue())) {
/*  97 */         mc.field_71442_b.func_187101_a((EntityPlayer)mc.field_71439_g, (World)mc.field_71441_e, EnumHand.MAIN_HAND);
/*  98 */         this.timer.reset();
/*     */       }
/*     */     
/* 101 */     } else if (this.prev_item != -1) {
/* 102 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.prev_item));
/* 103 */       this.prev_item = -1;
/* 104 */       this.arm1 = 0;
/* 105 */       this.arm2 = 0;
/* 106 */       this.arm3 = 0;
/* 107 */       this.arm4 = 0;
/* 108 */       this.totalarmor = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getXpSlot() {
/* 113 */     ItemStack stack = mc.field_71439_g.func_184614_ca();
/*     */     
/* 115 */     if (!stack.func_190926_b() && stack.func_77973_b() instanceof net.minecraft.item.ItemExpBottle) {
/* 116 */       return mc.field_71439_g.field_71071_by.field_70461_c;
/*     */     }
/* 118 */     for (int i = 0; i < 9; i++) {
/* 119 */       stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 120 */       if (!stack.func_190926_b() && stack.func_77973_b() instanceof net.minecraft.item.ItemExpBottle) {
/* 121 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 125 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/* 131 */     if (PlayerUtils.isKeyDown(((SubBind)this.subBind.getValue()).getKey())) {
/* 132 */       RenderUtil.drawSmoothRect(((Integer)this.waterMarkZ2.getValue()).intValue(), ((Integer)this.waterMarkZ1.getValue()).intValue(), (106 + ((Integer)this.waterMarkZ2.getValue()).intValue()), (35 + ((Integer)this.waterMarkZ1.getValue()).intValue()), (new Color(35, 35, 40, 230)).getRGB());
/* 133 */       RenderUtil.drawSmoothRect((((Integer)this.waterMarkZ2.getValue()).intValue() + 3), (((Integer)this.waterMarkZ1.getValue()).intValue() + 12), (103 + ((Integer)this.waterMarkZ2.getValue()).intValue()), (15 + ((Integer)this.waterMarkZ1.getValue()).intValue()), (new Color(51, 51, 58, 230)).getRGB());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       ItemStack[] armorStacks = { mc.field_71439_g.field_71071_by.func_70301_a(39), mc.field_71439_g.field_71071_by.func_70301_a(38), mc.field_71439_g.field_71071_by.func_70301_a(37), mc.field_71439_g.field_71071_by.func_70301_a(36) };
/*     */ 
/*     */       
/* 142 */       ItemStack stack = armorStacks[0];
/* 143 */       ItemStack stack1 = armorStacks[1];
/* 144 */       ItemStack stack2 = armorStacks[2];
/* 145 */       ItemStack stack3 = armorStacks[3];
/*     */ 
/*     */       
/* 148 */       if ((int)calculatePercentage(stack) >= this.arm1) {
/* 149 */         this.arm1 = (int)calculatePercentage(stack);
/*     */       }
/* 151 */       if ((int)calculatePercentage(stack1) >= this.arm2) {
/* 152 */         this.arm2 = (int)calculatePercentage(stack1);
/*     */       }
/* 154 */       if ((int)calculatePercentage(stack2) >= this.arm3) {
/* 155 */         this.arm3 = (int)calculatePercentage(stack2);
/*     */       }
/* 157 */       if ((int)calculatePercentage(stack3) >= this.arm4) {
/* 158 */         this.arm4 = (int)calculatePercentage(stack3);
/*     */       }
/*     */       
/* 161 */       this.totalarmor = (this.arm1 + this.arm3 + this.arm4 + this.arm2) / 4;
/*     */       
/* 163 */       float progress = (this.arm1 + this.arm3 + this.arm4 + this.arm2) / 400.0F;
/*     */       
/* 165 */       int expCount = getExpCount();
/*     */       
/* 167 */       mc.func_175599_af().func_175042_a(new ItemStack(Items.field_151062_by), ((Integer)this.waterMarkZ2.getValue()).intValue() + 70 + 11, ((Integer)this.waterMarkZ1.getValue()).intValue() + 17);
/* 168 */       String s3 = String.valueOf(expCount);
/* 169 */       Util.fr.func_175063_a(s3, (((Integer)this.waterMarkZ2.getValue()).intValue() + 85 + 11), (((Integer)this.waterMarkZ1.getValue()).intValue() + 9 + 17), 16777215);
/*     */       
/* 171 */       RenderUtil.drawSmoothRect((((Integer)this.waterMarkZ2.getValue()).intValue() + 3), (((Integer)this.waterMarkZ1.getValue()).intValue() + 12), (this.totalarmor + ((Integer)this.waterMarkZ2.getValue()).intValue() + 5), (15 + ((Integer)this.waterMarkZ1.getValue()).intValue()), PaletteHelper.fade((new Color(255, 0, 0, 255)).getRGB(), (new Color(0, 255, 0, 255)).getRGB(), progress));
/*     */       
/* 173 */       Util.fr.func_175063_a("Mending...", (((Integer)this.waterMarkZ2.getValue()).intValue() + 3), (((Integer)this.waterMarkZ1.getValue()).intValue() + 1), PaletteHelper.astolfo(false, 1).getRGB());
/*     */       
/* 175 */       int width = ((Integer)this.waterMarkZ2.getValue()).intValue() + -12;
/* 176 */       int height = ((Integer)this.waterMarkZ1.getValue()).intValue() + 17;
/* 177 */       GlStateManager.func_179098_w();
/* 178 */       int iteration = 0;
/* 179 */       for (ItemStack is : mc.field_71439_g.field_71071_by.field_70460_b) {
/* 180 */         iteration++;
/* 181 */         if (is.func_190926_b())
/*     */           continue; 
/* 183 */         int x = width - 90 + (9 - iteration) * 20 + 2;
/* 184 */         GlStateManager.func_179126_j();
/* 185 */         RenderUtil.itemRender.field_77023_b = 200.0F;
/* 186 */         RenderUtil.itemRender.func_180450_b(is, x, height);
/* 187 */         RenderUtil.itemRender.func_180453_a(mc.field_71466_p, is, x, height, "");
/* 188 */         RenderUtil.itemRender.field_77023_b = 0.0F;
/* 189 */         GlStateManager.func_179098_w();
/* 190 */         GlStateManager.func_179140_f();
/* 191 */         GlStateManager.func_179097_i();
/* 192 */         String s = (is.func_190916_E() > 1) ? (is.func_190916_E() + "") : "";
/* 193 */         mc.field_71466_p.func_175063_a(s, (x + 19 - 2 - mc.field_71466_p.func_78256_a(s)), (height + 9), 16777215);
/*     */       } 
/* 195 */       GlStateManager.func_179126_j();
/* 196 */       GlStateManager.func_179140_f();
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getExpCount() {
/* 201 */     int expCount = 0;
/* 202 */     for (int i = 0; i < 45; i++) {
/* 203 */       if (mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b().equals(Items.field_151062_by)) {
/* 204 */         expCount += mc.field_71439_g.field_71071_by.func_70301_a(i).func_190916_E();
/*     */       }
/*     */     } 
/* 207 */     if (mc.field_71439_g.func_184592_cb().func_77973_b().equals(Items.field_151062_by)) {
/* 208 */       expCount++;
/*     */     }
/* 210 */     return expCount;
/*     */   }
/*     */   
/*     */   public static float calculatePercentage(ItemStack stack) {
/* 214 */     float durability = (stack.func_77958_k() - stack.func_77952_i());
/* 215 */     return durability / stack.func_77958_k() * 100.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\AutoMend.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */