package Domain.Expression;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.Type;
import Domain.Values.Value;

public class ValueExpression implements Expression {
    Value expression;

    public ValueExpression(Value expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(SymbolTable<String, Value> symbolTable, iHeap<Integer,Value> heapTable) {
        return expression;
    }

    @Override
    public Type typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        return expression.getType();
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(this.expression.deepCopy());
    }

    @Override
    public String toString(){
        return expression.toString();
    }

}
