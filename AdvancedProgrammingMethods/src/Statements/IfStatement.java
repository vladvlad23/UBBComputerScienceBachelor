package Statements;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import AbstractDataTypes.iStack;
import Domain.Exceptions.MyException;
import Domain.Expression.Expression;
import Domain.ProgramState;
import Domain.Types.BoolType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.Value;

public class IfStatement implements iStatement {
    Expression expression;
    iStatement thenStatement;
    iStatement elseStatement;

    public IfStatement(Expression expression, iStatement thenStatement, iStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<String,Value> dictionary = state.getSymbolTable();
        iStack executableStack = state.getExecutableStack();
        iHeap<Integer, Value> heapTable = state.getHeapTable();
        Value value = expression.evaluate(dictionary,heapTable);
        if(value.getType() instanceof BoolType){
            BoolValue actualValue = (BoolValue) value;
            if(actualValue.getValue()){
                executableStack.push(thenStatement);
            }
            else{
                executableStack.push(elseStatement);
            }
        }
        else{
        }
        return null;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenStatement.typecheck(typeEnv.deepCopy());
            elseStatement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }

    @Override
    public iStatement deepCopy() {
        return new IfStatement(this.expression.deepCopy(),this.thenStatement.deepCopy(),this.elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "IF("+ expression.toString()+") THEN(" +thenStatement.toString() +")ELSE("+elseStatement.toString()+")";
    }
}
