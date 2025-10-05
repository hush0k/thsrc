package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public interface TestRule {
  Statement apply(Statement paramStatement, Description paramDescription);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\TestRule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */