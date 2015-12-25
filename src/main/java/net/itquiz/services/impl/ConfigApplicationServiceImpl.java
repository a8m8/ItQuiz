package net.itquiz.services.impl;

import net.itquiz.services.ConfigApplicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Artur Meshcheriakov
 */
@Service
public class ConfigApplicationServiceImpl implements ConfigApplicationService {

    @Value("${net.itquiz.cssJsVersion}")
    private String cssJsVersion;

    @Value("${production.mode}")
    private Boolean productionMode;

    @Value("${test.server.mode}")
    private Boolean testServerMode;

    @Value("${support.email}")
    private String supportEmail;

    @Override
    public String getCssJsVersion() {
        return cssJsVersion;
    }

    @Override
    public Boolean getProductionMode() {
        return productionMode;
    }

    @Override
    public Boolean getTestServerMode() {
        return testServerMode;
    }

    @Override
    public String getSupportEmail() {
        return supportEmail;
    }
}
