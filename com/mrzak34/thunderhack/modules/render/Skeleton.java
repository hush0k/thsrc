/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Skeleton
/*     */   extends Module
/*     */ {
/*  23 */   private static final HashMap<EntityPlayer, float[][]> entities = (HashMap)new HashMap<>();
/*  24 */   public final Setting<ColorSetting> Color3 = register(new Setting("FriendColor", new ColorSetting(-2013200640)));
/*  25 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*  26 */   private final Setting<Boolean> invisibles = register(new Setting("Invisibles", Boolean.valueOf(false)));
/*     */   
/*     */   public Skeleton() {
/*  29 */     super("Skeleton", "скелетон есп-на игроков", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   public static void addEntity(EntityPlayer e, ModelPlayer model) {
/*  33 */     entities.put(e, new float[][] { { model.field_78116_c.field_78795_f, model.field_78116_c.field_78796_g, model.field_78116_c.field_78808_h }, { model.field_178723_h.field_78795_f, model.field_178723_h.field_78796_g, model.field_178723_h.field_78808_h }, { model.field_178724_i.field_78795_f, model.field_178724_i.field_78796_g, model.field_178724_i.field_78808_h }, { model.field_178721_j.field_78795_f, model.field_178721_j.field_78796_g, model.field_178721_j.field_78808_h }, { model.field_178722_k.field_78795_f, model.field_178722_k.field_78796_g, model.field_178722_k.field_78808_h } });
/*     */   }
/*     */   
/*     */   private Vec3d getVec3(Render3DEvent event, EntityPlayer e) {
/*  37 */     float pt = event.getPartialTicks();
/*  38 */     double x = e.field_70142_S + (e.field_70165_t - e.field_70142_S) * pt;
/*  39 */     double y = e.field_70137_T + (e.field_70163_u - e.field_70137_T) * pt;
/*  40 */     double z = e.field_70136_U + (e.field_70161_v - e.field_70136_U) * pt;
/*  41 */     return new Vec3d(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/*  46 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*  49 */     startEnd(true);
/*  50 */     GL11.glEnable(2903);
/*  51 */     GL11.glDisable(2848);
/*  52 */     entities.keySet().removeIf(this::doesntContain);
/*  53 */     mc.field_71441_e.field_73010_i.forEach(e -> drawSkeleton(event, e));
/*  54 */     Gui.func_73734_a(0, 0, 0, 0, 0);
/*  55 */     startEnd(false);
/*     */   }
/*     */   
/*     */   private void drawSkeleton(Render3DEvent event, EntityPlayer e) {
/*  59 */     if (!BlockUtils.isPosInFov(new BlockPos(e.field_70165_t, e.field_70163_u, e.field_70161_v)).booleanValue()) {
/*     */       return;
/*     */     }
/*  62 */     if (e.func_82150_aj() && !((Boolean)this.invisibles.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*  65 */     float[][] entPos = entities.get(e);
/*  66 */     if (entPos != null && e.func_70089_S() && !e.field_70128_L && e != mc.field_71439_g && !e.func_70608_bn()) {
/*  67 */       GL11.glPushMatrix();
/*  68 */       GL11.glEnable(2848);
/*  69 */       GL11.glLineWidth(((Float)this.lineWidth.getValue()).floatValue());
/*  70 */       if (Thunderhack.friendManager.isFriend(e.func_70005_c_())) {
/*  71 */         GlStateManager.func_179131_c(((ColorSetting)this.Color3.getValue()).getRed() / 255.0F, ((ColorSetting)this.Color3.getValue()).getGreen() / 255.0F, ((ColorSetting)this.Color3.getValue()).getBlue() / 255.0F, ((ColorSetting)this.Color3.getValue()).getAlpha() / 255.0F);
/*     */       } else {
/*  73 */         GlStateManager.func_179131_c(((ColorSetting)this.Color3.getValue()).getRed() / 255.0F, ((ColorSetting)this.Color3.getValue()).getGreen() / 255.0F, ((ColorSetting)this.Color3.getValue()).getBlue() / 255.0F, ((ColorSetting)this.Color3.getValue()).getAlpha() / 255.0F);
/*     */       } 
/*  75 */       Vec3d vec = getVec3(event, e);
/*  76 */       double x = vec.field_72450_a - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX();
/*  77 */       double y = vec.field_72448_b - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY();
/*  78 */       double z = vec.field_72449_c - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ();
/*  79 */       GL11.glTranslated(x, y, z);
/*  80 */       float xOff = e.field_70760_ar + (e.field_70761_aq - e.field_70760_ar) * event.getPartialTicks();
/*  81 */       GL11.glRotatef(-xOff, 0.0F, 1.0F, 0.0F);
/*  82 */       GL11.glTranslated(0.0D, 0.0D, e.func_70093_af() ? -0.235D : 0.0D);
/*  83 */       float yOff = e.func_70093_af() ? 0.6F : 0.75F;
/*  84 */       GL11.glPushMatrix();
/*  85 */       GL11.glTranslated(-0.125D, yOff, 0.0D);
/*  86 */       if (entPos[3][0] != 0.0F) {
/*  87 */         GL11.glRotatef(entPos[3][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */       }
/*  89 */       if (entPos[3][1] != 0.0F) {
/*  90 */         GL11.glRotatef(entPos[3][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*  92 */       if (entPos[3][2] != 0.0F) {
/*  93 */         GL11.glRotatef(entPos[3][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */       }
/*  95 */       GL11.glBegin(3);
/*  96 */       GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/*  97 */       GL11.glVertex3d(0.0D, -yOff, 0.0D);
/*  98 */       GL11.glEnd();
/*  99 */       GL11.glPopMatrix();
/* 100 */       GL11.glPushMatrix();
/* 101 */       GL11.glTranslated(0.125D, yOff, 0.0D);
/* 102 */       if (entPos[4][0] != 0.0F) {
/* 103 */         GL11.glRotatef(entPos[4][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */       }
/* 105 */       if (entPos[4][1] != 0.0F) {
/* 106 */         GL11.glRotatef(entPos[4][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */       }
/* 108 */       if (entPos[4][2] != 0.0F) {
/* 109 */         GL11.glRotatef(entPos[4][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */       }
/* 111 */       GL11.glBegin(3);
/* 112 */       GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/* 113 */       GL11.glVertex3d(0.0D, -yOff, 0.0D);
/* 114 */       GL11.glEnd();
/* 115 */       GL11.glPopMatrix();
/* 116 */       GL11.glTranslated(0.0D, 0.0D, e.func_70093_af() ? 0.25D : 0.0D);
/* 117 */       GL11.glPushMatrix();
/* 118 */       GL11.glTranslated(0.0D, e.func_70093_af() ? -0.05D : 0.0D, e.func_70093_af() ? -0.01725D : 0.0D);
/* 119 */       GL11.glPushMatrix();
/* 120 */       GL11.glTranslated(-0.375D, yOff + 0.55D, 0.0D);
/* 121 */       if (entPos[1][0] != 0.0F) {
/* 122 */         GL11.glRotatef(entPos[1][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */       }
/* 124 */       if (entPos[1][1] != 0.0F) {
/* 125 */         GL11.glRotatef(entPos[1][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */       }
/* 127 */       if (entPos[1][2] != 0.0F) {
/* 128 */         GL11.glRotatef(-entPos[1][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */       }
/* 130 */       GL11.glBegin(3);
/* 131 */       GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/* 132 */       GL11.glVertex3d(0.0D, -0.5D, 0.0D);
/* 133 */       GL11.glEnd();
/* 134 */       GL11.glPopMatrix();
/* 135 */       GL11.glPushMatrix();
/* 136 */       GL11.glTranslated(0.375D, yOff + 0.55D, 0.0D);
/* 137 */       if (entPos[2][0] != 0.0F) {
/* 138 */         GL11.glRotatef(entPos[2][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */       }
/* 140 */       if (entPos[2][1] != 0.0F) {
/* 141 */         GL11.glRotatef(entPos[2][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */       }
/* 143 */       if (entPos[2][2] != 0.0F) {
/* 144 */         GL11.glRotatef(-entPos[2][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */       }
/* 146 */       GL11.glBegin(3);
/* 147 */       GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/* 148 */       GL11.glVertex3d(0.0D, -0.5D, 0.0D);
/* 149 */       GL11.glEnd();
/* 150 */       GL11.glPopMatrix();
/* 151 */       GL11.glRotatef(xOff - e.field_70759_as, 0.0F, 1.0F, 0.0F);
/* 152 */       GL11.glPushMatrix();
/* 153 */       GL11.glTranslated(0.0D, yOff + 0.55D, 0.0D);
/* 154 */       if (entPos[0][0] != 0.0F) {
/* 155 */         GL11.glRotatef(entPos[0][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */       }
/* 157 */       GL11.glBegin(3);
/* 158 */       GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/* 159 */       GL11.glVertex3d(0.0D, 0.3D, 0.0D);
/* 160 */       GL11.glEnd();
/* 161 */       GL11.glPopMatrix();
/* 162 */       GL11.glPopMatrix();
/* 163 */       GL11.glRotatef(e.func_70093_af() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
/* 164 */       GL11.glTranslated(0.0D, e.func_70093_af() ? -0.16175D : 0.0D, e.func_70093_af() ? -0.48025D : 0.0D);
/* 165 */       GL11.glPushMatrix();
/* 166 */       GL11.glTranslated(0.0D, yOff, 0.0D);
/* 167 */       GL11.glBegin(3);
/* 168 */       GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
/* 169 */       GL11.glVertex3d(0.125D, 0.0D, 0.0D);
/* 170 */       GL11.glEnd();
/* 171 */       GL11.glPopMatrix();
/* 172 */       GL11.glPushMatrix();
/* 173 */       GL11.glTranslated(0.0D, yOff, 0.0D);
/* 174 */       GL11.glBegin(3);
/* 175 */       GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/* 176 */       GL11.glVertex3d(0.0D, 0.55D, 0.0D);
/* 177 */       GL11.glEnd();
/* 178 */       GL11.glPopMatrix();
/* 179 */       GL11.glPushMatrix();
/* 180 */       GL11.glTranslated(0.0D, yOff + 0.55D, 0.0D);
/* 181 */       GL11.glBegin(3);
/* 182 */       GL11.glVertex3d(-0.375D, 0.0D, 0.0D);
/* 183 */       GL11.glVertex3d(0.375D, 0.0D, 0.0D);
/* 184 */       GL11.glEnd();
/* 185 */       GL11.glPopMatrix();
/* 186 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startEnd(boolean revert) {
/* 191 */     if (revert) {
/* 192 */       GlStateManager.func_179094_E();
/* 193 */       GlStateManager.func_179147_l();
/* 194 */       GL11.glEnable(2848);
/* 195 */       GlStateManager.func_179097_i();
/* 196 */       GlStateManager.func_179090_x();
/* 197 */       GL11.glHint(3154, 4354);
/*     */     } else {
/* 199 */       GlStateManager.func_179084_k();
/* 200 */       GlStateManager.func_179098_w();
/* 201 */       GL11.glDisable(2848);
/* 202 */       GlStateManager.func_179126_j();
/* 203 */       GlStateManager.func_179121_F();
/*     */     } 
/* 205 */     GlStateManager.func_179132_a((!revert));
/*     */   }
/*     */   
/*     */   private boolean doesntContain(EntityPlayer entityPlayer) {
/* 209 */     return !mc.field_71441_e.field_73010_i.contains(entityPlayer);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\Skeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */