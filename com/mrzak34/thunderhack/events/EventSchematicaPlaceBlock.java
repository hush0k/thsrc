/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class EventSchematicaPlaceBlock extends Event {
/*    */   public BlockPos Pos;
/*    */   
/*    */   public EventSchematicaPlaceBlock(BlockPos p_Pos) {
/* 10 */     this.Pos = p_Pos;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventSchematicaPlaceBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */