/*     */ package com.mrzak34.thunderhack.util;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class SpecialTagCompound
/*     */   extends NBTTagCompound
/*     */ {
/*     */   private boolean empty;
/*     */   private final int true_damage;
/*     */   
/*     */   public SpecialTagCompound(boolean empty, int true_damage) {
/*  20 */     this.empty = empty;
/*  21 */     this.true_damage = true_damage;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpecialTagCompound(NBTTagCompound old, int true_damage) {
/*  26 */     if (old == null) { this.empty = true; }
/*     */     else
/*  28 */     { for (String key : old.func_150296_c()) {
/*  29 */         super.func_74782_a(key, old.func_74781_a(key));
/*     */       } }
/*     */     
/*  32 */     this.true_damage = true_damage;
/*     */   }
/*     */   
/*     */   public int getTrueDamage() {
/*  36 */     return this.true_damage;
/*     */   }
/*     */   
/*     */   public byte func_74732_a() {
/*  40 */     if (this.empty) return 0; 
/*  41 */     return super.func_74732_a();
/*     */   }
/*     */   
/*     */   public NBTTagCompound func_74737_b() {
/*  45 */     NBTTagCompound copy = new SpecialTagCompound(this.empty, this.true_damage);
/*     */     
/*  47 */     for (String s : func_150296_c()) {
/*  48 */       ((SpecialTagCompound)copy).setTagLegacy(s, func_74781_a(s).func_74737_b());
/*     */     }
/*     */     
/*  51 */     return copy;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_74782_a(String key, NBTBase value) {
/*  68 */     this.empty = false;
/*  69 */     super.func_74782_a(key, value);
/*     */   }
/*     */   
/*     */   public void setTagLegacy(String key, NBTBase value) {
/*  73 */     super.func_74782_a(key, value);
/*     */   }
/*     */   
/*     */   public void func_74768_a(String key, int value) {
/*  77 */     this.empty = false;
/*  78 */     super.func_74768_a(key, value);
/*     */   }
/*     */   
/*     */   public void func_74774_a(String key, byte value) {
/*  82 */     this.empty = false;
/*  83 */     super.func_74774_a(key, value);
/*     */   }
/*     */   
/*     */   public void func_74777_a(String key, short value) {
/*  87 */     this.empty = false;
/*  88 */     super.func_74777_a(key, value);
/*     */   }
/*     */   
/*     */   public void func_74772_a(String key, long value) {
/*  92 */     this.empty = false;
/*  93 */     super.func_74772_a(key, value);
/*     */   }
/*     */   
/*     */   public void func_186854_a(String key, UUID value) {
/*  97 */     this.empty = false;
/*  98 */     super.func_186854_a(key, value);
/*     */   }
/*     */   
/*     */   public void func_74776_a(String key, float value) {
/* 102 */     this.empty = false;
/* 103 */     super.func_74776_a(key, value);
/*     */   }
/*     */   
/*     */   public void func_74780_a(String key, double value) {
/* 107 */     this.empty = false;
/* 108 */     super.func_74780_a(key, value);
/*     */   }
/*     */   
/*     */   public void func_74778_a(String key, String value) {
/* 112 */     this.empty = false;
/* 113 */     super.func_74778_a(key, value);
/*     */   }
/*     */   
/*     */   public void func_74773_a(String key, byte[] value) {
/* 117 */     this.empty = false;
/* 118 */     super.func_74773_a(key, value);
/*     */   }
/*     */   
/*     */   public void func_74783_a(String key, int[] value) {
/* 122 */     this.empty = false;
/* 123 */     super.func_74783_a(key, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\SpecialTagCompound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */