package Statements;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import AbstractDataTypes.iStack;
import Domain.Exceptions.MyException;
import Domain.Expression.Expression;
import Domain.ProgramState;
import Domain.Types.Type;
import Domain.Values.Value;

public class AssignStatement implements iStatement {

    String id;
    Expression expression;

    public AssignStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException { //todo new object should be created to avoid other multithreading issues (so int x = 10 ) the 10 should be a new int value rather than editing the old one
        iStack<iStatement> stack = state.getExecutableStack();
        SymbolTable<String, Value> symbolTable = state.getSymbolTable();
        iHeap<Integer, Value> heapTable = state.getHeapTable();
        Value value = expression.evaluate(symbolTable,heapTable); // todo about here should be a new object ? perhaps (expression.evaluate(symbolTable,heapTable)).deepcopy();
        if(symbolTable.isDefined(id)){
            Type typeId = symbolTable.getValue(id).getType();
            if(value.getType().equals(typeId)){
                symbolTable.update(id,value);
            }
            else throw new MyException("Declared type of variable "+id+" which is "  + typeId + " and type of the assigned expression which is " + value.getType() + " do not match");
        }
        else throw new MyException("The used variable " +id + " was not declared before");
        return null;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type typeVariable = typeEnv.getValue(id);
        Type typeExpression = expression.typecheck(typeEnv);
        if(typeVariable.equals(typeExpression))
            return typeEnv;
        throw new MyException("Assignment: right hand side and left hand side have different types \n");
    }

    @Override
    public iStatement deepCopy() {
        return new AssignStatement(this.id,this.expression.deepCopy());
    }

    @Override
    public String toString(){
        return id + "="+ expression.toString();
    }



}
