package filegenerator.api;

import filegenerator.ast.model.Chunk;
import filegenerator.filegenerator.api.JsonUtil;
import filegenerator.filegenerator.api.model.ExecutionResult;
import filegenerator.filegenerator.api.model.Template;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class JsonUtilTest {

    public Map<String, Chunk> createFakeChunks() {
        HashMap<String, Chunk> chunks = new HashMap<>();

        chunks.put("chunkname1", new Chunk("chunkname1", "output1", "Content1"));
        chunks.put("chunkname2", new Chunk("chunkname2", "output2", "Content2"));
        return chunks;
    }

    public Map<String, AbstractTypedVariable> createFakeVariables() {
        HashMap<String, AbstractTypedVariable> variables = new HashMap<>();

        TypedVariable<String> str = new TypedVariable<>();
        str.setValue("TEST1");

        TypedVariable<Boolean> bool = new TypedVariable<>();
        bool.setValue(true);

        TypedVariable<BigDecimal> bd = new TypedVariable<>();
        BigDecimal bigDecimal = new BigDecimal(42.55);
        bd.setValue(bigDecimal.setScale(2, BigDecimal.ROUND_FLOOR));

        variables.put("str", str);
        variables.put("bool", bool);
        variables.put("bd", bd);
        return variables;
    }

    @Test
    public void emptyExecutionResultTest() {
        ExecutionResult res = new ExecutionResult();
        Assert.assertEquals("{ }", JsonUtil.toJson(res));
    }

    @Test
    public void executionResultWithChunksTest() {
        ExecutionResult res = new ExecutionResult();
        res.setChunks(createFakeChunks());
        Assert.assertEquals(
                "{\"chunks\":{\"chunkname1\":{\"chunkName\":\"chunkname1\",\"chunkOutputFolder\":\"output1\",\"chunkContent\":\"Content1\"},\"chunkname2\":{\"chunkName\":\"chunkname2\",\"chunkOutputFolder\":\"output2\",\"chunkContent\":\"Content2\"}}}",
                JsonUtil.toJson(res).replaceAll("[ \n\t]", ""));
    }

    @Test
    public void executionResultFullTest() {
        ExecutionResult res = new ExecutionResult();
        res.setChunks(createFakeChunks());
        res.setOutputVariables(createFakeVariables());
        res.setResultingDotFile("DotFile content");

        String expectedJSON = "{\"resultingDotFile\":\"DotFilecontent\","
                + "\"chunks\":{"
                + "\"chunkname1\":{\"chunkName\":\"chunkname1\",\"chunkOutputFolder\":\"output1\",\"chunkContent\":\"Content1\"},"
                + "\"chunkname2\":{\"chunkName\":\"chunkname2\",\"chunkOutputFolder\":\"output2\",\"chunkContent\":\"Content2\"}},"
                + "\"outputVariables\":{\"str\":{\"value\":\"TEST1\"},"
                + "\"bd\":{\"value\":42.54},"
                + "\"bool\":{\"value\":true}}}";
        Assert.assertEquals(expectedJSON,
                JsonUtil.toJson(res).replaceAll("[ \n\t]", ""));

        Object obj = res;
        Assert.assertEquals(expectedJSON,
                JsonUtil.toJson(obj).replaceAll("[ \n\t]", ""));
    }

    @Test
    public void listTemplateTest() {
        List<Template> templates = new ArrayList<>();
        templates.add(new Template("First/File.tpl"));
        templates.add(new Template("Second/File.tpl"));

        String expectedJSON = "[{\"templateName\":[\"File.tpl\"],\"templatePath\":[\"" + templates.get(0).getTemplatePath() + "\"]},"
                + "{\"templateName\":[\"File.tpl\"],\"templatePath\":[\"" + templates.get(1).getTemplatePath() + "\"]}]";
        Assert.assertEquals(expectedJSON, JsonUtil.toJson(templates).replaceAll("[ \n\t]", ""));

        Object obj = templates;
        Assert.assertEquals(expectedJSON,
                JsonUtil.toJson(obj).replaceAll("[ \n\t]", ""));
    }

    @Test
    public void wrongObjectTest() {

        String expectedJSON = "{ 'error' : 'Object cannot be transformed to JSON' }";

        Object obj = new Long(44);
        Assert.assertEquals(expectedJSON,
                JsonUtil.toJson(obj));
    }
}
