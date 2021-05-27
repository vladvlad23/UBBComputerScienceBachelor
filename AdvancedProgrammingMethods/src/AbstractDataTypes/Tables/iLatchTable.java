package AbstractDataTypes.Tables;

import Domain.Values.Value;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface iLatchTable<T,V> {
    boolean isDefined(T id);

    V getValue(T id);

    void update(T id, V value);

    void update(Integer value);

    T getCurrentFree();


    void decrement(int indexInLatchTable);

    ConcurrentHashMap<Integer, Integer> getHashLatchTable(); //todo hopefully this is ok
}
