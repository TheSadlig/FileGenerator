package filegenerator.filegenerator;

import filegenerator.execution.Environnement;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author TheSadlig
 */
public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("This class is static and should never be instantiated");
    }

    public static String readFile(String path) throws IOException {
        try (InputStream is = new FileInputStream(path)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[16 * 1024];
            int len = 0;

            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }

            String message = baos.toString();
            baos.close();

            return message;
        }
    }

    public static void writeFile(String path, String content) throws IOException {
        File output = new File(path);
        try (FileOutputStream fos = new FileOutputStream(output)) {
            fos.write(content.getBytes());
        }
    }

    public static String generateRandomString(long stringSize, String alphabet) {
        StringBuilder resultingString = new StringBuilder();
        for (long i = 0; i < stringSize; ++i) {
            resultingString.append(getRandomChar(alphabet));
        }
        return resultingString.toString();
    }

    public static char getRandomChar(String alphabet) {
        return alphabet.charAt(Environnement.rand.nextInt(alphabet.length()));
    }

}
