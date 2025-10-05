package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({SPacketPlayerPosLook.class})
public interface ISPacketPlayerPosLook {
  @Accessor("yaw")
  void setYaw(float paramFloat);
  
  @Accessor("pitch")
  void setPitch(float paramFloat);
  
  @Accessor("teleportId")
  int getTeleportId();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\ISPacketPlayerPosLook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */