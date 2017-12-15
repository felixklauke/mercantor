package de.d3adspace.mercantor.server.rest

import de.d3adspace.mercantor.core.MercantorFactory
import de.d3adspace.mercantor.server.config.MercantorServerConfig
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class RestManagerFactoryTest {

    private val mercantor = MercantorFactory.createMercantor()

    @Test
    fun createRestManager() {
        val restManager = RestManagerFactory.createRestManager(mercantor, MercantorServerConfig("http://locahost:8080"))
        assertNotNull(restManager)
    }
}