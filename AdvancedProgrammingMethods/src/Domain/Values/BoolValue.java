package Domain.Values;

import Domain.Types.BoolType;
import Domain.Types.Type;

public class BoolValue implements Value {

    boolean value;

    public BoolValue(boolean value){
        this.value = value;
    }


    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof BoolValue)){
            return false;
        }
        return ((BoolValue) obj).getValue() == value;
    }

    @Override
    public Type getType(){
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(this.value);
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString(){
        return Boolean.toString(value);
    }
}
