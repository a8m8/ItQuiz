package ua.com.itquiz.services.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itquiz.services.CommonService;

/**
 * 
 * @author Artur Meshcheriakov
 */
@Service("commonService")
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CommonServiceImpl implements CommonService {
    // TODO Implementation CommonService
}
