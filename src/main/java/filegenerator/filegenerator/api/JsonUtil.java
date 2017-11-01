package filegenerator.filegenerator.api;

import filegenerator.filegenerator.api.model.ExecutionResult;
import filegenerator.filegenerator.api.model.Template;
import spark.ResponseTransformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 *
 * @author TheSadlig
 */
public class JsonUtil {

    private final static Logger LOGGER = LogManager.getLogger(JsonUtil.class.getSimpleName());

    public static String toJson(ExecutionResult result) {
        LOGGER.trace("Converting the ExecutionResult to JSON");

        try {
            ObjectMapper o = new ObjectMapper();

            return o.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Could not create the JSON from the ExecutionResult", ex);
            return "{ }";
        }
    }

    public static String toJson(List<Template> templates) {
        JSONArray jsonArray = new JSONArray();
        for (Template t : templates) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.append("templateName", t.getTemplateName());
            jsonObject.append("templatePath", t.getTemplatePath());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    public static String toJson(Object object) {
        if (object instanceof ExecutionResult) {
            ExecutionResult execResult = (ExecutionResult) object;
            return toJson(execResult);
        } else if (object instanceof List<?>) {
            List<Template> templates = (List<Template>) object;
            return toJson(templates);
        }
        return "{ 'error' : 'Object cannot be transformed to JSON' }";
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}
