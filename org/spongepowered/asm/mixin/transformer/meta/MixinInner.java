package org.spongepowered.asm.mixin.transformer.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface MixinInner {
  String mixin();
  
  String name();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\spongepowered\asm\mixin\transformer\meta\MixinInner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */