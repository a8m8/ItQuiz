package ua.com.itquiz.controllers;

import org.apache.log4j.Logger;

/**
 *
 * @author Artur Meshcheriakov
 */
public class AbstractController {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    protected String profile() {
	return null;
    }

}
