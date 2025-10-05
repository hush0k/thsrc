/*     */ package com.mrzak34.thunderhack.events;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.entity.RenderEnderCrystal;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ 
/*     */ @Cancelable
/*     */ public class CrystalRenderEvent
/*     */   extends Event
/*     */ {
/*     */   private final RenderEnderCrystal render;
/*     */   private final Entity entity;
/*     */   private final ModelBase model;
/*     */   
/*     */   private CrystalRenderEvent(RenderEnderCrystal render, Entity entity, ModelBase model) {
/*  19 */     this.render = render;
/*  20 */     this.entity = entity;
/*  21 */     this.model = model;
/*     */   }
/*     */   
/*     */   public RenderEnderCrystal getRender() {
/*  25 */     return this.render;
/*     */   }
/*     */   
/*     */   public Entity getEntity() {
/*  29 */     return this.entity;
/*     */   }
/*     */   
/*     */   public ModelBase getModel() {
/*  33 */     return this.model;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Pre
/*     */     extends CrystalRenderEvent
/*     */   {
/*     */     private final float limbSwing;
/*     */     
/*     */     private final float limbSwingAmount;
/*     */     
/*     */     private final float ageInTicks;
/*     */     
/*     */     private final float netHeadYaw;
/*     */     
/*     */     private final float headPitch;
/*     */     
/*     */     private final float scale;
/*     */     
/*     */     public Pre(RenderEnderCrystal render, Entity entity, ModelBase model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  53 */       super(render, entity, model);
/*  54 */       this.limbSwing = limbSwing;
/*  55 */       this.limbSwingAmount = limbSwingAmount;
/*  56 */       this.ageInTicks = ageInTicks;
/*  57 */       this.netHeadYaw = netHeadYaw;
/*  58 */       this.headPitch = headPitch;
/*  59 */       this.scale = scale;
/*     */     }
/*     */     
/*     */     public float getLimbSwing() {
/*  63 */       return this.limbSwing;
/*     */     }
/*     */     
/*     */     public float getLimbSwingAmount() {
/*  67 */       return this.limbSwingAmount;
/*     */     }
/*     */     
/*     */     public float getAgeInTicks() {
/*  71 */       return this.ageInTicks;
/*     */     }
/*     */     
/*     */     public float getNetHeadYaw() {
/*  75 */       return this.netHeadYaw;
/*     */     }
/*     */     
/*     */     public float getHeadPitch() {
/*  79 */       return this.headPitch;
/*     */     }
/*     */     
/*     */     public float getScale() {
/*  83 */       return this.scale;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Post
/*     */     extends CrystalRenderEvent
/*     */   {
/*     */     private final float limbSwing;
/*     */     
/*     */     private final float limbSwingAmount;
/*     */     
/*     */     private final float ageInTicks;
/*     */     
/*     */     private final float netHeadYaw;
/*     */     
/*     */     private final float headPitch;
/*     */     
/*     */     private final float scale;
/*     */     
/*     */     public Post(RenderEnderCrystal render, Entity entity, ModelBase model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 104 */       super(render, entity, model);
/* 105 */       this.limbSwing = limbSwing;
/* 106 */       this.limbSwingAmount = limbSwingAmount;
/* 107 */       this.ageInTicks = ageInTicks;
/* 108 */       this.netHeadYaw = netHeadYaw;
/* 109 */       this.headPitch = headPitch;
/* 110 */       this.scale = scale;
/*     */     }
/*     */     
/*     */     public float getLimbSwing() {
/* 114 */       return this.limbSwing;
/*     */     }
/*     */     
/*     */     public float getLimbSwingAmount() {
/* 118 */       return this.limbSwingAmount;
/*     */     }
/*     */     
/*     */     public float getAgeInTicks() {
/* 122 */       return this.ageInTicks;
/*     */     }
/*     */     
/*     */     public float getNetHeadYaw() {
/* 126 */       return this.netHeadYaw;
/*     */     }
/*     */     
/*     */     public float getHeadPitch() {
/* 130 */       return this.headPitch;
/*     */     }
/*     */     
/*     */     public float getScale() {
/* 134 */       return this.scale;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\CrystalRenderEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */