package filegenerator.filegenerator.api;

import filegenerator.filegenerator.api.model.Template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TheSadlig
 */
public class TemplateListHelper {

    private static final String EXTENSION = ".tpl";

    private TemplateListHelper() {
        throw new IllegalStateException("This class is static and should never be instantiated");
    }

    public static List<Template> listAllTemplates(File folder) {
        ArrayList<Template> templates = new ArrayList<>();

        File[] files = folder.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                templates.addAll(listAllTemplates(f));
            } else if (f.getName().endsWith(EXTENSION)) {
                templates.add(new Template(f));
            }
        }
        return templates;
    }

    public static List<Template> listAllTemplates(String path) {

        File folder = new File(path);
        return listAllTemplates(folder);
    }
}
