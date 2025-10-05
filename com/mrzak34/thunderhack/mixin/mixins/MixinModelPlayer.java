/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.render.Models;
/*     */ import com.mrzak34.thunderhack.modules.render.Skeleton;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelBox;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ @Mixin({ModelPlayer.class})
/*     */ public class MixinModelPlayer
/*     */   extends ModelBiped
/*     */ {
/*     */   @Shadow
/*     */   public ModelRenderer field_178734_a;
/*     */   @Shadow
/*     */   public ModelRenderer field_178732_b;
/*     */   @Shadow
/*     */   public ModelRenderer field_178733_c;
/*     */   @Shadow
/*     */   public ModelRenderer field_178731_d;
/*     */   @Shadow
/*     */   public ModelRenderer field_178730_v;
/*     */   public ModelRenderer left_leg;
/*     */   public ModelRenderer right_leg;
/*     */   public ModelRenderer body;
/*     */   public ModelRenderer eye;
/*     */   public ModelRenderer rabbitBone;
/*     */   public ModelRenderer rabbitRleg;
/*     */   public ModelRenderer rabbitLarm;
/*     */   public ModelRenderer rabbitRarm;
/*     */   public ModelRenderer rabbitLleg;
/*     */   public ModelRenderer rabbitHead;
/*     */   public ModelRenderer fredhead;
/*     */   public ModelRenderer armLeft;
/*     */   public ModelRenderer legRight;
/*     */   public ModelRenderer legLeft;
/*     */   public ModelRenderer armRight;
/*     */   public ModelRenderer fredbody;
/*     */   public ModelRenderer armLeftpad2;
/*     */   public ModelRenderer torso;
/*     */   public ModelRenderer earRightpad_1;
/*     */   public ModelRenderer armRightpad2;
/*     */   public ModelRenderer legLeftpad;
/*     */   public ModelRenderer hat;
/*     */   public ModelRenderer legLeftpad2;
/*     */   public ModelRenderer armRight2;
/*     */   public ModelRenderer legRight2;
/*     */   public ModelRenderer earRightpad;
/*     */   public ModelRenderer armLeft2;
/*     */   public ModelRenderer frednose;
/*     */   public ModelRenderer earLeft;
/*     */   public ModelRenderer footRight;
/*     */   public ModelRenderer legRightpad2;
/*     */   public ModelRenderer legRightpad;
/*     */   public ModelRenderer armLeftpad;
/*     */   public ModelRenderer legLeft2;
/*     */   public ModelRenderer footLeft;
/*     */   public ModelRenderer hat2;
/*     */   public ModelRenderer armRightpad;
/*     */   public ModelRenderer earRight;
/*     */   public ModelRenderer crotch;
/*     */   public ModelRenderer jaw;
/*     */   public ModelRenderer handRight;
/*     */   public ModelRenderer handLeft;
/*     */   
/*     */   public MixinModelPlayer(float modelSize, boolean smallArmsIn) {
/*  85 */     super(modelSize, 0.0F, 64, 64);
/*  86 */     this.body = new ModelRenderer((ModelBase)this);
/*  87 */     this.body.func_78793_a(0.0F, 0.0F, 0.0F);
/*  88 */     this.body.func_78784_a(34, 8).func_78789_a(-4.0F, 6.0F, -3.0F, 8, 12, 6);
/*  89 */     this.body.func_78784_a(15, 10).func_78789_a(-3.0F, 9.0F, 3.0F, 6, 8, 3);
/*  90 */     this.body.func_78784_a(26, 0).func_78789_a(-3.0F, 5.0F, -3.0F, 6, 1, 6);
/*  91 */     this.eye = new ModelRenderer((ModelBase)this);
/*  92 */     this.eye.func_78784_a(0, 10).func_78789_a(-3.0F, 7.0F, -4.0F, 6, 4, 1);
/*  93 */     this.left_leg = new ModelRenderer((ModelBase)this);
/*  94 */     this.left_leg.func_78793_a(-2.0F, 18.0F, 0.0F);
/*  95 */     this.left_leg.func_78784_a(0, 0).func_78790_a(2.9F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
/*  96 */     this.right_leg = new ModelRenderer((ModelBase)this);
/*  97 */     this.right_leg.func_78793_a(2.0F, 18.0F, 0.0F);
/*  98 */     this.right_leg.func_78784_a(13, 0).func_78789_a(-5.9F, 0.0F, -1.5F, 3, 6, 3);
/*  99 */     ModelRenderer footRight = new ModelRenderer((ModelBase)this, 22, 39);
/* 100 */     this.footRight = footRight;
/* 101 */     footRight.func_78793_a(0.0F, 8.0F, 0.0F);
/* 102 */     this.footRight.func_78790_a(-2.5F, 0.0F, -6.0F, 5, 3, 8, 0.0F);
/* 103 */     setRotationAngle(this.footRight, -0.034906585F, 0.0F, 0.0F);
/* 104 */     ModelRenderer earRight = new ModelRenderer((ModelBase)this, 8, 0);
/* 105 */     this.earRight = earRight;
/* 106 */     earRight.func_78793_a(-4.5F, -5.5F, 0.0F);
/* 107 */     this.earRight.func_78790_a(-1.0F, -3.0F, -0.5F, 2, 3, 1, 0.0F);
/* 108 */     setRotationAngle(this.earRight, 0.05235988F, 0.0F, -1.0471976F);
/* 109 */     ModelRenderer legLeftpad = new ModelRenderer((ModelBase)this, 48, 39);
/* 110 */     this.legLeftpad = legLeftpad;
/* 111 */     legLeftpad.func_78793_a(0.0F, 0.5F, 0.0F);
/* 112 */     this.legLeftpad.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 9, 6, 0.0F);
/* 113 */     ModelRenderer earRightpad_1 = new ModelRenderer((ModelBase)this, 40, 39);
/* 114 */     this.earRightpad_1 = earRightpad_1;
/* 115 */     earRightpad_1.func_78793_a(0.0F, -1.0F, 0.0F);
/* 116 */     this.earRightpad_1.func_78790_a(-2.0F, -5.0F, -1.0F, 4, 4, 2, 0.0F);
/* 117 */     ModelRenderer legLeft = new ModelRenderer((ModelBase)this, 54, 10);
/* 118 */     this.legLeft = legLeft;
/* 119 */     legLeft.func_78793_a(3.3F, 12.5F, 0.0F);
/* 120 */     this.legLeft.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
/* 121 */     ModelRenderer armRightpad2 = new ModelRenderer((ModelBase)this, 0, 26);
/* 122 */     this.armRightpad2 = armRightpad2;
/* 123 */     armRightpad2.func_78793_a(0.0F, 0.5F, 0.0F);
/* 124 */     this.armRightpad2.func_78790_a(-2.5F, 0.0F, -2.5F, 5, 7, 5, 0.0F);
/* 125 */     ModelRenderer handLeft = new ModelRenderer((ModelBase)this, 58, 56);
/* 126 */     this.handLeft = handLeft;
/* 127 */     handLeft.func_78793_a(0.0F, 8.0F, 0.0F);
/* 128 */     this.handLeft.func_78790_a(-2.0F, 0.0F, -2.5F, 4, 4, 5, 0.0F);
/* 129 */     setRotationAngle(this.handLeft, 0.0F, 0.0F, 0.05235988F);
/* 130 */     ModelRenderer armLeft = new ModelRenderer((ModelBase)this, 62, 10);
/* 131 */     this.armLeft = armLeft;
/* 132 */     armLeft.func_78793_a(6.5F, -8.0F, 0.0F);
/* 133 */     this.armLeft.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
/* 134 */     setRotationAngle(this.armLeft, 0.0F, 0.0F, -0.2617994F);
/* 135 */     ModelRenderer legRight = new ModelRenderer((ModelBase)this, 90, 8);
/* 136 */     this.legRight = legRight;
/* 137 */     legRight.func_78793_a(-3.3F, 12.5F, 0.0F);
/* 138 */     this.legRight.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
/* 139 */     ModelRenderer armLeft2 = new ModelRenderer((ModelBase)this, 90, 48);
/* 140 */     this.armLeft2 = armLeft2;
/* 141 */     armLeft2.func_78793_a(0.0F, 9.6F, 0.0F);
/* 142 */     this.armLeft2.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
/* 143 */     setRotationAngle(this.armLeft2, -0.17453292F, 0.0F, 0.0F);
/* 144 */     ModelRenderer legRight2 = new ModelRenderer((ModelBase)this, 20, 35);
/* 145 */     this.legRight2 = legRight2;
/* 146 */     legRight2.func_78793_a(0.0F, 9.6F, 0.0F);
/* 147 */     this.legRight2.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
/* 148 */     setRotationAngle(this.legRight2, 0.034906585F, 0.0F, 0.0F);
/* 149 */     ModelRenderer armLeftpad2 = new ModelRenderer((ModelBase)this, 0, 58);
/* 150 */     this.armLeftpad2 = armLeftpad2;
/* 151 */     armLeftpad2.func_78793_a(0.0F, 0.5F, 0.0F);
/* 152 */     this.armLeftpad2.func_78790_a(-2.5F, 0.0F, -2.5F, 5, 7, 5, 0.0F);
/* 153 */     ModelRenderer legLeft2 = new ModelRenderer((ModelBase)this, 72, 48);
/* 154 */     this.legLeft2 = legLeft2;
/* 155 */     legLeft2.func_78793_a(0.0F, 9.6F, 0.0F);
/* 156 */     this.legLeft2.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
/* 157 */     setRotationAngle(this.legLeft2, 0.034906585F, 0.0F, 0.0F);
/* 158 */     ModelRenderer hat = new ModelRenderer((ModelBase)this, 70, 24);
/* 159 */     this.hat = hat;
/* 160 */     hat.func_78793_a(0.0F, -8.4F, 0.0F);
/* 161 */     this.hat.func_78790_a(-3.0F, -0.5F, -3.0F, 6, 1, 6, 0.0F);
/* 162 */     setRotationAngle(this.hat, -0.017453292F, 0.0F, 0.0F);
/* 163 */     ModelRenderer earRightpad = new ModelRenderer((ModelBase)this, 85, 0);
/* 164 */     this.earRightpad = earRightpad;
/* 165 */     earRightpad.func_78793_a(0.0F, -1.0F, 0.0F);
/* 166 */     this.earRightpad.func_78790_a(-2.0F, -5.0F, -1.0F, 4, 4, 2, 0.0F);
/* 167 */     ModelRenderer crotch = new ModelRenderer((ModelBase)this, 56, 0);
/* 168 */     this.crotch = crotch;
/* 169 */     crotch.func_78793_a(0.0F, 9.5F, 0.0F);
/* 170 */     this.crotch.func_78790_a(-5.5F, 0.0F, -3.5F, 11, 3, 7, 0.0F);
/* 171 */     ModelRenderer torso = new ModelRenderer((ModelBase)this, 8, 0);
/* 172 */     this.torso = torso;
/* 173 */     torso.func_78793_a(0.0F, 0.0F, 0.0F);
/* 174 */     this.torso.func_78790_a(-6.0F, -9.0F, -4.0F, 12, 18, 8, 0.0F);
/* 175 */     setRotationAngle(this.torso, 0.017453292F, 0.0F, 0.0F);
/* 176 */     ModelRenderer armRight2 = new ModelRenderer((ModelBase)this, 90, 20);
/* 177 */     this.armRight2 = armRight2;
/* 178 */     armRight2.func_78793_a(0.0F, 9.6F, 0.0F);
/* 179 */     this.armRight2.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
/* 180 */     setRotationAngle(this.armRight2, -0.17453292F, 0.0F, 0.0F);
/* 181 */     ModelRenderer handRight = new ModelRenderer((ModelBase)this, 20, 26);
/* 182 */     this.handRight = handRight;
/* 183 */     handRight.func_78793_a(0.0F, 8.0F, 0.0F);
/* 184 */     this.handRight.func_78790_a(-2.0F, 0.0F, -2.5F, 4, 4, 5, 0.0F);
/* 185 */     setRotationAngle(this.handRight, 0.0F, 0.0F, -0.05235988F);
/* 186 */     ModelRenderer fredbody = new ModelRenderer((ModelBase)this, 0, 0);
/* 187 */     this.fredbody = fredbody;
/* 188 */     fredbody.func_78793_a(0.0F, -9.0F, 0.0F);
/* 189 */     this.fredbody.func_78790_a(-1.0F, -14.0F, -1.0F, 2, 24, 2, 0.0F);
/* 190 */     ModelRenderer fredhead = new ModelRenderer((ModelBase)this, 39, 22);
/* 191 */     this.fredhead = fredhead;
/* 192 */     fredhead.func_78793_a(0.0F, -13.0F, -0.5F);
/* 193 */     this.fredhead.func_78790_a(-5.5F, -8.0F, -4.5F, 11, 8, 9, 0.0F);
/* 194 */     ModelRenderer legRightpad = new ModelRenderer((ModelBase)this, 73, 33);
/* 195 */     this.legRightpad = legRightpad;
/* 196 */     legRightpad.func_78793_a(0.0F, 0.5F, 0.0F);
/* 197 */     this.legRightpad.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 9, 6, 0.0F);
/* 198 */     ModelRenderer frednose = new ModelRenderer((ModelBase)this, 17, 67);
/* 199 */     this.frednose = frednose;
/* 200 */     frednose.func_78793_a(0.0F, -2.0F, -4.5F);
/* 201 */     this.frednose.func_78790_a(-4.0F, -2.0F, -3.0F, 8, 4, 3, 0.0F);
/* 202 */     ModelRenderer legLeftpad2 = new ModelRenderer((ModelBase)this, 16, 50);
/* 203 */     this.legLeftpad2 = legLeftpad2;
/* 204 */     legLeftpad2.func_78793_a(0.0F, 0.5F, 0.0F);
/* 205 */     this.legLeftpad2.func_78790_a(-2.5F, 0.0F, -3.0F, 5, 7, 6, 0.0F);
/* 206 */     ModelRenderer armRightpad3 = new ModelRenderer((ModelBase)this, 70, 10);
/* 207 */     this.armRightpad = armRightpad3;
/* 208 */     armRightpad3.func_78793_a(0.0F, 0.5F, 0.0F);
/* 209 */     this.armRightpad.func_78790_a(-2.5F, 0.0F, -2.5F, 5, 9, 5, 0.0F);
/* 210 */     ModelRenderer armLeftpad3 = new ModelRenderer((ModelBase)this, 38, 54);
/* 211 */     this.armLeftpad = armLeftpad3;
/* 212 */     armLeftpad3.func_78793_a(0.0F, 0.5F, 0.0F);
/* 213 */     this.armLeftpad.func_78790_a(-2.5F, 0.0F, -2.5F, 5, 9, 5, 0.0F);
/* 214 */     ModelRenderer hat2 = new ModelRenderer((ModelBase)this, 78, 61);
/* 215 */     this.hat2 = hat2;
/* 216 */     hat2.func_78793_a(0.0F, 0.1F, 0.0F);
/* 217 */     this.hat2.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
/* 218 */     setRotationAngle(this.hat2, -0.017453292F, 0.0F, 0.0F);
/* 219 */     ModelRenderer legRightpad2 = new ModelRenderer((ModelBase)this, 0, 39);
/* 220 */     this.legRightpad2 = legRightpad2;
/* 221 */     legRightpad2.func_78793_a(0.0F, 0.5F, 0.0F);
/* 222 */     this.legRightpad2.func_78790_a(-2.5F, 0.0F, -3.0F, 5, 7, 6, 0.0F);
/* 223 */     ModelRenderer jaw = new ModelRenderer((ModelBase)this, 49, 65);
/* 224 */     this.jaw = jaw;
/* 225 */     jaw.func_78793_a(0.0F, 0.5F, 0.0F);
/* 226 */     this.jaw.func_78790_a(-5.0F, 0.0F, -4.5F, 10, 3, 9, 0.0F);
/* 227 */     setRotationAngle(this.jaw, 0.08726646F, 0.0F, 0.0F);
/* 228 */     ModelRenderer armRight3 = new ModelRenderer((ModelBase)this, 48, 0);
/* 229 */     this.armRight = armRight3;
/* 230 */     armRight3.func_78793_a(-6.5F, -8.0F, 0.0F);
/* 231 */     this.armRight.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
/* 232 */     setRotationAngle(this.armRight, 0.0F, 0.0F, 0.2617994F);
/* 233 */     ModelRenderer footLeft = new ModelRenderer((ModelBase)this, 72, 50);
/* 234 */     this.footLeft = footLeft;
/* 235 */     footLeft.func_78793_a(0.0F, 8.0F, 0.0F);
/* 236 */     this.footLeft.func_78790_a(-2.5F, 0.0F, -6.0F, 5, 3, 8, 0.0F);
/* 237 */     setRotationAngle(this.footLeft, -0.034906585F, 0.0F, 0.0F);
/* 238 */     ModelRenderer earLeft = new ModelRenderer((ModelBase)this, 40, 0);
/* 239 */     this.earLeft = earLeft;
/* 240 */     earLeft.func_78793_a(4.5F, -5.5F, 0.0F);
/* 241 */     this.earLeft.func_78790_a(-1.0F, -3.0F, -0.5F, 2, 3, 1, 0.0F);
/* 242 */     setRotationAngle(this.earLeft, 0.05235988F, 0.0F, 1.0471976F);
/* 243 */     this.legRight2.func_78792_a(this.footRight);
/* 244 */     this.fredhead.func_78792_a(this.earRight);
/* 245 */     this.legLeft.func_78792_a(this.legLeftpad);
/* 246 */     this.earLeft.func_78792_a(this.earRightpad_1);
/* 247 */     this.fredbody.func_78792_a(this.legLeft);
/* 248 */     this.armRight2.func_78792_a(this.armRightpad2);
/* 249 */     this.armLeft2.func_78792_a(this.handLeft);
/* 250 */     this.fredbody.func_78792_a(this.armLeft);
/* 251 */     this.fredbody.func_78792_a(this.legRight);
/* 252 */     this.armLeft.func_78792_a(this.armLeft2);
/* 253 */     this.legRight.func_78792_a(this.legRight2);
/* 254 */     this.armLeft2.func_78792_a(this.armLeftpad2);
/* 255 */     this.legLeft.func_78792_a(this.legLeft2);
/* 256 */     this.fredhead.func_78792_a(this.hat);
/* 257 */     this.earRight.func_78792_a(this.earRightpad);
/* 258 */     this.fredbody.func_78792_a(this.crotch);
/* 259 */     this.fredbody.func_78792_a(this.torso);
/* 260 */     this.armRight.func_78792_a(this.armRight2);
/* 261 */     this.armRight2.func_78792_a(this.handRight);
/* 262 */     this.fredbody.func_78792_a(this.fredhead);
/* 263 */     this.legRight.func_78792_a(this.legRightpad);
/* 264 */     this.fredhead.func_78792_a(this.frednose);
/* 265 */     this.legLeft2.func_78792_a(this.legLeftpad2);
/* 266 */     this.armRight.func_78792_a(this.armRightpad);
/* 267 */     this.armLeft.func_78792_a(this.armLeftpad);
/* 268 */     this.hat.func_78792_a(this.hat2);
/* 269 */     this.legRight2.func_78792_a(this.legRightpad2);
/* 270 */     this.fredhead.func_78792_a(this.jaw);
/* 271 */     this.fredbody.func_78792_a(this.armRight);
/* 272 */     this.legLeft2.func_78792_a(this.footLeft);
/* 273 */     this.fredhead.func_78792_a(this.earLeft);
/*     */   }
/*     */   
/*     */   @Inject(method = {"setRotationAngles"}, at = {@At("RETURN")})
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn, CallbackInfo callbackInfo) {
/* 278 */     if ((Minecraft.func_71410_x()).field_71441_e != null && (Minecraft.func_71410_x()).field_71439_g != null && entityIn instanceof EntityPlayer)
/* 279 */       Skeleton.addEntity((EntityPlayer)entityIn, ModelPlayer.class.cast(this)); 
/*     */   }
/*     */   
/*     */   public void generatemodel() {
/* 283 */     this.body = new ModelRenderer((ModelBase)this);
/* 284 */     this.body.func_78793_a(0.0F, 0.0F, 0.0F);
/* 285 */     this.body.func_78784_a(34, 8).func_78789_a(-4.0F, 6.0F, -3.0F, 8, 12, 6);
/* 286 */     this.body.func_78784_a(15, 10).func_78789_a(-3.0F, 9.0F, 3.0F, 6, 8, 3);
/* 287 */     this.body.func_78784_a(26, 0).func_78789_a(-3.0F, 5.0F, -3.0F, 6, 1, 6);
/* 288 */     this.eye = new ModelRenderer((ModelBase)this);
/* 289 */     this.eye.func_78784_a(0, 10).func_78789_a(-3.0F, 7.0F, -4.0F, 6, 4, 1);
/* 290 */     this.left_leg = new ModelRenderer((ModelBase)this);
/* 291 */     this.left_leg.func_78793_a(-2.0F, 18.0F, 0.0F);
/* 292 */     this.left_leg.func_78784_a(0, 0).func_78790_a(2.9F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
/* 293 */     this.right_leg = new ModelRenderer((ModelBase)this);
/* 294 */     this.right_leg.func_78793_a(2.0F, 18.0F, 0.0F);
/* 295 */     this.right_leg.func_78784_a(13, 0).func_78789_a(-5.9F, 0.0F, -1.5F, 3, 6, 3);
/* 296 */     (this.rabbitBone = new ModelRenderer((ModelBase)this)).func_78793_a(0.0F, 24.0F, 0.0F);
/* 297 */     this.rabbitBone.field_78804_l.add(new ModelBox(this.rabbitBone, 28, 45, -5.0F, -13.0F, -5.0F, 10, 11, 8, 0.0F, false));
/* 298 */     (this.rabbitRleg = new ModelRenderer((ModelBase)this)).func_78793_a(-3.0F, -2.0F, -1.0F);
/* 299 */     this.rabbitBone.func_78792_a(this.rabbitRleg);
/* 300 */     this.rabbitRleg.field_78804_l.add(new ModelBox(this.rabbitRleg, 0, 0, -2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F, false));
/* 301 */     (this.rabbitLarm = new ModelRenderer((ModelBase)this)).func_78793_a(5.0F, -13.0F, -1.0F);
/* 302 */     setRotationAngle(this.rabbitLarm, 0.0F, 0.0F, -0.0873F);
/* 303 */     this.rabbitBone.func_78792_a(this.rabbitLarm);
/* 304 */     this.rabbitLarm.field_78804_l.add(new ModelBox(this.rabbitLarm, 0, 0, 0.0F, 0.0F, -2.0F, 2, 8, 4, 0.0F, false));
/* 305 */     (this.rabbitRarm = new ModelRenderer((ModelBase)this)).func_78793_a(-5.0F, -13.0F, -1.0F);
/* 306 */     setRotationAngle(this.rabbitRarm, 0.0F, 0.0F, 0.0873F);
/* 307 */     this.rabbitBone.func_78792_a(this.rabbitRarm);
/* 308 */     this.rabbitRarm.field_78804_l.add(new ModelBox(this.rabbitRarm, 0, 0, -2.0F, 0.0F, -2.0F, 2, 8, 4, 0.0F, false));
/* 309 */     (this.rabbitLleg = new ModelRenderer((ModelBase)this)).func_78793_a(3.0F, -2.0F, -1.0F);
/* 310 */     this.rabbitBone.func_78792_a(this.rabbitLleg);
/* 311 */     this.rabbitLleg.field_78804_l.add(new ModelBox(this.rabbitLleg, 0, 0, -2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F, false));
/* 312 */     (this.rabbitHead = new ModelRenderer((ModelBase)this)).func_78793_a(0.0F, -14.0F, -1.0F);
/* 313 */     this.rabbitBone.func_78792_a(this.rabbitHead);
/* 314 */     this.rabbitHead.field_78804_l.add(new ModelBox(this.rabbitHead, 0, 0, -3.0F, 0.0F, -4.0F, 6, 1, 6, 0.0F, false));
/* 315 */     this.rabbitHead.field_78804_l.add(new ModelBox(this.rabbitHead, 56, 0, -5.0F, -9.0F, -5.0F, 2, 3, 2, 0.0F, false));
/* 316 */     this.rabbitHead.field_78804_l.add(new ModelBox(this.rabbitHead, 56, 0, 3.0F, -9.0F, -5.0F, 2, 3, 2, 0.0F, true));
/* 317 */     this.rabbitHead.field_78804_l.add(new ModelBox(this.rabbitHead, 0, 45, -4.0F, -11.0F, -4.0F, 8, 11, 8, 0.0F, false));
/* 318 */     this.rabbitHead.field_78804_l.add(new ModelBox(this.rabbitHead, 46, 0, 1.0F, -20.0F, 0.0F, 3, 9, 1, 0.0F, false));
/* 319 */     this.rabbitHead.field_78804_l.add(new ModelBox(this.rabbitHead, 46, 0, -4.0F, -20.0F, 0.0F, 3, 9, 1, 0.0F, false));
/* 320 */     this.field_78090_t = 100;
/* 321 */     this.field_78089_u = 80;
/* 322 */     ModelRenderer footRight = new ModelRenderer((ModelBase)this, 22, 39);
/* 323 */     this.footRight = footRight;
/* 324 */     footRight.func_78793_a(0.0F, 8.0F, 0.0F);
/* 325 */     this.footRight.func_78790_a(-2.5F, 0.0F, -6.0F, 5, 3, 8, 0.0F);
/* 326 */     setRotationAngle(this.footRight, -0.034906585F, 0.0F, 0.0F);
/* 327 */     ModelRenderer earRight = new ModelRenderer((ModelBase)this, 8, 0);
/* 328 */     this.earRight = earRight;
/* 329 */     earRight.func_78793_a(-4.5F, -5.5F, 0.0F);
/* 330 */     this.earRight.func_78790_a(-1.0F, -3.0F, -0.5F, 2, 3, 1, 0.0F);
/* 331 */     setRotationAngle(this.earRight, 0.05235988F, 0.0F, -1.0471976F);
/* 332 */     ModelRenderer legLeftpad = new ModelRenderer((ModelBase)this, 48, 39);
/* 333 */     this.legLeftpad = legLeftpad;
/* 334 */     legLeftpad.func_78793_a(0.0F, 0.5F, 0.0F);
/* 335 */     this.legLeftpad.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 9, 6, 0.0F);
/* 336 */     ModelRenderer earRightpad_1 = new ModelRenderer((ModelBase)this, 40, 39);
/* 337 */     this.earRightpad_1 = earRightpad_1;
/* 338 */     earRightpad_1.func_78793_a(0.0F, -1.0F, 0.0F);
/* 339 */     this.earRightpad_1.func_78790_a(-2.0F, -5.0F, -1.0F, 4, 4, 2, 0.0F);
/* 340 */     ModelRenderer legLeft = new ModelRenderer((ModelBase)this, 54, 10);
/* 341 */     this.legLeft = legLeft;
/* 342 */     legLeft.func_78793_a(3.3F, 12.5F, 0.0F);
/* 343 */     this.legLeft.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
/* 344 */     ModelRenderer armRightpad2 = new ModelRenderer((ModelBase)this, 0, 26);
/* 345 */     this.armRightpad2 = armRightpad2;
/* 346 */     armRightpad2.func_78793_a(0.0F, 0.5F, 0.0F);
/* 347 */     this.armRightpad2.func_78790_a(-2.5F, 0.0F, -2.5F, 5, 7, 5, 0.0F);
/* 348 */     ModelRenderer handLeft = new ModelRenderer((ModelBase)this, 58, 56);
/* 349 */     this.handLeft = handLeft;
/* 350 */     handLeft.func_78793_a(0.0F, 8.0F, 0.0F);
/* 351 */     this.handLeft.func_78790_a(-2.0F, 0.0F, -2.5F, 4, 4, 5, 0.0F);
/* 352 */     setRotationAngle(this.handLeft, 0.0F, 0.0F, 0.05235988F);
/* 353 */     ModelRenderer armLeft = new ModelRenderer((ModelBase)this, 62, 10);
/* 354 */     this.armLeft = armLeft;
/* 355 */     armLeft.func_78793_a(6.5F, -8.0F, 0.0F);
/* 356 */     this.armLeft.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
/* 357 */     setRotationAngle(this.armLeft, 0.0F, 0.0F, -0.2617994F);
/* 358 */     ModelRenderer legRight = new ModelRenderer((ModelBase)this, 90, 8);
/* 359 */     this.legRight = legRight;
/* 360 */     legRight.func_78793_a(-3.3F, 12.5F, 0.0F);
/* 361 */     this.legRight.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
/* 362 */     ModelRenderer armLeft2 = new ModelRenderer((ModelBase)this, 90, 48);
/* 363 */     this.armLeft2 = armLeft2;
/* 364 */     armLeft2.func_78793_a(0.0F, 9.6F, 0.0F);
/* 365 */     this.armLeft2.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
/* 366 */     setRotationAngle(this.armLeft2, -0.17453292F, 0.0F, 0.0F);
/* 367 */     ModelRenderer legRight2 = new ModelRenderer((ModelBase)this, 20, 35);
/* 368 */     this.legRight2 = legRight2;
/* 369 */     legRight2.func_78793_a(0.0F, 9.6F, 0.0F);
/* 370 */     this.legRight2.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
/* 371 */     setRotationAngle(this.legRight2, 0.034906585F, 0.0F, 0.0F);
/* 372 */     ModelRenderer armLeftpad2 = new ModelRenderer((ModelBase)this, 0, 58);
/* 373 */     this.armLeftpad2 = armLeftpad2;
/* 374 */     armLeftpad2.func_78793_a(0.0F, 0.5F, 0.0F);
/* 375 */     this.armLeftpad2.func_78790_a(-2.5F, 0.0F, -2.5F, 5, 7, 5, 0.0F);
/* 376 */     ModelRenderer legLeft2 = new ModelRenderer((ModelBase)this, 72, 48);
/* 377 */     this.legLeft2 = legLeft2;
/* 378 */     legLeft2.func_78793_a(0.0F, 9.6F, 0.0F);
/* 379 */     this.legLeft2.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
/* 380 */     setRotationAngle(this.legLeft2, 0.034906585F, 0.0F, 0.0F);
/* 381 */     ModelRenderer hat = new ModelRenderer((ModelBase)this, 70, 24);
/* 382 */     this.hat = hat;
/* 383 */     hat.func_78793_a(0.0F, -8.4F, 0.0F);
/* 384 */     this.hat.func_78790_a(-3.0F, -0.5F, -3.0F, 6, 1, 6, 0.0F);
/* 385 */     setRotationAngle(this.hat, -0.017453292F, 0.0F, 0.0F);
/* 386 */     ModelRenderer earRightpad = new ModelRenderer((ModelBase)this, 85, 0);
/* 387 */     this.earRightpad = earRightpad;
/* 388 */     earRightpad.func_78793_a(0.0F, -1.0F, 0.0F);
/* 389 */     this.earRightpad.func_78790_a(-2.0F, -5.0F, -1.0F, 4, 4, 2, 0.0F);
/* 390 */     ModelRenderer crotch = new ModelRenderer((ModelBase)this, 56, 0);
/* 391 */     this.crotch = crotch;
/* 392 */     crotch.func_78793_a(0.0F, 9.5F, 0.0F);
/* 393 */     this.crotch.func_78790_a(-5.5F, 0.0F, -3.5F, 11, 3, 7, 0.0F);
/* 394 */     ModelRenderer torso = new ModelRenderer((ModelBase)this, 8, 0);
/* 395 */     this.torso = torso;
/* 396 */     torso.func_78793_a(0.0F, 0.0F, 0.0F);
/* 397 */     this.torso.func_78790_a(-6.0F, -9.0F, -4.0F, 12, 18, 8, 0.0F);
/* 398 */     setRotationAngle(this.torso, 0.017453292F, 0.0F, 0.0F);
/* 399 */     ModelRenderer armRight2 = new ModelRenderer((ModelBase)this, 90, 20);
/* 400 */     this.armRight2 = armRight2;
/* 401 */     armRight2.func_78793_a(0.0F, 9.6F, 0.0F);
/* 402 */     this.armRight2.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
/* 403 */     setRotationAngle(this.armRight2, -0.17453292F, 0.0F, 0.0F);
/* 404 */     ModelRenderer handRight = new ModelRenderer((ModelBase)this, 20, 26);
/* 405 */     this.handRight = handRight;
/* 406 */     handRight.func_78793_a(0.0F, 8.0F, 0.0F);
/* 407 */     this.handRight.func_78790_a(-2.0F, 0.0F, -2.5F, 4, 4, 5, 0.0F);
/* 408 */     setRotationAngle(this.handRight, 0.0F, 0.0F, -0.05235988F);
/* 409 */     ModelRenderer fredbody = new ModelRenderer((ModelBase)this, 0, 0);
/* 410 */     this.fredbody = fredbody;
/* 411 */     fredbody.func_78793_a(0.0F, -9.0F, 0.0F);
/* 412 */     this.fredbody.func_78790_a(-1.0F, -14.0F, -1.0F, 2, 24, 2, 0.0F);
/* 413 */     ModelRenderer fredhead = new ModelRenderer((ModelBase)this, 39, 22);
/* 414 */     this.fredhead = fredhead;
/* 415 */     fredhead.func_78793_a(0.0F, -13.0F, -0.5F);
/* 416 */     this.fredhead.func_78790_a(-5.5F, -8.0F, -4.5F, 11, 8, 9, 0.0F);
/* 417 */     ModelRenderer legRightpad = new ModelRenderer((ModelBase)this, 73, 33);
/* 418 */     this.legRightpad = legRightpad;
/* 419 */     legRightpad.func_78793_a(0.0F, 0.5F, 0.0F);
/* 420 */     this.legRightpad.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 9, 6, 0.0F);
/* 421 */     ModelRenderer frednose = new ModelRenderer((ModelBase)this, 17, 67);
/* 422 */     this.frednose = frednose;
/* 423 */     frednose.func_78793_a(0.0F, -2.0F, -4.5F);
/* 424 */     this.frednose.func_78790_a(-4.0F, -2.0F, -3.0F, 8, 4, 3, 0.0F);
/* 425 */     ModelRenderer legLeftpad2 = new ModelRenderer((ModelBase)this, 16, 50);
/* 426 */     this.legLeftpad2 = legLeftpad2;
/* 427 */     legLeftpad2.func_78793_a(0.0F, 0.5F, 0.0F);
/* 428 */     this.legLeftpad2.func_78790_a(-2.5F, 0.0F, -3.0F, 5, 7, 6, 0.0F);
/* 429 */     ModelRenderer armRightpad3 = new ModelRenderer((ModelBase)this, 70, 10);
/* 430 */     this.armRightpad = armRightpad3;
/* 431 */     armRightpad3.func_78793_a(0.0F, 0.5F, 0.0F);
/* 432 */     this.armRightpad.func_78790_a(-2.5F, 0.0F, -2.5F, 5, 9, 5, 0.0F);
/* 433 */     ModelRenderer armLeftpad3 = new ModelRenderer((ModelBase)this, 38, 54);
/* 434 */     this.armLeftpad = armLeftpad3;
/* 435 */     armLeftpad3.func_78793_a(0.0F, 0.5F, 0.0F);
/* 436 */     this.armLeftpad.func_78790_a(-2.5F, 0.0F, -2.5F, 5, 9, 5, 0.0F);
/* 437 */     ModelRenderer hat2 = new ModelRenderer((ModelBase)this, 78, 61);
/* 438 */     this.hat2 = hat2;
/* 439 */     hat2.func_78793_a(0.0F, 0.1F, 0.0F);
/* 440 */     this.hat2.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
/* 441 */     setRotationAngle(this.hat2, -0.017453292F, 0.0F, 0.0F);
/* 442 */     ModelRenderer legRightpad2 = new ModelRenderer((ModelBase)this, 0, 39);
/* 443 */     this.legRightpad2 = legRightpad2;
/* 444 */     legRightpad2.func_78793_a(0.0F, 0.5F, 0.0F);
/* 445 */     this.legRightpad2.func_78790_a(-2.5F, 0.0F, -3.0F, 5, 7, 6, 0.0F);
/* 446 */     ModelRenderer jaw = new ModelRenderer((ModelBase)this, 49, 65);
/* 447 */     this.jaw = jaw;
/* 448 */     jaw.func_78793_a(0.0F, 0.5F, 0.0F);
/* 449 */     this.jaw.func_78790_a(-5.0F, 0.0F, -4.5F, 10, 3, 9, 0.0F);
/* 450 */     setRotationAngle(this.jaw, 0.08726646F, 0.0F, 0.0F);
/* 451 */     ModelRenderer armRight3 = new ModelRenderer((ModelBase)this, 48, 0);
/* 452 */     this.armRight = armRight3;
/* 453 */     armRight3.func_78793_a(-6.5F, -8.0F, 0.0F);
/* 454 */     this.armRight.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
/* 455 */     setRotationAngle(this.armRight, 0.0F, 0.0F, 0.2617994F);
/* 456 */     ModelRenderer footLeft = new ModelRenderer((ModelBase)this, 72, 50);
/* 457 */     this.footLeft = footLeft;
/* 458 */     footLeft.func_78793_a(0.0F, 8.0F, 0.0F);
/* 459 */     this.footLeft.func_78790_a(-2.5F, 0.0F, -6.0F, 5, 3, 8, 0.0F);
/* 460 */     setRotationAngle(this.footLeft, -0.034906585F, 0.0F, 0.0F);
/* 461 */     ModelRenderer earLeft = new ModelRenderer((ModelBase)this, 40, 0);
/* 462 */     this.earLeft = earLeft;
/* 463 */     earLeft.func_78793_a(4.5F, -5.5F, 0.0F);
/* 464 */     this.earLeft.func_78790_a(-1.0F, -3.0F, -0.5F, 2, 3, 1, 0.0F);
/* 465 */     setRotationAngle(this.earLeft, 0.05235988F, 0.0F, 1.0471976F);
/* 466 */     this.legRight2.func_78792_a(this.footRight);
/* 467 */     this.fredhead.func_78792_a(this.earRight);
/* 468 */     this.legLeft.func_78792_a(this.legLeftpad);
/* 469 */     this.earLeft.func_78792_a(this.earRightpad_1);
/* 470 */     this.fredbody.func_78792_a(this.legLeft);
/* 471 */     this.armRight2.func_78792_a(this.armRightpad2);
/* 472 */     this.armLeft2.func_78792_a(this.handLeft);
/* 473 */     this.fredbody.func_78792_a(this.armLeft);
/* 474 */     this.fredbody.func_78792_a(this.legRight);
/* 475 */     this.armLeft.func_78792_a(this.armLeft2);
/* 476 */     this.legRight.func_78792_a(this.legRight2);
/* 477 */     this.armLeft2.func_78792_a(this.armLeftpad2);
/* 478 */     this.legLeft.func_78792_a(this.legLeft2);
/* 479 */     this.fredhead.func_78792_a(this.hat);
/* 480 */     this.earRight.func_78792_a(this.earRightpad);
/* 481 */     this.fredbody.func_78792_a(this.crotch);
/* 482 */     this.fredbody.func_78792_a(this.torso);
/* 483 */     this.armRight.func_78792_a(this.armRight2);
/* 484 */     this.armRight2.func_78792_a(this.handRight);
/* 485 */     this.fredbody.func_78792_a(this.fredhead);
/* 486 */     this.legRight.func_78792_a(this.legRightpad);
/* 487 */     this.fredhead.func_78792_a(this.frednose);
/* 488 */     this.legLeft2.func_78792_a(this.legLeftpad2);
/* 489 */     this.armRight.func_78792_a(this.armRightpad);
/* 490 */     this.armLeft.func_78792_a(this.armLeftpad);
/* 491 */     this.hat.func_78792_a(this.hat2);
/* 492 */     this.legRight2.func_78792_a(this.legRightpad2);
/* 493 */     this.fredhead.func_78792_a(this.jaw);
/* 494 */     this.fredbody.func_78792_a(this.armRight);
/* 495 */     this.legLeft2.func_78792_a(this.footLeft);
/* 496 */     this.fredhead.func_78792_a(this.earLeft);
/*     */   }
/*     */   
/*     */   public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
/* 500 */     modelRenderer.field_78795_f = x;
/* 501 */     modelRenderer.field_78796_g = y;
/* 502 */     modelRenderer.field_78808_h = z;
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void renderHook(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci) {
/* 508 */     Models customModel = (Models)Thunderhack.moduleManager.getModuleByClass(Models.class);
/* 509 */     if (customModel.isOn()) {
/* 510 */       ci.cancel();
/* 511 */       renderCustom(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderCustom(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 517 */     if (this.left_leg == null) {
/* 518 */       generatemodel();
/*     */     }
/*     */ 
/*     */     
/* 522 */     Models customModel = (Models)Thunderhack.moduleManager.getModuleByClass(Models.class);
/* 523 */     GlStateManager.func_179094_E();
/* 524 */     if ((!((Boolean)customModel.onlySelf.getValue()).booleanValue() || entityIn == (Minecraft.func_71410_x()).field_71439_g || (Thunderhack.friendManager.isFriend(entityIn.func_70005_c_()) && ((Boolean)customModel.friends.getValue()).booleanValue())) && customModel.isEnabled()) {
/* 525 */       if (customModel.Mode.getValue() == Models.mode.Amogus) {
/* 526 */         boolean flag = (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).func_184599_cB() > 4);
/* 527 */         this.field_78116_c.field_78796_g = netHeadYaw * 0.017453292F;
/* 528 */         if (flag) {
/* 529 */           this.field_78116_c.field_78795_f = -0.7853982F;
/*     */         } else {
/* 531 */           this.field_78116_c.field_78795_f = headPitch * 0.017453292F;
/*     */         } 
/* 533 */         this.field_78115_e.field_78796_g = 0.0F;
/* 534 */         this.field_178723_h.field_78798_e = 0.0F;
/* 535 */         this.field_178723_h.field_78800_c = -5.0F;
/* 536 */         this.field_178724_i.field_78798_e = 0.0F;
/* 537 */         this.field_178724_i.field_78800_c = 5.0F;
/* 538 */         float f = 1.0F;
/* 539 */         if (flag) {
/* 540 */           f = (float)(entityIn.field_70159_w * entityIn.field_70159_w + entityIn.field_70181_x * entityIn.field_70181_x + entityIn.field_70179_y * entityIn.field_70179_y);
/* 541 */           f /= 0.2F;
/* 542 */           f = f * f * f;
/*     */         } 
/* 544 */         if (f < 1.0F) {
/* 545 */           f = 1.0F;
/*     */         }
/* 547 */         this.field_178723_h.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F / f;
/* 548 */         this.field_178724_i.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
/* 549 */         this.field_178723_h.field_78808_h = 0.0F;
/* 550 */         this.field_178724_i.field_78808_h = 0.0F;
/* 551 */         this.right_leg.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
/* 552 */         this.left_leg.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount / f;
/* 553 */         this.right_leg.field_78796_g = 0.0F;
/* 554 */         this.left_leg.field_78796_g = 0.0F;
/* 555 */         this.right_leg.field_78808_h = 0.0F;
/* 556 */         this.left_leg.field_78808_h = 0.0F;
/* 557 */         Color bodyColor = ((ColorSetting)customModel.bodyColor.getValue()).getColorObject();
/*     */         
/* 559 */         Color eyeColor = ((ColorSetting)customModel.eyeColor.getValue()).getColorObject();
/* 560 */         Color legsColor = ((ColorSetting)customModel.legsColor.getValue()).getColorObject();
/*     */         
/* 562 */         if (((Boolean)customModel.friendHighlight.getValue()).booleanValue() && Thunderhack.friendManager.isFriend(entityIn.func_70005_c_())) {
/* 563 */           bodyColor = Color.GREEN;
/* 564 */           eyeColor = Color.WHITE;
/* 565 */           legsColor = Color.GREEN;
/*     */         } 
/*     */         
/* 568 */         if (this.field_78091_s) {
/* 569 */           GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
/* 570 */           GlStateManager.func_179109_b(0.0F, 24.0F * scale, 0.0F);
/* 571 */           this.body.func_78785_a(scale);
/* 572 */           this.left_leg.func_78785_a(scale);
/* 573 */           this.right_leg.func_78785_a(scale);
/*     */         } else {
/* 575 */           GlStateManager.func_179137_b(0.0D, -0.8D, 0.0D);
/* 576 */           GlStateManager.func_179139_a(1.8D, 1.6D, 1.6D);
/* 577 */           RenderUtil.glColor(bodyColor);
/* 578 */           GlStateManager.func_179137_b(0.0D, 0.15D, 0.0D);
/* 579 */           this.body.func_78785_a(scale);
/* 580 */           RenderUtil.glColor(eyeColor);
/* 581 */           this.eye.func_78785_a(scale);
/* 582 */           RenderUtil.glColor(legsColor);
/* 583 */           GlStateManager.func_179137_b(0.0D, -0.15D, 0.0D);
/* 584 */           this.left_leg.func_78785_a(scale);
/* 585 */           this.right_leg.func_78785_a(scale);
/* 586 */           GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         } 
/* 588 */       } else if (customModel.isEnabled() && customModel.Mode.getValue() == Models.mode.Rabbit) {
/* 589 */         GlStateManager.func_179094_E();
/* 590 */         GlStateManager.func_179139_a(1.25D, 1.25D, 1.25D);
/* 591 */         GlStateManager.func_179137_b(0.0D, -0.3D, 0.0D);
/* 592 */         this.rabbitHead.field_78795_f = this.field_78116_c.field_78795_f;
/* 593 */         this.rabbitHead.field_78796_g = this.field_78116_c.field_78796_g;
/* 594 */         this.rabbitHead.field_78808_h = this.field_78116_c.field_78808_h;
/* 595 */         this.rabbitLarm.field_78795_f = this.field_178724_i.field_78795_f;
/* 596 */         this.rabbitLarm.field_78796_g = this.field_178724_i.field_78796_g;
/* 597 */         this.rabbitLarm.field_78808_h = this.field_178724_i.field_78808_h;
/* 598 */         this.rabbitRarm.field_78795_f = this.field_178723_h.field_78795_f;
/* 599 */         this.rabbitRarm.field_78796_g = this.field_178723_h.field_78796_g;
/* 600 */         this.rabbitRarm.field_78808_h = this.field_178723_h.field_78808_h;
/* 601 */         this.rabbitRleg.field_78795_f = this.field_178721_j.field_78795_f;
/* 602 */         this.rabbitRleg.field_78796_g = this.field_178721_j.field_78796_g;
/* 603 */         this.rabbitRleg.field_78808_h = this.field_178721_j.field_78808_h;
/* 604 */         this.rabbitLleg.field_78795_f = this.field_178722_k.field_78795_f;
/* 605 */         this.rabbitLleg.field_78796_g = this.field_178722_k.field_78796_g;
/* 606 */         this.rabbitLleg.field_78808_h = this.field_178722_k.field_78808_h;
/* 607 */         this.rabbitBone.func_78785_a(scale);
/* 608 */         GlStateManager.func_179121_F();
/* 609 */       } else if (customModel.isEnabled() && customModel.Mode.getValue() == Models.mode.Freddy) {
/* 610 */         this.fredhead.field_78795_f = this.field_78116_c.field_78795_f;
/* 611 */         this.fredhead.field_78796_g = this.field_78116_c.field_78796_g;
/* 612 */         this.fredhead.field_78808_h = this.field_78116_c.field_78808_h;
/* 613 */         this.armLeft.field_78795_f = this.field_178724_i.field_78795_f;
/* 614 */         this.armLeft.field_78796_g = this.field_178724_i.field_78796_g;
/* 615 */         this.armLeft.field_78808_h = this.field_178724_i.field_78808_h;
/* 616 */         this.legRight.field_78795_f = this.field_178721_j.field_78795_f;
/* 617 */         this.legRight.field_78796_g = this.field_178721_j.field_78796_g;
/* 618 */         this.legRight.field_78808_h = this.field_178721_j.field_78808_h;
/* 619 */         this.legLeft.field_78795_f = this.field_178722_k.field_78795_f;
/* 620 */         this.legLeft.field_78796_g = this.field_178722_k.field_78796_g;
/* 621 */         this.legLeft.field_78808_h = this.field_178722_k.field_78808_h;
/* 622 */         this.armRight.field_78795_f = this.field_178723_h.field_78795_f;
/* 623 */         this.armRight.field_78796_g = this.field_178723_h.field_78796_g;
/* 624 */         this.armRight.field_78808_h = this.field_178723_h.field_78808_h;
/* 625 */         GlStateManager.func_179094_E();
/* 626 */         GlStateManager.func_179139_a(0.75D, 0.65D, 0.75D);
/* 627 */         GlStateManager.func_179137_b(0.0D, 0.85D, 0.0D);
/* 628 */         this.fredbody.func_78785_a(scale);
/* 629 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */     } else {
/* 632 */       func_78088_a(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 633 */       if (this.field_78091_s) {
/* 634 */         float f = 2.0F;
/* 635 */         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
/* 636 */         GlStateManager.func_179109_b(0.0F, 24.0F * scale, 0.0F);
/* 637 */         this.field_178733_c.func_78785_a(scale);
/* 638 */         this.field_178731_d.func_78785_a(scale);
/* 639 */         this.field_178734_a.func_78785_a(scale);
/* 640 */         this.field_178732_b.func_78785_a(scale);
/* 641 */         this.field_178730_v.func_78785_a(scale);
/*     */       } else {
/* 643 */         if (entityIn.func_70093_af())
/* 644 */           GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F); 
/* 645 */         this.field_178733_c.func_78785_a(scale);
/* 646 */         this.field_178731_d.func_78785_a(scale);
/* 647 */         this.field_178734_a.func_78785_a(scale);
/* 648 */         this.field_178732_b.func_78785_a(scale);
/* 649 */         this.field_178730_v.func_78785_a(scale);
/*     */       } 
/*     */     } 
/* 652 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinModelPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */