package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({Minecraft.class})
public interface AccessorMinecraft {
  @Accessor("leftClickCounter")
  void setLeftClickCounter(int paramInt);
  
  @Invoker("sendClickBlockToController")
  void invokeSendClickBlockToController(boolean paramBoolean);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\AccessorMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */