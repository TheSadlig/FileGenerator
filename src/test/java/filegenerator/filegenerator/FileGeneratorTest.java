package filegenerator.filegenerator;

import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.filegenerator.api.model.ExecutionParameters;
import filegenerator.filegenerator.api.model.ExecutionResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class FileGeneratorTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void simpleGenerationTest() throws FileGeneratorException {
        String chunkName = "filename1";
        String inputTemplate = "===" + chunkName + ",./==={{{set,$var1,42}}}<<<var,$var1>>>===END===";

        ExecutionParameters executionParameters = new ExecutionParameters();

        executionParameters.setPath("./");
        executionParameters.addTemplatesContent("template1", inputTemplate);

        ExecutionResult result = FileGenerator.execute("template1", executionParameters);

        Assert.assertEquals(1, result.getChunks().size());
        Assert.assertEquals(chunkName, result.getChunks().get(chunkName).getChunkName());
        Assert.assertEquals("42", result.getChunks().get(chunkName).getChunkContent());
        Assert.assertEquals(0, result.getOutputVariables().size());
    }

    @Test
    public void expectedVariableTest() throws FileGeneratorException {
        String chunkName = "filename1";
        String inputTemplate = "===" + chunkName + ",./==={{{set,$var1,42}}}<<<var,$var1>>>===END===";

        ExecutionParameters executionParameters = new ExecutionParameters();

        executionParameters.addExpectedVariable("var1");
        executionParameters.setPath("./");
        executionParameters.addTemplatesContent("template1", inputTemplate);

        ExecutionResult result = FileGenerator.execute("template1", executionParameters);

        Assert.assertEquals(1, result.getChunks().size());
        Assert.assertEquals(chunkName, result.getChunks().get(chunkName).getChunkName());
        Assert.assertEquals("42", result.getChunks().get(chunkName).getChunkContent());
        Assert.assertEquals(1, result.getOutputVariables().size());
        Assert.assertEquals("42", result.getOutputVariables().get("var1").toString());
    }

    @Test
    public void expectedMultipleVariableTest() throws FileGeneratorException {
        String chunkName = "filename1";
        String inputTemplate = "===" + chunkName
                + ",./==={{{set,$var1,42}}}{{{set,$var2,43}}}{{{set,$var3,44}}}{{{set,$var4,45}}}{{{set,$var5,46}}}<<<var,$var1>>>===END===";

        ExecutionParameters executionParameters = new ExecutionParameters();

        executionParameters.addExpectedVariable("var1");
        executionParameters.addExpectedVariable("var2");
        executionParameters.addExpectedVariable("var3");
        executionParameters.addExpectedVariable("var4");
        executionParameters.addExpectedVariable("var5");
        executionParameters.setPath("./");
        executionParameters.addTemplatesContent("template1", inputTemplate);

        ExecutionResult result = FileGenerator.execute("template1", executionParameters);

        Assert.assertEquals(1, result.getChunks().size());
        Assert.assertEquals(chunkName, result.getChunks().get(chunkName).getChunkName());
        Assert.assertEquals("42", result.getChunks().get(chunkName).getChunkContent());

        Assert.assertEquals(5, result.getOutputVariables().size());
        Assert.assertEquals("42", result.getOutputVariables().get("var1").toString());
        Assert.assertEquals("43", result.getOutputVariables().get("var2").toString());
        Assert.assertEquals("44", result.getOutputVariables().get("var3").toString());
        Assert.assertEquals("45", result.getOutputVariables().get("var4").toString());
        Assert.assertEquals("46", result.getOutputVariables().get("var5").toString());
    }

    @Test
    public void multipleChunksTest() throws FileGeneratorException {
        String chunkName = "filename1";
        String chunkName2 = "filename2";
        String inputTemplate = "===" + chunkName + ",./==={{{set,$var1,42}}}<<<var,$var1>>>===END=== "
                + "===" + chunkName2 + ",./==={{{set,$var2,1337}}}<<<var,$var2>>>===END===";

        ExecutionParameters executionParameters = new ExecutionParameters();

        executionParameters.setPath("./");
        executionParameters.addTemplatesContent("template1", inputTemplate);

        ExecutionResult result = FileGenerator.execute("template1", executionParameters);

        Assert.assertEquals(2, result.getChunks().size());
        Assert.assertEquals(chunkName, result.getChunks().get(chunkName).getChunkName());
        Assert.assertEquals("42", result.getChunks().get(chunkName).getChunkContent());

        Assert.assertEquals(chunkName2, result.getChunks().get(chunkName2).getChunkName());
        Assert.assertEquals("1337", result.getChunks().get(chunkName2).getChunkContent());

        Assert.assertEquals(0, result.getOutputVariables().size());
    }
}
