package filegenerator.filegenerator.api.model;

import java.io.File;

/**
 *
 * @author TheSadlig
 */
public class Template {

    File templateFile;

    public Template(String path) {
        this(new File(path));
    }

    public Template(File file) {
        templateFile = file;
    }

    public String getTemplatePath() {
        return templateFile.getAbsolutePath();
    }

    public String getTemplateName() {
        return templateFile.getName();
    }
}
