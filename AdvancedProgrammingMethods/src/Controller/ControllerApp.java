package Controller;

import AbstractDataTypes.iStack;
import Domain.Exceptions.MyEmptyStackException;
import Domain.Exceptions.MyException;
import Domain.ProgramState;
import Repository.iRepository;
import Statements.iStatement;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerApp {
    private iRepository repository;
    private MyGarbageCollector myGarbageCollector;
    private ExecutorService executorService;

    public ControllerApp(iRepository repository)
    {
        this.repository = repository;
        this.myGarbageCollector = new MyGarbageCollector();
    }

    @Deprecated
    public ProgramState oneStepEvaluation(ProgramState state) throws MyException {
        iStack<iStatement> stack = state.getExecutableStack();
        if(stack.isEmpty())
            throw new MyEmptyStackException("Stack is empty");
        iStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }


    @Deprecated
    public ProgramState oldAllStepEvaluation() throws MyException {
        ProgramState program = repository.getCurrentProgram();
        while(!program.getExecutableStack().isEmpty()){
            oneStepEvaluation(program);

            program.getHeapTable().setContent(
                    myGarbageCollector.safeGarbageCollector(
                            myGarbageCollector.addIndirections(myGarbageCollector.getAddressFromSymbolTable(program.getSymbolTable().getContent()),program.getHeapTable()),
                            program.getHeapTable()
                    )
            );

            displayCurrentProgramState();
        }
        return program;
    }

    public void allStepEvaluation() throws MyException {
        executorService = Executors.newFixedThreadPool(2);
        //remove completed programs

        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramStateList());
        while(programStateList.size()>0){

            List<Integer> allAddress = programStateList.stream()
                    .map(e -> e.getSymbolTable().getContent()) //map program states to symbol table content
                    .map(e -> myGarbageCollector.getAddressFromSymbolTable(e))
                    .reduce(Stream.of(0).collect(Collectors.toList()),
                            (s1,s2) -> Stream.concat(s1.stream(), s2.stream()).collect(Collectors.toList()));


            oneStepEvaluationForAllPrograms(programStateList);
            programStateList = removeCompletedPrograms(repository.getProgramStateList());
        }
        executorService.shutdownNow();

        //from LabNotes:
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository

        repository.setProgramList(programStateList);


    }

    public void oneStepEvaluationForAllPrograms(List<ProgramState> listOfProgramStates) throws MyException {
        executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramStateList());

        programStateList
                .forEach(p -> {
                    try {
                        repository.logProgramStateExecution(p);
                    } catch (MyException e) {
                        System.out.println("One Step Evaluation failed." + e.toString());
                    }
                });

        //prepare the list of callables
        List<Callable<ProgramState>> callList = programStateList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) p::oneStepEvaluation)
                .collect(Collectors.toList());

        //start execution of callables

        List<ProgramState> newProgramList = null;
        try {
            newProgramList = executorService.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException e) {
                            System.out.println("One step evaluation for all programs failed or Stack is empty" + e.toString());
                        } catch (ExecutionException e) {
                            System.out.println("One step evaluation for all programs failed " + e.toString());
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .filter(Objects::nonNull) // <=> p -> p!=null. It's better like this
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            System.out.println(e.toString());; //we do not separately treat this exception as it is a system exception
        }

        //add new threads to existing threads
        programStateList.addAll(newProgramList);

        //after execution, print ProgramStateList into log file
        programStateList.forEach( program -> {
            try {
                repository.logProgramStateExecution(program);
                System.out.println(program.toString() + "\n");
            } catch (MyException e) {
                System.out.println("Error when executing one step for all programs" + e.toString());
            }
        });

        executorService.shutdownNow();

        repository.setProgramList(programStateList);
    }

    public void displayCurrentProgramState(){
        try {
            repository.logProgramStateExecution();
        } catch (MyException e) {
            System.out.println(e.toString());
        }

        System.out.println(repository.getCurrentProgram().toString() + "\n");
    }
    public void addProgram(ProgramState newProgram){
        repository.addProgram(newProgram);
    }


    private List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList){
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted) //<=> p -> (p.isNotCompleted())
                .collect((Collectors.toList()));
    }
}
