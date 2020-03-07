package filegenerator.execution.environment;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.TestUtils;
import filegenerator.execution.Environnement;
import filegenerator.filegenerator.api.model.ExecutionParameters;
import filegenerator.filegenerator.model.AbstractTypedVariable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 *
 * @author TheSadlig
 */
public class OutputVariablesTest {

    @Before
    public void init() {
        Environnement.getEnvironenement().clear();

    }

    @Test
    public void simpleVariableOutput() throws filegenerator.execution.FileGeneratorException {
        filegenerator.execution.Environnement env = filegenerator.execution.Environnement.getEnvironenement();

        ExecutionParameters params = env.getParameters();
        params.addExpectedVariable("test");

        AbstractAST astRoot = TestUtils.parseString("{{{set,$test,42}}}{{{set,$test2,55}}}");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Map<String, AbstractTypedVariable> outputVariables = env.getOutputVariables();
        Assert.assertEquals(1, outputVariables.size());
        Assert.assertEquals("42", outputVariables.get("test").toString());
    }

    @Test
    public void multipleVariableOutput() throws filegenerator.execution.FileGeneratorException {
        filegenerator.execution.Environnement env = filegenerator.execution.Environnement.getEnvironenement();

        ExecutionParameters params = env.getParameters();
        params.addExpectedVariable("test");
        params.addExpectedVariable("test2");

        AbstractAST astRoot = TestUtils.parseString("{{{set,$test,42}}}{{{set,$test2,55}}}{{{set,$test3,1337}}}");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Map<String, AbstractTypedVariable> outputVariables = env.getOutputVariables();
        Assert.assertEquals(2, outputVariables.size());
        Assert.assertEquals("42", outputVariables.get("test").toString());
        Assert.assertEquals("55", outputVariables.get("test2").toString());
    }

    @Test
    public void expectWrongVariable() throws filegenerator.execution.FileGeneratorException {
        filegenerator.execution.Environnement env = filegenerator.execution.Environnement.getEnvironenement();

        ExecutionParameters params = env.getParameters();
        params.addExpectedVariable("DONTEXIST");

        AbstractAST astRoot = TestUtils.parseString("{{{set,$test,42}}}");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Map<String, AbstractTypedVariable> outputVariables = env.getOutputVariables();
        Assert.assertEquals(1, outputVariables.size());
        Assert.assertNull(outputVariables.get("DONTEXIST"));
    }
}
