/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Confirmer {
/*  15 */   private final Timer placeTimer = new Timer();
/*  16 */   private final Timer breakTimer = new Timer();
/*     */   
/*     */   private BlockPos current;
/*     */   
/*     */   private AxisAlignedBB bb;
/*     */   
/*     */   private boolean placeConfirmed;
/*     */   
/*     */   private boolean breakConfirmed;
/*     */   
/*     */   private boolean newVer;
/*     */   private boolean valid;
/*     */   private int placeTime;
/*     */   
/*     */   public static Confirmer createAndSubscribe() {
/*  31 */     Confirmer confirmer = new Confirmer();
/*  32 */     MinecraftForge.EVENT_BUS.register(confirmer);
/*  33 */     return confirmer;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  38 */     if (e.getPacket() instanceof SPacketSpawnObject) {
/*  39 */       SPacketSpawnObject p = (SPacketSpawnObject)e.getPacket();
/*  40 */       if (p.func_148993_l() == 51) {
/*  41 */         confirmPlace(p.func_186880_c(), p.func_186882_d(), p.func_186881_e());
/*     */       }
/*     */     } 
/*  44 */     if (e.getPacket() instanceof SPacketSoundEffect) {
/*  45 */       SPacketSoundEffect p = (SPacketSoundEffect)e.getPacket();
/*  46 */       if (p.func_186977_b() == SoundCategory.BLOCKS && p
/*  47 */         .func_186978_a() == SoundEvents.field_187539_bB) {
/*  48 */         confirmBreak(p.func_149207_d(), p.func_149211_e(), p.func_149210_f());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPos(BlockPos pos, boolean newVer, int placeTime) {
/*  54 */     this.newVer = newVer;
/*     */     
/*  56 */     if (pos == null) {
/*  57 */       this.current = null;
/*  58 */       this.valid = false;
/*     */     }
/*     */     else {
/*     */       
/*  62 */       BlockPos crystalPos = new BlockPos((pos.func_177958_n() + 0.5F), (pos.func_177956_o() + 1), (pos.func_177952_p() + 0.5F));
/*  63 */       this.current = crystalPos;
/*  64 */       this.bb = createBB(crystalPos, newVer);
/*  65 */       this.valid = true;
/*  66 */       this.placeConfirmed = false;
/*  67 */       this.breakConfirmed = false;
/*  68 */       this.placeTime = (placeTime < 50) ? 0 : placeTime;
/*  69 */       this.placeTimer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void confirmPlace(double x, double y, double z) {
/*  74 */     if (this.valid && !this.placeConfirmed) {
/*  75 */       BlockPos p = new BlockPos(x, y, z);
/*  76 */       if (p.equals(this.current)) {
/*  77 */         this.placeConfirmed = true;
/*  78 */         this.breakTimer.reset();
/*  79 */       } else if (this.placeTimer.passedMs(this.placeTime)) {
/*  80 */         AxisAlignedBB currentBB = this.bb;
/*  81 */         if (currentBB != null && currentBB
/*  82 */           .func_72326_a(createBB(x, y, z, this.newVer))) {
/*  83 */           this.valid = false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void confirmBreak(double x, double y, double z) {
/*  90 */     if (this.valid && !this.breakConfirmed && this.placeConfirmed) {
/*  91 */       BlockPos current = this.current;
/*  92 */       if (current != null && current.func_177954_c(x, y, z) < 144.0D) {
/*  93 */         if (current.equals(new BlockPos(x, y, z))) {
/*  94 */           this.breakConfirmed = true;
/*     */         } else {
/*  96 */           this.valid = false;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/* 103 */     return this.valid;
/*     */   }
/*     */   
/*     */   public boolean isPlaceConfirmed(int placeConfirm) {
/* 107 */     if (!this.placeConfirmed && this.placeTimer.passedMs(placeConfirm)) {
/* 108 */       this.valid = false;
/* 109 */       return false;
/*     */     } 
/*     */     
/* 112 */     return (this.placeConfirmed && this.valid);
/*     */   }
/*     */   
/*     */   public boolean isBreakConfirmed(int breakConfirm) {
/* 116 */     if (this.placeConfirmed && !this.breakConfirmed && this.breakTimer
/*     */       
/* 118 */       .passedMs(breakConfirm)) {
/* 119 */       this.valid = false;
/* 120 */       return false;
/*     */     } 
/*     */     
/* 123 */     return (this.breakConfirmed && this.valid);
/*     */   }
/*     */   
/*     */   private AxisAlignedBB createBB(BlockPos crystalPos, boolean newVer) {
/* 127 */     return createBB((crystalPos.func_177958_n() + 0.5F), crystalPos
/* 128 */         .func_177956_o(), (crystalPos
/* 129 */         .func_177952_p() + 0.5F), newVer);
/*     */   }
/*     */ 
/*     */   
/*     */   private AxisAlignedBB createBB(double x, double y, double z, boolean newVer) {
/* 134 */     return new AxisAlignedBB(x - 1.0D, y, z - 1.0D, x + 1.0D, y + (newVer ? true : 2), z + 1.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\Confirmer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */