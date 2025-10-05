/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.TessellatorUtil;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class ClickTP
/*     */   extends Module {
/*  23 */   private final Setting<Float> ass = register(new Setting("BlockYCorrect", Float.valueOf(1.0F), Float.valueOf(-1.0F), Float.valueOf(1.0F)));
/*  24 */   private final Setting<Float> adss = register(new Setting("PlayerYCorrect", Float.valueOf(0.0F), Float.valueOf(-1.0F), Float.valueOf(1.0F)));
/*  25 */   private final Setting<Boolean> ground = register(new Setting("ground", Boolean.valueOf(false)));
/*  26 */   private final Setting<Boolean> spoofs = register(new Setting("spoofs", Boolean.valueOf(false)));
/*     */   public ClickTP() {
/*  28 */     super("ClickTP", "ClickTP", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMotion(EventSync e) {
/*  33 */     if (Mouse.isButtonDown(1)) {
/*  34 */       RayTraceResult ray = mc.field_71439_g.func_174822_a(256.0D, mc.func_184121_ak());
/*     */       
/*  36 */       BlockPos pos = null;
/*  37 */       if (ray != null) {
/*  38 */         pos = ray.func_178782_a();
/*     */       }
/*  40 */       EntityPlayer rayTracedEntity = getEntityUnderMouse(256);
/*     */       
/*  42 */       if (rayTracedEntity == null && ray != null && ray.field_72313_a == RayTraceResult.Type.BLOCK) {
/*  43 */         if (((Boolean)this.spoofs.getValue()).booleanValue()) {
/*  44 */           for (int i = 0; i < 10; i++) {
/*  45 */             this; mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(pos.func_177958_n(), (((Float)this.ass.getValue()).floatValue() + pos.func_177956_o()), pos.func_177952_p(), ((Boolean)this.ground.getValue()).booleanValue()));
/*     */           } 
/*     */         }
/*  48 */         mc.field_71439_g.func_70107_b(pos.func_177958_n(), (pos.func_177956_o() + ((Float)this.ass.getValue()).floatValue()), pos.func_177952_p());
/*  49 */       } else if (rayTracedEntity != null) {
/*     */         
/*  51 */         BlockPos bp = new BlockPos((Entity)rayTracedEntity);
/*  52 */         if (((Boolean)this.spoofs.getValue()).booleanValue()) {
/*  53 */           for (int i = 0; i < 10; i++) {
/*  54 */             this; mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(bp.func_177958_n(), (((Float)this.adss.getValue()).floatValue() + bp.func_177956_o()), bp.func_177952_p(), ((Boolean)this.ground.getValue()).booleanValue()));
/*     */           } 
/*     */         }
/*  57 */         mc.field_71439_g.func_70107_b(bp.func_177958_n(), (bp.func_177956_o() + ((Float)this.adss.getValue()).floatValue()), bp.func_177952_p());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/*  64 */     EntityPlayer rayTracedEntity = getEntityUnderMouse(256);
/*     */     
/*  66 */     RayTraceResult ray = mc.field_71439_g.func_174822_a(256.0D, mc.func_184121_ak());
/*  67 */     if (rayTracedEntity == null) {
/*  68 */       if (ray != null && ray.field_72313_a == RayTraceResult.Type.BLOCK) {
/*  69 */         BlockPos blockpos = ray.func_178782_a();
/*     */         
/*  71 */         RenderUtil.drawBlockOutline(blockpos, new Color(11008512), 1.0F, false, 0);
/*     */       } 
/*     */     } else {
/*  74 */       TessellatorUtil.prepare();
/*  75 */       TessellatorUtil.drawBoundingBox(rayTracedEntity.func_174813_aQ(), 3.0D, new Color(2817792));
/*  76 */       TessellatorUtil.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayer getEntityUnderMouse(int range) {
/*  83 */     Entity entity = mc.func_175606_aa();
/*     */     
/*  85 */     if (entity != null) {
/*  86 */       Vec3d pos = mc.field_71439_g.func_174824_e(1.0F); float i;
/*  87 */       for (i = 0.0F; i < range; i += 0.5F) {
/*  88 */         pos = pos.func_178787_e(mc.field_71439_g.func_70040_Z().func_186678_a(0.5D));
/*  89 */         for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/*  90 */           if (player == mc.field_71439_g)
/*  91 */             continue;  AxisAlignedBB bb = player.func_174813_aQ();
/*  92 */           if (bb == null)
/*  93 */             continue;  if (player.func_70032_d((Entity)mc.field_71439_g) > 6.0F) {
/*  94 */             bb = bb.func_186662_g(0.5D);
/*     */           }
/*  96 */           if (bb.func_72318_a(pos)) return player;
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/* 101 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\ClickTP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */