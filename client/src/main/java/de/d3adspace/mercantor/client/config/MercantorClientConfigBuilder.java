package de.d3adspace.mercantor.client.config;

public class MercantorClientConfigBuilder {
    private String serverHost;
    private int serverPort;

    public MercantorClientConfigBuilder setServerHost(String serverHost) {
        this.serverHost = serverHost;
        return this;
    }

    public MercantorClientConfigBuilder setServerPort(int serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public MercantorClientConfig createMercantorClientConfig() {
        return new MercantorClientConfig(serverHost, serverPort);
    }
}