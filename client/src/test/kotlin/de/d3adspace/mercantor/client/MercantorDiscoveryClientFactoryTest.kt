package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
class MercantorDiscoveryClientFactoryTest {

    private val config: MercantorDiscoveryClientConfig = MercantorDiscoveryClientConfig("http://localhost:8080")

    @Test
    fun createDiscoveryClient() {
        val createDiscoveryClient = MercantorDiscoveryClientFactory.createDiscoveryClient(config)
        assertNotNull(createDiscoveryClient)
    }
}
