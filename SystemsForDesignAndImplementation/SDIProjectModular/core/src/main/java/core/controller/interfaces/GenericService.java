package core.controller.interfaces;
import core.exceptions.ControllerException;
import core.model.BaseEntity;

import java.util.List;
import java.util.function.Function;


public interface GenericService<T extends BaseEntity<Long>> {

    List<T> getAll();
    BaseEntity<Long> create(String data) throws ControllerException;
    T add(T item) throws ControllerException;
    void remove(Long bookId) throws ControllerException;
    T update(T entity);

//    Iterable<T> findAll(Sort sort);

    List<T> filter(Function<BaseEntity<Long>, Boolean> lambda);
}
