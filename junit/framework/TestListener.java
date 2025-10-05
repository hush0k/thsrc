package junit.framework;

public interface TestListener {
  void addError(Test paramTest, Throwable paramThrowable);
  
  void addFailure(Test paramTest, AssertionFailedError paramAssertionFailedError);
  
  void endTest(Test paramTest);
  
  void startTest(Test paramTest);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\framework\TestListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */