package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({SPacketEntityVelocity.class})
public interface ISPacketEntityVelocity {
  @Accessor("motionX")
  int getMotionX();
  
  @Accessor("motionY")
  int getMotionY();
  
  @Accessor("motionZ")
  int getMotionZ();
  
  @Accessor("motionX")
  void setMotionX(int paramInt);
  
  @Accessor("motionY")
  void setMotionY(int paramInt);
  
  @Accessor("motionZ")
  void setMotionZ(int paramInt);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\ISPacketEntityVelocity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */