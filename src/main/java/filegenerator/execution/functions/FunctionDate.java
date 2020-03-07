package filegenerator.execution.functions;

import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.ExecutionInfo;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author TheSadlig
 */
public class FunctionDate implements Function {

    public AbstractTypedVariable<String> execute(List<ValueNode> parametersList, ExecutionInfo executionInfo) throws FileGeneratorException {
        if (parametersList.isEmpty()) {
            throw new FileGeneratorException("Displaying a date should have 2 parameters at least");
        }

        SimpleDateFormat df = new SimpleDateFormat(parametersList.get(0).getValue());
        Date finalDate = new Date();

        if (parametersList.size() == 2) {
            // We enable the possibility to shift the date by a given number of days
            int dateShift = Integer.parseInt(parametersList.get(1).getValue());
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(finalDate);
            calendar.add(Calendar.DATE, dateShift);
            finalDate = calendar.getTime();
        }
        TypedVariable<String> dateVariable = new TypedVariable<>();
        String date = df.format(finalDate);
        dateVariable.setValue(date);

        return dateVariable;
    }
}
