package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class MercantorDiscoveryClientImplTest {

    private val discoveryClient = MercantorDiscoveryClientFactory.createDiscoveryClient(MercantorDiscoveryClientConfig())
}