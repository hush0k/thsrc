/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.ConnectToServerEvent;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import io.netty.util.internal.ConcurrentSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.network.play.server.SPacketDestroyEntities;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SetDeadManager
/*     */ {
/*  35 */   private final Set<SoundObserver> observers = (Set<SoundObserver>)new ConcurrentSet();
/*  36 */   private final Map<Integer, EntityTime> killed = new ConcurrentHashMap<>();
/*     */ 
/*     */   
/*     */   public void init() {
/*  40 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public void unload() {
/*  44 */     MinecraftForge.EVENT_BUS.unregister(this);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  49 */     if (e.getPacket() instanceof SPacketDestroyEntities)
/*     */     {
/*  51 */       Util.mc.func_152344_a(() -> {
/*     */             for (int id : ((SPacketDestroyEntities)e.getPacket()).func_149098_c()) {
/*     */               confirmKill(id);
/*     */             }
/*     */           });
/*     */     }
/*     */     
/*  58 */     if (e.getPacket() instanceof SPacketSoundEffect) {
/*  59 */       SPacketSoundEffect p = (SPacketSoundEffect)e.getPacket();
/*  60 */       if (p.func_186977_b() == SoundCategory.BLOCKS && p
/*  61 */         .func_186978_a() == SoundEvents.field_187539_bB && 
/*  62 */         shouldRemove()) {
/*  63 */         Vec3d pos = new Vec3d(p.func_149207_d(), p.func_149211_e(), p.func_149210_f());
/*  64 */         Util.mc.func_152344_a(() -> {
/*     */               removeCrystals(pos, 11.0F, Util.mc.field_71441_e.field_72996_f);
/*     */               for (SoundObserver observer : this.observers) {
/*     */                 if (observer.shouldBeNotified()) {
/*     */                   observer.onChange(p);
/*     */                 }
/*     */               } 
/*     */             });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdate(EventSync e) {
/*  82 */     updateKilled();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConnect(ConnectToServerEvent e) {
/*  87 */     clear();
/*     */   }
/*     */   
/*     */   public Entity getEntity(int id) {
/*  91 */     EntityTime time = this.killed.get(Integer.valueOf(id));
/*  92 */     if (time != null) {
/*  93 */       return time.getEntity();
/*     */     }
/*     */     
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   public void setDeadCustom(Entity entity, long t) {
/* 100 */     EntityTime time = this.killed.get(Integer.valueOf(entity.func_145782_y()));
/* 101 */     if (time instanceof CustomEntityTime) {
/* 102 */       time.getEntity().func_70106_y();
/* 103 */       time.reset();
/*     */     } else {
/* 105 */       entity.func_70106_y();
/* 106 */       this.killed.put(Integer.valueOf(entity.func_145782_y()), new CustomEntityTime(entity, t));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void revive(int id) {
/* 111 */     EntityTime time = this.killed.remove(Integer.valueOf(id));
/* 112 */     if (time != null && time.isValid()) {
/* 113 */       Entity entity = time.getEntity();
/* 114 */       entity.field_70128_L = false;
/* 115 */       Util.mc.field_71441_e.func_73027_a(entity.func_145782_y(), entity);
/* 116 */       entity.field_70128_L = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateKilled() {
/* 127 */     for (Map.Entry<Integer, EntityTime> entry : this.killed.entrySet()) {
/* 128 */       if (!((EntityTime)entry.getValue()).isValid()) {
/* 129 */         ((EntityTime)entry.getValue()).getEntity().func_70106_y();
/* 130 */         this.killed.remove(entry.getKey()); continue;
/* 131 */       }  if (((EntityTime)entry.getValue()).passed(((Integer)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).deathTime.getValue()).intValue())) {
/*     */         
/* 133 */         Entity entity = ((EntityTime)entry.getValue()).getEntity();
/* 134 */         entity.field_70128_L = false;
/* 135 */         if (!Util.mc.field_71441_e.field_72996_f.contains(entity)) {
/* 136 */           Util.mc.field_71441_e.func_73027_a(((Integer)entry.getKey()).intValue(), entity);
/* 137 */           entity.field_70128_L = false;
/* 138 */           this.killed.remove(entry.getKey());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeCrystals(Vec3d pos, float range, List<Entity> entities) {
/* 153 */     for (Entity entity : entities) {
/* 154 */       if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && entity
/* 155 */         .func_70092_e(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c) <= 
/* 156 */         MathUtil.square(range)) {
/* 157 */         setDead(entity);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead(Entity entity) {
/* 171 */     EntityTime time = this.killed.get(Integer.valueOf(entity.func_145782_y()));
/* 172 */     if (time != null) {
/* 173 */       time.getEntity().func_70106_y();
/* 174 */       time.reset();
/* 175 */     } else if (!entity.field_70128_L) {
/* 176 */       entity.func_70106_y();
/* 177 */       this.killed.put(Integer.valueOf(entity.func_145782_y()), new EntityTime(entity));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void confirmKill(int id) {
/* 188 */     EntityTime time = this.killed.get(Integer.valueOf(id));
/* 189 */     if (time != null) {
/* 190 */       time.setValid(false);
/* 191 */       time.getEntity().func_70106_y();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean passedDeathTime(Entity entity, long deathTime) {
/* 196 */     return passedDeathTime(entity.func_145782_y(), deathTime);
/*     */   }
/*     */   
/*     */   public boolean passedDeathTime(int id, long deathTime) {
/* 200 */     if (deathTime <= 0L) {
/* 201 */       return true;
/*     */     }
/*     */     
/* 204 */     EntityTime time = this.killed.get(Integer.valueOf(id));
/* 205 */     if (time != null && time.isValid()) {
/* 206 */       return time.passed(deathTime);
/*     */     }
/*     */     
/* 209 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 216 */     this.killed.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObserver(SoundObserver observer) {
/* 228 */     this.observers.add(observer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeObserver(SoundObserver observer) {
/* 237 */     this.observers.remove(observer);
/*     */   }
/*     */   
/*     */   private boolean shouldRemove() {
/* 241 */     if (((Boolean)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).soundRemove.getValue()).booleanValue())
/*     */     {
/* 243 */       return false;
/*     */     }
/*     */     
/* 246 */     for (SoundObserver soundObserver : this.observers) {
/* 247 */       if (soundObserver.shouldRemove()) {
/* 248 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 252 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\SetDeadManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */