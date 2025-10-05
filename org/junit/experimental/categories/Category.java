package org.junit.experimental.categories;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Category {
  Class<?>[] value();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\categories\Category.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */