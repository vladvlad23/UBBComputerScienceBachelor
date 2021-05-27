import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LanguageScanner {

    private SymbolTable symbolTable;
    private String regexSplitVariables = "([a-zA-Z]+)";
//    private String arrayMatch ="(([a-zA-Z]+)\\[([a-zA-Z]+)\\])"
    private String regexConstantSplit = "([0-9]+)";
    private String regexConstantCharSplit = "\" [a-zA-Z] \"";
    private String regexTokens;

    private Hashtable<String, SymbolTable.SymbolTablePosition> pifTable;

    public LanguageScanner(SymbolTable symbolTable, String tokenFile) {
        this.pifTable = new Hashtable<>();
        this.symbolTable = symbolTable;
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        try {
            for(Scanner sc = new Scanner(new File(tokenFile)); sc.hasNext(); ){
                String line = sc.nextLine();
                if(line.chars().anyMatch(c -> "\\.[]{}()*+?^$|".indexOf(c) >= 0)) { //check if metacharacter to escape it
                    builder.append("\\");
//                    builder.append("\\");
                }
                builder.append(line);
                builder.append("|");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        builder.append(")");
        regexTokens = builder.toString();
    }

    public void scanFile(String inputFile){

        try {
            int lineNumber = 0;
            for(Scanner sc = new Scanner(new File(inputFile)); sc.hasNext(); ) {
                lineNumber++;
                String line = sc.nextLine();
                line = line.replaceAll(";","");
                line = line.replaceAll("\\(","");
                line = line.replaceAll("\\)","");
                line = line.replaceAll("\\{","");
                line = line.replaceAll("}","");
                String[] result = line.split("\\s");
                for(String token : result){
                    if(Pattern.matches(regexTokens,token)){
                        pifTable.put(token,new SymbolTable.SymbolTablePosition(-1,-1));
                    }
                    else {
                        if (Pattern.matches(regexSplitVariables, token)) {
                            pifTable.put(token, symbolTable.position(token));
                        }
                        else{
                            if(Pattern.matches(regexConstantSplit, token)){
                                pifTable.put(token, symbolTable.position(token));
                            }
                            else {
                                System.out.println("Error at line " + lineNumber + ".Invalid token");
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(); //todo add relevant error here
        }
        System.out.println("Everything is amazing");
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public String getPIFString(){
        StringBuilder builder = new StringBuilder();
        builder.append("PIF:\n");

        for(String key : pifTable.keySet()){
            builder.append(key).append(" ").append(pifTable.get(key)).append('\n');
        }

        return builder.toString();
    }
}
