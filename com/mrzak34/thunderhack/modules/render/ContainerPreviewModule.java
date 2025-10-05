/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.play.server.SPacketOpenWindow;
/*     */ import net.minecraft.network.play.server.SPacketWindowItems;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ public class ContainerPreviewModule
/*     */   extends Module
/*     */ {
/*  27 */   public Setting<Integer> av = register(new Setting("x", Integer.valueOf(256), Integer.valueOf(0), Integer.valueOf(1500)));
/*  28 */   public Setting<Integer> bv = register(new Setting("y", Integer.valueOf(256), Integer.valueOf(0), Integer.valueOf(1500)));
/*  29 */   public Setting<Integer> colorr = register(new Setting("Red", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(255)));
/*  30 */   public Setting<Integer> colorg = register(new Setting("Green", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(255)));
/*  31 */   public Setting<Integer> colorb = register(new Setting("Blue", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(255)));
/*  32 */   public Setting<Integer> colora = register(new Setting("Alpha", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(255)));
/*     */   public ScaledResolution scaledResolution;
/*  34 */   private final HashMap<BlockPos, ArrayList<ItemStack>> PosItems = new HashMap<>();
/*  35 */   private int TotalSlots = 0;
/*     */   public ContainerPreviewModule() {
/*  37 */     super("ContainerPrev", "Показывает содержимое-контейнера", Module.Category.RENDER);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/*  43 */     if (event.getPacket() instanceof SPacketWindowItems) {
/*     */       
/*  45 */       RayTraceResult ray = mc.field_71476_x;
/*     */       
/*  47 */       if (ray == null) {
/*     */         return;
/*     */       }
/*  50 */       if (ray.field_72313_a != RayTraceResult.Type.BLOCK) {
/*     */         return;
/*     */       }
/*  53 */       IBlockState l_State = mc.field_71441_e.func_180495_p(ray.func_178782_a());
/*     */       
/*  55 */       if (l_State == null) {
/*     */         return;
/*     */       }
/*  58 */       if (l_State.func_177230_c() != Blocks.field_150486_ae && !(l_State.func_177230_c() instanceof net.minecraft.block.BlockShulkerBox)) {
/*     */         return;
/*     */       }
/*  61 */       SPacketWindowItems l_Packet = (SPacketWindowItems)event.getPacket();
/*     */       
/*  63 */       BlockPos blockpos = ray.func_178782_a();
/*     */       
/*  65 */       this.PosItems.remove(blockpos);
/*     */       
/*  67 */       ArrayList<ItemStack> l_List = new ArrayList<>();
/*     */       
/*  69 */       for (int i = 0; i < l_Packet.func_148910_d().size(); i++) {
/*  70 */         ItemStack itemStack = l_Packet.func_148910_d().get(i);
/*  71 */         if (itemStack != null) {
/*     */ 
/*     */           
/*  74 */           if (i >= this.TotalSlots) {
/*     */             break;
/*     */           }
/*  77 */           l_List.add(itemStack);
/*     */         } 
/*     */       } 
/*  80 */       this.PosItems.put(blockpos, l_List);
/*  81 */     } else if (event.getPacket() instanceof SPacketOpenWindow) {
/*  82 */       SPacketOpenWindow l_Packet = (SPacketOpenWindow)event.getPacket();
/*  83 */       this.TotalSlots = l_Packet.func_148898_f();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent p_Event) {
/*  90 */     RayTraceResult ray = mc.field_71476_x;
/*  91 */     if (ray == null) {
/*     */       return;
/*     */     }
/*     */     
/*  95 */     if (ray.field_72313_a != RayTraceResult.Type.BLOCK) {
/*     */       return;
/*     */     }
/*     */     
/*  99 */     if (!this.PosItems.containsKey(ray.func_178782_a())) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 105 */     BlockPos l_Pos = ray.func_178782_a();
/*     */     
/* 107 */     ArrayList<ItemStack> l_Items = this.PosItems.get(l_Pos);
/*     */     
/* 109 */     if (l_Items == null) {
/*     */       return;
/*     */     }
/*     */     
/* 113 */     IBlockState pan4ur = mc.field_71441_e.func_180495_p(ray.func_178782_a());
/*     */     
/* 115 */     int l_I = 0;
/* 116 */     int l_Y = -20;
/* 117 */     int x = 0;
/*     */     
/* 119 */     for (ItemStack stack : l_Items) {
/* 120 */       if (stack != null) {
/*     */         
/* 122 */         GlStateManager.func_179094_E();
/* 123 */         GlStateManager.func_179147_l();
/* 124 */         GlStateManager.func_179120_a(770, 771, 1, 0);
/* 125 */         RenderHelper.func_74520_c();
/* 126 */         RenderUtil.drawSmoothRect((((Integer)this.av.getValue()).intValue() - 3), (((Integer)this.bv.getValue()).intValue() - 50), (((Integer)this.av.getValue()).intValue() + 150), (pan4ur.func_177230_c() != Blocks.field_150486_ae) ? ((Integer)this.bv.getValue()).intValue() : (((Integer)this.bv.getValue()).intValue() + 48), (new Color(((Integer)this.colorr.getValue()).intValue(), ((Integer)this.colorg.getValue()).intValue(), ((Integer)this.colorb.getValue()).intValue(), ((Integer)this.colora.getValue()).intValue())).getRGB());
/* 127 */         GlStateManager.func_179109_b((x + ((Integer)this.av.getValue()).intValue()), (l_Y + mc.field_71466_p.field_78288_b - 19 + ((Integer)this.bv.getValue()).intValue()), 0.0F);
/* 128 */         mc.func_175599_af().func_180450_b(stack, 0, 0);
/* 129 */         mc.func_175599_af().func_175030_a(mc.field_71466_p, stack, 0, 0);
/* 130 */         RenderHelper.func_74518_a();
/* 131 */         GlStateManager.func_179084_k();
/* 132 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 133 */         GlStateManager.func_179121_F();
/* 134 */         x += 16;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 140 */       if (++l_I % 9 == 0) {
/* 141 */         x = 0;
/* 142 */         l_Y += 15;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\ContainerPreviewModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */