package Repository;

import Domain.Exceptions.MyException;
import Domain.ProgramState;

import java.util.List;

public interface iRepository {

    @Deprecated
    ProgramState getCurrentProgram();

    void addProgram(ProgramState newProgram);

    List<ProgramState> getProgramStateList();

    void setProgramList(List<ProgramState> newProgramStateList);

    @Deprecated
    void logProgramStateExecution() throws MyException;

    void logProgramStateExecution(ProgramState state) throws MyException;

    ProgramState getProgramById(int activeProgram);
}
