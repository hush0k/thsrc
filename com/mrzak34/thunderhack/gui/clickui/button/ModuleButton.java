/*     */ package com.mrzak34.thunderhack.gui.clickui.button;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.gui.clickui.ColorUtil;
/*     */ import com.mrzak34.thunderhack.gui.clickui.base.AbstractElement;
/*     */ import com.mrzak34.thunderhack.gui.clickui.elements.CheckBoxElement;
/*     */ import com.mrzak34.thunderhack.gui.clickui.elements.ModeElement;
/*     */ import com.mrzak34.thunderhack.gui.clickui.elements.StringElement;
/*     */ import com.mrzak34.thunderhack.gui.clickui.elements.SubBindElement;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.manager.EventManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.client.ClickGui;
/*     */ import com.mrzak34.thunderhack.notification.Animation;
/*     */ import com.mrzak34.thunderhack.notification.DecelerateAnimation;
/*     */ import com.mrzak34.thunderhack.notification.Direction;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.render.Drawable;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ModuleButton {
/*  26 */   private final Animation animation = (Animation)new DecelerateAnimation(260, 1.0D, Direction.BACKWARDS);
/*  27 */   private final Animation enableAnimation = (Animation)new DecelerateAnimation(180, 1.0D, Direction.BACKWARDS); private final List<AbstractElement> elements;
/*     */   private final Module module;
/*     */   private double x;
/*     */   private double y;
/*     */   private double width;
/*     */   private double height;
/*     */   private double offsetY;
/*     */   private boolean open;
/*     */   private boolean hovered;
/*     */   private boolean binding = false;
/*     */   
/*     */   public ModuleButton(Module module) {
/*  39 */     this.module = module;
/*  40 */     this.elements = new ArrayList<>();
/*     */     
/*  42 */     for (Setting setting : module.getSettings()) {
/*  43 */       if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled") && !setting.getName().equals("Drawn")) {
/*  44 */         this.elements.add(new CheckBoxElement(setting)); continue;
/*  45 */       }  if (setting.getValue() instanceof ColorSetting) {
/*  46 */         this.elements.add(new ColorPickerElement(setting)); continue;
/*  47 */       }  if (setting.isNumberSetting() && setting.hasRestriction()) {
/*  48 */         this.elements.add(new SliderElement(setting)); continue;
/*  49 */       }  if (setting.isEnumSetting() && !(setting.getValue() instanceof com.mrzak34.thunderhack.setting.Parent) && !(setting.getValue() instanceof com.mrzak34.thunderhack.setting.PositionSetting)) {
/*  50 */         this.elements.add(new ModeElement(setting)); continue;
/*  51 */       }  if (setting.getValue() instanceof com.mrzak34.thunderhack.setting.SubBind) {
/*  52 */         this.elements.add(new SubBindElement(setting)); continue;
/*  53 */       }  if ((setting.getValue() instanceof String || setting.getValue() instanceof Character) && !setting.getName().equalsIgnoreCase("displayName")) {
/*  54 */         this.elements.add(new StringElement(setting)); continue;
/*  55 */       }  if (setting.getValue() instanceof com.mrzak34.thunderhack.setting.Parent) {
/*  56 */         this.elements.add(new ParentElement(setting));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void init() {
/*  62 */     this.elements.forEach(AbstractElement::init);
/*     */   }
/*     */   
/*     */   public void tick() {
/*  66 */     if (isOpen()) {
/*  67 */       this.elements.forEach(AbstractElement::tick);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float delta, Color color, boolean finished) {
/*  73 */     this.hovered = Drawable.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*     */     
/*  75 */     if (this.hovered) {
/*  76 */       EventManager.hoveredModule = this.module;
/*     */     }
/*     */     
/*  79 */     double ix = this.x + 5.0D;
/*  80 */     double iy = this.y + this.height / 2.0D - (FontRender.getFontHeight6() / 2.0F);
/*  81 */     this.enableAnimation.setDirection(this.module.isEnabled() ? Direction.BACKWARDS : Direction.FORWARDS);
/*     */     
/*  83 */     this.animation.setDirection(this.hovered ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */     
/*  85 */     if (isOpen()) {
/*     */       
/*  87 */       int sbg = (new Color(24, 24, 27)).getRGB();
/*  88 */       Drawable.horizontalGradient(this.x, this.y + this.height + 2.0D, (this.x + this.width) * (1.0D - this.enableAnimation.getOutput()), this.y + this.height + 2.0D + 
/*  89 */           getElementsHeight() * (1.0D - this.enableAnimation.getOutput()), 
/*  90 */           this.module.isEnabled() ? ColorUtil.applyOpacity(ClickGui.getInstance().getColor(200), 0.7F).getRGB() : sbg, 
/*  91 */           this.module.isEnabled() ? ColorUtil.applyOpacity(ClickGui.getInstance().getColor(0), 0.7F).getRGB() : sbg);
/*     */       
/*  93 */       double offsetY = 0.0D;
/*  94 */       for (AbstractElement element : this.elements) {
/*  95 */         if (!element.isVisible()) {
/*     */           continue;
/*     */         }
/*  98 */         element.setOffsetY(offsetY);
/*  99 */         element.setX(this.x);
/* 100 */         element.setY(this.y + this.height + 2.0D);
/* 101 */         element.setWidth(this.width);
/* 102 */         element.setHeight(15.0D);
/*     */         
/* 104 */         if (element instanceof ColorPickerElement) {
/* 105 */           element.setHeight(56.0D);
/*     */         }
/* 107 */         else if (element instanceof SliderElement) {
/* 108 */           element.setHeight(18.0D);
/*     */         } 
/* 110 */         if (element instanceof ModeElement) {
/* 111 */           ModeElement combobox = (ModeElement)element;
/* 112 */           combobox.setWHeight(17.0D);
/*     */           
/* 114 */           if (combobox.isOpen()) {
/* 115 */             offsetY += ((combobox.getSetting().getModes()).length * 6);
/* 116 */             element.setHeight(element.getHeight() + ((combobox.getSetting().getModes()).length * 6) + 3.0D);
/*     */           } else {
/* 118 */             element.setHeight(17.0D);
/*     */           } 
/*     */         } 
/* 121 */         element.render(mouseX, mouseY, delta);
/* 122 */         offsetY += element.getHeight();
/*     */       } 
/*     */       
/* 125 */       Drawable.drawBlurredShadow((int)this.x, (int)(this.y + this.height), (int)this.width, 3.0F, 9, new Color(0, 0, 0, 190));
/*     */     } 
/*     */     
/* 128 */     Drawable.drawRectWH(this.x, this.y, this.width, isOpen() ? (this.height + 2.0D) : this.height, ((ColorSetting)(ClickGui.getInstance()).downColor.getValue()).getColor());
/*     */     
/* 130 */     if (!this.enableAnimation.finished(Direction.FORWARDS)) {
/* 131 */       Drawable.horizontalGradient(this.x, this.y, (this.x + this.width) * (1.0D - this.enableAnimation.getOutput()), this.y + (
/* 132 */           isOpen() ? (this.height + 2.0D) : this.height), 
/* 133 */           ColorUtil.applyOpacity(ClickGui.getInstance().getColor(200), 0.9F).getRGB(), 
/* 134 */           ColorUtil.applyOpacity(ClickGui.getInstance().getColor(0), 0.9F).getRGB());
/*     */     }
/*     */     
/* 137 */     if (!((Boolean)(ClickGui.getInstance()).showBinds.getValue()).booleanValue()) {
/* 138 */       if (this.module.getSettings().size() > 4) {
/* 139 */         FontRender.drawCentString6(isOpen() ? "-" : "+", (float)this.x + (float)this.width - 8.0F, (float)this.y + 6.0F, -1);
/*     */       }
/* 141 */     } else if (!this.module.getBind().toString().equalsIgnoreCase("none")) {
/* 142 */       FontRender.drawString5(this.module.getBind().toString(), (float)this.x + (float)this.width - FontRender.getStringWidth5(this.module.getBind().toString()) - 3.0F, (float)this.y + 6.0F, -1);
/*     */     } 
/*     */ 
/*     */     
/* 146 */     float scale = (float)this.animation.getOutput();
/*     */     
/* 148 */     if (this.binding) {
/* 149 */       FontRender.drawString6("Keybind: " + this.module.getBind().toString(), (float)ix, (float)iy, -1381654, true);
/*     */     } else {
/* 151 */       FontRender.drawString6(this.module.getName(), (float)ix + scale, (float)iy + 3.0F, -1381654, true);
/*     */     } 
/*     */   }
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 155 */     if (this.hovered) {
/* 156 */       if (button == 0) {
/* 157 */         this.module.toggle();
/* 158 */       } else if (button == 1 && this.module.getSettings().size() > 4) {
/* 159 */         setOpen(!isOpen());
/*     */       } 
/* 161 */       if (button == 2) {
/* 162 */         this.binding = !this.binding;
/*     */       }
/*     */     } 
/*     */     
/* 166 */     if (this.open) {
/* 167 */       this.elements.forEach(element -> {
/*     */             if (element.isVisible())
/*     */               element.mouseClicked(mouseX, mouseY, button); 
/*     */           });
/*     */     } else {
/* 172 */       resetAnimation();
/*     */     } 
/*     */   }
/*     */   public void mouseReleased(int mouseX, int mouseY, int button) {
/* 176 */     if (isOpen())
/* 177 */       this.elements.forEach(element -> element.mouseReleased(mouseX, mouseY, button)); 
/*     */   }
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 181 */     if (!isOpen())
/*     */       return; 
/* 183 */     for (AbstractElement element : this.elements)
/* 184 */       element.handleMouseInput(); 
/*     */   }
/*     */   
/*     */   public void keyTyped(char chr, int keyCode) {
/* 188 */     if (isOpen()) {
/* 189 */       for (AbstractElement element : this.elements) {
/* 190 */         element.keyTyped(chr, keyCode);
/*     */       }
/*     */     }
/* 193 */     if (this.binding) {
/* 194 */       if (keyCode == 1 || keyCode == 57 || keyCode == 211) {
/* 195 */         this.module.setBind(0);
/* 196 */         Command.sendMessage("Удален бинд с модуля " + ChatFormatting.LIGHT_PURPLE + this.module.getName());
/*     */       } else {
/* 198 */         this.module.setBind(keyCode);
/* 199 */         Command.sendMessage(ChatFormatting.LIGHT_PURPLE + this.module.getName() + ChatFormatting.WHITE + " бинд изменен на " + ChatFormatting.GREEN + Keyboard.getKeyName(this.module.getBind().getKey()));
/*     */       } 
/* 201 */       this.binding = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onGuiClosed() {
/* 206 */     this.elements.forEach(AbstractElement::onClose);
/* 207 */     resetAnimation();
/*     */   }
/*     */   
/*     */   public void resetAnimation() {
/* 211 */     this.elements.forEach(AbstractElement::resetAnimation);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<AbstractElement> getElements() {
/* 216 */     return this.elements;
/*     */   }
/*     */   
/*     */   public double getElementsHeight() {
/* 220 */     double offsetY = 0.0D;
/* 221 */     double openedY = 0.0D;
/* 222 */     if (isOpen())
/* 223 */       for (AbstractElement element : getElements()) {
/* 224 */         if (element.isVisible()) {
/* 225 */           offsetY += element.getHeight();
/*     */         }
/*     */       }  
/* 228 */     return offsetY + openedY;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 232 */     return this.open;
/*     */   }
/*     */   
/*     */   public void setOpen(boolean open) {
/* 236 */     this.open = open;
/*     */   }
/*     */   
/*     */   public double getX() {
/* 240 */     return this.x;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/* 244 */     this.x = x;
/*     */   }
/*     */   
/*     */   public double getY() {
/* 248 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/* 252 */     this.y = y + this.offsetY;
/*     */   }
/*     */   
/*     */   public double getWidth() {
/* 256 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(double width) {
/* 260 */     this.width = width;
/*     */   }
/*     */   
/*     */   public double getHeight() {
/* 264 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setHeight(double height) {
/* 268 */     this.height = height;
/*     */   }
/*     */   
/*     */   public void setOffsetY(double offsetY) {
/* 272 */     this.offsetY = offsetY;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\button\ModuleButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */