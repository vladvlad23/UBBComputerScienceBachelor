package sample;

import AbstractDataTypes.Tables.SymbolTable;
import Domain.Exceptions.MyException;
import Domain.Expression.ArithmeticExpression;
import Domain.Expression.HeapExpressions.ReadHeapExpression;
import Domain.Expression.RelationalExpression;
import Domain.Expression.ValueExpression;
import Domain.Expression.VariableExpression;
import Domain.ProgramState;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Types.RefType;
import Domain.Types.StringType;
import Domain.Values.*;
import Statements.*;
import Statements.FileOperations.CloseRFile;
import Statements.FileOperations.OpenRFile;
import Statements.FileOperations.ReadFile;
import Statements.HeapStatements.NewStatement;
import Statements.HeapStatements.WriteHeapStatement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import Utils.ProgramUtils;

import javax.management.ValueExp;

public class Main extends Application {

    @Override
    public void init() throws Exception{
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        iStatement expression = null;

        List<String> choices = new ArrayList<>();
        choices.add("1. int v; v=2;Print(v)");
        choices.add("2. int a;int b; a=2+3*5;b=a+1;Print(b)");
        choices.add("3. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
        choices.add("Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)");
        choices.add("Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)");
        choices.add("Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);");
        choices.add("string varf;varf=\"test.in\";openRFile(varf);int varc; readFile(varf,varc);print(varc); readFile(varf,varc);print(varc)  closeRFile(varf)");
        choices.add("int v; v=4; (while (v>0) print(v);v=v-1);print(v)");
        choices.add("int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))");
        choices.add("int x; x=10; fork(x=15;fork(x=20;print(x));print(x));print(x)");
        choices.add("WRONG bool v; v=2;Print(v)");
        choices.add("WRONG int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)");
        choices.add("Ref int v1; Ref int v2; Ref int v3; int cnt;new(v1,2);new(v2,3);new(v3,4);newLatch(cnt,rH(v2));fork(wh(v1,rh(v1)*10));print(rh(v1));countDown(cnt); fork(wh(v2,rh(v2)*10));print(rh(v2));countDown(cnt);" +
                "fork(wh(v3,rh(v3)*10));print(rh(v3));countDown(cnt))));await(cnt);print(100);countDown(cnt);print(100)");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("1. int v; v=2;Print(v)", choices);
        dialog.setTitle("Program state picker");
        dialog.setHeaderText("Look, a Choice Dialog");
        dialog.setContentText("Please choose a program to run: ");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            switch(result.get()){
                case "1. int v; v=2;Print(v)":
                    expression = firstProgram();
                    break;
                case "2. int a;int b; a=2+3*5;b=a+1;Print(b)":
                    expression = secondProgram();
                    break;
                case "3. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)":
                    expression = thirdProgram();
                    break;
                case "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)":
                    expression = testHeapAllocation();
                    break;
                case "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)":
                    expression = testHeapReading();
                    break;
                case "Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);":
                    expression = testHeapWriting();
                    break;
                case "string varf;varf=\"test.in\";openRFile(varf);int varc; readFile(varf,varc);print(varc); readFile(varf,varc);print(varc)  closeRFile(varf)":
                    expression = testFileTable();
                    break;
                case "int v; v=4; (while (v>0) print(v);v=v-1);print(v)":
                    expression = testWhileLoop();
                    break;
                case "int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))":
                    expression = testMultithreading();
                    break;
                case "WRONG bool v; v=2;Print(v)":
                    expression = testTypechecking();
                    break;
                case "WRONG int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)":
                    expression = testTypeChecking2();
                    break;
                case "int x; x=10; fork(x=15;fork(x=20;print(x));print(x));print(x)":
                    expression = imbricatedForkStatements();
                    break;
                case "Ref int v1; Ref int v2; Ref int v3; int cnt;new(v1,2);new(v2,3);new(v3,4);newLatch(cnt,rH(v2));fork(wh(v1,rh(v1)*10));print(rh(v1));countDown(cnt); fork(wh(v2,rh(v2)*10));print(rh(v2));countDown(cnt);" +
                        "fork(wh(v3,rh(v3)*10));print(rh(v3));countDown(cnt))));await(cnt);print(100);countDown(cnt);print(100)":
                    expression = examExpression();
                    break;


            }
        }
        else{
            return;
        }

        try{
            expression.typecheck(new SymbolTable<>());
        }
        catch (MyException ex){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Program bad");
            errorAlert.setContentText(ex.toString());
            errorAlert.showAndWait();
            return;
        }

// The Java 8 way to get the response value (with lambda expression).
// result.ifPresent(letter -> System.out.println("Your choice: " + letter));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();

        Controller guiController = fxmlLoader.<Controller>getController();
        guiController.setProgram(expression);

        primaryStage.setTitle("GUI Interpreter");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    private iStatement examExpression() {
        // "Ref int v1; Ref int v2; Ref int v3; int cnt;new(v1,2);new(v2,3);new(v3,4);newLatch(cnt,rH(v2));fork(wh(v1,rh(v1)*10));print(rh(v1));countDown(cnt); fork(wh(v2,rh(v2)*10));print(rh(v2));countDown(cnt);" +
        //                        "fork(wh(v3,rh(v3)*10));print(rh(v3));countDown(cnt))));await(cnt);print(100);countDown(cnt);print(100)"

        iStatement firstFork = null,secondFork = null, thirdFork = null;

        try {
            firstFork = ProgramUtils.concatenate(new WriteHeapStatement("v1",
                    new ArithmeticExpression(
                            '*',
                            new ReadHeapExpression(new VariableExpression("v1")),
                            new ValueExpression(new IntValue(10))
                    )),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                    new CountDownStatement("cnt"));
        }
        catch(MyException ex){ }

        try {
            secondFork = ProgramUtils.concatenate(new WriteHeapStatement("v2",
                            new ArithmeticExpression(
                                    '*',
                                    new ReadHeapExpression(new VariableExpression("v2")),
                                    new ValueExpression(new IntValue(10))
                            )),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
                    new CountDownStatement("cnt"));
        }
        catch(MyException ex){ }

        try {
            thirdFork = ProgramUtils.concatenate(new WriteHeapStatement("v3",
                            new ArithmeticExpression(
                                    '*',
                                    new ReadHeapExpression(new VariableExpression("v3")),
                                    new ValueExpression(new IntValue(10))
                            )),
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v3"))),
                    new CountDownStatement("cnt"));
        }
        catch(MyException ex){ }

        return ProgramUtils.concatenate(
                new VariableDeclarationStatement("v1",new RefType(new IntType())),
                new VariableDeclarationStatement("v2",new RefType(new IntType())),
                new VariableDeclarationStatement("v3",new RefType(new IntType())),
                new VariableDeclarationStatement("cnt",new IntType()),
                new NewStatement("v1",new ValueExpression(new IntValue(2))),
                new NewStatement("v2",new ValueExpression(new IntValue(3))),
                new NewStatement("v3",new ValueExpression(new IntValue(4))),
                new NewLatchStatement("cnt",new ReadHeapExpression(new VariableExpression("v2"))),
                new ForkStatement(firstFork),
                new ForkStatement(secondFork),
                new ForkStatement(thirdFork),
                new AwaitStatement("cnt"),
                new PrintStatement(new ValueExpression(new IntValue(100))),
                new CountDownStatement("cnt"),
                new PrintStatement(new ValueExpression(new IntValue(100)))
                );

    }

    private iStatement imbricatedForkStatements() {

        iStatement innerFork = ProgramUtils.concatenate(
                new AssignStatement("x",new ValueExpression(new IntValue(15))),
                new ForkStatement(
                        new CompoundStatement(
                                new AssignStatement("x",new ValueExpression(new IntValue(20))),
                                new PrintStatement(new VariableExpression("x")))),
                new NopStatement(),
                new PrintStatement(new VariableExpression("x"))
        );


        return ProgramUtils.concatenate(
                new VariableDeclarationStatement("x",new IntType()),
                new AssignStatement("x",new ValueExpression(new IntValue(10))),
                new ForkStatement(innerFork),
                new NopStatement(),
                new NopStatement()
        );
    }

    private iStatement testTypeChecking2() {
        return ProgramUtils.concatenate(
                new VariableDeclarationStatement("v",new IntType()),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                new NewStatement("a", new VariableExpression("v")),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new VariableExpression("a")));
    }

    private iStatement testTypechecking() {
        return new CompoundStatement(
                new VariableDeclarationStatement("v",new BoolType()),
                new CompoundStatement(
                        new AssignStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v")
                        )));
    }


    public static void main(String[] args) {
        launch(args);
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

    private iStatement testHeapAllocation(){
        return ProgramUtils.concatenate(
                new VariableDeclarationStatement("v",new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                new NewStatement("a", new VariableExpression("v")),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new VariableExpression("a")));
    }

    private iStatement testHeapReading(){
        try {
            return ProgramUtils.concatenate(
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
        return null;
    }

    private iStatement testHeapWriting() {
        try {
            return ProgramUtils.concatenate(
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
        return null;
    }

    private iStatement testFileTable() {
        return ProgramUtils.concatenate(
                new VariableDeclarationStatement("varf",new StringType()),
                new AssignStatement("varf",new ValueExpression(new StringValue("test.in"))),
                new OpenRFile(new VariableExpression("varf")),
                new VariableDeclarationStatement("varc",new IntType()),
                new ReadFile(new VariableExpression("varf"),"varc"),
                new PrintStatement(new VariableExpression("varc")),
                new ReadFile(new VariableExpression("varf"),"varc"),
                new PrintStatement(new VariableExpression("varc")),
                new CloseRFile(new VariableExpression("varf"))
        );
    }

    private iStatement testWhileLoop() {
        try {
            return ProgramUtils.concatenate(
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
        return null;
    }

    private iStatement testMultithreading() {

        iStatement forkStatement = ProgramUtils.concatenate(
                new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                new AssignStatement("v", new ValueExpression(new IntValue(32))),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );

        return ProgramUtils.concatenate(
                new VariableDeclarationStatement("v", new IntType()),
                new VariableDeclarationStatement("a", new RefType(new IntType())),
                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                new NewStatement("a", new ValueExpression(new IntValue(22))),
                new ForkStatement(forkStatement),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );
    }

}
