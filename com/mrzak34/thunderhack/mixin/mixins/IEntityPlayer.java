/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.datasync.DataParameter;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.gen.Accessor;
/*    */ 
/*    */ @Mixin({EntityPlayer.class})
/*    */ public interface IEntityPlayer {
/*    */   @Accessor("ABSORPTION")
/*    */   static DataParameter<Float> getAbsorption() {
/* 13 */     throw new IllegalStateException("ABSORPTION accessor wasn't shadowed.");
/*    */   }
/*    */   
/*    */   @Accessor("gameProfile")
/*    */   void setGameProfile(GameProfile paramGameProfile);
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IEntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */