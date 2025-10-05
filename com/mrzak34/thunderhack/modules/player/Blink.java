/*     */ package com.mrzak34.thunderhack.modules.player;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Blink extends Module {
/*  24 */   public Setting<Float> circleWidth = register(new Setting("Width", Float.valueOf(2.5F), Float.valueOf(5.0F), Float.valueOf(0.1F)));
/*  25 */   public Setting<ColorSetting> circleColor = register(new Setting("Color", new ColorSetting(869950564, true)));
/*  26 */   private final Setting<Boolean> pulse = register(new Setting("Pulse", Boolean.valueOf(false)));
/*  27 */   private final Setting<Boolean> strict = register(new Setting("Strict", Boolean.valueOf(false)));
/*  28 */   private final Setting<Float> factor = register(new Setting("Factor", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(10.0F)));
/*  29 */   private final Setting<Boolean> render = register(new Setting("Render", Boolean.valueOf(true)));
/*  30 */   private final Setting<Boolean> fill = register(new Setting("Fill", Boolean.valueOf(true)));
/*  31 */   private final Queue<Packet> storedPackets = new LinkedList<>();
/*  32 */   private Vec3d lastPos = new Vec3d((Vec3i)BlockPos.field_177992_a);
/*  33 */   private final AtomicBoolean sending = new AtomicBoolean(false);
/*     */ 
/*     */   
/*     */   public Blink() {
/*  37 */     super("Blink", "Отменяет пакеты-движения", Module.Category.MISC);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  42 */     if (mc.field_71439_g == null || mc.field_71441_e == null)
/*  43 */       return;  if (((Boolean)this.render.getValue()).booleanValue() && this.lastPos != null) {
/*  44 */       GlStateManager.func_179094_E();
/*  45 */       GlStateManager.func_179147_l();
/*  46 */       GlStateManager.func_179090_x();
/*  47 */       GlStateManager.func_179140_f();
/*  48 */       GlStateManager.func_179097_i();
/*  49 */       GL11.glEnable(3008);
/*  50 */       GL11.glBlendFunc(770, 771);
/*  51 */       GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  52 */       IRenderManager renderManager = (IRenderManager)mc.func_175598_ae();
/*  53 */       float[] hsb = Color.RGBtoHSB(((ColorSetting)this.circleColor.getValue()).getRed(), ((ColorSetting)this.circleColor.getValue()).getGreen(), ((ColorSetting)this.circleColor.getValue()).getBlue(), null);
/*  54 */       float initialHue = (float)(System.currentTimeMillis() % 7200L) / 7200.0F;
/*  55 */       float hue = initialHue;
/*  56 */       int rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
/*  57 */       ArrayList<Vec3d> vecs = new ArrayList<>();
/*  58 */       double x = this.lastPos.field_72450_a - renderManager.getRenderPosX();
/*  59 */       double y = this.lastPos.field_72448_b - renderManager.getRenderPosY();
/*  60 */       double z = this.lastPos.field_72449_c - renderManager.getRenderPosZ();
/*  61 */       GL11.glShadeModel(7425);
/*  62 */       GlStateManager.func_179129_p();
/*  63 */       GL11.glLineWidth(((Float)this.circleWidth.getValue()).floatValue());
/*  64 */       GL11.glBegin(1);
/*  65 */       for (int i = 0; i <= 360; i++) {
/*  66 */         Vec3d vec = new Vec3d(x + Math.sin(i * Math.PI / 180.0D) * 0.5D, y + 0.01D, z + Math.cos(i * Math.PI / 180.0D) * 0.5D);
/*  67 */         vecs.add(vec);
/*     */       }  int j;
/*  69 */       for (j = 0; j < vecs.size() - 1; j++) {
/*  70 */         int red = rgb >> 16 & 0xFF;
/*  71 */         int green = rgb >> 8 & 0xFF;
/*  72 */         int blue = rgb & 0xFF;
/*  73 */         if (((ColorSetting)this.circleColor.getValue()).isCycle()) {
/*  74 */           GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, ((Boolean)this.fill.getValue()).booleanValue() ? 1.0F : (((ColorSetting)this.circleColor.getValue()).getAlpha() / 255.0F));
/*     */         } else {
/*  76 */           GL11.glColor4f(((ColorSetting)this.circleColor.getValue()).getRed() / 255.0F, ((ColorSetting)this.circleColor.getValue()).getGreen() / 255.0F, ((ColorSetting)this.circleColor.getValue()).getBlue() / 255.0F, ((Boolean)this.fill.getValue()).booleanValue() ? 1.0F : (((ColorSetting)this.circleColor.getValue()).getAlpha() / 255.0F));
/*     */         } 
/*  78 */         GL11.glVertex3d(((Vec3d)vecs.get(j)).field_72450_a, ((Vec3d)vecs.get(j)).field_72448_b, ((Vec3d)vecs.get(j)).field_72449_c);
/*  79 */         GL11.glVertex3d(((Vec3d)vecs.get(j + 1)).field_72450_a, ((Vec3d)vecs.get(j + 1)).field_72448_b, ((Vec3d)vecs.get(j + 1)).field_72449_c);
/*  80 */         hue += 0.0027777778F;
/*  81 */         rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
/*     */       } 
/*  83 */       GL11.glEnd();
/*  84 */       if (((Boolean)this.fill.getValue()).booleanValue()) {
/*  85 */         hue = initialHue;
/*  86 */         GL11.glBegin(9);
/*  87 */         for (j = 0; j < vecs.size() - 1; j++) {
/*  88 */           int red = rgb >> 16 & 0xFF;
/*  89 */           int green = rgb >> 8 & 0xFF;
/*  90 */           int blue = rgb & 0xFF;
/*  91 */           if (((ColorSetting)this.circleColor.getValue()).isCycle()) {
/*  92 */             GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, ((ColorSetting)this.circleColor.getValue()).getAlpha() / 255.0F);
/*     */           } else {
/*  94 */             GL11.glColor4f(((ColorSetting)this.circleColor.getValue()).getRed() / 255.0F, ((ColorSetting)this.circleColor.getValue()).getGreen() / 255.0F, ((ColorSetting)this.circleColor.getValue()).getBlue() / 255.0F, ((ColorSetting)this.circleColor.getValue()).getAlpha() / 255.0F);
/*     */           } 
/*  96 */           GL11.glVertex3d(((Vec3d)vecs.get(j)).field_72450_a, ((Vec3d)vecs.get(j)).field_72448_b, ((Vec3d)vecs.get(j)).field_72449_c);
/*  97 */           GL11.glVertex3d(((Vec3d)vecs.get(j + 1)).field_72450_a, ((Vec3d)vecs.get(j + 1)).field_72448_b, ((Vec3d)vecs.get(j + 1)).field_72449_c);
/*  98 */           hue += 0.0027777778F;
/*  99 */           rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
/*     */         } 
/* 101 */         GL11.glEnd();
/*     */       } 
/* 103 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 104 */       GL11.glDisable(3008);
/* 105 */       GlStateManager.func_179089_o();
/* 106 */       GL11.glShadeModel(7424);
/* 107 */       GlStateManager.func_179126_j();
/* 108 */       GlStateManager.func_179098_w();
/* 109 */       GlStateManager.func_179145_e();
/* 110 */       GlStateManager.func_179084_k();
/* 111 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacket(PacketEvent.Send event) {
/* 117 */     if (fullNullCheck())
/* 118 */       return;  Packet packet = event.getPacket();
/* 119 */     if (this.sending.get())
/* 120 */       return;  if (((Boolean)this.pulse.getValue()).booleanValue()) {
/* 121 */       if (event.getPacket() instanceof CPacketPlayer) {
/* 122 */         if (((Boolean)this.strict.getValue()).booleanValue() && !((CPacketPlayer)event.getPacket()).func_149465_i()) {
/* 123 */           this.sending.set(true);
/* 124 */           while (!this.storedPackets.isEmpty()) {
/* 125 */             Packet pckt = this.storedPackets.poll();
/* 126 */             mc.field_71439_g.field_71174_a.func_147297_a(pckt);
/* 127 */             if (pckt instanceof CPacketPlayer) {
/* 128 */               this.lastPos = new Vec3d(((CPacketPlayer)pckt).func_186997_a(mc.field_71439_g.field_70165_t), ((CPacketPlayer)pckt).func_186996_b(mc.field_71439_g.field_70163_u), ((CPacketPlayer)pckt).func_187000_c(mc.field_71439_g.field_70161_v));
/*     */             }
/*     */           } 
/* 131 */           this.sending.set(false);
/* 132 */           this.storedPackets.clear();
/*     */         } else {
/* 134 */           event.setCanceled(true);
/* 135 */           this.storedPackets.add(event.getPacket());
/*     */         } 
/*     */       }
/* 138 */     } else if (!(packet instanceof net.minecraft.network.play.client.CPacketChatMessage) && !(packet instanceof net.minecraft.network.play.client.CPacketConfirmTeleport) && !(packet instanceof net.minecraft.network.play.client.CPacketKeepAlive) && !(packet instanceof net.minecraft.network.play.client.CPacketTabComplete) && !(packet instanceof net.minecraft.network.play.client.CPacketClientStatus)) {
/* 139 */       event.setCanceled(true);
/* 140 */       this.storedPackets.add(event.getPacket());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 146 */     if (mc.field_71439_g == null || mc.field_71441_e == null || mc.func_71387_A()) {
/* 147 */       toggle();
/*     */       return;
/*     */     } 
/* 150 */     if (((Boolean)this.pulse.getValue()).booleanValue() && mc.field_71439_g != null && mc.field_71441_e != null && 
/* 151 */       this.storedPackets.size() >= ((Float)this.factor.getValue()).floatValue() * 10.0F) {
/* 152 */       this.sending.set(true);
/* 153 */       while (!this.storedPackets.isEmpty()) {
/* 154 */         Packet pckt = this.storedPackets.poll();
/* 155 */         mc.field_71439_g.field_71174_a.func_147297_a(pckt);
/* 156 */         if (pckt instanceof CPacketPlayer) {
/* 157 */           this.lastPos = new Vec3d(((CPacketPlayer)pckt).func_186997_a(mc.field_71439_g.field_70165_t), ((CPacketPlayer)pckt).func_186996_b(mc.field_71439_g.field_70163_u), ((CPacketPlayer)pckt).func_187000_c(mc.field_71439_g.field_70161_v));
/*     */         }
/*     */       } 
/* 160 */       this.sending.set(false);
/* 161 */       this.storedPackets.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 168 */     if (mc.field_71441_e == null || mc.field_71439_g == null)
/* 169 */       return;  while (!this.storedPackets.isEmpty()) {
/* 170 */       mc.field_71439_g.field_71174_a.func_147297_a(this.storedPackets.poll());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 176 */     if (mc.field_71439_g == null || mc.field_71441_e == null || mc.func_71387_A()) {
/* 177 */       toggle();
/*     */       return;
/*     */     } 
/* 180 */     this.lastPos = mc.field_71439_g.func_174791_d();
/* 181 */     this.sending.set(false);
/* 182 */     this.storedPackets.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\Blink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */