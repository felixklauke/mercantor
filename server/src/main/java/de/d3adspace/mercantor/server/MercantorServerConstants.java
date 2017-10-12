package de.d3adspace.mercantor.server;

import de.d3adspace.mercantor.server.service.repository.mode.ServiceLookupMode;

import java.util.concurrent.TimeUnit;

/**
 * Providing basic constants and default values.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorServerConstants {

    /**
     * The default server host.
     */
    public static final String DEFAULT_SERVER_HOST = "http://localhost";

    /**
     * The default server port.
     */
    public static final int DEFAULT_SERVER_PORT = 8080;

    /**
     * The default service expiration.
     */
    public static final long DEFAULT_SERVICE_EXPIRATION = 30;

    /**
     * The default time unit of the expiration.
     */
    public static final TimeUnit DEFAULT_SERVICE_EXPIRATION_TIMEUNIT = TimeUnit.SECONDS;

    /**
     * The default lookup mode for services.
     */
    public static final ServiceLookupMode DEFAULT_SERVICE_LOOKUP_MODE = ServiceLookupMode.RANDOM;

    /**
     * The prefix for the thread names of the worker threads.
     */
    public static final String WORKER_THREAD_PREFIX = "Mercantor Server Expiration Worker #";
}
