package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mock

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class MercantorDiscoveryClientFactoryTest {

    @Mock lateinit var config: MercantorDiscoveryClientConfig

    @Test
    fun createDiscoveryClient() {
        val createDiscoveryClient = MercantorDiscoveryClientFactory.createDiscoveryClient(config)
        assertNotNull(createDiscoveryClient)
    }
}