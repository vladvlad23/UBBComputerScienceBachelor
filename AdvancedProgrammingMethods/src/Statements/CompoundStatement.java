package Statements;

import AbstractDataTypes.Tables.iMemoryDictionary;
import AbstractDataTypes.iStack;
import Domain.Exceptions.MyException;
import Domain.ProgramState;
import Domain.Types.Type;

public class CompoundStatement implements iStatement {
    private iStatement first;
    private iStatement second;

    public CompoundStatement(iStatement first, iStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        iStack<iStatement> stack = state.getExecutableStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv)); //so it seems that first.typecheck will return a iMemDictionary where the first expression has been typechecked and
        // second.typecheck will typecheck the second one and reunite them
    }

    @Override
    public iStatement deepCopy() {
        return new CompoundStatement(this.first.deepCopy(),this.second.deepCopy());
    }

    @Override
    public String toString(){
        return "(" + first.toString() + second.toString() + ")";
    }

    public iStatement getFirst() {
        return first;
    }

    public iStatement getSecond(){
        return second;
    }
}
