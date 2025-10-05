/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.mixin.ducks.ISPacketSpawnObject;
/*    */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ 
/*    */ @Mixin({SPacketSpawnObject.class})
/*    */ public abstract class MixinSPacketSpawnObject
/*    */   implements ISPacketSpawnObject {
/*    */   @Unique
/*    */   private boolean attacked;
/*    */   
/*    */   public boolean isAttacked() {
/* 15 */     return this.attacked;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAttacked(boolean attacked) {
/* 20 */     this.attacked = attacked;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinSPacketSpawnObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */