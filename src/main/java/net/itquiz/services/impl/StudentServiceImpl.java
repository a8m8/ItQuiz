package net.itquiz.services.impl;

import net.itquiz.services.StudentService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Artur Meshcheriakov
 */
@Service("studentService")
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StudentServiceImpl implements StudentService {
    // TODO Implementation of StudentService
}
