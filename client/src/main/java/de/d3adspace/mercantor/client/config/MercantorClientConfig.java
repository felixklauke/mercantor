package de.d3adspace.mercantor.client.config;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorClientConfig {

    private final String serverHost;
    private final int serverPort;

    public MercantorClientConfig(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }
}
