package de.d3adspace.mercantor.server;

import java.util.concurrent.TimeUnit;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorServerConstants {

    public static final String DEFAULT_SERVER_HOST = "http://localhost";

    public static final int DEFAULT_SERVER_PORT = 8080;

    public static final long DEFAULT_SERVICE_EXPIRATION = 30;

    public static final TimeUnit DEFAULT_SERVICE_EXPIRATION_TIMEUNIT = TimeUnit.SECONDS;

    public static final String WORKER_THREAD_PREFIX = "Mercantor Server Expiration Worker #";
}
