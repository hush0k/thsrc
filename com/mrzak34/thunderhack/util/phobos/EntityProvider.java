/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PostWorldTick;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityProvider
/*     */ {
/*  27 */   private volatile List<EntityPlayer> players = Collections.emptyList();
/*  28 */   private volatile List<Entity> entities = Collections.emptyList();
/*     */ 
/*     */   
/*     */   public void init() {
/*  32 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public void unload() {
/*  36 */     MinecraftForge.EVENT_BUS.unregister(this);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPostTick(PostWorldTick e) {
/*  41 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   private void update() {
/*  46 */     if (Util.mc.field_71441_e != null) {
/*  47 */       setLists(new ArrayList<>(Util.mc.field_71441_e.field_72996_f), new ArrayList<>(Util.mc.field_71441_e.field_73010_i));
/*     */     } else {
/*  49 */       setLists(Collections.emptyList(), 
/*  50 */           Collections.emptyList());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setLists(List<Entity> loadedEntities, List<EntityPlayer> playerEntities) {
/*  56 */     this.entities = loadedEntities;
/*  57 */     this.players = playerEntities;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Entity> getEntities() {
/*  68 */     return this.entities;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<EntityPlayer> getPlayers() {
/*  79 */     return this.players;
/*     */   }
/*     */   
/*     */   public List<Entity> getEntitiesAsync() {
/*  83 */     return getEntities(!Util.mc.func_152345_ab());
/*     */   }
/*     */   
/*     */   public List<EntityPlayer> getPlayersAsync() {
/*  87 */     return getPlayers(!Util.mc.func_152345_ab());
/*     */   }
/*     */   
/*     */   public List<Entity> getEntities(boolean async) {
/*  91 */     return async ? this.entities : Util.mc.field_71441_e.field_72996_f;
/*     */   }
/*     */   
/*     */   public List<EntityPlayer> getPlayers(boolean async) {
/*  95 */     return async ? this.players : Util.mc.field_71441_e.field_73010_i;
/*     */   }
/*     */   
/*     */   public Entity getEntity(int id) {
/*  99 */     List<Entity> entities = getEntitiesAsync();
/* 100 */     if (entities != null) {
/* 101 */       return entities.stream()
/* 102 */         .filter(e -> (e != null && e.func_145782_y() == id))
/* 103 */         .findFirst()
/* 104 */         .orElse(null);
/*     */     }
/*     */     
/* 107 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\EntityProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */