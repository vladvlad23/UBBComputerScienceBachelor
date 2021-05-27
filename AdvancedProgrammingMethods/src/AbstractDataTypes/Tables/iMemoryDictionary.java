package AbstractDataTypes.Tables;

import Domain.Values.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface iMemoryDictionary<T,V> {

    boolean isDefined(T id);

    V getValue(T id);

    void update(T id, V value);

    void remove(Value value);

    List<V> getContent();

    iMemoryDictionary<T, V> deepCopy();

}

