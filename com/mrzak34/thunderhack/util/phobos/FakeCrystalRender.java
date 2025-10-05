/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ 
/*    */ public class FakeCrystalRender
/*    */ {
/* 14 */   private final List<EntityEnderCrystal> crystals = new ArrayList<>();
/*    */   private final Setting<Integer> simulate;
/*    */   
/*    */   public FakeCrystalRender(Setting<Integer> simulate) {
/* 18 */     this.simulate = simulate;
/*    */   }
/*    */   
/*    */   public void addFakeCrystal(EntityEnderCrystal crystal) {
/* 22 */     crystal.func_184517_a(false);
/* 23 */     Util.mc.func_152344_a(() -> {
/*    */           if (Util.mc.field_71441_e != null) {
/*    */             Iterator<EntityEnderCrystal> iterator = Util.mc.field_71441_e.func_72872_a(EntityEnderCrystal.class, crystal.func_174813_aQ()).iterator();
/*    */             if (iterator.hasNext()) {
/*    */               EntityEnderCrystal entity = iterator.next();
/*    */               crystal.field_70261_a = entity.field_70261_a;
/*    */             } 
/*    */           } 
/*    */           this.crystals.add(crystal);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onSpawn(EntityEnderCrystal crystal) {
/* 39 */     Iterator<EntityEnderCrystal> itr = this.crystals.iterator();
/* 40 */     while (itr.hasNext()) {
/* 41 */       EntityEnderCrystal fake = itr.next();
/* 42 */       if (fake.func_174813_aQ()
/* 43 */         .func_72326_a(crystal.func_174813_aQ())) {
/* 44 */         crystal.field_70261_a = fake.field_70261_a;
/* 45 */         itr.remove();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void tick() {
/* 51 */     if (((Integer)this.simulate.getValue()).intValue() == 0) {
/* 52 */       this.crystals.clear();
/*    */       
/*    */       return;
/*    */     } 
/* 56 */     Iterator<EntityEnderCrystal> itr = this.crystals.iterator();
/* 57 */     while (itr.hasNext()) {
/* 58 */       EntityEnderCrystal crystal = itr.next();
/* 59 */       crystal.func_70071_h_();
/* 60 */       if (++crystal.field_70173_aa >= ((Integer)this.simulate.getValue()).intValue()) {
/* 61 */         crystal.func_70106_y();
/* 62 */         itr.remove();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void render(float partialTicks) {
/* 68 */     RenderManager manager = Util.mc.func_175598_ae();
/* 69 */     for (EntityEnderCrystal crystal : this.crystals) {
/* 70 */       manager.func_188388_a((Entity)crystal, partialTicks, false);
/*    */     }
/*    */   }
/*    */   
/*    */   public void clear() {
/* 75 */     this.crystals.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\FakeCrystalRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */