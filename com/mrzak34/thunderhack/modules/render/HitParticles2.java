/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import com.mrzak34.thunderhack.util.render.DrawHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class HitParticles2
/*     */   extends Module
/*     */ {
/*  26 */   public final Setting<ColorSetting> colorLight = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  27 */   public final Setting<ColorSetting> colorLight2 = register(new Setting("Color2", new ColorSetting(-2013200640)));
/*  28 */   public final Setting<ColorSetting> colorLight3 = register(new Setting("Color3", new ColorSetting(-2013200640)));
/*  29 */   public final Setting<ColorSetting> colorLight4 = register(new Setting("Color4", new ColorSetting(-2013200640)));
/*     */ 
/*     */ 
/*     */   
/*  33 */   public Setting<Boolean> selfp = register(new Setting("Self", Boolean.valueOf(false)));
/*  34 */   public Setting<Integer> speedor = register(new Setting("Time", Integer.valueOf(8000), Integer.valueOf(1), Integer.valueOf(10000)));
/*  35 */   public Setting<Integer> speedor2 = register(new Setting("speed", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(1000)));
/*  36 */   ArrayList<Particle> particles = new ArrayList<>();
/*     */   public HitParticles2() {
/*  38 */     super("HitParticles", "HitParticles", Module.Category.RENDER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  43 */     if (mc.field_71441_e != null && mc.field_71439_g != null) {
/*  44 */       for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/*  45 */         if (!((Boolean)this.selfp.getValue()).booleanValue() && player == mc.field_71439_g) {
/*     */           continue;
/*     */         }
/*  48 */         if (player.field_70737_aN > 0) {
/*     */           
/*  50 */           Color col = null;
/*     */           
/*  52 */           int j = (int)MathUtil.random(0.0F, 3.0F);
/*     */           
/*  54 */           switch (j) { case 0:
/*  55 */               col = ((ColorSetting)this.colorLight.getValue()).getColorObject(); break;
/*  56 */             case 1: col = ((ColorSetting)this.colorLight2.getValue()).getColorObject(); break;
/*  57 */             case 2: col = ((ColorSetting)this.colorLight3.getValue()).getColorObject(); break;
/*  58 */             case 3: col = ((ColorSetting)this.colorLight4.getValue()).getColorObject();
/*     */               break; }
/*     */           
/*  61 */           this.particles.add(new Particle(player.field_70165_t + MathUtil.random(-0.05F, 0.05F), MathUtil.random((float)(player.field_70163_u + player.field_70131_O), (float)player.field_70163_u), player.field_70161_v + MathUtil.random(-0.05F, 0.05F), col));
/*  62 */           this.particles.add(new Particle(player.field_70165_t, MathUtil.random((float)(player.field_70163_u + player.field_70131_O), (float)(player.field_70163_u + 0.10000000149011612D)), player.field_70161_v, col));
/*  63 */           this.particles.add(new Particle(player.field_70165_t, MathUtil.random((float)(player.field_70163_u + player.field_70131_O), (float)(player.field_70163_u + 0.10000000149011612D)), player.field_70161_v, col));
/*     */         } 
/*     */         
/*  66 */         for (int i = 0; i < this.particles.size(); i++) {
/*  67 */           if (System.currentTimeMillis() - ((Particle)this.particles.get(i)).getTime() >= ((Integer)this.speedor.getValue()).intValue()) {
/*  68 */             this.particles.remove(i);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  78 */     if (mc.field_71439_g != null && mc.field_71441_e != null)
/*  79 */       for (Particle particle : this.particles) {
/*  80 */         particle.render();
/*     */       } 
/*     */   }
/*     */   
/*     */   public class Particle
/*     */   {
/*  86 */     public int alpha = 180;
/*     */     double x;
/*     */     double y;
/*     */     double z;
/*     */     double motionX;
/*     */     double motionY;
/*     */     double motionZ;
/*     */     long time;
/*     */     Color color;
/*     */     
/*     */     public Particle(double x, double y, double z, Color color) {
/*  97 */       this.x = x;
/*  98 */       this.y = y;
/*  99 */       this.z = z;
/* 100 */       this.motionX = MathUtil.random(-(((Integer)HitParticles2.this.speedor2.getValue()).intValue()) / 1000.0F, ((Integer)HitParticles2.this.speedor2.getValue()).intValue() / 1000.0F);
/* 101 */       this.motionY = MathUtil.random(-(((Integer)HitParticles2.this.speedor2.getValue()).intValue()) / 1000.0F, ((Integer)HitParticles2.this.speedor2.getValue()).intValue() / 1000.0F);
/* 102 */       this.motionZ = MathUtil.random(-(((Integer)HitParticles2.this.speedor2.getValue()).intValue()) / 1000.0F, ((Integer)HitParticles2.this.speedor2.getValue()).intValue() / 1000.0F);
/* 103 */       this.time = System.currentTimeMillis();
/* 104 */       this.color = color;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getTime() {
/* 109 */       return this.time;
/*     */     }
/*     */     
/*     */     public void update() {
/* 113 */       double yEx = 0.0D;
/*     */       
/* 115 */       double sp = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0D;
/* 116 */       this.x += this.motionX;
/*     */       
/* 118 */       this.y += this.motionY;
/*     */       
/* 120 */       if (posBlock(this.x, this.y, this.z)) {
/* 121 */         this.motionY = -this.motionY / 1.1D;
/*     */       
/*     */       }
/* 124 */       else if (posBlock(this.x, this.y, this.z) || 
/* 125 */         posBlock(this.x, this.y - yEx, this.z) || 
/* 126 */         posBlock(this.x, this.y + yEx, this.z) || 
/*     */         
/* 128 */         posBlock(this.x - sp, this.y, this.z - sp) || 
/* 129 */         posBlock(this.x + sp, this.y, this.z + sp) || 
/* 130 */         posBlock(this.x + sp, this.y, this.z - sp) || 
/* 131 */         posBlock(this.x - sp, this.y, this.z + sp) || 
/* 132 */         posBlock(this.x + sp, this.y, this.z) || 
/* 133 */         posBlock(this.x - sp, this.y, this.z) || 
/* 134 */         posBlock(this.x, this.y, this.z + sp) || 
/* 135 */         posBlock(this.x, this.y, this.z - sp) || 
/*     */         
/* 137 */         posBlock(this.x - sp, this.y - yEx, this.z - sp) || 
/* 138 */         posBlock(this.x + sp, this.y - yEx, this.z + sp) || 
/* 139 */         posBlock(this.x + sp, this.y - yEx, this.z - sp) || 
/* 140 */         posBlock(this.x - sp, this.y - yEx, this.z + sp) || 
/* 141 */         posBlock(this.x + sp, this.y - yEx, this.z) || 
/* 142 */         posBlock(this.x - sp, this.y - yEx, this.z) || 
/* 143 */         posBlock(this.x, this.y - yEx, this.z + sp) || 
/* 144 */         posBlock(this.x, this.y - yEx, this.z - sp) || 
/*     */         
/* 146 */         posBlock(this.x - sp, this.y + yEx, this.z - sp) || 
/* 147 */         posBlock(this.x + sp, this.y + yEx, this.z + sp) || 
/* 148 */         posBlock(this.x + sp, this.y + yEx, this.z - sp) || 
/* 149 */         posBlock(this.x - sp, this.y + yEx, this.z + sp) || 
/* 150 */         posBlock(this.x + sp, this.y + yEx, this.z) || 
/* 151 */         posBlock(this.x - sp, this.y + yEx, this.z) || 
/* 152 */         posBlock(this.x, this.y + yEx, this.z + sp) || 
/* 153 */         posBlock(this.x, this.y + yEx, this.z - sp)) {
/*     */ 
/*     */         
/* 156 */         this.motionX = -this.motionX + this.motionZ;
/* 157 */         this.motionZ = -this.motionZ + this.motionX;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 163 */       this.z += this.motionZ;
/*     */ 
/*     */       
/* 166 */       this.motionX /= 1.005D;
/* 167 */       this.motionZ /= 1.005D;
/* 168 */       this.motionY /= 1.005D;
/*     */     }
/*     */     
/*     */     public void render() {
/* 172 */       this.color = DrawHelper.injectAlpha(this.color, this.alpha);
/* 173 */       update();
/* 174 */       this.alpha = (int)(this.alpha - 0.1D);
/* 175 */       float scale = 0.07F;
/* 176 */       GlStateManager.func_179126_j();
/* 177 */       GL11.glEnable(3042);
/* 178 */       GL11.glDisable(3553);
/* 179 */       GL11.glEnable(2848);
/* 180 */       GL11.glBlendFunc(770, 771);
/*     */ 
/*     */       
/*     */       try {
/* 184 */         double posX = this.x - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX();
/* 185 */         double posY = this.y - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY();
/* 186 */         double posZ = this.z - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ();
/*     */         
/* 188 */         double distanceFromPlayer = Module.mc.field_71439_g.func_70011_f(this.x, this.y - 1.0D, this.z);
/* 189 */         int quality = (int)(distanceFromPlayer * 4.0D + 10.0D);
/*     */         
/* 191 */         if (quality > 350) {
/* 192 */           quality = 350;
/*     */         }
/* 194 */         GL11.glPushMatrix();
/* 195 */         GL11.glTranslated(posX, posY, posZ);
/*     */ 
/*     */         
/* 198 */         GL11.glScalef(-scale, -scale, -scale);
/*     */         
/* 200 */         GL11.glRotated(-(Module.mc.func_175598_ae()).field_78735_i, 0.0D, 1.0D, 0.0D);
/* 201 */         GL11.glRotated((Module.mc.func_175598_ae()).field_78732_j, 1.0D, 0.0D, 0.0D);
/*     */ 
/*     */         
/* 204 */         RenderUtil.drawFilledCircleNoGL(0, 0, 0.7D, this.color.hashCode(), quality);
/*     */         
/* 206 */         if (distanceFromPlayer < 4.0D) {
/* 207 */           RenderUtil.drawFilledCircleNoGL(0, 0, 1.4D, (new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 50)).hashCode(), quality);
/*     */         }
/* 209 */         if (distanceFromPlayer < 20.0D) {
/* 210 */           RenderUtil.drawFilledCircleNoGL(0, 0, 2.3D, (new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 30)).hashCode(), quality);
/*     */         }
/*     */         
/* 213 */         GL11.glScalef(0.8F, 0.8F, 0.8F);
/*     */         
/* 215 */         GL11.glPopMatrix();
/*     */       
/*     */       }
/* 218 */       catch (ConcurrentModificationException concurrentModificationException) {}
/*     */ 
/*     */       
/* 221 */       GL11.glDisable(2848);
/* 222 */       GL11.glEnable(3553);
/* 223 */       GL11.glDisable(3042);
/* 224 */       GL11.glColor3d(255.0D, 255.0D, 255.0D);
/*     */     }
/*     */     
/*     */     private boolean posBlock(double x, double y, double z) {
/* 228 */       return (Module.mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150350_a && Module.mc.field_71441_e
/* 229 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150355_j && Module.mc.field_71441_e
/* 230 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150353_l && Module.mc.field_71441_e
/* 231 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150324_C && Module.mc.field_71441_e
/* 232 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150414_aQ && Module.mc.field_71441_e
/* 233 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150329_H && Module.mc.field_71441_e
/* 234 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150457_bL && Module.mc.field_71441_e
/* 235 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150328_O && Module.mc.field_71441_e
/* 236 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150327_N && Module.mc.field_71441_e
/* 237 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150345_g && Module.mc.field_71441_e
/* 238 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150395_bd && Module.mc.field_71441_e
/* 239 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180405_aT && Module.mc.field_71441_e
/* 240 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180387_bt && Module.mc.field_71441_e
/* 241 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180404_aQ && Module.mc.field_71441_e
/* 242 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180392_bq && Module.mc.field_71441_e
/* 243 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180406_aS && Module.mc.field_71441_e
/* 244 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180385_bs && Module.mc.field_71441_e
/* 245 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180403_aR && Module.mc.field_71441_e
/* 246 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180386_br && Module.mc.field_71441_e
/* 247 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150386_bk && Module.mc.field_71441_e
/* 248 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180407_aO && Module.mc.field_71441_e
/* 249 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180390_bo && Module.mc.field_71441_e
/* 250 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180408_aP && Module.mc.field_71441_e
/* 251 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180391_bp && Module.mc.field_71441_e
/* 252 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150381_bn && Module.mc.field_71441_e
/* 253 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150378_br && Module.mc.field_71441_e
/* 254 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150398_cm && Module.mc.field_71441_e
/* 255 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150472_an && Module.mc.field_71441_e
/* 256 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150444_as && Module.mc.field_71441_e
/* 257 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150465_bP && Module.mc.field_71441_e
/* 258 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150453_bW && Module.mc.field_71441_e
/* 259 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_180402_cm && Module.mc.field_71441_e
/* 260 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150333_U && Module.mc.field_71441_e
/* 261 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150376_bx && Module.mc.field_71441_e
/* 262 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150404_cg && Module.mc.field_71441_e
/* 263 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150330_I && Module.mc.field_71441_e
/* 264 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150395_bd && Module.mc.field_71441_e
/* 265 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150488_af && Module.mc.field_71441_e
/* 266 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150436_aH && Module.mc.field_71441_e
/* 267 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150431_aC);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\HitParticles2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */