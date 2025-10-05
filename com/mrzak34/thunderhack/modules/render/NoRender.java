/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventEntitySpawn;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IGuiBossOverlay;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Pair;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.gui.BossInfoClient;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.network.play.server.SPacketEntityEffect;
/*     */ import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.BossInfo;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.client.event.RenderLivingEvent;
/*     */ import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class NoRender
/*     */   extends Module
/*     */ {
/*  34 */   private static NoRender INSTANCE = new NoRender();
/*     */ 
/*     */   
/*  37 */   public Setting<Boolean> noarmorstands = register(new Setting("ArmorStands", Boolean.valueOf(false)));
/*  38 */   public Setting<Boolean> fire = register(new Setting("Fire", Boolean.valueOf(false)));
/*  39 */   public Setting<Boolean> blin = register(new Setting("Blind", Boolean.valueOf(false)));
/*  40 */   public Setting<Boolean> arrows = register(new Setting("Arrows", Boolean.valueOf(false)));
/*  41 */   public Setting<Boolean> SkyLight = register(new Setting("SkyLight", Boolean.valueOf(false)));
/*  42 */   public Setting<Boolean> portal = register(new Setting("portal", Boolean.valueOf(false)));
/*  43 */   public Setting<Boolean> totemPops = register(new Setting("TotemPop", Boolean.valueOf(false)));
/*  44 */   public Setting<Boolean> items = register(new Setting("Items", Boolean.valueOf(false)));
/*  45 */   public Setting<Boolean> maps = register(new Setting("Maps", Boolean.valueOf(false)));
/*  46 */   public Setting<Boolean> nausea = register(new Setting("Nausea", Boolean.valueOf(false)));
/*  47 */   public Setting<Boolean> hurtcam = register(new Setting("HurtCam", Boolean.valueOf(false)));
/*  48 */   public Setting<Boolean> explosions = register(new Setting("Explosions", Boolean.valueOf(false)));
/*  49 */   public Setting<Boolean> lightning = register(new Setting("Lightning", Boolean.valueOf(false)));
/*  50 */   public Setting<Boolean> fog = register(new Setting("NoFog", Boolean.valueOf(false)));
/*  51 */   public Setting<Boolean> noWeather = register(new Setting("Weather", Boolean.valueOf(false)));
/*  52 */   public Setting<Boss> boss = register(new Setting("BossBars", Boss.NONE));
/*  53 */   public Setting<Float> scale = register(new Setting("Scale", Float.valueOf(0.5F), Float.valueOf(0.5F), Float.valueOf(1.0F), v -> (this.boss.getValue() == Boss.MINIMIZE || this.boss.getValue() == Boss.STACK)));
/*  54 */   public Setting<Boolean> bats = register(new Setting("Bats", Boolean.valueOf(false)));
/*  55 */   public Setting<NoArmor> noArmor = register(new Setting("NoArmor", NoArmor.NONE));
/*  56 */   public Setting<Boolean> blocks = register(new Setting("BlockOverlay", Boolean.valueOf(false)));
/*  57 */   public Setting<Boolean> advancements = register(new Setting("Advancements", Boolean.valueOf(false)));
/*  58 */   public Setting<Boolean> timeChange = register(new Setting("TimeChange", Boolean.valueOf(false)));
/*  59 */   public Setting<Integer> time = register(new Setting("Time", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(23000), v -> ((Boolean)this.timeChange.getValue()).booleanValue()));
/*  60 */   public Setting<Boolean> fireworks = register(new Setting("FireWorks", Boolean.valueOf(false)));
/*  61 */   public Setting<Boolean> hooks = register(new Setting("Hooks", Boolean.valueOf(false)));
/*     */   
/*     */   public NoRender() {
/*  64 */     super("NoRender", "не рендерить лаганые-херни", Module.Category.RENDER);
/*  65 */     setInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NoRender getInstance() {
/*  72 */     if (INSTANCE == null) {
/*  73 */       INSTANCE = new NoRender();
/*     */     }
/*  75 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  79 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  84 */     if (((Boolean)this.portal.getValue()).booleanValue()) {
/*  85 */       ((IEntity)mc.field_71439_g).setInPortal(false);
/*     */     }
/*  87 */     if (((Boolean)this.items.getValue()).booleanValue()) {
/*  88 */       mc.field_71441_e.field_72996_f.stream().filter(EntityItem.class::isInstance).map(EntityItem.class::cast).forEach(Entity::func_70106_y);
/*     */     }
/*  90 */     if (((Boolean)this.arrows.getValue()).booleanValue()) {
/*  91 */       mc.field_71441_e.field_72996_f.stream().filter(EntityArrow.class::isInstance).map(EntityArrow.class::cast).forEach(Entity::func_70106_y);
/*     */     }
/*  93 */     if (((Boolean)this.hooks.getValue()).booleanValue()) {
/*  94 */       mc.field_71441_e.field_72996_f.stream().filter(EntityFishHook.class::isInstance).map(EntityFishHook.class::cast).forEach(Entity::func_70106_y);
/*     */     }
/*  96 */     if (((Boolean)this.noWeather.getValue()).booleanValue() && mc.field_71441_e.func_72896_J()) {
/*  97 */       mc.field_71441_e.func_72894_k(0.0F);
/*     */     }
/*  99 */     if (((Boolean)this.timeChange.getValue()).booleanValue()) {
/* 100 */       mc.field_71441_e.func_72877_b(((Integer)this.time.getValue()).intValue());
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 106 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketMaps && ((Boolean)this.maps.getValue()).booleanValue()) {
/* 107 */       event.setCanceled(true);
/*     */     }
/* 109 */     if ((event.getPacket() instanceof net.minecraft.network.play.server.SPacketTimeUpdate & ((Boolean)this.timeChange.getValue()).booleanValue()) != 0) {
/* 110 */       event.setCanceled(true);
/*     */     }
/* 112 */     if ((event.getPacket() instanceof net.minecraft.network.play.server.SPacketExplosion & ((Boolean)this.explosions.getValue()).booleanValue()) != 0) {
/* 113 */       event.setCanceled(true);
/*     */     }
/* 115 */     if (event.getPacket() instanceof SPacketEntityEffect && ((Boolean)this.blin.getValue()).booleanValue()) {
/* 116 */       SPacketEntityEffect var3 = (SPacketEntityEffect)event.getPacket();
/* 117 */       if (var3.func_149427_e() == 15) {
/* 118 */         event.setCanceled(true);
/*     */       }
/*     */     } 
/* 121 */     if (event.getPacket() instanceof SPacketSpawnGlobalEntity && ((Boolean)this.lightning.getValue()).booleanValue() && (
/* 122 */       (SPacketSpawnGlobalEntity)event.getPacket()).func_149053_g() == 1) {
/* 123 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderPre(RenderGameOverlayEvent.Pre event) {
/* 130 */     if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO && this.boss.getValue() != Boss.NONE) {
/* 131 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */   
/* 135 */   private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderPost(RenderGameOverlayEvent.Post event) {
/* 139 */     if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO && this.boss.getValue() != Boss.NONE) {
/* 140 */       if (this.boss.getValue() == Boss.MINIMIZE) {
/* 141 */         Map<UUID, BossInfoClient> map = ((IGuiBossOverlay)mc.field_71456_v.func_184046_j()).getMapBossInfos();
/* 142 */         if (map == null) {
/*     */           return;
/*     */         }
/* 145 */         ScaledResolution scaledresolution = new ScaledResolution(mc);
/* 146 */         int i = scaledresolution.func_78326_a();
/* 147 */         int j = 12;
/* 148 */         for (Map.Entry<UUID, BossInfoClient> entry : map.entrySet()) {
/* 149 */           BossInfoClient info = entry.getValue();
/* 150 */           String text = info.func_186744_e().func_150254_d();
/* 151 */           int k = (int)(i / ((Float)this.scale.getValue()).floatValue() / 2.0F - 91.0F);
/* 152 */           GL11.glScaled(((Float)this.scale.getValue()).floatValue(), ((Float)this.scale.getValue()).floatValue(), 1.0D);
/* 153 */           if (!event.isCanceled()) {
/* 154 */             GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 155 */             mc.func_110434_K().func_110577_a(GUI_BARS_TEXTURES);
/* 156 */             ((IGuiBossOverlay)mc.field_71456_v.func_184046_j()).invokeRender(k, j, (BossInfo)info);
/* 157 */             mc.field_71466_p.func_175063_a(text, i / ((Float)this.scale.getValue()).floatValue() / 2.0F - mc.field_71466_p.func_78256_a(text) / 2.0F, (j - 9), 16777215);
/*     */           } 
/* 159 */           GL11.glScaled(1.0D / ((Float)this.scale.getValue()).floatValue(), 1.0D / ((Float)this.scale.getValue()).floatValue(), 1.0D);
/* 160 */           j += 10 + mc.field_71466_p.field_78288_b;
/*     */         } 
/* 162 */       } else if (this.boss.getValue() == Boss.STACK) {
/* 163 */         Map<UUID, BossInfoClient> map = ((IGuiBossOverlay)mc.field_71456_v.func_184046_j()).getMapBossInfos();
/* 164 */         HashMap<String, Pair<BossInfoClient, Integer>> to = new HashMap<>();
/* 165 */         for (Map.Entry<UUID, BossInfoClient> entry2 : map.entrySet()) {
/* 166 */           String s = ((BossInfoClient)entry2.getValue()).func_186744_e().func_150254_d();
/* 167 */           if (to.containsKey(s)) {
/* 168 */             Pair<BossInfoClient, Integer> pair = to.get(s);
/* 169 */             pair = new Pair(pair.getKey(), Integer.valueOf(((Integer)pair.getValue()).intValue() + 1));
/* 170 */             to.put(s, pair); continue;
/*     */           } 
/* 172 */           Pair<BossInfoClient, Integer> p = new Pair(entry2.getValue(), Integer.valueOf(1));
/* 173 */           to.put(s, p);
/*     */         } 
/*     */         
/* 176 */         ScaledResolution scaledresolution2 = new ScaledResolution(mc);
/* 177 */         int l = scaledresolution2.func_78326_a();
/* 178 */         int m = 12;
/* 179 */         for (Map.Entry<String, Pair<BossInfoClient, Integer>> entry3 : to.entrySet()) {
/* 180 */           String text = entry3.getKey();
/* 181 */           BossInfoClient info2 = (BossInfoClient)((Pair)entry3.getValue()).getKey();
/* 182 */           int a = ((Integer)((Pair)entry3.getValue()).getValue()).intValue();
/* 183 */           text = text + " x" + a;
/* 184 */           int k2 = (int)(l / ((Float)this.scale.getValue()).floatValue() / 2.0F - 91.0F);
/* 185 */           GL11.glScaled(((Float)this.scale.getValue()).floatValue(), ((Float)this.scale.getValue()).floatValue(), 1.0D);
/* 186 */           if (!event.isCanceled()) {
/* 187 */             GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 188 */             mc.func_110434_K().func_110577_a(GUI_BARS_TEXTURES);
/* 189 */             ((IGuiBossOverlay)mc.field_71456_v.func_184046_j()).invokeRender(k2, m, (BossInfo)info2);
/* 190 */             mc.field_71466_p.func_175063_a(text, l / ((Float)this.scale.getValue()).floatValue() / 2.0F - mc.field_71466_p.func_78256_a(text) / 2.0F, (m - 9), 16777215);
/*     */           } 
/* 192 */           GL11.glScaled(1.0D / ((Float)this.scale.getValue()).floatValue(), 1.0D / ((Float)this.scale.getValue()).floatValue(), 1.0D);
/* 193 */           m += 10 + mc.field_71466_p.field_78288_b;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderLiving(RenderLivingEvent.Pre<?> event) {
/* 201 */     if (((Boolean)this.bats.getValue()).booleanValue() && event.getEntity() instanceof net.minecraft.entity.passive.EntityBat) {
/* 202 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlaySound(PlaySoundAtEntityEvent event) {
/* 208 */     if ((((Boolean)this.bats.getValue()).booleanValue() && event.getSound().equals(SoundEvents.field_187740_w)) || event.getSound().equals(SoundEvents.field_187742_x) || event.getSound().equals(SoundEvents.field_187743_y) || event.getSound().equals(SoundEvents.field_189108_z) || event.getSound().equals(SoundEvents.field_187744_z)) {
/* 209 */       event.setVolume(0.0F);
/* 210 */       event.setPitch(0.0F);
/* 211 */       event.setCanceled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onFireWorkSpawned(EventEntitySpawn event) {
/* 217 */     if (event.getEntity() instanceof net.minecraft.entity.item.EntityFireworkRocket && ((Boolean)this.fireworks.getValue()).booleanValue())
/* 218 */       event.setCanceled(true); 
/*     */   }
/*     */   
/*     */   public enum Fog
/*     */   {
/* 223 */     NONE,
/* 224 */     AIR,
/* 225 */     NOFOG;
/*     */   }
/*     */   
/*     */   public enum Boss {
/* 229 */     NONE,
/* 230 */     REMOVE,
/* 231 */     STACK,
/* 232 */     MINIMIZE;
/*     */   }
/*     */   
/*     */   public enum NoArmor {
/* 236 */     NONE,
/* 237 */     ALL,
/* 238 */     HELMET;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\NoRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */