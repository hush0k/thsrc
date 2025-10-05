/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.EventSync;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.SilentRotationUtil;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ public class FunnyClicker
/*    */   extends Module
/*    */ {
/* 25 */   public Setting<Integer> chanceval = register(new Setting("Chance", Integer.valueOf(100), Integer.valueOf(1), Integer.valueOf(1000)));
/* 26 */   BlockPos bp = null;
/* 27 */   Timer timer = new Timer();
/*    */   
/*    */   public FunnyClicker() {
/* 30 */     super("FunnyClicker", "FunnyClicker", Module.Category.FUNNYGAME);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive e) {
/* 35 */     if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketOpenWindow) {
/* 36 */       e.setCanceled(true);
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onUpdateWalkingPlayerEvent(EventSync event) {
/* 42 */     for (TileEntity tileEntity : mc.field_71441_e.field_147482_g) {
/* 43 */       if (mc.field_71439_g.func_174818_b(new BlockPos((Vec3i)tileEntity.func_174877_v())) > 4.0D || 
/* 44 */         !(tileEntity instanceof net.minecraft.tileentity.TileEntityChest) || 
/* 45 */         mc.field_71439_g.func_70011_f(tileEntity.func_174877_v().func_177958_n(), tileEntity.func_174877_v().func_177956_o(), tileEntity.func_174877_v().func_177952_p()) > 8.0D) {
/*    */         continue;
/*    */       }
/* 48 */       if (this.timer.passedMs(((Integer)this.chanceval.getValue()).intValue())) {
/* 49 */         this.bp = tileEntity.func_174877_v();
/* 50 */         SilentRotationUtil.lookAtBlock(tileEntity.func_174877_v());
/* 51 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(tileEntity.func_174877_v(), EnumFacing.UP, EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
/* 52 */         this.timer.reset();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender3D(Render3DEvent e) {
/* 60 */     if (this.bp != null)
/*    */       try {
/* 62 */         RenderUtil.drawBlockOutline(this.bp, new Color(718982), 3.0F, true, 0);
/* 63 */       } catch (Exception exception) {} 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\FunnyClicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */