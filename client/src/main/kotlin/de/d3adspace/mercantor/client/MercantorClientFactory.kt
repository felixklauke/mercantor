package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorClientConfig

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
object MercantorClientFactory {

    fun createClient(mercantorClientConfig: MercantorClientConfig): MercantorClient {
        return MercantorClientImpl(mercantorClientConfig)
    }
}