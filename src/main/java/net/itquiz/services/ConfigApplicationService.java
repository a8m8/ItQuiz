package net.itquiz.services;

/**
 * @author Artur Meshcheriakov
 */
public interface ConfigApplicationService {

    String getCssJsVersion();

    Boolean getProductionMode();

    Boolean getTestServerMode();

    String getSupportEmail();

}
