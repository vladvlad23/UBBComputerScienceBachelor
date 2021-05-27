package Statements;

import AbstractDataTypes.ExecutableStack;
import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.ProgramState;
import Domain.Types.Type;

public class ForkStatement implements iStatement {
    iStatement statement;

    public ForkStatement(iStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        ProgramState newProgramState = new ProgramState(
                new ExecutableStack<>(),
                state.getSymbolTable().deepCopy(),
                state.getOut(),
                statement.deepCopy(),
                state.getFileTable(),
                state.getHeapTable(),
                state.getLatchTable()
        );
        return newProgramState;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        return statement.typecheck(typeEnv);
    }

    @Override
    public iStatement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public String toString(){
        return "Forking " + statement.toString();
    }
}
