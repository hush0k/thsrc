package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({EntityPlayerSP.class})
public interface IEntityPlayerSP {
  @Accessor("serverSneakState")
  boolean getServerSneakState();
  
  @Accessor("serverSneakState")
  void setServerSneakState(boolean paramBoolean);
  
  @Accessor("serverSprintState")
  boolean getServerSprintState();
  
  @Accessor("serverSprintState")
  void setServerSprintState(boolean paramBoolean);
  
  @Accessor("wasFallFlying")
  boolean wasFallFlying();
  
  @Accessor("prevOnGround")
  boolean getPrevOnGround();
  
  @Accessor("prevOnGround")
  void setPrevOnGround(boolean paramBoolean);
  
  @Accessor("autoJumpEnabled")
  void setAutoJumpEnabled(boolean paramBoolean);
  
  @Accessor("lastReportedPosX")
  double getLastReportedPosX();
  
  @Accessor("lastReportedPosX")
  void setLastReportedPosX(double paramDouble);
  
  @Accessor("lastReportedPosY")
  double getLastReportedPosY();
  
  @Accessor("lastReportedPosY")
  void setLastReportedPosY(double paramDouble);
  
  @Accessor("lastReportedPosZ")
  double getLastReportedPosZ();
  
  @Accessor("lastReportedPosZ")
  void setLastReportedPosZ(double paramDouble);
  
  @Accessor("lastReportedYaw")
  float getLastReportedYaw();
  
  @Accessor("lastReportedYaw")
  void setLastReportedYaw(float paramFloat);
  
  @Accessor("lastReportedPitch")
  float getLastReportedPitch();
  
  @Accessor("lastReportedPitch")
  void setLastReportedPitch(float paramFloat);
  
  @Accessor("positionUpdateTicks")
  int getPositionUpdateTicks();
  
  @Accessor("positionUpdateTicks")
  void setPositionUpdateTicks(int paramInt);
  
  @Invoker("onUpdateWalkingPlayer")
  void invokeOnUpdateWalkingPlayer();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IEntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */