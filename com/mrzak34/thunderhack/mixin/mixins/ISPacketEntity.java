package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.network.play.server.SPacketEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({SPacketEntity.class})
public interface ISPacketEntity {
  @Accessor("entityId")
  int getEntityId();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\ISPacketEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */