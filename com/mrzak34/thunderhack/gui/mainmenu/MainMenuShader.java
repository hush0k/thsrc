/*    */ package com.mrzak34.thunderhack.gui.mainmenu;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class MainMenuShader
/*    */ {
/*    */   private final int programId;
/*    */   private final int timeUniform;
/*    */   private final int mouseUniform;
/*    */   private final int resolutionUniform;
/*    */   
/*    */   public MainMenuShader(String fragmentShaderLocation) throws IOException {
/* 17 */     int program = GL20.glCreateProgram();
/* 18 */     GL20.glAttachShader(program, createShader(MainMenuShader.class.getResourceAsStream("/passthrough.vsh"), 35633));
/* 19 */     GL20.glAttachShader(program, createShader(MainMenuShader.class.getResourceAsStream(fragmentShaderLocation), 35632));
/* 20 */     GL20.glLinkProgram(program);
/* 21 */     int linked = GL20.glGetProgrami(program, 35714);
/* 22 */     if (linked == 0) {
/* 23 */       System.err.println(GL20.glGetProgramInfoLog(program, GL20.glGetProgrami(program, 35716)));
/* 24 */       throw new IllegalStateException("Shader failed to link");
/*    */     } 
/* 26 */     this.programId = program;
/* 27 */     GL20.glUseProgram(program);
/* 28 */     this.timeUniform = GL20.glGetUniformLocation(program, "time");
/* 29 */     this.mouseUniform = GL20.glGetUniformLocation(program, "mouse");
/* 30 */     this.resolutionUniform = GL20.glGetUniformLocation(program, "resolution");
/* 31 */     GL20.glUseProgram(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void useShader(int width, int height, float mouseX, float mouseY, float time) {
/* 36 */     GL20.glUseProgram(this.programId);
/* 37 */     GL20.glUniform2f(this.resolutionUniform, width, height);
/* 38 */     GL20.glUniform2f(this.mouseUniform, mouseX / width, 1.0F - mouseY / height);
/* 39 */     GL20.glUniform1f(this.timeUniform, time);
/*    */   }
/*    */   
/*    */   public int createShader(InputStream inputStream, int shaderType) throws IOException {
/* 43 */     int shader = GL20.glCreateShader(shaderType);
/* 44 */     GL20.glShaderSource(shader, readStreamToString(inputStream));
/* 45 */     GL20.glCompileShader(shader);
/* 46 */     int compiled = GL20.glGetShaderi(shader, 35713);
/* 47 */     if (compiled == 0) {
/* 48 */       System.err.println(GL20.glGetShaderInfoLog(shader, GL20.glGetShaderi(shader, 35716)));
/* 49 */       throw new IllegalStateException("Failed to compile shader");
/*    */     } 
/* 51 */     return shader;
/*    */   }
/*    */ 
/*    */   
/*    */   public String readStreamToString(InputStream inputStream) throws IOException {
/* 56 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 57 */     byte[] buffer = new byte[512];
/*    */     
/*    */     int read;
/* 60 */     while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
/* 61 */       out.write(buffer, 0, read);
/*    */     }
/*    */     
/* 64 */     return new String(out.toByteArray(), StandardCharsets.UTF_8);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\mainmenu\MainMenuShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */