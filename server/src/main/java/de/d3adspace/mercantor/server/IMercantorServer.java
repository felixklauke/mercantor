package de.d3adspace.mercantor.server;

/**
 * The default interface of the server.
 *
 * Default implementation: {@link MercantorServerImpl}.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public interface IMercantorServer {

    /**
     * Start the server.
     */
    void start();

    /**
     * Stop the server.
     */
    void stop();

    /**
     * Check if the server is running.
     *
     * @return if the server is running.
     */
    boolean isRunning();
}
