/*     */ package com.mrzak34.thunderhack.util.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ public class ShaderUtil {
/*  16 */   private final String roundedRectGradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius))) * color1.a;\n    gl_FragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);\n}";
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
/*     */   private final int programID;
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
/*  43 */   private final String roundedRect = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\nuniform bool blur;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    // Smooth the result (free antialiasing).\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n\n}";
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
/*  64 */   private final String blurShader = "#version 120\n\nuniform sampler2D textureIn;\nuniform vec2 texelSize, direction;\nuniform float radius;\nuniform float weights[256];\n\n#define offset texelSize * direction\n\nvoid main() {\n    vec3 blr = texture2D(textureIn, gl_TexCoord[0].st).rgb * weights[0];\n\n    for (float f = 1.0; f <= radius; f++) {\n        blr += texture2D(textureIn, gl_TexCoord[0].st + f * offset).rgb * (weights[int(abs(f))]);\n        blr += texture2D(textureIn, gl_TexCoord[0].st - f * offset).rgb * (weights[int(abs(f))]);\n    }\n\n    gl_FragColor = vec4(blr, 1.0);\n}";
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
/*     */   public ShaderUtil(String fragmentShaderLoc, String vertexShaderLoc) {
/*  86 */     int program = GL20.glCreateProgram();
/*     */     try {
/*     */       int fragmentShaderID;
/*  89 */       switch (fragmentShaderLoc) {
/*     */         case "roundedRect":
/*  91 */           fragmentShaderID = createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\nuniform bool blur;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    // Smooth the result (free antialiasing).\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n\n}".getBytes()), 35632);
/*     */           break;
/*     */         case "roundedRectGradient":
/*  94 */           fragmentShaderID = createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius))) * color1.a;\n    gl_FragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);\n}".getBytes()), 35632);
/*     */           break;
/*     */         case "blurShader":
/*  97 */           fragmentShaderID = createShader(new ByteArrayInputStream("#version 120\n\nuniform sampler2D textureIn;\nuniform vec2 texelSize, direction;\nuniform float radius;\nuniform float weights[256];\n\n#define offset texelSize * direction\n\nvoid main() {\n    vec3 blr = texture2D(textureIn, gl_TexCoord[0].st).rgb * weights[0];\n\n    for (float f = 1.0; f <= radius; f++) {\n        blr += texture2D(textureIn, gl_TexCoord[0].st + f * offset).rgb * (weights[int(abs(f))]);\n        blr += texture2D(textureIn, gl_TexCoord[0].st - f * offset).rgb * (weights[int(abs(f))]);\n    }\n\n    gl_FragColor = vec4(blr, 1.0);\n}".getBytes()), 35632);
/*     */           break;
/*     */         default:
/* 100 */           fragmentShaderID = createShader(Util.mc.func_110442_L().func_110536_a(new ResourceLocation(fragmentShaderLoc)).func_110527_b(), 35632);
/*     */           break;
/*     */       } 
/* 103 */       GL20.glAttachShader(program, fragmentShaderID);
/* 104 */       int vertexShaderID = createShader(Util.mc.func_110442_L().func_110536_a(new ResourceLocation(vertexShaderLoc)).func_110527_b(), 35633);
/* 105 */       GL20.glAttachShader(program, vertexShaderID);
/*     */     }
/* 107 */     catch (IOException e) {
/* 108 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 111 */     GL20.glLinkProgram(program);
/* 112 */     int status = GL20.glGetProgrami(program, 35714);
/*     */     
/* 114 */     if (status == 0) {
/* 115 */       throw new IllegalStateException("Shader failed to link!");
/*     */     }
/* 117 */     this.programID = program;
/*     */   }
/*     */   
/*     */   public ShaderUtil(String fragmentShaderLoc) {
/* 121 */     this(fragmentShaderLoc, "textures/vertex.vsh");
/*     */   }
/*     */   
/*     */   public static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius, ShaderUtil roundedTexturedShader) {
/* 125 */     ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
/* 126 */     roundedTexturedShader.setUniformf("location", new float[] { x * sr.func_78325_e(), (Minecraft.func_71410_x()).field_71440_d - height * sr.func_78325_e() - y * sr.func_78325_e() });
/* 127 */     roundedTexturedShader.setUniformf("rectSize", new float[] { width * sr.func_78325_e(), height * sr.func_78325_e() });
/* 128 */     roundedTexturedShader.setUniformf("radius", new float[] { radius * sr.func_78325_e() });
/*     */   }
/*     */   
/*     */   public static void drawQuads(float x, float y, float width, float height) {
/* 132 */     GL11.glBegin(7);
/* 133 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 134 */     GL11.glVertex2f(x, y);
/* 135 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 136 */     GL11.glVertex2f(x, y + height);
/* 137 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 138 */     GL11.glVertex2f(x + width, y + height);
/* 139 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 140 */     GL11.glVertex2f(x + width, y);
/* 141 */     GL11.glEnd();
/*     */   }
/*     */   
/*     */   public static void drawQuads() {
/* 145 */     ScaledResolution sr = new ScaledResolution(Util.mc);
/* 146 */     float width = (float)sr.func_78327_c();
/* 147 */     float height = (float)sr.func_78324_d();
/* 148 */     GL11.glBegin(7);
/* 149 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 150 */     GL11.glVertex2f(0.0F, 0.0F);
/* 151 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 152 */     GL11.glVertex2f(0.0F, height);
/* 153 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 154 */     GL11.glVertex2f(width, height);
/* 155 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 156 */     GL11.glVertex2f(width, 0.0F);
/* 157 */     GL11.glEnd();
/*     */   }
/*     */   
/*     */   public static String readInputStream(InputStream inputStream) {
/* 161 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/*     */     try {
/* 164 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
/*     */       String line;
/* 166 */       while ((line = bufferedReader.readLine()) != null) {
/* 167 */         stringBuilder.append(line).append('\n');
/*     */       }
/* 169 */     } catch (Exception e) {
/* 170 */       e.printStackTrace();
/*     */     } 
/* 172 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public void init() {
/* 176 */     GL20.glUseProgram(this.programID);
/*     */   }
/*     */   
/*     */   public void unload() {
/* 180 */     GL20.glUseProgram(0);
/*     */   }
/*     */   
/*     */   public int getUniform(String name) {
/* 184 */     return GL20.glGetUniformLocation(this.programID, name);
/*     */   }
/*     */   
/*     */   public void setUniformf(String name, float... args) {
/* 188 */     int loc = GL20.glGetUniformLocation(this.programID, name);
/* 189 */     switch (args.length) {
/*     */       case 1:
/* 191 */         GL20.glUniform1f(loc, args[0]);
/*     */         break;
/*     */       case 2:
/* 194 */         GL20.glUniform2f(loc, args[0], args[1]);
/*     */         break;
/*     */       case 3:
/* 197 */         GL20.glUniform3f(loc, args[0], args[1], args[2]);
/*     */         break;
/*     */       case 4:
/* 200 */         GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setUniformi(String name, int... args) {
/* 206 */     int loc = GL20.glGetUniformLocation(this.programID, name);
/* 207 */     if (args.length > 1) { GL20.glUniform2i(loc, args[0], args[1]); }
/* 208 */     else { GL20.glUniform1i(loc, args[0]); }
/*     */   
/*     */   }
/*     */   private int createShader(InputStream inputStream, int shaderType) {
/* 212 */     int shader = GL20.glCreateShader(shaderType);
/* 213 */     GL20.glShaderSource(shader, readInputStream(inputStream));
/* 214 */     GL20.glCompileShader(shader);
/*     */     
/* 216 */     if (GL20.glGetShaderi(shader, 35713) == 0) {
/* 217 */       System.out.println(GL20.glGetShaderInfoLog(shader, 4096));
/* 218 */       throw new IllegalStateException(String.format("Shader (%s) failed to compile!", new Object[] { Integer.valueOf(shaderType) }));
/*     */     } 
/*     */     
/* 221 */     return shader;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\render\ShaderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */