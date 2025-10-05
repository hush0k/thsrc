/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ 
/*    */ public class NoFall extends Module {
/*  8 */   public Setting<rotmod> mod = register(new Setting("Mode", rotmod.Rubberband));
/*    */ 
/*    */   
/*    */   public NoFall() {
/* 12 */     super("NoFall", "рубербендит если ты-упал", Module.Category.MOVEMENT);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 17 */     if (fullNullCheck())
/* 18 */       return;  if (mc.field_71439_g.field_70143_R > 3.0F && !mc.field_71439_g.func_70093_af() && this.mod.getValue() == rotmod.Rubberband) {
/* 19 */       mc.field_71439_g.field_70181_x -= 0.1D;
/* 20 */       mc.field_71439_g.field_70122_E = true;
/* 21 */       mc.field_71439_g.field_71075_bZ.field_75102_a = true;
/*    */     } 
/* 23 */     if (this.mod.getValue() == rotmod.Default && 
/* 24 */       mc.field_71439_g.field_70143_R > 2.5D) {
/* 25 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer(true));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public enum rotmod
/*    */   {
/* 32 */     Rubberband, Default;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\NoFall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */