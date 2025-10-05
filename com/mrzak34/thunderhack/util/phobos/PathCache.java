/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.TreeSet;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ 
/*    */ public class PathCache extends AbstractSphere {
/*    */   public PathCache(int expectedSize, int indicesSize, double radius) {
/* 10 */     super(expectedSize, indicesSize, radius);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Collection<BlockPos> sorter(BlockPos middle) {
/* 15 */     return new TreeSet<>((o, p) -> {
/*    */           if (o.equals(p))
/*    */             return 0; 
/*    */           int xpDiff = middle.func_177958_n() - p.func_177958_n();
/*    */           int ypDiff = middle.func_177956_o() - p.func_177956_o();
/*    */           int zpDiff = middle.func_177952_p() - p.func_177952_p();
/*    */           int xoDiff = middle.func_177958_n() - o.func_177958_n();
/*    */           int yoDiff = middle.func_177956_o() - o.func_177956_o();
/*    */           int zoDiff = middle.func_177952_p() - o.func_177952_p();
/*    */           int compare = Integer.compare((PathFinder.produceOffsets(false, false, xoDiff, yoDiff, zoDiff)).length, (PathFinder.produceOffsets(false, false, xpDiff, ypDiff, zpDiff)).length);
/*    */           if (compare != 0)
/*    */             return compare; 
/*    */           compare = Double.compare(middle.func_177951_i((Vec3i)o), middle.func_177951_i((Vec3i)p));
/*    */           if (compare == 0)
/*    */             compare = Integer.compare(Math.abs(o.func_177958_n()) + Math.abs(o.func_177956_o()) + Math.abs(o.func_177952_p()), Math.abs(p.func_177958_n()) + Math.abs(p.func_177956_o()) + Math.abs(p.func_177952_p())); 
/*    */           return (compare == 0) ? 1 : compare;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\PathCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */