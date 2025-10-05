/*    */ package com.mrzak34.thunderhack.util.shaders;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ 
/*    */ public class Shader
/*    */ {
/*    */   protected int program;
/*    */   protected Map<String, Integer> uniformsMap;
/*    */   
/*    */   public Shader(String fragmentShader) {
/*    */     int vertexShaderID, fragmentShaderID;
/*    */     try {
/* 20 */       InputStream vertexStream = getClass().getResourceAsStream("/assets/gamesense/shaders/vertex.vert");
/* 21 */       vertexShaderID = createShader(IOUtils.toString(vertexStream), 35633);
/* 22 */       IOUtils.closeQuietly(vertexStream);
/* 23 */       InputStream fragmentStream = getClass().getResourceAsStream("/assets/gamesense/fragment/" + fragmentShader);
/* 24 */       fragmentShaderID = createShader(IOUtils.toString(fragmentStream), 35632);
/* 25 */       IOUtils.closeQuietly(fragmentStream);
/* 26 */     } catch (Exception e) {
/* 27 */       e.printStackTrace();
/*    */       return;
/*    */     } 
/* 30 */     if (vertexShaderID == 0 || fragmentShaderID == 0) {
/*    */       return;
/*    */     }
/* 33 */     this.program = ARBShaderObjects.glCreateProgramObjectARB();
/* 34 */     if (this.program == 0) {
/*    */       return;
/*    */     }
/* 37 */     ARBShaderObjects.glAttachObjectARB(this.program, vertexShaderID);
/* 38 */     ARBShaderObjects.glAttachObjectARB(this.program, fragmentShaderID);
/* 39 */     ARBShaderObjects.glLinkProgramARB(this.program);
/* 40 */     ARBShaderObjects.glValidateProgramARB(this.program);
/*    */   }
/*    */   
/*    */   public void startShader(float duplicate) {
/* 44 */     GL11.glPushMatrix();
/* 45 */     GL20.glUseProgram(this.program);
/* 46 */     if (this.uniformsMap == null) {
/* 47 */       this.uniformsMap = new HashMap<>();
/* 48 */       setupUniforms();
/*    */     } 
/* 50 */     updateUniforms(duplicate);
/*    */   }
/*    */   
/*    */   public void stopShader() {
/* 54 */     GL20.glUseProgram(0);
/* 55 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setupUniforms() {}
/*    */ 
/*    */   
/*    */   public void updateUniforms(float duplicate) {}
/*    */ 
/*    */   
/*    */   private int createShader(String shaderSource, int shaderType) {
/* 67 */     int shader = 0;
/*    */     try {
/* 69 */       shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
/* 70 */       if (shader == 0) {
/* 71 */         return 0;
/*    */       }
/* 73 */       ARBShaderObjects.glShaderSourceARB(shader, shaderSource);
/* 74 */       ARBShaderObjects.glCompileShaderARB(shader);
/* 75 */       if (ARBShaderObjects.glGetObjectParameteriARB(shader, 35713) == 0) {
/* 76 */         throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
/*    */       }
/* 78 */       return shader;
/* 79 */     } catch (Exception e) {
/* 80 */       ARBShaderObjects.glDeleteObjectARB(shader);
/* 81 */       throw e;
/*    */     } 
/*    */   }
/*    */   
/*    */   private String getLogInfo(int i) {
/* 86 */     return ARBShaderObjects.glGetInfoLogARB(i, ARBShaderObjects.glGetObjectParameteriARB(i, 35716));
/*    */   }
/*    */   
/*    */   public void setUniform(String uniformName, int location) {
/* 90 */     this.uniformsMap.put(uniformName, Integer.valueOf(location));
/*    */   }
/*    */   
/*    */   public void setupUniform(String uniformName) {
/* 94 */     setUniform(uniformName, GL20.glGetUniformLocation(this.program, uniformName));
/*    */   }
/*    */   
/*    */   public int getUniform(String uniformName) {
/* 98 */     return ((Integer)this.uniformsMap.get(uniformName)).intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\Shader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */