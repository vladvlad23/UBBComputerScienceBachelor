package AbstractDataTypes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface iList<T> {

    public void add(T value);

    List getList();

    Stream<T> getStreamOfElements();
}
