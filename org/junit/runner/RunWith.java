package org.junit.runner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RunWith {
  Class<? extends Runner> value();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runner\RunWith.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */