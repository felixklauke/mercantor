package de.d3adspace.mercantor.core

import de.d3adspace.mercantor.commons.model.HeartbeatModel
import de.d3adspace.mercantor.commons.model.ServiceModel
import de.d3adspace.mercantor.commons.model.ServiceStatus
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
class MercantorImplTest {

    private lateinit var mercantor: Mercantor

    @Before
    fun setUp() {
        mercantor = MercantorFactory.createMercantor()
    }

    @Test
    fun registerService() {
        val serviceModel = ServiceModel(UUID.randomUUID(), "", "", "", 0,
                "", ServiceStatus.UP, emptyMap())

        mercantor.registerService(serviceModel)

        Assert.assertEquals(serviceModel, mercantor.getService("")[0])
    }

    @Test
    fun serviceExists() {
        val randomUUID = UUID.randomUUID()

        val serviceModel = ServiceModel(randomUUID, "", "", "", 0,
                "", ServiceStatus.UP, emptyMap())

        mercantor.registerService(serviceModel)

        Assert.assertTrue(mercantor.serviceExists(randomUUID))
    }

    @Test
    fun deleteService() {
        val randomUUID = UUID.randomUUID()

        val serviceModel = ServiceModel(randomUUID, "", "", "", 0,
                "", ServiceStatus.UP, emptyMap())

        mercantor.registerService(serviceModel)

        Assert.assertTrue(mercantor.serviceExists(randomUUID))

        mercantor.deleteService(randomUUID)

        Assert.assertFalse(mercantor.serviceExists(randomUUID))
    }

    @Test
    fun handleHeartbeat() {
        val heartbeatModel = HeartbeatModel(ServiceStatus.UP, UUID.randomUUID())

        mercantor.handleHeartbeat(heartbeatModel)
    }
}
