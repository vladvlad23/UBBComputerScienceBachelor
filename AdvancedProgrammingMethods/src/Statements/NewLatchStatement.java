package Statements;

import AbstractDataTypes.Tables.LatchTable;
import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import AbstractDataTypes.iStack;
import Domain.Exceptions.MyException;
import Domain.Expression.Expression;
import Domain.ProgramState;
import Domain.Types.IntType;
import Domain.Types.Type;
import Domain.Values.IntValue;
import Domain.Values.Value;

public class NewLatchStatement implements iStatement {
    String var;
    Expression expression;

    public NewLatchStatement(String var, Expression expression) {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        //todo think about lock as it's already pretty synchronized.
        SymbolTable<String, Value> symbolTable = state.getSymbolTable();
        iHeap<Integer, Value> heapTable = state.getHeapTable();
        LatchTable latchTable = state.getLatchTable();

        Value resultValue = expression.evaluate(symbolTable,heapTable);
        if(!(resultValue.getType().equals(new IntType()))){
            throw new MyException("New Latch exception. Variable not integer");
        }

        //synchronized union?
        //todo i should definitely lock this because i get the value and then do
        //todo other stuff.
        IntValue currentValue = new IntValue(latchTable.getCurrentFree());
        latchTable.update(((IntValue) resultValue).getValue());

        if((!symbolTable.isDefined(var))){
            throw new MyException("New latch exception. Variable not in symbol table");
        }
        else{
            Value valueInTable = symbolTable.getValue(var);
            if(!(valueInTable.getType().equals(new IntType()))){
                throw new MyException("New latch exception. Variable in symbol table not int");
            }
            else{
                symbolTable.update(var,currentValue);
            }
        }
        //todo unlock here

        return null;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.getValue(var);
        Type expType = expression.typecheck(typeEnv);

        if(!(varType.equals(expType))){
            throw new MyException("Typecheck newlatch failed. Types not equal");
        }

        return typeEnv;
    }

    @Override
    public iStatement deepCopy() {
        return new NewLatchStatement(this.var,this.expression.deepCopy());
    }


    @Override
    public String toString() {
        return "NewLatch(" + this.var + " " + this.expression.toString() + ")";
    }
}
