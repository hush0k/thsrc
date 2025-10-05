package junit.runner;

public interface TestRunListener {
  public static final int STATUS_ERROR = 1;
  
  public static final int STATUS_FAILURE = 2;
  
  void testRunStarted(String paramString, int paramInt);
  
  void testRunEnded(long paramLong);
  
  void testRunStopped(long paramLong);
  
  void testStarted(String paramString);
  
  void testEnded(String paramString);
  
  void testFailed(int paramInt, String paramString1, String paramString2);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\runner\TestRunListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */