package Domain.Types;

import Domain.Values.StringValue;
import Domain.Values.Value;

public class StringType implements Type {

    @Override
    public Type deepCopy() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another){
        return another instanceof StringType;
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString(){
        return "String";
    }
}
