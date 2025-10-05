/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.EventSync;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import java.util.Comparator;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketUseEntity;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AutoSheep extends Module {
/* 18 */   public Setting<Boolean> Rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
/*    */   
/*    */   public AutoSheep() {
/* 21 */     super("AutoSheep", "стрегет овец", Module.Category.MISC);
/*    */   }
/*    */   
/*    */   public static float[] calcAngle(Vec3d from, Vec3d to) {
/* 25 */     double difX = to.field_72450_a - from.field_72450_a;
/* 26 */     double difY = (to.field_72448_b - from.field_72448_b) * -1.0D;
/* 27 */     double difZ = to.field_72449_c - from.field_72449_c;
/* 28 */     double dist = MathHelper.func_76133_a(difX * difX + difZ * difZ);
/* 29 */     return new float[] { (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difY, dist))) };
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onUpdateWalkingPlayerPre(EventSync p_Event) {
/* 34 */     if (!(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemShears)) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     EntitySheep l_Sheep = mc.field_71441_e.field_72996_f.stream().filter(p_Entity -> IsValidSheep(p_Entity)).map(p_Entity -> (EntitySheep)p_Entity).min(Comparator.comparing(p_Entity -> Float.valueOf(mc.field_71439_g.func_70032_d((Entity)p_Entity)))).orElse(null);
/*    */     
/* 43 */     if (l_Sheep != null) {
/* 44 */       if (((Boolean)this.Rotate.getValue()).booleanValue()) {
/* 45 */         float[] angle = calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), l_Sheep.func_174824_e(mc.func_184121_ak()));
/* 46 */         mc.field_71439_g.field_70177_z = angle[0];
/* 47 */         mc.field_71439_g.field_70125_A = angle[1];
/*    */       } 
/* 49 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity((Entity)l_Sheep, EnumHand.MAIN_HAND));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean IsValidSheep(Entity p_Entity) {
/* 55 */     if (!(p_Entity instanceof EntitySheep))
/* 56 */       return false; 
/* 57 */     if (p_Entity.func_70032_d((Entity)mc.field_71439_g) > 4.0F)
/* 58 */       return false; 
/* 59 */     EntitySheep l_Sheep = (EntitySheep)p_Entity;
/* 60 */     return l_Sheep.isShearable(mc.field_71439_g.func_184614_ca(), (IBlockAccess)mc.field_71441_e, p_Entity.func_180425_c());
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\AutoSheep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */