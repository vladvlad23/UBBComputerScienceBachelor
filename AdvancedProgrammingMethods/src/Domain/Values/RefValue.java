package Domain.Values;

import Domain.Types.RefType;
import Domain.Types.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    @Override
    public boolean equals(Object another){
        if(!(another instanceof RefValue)){
            return false;
        }
        if(((RefValue) another).getAddress() == this.address && ((RefValue) another).getLocationType().equals(this.locationType))
            return true;
        return false;
    }

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address,locationType.deepCopy());
    }

    @Override
    public String toString(){
        return "(" + Integer.toString(this.address) + "," + locationType.toString() + ")";
    }
}
