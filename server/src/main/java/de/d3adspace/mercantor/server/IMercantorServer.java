package de.d3adspace.mercantor.server;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public interface IMercantorServer {

    void start();

    void stop();

    boolean isRunning();
}
