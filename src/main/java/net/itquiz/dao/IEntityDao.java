package net.itquiz.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface IEntityDao<T> {

    void save(T t);

    void update(T t);

    void delete(T t);

    void remove(T t);

    T findById(Serializable id);

    T getProxy(Serializable id);

    List<T> list();

    List<T> list(int offset, int count);

}
