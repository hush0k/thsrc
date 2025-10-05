/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ 
/*    */ @Cancelable
/*    */ public class EventSchematicaPlaceBlockFull extends EventSchematicaPlaceBlock {
/*    */   public boolean Result = true;
/*    */   public ItemStack ItemStack;
/*    */   
/*    */   public EventSchematicaPlaceBlockFull(BlockPos p_Pos, ItemStack itemStack) {
/* 13 */     super(p_Pos);
/* 14 */     this.ItemStack = itemStack;
/*    */   }
/*    */   
/*    */   public boolean GetResult() {
/* 18 */     return this.Result;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventSchematicaPlaceBlockFull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */