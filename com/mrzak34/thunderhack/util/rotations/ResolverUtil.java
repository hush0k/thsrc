/*    */ package com.mrzak34.thunderhack.util.rotations;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class ResolverUtil {
/*    */   public static double backUpX;
/*    */   public static double backUpY;
/*    */   public static double backUpZ;
/*    */   public static double serverX;
/*    */   
/*    */   public static void resolve(EntityOtherPlayerMP player) {
/*    */     Vec3d target;
/* 15 */     backUpX = player.field_70165_t;
/* 16 */     backUpY = player.field_70163_u;
/* 17 */     backUpZ = player.field_70161_v;
/* 18 */     Vec3d position = Util.mc.field_71439_g.func_174791_d();
/*    */     
/* 20 */     Vec3d from = new Vec3d(prevServerX, prevServerY, prevServerZ);
/* 21 */     Vec3d to = new Vec3d(serverX, serverY, serverZ);
/* 22 */     if (position.func_72438_d(from) > position.func_72438_d(to)) {
/* 23 */       target = to;
/*    */     } else {
/* 25 */       target = from;
/*    */     } 
/* 27 */     if (prevServerX != 0.0D && prevServerZ != 0.0D && prevServerY != 0.0D && serverY != 0.0D && serverX != 0.0D && serverZ != 0.0D)
/* 28 */       player.func_70107_b(target.field_72450_a, target.field_72448_b, target.field_72449_c); 
/*    */   }
/*    */   public static double serverY; public static double serverZ; public static double prevServerX; public static double prevServerY; public static double prevServerZ;
/*    */   
/*    */   public static void releaseResolver(EntityOtherPlayerMP player) {
/* 33 */     if (backUpY != -999.0D) {
/* 34 */       player.func_70107_b(backUpX, backUpY, backUpZ);
/* 35 */       backUpY = -999.0D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void reset() {
/* 40 */     backUpX = 0.0D;
/* 41 */     backUpY = -999.0D;
/* 42 */     backUpZ = 0.0D;
/* 43 */     serverX = 0.0D;
/* 44 */     serverY = 0.0D;
/* 45 */     serverZ = 0.0D;
/* 46 */     prevServerX = 0.0D;
/* 47 */     prevServerY = 0.0D;
/* 48 */     prevServerZ = 0.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\rotations\ResolverUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */