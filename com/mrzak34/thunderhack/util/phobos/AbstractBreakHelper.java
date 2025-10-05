/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*    */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*    */ import com.mrzak34.thunderhack.util.EntityUtil;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public abstract class AbstractBreakHelper<T extends CrystalData>
/*    */   implements IBreakHelper<T> {
/*    */   protected final AutoCrystal module;
/*    */   
/*    */   public AbstractBreakHelper(AutoCrystal module) {
/* 18 */     this.module = module;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract T newCrystalData(Entity paramEntity);
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract boolean isValid(Entity paramEntity, T paramT);
/*    */ 
/*    */   
/*    */   protected abstract boolean calcSelf(BreakData<T> paramBreakData, Entity paramEntity, T paramT);
/*    */ 
/*    */   
/*    */   protected abstract void calcCrystal(BreakData<T> paramBreakData, T paramT, Entity paramEntity, List<EntityPlayer> paramList);
/*    */ 
/*    */   
/*    */   public BreakData<T> getData(Collection<T> dataSet, List<Entity> entities, List<EntityPlayer> players, List<EntityPlayer> friends) {
/* 37 */     BreakData<T> data = newData(dataSet);
/* 38 */     for (Entity crystal : entities) {
/* 39 */       if (!(crystal instanceof net.minecraft.entity.item.EntityEnderCrystal) || (EntityUtil.isDead(crystal) && (!((Boolean)this.module.countDeadCrystals.getValue()).booleanValue() || (((Boolean)this.module.countDeathTime.getValue()).booleanValue() && ((EntityUtil.isDead(crystal) && Thunderhack.setDeadManager.passedDeathTime(crystal, this.module.getDeathTime())) || (((IEntity)crystal).isPseudoDeadT() && ((IEntity)crystal).getPseudoTimeT().passedMs(this.module.getDeathTime()))))))) {
/*    */         continue;
/*    */       }
/* 42 */       T crystalData = newCrystalData(crystal);
/* 43 */       if (calcSelf(data, crystal, crystalData)) {
/*    */         continue;
/*    */       }
/*    */       
/* 47 */       if (!isValid(crystal, crystalData) || (this.module.shouldCalcFuckinBitch(AutoCrystal.AntiFriendPop.Break) && checkFriendPop(crystal, friends))) {
/*    */         continue;
/*    */       }
/*    */       
/* 51 */       calcCrystal(data, crystalData, crystal, players);
/*    */     } 
/*    */     
/* 54 */     return data;
/*    */   }
/*    */   
/*    */   protected boolean checkFriendPop(Entity entity, List<EntityPlayer> friends) {
/* 58 */     for (EntityPlayer friend : friends) {
/* 59 */       float fDamage = this.module.damageHelper.getDamage(entity, (EntityLivingBase)friend);
/* 60 */       if (fDamage > EntityUtil.getHealth((Entity)friend) - 1.0F) {
/* 61 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 65 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\AbstractBreakHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */