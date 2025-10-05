/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.dism.EntityGib;
/*     */ import com.mrzak34.thunderhack.util.dism.ParticleBlood;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.entity.living.LivingDeathEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Dismemberment
/*     */   extends Module
/*     */ {
/*     */   public static int ticks;
/*  34 */   public Setting<Boolean> blood = register(new Setting("Blood", Boolean.valueOf(false)));
/*  35 */   public Setting<Integer> gibGroundTime = register(new Setting("OnGroundTime", Integer.valueOf(425), Integer.valueOf(0), Integer.valueOf(2000)));
/*  36 */   public Setting<Integer> gibTime = register(new Setting("LiveTime", Integer.valueOf(425), Integer.valueOf(0), Integer.valueOf(2000)));
/*  37 */   public Setting<Integer> bcount = register(new Setting("BloodCount", Integer.valueOf(425), Integer.valueOf(0), Integer.valueOf(2000)));
/*     */ 
/*     */   
/*  40 */   public HashMap<EntityLivingBase, Integer> dismemberTimeout = new HashMap<>();
/*  41 */   public HashMap<Entity, Integer> exploTime = new HashMap<>();
/*  42 */   public ArrayList<Entity> explosionSources = new ArrayList<>();
/*  43 */   public HashMap<EntityLivingBase, EntityGib[]> amputationMap = (HashMap)new HashMap<>();
/*     */ 
/*     */   
/*     */   public Dismemberment() {
/*  47 */     super("Dismemberment", "Dismemberment", Module.Category.MISC);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void spawnParticle(Particle particle) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLivingDeath(LivingDeathEvent event) {
/*  56 */     if ((event.getEntity()).field_70170_p.field_72995_K && (event.getEntityLiving() instanceof net.minecraft.entity.monster.EntityZombie || event.getEntityLiving() instanceof net.minecraft.entity.player.EntityPlayer || event.getEntityLiving() instanceof net.minecraft.entity.monster.EntitySkeleton || event.getEntityLiving() instanceof net.minecraft.entity.monster.EntityCreeper) && !event.getEntityLiving().func_70631_g_()) {
/*  57 */       this.dismemberTimeout.put(event.getEntityLiving(), Integer.valueOf(2));
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onClientConnection(FMLNetworkEvent.ClientConnectedToServerEvent event) {
/*  63 */     this.exploTime.clear();
/*  64 */     this.dismemberTimeout.clear();
/*  65 */     this.explosionSources.clear();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void worldTick(TickEvent.ClientTickEvent event) {
/*  70 */     if (event.phase == TickEvent.Phase.END && (Minecraft.func_71410_x()).field_71441_e != null) {
/*  71 */       Minecraft mc = Minecraft.func_71410_x();
/*  72 */       WorldClient world = mc.field_71441_e;
/*     */       
/*  74 */       if (!mc.func_147113_T()) {
/*  75 */         int i; for (i = 0; i < world.field_72996_f.size(); i++) {
/*  76 */           Entity ent = world.field_72996_f.get(i);
/*  77 */           if ((ent instanceof net.minecraft.entity.monster.EntityCreeper || ent instanceof net.minecraft.entity.item.EntityTNTPrimed || ent instanceof net.minecraft.entity.item.EntityMinecartTNT || ent instanceof net.minecraft.entity.item.EntityEnderCrystal) && 
/*  78 */             !this.explosionSources.contains(ent)) {
/*  79 */             this.explosionSources.add(ent);
/*     */           }
/*     */           
/*  82 */           if ((ent instanceof net.minecraft.entity.monster.EntityZombie || ent instanceof net.minecraft.entity.player.EntityPlayer || ent instanceof net.minecraft.entity.monster.EntitySkeleton || ent instanceof net.minecraft.entity.monster.EntityCreeper) && !ent.func_70089_S() && !this.dismemberTimeout.containsKey(ent)) {
/*  83 */             this.dismemberTimeout.put((EntityLivingBase)ent, Integer.valueOf(2));
/*     */           }
/*     */         } 
/*  86 */         for (i = this.explosionSources.size() - 1; i >= 0; i--) {
/*  87 */           Entity ent = this.explosionSources.get(i);
/*  88 */           if (ent.field_70128_L) {
/*  89 */             if ((ent instanceof net.minecraft.entity.item.EntityTNTPrimed || ent instanceof net.minecraft.entity.item.EntityMinecartTNT) && 
/*  90 */               !this.exploTime.containsKey(ent)) {
/*  91 */               int time = ticks % 24000;
/*  92 */               if (time > 23959L) {
/*  93 */                 time = (int)(time - 23999L);
/*     */               }
/*  95 */               this.exploTime.put(ent, Integer.valueOf(time));
/*     */             } 
/*     */ 
/*     */             
/*  99 */             this.explosionSources.remove(i);
/*     */           } 
/*     */         } 
/*     */         
/* 103 */         Iterator<Map.Entry<EntityLivingBase, Integer>> ite = this.dismemberTimeout.entrySet().iterator();
/* 104 */         if (ite.hasNext()) {
/* 105 */           Map.Entry<EntityLivingBase, Integer> e = ite.next();
/*     */           
/* 107 */           e.setValue(Integer.valueOf(((Integer)e.getValue()).intValue() - 1));
/*     */           
/* 109 */           ((EntityLivingBase)e.getKey()).field_70737_aN = 0;
/* 110 */           ((EntityLivingBase)e.getKey()).field_70725_aQ = 0;
/*     */           
/* 112 */           Entity explo = null;
/* 113 */           double dist = 1000.0D;
/* 114 */           for (Map.Entry<Entity, Integer> e1 : this.exploTime.entrySet()) {
/* 115 */             double mobDist = ((Entity)e1.getKey()).func_70032_d((Entity)e.getKey());
/* 116 */             if (mobDist < 10.0D && mobDist < dist) {
/* 117 */               dist = mobDist;
/* 118 */               explo = e1.getKey();
/* 119 */               e.setValue(Integer.valueOf(0));
/*     */             } 
/*     */           } 
/*     */           
/* 123 */           if (((Integer)e.getValue()).intValue() <= 0) {
/* 124 */             if (dismember(((EntityLivingBase)e.getKey()).field_70170_p, e.getKey(), explo)) {
/* 125 */               ((EntityLivingBase)e.getKey()).func_70106_y();
/*     */             }
/* 127 */             ite.remove();
/*     */           } 
/*     */         } 
/*     */         
/* 131 */         Iterator<Map.Entry<Entity, Integer>> ite1 = this.exploTime.entrySet().iterator();
/* 132 */         int worldTime = ticks % 24000;
/* 133 */         while (ite1.hasNext()) {
/* 134 */           Map.Entry<Entity, Integer> e = ite1.next();
/* 135 */           if (((Integer)e.getValue()).intValue() + 40L < worldTime) {
/* 136 */             ite1.remove();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean dismember(World world, EntityLivingBase living, Entity explo) {
/* 144 */     if (living.func_70631_g_()) {
/* 145 */       return false;
/*     */     }
/* 147 */     if (living instanceof net.minecraft.entity.monster.EntityCreeper) {
/* 148 */       world.func_72838_d((Entity)new EntityGib(world, living, 0, explo));
/* 149 */       world.func_72838_d((Entity)new EntityGib(world, living, 3, explo));
/* 150 */       world.func_72838_d((Entity)new EntityGib(world, living, 6, explo));
/* 151 */       world.func_72838_d((Entity)new EntityGib(world, living, 7, explo));
/* 152 */       world.func_72838_d((Entity)new EntityGib(world, living, 8, explo));
/* 153 */       world.func_72838_d((Entity)new EntityGib(world, living, 9, explo));
/*     */     } else {
/* 155 */       if (living instanceof net.minecraft.entity.player.EntityPlayer) {
/* 156 */         for (int i = -1; i < 6; i++) {
/* 157 */           world.func_72838_d((Entity)new EntityGib(world, living, i, explo));
/*     */         }
/*     */       } else {
/* 160 */         for (int i = 0; i < 6; i++) {
/* 161 */           world.func_72838_d((Entity)new EntityGib(world, living, i, explo));
/*     */         }
/*     */       } 
/*     */       
/* 165 */       if (living instanceof net.minecraft.entity.monster.EntityZombie || (living instanceof net.minecraft.entity.player.EntityPlayer && ((Boolean)this.blood.getValue()).booleanValue())) {
/* 166 */         for (int k = 0; k < ((explo != null) ? (((Integer)this.bcount.getValue()).intValue() * 10) : ((Integer)this.bcount.getValue()).intValue()); k++) {
/* 167 */           float var4 = 0.3F;
/* 168 */           double mX = (-MathHelper.func_76126_a(living.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(living.field_70125_A / 180.0F * 3.1415927F) * var4);
/* 169 */           double mZ = (MathHelper.func_76134_b(living.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(living.field_70125_A / 180.0F * 3.1415927F) * var4);
/* 170 */           double mY = (-MathHelper.func_76126_a(living.field_70125_A / 180.0F * 3.1415927F) * var4 + 0.1F);
/* 171 */           var4 = 0.02F;
/* 172 */           float var5 = living.func_70681_au().nextFloat() * 3.1415927F * 2.0F;
/* 173 */           var4 *= living.func_70681_au().nextFloat();
/*     */           
/* 175 */           if (explo != null) {
/* 176 */             var4 = (float)(var4 * 100.0D);
/*     */           }
/*     */           
/* 179 */           mX += Math.cos(var5) * var4;
/* 180 */           mY += ((living.func_70681_au().nextFloat() - living.func_70681_au().nextFloat()) * 0.1F);
/* 181 */           mZ += Math.sin(var5) * var4;
/*     */           
/* 183 */           spawnParticle((Particle)new ParticleBlood(living.field_70170_p, living.field_70165_t, living.field_70163_u + 0.5D + living.func_70681_au().nextDouble() * 0.7D, living.field_70161_v, living.field_70159_w + mX, living.field_70181_x + mY, living.field_70179_y + mZ, false));
/*     */         } 
/*     */       }
/*     */     } 
/* 187 */     return true;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onClientTick(TickEvent.ClientTickEvent event) {
/* 192 */     if (event.phase == TickEvent.Phase.END)
/* 193 */       ticks++; 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\Dismemberment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */