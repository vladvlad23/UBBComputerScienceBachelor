import Domain.Expression.ValueExpression;
import Domain.Expression.VariableExpression;
import Domain.Types.IntType;
import Domain.Values.IntValue;
import Statements.*;

import java.util.Arrays;
import java.util.List;

public class ProgramUtils
{
    private ProgramUtils()
    {
    }

    private static iStatement program()
    {
        return concatenate(
                new VariableDeclarationStatement("a", new IntType()),
                new AssignStatement("a", new ValueExpression(new IntValue(3))),
                new PrintStatement(new VariableExpression("a"))
        );
    }

    public static iStatement concatenate(iStatement... statements)
    {
        if (statements.length > 1)
        {
            return new CompoundStatement(statements[0], concatenate(Arrays.copyOfRange(statements, 1, statements.length)));
        }
        if (statements.length == 1)
        {
            return statements[0];
        }
        return null;
    }
}