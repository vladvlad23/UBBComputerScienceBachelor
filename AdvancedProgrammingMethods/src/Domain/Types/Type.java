package Domain.Types;

import Domain.Values.Value;
import Domain.iCopyable;

public interface Type extends iCopyable {

    Type deepCopy();
    Value defaultValue();
    String toString();
}
