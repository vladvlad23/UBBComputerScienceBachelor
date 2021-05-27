package Statements;

import AbstractDataTypes.Tables.iMemoryDictionary;
import AbstractDataTypes.iStack;
import Domain.Exceptions.MyException;
import Domain.ProgramState;
import Domain.Types.Type;

public class VariableDeclarationStatement implements iStatement {

    String name;
    Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        iStack executableStack = state.getExecutableStack();
        iMemoryDictionary symbolTable = state.getSymbolTable();
        if(symbolTable.isDefined(name)){
            throw new MyException("Cannot execute Variable Declaration Statement. The variable " + name + " is already defined");
        }
        else{
/*            if(type instanceof IntType){
                symbolTable.update(name,new IntValue(0));
            }
            else if(type instanceof BoolType){
                symbolTable.update(name,new BoolValue(false));
            }*/
            symbolTable.update(name,type.defaultValue());
        }
        return null;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.update(name,type);
        return typeEnv;
    }

    @Override
    public iStatement deepCopy() {
        return new VariableDeclarationStatement(this.name,this.type.deepCopy());
    }

    @Override
    public String toString(){
        return name + " " + type.toString();
    }
}
