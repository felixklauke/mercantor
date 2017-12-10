package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorClientConfig
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * @author Felix Klauke <fklauke></fklauke>@itemis.de>
 */
class MercantorClientFactoryTest {

    private lateinit var config: MercantorClientConfig

    @Before
    fun setUp() {
        config = MercantorClientConfig("http://0.0.0.0:8080")
    }

    @Test
    fun createDiscoveryClient() {
        val discoveryClient = MercantorClientFactory.createDiscoveryClient(config)
        assertNotNull(discoveryClient)
    }
}