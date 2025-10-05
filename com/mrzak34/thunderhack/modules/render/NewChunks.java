/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import io.netty.util.internal.ConcurrentSet;
/*     */ import java.awt.Color;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.network.play.server.SPacketChunkData;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ public class NewChunks
/*     */   extends Module
/*     */ {
/*  27 */   public Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting((new Color(0.8392157F, 0.3372549F, 0.5764706F, 0.39215687F)).hashCode(), false)));
/*  28 */   private final ICamera frustum = (ICamera)new Frustum();
/*     */   
/*  30 */   private final Set<ChunkPos> chunks = (Set<ChunkPos>)new ConcurrentSet();
/*     */   
/*     */   public NewChunks() {
/*  33 */     super("NewChunks", "NewChunks", "NewChunks", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   public static void drawBox(AxisAlignedBB box, int mode, int color) {
/*  37 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  38 */     BufferBuilder buffer = tessellator.func_178180_c();
/*  39 */     buffer.func_181668_a(mode, DefaultVertexFormats.field_181706_f);
/*  40 */     float r = (color >> 16 & 0xFF) / 255.0F;
/*  41 */     float g = (color >> 8 & 0xFF) / 255.0F;
/*  42 */     float b = (color & 0xFF) / 255.0F;
/*  43 */     float a = (color >> 24 & 0xFF) / 255.0F;
/*  44 */     buffer.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181666_a(r, g, b, 0.0F).func_181675_d();
/*  45 */     buffer.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181666_a(r, g, b, a).func_181675_d();
/*  46 */     buffer.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181666_a(r, g, b, a).func_181675_d();
/*  47 */     buffer.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72334_f).func_181666_a(r, g, b, a).func_181675_d();
/*  48 */     buffer.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72334_f).func_181666_a(r, g, b, a).func_181675_d();
/*  49 */     buffer.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181666_a(r, g, b, a).func_181675_d();
/*  50 */     buffer.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72339_c).func_181666_a(r, g, b, a).func_181675_d();
/*  51 */     buffer.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72339_c).func_181666_a(r, g, b, a).func_181675_d();
/*  52 */     buffer.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72334_f).func_181666_a(r, g, b, a).func_181675_d();
/*  53 */     buffer.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72334_f).func_181666_a(r, g, b, a).func_181675_d();
/*  54 */     buffer.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72339_c).func_181666_a(r, g, b, a).func_181675_d();
/*  55 */     buffer.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72334_f).func_181666_a(r, g, b, 0.0F).func_181675_d();
/*  56 */     buffer.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72334_f).func_181666_a(r, g, b, a).func_181675_d();
/*  57 */     buffer.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72334_f).func_181666_a(r, g, b, 0.0F).func_181675_d();
/*  58 */     buffer.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72334_f).func_181666_a(r, g, b, a).func_181675_d();
/*  59 */     buffer.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72339_c).func_181666_a(r, g, b, 0.0F).func_181675_d();
/*  60 */     buffer.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181666_a(r, g, b, a).func_181675_d();
/*  61 */     buffer.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181666_a(r, g, b, 0.0F).func_181675_d();
/*  62 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onReceive(PacketEvent.Receive event) {
/*  67 */     if (event.getPacket() instanceof SPacketChunkData) {
/*  68 */       SPacketChunkData packet = (SPacketChunkData)event.getPacket();
/*  69 */       if (packet.func_149274_i())
/*  70 */         return;  ChunkPos newChunk = new ChunkPos(packet.func_149273_e(), packet.func_149271_f());
/*  71 */       this.chunks.add(newChunk);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender(Render3DEvent event) {
/*  77 */     if (mc.func_175606_aa() == null)
/*  78 */       return;  this.frustum.func_78547_a((mc.func_175606_aa()).field_70165_t, (mc.func_175606_aa()).field_70163_u, (mc.func_175606_aa()).field_70161_v);
/*     */     
/*  80 */     GlStateManager.func_179094_E();
/*  81 */     RenderUtil.beginRender();
/*  82 */     GlStateManager.func_179090_x();
/*  83 */     GlStateManager.func_179118_c();
/*  84 */     GlStateManager.func_179097_i();
/*  85 */     GlStateManager.func_179132_a(false);
/*  86 */     GlStateManager.func_187441_d(2.0F);
/*     */     
/*  88 */     for (ChunkPos chunk : this.chunks) {
/*  89 */       AxisAlignedBB chunkBox = new AxisAlignedBB(chunk.func_180334_c(), 0.0D, chunk.func_180333_d(), chunk.func_180332_e(), 0.0D, chunk.func_180330_f());
/*     */ 
/*     */       
/*  92 */       GlStateManager.func_179094_E();
/*  93 */       if (this.frustum.func_78546_a(chunkBox)) {
/*  94 */         double x = mc.field_71439_g.field_70142_S + (mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70142_S) * event.getPartialTicks();
/*  95 */         double y = mc.field_71439_g.field_70137_T + (mc.field_71439_g.field_70163_u - mc.field_71439_g.field_70137_T) * event.getPartialTicks();
/*  96 */         double z = mc.field_71439_g.field_70136_U + (mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70136_U) * event.getPartialTicks();
/*  97 */         drawBox(chunkBox.func_72317_d(-x, -y, -z), 3, ((ColorSetting)this.color.getValue()).getColor());
/*     */       } 
/*     */       
/* 100 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */     
/* 103 */     GlStateManager.func_187441_d(1.0F);
/* 104 */     GlStateManager.func_179098_w();
/* 105 */     GlStateManager.func_179126_j();
/* 106 */     GlStateManager.func_179132_a(true);
/* 107 */     GlStateManager.func_179141_d();
/* 108 */     RenderUtil.endRender();
/* 109 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 114 */     this.chunks.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\NewChunks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */