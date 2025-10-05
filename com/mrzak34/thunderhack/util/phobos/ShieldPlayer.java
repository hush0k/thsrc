/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ShieldPlayer
/*    */   extends EntityPlayer {
/*    */   public ShieldPlayer(World worldIn) {
/* 11 */     super(worldIn, new GameProfile(UUID.randomUUID(), "Shield"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_175149_v() {
/* 16 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_184812_l_() {
/* 21 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ShieldPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */