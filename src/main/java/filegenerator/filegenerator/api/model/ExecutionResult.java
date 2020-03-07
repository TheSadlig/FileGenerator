package filegenerator.filegenerator.api.model;

import filegenerator.ast.model.Chunk;
import filegenerator.filegenerator.model.AbstractTypedVariable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Map;

/**
 * The model to represent the result of the execution of the generator
 *
 * @author TheSadlig
 */
@JsonInclude(Include.NON_NULL)
public class ExecutionResult {

    // The dotFile representing the template
    private String resultingDotFile = null;

    private Map<String, Chunk> chunks = null;

    private Map<String, AbstractTypedVariable> outputVariables = null;

    public String getResultingDotFile() {
        return resultingDotFile;
    }

    public void setResultingDotFile(String resultingDotFile) {
        this.resultingDotFile = resultingDotFile;
    }

    public Map<String, Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(Map<String, Chunk> chunks) {
        this.chunks = chunks;
    }

    public Map<String, AbstractTypedVariable> getOutputVariables() {
        return outputVariables;
    }

    public void setOutputVariables(Map<String, AbstractTypedVariable> outputVariables) {
        this.outputVariables = outputVariables;
    }

}
