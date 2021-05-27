package Domain.Values;

import Domain.Types.IntType;
import Domain.Types.Type;

public class IntValue implements Value {
    int value;


    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof IntValue)){
            return false;
        }
        return ((IntValue) obj).getValue() == value;
    }


    public IntValue(int v){
        value = v;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return Integer.toString(value);
    }


    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(this.value);
    }
}
