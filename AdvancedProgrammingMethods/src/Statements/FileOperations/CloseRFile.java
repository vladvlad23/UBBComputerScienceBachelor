package Statements.FileOperations;

import AbstractDataTypes.Tables.FileTable;
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
import java.io.IOException;

public class CloseRFile implements iStatement {
    private Expression expression;

    public CloseRFile(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<String, Value> symbolTable = state.getSymbolTable();
        iHeap heapTable = state.getHeapTable();
        FileTable fileTable = (FileTable) state.getFileTable();
        Value value = expression.evaluate(symbolTable,heapTable);
        if(!(value.getType().equals(new StringType()))){
            throw new MyException("Close File failed. String not valid");
        }

        if(!fileTable.isDefined((StringValue) value)){
            throw new MyException("Close file failed. File not defined");
        }

        BufferedReader currentReader = fileTable.getValue((StringValue) value);
        try {
            currentReader.close();
        } catch (IOException e) {
            throw new MyException("Close file failed. Reader could not be closed");
        }
        fileTable.remove(value);
        return state;
    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type typeExpression = expression.typecheck(typeEnv);
        if(typeExpression.equals(new StringType())) {
            return typeEnv;
        }
        throw new MyException("Closing file  failed. The string is not ok");
    }

    @Override
    public iStatement deepCopy() {
        return new CloseRFile(this.expression);
    }

    @Override
    public String toString() { return "Close file " + expression.toString(); }
}
