package Domain.Expression;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.Type;
import Domain.Values.Value;

public class VariableExpression implements Expression {
    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public Value evaluate(SymbolTable<String, Value> symbolTable, iHeap<Integer, Value> heapTable) {
        return symbolTable.getValue(id);
    }

    @Override
    public Type typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.getValue(id);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(this.id);
    }

    @Override
    public String toString(){
        return id;
    }
}
