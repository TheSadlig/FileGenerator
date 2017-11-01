package filegenerator.filegenerator;

import filegenerator.execution.Environnement;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author TheSadlig
 */
public class StringUtils {

    public static String readFile(String path) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(path);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[16 * 1024];
            int len = 0;

            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }

            String message = baos.toString();
            baos.close();

            return message;
        } finally {
            is.close();
        }
    }

    public static void writeFile(String path, String content) throws FileNotFoundException, IOException {
        File output = new File(path);
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(content.getBytes());
        fos.close();
    }

    public static String generateRandomString(long stringSize, String alphabet) {
        String resultingString = "";
        for (long i = 0; i < stringSize; ++i) {
            resultingString += getRandomChar(alphabet);
        }
        return resultingString;
    }

    public static char getRandomChar(String alphabet) {
        Environnement env = Environnement.getEnvironenement();
        return alphabet.charAt(env.rand.nextInt(alphabet.length()));
    }

}
