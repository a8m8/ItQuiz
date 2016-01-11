package net.itquiz.dao;

import net.itquiz.entities.Test;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface TestDao extends IEntityDao<Test> {

    long countCreatedBy(int idAccount);

    long countAll();

    long countAvailable();

    List<Test> listCreatedBy(int idAccount, int offset, int count);

    List<Test> listAvailable(int offset, int count);

    Test findByTitle(String title);
}
