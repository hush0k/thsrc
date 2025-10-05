/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Bind;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemShulkerBox;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntityShulkerBox;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import net.minecraftforge.client.event.RenderTooltipEvent;
/*     */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class ToolTips
/*     */   extends Module
/*     */ {
/*  42 */   private static final ResourceLocation MAP = new ResourceLocation("textures/map/map_background.png");
/*  43 */   private static final ResourceLocation SHULKER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
/*  44 */   private static ToolTips INSTANCE = new ToolTips();
/*  45 */   public Setting<Boolean> maps = register(new Setting("Maps", Boolean.valueOf(true)));
/*  46 */   public Setting<Boolean> shulkers = register(new Setting("ShulkerViewer", Boolean.valueOf(true)));
/*  47 */   public Setting<Bind> peek = register(new Setting("Peek", new Bind(-1)));
/*  48 */   public Setting<Boolean> shulkerSpy = register(new Setting("ShulkerSpy", Boolean.valueOf(true)));
/*  49 */   public Setting<Boolean> render = register(new Setting("Render", Boolean.valueOf(true), v -> ((Boolean)this.shulkerSpy.getValue()).booleanValue()));
/*  50 */   public Setting<Boolean> own = register(new Setting("OwnShulker", Boolean.valueOf(true), v -> ((Boolean)this.shulkerSpy.getValue()).booleanValue()));
/*  51 */   public Setting<Integer> cooldown = register(new Setting("ShowForS", Integer.valueOf(2), Integer.valueOf(0), Integer.valueOf(5), v -> ((Boolean)this.shulkerSpy.getValue()).booleanValue()));
/*  52 */   public Setting<Boolean> textColor = register(new Setting("TextColor", Boolean.valueOf(false), v -> ((Boolean)this.shulkers.getValue()).booleanValue()));
/*  53 */   private final Setting<Integer> red = register(new Setting("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.textColor.getValue()).booleanValue()));
/*  54 */   private final Setting<Integer> green = register(new Setting("Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.textColor.getValue()).booleanValue()));
/*  55 */   private final Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.textColor.getValue()).booleanValue()));
/*  56 */   private final Setting<Integer> alpha = register(new Setting("Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.textColor.getValue()).booleanValue()));
/*  57 */   public Setting<Boolean> offsets = register(new Setting("Offsets", Boolean.valueOf(false)));
/*  58 */   private final Setting<Integer> yPerPlayer = register(new Setting("Y/Player", Integer.valueOf(18), v -> ((Boolean)this.offsets.getValue()).booleanValue()));
/*  59 */   private final Setting<Integer> xOffset = register(new Setting("XOffset", Integer.valueOf(4), v -> ((Boolean)this.offsets.getValue()).booleanValue()));
/*  60 */   private final Setting<Integer> yOffset = register(new Setting("YOffset", Integer.valueOf(2), v -> ((Boolean)this.offsets.getValue()).booleanValue()));
/*  61 */   private final Setting<Integer> trOffset = register(new Setting("TROffset", Integer.valueOf(2), v -> ((Boolean)this.offsets.getValue()).booleanValue()));
/*  62 */   public Setting<Integer> invH = register(new Setting("InvH", Integer.valueOf(3), v -> ((Boolean)this.offsets.getValue()).booleanValue()));
/*  63 */   public Map<EntityPlayer, ItemStack> spiedPlayers = new ConcurrentHashMap<>();
/*  64 */   public Map<EntityPlayer, Timer> playerTimers = new ConcurrentHashMap<>();
/*     */   private int textRadarY;
/*     */   
/*     */   public ToolTips() {
/*  68 */     super("ToolTips", "показывает содержимое-шалкера/карты/книги", Module.Category.MISC);
/*  69 */     setInstance();
/*     */   }
/*     */   
/*     */   public static ToolTips getInstance() {
/*  73 */     if (INSTANCE == null) {
/*  74 */       INSTANCE = new ToolTips();
/*     */     }
/*  76 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static void displayInv(ItemStack stack, String name) {
/*     */     try {
/*  81 */       Item item = stack.func_77973_b();
/*  82 */       TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
/*  83 */       ItemShulkerBox shulker = (ItemShulkerBox)item;
/*     */       
/*  85 */       entityBox.func_145834_a((World)mc.field_71441_e);
/*  86 */       NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
/*  87 */       ItemStackHelper.func_191283_b(((NBTTagCompound)Objects.<NBTTagCompound>requireNonNull(stack.func_77978_p())).func_74775_l("BlockEntityTag"), nonnulllist);
/*  88 */       entityBox.func_145839_a(stack.func_77978_p().func_74775_l("BlockEntityTag"));
/*  89 */       entityBox.func_190575_a((name == null) ? stack.func_82833_r() : name);
/*  90 */       (new Thread(() -> {
/*     */             try {
/*     */               Thread.sleep(200L);
/*  93 */             } catch (InterruptedException interruptedException) {}
/*     */             
/*     */             mc.field_71439_g.func_71007_a((IInventory)entityBox);
/*  96 */           })).start();
/*  97 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private void setInstance() {
/* 102 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 109 */     if (fullNullCheck() || !((Boolean)this.shulkerSpy.getValue()).booleanValue())
/*     */       return;  ItemStack stack;
/*     */     Slot slot;
/* 112 */     if (((Bind)this.peek.getValue()).getKey() != -1 && mc.field_71462_r instanceof GuiContainer && Keyboard.isKeyDown(((Bind)this.peek.getValue()).getKey()) && (slot = ((GuiContainer)mc.field_71462_r).getSlotUnderMouse()) != null && (stack = slot.func_75211_c()) != null && stack.func_77973_b() instanceof ItemShulkerBox) {
/* 113 */       displayInv(stack, (String)null);
/*     */     }
/* 115 */     for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/* 116 */       if (player == null || !(player.func_184614_ca().func_77973_b() instanceof ItemShulkerBox) || (!((Boolean)this.own.getValue()).booleanValue() && mc.field_71439_g.equals(player)))
/*     */         continue; 
/* 118 */       ItemStack stack2 = player.func_184614_ca();
/* 119 */       this.spiedPlayers.put(player, stack2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender2D(Render2DEvent event) {
/* 125 */     if (fullNullCheck() || !((Boolean)this.shulkerSpy.getValue()).booleanValue() || !((Boolean)this.render.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/* 128 */     int x = -4 + ((Integer)this.xOffset.getValue()).intValue();
/* 129 */     int y = 10 + ((Integer)this.yOffset.getValue()).intValue();
/* 130 */     this.textRadarY = 0;
/* 131 */     for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/*     */       
/* 133 */       if (this.spiedPlayers.get(player) == null)
/* 134 */         continue;  player.func_184614_ca();
/* 135 */       if (!(player.func_184614_ca().func_77973_b() instanceof ItemShulkerBox)) {
/* 136 */         Timer playerTimer = this.playerTimers.get(player);
/* 137 */         if (playerTimer == null)
/* 138 */         { Timer timer = new Timer();
/* 139 */           timer.reset();
/* 140 */           this.playerTimers.put(player, timer); }
/* 141 */         else if (playerTimer.passedS(((Integer)this.cooldown.getValue()).intValue())) { continue; }
/*     */       
/*     */       } else {
/* 144 */         Timer playerTimer; if (player.func_184614_ca().func_77973_b() instanceof ItemShulkerBox && (playerTimer = this.playerTimers.get(player)) != null) {
/* 145 */           playerTimer.reset();
/* 146 */           this.playerTimers.put(player, playerTimer);
/*     */         } 
/* 148 */       }  ItemStack stack = this.spiedPlayers.get(player);
/* 149 */       renderShulkerToolTip(stack, x, y, player.func_70005_c_());
/* 150 */       this.textRadarY = (y += ((Integer)this.yPerPlayer.getValue()).intValue() + 60) - 10 - ((Integer)this.yOffset.getValue()).intValue() + ((Integer)this.trOffset.getValue()).intValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void makeTooltip(ItemTooltipEvent event) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderTooltip(RenderTooltipEvent.PostText event) {
/*     */     MapData mapData;
/* 162 */     if (((Boolean)this.maps.getValue()).booleanValue() && !event.getStack().func_190926_b() && event.getStack().func_77973_b() instanceof net.minecraft.item.ItemMap && (mapData = Items.field_151098_aY.func_77873_a(event.getStack(), (World)mc.field_71441_e)) != null) {
/* 163 */       GlStateManager.func_179094_E();
/* 164 */       GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
/* 165 */       RenderHelper.func_74518_a();
/* 166 */       mc.func_110434_K().func_110577_a(MAP);
/* 167 */       Tessellator instance = Tessellator.func_178181_a();
/* 168 */       BufferBuilder buffer = instance.func_178180_c();
/* 169 */       int n = 7;
/* 170 */       float n2 = 135.0F;
/* 171 */       float n3 = 0.5F;
/* 172 */       GlStateManager.func_179109_b(event.getX(), event.getY() - n2 * n3 - 5.0F, 0.0F);
/* 173 */       GlStateManager.func_179152_a(n3, n3, n3);
/* 174 */       buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 175 */       buffer.func_181662_b(-n, n2, 0.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
/* 176 */       buffer.func_181662_b(n2, n2, 0.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
/* 177 */       buffer.func_181662_b(n2, -n, 0.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
/* 178 */       buffer.func_181662_b(-n, -n, 0.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
/* 179 */       instance.func_78381_a();
/* 180 */       mc.field_71460_t.func_147701_i().func_148250_a(mapData, false);
/* 181 */       GlStateManager.func_179145_e();
/* 182 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderShulkerToolTip(ItemStack stack, int x, int y, String name) {
/* 188 */     NBTTagCompound tagCompound = stack.func_77978_p(); NBTTagCompound blockEntityTag;
/* 189 */     if (tagCompound != null && tagCompound.func_150297_b("BlockEntityTag", 10) && (blockEntityTag = tagCompound.func_74775_l("BlockEntityTag")).func_150297_b("Items", 9)) {
/* 190 */       GlStateManager.func_179098_w();
/* 191 */       GlStateManager.func_179140_f();
/* 192 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 193 */       GlStateManager.func_179147_l();
/* 194 */       GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 195 */       mc.func_110434_K().func_110577_a(SHULKER_GUI_TEXTURE);
/* 196 */       RenderUtil.drawTexturedRect(x, y, 0, 0, 176, 16, 500);
/* 197 */       RenderUtil.drawTexturedRect(x, y + 16, 0, 16, 176, 54 + ((Integer)this.invH.getValue()).intValue(), 500);
/* 198 */       RenderUtil.drawTexturedRect(x, y + 16 + 54, 0, 160, 176, 8, 500);
/* 199 */       GlStateManager.func_179097_i();
/* 200 */       Color color = new Color(0, 0, 0, 255);
/* 201 */       if (((Boolean)this.textColor.getValue()).booleanValue()) {
/* 202 */         color = new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue());
/*     */       }
/* 204 */       mc.field_71466_p.func_175063_a((name == null) ? stack.func_82833_r() : name, (x + 8), (y + 6), color.getRGB());
/* 205 */       GlStateManager.func_179126_j();
/* 206 */       RenderHelper.func_74520_c();
/* 207 */       GlStateManager.func_179091_B();
/* 208 */       GlStateManager.func_179142_g();
/* 209 */       GlStateManager.func_179145_e();
/* 210 */       NonNullList nonnulllist = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
/* 211 */       ItemStackHelper.func_191283_b(blockEntityTag, nonnulllist);
/* 212 */       for (int i = 0; i < nonnulllist.size(); i++) {
/* 213 */         int iX = x + i % 9 * 18 + 8;
/* 214 */         int iY = y + i / 9 * 18 + 18;
/* 215 */         ItemStack itemStack = (ItemStack)nonnulllist.get(i);
/* 216 */         (mc.func_175599_af()).field_77023_b = 501.0F;
/* 217 */         RenderUtil.itemRender.func_180450_b(itemStack, iX, iY);
/* 218 */         RenderUtil.itemRender.func_180453_a(mc.field_71466_p, itemStack, iX, iY, null);
/* 219 */         (mc.func_175599_af()).field_77023_b = 0.0F;
/*     */       } 
/* 221 */       GlStateManager.func_179140_f();
/* 222 */       GlStateManager.func_179084_k();
/* 223 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\ToolTips.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */