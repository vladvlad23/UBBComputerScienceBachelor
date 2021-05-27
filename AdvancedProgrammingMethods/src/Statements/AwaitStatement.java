package Statements;

import AbstractDataTypes.ExecutableStack;
import AbstractDataTypes.Tables.LatchTable;
import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import AbstractDataTypes.iStack;
import Domain.Exceptions.MyException;
import Domain.ProgramState;
import Domain.Types.IntType;
import Domain.Types.Type;
import Domain.Values.IntValue;
import Domain.Values.Value;

public class AwaitStatement implements iStatement {
    String var;

    public AwaitStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        //todo think about synhronization
        SymbolTable<String, Value> symbolTable = state.getSymbolTable();
        iStack stack = state.getExecutableStack();
        LatchTable latchTable = state.getLatchTable();

        if(!(symbolTable.isDefined(var))){
            throw new MyException("Await failed. CountdownLatch not found");
        }

        Value val = symbolTable.getValue(var);
        if(!(val.getType().equals(new IntType()))){
            throw new MyException("Await failed. CountdownLatch is wrong in Symbol Table");
        }

        Integer indexInLatchTable = ((IntValue) val).getValue();

        if(!(latchTable.isDefined(indexInLatchTable))){
            throw new MyException("Await failed. Index in latch table not found");
        }

        if(latchTable.getValue(indexInLatchTable) > 0){
            stack.push(this);
        }

        return null;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.getValue(var);
        if(!(varType.equals(new IntType()))){
            throw new MyException("Await typecheck failed. Argument not integer");
        }

        return typeEnv;
    }

    @Override
    public iStatement deepCopy() {
        return new AwaitStatement(this.var);
    }

    @Override
    public String toString() {
        return "Await(" + this.var + ")";
    }
}
