/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PreRenderEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StorageEsp
/*     */   extends Module
/*     */ {
/*  33 */   public final Setting<Float> range = register(new Setting("Range", Float.valueOf(50.0F), Float.valueOf(1.0F), Float.valueOf(300.0F)));
/*     */ 
/*     */   
/*  36 */   public final Setting<Boolean> chest = register(new Setting("Chest", Boolean.valueOf(true)));
/*  37 */   public final Setting<Boolean> dispenser = register(new Setting("Dispenser", Boolean.valueOf(false)));
/*  38 */   public final Setting<Boolean> shulker = register(new Setting("Shulker", Boolean.valueOf(true)));
/*  39 */   public final Setting<Boolean> echest = register(new Setting("Ender Chest", Boolean.valueOf(true)));
/*  40 */   public final Setting<Boolean> furnace = register(new Setting("Furnace", Boolean.valueOf(false)));
/*  41 */   public final Setting<Boolean> hopper = register(new Setting("Hopper", Boolean.valueOf(false)));
/*  42 */   public final Setting<Boolean> cart = register(new Setting("Minecart", Boolean.valueOf(false)));
/*  43 */   public final Setting<Boolean> frame = register(new Setting("ItemFrame", Boolean.valueOf(false)));
/*  44 */   private final Setting<ColorSetting> chestColor = register(new Setting("ChestColor", new ColorSetting(-2013200640)));
/*  45 */   private final Setting<ColorSetting> shulkColor = register(new Setting("ShulkerColor", new ColorSetting(-2013200640)));
/*  46 */   private final Setting<ColorSetting> echestColor = register(new Setting("EChestColor", new ColorSetting(-2013200640)));
/*  47 */   private final Setting<ColorSetting> frameColor = register(new Setting("FrameColor", new ColorSetting(-2013200640)));
/*  48 */   private final Setting<ColorSetting> shulkerframeColor = register(new Setting("ShulkFrameColor", new ColorSetting(-2013200640)));
/*  49 */   private final Setting<ColorSetting> furnaceColor = register(new Setting("FurnaceColor", new ColorSetting(-2013200640)));
/*  50 */   private final Setting<ColorSetting> hopperColor = register(new Setting("HopperColor", new ColorSetting(-2013200640)));
/*  51 */   private final Setting<ColorSetting> dispenserColor = register(new Setting("DispenserColor", new ColorSetting(-2013200640)));
/*  52 */   private final Setting<ColorSetting> minecartColor = register(new Setting("MinecartColor", new ColorSetting(-2013200640)));
/*  53 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.ShaderBox));
/*  54 */   public final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(10.0F), v -> (this.mode.getValue() != Mode.Box)));
/*  55 */   public final Setting<Integer> boxAlpha = register(new Setting("BoxAlpha", Integer.valueOf(170), Integer.valueOf(0), Integer.valueOf(255), v -> (this.mode.getValue() != Mode.Outline)));
/*  56 */   private final ArrayList<Storage> storages = new ArrayList<>();
/*     */   public StorageEsp() {
/*  58 */     super("StorageESP", "подсвечивает контейнеры", Module.Category.RENDER);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderingShit(PreRenderEvent event) {
/*  65 */     boolean depth = GL11.glIsEnabled(2929);
/*  66 */     GlStateManager.func_179097_i();
/*     */     
/*  68 */     if (this.mode.getValue() == Mode.ShaderBox || this.mode.getValue() == Mode.ShaderOutline) {
/*  69 */       checkSetupFBO();
/*     */     }
/*  71 */     for (TileEntity tileEntity : mc.field_71441_e.field_147482_g) {
/*     */       BlockPos pos;
/*  73 */       if (((tileEntity instanceof TileEntityChest && ((Boolean)this.chest.getValue()).booleanValue()) || (tileEntity instanceof net.minecraft.tileentity.TileEntityDispenser && ((Boolean)this.dispenser.getValue()).booleanValue()) || (tileEntity instanceof net.minecraft.tileentity.TileEntityShulkerBox && ((Boolean)this.shulker.getValue()).booleanValue()) || (tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest && ((Boolean)this.echest.getValue()).booleanValue()) || (tileEntity instanceof net.minecraft.tileentity.TileEntityFurnace && ((Boolean)this.furnace.getValue()).booleanValue()) || (tileEntity instanceof net.minecraft.tileentity.TileEntityHopper && ((Boolean)this.hopper.getValue()).booleanValue())) && mc.field_71439_g.func_174818_b(pos = tileEntity.func_174877_v()) <= MathUtil.square(((Float)this.range.getValue()).floatValue())) {
/*     */         
/*  75 */         int mode = 0;
/*     */         
/*  77 */         if (tileEntity instanceof TileEntityChest) {
/*  78 */           TileEntityChest chest = (TileEntityChest)tileEntity;
/*  79 */           if (chest.field_145988_l != null) {
/*  80 */             mode = 3;
/*  81 */           } else if (chest.field_145990_j != null) {
/*  82 */             mode = 1;
/*  83 */           } else if (chest.field_145991_k != null) {
/*  84 */             mode = 2;
/*  85 */           } else if (chest.field_145992_i != null) {
/*  86 */             mode = 4;
/*     */           } 
/*     */         } 
/*  89 */         this.storages.add(new Storage(pos, getTileEntityColor(tileEntity), mode));
/*     */       } 
/*     */     } 
/*  92 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/*     */       BlockPos pos;
/*  94 */       if (((entity instanceof EntityItemFrame && ((Boolean)this.frame.getValue()).booleanValue()) || (entity instanceof net.minecraft.entity.item.EntityMinecartChest && ((Boolean)this.cart.getValue()).booleanValue())) && mc.field_71439_g.func_174818_b(pos = entity.func_180425_c()) <= MathUtil.square(((Float)this.range.getValue()).floatValue())) {
/*  95 */         this.storages.add(new Storage(pos, getEntityColor(entity), 0));
/*     */       }
/*     */     } 
/*  98 */     for (Storage storage : this.storages) {
/*  99 */       if (this.mode.getValue() != Mode.ShaderOutline) {
/* 100 */         GlStateManager.func_179094_E();
/* 101 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 102 */         RenderUtil.drawBoxESP(storage.position, new Color(storage.color), false, new Color(storage.color), ((Float)this.lineWidth.getValue()).floatValue(), (this.mode.getValue() == Mode.Outline || this.mode.getValue() == Mode.BoxOutline), (this.mode.getValue() == Mode.ShaderBox || this.mode.getValue() == Mode.Box || this.mode.getValue() == Mode.BoxOutline), ((Integer)this.boxAlpha.getValue()).intValue(), false, storage.getChest());
/* 103 */         GlStateManager.func_179117_G();
/* 104 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */     } 
/* 107 */     if (depth)
/* 108 */       GlStateManager.func_179126_j(); 
/* 109 */     this.storages.clear();
/*     */   }
/*     */   
/*     */   public int getTileEntityColor(TileEntity tileEntity) {
/* 113 */     if (tileEntity instanceof TileEntityChest) {
/* 114 */       return ((ColorSetting)this.chestColor.getValue()).getColor();
/*     */     }
/* 116 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest) {
/* 117 */       return ((ColorSetting)this.echestColor.getValue()).getColor();
/*     */     }
/* 119 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityShulkerBox) {
/* 120 */       return ((ColorSetting)this.shulkColor.getValue()).getColor();
/*     */     }
/* 122 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityFurnace) {
/* 123 */       return ((ColorSetting)this.furnaceColor.getValue()).getColor();
/*     */     }
/* 125 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityHopper) {
/* 126 */       return ((ColorSetting)this.hopperColor.getValue()).getColor();
/*     */     }
/* 128 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityDispenser) {
/* 129 */       return ((ColorSetting)this.dispenserColor.getValue()).getColor();
/*     */     }
/* 131 */     return -1;
/*     */   }
/*     */   
/*     */   private int getEntityColor(Entity entity) {
/* 135 */     if (entity instanceof net.minecraft.entity.item.EntityMinecartChest) {
/* 136 */       return ((ColorSetting)this.minecartColor.getValue()).getColor();
/*     */     }
/* 138 */     if (entity instanceof EntityItemFrame && ((EntityItemFrame)entity).func_82335_i().func_77973_b() instanceof net.minecraft.item.ItemShulkerBox) {
/* 139 */       return ((ColorSetting)this.shulkerframeColor.getValue()).getColor();
/*     */     }
/* 141 */     if (entity instanceof EntityItemFrame && !(((EntityItemFrame)entity).func_82335_i().func_77973_b() instanceof net.minecraft.item.ItemShulkerBox)) {
/* 142 */       return ((ColorSetting)this.frameColor.getValue()).getColor();
/*     */     }
/* 144 */     return -1;
/*     */   }
/*     */   
/*     */   public void checkSetupFBO() {
/* 148 */     Framebuffer fbo = mc.func_147110_a();
/* 149 */     if (fbo != null && fbo.field_147624_h > -1) {
/* 150 */       setupFBO(fbo);
/* 151 */       fbo.field_147624_h = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setupFBO(Framebuffer fbo) {
/* 156 */     EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.field_147624_h);
/* 157 */     int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
/* 158 */     EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
/* 159 */     EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.field_71443_c, mc.field_71440_d);
/* 160 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
/* 161 */     EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
/*     */   }
/*     */   
/*     */   public void renderNormal(float n) {
/* 165 */     RenderHelper.func_74519_b();
/* 166 */     for (TileEntity tileEntity : mc.field_71441_e.field_147482_g) {
/* 167 */       if (!(tileEntity instanceof TileEntityChest) && !(tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest) && !(tileEntity instanceof net.minecraft.tileentity.TileEntityShulkerBox)) {
/*     */         continue;
/*     */       }
/* 170 */       GL11.glPushMatrix();
/* 171 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 172 */       TileEntityRendererDispatcher.field_147556_a.func_147549_a(tileEntity, tileEntity.func_174877_v().func_177958_n() - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX(), tileEntity.func_174877_v().func_177956_o() - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY(), tileEntity.func_174877_v().func_177952_p() - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ(), n);
/* 173 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderColor(float n) {
/* 178 */     RenderHelper.func_74519_b();
/* 179 */     for (TileEntity tileEntity : mc.field_71441_e.field_147482_g) {
/* 180 */       if (!(tileEntity instanceof TileEntityChest) && !(tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest) && !(tileEntity instanceof net.minecraft.tileentity.TileEntityShulkerBox) && !(tileEntity instanceof net.minecraft.tileentity.TileEntityFurnace) && !(tileEntity instanceof net.minecraft.tileentity.TileEntityHopper)) {
/*     */         continue;
/*     */       }
/*     */       
/* 184 */       setColor(new Color(getTileEntityColor(tileEntity)));
/*     */       
/* 186 */       TileEntityRendererDispatcher.field_147556_a.func_147549_a(tileEntity, tileEntity.func_174877_v().func_177958_n() - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX(), tileEntity.func_174877_v().func_177956_o() - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY(), tileEntity.func_174877_v().func_177952_p() - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ(), n);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setColor(Color c) {
/* 191 */     GL11.glColor3f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F);
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 195 */     Outline, Box, BoxOutline, ShaderOutline, ShaderBox;
/*     */   }
/*     */ 
/*     */   
/*     */   private class Storage
/*     */   {
/*     */     BlockPos position;
/*     */     int color;
/*     */     int chest;
/*     */     
/*     */     private Storage(BlockPos pos, int color, int chest) {
/* 206 */       this.position = pos;
/* 207 */       this.color = color;
/* 208 */       this.chest = chest;
/*     */     }
/*     */     
/*     */     public int getChest() {
/* 212 */       return this.chest;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos getPosition() {
/* 217 */       return this.position;
/*     */     }
/*     */     
/*     */     public int getColor() {
/* 221 */       return this.color;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\StorageEsp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */