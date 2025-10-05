/*    */ package com.mrzak34.thunderhack.util.rotations;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CastHelper
/*    */ {
/* 18 */   private final List<EntityType> casts = new ArrayList<>();
/*    */   
/*    */   public static EntityType isInstanceof(Entity entity, EntityType... types) {
/* 21 */     for (EntityType type : types) {
/* 22 */       if (entity == (Minecraft.func_71410_x()).field_71439_g && 
/* 23 */         type == EntityType.SELF) {
/* 24 */         return type;
/*    */       }
/* 26 */       if (type == EntityType.VILLAGERS && entity instanceof net.minecraft.entity.passive.EntityVillager) {
/* 27 */         return type;
/*    */       }
/* 29 */       if (type == EntityType.FRIENDS && entity instanceof net.minecraft.entity.player.EntityPlayer && 
/* 30 */         Thunderhack.friendManager.isFriend(entity.func_70005_c_())) {
/* 31 */         return type;
/*    */       }
/*    */       
/* 34 */       if (type == EntityType.PLAYERS && entity instanceof net.minecraft.entity.player.EntityPlayer && entity != 
/* 35 */         (Minecraft.func_71410_x()).field_71439_g && !Thunderhack.friendManager.isFriend(entity.func_70005_c_())) {
/* 36 */         return type;
/*    */       }
/* 38 */       if (type == EntityType.MOBS && entity instanceof net.minecraft.entity.monster.EntityMob) {
/* 39 */         return type;
/*    */       }
/* 41 */       if (type == EntityType.ANIMALS && (entity instanceof net.minecraft.entity.passive.EntityAnimal || entity instanceof net.minecraft.entity.monster.EntityGolem)) {
/* 42 */         return type;
/*    */       }
/*    */     } 
/* 45 */     return null;
/*    */   }
/*    */   
/*    */   public CastHelper apply(EntityType type) {
/* 49 */     this.casts.add(type);
/* 50 */     return this;
/*    */   }
/*    */   
/*    */   public EntityType[] build() {
/* 54 */     return this.casts.<EntityType>toArray(new EntityType[0]);
/*    */   }
/*    */   
/*    */   public enum EntityType {
/* 58 */     PLAYERS, MOBS, ANIMALS, VILLAGERS, FRIENDS, SELF;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\rotations\CastHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */