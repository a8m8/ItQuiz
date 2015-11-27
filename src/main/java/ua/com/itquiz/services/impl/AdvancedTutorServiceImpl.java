package ua.com.itquiz.services.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itquiz.services.AdvancedTutorService;

/**
 * 
 * @author Artur Meshcheriakov
 */
@Service("advancedTutorService")
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AdvancedTutorServiceImpl implements AdvancedTutorService {
    // TODO AdvancedTutorService
}
