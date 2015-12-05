package ua.com.itquiz.dao;

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

    void flush();

    T findById(Serializable id);

    List<T> findAll();

    List<T> list(int offset, int count);

}
