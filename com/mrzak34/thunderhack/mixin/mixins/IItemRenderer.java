package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ItemRenderer.class})
public interface IItemRenderer {
  @Accessor("equippedProgressMainHand")
  void setEquippedProgressMainHand(float paramFloat);
  
  @Accessor("equippedProgressMainHand")
  float getEquippedProgressMainHand();
  
  @Accessor("itemStackMainHand")
  void setItemStackMainHand(ItemStack paramItemStack);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */