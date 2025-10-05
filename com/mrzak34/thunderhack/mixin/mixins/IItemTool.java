package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ItemTool.class})
public interface IItemTool {
  @Accessor("attackDamage")
  float getAttackDamage();
  
  @Accessor("toolMaterial")
  Item.ToolMaterial getToolMaterial();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IItemTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */