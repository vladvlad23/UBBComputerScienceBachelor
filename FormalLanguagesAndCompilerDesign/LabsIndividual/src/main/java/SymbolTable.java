import java.util.ArrayList;
import java.util.List;

/*
* @author - Ungureanu Ionut-Vladimir. Group 937
*/

/**
 * The symbol table for the not named yet language
 */
public class SymbolTable {

    /**
     * Initial Maximum Hash Table size. Since there is no need yet for resizing, it stays this way for now.
     */
    public static final int HASH_TABLE_SIZE = 5;

    /**
     * The Structure used for implementing the hash table
     */
    private List<List<String>> symbolTableEntries;

    /**
     * <p>The constructor prepares the hash table</p>
     */
    public SymbolTable() {
        symbolTableEntries = new ArrayList<>();
        for (int i = 0; i < HASH_TABLE_SIZE; i++) {
            symbolTableEntries.add(new ArrayList<>());
        }
    }

     /** <p> Function adds the token to a new position in the symbol table </p>
     * @param token the token to be added
     * @return instance of SymbolTable representing the position of the token in the symbol table
     */
    private SymbolTablePosition add(String token) {
        int firstPosition = hashFunction(token);
        List<String> listPosition = symbolTableEntries.get(hashFunction(token));
        if (listPosition == null) {
            listPosition = new ArrayList<>();
        }
        listPosition.add(token);
        symbolTableEntries.set(firstPosition, listPosition); //not sure if it updates automatically from the previous instruction
        return new SymbolTablePosition(firstPosition, listPosition.size() - 1);
    }

    /** <p> Function returns the position of an element in the symbol table even if it does not exist. </p>
     * @param token the token for which the position is required
     * @return instance of SymbolTable representing the position of the token in the symbol table
     */
    public SymbolTablePosition position(String token) {
        int firstPosition = hashFunction(token);
        List<String> listPosition = symbolTableEntries.get(firstPosition);
        for (int secondPosition = 0; secondPosition < listPosition.size(); secondPosition++) {
            if (listPosition.get(secondPosition).equals(token)) {
                return new SymbolTablePosition(firstPosition, secondPosition);
            }
        }
        return add(token);
    }

    /** <p>Hash function for the symbol table</p>
     * @param tokenToBeHashed the token which must be hashed for table introduction
     * @return the hashed value of the token
     */
    private int hashFunction(String tokenToBeHashed) {
        return tokenToBeHashed.toString().chars().sum() % HASH_TABLE_SIZE;
    }

    /**
     * <p> Given a known instance of SymbolTable, returns the token present in the symbol table</p>
     * @param position of type SymbolTable
     * @return the token corresponding to the position or null if the position is invalid
     */
    public String getToken(SymbolTablePosition position) {
        int firstPosition = position.getPositionInFirstList();
        int secondPosition = position.getPositionInSecondList();

        if(firstPosition < 0 || secondPosition < 0 ){
            return null;
        }

        if(symbolTableEntries.size() < firstPosition || symbolTableEntries.get(firstPosition).size()< secondPosition){
            return null;
        }
        return symbolTableEntries.get(firstPosition).get(secondPosition);
    }

    /**
     * <p>Class representing the position of a token in the symbol table </p>
     */
    public static class SymbolTablePosition {
        private int positionInFirstList;
        private int positionInSecondList;

        public SymbolTablePosition(int positionInFirstList, int positionInSecondList) {
            this.positionInFirstList = positionInFirstList;
            this.positionInSecondList = positionInSecondList;
        }

        public int getPositionInFirstList() {
            return positionInFirstList;
        }

        public void setPositionInFirstList(int positionInFirstList) {
            this.positionInFirstList = positionInFirstList;
        }

        public int getPositionInSecondList() {
            return positionInSecondList;
        }

        public void setPositionInSecondList(int positionInSecondList) {
            this.positionInSecondList = positionInSecondList;
        }

        @Override
        public java.lang.String toString() {
            return "Position: " + positionInFirstList + ":" + positionInSecondList;
        }
    }

    @Override
    public java.lang.String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Symbol Table:\n");

        for(int i=0;i<symbolTableEntries.size();i++){
            for(int j=0;j< symbolTableEntries.get(i).size(); j++){
                builder.append(symbolTableEntries.get(i).get(j)).append(" position ").append(i).append(" ").append(j).append("\n");
            }
        }

        return builder.toString();
    }
}
