import AbstractDataTypes.ExecutableStack;
import AbstractDataTypes.MyList;
import AbstractDataTypes.Tables.FileTable;
import AbstractDataTypes.Tables.HeapTable;
import AbstractDataTypes.Tables.SymbolTable;
import Controller.ControllerApp;
import Domain.Exceptions.MyException;
import Domain.Expression.ArithmeticExpression;
import Domain.Expression.ValueExpression;
import Domain.Expression.VariableExpression;
import Domain.ProgramState;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.Value;
import Repository.Repository;
import Repository.iRepository;
import Statements.*;
import View.Commands.ExitCommand;
import View.Commands.RunExample;
import View.TextMenu;

public class Interpreter {
    public static void main(String[] args) {
        iStatement ex1 = firstProgram();


        try {
            ex1.typecheck(new SymbolTable<String, Type>());
        } catch (MyException e) {
            e.printStackTrace();
        }

        ProgramState prg1 = new ProgramState(
                new ExecutableStack<>(),
                new SymbolTable<String, Value>(),
                new MyList<>(),
                ex1,
                new FileTable(),
                new HeapTable()
        );
        iRepository repo1 = new Repository("log1.txt");
        repo1.addProgram(prg1);
        ControllerApp ctr1 = new ControllerApp(repo1);


        iStatement ex2 = secondProgram();
        try {
            ex2.typecheck(new SymbolTable<>());
        } catch (MyException e) {
            e.printStackTrace();
        }

        ProgramState prg2 = new ProgramState(
                new ExecutableStack<>(),
                new SymbolTable<String, Value>(),
                new MyList<>(),
                ex1,
                new FileTable(),
                new HeapTable()
        );

        iRepository repo2 = new Repository("log2.txt");
        repo2.addProgram(prg2);
        ControllerApp ctr2 = new ControllerApp(repo2);
        iStatement ex3 = thirdProgram();

        try {
            ex3.typecheck(new SymbolTable<>());
        } catch (MyException e) {
            e.printStackTrace();
        }

        ProgramState prg3 = new ProgramState(
                new ExecutableStack<>(),
                new SymbolTable<String, Value>(),
                new MyList<>(),
                ex1,
                new FileTable(),
                new HeapTable()
        );
        iRepository repo3 = new Repository("log3.txt");
        repo3.addProgram(prg3);
        ControllerApp ctr3 = new ControllerApp(repo3);
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.show();

    }

    private static iStatement secondProgram() {
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

    private static iStatement firstProgram() {
        return new CompoundStatement(
                new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(
                        new AssignStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v")
                        )));

    }

    private static iStatement thirdProgram(){
        return new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));
    }


}
