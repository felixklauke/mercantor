package de.d3adspace.mercantor.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class of the whole server.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorServerBootstrap {

    /**
     * The logger to log all actions.
     */
    private static Logger logger = LoggerFactory.getLogger(MercantorServerBootstrap.class);

    public static void main(String[] args) {
        logger.info("Bootstrapping server.");

        IMercantorServer mercantorServer = MercantorServerFactory.createMercantorServer();
        mercantorServer.start();

        logger.info("Bootstrapping server successful.");
    }
}
