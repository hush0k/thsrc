import java.util.Arrays;
/*    */ import net.minecraft.client.main.Main;
/*    */
/*    */
/*    */
/*    */ public class Start
        /*    */ {
    /*    */   public static void main(String[] args) {
        /*  9 */     String assets = System.getenv().containsKey("assetDirectory") ? System.getenv("assetDirectory") : "assets";
        /* 10 */     Main.main(concat(new String[] { "--version", "mcp", "--accessToken", "0", "--assetsDir", assets, "--assetIndex", "1.12", "--userProperties", "{}" }, args));
        /*    */   }
    /*    */
    /*    */
    /*    */   public static <T> T[] concat(T[] first, T[] second) {
        /* 15 */     T[] result = Arrays.copyOf(first, first.length + second.length);
        /* 16 */     System.arraycopy(second, 0, result, first.length, second.length);
        /* 17 */     return result;
        /*    */   }
    /*    */ }