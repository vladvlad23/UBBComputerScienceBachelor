package Statements.HeapStatements;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.Expression.Expression;
import Domain.ProgramState;
import Domain.Types.RefType;
import Domain.Types.Type;
import Domain.Values.RefValue;
import Domain.Values.Value;
import Statements.iStatement;

public class WriteHeapStatement implements iStatement {

    String variableName;
    Expression expression;

    public WriteHeapStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<String, Value> symbolTable = state.getSymbolTable();
        iHeap<Integer,Value> heapTable = state.getHeapTable();

        if(!symbolTable.isDefined(variableName)){
            throw new MyException("Writing to heap failed. Variable name not defined");
        }

        Value oldValue = symbolTable.getValue(variableName);

        if(!(oldValue.getType() instanceof RefType)){
            throw new MyException("Writing to heap failed. Type is not reference");
        }

        if(!heapTable.isDefined(((RefValue) oldValue).getAddress())){
            throw new MyException("Writing to heap failed. Address not defined");
        }

        Value newValue = expression.evaluate(symbolTable,heapTable);

        if(!(newValue.getType().equals(((RefValue) oldValue).getLocationType()))){
            throw new MyException("Writing to heap failed. Type of new value is wrong");
        }

        heapTable.update(((RefValue) oldValue).getAddress(),newValue);
        return null;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type typeVariable = typeEnv.getValue(variableName);
        Type typeExpression = expression.typecheck(typeEnv);
        if(typeVariable.equals(new RefType(typeExpression))) {
            return typeEnv;
        }
        throw new MyException("New allocation failed. The type is not reference");
    }

    @Override
    public iStatement deepCopy() {
        return new WriteHeapStatement(this.variableName,this.expression.deepCopy());
    }

    @Override
    public String toString(){
        return "(" + variableName + expression.toString() + ")";
    }
}
