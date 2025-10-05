/*     */ package junit.runner;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Properties;
/*     */ import junit.framework.AssertionFailedError;
/*     */ import junit.framework.Test;
/*     */ import junit.framework.TestListener;
/*     */ import junit.framework.TestSuite;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseTestRunner
/*     */   implements TestListener
/*     */ {
/*     */   public static final String SUITE_METHODNAME = "suite";
/*     */   private static Properties fPreferences;
/*  31 */   static int fgMaxMessageLength = 500;
/*     */   
/*     */   static boolean fgFilterStack = true;
/*     */   
/*     */   boolean fLoading = true;
/*     */ 
/*     */   
/*     */   public synchronized void startTest(Test test) {
/*  39 */     testStarted(test.toString());
/*     */   }
/*     */   
/*     */   protected static void setPreferences(Properties preferences) {
/*  43 */     fPreferences = preferences;
/*     */   }
/*     */   
/*     */   protected static Properties getPreferences() {
/*  47 */     if (fPreferences == null) {
/*  48 */       fPreferences = new Properties();
/*  49 */       fPreferences.put("loading", "true");
/*  50 */       fPreferences.put("filterstack", "true");
/*  51 */       readPreferences();
/*     */     } 
/*  53 */     return fPreferences;
/*     */   }
/*     */   
/*     */   public static void savePreferences() throws IOException {
/*  57 */     FileOutputStream fos = new FileOutputStream(getPreferencesFile());
/*     */     try {
/*  59 */       getPreferences().store(fos, "");
/*     */     } finally {
/*  61 */       fos.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setPreference(String key, String value) {
/*  66 */     getPreferences().put(key, value);
/*     */   }
/*     */   
/*     */   public synchronized void endTest(Test test) {
/*  70 */     testEnded(test.toString());
/*     */   }
/*     */   
/*     */   public synchronized void addError(Test test, Throwable t) {
/*  74 */     testFailed(1, test, t);
/*     */   }
/*     */   
/*     */   public synchronized void addFailure(Test test, AssertionFailedError t) {
/*  78 */     testFailed(2, test, (Throwable)t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void testStarted(String paramString);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void testEnded(String paramString);
/*     */ 
/*     */   
/*     */   public abstract void testFailed(int paramInt, Test paramTest, Throwable paramThrowable);
/*     */ 
/*     */   
/*     */   public Test getTest(String suiteClassName) {
/*  94 */     if (suiteClassName.length() <= 0) {
/*  95 */       clearStatus();
/*  96 */       return null;
/*     */     } 
/*  98 */     Class<?> testClass = null;
/*     */     try {
/* 100 */       testClass = loadSuiteClass(suiteClassName);
/* 101 */     } catch (ClassNotFoundException e) {
/* 102 */       String clazz = e.getMessage();
/* 103 */       if (clazz == null)
/* 104 */         clazz = suiteClassName; 
/* 105 */       runFailed("Class not found \"" + clazz + "\"");
/* 106 */       return null;
/* 107 */     } catch (Exception e) {
/* 108 */       runFailed("Error: " + e.toString());
/* 109 */       return null;
/*     */     } 
/* 111 */     Method suiteMethod = null;
/*     */     try {
/* 113 */       suiteMethod = testClass.getMethod("suite", new Class[0]);
/* 114 */     } catch (Exception e) {
/*     */       
/* 116 */       clearStatus();
/* 117 */       return (Test)new TestSuite(testClass);
/*     */     } 
/* 119 */     if (!Modifier.isStatic(suiteMethod.getModifiers())) {
/* 120 */       runFailed("Suite() method must be static");
/* 121 */       return null;
/*     */     } 
/* 123 */     Test test = null;
/*     */     try {
/* 125 */       test = (Test)suiteMethod.invoke(null, (Object[])new Class[0]);
/* 126 */       if (test == null) {
/* 127 */         return test;
/*     */       }
/* 129 */     } catch (InvocationTargetException e) {
/* 130 */       runFailed("Failed to invoke suite():" + e.getTargetException().toString());
/* 131 */       return null;
/*     */     }
/* 133 */     catch (IllegalAccessException e) {
/* 134 */       runFailed("Failed to invoke suite():" + e.toString());
/* 135 */       return null;
/*     */     } 
/*     */     
/* 138 */     clearStatus();
/* 139 */     return test;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String elapsedTimeAsString(long runTime) {
/* 146 */     return NumberFormat.getInstance().format(runTime / 1000.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String processArguments(String[] args) {
/* 154 */     String suiteName = null;
/* 155 */     for (int i = 0; i < args.length; i++) {
/* 156 */       if (args[i].equals("-noloading")) {
/* 157 */         setLoading(false);
/* 158 */       } else if (args[i].equals("-nofilterstack")) {
/* 159 */         fgFilterStack = false;
/* 160 */       } else if (args[i].equals("-c")) {
/* 161 */         if (args.length > i + 1) {
/* 162 */           suiteName = extractClassName(args[i + 1]);
/*     */         } else {
/* 164 */           System.out.println("Missing Test class name");
/* 165 */         }  i++;
/*     */       } else {
/* 167 */         suiteName = args[i];
/*     */       } 
/*     */     } 
/* 170 */     return suiteName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoading(boolean enable) {
/* 177 */     this.fLoading = enable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String extractClassName(String className) {
/* 183 */     if (className.startsWith("Default package for"))
/* 184 */       return className.substring(className.lastIndexOf(".") + 1); 
/* 185 */     return className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String truncate(String s) {
/* 192 */     if (fgMaxMessageLength != -1 && s.length() > fgMaxMessageLength)
/* 193 */       s = s.substring(0, fgMaxMessageLength) + "..."; 
/* 194 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void runFailed(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class<?> loadSuiteClass(String suiteClassName) throws ClassNotFoundException {
/* 207 */     return Class.forName(suiteClassName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearStatus() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean useReloadingTestSuiteLoader() {
/* 217 */     return (getPreference("loading").equals("true") && this.fLoading);
/*     */   }
/*     */   
/*     */   private static File getPreferencesFile() {
/* 221 */     String home = System.getProperty("user.home");
/* 222 */     return new File(home, "junit.properties");
/*     */   }
/*     */   
/*     */   private static void readPreferences() {
/* 226 */     InputStream is = null;
/*     */     try {
/* 228 */       is = new FileInputStream(getPreferencesFile());
/* 229 */       setPreferences(new Properties(getPreferences()));
/* 230 */       getPreferences().load(is);
/* 231 */     } catch (IOException e) {
/*     */       try {
/* 233 */         if (is != null)
/* 234 */           is.close(); 
/* 235 */       } catch (IOException e1) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getPreference(String key) {
/* 241 */     return getPreferences().getProperty(key);
/*     */   }
/*     */   
/*     */   public static int getPreference(String key, int dflt) {
/* 245 */     String value = getPreference(key);
/* 246 */     int intValue = dflt;
/* 247 */     if (value == null)
/* 248 */       return intValue; 
/*     */     try {
/* 250 */       intValue = Integer.parseInt(value);
/* 251 */     } catch (NumberFormatException ne) {}
/*     */     
/* 253 */     return intValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFilteredTrace(Throwable t) {
/* 260 */     StringWriter stringWriter = new StringWriter();
/* 261 */     PrintWriter writer = new PrintWriter(stringWriter);
/* 262 */     t.printStackTrace(writer);
/* 263 */     StringBuffer buffer = stringWriter.getBuffer();
/* 264 */     String trace = buffer.toString();
/* 265 */     return getFilteredTrace(trace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFilteredTrace(String stack) {
/* 272 */     if (showStackRaw()) {
/* 273 */       return stack;
/*     */     }
/* 275 */     StringWriter sw = new StringWriter();
/* 276 */     PrintWriter pw = new PrintWriter(sw);
/* 277 */     StringReader sr = new StringReader(stack);
/* 278 */     BufferedReader br = new BufferedReader(sr);
/*     */     
/*     */     try {
/*     */       String line;
/* 282 */       while ((line = br.readLine()) != null) {
/* 283 */         if (!filterLine(line))
/* 284 */           pw.println(line); 
/*     */       } 
/* 286 */     } catch (Exception IOException) {
/* 287 */       return stack;
/*     */     } 
/* 289 */     return sw.toString();
/*     */   }
/*     */   
/*     */   protected static boolean showStackRaw() {
/* 293 */     return (!getPreference("filterstack").equals("true") || !fgFilterStack);
/*     */   }
/*     */   
/*     */   static boolean filterLine(String line) {
/* 297 */     String[] patterns = { "junit.framework.TestCase", "junit.framework.TestResult", "junit.framework.TestSuite", "junit.framework.Assert.", "junit.swingui.TestRunner", "junit.awtui.TestRunner", "junit.textui.TestRunner", "java.lang.reflect.Method.invoke(" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     for (int i = 0; i < patterns.length; i++) {
/* 308 */       if (line.indexOf(patterns[i]) > 0)
/* 309 */         return true; 
/*     */     } 
/* 311 */     return false;
/*     */   }
/*     */   
/*     */   static {
/* 315 */     fgMaxMessageLength = getPreference("maxmessage", fgMaxMessageLength);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\runner\BaseTestRunner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */