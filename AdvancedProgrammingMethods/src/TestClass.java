import AbstractDataTypes.ExecutableStack;
import AbstractDataTypes.MyList;
import AbstractDataTypes.Tables.*;
import AbstractDataTypes.iList;
import AbstractDataTypes.iStack;
import Controller.ControllerApp;
import Domain.Exceptions.MyException;
import Domain.Expression.ArithmeticExpression;
import Domain.Expression.HeapExpressions.ReadHeapExpression;
import Domain.Expression.RelationalExpression;
import Domain.Expression.ValueExpression;
import Domain.Expression.VariableExpression;
import Domain.ProgramState;
import Domain.Types.IntType;
import Domain.Types.RefType;
import Domain.Types.StringType;
import Domain.Values.IntValue;
import Domain.Values.RefValue;
import Domain.Values.StringValue;
import Domain.Values.Value;
import Repository.Repository;
import Repository.iRepository;
import Statements.*;
import Statements.FileOperations.CloseRFile;
import Statements.FileOperations.OpenRFile;
import Statements.FileOperations.ReadFile;
import Statements.HeapStatements.NewStatement;
import Statements.HeapStatements.WriteHeapStatement;
import Utils.ProgramUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestClass {




    @Test
    void testFileTable() {
        iStatement ex = ProgramUtils.concatenate(
                new VariableDeclarationStatement("varf",new StringType()),
                new AssignStatement("varf",new ValueExpression(new StringValue("test.in"))),
                new OpenRFile(new VariableExpression("varf")),
                new VariableDeclarationStatement("v",new IntType()),
                new ReadFile(new VariableExpression("varf"),"varc"),
                new PrintStatement(new VariableExpression("varc")),
                new ReadFile(new VariableExpression("varf"),"varc"),
                new PrintStatement(new VariableExpression("varc")),
                new CloseRFile(new VariableExpression("varf")));

        iStack stack = new ExecutableStack();
        SymbolTable<String, Value> symbolTable = new SymbolTable();
        iMemoryDictionary fileTable = new FileTable();
        iHeap heapTable = new HeapTable();
        iList myList = new MyList<>();
        ProgramState prg1 = new ProgramState(
                stack,
                symbolTable,
                myList,
                ex,
                fileTable,
                heapTable
        );
        iRepository repo1 = new Repository("logTest.txt");
        repo1.addProgram(prg1);
        ControllerApp ctr1 = new ControllerApp(repo1);
        try {
            ctr1.oldAllStepEvaluation();
        }
        catch(MyException exc){
            exc.printStackTrace();
        }
    }



    @Test
    void testConcatenation(){
        /* Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a) */


        iStatement horatiuStatement = ProgramUtils.concatenate(
                new VariableDeclarationStatement("v", new IntType()),
                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                new PrintStatement(new VariableExpression("v"))
        );

        iStatement myStatement = new CompoundStatement(
                new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(
                        new AssignStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v")
                        )));
    }


    @Test
    void testHeap(){
        iStatement ex1 = ProgramUtils.concatenate(
                new VariableDeclarationStatement("v",new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                new NewStatement("a", new VariableExpression("v")),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new VariableExpression("a")));

            //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)


        iStack stack = new ExecutableStack();
        SymbolTable<String,Value> symbolTable = new SymbolTable();
        iMemoryDictionary fileTable = new FileTable();
        iHeap heapTable = new HeapTable();
        iList myList = new MyList<>();
        ProgramState prg1 = new ProgramState(
                stack,
                symbolTable,
                myList,
                ex1,
                fileTable,
                heapTable
        );
        iRepository repo1 = new Repository("logTest.txt");
        repo1.addProgram(prg1);
        ControllerApp ctr1 = new ControllerApp(repo1);
        try {
            ctr1.oldAllStepEvaluation();
        }
        catch(MyException ex){
            ex.printStackTrace();
        }

        Assertions.assertTrue(stack.isEmpty());
        Assertions.assertEquals(((RefValue) myList.getList().get(0)),new RefValue(1,new IntType()));
        Assertions.assertEquals(((RefValue) myList.getList().get(1)),new RefValue(2,new RefType(new IntType())));


        iStatement ex2 = null;
        try {
            ex2 = ProgramUtils.concatenate(
                    new VariableDeclarationStatement("v",new RefType(new IntType())),
                    new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                    new NewStatement("a", new ValueExpression(new RefValue(1, new IntType()))),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                    new PrintStatement(new ArithmeticExpression('+',
                                    new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                    new ValueExpression(new IntValue(5))))
                    );
        } catch (MyException e) {
            e.printStackTrace();
        }

        stack = new ExecutableStack();
        symbolTable = new SymbolTable();
        fileTable = new FileTable();
        heapTable = new HeapTable();
        myList = new MyList<>();


        ProgramState prg2 = new ProgramState(
                stack,
                symbolTable,
                myList,
                ex2,
                fileTable,
                heapTable
        );
        iRepository repo2 = new Repository("logTest.txt");
        repo2.addProgram(prg2);
        ControllerApp ctr2 = new ControllerApp(repo2);
        try {
            ctr2.oldAllStepEvaluation();
        }
        catch(MyException ex){
            ex.printStackTrace();
        }


        iStatement ex3 = null;
        try {
            ex3 = ProgramUtils.concatenate(
                        new VariableDeclarationStatement("v",new RefType(new IntType())),
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                        new WriteHeapStatement("v",new ValueExpression(new IntValue(30))),
                        new PrintStatement(
                                new ArithmeticExpression('+',
                                        new ReadHeapExpression(new VariableExpression("v")),
                                        new ValueExpression(new IntValue(5))
                                )
                        )
                );
        } catch (MyException e) {
            e.printStackTrace();
        }

        ProgramState prg3 = new ProgramState(
                stack,
                symbolTable,
                myList,
                ex3,
                fileTable,
                heapTable
        );
        iRepository repo3 = new Repository("logTest.txt");
        repo3.addProgram(prg3);
        ControllerApp ctr3 = new ControllerApp(repo3);
        try {
            ctr3.oldAllStepEvaluation();
        }
        catch(MyException ex){
            ex.printStackTrace();
        }
    }

    @Test
    void testWhileLoop(){
        iStatement ex = null;
        try {
            ex = ProgramUtils.concatenate(
                        new VariableDeclarationStatement("v",new IntType()),
                        new AssignStatement("v",new ValueExpression(new IntValue(4))),
                        new WhileStatement(new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0)), ">"),
                                new CompoundStatement(
                                    new PrintStatement(new VariableExpression("v")),
                                    new AssignStatement("v",new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1))))
                                )
                        )
                );
        } catch (MyException e) {
            e.printStackTrace();
        }

        System.out.println(ex);

        ProgramState prg = new ProgramState(
                new ExecutableStack<>(),
                new SymbolTable<>(),
                new MyList<>(),
                ex,
                new FileTable(),
                new HeapTable()
        );

        iRepository repo = new Repository("logTest.txt");
        repo.addProgram(prg);
        ControllerApp ctr = new ControllerApp(repo);

        try {
            ctr.oldAllStepEvaluation();
        }
        catch(MyException e){
            e.printStackTrace();
        }
    }


    @Test
    void testGarbageCollector(){
        /*Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))*/

        iStatement ex = ProgramUtils.concatenate(
                    new VariableDeclarationStatement("v",new RefType(new IntType())),
                    new NewStatement("v",new ValueExpression(new IntValue(20))),
                    new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                    new NewStatement("a",new ValueExpression(new RefValue(1,new IntType()))),
                    new NewStatement("v",new ValueExpression(new IntValue(30))),
                    new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
            );

        ProgramState prg = new ProgramState(
                new ExecutableStack<>(),
                new SymbolTable<>(),
                new MyList<>(),
                ex,
                new FileTable(),
                new HeapTable()
        );

        iRepository repo = new Repository("logTest.txt");
        repo.addProgram(prg);
        ControllerApp ctr = new ControllerApp(repo);

        try {
            ctr.oldAllStepEvaluation();
        }
        catch(MyException e){
            e.printStackTrace();
        }
    }

    @Test
    void testAdditionToStream(){
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
    }

    @Test
    void testMultithreading() throws MyException {

        iStatement forkStatement = ProgramUtils.concatenate(
                new WriteHeapStatement("a",new ValueExpression(new IntValue(30))),
                new AssignStatement("v",new ValueExpression(new IntValue(32))),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );

        iStatement ex = ProgramUtils.concatenate(
                new VariableDeclarationStatement("v",new IntType()),
                new VariableDeclarationStatement("a",new RefType(new IntType())),
                new AssignStatement("v",new ValueExpression(new IntValue(10))),
                new NewStatement("a",new ValueExpression(new IntValue(22))),
                new ForkStatement(forkStatement),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );

        iStack stack = new ExecutableStack();
        SymbolTable<String,Value> symbolTable = new SymbolTable();
        iMemoryDictionary fileTable = new FileTable();
        iHeap heapTable = new HeapTable();
        iList myList = new MyList<>();

        ex.typecheck(new SymbolTable<>());

        ProgramState prg = new ProgramState(
                stack,
                symbolTable,
                myList,
                ex,
                fileTable,
                heapTable
        );

        iRepository repo = new Repository("logTest.txt");
        ControllerApp ctr = new ControllerApp(repo);
        ctr.addProgram(prg);
        try {
            ctr.allStepEvaluation();
        }
        catch(MyException except){
            except.printStackTrace();
        }
    }

    @Test
    void testMultiThreading2(){

        iStatement forkStatement = ProgramUtils.concatenate(
                new WriteHeapStatement("a",new ValueExpression(new IntValue(30))),
                new AssignStatement("v",new ValueExpression(new IntValue(32))),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );

        iStatement forkStatement2 = ProgramUtils.concatenate(
                forkStatement,
                new WriteHeapStatement("a",new ValueExpression(new IntValue(30))),
                new AssignStatement("v",new ValueExpression(new IntValue(32))),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );

        iStatement ex = ProgramUtils.concatenate(
                new VariableDeclarationStatement("v",new IntType()),
                new VariableDeclarationStatement("a",new RefType(new IntType())),
                new AssignStatement("v",new ValueExpression(new IntValue(10))),
                new NewStatement("a",new ValueExpression(new IntValue(22))),
                new ForkStatement(forkStatement),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );


    }
}