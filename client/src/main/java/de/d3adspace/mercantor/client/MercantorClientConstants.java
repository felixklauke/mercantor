package de.d3adspace.mercantor.client;

/**
 * The class containing the important constants and default values.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorClientConstants {

    /**
     * The default host of the mercantor server.
     */
    public static final String DEFAULT_SERVER_HOST = "http://localhost";

    /**
     * The default port of the mercantor server.
     */
    public static final int DEFAULT_SERVER_PORT = 8080;

    /**
     * The prefix for the client worker threads.
     */
    public static final String WORKER_THREAD_PREFIX = "Mercantor Client Worker #";

    /**
     * The prefix for the threads that will send heartbeats.
     */
    public static final String SERVICE_THREAD_PREFIX = "Mercantor Service Updating Worker #";
}
