package filegenerator.ast.nodes;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.model.Chunk;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class ChunkNodeTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void singleChunkSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        env.addVariable("test", 10);

        AbstractAST astRoot = TestUtils.parseString("===firstTest,PAYMENT/===<<<var,$test>>>===END===");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Map<String, Chunk> chunks = env.getChunksMap();

        Assert.assertEquals(1, chunks.size());
        Chunk chunk = chunks.entrySet().iterator().next().getValue();
        Assert.assertEquals("10", chunk.getChunkContent());
    }

    @Test
    public void dualChunksSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        env.addVariable("test", 10);
        env.addVariable("test2", 42);

        AbstractAST astRoot = TestUtils.parseString("===test1.xml,PAYMENT/===<<<var,$test>>>===END===\n"
                + "===test2,PAYMENT/===<<<var,$test2>>>===END===");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Map<String, Chunk> chunks = env.getChunksMap();

        Assert.assertEquals(2, chunks.size());
        Iterator<Map.Entry<String, Chunk>> iterator = chunks.entrySet().iterator();
        Chunk firstChunk = iterator.next().getValue();
        Assert.assertEquals("10", firstChunk.getChunkContent());
        
        Chunk secondChunk = iterator.next().getValue();
        Assert.assertEquals("42", secondChunk.getChunkContent());
    }
}
