package net.itquiz.services.impl;

import net.itquiz.services.ConfigApplicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Artur Meshcheriakov
 */
@Service
public class ConfigApplicationServiceImpl implements ConfigApplicationService {

    @Value("${ua.com.itquiz.supportEmailAddress}")
    private String supportEmailAddress;

    @Value("${ua.com.itquiz.cssJsVersion}")
    private String cssJsVersion;

    @Override
    public String getSupportEmailAddress() {
        return supportEmailAddress;
    }

    @Override
    public String getCssJsVersion() {
        return cssJsVersion;
    }

}
