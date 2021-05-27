package Statements;

import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.ProgramState;
import Domain.Types.Type;

public class NopStatement implements iStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public iStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public String toString() { return "JustNope"; }
}
