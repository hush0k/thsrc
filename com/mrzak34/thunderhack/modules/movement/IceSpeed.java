/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class IceSpeed extends Module {
/*  8 */   private final Setting<Float> speed = register(new Setting("Speed", Float.valueOf(0.4F), Float.valueOf(0.1F), Float.valueOf(1.5F)));
/*    */   
/*    */   public IceSpeed() {
/* 11 */     super("IceSpeed", "+скорость если на льду", Module.Category.MOVEMENT);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 16 */     Blocks.field_150432_aD.field_149765_K = ((Float)this.speed.getValue()).floatValue();
/* 17 */     Blocks.field_150403_cj.field_149765_K = ((Float)this.speed.getValue()).floatValue();
/* 18 */     Blocks.field_185778_de.field_149765_K = ((Float)this.speed.getValue()).floatValue();
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 22 */     Blocks.field_150432_aD.field_149765_K = 0.98F;
/* 23 */     Blocks.field_150403_cj.field_149765_K = 0.98F;
/* 24 */     Blocks.field_185778_de.field_149765_K = 0.98F;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\IceSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */