package filegenerator.execution.functions;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.TestUtils;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class DateFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void setDateToVariableSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        String format = "YYYY-mm-dd";
        AbstractAST astRoot = TestUtils.parseString("{{{date,$test," + format + "}}}<<<var,$test>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(format);

        Assert.assertEquals(df.format(d), env.getOutput());
    }

    @Test
    public void displayDateSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        String format = "YYYY-mm-dd";
        AbstractAST astRoot = TestUtils.parseString("<<<date," + format + ">>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(format);

        Assert.assertEquals(df.format(d), env.getOutput());
    }

}
