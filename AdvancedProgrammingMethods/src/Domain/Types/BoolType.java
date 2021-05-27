package Domain.Types;

import Domain.Values.BoolValue;
import Domain.Values.Value;

public class BoolType implements Type {

    public BoolType(){
    }

    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public String toString(){
        return "bool";
    }
}
