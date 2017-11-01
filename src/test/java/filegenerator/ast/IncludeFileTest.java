package filegenerator.ast;

import filegenerator.execution.Environnement;
import filegenerator.filegenerator.api.model.ExecutionParameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class IncludeFileTest {

    @Before
    public void init() {
        Environnement.getEnvironenement().clear();
        Environnement env = Environnement.getEnvironenement();
        env.setResourcePath("src/test/resources/");
    }

    @Test
    public void simpleIncludeFileFromDisk() throws filegenerator.execution.FileGeneratorException {
        filegenerator.execution.Environnement env = filegenerator.execution.Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString(
                "Executed in the file |||includedFile.subtpl||| output: <<<var,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("Executed in the file 42 output: 42", env.getOutput());
    }

    @Test
    public void simpleIncludeFileFromEnvironment() throws filegenerator.execution.FileGeneratorException {
        filegenerator.execution.Environnement env = filegenerator.execution.Environnement.getEnvironenement();

        ExecutionParameters params = env.getParameters();
        params.addTemplatesContent("MySubTpl", "{{{set,$test,50}}}<<<var,$test>>>");

        AbstractAST astRoot = TestUtils.parseString(
                "Executed in the file |||MySubTpl||| output: <<<var,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("Executed in the file 50 output: 50", env.getOutput());
    }

    @Test
    public void multipleIncludeFileFromEnvironment() throws filegenerator.execution.FileGeneratorException {
        filegenerator.execution.Environnement env = filegenerator.execution.Environnement.getEnvironenement();

        ExecutionParameters params = env.getParameters();
        params.addTemplatesContent("MySubTpl", "{{{add,$test,50}}}<<<var,$test>>>");

        AbstractAST astRoot = TestUtils.parseString(
                "|||MySubTpl||| |||MySubTpl||| |||MySubTpl|||");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("50 100 150", env.getOutput());
    }
}
