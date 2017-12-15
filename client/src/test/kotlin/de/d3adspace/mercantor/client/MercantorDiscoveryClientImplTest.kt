package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import org.junit.Test
import java.net.ConnectException

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class MercantorDiscoveryClientImplTest {

    private val discoveryClient = MercantorDiscoveryClientFactory.createDiscoveryClient(MercantorDiscoveryClientConfig())

    @Test(expected = ConnectException::class)
    fun discoverService() {
        discoveryClient.discoverService("de.test")
    }
}