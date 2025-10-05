/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelPlayer;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.EnumAction;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({ModelPlayer.class})
/*    */ public class MixinPlayerModel extends ModelBiped {
/*    */   @Shadow
/*    */   public ModelRenderer field_178734_a;
/*    */   @Shadow
/*    */   public ModelRenderer field_178732_b;
/*    */   
/*    */   public MixinPlayerModel(float p_i1148_1_) {
/* 28 */     super(p_i1148_1_);
/*    */   }
/*    */   
/*    */   @Inject(at = {@At("TAIL")}, method = {"Lnet/minecraft/client/model/ModelPlayer;setRotationAngles(FFFFFFLnet/minecraft/entity/Entity;)V"})
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn, CallbackInfo ci) {
/* 33 */     if (entityIn instanceof EntityPlayerSP && ((Boolean)((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).eatAnim.getValue()).booleanValue()) {
/* 34 */       eatingAnimationRightHand(EnumHand.MAIN_HAND, (EntityPlayerSP)entityIn, ageInTicks);
/* 35 */       eatingAnimationLeftHand(EnumHand.OFF_HAND, (EntityPlayerSP)entityIn, ageInTicks);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void eatingAnimationRightHand(EnumHand hand, EntityPlayerSP entity, float ageInTicks) {
/* 40 */     ItemStack itemstack = entity.func_184586_b(hand);
/* 41 */     boolean drinkingoreating = (itemstack.func_77975_n() == EnumAction.EAT || itemstack.func_77975_n() == EnumAction.DRINK);
/* 42 */     if (entity.func_184605_cv() > 0 && drinkingoreating && entity.func_184600_cs() == hand) {
/* 43 */       this.field_178723_h.field_78796_g = -0.5F;
/* 44 */       this.field_178723_h.field_78795_f = -1.3F;
/* 45 */       this.field_178723_h.field_78808_h = MathHelper.func_76134_b(ageInTicks) * 0.1F;
/* 46 */       copyModelAngles2(this.field_178723_h, this.field_178732_b);
/*    */       
/* 48 */       this.field_78116_c.field_78795_f = MathHelper.func_76134_b(ageInTicks) * 0.2F;
/* 49 */       this.field_78116_c.field_78796_g = this.field_178720_f.field_78796_g;
/* 50 */       copyModelAngles2(this.field_78116_c, this.field_178720_f);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void eatingAnimationLeftHand(EnumHand hand, EntityPlayerSP entity, float ageInTicks) {
/* 55 */     ItemStack itemstack = entity.func_184586_b(hand);
/* 56 */     boolean drinkingoreating = (itemstack.func_77975_n() == EnumAction.EAT || itemstack.func_77975_n() == EnumAction.DRINK);
/* 57 */     if (entity.func_184605_cv() > 0 && drinkingoreating && entity.func_184600_cs() == hand) {
/* 58 */       this.field_178724_i.field_78796_g = 0.5F;
/* 59 */       this.field_178724_i.field_78795_f = -1.3F;
/* 60 */       this.field_178724_i.field_78808_h = MathHelper.func_76134_b(ageInTicks) * 0.1F;
/* 61 */       copyModelAngles2(this.field_178724_i, this.field_178734_a);
/* 62 */       this.field_78116_c.field_78795_f = MathHelper.func_76134_b(ageInTicks) * 0.2F;
/* 63 */       this.field_78116_c.field_78796_g = this.field_178720_f.field_78796_g;
/* 64 */       copyModelAngles2(this.field_78116_c, this.field_178720_f);
/*    */     } 
/*    */   }
/*    */   
/*    */   void copyModelAngles2(ModelRenderer source, ModelRenderer dest) {
/* 69 */     dest.field_78795_f = source.field_78795_f;
/* 70 */     dest.field_78796_g = source.field_78796_g;
/* 71 */     dest.field_78808_h = source.field_78808_h;
/* 72 */     dest.field_78800_c = source.field_78800_c;
/* 73 */     dest.field_78797_d = source.field_78797_d;
/* 74 */     dest.field_78798_e = source.field_78798_e;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinPlayerModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */