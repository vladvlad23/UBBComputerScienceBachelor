package sample;

import AbstractDataTypes.ExecutableStack;
import AbstractDataTypes.MyList;
import AbstractDataTypes.Tables.*;
import AbstractDataTypes.iList;
import AbstractDataTypes.iStack;
import Domain.Exceptions.MyEmptyStackException;
import Domain.Exceptions.MyException;
import Domain.ProgramState;
import Domain.Values.Value;
import Repository.Repository;
import Statements.CompoundStatement;
import Statements.iStatement;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Repository.iRepository;
import Controller.ControllerApp;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    private iRepository repository;
    private ControllerApp controllerApp;
    private int activeProgram;

    @FXML
    private TableView<TableRow> countdownLatchTable;

    @FXML
    private TableColumn<TableRow,String> countdownLocationColumn;


    @FXML
    private TableColumn<TableRow,String> countdownValueColumn;


    @FXML
    private TableColumn<TableRow,String> heapAddressColumn;


    @FXML
    private TableColumn<TableRow,String> heapValueColumn;


    @FXML
    private TableColumn<TableRow,String> symbolNameColumn;


    @FXML
    private TableColumn<TableRow,String> symbolValueColumn;

    @FXML
    private ListView outList;

    @FXML
    private ListView listOfProgramStates;

    @FXML
    private ListView executableStack;

    @FXML
    private ListView fileTable;

    @FXML
    private TableView<TableRow> heapTable;

    @FXML
    private TableView<TableRow> symbolTable;

    @FXML
    private TextField numberOfProgramStates;


    @FXML
    public void oneStepEvaluate(){


        try {
            controllerApp.oneStepEvaluationForAllPrograms(repository.getProgramStateList());
        } catch (MyEmptyStackException e) {
            //it's done Maybe should create a popup here
        }
        catch (MyException e){
            e.printStackTrace(); // really a problem. Gotta stop the execution
        }

        if(repository.getProgramStateList().size()==0) //no more program states so the button does nothing now.
            return;

        if(repository.getProgramById(activeProgram) == null) //if there are no more programs, we get the first available one.
            activeProgram = repository.getProgramStateList().get(0).getActiveProgram();
        updateData();

    }


    public void setProgram(iStatement expression) throws MyException {

        // ----- Initialize business logic -----
        repository = new Repository("log.txt");
        controllerApp = new ControllerApp(repository);

        iStack<iStatement> stack = new ExecutableStack();
        SymbolTable<String,Value> table = new SymbolTable<String, Value>();
        iMemoryDictionary fileTable = new FileTable();
        iHeap<Integer, Value> heapTableApp = new HeapTable();
        iList list = new MyList();
        LatchTable latchTable = new LatchTable();
        ProgramState state = new ProgramState(stack,table,list,expression,fileTable,heapTableApp,latchTable);


        //------ Set required properties for the GUI objects -----


        //set listener for when you change the program state
        listOfProgramStates.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            // Your action here
            System.out.println("Selected program state: " + newValue);

            if(newValue==null) // this is for when nothing is selected
                return;

            int programNumber = Integer.parseInt(newValue);
            if(repository.getProgramById(programNumber)!=null){
                System.out.println("Old program state: " + Integer.toString(activeProgram) + " new program state " + newValue);
                activeProgram = programNumber;

                // this must be done otherwise i get an index error given by javafx when i try to clear the observablelist because you shouldn't do that with another event? Point is it gets it done
                Platform.runLater(this::updateData);
            }
            else{
            }
        });

        //set the TableViews

        //heap columns
        heapAddressColumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("name"));

        heapValueColumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("value"));

        symbolNameColumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("name"));

        symbolValueColumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("value"));

        countdownLocationColumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("name"));

        countdownValueColumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("value"));


        repository.addProgram(state);

        activeProgram=1;

    }

    private void updateData() {
        //each function will update the graphical component of its associated structure.


        updateTotalProgramStates();
        updateProgramStates();
        updateHeapTable();
        updateSymbolTable();
        updateExecutableStack();
        updateFileTable();
        updateOutList();
        updateCountdownTable();
    }

    private void updateCountdownTable() {
        countdownLatchTable.getItems().clear();

        ConcurrentHashMap<Integer,Integer> hashSymbolTable = repository.getProgramById(activeProgram).getLatchTable().getHashLatchTable();
        if(hashSymbolTable == null)
            return;
        Set<Integer> keys = hashSymbolTable.keySet();


        ObservableList<TableRow> newLatchTable = FXCollections.observableList(keys.stream()
                .map(e -> new TableRow(Integer.toString(e), hashSymbolTable.get(e).toString()))
                .collect(Collectors.toList()));

        countdownLatchTable.getItems().clear();
        countdownLatchTable.setItems(newLatchTable);
    }

    private void updateTotalProgramStates() {
        numberOfProgramStates.setText(Integer.toString(repository.getProgramStateList().size()));
    }

    private void updateFileTable() {
        fileTable.getItems().clear();

        List<String> temporaryList = (List<String>) ((FileTable) repository.getProgramById(activeProgram).getFileTable()).getStringValues();
        Collections.reverse(temporaryList);

        temporaryList.forEach(e -> fileTable.getItems().add(e));
    }

    private void updateOutList() {
        outList.getItems().clear();

        List<String> temporaryList = (List<String>) (repository.getProgramById(activeProgram).getOut().getStreamOfElements()
                .map(e -> e.toString())
                .collect(Collectors.toList()));
        Collections.reverse(temporaryList);

        temporaryList.forEach(e -> outList.getItems().add(e));
    }

    private void updateExecutableStack() {
        executableStack.getItems().clear();

        Stack stack = ((ExecutableStack) repository.getProgramById(activeProgram).getExecutableStack()).getActualStack();

        //List<String> temporaryList = processStatements(stack);

        List<String> temporaryList = (List<String>) ((ExecutableStack) repository.getProgramById(activeProgram).getExecutableStack()).getStreamOfElements()
                .map(e -> e.toString())
                .collect(Collectors.toList());

        Collections.reverse(temporaryList);

        temporaryList.forEach(e -> executableStack.getItems().add(e));
    }

    private List<String> processStatements(Stack stack) {
        //method required for when i did the alternative way of showing the executable stack which was not showing the compound but rather the full list (version dismissed by course professor)
        List<String> returnList = new LinkedList<>();
        while(!stack.isEmpty()){
            iStatement statement = (iStatement) stack.pop();
            if(statement instanceof CompoundStatement) {
                stack.push(((CompoundStatement) statement).getSecond());
                stack.push(((CompoundStatement) statement).getFirst());
            }
            else {
                returnList.add(statement.toString());
            }
        }
        return returnList;
    }

    private void updateSymbolTable() {
        ConcurrentHashMap<String,Value> hashSymbolTable = repository.getProgramById(activeProgram).getSymbolTable().getHashSymbolTable();
        Set<String> keys = hashSymbolTable.keySet();

        ObservableList<TableRow> newGUISymbolTable = FXCollections.observableList(keys.stream()
                .map(e -> new TableRow(e, hashSymbolTable.get(e).toString()))
                .collect(Collectors.toList()));

        symbolTable.getItems().clear();
        symbolTable.setItems(newGUISymbolTable);
    }

    private void updateHeapTable() {
        ConcurrentHashMap<Integer,Value> hashHeapTable = repository.getProgramById(activeProgram).getHeapTable().getHashHeapTable();
        Set<Integer> keys = hashHeapTable.keySet();

        ObservableList<TableRow> newGUISymbolTable = FXCollections.observableList(keys.stream()
                .map(e -> new TableRow(Integer.toString(e), hashHeapTable.get(e).toString()))
                .collect(Collectors.toList()));

        heapTable.getItems().clear();
        heapTable.setItems(newGUISymbolTable);
    }

    private void updateProgramStates() {

        listOfProgramStates.getItems().clear();

        List<String> temporaryList = (List<String>) (repository.getProgramStateList().stream()
                .map(e -> e.getPersonalId())
                .map(e -> e.toString())
                .collect(Collectors.toList()));
//        Collections.reverse(temporaryList);

        temporaryList.forEach(e -> listOfProgramStates.getItems().add(e));
    }


     public static class TableRow {

        private String name,value;

        private TableRow(String name, String value){
            this.name = name;
            this.value = value;
        }

         public String getName() {
             return name;
         }

         public void setName(String name) {
             this.name = name;
         }

         public String getValue() {
             return value;
         }

         public void setValue(String value) {
             this.value = value;
         }
     }
}
