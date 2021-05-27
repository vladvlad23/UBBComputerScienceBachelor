package Statements;

import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.ProgramState;
import Domain.Types.Type;
import Domain.iCopyable;

public interface iStatement extends iCopyable {

    public ProgramState execute(ProgramState state) throws MyException;

    iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException;

    iStatement deepCopy();
}
