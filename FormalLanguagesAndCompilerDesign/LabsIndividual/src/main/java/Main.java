public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();

        LanguageScanner languageScanner = new LanguageScanner(symbolTable,"token.in");
        languageScanner.scanFile("p3.txt");
        System.out.println(symbolTable.toString());
        System.out.println(languageScanner.getPIFString());


    }
}
