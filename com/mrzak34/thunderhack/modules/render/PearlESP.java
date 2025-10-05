/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.gui.clickui.ColorUtil;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.RadarRewrite;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.TimeAnimation;
/*     */ import com.mrzak34.thunderhack.util.Trace;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.AnimationMode;
/*     */ import com.mrzak34.thunderhack.util.render.DrawHelper;
/*     */ import com.mrzak34.thunderhack.util.render.Drawable;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.play.server.SPacketDestroyEntities;
/*     */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PearlESP
/*     */   extends Module
/*     */ {
/*  42 */   public static final Vec3d ORIGIN = new Vec3d(8.0D, 64.0D, 8.0D);
/*  43 */   private final Setting<ColorSetting> color = register(new Setting("Color1", new ColorSetting(-2013200640)));
/*  44 */   private final Setting<ColorSetting> color2 = register(new Setting("Color2", new ColorSetting(-2013200640)));
/*  45 */   private final Setting<ColorSetting> TriangleColor = register(new Setting("TriangleColor", new ColorSetting(-2013200640)));
/*  46 */   public Setting<Float> width2 = register(new Setting("Width", Float.valueOf(1.6F), Float.valueOf(0.1F), Float.valueOf(10.0F)));
/*  47 */   public Setting<Boolean> arrows = register(new Setting("Arrows", Boolean.valueOf(false)));
/*  48 */   public Setting<Boolean> pearls = register(new Setting("Pearls", Boolean.valueOf(false)));
/*  49 */   public Setting<Boolean> snowballs = register(new Setting("Snowballs", Boolean.valueOf(false)));
/*  50 */   public Setting<Integer> time = register(new Setting("Time", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(10)));
/*  51 */   public Map<Entity, List<PredictedPosition>> entAndTrail = new HashMap<>();
/*  52 */   protected Map<Integer, TimeAnimation> ids = new ConcurrentHashMap<>();
/*  53 */   protected Map<Integer, List<Trace>> traceLists = new ConcurrentHashMap<>();
/*  54 */   protected Map<Integer, Trace> traces = new ConcurrentHashMap<>();
/*  55 */   private final Setting<Boolean> triangleESP = register(new Setting("TriangleESP", Boolean.valueOf(true)));
/*  56 */   private final Setting<Boolean> glow = register(new Setting("Glow", Boolean.valueOf(true)));
/*  57 */   private final Setting<Float> width = register(new Setting("TracerHeight", Float.valueOf(2.5F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*  58 */   private final Setting<Float> radius = register(new Setting("Radius", Float.valueOf(50.0F), Float.valueOf(-50.0F), Float.valueOf(50.0F)));
/*  59 */   private final Setting<Float> rad22ius = register(new Setting("TracerDown", Float.valueOf(3.0F), Float.valueOf(0.1F), Float.valueOf(20.0F)));
/*  60 */   private final Setting<Float> tracerA = register(new Setting("TracerWidth", Float.valueOf(0.5F), Float.valueOf(0.0F), Float.valueOf(8.0F)));
/*  61 */   private final Setting<Integer> glowe = register(new Setting("GlowRadius", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(20)));
/*  62 */   private final Setting<Integer> glowa = register(new Setting("GlowAlpha", Integer.valueOf(150), Integer.valueOf(0), Integer.valueOf(255)));
/*  63 */   private final Setting<Mode> mode = register(new Setting("LineMode", Mode.Mode1));
/*     */   
/*     */   public PearlESP() {
/*  66 */     super("Predictions", "Predictions", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  71 */     if (this.mode.getValue() == Mode.Mode2) {
/*  72 */       PlayerToPearl(event);
/*  73 */     } else if (this.mode.getValue() == Mode.Mode1) {
/*  74 */       PearlToDest(event);
/*  75 */     } else if (this.mode.getValue() == Mode.Both) {
/*  76 */       PlayerToPearl(event);
/*  77 */       PearlToDest(event);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent event) {
/*  84 */     if (!((Boolean)this.triangleESP.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*  87 */     ScaledResolution sr = new ScaledResolution(mc);
/*  88 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/*  89 */       if (entity == null || 
/*  90 */         !(entity instanceof net.minecraft.entity.item.EntityEnderPearl)) {
/*     */         continue;
/*     */       }
/*  93 */       float xOffset = sr.func_78326_a() / 2.0F;
/*  94 */       float yOffset = sr.func_78328_b() / 2.0F;
/*     */       
/*  96 */       GlStateManager.func_179094_E();
/*  97 */       float yaw = RadarRewrite.getRotations(entity) - mc.field_71439_g.field_70177_z;
/*  98 */       GL11.glTranslatef(xOffset, yOffset, 0.0F);
/*  99 */       GL11.glRotatef(yaw, 0.0F, 0.0F, 1.0F);
/* 100 */       GL11.glTranslatef(-xOffset, -yOffset, 0.0F);
/* 101 */       drawTriangle(xOffset, yOffset - ((Float)this.radius.getValue()).floatValue(), ((Float)this.width.getValue()).floatValue() * 5.0F, ((ColorSetting)this.TriangleColor.getValue()).getColor());
/* 102 */       GL11.glTranslatef(xOffset, yOffset, 0.0F);
/* 103 */       GL11.glRotatef(-yaw, 0.0F, 0.0F, 1.0F);
/* 104 */       GL11.glTranslatef(-xOffset, -yOffset, 0.0F);
/* 105 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 106 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 114 */     this.ids = new ConcurrentHashMap<>();
/* 115 */     this.traces = new ConcurrentHashMap<>();
/* 116 */     this.traceLists = new ConcurrentHashMap<>();
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 122 */     if (event.getPacket() instanceof SPacketDestroyEntities) {
/* 123 */       for (int id : ((SPacketDestroyEntities)event.getPacket()).func_149098_c()) {
/* 124 */         if (this.ids.containsKey(Integer.valueOf(id))) {
/* 125 */           ((TimeAnimation)this.ids.get(Integer.valueOf(id))).play();
/*     */         }
/*     */       } 
/*     */     }
/* 129 */     if (event.getPacket() instanceof SPacketSpawnObject && (((
/* 130 */       (Boolean)this.pearls.getValue()).booleanValue() && ((SPacketSpawnObject)event.getPacket()).func_148993_l() == 65) || (((Boolean)this.arrows
/* 131 */       .getValue()).booleanValue() && ((SPacketSpawnObject)event.getPacket()).func_148993_l() == 60) || (((Boolean)this.snowballs
/* 132 */       .getValue()).booleanValue() && ((SPacketSpawnObject)event.getPacket()).func_148993_l() == 61))) {
/* 133 */       TimeAnimation animation = new TimeAnimation((((Integer)this.time.getValue()).intValue() * 1000), 0.0D, ((ColorSetting)this.color.getValue()).getAlpha(), false, AnimationMode.LINEAR);
/* 134 */       animation.stop();
/* 135 */       this.ids.put(Integer.valueOf(((SPacketSpawnObject)event.getPacket()).func_149001_c()), animation);
/* 136 */       this.traceLists.put(Integer.valueOf(((SPacketSpawnObject)event.getPacket()).func_149001_c()), new ArrayList<>());
/*     */       try {
/* 138 */         this.traces.put(Integer.valueOf(((SPacketSpawnObject)event.getPacket()).func_149001_c()), new Trace(0, null, mc.field_71441_e.field_73011_w
/*     */               
/* 140 */               .func_186058_p(), new Vec3d(((SPacketSpawnObject)event
/* 141 */                 .getPacket()).func_186880_c(), ((SPacketSpawnObject)event.getPacket()).func_186882_d(), ((SPacketSpawnObject)event.getPacket()).func_186881_e()), new ArrayList()));
/*     */       }
/* 143 */       catch (Exception e) {
/* 144 */         System.out.println(e.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void PlayerToPearl(Render3DEvent event) {
/* 152 */     if (mc.field_71441_e == null || mc.field_71439_g == null)
/* 153 */       return;  for (Map.Entry<Integer, List<Trace>> entry : this.traceLists.entrySet()) {
/* 154 */       GL11.glPushAttrib(1048575);
/* 155 */       GL11.glPushMatrix();
/* 156 */       GL11.glDisable(3008);
/* 157 */       GL11.glEnable(3042);
/* 158 */       GL11.glBlendFunc(770, 771);
/* 159 */       GL11.glDisable(3553);
/* 160 */       GL11.glDisable(2929);
/* 161 */       GL11.glDepthMask(false);
/* 162 */       GL11.glEnable(2884);
/* 163 */       GL11.glEnable(2848);
/* 164 */       GL11.glHint(3154, 4353);
/* 165 */       GL11.glDisable(2896);
/* 166 */       GL11.glLineWidth(((Float)this.width2.getValue()).floatValue());
/* 167 */       TimeAnimation animation = this.ids.get(entry.getKey());
/* 168 */       animation.add(event.getPartialTicks());
/*     */       
/* 170 */       GL11.glColor4f(((ColorSetting)this.color.getValue()).getRed(), ((ColorSetting)this.color.getValue()).getGreen(), ((ColorSetting)this.color.getValue()).getBlue(), MathHelper.func_76131_a((float)(((ColorSetting)this.color.getValue()).getAlpha() - animation.getCurrent() / 255.0D), 0.0F, 255.0F));
/*     */ 
/*     */       
/* 173 */       ((List)entry.getValue()).forEach(trace -> {
/*     */             GL11.glBegin(3);
/*     */             
/*     */             trace.getTrace().forEach(this::renderVec);
/*     */             
/*     */             GL11.glEnd();
/*     */           });
/* 180 */       GL11.glColor4f(((ColorSetting)this.color.getValue()).getRed(), ((ColorSetting)this.color.getValue()).getGreen(), ((ColorSetting)this.color.getValue()).getBlue(), MathHelper.func_76131_a((float)(((ColorSetting)this.color.getValue()).getAlpha() - animation.getCurrent() / 255.0D), 0.0F, 255.0F));
/*     */ 
/*     */       
/* 183 */       GL11.glBegin(3);
/* 184 */       Trace trace = this.traces.get(entry.getKey());
/* 185 */       if (trace != null) {
/* 186 */         trace.getTrace().forEach(this::renderVec);
/*     */       }
/* 188 */       GL11.glEnd();
/* 189 */       GL11.glEnable(2896);
/* 190 */       GL11.glDisable(2848);
/* 191 */       GL11.glEnable(3553);
/* 192 */       GL11.glEnable(2929);
/* 193 */       GL11.glDisable(3042);
/* 194 */       GL11.glEnable(3008);
/* 195 */       GL11.glDepthMask(true);
/* 196 */       GL11.glCullFace(1029);
/* 197 */       GL11.glPopMatrix();
/* 198 */       GL11.glPopAttrib();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderVec(Trace.TracePos tracePos) {
/* 203 */     double x = (tracePos.getPos()).field_72450_a - ((IRenderManager)mc.func_175598_ae()).getRenderPosX();
/* 204 */     double y = (tracePos.getPos()).field_72448_b - ((IRenderManager)mc.func_175598_ae()).getRenderPosY();
/* 205 */     double z = (tracePos.getPos()).field_72449_c - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ();
/* 206 */     GL11.glVertex3d(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 212 */     if (mc.field_71441_e == null)
/* 213 */       return;  if (this.ids.keySet().isEmpty())
/* 214 */       return;  for (Integer id : this.ids.keySet()) {
/* 215 */       if (id == null)
/* 216 */         continue;  if (mc.field_71441_e.field_72996_f == null)
/* 217 */         return;  if (mc.field_71441_e.field_72996_f.isEmpty())
/* 218 */         return;  Trace idTrace = this.traces.get(id);
/* 219 */       Entity entity = mc.field_71441_e.func_73045_a(id.intValue());
/* 220 */       if (entity != null) {
/* 221 */         Vec3d vec = entity.func_174791_d();
/* 222 */         if (vec == null || 
/* 223 */           vec.equals(ORIGIN)) {
/*     */           continue;
/*     */         }
/*     */         
/* 227 */         if (!this.traces.containsKey(id) || idTrace == null) {
/* 228 */           this.traces.put(id, new Trace(0, null, mc.field_71441_e.field_73011_w.func_186058_p(), vec, new ArrayList()));
/* 229 */           idTrace = this.traces.get(id);
/*     */         } 
/*     */         
/* 232 */         List<Trace.TracePos> trace = idTrace.getTrace();
/* 233 */         Vec3d vec3d = trace.isEmpty() ? vec : ((Trace.TracePos)trace.get(trace.size() - 1)).getPos();
/* 234 */         if (!trace.isEmpty() && (vec.func_72438_d(vec3d) > 100.0D || idTrace.getType() != mc.field_71441_e.field_73011_w.func_186058_p())) {
/* 235 */           ((List<Trace>)this.traceLists.get(id)).add(idTrace);
/* 236 */           trace = new ArrayList<>();
/* 237 */           this.traces.put(id, new Trace(((List)this.traceLists.get(id)).size() + 1, null, mc.field_71441_e.field_73011_w.func_186058_p(), vec, new ArrayList()));
/*     */         } 
/*     */         
/* 240 */         if (trace.isEmpty() || !vec.equals(vec3d)) {
/* 241 */           trace.add(new Trace.TracePos(vec));
/*     */         }
/*     */       } 
/*     */       
/* 245 */       TimeAnimation animation = this.ids.get(id);
/*     */       
/* 247 */       if (entity instanceof net.minecraft.entity.projectile.EntityArrow && (entity.field_70122_E || entity.field_70132_H || !entity.field_70160_al)) {
/* 248 */         animation.play();
/*     */       }
/*     */       
/* 251 */       if (animation != null && ((ColorSetting)this.color.getValue()).getAlpha() - animation.getCurrent() <= 0.0D) {
/* 252 */         animation.stop();
/* 253 */         this.ids.remove(id);
/* 254 */         this.traceLists.remove(id);
/* 255 */         this.traces.remove(id);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawTriangle(float x, float y, float size, int color) {
/* 262 */     boolean blend = GL11.glIsEnabled(3042);
/* 263 */     GL11.glEnable(3042);
/* 264 */     boolean depth = GL11.glIsEnabled(2929);
/* 265 */     GL11.glDisable(2929);
/*     */     
/* 267 */     GL11.glDisable(3553);
/* 268 */     GL11.glBlendFunc(770, 771);
/* 269 */     GL11.glEnable(2848);
/* 270 */     GL11.glPushMatrix();
/*     */     
/* 272 */     RadarRewrite.hexColor(color);
/* 273 */     GL11.glBegin(7);
/* 274 */     GL11.glVertex2d(x, y);
/* 275 */     GL11.glVertex2d((x - size * ((Float)this.tracerA.getValue()).floatValue()), (y + size));
/* 276 */     GL11.glVertex2d(x, (y + size - ((Float)this.rad22ius.getValue()).floatValue()));
/* 277 */     GL11.glVertex2d(x, y);
/* 278 */     GL11.glEnd();
/*     */     
/* 280 */     RadarRewrite.hexColor(ColorUtil.darker(new Color(color), 0.8F).getRGB());
/* 281 */     GL11.glBegin(7);
/* 282 */     GL11.glVertex2d(x, y);
/* 283 */     GL11.glVertex2d(x, (y + size - ((Float)this.rad22ius.getValue()).floatValue()));
/* 284 */     GL11.glVertex2d((x + size * ((Float)this.tracerA.getValue()).floatValue()), (y + size));
/* 285 */     GL11.glVertex2d(x, y);
/* 286 */     GL11.glEnd();
/*     */ 
/*     */     
/* 289 */     RadarRewrite.hexColor(ColorUtil.darker(new Color(color), 0.6F).getRGB());
/* 290 */     GL11.glBegin(7);
/* 291 */     GL11.glVertex2d((x - size * ((Float)this.tracerA.getValue()).floatValue()), (y + size));
/* 292 */     GL11.glVertex2d((x + size * ((Float)this.tracerA.getValue()).floatValue()), (y + size));
/* 293 */     GL11.glVertex2d(x, (y + size - ((Float)this.rad22ius.getValue()).floatValue()));
/* 294 */     GL11.glVertex2d((x - size * ((Float)this.tracerA.getValue()).floatValue()), (y + size));
/* 295 */     GL11.glEnd();
/* 296 */     GL11.glPopMatrix();
/*     */     
/* 298 */     GL11.glEnable(3553);
/* 299 */     if (!blend)
/* 300 */       GL11.glDisable(3042); 
/* 301 */     GL11.glDisable(2848);
/* 302 */     if (((Boolean)this.glow.getValue()).booleanValue())
/* 303 */       Drawable.drawBlurredShadow(x - size * ((Float)this.tracerA.getValue()).floatValue(), y, x + size * ((Float)this.tracerA.getValue()).floatValue() - x - size * ((Float)this.tracerA.getValue()).floatValue(), size, ((Integer)this.glowe.getValue()).intValue(), DrawHelper.injectAlpha(new Color(color), ((Integer)this.glowa.getValue()).intValue())); 
/* 304 */     if (depth)
/* 305 */       GL11.glEnable(2929); 
/*     */   }
/*     */   
/*     */   public void draw(List<PredictedPosition> list, Entity entity) {
/* 309 */     boolean first = true;
/* 310 */     boolean depth = GL11.glIsEnabled(2929);
/* 311 */     boolean texture = GL11.glIsEnabled(3553);
/* 312 */     GL11.glPushMatrix();
/* 313 */     GL11.glDisable(3553);
/* 314 */     GL11.glColor4f(((ColorSetting)this.color2.getValue()).getRed() / 255.0F, ((ColorSetting)this.color2.getValue()).getGreen() / 255.0F, ((ColorSetting)this.color2.getValue()).getBlue() / 255.0F, ((ColorSetting)this.color2.getValue()).getAlpha() / 255.0F);
/* 315 */     GL11.glEnable(2848);
/* 316 */     GL11.glDisable(2929);
/* 317 */     GL11.glLineWidth(0.5F);
/* 318 */     GL11.glBegin(3);
/* 319 */     for (int i = 0; i < list.size(); i++) {
/* 320 */       PredictedPosition pp = list.get(i);
/* 321 */       Vec3d v = new Vec3d(pp.pos.field_72450_a, pp.pos.field_72448_b, pp.pos.field_72449_c);
/* 322 */       if (list.size() > 2 && first) {
/* 323 */         PredictedPosition next = list.get(i + 1);
/* 324 */         v = v.func_72441_c((next.pos.field_72450_a - v.field_72450_a) * mc.func_184121_ak(), (next.pos.field_72448_b - v.field_72448_b) * mc
/* 325 */             .func_184121_ak(), (next.pos.field_72449_c - v.field_72449_c) * mc
/* 326 */             .func_184121_ak());
/*     */       } 
/* 328 */       GL11.glVertex3d(v.field_72450_a - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX(), v.field_72448_b - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY(), v.field_72449_c - ((IRenderManager)Util.mc
/* 329 */           .func_175598_ae()).getRenderPosZ());
/* 330 */       first = false;
/*     */     } 
/* 332 */     list.removeIf(w -> (w.tick < entity.field_70173_aa));
/* 333 */     GL11.glEnd();
/*     */     
/* 335 */     if (depth)
/* 336 */       GL11.glEnable(2929); 
/* 337 */     if (texture) {
/* 338 */       GL11.glEnable(3553);
/*     */     }
/* 340 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void PearlToDest(Render3DEvent event) {
/* 344 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 345 */       if (entity instanceof net.minecraft.entity.item.EntityEnderPearl && 
/* 346 */         this.entAndTrail.get(entity) != null) {
/* 347 */         draw(this.entAndTrail.get(entity), entity);
/*     */       }
/*     */       
/* 350 */       if (entity instanceof net.minecraft.entity.projectile.EntityArrow && 
/* 351 */         this.entAndTrail.get(entity) != null) {
/* 352 */         draw(this.entAndTrail.get(entity), entity);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Mode
/*     */   {
/* 361 */     NONE, Mode1, Mode2, Both;
/*     */   }
/*     */   
/*     */   public static class PredictedPosition {
/*     */     public Color color;
/*     */     public Vec3d pos;
/*     */     public int tick;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\PearlESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */