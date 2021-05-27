package AbstractDataTypes;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Stream;

public class MyList<T> implements iList {
    private List outList;

    public MyList(){
        outList = new Vector<T>();
    }
    @Override   
    public void add(Object value){
        outList.add(value);

    }

    @Override
    public List getList(){
        return outList;
    }

    @Override
    public Stream<T> getStreamOfElements() {
        return outList.stream();
    }

    @Override
    public String toString(){
        return outList.toString();
    }


}
