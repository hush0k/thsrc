/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityPlayerNoInterp
/*    */   extends EntityOtherPlayerMP implements IEntityNoInterp {
/*    */   public EntityPlayerNoInterp(World worldIn) {
/* 11 */     this(worldIn, Util.mc.field_71439_g.func_146103_bH());
/*    */   }
/*    */   
/*    */   public EntityPlayerNoInterp(World worldIn, GameProfile gameProfileIn) {
/* 15 */     super(worldIn, gameProfileIn);
/*    */   }
/*    */   
/*    */   public void setNoInterpX(double x) {}
/*    */   
/*    */   public void setNoInterpY(double y) {}
/*    */   
/*    */   public void setNoInterpZ(double z) {}
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\EntityPlayerNoInterp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */