package de.d3adspace.mercantor.client.config;

/**
 * The central config providing the necessary data for the connection to the server.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorClientConfig {

    /**
     * The host of the mercantor server.
     */
    private final String serverHost;

    /**
     * The port of the mercantor server.
     */
    private final int serverPort;

    /**
     * Create a new config instance. We suggest using {@link MercantorClientConfigBuilder}.
     *
     * @param serverHost The server host.
     * @param serverPort The server port.
     */
    public MercantorClientConfig(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    /**
     * Get the port of the mercantor server.
     *
     * @return The port.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Get the host of the mercantor server.
     *
     * @return The server host.
     */
    public String getServerHost() {
        return serverHost;
    }
}
