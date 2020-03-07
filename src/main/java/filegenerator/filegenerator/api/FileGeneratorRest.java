package filegenerator.filegenerator.api;

import filegenerator.filegenerator.FileGenerator;
import filegenerator.filegenerator.StringUtils;
import filegenerator.filegenerator.api.model.ExecutionParameters;
import spark.Spark;

import org.json.JSONObject;

import java.io.File;

/**
 *
 * @author TheSadlig
 */
public class FileGeneratorRest {

    public static void main(String[] args) {

        Spark.after((req, res) -> {
            res.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Headers", "*");

            res.type("application/json");
        });

        Spark.get("/templates/:path/generate/:variables", (req, res) -> {
            String inputFile = req.params(":path");

            String inputTemplate = StringUtils.readFile(inputFile);

            ExecutionParameters executionParameters = new ExecutionParameters();

            File f = new File(inputFile);

            executionParameters.setPath(f.getAbsoluteFile().getParentFile().getAbsolutePath());
            executionParameters.addTemplatesContent(inputFile, inputTemplate);

            return FileGenerator.execute(inputFile, executionParameters);
        }, JsonUtil.json());

        // List all the templates in the path
        Spark.get("/templates/:path/list", (req, res) -> {
            String lookUpPath = req.params(":path");

            return TemplateListHelper.listAllTemplates(lookUpPath);
        }, JsonUtil.json());

        Spark.post("/templates/preview", (req, res) -> {

            JSONObject params = new JSONObject(req.body());
            String inputTemplate = params.getString("inputTemplate");

            ExecutionParameters executionParameters = new ExecutionParameters();

            executionParameters.setPath("./");
            executionParameters.addTemplatesContent("main", inputTemplate);

            return FileGenerator.execute("main", executionParameters);
        }, JsonUtil.json());

    }

}
