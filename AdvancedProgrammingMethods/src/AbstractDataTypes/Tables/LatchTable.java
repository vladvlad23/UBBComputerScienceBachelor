package AbstractDataTypes.Tables;

import Domain.Values.Value;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LatchTable implements iLatchTable<Integer,Integer> {
    ConcurrentHashMap<Integer,Integer> table;
    private static AtomicInteger nextFreeMemory = new AtomicInteger(1);


    public LatchTable() {
        this.table = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isDefined(Integer id) {
        return table.containsKey(id);
    }

    @Override
    public Integer getValue(Integer id) {
        //todo since concurrent hashmap is used it is enough locking mechanism
        return table.get(id);
    }

    @Override
    public void update(Integer id, Integer value) {
        table.put(id,value);
    }

    @Override
    public void update(Integer value){
        table.put(nextFreeMemory.getAndIncrement(),value);
    }

    @Override
    public Integer getCurrentFree() {
        return nextFreeMemory.get(); //todo this might be problematic?
    }

    @Override
    public void decrement(int indexInLatchTable) {
        //todo sync?
        int currentValue = table.get(indexInLatchTable);
        table.put(indexInLatchTable,currentValue-1);
    }

    @Override
    public ConcurrentHashMap<Integer,Integer> getHashLatchTable() {
        return this.table;
    }

    @Override
    public String toString(){
        return table.toString();
    }


    //todo deepcopy?
}
