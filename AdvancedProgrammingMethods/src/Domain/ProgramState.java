package Domain;

import AbstractDataTypes.Tables.LatchTable;
import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import AbstractDataTypes.iList;
import AbstractDataTypes.iStack;
import Domain.Exceptions.MyException;
import Domain.Values.Value;
import Statements.iStatement;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgramState {
    private static AtomicInteger id = new AtomicInteger(1);

    public int getPersonalId() {
        return personalId;
    }

    private int personalId; //todo introduce this personalId in all prints. This or the static int should be Atomic

    private iStack<iStatement> executableStack;
    private SymbolTable<String, Value> symbolTable;
    private iList<Value> out;
    private iStatement originalProgram; // "optional field" according to teacher, but i don't want to regret later
    private iMemoryDictionary<String, BufferedReader> fileTable;
    private iHeap heapTable;
    private LatchTable latchTable;

    public ProgramState(iStack<iStatement> stack,
                        SymbolTable<String, Value> symbolTable,
                        iList<Value> ot, iStatement program,
                        iMemoryDictionary fileTable,
                        iHeap heapTable
                        ){
        this.executableStack = stack;
        this.symbolTable = symbolTable;
        this.out = ot;
        originalProgram = program.deepCopy();
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.personalId = allocateNewProgramStateMemory();
        stack.push(program);
    }

    public ProgramState(iStack<iStatement> stack,
                        SymbolTable<String, Value> symbolTable,
                        iList<Value> ot, iStatement program,
                        iMemoryDictionary fileTable,
                        iHeap heapTable,
                        LatchTable latchTable
    ){
        this.executableStack = stack;
        this.symbolTable = symbolTable;
        this.out = ot;
        originalProgram = program.deepCopy();
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.latchTable = latchTable;
        this.personalId = allocateNewProgramStateMemory();
        stack.push(program);
    }

    private synchronized static int allocateNewProgramStateMemory() {
        return id.incrementAndGet(); //atomic, thread safe
    }


    public iStack<iStatement> getExecutableStack() {
        return executableStack;
    }

    public void setExecutableStack(iStack<iStatement> executableStack) {
        this.executableStack = executableStack;
    }

    public SymbolTable<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public iList<Value> getOut() {
        return out;
    }

    public void setOut(iList<Value> out) {
        this.out = out;
    }

    public iStatement getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(iStatement originalProgram) {
        this.originalProgram = originalProgram;
    }

    @Override
    public String toString(){
        return "Executable Stack" + Integer.toString(personalId) + ": " + executableStack.toString() +
                "\nHeap" + ": " + heapTable.toString() +
                "\nSymbol Table" + Integer.toString(personalId) + ": " + symbolTable.toString() +
                "\nOut list" + Integer.toString(personalId) + ": " + out.toString() + "\n";
    }

    public iMemoryDictionary getFileTable() {
        return fileTable;
    }

    public boolean isNotCompleted(){
        return !this.executableStack.isEmpty();
    }

    public ProgramState oneStepEvaluation() throws MyException {
        if(executableStack.isEmpty())
            throw new MyException("Program state stack is empty");
        iStatement currentStatement = executableStack.pop();
        return currentStatement.execute(this);
    }


    public iHeap getHeapTable() {
        return heapTable;
    }

    public int getActiveProgram() {
        return personalId;
    }

    public LatchTable getLatchTable() {
        return this.latchTable;
    }
}
