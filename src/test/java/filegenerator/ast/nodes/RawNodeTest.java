package filegenerator.ast.nodes;

import filegenerator.ast.AbstractAST;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author TheSadlig
 */
public class RawNodeTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void onlyRawText() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        String input = "This is a simple raw text";
        AbstractAST astRoot = TestUtils.parseString(input);
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals(input, env.getOutput());
    }

    @Test
    public void rawTextWithSeveralWhiteSpaces() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        String input = "This is a      simple raw text                   ";
        AbstractAST astRoot = TestUtils.parseString(input);
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals(input, env.getOutput());
    }

    @Test
    public void onlyWhiteSpaces() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        String input = "        ";
        AbstractAST astRoot = TestUtils.parseString(input);
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals(input, env.getOutput());
    }

    @Test
    public void rawTextWithLF() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        String input = "This is a simple raw text\n This is my super text ?\t yippie";
        AbstractAST astRoot = TestUtils.parseString(input);
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals(input, env.getOutput());
    }

    @Test
    public void rawTextSurroundedByDate() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        String input = "<<<date,yyyy>>> This is my super text ?\t <<<date,yyyy>>>";
        AbstractAST astRoot = TestUtils.parseString(input);
        Assert.assertNotNull(astRoot);

        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String expectedResult = df.format(new Date());

        astRoot.execute();

        Assert.assertEquals(expectedResult + " This is my super text ?\t " + expectedResult, env.getOutput());
    }

    @Test
    public void specialChars() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        String input = "<> {} [] [] ..";
        AbstractAST astRoot = TestUtils.parseString(input);
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals(input, env.getOutput());
    }
}
