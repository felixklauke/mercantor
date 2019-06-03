package de.d3adspace.mercantor.server.rest

import de.d3adspace.mercantor.core.MercantorFactory
import de.d3adspace.mercantor.server.config.MercantorServerConfig
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
class JerseyRestManagerTest {

    private val mercantor = MercantorFactory.createMercantor()
    private val serverConfig = MercantorServerConfig("http://localhost:8080")
    private val restManager = RestManagerFactory.createRestManager(mercantor, serverConfig)

    @Test
    fun startService() {
        restManager.startService()

        assertTrue(restManager.isServiceRunning())
    }

    @Test
    fun stopService() {
        assertFalse(restManager.isServiceRunning())

        restManager.startService()

        assertTrue(restManager.isServiceRunning())

        restManager.stopService()

        assertFalse(restManager.isServiceRunning())
    }

    @Test
    fun isServiceRunning() {
        assertFalse(restManager.isServiceRunning())

        restManager.startService()

        assertTrue(restManager.isServiceRunning())
    }

    @After
    fun tearDown() {
        if (restManager.isServiceRunning()) {
            restManager.stopService()
        }
    }
}
