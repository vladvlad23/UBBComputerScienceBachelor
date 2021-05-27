package Statements;

import AbstractDataTypes.MyList;
import AbstractDataTypes.Tables.LatchTable;
import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iMemoryDictionary;
import AbstractDataTypes.iList;
import AbstractDataTypes.iStack;
import Domain.Exceptions.MyException;
import Domain.ProgramState;
import Domain.Types.IntType;
import Domain.Types.Type;
import Domain.Values.IntValue;
import Domain.Values.StringValue;
import Domain.Values.Value;

public class CountDownStatement implements iStatement {
    String var;


    public CountDownStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        //todo think about synchronization
        SymbolTable<String, Value> symbolTable = state.getSymbolTable();
        LatchTable latchTable = state.getLatchTable();
        iList outList = state.getOut();

        if(!(symbolTable.isDefined(var))){
            throw new MyException("CountDown failed. CountdownLatch not found");
        }

        Value val = symbolTable.getValue(var);
        if(!(val.getType().equals(new IntType()))){
            throw new MyException("Countdown failed. CountdownLatch is wrong in Symbol Table");
        }

        int indexInLatchTable = ((IntValue) val).getValue();

        if(!(latchTable.isDefined(indexInLatchTable))){
            throw new MyException("Countdown failed. Index in latch table not found");
        }

        if(latchTable.getValue(indexInLatchTable) > 0){
            latchTable.decrement(indexInLatchTable);
            outList.add(new StringValue(Integer.toString(state.getPersonalId())));
        }
        else{
            outList.add(new StringValue(Integer.toString(state.getPersonalId())));
        }

        return null;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.getValue(var);
        if(!(varType.equals(new IntType()))){
            throw new MyException("Countdown typecheck failed. Argument not integer");
        }

        return typeEnv;
    }

    @Override
    public iStatement deepCopy() {
        return new CountDownStatement(this.var);
    }


    @Override
    public String toString() {
        return "Countdown(" + this.var + ")";
    }
}
