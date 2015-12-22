package net.itquiz.dao;

import net.itquiz.entities.Test;

import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
public interface TestDao extends IEntityDao<Test> {

    List<Test> listTestsCreatedBy(int idAccount, int offset, int count);

    long countTestsCreatedBy(int idAccount);

    long countAllTests();

}
