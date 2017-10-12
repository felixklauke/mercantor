package de.d3adspace.mercantor.client;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorClientBootstrap {

    public static void main(String[] args) {
        IMercantorClient mercantorClient = MercantorClientFactory.createMercantorClient();
        mercantorClient.registerService("http://localhost", "boss");
    }
}
