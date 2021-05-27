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

public class NewStatement implements iStatement {
    String variableName;
    Expression expression;

    public NewStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{ //todo declare synchronized all the methods that read and write to heap. Also, atomic integer ?
        SymbolTable<String,Value> symbolTable = state.getSymbolTable();
        iHeap<Integer, Value> heapTable = state.getHeapTable();
        Value value = expression.evaluate(symbolTable,heapTable);

        if(!symbolTable.isDefined(variableName)){
            throw new MyException("New allocation failed. The variable " + variableName + " is not defined");
        }

        if(!(((Value) symbolTable.getValue(variableName)).getType() instanceof RefType)){
            throw new MyException("New allocation failed. The type is not reference");
        }

        RefValue currentValueInSymbolTable = (RefValue) symbolTable.getValue(variableName);

        RefValue valueInSymbolTable = (RefValue) currentValueInSymbolTable.deepCopy();

        if(!value.getType().equals(valueInSymbolTable.getLocationType())){
            throw new MyException("New allocation failed. The variables are not of same type");
        }
        RefValue valueToBePutIntoTable = new RefValue(heapTable.getCurrentFree(),value.getType());
        valueToBePutIntoTable.setAddress(heapTable.getCurrentFree());
        symbolTable.update(variableName,valueToBePutIntoTable);
        heapTable.update(value);
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
        return new NewStatement(variableName,expression.deepCopy());
    }

    @Override
    public String toString(){
        return "(New " + variableName + " " +  expression.toString() + ")";
    }

}
