package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorClientConfig

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
object MercantorClientFactory {

    fun createDiscoveryClient(mercantorClientConfig: MercantorClientConfig): MercantorDiscoveryClient {
        return MercantorDiscoveryClientImpl(mercantorClientConfig)
    }
}