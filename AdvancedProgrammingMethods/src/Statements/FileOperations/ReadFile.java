package Statements.FileOperations;

import AbstractDataTypes.Tables.FileTable;
import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.Expression.Expression;
import Domain.ProgramState;
import Domain.Types.IntType;
import Domain.Types.StringType;
import Domain.Types.Type;
import Domain.Values.IntValue;
import Domain.Values.StringValue;
import Domain.Values.Value;
import Statements.iStatement;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements iStatement {
    private Expression expression;
    private String variableName;

    public ReadFile(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<String,Value> symbolTable = state.getSymbolTable();
        FileTable fileTable = (FileTable) state.getFileTable();
        iHeap<Integer,Value> heapTable = state.getHeapTable();
        Value value = expression.evaluate(symbolTable,heapTable);
        if(!(value.getType().equals(new StringType()))){
            throw new MyException("Read File failed. String not valid");
        }

        if(!fileTable.isDefined((StringValue) value)){
            throw new MyException("Read file failed. File not defined");
        }

        BufferedReader currentReader = fileTable.getValue((StringValue) value);


        try {
            if(!currentReader.ready()){
                System.out.println("Current reader is ready ");
            }
        } catch (IOException e) {
            System.out.println("Current file reader is closed");
        }


        try {
            String lineRead = currentReader.readLine();
            IntValue intValue;
            if(lineRead.isEmpty()){
                intValue = (IntValue) new IntType().defaultValue();
            }
            else{
                intValue = new IntValue(Integer.parseInt(lineRead));
            }
            symbolTable.update(variableName,intValue);
        } catch (IOException e) {
            throw new MyException("Read file failed. Couldn't read lines");
        }
        return state;

    }

    @Override
    public iMemoryDictionary<String, Type> typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type typeVariable = typeEnv.getValue(variableName);
        Type typeExpression = expression.typecheck(typeEnv); //this just has to be verified. No need to return anything as there is no type environment change after expression evaluation
        if(typeVariable.equals(new IntType())) {
            return typeEnv;
        }
        throw new MyException("Read file failed. The type is not int");
    }

    @Override
    public iStatement deepCopy() {
        return new ReadFile(this.expression,this.variableName);
    }

    @Override
    public String toString() { return "ReadFile " + expression.toString() + " to " + variableName; }
}
