package Repository;

import Domain.Exceptions.MyException;
import Domain.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Repository implements iRepository {
    private List<ProgramState> programStateList;
    private String logFilePath;

    public Repository(String logFilePath) {
        this.logFilePath = logFilePath;
        programStateList = new LinkedList<ProgramState>();
    }
    public ProgramState getCurrentProgram(){
        return programStateList.get(0);
    }

    @Override
    public void addProgram(ProgramState newProgram) {
        programStateList.add(newProgram);
    }

    @Override
    public List<ProgramState> getProgramStateList() {
        return programStateList;
    }

    @Override
    public void setProgramList(List<ProgramState> newProgramStateList){
        this.programStateList = newProgramStateList;
    }


    @Override
    public void logProgramStateExecution() throws MyException {
        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)))){
            logFile.print("Executable Stack: " + programStateList.get(0).getExecutableStack().toString());
            logFile.println("Heap: " + programStateList.get(0).getHeapTable().toString());
            logFile.println("Symbol table: " + programStateList.get(0).getSymbolTable().toString());
            logFile.println("Out: " + programStateList.get(0).getOut().toString());

        } catch (IOException e) {
            throw new MyException(e.toString());
        }


    }

    @Override
    public void logProgramStateExecution(ProgramState state) throws MyException {
        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)))){
            logFile.print("Executable Stack: " + state.getExecutableStack().toString());
            logFile.println("Heap: " + state.getHeapTable().toString());
            logFile.println("Symbol table: " + state.getSymbolTable().toString());
            logFile.println("Out: " + state.getOut().toString());

        } catch (IOException e) {
            throw new MyException(e.toString());
        }
    }

    @Override
    public ProgramState getProgramById(int activeProgram) {
        for(ProgramState program: programStateList){
            if(program.getActiveProgram() == activeProgram)
                return program;
        }
        return null;
    }
}
