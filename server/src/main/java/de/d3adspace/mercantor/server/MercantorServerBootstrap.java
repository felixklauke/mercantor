package de.d3adspace.mercantor.server;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorServerBootstrap {

    public static void main(String[] args) {
        IMercantorServer mercantorServer = MercantorServerFactory.createMercantorServer();
        mercantorServer.start();
    }
}
