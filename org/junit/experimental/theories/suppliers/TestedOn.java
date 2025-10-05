package org.junit.experimental.theories.suppliers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.experimental.theories.ParametersSuppliedBy;

@ParametersSuppliedBy(TestedOnSupplier.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestedOn {
  int[] ints();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\theories\suppliers\TestedOn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */