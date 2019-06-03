package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
object MercantorDiscoveryClientFactory {

    fun createDiscoveryClient(mercantorDiscoveryClientConfig: MercantorDiscoveryClientConfig): MercantorDiscoveryClient {
        return MercantorDiscoveryClientImpl(mercantorDiscoveryClientConfig)
    }
}
