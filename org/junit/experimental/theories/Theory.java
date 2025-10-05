package org.junit.experimental.theories;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Theory {
  boolean nullsAccepted() default true;
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\theories\Theory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */