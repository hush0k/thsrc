/*     */ package org.junit.runner;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ 
/*     */ public class Description
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public static Description createSuiteDescription(String name, Annotation... annotations) {
/*  39 */     if (name.length() == 0)
/*  40 */       throw new IllegalArgumentException("name must have non-zero length"); 
/*  41 */     return new Description(name, annotations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Description createTestDescription(Class<?> clazz, String name, Annotation... annotations) {
/*  53 */     return new Description(String.format("%s(%s)", new Object[] { name, clazz.getName() }), annotations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Description createTestDescription(Class<?> clazz, String name) {
/*  65 */     return createTestDescription(clazz, name, new Annotation[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Description createSuiteDescription(Class<?> testClass) {
/*  74 */     return new Description(testClass.getName(), testClass.getAnnotations());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final Description EMPTY = new Description("No Tests", new Annotation[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public static final Description TEST_MECHANISM = new Description("Test mechanism", new Annotation[0]);
/*     */   
/*  89 */   private final ArrayList<Description> fChildren = new ArrayList<Description>();
/*     */   
/*     */   private final String fDisplayName;
/*     */   private final Annotation[] fAnnotations;
/*     */   
/*     */   private Description(String displayName, Annotation... annotations) {
/*  95 */     this.fDisplayName = displayName;
/*  96 */     this.fAnnotations = annotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 103 */     return this.fDisplayName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(Description description) {
/* 111 */     getChildren().add(description);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Description> getChildren() {
/* 118 */     return this.fChildren;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSuite() {
/* 125 */     return !isTest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTest() {
/* 132 */     return getChildren().isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int testCount() {
/* 139 */     if (isTest())
/* 140 */       return 1; 
/* 141 */     int result = 0;
/* 142 */     for (Description child : getChildren())
/* 143 */       result += child.testCount(); 
/* 144 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 149 */     return getDisplayName().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 154 */     if (!(obj instanceof Description))
/* 155 */       return false; 
/* 156 */     Description d = (Description)obj;
/* 157 */     return getDisplayName().equals(d.getDisplayName());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 162 */     return getDisplayName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 169 */     return equals(EMPTY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Description childlessCopy() {
/* 177 */     return new Description(this.fDisplayName, this.fAnnotations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
/* 185 */     for (Annotation each : this.fAnnotations) {
/* 186 */       if (each.annotationType().equals(annotationType))
/* 187 */         return annotationType.cast(each); 
/* 188 */     }  return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Annotation> getAnnotations() {
/* 195 */     return Arrays.asList(this.fAnnotations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getTestClass() {
/* 203 */     String name = getClassName();
/* 204 */     if (name == null)
/* 205 */       return null; 
/*     */     try {
/* 207 */       return Class.forName(name);
/* 208 */     } catch (ClassNotFoundException e) {
/* 209 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/* 218 */     Matcher matcher = methodStringMatcher();
/* 219 */     return matcher.matches() ? matcher.group(2) : toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMethodName() {
/* 229 */     return parseMethod();
/*     */   }
/*     */   
/*     */   private String parseMethod() {
/* 233 */     Matcher matcher = methodStringMatcher();
/* 234 */     if (matcher.matches())
/* 235 */       return matcher.group(1); 
/* 236 */     return null;
/*     */   }
/*     */   
/*     */   private Matcher methodStringMatcher() {
/* 240 */     return Pattern.compile("(.*)\\((.*)\\)").matcher(toString());
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runner\Description.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */