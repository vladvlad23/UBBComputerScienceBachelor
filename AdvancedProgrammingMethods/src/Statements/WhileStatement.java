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

public class WhileStatement implements iStatement {
    Expression expression;
    iStatement innerStatement;

    public WhileStatement(Expression expression, iStatement statement) {
        this.expression = expression;
        this.innerStatement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<String,Value> symbolTable = state.getSymbolTable();
        iHeap heapTable = state.getHeapTable();
        iStack executableStack = state.getExecutableStack();
        Value value = expression.evaluate(symbolTable,heapTable);

        if(!(value.getType() instanceof BoolType)){
            throw new MyException("Executing while failed. Condition not boolean");
        }

        if(!((BoolValue) value).getValue()){
            return state;
        }
        executableStack.push(this);
        executableStack.push(innerStatement);


        return null;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            innerStatement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of while has not the type bool");
    }

    @Override
    public iStatement deepCopy() {
        return new WhileStatement(this.expression,this.innerStatement);
    }

    @Override
    public String toString(){
        return "(While( " + expression.toString() + " ) " + innerStatement.toString() + ")";
    }
}
