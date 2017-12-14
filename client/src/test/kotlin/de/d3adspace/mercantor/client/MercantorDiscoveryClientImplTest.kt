package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import de.d3adspace.mercantor.client.exception.NoSuchServiceException
import org.junit.Test

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class MercantorDiscoveryClientImplTest {

    private val discoveryClient = MercantorDiscoveryClientFactory.createDiscoveryClient(MercantorDiscoveryClientConfig())

    @Test(expected = NoSuchServiceException::class)
    fun discoverService() {
        discoveryClient.discoverService("de.test")
    }
}