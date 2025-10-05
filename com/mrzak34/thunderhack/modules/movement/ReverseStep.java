/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.EventSync;
/*    */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class ReverseStep extends Module {
/* 14 */   public Setting<Float> timer = register(new Setting("Timer", Float.valueOf(3.0F), Float.valueOf(1.0F), Float.valueOf(10.0F)));
/* 15 */   public Setting<Boolean> anyblock = register(new Setting("AnyBlock", Boolean.valueOf(false)));
/*    */   private boolean Field292 = true;
/*    */   private boolean Field293 = false;
/* 18 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.Motion));
/*    */   
/*    */   public ReverseStep() {
/* 21 */     super("ReverseStep", "ReverseStep", Module.Category.MOVEMENT);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onEntitySync(EventSync eventPlayerUpdateWalking) {
/* 26 */     if (((PacketFly)Thunderhack.moduleManager.getModuleByClass(PacketFly.class)).isEnabled() || ((PacketFly2)Thunderhack.moduleManager.getModuleByClass(PacketFly2.class)).isEnabled()) {
/*    */       return;
/*    */     }
/* 29 */     IBlockState iBlockState = mc.field_71441_e.func_180495_p((new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_177979_c(2));
/* 30 */     IBlockState iBlockState2 = mc.field_71441_e.func_180495_p((new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_177979_c(3));
/* 31 */     IBlockState iBlockState3 = mc.field_71441_e.func_180495_p((new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_177979_c(4));
/* 32 */     if ((iBlockState.func_177230_c() == Blocks.field_150357_h || iBlockState.func_177230_c() == Blocks.field_150343_Z || ((Boolean)this.anyblock.getValue()).booleanValue()) && !mc.field_71439_g.func_180799_ab() && !mc.field_71439_g.func_70090_H() && !((IEntity)mc.field_71439_g).isInWeb() && !mc.field_71439_g.func_184613_cA() && !mc.field_71439_g.field_71075_bZ.field_75100_b) {
/* 33 */       if (mc.field_71439_g.field_70122_E && this.mode.getValue() == Mode.Motion) {
/* 34 */         mc.field_71439_g.field_70181_x--;
/*    */       }
/* 36 */       if (this.mode.getValue() == Mode.Timer && this.Field293 && !mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70181_x < -0.1D && !this.Field292 && !mc.field_71439_g.func_180799_ab() && !mc.field_71439_g.func_70090_H() && !((IEntity)mc.field_71439_g).isInWeb() && !mc.field_71439_g.func_184613_cA() && !mc.field_71439_g.field_71075_bZ.field_75100_b) {
/* 37 */         Thunderhack.TICK_TIMER = ((Float)this.timer.getValue()).floatValue();
/* 38 */         this.Field292 = true;
/*    */       } 
/* 40 */     } else if ((iBlockState2.func_177230_c() == Blocks.field_150357_h || iBlockState2.func_177230_c() == Blocks.field_150343_Z || ((Boolean)this.anyblock.getValue()).booleanValue()) && !mc.field_71439_g.func_180799_ab() && !mc.field_71439_g.func_70090_H() && !((IEntity)mc.field_71439_g).isInWeb() && !mc.field_71439_g.func_184613_cA() && !mc.field_71439_g.field_71075_bZ.field_75100_b) {
/* 41 */       if (mc.field_71439_g.field_70122_E && this.mode.getValue() == Mode.Motion) {
/* 42 */         mc.field_71439_g.field_70181_x--;
/*    */       }
/* 44 */       if (this.mode.getValue() == Mode.Timer && this.Field293 && !mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70181_x < -0.1D && !this.Field292 && !mc.field_71439_g.func_180799_ab() && !mc.field_71439_g.func_70090_H() && !((IEntity)mc.field_71439_g).isInWeb() && !mc.field_71439_g.func_184613_cA() && !mc.field_71439_g.field_71075_bZ.field_75100_b) {
/* 45 */         Thunderhack.TICK_TIMER = ((Float)this.timer.getValue()).floatValue();
/* 46 */         this.Field292 = true;
/*    */       } 
/* 48 */     } else if ((iBlockState3.func_177230_c() == Blocks.field_150357_h || iBlockState3.func_177230_c() == Blocks.field_150343_Z || ((Boolean)this.anyblock.getValue()).booleanValue()) && !mc.field_71439_g.func_180799_ab() && !mc.field_71439_g.func_70090_H() && !((IEntity)mc.field_71439_g).isInWeb() && !mc.field_71439_g.func_184613_cA() && !mc.field_71439_g.field_71075_bZ.field_75100_b) {
/* 49 */       if (mc.field_71439_g.field_70122_E && this.mode.getValue() == Mode.Motion) {
/* 50 */         mc.field_71439_g.field_70181_x--;
/*    */       }
/* 52 */       if (this.mode.getValue() == Mode.Timer && this.Field293 && !mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70181_x < -0.1D && !this.Field292 && !mc.field_71439_g.func_180799_ab() && !mc.field_71439_g.func_70090_H() && !((IEntity)mc.field_71439_g).isInWeb() && !mc.field_71439_g.func_184613_cA() && !mc.field_71439_g.field_71075_bZ.field_75100_b) {
/* 53 */         Thunderhack.TICK_TIMER = ((Float)this.timer.getValue()).floatValue();
/* 54 */         this.Field292 = true;
/*    */       } 
/*    */     } 
/* 57 */     if (this.Field292 && (mc.field_71439_g.field_70122_E || mc.field_71439_g.func_180799_ab() || mc.field_71439_g.func_70090_H() || ((IEntity)mc.field_71439_g).isInWeb() || mc.field_71439_g.func_184613_cA() || mc.field_71439_g.field_71075_bZ.field_75100_b)) {
/* 58 */       this.Field292 = false;
/* 59 */       Thunderhack.TICK_TIMER = 1.0F;
/*    */     } 
/* 61 */     this.Field293 = mc.field_71439_g.field_70122_E;
/*    */   }
/*    */   
/*    */   public enum Mode
/*    */   {
/* 66 */     Timer, Motion;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\ReverseStep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */