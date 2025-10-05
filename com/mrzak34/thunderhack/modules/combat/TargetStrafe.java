/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.render.PaletteHelper;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TargetStrafe extends Module {
/*  26 */   public Setting<Float> reversedDistance = register(new Setting("Reversed Distance", Float.valueOf(3.0F), Float.valueOf(1.0F), Float.valueOf(6.0F)));
/*  27 */   public Setting<Float> speedIfUsing = register(new Setting("Speed if using", Float.valueOf(0.1F), Float.valueOf(0.1F), Float.valueOf(2.0F)));
/*  28 */   public Setting<Float> range = register(new Setting("Strafe Distance", Float.valueOf(2.4F), Float.valueOf(0.1F), Float.valueOf(6.0F)));
/*  29 */   public Setting<Float> spd = register(new Setting("Strafe Speed", Float.valueOf(0.23F), Float.valueOf(0.1F), Float.valueOf(2.0F)));
/*  30 */   public Setting<Boolean> reversed = register(new Setting("Reversed", Boolean.valueOf(false)));
/*  31 */   public Setting<Boolean> autoJump = register(new Setting("AutoJump", Boolean.valueOf(true)));
/*  32 */   public Setting<Boolean> smartStrafe = register(new Setting("Smart Strafe", Boolean.valueOf(true)));
/*  33 */   public Setting<Boolean> usingItemCheck = register(new Setting("EatingSlowDown", Boolean.valueOf(false)));
/*  34 */   public Setting<Boolean> speedpot = register(new Setting("Speed if Potion ", Boolean.valueOf(true)));
/*  35 */   public Setting<Float> spdd = register(new Setting("PotionSpeed", Float.valueOf(0.45F), Float.valueOf(0.1F), Float.valueOf(2.0F), v -> ((Boolean)this.speedpot.getValue()).booleanValue()));
/*  36 */   public Setting<Boolean> autoThirdPerson = register(new Setting("AutoThirdPers", Boolean.TRUE));
/*  37 */   public Setting<Float> trgrange = register(new Setting("TrgtRange", Float.valueOf(3.8F), Float.valueOf(0.1F), Float.valueOf(7.0F)));
/*  38 */   public Setting<Boolean> drawradius = register(new Setting("drawradius", Boolean.valueOf(true)));
/*  39 */   public Setting<Boolean> strafeBoost = register(new Setting("StrafeBoost", Boolean.valueOf(false)));
/*  40 */   public Setting<Boolean> addddd = register(new Setting("add", Boolean.valueOf(false)));
/*  41 */   public Setting<Float> reduction = register(new Setting("reduction ", Float.valueOf(2.0F), Float.valueOf(1.0F), Float.valueOf(5.0F)));
/*  42 */   public Setting<Float> velocityUse = register(new Setting("velocityUse ", Float.valueOf(50000.0F), Float.valueOf(0.1F), Float.valueOf(100000.0F)));
/*  43 */   public Setting<Integer> bticks = register(new Setting("BoostTicks", Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(60)));
/*  44 */   public Setting<Integer> velocitydecrement = register(new Setting("BoostDecr", Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(5000)));
/*  45 */   EntityPlayer strafeTarget = null;
/*  46 */   int boostticks = 0;
/*  47 */   float speedy = 1.0F;
/*  48 */   int velocity = 0;
/*  49 */   private float wrap = 0.0F;
/*     */   private boolean switchDir = true;
/*     */   
/*     */   public TargetStrafe() {
/*  53 */     super("TargetStrafe", "Вращаться вокруг цели", Module.Category.COMBAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  58 */     this.wrap = 0.0F;
/*  59 */     this.switchDir = true;
/*  60 */     Thunderhack.TICK_TIMER = 1.0F;
/*  61 */     this.velocity = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  66 */     if (((Boolean)this.autoThirdPerson.getValue()).booleanValue()) {
/*  67 */       mc.field_71474_y.field_74320_O = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean needToSwitch(double x, double z) {
/*  72 */     if (mc.field_71474_y.field_74370_x.func_151468_f() || mc.field_71474_y.field_74366_z.func_151468_f()) {
/*  73 */       return true;
/*     */     }
/*  75 */     for (int i = (int)(mc.field_71439_g.field_70163_u + 4.0D); i >= 0; ) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  81 */       BlockPos playerPos = new BlockPos(x, i, z);
/*  82 */       if (!mc.field_71441_e.func_180495_p(playerPos).func_177230_c().equals(Blocks.field_150353_l) && 
/*  83 */         !mc.field_71441_e.func_180495_p(playerPos).func_177230_c().equals(Blocks.field_150480_ab)) {
/*     */ 
/*     */ 
/*     */         
/*  87 */         if (mc.field_71441_e.func_175623_d(playerPos)) { i--; continue; }
/*  88 */          return false;
/*     */       }  return true;
/*  90 */     }  return true;
/*     */   }
/*     */   
/*     */   private float toDegree(double x, double z) {
/*  94 */     return (float)(Math.atan2(z - mc.field_71439_g.field_70161_v, x - mc.field_71439_g.field_70165_t) * 180.0D / Math.PI) - 90.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  99 */     if (Aura.target != null) {
/* 100 */       if (!(Aura.target instanceof EntityPlayer)) {
/*     */         return;
/*     */       }
/*     */       
/* 104 */       this.strafeTarget = (EntityPlayer)Aura.target;
/*     */     } else {
/*     */       
/* 107 */       this.strafeTarget = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerPre(EventSync event) {
/* 113 */     if (this.strafeTarget == null)
/*     */       return; 
/* 115 */     if (mc.field_71439_g.func_70068_e((Entity)this.strafeTarget) < 0.2D) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     if (((Boolean)this.autoThirdPerson.getValue()).booleanValue()) {
/* 120 */       if (this.strafeTarget.func_110143_aJ() > 0.0F && mc.field_71439_g.func_70032_d((Entity)this.strafeTarget) <= ((Float)this.trgrange.getValue()).floatValue() && mc.field_71439_g.func_110143_aJ() > 0.0F) {
/* 121 */         if (((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).isEnabled()) {
/* 122 */           mc.field_71474_y.field_74320_O = 1;
/*     */         }
/*     */       } else {
/* 125 */         mc.field_71474_y.field_74320_O = 0;
/*     */       } 
/*     */     }
/*     */     
/* 129 */     if (mc.field_71439_g.func_70032_d((Entity)this.strafeTarget) <= ((Float)this.trgrange.getValue()).floatValue()) {
/* 130 */       if (EntityUtil.getHealth((Entity)this.strafeTarget) > 0.0F && (
/* 131 */         (Boolean)this.autoJump.getValue()).booleanValue() && ((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).isEnabled() && 
/* 132 */         mc.field_71439_g.field_70122_E) {
/* 133 */         mc.field_71439_g.func_70664_aZ();
/*     */       }
/*     */ 
/*     */       
/* 137 */       if (EntityUtil.getHealth((Entity)this.strafeTarget) > 0.0F) {
/* 138 */         EntityPlayer entityPlayer = this.strafeTarget;
/* 139 */         if (entityPlayer == null || mc.field_71439_g.field_70173_aa < 20) {
/*     */           return;
/*     */         }
/*     */         
/* 143 */         if (((Boolean)this.speedpot.getValue()).booleanValue()) {
/* 144 */           if (mc.field_71439_g.func_70644_a(Objects.<Potion>requireNonNull(Potion.func_180142_b("speed")))) {
/* 145 */             this.speedy = ((Float)this.spdd.getValue()).floatValue();
/*     */           } else {
/* 147 */             this.speedy = ((Float)this.spd.getValue()).floatValue();
/*     */           } 
/*     */         } else {
/* 150 */           this.speedy = ((Float)this.spd.getValue()).floatValue();
/*     */         } 
/*     */         
/* 153 */         float speed = (mc.field_71474_y.field_74313_G.func_151470_d() && ((Boolean)this.usingItemCheck.getValue()).booleanValue()) ? ((Float)this.speedIfUsing.getValue()).floatValue() : this.speedy;
/*     */         
/* 155 */         if (this.velocity > ((Float)this.velocityUse.getValue()).floatValue() && ((Boolean)this.strafeBoost.getValue()).booleanValue()) {
/* 156 */           if (this.velocity < 0) {
/* 157 */             this.velocity = 0;
/*     */           }
/* 159 */           if (((Boolean)this.addddd.getValue()).booleanValue()) {
/* 160 */             speed += this.velocity / 8000.0F / ((Float)this.reduction.getValue()).floatValue();
/*     */           } else {
/* 162 */             speed = this.velocity / 8000.0F / ((Float)this.reduction.getValue()).floatValue();
/*     */           } 
/* 164 */           this.boostticks++;
/* 165 */           this.velocity -= ((Integer)this.velocitydecrement.getValue()).intValue();
/*     */         } 
/*     */         
/* 168 */         if (this.boostticks >= ((Integer)this.bticks.getValue()).intValue()) {
/* 169 */           this.boostticks = 0;
/* 170 */           this.velocity = 0;
/*     */         } 
/*     */         
/* 173 */         this.wrap = (float)Math.atan2(mc.field_71439_g.field_70161_v - ((EntityLivingBase)entityPlayer).field_70161_v, mc.field_71439_g.field_70165_t - ((EntityLivingBase)entityPlayer).field_70165_t);
/* 174 */         this.wrap += this.switchDir ? (speed / mc.field_71439_g.func_70032_d((Entity)entityPlayer)) : -(speed / mc.field_71439_g.func_70032_d((Entity)entityPlayer));
/* 175 */         double x = ((EntityLivingBase)entityPlayer).field_70165_t + ((Float)this.range.getValue()).floatValue() * Math.cos(this.wrap);
/* 176 */         double z = ((EntityLivingBase)entityPlayer).field_70161_v + ((Float)this.range.getValue()).floatValue() * Math.sin(this.wrap);
/* 177 */         if (((Boolean)this.smartStrafe.getValue()).booleanValue() && needToSwitch(x, z)) {
/* 178 */           this.switchDir = !this.switchDir;
/* 179 */           this.wrap += 2.0F * (this.switchDir ? (speed / mc.field_71439_g.func_70032_d((Entity)entityPlayer)) : -(speed / mc.field_71439_g.func_70032_d((Entity)entityPlayer)));
/* 180 */           x = ((EntityLivingBase)entityPlayer).field_70165_t + ((Float)this.range.getValue()).floatValue() * Math.cos(this.wrap);
/* 181 */           z = ((EntityLivingBase)entityPlayer).field_70161_v + ((Float)this.range.getValue()).floatValue() * Math.sin(this.wrap);
/*     */         } 
/* 183 */         float searchValue = (((Boolean)this.reversed.getValue()).booleanValue() && mc.field_71439_g.func_70032_d((Entity)this.strafeTarget) < ((Float)this.reversedDistance.getValue()).floatValue()) ? -90.0F : 0.0F;
/* 184 */         float reversedValue = (!mc.field_71474_y.field_74370_x.func_151470_d() && !mc.field_71474_y.field_74366_z.func_151470_d()) ? searchValue : 0.0F;
/*     */         
/* 186 */         mc.field_71439_g.field_70159_w = speed * -Math.sin((float)Math.toRadians(toDegree(x + reversedValue, z + reversedValue)));
/* 187 */         mc.field_71439_g.field_70179_y = speed * Math.cos((float)Math.toRadians(toDegree(x + reversedValue, z + reversedValue)));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent e) {
/* 197 */     if (Aura.target != null && ((Boolean)this.drawradius.getValue()).booleanValue()) {
/* 198 */       EntityLivingBase entity = Aura.target;
/*     */       
/* 200 */       double calcX = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * mc.func_184121_ak() - ((IRenderManager)mc.func_175598_ae()).getRenderPosX();
/*     */       
/* 202 */       double calcY = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * mc.func_184121_ak() - ((IRenderManager)mc.func_175598_ae()).getRenderPosY();
/*     */       
/* 204 */       double calcZ = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * mc.func_184121_ak() - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ();
/* 205 */       float radius = ((Float)this.range.getValue()).floatValue();
/* 206 */       GL11.glPushMatrix();
/* 207 */       GL11.glDisable(3553);
/* 208 */       GL11.glEnable(2848);
/* 209 */       GL11.glDisable(2929);
/* 210 */       GL11.glEnable(3042);
/* 211 */       GL11.glDisable(2929);
/* 212 */       GL11.glBegin(3);
/*     */       
/* 214 */       for (int i = 0; i <= 360; i++) {
/* 215 */         int rainbow = PaletteHelper.rainbow(300, 1.0F, 1.0F).getRGB();
/* 216 */         GlStateManager.func_179124_c((rainbow >> 16 & 0xFF) / 255.0F, (rainbow >> 8 & 0xFF) / 255.0F, (rainbow & 0xFF) / 255.0F);
/*     */         
/* 218 */         GL11.glVertex3d(calcX + radius * Math.cos(Math.toRadians(i)), calcY, calcZ + radius * 
/* 219 */             Math.sin(Math.toRadians(i)));
/*     */       } 
/*     */       
/* 222 */       GL11.glEnd();
/* 223 */       GL11.glDisable(3042);
/* 224 */       GL11.glEnable(2929);
/* 225 */       GL11.glDisable(2848);
/* 226 */       GL11.glEnable(2929);
/* 227 */       GL11.glEnable(3553);
/* 228 */       GL11.glPopMatrix();
/* 229 */       GlStateManager.func_179117_G();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/* 235 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/* 238 */     if (e.getPacket() instanceof SPacketEntityVelocity && (
/* 239 */       (SPacketEntityVelocity)e.getPacket()).func_149412_c() == mc.field_71439_g.func_145782_y()) {
/* 240 */       SPacketEntityVelocity pack = (SPacketEntityVelocity)e.getPacket();
/* 241 */       int vX = pack.func_149411_d();
/* 242 */       int vZ = pack.func_149409_f();
/* 243 */       if (vX < 0) vX *= -1; 
/* 244 */       if (vZ < 0) vZ *= -1; 
/* 245 */       this.velocity = vX + vZ;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\TargetStrafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */