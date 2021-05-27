package Statements.FileOperations;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.Expression.Expression;
import Domain.ProgramState;
import Domain.Types.StringType;
import Domain.Types.Type;
import Domain.Values.StringValue;
import Domain.Values.Value;
import Statements.iStatement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements iStatement {
    private Expression expression;


    public OpenRFile(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<String,Value> symbolTable = state.getSymbolTable();
        iHeap<Integer,Value> heapTable = state.getHeapTable();
        iMemoryDictionary fileTable = state.getFileTable();
        Value value = expression.evaluate(symbolTable,heapTable);
        if(!(value.getType().equals(new StringType()))){
            throw new MyException("Open File failed. String not valid");
        }

        String actualString = ((StringValue) value).getValue();

        if(fileTable.isDefined(value)){
            throw new MyException("Open File failed. File already defined");
        }

        try{ // try with resources is bad here because after all this i have a filetable of closed buffered readers
            BufferedReader reader = new BufferedReader(new FileReader(actualString));
            fileTable.update(value,reader);
        } catch (IOException e) {
            throw new MyException("Open file failed. IO Exception");
        }
        return state;


    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type typeExpression = expression.typecheck(typeEnv);
        if(typeExpression.equals(new StringType())){
            return typeEnv;
        }
        throw new MyException("Open file failed. Expression is not string");
    }

    @Override
    public iStatement deepCopy() {
        return new OpenRFile(expression);
    }

    @Override
    public String toString() { return "Open file " + expression.toString(); }
}
