public class StringBufferUtils {
    public static int getSizeWithoutNull(String str, int strSize) {
        byte[] strToBytes = str.getBytes();
        for (int i = 0; i < strSize; i++) {
            if (strToBytes[i] == 0) {
                return i;
            }
        }
        return strSize;
    }
    public static String trimNullSpaces(String str) {
        return str.substring(0, getSizeWithoutNull(str, 1024));
    }
}
