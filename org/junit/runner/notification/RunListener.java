package org.junit.runner.notification;

import org.junit.runner.Description;
import org.junit.runner.Result;

public class RunListener {
  public void testRunStarted(Description description) throws Exception {}
  
  public void testRunFinished(Result result) throws Exception {}
  
  public void testStarted(Description description) throws Exception {}
  
  public void testFinished(Description description) throws Exception {}
  
  public void testFailure(Failure failure) throws Exception {}
  
  public void testAssumptionFailure(Failure failure) {}
  
  public void testIgnored(Description description) throws Exception {}
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runner\notification\RunListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */