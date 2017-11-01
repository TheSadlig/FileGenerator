package filegenerator.filegenerator.api.model;

import java.io.File;

/**
 *
 * @author TheSadlig
 */
public class Template {

    File template;

    public Template(String path) {
        this(new File(path));
    }

    public Template(File file) {
        template = file;
    }

    public String getTemplatePath() {
        return template.getAbsolutePath();
    }

    public String getTemplateName() {
        return template.getName();
    }
}
