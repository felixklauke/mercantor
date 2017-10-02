package de.d3adspace.mercantor.core;

import java.util.concurrent.TimeUnit;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorConstants {

    /**
     * The prefix the threads handling service expiration should prepend to their id.
     */
    public static final String MERCANTOR_WORKER_THREAD_PREFIX = "Mercantor Service Expiration Thread #";

    /**
     * The default host name of the service.
     */
    public static final String DEFAULT_HOST = "localhost";

    /**
     * The default port of the service.
     */
    public static final int DEFAULT_PORT = 8081;

    /**
     * The default value of the time after that a service should expire.
     */
    public static final long DEFAULT_SERVICE_EXPIRATION = 30;

    /**
     * The time unit of {@link #DEFAULT_SERVICE_EXPIRATION}.
     */
    public static final TimeUnit DEFAULT_SERVICE_EXPIRATION_TIMEUNIT = TimeUnit.SECONDS;

    /**
     * The default interval of the service expiration checks.
     */
    public static final long DEFAULT_SERVICE_EXPIRATION_CHECK_INTERVAL = 30;

    /**
     * The time unit of {@link #DEFAULT_SERVICE_EXPIRATION_CHECK_INTERVAL}.
     */
    public static final TimeUnit DEFAULT_SERVICE_EXPIRATION_CHECK_INTERVAL_TIMEUNIT = TimeUnit.SECONDS;
}
