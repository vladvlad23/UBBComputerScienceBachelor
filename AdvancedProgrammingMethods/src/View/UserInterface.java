package View;

import AbstractDataTypes.ExecutableStack;
import AbstractDataTypes.MyList;
import AbstractDataTypes.Tables.*;
import AbstractDataTypes.iList;
import AbstractDataTypes.iStack;
import Controller.ControllerApp;
import Domain.Exceptions.MyException;
import Domain.Expression.ArithmeticExpression;
import Domain.Expression.ValueExpression;
import Domain.Expression.VariableExpression;
import Domain.ProgramState;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.Value;
import Repository.Repository;
import Statements.*;

import java.util.Scanner;

public class UserInterface {


    public void start(){
        while(true) {
            System.out.println("Please choose a program!\n" +
                    "1. int v; v=2;Print(v)\n" +
                    "2. int a;int b; a=2+3*5;b=a+1;Print(b)\n" +
                    "3. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)\n");

            Scanner scan = new Scanner(System.in);
            int result = scan.nextInt();
            iStatement expression = null;
            if(result == 1){
                expression = firstProgram();
            }
            else if(result == 2 ){
                expression = secondProgram();
            }
            else if(result == 3){
                expression = thirdProgram();
            }

            Repository repository = new Repository("log.txt");
            ControllerApp controller = new ControllerApp(repository);


            iStack<iStatement> stack = new ExecutableStack();
            SymbolTable<String,Value> table = new SymbolTable<String, Value>();
            iMemoryDictionary fileTable = new FileTable();
            iHeap<Integer, Value> heapTable = new HeapTable();
            iList list = new MyList();
            ProgramState state = new ProgramState(stack,table,list,expression,fileTable,heapTable);

            repository.addProgram(state);

            System.out.println("Current program state");
            controller.displayCurrentProgramState();


            while(true) {
                System.out.println("1. One Step Evaluation");
                System.out.println("2. Full evaluation");

                int userInput = scan.nextInt();
                if (userInput == 1) {
                    try {
                        controller.oneStepEvaluation(state);
                        controller.displayCurrentProgramState();
                    } catch (MyException ex) {
                        System.out.println(ex.toString());
                        if(ex.toString().equals("Domain.Exceptions.MyException: Stack Empty")){
                            System.out.println(ex.toString());
                            return;
                        }
                    }
                } else if (userInput == 2) {
                    try {
                        controller.oldAllStepEvaluation();
                    } catch (MyException ex) {
                        if(ex.toString().equals("Domain.Exceptions.MyException: Stack Empty")){

                            return;
                        }
                        System.out.println(ex.toString());
                    }
                }
            }

        }
    }


    private iStatement secondProgram() {
        try {
            return new CompoundStatement(
                    new VariableDeclarationStatement("a", new IntType()),
                    new CompoundStatement(
                            new VariableDeclarationStatement("b", new IntType()),
                            new CompoundStatement(
                                    new AssignStatement("a",
                                            new ArithmeticExpression('+',
                                                    new ValueExpression(new IntValue(2)),
                                                    new ArithmeticExpression(
                                                            '*',
                                                            new ValueExpression(new IntValue(3)),
                                                            new ValueExpression(new IntValue(5))))),
                                    new CompoundStatement(
                                            new AssignStatement(
                                                    "b",
                                                    new ArithmeticExpression('+',
                                                            new VariableExpression("a"),
                                                            new ValueExpression(new IntValue(1)))),
                                            new PrintStatement(new VariableExpression("b"))))));
        }
        catch(MyException eX){
            System.out.println("This exception won't be caught as this is a test");
            return null;
        }
    }

    private iStatement firstProgram() {
        return new CompoundStatement(
                new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(
                        new AssignStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v")
                        )));

    }

    private iStatement thirdProgram(){
        return new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));
    }

}
