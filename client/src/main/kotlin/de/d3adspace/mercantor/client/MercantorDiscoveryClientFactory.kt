package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
object MercantorDiscoveryClientFactory {

    fun createDiscoveryClient(mercantorDiscoveryClientConfig: MercantorDiscoveryClientConfig): MercantorDiscoveryClient {
        return MercantorDiscoveryClientImpl(mercantorDiscoveryClientConfig)
    }
}