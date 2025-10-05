/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mrzak34.thunderhack.events.PostRenderEntitiesEvent;
/*     */ import com.mrzak34.thunderhack.events.TotemPopEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import java.util.LinkedList;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class PopChams
/*     */   extends Module
/*     */ {
/*  24 */   public final CopyOnWriteArrayList<Person> popList = new CopyOnWriteArrayList<>();
/*  25 */   private final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  26 */   private final LinkedList<Long> frames = new LinkedList<>();
/*  27 */   public Setting<Boolean> self = register(new Setting("SelfPop", Boolean.valueOf(false)));
/*  28 */   public Setting<Boolean> anim = register(new Setting("Copy Animations", Boolean.valueOf(false)));
/*  29 */   public Setting<Float> maxOffset = register(new Setting("Offset", Float.valueOf(3.0F), Float.valueOf(0.1F), Float.valueOf(15.0F)));
/*  30 */   public Setting<Float> speed = register(new Setting("ASpeed", Float.valueOf(10.0F), Float.valueOf(0.1F), Float.valueOf(10.0F)));
/*     */   private int fps;
/*     */   
/*     */   public PopChams() {
/*  34 */     super("PopChams", "рендерит юз-тотема", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   public static void renderEntity(EntityLivingBase entity, ModelBase modelBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  38 */     if (modelBase instanceof ModelPlayer) {
/*  39 */       ModelPlayer modelPlayer = (ModelPlayer)modelBase;
/*  40 */       modelPlayer.field_178730_v.field_78806_j = false;
/*  41 */       modelPlayer.field_178733_c.field_78806_j = false;
/*  42 */       modelPlayer.field_178731_d.field_78806_j = false;
/*  43 */       modelPlayer.field_178734_a.field_78806_j = false;
/*  44 */       modelPlayer.field_178732_b.field_78806_j = false;
/*  45 */       modelPlayer.field_178720_f.field_78806_j = true;
/*  46 */       modelPlayer.field_78116_c.field_78806_j = false;
/*     */     } 
/*     */     
/*  49 */     float partialTicks = mc.func_184121_ak();
/*  50 */     double x = entity.field_70165_t - (mc.func_175598_ae()).field_78730_l;
/*  51 */     double y = entity.field_70163_u - (mc.func_175598_ae()).field_78731_m;
/*  52 */     double z = entity.field_70161_v - (mc.func_175598_ae()).field_78728_n;
/*     */     
/*  54 */     GlStateManager.func_179094_E();
/*     */     
/*  56 */     GlStateManager.func_179109_b((float)x, (float)y, (float)z);
/*  57 */     GlStateManager.func_179114_b(180.0F - entity.field_70759_as, 0.0F, 1.0F, 0.0F);
/*  58 */     float f4 = prepareScale(entity, scale);
/*  59 */     float yaw = entity.field_70759_as;
/*     */     
/*  61 */     boolean alpha = GL11.glIsEnabled(3008);
/*  62 */     GlStateManager.func_179141_d();
/*  63 */     modelBase.func_78086_a(entity, limbSwing, limbSwingAmount, partialTicks);
/*  64 */     modelBase.func_78087_a(limbSwing, limbSwingAmount, 0.0F, yaw, entity.field_70125_A, f4, (Entity)entity);
/*  65 */     modelBase.func_78088_a((Entity)entity, limbSwing, limbSwingAmount, 0.0F, yaw, entity.field_70125_A, f4);
/*     */     
/*  67 */     if (!alpha)
/*  68 */       GlStateManager.func_179118_c(); 
/*  69 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   private static float prepareScale(EntityLivingBase entity, float scale) {
/*  73 */     GlStateManager.func_179091_B();
/*  74 */     GlStateManager.func_179152_a(-1.0F, -1.0F, 1.0F);
/*  75 */     double widthX = (entity.func_184177_bl()).field_72336_d - (entity.func_184177_bl()).field_72340_a;
/*  76 */     double widthZ = (entity.func_184177_bl()).field_72334_f - (entity.func_184177_bl()).field_72339_c;
/*     */     
/*  78 */     GlStateManager.func_179139_a(scale + widthX, (scale * entity.field_70131_O), scale + widthZ);
/*  79 */     float f = 0.0625F;
/*     */     
/*  81 */     GlStateManager.func_179109_b(0.0F, -1.501F, 0.0F);
/*  82 */     return f;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTotemPop(TotemPopEvent e) {
/*  87 */     if (!((Boolean)this.self.getValue()).booleanValue() && 
/*  88 */       e.getEntity() == mc.field_71439_g)
/*     */       return; 
/*  90 */     EntityPlayer sp = e.getEntity();
/*  91 */     EntityPlayer entity = new EntityPlayer((World)mc.field_71441_e, new GameProfile(sp.func_110124_au(), sp.func_70005_c_()))
/*     */       {
/*     */         public boolean func_175149_v() {
/*  94 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean func_184812_l_() {
/*  99 */           return false;
/*     */         }
/*     */       };
/* 102 */     entity.func_82149_j((Entity)sp);
/*     */     
/* 104 */     if (((Boolean)this.anim.getValue()).booleanValue()) {
/* 105 */       entity.field_184619_aG = sp.field_184619_aG;
/* 106 */       entity.field_70721_aZ = sp.field_70721_aZ;
/* 107 */       entity.func_70095_a(sp.func_70093_af());
/*     */     } 
/* 109 */     this.popList.add(new Person(entity));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2(PostRenderEntitiesEvent e) {
/* 114 */     GL11.glPushAttrib(1048575);
/* 115 */     boolean texture = GL11.glIsEnabled(3553);
/* 116 */     boolean blend = GL11.glIsEnabled(3042);
/* 117 */     boolean hz = GL11.glIsEnabled(2848);
/*     */     
/* 119 */     GL11.glDepthMask(false);
/* 120 */     GL11.glDisable(2929);
/*     */     
/* 122 */     GlStateManager.func_179147_l();
/* 123 */     GlStateManager.func_179120_a(770, 771, 0, 1);
/* 124 */     GlStateManager.func_179090_x();
/* 125 */     GL11.glEnable(2848);
/* 126 */     GL11.glHint(3154, 4354);
/*     */     
/* 128 */     this.popList.forEach(person -> {
/*     */           person.update(this.popList);
/*     */           
/*     */           person.modelPlayer.field_178733_c.field_78806_j = false;
/*     */           
/*     */           person.modelPlayer.field_178731_d.field_78806_j = false;
/*     */           
/*     */           person.modelPlayer.field_178734_a.field_78806_j = false;
/*     */           person.modelPlayer.field_178732_b.field_78806_j = false;
/*     */           person.modelPlayer.field_178730_v.field_78806_j = false;
/*     */           person.modelPlayer.field_78116_c.field_78806_j = true;
/*     */           person.modelPlayer.field_178720_f.field_78806_j = false;
/*     */           GlStateManager.func_179131_c(((ColorSetting)this.color.getValue()).getRed() / 255.0F, ((ColorSetting)this.color.getValue()).getGreen() / 255.0F, ((ColorSetting)this.color.getValue()).getBlue() / 255.0F, (float)person.alpha / 255.0F);
/*     */           GL11.glPolygonMode(1032, 6914);
/*     */           renderEntity((EntityLivingBase)person.player, (ModelBase)person.modelPlayer, person.player.field_184619_aG, person.player.field_70721_aZ, person.player.field_70173_aa, person.player.field_70759_as, person.player.field_70125_A, 1.0F);
/*     */           GlStateManager.func_179117_G();
/*     */         });
/* 145 */     if (!hz)
/* 146 */       GL11.glDisable(2848); 
/* 147 */     if (texture)
/* 148 */       GlStateManager.func_179098_w(); 
/* 149 */     if (!blend)
/* 150 */       GlStateManager.func_179084_k(); 
/* 151 */     GL11.glPopAttrib();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 156 */     long time = System.nanoTime();
/*     */     
/* 158 */     this.frames.add(Long.valueOf(time));
/*     */     
/*     */     while (true) {
/* 161 */       long f = ((Long)this.frames.getFirst()).longValue();
/* 162 */       long ONE_SECOND = 1000000000L;
/* 163 */       if (time - f > 1000000000L) { this.frames.remove(); continue; }
/*     */       
/*     */       break;
/*     */     } 
/* 167 */     this.fps = this.frames.size();
/*     */   }
/*     */   
/*     */   public float getFrametime() {
/* 171 */     return 1.0F / this.fps;
/*     */   }
/*     */   
/*     */   public class Person {
/*     */     private final EntityPlayer player;
/*     */     private final ModelPlayer modelPlayer;
/*     */     private double alpha;
/*     */     
/*     */     public Person(EntityPlayer player) {
/* 180 */       this.player = player;
/* 181 */       this.modelPlayer = new ModelPlayer(0.0F, false);
/* 182 */       this.alpha = 180.0D;
/*     */     }
/*     */     
/*     */     public void update(CopyOnWriteArrayList<Person> arrayList) {
/* 186 */       if (this.alpha <= 0.0D) {
/* 187 */         arrayList.remove(this);
/* 188 */         Module.mc.field_71441_e.func_72900_e((Entity)this.player);
/*     */         return;
/*     */       } 
/* 191 */       this.alpha -= (180.0F / ((Float)PopChams.this.speed.getValue()).floatValue() * PopChams.this.getFrametime());
/* 192 */       this.player.field_70163_u += (((Float)PopChams.this.maxOffset.getValue()).floatValue() / ((Float)PopChams.this.speed.getValue()).floatValue() * PopChams.this.getFrametime());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\PopChams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */