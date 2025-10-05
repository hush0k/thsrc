/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*    */ import java.util.Set;
/*    */ import java.util.TreeSet;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class AntiTotemData
/*    */   extends PositionData {
/* 10 */   private final Set<PositionData> corresponding = new TreeSet<>();
/*    */   
/*    */   public AntiTotemData(PositionData data, AutoCrystal module) {
/* 13 */     super(data.getPos(), data.getMaxLength(), module, data.getAntiTotems());
/*    */   }
/*    */   
/*    */   public void addCorrespondingData(PositionData data) {
/* 17 */     this.corresponding.add(data);
/*    */   }
/*    */   
/*    */   public Set<PositionData> getCorresponding() {
/* 21 */     return this.corresponding;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(PositionData o) {
/* 26 */     if (Math.abs(o.getSelfDamage() - getSelfDamage()) < 1.0F && o instanceof AntiTotemData) {
/*    */       
/* 28 */       EntityPlayer player = getFirstTarget();
/* 29 */       EntityPlayer other = ((AntiTotemData)o).getFirstTarget();
/*    */       
/* 31 */       if (other == null) {
/* 32 */         return (player == null) ? super.compareTo(o) : -1;
/*    */       }
/* 34 */       return (player == null) ? 1 : Double.compare(player.func_174818_b(getPos()), other
/* 35 */           .func_174818_b(o.getPos()));
/*    */     } 
/*    */ 
/*    */     
/* 39 */     return super.compareTo(o);
/*    */   }
/*    */   
/*    */   public EntityPlayer getFirstTarget() {
/* 43 */     return getAntiTotems().stream().findFirst().orElse(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\AntiTotemData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */