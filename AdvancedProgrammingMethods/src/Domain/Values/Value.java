package Domain.Values;

import Domain.Types.Type;
import Domain.iCopyable;

public interface Value extends iCopyable {
    Type getType();
    boolean equals(Object obj);
    Value deepCopy();
    String toString();
}
