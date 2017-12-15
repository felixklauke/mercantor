package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import org.glassfish.jersey.client.JerseyClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(JerseyClient::class)
class ServiceLoaderTest {

    @Mock private lateinit var client: JerseyClient
    private lateinit var serviceLoader: ServiceLoader

    @Before
    fun setUp() {
        serviceLoader = ServiceLoader(MercantorDiscoveryClientConfig("http://0.0.0.8:80"))

        PowerMockito.mockStatic(JerseyClient::class.java)
        PowerMockito.whenNew(JerseyClient::class.java).withAnyArguments().thenReturn(client)
    }

    @Test
    fun loadServices() {
        val testKey = "testVipAddress"

        serviceLoader.loadServices(testKey)
    }
}