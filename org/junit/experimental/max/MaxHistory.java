/*     */ package org.junit.experimental.max;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runner.Result;
/*     */ import org.junit.runner.notification.Failure;
/*     */ import org.junit.runner.notification.RunListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MaxHistory
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public static MaxHistory forFolder(File file) {
/*  34 */     if (file.exists())
/*     */       try {
/*  36 */         return readHistory(file);
/*  37 */       } catch (CouldNotReadCoreException e) {
/*  38 */         e.printStackTrace();
/*  39 */         file.delete();
/*     */       }  
/*  41 */     return new MaxHistory(file);
/*     */   }
/*     */ 
/*     */   
/*     */   private static MaxHistory readHistory(File storedResults) throws CouldNotReadCoreException {
/*     */     try {
/*  47 */       FileInputStream file = new FileInputStream(storedResults);
/*     */       try {
/*  49 */         ObjectInputStream stream = new ObjectInputStream(file);
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/*  56 */         file.close();
/*     */       } 
/*  58 */     } catch (Exception e) {
/*  59 */       throw new CouldNotReadCoreException(e);
/*     */     } 
/*     */   }
/*     */   
/*  63 */   private final Map<String, Long> fDurations = new HashMap<String, Long>();
/*     */   
/*  65 */   private final Map<String, Long> fFailureTimestamps = new HashMap<String, Long>();
/*     */   
/*     */   private final File fHistoryStore;
/*     */   
/*     */   private MaxHistory(File storedResults) {
/*  70 */     this.fHistoryStore = storedResults;
/*     */   }
/*     */   
/*     */   private void save() throws IOException {
/*  74 */     ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(this.fHistoryStore));
/*     */     
/*  76 */     stream.writeObject(this);
/*  77 */     stream.close();
/*     */   }
/*     */   
/*     */   Long getFailureTimestamp(Description key) {
/*  81 */     return this.fFailureTimestamps.get(key.toString());
/*     */   }
/*     */   
/*     */   void putTestFailureTimestamp(Description key, long end) {
/*  85 */     this.fFailureTimestamps.put(key.toString(), Long.valueOf(end));
/*     */   }
/*     */   
/*     */   boolean isNewTest(Description key) {
/*  89 */     return !this.fDurations.containsKey(key.toString());
/*     */   }
/*     */   
/*     */   Long getTestDuration(Description key) {
/*  93 */     return this.fDurations.get(key.toString());
/*     */   }
/*     */   
/*     */   void putTestDuration(Description description, long duration) {
/*  97 */     this.fDurations.put(description.toString(), Long.valueOf(duration));
/*     */   }
/*     */   
/*     */   private final class RememberingListener extends RunListener {
/* 101 */     private long overallStart = System.currentTimeMillis();
/*     */     
/* 103 */     private Map<Description, Long> starts = new HashMap<Description, Long>();
/*     */ 
/*     */     
/*     */     public void testStarted(Description description) throws Exception {
/* 107 */       this.starts.put(description, Long.valueOf(System.nanoTime()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void testFinished(Description description) throws Exception {
/* 113 */       long end = System.nanoTime();
/* 114 */       long start = ((Long)this.starts.get(description)).longValue();
/* 115 */       MaxHistory.this.putTestDuration(description, end - start);
/*     */     }
/*     */ 
/*     */     
/*     */     public void testFailure(Failure failure) throws Exception {
/* 120 */       MaxHistory.this.putTestFailureTimestamp(failure.getDescription(), this.overallStart);
/*     */     }
/*     */ 
/*     */     
/*     */     public void testRunFinished(Result result) throws Exception {
/* 125 */       MaxHistory.this.save();
/*     */     }
/*     */     
/*     */     private RememberingListener() {} }
/*     */   
/*     */   private class TestComparator implements Comparator<Description> {
/*     */     public int compare(Description o1, Description o2) {
/* 132 */       if (MaxHistory.this.isNewTest(o1))
/* 133 */         return -1; 
/* 134 */       if (MaxHistory.this.isNewTest(o2)) {
/* 135 */         return 1;
/*     */       }
/* 137 */       int result = getFailure(o2).compareTo(getFailure(o1));
/* 138 */       return (result != 0) ? result : MaxHistory.this.getTestDuration(o1).compareTo(MaxHistory.this.getTestDuration(o2));
/*     */     }
/*     */     
/*     */     private TestComparator() {}
/*     */     
/*     */     private Long getFailure(Description key) {
/* 144 */       Long result = MaxHistory.this.getFailureTimestamp(key);
/* 145 */       if (result == null)
/* 146 */         return Long.valueOf(0L); 
/* 147 */       return result;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RunListener listener() {
/* 156 */     return new RememberingListener();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparator<Description> testComparator() {
/* 164 */     return new TestComparator();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\max\MaxHistory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */