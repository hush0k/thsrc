/*     */ package org.junit.experimental.categories;
/*     */ 
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runner.manipulation.Filter;
/*     */ import org.junit.runner.manipulation.NoTestsRemainException;
/*     */ import org.junit.runners.Suite;
/*     */ import org.junit.runners.model.InitializationError;
/*     */ import org.junit.runners.model.RunnerBuilder;
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
/*     */ public class Categories
/*     */   extends Suite
/*     */ {
/*     */   public static class CategoryFilter
/*     */     extends Filter
/*     */   {
/*     */     private final Class<?> fIncluded;
/*     */     private final Class<?> fExcluded;
/*     */     
/*     */     public static CategoryFilter include(Class<?> categoryType) {
/*  82 */       return new CategoryFilter(categoryType, null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CategoryFilter(Class<?> includedCategory, Class<?> excludedCategory) {
/*  91 */       this.fIncluded = includedCategory;
/*  92 */       this.fExcluded = excludedCategory;
/*     */     }
/*     */ 
/*     */     
/*     */     public String describe() {
/*  97 */       return "category " + this.fIncluded;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldRun(Description description) {
/* 102 */       if (hasCorrectCategoryAnnotation(description))
/* 103 */         return true; 
/* 104 */       for (Description each : description.getChildren()) {
/* 105 */         if (shouldRun(each))
/* 106 */           return true; 
/* 107 */       }  return false;
/*     */     }
/*     */     
/*     */     private boolean hasCorrectCategoryAnnotation(Description description) {
/* 111 */       List<Class<?>> categories = categories(description);
/* 112 */       if (categories.isEmpty())
/* 113 */         return (this.fIncluded == null); 
/* 114 */       for (Class<?> each : categories) {
/* 115 */         if (this.fExcluded != null && this.fExcluded.isAssignableFrom(each))
/* 116 */           return false; 
/* 117 */       }  for (Class<?> each : categories) {
/* 118 */         if (this.fIncluded == null || this.fIncluded.isAssignableFrom(each))
/* 119 */           return true; 
/* 120 */       }  return false;
/*     */     }
/*     */     
/*     */     private List<Class<?>> categories(Description description) {
/* 124 */       ArrayList<Class<?>> categories = new ArrayList<Class<?>>();
/* 125 */       categories.addAll(Arrays.asList(directCategories(description)));
/* 126 */       categories.addAll(Arrays.asList(directCategories(parentDescription(description))));
/* 127 */       return categories;
/*     */     }
/*     */     
/*     */     private Description parentDescription(Description description) {
/* 131 */       Class<?> testClass = description.getTestClass();
/* 132 */       if (testClass == null)
/* 133 */         return null; 
/* 134 */       return Description.createSuiteDescription(testClass);
/*     */     }
/*     */     
/*     */     private Class<?>[] directCategories(Description description) {
/* 138 */       if (description == null)
/* 139 */         return new Class[0]; 
/* 140 */       Category annotation = (Category)description.getAnnotation(Category.class);
/* 141 */       if (annotation == null)
/* 142 */         return new Class[0]; 
/* 143 */       return annotation.value();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Categories(Class<?> klass, RunnerBuilder builder) throws InitializationError {
/* 149 */     super(klass, builder);
/*     */     try {
/* 151 */       filter(new CategoryFilter(getIncludedCategory(klass), getExcludedCategory(klass)));
/*     */     }
/* 153 */     catch (NoTestsRemainException e) {
/* 154 */       throw new InitializationError(e);
/*     */     } 
/* 156 */     assertNoCategorizedDescendentsOfUncategorizeableParents(getDescription());
/*     */   }
/*     */   
/*     */   private Class<?> getIncludedCategory(Class<?> klass) {
/* 160 */     IncludeCategory annotation = klass.<IncludeCategory>getAnnotation(IncludeCategory.class);
/* 161 */     return (annotation == null) ? null : annotation.value();
/*     */   }
/*     */   
/*     */   private Class<?> getExcludedCategory(Class<?> klass) {
/* 165 */     ExcludeCategory annotation = klass.<ExcludeCategory>getAnnotation(ExcludeCategory.class);
/* 166 */     return (annotation == null) ? null : annotation.value();
/*     */   }
/*     */   
/*     */   private void assertNoCategorizedDescendentsOfUncategorizeableParents(Description description) throws InitializationError {
/* 170 */     if (!canHaveCategorizedChildren(description))
/* 171 */       assertNoDescendantsHaveCategoryAnnotations(description); 
/* 172 */     for (Description each : description.getChildren())
/* 173 */       assertNoCategorizedDescendentsOfUncategorizeableParents(each); 
/*     */   }
/*     */   
/*     */   private void assertNoDescendantsHaveCategoryAnnotations(Description description) throws InitializationError {
/* 177 */     for (Description each : description.getChildren()) {
/* 178 */       if (each.getAnnotation(Category.class) != null)
/* 179 */         throw new InitializationError("Category annotations on Parameterized classes are not supported on individual methods."); 
/* 180 */       assertNoDescendantsHaveCategoryAnnotations(each);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canHaveCategorizedChildren(Description description) {
/* 187 */     for (Description each : description.getChildren()) {
/* 188 */       if (each.getTestClass() == null)
/* 189 */         return false; 
/* 190 */     }  return true;
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   public static @interface ExcludeCategory {
/*     */     Class<?> value();
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   public static @interface IncludeCategory {
/*     */     Class<?> value();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\categories\Categories.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */