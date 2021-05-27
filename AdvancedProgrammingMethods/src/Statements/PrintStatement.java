package Statements;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import AbstractDataTypes.iList;
import Domain.Exceptions.MyException;
import Domain.Expression.Expression;
import Domain.ProgramState;
import Domain.Types.Type;
import Domain.Values.Value;

public class PrintStatement implements iStatement {
    Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }


    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        iList list = state.getOut();
        SymbolTable<String, Value> dictionary = state.getSymbolTable();
        iHeap heapTable = state.getHeapTable();
        list.add(expression.evaluate(dictionary,heapTable));
        return null;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public iStatement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public String toString(){
        return "print(" + expression.toString()+ ")";
    }
}
