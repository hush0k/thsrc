/*     */ package com.mrzak34.thunderhack.events;
/*     */ 
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderItemEvent
/*     */   extends Event
/*     */ {
/*     */   float mainX;
/*     */   float mainY;
/*     */   float mainZ;
/*     */   float offX;
/*     */   float offY;
/*     */   float offZ;
/*     */   float mainRotX;
/*     */   float mainRotY;
/*     */   float mainRotZ;
/*     */   
/*     */   public RenderItemEvent(float mainX, float mainY, float mainZ, float offX, float offY, float offZ, float mainRotX, float mainRotY, float mainRotZ, float offRotX, float offRotY, float offRotZ, float mainHandScaleX, float mainHandScaleY, float mainHandScaleZ, float offHandScaleX, float offHandScaleY, float offHandScaleZ) {
/*  21 */     this.mainX = mainX;
/*  22 */     this.mainY = mainY;
/*  23 */     this.mainZ = mainZ;
/*  24 */     this.offX = offX;
/*  25 */     this.offY = offY;
/*  26 */     this.offZ = offZ;
/*  27 */     this.mainRotX = mainRotX;
/*  28 */     this.mainRotY = mainRotY;
/*  29 */     this.mainRotZ = mainRotZ;
/*  30 */     this.offRotX = offRotX;
/*  31 */     this.offRotY = offRotY;
/*  32 */     this.offRotZ = offRotZ;
/*  33 */     this.mainHandScaleX = mainHandScaleX;
/*  34 */     this.mainHandScaleY = mainHandScaleY;
/*  35 */     this.mainHandScaleZ = mainHandScaleZ;
/*     */     
/*  37 */     this.offHandScaleX = offHandScaleX;
/*  38 */     this.offHandScaleY = offHandScaleY;
/*  39 */     this.offHandScaleZ = offHandScaleZ;
/*     */   }
/*     */   float offRotX; float offRotY; float offRotZ; float mainHandScaleX; float mainHandScaleY; float mainHandScaleZ; float offHandScaleX; float offHandScaleY; float offHandScaleZ;
/*     */   
/*     */   public float getMainX() {
/*  44 */     return this.mainX;
/*     */   }
/*     */   
/*     */   public void setMainX(float v) {
/*  48 */     this.mainX = v;
/*     */   }
/*     */   
/*     */   public float getMainY() {
/*  52 */     return this.mainY;
/*     */   }
/*     */   
/*     */   public void setMainY(float v) {
/*  56 */     this.mainY = v;
/*     */   }
/*     */   
/*     */   public float getMainZ() {
/*  60 */     return this.mainZ;
/*     */   }
/*     */   
/*     */   public void setMainZ(float v) {
/*  64 */     this.mainZ = v;
/*     */   }
/*     */   
/*     */   public float getOffX() {
/*  68 */     return this.offX;
/*     */   }
/*     */   
/*     */   public void setOffX(float v) {
/*  72 */     this.offX = v;
/*     */   }
/*     */   
/*     */   public float getOffY() {
/*  76 */     return this.offY;
/*     */   }
/*     */   
/*     */   public void setOffY(float v) {
/*  80 */     this.offY = v;
/*     */   }
/*     */   
/*     */   public float getOffZ() {
/*  84 */     return this.offZ;
/*     */   }
/*     */   
/*     */   public void setOffZ(float v) {
/*  88 */     this.offZ = v;
/*     */   }
/*     */   
/*     */   public float getMainRotX() {
/*  92 */     return this.mainRotX;
/*     */   }
/*     */   
/*     */   public void setMainRotX(float v) {
/*  96 */     this.mainRotX = v;
/*     */   }
/*     */   
/*     */   public float getMainRotY() {
/* 100 */     return this.mainRotY;
/*     */   }
/*     */   
/*     */   public void setMainRotY(float v) {
/* 104 */     this.mainRotY = v;
/*     */   }
/*     */   
/*     */   public float getMainRotZ() {
/* 108 */     return this.mainRotZ;
/*     */   }
/*     */   
/*     */   public void setMainRotZ(float v) {
/* 112 */     this.mainRotZ = v;
/*     */   }
/*     */   
/*     */   public float getOffRotX() {
/* 116 */     return this.offRotX;
/*     */   }
/*     */   
/*     */   public void setOffRotX(float v) {
/* 120 */     this.offRotX = v;
/*     */   }
/*     */   
/*     */   public float getOffRotY() {
/* 124 */     return this.offRotY;
/*     */   }
/*     */   
/*     */   public void setOffRotY(float v) {
/* 128 */     this.offRotY = v;
/*     */   }
/*     */   
/*     */   public float getOffRotZ() {
/* 132 */     return this.offRotZ;
/*     */   }
/*     */   
/*     */   public void setOffRotZ(float v) {
/* 136 */     this.offRotZ = v;
/*     */   }
/*     */   
/*     */   public float getMainHandScaleX() {
/* 140 */     return this.mainHandScaleX;
/*     */   }
/*     */   
/*     */   public void setMainHandScaleX(float v) {
/* 144 */     this.mainHandScaleX = v;
/*     */   }
/*     */   
/*     */   public float getMainHandScaleY() {
/* 148 */     return this.mainHandScaleY;
/*     */   }
/*     */   
/*     */   public void setMainHandScaleY(float v) {
/* 152 */     this.mainHandScaleY = v;
/*     */   }
/*     */   
/*     */   public float getMainHandScaleZ() {
/* 156 */     return this.mainHandScaleZ;
/*     */   }
/*     */   
/*     */   public void setMainHandScaleZ(float v) {
/* 160 */     this.mainHandScaleZ = v;
/*     */   }
/*     */   
/*     */   public float getOffHandScaleX() {
/* 164 */     return this.offHandScaleX;
/*     */   }
/*     */   
/*     */   public void setOffHandScaleX(float v) {
/* 168 */     this.offHandScaleX = v;
/*     */   }
/*     */   
/*     */   public float getOffHandScaleY() {
/* 172 */     return this.offHandScaleY;
/*     */   }
/*     */   
/*     */   public void setOffHandScaleY(float v) {
/* 176 */     this.offHandScaleY = v;
/*     */   }
/*     */   
/*     */   public float getOffHandScaleZ() {
/* 180 */     return this.offHandScaleZ;
/*     */   }
/*     */   
/*     */   public void setOffHandScaleZ(float v) {
/* 184 */     this.offHandScaleZ = v;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\RenderItemEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */