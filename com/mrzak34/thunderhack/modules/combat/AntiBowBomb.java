/*    */ package com.mrzak34.thunderhack.modules.combat;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.EventPostSync;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.IKeyBinding;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.EntityUtil;
/*    */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*    */ import com.mrzak34.thunderhack.util.SilentRotationUtil;
/*    */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemShield;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AntiBowBomb
/*    */   extends Module
/*    */ {
/* 21 */   public final Setting<Boolean> stopa = register(new Setting("StopAura", Boolean.valueOf(true)));
/* 22 */   public Setting<Integer> range = register(new Setting("Range", Integer.valueOf(40), Integer.valueOf(0), Integer.valueOf(60)));
/* 23 */   public Setting<Integer> maxUse = register(new Setting("MaxUse", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(20)));
/*    */   
/*    */   EntityPlayer target;
/*    */   
/*    */   public AntiBowBomb() {
/* 28 */     super("AntiBowBomb", "Ставит щит если-в тебя целится-игрок", Module.Category.COMBAT);
/*    */   }
/*    */   int old; boolean b;
/*    */   
/*    */   public void onDisable() {
/* 33 */     this.b = false;
/* 34 */     this.old = -1;
/* 35 */     this.target = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPlayer getTarget(float range) {
/* 40 */     EntityPlayer currentTarget = null;
/* 41 */     for (int size = mc.field_71441_e.field_73010_i.size(), i = 0; i < size; i++) {
/* 42 */       EntityPlayer player = mc.field_71441_e.field_73010_i.get(i);
/* 43 */       if (!isntValid((Entity)player, range)) {
/* 44 */         if (currentTarget == null) {
/* 45 */           currentTarget = player;
/* 46 */         } else if (mc.field_71439_g.func_70068_e((Entity)player) < mc.field_71439_g.func_70068_e((Entity)currentTarget)) {
/* 47 */           currentTarget = player;
/*    */         } 
/*    */       }
/*    */     } 
/* 51 */     return currentTarget;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void pnPostSync(EventPostSync e) {
/* 56 */     this.target = getTarget(((Integer)this.range.getValue()).intValue());
/*    */     
/* 58 */     if (this.target == null) {
/* 59 */       if (this.b) {
/* 60 */         ((IKeyBinding)mc.field_71474_y.field_74313_G).setPressed(false);
/* 61 */         if (this.old != -1) InventoryUtil.swapToHotbarSlot(this.old, false); 
/* 62 */         this.target = null;
/* 63 */         this.b = false;
/*    */       } 
/*    */     } else {
/* 66 */       this.old = mc.field_71439_g.field_71071_by.field_70461_c;
/* 67 */       int shield = InventoryUtil.findItem(ItemShield.class);
/* 68 */       if (shield == -1) {
/* 69 */         this.target = null;
/*    */         return;
/*    */       } 
/* 72 */       if (Thunderhack.friendManager.isFriend(this.target.func_70005_c_()))
/* 73 */         return;  if (this.target.func_184612_cw() <= ((Integer)this.maxUse.getValue()).intValue())
/*    */         return; 
/* 75 */       if (!(this.target.func_184586_b(EnumHand.MAIN_HAND).func_77973_b() instanceof net.minecraft.item.ItemBow) || this.target.func_184586_b(EnumHand.OFF_HAND).func_77973_b() instanceof net.minecraft.item.ItemBow) {
/*    */         return;
/*    */       }
/*    */       
/* 79 */       if (((Boolean)this.stopa.getValue()).booleanValue() && (
/* 80 */         (TargetStrafe)Thunderhack.moduleManager.getModuleByClass(TargetStrafe.class)).isEnabled()) {
/* 81 */         ((TargetStrafe)Thunderhack.moduleManager.getModuleByClass(TargetStrafe.class)).toggle();
/*    */       }
/*    */       
/* 84 */       InventoryUtil.switchToHotbarSlot(shield, false);
/*    */       
/* 86 */       if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemShield) {
/* 87 */         ((IKeyBinding)mc.field_71474_y.field_74313_G).setPressed(true);
/* 88 */         InventoryUtil.swapToHotbarSlot(shield, false);
/* 89 */         SilentRotationUtil.lookAtEntity((Entity)this.target);
/* 90 */         this.b = true;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isntValid(Entity entity, double range) {
/* 96 */     return (entity == null || EntityUtil.isDead(entity) || entity.equals(mc.field_71439_g) || (entity instanceof EntityPlayer && Thunderhack.friendManager.isFriend(entity.func_70005_c_())) || mc.field_71439_g.func_70068_e(entity) > MathUtil.square(range));
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\AntiBowBomb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */