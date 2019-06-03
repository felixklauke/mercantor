package de.d3adspace.mercantor.client

import de.d3adspace.mercantor.client.config.MercantorDiscoveryClientConfig
import org.glassfish.jersey.client.JerseyClient
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(JerseyClient::class)
class ServiceContainerTest {

    private val testVipAddress = "de.felix.cool"
    private lateinit var serviceContainer: ServiceContainer
    private lateinit var serviceLoader: ServiceLoader

    @Before
    fun setUp() {
        PowerMockito.mockStatic(JerseyClient::class.java)
        PowerMockito.whenNew(JerseyClient::class.java).withAnyArguments().thenReturn(Mockito.mock(JerseyClient::class.java))

        serviceLoader = ServiceLoader(MercantorDiscoveryClientConfig("http://localhost:8080"))
        serviceContainer = ServiceContainer(testVipAddress, serviceLoader)
    }

    @Test(expected = RuntimeException::class)
    fun getServices() {
        val serviceObservable = serviceContainer.services

        assertNotNull(serviceObservable)
    }
}
