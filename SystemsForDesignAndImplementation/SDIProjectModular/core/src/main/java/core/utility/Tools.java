package core.utility;

public class Tools {
    public static int integerParser(String numberString){
        int price = 0;
        try{
            price = new Integer(numberString);
        }
        catch (NumberFormatException e){ throw new RuntimeException("No price found"); }
        return price;
    }
}
