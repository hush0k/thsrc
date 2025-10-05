/*    */ package com.mrzak34.thunderhack.util.dism;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.misc.Dismemberment;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderGib
/*    */   extends Render<EntityGib>
/*    */ {
/* 19 */   private static final ResourceLocation zombieTexture = new ResourceLocation("textures/entity/zombie/zombie.png");
/* 20 */   private static final ResourceLocation skeletonTexture = new ResourceLocation("textures/entity/skeleton/skeleton.png");
/* 21 */   private static final ResourceLocation creeperTexture = new ResourceLocation("textures/entity/creeper/creeper.png");
/*    */   
/*    */   public RenderGib(RenderManager manager) {
/* 24 */     super(manager);
/* 25 */     this.modelGib = new ModelGib();
/*    */   }
/*    */   public ModelGib modelGib;
/*    */   
/*    */   public static float interpolateRotation(float par1, float par2, float par3) {
/*    */     float f3;
/* 31 */     for (f3 = par2 - par1; f3 < -180.0F; f3 += 360.0F);
/*    */ 
/*    */     
/* 34 */     while (f3 >= 180.0F) {
/* 35 */       f3 -= 360.0F;
/*    */     }
/*    */     
/* 38 */     return par1 + par3 * f3;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getEntityTexture(EntityGib gib) {
/* 43 */     if (gib.parent instanceof net.minecraft.entity.player.EntityPlayer) {
/* 44 */       return ((AbstractClientPlayer)gib.parent).func_110306_p();
/*    */     }
/* 46 */     if (gib.parent instanceof net.minecraft.entity.monster.EntityZombie) {
/* 47 */       return zombieTexture;
/*    */     }
/* 49 */     if (gib.parent instanceof net.minecraft.entity.monster.EntitySkeleton) {
/* 50 */       return skeletonTexture;
/*    */     }
/* 52 */     if (gib.parent instanceof net.minecraft.entity.monster.EntityCreeper) {
/* 53 */       return creeperTexture;
/*    */     }
/* 55 */     return zombieTexture;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityGib gib, double par2, double par4, double par6, float par8, float par9) {
/* 61 */     GlStateManager.func_179129_p();
/* 62 */     GlStateManager.func_179094_E();
/* 63 */     func_180548_c(gib);
/*    */     
/* 65 */     GlStateManager.func_179147_l();
/* 66 */     GlStateManager.func_179112_b(770, 771);
/* 67 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, MathHelper.func_76131_a((gib.groundTime >= ((Integer)((Dismemberment)Thunderhack.moduleManager.getModuleByClass(Dismemberment.class)).gibGroundTime.getValue()).intValue()) ? (1.0F - ((gib.groundTime - ((Integer)((Dismemberment)Thunderhack.moduleManager.getModuleByClass(Dismemberment.class)).gibGroundTime.getValue()).intValue()) + par9) / 20.0F) : 1.0F, 0.0F, 1.0F));
/* 68 */     GlStateManager.func_179092_a(516, 0.003921569F);
/*    */     
/* 70 */     GlStateManager.func_179137_b(par2, par4, par6);
/*    */     
/* 72 */     GlStateManager.func_179109_b(0.0F, (gib.type == 0) ? 0.25F : ((gib.type <= 2 && gib.parent instanceof net.minecraft.entity.monster.EntitySkeleton) ? 0.0625F : 0.125F), 0.0F);
/*    */     
/* 74 */     GlStateManager.func_179114_b(interpolateRotation(gib.field_70126_B, gib.field_70177_z, par9), 0.0F, 1.0F, 0.0F);
/* 75 */     GlStateManager.func_179114_b(interpolateRotation(gib.field_70127_C, gib.field_70125_A, par9), -1.0F, 0.0F, 0.0F);
/*    */     
/* 77 */     GlStateManager.func_179109_b(0.0F, 1.5F - gib.field_70131_O * 0.5F, 0.0F);
/*    */     
/* 79 */     GlStateManager.func_179152_a(-1.0F, -1.0F, 1.0F);
/*    */     
/* 81 */     this.modelGib.func_78088_a(gib, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/*    */     
/* 83 */     GlStateManager.func_179092_a(516, 0.1F);
/* 84 */     GlStateManager.func_179084_k();
/* 85 */     GlStateManager.func_179121_F();
/* 86 */     GlStateManager.func_179089_o();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\dism\RenderGib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */