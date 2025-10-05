/*     */ package com.mrzak34.thunderhack.util.dism;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ 
/*     */ public class ModelGib
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer skeleLeg;
/*     */   public ModelRenderer skeleArm;
/*     */   public ModelRenderer creeperFoot;
/*     */   public ModelRenderer head64;
/*     */   public ModelRenderer body64;
/*     */   public ModelRenderer leg64;
/*     */   public ModelRenderer arm64;
/*     */   public ModelRenderer head32;
/*     */   public ModelRenderer body32;
/*     */   
/*     */   public ModelGib() {
/*  22 */     this.field_78090_t = 64;
/*  23 */     this.field_78089_u = 64;
/*  24 */     this.leg64 = new ModelRenderer(this, 0, 16);
/*  25 */     this.leg64.func_78787_b(64, 64);
/*  26 */     this.leg64.func_78789_a(-2.0F, -6.0F, -2.0F, 4, 12, 4);
/*  27 */     this.leg64.func_78793_a(0.0F, 24.0F, 0.0F);
/*  28 */     this.leg64.field_78795_f = 0.0F;
/*  29 */     this.leg64.field_78796_g = 0.0F;
/*  30 */     this.leg64.field_78808_h = 0.0F;
/*  31 */     this.leg64.field_78809_i = false;
/*     */     
/*  33 */     this.arm64 = new ModelRenderer(this, 40, 16);
/*  34 */     this.arm64.func_78787_b(64, 64);
/*  35 */     this.arm64.func_78789_a(-2.0F, -6.0F, -2.0F, 4, 12, 4);
/*  36 */     this.arm64.func_78793_a(0.0F, 22.0F, 0.0F);
/*  37 */     this.arm64.field_78795_f = 0.0F;
/*  38 */     this.arm64.field_78796_g = 0.0F;
/*  39 */     this.arm64.field_78808_h = 0.0F;
/*  40 */     this.arm64.field_78809_i = false;
/*     */     
/*  42 */     this.skeleArm = new ModelRenderer(this, 40, 16);
/*  43 */     this.skeleArm.func_78787_b(64, 32);
/*  44 */     this.skeleArm.func_78789_a(-1.0F, -6.0F, -1.0F, 2, 12, 2);
/*  45 */     this.skeleArm.func_78793_a(0.0F, 24.0F, 0.0F);
/*  46 */     this.skeleArm.field_78795_f = 0.0F;
/*  47 */     this.skeleArm.field_78796_g = 0.0F;
/*  48 */     this.skeleArm.field_78808_h = 0.0F;
/*  49 */     this.skeleArm.field_78809_i = false;
/*     */     
/*  51 */     this.skeleLeg = new ModelRenderer(this, 0, 16);
/*  52 */     this.skeleLeg.func_78787_b(64, 32);
/*  53 */     this.skeleLeg.func_78789_a(-1.0F, -6.0F, -1.0F, 2, 12, 2);
/*  54 */     this.skeleLeg.func_78793_a(0.0F, 24.0F, 0.0F);
/*  55 */     this.skeleLeg.field_78795_f = 0.0F;
/*  56 */     this.skeleLeg.field_78796_g = 0.0F;
/*  57 */     this.skeleLeg.field_78808_h = 0.0F;
/*  58 */     this.skeleLeg.field_78809_i = false;
/*     */     
/*  60 */     this.creeperFoot = new ModelRenderer(this, 0, 16);
/*  61 */     this.creeperFoot.func_78787_b(64, 32);
/*  62 */     this.creeperFoot.func_78789_a(-2.0F, -3.0F, -2.0F, 4, 6, 4);
/*  63 */     this.creeperFoot.func_78793_a(0.0F, 24.0F, 0.0F);
/*  64 */     this.creeperFoot.field_78795_f = 0.0F;
/*  65 */     this.creeperFoot.field_78796_g = 0.0F;
/*  66 */     this.creeperFoot.field_78808_h = 0.0F;
/*  67 */     this.creeperFoot.field_78809_i = false;
/*     */     
/*  69 */     this.head64 = new ModelRenderer(this, 0, 0);
/*  70 */     this.head64.func_78787_b(64, 64);
/*  71 */     this.head64.func_78789_a(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/*  72 */     this.head64.func_78793_a(0.0F, 20.0F, 0.0F);
/*  73 */     this.head64.field_78795_f = 0.0F;
/*  74 */     this.head64.field_78796_g = 0.0F;
/*  75 */     this.head64.field_78808_h = 0.0F;
/*  76 */     this.head64.field_78809_i = false;
/*     */     
/*  78 */     this.head64.func_78784_a(32, 0);
/*  79 */     this.head64.func_78790_a(-4.0F, -4.0F, -4.0F, 8, 8, 8, 1.1F);
/*  80 */     this.head64.func_78793_a(0.0F, 20.0F, 0.0F);
/*  81 */     this.head64.field_78795_f = 0.0F;
/*  82 */     this.head64.field_78796_g = 0.0F;
/*  83 */     this.head64.field_78808_h = 0.0F;
/*  84 */     this.head64.field_78809_i = false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     this.body64 = new ModelRenderer(this, 16, 16);
/* 104 */     this.body64.func_78787_b(64, 64);
/* 105 */     this.body64.func_78789_a(-4.0F, -6.0F, -2.0F, 8, 12, 4);
/* 106 */     this.body64.func_78793_a(0.0F, 22.0F, 0.0F);
/* 107 */     this.body64.field_78795_f = 0.0F;
/* 108 */     this.body64.field_78796_g = 0.0F;
/* 109 */     this.body64.field_78808_h = 0.0F;
/* 110 */     this.body64.field_78809_i = false;
/*     */     
/* 112 */     this.head32 = new ModelRenderer(this, 0, 0);
/* 113 */     this.head32.func_78787_b(64, 32);
/* 114 */     this.head32.func_78789_a(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/* 115 */     this.head32.func_78793_a(0.0F, 20.0F, 0.0F);
/* 116 */     this.head32.field_78795_f = 0.0F;
/* 117 */     this.head32.field_78796_g = 0.0F;
/* 118 */     this.head32.field_78808_h = 0.0F;
/* 119 */     this.head32.field_78809_i = false;
/*     */     
/* 121 */     this.body32 = new ModelRenderer(this, 16, 16);
/* 122 */     this.body32.func_78787_b(64, 32);
/* 123 */     this.body32.func_78789_a(-4.0F, -6.0F, -2.0F, 8, 12, 4);
/* 124 */     this.body32.func_78793_a(0.0F, 22.0F, 0.0F);
/* 125 */     this.body32.field_78795_f = 0.0F;
/* 126 */     this.body32.field_78796_g = 0.0F;
/* 127 */     this.body32.field_78808_h = 0.0F;
/* 128 */     this.body32.field_78809_i = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_78088_a(Entity ent, float f, float f1, float f2, float f3, float f4, float f5) {
/* 133 */     func_78087_a(f, f1, f2, f3, f4, f5, ent);
/*     */     
/* 135 */     if (ent instanceof EntityGib) {
/* 136 */       EntityGib gib = (EntityGib)ent;
/*     */       
/* 138 */       if (gib.type == -1);
/*     */ 
/*     */ 
/*     */       
/* 142 */       if (gib.type == 0) {
/* 143 */         if (gib.parent instanceof net.minecraft.entity.monster.EntityZombie || gib.parent instanceof net.minecraft.entity.player.EntityPlayer) {
/* 144 */           this.head64.func_78785_a(f5);
/*     */         } else {
/* 146 */           this.head32.func_78785_a(f5);
/*     */         } 
/* 148 */       } else if (gib.type == 1 || gib.type == 2) {
/* 149 */         if (gib.parent instanceof net.minecraft.entity.monster.EntityZombie || gib.parent instanceof net.minecraft.entity.player.EntityPlayer) {
/* 150 */           this.arm64.func_78785_a(f5);
/*     */         } else {
/* 152 */           this.skeleArm.func_78785_a(f5);
/*     */         } 
/* 154 */       } else if (gib.type == 3) {
/*     */         
/* 156 */         if (gib.parent instanceof net.minecraft.entity.monster.EntityZombie || gib.parent instanceof net.minecraft.entity.player.EntityPlayer) {
/* 157 */           this.body64.func_78785_a(f5);
/*     */         } else {
/* 159 */           this.body32.func_78785_a(f5);
/*     */         } 
/* 161 */       } else if (gib.type == 4 || gib.type == 5) {
/*     */         
/* 163 */         if (gib.parent instanceof net.minecraft.entity.monster.EntityZombie || gib.parent instanceof net.minecraft.entity.player.EntityPlayer) {
/* 164 */           this.leg64.func_78785_a(f5);
/*     */         } else {
/* 166 */           this.skeleLeg.func_78785_a(f5);
/*     */         } 
/* 168 */       } else if (gib.type >= 6) {
/*     */         
/* 170 */         this.creeperFoot.func_78785_a(f5);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity ent) {
/* 177 */     this.arm64.field_78796_g = f3 / 57.29578F;
/* 178 */     this.arm64.field_78795_f = f4 / 57.29578F;
/*     */     
/* 180 */     this.leg64.field_78796_g = f3 / 57.29578F;
/* 181 */     this.leg64.field_78795_f = f4 / 57.29578F;
/*     */     
/* 183 */     this.skeleArm.field_78796_g = f3 / 57.29578F;
/* 184 */     this.skeleArm.field_78795_f = f4 / 57.29578F;
/*     */     
/* 186 */     this.skeleLeg.field_78796_g = f3 / 57.29578F;
/* 187 */     this.skeleLeg.field_78795_f = f4 / 57.29578F;
/*     */     
/* 189 */     this.creeperFoot.field_78796_g = f3 / 57.29578F;
/* 190 */     this.creeperFoot.field_78795_f = f4 / 57.29578F;
/*     */     
/* 192 */     this.head64.field_78796_g = f3 / 57.29578F;
/* 193 */     this.head64.field_78795_f = f4 / 57.29578F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     this.body64.field_78796_g = f3 / 57.29578F;
/* 199 */     this.body64.field_78795_f = f4 / 57.29578F;
/*     */     
/* 201 */     this.head32.field_78796_g = f3 / 57.29578F;
/* 202 */     this.head32.field_78795_f = f4 / 57.29578F;
/*     */     
/* 204 */     this.body32.field_78796_g = f3 / 57.29578F;
/* 205 */     this.body32.field_78795_f = f4 / 57.29578F;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\dism\ModelGib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */