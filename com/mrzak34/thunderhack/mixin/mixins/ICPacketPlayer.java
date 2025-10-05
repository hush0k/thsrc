package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({CPacketPlayer.class})
public interface ICPacketPlayer {
  @Accessor("x")
  void setX(double paramDouble);
  
  @Accessor("y")
  void setY(double paramDouble);
  
  @Accessor("z")
  void setZ(double paramDouble);
  
  @Accessor("yaw")
  void setYaw(float paramFloat);
  
  @Accessor("pitch")
  void setPitch(float paramFloat);
  
  @Accessor("onGround")
  void setOnGround(boolean paramBoolean);
  
  @Accessor("moving")
  boolean isMoving();
  
  @Accessor("moving")
  void setMoving(boolean paramBoolean);
  
  @Accessor("rotating")
  boolean isRotating();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\ICPacketPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */