/*     */ package org.junit.rules;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
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
/*     */ public class TemporaryFolder
/*     */   extends ExternalResource
/*     */ {
/*     */   private File folder;
/*     */   
/*     */   protected void before() throws Throwable {
/*  32 */     create();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void after() {
/*  37 */     delete();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void create() throws IOException {
/*  45 */     this.folder = newFolder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File newFile(String fileName) throws IOException {
/*  52 */     File file = new File(getRoot(), fileName);
/*  53 */     file.createNewFile();
/*  54 */     return file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File newFile() throws IOException {
/*  61 */     return File.createTempFile("junit", null, this.folder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File newFolder(String... folderNames) {
/*  68 */     File file = getRoot();
/*  69 */     for (String folderName : folderNames) {
/*  70 */       file = new File(file, folderName);
/*  71 */       file.mkdir();
/*     */     } 
/*  73 */     return file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File newFolder() throws IOException {
/*  81 */     File createdFolder = File.createTempFile("junit", "", this.folder);
/*  82 */     createdFolder.delete();
/*  83 */     createdFolder.mkdir();
/*  84 */     return createdFolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getRoot() {
/*  91 */     if (this.folder == null) {
/*  92 */       throw new IllegalStateException("the temporary folder has not yet been created");
/*     */     }
/*  94 */     return this.folder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete() {
/* 103 */     recursiveDelete(this.folder);
/*     */   }
/*     */   
/*     */   private void recursiveDelete(File file) {
/* 107 */     File[] files = file.listFiles();
/* 108 */     if (files != null)
/* 109 */       for (File each : files)
/* 110 */         recursiveDelete(each);  
/* 111 */     file.delete();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\TemporaryFolder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */